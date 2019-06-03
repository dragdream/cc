package com.tianee.oa.subsys.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.oa.subsys.cms.service.CmsSiteTemplateService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsSiteTemplate")
public class CmsSiteTemplateController {
	
	@Autowired
	private CmsSiteTemplateService siteTemplateService;
	
	
	/**
	 * 获取模板
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTemplate")
	public TeeJson getTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(siteTemplateService.getTemplateModel(sid));
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 创建模板
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createTemplate")
	public TeeJson createTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		SiteTemplateModel siteTemplateModel = (SiteTemplateModel) TeeServletUtility.request2Object(request, SiteTemplateModel.class);
		siteTemplateService.createTemplate(siteTemplateModel);
		json.setRtData(siteTemplateModel.getSid());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 更新模板
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateTemplate")
	public TeeJson updateTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		SiteTemplateModel siteTemplateModel = (SiteTemplateModel) TeeServletUtility.request2Object(request, SiteTemplateModel.class);
		siteTemplateService.updateTemplate(siteTemplateModel);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除模板
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delTemplate")
	public TeeJson delTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		siteTemplateService.delTemplate(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取模板列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listTemplates")
	public TeeEasyuiDataGridJson listTemplates(HttpServletRequest request){
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		return siteTemplateService.listTemplates(siteId);
	}
	
	
	
}
