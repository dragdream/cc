package com.tianee.oa.subsys.crm.core.base.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmCompetitor;
import com.tianee.oa.subsys.crm.core.base.service.TeeCrmCompetitorService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/teeCrmCompetitorController")
public class TeeCrmCompetitorController extends BaseController{
	@Autowired
	TeeCrmCompetitorService competitorService;
	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request, TeeCrmCompetitor object){
		TeeJson json = competitorService.addOrUpdate( request ,object);
		return json;
	}	
	
	
	/**
	 * 管理  --通用分页
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager")
	@ResponseBody
	public TeeEasyuiDataGridJson manager(TeeDataGridModel dm,HttpServletRequest request ,TeeCrmCompetitor model) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return competitorService.manager(dm, request, loginPerson ,model);
	}
	
	/**
	 * 删除byIds
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delByIds")
	@ResponseBody
	public TeeJson delByIds(HttpServletRequest request ,TeeCrmCompetitor model) {
		return competitorService.delByIds( request);
	}
	
	/**
	 * 获取ById
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request ,TeeCrmCompetitor model) {
		return competitorService.getById( request);
	}
	
}
