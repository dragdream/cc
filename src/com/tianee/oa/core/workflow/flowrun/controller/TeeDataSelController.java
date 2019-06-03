package com.tianee.oa.core.workflow.flowrun.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowrun.service.TeeDataSelServiceInterface;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/dataSel")
public class TeeDataSelController {
	
	@Autowired
	private TeeDataSelServiceInterface dataSelService;
	
	/**
	 * 获取数据列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getDataList")
	public TeeEasyuiDataGridJson getDataList(HttpServletRequest request){
		//控件sid
		int itemSid = TeeStringUtil.getInteger(request.getParameter("itemSid"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		Map<String,String> formDatas = TeeServletUtility.getParamMap(request);
		return dataSelService.getDataList(itemSid,runId,formDatas);
	}
	
	/**
	 * 获取数据列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMetaData")
	public TeeJson getMetaData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		//控件sid
		int itemSid = TeeStringUtil.getInteger(request.getParameter("itemSid"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		Map<String,String> formDatas = TeeServletUtility.getParamMap(request);
		json.setRtData(dataSelService.getMetaData(itemSid,runId,formDatas));
		return json;
	}
}
