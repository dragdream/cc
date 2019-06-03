package com.tianee.oa.subsys.crm.core.drawback.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.drawback.model.TeeCrmDrawbackModel;
import com.tianee.oa.subsys.crm.core.drawback.service.TeeCrmDrawbackService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmDrawbackController")
public class TeeCrmDrawbackController extends BaseController{
	
	@Autowired
	private TeeCrmDrawbackService drawbackService;
	
	/**
	 * 添加或编辑退款
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmDrawbackModel model){
		TeeJson json = new TeeJson();
		json = drawbackService.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 退款列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return drawbackService.datagird(dm, requestDatas);
	}
	
	/**
	 * 退款详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmDrawbackModel model){
		TeeJson json = new TeeJson();
		json = drawbackService.getInfoBySid(request,model);
		return json;
	}
	
	/**
	 * 同意
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agree")
	public TeeJson agree(HttpServletRequest request){
		TeeJson json= new TeeJson();
		json = drawbackService.agree(request);
		return json;
	}
	
	
	/**
	 * 驳回
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reject")
	public TeeJson reject(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = drawbackService.reject(request);
		return json;
	}
	
	/**
	 * 删除退款信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteById")
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = drawbackService.deleteById(request);
		return json;
	}


}
