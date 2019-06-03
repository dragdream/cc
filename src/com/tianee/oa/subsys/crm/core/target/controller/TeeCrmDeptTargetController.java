package com.tianee.oa.subsys.crm.core.target.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmDeptTargetModel;
import com.tianee.oa.subsys.crm.core.target.service.TeeCrmDeptTargetService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/crmDeptTargetController")
public class TeeCrmDeptTargetController {

	@Autowired
	private TeeCrmDeptTargetService service;

	/**
	 * 部门机会列表 按年分
	 * 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getDeptTargetList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getDeptTargetList(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException,
			java.text.ParseException {
		TeeCrmDeptTargetModel model = new TeeCrmDeptTargetModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getDeptTargetList(dm, request, model);
	}

	/**
	 * 获取某年公司的目标总额  和 部门的目标总额
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSumTarget.action")
	@ResponseBody
	public TeeJson getSumTarget(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int year = Integer.parseInt(request.getParameter("year"));
		json.setRtData(service.getSumTarget(year));
		json.setRtState(true);
		return json;

	}
	
	/**
	 * 删除部门目标
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteDeptTarget")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.deleteTargetById(sid);
		return json;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeptTargetById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.getDeptTargetById(sid);
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
		TeeCrmDeptTargetModel model = new TeeCrmDeptTargetModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdate(request, model);
		return json;
	}
}
