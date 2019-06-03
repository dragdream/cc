package com.tianee.oa.subsys.salManage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.salManage.model.TeeSalFlowModel;
import com.tianee.oa.subsys.salManage.service.TeeSalFlowService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 工资数据
 * @author think
 *
 */
@Controller
@RequestMapping("/teeSalFlowController")
public class TeeSalFlowController  extends BaseController{
	
	@Autowired
	private TeeSalFlowService flowService;
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeSalFlowModel model) {
		TeeJson json = new TeeJson();
		json = flowService.getById( model);
		return json;
	}
	
	
	/** 新建或者更新
	 * @author 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeSalFlowModel model) {
		TeeJson json = new TeeJson();
		Map map = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		map.put(TeeConst.LOGIN_USER, person);
		json = flowService.addOrUpdate(map, model);
		return json;
	}
	
	
	/**
	 * 获取账套工资流程通用列表
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getManageSalFlowList")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageSalFlowList(TeeDataGridModel dm, HttpServletRequest request , TeeSalFlowModel model) {
		return flowService.getManageSalFlowList(dm, request, model);
	}

	
	/**
	 * 根据Ids 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public TeeJson deleteByIds( HttpServletRequest request ){
		Map map = TeeServletUtility.getParamMap(request);
		return flowService.deleteByIds(map);
	}
}
