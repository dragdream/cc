package com.tianee.oa.subsys.zhidao.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.zhidao.service.TeeZhiDaoCatService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/zhiDaoCatController")
public class TeeZhiDaoCatController {
	
	@Autowired
	private TeeZhiDaoCatService zhiDaoCatService;
	
	
	/**
	 * 新建/编辑分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoCatService.addOrUpdate(request);
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
		return zhiDaoCatService.getInfoBySid(request);
	}
	
	
	/**
	 * 获取所有的一级分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getFirstLevelCat")
	@ResponseBody
	public TeeJson getFirstLevelCat(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoCatService.getFirstLevelCat(request);
	}
	
	/**
	 * 获取所有分分类
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getAllCat")
	@ResponseBody
	public List getAllCat(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoCatService.getAllCat(request,dm);
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
		return zhiDaoCatService.delBySid(request);
	}
	
	
	/**
	 * 获取所有的分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getAllCat1")
	@ResponseBody
	public TeeJson getAllCat(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoCatService.getAllCat1(request);
	}
	
	
	/**
	 * 判断当前登陆人是不是当前分类的管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isManager")
	@ResponseBody
	public TeeJson isManager(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoCatService.isManager(request);
	}
}
