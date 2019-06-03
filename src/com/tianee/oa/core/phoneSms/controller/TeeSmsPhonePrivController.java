package com.tianee.oa.core.phoneSms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.phoneSms.model.TeeSmsPhonePrivModel;
import com.tianee.oa.core.phoneSms.service.TeeSmsPhonePrivService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/TeeSmsPhonePrivController")
public class TeeSmsPhonePrivController extends BaseController{
	@Autowired
	private TeeSmsPhonePrivService privService;
	
	@Autowired
	private TeeSysParaService sysParaServ;
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeSmsPhonePrivModel model) {
		TeeJson json = new TeeJson();
		json = privService.addOrUpdate(request , model);
		return json;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeSmsPhonePrivModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = privService.deleteByIdService(request ,sids);
		return json;
	}
	

	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeSmsPhonePrivModel model) {
		TeeJson json = new TeeJson();
		json = privService.getById(request , model);
		return json;
	}
	
	/**
	 * @author ny
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return privService.datagird(dm, requestDatas);
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPhonePriv")
	@ResponseBody
	public TeeJson getPhonePriv(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = privService.getPhonePriv();
		return json;
	}
	
	@RequestMapping("/getUserIds")
	@ResponseBody
	public TeeJson getUserIds(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = privService.getUserIds(request);
		return json;
	}
	
	/**
	 * 获取短消息配置项
	 * @author kakalion
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getSmsConfigs")
	@ResponseBody
	public TeeJson getSmsConfigs(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map map = new HashMap();
		map.put("SMS_APIKEY", TeeSysProps.getString("SMS_APIKEY"));
		map.put("SMS_USERNAME", TeeSysProps.getString("SMS_USERNAME"));
		map.put("SMS_PASSWORD", TeeSysProps.getString("SMS_PASSWORD"));
		map.put("SMS_URL", TeeSysProps.getString("SMS_URL"));
		map.put("SMS_OPEN", TeeSysProps.getString("SMS_OPEN"));
		map.put("SMS_TEMPLATE", TeeSysProps.getString("SMS_TEMPLATE"));
		json.setRtData(map);
		return json;
	}
	
	/**
	 * 获取短消息配置项
	 * @author kakalion
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateSmsConfigs")
	@ResponseBody
	public TeeJson updateSmsConfigs(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json.setRtState(true);
		sysParaServ.updateSysPara("SMS_APIKEY", request.getParameter("SMS_APIKEY"));
		sysParaServ.updateSysPara("SMS_USERNAME", request.getParameter("SMS_USERNAME"));
		sysParaServ.updateSysPara("SMS_PASSWORD", request.getParameter("SMS_PASSWORD"));
//		sysParaServ.updateSysPara("SMS_URL", request.getParameter("SMS_URL"));
		sysParaServ.updateSysPara("SMS_OPEN", request.getParameter("SMS_OPEN"));
		sysParaServ.updateSysPara("SMS_TEMPLATE", request.getParameter("SMS_TEMPLATE"));
		return json;
	}
	
}