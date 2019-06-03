package com.tianee.oa.core.base.vehicle.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleUsageModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("vehicleUsageDao")
public class TeeVehicleUsageDao  extends TeeBaseDao<TeeVehicleUsage> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeVehicleUsage
	 */
	public void add(TeeVehicleUsage attendOut) {
		save(attendOut);
	}
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeVehicleUsage loadById(int id) {
		TeeVehicleUsage intf = load(id);
		return intf;
	}
	
	
	/**
	 * 查询
	 * @date 2014-3-8
	 * @author 
	 * @param id
	 * @return
	 */
	public TeeVehicleUsage getById(int id) {
		TeeVehicleUsage intf = get(id);
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
	 * 删除 所有车辆申请
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delAll(){
		String hql = "delete from TeeVehicleUsage ";
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
			String hql = "delete from TeeVehicleUsage where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeVehicleUsage> getAllVehicle(TeePerson person , TeeVehicleUsageModel model) {
		Object[] values = { };
		String hql = "from TeeVehicleUsage";
		List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * @author syl
	 * 查询所有记录  ---  带申请权限
	 * @param 
	 */
	public  List<TeeVehicleUsage> selectPost(TeePerson person , TeeVehicleUsageModel model) {
		Object[] values = { person.getDept() , person};
		String hql = "from TeeVehicleUsage  v where  ( exists (select 1 from mr.postDept dept where dept =?) or exists (select 1 from mr.postUser user where user= ? )) order by mr.buyDate";
		List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
		return list;
	}	

	
	   
    /**
     * 根据时间查询个人记录
     * @date 2014-3-6
     * @author 
     * @param person
     * @param startTime
     * @param endTime
     * @param vehicleSid
     * @return
     */
    public  List<TeeVehicleUsage> selectPersonalByTime(TeePerson person , Date startTime , Date endTime,int vehicleSid) {
        Object[] values = { endTime , startTime ,vehicleSid};
        String hql = "from TeeVehicleUsage where";
        hql = hql + "  vuStart < ?";
        hql = hql + " and vuEnd >= ?  ";
        hql = hql + " and vehicle.sid = ? ";
        
        hql = hql + " order by createTime";
        List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
        return list;
    }
	
	
	
    /**
     * 自动 查询  审批记录  -- “已批准” 和 “进行中”
     * @date 2014-3-9
     * @author 
     * @param person
     * @return
     */
    public  List<TeeVehicleUsage> getAutoLeaderVehicleByStatus(TeePerson person) {
        Object[] values = {person  };
        String hql = "from TeeVehicleUsage where vuProposer = ? and (status = 1 or  status = 2)  order by  createTime desc";
        List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
        return list;
    }
	
	
    /**
     * 自动 -- 更改会议状态  用于 “已审批” 改成 “进行中”  ； “进行中” 改成“结束”
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     */
    public void autoUpdateStatus(TeePerson person , TeeVehicleUsageModel model) {
        Object[] values = {model.getStatus() , model.getSid() };
        String hql = "update  TeeVehicleUsage  set status = ? where sid = ?";
        deleteOrUpdateByQuery(hql,values);
    }
	
    /**
     * 根据状态查询个人车辆申请信息
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public  List<TeeVehicleUsage> getPersonVehicleByStatus(TeePerson person, TeeVehicleUsageModel model) {
        Object[] values = {person  ,  model.getStatus()};
        String hql = "from TeeVehicleUsage where vuProposer = ? and status = ? order by  createTime desc";
        if(model.getStatus()==2){
        	hql = "from TeeVehicleUsage where vuProposer = ? and (status = ? or status=5) order by  createTime desc";
        }
        List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
        return list;
    }
    
    
    /**
     * 根据调度员和车辆状态获取总数
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public  long getLeaderApproveCount(TeePerson person , TeeVehicleUsageModel model) {
        Object[] values = {person.getUuid()  ,  model.getStatus()};
        String hql = "select count(sid) from TeeVehicleUsage  where vuOperator.uuid = ? and status = ?  ";
        if(model.getStatus()==2){
        	hql = "select count(sid) from TeeVehicleUsage  where vuOperator.uuid = ? and (status = ? or status = 5) ";
        }
        long count = count(hql, values);
        return count;
    }
    
    /**
     * 根据调度员、状态查询个人车辆申请信息
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public  List<TeeVehicleUsage> getApprovalVehicleByStatus(TeePerson person, TeeVehicleUsageModel model) {
        Object[] values = {person  ,  model.getStatus()};
        String hql = "from TeeVehicleUsage where vuOperator = ? and status = ? order by  createTime desc";
        if(model.getStatus()==2){
        	hql = "from TeeVehicleUsage where vuProposer = ? and (status = ? or status=5) order by  createTime desc";
        }
        List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
        return list;
    }
    
    /**
     * 根据时间获取车辆使用情况
     * @date 2014-3-9
     * @author 
     * @param startTime
     * @param endTime
     * @return
     */
    public  List<TeeVehicleUsage> getAllVehicleUsageByTime(Date startTime , Date endTime) {
        Object[] values = { endTime , startTime};
        String hql = "from TeeVehicleUsage where ";
        hql = hql + "  vuStart < ?";
        hql = hql + " and vuEnd >= ?  ";
        hql = hql + " order by createTime";
        List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
        return list;
    }
    
    /**
     * 通用列表        查询数量
     * @date 2014-3-10
     * @author 
     * @param person
     * @param model
     * @return
     * @throws ParseException
     */
    public  long getQueryCount(TeePerson person , TeeVehicleUsageModel model) throws ParseException {
        List list = new ArrayList();
        String hql = "select count(sid) from TeeVehicleUsage  where  1 = 1  ";
        if(!TeeUtility.isNullorEmpty(model.getVehicleName())){//名称
            hql = hql + " and vehicle.vModel like  ?";
            list.add("%" + model.getVehicleName() + "%");
        }
        
        if(model.getVuProposerId()!=0){//申请人
            hql = hql + " and vuProposer.uuid = ?";
            list.add(TeeStringUtil.getInteger(model.getVuProposerId(),0));
        }
        
        if(!TeeUtility.isNullorEmpty(model.getVuStartStr())){//开始时间
            Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm", model.getVuStartStr());
            hql = hql + " and vuStart >= ?";
            list.add(date);
        }
        
        if(!TeeUtility.isNullorEmpty(model.getVuEndStr())){//开始时间
            Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm", model.getVuEndStr());
            hql = hql + " and vuEnd <= ? ";
            list.add(date);
        }
        
        if(model.getVehicleId() != 0){//车辆
            hql = hql + " and vehicle.sid = ?";
            list.add(TeeStringUtil.getInteger(model.getVehicleId(),0));
        }
        
        if(model.getStatus() > 0){//状态
            hql = hql + " and status = ?";
            list.add(model.getStatus()  - 1);
        }
        long count = countByList(hql, list);
        return count;
    }   
    
    
    /**
     * 分页查询
     * @date 2014-3-10
     * @author 
     * @param firstResult
     * @param pageSize
     * @param dm
     * @param model
     * @return
     * @throws ParseException
     */
    public  List<TeeVehicleUsage> getMeetPageFind(int firstResult,int pageSize,TeeDataGridModel dm ,TeeVehicleUsageModel model) throws ParseException { 
        String hql = "from TeeVehicleUsage  where  1 = 1  ";
        List list = new ArrayList();
        if(!TeeUtility.isNullorEmpty(model.getVehicleName())){//名称
            hql = hql + " and vehicle.vModel like  ?";
            list.add("%" + model.getVehicleName() + "%");
        }
        
        if(model.getVuProposerId()!=0){//申请人
            hql = hql + " and vuProposer.uuid = ?";
            list.add(TeeStringUtil.getInteger(model.getVuProposerId(),0));
        }
        
        if(!TeeUtility.isNullorEmpty(model.getVuStartStr())){//开始时间
            Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm", model.getVuStartStr());
            hql = hql + " and vuStart >= ?";
            list.add(date);
        }
        
        if(!TeeUtility.isNullorEmpty(model.getVuEndStr())){//开始时间
            Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm", model.getVuEndStr());
            hql = hql + " and vuEnd <= ? ";
            list.add(date);
        }
        
        if(model.getVehicleId() != 0){//车辆
            hql = hql + " and vehicle.sid = ?";
            list.add(TeeStringUtil.getInteger(model.getVehicleId(),0));
        }
        
        if(model.getStatus() > 0){//状态
            hql = hql + " and status = ?";
            list.add(model.getStatus()  - 1);
        }
        if(TeeUtility.isNullorEmpty(dm.getSort())){
            dm.setSort("vuStart");
            dm.setOrder("desc");
        }
        hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
        return pageFindByList(hql, firstResult, pageSize, list);
    }
    
    /**
     * 判断申请的车辆是否正在使用
     * @param person
     * @param model
     * @return
     */
    public  List<TeeVehicleUsage> getUsingVehicle(int sid,String vuStartStr) {
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	try{
    		Object[] values = {sid,sf.parse(vuStartStr),sf.parse(vuStartStr) };
    		String hql = "from TeeVehicleUsage where vehicle.sid = ? and status in(1,2,5)  and vuStart < ? and vuEnd>?";
    		List<TeeVehicleUsage> list = (List<TeeVehicleUsage>) executeQuery(hql,values);
    		return list;
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return null;
    }
    
  
}
