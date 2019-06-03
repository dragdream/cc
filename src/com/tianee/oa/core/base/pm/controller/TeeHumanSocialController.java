package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanSocialModel;
import com.tianee.oa.core.base.pm.service.TeeHumanSocialService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanSocialController")
public class TeeHumanSocialController {
	
	@Autowired
	private TeeHumanSocialService socialService;
	
	@RequestMapping("/addHumanSocial")
	@ResponseBody
	public TeeJson addHumanSocial(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanSocialModel humanSocial = new TeeHumanSocialModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanSocial);
		socialService.addHumanSocial(humanSocial);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return socialService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(socialService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanSocial")
	@ResponseBody
	public TeeJson updateHumanSocial(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanSocialModel humanSocialModel = new TeeHumanSocialModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanSocialModel);
		socialService.updateHumanSocial(humanSocialModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanSocial")
	@ResponseBody
	public TeeJson delHumanSocial(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		socialService.delHumanSocial(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
