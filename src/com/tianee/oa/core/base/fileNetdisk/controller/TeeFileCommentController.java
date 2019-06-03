package com.tianee.oa.core.base.fileNetdisk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.model.TeeFileCommentModel;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileCommentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("teeFileCommentController")
public class TeeFileCommentController {

	@Autowired
	private TeeFileCommentService teeFileCommentService;
	
	
	/**
	 * 添加评论
	 * */
	@ResponseBody
	@RequestMapping("/addFileComment")
	 public TeeJson addFileComment(HttpServletRequest request){
		 return teeFileCommentService.addFileComment(request);
	 }
	
	/**
	 * 删除评论
	 * */
	@ResponseBody
	@RequestMapping("/deleteFileComment")
	public TeeJson deleteFileComment(HttpServletRequest request){
		return teeFileCommentService.deleteFileComment(request);
	}
	
	/**
	 * 查询所有的评论
	 * */
	@ResponseBody
	@RequestMapping("/getFileComment")
	public TeeEasyuiDataGridJson getFileComment(TeeDataGridModel dm,HttpServletRequest request){
		return teeFileCommentService.getFileComment(request,dm);
	}
}
