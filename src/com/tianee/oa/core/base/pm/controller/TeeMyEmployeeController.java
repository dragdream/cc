package com.tianee.oa.core.base.pm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/myEmployee")
public class TeeMyEmployeeController {
	
	public TeeJson getMyEmployees(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		
		return json;
	}
	
}
