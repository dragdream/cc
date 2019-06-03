package com.tianee.oa.core.workflow.workmanage.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/workflowManage")
public class TeeWorkflowManageController {
	
	@Autowired
	private TeeWorkflowManageServiceInterface workflowManageService;
	
	/**
	 * 流程管理，结束流程
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/endRun")
	@ResponseBody
	public TeeJson endRun(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		workflowManageService.endRun(runId);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 流程管理，恢复流程
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/recoverRun")
	@ResponseBody
	public TeeJson recoverRun(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		workflowManageService.recoverRun(runId);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 流程管理，删除流程
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delRun")
	@ResponseBody
	public TeeJson delRun(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		workflowManageService.delRun(runId);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 流程管理，销毁流程
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/destroyRun")
	@ResponseBody
	public TeeJson destroyRun(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		workflowManageService.destroyRun(runId);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 流程委托
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/flowRunDelegate")
	@ResponseBody
	public TeeJson flowRunDelegate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		int delegateTo = TeeStringUtil.getInteger(request.getParameter("delegateTo"),0);
		workflowManageService.flowRunDelegate(frpSid, delegateTo);
		json.setRtState(true);
		json.setRtMsg("委托操作成功");
		return json;
	}
	
	/**
	 * 工作移交
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/handover")
	@ResponseBody
	public TeeJson handover(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String flowIds = TeeStringUtil.getString(request.getParameter("flowIds"));
		int fromUser = TeeStringUtil.getInteger(request.getParameter("fromUser"), 0);
		int toUser = TeeStringUtil.getInteger(request.getParameter("toUser"), 0);
		Calendar beginTime = TeeDateUtil.parseCalendar(request.getParameter("beginTime")+" 00:00:00");
		Calendar endTime = TeeDateUtil.parseCalendar(request.getParameter("endTime")+" 23:59:59");
		int beginRunId = TeeStringUtil.getInteger(request.getParameter("beginRunId"), 0);
		int endRunId = TeeStringUtil.getInteger(request.getParameter("endRunId"), 0);
		
		workflowManageService.handover(flowIds, fromUser, toUser, beginTime, endTime, beginRunId, endRunId,loginPerson);
		
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 收回委托
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/takebackDelegate")
	@ResponseBody
	public TeeJson takebackDelegate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		workflowManageService.takebackDelegate(frpSid);
		json.setRtState(true);
		json.setRtMsg("收回委托操作成功");
		return json;
	}
	
	/**
	 * 收回工作
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/takebackWorks")
	@ResponseBody
	public TeeJson takebackWorks(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		workflowManageService.takebackWorks(frpSid);
		json.setRtState(true);
		json.setRtMsg("收回工作成功");
		return json;
	}
	
	/**
	 * 获取委托处理数据，前端数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getDelegateHandlerData")
	@ResponseBody
	public TeeJson getDelegateHandlerData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		json.setRtData(workflowManageService.getDelegateHandlerData(frpSid, loginPerson));
		json.setRtState(true);
		return json;
	}

	public void setWorkflowManageService(TeeWorkflowManageServiceInterface workflowManageService) {
		this.workflowManageService = workflowManageService;
	}

	public TeeWorkflowManageServiceInterface getWorkflowManageService() {
		return workflowManageService;
	}
	
	
	/**
	 * 工作查询  批量删除流程
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delRunBatch")
	@ResponseBody
	public TeeJson delRunBatch(HttpServletRequest request){
		String runIds = TeeStringUtil.getString(request.getParameter("runIds"));
		return workflowManageService.delRunBatch(runIds);
	}
	
}
