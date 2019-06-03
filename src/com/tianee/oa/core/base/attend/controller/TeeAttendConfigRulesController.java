package com.tianee.oa.core.base.attend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.tianee.oa.core.base.attend.model.TeeAttendConfigRulesModel;
import com.tianee.oa.core.base.attend.service.TeeAttendConfigRulesService;

import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("/attendConfigRulesController")
public class TeeAttendConfigRulesController {
	
	@Autowired
	private TeeAttendConfigRulesService  attendConfigRulesService;
	
	/**
	 * 新建/编辑固定排班类型
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request, TeeAttendConfigRulesModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigRulesService.addOrUpdate(request, model);
		return json;
	}
	
	
	/**
	 * 获取固定排班列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request) {
		return attendConfigRulesService.datagrid(dm, request);
	}
	
	
	
	/**
	 * 根据主键进行删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigRulesService.delBySid(request);
		return json;
	}
	
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigRulesService.getInfoBySid(request);
		return json;
	}
}
