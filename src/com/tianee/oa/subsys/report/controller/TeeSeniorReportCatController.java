package com.tianee.oa.subsys.report.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.report.bean.TeeSeniorReportCat;
import com.tianee.oa.subsys.report.service.TeeSeniorReportCatService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/seniorReportCat")
public class TeeSeniorReportCatController {
	
	@Autowired
	private TeeSeniorReportCatService reportCatService;
	
	@ResponseBody
	@RequestMapping("/add")
	public TeeJson add(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeSeniorReportCat reportCat = 
				(TeeSeniorReportCat) TeeServletUtility.request2Object(request, TeeSeniorReportCat.class);
		reportCatService.add(reportCat);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		reportCatService.delete(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeSeniorReportCat reportCat = 
				(TeeSeniorReportCat) TeeServletUtility.request2Object(request, TeeSeniorReportCat.class);
		reportCatService.update(reportCat);
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(reportCatService.get(sid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		return reportCatService.datagrid(requestData, dm);
	}
	
	
	/**
	 * 获取所有的报表分类  不分页
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllCat")
	public TeeJson getAllCat(HttpServletRequest request){
		return reportCatService.getAllCat(request);
	}
}
