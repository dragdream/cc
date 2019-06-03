package com.tianee.oa.core.base.officeProducts.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeDepository;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeDepositoryModel;
import com.tianee.oa.core.base.officeProducts.service.TeeOfficeDepositoryService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("officeDepositoryController")
public class TeeOfficeDepositoryController {
	
	@Autowired
	TeeOfficeDepositoryService officeDepositoryService;
	
	@RequestMapping("/addDepository")
	@ResponseBody
	public TeeJson addDepository(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeOfficeDepositoryModel model = new TeeOfficeDepositoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		officeDepositoryService.addDepositoryModel(model);
		
		json.setRtMsg("保存成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/delDepository")
	@ResponseBody
	public TeeJson delDepository(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeOfficeDepository depository = officeDepositoryService.getBySid(sid);
		officeDepositoryService.delDepository(depository);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getDepository")
	@ResponseBody
	public TeeJson getDepository(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		json.setRtData(officeDepositoryService.getModelBySid(sid));
		return json;
	}
	
	@RequestMapping("/updateDepository")
	@ResponseBody
	public TeeJson updateDepository(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeOfficeDepositoryModel model = new TeeOfficeDepositoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		officeDepositoryService.updateDepositoryModel(model);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeDepositoryService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getDeposListWithNoPriv")
	@ResponseBody
	public TeeJson getDeposListWithNoPriv(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		TeeJson json = new TeeJson();
		json.setRtData(officeDepositoryService.getDeposListWithNoPriv());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据调度员权限获取用品库
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeposListByOperatePriv")
	@ResponseBody
	public TeeJson getDeposListByOperatePriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(officeDepositoryService.getDeposListByOperatePriv(loginUser));
		json.setRtState(true);
		return json;
	}
	
}
