package com.tianee.oa.subsys.contract.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.contract.model.TeeContractCategoryModel;
import com.tianee.oa.subsys.contract.service.TeeContractCategoryService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeRequestExceptionInterceptor;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/contractCategory")
public class TeeContractCategoryController {
	
	@Autowired
	private TeeContractCategoryService categoryService;
	
	@ResponseBody
	@RequestMapping("/add")
	public TeeJson add(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeContractCategoryModel categoryModel = 
				(TeeContractCategoryModel) TeeServletUtility.request2Object(request, TeeContractCategoryModel.class);
		categoryService.add(categoryModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeContractCategoryModel categoryModel = 
				(TeeContractCategoryModel) TeeServletUtility.request2Object(request, TeeContractCategoryModel.class);
		categoryService.update(categoryModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		categoryService.delete(sid);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(categoryService.get(sid));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		TeePerson loginUser = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return categoryService.datagrid(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/getCategoryTreeByViewPriv")
	public TeeJson getCategoryTreeByViewPriv(HttpServletRequest request,TeeDataGridModel dm){
		TeeJson json = new TeeJson();
		TeePerson loginUser = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(categoryService.getCategoryTreeByViewPriv(loginUser));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getCategoryTreeByManagePriv")
	public TeeJson getCategoryTreeByManagePriv(HttpServletRequest request,TeeDataGridModel dm){
		TeeJson json = new TeeJson();
		TeePerson loginUser = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(categoryService.getCategoryTreeByManagePriv(loginUser));
		json.setRtState(true);
		return json;
	}
}
