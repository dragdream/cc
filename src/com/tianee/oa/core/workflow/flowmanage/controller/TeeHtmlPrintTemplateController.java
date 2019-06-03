package com.tianee.oa.core.workflow.flowmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("/teeHtmlPrintTemplateController")
public class TeeHtmlPrintTemplateController {
	@Autowired
	private TeeHtmlPrintTemplateServiceInterface teeHtmlPrintTemplateService;
	
	
	
	
	/**
	 * 添加html打印模板   /  编辑html打印模板的名称
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request){
		return teeHtmlPrintTemplateService.addOrUpdate(request);
	}
	
	
	/**
	 * 根据flowTypeId获取打印模板
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public TeeJson list(HttpServletRequest request){
		return teeHtmlPrintTemplateService.list(request);
	}
	
	
	
	
	/**
	 * 根据runId  获取相关的HTML打印模板
	 * @param request
	 * @return
	 */
	@RequestMapping("/listByRunId")
	@ResponseBody
	public TeeJson listByRunId(HttpServletRequest request){
		return teeHtmlPrintTemplateService.listByRunId(request);
	}
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){
		return teeHtmlPrintTemplateService.getInfoBySid(request);
	}
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteBySid")
	@ResponseBody
	public TeeJson deleteBySid(HttpServletRequest request){
		return teeHtmlPrintTemplateService.deleteBySid(request);
	}
	
	
	
	/**
	 * 根据流程类型获取所有的表单项
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBasicFormItemsByFlowType")
	@ResponseBody
	public TeeJson getBasicFormItemsByFlowType(HttpServletRequest request){
		return teeHtmlPrintTemplateService.getBasicFormItemsByFlowType(request);
	}
	
	
	
	
	/**
	 * 打印模板设计
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateTplContent")
	@ResponseBody
	public TeeJson updateTplContent(HttpServletRequest request){
		return teeHtmlPrintTemplateService.updateTplContent(request);
	}
	
	
	
	/**
	 * html打印预览
	 * @param request
	 * @return
	 */
	@RequestMapping("/printExplore")
	@ResponseBody
	public TeeJson printExplore(HttpServletRequest request){
		TeeJson json=new  TeeJson();
		json.setRtState(true);
		json.setRtData(teeHtmlPrintTemplateService.printExplore(request));
		return json;
	}
}
