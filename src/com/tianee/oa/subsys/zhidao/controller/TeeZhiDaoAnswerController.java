package com.tianee.oa.subsys.zhidao.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.zhidao.service.TeeZhiDaoAnswerService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/zhiDaoAnswerController")
public class TeeZhiDaoAnswerController {
	@Autowired
	private TeeZhiDaoAnswerService zhiDaoAnswerService;
	
	/**
	 * 获取问题的最佳答案
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getBestAnswer")
	@ResponseBody
	public TeeJson getBestAnswer(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoAnswerService.getBestAnswer(request);
	}
	
	
	/**
	 * 获取其他回到
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getOtherAnswer")
	@ResponseBody
	public TeeJson getOtherAnswer(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoAnswerService.getOtherAnswer(request);
	}
	
	
	/**
	 * 我来回答
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoAnswerService.add(request);
	}
	
	
	/**
	 * 删除回答
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return zhiDaoAnswerService.delBySid(request);
	}
}
