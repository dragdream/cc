package com.tianee.oa.core.ispirit.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.ispirit.service.TeeUserMessageService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/teeUserMessageController")
public class TeeUserMessageController {

	@Autowired
	private TeeUserMessageService messageService;
	
	
	/**
	 * 根据用户名 获取详细信息
	 * @param request
	 * @return
	 */
	
	@RequestMapping("/getUserInfoByUserId")
	@ResponseBody
	public TeeJson getUserInfoByUserId(HttpServletRequest request){
		Map dataMap = TeeServletUtility.getParamMap(request);
		TeeJson json = messageService.getUserInfoByUserId(dataMap);
		return json;
	}
}
