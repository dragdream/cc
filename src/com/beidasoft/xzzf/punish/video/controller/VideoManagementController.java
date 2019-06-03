package com.beidasoft.xzzf.punish.video.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.plan.model.InspectionPlanBaseModel;
import com.beidasoft.xzzf.inspection.plan.service.InspectionPlanBaseService;
import com.beidasoft.xzzf.punish.video.model.VideoManagementModel;
import com.beidasoft.xzzf.punish.video.service.VideoManagementService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/videoManagementController")
public class VideoManagementController {
	
	@Autowired
	private TeeBaseUpload baseUpload;
	@Autowired
	private TeeAttachmentService attachmentService;
	@Autowired
	private VideoManagementService videoManagementService;
	
	@RequestMapping("/table")
	@ResponseBody
	public TeeEasyuiDataGridJson table(TeeDataGridModel dm, String baseId, HttpServletRequest request) {
		return videoManagementService.getVideoManagementOfPage(dm, baseId, request);
	}
	
	/**
	 * 增加
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson save(VideoManagementModel model, HttpServletRequest request) {
		return videoManagementService.save(model, request);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(VideoManagementModel model, HttpServletRequest request){
		return videoManagementService.updateVideoManagement(model);
	}
	
	/**
	 * 根据id获取
	 * @param id
	 * @return TeeJson
	 */
	@RequestMapping("/get")
	@ResponseBody
	public TeeJson get(String id, HttpServletRequest request) {
		return videoManagementService.get(id);
	}
	
	/**
	 * 判断附件是否是mp4格式
	 * @param ids
	 * @return TeeJson
	 */
	@RequestMapping("/isMP4")
	@ResponseBody
	public TeeJson isMP4(String ids, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json.setRtState(true);
		List<TeeAttachment> attachs = attachmentService.getAttachmentsByIds(ids);
		for (int i = 0; i < attachs.size(); i++) {
			if(attachs.get(i).getExt().toLowerCase().equals("mp4")){
				json.setRtState(false);
			}
		}
		return json;
	}
	
}
