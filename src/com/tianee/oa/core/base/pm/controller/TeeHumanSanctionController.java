package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanSanctionModel;
import com.tianee.oa.core.base.pm.service.TeeHumanSanctionService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanSanctionController")
public class TeeHumanSanctionController {
	
	@Autowired
	private TeeHumanSanctionService sanctionService;
	
	@RequestMapping("/addHumanSanction")
	@ResponseBody
	public TeeJson addHumanSanction(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanSanctionModel humanSanction = new TeeHumanSanctionModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanSanction);
		sanctionService.addHumanSanction(humanSanction);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return sanctionService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(sanctionService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanSanction")
	@ResponseBody
	public TeeJson updateHumanSanction(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanSanctionModel humanSanctionModel = new TeeHumanSanctionModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanSanctionModel);
		sanctionService.updateHumanSanction(humanSanctionModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanSanction")
	@ResponseBody
	public TeeJson delHumanSanction(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		sanctionService.delHumanSanction(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
