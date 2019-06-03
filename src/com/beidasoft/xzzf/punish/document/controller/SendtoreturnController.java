package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocSendtoreturn;
import com.beidasoft.xzzf.punish.document.model.SendtoreturnModel;
import com.beidasoft.xzzf.punish.document.service.SendtoreturnService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;



@Controller
@RequestMapping("/sendtoreturnCtrl")
public class SendtoreturnController {
	@Autowired
	private SendtoreturnService sendtoreturnService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存送达回证信息
	 * @param sendtoreturnModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(SendtoreturnModel sendtoreturnModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocSendtoreturn docSendtoreturn = new DocSendtoreturn();
		
		// 属性值传递
		BeanUtils.copyProperties(sendtoreturnModel, docSendtoreturn);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(sendtoreturnModel.getArrvelTimeStr())) {
			docSendtoreturn.setArrvelTime(TeeDateUtil.format(sendtoreturnModel.getArrvelTimeStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(sendtoreturnModel.getArrvelTimeOneStr())) {
			docSendtoreturn.setArrvelTimeOne(TeeDateUtil.format(sendtoreturnModel.getArrvelTimeOneStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(sendtoreturnModel.getArrvelTimeTwoStr())) {
			docSendtoreturn.setArrvelTimeTwo(TeeDateUtil.format(sendtoreturnModel.getArrvelTimeTwoStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(sendtoreturnModel.getArrvelTimeThreeStr())) {
			docSendtoreturn.setArrvelTimeThree(TeeDateUtil.format(sendtoreturnModel.getArrvelTimeThreeStr(),
					"yyyy年MM月dd日"));
		}
		
		// 设置创建人相关信息
		if (StringUtils.isBlank(sendtoreturnModel.getId())) {
			docSendtoreturn.setId(UUID.randomUUID().toString());
			docSendtoreturn.setCreateUserName(user.getUserName());
			docSendtoreturn.setCreateUserId(user.getUuid()+"");
			docSendtoreturn.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建送达回证");
			docSendtoreturn.setDelFlg("0");
		} else {
			//设置修改人相关信息
			docSendtoreturn.setUpdateUserId(user.getUserId());
			docSendtoreturn.setUpdateUserName(user.getUserName());
			docSendtoreturn.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改送达回证");
		}
		
		// 保存 送达回证记录表
		sendtoreturnService.saveDocInfo(docSendtoreturn, request);
		
		json.setRtData(docSendtoreturn);
		json.setRtState(true);

		return json;
	}
	/**
	 * 查询送达回证信息（根据ID）
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocSendtoreturn docSendtoreturn = sendtoreturnService.getDocInfo(id);
		SendtoreturnModel sendtoreturnModel = new SendtoreturnModel();
		BeanUtils.copyProperties(docSendtoreturn, sendtoreturnModel);
		// 单独处理时间类型转换
		if (docSendtoreturn.getArrvelTime() != null) {
			sendtoreturnModel.setArrvelTimeStr(TeeDateUtil.format(docSendtoreturn.getArrvelTime(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (docSendtoreturn.getArrvelTimeOne() != null) {
			sendtoreturnModel.setArrvelTimeOneStr(TeeDateUtil.format(docSendtoreturn.getArrvelTimeOne(),
					"yyyy年MM月dd日"));
		}
		if (docSendtoreturn.getArrvelTimeTwo() != null) {
			sendtoreturnModel.setArrvelTimeTwoStr(TeeDateUtil.format(docSendtoreturn.getArrvelTimeTwo(),
					"yyyy年MM月dd日"));
		}
		if (docSendtoreturn.getArrvelTimeThree() != null) {
			sendtoreturnModel.setArrvelTimeThreeStr(TeeDateUtil.format(docSendtoreturn.getArrvelTimeThree(),
					"yyyy年MM月dd日 "));
		}
		// 返回 送达回证记录表 json 对象
		json.setRtData(sendtoreturnModel);
		json.setRtState(true);

		return json;
	}

	/**
	 * 送达回证信息预览
	 * @param sendtoreturnModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(SendtoreturnModel sendtoreturnModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = sendtoreturnModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
