package com.tianee.oa.subsys.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.cms.bean.ChannelInfoExt;
import com.tianee.oa.subsys.cms.service.CmsChannelInfoExtService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsChannelExt")
public class CmsChannelInfoExtController {
	
	@Autowired
	private CmsChannelInfoExtService channelInfoExtService;
	
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		ChannelInfoExt ext = (ChannelInfoExt) TeeServletUtility.request2Object(request, ChannelInfoExt.class);
		channelInfoExtService.addOrUpdate(ext);
		json.setRtState(true);
		json.setRtMsg("操作成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/remove")
	public TeeJson remove(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		channelInfoExtService.remove(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(channelInfoExtService.get(sid));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public TeeEasyuiDataGridJson list(HttpServletRequest request){
		List<ChannelInfoExt> list = channelInfoExtService.list();
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		dataGridJson.setRows(list);
		dataGridJson.setTotal(Long.parseLong(String.valueOf(list.size())));
		return dataGridJson;
	}
	
	
	/**
	 * 判断字段名称是否已经存在
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkFieldNameIsExist")
	public TeeJson channelInfoExtService(HttpServletRequest request){
		return channelInfoExtService.checkFieldNameIsExist(request);
	}
}
