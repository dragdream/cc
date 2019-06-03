package com.tianee.oa.core.partthree.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.model.TeeAttendOutModel;
import com.tianee.oa.core.partthree.service.TeePartThreeRuleService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/teePartThreeRuleController")
public class TeePartThreeRuleController {

	@Autowired
	private TeePartThreeRuleService teePartThreeRuleService;
	
	
	
	/**
	 * 获取所有的规则
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllRules")
	@ResponseBody
	public TeeJson getAllRules(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = teePartThreeRuleService.getAllRules(request);
		return json;
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = teePartThreeRuleService.getInfoBySid(request);
		return json;
	}
	
	/**
	 * 修改规则
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateRule")
	@ResponseBody
	public TeeJson updateRule(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = teePartThreeRuleService.updateRule(request);
		return json;
	}
}
