package com.tianee.oa.core.workflow.flowrun.controller;

import java.io.IOException;
import java.util.List;
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
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/workflow")
public class TeeWorkflowController{
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	
	@Autowired
	@Qualifier("teeWorkflowService")
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private TeeFlowProcessServiceInterface flowProcessService;
	
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeFlowRunServiceInterface flowRunService;
	
	/**
	 * 获取已办结工作
	 * @param request
	 * @param response ToDo tasks
	 */
	@RequestMapping(value="/getHandledWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getHandledWorks(HttpServletRequest request,TeeDataGridModel m){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		
		try{
			gridJson = workflowService.getHandledWorks(requestData,person,m);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gridJson;
	}
	/**
	 * 获取未接收的待办工作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getNoReceivedWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getNoReceivedWorks(HttpServletRequest request,TeeDataGridModel m){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		
		try{
			gridJson = workflowService.getNoReceivedWorks(requestData,person,m);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gridJson;
	}
	/**
	 * 待办工作（！！）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getReceivedWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getReceivedWorks(HttpServletRequest request,TeeDataGridModel m){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		try{
			gridJson = workflowService.getReceivedWorks(requestData,person,m);
		}catch(Exception e){
			e.printStackTrace();
		}
		return gridJson;
	}
	
	
	/**
	 * 获取相关的流程类型ids  flowTypeIds
	 * type=1  代表我的待办   type=2 代表我已办结
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPrivFlowTypeIds")
	@ResponseBody
	public TeeJson getPrivFlowTypes(HttpServletRequest request){
		return  workflowService.getPrivFlowTypeIds(request);
	}
	
	/**
	 * 获取关注工作
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/getConcernedWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getConcernedWorks(HttpServletRequest request,TeeDataGridModel m){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		try{
			gridJson = workflowService.getConcernedWorks(requestData,person,m);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gridJson;
	}
	
	/**
	 * 获取待查阅的工作
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/getViewsWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getViewsWorks(HttpServletRequest request,TeeDataGridModel m){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		try{
			gridJson = workflowService.getViewsWorks(requestData,person,m);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gridJson;
	}
	
	/**
	 * 获取挂起工作
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/getSuspendedWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getSuspendedWorks(HttpServletRequest request,TeeDataGridModel m){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		try{
			gridJson = workflowService.getSuspendedWorks(requestData,person,m);
		}catch(Exception e){
			e.printStackTrace();
		}
		return gridJson;
	}
	
	/**
	 * 获取未办理的工作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getNoHandledWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getNoHandledWorks(HttpServletRequest request,TeeDataGridModel m){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson gridJson = workflowService.getNoHandledWorks(person, m);
		return gridJson;
	}
	
	@RequestMapping(value="/suspend")
	@ResponseBody
	public TeeJson suspend(HttpServletRequest request,TeeDataGridModel m){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		workflowService.suspend(frpSid);
		return json;
	}
	
	@RequestMapping(value="/unsuspend")
	@ResponseBody
	public TeeJson unsuspend(HttpServletRequest request,TeeDataGridModel m){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		workflowService.unsuspend(frpSid);
		return json;
	}
	
	/**
	 * 获取可办理的工作
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getHandleableWorks")
	@ResponseBody
	public TeeJson getHandleableWorks(HttpServletRequest request){
		TeeJson jsonData = new TeeJson();
		jsonData.setRtState(TeeConst.RETURN_OK);
		
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		person = personService.selectByUuid(person.getUuid()+"");
		
		List list = workflowService.getCreatablePrivFlowListModelsByPerson(person);
		jsonData.setRtData(list);
		
		return jsonData;
	}
	
	/**
	 * 获取额外已办结工作
	 * @param request
	 */
	public void getExtraWorksByRunId(HttpServletRequest request){
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		
	}
	
	/**
	 * 快速查询，根据流程名称和流水号
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/quickSearch")
	@ResponseBody
	public TeeJson quickSearch(HttpServletRequest request){
		TeeJson jsonData = new TeeJson();
		jsonData.setRtState(TeeConst.RETURN_OK);
		
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		person = personService.selectByUuid(person.getUuid()+"");
		
		String psnName = TeeStringUtil.getString(request.getParameter("psnName"));
		
		List list = workflowService.quickSearch(psnName,person);
		jsonData.setRtData(list);
		
		return jsonData;
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
		workflowService.exportExcel(params,loginUser,response);
	}
	
	
	/**
	 * 查看工作流程（自动判断并跳转到指定的页面（办理/查看））
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/view")
	public void view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		workflowService.view(request, response);
	}
	
	/**
	 * 自动跳转预览页面
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value="/toView")
	public void toView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		workflowService.toView(request,response);
	}
	
	/**
	 * 自动跳转流程办理界面
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value="/toFlowRun")
	public void toFlowRun(HttpServletRequest request,HttpServletResponse response) throws Exception{
		workflowService.toFlowRun(request,response);
	}
	
	/**
	 * 获取我发起的 并且未删除的流程
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/getMyBeginRunList")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyBeginRunList(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson gridJson = workflowService.getMyBeginRunList(request,person, dm);
		return gridJson;
	}
	
	/**
	 * 撤销
	 * */
	@ResponseBody
	@RequestMapping("/doCheXiao")
    public TeeJson doCheXiao(HttpServletRequest request){
    	return workflowService.doCheXiao(request);
    }
	
	
	/**
	 * 手动创建流程实例
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addFlowRunPrcs")
    public TeeJson addFlowRunPrcs(HttpServletRequest request){
    	return workflowService.addFlowRunPrcs(request);
    }
	
	
	
	/**
	 * 手动修改流程实例状态
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateFlowRunPrcs")
    public TeeJson updateFlowRunPrcs(HttpServletRequest request){
    	return workflowService.updateFlowRunPrcs(request);
    }
	
	
	
	public void setWorkflowService(TeeWorkflowServiceInterface workflowService) {
		this.workflowService = workflowService;
	}
	public void setFlowProcessService(TeeFlowProcessServiceInterface flowProcessService) {
		this.flowProcessService = flowProcessService;
	}
	public void setFlowSortService(TeeFlowSortServiceInterface flowSortService) {
		this.flowSortService = flowSortService;
	}
	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}

	
}
