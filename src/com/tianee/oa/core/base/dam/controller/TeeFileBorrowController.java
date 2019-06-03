package com.tianee.oa.core.base.dam.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.dam.model.TeeFilesModel;
import com.tianee.oa.core.base.dam.service.TeeFileBorrowService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("TeeFileBorrowController")
public class TeeFileBorrowController {
	@Autowired
	TeeFileBorrowService borrowService;

	
	/**
	 * 档案借阅
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/borrow")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request){
		return borrowService.borrow(request);
	}
	
	
    /**
     * 我的借阅
     * @param dm
     * @param request
     * @return
     * @throws ParseException
     * @throws java.text.ParseException
     */
	@RequestMapping("/getMyBorrow")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyBorrow(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return borrowService.getMyBorrow(dm, request);
	}
	
	
	
	/**
	 * 删除借阅记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){
		return borrowService.delBySid(request);
	}
	
	
	
	/**
	 * 归还
	 * @param request
	 * @return
	 */
	@RequestMapping("/giveBack")
	@ResponseBody
	public TeeJson giveBack(HttpServletRequest request){
		return borrowService.giveBack(request);
	}
	
	
	
	/**
	 * 获取待审批列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getNoApprove")
	@ResponseBody
	public TeeEasyuiDataGridJson getNoApprove(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return borrowService.getNoApprove(dm, request);
	}
	
	
	/**
	 * 获取已批准列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getApproved")
	@ResponseBody
	public TeeEasyuiDataGridJson getApproved(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return borrowService.getApproved(dm, request);
	}
	
	
	/**
	 * 获取未批准列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getNotApproved")
	@ResponseBody
	public TeeEasyuiDataGridJson getNotApproved(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return borrowService.getNotApproved(dm, request);
	}
	
	
	/**
	 * 获取归还待确认的记录
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getNoConfirmRecords")
	@ResponseBody
	public TeeEasyuiDataGridJson getNoConfirmRecords(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return borrowService.getNoConfirmRecords(dm, request);
	}
	
	
	
	/**
	 * 获取已经确认的归还记录
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getConfirmedRecords")
	@ResponseBody
	public TeeEasyuiDataGridJson getConfirmedRecords(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return borrowService.getConfirmedRecords(dm, request);
	}
	
	
	
	/**
	 * 审批
	 * @param request
	 * @return
	 */
	@RequestMapping("/approve")
	@ResponseBody
	public TeeJson approve(HttpServletRequest request){
		return borrowService.approve(request);
	}
	
	
	
	/**
	 * 归还确认
	 * @param request
	 * @return
	 */
	@RequestMapping("/confirmReturn")
	@ResponseBody
	public TeeJson confirmReturn(HttpServletRequest request){
		return borrowService.confirmReturn(request);
	}
	
	
}
