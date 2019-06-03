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
import com.beidasoft.xzzf.punish.document.bean.DocHearingReport;
import com.beidasoft.xzzf.punish.document.model.HearingReportModel;
import com.beidasoft.xzzf.punish.document.service.HearingReportService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/hearingReportCtrl")
public class HearingReportController {
	@Autowired
	private HearingReportService hearingReportService;
	@Autowired
	private TeeWenShuService wenShuService;
	@Autowired
	private CommonService commonService;

	/*
	 * 行政处罚听证报告
	 */

	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(HearingReportModel hearingReportModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocHearingReport hearingReport = new DocHearingReport();
		// 属性值传递
		BeanUtils.copyProperties(hearingReportModel, hearingReport);
		// 单独处理时间类型转换
		// 听证时间HearingTimeStr
		if (StringUtils.isNotBlank(hearingReportModel.getHearingTimeStr())) {
			hearingReport
					.setHearingTime(TeeDateUtil.format(
							hearingReportModel.getHearingTimeStr(),
							"yyyy年MM月dd日 HH时mm分"));
		}
		// 报告日期ReportDateStr
		if (StringUtils.isNotBlank(hearingReportModel.getReportDateStr())) {
			hearingReport.setReportDate(TeeDateUtil.format(
					hearingReportModel.getReportDateStr(), "yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(hearingReportModel.getId())) {
			hearingReport.setId(UUID.randomUUID().toString());
			hearingReport.setCreateUserId(user.getUserId());
			hearingReport.setCreateUserName(user.getUserName());
			hearingReport.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建行政处罚听证报告");
		} else {
			// 设置创建人相关信息
			hearingReport.setUpdateUserId(user.getUserId());
			hearingReport.setUpdateUserName(user.getUserName());
			hearingReport.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改行政处罚听证报告");
		}
		hearingReport.setDelFlg("0");
		// 保存 行政处罚听证报告
		hearingReportService.save(hearingReport , request);
		json.setRtData(hearingReport);
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
		DocHearingReport hearingReport = hearingReportService.getById(id);
		HearingReportModel hearingReportModel = new HearingReportModel();
		BeanUtils.copyProperties(hearingReport, hearingReportModel);
		// 单独处理时间类型转换
		// 听证时间HearingTimeStr
		if (hearingReport.getHearingTime() != null) {
			hearingReportModel.setHearingTimeStr(TeeDateUtil.format(
					hearingReport.getHearingTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (hearingReport.getReportDate() != null) {
			hearingReportModel.setReportDateStr(TeeDateUtil.format(
					hearingReport.getReportDate(), "yyyy年MM月dd日"));
		}
		// 返回 行政处罚听证报告json 对象
		json.setRtData(hearingReportModel);
		json.setRtState(true);
		return json;
	}

	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(HearingReportModel hearingReportModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = hearingReportModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
