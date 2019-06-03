package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanRehabModel;
import com.tianee.oa.core.base.pm.service.TeeHumanRehabService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanRehabController")
public class TeeHumanRehabController {
	
	@Autowired
	private TeeHumanRehabService rehabService;
	
	@RequestMapping("/addHumanRehab")
	@ResponseBody
	public TeeJson addHumanRehab(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanRehabModel humanRehab = new TeeHumanRehabModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanRehab);
		int isDel = TeeStringUtil.getInteger(request.getParameter("isDel"), 0);
		rehabService.addHumanRehab(humanRehab,isDel);
		if(isDel==1){
			json.setRtMsg("保存成功，关联OA用户可以登录系统了！");
		}else{
			json.setRtMsg("保存成功");
		}
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return rehabService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(rehabService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanRehab")
	@ResponseBody
	public TeeJson updateHumanRehab(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanRehabModel humanRehabModel = new TeeHumanRehabModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanRehabModel);
		int isDel = TeeStringUtil.getInteger(request.getParameter("isDel"), 0);
		rehabService.updateHumanRehab(humanRehabModel,isDel);
		json.setRtState(true);
		if(isDel==1){
			json.setRtMsg("更新成功，关联OA用户可以登录系统了！");
		}else{
			json.setRtMsg("更新成功");
		}
		return json;
	}
	
	
	@RequestMapping("/delHumanRehab")
	@ResponseBody
	public TeeJson delHumanRehab(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		rehabService.delHumanRehab(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
