package com.tianee.oa.subsys.crm.core.visit.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.visit.model.TeeCrmVisitModel;
import com.tianee.oa.subsys.crm.core.visit.service.TeeCrmVisitService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmVisitController")
public class TeeCrmVisitController extends BaseController {
	
	@Autowired
	private TeeCrmVisitService visitService;
	
	/***
	 * 添加拜访记录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmVisitModel model){
		TeeJson json = new TeeJson();
		json =visitService.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 拜访记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return visitService.datagird(dm, requestDatas);
	}
	
	/**
	 * 拜访记录详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmVisitModel model){
		TeeJson json = new TeeJson();
		json = visitService.getInfoBySid(request,model);
		return json;
		
	}
	
	/**
	 * 完成拜访
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/finishVisit")
	public TeeJson finishVisit(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = visitService.finishVisit(request);
		return json;
	}
	
	/**
	 * 单个删除拜访记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delById")
	public TeeJson delById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = visitService.delById(request);
		return json;
	}

}
