package com.tianee.oa.mobile.meeting.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.meeting.service.TeeMobileMeetingAttendConfirmService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/mobileMeetingAttendConfirm")
public class TeeMobileMeetingAttendConfirmController {

	@Autowired
	 private TeeMobileMeetingAttendConfirmService mobileMeetingAttendConfirmService;

	/**
	 * 根据状态获取会议参会情况
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月31日
	 * @param request
	 * @return
	 * @throws ParseException TeeJson
	 */
	@RequestMapping("/showMeetingAttendInfoByStatus")
	@ResponseBody
	public TeeJson showMeetingAttendInfoByStatus(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = mobileMeetingAttendConfirmService.showMeetingAttendInfoByStatus(requestMap,person);
		return json;
	}


}
