package com.tianee.oa.core.base.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weibo.service.TeeWeibCommentService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("teeWeibCommentController")
public class TeeWeibCommentController {

	@Autowired
	private TeeWeibCommentService teeWeibCommentService;
	
	/**
	 * 添加评论
	 * */
	@ResponseBody
	@RequestMapping("/addComment")
	public TeeJson addComment(HttpServletRequest request){
		return teeWeibCommentService.addComment(request);
	}
	
	/**
	 * 删除评论
	 * */
	@ResponseBody
	@RequestMapping("/deletePingLun")
	public TeeJson deletePingLun(int sid){
		return teeWeibCommentService.deletePingLun(sid);
	}
}
