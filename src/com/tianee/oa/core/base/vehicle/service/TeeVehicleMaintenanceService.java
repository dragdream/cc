package com.tianee.oa.core.base.vehicle.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.vehicle.bean.TeeVehicle;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleMaintenance;
import com.tianee.oa.core.base.vehicle.dao.TeeVehicleDao;
import com.tianee.oa.core.base.vehicle.dao.TeeVehicleMaintenanceDao;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleMaintenanceModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeVehicleMaintenanceService extends TeeBaseService {

    @Autowired
    private TeeVehicleMaintenanceDao vehicleMaintenanceDao;
    @Autowired
    private TeeSmsManager smsManager;
    @Autowired
    private TeeVehicleDao vehicleDao;
    @Autowired
    private TeePersonDao personDao;
    
    
    
    
    
    
    public TeeJson addOrUpdate(HttpServletRequest request, TeeVehicleMaintenanceModel model) throws ParseException {
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeJson json = new TeeJson();
        
        TeeVehicleMaintenance vehicleMaintenance = new TeeVehicleMaintenance();
        BeanUtils.copyProperties(model, vehicleMaintenance);
        int vehicleId = model.getVehicleId();
        TeeVehicle vehicle = vehicleDao.getById(vehicleId);
        
        if (vehicle != null) {
            if (model.getSid() > 0) {
            	TeeVehicleMaintenance dbVehicleMaintenance = vehicleMaintenanceDao.getById(model.getSid());
            	TeePerson vmPerson = personDao.get(model.getVmPersonId());
            	if(dbVehicleMaintenance != null){
            		dbVehicleMaintenance.setVehicle(vehicle);
            		dbVehicleMaintenance.setVmFee(model.getVmFee());
            		dbVehicleMaintenance.setVmReason(model.getVmReason());
            		dbVehicleMaintenance.setVmRemark(model.getVmRemark());
            		dbVehicleMaintenance.setVmType(model.getVmType());
            		dbVehicleMaintenance.setVmRequestDate(TeeUtility.parseDate(model.getVmRequestDateStr()));
            		dbVehicleMaintenance.setVmPerson(vmPerson);
            		vehicleMaintenanceDao.update(dbVehicleMaintenance);
            		
            	}
                
            }else {
                vehicleMaintenance.setCreateTime(new Date());
                vehicleMaintenance.setVehicle(vehicle);
                vehicleMaintenance.setVmType(model.getVmType());
                int userSid = model.getVmPersonId();
                vehicleMaintenance.setVmRequestDate(TeeUtility.parseDate("yyyy-MM-dd",model.getVmRequestDateStr()));
                TeePerson vmPerson = personDao.get(userSid);    
                if (vmPerson != null) {
                    vehicleMaintenance.setVmPerson(vmPerson);
                }
                vehicleMaintenanceDao.save(vehicleMaintenance);
            }
            
        }
       
        
        
        json.setRtState(true);
        return json;
    }
    
    
    /**
     * 通用列表
     * @param dm
     * @return
     * @throws ParseException 
     */
    @Transactional(readOnly = true)
    public TeeEasyuiDataGridJson getVehicleMaintenanceList(TeeDataGridModel dm,HttpServletRequest request , TeeVehicleMaintenanceModel model) throws ParseException {
        TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        j.setTotal(vehicleMaintenanceDao.getQueryCount(loginPerson ,model));// 设置总记录数
        int firstIndex = 0;
        firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
        Object parm[] = {};
        List<TeeVehicleMaintenance> list = vehicleMaintenanceDao.getMeetPageFind(firstIndex, dm.getRows(), dm, model);// 查
        List<TeeVehicleMaintenanceModel> modelList = new ArrayList<TeeVehicleMaintenanceModel>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                TeeVehicleMaintenanceModel modeltemp = parseModel(list.get(i));
                modelList.add(modeltemp);
            }
        }
        j.setRows(modelList);// 设置返回的行
        return j;
    }
    
    /**
     * 封装对象
     * @date 2014-3-17
     * @author 
     * @param obj
     * @return
     */
    public TeeVehicleMaintenanceModel parseModel(TeeVehicleMaintenance obj) {
        TeeVehicleMaintenanceModel model = new TeeVehicleMaintenanceModel();
        if (obj == null) {
            return model;
        }
        if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
            model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        if (!TeeUtility.isNullorEmpty(obj.getVmRequestDate())) {
            model.setVmRequestDateStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd")));
        }
        TeeVehicle vehicle = obj.getVehicle();
        if (vehicle != null) {
            model.setVehicleId(vehicle.getSid());
        }
        
        if (obj.getVmPerson() != null) {
            model.setVmPersonId(obj.getVmPerson().getUuid());
            model.setVmPersonName(obj.getVmPerson().getUserName());
        }
        if(obj.getVehicle() != null){
        	model.setVehicleName(obj.getVehicle().getvModel());
        }
        
        BeanUtils.copyProperties(obj, model);
        return model;
    }
    
    /**
     * 根据sid查看详情
     * @date 2014-3-8
     * @author 
     * @param request
     * @param model
     * @return
     */
    public TeeJson getInfoByIdService(HttpServletRequest request, TeeVehicleMaintenanceModel model) {
        TeeJson json = new TeeJson();
        if (model.getSid() > 0) {
            TeeVehicleMaintenance out = vehicleMaintenanceDao.getById(model.getSid());
            if (out != null) {
                model = parseModel(out);
                json.setRtData(model);
                json.setRtState(true);
                json.setRtMsg("查询成功!");
                return json;
            }
        }
        json.setRtState(false);
        return json;
    }
    
    /**
     * 删除车辆维护信息
     * @date 2014年4月7日
     * @author 
     * @param sids
     * @return
     */
    public TeeJson deleteObjByIdService(String sids){
    	TeeJson json = new TeeJson();
    	
    	vehicleMaintenanceDao.delByIds(sids);
    	json.setRtState(true);
        json.setRtMsg("删除成功!");
    	return json;
    }
    
    
    
    
    

}
