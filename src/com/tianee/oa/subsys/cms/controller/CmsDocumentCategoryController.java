package com.tianee.oa.subsys.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.cms.service.CmsDocumentCategoryService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/cmsDocCat")
public class CmsDocumentCategoryController {
	
	@Autowired
	CmsDocumentCategoryService categoryService;
	
	@ResponseBody
	@RequestMapping("/listCats")
	public TeeJson listCats(){
		TeeJson json = new TeeJson();
		json.setRtData(categoryService.listCats());
		return json;
	}
}
