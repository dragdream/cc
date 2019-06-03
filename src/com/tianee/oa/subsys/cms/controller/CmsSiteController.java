package com.tianee.oa.subsys.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.cms.bean.SiteInfo;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsSite")
public class CmsSiteController {
	@Autowired
	private CmsSiteService cmsSiteService;
	
	@ResponseBody
	@RequestMapping("/addSiteInfo")
	public TeeJson addSiteInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		SiteInfo siteInfo = (SiteInfo) TeeServletUtility.request2Object(request, SiteInfo.class);
		cmsSiteService.addSiteInfo(siteInfo);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateSiteInfo")
	public TeeJson updateSiteInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		SiteInfo siteInfo = (SiteInfo) TeeServletUtility.request2Object(request, SiteInfo.class);
		cmsSiteService.updateSiteInfo(siteInfo);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delSiteInfo")
	public TeeJson delSiteInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		SiteInfo siteInfo = (SiteInfo) TeeServletUtility.request2Object(request, SiteInfo.class);
		cmsSiteService.delSiteInfo(siteInfo);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getSiteInfo")
	public TeeJson getSiteInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		json.setRtData(cmsSiteService.getSiteInfo(siteId));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		SiteInfo siteInfo = (SiteInfo) TeeServletUtility.request2Object(request, SiteInfo.class);
		return cmsSiteService.datagrid(dm, siteInfo);
	}
}
