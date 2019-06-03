package com.tianee.oa.core.workflow.flowrun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowRunApiService;

@Controller
@RequestMapping("flowRunAip")
public class TeeWorkFlowRunApiController {
	
	@Autowired
	private TeeWorkflowRunApiService apiService;
	
	@RequestMapping("test")
	public void test(){
		
		apiService.getNextProcessList(152);
		
	}
	
}
