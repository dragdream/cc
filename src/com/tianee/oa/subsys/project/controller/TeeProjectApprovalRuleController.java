package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.subsys.project.model.TeeProjectApprovalRuleModel;
import com.tianee.oa.subsys.project.service.TeeProjectApprovalRuleService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectApprovalRuleController")
public class TeeProjectApprovalRuleController {

	@Autowired
	private TeeProjectApprovalRuleService projectApprovalRuleService;
	
	
	
	/**
	 * 设置免审批规则
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/setNoApprovalRule")
	@ResponseBody
	public TeeJson setNoApprovalRule(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectApprovalRuleService.setNoApprovalRule(request);
	}
	
	
	
	/**
	 * 获取免审批规则数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getNoApprovalRule")
	@ResponseBody
	public TeeJson getNoApprovalRule(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectApprovalRuleService.getNoApprovalRule(request);
	}
	

	/**
	 * 添加/编辑审批规则
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdateApprovalRule")
	@ResponseBody
	public TeeJson addOrUpdateApprovalRule(HttpServletRequest request , HttpServletResponse response, TeeProjectApprovalRuleModel model) throws ParseException {
		return projectApprovalRuleService.addOrUpdateApprovalRule(request,model);
	}
	
	
	
	/**
	 * 根据主键获取某一个审批规则的详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getApprovalRuleBySid")
	@ResponseBody
	public TeeJson getApprovalRuleBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectApprovalRuleService.getApprovalRuleBySid(request);
	}
	
	
	/**
	 * 根据主键获取某一个审批规则的详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/deleteBySid")
	@ResponseBody
	public TeeJson deleteBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		
		return projectApprovalRuleService.deleteBySid(request);
	}
	
	
	
	
	/**
	 * 获取所有的审批规则列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getApprovalRuleList")
	@ResponseBody
	public TeeJson getApprovalRuleList(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectApprovalRuleService.getApprovalRuleList(request);
	}
	
	
	
    /**
     * 根据当前登陆人  获取项目审批人
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
	@RequestMapping("/getApproverByLoginUser")
	@ResponseBody
	public TeeJson getApproverByLoginUser(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectApprovalRuleService.getApproverByLoginUser(request);
	}
	
	
	 /**
     * 判断当前登陆人  是否免审批
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
	@RequestMapping("/isNoApprove")
	@ResponseBody
	public TeeJson isNoApprove(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectApprovalRuleService.isNoApprove(request);
	}
}
