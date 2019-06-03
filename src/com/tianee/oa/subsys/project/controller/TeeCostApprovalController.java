package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeCostApprovalService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/costApprovalController")
public class TeeCostApprovalController {
   
	@Autowired
	private TeeCostApprovalService costApprovalService;
	
	/**
	 * 设置费用审批规则
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/setCostApprovalRule")
	@ResponseBody
	public TeeJson setCostApprovalRule(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return costApprovalService.setCostApprovalRules(request);
	}
	
	
	
	/**
	 * 获取费用审批规则
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getCostApprovalRule")
	@ResponseBody
	public TeeJson getCostApprovalRule(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return costApprovalService.getCostApprovalRule(request);
	}
	
	
	/**
	 * 获取费用审批人员
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getApprover")
	@ResponseBody
	public TeeJson getApprover(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return costApprovalService.getApprover(request);
	}
}
