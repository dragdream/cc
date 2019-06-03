package com.tianee.oa.subsys.crm.core.chance.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.chance.model.TeeCrmChanceModel;
import com.tianee.oa.subsys.crm.core.chance.service.TeeCrmChanceService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/crmChanceController")
public class TeeCrmChanceController {
  
	@Autowired
	private TeeCrmChanceService service;
	
	
	
	/**
	 * 新增或者更新
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeCrmChanceModel model = new TeeCrmChanceModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdateService(request, model);
		return json;
	}

	/**
	 * 客户机会列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getChanceInfoList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getChanceList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeCrmChanceModel model = new TeeCrmChanceModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getChanceList(dm, request, model);
	}

	/**
	 * 根据id获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getChanceById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.getChanceById(sid);
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteSingleChance")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.deleteChanceById(sid);
		return json;
	}
	
	
	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteChances")
	@ResponseBody
	public TeeJson deleteChances(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = service.deleteChance(sidStr);
		return json;
	}
	
	
	/**
	 * 共享机会列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getShareChanceList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getShareChanceList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeCrmChanceModel model = new TeeCrmChanceModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getShareChanceList(dm, request, model);
	}
	
}
