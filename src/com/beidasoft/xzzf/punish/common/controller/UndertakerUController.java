package com.beidasoft.xzzf.punish.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.model.UndertakerUModel;
import com.beidasoft.xzzf.punish.common.service.UndertakerUService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/undertakerController")
public class UndertakerUController {
	
	@Autowired
	private UndertakerUService undertakerUService;
	
	/**
	 * 保存的方法
	 * @param baseModel
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson save(UndertakerUModel model, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		json = undertakerUService.save(request, model);
		
		return json;
	}
}
