package com.tianee.oa.core.xt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.xt.model.TeeXTRunModel;
import com.tianee.oa.core.xt.service.TeeXTReplyService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/teeXTReplyController")
public class TeeXTReplyController {

	@Autowired
	private TeeXTReplyService xtReplyService;
	
	
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request){
		TeeJson json=new TeeJson();
		json=xtReplyService.add(request);
		return json;
	}
	
	
	/**
	 * 获取回复列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getReplyListByPrcsId")
	@ResponseBody
	public TeeJson getReplyListByPrcsId(HttpServletRequest request){
		TeeJson json=new TeeJson();
		json=xtReplyService.getReplyListByPrcsId(request);
		return json;
	}
	
}
