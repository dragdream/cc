package com.tianee.oa.core.base.calendar.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.calendar.bean.TeeCalendarAffair;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;



/**
 * 
 * @author syl
 *
 */
@Repository("calendarDao")
public class TeeCalendarAffairDao extends TeeBaseDao<TeeCalendarAffair> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeCalendar
	 */
	public void addCalendar(TeeCalendarAffair cal) {
		save(cal);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeCalendar
	 */
	public void updateCalendar(TeeCalendarAffair cal) {
		update(cal);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeCalendarAffair loadById(int id) {
		TeeCalendarAffair intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeCalendarAffair getById(int id) {
		TeeCalendarAffair intf = get(id);
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
			String hql = "delete from TeeCalendarAffair where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeCalendarAffair> select() {
		Object[] values = null;
		String hql = "from TeeCalendarAffair";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * 查询  byTime  根据 日、月、周视图
	 * @author syl
	 * @date 2013-12-24
	 * @return
	 * @throws ParseException 
	 */
	public  List<TeeCalendarAffair> selectByTime(TeePerson user , String stratTime , String EndTime) throws ParseException {
		String hql = "from TeeCalendarAffair tcal where tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?))";
		List values = new ArrayList();
		values.add(user);
		values.add(user);
		Date endDate = TeeUtility.parseDate(EndTime + " 23:59:59");
		Date startDate = TeeUtility.parseDate(stratTime + " 00:00:00");  
		//hql = hql + " and tcal.startTime < ?";
		hql = hql + " and ((tcal.startTime>= ? and tcal.endTime<= ?)";
		values.add(startDate.getTime());
		values.add(endDate.getTime());
		hql = hql +" or (tcal.startTime<= ? and tcal.endTime>= ?)";
		values.add(startDate.getTime());
		values.add(endDate.getTime());
		hql = hql +" or (tcal.endTime<= ? and tcal.endTime>= ?)";
		values.add(endDate.getTime());
		values.add(startDate.getTime());
		hql = hql +" or (tcal.startTime>= ? and tcal.startTime<= ?))";
		values.add(startDate.getTime());
		values.add(endDate.getTime());
		/*hql = hql + " and (tcal.endTime >= ? or tcal.endTime = 0)  ";
		values.add(startDate.getTime());*/
		hql = hql + " order by tcal.startTime";
	
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQueryByList(hql,values);
		return list;
	}
	
	/**
	 * 查询  byTime  根据 日、月、周视图 
	 * @author syl
	 * @date 2013-12-24
	 * @return
	 * @throws ParseException 
	 */
	public  long selectCountByTime(TeePerson user , String stratTime , String EndTime) throws ParseException {
		String hql = "select count(*) from TeeCalendarAffair tcal where (tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?)) ) ";
		List values = new ArrayList();
		values.add(user);
		values.add(user);
		Date endDate = TeeUtility.parseDate(EndTime + " 23:59:59");
		hql = hql + " and tcal.startTime <= ?";
		values.add(endDate.getTime());
		/*Date startDate = TeeUtility.parseDate(stratTime + " 00:00:00");  
		hql = hql + " and (tcal.endTime >= ? or tcal.endTime = 0)  ";
		values.add(startDate.getTime());*/
		
		hql = hql + " and tcal.overStatus = 0 and tcal.calAffType = 0 ";
	
		long count = countByList(hql,values);
		return count;
	}
	
	
	/**
	 * 根据条件查询 个人日程
	 * @author syl
	 * @date 2014-1-3
	 * @param userId  用户登录Id
	 * @param model  
	 * @return
	 */
	public  List<TeeCalendarAffair> queryCal(TeePerson user , TeeCalendarAffairModel model ){
		String overStatus = model.getOverStatus() +"";
		String hql = "from TeeCalendarAffair tcal where tcal.calAffType = 0 and(tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?)))";
		List values = new ArrayList();
		values.add(user);
		values.add(user);
		if(model.getStartDate() != null){//开始时间
			 hql = hql + " and tcal.endTime >= ?";
			 values.add(model.getStartDate().getTimeInMillis());
		}
		 
		if(model.getEndDate() != null){//结束时间 
			 hql = hql + " and tcal.startTime <= ?";
			 values.add(model.getEndDate().getTimeInMillis());
		}
		if(model.getCalType() > 0){
			 hql = hql + " and tcal.calType = ?";
			 values.add(model.getCalType());
		}

   
	    if(!overStatus.equals("")){
	      if(overStatus.equals("1")){//未开始的
	        hql = hql + " and tcal.startTime >= ? and tcal.overStatus='0'";
	        values.add(new Date().getTime());
	      }
	      if(overStatus.equals("2")){//进行中的
	        hql = hql + " and tcal.startTime <= ? and (tcal.endTime >=? or tcal.endTime = 0)  and tcal.overStatus='0'";
	        values.add(new Date().getTime());
	        values.add(new Date().getTime());
	      }
	      if(overStatus.equals("3")){//已超时
	      /*  String temp = T9DBUtility.getDateFilter("END_TIME", dateStr, "<=");
	        sql = sql + " and " + temp + " and OVER_STATUS='0'";*/
	        
	        hql = hql + " and tcal.endTime <= ? and tcal.overStatus='0'";
	        values.add(new Date().getTime());
	      }
	      if(overStatus.equals("4")){
	    	  hql = hql + "  and tcal.overStatus='1'";
	      }
	    }
		if(!TeeUtility.isNullorEmpty(model.getContent())){

    		hql = hql + " and tcal.content like ?";
    		values.add("%" + model.getContent() + "%");
		}
		hql = hql + " order by startTime desc";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQueryByList(hql,values);
		return list;
	}
	
	
	/**
	 * 根据条件查询 日程安排  --- 领导日程查询
	 * @author syl
	 * @date 2014-1-3
	 * @param userId  用户登录Id
	 * @param model  
	 * @param map 存放管理权限
	 * @return
	 */
	public  List<TeeCalendarAffair> leaderQueryCal(TeePerson person , String userIdStr, TeeCalendarAffairModel model , Map map){
		String overStatus = model.getOverStatus() +"";
		String hql = "from TeeCalendarAffair tcal where tcal.calAffType = 0 and (tcal.user.uuid = ? or (exists (select 1 from tcal.actor actor where actor.uuid=?)))";
		List values = new ArrayList();
		boolean bingPerson = (Boolean) map.get("bingPerson");
		List<TeeDepartment> deptList = (List<TeeDepartment>)map.get("deptList");//管理范围部门
		List<TeePerson> personList = (List<TeePerson>)map.get("personList");//管理范围人员
		boolean isSuperAdmin = (Boolean) map.get("isSuperAdmin");//是否超级管理员
		String personIds = "";
		for(TeePerson p :personList ){
			personIds = personIds + p.getUuid() + ",";
		}
		if(personIds.endsWith(",")){
			personIds = personIds.substring(0, personIds.length() -1);
		}	
		if(personIds.equals("")){
			personIds = "0";
		}
		if(TeeUtility.isNullorEmpty(userIdStr)){//为空
			if(isSuperAdmin){
				hql = "from TeeCalendarAffair tcal where tcal.calAffType = 0";
			}else{
				hql = "from TeeCalendarAffair tcal where tcal.calAffType = 0 and (tcal.user.uuid in("+personIds+") or (exists (select 1 from tcal.actor actor where actor.uuid in("+personIds+"))))";
				/*values.add("1,2");
				values.add("1,2");*/
			}
		}else{
			int userId = TeeStringUtil.getInteger(userIdStr, 0);
			if(userId > 0){//按人员查询
				hql = "from TeeCalendarAffair tcal where tcal.calAffType = 0 and(tcal.user.uuid = ? or (exists (select 1 from tcal.actor actor where actor.uuid=?)))";
				values.add(userId);
				values.add(userId);
			}else{//按部门查询
				hql = "from TeeCalendarAffair tcal where tcal.calAffType = 0 and(tcal.user.dept.uuid = ? or (exists (select 1 from tcal.actor actor where actor.dept.uuid=?)))";
				values.add(TeeStringUtil.getInteger(userIdStr.split(";")[0], 0));
				values.add(TeeStringUtil.getInteger(userIdStr.split(";")[0] , 0 ));
				if(!isSuperAdmin){//不是超级管理员，走权限
					hql = hql + " and (exists (select 1 from tcal.actor actor where actor.uuid in("+personIds+")))";
					//values.add(personList);
				}
			}
		}
	
		if(model.getStartDate() != null){//开始时间
			 hql = hql + " and tcal.endTime >= ?";
			 values.add(model.getStartDate().getTimeInMillis());
		}
		 
		if(model.getEndDate() != null){//结束时间 
			 hql = hql + " and tcal.startTime <= ?";
			 values.add(model.getEndDate().getTimeInMillis());
		}
		if(model.getCalType() > 0){
			 hql = hql + " and tcal.calType = ?";
			 values.add(model.getCalType());
		}

   
	    if(!overStatus.equals("")){
	      if(overStatus.equals("1")){//未开始的
	        hql = hql + " and tcal.startTime >= ? and tcal.overStatus='0'";
	        values.add(new Date().getTime());
	      }
	      if(overStatus.equals("2")){//进行中的
	        hql = hql + " and tcal.startTime <= ? and (tcal.endTime >=? or tcal.endTime = 0)  and tcal.overStatus='0'";
	        values.add(new Date().getTime());
	        values.add(new Date().getTime());
	      }
	      if(overStatus.equals("3")){//已超时
	      /*  String temp = T9DBUtility.getDateFilter("END_TIME", dateStr, "<=");
	        sql = sql + " and " + temp + " and OVER_STATUS='0'";*/
	        
	        hql = hql + " and tcal.endTime <= ? and tcal.overStatus='0'";
	        values.add(new Date().getTime());
	      }
	      if(overStatus.equals("4")){
	    	  hql = hql + "  and tcal.overStatus='1'";
	      }
	    }
		if(!TeeUtility.isNullorEmpty(model.getContent())){
    		hql = hql + " and tcal.content like ?";
    		values.add("%" + model.getContent() + "%");
		}
		hql = hql + " and tcal.calType = 1";  
		hql = hql + " order by tcal.startTime desc";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQueryByList(hql,values);
		return list;
	}
	
	
	
	/**
	 * 领导日程查询 ---
	 * @author syl
	 * @date 2014-1-12
	 * @param userIds
	 * @param startTime
	 * @param endTime
	 * @param status  状态  0- 全部  1- 未进行 2 进行中 3已超时 4 已完成
	 * @return
	 * @throws Exception
	 */
	public List<TeeCalendarAffair> selectLeaderCalendar(String userIds,
			long startTime, long endTime, String status) throws Exception {
		List<TeeCalendarAffair> calendarList = new ArrayList<TeeCalendarAffair>();
		if (userIds.endsWith(",")) {
			userIds = userIds.substring(0, userIds.length() - 1);
		}
		String hql = "from TeeCalendarAffair tcal where tcal.calType = 1  and   (tcal.user.uuid in ("
				+ userIds
				+ ")  or (exists (select 1 from tcal.actor actor where actor.uuid in ("
				+ userIds + "))))";
		List values = new ArrayList();

		//Date endDate = TeeUtility.parseDate(endTime + " 00:00:00");
		hql = hql + " and tcal.startTime <= ?";
		values.add(endTime);
		//Date startDate = TeeUtility.parseDate(startTime + " 00:00:00");
		hql = hql + " and (tcal.endTime >= ? or tcal.endTime = 0)  ";
		values.add(startTime);
		if (!TeeUtility.isNullorEmpty(status)) {
			if (status.equals("1")) {// 未开始的
				hql = hql + " and tcal.startTime >= ? and tcal.overStatus='0'";
				values.add(new Date().getTime());
			}
			if (status.equals("2")) {// 进行中的
				hql = hql + " and tcal.startTime <= ? and (tcal.endTime >=? or tcal.endTime = 0)  and tcal.overStatus='0'";
				values.add(new Date().getTime());
				values.add(new Date().getTime());
			}
			if (status.equals("3")) {// 已超时
				hql = hql + " and tcal.endTime <= ? and tcal.overStatus='0'";
				values.add(new Date().getTime());
			}
			if (status.equals("4")) {
				hql = hql + "  and tcal.overStatus='1'";
			}
		}
		hql = hql + " order by tcal.startTime ";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQueryByList(
				hql, values);
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取需要周期性事务 ----短信定时提醒
	 * @author syl
	 * @date 2014-2-11
	 * @param model
	 * @param person
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public  List<TeeCalendarAffair> getDesktop(TeeCalendarAffairModel  model, TeePerson person , long startTime , long endTime) {
		List values = new ArrayList();
		String hql = "from TeeCalendarAffair tcal where  (tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?)))";
		values.add(person);
		values.add(person);
		if(!TeeUtility.isNullorEmpty(endTime)){
			values.add(endTime);
			hql = hql + " and tcal.startTime <= ?";
		}
		if(!TeeUtility.isNullorEmpty(startTime)){
			values.add(startTime);
			hql = hql + " and (tcal.endTime >= ? or tcal.endTime =0 ) ";//结束时间大于等于（起始时间） 或者结束时间为空
		}

		
		
		hql  = hql + "  order by tcal.startTime";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQueryByList(hql, values);
		return list;
	}

}


