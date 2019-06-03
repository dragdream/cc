package com.beidasoft.xzzf.inspection.plan.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.plan.model.InspectionPlanBaseModel;
import com.beidasoft.xzzf.inspection.plan.service.InspectionPlanBaseService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/inspectionPlanController")
public class InspectionPlanBaseController {
	
	@Autowired
	private TeeBaseUpload baseUpload;
	@Autowired
	private TeeAttachmentService attachmentService;
	@Autowired
	private InspectionPlanBaseService inspectionPlanService;
	
	@RequestMapping("/table")
	@ResponseBody
	public TeeEasyuiDataGridJson table(TeeDataGridModel dm, HttpServletRequest request) {
		return inspectionPlanService.getInspectionPlanBaseOfPage(dm, request);
	}
	
	/**
	 * 增加
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson save(InspectionPlanBaseModel model, HttpServletRequest request) {
		return inspectionPlanService.save(model, request);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(InspectionPlanBaseModel model, HttpServletRequest request){
		return inspectionPlanService.updateInspectionPlanBase(model);
	}
	
	/**
	 * 根据id获取
	 * @param id
	 * @return TeeJson
	 */
	@RequestMapping("/get")
	@ResponseBody
	public TeeJson get(String id, HttpServletRequest request) {
		return inspectionPlanService.get(id);
	}
	
	/**
	 * 批量修改状态
	 * @param ids
	 * @param num
	 * @param param
	 * @return TeeJson
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public TeeJson updateStatus(String ids, String num, String param, HttpServletRequest request) {
		return inspectionPlanService.updateStatus(ids, Integer.parseInt(num), param);
	}
	
	/**
	 * 根据标题模糊查询
	 * @param title
	 * @return TeeJson
	 */
	@RequestMapping("/listByTitle")
	@ResponseBody
	public TeeJson listByTitle(String title, HttpServletRequest request) {
		return inspectionPlanService.listByTitle(title);
	}
}
