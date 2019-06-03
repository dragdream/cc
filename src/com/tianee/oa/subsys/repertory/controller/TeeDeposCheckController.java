package com.tianee.oa.subsys.repertory.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.repertory.service.TeeDeposCheckService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/deposCheckController")
public class TeeDeposCheckController {
	
	@Autowired
	private TeeDeposCheckService checkService;
	
	/**
	 * 添加盘库记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addCheckRecord")
	public TeeJson addCheckRecord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		checkService.addCheckRecord(requestData);
		json.setRtState(true);
		json.setRtMsg("创建成功");
		json.setRtData(requestData.get("sid"));
		return json;
	}
	
	/**
	 * 删除盘库记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delCheckRecord")
	public TeeJson delCheckRecord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		checkService.delCheckRecord(requestData);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 结束盘库记录，不允许修改
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/finishCheckRecord")
	public TeeJson finishCheckRecord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		checkService.finishCheckRecord(requestData);
		return json;
	}
	
	/**
	 * 列出库存盘点记录（有权限）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listCheckRecord")
	public TeeEasyuiDataGridJson listCheckRecord(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return checkService.listCheckRecord(requestData,dm);
	}
	
	/**
	 * 列出库存盘点记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listCheckItem")
	public TeeEasyuiDataGridJson listCheckItem(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		return checkService.listCheckItem(requestData,dm);
	}
	
	/**
	 * 更新盘点项
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateCheckItems")
	public TeeJson updateCheckItems(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		checkService.updateCheckItems(requestData);
		json.setRtMsg("保存盘库项数据成功");
		json.setRtState(true);
		return json;
	}
	
	
	
}
