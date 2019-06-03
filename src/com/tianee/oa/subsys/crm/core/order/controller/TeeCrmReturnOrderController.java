package com.tianee.oa.subsys.crm.core.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.order.model.TeeCrmOrderModel;
import com.tianee.oa.subsys.crm.core.order.model.TeeOrderProductsModel;
import com.tianee.oa.subsys.crm.core.order.model.TeeReturnOrderModel;
import com.tianee.oa.subsys.crm.core.order.service.TeeCrmReturnOrderService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmReturnOrderController")
public class TeeCrmReturnOrderController extends BaseController{
	@Autowired
	private TeeCrmReturnOrderService returnOrderService;
	
	/**
	 * 新建或编辑退货单
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,TeeReturnOrderModel model){
		TeeJson json = new TeeJson();
		json = returnOrderService.addOrUpdate(request,model);
		return json;
		
	}
	
	/**
	 * 退货单列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return returnOrderService.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取退货单详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeReturnOrderModel model){
		TeeJson json = new TeeJson();
		json = returnOrderService.getInfoBySid(request,model);
		return json;
	}
	
	/**
	 * 获取退货单下的产品详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getProductItem")
	public TeeJson getProductItem(HttpServletRequest request,TeeReturnOrderModel model){
		TeeJson json = new TeeJson();
		json = returnOrderService.getProductItem(request);
		return json;
	}
	
	/**
	 * 订单管理员同意退货单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agree")
	public TeeJson agree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = returnOrderService.agree(request);
		return json;
	}
	
	/**
	 * 订单管理员驳回退货单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reject")
	public TeeJson reject(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = returnOrderService.reject(request);
		return json;
	}
	
	/**
	 * 删除退货单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delById")
	public TeeJson delById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = returnOrderService.delById(request);
		return json;
	}
	

	

}
