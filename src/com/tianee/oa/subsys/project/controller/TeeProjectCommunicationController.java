package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.service.TeeProjectCommunicationService;
import com.tianee.oa.subsys.topic.model.TeeTopicReplyModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/projectCommunicationController")
public class TeeProjectCommunicationController {

	@Autowired
	private TeeProjectCommunicationService projectCommunicationService;
	
	/**
	 * 新增/编辑交流
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCommunicationService.addOrUpdate(request);
	}
	
	
	/**
	 * 根据项目主键  获取对应的项目交流
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCommunicationPage")
	@ResponseBody
	public TeeEasyuiDataGridJson getCommunicationPage(TeeDataGridModel dm, HttpServletRequest request) {
		
		return projectCommunicationService.getCommunicationPage(dm, request);
	}
	
	
	/**
	 * 删除项目交流
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCommunicationService.delBySid(request);
	}
	
	
	/**
	 * 根据主键 获取项目交流内容
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCommunicationService.getInfoBySid(request);
	}
}
