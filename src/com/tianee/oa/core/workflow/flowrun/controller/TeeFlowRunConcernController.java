package com.tianee.oa.core.workflow.flowrun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunConcernServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/flowRunConcern")
public class TeeFlowRunConcernController{
	@Autowired
	TeeFlowRunConcernServiceInterface flowRunConcernService;
	
	/**
	 * 允许关注
	 * @param request
	 * @return
	 */
	@RequestMapping("/concern")
	@ResponseBody
	public TeeJson concern(HttpServletRequest request){
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		flowRunConcernService.concern(runId, loginPerson.getUuid());
		
		json.setRtMsg("已关注该工作");
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 取消关注
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancelConcern")
	@ResponseBody
	public TeeJson cancelConcern(HttpServletRequest request){
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		flowRunConcernService.cancelConcern(runId, loginPerson.getUuid());
		
		json.setRtMsg("已取消对该工作的关注");
		json.setRtState(true);
		
		return json;
	}
	
}
