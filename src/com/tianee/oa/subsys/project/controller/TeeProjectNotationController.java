package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeProjectNotationService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectNotationController")
public class TeeProjectNotationController {

	@Autowired
	private TeeProjectNotationService projectNotationService;
	/**
	 * 添加/编辑批注内容
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectNotationService.addOrUpdate(request);
	}
	
	
	
	
	 /**
	  * 根据项目主键 获取批注列表
	  * @param dm
	  * @param request
	  * @param response
	  * @return
	  * @throws ParseException
	  */
	@RequestMapping("/getNotationListByProjectId")
	@ResponseBody
	public TeeEasyuiDataGridJson getNotationListByProjectId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectNotationService.getNotationListByProjectId(request,dm);
	}
	
	
	/**
	 * 根据项目主键获取项目批注列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getNotationsByProjectId")
	@ResponseBody
	public TeeJson getNotationsByProjectId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectNotationService.getNotationsByProjectId(request,dm);
	}
	
	
	/**
	 * 根据主键  获取批注详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectNotationService.getInfoBySid(request);
	}
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectNotationService.delBySid(request);
	}
}
