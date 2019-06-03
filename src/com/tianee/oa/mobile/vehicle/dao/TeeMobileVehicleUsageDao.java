package com.tianee.oa.mobile.vehicle.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository("mobileVehicleUsageDao")
public class TeeMobileVehicleUsageDao extends TeeBaseDao<TeeVehicleUsage>{

	
	  /**
     * 获取当前登录人的所有的车辆申请
     * @param person
     * @return
     */
	public List<TeeVehicleUsage> getPersonVehicleAllStatus(TeePerson person) {
		 Object[] values = {person};
	        String hql = " from TeeVehicleUsage where vuProposer = ? order by  createTime desc";      
	        List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
	        return list;
	}
}
