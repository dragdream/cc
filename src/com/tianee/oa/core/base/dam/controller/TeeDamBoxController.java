package com.tianee.oa.core.base.dam.controller;



import javax.servlet.http.HttpServletRequest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.dam.service.TeeDamBoxService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("damBoxController")
public class TeeDamBoxController {
	@Autowired
	TeeDamBoxService damBoxService;
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addRecord(HttpServletRequest request) throws Exception{
		return damBoxService.addOrUpdate(request);
	}
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) throws Exception{
		return damBoxService.getById(request);
	}
	
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){
		return damBoxService.delBySid(request);
	}
	
	
	
	/**
	 * 获取当前登录人创建的  并且 未归档的卷盒
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return damBoxService.datagrid(dm, request);
	}
	
	
	
	
	/**
	 * 获取当前登陆人创建的并且未归档的卷盒  不分页
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getAllBoxByLoginUser")
	@ResponseBody
	public TeeJson getAllBoxByLoginUser(HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return damBoxService.getAllBoxByLoginUser(request);
	}
	
	
	/**
	 * 判断盒号是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkBoxNo")
	@ResponseBody
	public TeeJson checkBoxNo(HttpServletRequest request){
		return damBoxService.checkBoxNo(request);
	}
	
	
	

	/**
	 * 归档
	 * @param request
	 * @return
	 */
	@RequestMapping("/archive")
	@ResponseBody
	public TeeJson archive(HttpServletRequest request){
		return damBoxService.archive(request);
	}
}
