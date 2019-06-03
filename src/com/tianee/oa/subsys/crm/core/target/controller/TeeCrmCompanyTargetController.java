package com.tianee.oa.subsys.crm.core.target.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmCompanyTargetModel;
import com.tianee.oa.subsys.crm.core.target.service.TeeCrmCompanyTargetService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/crmCompanyTargetController")
public class TeeCrmCompanyTargetController {

	@Autowired
	private TeeCrmCompanyTargetService service;
	
	/**
	 * 获取公司目标列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getCompanyTargetList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getChanceList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeCrmCompanyTargetModel model = new TeeCrmCompanyTargetModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getCompanyTargetList(dm, request, model);
	}
	
	/**
	 * 删除公司目标
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCompanyTarget")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.deleteCompanyTargetById(sid);
		return json;
	}
	
	
	/**
	 * 根据id获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyTargetById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.getCompanyTargetById(sid);
		return json;
	}
	
	/**
	 * 新增或者更新
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeCrmCompanyTargetModel model = new TeeCrmCompanyTargetModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdate(request, model);
		return json;
	}
}
