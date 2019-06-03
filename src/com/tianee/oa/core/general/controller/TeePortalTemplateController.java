package com.tianee.oa.core.general.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.bean.TeePortalTemplate;
import com.tianee.oa.core.general.service.TeePortalTemplateService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teePortalTemplateController")
public class TeePortalTemplateController {
	@Autowired
	TeePortalTemplateService templateService;
	
	@Autowired
	TeePersonService personService;
	
	@RequestMapping("/addPortalTemplate")
	@ResponseBody
	public TeeJson addPortalTemplate(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePortalTemplate template = new TeePortalTemplate();
		TeeServletUtility.requestParamsCopyToObject(request, template);
		templateService.addPortalTemplate(template);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editPortalTemplate")
	@ResponseBody
	public TeeJson editPortalTemplate(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		//TeePortalTemplate template = new TeePortalTemplate();
		//TeeServletUtility.requestParamsCopyToObject(request, template);
		//templateService.editPortalTemplate(template);
		String portalModel=request.getParameter("portalModel");
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		person.setDesktop(portalModel);
		personService.updatePerson(person);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delPortalTemplate")
	@ResponseBody
	public TeeJson delPortalTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeePortalTemplate template = new TeePortalTemplate();
		template.setSid(sid);
		templateService.delPortalTemplate(template);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getPortalTemplate")
	@ResponseBody
	public TeeJson getPortalTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		//int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//TeePortalTemplate template = templateService.getById(sid);
		//json.setRtData(template);
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//System.out.println(person.getDesktop());
		json.setRtData(person.getDesktop());
		json.setRtMsg("获取成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		int type = TeeStringUtil.getInteger(request.getParameter("type"),0);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return templateService.datagrid(dm, requestDatas,type);
	}
	
	/**
	 * 获取当前登录人有权限的模板信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getTemplateList")
	@ResponseBody
	public TeeJson getTemplateList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = new TeePerson();
		int uuid = TeeStringUtil.getInteger(request.getParameter("uuid"),0);
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"),0);
		if(uuid>0){
			person = personService.getPersonByUuids(""+uuid).get(0);
		}else{
			person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		}
		List<TeePortalTemplate> list = templateService.getTemplateList(person,deptId);
		json.setRtData(list);
		json.setRtMsg("获取成功");
		json.setRtState(true);
		return json;		
	}
	
}
