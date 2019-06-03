package com.tianee.oa.core.base.calendar.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.base.calendar.service.TeeCalAffairService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;



/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/affairManage")
public class TeeCalAffairController extends BaseController {
	@Autowired
	private TeeCalAffairService affairService;

	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param message
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response , TeeCalendarAffairModel model) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = new TeeJson();
		json  = affairService.addOrUpdateAffair(request , response, model,  person);
		return json;
	}

	/**
	 * @author syl
	 * 获取所有周期性事务
	 * @param request
	 * @return
	 */
	@RequestMapping("getAll")
	@ResponseBody
	public TeeJson getAll(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = affairService.getAllAffair(person );
		return json;
	}

	/**
	 * @author syl
	 * 查询周期性事务
	 * @param request
	 * @return
	 */
	@RequestMapping("queryAffair")
	@ResponseBody
	public TeeJson queryAffair(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = affairService.queryAffair(request, person);
		return json;
	}

	/**
	 * @author syl
	 * byId 查询
	 * @param request
	 * @return
	 */
	@RequestMapping("selectById")
	@ResponseBody
	public TeeJson selectById(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		TeeJson json = affairService.selectById(sid );;
		return json;
	}
	
	/**
	 * @author syl
	 * byId 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		TeeJson json = affairService.delById(sid );;
		return json;
	}
	
	/**
	 * @author syl
	 * byId 删除多个
	 * @param request
	 * @return
	 */
	@RequestMapping("delByIds")
	@ResponseBody
	public TeeJson delByIds(HttpServletRequest request) {
		String sid = request.getParameter("sids");
		TeeJson json = affairService.delByIds(sid );;
		return json;
	}

	public TeeCalAffairService getAffairService() {
		return affairService;
	}

	public void setAffairService(TeeCalAffairService affairService) {
		this.affairService = affairService;
	}
	

	
}