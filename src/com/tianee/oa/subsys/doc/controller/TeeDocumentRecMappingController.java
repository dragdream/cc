package com.tianee.oa.subsys.doc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.doc.service.TeeDocumentRecMappingService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/docRecMapping")
public class TeeDocumentRecMappingController {
	@Autowired
	private TeeDocumentRecMappingService documentRecMappingService;
	
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
		documentRecMappingService.addOrUpdate(requestData);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		documentRecMappingService.delete(uuid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public TeeJson list(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(documentRecMappingService.list());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		json.setRtData(documentRecMappingService.get(uuid));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据部门主键获取收文员设置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocRecMappingByDeptId")
	public TeeJson getDocRecMappingByDeptId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);;
		json.setRtData(documentRecMappingService.getDocRecMappingByDeptId(deptId));
		json.setRtState(true);
		return json;
	}
}
