package com.tianee.oa.core.customnumber.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.customnumber.bean.TeeCustomNumber;
import com.tianee.oa.core.customnumber.service.TeeCusNumberService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cusNumberController")
public class TeeCusNumberController{
	
	@Autowired
	private TeeCusNumberService cusNumberService;
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		return cusNumberService.datagrid(dm, null);
	}
	
	
	@RequestMapping("/addCusNumber")
	@ResponseBody
	public TeeJson addCusNumber(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCustomNumber  customNumber = new TeeCustomNumber();
		TeeServletUtility.requestParamsCopyToObject(request, customNumber);
		cusNumberService.addCusNumber(customNumber);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int uuid = TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
		cusNumberService.deleteUser(uuid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(HttpServletRequest request,TeeCustomNumber customNumber){
		TeeJson json = new TeeJson();
		int uuid= Integer.parseInt(request.getParameter("uuid"));
		TeeServletUtility.requestParamsCopyToObject(request, customNumber);
		cusNumberService.updateNumber(customNumber, uuid);
		
		json.setRtMsg("修改成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int uuid = TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
		json.setRtData(cusNumberService.getCustomNumber(uuid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/generateCustomNumber")
	public TeeJson generateCustomNumber(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int uuid=TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
		String model = TeeStringUtil.getString(request.getParameter("model"));
		String modelId = TeeStringUtil.getString(request.getParameter("modelId"));
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(cusNumberService.generateCustomNumber(uuid,model,modelId));
		return json;
	}
	
	
}
