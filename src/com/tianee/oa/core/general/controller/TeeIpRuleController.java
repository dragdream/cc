package com.tianee.oa.core.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.bean.TeeIpRule;
import com.tianee.oa.core.general.service.TeeIpRuleService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("/ipRuleController")
public class TeeIpRuleController extends BaseController{
	@Autowired
	private TeeIpRuleService ipRuleService;
	
	/**
	 * 新增或者更新
	 * @param intf
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdate(TeeIpRule intf,HttpServletRequest request) {
		TeeJson json = ipRuleService.addOrUpdate(intf , request);
		return json;
	}
	
	/**
	 * 根据Id查询
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json = ipRuleService.getById(request);
		return json;
	}
	
	/**
	 * 根据Id 删除
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request) {
		TeeJson json = ipRuleService.deleteByIdService(request);
		return json;
	}

	
	/**
	 * 查询所有的
	 */
	@RequestMapping("/getAllIpRule")
	@ResponseBody
	public TeeJson getAllIpRule(HttpServletRequest request) {
		TeeJson json = ipRuleService.getAllIpRule();
		return json;
	}

	

	public void setIpRuleService(TeeIpRuleService ipRuleService) {
		this.ipRuleService = ipRuleService;
	}
	
	
}

	