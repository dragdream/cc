package com.tianee.oa.core.general.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.service.TeeQuickSearchService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("quickSearch")
public class TeeQuickSearchController {
	
	@Autowired
	private TeeQuickSearchService quickSearchService;
	
	@RequestMapping("/quickSearchUser")
	@ResponseBody
	public TeeJson quickSearchUser(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		json.setRtState(true);
		json.setRtData(quickSearchService.quickSearchUser(loginUser, requestDatas));
		return json;
	}
	
	@RequestMapping("/quickSearchWork")
	@ResponseBody
	public TeeJson quickSearchWork(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		json.setRtState(true);
		json.setRtData(quickSearchService.quickSearchWork(loginUser, requestDatas));
		return json;
	}
	
	@RequestMapping("/quickSearchCount")
	@ResponseBody
	public Map quickSearchCount(String word,HttpSession session){
		TeePerson loginUser = (TeePerson) session.getAttribute(TeeConst.LOGIN_USER);
		return quickSearchService.quickSearchCount(word,loginUser);
	}
	
	@RequestMapping("/quickSearchList")
	@ResponseBody
	public TeeEasyuiDataGridJson quickSearchList(String word,int type,TeeDataGridModel dataGridModel,HttpSession session){
		TeePerson loginUser = (TeePerson) session.getAttribute(TeeConst.LOGIN_USER);
		return  quickSearchService.quickSearchList(word,type,dataGridModel,loginUser);
	}
}
