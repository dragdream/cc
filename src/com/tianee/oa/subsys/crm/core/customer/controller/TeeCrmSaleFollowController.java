package com.tianee.oa.subsys.crm.core.customer.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmSaleFollowModel;
import com.tianee.oa.subsys.crm.core.customer.service.TeeCrmSaleFollowService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/TeeCrmSaleFollowController")
public class TeeCrmSaleFollowController extends BaseController{
	@Autowired
	private TeeCrmSaleFollowService saleFollowService;
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		json = saleFollowService.addOrUpdate(request , model);
		return json;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = saleFollowService.deleteByIdService(request ,sids);
		return json;
	}
	

	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		json = saleFollowService.getById(request , model);
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getProductItem")
	@ResponseBody
	public TeeJson getProductItem(HttpServletRequest request , TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		json = saleFollowService.getProductItem(request);
		return json;
	}
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getByIdForDetail")
	@ResponseBody
	public TeeJson getByIdForDetail(HttpServletRequest request , TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		json = saleFollowService.getByIdForDetail(request , model);
		return json;
	}
	
	/**
	 * @author ny
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return saleFollowService.datagird(dm, requestDatas);
	}
	
	
	/**
	 * 获取跟单记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSaleFollowList")
	@ResponseBody
	public TeeJson getSaleFollowList(HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		TeeJson json = new TeeJson();
		json = saleFollowService.getSaleFollowList(requestDatas);
		return json;
	}
}