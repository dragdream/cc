package com.beidasoft.xzzf.punish.document.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.beidasoft.xzzf.punish.common.service.PunishFlowService;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocApprovaltable;
import com.beidasoft.xzzf.punish.document.bean.DocPutonrecord;
import com.beidasoft.xzzf.punish.document.model.ApprovaltableModel;
import com.beidasoft.xzzf.punish.document.model.PutonrecordModel;
import com.beidasoft.xzzf.punish.document.service.ApprovaltableService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/approvaltableCtrl")
public class ApprovaltableController {
	@Autowired
	private ApprovaltableService approvalservice;
	@Autowired
	private TeeWenShuService wenShuService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 展示审批表数据
	 * 
	 * @param dataGridModel
	 * @param approvaltable
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/viewDocInfo")
	public TeeJson viewDocInfo(ApprovaltableModel approvaltableModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = approvaltableModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}

	/**
	 * 保存审批表数据
	 * 
	 * @param approvaltable
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(ApprovaltableModel approvaltablemodel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
//		// RUNID
//		String runId = request.getParameter("runId");
		DocApprovaltable approval = new DocApprovaltable();
		BeanUtils.copyProperties(approvaltablemodel, approval);
		// 单独处理时间转换
		if (StringUtils.isNotBlank(approvaltablemodel
				.getUndertakePersonalTimes())) {
			approval.setUndertakePersonalTime(TeeDateUtil.format(
					approvaltablemodel.getUndertakePersonalTimes(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(approvaltablemodel
				.getUndertakeDepartmentTimes())) {
			approval.setUndertakeDepartmentTime(TeeDateUtil.format(
					approvaltablemodel.getUndertakeDepartmentTimes(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(approvaltablemodel.getLeaderTimes())) {
			approval.setLeaderTime(TeeDateUtil.format(
					approvaltablemodel.getLeaderTimes(), "yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(approvaltablemodel.getId())) {
			approval.setId(UUID.randomUUID().toString());
			approval.setCreateUserId(user.getUserId());
			approval.setCreateUserName(user.getUserName());
			approval.setCreateTime(Calendar.getInstance().getTime());
			approval.setDelFlg("0");
			//添加文书操作日志
			commonService.writeLog(request, "新建审批表");
		} else {
			// 设置创建人相关信息
			approval.setUpdateUserId(user.getUserId());
			approval.setUpdateUserName(user.getUserName());
			approval.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改审批表");
		}
		
		// 实体类信息存到数据库
		approvalservice.save(approval,request);
		json.setRtData(approval);
		json.setRtState(true);
		return json;
	}

	/**
	 * 根据UUid 获取审批表数据
	 * 
	 * @param approvaltable
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocApprovaltable approval = approvalservice.getByUID(id);
		ApprovaltableModel approvaltablemodel = new ApprovaltableModel();
		BeanUtils.copyProperties(approval, approvaltablemodel);
		// 单独处理时间转换
		if (approval.getUndertakePersonalTime() != null) {
			approvaltablemodel.setUndertakePersonalTimes(TeeDateUtil.format(
					approval.getUndertakePersonalTime(), "yyyy年MM月dd日"));
		}
		if (approval.getUndertakeDepartmentTime() != null) {
			approvaltablemodel.setUndertakeDepartmentTimes(TeeDateUtil.format(
					approval.getUndertakeDepartmentTime(), "yyyy年MM月dd日"));
		}
		if (approval.getLeaderTime() != null) {
			approvaltablemodel.setLeaderTimes(TeeDateUtil.format(
					approval.getLeaderTime(), "yyyy年MM月dd日"));
		}
		json.setRtData(approvaltablemodel);
		json.setRtState(true);
		return json;
	}
	/**
	 *取flow表中审批表的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocFromPuton")
	public TeeJson getDocFromPuton(String runId) {
		TeeJson json = new TeeJson();
		PunishFLow list = flowService.getByRunId(runId);
		json.setRtData(list);
		json.setRtState(true);
		return json;
  }
	/**
	 * 审批表主办人设置弹窗 提醒填写公告
	 * @param id
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/getwindows")
	public TeeJson getwindows(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Map requestData1 = new HashMap();
		requestData1.put("content", "公告收缴审批表已办理结束，请填写公告！");
		requestData1.put("userListIds", id);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/projectApprove/capture.jsp");
		smsManager.sendSms(requestData1, user);
		json.setRtData(requestData1);
		json.setRtState(true);
		return json;
  }
}
