package com.tianee.oa.core.workflow.flowrun.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowrun.service.TeeFlowArchiveServiceInterface;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/flowArchiveController")
public class TeeFlowArchiveController {

	@Autowired
	private TeeFlowArchiveServiceInterface archiveService;
	
	@RequestMapping(value="/getArchiveList")
	@ResponseBody
	public TeeEasyuiDataGridJson getArchiveList(HttpServletRequest request,TeeDataGridModel dm){
		return archiveService.getArchiveList(dm, request);
	}
	
	
	
	/**
	 * 获取归档相关数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getArchiveCount")
	@ResponseBody
	public TeeJson getArchiveCount(HttpServletRequest request){
		return archiveService.getArchiveCount(request);
	}
	
	
	
	/**
	 * 流程数据归档
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/doArchive")
	public void doArchive(HttpServletRequest request,HttpServletResponse response) throws IOException{
		archiveService.doArchive(request,response);
	}
	
	
	
	/**
	 * 刪除流程归档数据（假删除）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del")
	@ResponseBody
	public TeeJson del(HttpServletRequest request){
		return archiveService.del(request);
	}
	
	
	/**
	 * 根据归档日期获取归档列表     以判读该归档日期是否已经进行过归档操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getArchiveListByDate")
	@ResponseBody
	public TeeJson getArchiveListByDate(HttpServletRequest request){
		return archiveService.getArchiveListByDate(request);
	}
	
}
