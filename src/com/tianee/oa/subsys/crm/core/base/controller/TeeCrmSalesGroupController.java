package com.tianee.oa.subsys.crm.core.base.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.crm.core.base.model.TeeCrmSalesGroupModel;
import com.tianee.oa.subsys.crm.core.base.service.TeeCrmSalesGroupService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
@Controller
@RequestMapping("/teeCrmSalesGroupController")
public class TeeCrmSalesGroupController extends BaseController{
	
	@Autowired
	TeeCrmSalesGroupService crmSalesGroupService;

	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request, TeeCrmSalesGroupModel object){
		TeeJson json = crmSalesGroupService.addOrUpdate( request ,object);
		return json;
	}

	/**
	 * 获取所有销售组
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllGroup")
	@ResponseBody
	public TeeJson getAllGroup(HttpServletRequest request){
		TeeJson json = crmSalesGroupService.getAllGroup( request);
		return json;
	}
	
	/**
	 * 获取销售组 树
	 * @param request
	 * @return
	 */
	@RequestMapping("/getGroupTree")
	@ResponseBody
	public TeeJson getGroupTree(HttpServletRequest request){
		TeeJson json = crmSalesGroupService.getGroupTree( request);
		return json;
	}
	
	/**
	 * 获取销售组树， 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getParentGroupTree")
	@ResponseBody
	public TeeJson getParentGroupTree(HttpServletRequest request){
		TeeJson json = crmSalesGroupService.getParentGroupTree( request);
		return json;
	}
	
	
	/**
	 * 删除类型 byId
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json = crmSalesGroupService.deleteById( request);
		return json;
	}
	
	/**
	 * 删除类型 byId
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = crmSalesGroupService.getById( request);
		return json;
	}
		
}
