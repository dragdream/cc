package com.tianee.oa.core.base.attend.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.model.TeeAttendAssignModel;
import com.tianee.oa.core.base.attend.service.TeeAttendAssignService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("/TeeAttendAssignController")
public class TeeAttendAssignController {
	@Autowired
	private TeeAttendAssignService attendAssignService;
	
	
	/**
	 * 添加
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request, TeeAttendAssignModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendAssignService.add(request, model);
		return json;
	}
	
	
	/**
	 * 获取详情
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request, TeeAttendAssignModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendAssignService.getInfoBySid(request, model);
		return json;
	}
	
	
	
	/**
	 * 根据时间范围   获取列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getListByStatus(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return attendAssignService.getListByStatus(request,dm);
	}
}
