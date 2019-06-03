package com.tianee.oa.core.workflow.flowrun.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/runRel")
public class TeeRunRelController {
	
	@Autowired
	private TeeRunRelServiceInterface relService;
	
	/**
	 * 获取计划
	 * @param requestData
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listSchedule")
	public TeeEasyuiDataGridJson listSchedule(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listSchedule(requestData, dm);
	}
	
	/**
	 * 获取任务
	 * @param requestData
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listTask")
	public TeeEasyuiDataGridJson listTask(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listTask(requestData, dm);
	}
	
	/**
	 * 获取客户
	 * @param requestData
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listCustomer")
	public TeeEasyuiDataGridJson listCustomer(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listCustomer(requestData, dm);
	}
	
	/**
	 * 获取流程
	 * @param requestData
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listFlowRun")
	public TeeEasyuiDataGridJson listFlowRun(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listFlowRun(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/addScheduleRel")
	public TeeJson addScheduleRel(HttpServletRequest request){
		String ids[] = TeeStringUtil.parseStringArray(request.getParameter("ids"));
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		for(String uuid:ids){
			relService.addScheduleRel(uuid, runId);
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/delScheduleRel")
	public TeeJson delScheduleRel(HttpServletRequest request){
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		relService.delScheduleRel(uuid);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/listScheduleRel")
	public TeeEasyuiDataGridJson listScheduleRel(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listScheduleRel(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/addTaskRel")
	public TeeJson addTaskRel(HttpServletRequest request){
		int ids[] = TeeStringUtil.parseIntegerArray(request.getParameter("ids"));
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		for(int taskId:ids){
			relService.addTaskRel(taskId, runId);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/delTaskRel")
	public TeeJson delTaskRel(HttpServletRequest request){
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		relService.delTaskRel(uuid);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/listTaskRel")
	public TeeEasyuiDataGridJson listTaskRel(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listTaskRel(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/addCustomerRel")
	public TeeJson addCustomerRel(HttpServletRequest request){
		int ids[] = TeeStringUtil.parseIntegerArray(request.getParameter("ids"));
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		for(int customerId:ids){
			relService.addCustomerRel(customerId, runId);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/delCustomerRel")
	public TeeJson delCustomerRel(HttpServletRequest request){
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		relService.delCustomerRel(uuid);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/listCustomerRel")
	public TeeEasyuiDataGridJson listCustomerRel(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listCustomerRel(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/addFlowRunRel")
	public TeeJson addFlowRunRel(HttpServletRequest request){
		int ids[] = TeeStringUtil.parseIntegerArray(request.getParameter("ids"));
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		for(int runId_:ids){
			relService.addFlowRunRel(runId_, runId);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/delFlowRunRel")
	public TeeJson delFlowRunRel(HttpServletRequest request){
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		relService.delFlowRunRel(uuid);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/listFlowRunRel")
	public TeeEasyuiDataGridJson listFlowRunRel(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return relService.listFlowRunRel(requestData, dm);
	}

	@ResponseBody
	@RequestMapping("/getDataCounts")
	public TeeJson getDataCounts(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		json.setRtData(relService.getDataCounts(runId));
		json.setRtState(true);
		return json;
	}
}
