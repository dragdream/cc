package com.tianee.oa.core.base.officeProducts.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeCategory;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeCategoryModel;
import com.tianee.oa.core.base.officeProducts.service.TeeOfficeCategoryService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("officeCategoryController")
public class TeeOfficeCategoryController {
	@Autowired
	TeeOfficeCategoryService officeCategoryService;
	
	@RequestMapping("/addCategory")
	@ResponseBody
	public TeeJson addCategory(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeOfficeCategoryModel categoryModel = new TeeOfficeCategoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, categoryModel);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		categoryModel.setCreateUserId(loginUser.getUuid());
		officeCategoryService.addCategoryModel(categoryModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editCategory")
	@ResponseBody
	public TeeJson editCategory(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeOfficeCategoryModel categoryModel = new TeeOfficeCategoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, categoryModel);
		officeCategoryService.updateCategoryModel(categoryModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delCategory")
	@ResponseBody
	public TeeJson delCategory(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeOfficeCategory category = officeCategoryService.getById(sid);
		officeCategoryService.deleteCategory(category);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getCategory")
	@ResponseBody
	public TeeJson getCategory(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeOfficeCategory category = officeCategoryService.getById(sid);
		TeeOfficeCategoryModel model = new TeeOfficeCategoryModel();
		BeanUtils.copyProperties(category, model);
		model.setCreateUserId(category.getCreateUser().getUuid());
		model.setCreateUserDesc(category.getCreateUser().getUserName());
		if(category.getOfficeDepository()!=null){
			model.setOfficeDepositoryDesc(category.getOfficeDepository().getDeposName());
			model.setOfficeDepositoryId(category.getOfficeDepository().getSid());
		}
		
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeCategoryService.datagrid(dm, requestDatas);
	}
	
	@RequestMapping("/getCatListWithNoPriv")
	@ResponseBody
	public TeeJson getCatListWithNoPriv(TeeDataGridModel dm,HttpServletRequest request){
		TeeJson json = new TeeJson();
		int deposId = TeeStringUtil.getInteger(request.getParameter("deposId"), 0);
		json.setRtData(officeCategoryService.getCatListWithNoPriv(deposId));
		json.setRtState(true);
		return json;
	}
	
}
