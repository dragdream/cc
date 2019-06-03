package com.tianee.oa.core.base.attend.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.model.TeeAttendAssignModel;
import com.tianee.oa.core.base.attend.model.TeeDutyComplaintModel;
import com.tianee.oa.core.base.attend.service.TeeDutyComplaintService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeDutyComplaintController")
public class TeeDutyComplaintController {

	@Autowired
	private TeeDutyComplaintService  dutyComplaintService;
	
	/**
	 * 添加
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request, TeeDutyComplaintModel model) throws Exception {
		return dutyComplaintService.add(request, model);
	}
	
	
	/**
	 * 查看申诉
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/view")
	@ResponseBody
	public TeeJson view(HttpServletRequest request) throws Exception {
		return dutyComplaintService.view(request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request) throws Exception {
		return dutyComplaintService.getInfoBySid(request);
	}
	
	
	/**
	 * 考勤申诉
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/approve")
	@ResponseBody
	public TeeJson approve(HttpServletRequest request) throws Exception {
		return dutyComplaintService.approve(request);
	}
	
	
	/**
	 * 根据状态
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return dutyComplaintService.datagrid(request,dm);
	}
	
}
