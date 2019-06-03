package com.tianee.oa.core.workflow.workmanage.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
 
/**
 * 工作查询
 * @author kakalion
 *
 */
@Controller
@RequestMapping("workQuery")
public class TeeWorkQueryController {
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	@Qualifier("teeWorkflowService")
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	@Autowired
	private TeeWorkQueryServiceInterface workQueryService;
	
	@Autowired
	private TeeFlowRunServiceInterface flowRunService;
	
	/**
	 * 获取流程分类以及分类下的可执行流程（用于Select选择框数据）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHandableFlowType2SelectCtrl")
	@ResponseBody
	public TeeJson getHandableFlowType2SelectCtrl(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json.setRtData(workflowService.getHandableFlowType2SelectCtrl(person,requestMap));
		
		
		return json;
	}
	
	/**
	 * 获取流程分类以及分类下的可执行流程（用于Select选择框数据）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowType2SelectCtrl")
	@ResponseBody
	public TeeJson getFlowType2SelectCtrl(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(workflowService.getFlowType2SelectCtrl());
		return json;
	}
	
	
	/**
	 * 获取guding固定流程分类以及分类下的可执行流程（用于Select选择框数据）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFixedFlowType2SelectCtrl")
	@ResponseBody
	public TeeJson getFixedFlowType2SelectCtrl(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(workflowService.getFixedFlowType2SelectCtrl());
		return json;
	}
	
	/**
	 * 我的待办  我已办结  选择所属流程  只展现相关的流程  无关的显示出来
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPrivFlowTypes")
	@ResponseBody
	public TeeJson getPrivFlowTypes(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(workflowService.getPrivFlowTypes(request));
		return json;
	}
	
	
	@RequestMapping("/query")
	@ResponseBody
	public TeeEasyuiDataGridJson query(HttpServletRequest request,TeeDataGridModel dataGridModel){
		
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(String.valueOf(loginUser.getUuid()));
		
		Enumeration<String> enumer = request.getParameterNames();
		Map params = new HashMap();
		String key;
		while(enumer.hasMoreElements()){
			key = enumer.nextElement();
			params.put(key, request.getParameter(key));
		}
		
		TeeEasyuiDataGridJson dataGridJson = null;
		try{
			dataGridJson = workQueryService.query(params, loginUser, dataGridModel);
		}catch(Exception e){
			e.printStackTrace();
			dataGridJson = new TeeEasyuiDataGridJson();
			dataGridJson.setRows(new ArrayList());
			dataGridJson.setTotal(Long.parseLong("0"));
		}
		
		return dataGridJson;
	}
	

	/**
	 * 工作查询  选择所属流程  获取有权限查询的流程类型ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHasQueryPrivFlowTypeIds")
	@ResponseBody
	public TeeJson getHasQueryPrivFlowTypeIds(HttpServletRequest request){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(String.valueOf(loginUser.getUuid()));
		
		Enumeration<String> enumer = request.getParameterNames();
		Map params = new HashMap();
		String key;
		while(enumer.hasMoreElements()){
			key = enumer.nextElement();
			params.put(key, request.getParameter(key));
		}
		return  workQueryService.getHasQueryPrivFlowTypeIds(params, loginUser);
	}
	
	
	/**
	 * 获取流程可以恢复的步骤
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRecoverFlowRunPrcsList")
	@ResponseBody
	public TeeEasyuiDataGridJson getRecoverFlowRunPrcsList(HttpServletRequest request){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(String.valueOf(loginUser.getUuid()));
		
		Enumeration<String> enumer = request.getParameterNames();
		Map params = new HashMap();
		String key;
		while(enumer.hasMoreElements()){
			key = enumer.nextElement();
			params.put(key, request.getParameter(key));
		}
		return  workQueryService.getRecoverFlowRunPrcsList(params, loginUser);
	
	}
	
	
	/**
	 * 流程恢复
	 * @param request
	 * @return
	 */
	@RequestMapping("/recover")
	@ResponseBody
	public TeeJson recover(HttpServletRequest request){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(String.valueOf(loginUser.getUuid()));
		
		Enumeration<String> enumer = request.getParameterNames();
		Map params = new HashMap();
		String key;
		while(enumer.hasMoreElements()){
			key = enumer.nextElement();
			params.put(key, request.getParameter(key));
		}
		return  workQueryService.recover(params, loginUser);
	
	}
	
	
	
	/**
	 * 导出excel表格
	 * @param request
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
	    //获取当前登录的用户
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(String.valueOf(loginUser.getUuid()));
		
		String para=request.getParameter("params");
		Map params = TeeJsonUtil.JsonStr2Map(para);
		workQueryService.exportExcel(params,loginUser,response);
	}
	
	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}

	public void setWorkflowService(TeeWorkflowServiceInterface workflowService) {
		this.workflowService = workflowService;
	}

	public void setFlowSortService(TeeFlowSortServiceInterface flowSortService) {
		this.flowSortService = flowSortService;
	}

	public void setFlowRunService(TeeFlowRunServiceInterface flowRunService) {
		this.flowRunService = flowRunService;
	}

	public TeeFlowRunServiceInterface getFlowRunService() {
		return flowRunService;
	}
	
	
}
