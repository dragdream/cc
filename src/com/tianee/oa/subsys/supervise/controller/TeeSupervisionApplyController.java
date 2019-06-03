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
import com.tianee.oa.subsys.supervise.service.TeeSupervisionApplyService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/supervisionApplyController")
public class TeeSupervisionApplyController {

	@Autowired
	private TeeSupervisionApplyService  applyService;
	
	/**
	 * 新增记录
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return applyService.add(request);
	}
	
	
	/**
	 * 根据任务主键  获取暂停恢复申请记录
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getPauseOrRecoverApplyListBySupId")
	@ResponseBody
	public TeeEasyuiDataGridJson getPauseOrRecoverApplyListBySupId(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		return applyService.getPauseOrRecoverApplyListBySupId(dm, request);
	}
	
	
	/**
	 * 根据任务主键  获取办结申请记录
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getEndApplyListBySupId")
	@ResponseBody
	public TeeEasyuiDataGridJson getEndApplyListBySupId(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		return applyService.getEndApplyListBySupId(dm, request);
	}
	
	
	@RequestMapping("/approve")
	@ResponseBody
	public TeeJson approve(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return applyService.approve(request);
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
		return applyService.getInfoBySid(request);
	}
	
}
