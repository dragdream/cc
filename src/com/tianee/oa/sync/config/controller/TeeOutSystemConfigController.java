package com.tianee.oa.sync.config.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.sync.config.service.TeeOutSystemConfigService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/outSystemConfig")
public class TeeOutSystemConfigController {

	@Autowired
	private TeeOutSystemConfigService configService;
	
	/**
	 * 分页查询
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson getPageList(TeeDataGridModel model,HttpServletRequest request){
		return configService.dataGrid(model,null);
	}
	
	/**
	 * 添加配置
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/addConfig")
	@ResponseBody
	public TeeJson addConfig(HttpServletRequest request){
		return configService.saveConfig(request);
	}
	
	/**
	 * 获取配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getConfig")
	@ResponseBody
	public TeeJson getConfig(HttpServletRequest request){
		return configService.getConfig(request);
	}
	
	/**
	 * 修改配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateConfig")
	@ResponseBody
	public TeeJson updateConfig(HttpServletRequest request){
		return configService.updateConfig(request);
	}
	
	/**
	 * 删除配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/delConfig")
	@ResponseBody
	public TeeJson delConfig(HttpServletRequest request){
		return configService.delConfig(request);
	}
}
