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
import com.beidasoft.xzzf.punish.document.bean.DocEngorceApplication;
import com.beidasoft.xzzf.punish.document.model.EngorceApplicationModel;
import com.beidasoft.xzzf.punish.document.service.EngorceApplicationService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/engorceApplicationCtrl")
public class EngorceApplicationController {
	@Autowired
	private EngorceApplicationService engorceApplicationService;
	@Autowired
	private TeeWenShuService wenShuService;
	@Autowired
	private CommonService commonService;
	/*
	 * 强制执行申请书
	 */

	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(EngorceApplicationModel engorceApplicationModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocEngorceApplication engorceApplication = new DocEngorceApplication();
		// 属性值传递
		BeanUtils.copyProperties(engorceApplicationModel, engorceApplication);
		// 单独处理时间类型转换
		// 处罚决定书送达日期 enforceDataStr
		if (StringUtils.isNotBlank(engorceApplicationModel.getEnforceDataStr())) {
			engorceApplication
					.setEnforceData(TeeDateUtil.format(
							engorceApplicationModel.getEnforceDataStr(),
							"yyyy年MM月dd日"));
		}
		// 加处罚决定书送达日期 AddPenaltyDecisionStr
		if (StringUtils.isNotBlank(engorceApplicationModel
				.getAddPenaltyDecisionStr())) {
			engorceApplication.setAddPenaltyDecision(TeeDateUtil.format(
					engorceApplicationModel.getAddPenaltyDecisionStr(),
					"yyyy年MM月dd日"));
		}
		// 催告书送达日期 ExigentNoticeDataStr
		if (StringUtils.isNotBlank(engorceApplicationModel
				.getExigentNoticeDataStr())) {
			engorceApplication.setExigentNoticeData(TeeDateUtil.format(
					engorceApplicationModel.getExigentNoticeDataStr(),
					"yyyy年MM月dd日"));
		}
		// 行政机关盖章时间 LawUnitDateStr
		if (StringUtils.isNotBlank(engorceApplicationModel.getLawUnitDateStr())) {
			engorceApplication
					.setLawUnitDate(TeeDateUtil.format(
							engorceApplicationModel.getLawUnitDateStr(),
							"yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(engorceApplicationModel.getId())) {
			engorceApplication.setId(UUID.randomUUID().toString());
			engorceApplication.setCreateUserId(user.getUserId());
			engorceApplication.setCreateUserName(user.getUserName());
			engorceApplication.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建强制执行申请书");
		} else{
			// 设置创建人相关信息
			engorceApplication.setUpdateUserId(user.getUserId());
			engorceApplication.setUpdateUserName(user.getUserName());
			engorceApplication.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改强制执行申请书");
		}
		engorceApplication.setDelFlg("0");
		// 保存 强制执行申请书
		engorceApplicationService.save(engorceApplication , request);
		json.setRtData(engorceApplication);
		json.setRtState(true);
		return json;
	}

	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocEngorceApplication engorceApplication = engorceApplicationService
				.getById(id);
		EngorceApplicationModel engorceApplicationModel = new EngorceApplicationModel();
		BeanUtils.copyProperties(engorceApplication, engorceApplicationModel);
		// 单独处理时间类型转换
		if (engorceApplication.getEnforceData() != null) {
			engorceApplicationModel.setEnforceDataStr(TeeDateUtil.format(
					engorceApplication.getEnforceData(), "yyyy年MM月dd日"));
		}
		if (engorceApplication.getExigentNoticeData() != null) {
			engorceApplicationModel.setExigentNoticeDataStr(TeeDateUtil.format(
					engorceApplication.getExigentNoticeData(), "yyyy年MM月dd日"));
		}
		if (engorceApplication.getAddPenaltyDecision() != null) {
			engorceApplicationModel.setAddPenaltyDecisionStr(TeeDateUtil
					.format(engorceApplication.getAddPenaltyDecision(),
							"yyyy年MM月dd日"));
		}
		if (engorceApplication.getLawUnitDate() != null) {
			engorceApplicationModel.setLawUnitDateStr(TeeDateUtil.format(
					engorceApplication.getLawUnitDate(), "yyyy年MM月dd日"));
		}
		// 返回 强制执行申请书 json 对象
		json.setRtData(engorceApplicationModel);
		json.setRtState(true);
		return json;
	}

	// 预览文书

	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(EngorceApplicationModel engorceApplicationModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = engorceApplicationModel
				.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
