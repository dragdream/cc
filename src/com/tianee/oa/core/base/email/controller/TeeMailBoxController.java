package com.tianee.oa.core.base.email.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.email.service.TeeMailBoxService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/mailBoxController")
public class TeeMailBoxController {
	@Autowired
	TeeMailBoxService mailBoxService;
	
	
	/**
	 * 获取自定义邮箱信息 ,根据mailBox 的sid
	 * @date 2014年6月24日
	 * @author 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmailBoxInfoById")
	@ResponseBody
	public TeeJson getEmailBoxInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = mailBoxService.getEmailBoxInfoByIdService(request);
		return json;
	}

	/**
	 * 删除ById
	 * @author syl
	 * @date 2014-6-28
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleEmailBoxById")
	@ResponseBody
	public TeeJson deleEmailBoxById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = mailBoxService.deleEmailBoxById(request);
		return json;
	}
	
	
	

}
