package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.subsys.project.service.TeeProjectCreatePrivService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectCreatePrivController")
public class TeeProjectCreatePrivController {

	@Autowired
	private TeeProjectCreatePrivService projectCreatePrivService;
	
	
	/**
	 * 获取项目新建权限值
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public TeeJson getData(HttpServletRequest request ,HttpServletResponse response) throws ParseException {
		
		return projectCreatePrivService.getData(request);
	}
	
	
	/**
	 * 设置项目新建的权限值
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/setData")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectCreatePrivService.setData(request);
	}
	
	
	/**
	 * 判断当前登陆人有没有创建项目的权利
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isCreateByLoginUser")
	@ResponseBody
	public TeeJson isCreateByLoginUser(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		return projectCreatePrivService.isCreateByLoginUser(request);
	}
}
