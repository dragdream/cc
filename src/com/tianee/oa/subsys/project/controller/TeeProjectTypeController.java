package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeProjectTypeService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectTypeController")
public class TeeProjectTypeController {

	@Autowired
	private TeeProjectTypeService projectTypeService;
	
	/**
	 * 新增/编辑项目类型
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectTypeService.addOrUpdate(request);
	}
	
	
	/**
	 * 根据主键获取项目类型的详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getProjectTypeBySid")
	@ResponseBody
	public TeeJson getProjectTypeBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectTypeService.getProjectTypeBySid(request);
	}
	
	
	
	/**
	 * 获取项目类型列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getTypeList")
	@ResponseBody
	public TeeJson getTypeList(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectTypeService.getTypeList(request);
	}
	
	
	/**
	 * 根据主键删除项目类型对象
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/deleteBySid")
	@ResponseBody
	public TeeJson deleteBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectTypeService.deleteBySid(request);
	}
	
    /**
     * 获取某种类型下的所有任务
     * */ 
	@ResponseBody
	@RequestMapping("/findTaskByType")
	public TeeJson  findTaskByType(HttpServletRequest request){
		return projectTypeService.findTaskByType(request);
	}
	
	/**
	 * 添加模板中的数据
	 * */
	@ResponseBody
	@RequestMapping("/addTaskMb")
	public TeeJson addTaskMb(HttpServletRequest request){
		return projectTypeService.addTaskMb(request);
	}
	/**
	 * 获取某个类别下的所有任务
	 * */
	@ResponseBody
	@RequestMapping("/getTaskAllByLei")
	public TeeJson getTaskAllByLei(HttpServletRequest request){
		return projectTypeService.getTaskAllByLei(request);
	}
	
	/**
	 * 获取类别下的选中的任务
	 * */
	@ResponseBody
	@RequestMapping("/getTaskIdByLei")
	public TeeJson getTaskIdByLei(HttpServletRequest request){
		return projectTypeService.getTaskIdByLei(request);
	}
}
