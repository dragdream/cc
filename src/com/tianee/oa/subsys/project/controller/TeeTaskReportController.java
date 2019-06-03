package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeTaskReportService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/taskReportController")
public class TeeTaskReportController {
	@Autowired
	private TeeTaskReportService taskReportService;
	
	
	
	/**
	 * 根据任务主键  获取任务汇报列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getReportListByTaskId")
	@ResponseBody
	public TeeJson getReportListByTaskId(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskReportService.getReportListByTaskId(request);
	}
	
	
	
	/**
	 * 添加任务汇报内容
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addReport")
	@ResponseBody
	public TeeJson addReport(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskReportService.addReport(request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskReportService.getInfoBySid(request);
	}
}
