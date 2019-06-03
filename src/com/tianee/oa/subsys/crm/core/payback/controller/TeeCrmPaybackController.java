package com.tianee.oa.subsys.crm.core.payback.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.payback.model.TeeCrmPaybackModel;
import com.tianee.oa.subsys.crm.core.payback.service.TeeCrmPaybackService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmPaybackController")
public class TeeCrmPaybackController extends BaseController {
	
	@Autowired
	private TeeCrmPaybackService paybackService;
	
	/**
	 * 添加或编辑回款
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmPaybackModel model){
		TeeJson json = new TeeJson();
		json = paybackService.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 回款列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return paybackService.datagird(dm, requestDatas);
	}
	
	/**
	 * 回款详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmPaybackModel model){
		TeeJson json = new TeeJson();
		json = paybackService.getInfoBySid(request,model);
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
		json = paybackService.agree(request);
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
		json = paybackService.reject(request);
		return json;
	}
	
	/**
	 * 删除回款信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteById")
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = paybackService.deleteById(request);
		return json;
	}

}
