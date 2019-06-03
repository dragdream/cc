package com.tianee.oa.core.base.attend.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendEvection;
import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.model.TeeAttendEvectionModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOutModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("attendEvectionDao")
public class TeeAttendEvectionDao extends TeeBaseDao<TeeAttendEvection>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeAttendOut
	 */
	public void addAttendEvection(TeeAttendEvection leave) {
		save(leave);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeAttendOut
	 */
	public void updateAttendLeave(TeeAttendEvection leave) {
		update(leave);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendEvection loadById(int id) {
		TeeAttendEvection intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendEvection getById(int id) {
		TeeAttendEvection intf = get(id);
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
			String hql = "delete from TeeAttendEvection where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeAttendEvection> selectPersonalEvection(TeePerson person , TeeAttendEvectionModel model) {
		if(model.getStatus()==9){
			Object[] values = {person};
			String hql = "from TeeAttendEvection where user = ? and allow = 1  order by  createTime desc";
			List<TeeAttendEvection> list = (List<TeeAttendEvection>) executeQuery(hql,values);
			return list;
		}else{
			Object[] values = {person , model.getStatus()};
			String hql = "from TeeAttendEvection where user = ? and status = ?  order by  createTime desc";
			List<TeeAttendEvection> list = (List<TeeAttendEvection>) executeQuery(hql,values);
			return list;
		}
	}
	
	public  List<TeeAttendEvection> selectPersonalEvectionPage(TeePerson person ,int firstResult,int pageSize,TeeDataGridModel dm , TeeAttendEvectionModel model)throws ParseException{
		List list = new ArrayList();
		if(model.getStatus()==9){
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			String hql = "from TeeAttendEvection where user = ? and allow = 1  order by  createTime desc";
			return (List<TeeAttendEvection>) pageFindByList(hql, firstResult, pageSize, list);
		}else{
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			if(TeeUtility.isNullorEmpty(model.getStatus())){
				list.add(model.getStatus());
			}
			String hql = "from TeeAttendEvection where user = ? and status = ?  order by  createTime desc";
			return (List<TeeAttendEvection>) pageFindByList(hql, firstResult, pageSize, list);
		}
	}	
	
	public  long selectPersonalEvectionCount(TeePerson person, TeeAttendEvectionModel model)throws ParseException{
		List list = new ArrayList();
		if(model.getStatus()==9){
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			String hql = "select count(sid) from TeeAttendEvection where user = ? and allow = 1";
			long count = countByList(hql, list);
			return count;
		}else{
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			if(TeeUtility.isNullorEmpty(model.getStatus())){
				list.add(model.getStatus());
			}
			String hql = "select count(sid) from TeeAttendEvection where user = ? and status = ?";
			long count = countByList(hql, list);
			return count;
		}
	}	
	/*外出审批管理*/
	
	/**
	 * 获取待审批记录
	 * @author syl
	 * @date 2014-2-5
	 * @param person
	 * @param model
	 * @return
	 */
	public  List<TeeAttendEvection> getLeaderEvection(TeePerson person , TeeAttendEvectionModel model) {
		Object[] values = {person , model.getStatus() , model.getAllow()};
		String hql = "from TeeAttendEvection where leader = ? and status = ?  and allow = ? order by  createTime desc";
		List<TeeAttendEvection> list = (List<TeeAttendEvection>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * 获取需要审批记录数量
	 * @author syl
	 * @date 2014-2-6
	 * @param person
	 * @param model
	 * @return
	 */
	public  long getEvectionCount(TeePerson person , TeeAttendEvectionModel model) {
		Object[] values = {person , model.getStatus() , model.getAllow()};
		String hql = "select count(*) from TeeAttendEvection where leader = ? and status = ?  and allow = ? order by  createTime desc";
		long count = count(hql, values);
		return count;
	}
	
	
	/**
	 * 获取已审批记录
	 * @author syl
	 * @date 2014-2-5
	 * @param person
	 * @param model
	 * @return
	 */
	public  List<TeeAttendEvection> getLeaderApprovEvection(TeePerson person , TeeAttendEvectionModel model) {
		Object[] values = {person , model.getAllow()};
		String hql = "from TeeAttendEvection where leader = ? and allow <> ? order by  createTime desc";
		List<TeeAttendEvection> list = (List<TeeAttendEvection>) executeQuery(hql,values);
		return list;
	}	
	/**
	 * 审批
	 * @author syl
	 * @date 2014-2-5
	 * @param person
	 * @param model
	 * @return
	 */
	public  void approve(TeePerson person , TeeAttendEvectionModel model) {
		Object[] values = {model.getAllow() , model.getReason() ,model.getSid() };
		String hql = "update TeeAttendEvection set allow = ? , reason = ?  where sid = ?";
		deleteOrUpdateByQuery(hql, values);
	}	
	
	
	/**
	 * 审批  销假
	 * @author syl
	 * @date 2014-2-5
	 * @param person
	 * @param model
	 * @return
	 */
	public  void destroyLeave(TeePerson person , TeeAttendEvectionModel model) {
		Object[] values = {model.getStatus() , model.getSid() };
		String hql = "update TeeAttendEvection set status = ?   where sid = ?";
		deleteOrUpdateByQuery(hql, values);
	}

	public List<TeeAttendEvection> getEvectionByCondition(String deptIds,
			String startDateDesc, String endDateDesc) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendEvection evection where evection.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and evection.user.dept.uuid in ("+deptIds+") ";
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
		hql+=" and ((startTime<=? and endTime>=?) or (startTime>=? and endTime<=?) or (startTime<=? and endTime>=? and endTime<=?) or(startTime>=? and startTime<=? and endTime>=?))";
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
		
		hql+=" order by evection.user.dept.deptNo asc ,evection.user.uuid asc,evection.createTime asc group by evection.user.uuid";
		List<TeeAttendEvection> list = (List<TeeAttendEvection>) executeQueryByList(hql,param);
		return list;
	}
	
	public  List<TeeAttendEvection> getEvectionByConditionPage(String deptIds,
			String startDateDesc, String endDateDesc, int firstResult,int pageSize,TeeDataGridModel dm)throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendEvection evection where evection.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and evection.user.dept.uuid in ("+deptIds+") ";
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
		hql+=" and ((startTime<=? and endTime>=?) or (startTime>=? and endTime<=?) or (startTime<=? and endTime>=? and endTime<=?) or(startTime>=? and startTime<=? and endTime>=?))";
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
		
		
		hql+=" order by evection.user.dept.deptNo asc,evection.user.uuid asc,evection.createTime asc group by evection.user.uuid";
		return (List<TeeAttendEvection>) pageFindByList(hql, firstResult, pageSize, param);
	}
	
	public  long getEvectionByConditionCount(String deptIds,
			String startDateDesc, String endDateDesc) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="select count(sid) from TeeAttendEvection evection where evection.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			
			hql +=" and evection.user.dept.uuid in ("+deptIds+") ";
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
		hql+=" and ((startTime<=? and endTime>=?) or (startTime>=? and endTime<=?) or (startTime<=? and endTime>=? and endTime<=?) or(startTime>=? and startTime<=? and endTime>=?))";
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
		
		
		//hql+=" group by evection.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}
	
	
	/**
	 *  判断时间段
	 * @author syl
	 * @date 2014-6-21
	 * @param dateStr
	 * @param person
	 * @return
	 * @throws ParseException
	 */
	public List<TeeAttendEvection>  getParseDataTest(String dateStr , TeePerson person) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date  = format.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		//一天最大 2014-2-2 23:59:59
		Calendar MaxCal = Calendar.getInstance();
		MaxCal= TeeUtility.getMaxTimeOfDayCalendar(cal);
		//一天最小 2014-2-2 0:0:0
		Calendar minCal = Calendar.getInstance();
		minCal= TeeUtility.getMinTimeOfDayCalendar(cal);
		
		
		String hql=" from TeeAttendEvection evection where evection.user.uuid="+person.getUuid()+" and evection.startTime<=? and evection.endTime>=? ";
		Object[] param = {MaxCal.getTimeInMillis() , minCal.getTimeInMillis()};
		List<TeeAttendEvection> list = executeQuery(hql,param);
		return list;
	}
	
	
	/**
	 *  判断一天和此用户是否有外出记录
	 * @author syl
	 * @date 2014-6-21
	 * @param dateStr
	 * @param person
	 * @return
	 * @throws ParseException
	 */
	public long  getEvectionCountByDate(Date date , TeePerson person) throws ParseException{
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//Date date  = format.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		//一天最大 2014-2-2 23:59:59
		Calendar MaxCal = Calendar.getInstance();
		MaxCal= TeeUtility.getMaxTimeOfDayCalendar(cal);
		//一天最小 2014-2-2 0:0:0
		Calendar minCal = Calendar.getInstance();
		minCal= TeeUtility.getMinTimeOfDayCalendar(cal);
		String hql="select count(*) from TeeAttendEvection evection where evection.user.uuid="+person.getUuid()+" and evection.startTime<=? and evection.endTime>=? " +
				"and evection.allow=1";
		Object[] param = {MaxCal.getTimeInMillis() , minCal.getTimeInMillis()};
		long count = count(hql,param);
		return count;
	}

	
	/**
	 * 
	 * 获取当前登陆者有可见权限的部门下人员的出差记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws ParseException 
	 */
	public Long getViewPrivEvectionByConditionCount(
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
		String hql ="select count(sid) from TeeAttendEvection evection where evection.allow =1 and evection.user.dept.uuid in ("+deptIds+")   ";
		
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
		
		hql+=" group by evection.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}

	
	/**
	 * 获取当前登陆者有可见权限的部门下人员的出差记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @param firstIndex
	 * @param rows
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeAttendEvection> getViewPrivEvectionByConditionPage(
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
		String hql ="from TeeAttendEvection evection where evection.allow =1 and evection.user.dept.uuid in ("+deptIds+") ";
		
		
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
		
		
		hql+=" order by evection.createTime desc group by evection.user.uuid";
		return (List<TeeAttendEvection>) pageFindByList(hql, firstIndex, rows, param);
	}
}

