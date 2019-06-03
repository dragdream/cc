package com.tianee.oa.subsys.footprint.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.footprint.service.TeeFootPrintRangeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeFootPrintRangeController")
public class TeeFootPrintRangeController {
	@Autowired
	private TeeFootPrintRangeService rangeService;
	
	
	
	/**
	 * 获取所有的围栏设置列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping("/getAllList")
	@ResponseBody
	public TeeEasyuiDataGridJson getAllList(HttpServletRequest request,TeeDataGridModel dm){
		return rangeService.getAllList(request,dm);
	}
	
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request){	
		return rangeService.addOrUpdate(request);
	}
	

	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){	
		return rangeService.getInfoBySid(request);
	}
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){	
		return rangeService.delBySid(request);
	}
	
	
	
	/**
	 * 设计范围
	 * @param request
	 * @return
	 */
	@RequestMapping("/design")
	@ResponseBody
	public TeeJson design(HttpServletRequest request){	
		return rangeService.design(request);
	}
	
}
