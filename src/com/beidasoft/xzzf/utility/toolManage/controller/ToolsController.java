package com.beidasoft.xzzf.utility.toolManage.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.utility.toolManage.bean.Tools;
import com.beidasoft.xzzf.utility.toolManage.model.ToolsModel;
import com.beidasoft.xzzf.utility.toolManage.service.ToolsService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/toolsController")
public class ToolsController {
	
	@Autowired
	private ToolsService toolsService;
	
	/**
	 * 列表
	 * @param dm
	 * @param request
	 * @param tools
	 * @return
	 */
	@RequestMapping("/table")
	@ResponseBody
	public TeeEasyuiDataGridJson table(TeeDataGridModel dm, HttpServletRequest request, Tools tools) {
		return toolsService.pageList(dm, tools);
	}
	
	/**
	 * 增加
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson save(ToolsModel toolsModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		toolsService.save(toolsModel, request);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(ToolsModel toolsModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		toolsService.update(toolsModel, request);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		toolsService.delete(id);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据id获取
	 * @param id
	 * @return TeeJson
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(String id, HttpServletRequest request) {
		return toolsService.loadById(id);
	}
	
	
}
