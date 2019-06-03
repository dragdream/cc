package com.tianee.oa.subsys.mymodule.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.mymodule.service.MyModuleService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/myModule")
public class MyModuleController {
	
	@Autowired
	private MyModuleService myModuleService;
	
	@ResponseBody
	@RequestMapping("/dflist")
	public TeeEasyuiDataGridJson dflist(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		return myModuleService.dflist(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public TeeJson saveOrUpdate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"));
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		myModuleService.saveOrUpdate(TeeServletUtility.getParamMap(request), loginUser, moduleId);
		json.setRtMsg("保存成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"));
		String bisKey = TeeStringUtil.getString(request.getParameter("bisKey"));
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		myModuleService.delete(loginUser, moduleId, bisKey);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getFormHtml")
	public TeeJson getFormHtml(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"));
		String bisKey = TeeStringUtil.getString(request.getParameter("bisKey"));
		int view = TeeStringUtil.getInteger(request.getParameter("view"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(myModuleService.getFormHtml(loginUser, moduleId, bisKey,view));
		json.setRtState(true);
		return json;
	}
}
