package com.tianee.oa.core.workflow.flowrun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.model.TeeDocNumModel;
import com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/docNumController")
public class TeeDocNumController {
	
	@Autowired
	private TeeDocNumServiceInterface docNumService;
	
	@RequestMapping(value="/saveDocNum")
	@ResponseBody
	public TeeJson saveDocNum(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeDocNumModel docNumModel = new TeeDocNumModel();
		TeeServletUtility.requestParamsCopyToObject(request, docNumModel);
		docNumService.addDocNumModel(docNumModel);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping(value="/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeDocNumModel model = docNumService.getById(sid);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	
	@RequestMapping(value="/updateDocNum")
	@ResponseBody
	public TeeJson updateDocNum(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeDocNumModel docNumModel = new TeeDocNumModel();
		TeeServletUtility.requestParamsCopyToObject(request, docNumModel);
		docNumService.updateDocNumModel(docNumModel);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping(value="/deleteDocNum")
	@ResponseBody
	public TeeJson deleteDocNum(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		docNumService.deleteDocNumById(sid);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping(value="/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		return docNumService.datagrid(dm, null);
	}
	
	@RequestMapping(value="/listHistory")
	@ResponseBody
	public TeeEasyuiDataGridJson listHistory(HttpServletRequest request,TeeDataGridModel dm){
		return docNumService.listHistory(dm, TeeServletUtility.getParamMap(request));
	}
	
	@RequestMapping(value="/getDocNumListByPriv")
	@ResponseBody
	public TeeJson getDocNumListByPriv(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(docNumService.getDocNumListByPriv(dm, loginPerson));
		return json;
	}
	
	@RequestMapping(value="/generateDocNum")
	@ResponseBody
	public TeeJson generateDocNum(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		json.setRtData(docNumService.generateDocNum(loginPerson, sid, runId, flowId));
		return json;
	}
	
	/**
	 * 动态修改文号
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/diynamicEditDocNum")
	@ResponseBody
	public TeeJson diynamicEditDocNum(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int editNum = TeeStringUtil.getInteger(request.getParameter("editNum"), 0);
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		json.setRtData(docNumService.diynamicEditDocNum(loginPerson,runId, flowId,editNum));
		return json;
	}
	
	
	@RequestMapping(value="/checkExistsDocNum")
	@ResponseBody
	public TeeJson checkExistsDocNum(HttpServletRequest request,TeeDataGridModel dm){
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		json.setRtData(docNumService.checkExistsDocNum(runId, flowId));
		return json;
	}
	
	
	@RequestMapping(value="/getCurDocAndMaxDoc")
	@ResponseBody
	public TeeJson getCurDocAndMaxDoc(HttpServletRequest request,TeeDataGridModel dm){
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		json.setRtData(docNumService.getCurDocAndMaxDoc(runId, flowId));
		return json;
	}
	
	@RequestMapping(value="/clear")
	@ResponseBody
	public TeeJson clear(HttpServletRequest request,TeeDataGridModel dm){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		docNumService.clear(sid);
		return json;
	}
	
	
	
	/**
	 * 删除文号日志
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping(value="/delHistoryBySid")
	@ResponseBody
	public TeeJson delHistoryBySid(HttpServletRequest request,TeeDataGridModel dm){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		return docNumService.delHistoryBySid(sid);
	}
}
