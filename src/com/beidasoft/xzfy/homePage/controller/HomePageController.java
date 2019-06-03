/**
 * 
 */
package com.beidasoft.xzfy.homePage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.homePage.service.CaseSummaryService;

/**
 * 功能：首页相关请求控制器
 * @author HeLi
 * @version v1.0
 */
@Controller
@RequestMapping("/xzfy/homepage")
public class HomePageController {
	@Autowired
	private CaseSummaryService caseSummaryService;
	
	/**
	 * 根据用户id获取案件状态统计,对应首页的任务统计栏
	 * @param userId
	 * @return Json
	 */
	@ResponseBody
	@RequestMapping("/caseSummary/listByStatus")
	public List listByStatus(String userId) {
		return caseSummaryService.listCaseStatusSummary(userId);
	}
	
	
	/**
	 * 根据用户id获取案件受理情况统计，对应首页的饼图
	 * @param userId
	 * @return Json
	 */
	@ResponseBody
	@RequestMapping("/caseSummary/listByAccepted")
	public List listByAccepted(String userId) {
		return caseSummaryService.listCaseAcceptedSummary(userId);
	}
	
	/**
	 * 根据用户id获取案件结案情况统计，对应首页的柱状图
	 * @param userId
	 * @return Json
	 */
	@ResponseBody
	@RequestMapping("/caseSummary/listByClosed")
	public List listByClosed(String userId) {
		return caseSummaryService.listCaseClosedSummary(userId);
	}
	
}
