package com.tianee.oa.mobile.vehicle.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleUsageModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.vehicle.dao.TeeMobileVehicleUsageDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeMobileVehicleUsageService extends TeeBaseService{

	 @Autowired
	 private TeeMobileVehicleUsageDao mobileVehicleUsageDao;
	
	/**
	 * 获取当前登录人所有的车辆申请（所有状态）
	 * @param person
	 * @return
	 */
	public TeeEasyuiDataGridJson getPersonVehicleAllStatus(TeePerson person,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		
		
		 String hql = " from TeeVehicleUsage where vuProposer = ? order by  createTime desc";    
		 List param = new ArrayList();
		 param.add(person);
		// 设置总记录数
		j.setTotal(mobileVehicleUsageDao.countByList("select count(*) " + hql, param));// 设置总记录数

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeVehicleUsage> list = mobileVehicleUsageDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
        List<TeeVehicleUsageModel> listModel = new ArrayList<TeeVehicleUsageModel>();
        if (list != null && list.size()>0) {
            for(TeeVehicleUsage vehicleUsage:list){
                listModel.add(parseModel(vehicleUsage) );  
            }
        }
        j.setRows(listModel);
        return j;
	}
	
	
	
	/**
     * 对象转换
     * 
     * @author syl
     * @date 2014-1-29
     * @param out
     * @return
     */
    public TeeVehicleUsageModel parseModel(TeeVehicleUsage vehicleUsage) {
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        if (vehicleUsage == null) {
            return model;
        }
        if (vehicleUsage.getVehicle() != null) {
            model.setVehicleId(vehicleUsage.getVehicle().getSid());
            model.setVehicleName(vehicleUsage.getVehicle().getvModel());
        }
        if (vehicleUsage.getVuProposer() != null) {
            model.setVuProposerName(vehicleUsage.getVuProposer().getUserName());
        }
        if (vehicleUsage.getVuUser() != null) {
            model.setVuUserName(vehicleUsage.getVuUser().getUserName());
        }
        if (vehicleUsage.getCreateTime() != null) {
            model.setCreateTimeStr(TeeUtility.getDateTimeStr(vehicleUsage.getCreateTime()));
        }
        if (vehicleUsage.getVuStart() != null) {
            model.setVuStartStr(TeeUtility.getDateStrByFormat(vehicleUsage.getVuStart(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
        }
        if (vehicleUsage.getVuEnd() != null) {
            model.setVuEndStr(TeeUtility.getDateStrByFormat(vehicleUsage.getVuEnd(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
        }
        if (vehicleUsage.getVuOperator() != null) {
            model.setVuOperatorName(vehicleUsage.getVuOperator().getUserName());
        }
        BeanUtils.copyProperties(vehicleUsage, model);

        /*
         * String[] personInfo =
         * personDao.getPersonNameAndUuidByUuids(room.getManagerIds());
         * model.setManagerIds(personInfo[0]);
         * model.setManagerNames(personInfo[1]);
         */
        return model;
    }



    /**
     * 获取由当前登录人审批的所有的车辆申请
     * @param person
     * @param dm
     * @return
     */
	public TeeEasyuiDataGridJson getApprovalVehicleAllStatus(TeePerson person,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        
        
        String hql = "from TeeVehicleUsage where vuOperator = ?  order by  createTime desc";
       
    	List param = new ArrayList();
		param.add(person);
		// 设置总记录数
		json.setTotal(mobileVehicleUsageDao.countByList("select count(*) " + hql, param));// 设置总记录数

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeVehicleUsage> list = mobileVehicleUsageDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
        
        List<TeeVehicleUsageModel> listModel = new ArrayList<TeeVehicleUsageModel>();
        if (list != null && list.size()>0) {
            for(TeeVehicleUsage vehicleUsage:list){
                listModel.add(parseModel(vehicleUsage) );  
            }
        }
        json.setRows(listModel);
        return json;
	}
}
