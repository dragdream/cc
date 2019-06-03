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

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowInfoChartServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
/**
 * 
 * @author zhp
 * @createTime 2013-10-1
 * @desc
 */
@Controller
@RequestMapping("/flowInfoChar")
public class TeeFlowInfoCharController {

	@Autowired
	private TeeFlowInfoChartServiceInterface chartService;
	
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	
	@Autowired
	private TeeFlowRunServiceInterface flowRunService;
	
	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-1
	 * @editTime 下午07:00:06
	 * @desc
	 */
	@RequestMapping(value="/getCharDate")
	@ResponseBody
	public TeeJson getFlowInfoCharModelList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		List list = chartService.loadAllPrcsInfo(runId);
		json.setRtData(list);
		json.setRtMsg("获取数据成功!");
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取流程可视化图形数据，即取出FlowRunPrcs步骤实例
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFlowRunViewGraphicsData")
	@ResponseBody
	public TeeJson getFlowRunViewGraphicsData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeeFlowRun fr = flowRunService.get(runId);
		if(fr==null){
			throw new TeeOperationException("流程不存在或已删除");
		}
		
		json.setRtState(true);
		Map datas = new HashMap();
		datas.put("prcsList", flowRunPrcsService.getFlowRunPrcsModelList(runId));
		datas.put("runName", fr.getRunName());
		datas.put("runId", fr.getRunId());
		datas.put("type", fr.getFlowType().getType());
		if(null!=fr.getEndTime()){
			datas.put("isEnd", true);
		}else{
			datas.put("isEnd", false);
		}
		json.setRtData(datas);
		return json;
	}
	
	/**
	 * 获取子流程数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFlowRunChildData")
	@ResponseBody
	public TeeJson getFlowRunChildData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
//		List<TeeFlowRun> list = flowRunService.getChildFlowRuns(runId);
//		List datas = new ArrayList();
//		TeeFlowRunPrcs flowRunPrcs = null;
//		for(TeeFlowRun fr:list){
//			Map dataItem = new HashMap();
//			dataItem.put("runName", fr.getRunName());
//			dataItem.put("runId", fr.getRunId());
//			dataItem.put("flowId", fr.getFlowType().getSid());
//			dataItem.put("pFlowPrcsId", fr.getPFlowPrcsId());
//			dataItem.put("beginUserName", fr.getBeginPerson().getUserName());
//			dataItem.put("beginTime", TeeDateUtil.format(fr.getBeginTime()));
//			dataItem.put("endTime", fr.getEndTime()==null?null:fr.getEndTime());
//			datas.add(dataItem);
//		}
		
		json.setRtState(true);
		json.setRtData(chartService.getFlowRunChildData(runId));
		return json;
	}
	
	public TeeFlowInfoChartServiceInterface getChartService() {
		return chartService;
	}

	public void setChartService(TeeFlowInfoChartServiceInterface chartService) {
		this.chartService = chartService;
	}

	public void setFlowRunPrcsService(TeeFlowRunPrcsServiceInterface flowRunPrcsService) {
		this.flowRunPrcsService = flowRunPrcsService;
	}

	public TeeFlowRunPrcsServiceInterface getFlowRunPrcsService() {
		return flowRunPrcsService;
	}

	public void setFlowRunService(TeeFlowRunServiceInterface flowRunService) {
		this.flowRunService = flowRunService;
	}

	public TeeFlowRunServiceInterface getFlowRunService() {
		return flowRunService;
	}
	
	
}
