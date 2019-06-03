package com.tianee.oa.core.base.dam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.dam.service.TeeOperationRecordsService;
import com.tianee.webframe.httpmodel.TeeJson;


@Controller
@RequestMapping("opeRecordsController")
public class TeeOperationRecordsController {

	@Autowired
	TeeOperationRecordsService operRecordsService;
	
	
	
	
	/**
	 * 根据档案主键  获取日志列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListByFileId")
	@ResponseBody
	public TeeJson getListByFileId(HttpServletRequest request) throws Exception{
		return operRecordsService.getListByFileId(request);
	}
}
