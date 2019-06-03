package com.tianee.oa.subsys.supervise.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.supervise.service.TeeSupFeedBackReplyService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/supFeedBackReplyController")
public class TeeSupFeedBackReplyController {

	@Autowired
	private TeeSupFeedBackReplyService replyService;
	
	/**
	 * 新建、编辑
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson add(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return replyService.addOrUpdate(request);
	}
	
	
	
	/**
	 * 根据反馈主键  获取回复列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getReplyListByFbId")
	@ResponseBody
	public TeeJson getReplyListByFbId(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return replyService.getReplyListByFbId(request);
	}
	
	
	/**
	 * 删除回复
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return replyService.delBySid(request);
	}
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return replyService.getInfoBySid(request);
	}
}
