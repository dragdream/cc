package com.tianee.oa.core.base.vehicle.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.vehicle.model.TeeVehicleUsageModel;
import com.tianee.oa.core.base.vehicle.service.TeeVehicleUsageService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author wyw
 *
 */
@Controller
@RequestMapping("/vehicleUsageManage")
public class TeeVelicleUsageController extends BaseController{
    
    @Autowired
    private TeeVehicleUsageService vehicleUsageService;

    /**
     * 新增或者更新
     * @throws ParseException
     */
    @RequestMapping("/addOrUpdate")
    @ResponseBody
    public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
        TeeJson json = new TeeJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.addOrUpdate(request, model);
        return json;
    }
    
    /**
     * 获取系统当前登录人 所有车辆申请记录
     * @date 2014-3-4
     * @author 
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getAllVelicleUsage")
    @ResponseBody
    public TeeJson getAllVelicleUsage(HttpServletRequest request ) throws ParseException {
        TeeJson json = new TeeJson();
        //将request中的对应字段映值射到目标对象的属性中
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.getAllVelicleUsage(request , model);
        return json;
    }
    

    /**
     * 获取所有会议室
     * 
     * @author syl
     * @date 2014-1-30
     * @param request
     * @param model
     * @return
     */
/*    @RequestMapping("/getAllVehicle")
    @ResponseBody
    public TeeJson getAllRoom(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        // json = vehicleUsageService.getAllRoom(request , model);
        return json;
    }*/

    /**
     * 获取所有会议室 --- 根据申请权限
     * 
     * @author syl
     * @date 2014-1-30
     * @param request
     * @param model
     * @return
     */
/*    @RequestMapping("/selectPostMeetRoom")
    @ResponseBody
    public TeeJson selectPostMeetRoom(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.selectPostMeetRoom(request, model);
        return json;
    }*/

    /**
     * 删除 byId
     * @date 2014-3-9
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/deleteById")
    @ResponseBody
    public TeeJson deleteById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.deleteById(request, model);
        return json;
    }

    /**
     * 删除 所有会议室
     * 
     * @author syl
     * @date 2014-1-30
     * @param request
     * @param model
     * @return
     */
/*    @RequestMapping("/deleteAll")
    @ResponseBody
    public TeeJson deleteAll(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.deleteAll(request, model);
        return json;
    }
*/
    /**
     * 根据sid查看详情
     * @date 2014-3-8
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getInfoById")
    @ResponseBody
    public TeeJson getInfoById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.getInfoByIdService(request, model);
        return json;
    }
    
    /**
     * 获取系统当前登录人  车辆申请记录    --- 根据车辆申请状态
     * @date 2014-3-9
     * @author 
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getPersonVehicleByStatus")
    @ResponseBody
    public TeeEasyuiDataGridJson getPersonVehicleByStatus(HttpServletRequest request,TeeDataGridModel dm ) throws ParseException {
    	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = vehicleUsageService.getPersonalMeetByStatus(person,model,dm);
        return json;
    }
    
    
 
    
    
    
    
    /**
     * 获取各种状态车辆申请总数
     * @date 2014-3-9
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getLeaderApproveCount")
    @ResponseBody
    public TeeJson getLeaderApproveCount(HttpServletRequest request ) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        //将request中的对应字段映值射到目标对象的属性中
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.getLeaderApproveCount(person , model);
        return json;
    }
    
    /**
     * 获取当前系统登录人  车辆申请审批记录    --- 根据车辆申请状态
     * @date 2014-3-9
     * @author 
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getApprovalVehicleByStatus")
    @ResponseBody
    public TeeEasyuiDataGridJson getApprovalVehicleByStatus(HttpServletRequest request,TeeDataGridModel dm ) throws ParseException {
    	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = vehicleUsageService.getApprovalVehicleByStatus(person,model,dm);
        return json;
    }
    
    /**
     * 审批管理
     * @date 2014-3-9
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/approval")
    @ResponseBody
    public TeeJson approval(HttpServletRequest request ) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        //将request中的对应字段映值射到目标对象的属性中
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.approvalService(person , model);
        return json;
    }
    
	 /**
	  * 归还
	  * @param request
	  * @return
	  */
    @RequestMapping("/toEnd")
    @ResponseBody
    public TeeJson toEnd(HttpServletRequest request ) {
    	TeeJson json = new TeeJson();
    	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
    	//将request中的对应字段映值射到目标对象的属性中
    	TeeVehicleUsageModel model = new TeeVehicleUsageModel();
    	TeeServletUtility.requestParamsCopyToObject(request, model);
    	json = vehicleUsageService.toEnd(person , model);
    	return json;
    }
    
    
    /**
     * 获取所有数据  byTime
     * @date 2014-3-9
     * @author 
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getAllVehicleByTime")
    @ResponseBody
    public TeeJson getAllVehicleByTime(HttpServletRequest request ) throws ParseException {
        TeeJson json = new TeeJson();
        //将request中的对应字段映值射到目标对象的属性中
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleUsageService.getAllVehicleByTime(request , model);
        return json;
    }
    
    
    /**
     * 车辆使用查询
     * @date 2014-3-10
     * @author 
     * @param dm
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/queryVehicleUsageList.action")
    @ResponseBody
    public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        TeeServletUtility.requestParamsCopyToObject(request, model);
        return vehicleUsageService.datagrid(dm,request , model);
    }
    
    
    /**
     * 拖拉更改车辆信息
     * @date 2014-2-15
     * @param request
     * @param model
     * @return
     * @throws Exception 
     */
    @RequestMapping("/updateChangeVehicle")
    @ResponseBody
    public TeeJson updateChangeMeet(HttpServletRequest request) throws Exception {
        TeeJson json = new TeeJson();
        try {
            TeeVehicleUsageModel model = new TeeVehicleUsageModel();
            TeeServletUtility.requestParamsCopyToObject(request, model);
            json = vehicleUsageService.updateChangeMeet(request , model);
        } catch (Exception e) {
            throw e;
        }
        
        return json;
    }
    
    @RequestMapping("/isApply")
    @ResponseBody
    public TeeJson isApply(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String vehicleId = TeeStringUtil.getString(request.getParameter("vehicleId"), "");        
        String vuStartStr = TeeStringUtil.getString(request.getParameter("vuStartStr"), ""); 
        json = vehicleUsageService.isApply(vehicleId,vuStartStr);
        return json;
    }
    
    
    @RequestMapping("/getRecords")
    @ResponseBody
    public TeeJson getRecords(HttpServletRequest request) {
    	TeeJson json = new TeeJson();
    	int vehicleId = TeeStringUtil.getInteger(request.getParameter("vehicleId"), 0);        
    	json = vehicleUsageService.getRecords(vehicleId);
    	return json;
    }
}
