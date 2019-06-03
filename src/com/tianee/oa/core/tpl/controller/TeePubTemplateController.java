package com.tianee.oa.core.tpl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.tpl.bean.TeePubTemplate;
import com.tianee.oa.core.tpl.service.TeePubTemplateService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("pubTemplate")
public class TeePubTemplateController {
	@Autowired
	private TeePubTemplateService pubTemplateService;
	
	@ResponseBody
	@RequestMapping("addTemplate")
	public TeeJson addTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePubTemplate pubTemplate = new TeePubTemplate();
		TeeServletUtility.requestParamsCopyToObject(request, pubTemplate);
		pubTemplateService.addTemplate(pubTemplate);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("updateTemplate")
	public TeeJson updateTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePubTemplate pubTemplate = new TeePubTemplate();
		TeeServletUtility.requestParamsCopyToObject(request, pubTemplate);
		pubTemplateService.updateTemplate(pubTemplate);
		json.setRtMsg("修改成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("deleteTemplate")
	public TeeJson deleteTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		pubTemplateService.deleteTemplate(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getTemplate")
	public TeeJson getTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(pubTemplateService.getTemplate(sid));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取简易模板
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listTemplateSimple")
	public TeeJson listTemplateSimple(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(pubTemplateService.listTemplateSimple());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		return pubTemplateService.datagrid(dm, null);
	}

	public void setPubTemplateService(TeePubTemplateService pubTemplateService) {
		this.pubTemplateService = pubTemplateService;
	}
	
	
}
