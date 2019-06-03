package com.tianee.oa.subsys.bisengin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.model.BisModuleModel;
import com.tianee.oa.subsys.bisengin.service.BisModuleService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/bisModule")
public class BisModuleController {
	
	@Autowired
	private BisModuleService bisModuleService;
	
	@ResponseBody
	@RequestMapping("/addBisModule")
	public TeeJson addBisModule(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisModuleModel bisModuleModel = 
				(BisModuleModel) TeeServletUtility.request2Object(request, BisModuleModel.class);
		bisModuleService.addBisModule(bisModuleModel);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateBisModule")
	public TeeJson updateBisModule(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisModuleModel bisModuleModel = 
				(BisModuleModel) TeeServletUtility.request2Object(request, BisModuleModel.class);
		bisModuleService.updateBisModule(bisModuleModel);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/deleteBisModule")
	public TeeJson deleteBisModule(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		bisModuleService.deleteBisModule(uuid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getBisModule")
	public TeeJson getBisModule(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		json.setRtData(bisModuleService.getBisModule(uuid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request){
		return bisModuleService.dataGridJson();
	}
}
