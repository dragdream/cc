package com.tianee.oa.core.base.dam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.dam.service.TeePreArchiveTypeService;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;



@Controller
@RequestMapping("preArchiveTypeController")
public class TeePreArchiveTypeController {
	
	@Autowired
	TeePreArchiveTypeService preArchiveTypeService;
	
	
	/**
	 * 新建/编辑分类
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request){
		return preArchiveTypeService.addOrUpdate(request);
	}
	
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request){
		return preArchiveTypeService.getById(request);
	}
	
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){
		return preArchiveTypeService.delBySid(request);
	}
	
	
	
	/**
	 * 获取所有分类列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return preArchiveTypeService.datagrid(dm, request);
	}
	
	
	
	
	/**
	 * 获取所有分类列表 不分页
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getAllPreArchiveType")
	@ResponseBody
	public TeeJson getAllPreArchiveType(HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return preArchiveTypeService.getAllPreArchiveType(request);
	}
}
