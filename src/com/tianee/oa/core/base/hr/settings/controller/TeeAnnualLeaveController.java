package com.tianee.oa.core.base.hr.settings.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.settings.model.TeeAnnualLeaveModel;
import com.tianee.oa.core.base.hr.settings.service.TeeAnnualLeaveService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/annualLeaveController")
public class TeeAnnualLeaveController {

	@Autowired
	private TeeAnnualLeaveService service;

	/**
	 * 新增或者更新
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeAnnualLeaveModel model = new TeeAnnualLeaveModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdateService(request, model);
		return json;
	}
	/**
	 * @function: 返回全部数据集合
	 * @author: wyw
	 * @data: 2014年9月4日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getManageList")
	@ResponseBody
	public TeeJson getManageList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = service.getManageList();
		return json;
	}
	
	

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = service.deleteObjById(sidStr);
		return json;
	}
	
	
	
	
	

}
