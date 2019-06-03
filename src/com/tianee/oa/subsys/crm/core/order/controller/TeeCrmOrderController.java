package com.tianee.oa.subsys.crm.core.order.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mozilla.javascript.tools.shell.JSConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.regexp.internal.RE;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.chances.model.TeeCrmChancesModel;
import com.tianee.oa.subsys.crm.core.order.model.TeeCrmOrderModel;
import com.tianee.oa.subsys.crm.core.order.service.TeeCrmOrderService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmOrderController")
public class TeeCrmOrderController extends BaseController {
	@Autowired
	private TeeCrmOrderService orderService;
	
	/**
	 * 添加或编辑销售订单
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmOrderModel model){
		TeeJson json = new TeeJson();
		json = orderService.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 订单列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return orderService.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取订单详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmOrderModel model){
		TeeJson json = new TeeJson();
		json = orderService.getInfoBySid(request,model);
		return json;
	}
	
	/**
	 * 获取产品详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getProductItem")
	public TeeJson getProductItem(HttpServletRequest request,TeeCrmOrderModel model){
		TeeJson json = new TeeJson();
		json = orderService.getProductItem(request);
		return json;
		
	}
	

	/**
	 * 删除订单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delById")
	public TeeJson delById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = orderService.delById(request);
		return json;
		
	}
	
	/**
	 * 同意
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agree")
	public TeeJson agree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = orderService.agree(request);
		return json;
	}
	
	/**
	 * 驳回
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reject")
	public TeeJson reject(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = orderService.reject(request);
		return json;
	}
	
	/**
	 * 查询某客户下的订单数据
	 * @param request
	 * @return
	 */
	@RequestMapping("selectOrders")
	@ResponseBody
	public TeeJson selectOrders(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeCrmOrderModel> list = orderService.selectOrders(request);
		if(list.size()>0){
			json.setRtData(list);
			json.setRtMsg("查询成功！");
			json.setRtState(true);
		}else{
			json.setRtMsg("查询失败！");
			json.setRtState(false);
		}
		
		return json;
	}

}
