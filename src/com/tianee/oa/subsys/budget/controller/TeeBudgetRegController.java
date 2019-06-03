package com.tianee.oa.subsys.budget.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.budget.model.TeeBudgetRegModel;
import com.tianee.oa.subsys.budget.service.TeeBudgetRegService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 预算申请
 * @author kakalion
 *
 */
@Controller
@RequestMapping("/budgetRegController")
public class TeeBudgetRegController {
	
	@Autowired
	private TeeBudgetRegService service;
	
	
	
	/**
	 * @function: 新建或编辑
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeBudgetRegModel model = new TeeBudgetRegModel();
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
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getManageInfoList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeBudgetRegModel model = new TeeBudgetRegModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getManageInfoList(dm, requestMap,loginPerson, model);
	}
	
	
	
	
	
	
	/**
	 * @function: 详情
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeBudgetRegModel model = new TeeBudgetRegModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getInfoById(requestMap, loginPerson, model);
		return json;
	}
	
	
	

	/**
	 * @function: 详情
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeBudgetRegModel model = new TeeBudgetRegModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		String sids = (String) requestMap.get("sids");
		json = service.deleteObjById(sids);
		return json;
	}
	
	@RequestMapping("/getCurRegUserInfoAbout")
	@ResponseBody
	public TeeJson getCurRegUserInfoAbout(HttpServletRequest request) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return service.getCurRegUserInfoAbout(loginPerson.getUuid());
	}
	
}
