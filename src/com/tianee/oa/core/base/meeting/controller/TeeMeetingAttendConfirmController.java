package com.tianee.oa.core.base.meeting.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.meeting.model.TeeMeetingAttendConfirmModel;
import com.tianee.oa.core.base.meeting.service.TeeMeetingAttendConfirmService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("TeeMeetingAttendConfirmController")
public class TeeMeetingAttendConfirmController {
	
	@Autowired
	private TeeMeetingAttendConfirmService service;
	
	/**
	 * @function: 新增或者更新
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		
		TeeMeetingAttendConfirmModel model = new TeeMeetingAttendConfirmModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		
		if(model.getSid()>0){
			json = service.updateObj(requestMap, loginPerson, model);
		}else{
			json = service.addObj(requestMap, loginPerson, model);
		}
		return json;
	}
	
	
	/**
	 * @function: 参加会议或缺席
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/updateAttendFlag")
	@ResponseBody
	public TeeJson updateAttendFlag(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeeMeetingAttendConfirmModel model = new TeeMeetingAttendConfirmModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.updateAttendFlag(requestMap, loginPerson, model);
		return json;
	}
	
	/**
	 * @function: 是否确认参会
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/isConfirmFlag")
	@ResponseBody
	public TeeJson isConfirmFlag(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeeMeetingAttendConfirmModel model = new TeeMeetingAttendConfirmModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.isConfirmFlag(requestMap, loginPerson, model);
		return json;
	}
	
	/**
	 * 获取会议参会情况
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月31日
	 * @param request
	 * @return
	 * @throws ParseException TeeJson
	 */
	@RequestMapping("/showMeetingAttendInfo")
	@ResponseBody
	public TeeJson showMeetingAttendInfo(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.showMeetingAttendInfo(requestMap,person);
		return json;
	}
	/**
	 * 获取会议签阅情况
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月31日
	 * @param request
	 * @return
	 * @throws ParseException TeeJson
	 */
	@RequestMapping("/showMeetingReadInfo")
	@ResponseBody
	public TeeJson showMeetingReadInfo(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.showMeetingReadInfo(requestMap,person);
		return json;
	}
	
	
	/**
	 * @function: 更改会议签阅状态
	 * @data: 2015年10月29日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/updateReadFlag")
	@ResponseBody
	public TeeJson updateReadFlag(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeeMeetingAttendConfirmModel model = new TeeMeetingAttendConfirmModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.updateReadFlag(requestMap, loginPerson, model);
		return json;
	}
	/**
	 * @function: 是否确认参会
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/isReadFlag")
	@ResponseBody
	public TeeJson isReadFlag(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeeMeetingAttendConfirmModel model = new TeeMeetingAttendConfirmModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.isReadFlag(requestMap, loginPerson, model);
		return json;
	}
	

}
