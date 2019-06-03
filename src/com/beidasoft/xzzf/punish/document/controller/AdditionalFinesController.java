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
import com.beidasoft.xzzf.punish.document.bean.DocAdditionalFines;
import com.beidasoft.xzzf.punish.document.model.AdditionalFinesModel;
import com.beidasoft.xzzf.punish.document.service.AdditionalFinesService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/additionalFinesCtrl")
public class AdditionalFinesController {
	@Autowired
	private AdditionalFinesService additionalFinesService;

	@Autowired
	private TeeWenShuService wenShuService;

	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 行政处罚加处罚款决定书的保存
	 * 
	 * @param additionalFinesModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(AdditionalFinesModel additionalFinesModel,
			HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocAdditionalFines additionalFines = new DocAdditionalFines();
		// 属性值传递
		BeanUtils.copyProperties(additionalFinesModel, additionalFines);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(additionalFinesModel
				.getPenaltyDecisoinSentDateStr())) {
			additionalFines.setPenaltyDecisoinSentDate(TeeDateUtil.format(
					additionalFinesModel.getPenaltyDecisoinSentDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(additionalFinesModel.getClosingDateStr())) {
			additionalFines.setClosingDate(TeeDateUtil.format(
					additionalFinesModel.getClosingDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(additionalFinesModel.getLawUnitDateStr())) {
			additionalFines.setLawUnitDate(TeeDateUtil.format(
					additionalFinesModel.getLawUnitDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(additionalFinesModel.getSendTimeStr())) {
			additionalFines
					.setSendTime(TeeDateUtil.format(
							additionalFinesModel.getSendTimeStr(),
							"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(additionalFinesModel
				.getReceiverSignatureDateStr())) {
			additionalFines.setReceiverSignatureDate(TeeDateUtil.format(
					additionalFinesModel.getReceiverSignatureDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(additionalFinesModel.getId())) {
			additionalFines.setId(UUID.randomUUID().toString());
			additionalFines.setCreateUserId(user.getUserId());
			additionalFines.setCreateUserName(user.getUserName());
			additionalFines.setCreateTime(Calendar.getInstance().getTime());
			// 防止出现空
			additionalFines.setDelFlg("0");
			//添加文书操作日志
			commonService.writeLog(request, "新建行政处罚加处罚款决定书");
		} else {
			// 设置修改人相关信息
			additionalFines.setUpdateUserId(user.getUserId());
			additionalFines.setUpdateUserName(user.getUserName());
			additionalFines.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改行政处罚加处罚款决定书");
		}
		
		// 保存 行政处罚加处罚款决定书
		additionalFinesService.save(additionalFines , request);
		json.setRtData(additionalFines);
		json.setRtState(true);
		return json;
	}

	/**
	 * 行政处罚加处罚款决定书的获取
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		DocAdditionalFines additionalFines = additionalFinesService.getById(id);
		AdditionalFinesModel additionalFinesModel = new AdditionalFinesModel();
		BeanUtils.copyProperties(additionalFines, additionalFinesModel);

		// 单独处理时间类型转换

		if (additionalFines.getPenaltyDecisoinSentDate() != null) {
			additionalFinesModel.setPenaltyDecisoinSentDateStr(TeeDateUtil
					.format(additionalFines.getPenaltyDecisoinSentDate(),
							"yyyy年MM月dd日"));
		}
		if (additionalFines.getClosingDate() != null) {
			additionalFinesModel.setClosingDateStr(TeeDateUtil.format(
					additionalFines.getClosingDate(), "yyyy年MM月dd日"));
		}
		if (additionalFines.getLawUnitDate() != null) {
			additionalFinesModel.setLawUnitDateStr(TeeDateUtil.format(
					additionalFines.getLawUnitDate(), "yyyy年MM月dd日"));
		}
		if (additionalFines.getSendTime() != null) {
			additionalFinesModel.setSendTimeStr(TeeDateUtil.format(
					additionalFines.getSendTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (additionalFines.getReceiverSignatureDate() != null) {
			additionalFinesModel.setReceiverSignatureDateStr(TeeDateUtil
					.format(additionalFines.getReceiverSignatureDate(),
							"yyyy年MM月dd日 HH时mm分"));
		}

		// 返回 行政处罚加处罚款决定书json 对象
		json.setRtData(additionalFinesModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 预览行政处罚加处罚款决定书
	 * 
	 * @param additionalFinesModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(AdditionalFinesModel additionalFinesModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = additionalFinesModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
