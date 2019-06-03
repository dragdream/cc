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
import com.tianee.oa.subsys.topic.model.TeeTopicReplyModel;
import com.tianee.oa.subsys.topic.service.TeeTopicReplyService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 论坛话题回复
 * 
 * @author wyw
 * 
 */
@Controller
@RequestMapping("/TeeTopicReplyController")
public class TeeTopicReplyController {

	@Autowired
	private TeeTopicReplyService service;

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
	public TeeJson addOrUpdate(HttpServletRequest request) throws java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeTopicReplyModel model = new TeeTopicReplyModel();
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
	 * @function: 我的帖子管理列表
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
	@RequestMapping("/getManageInfoList")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException {
		TeeTopicReplyModel model = new TeeTopicReplyModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getManageInfoList(dm, requestMap, loginPerson, model);
	}
	
	/**
	 * @function: 最新帖子管理列表
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
	@RequestMapping("/getLatelyTopicList")
	@ResponseBody
	public TeeEasyuiDataGridJson getLatelyTopicList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException {
		TeeTopicReplyModel model = new TeeTopicReplyModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getLatelyTopicList(dm, requestMap, loginPerson, model);
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
		TeeTopicReplyModel model = new TeeTopicReplyModel();
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
		TeeTopicReplyModel model = new TeeTopicReplyModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.deleteObjById(requestMap,loginPerson,model);
		return json;
	}
	
	
	
	/**
	 * 根据话题获取回复内容数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getTopicReplyList")
	@ResponseBody
	public TeeJson getTopicReplyList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeTopicReplyModel model = new TeeTopicReplyModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		requestMap.put(TeeConst.LOGIN_USER, loginPerson);
		json = service.getTopicReplyList(requestMap, model);
		return json;
	}
	
	
	/**
	 * 获取回复内容
	 * @function: 
	 * @author: wyw
	 * @data: 2015年8月19日
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getTopicReplyPage")
	@ResponseBody
	public TeeEasyuiDataGridJson getTopicReplyPage(TeeDataGridModel dm, HttpServletRequest request) {
		TeeTopicReplyModel model = new TeeTopicReplyModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getTopicReplyPage(dm, requestMap, model);
	}
	
	
	

}
