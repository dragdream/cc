package com.tianee.oa.core.workflow.workmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryTemplateServiceInterface;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("flowSeniorQueryTemplateController")
public class TeeFlowSeniorQueryTemplateController {

	@Autowired
	private TeeFlowSeniorQueryTemplateServiceInterface  templateService;
	
	
	/**
	 * 新建  编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request){
		
		return templateService.addOrUpdate(request);
	}
	
	
	
	/**
	 * 根据当前登陆人   流程id  获取相关的模板
	 * @param request
	 * @return
	 */
	@RequestMapping("/renderTempalte")
	@ResponseBody
	public TeeJson renderTempalte(HttpServletRequest request){
		
		return templateService.renderTempalte(request);
	}
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){
		
		return templateService.getInfoBySid(request);
	}
}
