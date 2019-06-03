package com.tianee.oa.core.base.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weibo.service.TeeWeibRelayService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("TeeWeibRelayController")
public class TeeWeibRelayController {

	@Autowired
	private TeeWeibRelayService teeWeibRelayService;
	
	/**
	 * 转发微博信息
	 * */
	@ResponseBody
	@RequestMapping("/addRelay")
	public TeeJson addRelay(HttpServletRequest request){
		return teeWeibRelayService.addRelay(request);
	}
	
}
