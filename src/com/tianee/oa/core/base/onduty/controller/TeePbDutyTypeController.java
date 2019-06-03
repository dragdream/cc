package com.tianee.oa.core.base.onduty.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.onduty.model.TeePbDutyTypeModel;
import com.tianee.oa.core.base.onduty.service.TeePbDutyTypeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("teePbDutyTypeController")
public class TeePbDutyTypeController {

	@Autowired
	private TeePbDutyTypeService teePbDutyTypeService;
	
	/**
	 * 获取值班类型列表
	 * */
	@ResponseBody
	@RequestMapping("/findDutyTypeList")
	public TeeEasyuiDataGridJson findDutyTypeList(HttpServletRequest request,TeeDataGridModel m){
		return teePbDutyTypeService.findDutyTypeList(request,m);
	}
	
	/**
	 * 添加或修改值班类型
	 * */
	@ResponseBody
	@RequestMapping("/addOrUpdateDutyType")
	public TeeJson addOrUpdateDutyType(HttpServletRequest request,TeePbDutyTypeModel model){
		return teePbDutyTypeService.addOrUpdateDutyType(request,model);
	}
	
	/**
	 * 删除值班类型
	 * */
	@ResponseBody
	@RequestMapping("/deleteDutyType")
	public TeeJson deleteDutyType(int sid){
		return teePbDutyTypeService.deleteDutyType(sid);
	}
	
	/**
	 * 获取类型的详细信息
	 * */
	@ResponseBody
	@RequestMapping("/findDutyTypeById")
	public TeeJson findDutyTypeById(HttpServletRequest request){
		return teePbDutyTypeService.findDutyTypeById(request);
	}
	
	/**
	 * 获取所有值班信息
	 * */
	@ResponseBody
	@RequestMapping("/findDutyTypeAll")
	public TeeJson findDutyTypeAll(){
		return teePbDutyTypeService.findDutyTypeAll();
	}
	
}
