package com.tianee.oa.core.base.meeting.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.meeting.model.TeeMeetingEquipmentModel;
import com.tianee.oa.core.base.meeting.service.TeeMeetingEquipmentService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/meetEquipmentManage")
public class TeeMeetingEquipmentController extends BaseController{
	@Autowired
	private TeeMeetingEquipmentService meetingService;

	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeMeetingEquipmentModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.addOrUpdate(request , model);
		return json;
	}
	
	/**
	 * 获取外出管理
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllEquipment")
	@ResponseBody
	public TeeJson getAllEquipment(HttpServletRequest request , TeeMeetingEquipmentModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.getAllEquipment(request , model);
		return json;
	}
	
	/**
	 * 删除  byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeMeetingEquipmentModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = meetingService.deleteById(request , sids);
		return json;
	}
	

	/**
	 * 查询 byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeMeetingEquipmentModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.getById(request , model);
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
		return meetingService.datagird(dm, requestDatas);
	}
}