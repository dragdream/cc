package com.tianee.oa.core.base.fileNetdisk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.service.TeeFileHistoryService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("teeFileHistoryController")
public class TeeFileHistoryController {
	@Autowired
	private TeeFileHistoryService teeFileHistoryService;
	
	//生成版本
	@ResponseBody
	@RequestMapping("/addFileHistory")
	public TeeJson addFileHistory(HttpServletRequest request){
		return teeFileHistoryService.addFileHistory(request);
	}
	
	//获取所有的版本
	@ResponseBody
	@RequestMapping("/getFileHistoryAll")
	public TeeEasyuiDataGridJson getFileHistoryAll(HttpServletRequest request,TeeDataGridModel dm){
		return teeFileHistoryService.getFileHistoryAll(request,dm);
	}

}
