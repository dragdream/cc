package com.tianee.oa.core.base.commonword.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.commonword.model.CommonWordModel;
import com.tianee.oa.core.base.commonword.service.CommonWordService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("CommonWord")
public class CommonWordController {
	@Autowired
	private CommonWordService commonWordService;
	@ResponseBody
	@RequestMapping("addCm")
	public TeeJson addCm(HttpServletRequest request){
		TeeJson teeJson = new TeeJson ();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		CommonWordModel cwm= new CommonWordModel();
		TeeServletUtility.requestParamsCopyToObject(request, cwm);
		commonWordService.addCm(cwm,person.getUuid());
		teeJson.setRtMsg("添加成功");
		teeJson.setRtState(true);
		return teeJson;
	}
	@ResponseBody
	@RequestMapping("testDatagrid")
	public TeeEasyuiDataGridJson testDatagrid(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return commonWordService.sel(person,dm);
	}
	@ResponseBody
	@RequestMapping("deleteCm")
	public TeeJson deteleCm(HttpServletRequest request){
		TeeJson teeJson = new TeeJson ();
		String sid = TeeStringUtil.getString(request.getParameter("sid"), "");
		commonWordService.deteleCm(sid);
		
		teeJson.setRtMsg("删除成功");
		teeJson.setRtState(true);
		return teeJson;
	}
	
	
	
	@ResponseBody
	@RequestMapping("getCommonWords")
	public TeeJson getCommonWords(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"));
		json.setRtData(commonWordService.getCommonWords(sid));
		json.setRtState(true);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("updateCommonWord")
	public TeeJson updateCommonWord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		CommonWordModel commonWordModel = new CommonWordModel();
		TeeServletUtility.requestParamsCopyToObject(request, commonWordModel);
		commonWordService.updateCommonWord(commonWordModel);
		json.setRtMsg("修改成功");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 使用频次+1
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("wordCountPlus")
	public TeeJson wordCountPlus(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String sid = request.getParameter("sid");
		commonWordService.wordCountPlus(sid);
		json.setRtState(true);
		return json;
	}
}
