package com.tianee.oa.core.workflow.flowrun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunLogServiceInterface;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/flowRunLog")
public class TeeFlowRunLogController {
	
	@Autowired
	private TeeFlowRunLogServiceInterface flowRunLogService;
	
	/**
	 * 获取流程日志
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowRunLogs")
	@ResponseBody
	public TeeJson getFlowRunLogs(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		json.setRtData(flowRunLogService.getFlowRunLogs(runId));
		return json;
	}

	public void setFlowRunLogService(TeeFlowRunLogServiceInterface flowRunLogService) {
		this.flowRunLogService = flowRunLogService;
	}

	public TeeFlowRunLogServiceInterface getFlowRunLogService() {
		return flowRunLogService;
	}
}
