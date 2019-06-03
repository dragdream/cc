package com.tianee.oa.core.workflow.workmanage.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeWorkDestroyServiceInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
 
/**
 * 工作查询
 * @author kakalion
 *
 */
@Controller
@RequestMapping("workDestroy")
public class TeeWorkDestroyController {
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	@Qualifier("teeWorkflowService")
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	@Autowired
	private TeeWorkDestroyServiceInterface workDestroyService;
	
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
		
		return workDestroyService.query(params, loginUser, dataGridModel);
	}
	
	@RequestMapping("/destroy")
	@ResponseBody
	public TeeJson destroy(String runIds){
		TeeJson json = new TeeJson();
		int _runIds[] = TeeStringUtil.parseIntegerArray(runIds);
		int delCount = 0;
		boolean hasExp=false;
		for(int runId:_runIds){
			try{
				workDestroyService.destroy(runId);
				delCount++;
			}catch(Exception e){
				hasExp = true;
				e.printStackTrace();
			}
		}
		
		if(hasExp){
			json.setRtMsg("已销毁"+delCount+"个流程，其中"+(_runIds.length-delCount)+"个流程禁止销毁");
			json.setRtState(false);
		}else{
			json.setRtMsg("成功销毁"+_runIds.length+"个流程");
			json.setRtState(true);
		}
		
		return json;
	}
	
	@RequestMapping("/restore")
	@ResponseBody
	public TeeJson restore(String runIds){
		TeeJson json = new TeeJson();
		int _runIds[] = TeeStringUtil.parseIntegerArray(runIds);
		int restoreCount = 0;
		boolean hasExp=false;
		for(int runId:_runIds){
			try{
				workDestroyService.restore(runId);
				restoreCount++;
			}catch(Exception e){
				hasExp = true;
			}
		}
		
		if(hasExp){
			json.setRtMsg("已还原"+restoreCount+"个流程，其中"+(_runIds.length-restoreCount)+"个流程无法还原");
			json.setRtState(false);
		}else{
			json.setRtMsg("成功还原"+_runIds.length+"个流程");
			json.setRtState(true);
		}
		return json;
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

	public void setWorkDestroyService(TeeWorkDestroyServiceInterface workDestroyService) {
		this.workDestroyService = workDestroyService;
	}
	
	
}
