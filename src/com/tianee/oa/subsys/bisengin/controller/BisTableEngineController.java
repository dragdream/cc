package com.tianee.oa.subsys.bisengin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.model.BisTableEngineModel;
import com.tianee.oa.subsys.bisengin.service.BisTableEngineService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/bisTableEngine")
public class BisTableEngineController {
	
	@Autowired
	private BisTableEngineService bisTableEngineService;
	
	@ResponseBody
	@RequestMapping("/addBisTableEngine")
	public TeeJson addBisTableEngine(HttpServletRequest request){
		BisTableEngineModel bisTableEngineModel = (BisTableEngineModel) TeeServletUtility.request2Object(request, BisTableEngineModel.class);
		TeeJson json = new TeeJson();
		bisTableEngineService.addBisTableEngine(bisTableEngineModel);		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateBisTableEngine")
	public TeeJson updateBisTableEngine(HttpServletRequest request){
		BisTableEngineModel bisTableEngineModel = (BisTableEngineModel) TeeServletUtility.request2Object(request, BisTableEngineModel.class);
		TeeJson json = new TeeJson();
		bisTableEngineService.updateBisTableEngine(bisTableEngineModel);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delBisTableEngine")
	public TeeJson delBisTableEngine(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisTableEngineService.delBisTableEngine(sid);
		TeeJson json = new TeeJson();
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getBisTableEngine")
	public TeeJson getBisTableEngine(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeJson json = new TeeJson();
		json.setRtData(bisTableEngineService.getBisTableEngine(sid));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		Map requestData = TeeServletUtility.getParamMap(request);
		return bisTableEngineService.datagrid(dm, requestData);
	}
	
	@ResponseBody
	@RequestMapping("/setStatus")
	public TeeJson setStatus(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
		TeeJson json = new TeeJson();
		bisTableEngineService.setStatus(sid, status);
		return json;
	}
	
}
