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
import com.beidasoft.xzzf.punish.document.bean.DocFinesNotice;
import com.beidasoft.xzzf.punish.document.model.FinesNoticeModel;
import com.beidasoft.xzzf.punish.document.service.FinesNoticeService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/finesNoticeCtrl")
public class FinesNoticeController {
	
	@Autowired
	private FinesNoticeService finesNoticeService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存行政处罚履行催告书信息
	 * @param finesNoticeModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(FinesNoticeModel finesNoticeModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocFinesNotice docFinesNotice = new DocFinesNotice();
		
		// 属性值传递
		BeanUtils.copyProperties(finesNoticeModel, docFinesNotice);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(finesNoticeModel.getPenaltyDecisoinSentDateStr())) {
			docFinesNotice.setPenaltyDecisoinSentDate(TeeDateUtil.format(finesNoticeModel.getPenaltyDecisoinSentDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finesNoticeModel.getAddPenaltyDecisoinSentDateStr())) {
			docFinesNotice.setAddPenaltyDecisoinSentDate(TeeDateUtil.format(finesNoticeModel.getAddPenaltyDecisoinSentDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finesNoticeModel.getLawUnitDateStr())) {
			docFinesNotice.setLawUnitDate(TeeDateUtil.format(finesNoticeModel.getLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finesNoticeModel.getSendTimeStr())) {
			docFinesNotice.setSendTime(TeeDateUtil.format(finesNoticeModel.getSendTimeStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(finesNoticeModel.getReceiverSignatureDateStr())) {
			docFinesNotice.setReceiverSignatureDate(TeeDateUtil.format(finesNoticeModel.getReceiverSignatureDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		
		
		// 设置创建人相关信息
		if (StringUtils.isBlank(finesNoticeModel.getId())) {
			docFinesNotice.setId(UUID.randomUUID().toString());
			docFinesNotice.setCreateUserName(user.getUserName());
			docFinesNotice.setCreateUserId(user.getUuid()+"");
			docFinesNotice.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建行政处罚履行催告书");
			docFinesNotice.setDelFlg("0");
		} else {
			//设置修改人相关信息
			docFinesNotice.setUpdateUserId(user.getUserId());
			docFinesNotice.setUpdateUserName(user.getUserName());
			docFinesNotice.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改行政处罚履行催告书");
		}
		// 保存 行政处罚履行催告书记录表
		finesNoticeService.saveDocInfo(docFinesNotice , request);
		
		json.setRtData(docFinesNotice);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 查询行政处罚履行催告书信息（根据Id）
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocFinesNotice docFinesNotice = finesNoticeService.getDocInfo(id);
		FinesNoticeModel finesNoticeModel = new FinesNoticeModel();
		BeanUtils.copyProperties(docFinesNotice, finesNoticeModel);
		// 单独处理时间类型转换
		if (docFinesNotice.getPenaltyDecisoinSentDate() != null) {
			finesNoticeModel.setPenaltyDecisoinSentDateStr(TeeDateUtil.format(docFinesNotice.getPenaltyDecisoinSentDate(),
					"yyyy年MM月dd日"));
		}
		if (docFinesNotice.getAddPenaltyDecisoinSentDate() != null) {
			finesNoticeModel.setAddPenaltyDecisoinSentDateStr(TeeDateUtil.format(docFinesNotice.getAddPenaltyDecisoinSentDate(),
					"yyyy年MM月dd日"));
		}
		if (docFinesNotice.getLawUnitDate() != null) {
			finesNoticeModel.setLawUnitDateStr(TeeDateUtil.format(docFinesNotice.getLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		if (docFinesNotice.getSendTime() != null) {
			finesNoticeModel.setSendTimeStr(TeeDateUtil.format(docFinesNotice.getSendTime(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (docFinesNotice.getReceiverSignatureDate() != null) {
			finesNoticeModel.setReceiverSignatureDateStr(TeeDateUtil.format(docFinesNotice.getReceiverSignatureDate(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 返回 行政处罚履行催告书记录表 json 对象
		json.setRtData(finesNoticeModel);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 预览行政处罚履行催告书信息
	 * @param finesNoticeModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(FinesNoticeModel finesNoticeModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = finesNoticeModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
