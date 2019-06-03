package com.tianee.oa.subsys.informationReport.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.informationReport.service.TeeTaskTemplateItemService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeTaskTemplateItemController")
public class TeeTaskTemplateItemController {
	@Autowired
	private TeeTaskTemplateItemService  taskTemplateItemService;
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){	
		return taskTemplateItemService.getInfoBySid(request);
	}
	
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request){	
		return taskTemplateItemService.addOrUpdate(request);
	}
	
	
	
	/**
	 * 根据任务模板主键获取任务模板项列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemListByTaskTemplateId")
	@ResponseBody
	public TeeEasyuiDataGridJson getItemListByTaskTemplateId(TeeDataGridModel dm, HttpServletRequest request){
		return taskTemplateItemService.getItemListByTaskTemplateId(dm, request);
	}
	
	
	/**
	 * 根据任务模板主键获取任务模板项列表  不分页
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListByTemplateId")
	@ResponseBody
	public TeeJson getListByTemplateId(HttpServletRequest request){	
		return taskTemplateItemService.getListByTemplateId(request);
	}
	
	
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){	
		return taskTemplateItemService.delBySid(request);
	}
	
	
	
	
}
