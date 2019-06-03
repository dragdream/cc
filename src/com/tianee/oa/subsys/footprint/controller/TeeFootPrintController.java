package com.tianee.oa.subsys.footprint.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.footprint.service.TeeFootPrintService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeFootPrintController")
public class TeeFootPrintController {

	@Autowired
	private TeeFootPrintService   footPrintService;
	
	
	/**
	 * 添加足迹信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFootPrint")
	@ResponseBody
	public TeeJson addFootPrint(HttpServletRequest request){	
		return footPrintService.addFootPrint(request);
	}
	
	/**
	 * 添加足迹信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFootPrintBatch")
	@ResponseBody
	public TeeJson addFootPrintBatch(HttpServletRequest request){	
		return footPrintService.addFootPrintBatch(request);
	}
	
	/**
	 * 根据时间查找我的足迹集合
	 * @param request
	 * @return
	 */
	@RequestMapping("/findFootPrintList")
	@ResponseBody
	public TeeEasyuiDataGridJson findFootPrintList(HttpServletRequest request,TeeDataGridModel dm){
		
		return footPrintService.findFootPrintList(request,dm);
	}
	
	
	/**
	 * 根据时间和人员查找的足迹集合
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListByPerson")
	@ResponseBody
	public TeeEasyuiDataGridJson getListByPerson(HttpServletRequest request,TeeDataGridModel dm){
		
		return footPrintService.getListByPerson(request,dm);
	}
	
	
	
	/**
	 * 电子围栏报警次数统计图
	 * @param request
	 * @return
	 */
	@RequestMapping("/renderCrossImg")
	@ResponseBody
	public TeeJson renderCrossImg(HttpServletRequest request){	
		return footPrintService.renderCrossImg(request);
	}
	
	
	/**
	 * 获取报警记录列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping("/getAcrossList")
	@ResponseBody
	public TeeEasyuiDataGridJson getAcrossList(HttpServletRequest request,TeeDataGridModel dm){
		
		return footPrintService.getAcrossList(request,dm);
	}
	
}
