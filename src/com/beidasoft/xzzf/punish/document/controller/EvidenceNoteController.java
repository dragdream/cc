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

import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocEvidenceNote;
import com.beidasoft.xzzf.punish.document.bean.DocPutonrecord;
import com.beidasoft.xzzf.punish.document.model.EvidenceNoteModel;
import com.beidasoft.xzzf.punish.document.service.EvidenceNoteService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/evidenceNoteCtrl")
public class EvidenceNoteController {

	@Autowired
	private EvidenceNoteService evidenceNoteService;

	@Autowired
	private TeeWenShuService wenShuService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private PunishFlowService flowService;
	/**
	 * 保存证据先行登记保存通知书
	 * 
	 * @param evidenceNoteModel
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(EvidenceNoteModel evidenceNoteModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		// 1.保存证据先行登记保存通知书
		DocEvidenceNote evidenceNote = new DocEvidenceNote();
		BeanUtils.copyProperties(evidenceNoteModel, evidenceNote);

		// 设置时间赋值
		if (StringUtils.isNotBlank(evidenceNoteModel.getSaveDateStartStr())) {
			evidenceNote.setSaveDateStart(TeeDateUtil.format(
					evidenceNoteModel.getSaveDateStartStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(evidenceNoteModel.getSaveDateEndStr())) {
			evidenceNote.setSaveDateEnd(TeeDateUtil.format(
					evidenceNoteModel.getSaveDateEndStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(evidenceNoteModel.getLawUnitDateStr())) {
			evidenceNote.setLawUnitDate(TeeDateUtil.format(
					evidenceNoteModel.getLawUnitDateStr(), "yyyy年MM月dd日"));
		}

		if (StringUtils.isNotBlank(evidenceNoteModel.getSendTimeStr())) {
			evidenceNote.setSendTime(TeeDateUtil.format(
					evidenceNoteModel.getSendTimeStr(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(evidenceNoteModel
				.getReceiverSignatureDateStr())) {
			evidenceNote.setReceiverSignatureDate(TeeDateUtil.format(
					evidenceNoteModel.getReceiverSignatureDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(evidenceNoteModel.getId())) {
			evidenceNote.setId(UUID.randomUUID().toString());
			evidenceNote.setCreateUserId(user.getUserId());
			evidenceNote.setCreateUserName(user.getUserName());
			evidenceNote.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建证据先行登记保存通知书");
		} else {
			// 设置创建人相关信息
			evidenceNote.setUpdateUserId(user.getUserId());
			evidenceNote.setUpdateUserName(user.getUserName());
			evidenceNote.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改证据先行登记保存通知书");
		}
		
		evidenceNote.setDelFlg("0");
		// 保存 证据先行登记保存通知书
		evidenceNoteService.save(evidenceNote,request);
		json.setRtData(evidenceNote);
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
	public TeeJson getById(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();

		// 执行getById方法
		DocEvidenceNote evidenceNote = evidenceNoteService.getById(id);

		// 将bean实体类转换为model展示前台
		EvidenceNoteModel evidenceNoteModel = new EvidenceNoteModel();
		BeanUtils.copyProperties(evidenceNote, evidenceNoteModel);

		// 再将时间等特殊字段进行转换
		if (evidenceNote.getSaveDateStart() != null) {
			evidenceNoteModel.setSaveDateStartStr(TeeDateUtil.format(
					evidenceNote.getSaveDateStart(), "yyyy年MM月dd日"));
		}
		if (evidenceNote.getSaveDateEnd() != null) {
			evidenceNoteModel.setSaveDateEndStr(TeeDateUtil.format(
					evidenceNote.getSaveDateEnd(), "yyyy年MM月dd日"));
		}
		if (evidenceNote.getLawUnitDate() != null) {
			evidenceNoteModel.setLawUnitDateStr(TeeDateUtil.format(
					evidenceNote.getLawUnitDate(), "yyyy年MM月dd日"));
		}
		if (evidenceNote.getSendTime() != null) {
			evidenceNoteModel.setSendTimeStr(TeeDateUtil.format(
					evidenceNote.getSendTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (evidenceNote.getReceiverSignatureDate() != null) {
			evidenceNoteModel.setReceiverSignatureDateStr(TeeDateUtil.format(
					evidenceNote.getReceiverSignatureDate(),
					"yyyy年MM月dd日 HH时mm分"));
		}

		// 将数据以json格式展示前台
		json.setRtData(evidenceNoteModel);
		json.setRtState(true);
		return json;
	}

	/*	*//**
	 * 修改
	 * 
	 * @param evidenceNoteModel
	 * @return
	 */

	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(EvidenceNoteModel evidenceNoteModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = evidenceNoteModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}

	/**
	 * 证据先行登记保存通知书取立案表的信息
	 * @param baseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromPuton")
	public TeeJson getDocFromPuton(String baseId) {
		TeeJson json = new TeeJson();
		List<PunishFLow> list = flowService.getFlowcase(baseId, "立案审批", "");//通过baseId 和 流程名  查流程 信息（立案审批一般只有一个）
		PunishFLow fir = list.get(0);
		String lawLinkId = fir.getTacheId();
		List<DocPutonrecord> putoList = (List<DocPutonrecord>) commonService.getDocByBaseId("DocPutonrecord", baseId, lawLinkId);
		json.setRtData(putoList);
		return json;
  }
}
