package com.tianee.lucene.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.lucene.entity.SearchModel;
import com.tianee.lucene.service.TeeNetDiskLuceneService;
import com.tianee.oa.subsys.crm.core.chance.model.TeeCrmChanceModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;


@Controller
@RequestMapping("/netDiskLuceneController")
public class TeeNetDiskLuceneController {
	
	@Autowired
	private TeeNetDiskLuceneService service;
	
	
	/**
	 * 创建索引
	 * @param request
	 * @return
	 */
	@RequestMapping("/createNetDiskIndex")
	@ResponseBody
	public TeeJson createNetDiskIndex(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.createNetDiskIndex(sid);
		return json;
	}
	
	
	
	
}
