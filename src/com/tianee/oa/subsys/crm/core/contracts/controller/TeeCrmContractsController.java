package com.tianee.oa.subsys.crm.core.contracts.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.contracts.model.TeeCrmContractsModel;
import com.tianee.oa.subsys.crm.core.contracts.service.TeeCrmContractsService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmContractsController")
public class TeeCrmContractsController extends BaseController{
	
	@Autowired
	private TeeCrmContractsService contractsService;
	
	/**
	 * 添加或编辑合同
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmContractsModel model){
		TeeJson json = new TeeJson();
		json= contractsService.addOrUpdate(request,model);
		return json;
		
	}
	
	/**
	 * 合同列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractsService.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取合同详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmContractsModel model){
		TeeJson json = new TeeJson();
		json = contractsService.getInfoBySid(request,model);
		return json;
	}
	
	/**
	 * 作废合同
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancel")
	public TeeJson cancel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = contractsService.cancel(request);
		return json;
	}
	
	/**
	 * 恢复
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/recovery")
	public TeeJson recovery(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = contractsService.recovery(request);
		return json;
	}
	
	/**
	 * 删除合同信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteById")
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		json = contractsService.deleteById(request);
		return json;
	}
	

}
