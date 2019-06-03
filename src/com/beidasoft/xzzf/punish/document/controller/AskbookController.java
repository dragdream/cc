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

import com.beidasoft.xzzf.punish.document.bean.DocAskbook;
import com.beidasoft.xzzf.punish.document.model.AskbookModel;
import com.beidasoft.xzzf.punish.document.service.AskbookService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/askbookCtrl")
public class AskbookController {

	@Autowired
	private AskbookService askbookService;

	@Autowired
	private TeeWenShuService wenShuService;

	/**
	 * 保存调查询问通知书信息
	 * @param askbookModel
	 * @param request
	 * @return
	 */
	public TeeJson saveDocInfo(AskbookModel askbookModel,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//实例化实体类对象
		DocAskbook docAskbook = new DocAskbook();
		
		//属性值传递
		BeanUtils.copyProperties(askbookModel, docAskbook);
		
		//单独处理时间类型转换(到达时间)
		if (StringUtils.isNotBlank(askbookModel.getArravelTimeStr())) {
			docAskbook.setArravelTime(TeeDateUtil.format(askbookModel.getArravelTimeStr(),
					"yyyy年MM月dd日 HH时"));
		}
		//单独处理时间类型转换(盖章时间)
		if(StringUtils.isNotBlank(askbookModel.getLawUnitDateStr())) {
			docAskbook.setLawUnitDate(TeeDateUtil.format(askbookModel.getLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		//设置创建人相关信息
		if (StringUtils.isBlank(askbookModel.getId()) ) {
			docAskbook.setCreateUserName(user.getUserName());
			docAskbook.setCreateUserId(user.getUuid()+"");
			docAskbook.setCreateTime(Calendar.getInstance().getTime());
			docAskbook.setDelFlg("0");
		}
		//设置修改人相关信息
		docAskbook.setUpdateUserName(user.getUserName());
		docAskbook.setUpdateUserId(user.getUserId());
		docAskbook.setUpdateTime(Calendar.getInstance().getTime());
		
		//判断主键是否为空
		if (StringUtils.isBlank(askbookModel.getId())) {
			//生成主键
			docAskbook.setId(UUID.randomUUID().toString());
			//保存调查询问通知书信息
			askbookService.saveDocInfo(docAskbook);
		}else {
			//改调查询问通知书信息
			askbookService.updateDocInfo(docAskbook);
		}
		json.setRtData(docAskbook);
		json.setRtData(true);
		return json;
	}
	/**
	 * 查询调查询问通知书信息（根据ID）
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id) {
		TeeJson json = new TeeJson();
		DocAskbook docAskbook = askbookService.getDocInfo(id);
		AskbookModel askbookModel = new AskbookModel();
		BeanUtils.copyProperties(docAskbook, askbookModel);
		//单独处理时间类型转换
		if (docAskbook.getArravelTime() != null) {
			askbookModel.setArravelTimeStr(TeeDateUtil.format(docAskbook.getArravelTime(),
					"yyyy年MM月dd日 HH时 "));
		}
		if (docAskbook.getLawUnitDate() != null) {
			askbookModel.setLawUnitDateStr(TeeDateUtil.format(docAskbook.getLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		json.setRtData(askbookModel);
		json.setRtData(true);
		return json;
	}
	
	/**
	 * 调查询问通知书信息预览
	 * @param askbookModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(AskbookModel askbookModel,HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = askbookModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		
		return json;
	}
}
