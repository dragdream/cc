package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanExperienceModel;
import com.tianee.oa.core.base.pm.service.TeeHumanExperienceService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanExperienceController")
public class TeeHumanExperienceController {
	
	@Autowired
	private TeeHumanExperienceService experienceService;
	
	@RequestMapping("/addHumanExperience")
	@ResponseBody
	public TeeJson addHumanExperience(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanExperienceModel humanExperience = new TeeHumanExperienceModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanExperience);
		experienceService.addHumanExperience(humanExperience);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return experienceService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(experienceService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanExperience")
	@ResponseBody
	public TeeJson updateHumanExperience(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanExperienceModel humanExperienceModel = new TeeHumanExperienceModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanExperienceModel);
		experienceService.updateHumanExperience(humanExperienceModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanExperience")
	@ResponseBody
	public TeeJson delHumanExperience(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		experienceService.delHumanExperience(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
