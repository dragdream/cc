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
import com.beidasoft.xzzf.punish.document.bean.DocPaymentinstallments;
import com.beidasoft.xzzf.punish.document.model.PaymentinstallmentsModel;
import com.beidasoft.xzzf.punish.document.service.PaymentinstallmentsService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/paymentinstallmentsCtrl")
public class PaymentinstallmentsController {
	@Autowired
	private PaymentinstallmentsService paymentinstallmentsService;

	@Autowired
	private TeeWenShuService wenShuService;

	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private CommonService commonService;

	/**
	 * 保存责令改正通知书
	 * 
	 * @param paymentinstallmentsModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(
			PaymentinstallmentsModel paymentinstallmentsModel,
			HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocPaymentinstallments paymentinstallments = new DocPaymentinstallments();
		// 属性值传递
		BeanUtils.copyProperties(paymentinstallmentsModel, paymentinstallments);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(paymentinstallmentsModel.getSendDataStr())) {
			paymentinstallments.setSendData(TeeDateUtil.format(
					paymentinstallmentsModel.getSendDataStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(paymentinstallmentsModel.getLastDataStr())) {
			paymentinstallments.setLastData(TeeDateUtil.format(
					paymentinstallmentsModel.getLastDataStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(paymentinstallmentsModel
				.getExtensionDateStr())) {
			paymentinstallments.setExtensionDate(TeeDateUtil.format(
					paymentinstallmentsModel.getExtensionDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(paymentinstallmentsModel
				.getFirstPhaseDataStr())) {
			paymentinstallments.setFirstPhaseData(TeeDateUtil.format(
					paymentinstallmentsModel.getFirstPhaseDataStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(paymentinstallmentsModel
				.getTwoPhaseDataStr())) {
			paymentinstallments.setTwoPhaseData(TeeDateUtil.format(
					paymentinstallmentsModel.getTwoPhaseDataStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(paymentinstallmentsModel
				.getThreePhaseDataStr())) {
			paymentinstallments.setThreePhaseData(TeeDateUtil.format(
					paymentinstallmentsModel.getThreePhaseDataStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(paymentinstallmentsModel.getStampDateStr())) {
			paymentinstallments.setStampDate(TeeDateUtil.format(
					paymentinstallmentsModel.getStampDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(paymentinstallmentsModel.getSendDateStr())) {
			paymentinstallments.setSendDate(TeeDateUtil.format(
					paymentinstallmentsModel.getSendDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils
				.isNotBlank(paymentinstallmentsModel.getReceiptDateStr())) {
			paymentinstallments.setReceiptDate(TeeDateUtil.format(
					paymentinstallmentsModel.getReceiptDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(paymentinstallmentsModel.getId())) {
			paymentinstallments.setId(UUID.randomUUID().toString());
			paymentinstallments.setCreateUserId(user.getUserId());
			paymentinstallments.setCreateUserName(user.getUserName());
			paymentinstallments.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建延期、分期缴纳罚款审批书");
			// 防止出现空
			paymentinstallments.setDelFlg("0");
		} else {
			// 设置创建人相关信息
			paymentinstallments.setUpdateUserId(user.getUserId());
			paymentinstallments.setUpdateUserName(user.getUserName());
			paymentinstallments.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改延期、分期缴纳罚款审批书");
		}
		
		// 保存 延期、分期缴纳罚款审批书
		paymentinstallmentsService.save(paymentinstallments , request);
		json.setRtData(paymentinstallments);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取责令改正通知书
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		DocPaymentinstallments paymentinstallments = paymentinstallmentsService
				.getById(id);
		PaymentinstallmentsModel paymentinstallmentsModel = new PaymentinstallmentsModel();
		BeanUtils.copyProperties(paymentinstallments, paymentinstallmentsModel);
		// 单独处理时间类型转换
		if (paymentinstallments.getSendData() != null) {
			paymentinstallmentsModel.setSendDataStr(TeeDateUtil.format(
					paymentinstallments.getSendData(), "yyyy年MM月dd日"));
		}
		if (paymentinstallments.getLastData() != null) {
			paymentinstallmentsModel.setLastDataStr(TeeDateUtil.format(
					paymentinstallments.getLastData(), "yyyy年MM月dd日"));
		}
		if (paymentinstallments.getExtensionDate() != null) {
			paymentinstallmentsModel.setExtensionDateStr(TeeDateUtil.format(
					paymentinstallments.getExtensionDate(), "yyyy年MM月dd日"));
		}
		if (paymentinstallments.getFirstPhaseData() != null) {
			paymentinstallmentsModel.setFirstPhaseDataStr(TeeDateUtil.format(
					paymentinstallments.getFirstPhaseData(), "yyyy年MM月dd日"));
		}
		if (paymentinstallments.getTwoPhaseData() != null) {
			paymentinstallmentsModel.setTwoPhaseDataStr(TeeDateUtil.format(
					paymentinstallments.getTwoPhaseData(), "yyyy年MM月dd日"));
		}
		if (paymentinstallments.getThreePhaseData() != null) {
			paymentinstallmentsModel.setThreePhaseDataStr(TeeDateUtil.format(
					paymentinstallments.getThreePhaseData(), "yyyy年MM月dd日"));
		}
		if (paymentinstallments.getStampDate() != null) {
			paymentinstallmentsModel.setStampDateStr(TeeDateUtil.format(
					paymentinstallments.getStampDate(), "yyyy年MM月dd日"));
		}
		if (paymentinstallments.getSendDate() != null) {
			paymentinstallmentsModel.setSendDateStr(TeeDateUtil.format(
					paymentinstallments.getSendDate(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (paymentinstallments.getReceiptDate() != null) {
			paymentinstallmentsModel.setReceiptDateStr(TeeDateUtil.format(
					paymentinstallments.getReceiptDate(), "yyyy年MM月dd日 HH时mm分"));
		}
		// 返回 延期、分期缴纳罚款审批书json 对象
		json.setRtData(paymentinstallmentsModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 预览文书
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(
			PaymentinstallmentsModel paymentinstallmentsModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = paymentinstallmentsModel
				.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
