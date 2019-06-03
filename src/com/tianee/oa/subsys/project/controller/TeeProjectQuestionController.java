package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.model.TeeProjectQuestionModel;
import com.tianee.oa.subsys.project.service.TeeProjectQuestionService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectQuestionController")
public class TeeProjectQuestionController {
 
	@Autowired
	private TeeProjectQuestionService projectQuestionService;
	
	/**
	 * 
	 * 创建问题
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addQuestion")
	@ResponseBody
	public TeeJson addQuestion(HttpServletRequest request , HttpServletResponse response,TeeProjectQuestionModel model) throws ParseException {	
		return projectQuestionService.addQuestion(request,model);
	}
	
	
	
	
	/**
	 * 根据任务主键 获取问题列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getQuestionListByTaskId")
	@ResponseBody
	public TeeEasyuiDataGridJson getQuestionListByTaskId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectQuestionService.getQuestionListByTaskId(request,dm);
	}
	
	
	
	/**
	 * 根据问题主键获取问题详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectQuestionService.getInfoBySid(request);
	}
	
	
	
    /**
     * 获取当前登陆人 待解决  已解决的问题列表
     * @param dm
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
	@RequestMapping("/getQuestionListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getQuestionListByStatus(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectQuestionService.getQuestionListByStatus(request,dm);
	}
	
	
	/**
	 * 获取项目的所有问题列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getQuestionListByProjectId")
	@ResponseBody
	public TeeEasyuiDataGridJson getQuestionListByProjectId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectQuestionService.getQuestionListByProjectId(request,dm);
	}
	
	
	
	/**
	 * 根据项目主键 获取问题列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getQuestionsByProjectId")
	@ResponseBody
	public TeeJson getQuestionsByProjectId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectQuestionService.getQuestionsByProjectId(request,dm);
	}
	
	
	
	
	/**
	 * 问题办理
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/handle")
	@ResponseBody
	public TeeJson handle(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectQuestionService.handle(request);
	}
}
