package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanShiftModel;
import com.tianee.oa.core.base.pm.service.TeeHumanShiftService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanShiftController")
public class TeeHumanShiftController {
	
	@Autowired
	private TeeHumanShiftService shiftService;
	
	@RequestMapping("/addHumanShift")
	@ResponseBody
	public TeeJson addHumanShift(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanShiftModel humanShift = new TeeHumanShiftModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanShift);
		shiftService.addHumanShift(humanShift);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return shiftService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(shiftService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanShift")
	@ResponseBody
	public TeeJson updateHumanShift(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanShiftModel humanShiftModel = new TeeHumanShiftModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanShiftModel);
		shiftService.updateHumanShift(humanShiftModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanShift")
	@ResponseBody
	public TeeJson delHumanShift(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		shiftService.delHumanShift(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
