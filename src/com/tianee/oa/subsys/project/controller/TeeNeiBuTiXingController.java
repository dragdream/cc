package com.tianee.oa.subsys.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.model.TeeNeiBuTiXingModel;
import com.tianee.oa.subsys.project.service.TeeNeiBuTiXingService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("neiBuTiXingController")
public class TeeNeiBuTiXingController {

	@Autowired
	private TeeNeiBuTiXingService teeNeiBuTiXingService;
	
	/**
	 * 获取所有提示信息
	 * */
	@ResponseBody
	@RequestMapping("/getNeiBuTiXingList")
	public TeeEasyuiDataGridJson getNeiBuTiXingList(TeeDataGridModel m,HttpServletRequest request){
		return teeNeiBuTiXingService.getNeiBuTiXingList(m,request);
	}
	
	/**
	 * 添加或修改提示信息
	 * */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(TeeNeiBuTiXingModel model,HttpServletRequest request){
		return teeNeiBuTiXingService.addOrUpdate(model,request);
	}
	
	/**
	 * 删除提示信息
	 * */
	@ResponseBody
	@RequestMapping("/deleteTiXing")
	public TeeJson deleteTiXing(HttpServletRequest request){
	   return teeNeiBuTiXingService.deleteTiXing(request);
	}
	
	/**
	 * 根据提示信息ID查找提示信息
	 * */
	@ResponseBody
	@RequestMapping("/getTiXingById")
	public TeeJson getTiXingById(HttpServletRequest request){
		return teeNeiBuTiXingService.getTiXingById(request);
	}
}
