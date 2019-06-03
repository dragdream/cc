package com.tianee.oa.core.base.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.job.bean.TeeJob;
import com.tianee.oa.core.base.job.service.TeeJobService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("job")
public class TeeJobController {
	
	@Autowired
	private TeeJobService jobService;
	
	@ResponseBody
	@RequestMapping("addOrUpdate")
	public TeeJson addOrUpdate(TeeJob job){
		TeeJson json = new TeeJson();
		jobService.addOrUpdate(job);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("changeStatus")
	public TeeJson changeStatus(String id,int status){
		TeeJson json = new TeeJson();
		jobService.changeStatus(id,status);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson delete(String id){
		TeeJson json = new TeeJson();
		jobService.delete(id);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getById")
	public TeeJson getById(String id){
		TeeJson json = new TeeJson();
		json.setRtData(jobService.getById(id));
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm){
		return jobService.datagrid(dm);
	}
}
