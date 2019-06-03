package com.beidasoft.xzzf.punish.document.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.document.model.CasedealModel;
import com.beidasoft.xzzf.punish.document.service.CasedealService;
import com.beidasoft.xzzf.punish.document.service.SecurityAdminService;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/casedealCtrl")
public class CasedealController {
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CasedealService casedealService;
	
	@Autowired
	private SecurityAdminService securityAdminService;
	
	/**
	 * 保存案件处理呈批表
	 * 
	 * @param casedealModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	 
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(CasedealModel casedealModel, HttpServletRequest request) throws Exception {

		// 保存 案件处理呈批表
		return casedealService.save(casedealModel,request);
	}
	
	/**
	 * 获取单个案件处理呈批表
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson get(String id, HttpServletRequest request) {

		return casedealService.getById(id, request);
	}
	
}
