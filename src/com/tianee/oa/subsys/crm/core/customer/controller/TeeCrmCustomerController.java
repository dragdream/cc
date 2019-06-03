package com.tianee.oa.subsys.crm.core.customer.controller;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.incors.plaf.alloy.cu;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeExtendFiled;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerModel;
import com.tianee.oa.subsys.crm.core.customer.service.TeeCrmCustomerExeFiledService;
import com.tianee.oa.subsys.crm.core.customer.service.TeeCrmCustomerService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;


@Controller
@RequestMapping("/TeeCrmCustomerController")
public class TeeCrmCustomerController extends BaseController{
	@Autowired
	private TeeCrmCustomerService customerService;
	
	@Autowired
	private TeeCrmCustomerExeFiledService fieldService;
	/**
	 * 判断项目名称是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/isExistCustomer")
	@ResponseBody
	public TeeJson isExistCustomer(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = customerService.isExistCustomNum(request);
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeCrmCustomerModel model) {
		TeeJson json = new TeeJson();
		json = customerService.addOrUpdate(request, model);
		return json;
	}
	
	/**
	 * 获取自定义字段
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListFieldByCustomer")
	@ResponseBody
	private TeeJson getListFieldByCustomer(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeExtendFiled> list = new ArrayList<TeeExtendFiled>();
		list = fieldService.getListFieldByCustomer(request);
		json.setRtData(list);
		json.setRtMsg("查询成功！");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取自定义字段作为查询的字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getQueryFieldListById")
	public TeeJson getQueryFieldListById(HttpServletRequest request){
		return fieldService.getQueryFieldListById(request);
	}
	
	/**
	 * 获取自定义字段作为显示的字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getShowFieldListById")
	public TeeJson getShowFieldListById(HttpServletRequest request){
		return fieldService.getShowFieldListById(request);
	}
	
	/**
	 * 查询客户列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return customerService.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取客户详细信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json=customerService.getInfoBySid(request);
		return json;
	}
	
	/**
	 * 获取客户信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getById")
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json =customerService.getById(request);
		return json;
	}
	
	/**
	 * 更换负责人
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeManage")
	public TeeJson changeManage(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json= customerService.changeManage(request);
		return json;
	}
	
	/**
	 * 作废
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancel")
	public TeeJson cancel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = customerService.cancel(request);
		return json;
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delCustomer")
	public TeeJson delCustomer(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = customerService.delCustomerById(request);
		return json;
	}
	
	/**
	 * 恢复客户状态到作废前
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/recovery")
	public TeeJson recovery(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = customerService.recovery(request);
		return json;
	}
	
	/**
	 * 销售汇总
	 * @param request
	 * @return
	 */
	@RequestMapping("/salesSummary")
	@ResponseBody
	public TeeJson salesSummary(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = customerService.salesSummary(request);
		return json;
		
	}
	
	/**
	 * 获取所有客户数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectAllCustomer")
	@ResponseBody
	public TeeJson selectAllCustomer(HttpServletRequest request){
		TeeJson json = customerService.selectAllCustomer(request);
		return json;
	}
		

}