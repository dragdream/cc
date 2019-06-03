package com.tianee.oa.subsys.supervise.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.subsys.supervise.service.TeeSupFeedBackService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/supFeedBackController")
public class TeeSupFeedBackController {

	@Autowired
	private TeeSupFeedBackService feedBackService;
	
	
	
	/**
	 * 发表反馈
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson add(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return feedBackService.addOrUpdate(request);
	}
	
	
	/**
	 * 根据任务主键  获取反馈情况
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getFeedBackListBySupId")
	@ResponseBody
	public TeeEasyuiDataGridJson getFeedBackListBySupId(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		return feedBackService.getFeedBackListBySupId(dm, request);
	}
	
	
	/**
	 * 根据主键  获取反馈详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return feedBackService.getInfoBySid(request);
	}
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return feedBackService.delBySid(request);
	}
}
