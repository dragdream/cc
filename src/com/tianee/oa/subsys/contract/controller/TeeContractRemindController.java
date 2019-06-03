package com.tianee.oa.subsys.contract.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.contract.bean.TeeContractRemind;
import com.tianee.oa.subsys.contract.model.TeeContractRemindModel;
import com.tianee.oa.subsys.contract.service.TeeContractRemindService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/contractRemind")
public class TeeContractRemindController {
	@Autowired
	private TeeContractRemindService contractRemindService;
	
	@ResponseBody
	@RequestMapping("/add")
	public TeeJson add(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeContractRemindModel contractRemindModel = 
				(TeeContractRemindModel) TeeServletUtility.request2Object(request, TeeContractRemindModel.class);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		contractRemindModel.setCrUserId(loginUser.getUuid());
		contractRemindService.add(contractRemindModel);
		json.setRtState(true);
		json.setRtMsg("添加成功！");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeContractRemindModel contractRemindModel = 
				(TeeContractRemindModel) TeeServletUtility.request2Object(request, TeeContractRemindModel.class);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		contractRemindModel.setCrUserId(loginUser.getUuid());
		contractRemindService.update(contractRemindModel);
		json.setRtState(true);
		json.setRtMsg("更新成功！");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		contractRemindService.delete(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractRemindService.datagrid(requestData, dm);
	}
}
