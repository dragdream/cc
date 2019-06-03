package com.tianee.oa.subsys.topic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.topic.model.TeeTopicSectionModel;
import com.tianee.oa.subsys.topic.service.TeeTopicSectionService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 论坛版块
 * 
 * @author wyw
 * 
 */
@Controller
@RequestMapping("/TeeTopicSectionController")
public class TeeTopicSectionController {

	@Autowired
	private TeeTopicSectionService service;

	/**
	 * @function: 新建或编辑
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws java.text.ParseException 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws java.text.ParseException  {
		TeeJson json = new TeeJson();
		TeeTopicSectionModel model = new TeeTopicSectionModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		String uuid = (String) requestMap.get("uuid");
		if (TeeUtility.isNullorEmpty(uuid)) {
			json = service.addObj(requestMap, loginPerson, model);
		} else {
			json = service.updateObj(requestMap, loginPerson, model);
		}
		return json;
	}

	/**
	 * @function: 管理列表(普通用户板块浏览)
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getManageInfoList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException  {
		TeeTopicSectionModel model = new TeeTopicSectionModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getManageInfoList(dm, requestMap, loginPerson, model);
	}
	
	/**
	 * @function: 管理列表(管理员-板块浏览)
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getTopicSectionList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getTopicSectionList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException  {
		TeeTopicSectionModel model = new TeeTopicSectionModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getTopicSectionList(dm, requestMap, loginPerson, model);
	}

	/**
	 * @function: 详情
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeTopicSectionModel model = new TeeTopicSectionModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getInfoById(requestMap, loginPerson, model);
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeTopicSectionModel model = new TeeTopicSectionModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		String sids = (String) requestMap.get("sids");
		json = service.deleteObjById(sids);
		return json;
	}
	
	
	
	/**
	 * 获取论坛版块列表
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getTopicSectionList")
	@ResponseBody
	public TeeJson getTopicSectionList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeTopicSectionModel model = new TeeTopicSectionModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		requestMap.put(TeeConst.LOGIN_USER, loginPerson);
		json = service.getTopicSectionList(requestMap, model);
		return json;
	}
	
	

}
