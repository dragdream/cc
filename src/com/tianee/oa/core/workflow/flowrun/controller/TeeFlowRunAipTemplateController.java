package com.tianee.oa.core.workflow.flowrun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunAipTemplateServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/flowRunAipTemplateController")
public class TeeFlowRunAipTemplateController {
	@Autowired
	TeeFlowRunAipTemplateServiceInterface flowRunAipTemplateService;
	
	
	/**
	 * 判断当前模板的中间附件是否存在  不存在则生成   存在则取出来
	 * @param request
	 * @return
	 */
	@RequestMapping("/isExist")
	@ResponseBody
	public TeeJson isExist(HttpServletRequest request){
		return flowRunAipTemplateService.isExist(request);
	}
	
	
	/**
	 * 根据流程id查是否存在签批单
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListByRunId")
	@ResponseBody
	public TeeJson getListByRunId(HttpServletRequest request){
		return flowRunAipTemplateService.getListByRunId(request);
	}
	
	
	
}
