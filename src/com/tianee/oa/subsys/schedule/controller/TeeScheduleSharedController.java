package com.tianee.oa.subsys.schedule.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.schedule.service.TeeScheduleSharedService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/scheduleShared")
public class TeeScheduleSharedController {
	
	@Autowired
	private TeeScheduleSharedService scheduleSharedService;
	
	@ResponseBody
	@RequestMapping("/read")
	public TeeJson read(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		scheduleSharedService.read(uuid);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return scheduleSharedService.datagrid(requestData, dm);
	}
	
	
	@ResponseBody
	@RequestMapping("/getShareSchedules")
	public TeeJson getShareSchedules(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		json.setRtData(scheduleSharedService.getShareSchedules(requestData));
		json.setRtState(true);
		return json;
	}
}
