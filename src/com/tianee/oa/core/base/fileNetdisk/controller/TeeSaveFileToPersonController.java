package com.tianee.oa.core.base.fileNetdisk.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.service.TeeSaveFileToPersonService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 转存至个人网盘
 * 
 * @author wyw
 * 
 */
@Controller
@RequestMapping("/saveFileToPersonController")
public class TeeSaveFileToPersonController {
	@Autowired
	private TeeSaveFileToPersonService service;

	/**
	 * @function: 获取转存个人网盘目录树
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getPersonFolderTree")
	@ResponseBody
	public TeeJson getPersonFolderTree(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getPersonFolderTree(requestMap, loginPerson);
		return json;
	}

	
	/**
	 * @function: 转存至个人网盘
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/saveToPersonFolder")
	@ResponseBody
	public TeeJson saveToPersonFolder(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.saveToPersonFolder(requestMap, loginPerson);
		return json;
	}
	
	/**
	 * @function: 获取文件信息
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getAttachmentInfo")
	@ResponseBody
	public TeeJson getAttachmentInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getAttachmentInfo(requestMap, loginPerson);
		return json;
	}
	
	
	
}
