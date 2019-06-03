package com.tianee.oa.core.monitor.controller;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.monitor.service.TeeMonitorService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/monitor")
public class TeeMonitorController {
	@Autowired
	private TeeMonitorService monitorService;
	
	/**
	 * 监控列表
	 * @param request
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping("/monitorList")
	public TeeEasyuiDataGridJson monitorList(HttpServletRequest request,TeeDataGridModel dm) throws ParseException{
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return monitorService.monitorList(requestData, dm);
	}
	
	/**
	 * 计划列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/scheduleList")
	public TeeEasyuiDataGridJson scheduleList(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return monitorService.scheduleList(requestData, dm);
	}
	
	/**
	 * 任务列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/taskList")
	public TeeEasyuiDataGridJson taskList(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return monitorService.taskList(requestData, dm);
	}
	
	/**
	 * 客户列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/customerList")
	public TeeEasyuiDataGridJson customerList(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return monitorService.customerList(requestData, dm);
	}
	
	/**
	 * 流程列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/runList")
	public TeeEasyuiDataGridJson runList(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return monitorService.runList(requestData, dm);
	}
	
	/**
	 * 日志列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/diaryList")
	public TeeEasyuiDataGridJson diaryList(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return monitorService.diaryList(requestData, dm);
	}
	
	/**
	 * 邮件列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mailList")
	public TeeEasyuiDataGridJson mailList(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return monitorService.mailList(requestData, dm);
	}
}
