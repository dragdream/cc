package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanCertModel;
import com.tianee.oa.core.base.pm.service.TeeHumanCertService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanCertController")
public class TeeHumanCertController {
	
	@Autowired
	private TeeHumanCertService certService;
	
	@RequestMapping("/addHumanCert")
	@ResponseBody
	public TeeJson addHumanCert(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanCertModel humanCert = new TeeHumanCertModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanCert);
		certService.addHumanCert(humanCert);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return certService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(certService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanCert")
	@ResponseBody
	public TeeJson updateHumanCert(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanCertModel humanCertModel = new TeeHumanCertModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanCertModel);
		certService.updateHumanCert(humanCertModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanCert")
	@ResponseBody
	public TeeJson delHumanCert(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		certService.delHumanCert(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
