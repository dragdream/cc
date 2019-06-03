package com.tianee.oa.core.base.attend.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendEvection;
import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOvertime;
import com.tianee.oa.core.base.attend.model.TeeAttendEvectionModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOvertimeModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author ny
 *
 */
@Repository("attendOvertimeDao")
public class TeeAttendOvertimeDao  extends TeeBaseDao<TeeAttendOvertime> {
	/**
	 * @author ny
	 * 增加
	 * @param TeeAttendOvertime
	 */
	public void addAttendOvertime(TeeAttendOvertime attendOvertime) {
		save(attendOvertime);
	}
	
	/**
	 * @author ny
	 * 更新
	 * @param TeeAttendOvertime
	 */
	public void updateAttendOvertime(TeeAttendOvertime attendOvertime) {
		update(attendOvertime);
	}
	/**
	 * @author ny
	 * byId 查询
	 * @param 
	 */
	public TeeAttendOvertime loadById(int id) {
		TeeAttendOvertime intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author ny
	 * byId 查询
	 * @param 
	 */
	public TeeAttendOvertime getById(int id) {
		TeeAttendOvertime intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author ny
	 * byId 删除
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	/**
	 * 删除 by Ids
	 * 
	 * @author ny
	 * @date 2014-5-23
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeAttendOvertime where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author ny
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeAttendOvertime> selectPersonalOvertime(TeePerson person , TeeAttendOvertimeModel model) {
		if(model.getStatus()==9){
			Object[] values = {person};
			String hql = "from TeeAttendOvertime where user = ? and allow = 1  order by  createTime desc";
			List<TeeAttendOvertime> list = (List<TeeAttendOvertime>) executeQuery(hql,values);
			return list;
		}else{
			Object[] values = {person , model.getStatus()};
			String hql = "from TeeAttendOvertime where user = ? and status = ?  order by  createTime desc";
			List<TeeAttendOvertime> list = (List<TeeAttendOvertime>) executeQuery(hql,values);
			return list;
		}
	
	}	
	
	public  List<TeeAttendOvertime> selectPersonalOvertimePage(TeePerson person ,int firstResult,int pageSize,TeeDataGridModel dm , TeeAttendOvertimeModel model)throws ParseException{
		List list = new ArrayList();
		if(model.getStatus()==9){
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			String hql = "from TeeAttendOvertime where user = ? and allow = 1  order by  createTime desc";
			return (List<TeeAttendOvertime>) pageFindByList(hql, firstResult, pageSize, list);
		}else{
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			if(TeeUtility.isNullorEmpty(model.getStatus())){
				list.add(model.getStatus());
			}
			String hql = "from TeeAttendOvertime where user = ? and status = ?  order by  createTime desc";
			return (List<TeeAttendOvertime>) pageFindByList(hql, firstResult, pageSize, list);
		}
	}	
	
	public  long selectPersonalOvertimeCount(TeePerson person, TeeAttendOvertimeModel model)throws ParseException{
		List list = new ArrayList();
		if(model.getStatus()==9){
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			String hql = "select count(sid) from TeeAttendOvertime where user = ? and allow = 1";
			long count = countByList(hql, list);
			return count;
		}else{
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			if(TeeUtility.isNullorEmpty(model.getStatus())){
				list.add(model.getStatus());
			}
			String hql = "select count(sid) from TeeAttendOvertime where user = ? and status = ?";
			long count = countByList(hql, list);
			return count;
		}
	}	
	
	
	
	/*外出审批管理*/
	
	/**
	 * 获取待审批记录
	 * @author ny
	 * @date 2014-5-23
	 * @param person
	 * @param model
	 * @return
	 */
	public  List<TeeAttendOvertime> getManagerOvertime(TeePerson person , TeeAttendOvertimeModel model) {
		Object[] values = {person , model.getStatus() , model.getAllow()};
		String hql = "from TeeAttendOvertime where leader = ? and status = ? and allow = ? order by  createTime desc";
		List<TeeAttendOvertime> list = (List<TeeAttendOvertime>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * 获取已审批记录
	 * @author ny
	 * @date 2014-5-23
	 * @param person
	 * @param model
	 * @return
	 */
	public  List<TeeAttendOvertime> getManagerApprovOvertime(TeePerson person , TeeAttendOvertimeModel model) {
		Object[] values = {person , model.getAllow()};
		String hql = "from TeeAttendOvertime where leader = ? and allow <> ? order by  createTime desc";
		List<TeeAttendOvertime> list = (List<TeeAttendOvertime>) executeQuery(hql,values);
		return list;
	}	
	/**
	 * 审批
	 * @author ny
	 * @date 2014-5-23
	 * @param person
	 * @param model
	 * @return
	 */
	public  void approve(TeePerson person , TeeAttendOvertimeModel model) {
		Object[] values = {model.getAllow() , model.getReason() ,model.getSid() };
		String hql="";
		if(model.getAllow()==1){
			hql= "update TeeAttendOvertime set allow = ? , reason = ? ,status=1 where sid = ?";
		}else{
		    hql = "update TeeAttendOvertime set allow = ? , reason = ?  where sid = ?";
		}
		deleteOrUpdateByQuery(hql, values);
	}	
	
	
	
	
	/**
	 * 获取待审批 记录数量
	 * @author ny
	 * @date 2014-5-23
	 * @param person
	 * @param model
	 * @return
	 */
	public  long getLeaderCount(TeePerson person , TeeAttendOvertimeModel model) {
		Object[] values = {person , model.getStatus() , model.getAllow()};
		String hql = "select count(*) from TeeAttendOvertime where leader = ? and status = ? and allow = ? order by  createTime desc";
		long count = count(hql, values);
		return count;
	}	
	
	
	/**
	 * 个人桌面模块
	 * @author ny
	 * @date 2014-5-23
	 * @param person
	 * @param model
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<TeeAttendOvertime> getDeskTop(TeePerson person , TeeAttendOvertimeModel model , long startTime , long endTime){
		Object[] values = { model.getStatus() , model.getAllow() , startTime};
		String hql = "from TeeAttendOvertime where  status = ? and allow = ?  and submitTime =?    order by  createTime desc";
		List<TeeAttendOvertime> list = (List<TeeAttendOvertime>) executeQuery(hql,values);
		return list;
	}

	/**
	 * 根据条件统计加班记录
	 * @param deptId
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws Exception 
	 */
	public List<TeeAttendOvertime> getOvertimeByCondition(String deptIds,String startDateDesc, String endDateDesc) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendOvertime over where over.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and over.user.dept.uuid in ("+deptIds+") ";
		}
		
        Calendar cl1=null;//开始统计时间
		Calendar cl2=null;//结束统计时间
		
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));	
		}
		
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
		}
		long s=cl1.getTimeInMillis();
		long e=cl2.getTimeInMillis();
		//开始时间小于 统计开始时间   结束时间大于 统计结束时间
		//开始时间大于 统计开始时间    结束时间小于  统计结束时间
		//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
		//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
		hql+=" and ((startTime<? and endTime>?) or (startTime>? and endTime<?) or (startTime<? and endTime>? and endTime<?) or(startTime>? and startTime<? and endTime>?))";
		param.add(s);
		param.add(e);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(e);
		param.add(e);
		
		hql+=" order by over.user.dept.deptNo asc,over.user.uuid asc,over.createTime asc group by over.user.uuid";
		List<TeeAttendOvertime> list = (List<TeeAttendOvertime>) executeQueryByList(hql,param);
		return list;
	}
	
	public  List<TeeAttendOvertime> getOvertimeByConditionPage(String deptIds,
			String startDateDesc, String endDateDesc, int firstResult,int pageSize,TeeDataGridModel dm)throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendOvertime over where over.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and over.user.dept.uuid in ("+deptIds+") ";
		}
        Calendar cl1=null;//开始统计时间
		Calendar cl2=null;//结束统计时间
		
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));	
		}
		
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
		}
		long s=cl1.getTimeInMillis();
		long e=cl2.getTimeInMillis();
		//开始时间小于 统计开始时间   结束时间大于 统计结束时间
		//开始时间大于 统计开始时间    结束时间小于  统计结束时间
		//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
		//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
		hql+=" and ((startTime<? and endTime>?) or (startTime>? and endTime<?) or (startTime<? and endTime>? and endTime<?) or(startTime>? and startTime<? and endTime>?))";
		param.add(s);
		param.add(e);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(e);
		param.add(e);
		hql+=" order by over.user.dept.deptNo asc,over.user.uuid asc,over.createTime asc group by over.user.uuid";
		return (List<TeeAttendOvertime>) pageFindByList(hql, firstResult, pageSize, param);
		
	}
	
	public long getOvertimeByConditionCount(String deptIds,
			String startDateDesc, String endDateDesc) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="select count(sid) from TeeAttendOvertime over where over.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and over.user.dept.uuid in ("+deptIds+")";
		}
        Calendar cl1=null;//开始统计时间
		Calendar cl2=null;//结束统计时间
		
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));	
		}
		
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
		}
		long s=cl1.getTimeInMillis();
		long e=cl2.getTimeInMillis();
		//开始时间小于 统计开始时间   结束时间大于 统计结束时间
		//开始时间大于 统计开始时间    结束时间小于  统计结束时间
		//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
		//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
		hql+=" and ((startTime<? and endTime>?) or (startTime>? and endTime<?) or (startTime<? and endTime>? and endTime<?) or(startTime>? and startTime<? and endTime>?))";
		param.add(s);
		param.add(e);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(e);
		param.add(e);
		//hql+=" group by over.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}

	
	/**
	 * 获取当前登陆人有可见范围的部门的人员的加班记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws ParseException 
	 */
	public Long getViewPrivOvertimeByConditionCount(
			List<TeeDepartment> listDept, String startDateDesc,
			String endDateDesc) throws ParseException {
		String deptIds="";
		for (TeeDepartment teeDepartment : listDept) {
			deptIds+=teeDepartment.getUuid()+",";
		}
		if(deptIds.endsWith(",")){
			deptIds=deptIds.substring(0,deptIds.length()-1);
		}
		
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="select count(sid) from TeeAttendOvertime over where over.allow =1 and over.user.dept.uuid in ("+deptIds+") ";
		
        Calendar cl1=null;//开始统计时间
		Calendar cl2=null;//结束统计时间
		
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));	
		}
		
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
		}
		long s=cl1.getTimeInMillis();
		long e=cl2.getTimeInMillis();
		//开始时间小于 统计开始时间   结束时间大于 统计结束时间
		//开始时间大于 统计开始时间    结束时间小于  统计结束时间
		//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
		//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
		hql+=" and ((startTime<? and endTime>?) or (startTime>? and endTime<?) or (startTime<? and endTime>? and endTime<?) or(startTime>? and startTime<? and endTime>?))";
		param.add(s);
		param.add(e);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(e);
		param.add(e);
		hql+=" group by over.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}

	
	/**
	 *  获取当前登陆人有可见范围的部门的人员的加班记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @param firstIndex
	 * @param rows
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeAttendOvertime> getViewPrivOvertimeByConditionPage(
			List<TeeDepartment> listDept, String startDateDesc,
			String endDateDesc, int firstIndex, int rows, TeeDataGridModel dm) throws ParseException {
		String deptIds="";
		for (TeeDepartment teeDepartment : listDept) {
			deptIds+=teeDepartment.getUuid()+",";
		}
		if(deptIds.endsWith(",")){
			deptIds=deptIds.substring(0,deptIds.length()-1);
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendOvertime over where over.allow =1  and over.user.dept.uuid in ("+deptIds+")  ";
		
        Calendar cl1=null;//开始统计时间
		Calendar cl2=null;//结束统计时间
		
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));	
		}
		
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
		}
		long s=cl1.getTimeInMillis();
		long e=cl2.getTimeInMillis();
		//开始时间小于 统计开始时间   结束时间大于 统计结束时间
		//开始时间大于 统计开始时间    结束时间小于  统计结束时间
		//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
		//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
		hql+=" and ((startTime<? and endTime>?) or (startTime>? and endTime<?) or (startTime<? and endTime>? and endTime<?) or(startTime>? and startTime<? and endTime>?))";
		param.add(s);
		param.add(e);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(s);
		param.add(e);
		
		param.add(s);
		param.add(e);
		param.add(e);
		hql+=" order by over.createTime desc group by over.user.uuid";
		return (List<TeeAttendOvertime>) pageFindByList(hql, firstIndex, rows, param);
	}
	
}