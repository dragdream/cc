package com.tianee.oa.core.base.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weibo.service.TeeWeibReplyService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("TeeWeibReplyController")
public class TeeWeibReplyController {

	@Autowired
	private TeeWeibReplyService teeWeibReplyService;
	
	/**
	 * 添加回复
	 * */
	@ResponseBody
	@RequestMapping("/addReply")
	public TeeJson addReply(HttpServletRequest request){
		return teeWeibReplyService.addReply(request);
	}
	
	/**
	 * 获取某评论下所有的回复
	 * */
	@ResponseBody
	@RequestMapping("/findReplyAll")
	public TeeJson findReplyAll(int plId,HttpServletRequest request){
		return teeWeibReplyService.findReplyAll(plId,request);
	}
	
	/**
	 * 删除某条回复
	 * */
	@ResponseBody
	@RequestMapping("/deleteReply")
	public TeeJson deleteReply(int sid){
		return teeWeibReplyService.deleteReply(sid);
	}
}
