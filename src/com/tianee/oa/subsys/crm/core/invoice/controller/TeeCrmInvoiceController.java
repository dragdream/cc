package com.tianee.oa.subsys.crm.core.invoice.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.invoice.model.TeeCrmInvoiceModel;
import com.tianee.oa.subsys.crm.core.invoice.service.TeeCrmInvoiceService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmInvoiceController")
public class TeeCrmInvoiceController extends BaseController {
	
	@Autowired
	private TeeCrmInvoiceService invoiceService;
	
	/**
	 * 添加或编辑开票
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmInvoiceModel model){
		TeeJson json = new TeeJson();
		json = invoiceService.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 开票列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return invoiceService.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取开票信息详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmInvoiceModel model){
		TeeJson json = new TeeJson();
		json = invoiceService.getInfoBySid(request,model);
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
		TeeJson json= new TeeJson();
		json = invoiceService.agree(request);
		return json;
	}
	
	/**
	 * 驳回
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("reject")
	public TeeJson reject(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = invoiceService.reject(request);
		return json;
	}
	
	/**
	 * 删除开票记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteById")
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = invoiceService.deleteById(request);
		return json;
	}

}
