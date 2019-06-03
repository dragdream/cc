package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeProjectCostService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
@Controller
@RequestMapping("/projectCostController")
public class TeeProjectCostController {

	@Autowired
	private TeeProjectCostService projectCostService;
	

	/**
	 * 增加项目预算
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.add(request);
	}
	
	
	
	/**
	 * 根据项目主键    获取每个费用类型的金额总数
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getCostSumList")
	@ResponseBody
	public TeeJson getCostSumList(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.getCostSumList(request);
	}
	
	
	
	/**
	 * 根据项目主键 和 费用类型  获取审批通过的预算
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getCostDetail")
	@ResponseBody
	public TeeJson getCostDetail(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.getCostDetail(request);
	}
	
	
	
	/**
	 * 根据状态获取我的审批列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getApproveListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getApproveListByStatus(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.getApproveListByStatus(request,dm);
	}
	
	
	
    /**
     * 费用审批
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
	@RequestMapping("/approve")
	@ResponseBody
	public TeeJson approve(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.approve(request);
	}
	
	
	
	/**
	 * 根据状态 获取  我的费用列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getMyCostListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyCostListByStatus(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.getMyCostListByStatus(request,dm);
	}
	
	
	
	 /**
	  * 根据主键删除费用申请
	  * @param request
	  * @param response
	  * @return
	  * @throws ParseException
	  */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.delBySid(request);
	}
	
	
	
	 /**
	  * 根据主键  获取详情
	  * @param request
	  * @param response
	  * @return
	  * @throws ParseException
	  */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.getInfoBySid(request);
	}
	
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCostService.update(request);
	}
}
