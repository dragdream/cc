package com.tianee.oa.core.org.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeeUserRoleType;
import com.tianee.oa.core.org.service.TeeUserRoleTypeService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/teeUserRoleTypeController")
public class TeeUserRoleTypeController extends BaseController{

		
	@Autowired
	private TeeUserRoleTypeService roleTypeService;
	

	/**
	 * 新建或者更新
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(TeeUserRoleType model,HttpServletRequest request) {
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put("userRoleType", model);
		TeeJson json = roleTypeService.addOrUpdateService(requestData);
		return json;
	}
	
	/**
	 * 获取所有
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeUserRoleType model,HttpServletRequest request) {
		Map requestData = TeeServletUtility.getParamMap(request);
		return roleTypeService.datagrid(requestData);
	}
	
	/**
	 * 获取所有
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllType")
	@ResponseBody
	public TeeJson getAllType(TeeUserRoleType model,HttpServletRequest request) {
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put("userRoleType", requestData);
		TeeJson json = roleTypeService.getAllType(requestData);
		return json;
	}
	
	/**
	 * 删除byId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request) {
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeJson json = roleTypeService.deleteByIdService(requestData);
		return json;
	}
	
	
}
