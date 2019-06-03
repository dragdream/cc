package com.tianee.oa.subsys.evaluation.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTemplateModel;
import com.tianee.oa.subsys.evaluation.service.TeeEvalTemplateService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/TeeEvalTemplateController")
public class TeeEvalTemplateController extends BaseController{
	@Autowired
	private TeeEvalTemplateService templateService;
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeEvalTemplateModel model) {
		TeeJson json = new TeeJson();
		json = templateService.addOrUpdate(request , model);
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
	public TeeJson deleteById(HttpServletRequest request , TeeEvalTemplateModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = templateService.deleteByIdService(request ,sids);
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
	public TeeJson getById(HttpServletRequest request , TeeEvalTemplateModel model) {
		TeeJson json = new TeeJson();
		json = templateService.getById(request , model);
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
		return templateService.datagird(dm, requestDatas);
	}
	
}