package com.tianee.oa.mobile.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.manage.service.TeeMobileModuleService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("mobileModule")
public class TeeMobileModuleController {
	@Autowired
	private TeeMobileModuleService mobileModuleService;
	
	@ResponseBody
	@RequestMapping("addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request){
		Map params = TeeServletUtility.getParamMap(request);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		mobileModuleService.addOrUpdate(params);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("del")
	public TeeJson del(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		mobileModuleService.del(sid);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("sorting")
	public TeeJson sorting(HttpServletRequest request){
		String ids = request.getParameter("ids");
		mobileModuleService.sorting(ids);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("list")
	public TeeJson list(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(mobileModuleService.list());
		return json;
	}
	
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(mobileModuleService.get(sid));
		return json;
	}
	
	/**
	 * 判断是否有该模块的管理权限
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkManagePriv")
	public TeeJson checkManagePriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(mobileModuleService.checkManagePriv(sid, person));
		return json;
	}
	
	
	/**
	 * 验证id标识是否已经存在
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkIdIsExists")
	public TeeJson checkIdIsExists(HttpServletRequest request){
		
		return mobileModuleService.checkIdIsExists(request);
	}
}
