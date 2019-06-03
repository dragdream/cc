package com.tianee.oa.core.base.applicationSystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.applicationSystem.service.TeeApplicationSystemMaintainService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/ApplicationSystemMaintainController")
public class TeeApplicationSystemMaintainController {

	
	@Autowired
	private TeeApplicationSystemMaintainService applicationSystemMaintainService;
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid.action")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request) {
		return applicationSystemMaintainService.getInfoBySid(request);
	}
	
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate.action")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) {
		return applicationSystemMaintainService.addOrUpdate(request);
	}
	
	
	
	/**
	 * 根据主键进行批量删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delByIds.action")
	@ResponseBody
	public TeeJson delByIds(HttpServletRequest request) {
		return applicationSystemMaintainService.delByIds(request);
	}
	
	
	/**
	 * 获取列表信息
	 * @param dm
	 * @param response
	 * @return
	 */
	@RequestMapping("/getList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getList(TeeDataGridModel dm, HttpServletRequest response) {
		return applicationSystemMaintainService.getList(dm,response);
	}
	
	
	/**
	 * 获取所有信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAll.action")
	@ResponseBody
	public TeeJson getAll(HttpServletRequest request) {
		return applicationSystemMaintainService.getAll(request);
	}
	
	
	/**
	 * 获取当前登陆人有权限的系统应用列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPrivListByLoginUser.action")
	@ResponseBody
	public TeeJson getPrivListByLoginUser(HttpServletRequest request) {
		return applicationSystemMaintainService.getPrivListByLoginUser(request);
	}

}
