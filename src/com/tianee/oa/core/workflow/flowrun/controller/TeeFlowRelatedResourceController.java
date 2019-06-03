package com.tianee.oa.core.workflow.flowrun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeFlowRelatedResourceController")
public class TeeFlowRelatedResourceController {

	@Autowired
	private TeeFlowRelatedResourceServiceInterface flowRelatedResourceService;

	
	/**
	 * 相关流程列表（我发起  或  经我办理的 ）
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/relatedFlowRunList")
	@ResponseBody
	public TeeEasyuiDataGridJson relatedFlowRunList(HttpServletRequest request,TeeDataGridModel dm){
		return flowRelatedResourceService.relatedFlowRunList(dm, request);
	}

	
	
	/**
	 * 相关任务列表（我创建的  我负责的  我参与的）
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/relatedTaskList")
	@ResponseBody
	public TeeEasyuiDataGridJson relatedTaskList(HttpServletRequest request,TeeDataGridModel dm){
		return flowRelatedResourceService.relatedTaskList(dm, request);
	}
	
	
	/**
	 * 相关客户列表（我负责的  我下属负责的  公海客户）
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/relatedCustomerList")
	@ResponseBody
	public TeeEasyuiDataGridJson relatedCustomerList(HttpServletRequest request,TeeDataGridModel dm){
		return flowRelatedResourceService.relatedCustomerList(dm, request);
	}
	
	
	/**
	 * 相关项目列表(我创建的、我负责的、我是项目成员和项目观察者   共享給我的)
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/relatedProjectList")
	@ResponseBody
	public TeeEasyuiDataGridJson relatedProjectList(HttpServletRequest request,TeeDataGridModel dm){
		return flowRelatedResourceService.relatedProjectList(dm, request);
	}
	
	/**
	 * 增加相关资源
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request,TeeDataGridModel dm){
		return flowRelatedResourceService.add(dm, request);
	}
	
	
	
	/**
	 * 根据流程id和  type   获取相关资源信息
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/getRelatedResourceByRunId")
	@ResponseBody
	public TeeJson getRelatedResourceByRunId(HttpServletRequest request,TeeDataGridModel dm){
		return flowRelatedResourceService.getRelatedResourceByRunId(dm, request);
	}
	
	
	/**
	 * 删除相关资源
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){
		return flowRelatedResourceService.delBySid(request);
	}
}
