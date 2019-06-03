package com.tianee.oa.mobile.meeting.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.mobile.meeting.service.TeeMobileMeetingService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/mobileMeetingManage")
public class TeeMobileMeetingController {

	@Autowired
	 private TeeMobileMeetingService mobileMeetingService;
	
	
	/**
	 * 获取所有由当前登陆人审批的会议
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getLeaderMeetAllStatus.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getLeaderMeetAllStatus(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return mobileMeetingService.getLeaderMeetAllStatus(dm,request , model);
	}
	
	
	/**
	 * 获取所有由当前登陆人申请的或者参与的会议（状态为：已批准   进行中   已结束）
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getMyMeetByStatus.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyMeetByStatus(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return mobileMeetingService.getMyMeetByStatus(dm,request , model);
	}
	
	
}
