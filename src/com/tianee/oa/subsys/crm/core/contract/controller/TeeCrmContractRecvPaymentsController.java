package com.tianee.oa.subsys.crm.core.contract.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractRecvPaymentsModel;
import com.tianee.oa.subsys.crm.core.contract.service.TeeCrmContractRecvPaymentsService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
@Controller
@RequestMapping("/crmContractRecvPaymentsController")
public class TeeCrmContractRecvPaymentsController  extends BaseController{
	@Autowired
	TeeCrmContractRecvPaymentsService contractRecvPaymentsService;
	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request, TeeCrmContractRecvPaymentsModel object){
		TeeJson json = contractRecvPaymentsService.addOrUpdate( request ,object);
		return json;
	}
	
	/**
	 * 回款管理  --通用分页
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager")
	@ResponseBody
	public TeeEasyuiDataGridJson manager(TeeDataGridModel dm,HttpServletRequest request ,TeeCrmContractRecvPaymentsModel model) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return contractRecvPaymentsService.manager(dm, request, loginPerson ,model);
	}
	
	/**
	 * 根据Id  查询
	 * @param request
	 * @param object
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request, TeeCrmContractRecvPaymentsModel object){
		TeeJson json = contractRecvPaymentsService.getById( request ,object);
		return json;
	}
	
	/**
	 * 删除 ByIds  
	 * @param request
	 * @param object
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public TeeJson deleteByIds(HttpServletRequest request, TeeCrmContractRecvPaymentsModel object){
		TeeJson json = contractRecvPaymentsService.deleteByIds( request ,object);
		return json;
	}
	
	/**
	 * 批量设置回款 ByIds  
	 * @param request
	 * @param object
	 * @return
	 */
	@RequestMapping("/batchRecvPayment")
	@ResponseBody
	public TeeJson batchRecvPayment(HttpServletRequest request, TeeCrmContractRecvPaymentsModel object){
		TeeJson json = contractRecvPaymentsService.batchRecvPayment( request ,object);
		return json;
	}
	
	
}
