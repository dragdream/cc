package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanEducationModel;
import com.tianee.oa.core.base.pm.service.TeeHumanEducationService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanEducationController")
public class TeeHumanEducationController {
	
	@Autowired
	private TeeHumanEducationService educationService;
	
	@RequestMapping("/addHumanEducation")
	@ResponseBody
	public TeeJson addHumanEducation(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanEducationModel humanEducation = new TeeHumanEducationModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanEducation);
		educationService.addHumanEducation(humanEducation);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return educationService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(educationService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanEducation")
	@ResponseBody
	public TeeJson updateHumanEducation(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanEducationModel humanEducationModel = new TeeHumanEducationModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanEducationModel);
		educationService.updateHumanEducation(humanEducationModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanEducation")
	@ResponseBody
	public TeeJson delHumanEducation(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		educationService.delHumanEducation(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
