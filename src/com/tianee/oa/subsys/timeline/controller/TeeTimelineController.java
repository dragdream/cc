package com.tianee.oa.subsys.timeline.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.timeline.model.TeeTimelineModel;
import com.tianee.oa.subsys.timeline.service.TeeTimelineService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/TeeTimelineController")
public class TeeTimelineController extends BaseController{
	@Autowired
	private TeeTimelineService timelineService;
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		json = timelineService.addOrUpdate(request , model);
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
	public TeeJson deleteById(HttpServletRequest request , TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = timelineService.deleteByIdService(request ,sids);
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
	public TeeJson getById(HttpServletRequest request , TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		json = timelineService.getById(request , model);
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
		return timelineService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getVisitPriv")
	@ResponseBody
	public TeeJson getVisitPriv(HttpServletRequest request , TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		json = timelineService.getVisitPriv(request , model);
		return json;
	}
	
	@RequestMapping("/getManagePriv")
	@ResponseBody
	public TeeJson getManagePriv(HttpServletRequest request , TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		json = timelineService.getManagePriv(request , model);
		return json;
	}

}