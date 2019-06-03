package com.tianee.oa.core.system.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.system.service.TeeSystemOtherServer;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeSystemOtherController")
public class TeeSystemOtherController {

	@Autowired
	private TeeSystemOtherServer systemOtherServer;
	  /**
	   * 获取个模块需要办理的记录数
	   * @author syl
	   * @date 2014-6-15
	   * @param request
	   * @param response
	   * @return
	 * @throws ParseException 
	   */
	  @RequestMapping("/getModelHandCount")
	  @ResponseBody
	  public TeeJson getModelHandCount(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		  TeeJson json = systemOtherServer.getModelNoHandCount(request);
		  return json;
	  }
	
}
