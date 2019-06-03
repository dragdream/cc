package com.tianee.oa.core.workflow.workmanage.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowDocTemplate;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowDocTemplateService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("teeFlowDocTemplateController")
public class TeeFlowDocTemplateController {

	@Autowired
	private  TeeFlowDocTemplateService flowDocTemplateService;
	
	
	
	/**
	 * 添加或者修改
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws IOException{
		return flowDocTemplateService.addOrUpdate(request);
	}
	
	
	/**
	 * 根据流程  获取文书模板集合
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getTemplateListByFlowType")
	@ResponseBody
	public TeeJson getTemplateListByFlowType(HttpServletRequest request) throws IOException{
		return flowDocTemplateService.getTemplateListByFlowType(request);
	}
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request) throws IOException{
		return flowDocTemplateService.deleteById(request);
	}
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request) throws IOException{
		return flowDocTemplateService.getInfoBySid(request);
	}
}
