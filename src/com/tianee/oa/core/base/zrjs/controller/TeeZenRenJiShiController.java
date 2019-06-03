package com.tianee.oa.core.base.zrjs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.zrjs.model.TeeZenRenJiShiModel;
import com.tianee.oa.core.base.zrjs.service.TeeZenRenJiShiService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("teeZenRenJiShiController")
public class TeeZenRenJiShiController {

	@Autowired
	private TeeZenRenJiShiService teeZenRenJiShiService;
	
	/**
	 * 添加/修改责任纪实
	 * */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeZenRenJiShiModel model){
		return teeZenRenJiShiService.addOrUpdate(request,model);
	}
	
	/**
	 * 查询责任纪实
	 * */
	@ResponseBody
	@RequestMapping("/findZenRenJiShi")
	public TeeJson findZenRenJiShi(HttpServletRequest request){
		return teeZenRenJiShiService.findZenRenJiShi(request);
	}
	
	/**
	 * 查询责任纪实列表
	 * */
	@ResponseBody
	@RequestMapping("/ZenREnJiShiList")
	public TeeEasyuiDataGridJson ZenREnJiShiList(HttpServletRequest request,TeeDataGridModel m){
		return teeZenRenJiShiService.ZenREnJiShiList(request,m);
	}
	
	/**
	 * 删除责任纪实
	 * */
	@ResponseBody
	@RequestMapping("/deleteZenRenJiShi")
	public TeeJson deleteZenRenJiShi(HttpServletRequest request){
		return teeZenRenJiShiService.deleteZenRenJiShi(request);
	}
	/**
	 * 删除责任纪实(批量)
	 * */
	@ResponseBody
	@RequestMapping("/deletePicZenRenJiShi")
	public TeeJson deletePicZenRenJiShi(HttpServletRequest request){
		return teeZenRenJiShiService.deletePicZenRenJiShi(request);
	}
	
	/**
	 * 签字
	 * */
	@ResponseBody
	@RequestMapping("/qianZiBySid")
	public TeeJson qianZiBySid(HttpServletRequest request){
		return teeZenRenJiShiService.qianZiBySid(request);
	}
	
	/**
	 * 导出
	 * */
	@ResponseBody
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
	    teeZenRenJiShiService.exportExcel(request,response);
	}
	
	@ResponseBody
	@RequestMapping("/getZenRenJiShiAll")
	public TeeEasyuiDataGridJson getZenRenJiShiAll(HttpServletRequest request,TeeDataGridModel m){
		return teeZenRenJiShiService.getZenRenJiShiAll(request,m);
	}
	
	/**
	 * 调用内部信息接口
	 * */
	@ResponseBody
	@RequestMapping("/sendSmsSender")
	public TeeJson sendSmsSender(HttpServletRequest request){
		return teeZenRenJiShiService.sendSmsSender(request);
	}
}
