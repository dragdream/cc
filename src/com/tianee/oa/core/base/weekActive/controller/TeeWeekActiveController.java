package com.tianee.oa.core.base.weekActive.controller;

import java.text.ParseException;
import java.util.Map;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weekActive.model.TeeWeekActiveModel;
import com.tianee.oa.core.base.weekActive.service.TeeWeekActiveService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/weekActiveController")
public class TeeWeekActiveController extends BaseController {
	@Autowired
	private TeeWeekActiveService service;

	/**
	 * 新建或编辑
	 * 
	 * @date 2014年4月26日
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeWeekActiveModel model = new TeeWeekActiveModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		if (model.getSid() <= 0) {
			json = service.addObj(requestMap, loginPerson, model);
		} else {
			json = service.updateObj(requestMap, loginPerson, model);
		}
		return json;

	}
	
	@ResponseBody
	@RequestMapping("/checkHongdong")
	public TeeJson checkHongdong(HttpServletRequest request){
		TeeWeekActiveModel model = new TeeWeekActiveModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.checkHongdong(loginPerson, model);
	}

	/**
	 * 根据日期获取数据
	 * 
	 * @date 2014年5月10日
	 * @author
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getWeekActiveList")
	@ResponseBody
	public TeeJson getWeekActiveList(HttpServletRequest request) throws ParseException {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeWeekActiveModel model = new TeeWeekActiveModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);

		requestMap.put(TeeConst.LOGIN_USER, loginPerson);
		TeeJson json = new TeeJson();
		json = service.getWeekActiveList(requestMap);
		return json;
	}
	
	@RequestMapping("/getWeekActiveList2")
	@ResponseBody
	public TeeJson getWeekActiveList2(HttpServletRequest request) throws ParseException {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = service.getWeekActiveList2(loginPerson);
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2015年2月5日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeWeekActiveModel model = new TeeWeekActiveModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.deleteObjById(requestMap, loginPerson, model);
		return json;
	}

	/**
	 * 获取对象
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年2月6日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeWeekActiveModel model = new TeeWeekActiveModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getInfoById(requestMap, loginPerson, model);
		return json;
	}
	
	
	
	/**
	 * 获取本周  其他领导的事务安排
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getCurrWeekOtherLeaderList")
	@ResponseBody
	public TeeJson getCurrWeekOtherLeaderList(HttpServletRequest request) throws ParseException {
		
		return service.getCurrWeekOtherLeaderList(request);
	}
	
	/**
	 * 获取本周  本人的事务安排
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getCurrWeekMyselfList")
	@ResponseBody
	public TeeJson getCurrWeekMyselfList(HttpServletRequest request) throws ParseException {
		
		return service.getCurrWeekMyselfList(request);
	}

}
