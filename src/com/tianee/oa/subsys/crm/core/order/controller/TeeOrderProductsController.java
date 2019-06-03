package com.tianee.oa.subsys.crm.core.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.order.service.TeeOrderProductsService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeOrderProductsController")
public class TeeOrderProductsController extends BaseController{
	
	@Autowired
	private TeeOrderProductsService orderProductsService;
	
	/**
	 * 获取订单下的产品信息
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOrderProductsInfoList")
	@ResponseBody
	public TeeEasyuiDataGridJson getOrderProductsInfoList(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return orderProductsService.getOrderProductsInfoList(dm, requestDatas);
	}

	
	
	
	
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request){
		return orderProductsService.getInfoById(request);
	}
}
