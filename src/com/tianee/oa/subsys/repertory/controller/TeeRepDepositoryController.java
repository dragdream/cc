package com.tianee.oa.subsys.repertory.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.repertory.model.TeeRepDepositoryModel;
import com.tianee.oa.subsys.repertory.service.TeeRepDepositoryService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("repDepository")
public class TeeRepDepositoryController {
	
	@Autowired
	private TeeRepDepositoryService depositoryService;
	
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeRepDepositoryModel depositoryModel = 
				(TeeRepDepositoryModel) TeeServletUtility.request2Object(request, TeeRepDepositoryModel.class);
		depositoryService.save(depositoryModel);
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeRepDepositoryModel depositoryModel = 
				(TeeRepDepositoryModel) TeeServletUtility.request2Object(request, TeeRepDepositoryModel.class);
		depositoryService.update(depositoryModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		json.setRtState(true);
		json.setRtData(depositoryService.get(sid));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		depositoryService.delete(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/depositoryList")
	public TeeEasyuiDataGridJson depositoryList(HttpServletRequest request){
		return depositoryService.depositoryList();
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public TeeEasyuiDataGridJson list(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return depositoryService.list(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/detailList")
	public TeeEasyuiDataGridJson detailList(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return depositoryService.detailList(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/clear")
	public TeeJson clear(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		depositoryService.clear(requestData);
		return json;
	}
}
