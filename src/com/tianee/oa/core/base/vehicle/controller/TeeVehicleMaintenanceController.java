package com.tianee.oa.core.base.vehicle.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.vehicle.model.TeeVehicleMaintenanceModel;
import com.tianee.oa.core.base.vehicle.service.TeeVehicleMaintenanceService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 车辆维护
 * 
 * @author wyw
 * 
 */
@Controller
@RequestMapping("/vehicleMaintenanceController")
public class TeeVehicleMaintenanceController extends BaseController {

    @Autowired
    private TeeVehicleMaintenanceService vehicleMaintenanceService;
    
    /**
     * 新增或者更新
     * @throws ParseException
     */
    @RequestMapping("/addOrUpdate")
    @ResponseBody
    public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
        TeeJson json = new TeeJson();
        TeeVehicleMaintenanceModel model = new TeeVehicleMaintenanceModel();
        
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleMaintenanceService.addOrUpdate(request, model);
        return json;
    }
    
    
    

    /**
     * 车辆维护查询
     * @date 2014-3-18
     * @author 
     * @param dm
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getVehicleMaintenanceList.action")
    @ResponseBody
    public TeeEasyuiDataGridJson getVehicleMaintenanceList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException {
        TeeVehicleMaintenanceModel model = new TeeVehicleMaintenanceModel();
        TeeServletUtility.requestParamsCopyToObject(request, model);
        return vehicleMaintenanceService.getVehicleMaintenanceList(dm, request, model);
    }

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
        TeeVehicleMaintenanceModel model = new TeeVehicleMaintenanceModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = vehicleMaintenanceService.getInfoByIdService(request, model);
        return json;
    }
    
    
    /**
     * 删除车辆维护信息
     * @date 2014年4月7日
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/deleteObjById")
    @ResponseBody
    public TeeJson deleteObjById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String sidStr = request.getParameter("sids");
        json = vehicleMaintenanceService.deleteObjByIdService(sidStr);
        return json;
    }
    
    
    
    
}
