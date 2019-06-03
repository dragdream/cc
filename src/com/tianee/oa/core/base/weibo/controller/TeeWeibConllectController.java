package com.tianee.oa.core.base.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weibo.service.TeeWeibConllectService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeWeibConllectController")
public class TeeWeibConllectController {

	@Autowired
	private TeeWeibConllectService teeWeibConllectService;
	
	/**
	 * 查看当前登录人是否收藏此微博信息
	 * */
	@ResponseBody
	@RequestMapping("/findCollect")
	public TeeJson findCollect(HttpServletRequest request){
		return teeWeibConllectService.findCollect(request);
	}
	
	/**
	 * 收藏
	 * */
	@ResponseBody
	@RequestMapping("/addCollect")
	public TeeJson addCollect(HttpServletRequest request){
		return teeWeibConllectService.addCollect(request);
	}
	
	/**
	 * 取消收藏
	 * */
	@ResponseBody
	@RequestMapping("/deleteCollect")
	public TeeJson deleteCollect(HttpServletRequest request){
		return teeWeibConllectService.deleteCollect(request);
	}
}
