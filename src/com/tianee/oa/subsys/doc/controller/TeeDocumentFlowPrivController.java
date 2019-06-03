package com.tianee.oa.subsys.doc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.doc.service.TeeDocumentFlowPrivService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("docFlowPriv")
public class TeeDocumentFlowPrivController {
	@Autowired
	private TeeDocumentFlowPrivService documentFlowPrivService;
	
	/**
	 * 添加与更新映射
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOuUpdate")
	public TeeJson addOuUpdate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		documentFlowPrivService.addOrUpdate(requestData);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		documentFlowPrivService.delete(uuid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public TeeJson list(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(documentFlowPrivService.list());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		json.setRtData(documentFlowPrivService.get(uuid));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取当前可发起的流程
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listCreatableFlow")
	public TeeJson listCreatableFlow(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(documentFlowPrivService.listCreatableFlow(loginUser));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取当前可发起的流程分类
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listCreatableFlowSort")
	public TeeJson listCreatableFlowSort(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(documentFlowPrivService.listCreatableFlowSort(loginUser));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据流程分类获取当前可发起的流程
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listCreatableFlowBySort")
	public TeeJson listCreatableFlowBySort(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sortId = TeeStringUtil.getInteger(request.getParameter("sortId"),0);
		json.setRtData(documentFlowPrivService.listCreatableFlowBySort(sortId,loginUser));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 更新映射
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateMapping")
	public TeeJson updateMapping(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		String fieldMapping = TeeStringUtil.getString(request.getParameter("fieldMapping"));
		documentFlowPrivService.updateMapping(uuid,fieldMapping);
		json.setRtState(true);
		return json;
	}
}
