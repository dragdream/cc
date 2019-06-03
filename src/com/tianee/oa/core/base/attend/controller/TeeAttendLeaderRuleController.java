package com.tianee.oa.core.base.attend.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaderRuleModel;
import com.tianee.oa.core.base.attend.service.TeeAttendLeaderRuleService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/attendLeaderRuleManage")
public class TeeAttendLeaderRuleController  extends BaseController{
	@Autowired
	private TeeAttendLeaderRuleService attendLeaderRuleService;
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
	public TeeJson addOrUpdate(HttpServletRequest request , TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaderRuleService.addOrUpdate(request , model);
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
	@RequestMapping("/selectLeaderRule")
	@ResponseBody
	public TeeJson selectLeaderRule(HttpServletRequest request , TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaderRuleService.getRule(request , model);
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
	public TeeJson deleteById(HttpServletRequest request , TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaderRuleService.deleteByIdService(request , model);
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
	public TeeJson getById(HttpServletRequest request , TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaderRuleService.getById(request , model);
		return json;
	}
	
	/**
	 * 获取审批规则  审批人员
	 * @author syl
	 * @date 2014-2-5
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectRuleLeaderPerson")
	@ResponseBody
	public TeeJson selectRuleLeaderPerson(HttpServletRequest request , TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaderRuleService.selectLeaderRule(request );
		return json;
	}
	
	
}