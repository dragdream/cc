package com.tianee.oa.subsys.crm.core.product.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.crm.core.product.model.TeeCrmProductsTypeModel;
import com.tianee.oa.subsys.crm.core.product.service.TeeCrmProductsTypeService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
@Controller
@RequestMapping("/teeCrmProductsTypeController")
public class TeeCrmProductsTypeController extends BaseController{
	
	@Autowired
	TeeCrmProductsTypeService crmProductsTypeService;

	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request, TeeCrmProductsTypeModel object){
		TeeJson json = crmProductsTypeService.addOrUpdate( request ,object);
		return json;
	}

	
	/**
	 * 获取产品类型 树
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProductTypeTree")
	@ResponseBody
	public TeeJson getProductTypeTree(HttpServletRequest request){
		TeeJson json = crmProductsTypeService.getProductTypeTree( request);
		return json;
	}
	
	/**
	 * 获取上级产品类型树， 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getParentProductTypeTree")
	@ResponseBody
	public TeeJson getParentProductTypeTree(HttpServletRequest request){
		TeeJson json = crmProductsTypeService.getParentProductTypeTree( request);
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
		TeeJson json = crmProductsTypeService.deleteByIdService( request);
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
		TeeJson json = crmProductsTypeService.getById( request);
		return json;
	}
		
}
