package com.tianee.oa.subsys.supervise.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.subsys.supervise.service.TeeSupervisionUrgeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/supUrgeController")
public class TeeSupervisionUrgeController {

	@Autowired
	private TeeSupervisionUrgeService supUrgeService;
	
	
	/**
	 * 新建催办
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supUrgeService.add(request);
	}
	
	
	/**
	 * 根据督办任务主键 获取催办列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getUrgeListBySupId")
	@ResponseBody
	public TeeEasyuiDataGridJson getUrgeListBySupId(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		return supUrgeService.getUrgeListBySupId(dm, request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supUrgeService.getInfoBySid(request);
	}
	
}
