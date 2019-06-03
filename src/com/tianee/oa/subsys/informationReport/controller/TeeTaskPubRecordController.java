package com.tianee.oa.subsys.informationReport.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.informationReport.service.TeeTaskPubRecordService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("/TeeTaskPubRecordController")
public class TeeTaskPubRecordController {
	@Autowired
	private TeeTaskPubRecordService  taskPubRecordService;
	
	
	
	/**
	 * 根据任务模板主键  获取任务发布记录 汇总
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRecordListByTaskTemplateId")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyPubTask(TeeDataGridModel dm, HttpServletRequest request){
		return taskPubRecordService.getRecordListByTaskTemplateId(dm, request);
	}
	
	
}
