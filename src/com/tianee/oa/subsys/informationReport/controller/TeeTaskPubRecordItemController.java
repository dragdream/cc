package com.tianee.oa.subsys.informationReport.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.informationReport.model.TeeTaskPubRecordItemModel;
import com.tianee.oa.subsys.informationReport.service.TeeTaskPubRecordItemService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeTaskPubRecordItemController")
public class TeeTaskPubRecordItemController {
	
	@Autowired
	private TeeTaskPubRecordItemService  taskPubRecordItemService;
	
	
	/**
	 * 获取我的上报列表    0=待上报    1=已上报
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMyReport")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyPubTask(TeeDataGridModel dm, HttpServletRequest request,TeeTaskPubRecordItemModel models){
		return taskPubRecordItemService.getMyReport(dm, request,models);
	}
	
	

	/**
	 * 汇报
	 * @param request
	 * @return
	 */
	@RequestMapping("/report")
	@ResponseBody
	public TeeJson report(HttpServletRequest request){	
		return taskPubRecordItemService.report(request);
	}
	
	
	
	/**
	 * 根据任务发布记录项的主键    获取发布数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRepDataByRecordItemId")
	@ResponseBody
	public TeeJson getRepDataByRecordItemId(HttpServletRequest request){	
		return taskPubRecordItemService.getRepDataByRecordItemId(request);
	}
	
	
	
	/**
	 * 获取历史汇报列表(我的)
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHistoryReportList")
	@ResponseBody
	public TeeJson getHistoryReportList(HttpServletRequest request){	
		return taskPubRecordItemService.getHistoryReportList(request);
	}
	
	
	/**
	 * 获取历史汇报列表(根据上报人人主键)
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHistoryReportListByUserId")
	@ResponseBody
	public TeeJson getHistoryReportListByUserId(HttpServletRequest request){	
		return taskPubRecordItemService.getHistoryReportListByUserId(request);
	}
	
	
	/**
	 * 根据任务发布记录   获取发布数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRepDataListByRecordId")
	@ResponseBody
	public TeeEasyuiDataGridJson getRepDataListByRecordId(TeeDataGridModel dm, HttpServletRequest request){
		return taskPubRecordItemService.getRepDataListByRecordId(dm, request);
	}
}
