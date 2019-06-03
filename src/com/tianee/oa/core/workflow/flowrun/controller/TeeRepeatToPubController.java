package com.tianee.oa.core.workflow.flowrun.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPubServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/repeatToPub")
public class TeeRepeatToPubController {
	@Autowired
	private TeeRepeatToPubServiceInterface service;

	/**
	 * @function: 获取转存公共网盘目录树
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getPublicFolderTree")
	@ResponseBody
	public TeeJson getPublicFolderTree(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getPublicFolderTree(requestMap, loginPerson);
		return json;
	}

	/**
	 * @function: 转存至公共网盘
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/saveToPublicFolder")
	@ResponseBody
	public TeeJson saveToPublicFolder(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.saveToPersonFolder(requestMap, loginPerson);
		return json;
	}

/*	*//**
	 * @function: 获取文件信息
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param request
	 * @return TeeJson
	 *//*
	@RequestMapping("/getAttachmentInfo")
	@ResponseBody
	public TeeJson getAttachmentInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getAttachmentInfo(requestMap, loginPerson);
		return json;
	}*/
}
