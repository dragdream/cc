package com.tianee.oa.core.base.vehicle.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.vehicle.bean.TeeVehicle;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("vehicleDao")
public class TeeVehicleDao extends TeeBaseDao<TeeVehicle>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeVehicle
	 */
	public void add(TeeVehicle attendOut) {
		save(attendOut);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeVehicle
	 */
	public void updateObj(TeeVehicle attendOut) {
		update(attendOut);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeVehicle loadById(int id) {
		TeeVehicle intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeVehicle getById(int id) {
		TeeVehicle intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 删除
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * 删除 所有会议室
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delAll(){
		String hql = "delete from TeeVehicle ";
		deleteOrUpdateByQuery(hql, null);
	}
	/**
	 * 删除 by Ids
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeVehicle where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeVehicle> getAllVehicle(TeePerson person , TeeVehicleModel model) {
		Object[] values = { };
		String hql = "from TeeVehicle order by buyDate";
		List<TeeVehicle> list = (List<TeeVehicle>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * @author syl
	 * 查询所有记录  ---  带申请权限
	 * @param 
	 */
	public  List<TeeVehicle> selectPostVehicle(TeePerson person , TeeVehicleModel model) {
		Object[] values = { person.getDept() , person};
		String hql = "from TeeVehicle  v where  ( exists (select 1 from v.postDept dept where dept =?) or exists (select 1 from v.postUser user where user= ? )) order by v.buyDate";
		List<TeeVehicle> list = (List<TeeVehicle>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * 校验名称的唯一性
	 * @date 2014年7月20日
	 * @author 
	 * @param sid
	 * @param nameStr
	 * @return
	 */
	public long getVehicleCountByNameDao(int sid,String nameStr){
		long counter = 0;
		Object[] values = {nameStr };
		String hql = "select count(sid) from TeeVehicle  where  vModel=?  ";
		if(sid>0){
			hql += " and sid<>" + sid;
		}
		counter = count(hql, values);
		return counter;
	}
	
	
	
}


	