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
import com.tianee.oa.subsys.budget.model.TeeDeptBudgetModel;
import com.tianee.oa.subsys.budget.service.TeeDeptBudgetService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/deptBudgetController")
public class TeeDeptBudgetController {
	@Autowired
	private TeeDeptBudgetService service;

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
		TeeDeptBudgetModel model = new TeeDeptBudgetModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		String monthId01 = (String) requestMap.get("monthId_01");
		if (TeeUtility.isNullorEmpty(monthId01)) {
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
		TeeDeptBudgetModel model = new TeeDeptBudgetModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return service.getManageInfoList(dm, requestMap, model);
	}

	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeDeptBudgetModel model = new TeeDeptBudgetModel();
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
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeDeptBudgetModel model = new TeeDeptBudgetModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.deleteObjById(requestMap, loginPerson, model);
		return json;
	}
	
	
	/**
	 * @function: 校验是否已存在
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/checkExist")
	@ResponseBody
	public TeeJson checkExist(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeDeptBudgetModel model = new TeeDeptBudgetModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		
		json = service.checkExist(requestMap, loginPerson, model);
		return json;
	}
	
	/**
	 * 
	 * @function: 获取部门预算剩余金额
	 * @author: wyw
	 * @data: 2014年9月7日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getDeptBudgetCost")
	@ResponseBody
	public TeeJson getDeptBudgetCost(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeDeptBudgetModel model = new TeeDeptBudgetModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		int deptId = TeeStringUtil.getInteger((String)requestMap.get("deptId"), 0);
		json = service.getDeptBudgetCost(requestMap, deptId);
		return json;
	}
	
	

}
