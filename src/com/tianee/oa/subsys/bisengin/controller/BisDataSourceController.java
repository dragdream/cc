package com.tianee.oa.subsys.bisengin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.service.BisDataSourceService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/bisDataSource")
public class BisDataSourceController {
	@Autowired
	private BisDataSourceService bisDataSourceService;
	
	@ResponseBody
	@RequestMapping("/add")
	public TeeJson add(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisDataSource bisDataSource = 
				(BisDataSource) TeeServletUtility.request2Object(request, BisDataSource.class);
		bisDataSourceService.add(bisDataSource);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisDataSource bisDataSource = 
				(BisDataSource) TeeServletUtility.request2Object(request, BisDataSource.class);
		bisDataSourceService.update(bisDataSource);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisDataSourceService.delete(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(bisDataSourceService.get(sid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request){
		return bisDataSourceService.datagrid();
	}
	
	/**
	 * 连接测试
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testConnect")
	public TeeJson testConnect(HttpServletRequest request){
		return bisDataSourceService.testConnect(request);
	}
	
	
	/**
	 * 新建/編輯页面连接测试
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testConn")
	public TeeJson testConn(HttpServletRequest request){
		return bisDataSourceService.testConn(request);
	}
}
