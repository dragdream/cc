package com.tianee.oa.mobile.calendar.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.calendar.service.TeeMobileCalendarService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 手机端  -- 日程
 * @author syl
 */
@Controller
@RequestMapping("/mobileCalendarController")
public class TeeMobileCalendarController  extends BaseController{

	@Autowired
	TeeMobileCalendarService mobileCalendarService;

	/**
	 * 获取日程列表
	 * @author syl
	 * @throws ParseException 
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public TeeEasyuiDataGridJson getList(TeeDataGridModel dm,HttpServletRequest request , TeeCalendarAffairModel model) throws ParseException {	
		TeeEasyuiDataGridJson json =  mobileCalendarService.getListService(dm, request , model);
		return json;
	}
	
	/**
	 * 获取日程byId
	 * @author syl
	 * @throws ParseException 
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeCalendarAffairModel model) throws ParseException {	
		TeePerson person  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json =  mobileCalendarService.getById(  model , person);
		return json;
	}
	
	/**
	 * 保存日程byId
	 * @author syl
	 * @throws ParseException 
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeCalendarAffairModel model) throws ParseException {	
		TeePerson person  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json =  mobileCalendarService.addOrUpdate(  model , person);
		return json;
	}
	
	
	

}
