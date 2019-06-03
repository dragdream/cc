package com.tianee.oa.subsys.crm.core.target.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmPersonTargetModel;
import com.tianee.oa.subsys.crm.core.target.service.TeeCrmPersonTargetService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/crmPersonTargetController")
public class TeeCrmPersonTargetController {
  
	@Autowired
	private TeeCrmPersonTargetService service;
	
	/**
	 * 根据年份和部门获取个体目标集合
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getPersonTargetListByDept.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getPersonTargetListByDept(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException,
			java.text.ParseException {
		TeeCrmPersonTargetModel model = new TeeCrmPersonTargetModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getPersonTargetListByDept(dm, request, model);
	}

	/**
	 * 获取某年某部门目标总额  和 部门下个体目标总额
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSumTarget.action")
	@ResponseBody
	public TeeJson getSumTarget(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int year = Integer.parseInt(request.getParameter("year"));
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		json.setRtData(service.getSumTarget(year,deptId));
		json.setRtState(true);
		return json;

	}
	
	/**
	 * 删除部门目标
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletePersonTarget")
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
	@RequestMapping("/getPersonTargetById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.getPersonTargetById(sid);
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
		TeeCrmPersonTargetModel model = new TeeCrmPersonTargetModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdate(request, model);
		return json;
	}
	
	/**
	 * 获取简易模板
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listTeeCrmPersonTargets")
	public TeeEasyuiDataGridJson listTeeCrmPersonTargets(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException  {

		TeeCrmPersonTargetModel model = new TeeCrmPersonTargetModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.listTeeCrmPersonTargets(dm, request, model);
		}
	
}
