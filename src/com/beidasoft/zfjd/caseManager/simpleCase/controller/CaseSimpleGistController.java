package com.beidasoft.zfjd.caseManager.simpleCase.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.simpleCase.service.CaseSimpleGistService;
import com.beidasoft.zfjd.power.model.PowerGistModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("caseSimpleGistCtrl")
public class CaseSimpleGistController {
    
	@Autowired
	private CaseSimpleGistService simpleGistService;
	
	/**
    **  分页查询
    * @param tModel
    * @param cbModel
    * @param request
    * @return
    */    
    @ResponseBody
    @RequestMapping("/findListBypage.action")
	public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, PowerGistModel pgModel, HttpServletRequest request) {
    	TeeEasyuiDataGridJson json = simpleGistService.findListBypage(tModel, pgModel, request);
    	return json;
	}
}
