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
import com.tianee.oa.subsys.topic.model.TeeTopicModel;
import com.tianee.oa.subsys.topic.service.TeeTopicService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 论坛话题
 * 
 * @author wyw
 * 
 */
@Controller
@RequestMapping("/TeeTopicController")
public class TeeTopicController {

	@Autowired
	private TeeTopicService service;

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
		TeeTopicModel model = new TeeTopicModel();
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
	 * @function: 管理列表
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
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getManageInfoList(dm, requestMap, loginPerson, model);
	}
	
	/**
	 * @function: 我的帖子
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getMyTopicList")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyTopicList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException  {
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getMyTopicList(dm, requestMap, loginPerson, model);
	}
	

	/**
	 * @function: 最新帖子
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getLatelyTopicList")
	@ResponseBody
	public TeeEasyuiDataGridJson getLatelyTopicList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException  {
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getLatelyTopicList(dm, requestMap, loginPerson, model);
	}
	
	/**
	 * @function: 周热门帖
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getWeekTopicList")
	@ResponseBody
	public TeeEasyuiDataGridJson getWeekTopicList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException  {
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getWeekTopicList(dm, requestMap, loginPerson, model);
	}
	/**
	 * @function: 月热门贴
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getMonthTopicList")
	@ResponseBody
	public TeeEasyuiDataGridJson getMonthTopicList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException  {
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getMonthTopicList(dm, requestMap, loginPerson, model);
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
		TeeTopicModel model = new TeeTopicModel();
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
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		String sids = (String) requestMap.get("sids");
		json = service.deleteObjById(sids);
		return json;
	}
	
	/**
	 * 设置查看数
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/updateClickCount")
	@ResponseBody
	public TeeJson updateClickCount(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.updateClickCount(requestMap,loginPerson,model);
		return json;
	}
	/**
	 * 设置置顶
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/setTopById")
	@ResponseBody
	public TeeJson setTopById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeTopicModel model = new TeeTopicModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.setTopById (requestMap,loginPerson,model);
		return json;
	}
	
	

}
