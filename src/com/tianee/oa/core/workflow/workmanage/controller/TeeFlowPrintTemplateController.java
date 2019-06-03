package com.tianee.oa.core.workflow.workmanage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessService;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPrintTemplate;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/flowPrintTemplate")
public class TeeFlowPrintTemplateController {
	@Autowired
	private TeeFlowPrintTemplateServiceInterface flowPrintTemplateService;
	


	/**
	 * 新增或者更新
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateModul")
	@ResponseBody
	public TeeJson addOrUpdate(TeeFlowPrintTemplateModel model, HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeeFlowPrintTemplate tpl = flowPrintTemplateService.addOrUpdateTpl(model, request);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtState(false);
		}
		return json;
	}
	
	
	/**
	 * 更新设计模版
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateModulDesigner")
	@ResponseBody
	public TeeJson updateModulDesigner(TeeFlowPrintTemplateModel model, HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeeFlowPrintTemplate tpl = flowPrintTemplateService.updateModulDesigner(model, request);
			if(tpl == null){
				json.setRtState(false);
				json.setRtMsg("更新失败！");
			}
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtState(false);
		}
		return json;
	}
	
	
	/**
	 * 更新更换模版
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateModul")
	@ResponseBody
	public TeeJson updateModul(TeeFlowPrintTemplateModel model, HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeeFlowPrintTemplate tpl = flowPrintTemplateService.updateModul(model, request);
			if(tpl == null){
				json.setRtState(false);
				json.setRtMsg("更新失败！");
			}
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 根据流程获取所有打印模版
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectModulByFlowType")
	@ResponseBody
	public TeeJson selectModulByFlowType( HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String flowTypeId = request.getParameter("flowTypeId");
		try {
			List<TeeFlowPrintTemplateModel> modelList = flowPrintTemplateService.selectModulByFlowType(flowTypeId);
			json.setRtState(true);
			json.setRtData(modelList);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg("查询出错！");
		}
		return json;
	}
	
	
	/**
	 * 根据流程步骤实例获取所有打印模版
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectModulByFlowRunPrcs")
	@ResponseBody
	public TeeJson selectModulByFlowRunPrcs( HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		List<TeeFlowPrintTemplateModel> modelList = flowPrintTemplateService.selectModulByFlowRunPrcs(frpSid);
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}
	
	
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById( HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeeFlowPrintTemplateModel model = flowPrintTemplateService.getById(request);
			json.setRtState(true);
			json.setRtData(model);
		} catch (Exception ex) {
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 获取基本信息，不需要板式文件信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdInfo")
	@ResponseBody
	public TeeJson getByIdInfo( HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			json = flowPrintTemplateService.getByIdInfo(request);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtState(false);
		}
		return json;
	}

	/*
	 * 删除 ById
	 */
	@RequestMapping("/delById")
	@ResponseBody
	public TeeJson delById( HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String sid = request.getParameter("sid");
		try {
		    flowPrintTemplateService.delById(sid);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtState(false);
		}
		return json;
	}
	
	
	/**
	 * 渲染AIP签批模板
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/renderTemplate")
	@ResponseBody
	public TeeJson renderTemplate( HttpServletRequest request){
		return flowPrintTemplateService.renderTemplate(request);
	}
	
	
	public void setFlowPrintTemplateService(
			TeeFlowPrintTemplateServiceInterface flowPrintTemplateService) {
		this.flowPrintTemplateService = flowPrintTemplateService;
	}



}


	