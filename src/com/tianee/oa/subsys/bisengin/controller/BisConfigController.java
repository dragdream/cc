package com.tianee.oa.subsys.bisengin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.service.BisConfigService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/bisConfig")
public class BisConfigController {
	
	@Autowired
	private BisConfigService bisConfigService;
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagird(){
		return bisConfigService.datagrid();
	}
	
	@ResponseBody
	@RequestMapping("/isOpenBisEngine")
	public TeeJson isOpenBisEngine(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int type = TeeStringUtil.getInteger(request.getParameter("type"), 0);
		json.setRtData(bisConfigService.isOpenBisEngine(type));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/setStatus")
	public TeeJson setStatus(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int type = TeeStringUtil.getInteger(request.getParameter("type"), 0);
		int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
		bisConfigService.setStatus(type, status);
		return json;
	}
}
