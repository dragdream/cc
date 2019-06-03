package com.tianee.oa.core.attachment.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.service.TeeOfficeSwitchService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/officeSwitchController")
public class TeeOfficeSwitchController {

	@Autowired
	TeeOfficeSwitchService  officeSwitchService;
	
	
    /**
     * 根据原附件id获取中间表信息
     * @param request
     * @return
     */
	@RequestMapping("/getTaskByAttachId")
	@ResponseBody
	public TeeJson getTaskByAttachId(HttpServletRequest request){
		
		return officeSwitchService.getTaskByAttachId(request);
	}
	
	
	/**
	 * 插入中間表数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertSwitchTask")
	@ResponseBody
	public TeeJson insertSwitchTask(HttpServletRequest request){
		
		return officeSwitchService.insertSwitchTask(request);
	}
}
