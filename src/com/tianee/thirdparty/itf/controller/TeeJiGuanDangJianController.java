package com.tianee.thirdparty.itf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.thirdparty.itf.service.TeeJiGuanDangJianService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("jiGuanDangJian")
public class TeeJiGuanDangJianController {

	@Autowired
	private TeeJiGuanDangJianService teeJiGuanDangJianService;
	
	/**
	 * 调用内部信息接口
	 * */
	@ResponseBody
	@RequestMapping("/sendSmsSender")
	public TeeJson sendSmsSender(HttpServletRequest request){
		return teeJiGuanDangJianService.sendSmsSender(request);
	}
	
	/**
	 * 发布通知公告
	 * @throws IOException 
	 * */
	@ResponseBody
	@RequestMapping("/sendAnnouncement")
	public TeeJson sendAnnouncement(HttpServletRequest request) throws IOException{
		return teeJiGuanDangJianService.sendAnnouncement(request);
	}
}
