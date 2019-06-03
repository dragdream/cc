package com.tianee.oa.mobile.vehicle.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.vehicle.model.TeeVehicleUsageModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.vehicle.service.TeeMobileVehicleUsageService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
@Controller
@RequestMapping("/mobileVehicleUsageManage")
public class TeeMobileVehicleUsageController {

	 @Autowired
	 private TeeMobileVehicleUsageService mobileVehicleUsageService;
	
	 /**
     * 获取当前登录人所有的车辆申请（所有状态）
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getPersonVehicleAllStatus")
    @ResponseBody
    public TeeEasyuiDataGridJson getPersonVehicleAllStatus(HttpServletRequest request,TeeDataGridModel dm ) throws ParseException {
    	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = mobileVehicleUsageService.getPersonVehicleAllStatus(person,dm);
        return json;
    }
    
    
    
    
    /**
     * 获取由当前登录人审批的所有的车辆申请
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getApprovalVehicleAllStatus")
    @ResponseBody
    public TeeEasyuiDataGridJson getApprovalVehicleAllStatus(HttpServletRequest request,TeeDataGridModel dm ) throws ParseException {
    	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = mobileVehicleUsageService.getApprovalVehicleAllStatus(person,dm);
        return json;
    }
	
}
