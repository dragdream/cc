package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.model.TeeProjectCustomFieldModel;
import com.tianee.oa.subsys.project.service.TeeProjectCustomFieldService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectCustomFieldController")
public class TeeProjectCustomFieldController {

	@Autowired
	private TeeProjectCustomFieldService projectCustomFieldService;
	
	/**
	 * 新增/编辑自定义字段
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response,TeeProjectCustomFieldModel model) throws ParseException {	
		return projectCustomFieldService.addOrUpdate(request,model);
	}
	
	
	/**
	 * 获取自定义字段列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getFieldList")
	@ResponseBody
	public TeeJson getFieldList(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCustomFieldService.getFieldList(request);
	}
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/deleteBySid")
	@ResponseBody
	public TeeJson deleteBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCustomFieldService.deleteBySid(request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCustomFieldService.getInfoBySid(request);
	}
	
	
	/**
	 * 根据项目类型id获取自定义字段的集合
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getListByProjectType")
	@ResponseBody
	public TeeJson getListByProjectType(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCustomFieldService.getListByProjectType(request);
	}
	
	

	
	/**
	 * 根据项目类型主键      获取自定义字段中可以在列表显示的字段集合
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getShowFieldListByProjectTypeId")
	@ResponseBody
	public TeeJson getShowFieldListByProjectTypeId(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCustomFieldService.getShowFieldListByProjectTypeId(request);
	}
	
	
	
	/**
	 * 根据项目类型主键      获取自定义字段中可以查询的字段集合
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getQueryFieldListByProjectTypeId")
	@ResponseBody
	public TeeJson getQueryFieldListByProjectTypeId(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCustomFieldService.getQueryFieldListByProjectTypeId(request);
	}
}
