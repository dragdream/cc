package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.model.TeeTaskModel;
import com.tianee.oa.subsys.project.service.TeeProjectFlowService;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("/projectFlowController")
public class TeeProjectFlowController {
	@Autowired
	private TeeProjectFlowService projectFlowService;
	
	

	/**
	 * 新增
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request , HttpServletResponse response,TeeTaskModel model) throws ParseException {	
		return projectFlowService.add(request,model);
	}
	
	
	
	/**
	 * 根据任务主键   获取与任务相关的流程数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getFlowRunData")
	@ResponseBody
	public TeeJson getFlowRunData(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectFlowService.getFlowRunData(request);
	}
	
}
