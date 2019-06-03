package com.tianee.oa.core.workflow.statistic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.statistic.service.TeeFlowStatisticServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/flowStatistic")
public class TeeFlowStatisticController {
	
	@Autowired
	private TeeFlowStatisticServiceInterface flowStatisticService;
	
	/**
	 * 超时图表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/timeoutStatistic")
	public TeeJson timeoutStatisticGraphics(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		json.setRtState(true);
		json.setRtData(flowStatisticService.timeoutStatistic(requestData));
		return json;
	}
	
	/**
	 * 处理情况
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/handleStatistic")
	public TeeJson handleStatistic(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		json.setRtState(true);
		json.setRtData(flowStatisticService.handleStatistic(requestData));
		return json;
	}
	
	/**
	 * 办理情况
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/handle0Statistic")
	public TeeJson handle0Statistic(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		json.setRtState(true);
		json.setRtData(flowStatisticService.handle0Statistic(requestData));
		return json;
	}
	
	
	
	/**
	 * 获取统计的流程数据
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/getStatisticFlowData")
	@ResponseBody
	public TeeEasyuiDataGridJson getStatisticFlowData(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		
		try{
			gridJson = flowStatisticService.getStatisticFlowData(requestData,loginUser,dm);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gridJson;
	}
	
}
