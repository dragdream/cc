package com.tianee.oa.sync.log.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.sync.log.model.TeeSyncLogModel;
import com.tianee.oa.sync.log.service.TeeSyncLogService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/syncLogController")
public class TeeSyncLogController {

	@Autowired
	private TeeSyncLogService syncLogService;
	
	/**
	 * 分页查询
	 * @param model
	 * @param queryModel
	 * @return
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public TeeEasyuiDataGridJson getPage(TeeDataGridModel model,TeeSyncLogModel queryModel){
		return syncLogService.getPage(model,queryModel);
	}
	
	/**
	 * 根据id获取日志信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSyncLog")
	@ResponseBody
	public TeeJson getSyncLogById(HttpServletRequest request){
		return syncLogService.getSyncLogById(request);
	}
	
	/**
	 * 删除日志信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/delSyncLog")
	@ResponseBody
	public TeeJson delSyncLogById(HttpServletRequest request){
		return syncLogService.delSyncLogById(request);
	}
	
	@RequestMapping("/syncLog")
	@ResponseBody
	public TeeJson syncLog(HttpServletRequest request) throws Exception{
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		return syncLogService.syncLog(sid,request);
	}
	
}
