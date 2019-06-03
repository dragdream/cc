package com.beidasoft.xzfy.caseStatAna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.caseStatAna.service.CaseStatService;
import com.tianee.webframe.httpmodel.TeeJson;

import common.Logger;
@Controller
@RequestMapping("/xzfy/caseStatAna")
public class CaseStatController extends FyBaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//日志
	public Logger log = Logger.getLogger(CaseStatController.class);
	
	@Autowired
	private CaseStatService service;
	
	@RequestMapping("/getCaseStat")
	@ResponseBody
	public TeeJson getCaseStat(String beginTime,String endTime){
		
		TeeJson data = service.getCaseStatList(beginTime,endTime);
		return data;
	}

}
