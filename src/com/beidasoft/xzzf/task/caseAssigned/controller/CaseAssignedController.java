package com.beidasoft.xzzf.task.caseAssigned.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.task.caseAssigned.service.CaseAssignedService;
import com.beidasoft.xzzf.task.caseOrder.model.CaseOrderModell;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("caseAssignedController")
public class CaseAssignedController {
	@Autowired
	private CaseAssignedService caseAssignedService;
	
	/**
	 * 通过人员编号查看自己案件进度
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listByPage")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,CaseAppointedInfo queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//通过分页显示用户信息
		long total =  caseAssignedService.getTotal(queryModel);
		@SuppressWarnings("rawtypes")
		List<Map> ss = caseAssignedService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(), queryModel);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(ss);
		
		return dataGridJson;
	}
	
//	@ResponseBody
//	@RequestMapping("listPage")
//	public TeeEasyuiDataGridJson listPage(TeeDataGridModel dataGridModel,CaseAppointedInfo queryModel){
//		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
//		//通过分页显示用户信息
//		List<CaseOrderModell> modelList = new ArrayList<>();
//		dataGridJson.setRows(modelList);
//		return dataGridJson;
//	}
}
