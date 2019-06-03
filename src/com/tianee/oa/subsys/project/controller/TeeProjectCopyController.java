package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeProjectCopyService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectCopyController")
public class TeeProjectCopyController {
	@Autowired
	private TeeProjectCopyService projectCopyService;
	
	/**
	 * 抄送
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/copy")
	@ResponseBody
	public TeeJson copy(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCopyService.copy(request);
	}
	
	
	
	/**
	 * 获取我的查阅列表  根据已阅   待阅
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getMyLookUpByReadFlag")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyLookUpByReadFlag(HttpServletRequest request , HttpServletResponse response,TeeDataGridModel dm) throws ParseException {	
		return projectCopyService.getMyLookUpByReadFlag(request,dm);
	}
	
	
	
	/**
	 * 改变阅读状态
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/changeReadFlag")
	@ResponseBody
	public TeeJson changeReadFlag(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectCopyService.changeReadFlag(request);
	}
}
