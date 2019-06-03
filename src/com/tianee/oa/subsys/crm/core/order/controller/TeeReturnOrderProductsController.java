package com.tianee.oa.subsys.crm.core.order.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.crm.core.order.service.TeeReturnOrderProductsService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeReturnOrderProductsController")
public class TeeReturnOrderProductsController {
	@Autowired
	private TeeReturnOrderProductsService teeReturnOrderProductsService;
	
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = teeReturnOrderProductsService.getInfoBySid(request);
		return json;
	}
	
}
