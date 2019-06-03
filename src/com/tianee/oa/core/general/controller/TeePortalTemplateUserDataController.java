package com.tianee.oa.core.general.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.bean.TeePortalTemplate;
import com.tianee.oa.core.general.bean.TeePortalTemplateUserData;
import com.tianee.oa.core.general.service.TeePortalTemplateService;
import com.tianee.oa.core.general.service.TeePortalTemplateUserDataService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teePortalTemplateUserDataController")
public class TeePortalTemplateUserDataController {
	@Autowired
	TeePortalTemplateUserDataService userDataService;
	
	@Autowired
	TeePortalTemplateService templateService;
	
	@RequestMapping("/addPortalTemplateUserData")
	@ResponseBody
	public TeeJson addPortalTemplateUserData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePortalTemplateUserData userData = new TeePortalTemplateUserData();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeePortalTemplate template = templateService.getById(Integer.parseInt(person.getDesktop()));
		userData.setUser(person);
		userData.setPortalData(template.getPortalModel());
		userData.setPortalTemplate(template);
		userDataService.addTemplate(userData);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editPortalTemplateUserData")
	@ResponseBody
	public TeeJson editPortalTemplateUserData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePortalTemplateUserData userData = new TeePortalTemplateUserData();
		TeeServletUtility.requestParamsCopyToObject(request, userData);
		userDataService.updateTemplate(userData);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getTemplateUserData")
	@ResponseBody
	public TeeJson getTemplateUserData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
//		TeePortalTemplateUserData userData = new TeePortalTemplateUserData();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		userData = userDataService.getTemplateUserData(person);
//		Map data = new HashMap();
//		data.put("data", userData.getPortalData());
//		data.put("cols", userData.getPortalTemplate().getCols());
//		json.setRtData(data);
		json.setRtData(person.getDesktop());
		json.setRtState(true);
		json.setRtMsg("获取成功！");
		return json;
	}
	
	@RequestMapping("/updateTemplateUserData")
	@ResponseBody
	public TeeJson updateTemplateUserData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePortalTemplateUserData userData = new TeePortalTemplateUserData();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		userData = userDataService.getTemplateUserData(person);
		String desktop = TeeStringUtil.getString(request.getParameter("desktop"));
        person.setDesktop(desktop);
		//		userData.setPortalData(desktop);
		userData.setUser(person);
		userDataService.updateTemplate(userData);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/reset")
	@ResponseBody
	public TeeJson reset(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		userDataService.reset(person,templateId);
		json.setRtMsg("重置成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/setDefault")
	@ResponseBody
	public TeeJson setDefault(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		userDataService.setDefault(person,templateId);
		json.setRtMsg("重置成功");
		json.setRtState(true);
		return json;		
	}
}