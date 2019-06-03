package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanLeaveModel;
import com.tianee.oa.core.base.pm.service.TeeHumanLeaveService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("TeeHumanLeaveController")
public class TeeHumanLeaveController {
	
	@Autowired
	private TeeHumanLeaveService leaveService;
	
	@RequestMapping("/addHumanLeave")
	@ResponseBody
	public TeeJson addHumanLeave(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanLeaveModel humanLeave = new TeeHumanLeaveModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanLeave);
		int isDel = TeeStringUtil.getInteger(request.getParameter("isDel"), 0);
		leaveService.addHumanLeave(humanLeave,isDel);
		if(isDel==1){
			json.setRtMsg("保存成功，关联OA用户不能登录系统！");
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
		return leaveService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(leaveService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanLeave")
	@ResponseBody
	public TeeJson updateHumanLeave(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanLeaveModel humanLeaveModel = new TeeHumanLeaveModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanLeaveModel);
		int isDel = TeeStringUtil.getInteger(request.getParameter("isDel"), 0);
		leaveService.updateHumanLeave(humanLeaveModel,isDel);
		json.setRtState(true);
		if(isDel==1){
			json.setRtMsg("更新成功，关联OA用户不能登录系统");
		}else{
			json.setRtMsg("更新成功");
		}
		return json;
	}
	
	
	@RequestMapping("/delHumanLeave")
	@ResponseBody
	public TeeJson delHumanLeave(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		leaveService.delHumanLeave(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
