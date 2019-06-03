package com.beidasoft.xzzf.inspection.plan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionTask;
import com.beidasoft.xzzf.inspection.plan.model.InspectionTaskModel;
import com.beidasoft.xzzf.inspection.plan.service.InspectionTaskService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("inspectionTask")
public class InspectionTaskController {
	@Autowired
	InspectionTaskService inspectionTaskService;
	/**
	 * 根据计划id查询
	 * @param id
	 * @return
	 */
	@RequestMapping("getObjById")
	@ResponseBody
	public InspectionTaskModel getObjById(HttpServletRequest request,String id){
		return inspectionTaskService.getObjById(id);
	}
	/**
	 * 修改计划
	 * @param inspectionTaskModel
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public TeeJson update(HttpServletRequest request,InspectionTaskModel inspectionTaskModel){
		return inspectionTaskService.update(inspectionTaskModel);
	}
	
	/**
	 * 分页查询
	 * @param inspectionId
	 * @param dataGridModel
	 * @return
	 */
	@RequestMapping("taskListByPage")
	@ResponseBody
	public TeeEasyuiDataGridJson taskListByPage(HttpServletRequest request,InspectionTaskModel taskModel,TeeDataGridModel dataGridModel){
		return inspectionTaskService.taskListByPage(taskModel, dataGridModel.getFirstResult(), dataGridModel.getRows());
	}
	
	/**
	 * 随机分配
	 * @param inspectionId
	 * @return
	 */
	@RequestMapping("assignmentTask")
	@ResponseBody
	public TeeJson assignmentTask(HttpServletRequest request,String inspectionId){
		return inspectionTaskService.assignmentTask(inspectionId);
	}
}
