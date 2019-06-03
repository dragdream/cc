package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.List;
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
import com.beidasoft.xzzf.punish.document.bean.DocEvidenceDecision;
import com.beidasoft.xzzf.punish.document.bean.DocEvidenceNote;
import com.beidasoft.xzzf.punish.document.model.EvidenceDecisionModel;
import com.beidasoft.xzzf.punish.document.service.EvidenceDecisionService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/evidenceDecisionCtrl")
public class EvidenceDecisionController {

	@Autowired
	private EvidenceDecisionService evidenceDecisionService;
	
	@Autowired
	private TeeWenShuService wenShuService;

	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private CommonService commonService;

	/**
	 * 保存证据先行登记保存处理决定书
	 * 
	 * @param evidenceNoteModel
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(EvidenceDecisionModel evidenceDecisionModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocEvidenceDecision evidenceDecision = new DocEvidenceDecision();
		// 属性值传递
		BeanUtils.copyProperties(evidenceDecisionModel, evidenceDecision);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(evidenceDecisionModel
				.getCheckupDateStartStr())) {
			evidenceDecision.setCheckupDateStart(TeeDateUtil.format(
					evidenceDecisionModel.getCheckupDateStartStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils
				.isNotBlank(evidenceDecisionModel.getCheckupDateEndStr())) {
			evidenceDecision
					.setCheckupDateEnd(TeeDateUtil.format(
							evidenceDecisionModel.getCheckupDateEndStr(),
							"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(evidenceDecisionModel.getLawUnitDateStr())) {
			evidenceDecision.setLawUnitDate(TeeDateUtil.format(
					evidenceDecisionModel.getLawUnitDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(evidenceDecisionModel.getSendTimeStr())) {
			evidenceDecision
					.setSendTime(TeeDateUtil.format(
							evidenceDecisionModel.getSendTimeStr(),
							"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(evidenceDecisionModel
				.getReceiverSignatureDateStr())) {
			evidenceDecision.setReceiverSignatureDate(TeeDateUtil.format(
					evidenceDecisionModel.getReceiverSignatureDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}

		// 设置创建人相关信息
		if (StringUtils.isBlank(evidenceDecisionModel.getId())) {
			evidenceDecision.setId(UUID.randomUUID().toString());
			evidenceDecision.setCreateUserId(user.getUserId());
			evidenceDecision.setCreateUserName(user.getUserName());
			evidenceDecision.setCreateTime(Calendar.getInstance().getTime());
			// 防止出现空
			evidenceDecision.setDelFlg("0");
			//添加文书操作日志
			commonService.writeLog(request, "新建证据先行登记保存处理决定书");
		} else {
			// 设置创建人相关信息
			evidenceDecision.setUpdateUserId(user.getUserId());
			evidenceDecision.setUpdateUserName(user.getUserName());
			evidenceDecision.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改证据先行登记保存处理决定书");
		}
		// 保存 证据先行登记保存处理决定书
		evidenceDecisionService.save(evidenceDecision,request);
		json.setRtData(evidenceDecision);
		json.setRtState(true);
		return json;
	}

	/**
	 * 通过getById 或去对应表单信息
	 * 
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocEvidenceDecision evidenceDecision = evidenceDecisionService
				.getById(id);
		EvidenceDecisionModel evidenceDecisionModel = new EvidenceDecisionModel();
		BeanUtils.copyProperties(evidenceDecision, evidenceDecisionModel);
		// 单独处理时间类型转换

		if (evidenceDecision.getCheckupDateStart() != null) {
			evidenceDecisionModel.setCheckupDateStartStr(TeeDateUtil.format(
					evidenceDecision.getCheckupDateStart(), "yyyy年MM月dd日"));
		}
		if (evidenceDecision.getCheckupDateEnd() != null) {
			evidenceDecisionModel.setCheckupDateEndStr(TeeDateUtil.format(
					evidenceDecision.getCheckupDateEnd(), "yyyy年MM月dd日"));
		}
		if (evidenceDecision.getLawUnitDate() != null) {
			evidenceDecisionModel.setLawUnitDateStr(TeeDateUtil.format(
					evidenceDecision.getLawUnitDate(), "yyyy年MM月dd日"));
		}
		if (evidenceDecision.getSendTime() != null) {
			evidenceDecisionModel.setSendTimeStr(TeeDateUtil.format(
					evidenceDecision.getSendTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (evidenceDecision.getReceiverSignatureDate() != null) {
			evidenceDecisionModel.setReceiverSignatureDateStr(TeeDateUtil
					.format(evidenceDecision.getReceiverSignatureDate(),
							"yyyy年MM月dd日 HH时mm分"));
		}
		// 返回 证据先行登记保存处理决定书json 对象
		json.setRtData(evidenceDecisionModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 预览文书
	 * 
	 * @param evidenceDecisionModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/viewDocInfo")
	public TeeJson viewDocInfo(EvidenceDecisionModel evidenceDecisionModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = evidenceDecisionModel
				.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 证据先行登记保存处理通知书取证据先行登记保存通知书信息
	 * @param baseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromEvid")
	public TeeJson getDocFromEvid(String baseId, String lawLinkId) {
		TeeJson json = new TeeJson();
		List<DocEvidenceNote> evidList = (List<DocEvidenceNote>) commonService.getDocByBaseId("DocEvidenceNote", baseId, lawLinkId);
		json.setRtData(evidList);
		return json;
  }
}
