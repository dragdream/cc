package com.tianee.oa.core.base.vehicle.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.vehicle.bean.TeeVehicleMaintenance;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleMaintenanceModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("vehicleMaintenanceDao")
public class TeeVehicleMaintenanceDao  extends TeeBaseDao<TeeVehicleMaintenance> {
	/**
	 * 增加
	 * @date 2014-3-17
	 * @author 
	 * @param vehicleMaintenance
	 */
	public void add(TeeVehicleMaintenance vehicleMaintenance) {
		save(vehicleMaintenance);
	}
	
	/**
	 *  查询
	 * @date 2014-3-17
	 * @author 
	 * @param id
	 * @return
	 */
	public TeeVehicleMaintenance getById(int id) {
	    TeeVehicleMaintenance intf = load(id);
		return intf;
	}
	
	
	
	/**
	 * 根据id单个删除
	 * @date 2014-3-8
	 * @author 
	 * @param id
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * 删除 所有车辆维护
	 * @date 2014-3-17
	 * @author
	 */
	public void delAll(){
		String hql = "delete from TeeVehicleMaintenance ";
		deleteOrUpdateByQuery(hql, null);
	}
	/**
	 * 多个删除
	 * @date 2014-3-8
	 * @author 
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeVehicleMaintenance where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * 查询所有记录
	 * @date 2014-3-17
	 * @author 
	 * @param person
	 * @param model
	 * @return
	 */
	public  List<TeeVehicleMaintenance> getAllVehicle(TeePerson person , TeeVehicleMaintenanceModel model) {
		Object[] values = { };
		String hql = "from TeeVehicleMaintenance order by buyDate";
		List<TeeVehicleMaintenance> list = (List<TeeVehicleMaintenance>) executeQuery(hql,values);
		return list;
	}	
	
	public long getQueryCount(TeePerson person , TeeVehicleMaintenanceModel model){
	    String hql = "select count(sid) from TeeVehicleMaintenance  where  1 = 1  ";
	    long count = count(hql, null);
        return count;
	    
	}
	
	
	
	  /**
	   * 分页查询
	   * @date 2014-3-17
	   * @author 
	   * @param firstResult
	   * @param pageSize
	   * @param dm
	   * @param model
	   * @return
	   * @throws ParseException
	   */
    public  List<TeeVehicleMaintenance> getMeetPageFind(int firstResult,int pageSize,TeeDataGridModel dm ,TeeVehicleMaintenanceModel model) throws ParseException { 
        String hql = "from TeeVehicleMaintenance  where  1 = 1  ";
        List list = new ArrayList();
        if(TeeUtility.isNullorEmpty(dm.getSort())){
            dm.setSort("createTime");
            dm.setOrder("desc");
        }
        hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
        return pageFindByList(hql, firstResult, pageSize, list);
    }

    
	public List<TeeVehicleMaintenance> getMaintenanceVehicle(int sid,String vuStartStr) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql=" from TeeVehicleMaintenance  main where  1 = 1 and  main.vehicle.sid=?";
		param.add(sid);
		hql+=" and main.vmRequestDate = ?";
		try {
			param.add(sf.parse(vuStartStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TeeVehicleMaintenance> list = executeQueryByList(hql, param);
		return list;
	}
	

}
