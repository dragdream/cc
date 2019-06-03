package com.tianee.oa.subsys.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.weixin.service.TeeWeixinDeptService;
import com.tianee.oa.subsys.weixin.service.TeeWeixinService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/weixinDept")
public class TeeWeixinDeptController {
	@Autowired
	private TeeWeixinDeptService deptService;
	@Autowired
	TeeWeixinService weixinService;

	/**
	 * 同步组织机构
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/syncOrg")
	public void syncOrg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		deptService.syncOrg(response); 
	}
	
	
	
	/**
	 * 同步指定部门
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/syncDept")
	public void syncDept(HttpServletRequest request,HttpServletResponse response) throws Exception{
		deptService.syncDept(response,request); 
	}
	
	
	
	/**
	 * 删除微信部门
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delWeiXinDept")
	public void delWeiXinDept(HttpServletRequest request,HttpServletResponse response) throws Exception{
		deptService.delWeiXinDept(response,request); 
	}
	/**
	 * 获取微信组织机构
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWeixinOrg")
	@ResponseBody
	public TeeJson getWeixinOrg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id") == null ? "1" : request.getParameter("id");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = deptService.getWeixinDeptAsync(id);
		return json;
	}
}
