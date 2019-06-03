package com.tianee.oa.core.base.onduty.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.onduty.model.TeePbTypeChildModel;
import com.tianee.oa.core.base.onduty.service.TeePbTypeChildService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("teePbTypeChildController")
public class TeePbTypeChildController {

	@Autowired
	private TeePbTypeChildService teePbTypeChildService;
	
	
	/**
	 * 获取字段列表
	 * */
	@ResponseBody
	@RequestMapping("/findFieldAll")
	public TeeEasyuiDataGridJson findFieldAll(HttpServletRequest request,TeeDataGridModel m){
		return teePbTypeChildService.findFieldAll(request,m);
	}
	
	/**
	 * 添加或修改字段
	 * */
	@ResponseBody
	@RequestMapping("/addOrUpdateField")
	public TeeJson addOrUpdateField(TeePbTypeChildModel model){
		return teePbTypeChildService.addOrUpdateField(model);
	}
	
	/**
	 * 删除字段
	 * */
	@ResponseBody
	@RequestMapping("/deleteField")
	public TeeJson deleteField(int sid){
		return teePbTypeChildService.deleteField(sid);
	}
	
}
