package com.tianee.oa.core.base.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weibo.service.TeeWeibDianZaiService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("TeeWeibDianZaiController")
public class TeeWeibDianZaiController {

	@Autowired
	private TeeWeibDianZaiService teeWeibDianZaiService;
	
	/**
	 * 查询当前用户是否给某个微博信息点赞
	 * */
	@ResponseBody
	@RequestMapping("/findDianZan")
	private TeeJson updateDianZan(HttpServletRequest request){
		return teeWeibDianZaiService.findDianZan(request);
	}
	
	/**
	 * 点赞（微博）
	 * */
	@ResponseBody
	@RequestMapping("/addDianZan")
	public TeeJson addDianZan(HttpServletRequest request){
		return teeWeibDianZaiService.addDianZan(request);
	}
	
	/**
	 * 取消点赞（微博）
	 * */
	@ResponseBody
	@RequestMapping("/deleteDianZan")
	public TeeJson deleteDianZan(HttpServletRequest request){
		return teeWeibDianZaiService.deleteDianZan(request);
	}
	
	/**
	 * 查询当前用户是否给某个微博评论信息点赞
	 * */
	@ResponseBody
	@RequestMapping("/findDianZan2")
	private TeeJson updateDianZan2(HttpServletRequest request){
		return teeWeibDianZaiService.findDianZan2(request);
	}
	
	/**
	 * 点赞（评论）
	 * */
	@ResponseBody
	@RequestMapping("/addDianZan2")
	public TeeJson addDianZan2(HttpServletRequest request){
		return teeWeibDianZaiService.addDianZan2(request);
	}
	
	/**
	 * 取消点赞（评论）
	 * */
	@ResponseBody
	@RequestMapping("/deleteDianZan2")
	public TeeJson deleteDianZan2(HttpServletRequest request){
		return teeWeibDianZaiService.deleteDianZan2(request);
	}
	
	
	
	/**
	 * 查询当前用户是否给某个微博评论回复信息点赞
	 * */
	@ResponseBody
	@RequestMapping("/findDianZan3")
	private TeeJson updateDianZan3(HttpServletRequest request){
		return teeWeibDianZaiService.findDianZan3(request);
	}
	
	/**
	 * 点赞（回复）
	 * */
	@ResponseBody
	@RequestMapping("/addDianZan3")
	public TeeJson addDianZan3(HttpServletRequest request){
		return teeWeibDianZaiService.addDianZan3(request);
	}
	
	/**
	 * 取消点赞（回复）
	 * */
	@ResponseBody
	@RequestMapping("/deleteDianZan3")
	public TeeJson deleteDianZan3(HttpServletRequest request){
		return teeWeibDianZaiService.deleteDianZan3(request);
	}
}
