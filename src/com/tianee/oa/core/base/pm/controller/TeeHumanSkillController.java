package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanSkillModel;
import com.tianee.oa.core.base.pm.service.TeeHumanSkillService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanSkillController")
public class TeeHumanSkillController {
	
	@Autowired
	private TeeHumanSkillService skillService;
	
	@RequestMapping("/addHumanSkill")
	@ResponseBody
	public TeeJson addHumanSkill(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanSkillModel humanSkill = new TeeHumanSkillModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanSkill);
		skillService.addHumanSkill(humanSkill);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return skillService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(skillService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanSkill")
	@ResponseBody
	public TeeJson updateHumanSkill(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanSkillModel humanSkillModel = new TeeHumanSkillModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanSkillModel);
		skillService.updateHumanSkill(humanSkillModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanSkill")
	@ResponseBody
	public TeeJson delHumanSkill(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		skillService.delHumanSkill(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
