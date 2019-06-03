package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeProjectCostTypeService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectCostTypeController")
public class TeeProjectCostTypeController {

	@Autowired
	private TeeProjectCostTypeService projectCostTypeService;
	
	/**
	 * 获取费用类型列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getCostTypeList")
	@ResponseBody
	public TeeJson getCostTypeList(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostTypeService.getCostTypeList(request);
	}
	
	
	/**
	 * 根据主键删除费用类型
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/deleteBySid")
	@ResponseBody
	public TeeJson deleteBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostTypeService.deleteBySid(request);
	}
	
	
	
	/**
	 * 根据主键获取费用类型详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getCostTypeBySid")
	@ResponseBody
	public TeeJson getCostTypeBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostTypeService.getCostTypeBySid(request);
	}
	
	
	

	/**
	 * 新增/编辑费用类型
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostTypeService.addOrUpdate(request);
	}
}
