package com.tianee.oa.core.base.attend.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaveModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("attendLeaveDao")
public class TeeAttendLeaveDao extends TeeBaseDao<TeeAttendLeave>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeAttendOut
	 */
	public void addAttendLeave(TeeAttendLeave leave) {
		save(leave);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeAttendOut
	 */
	public void updateAttendLeave(TeeAttendLeave leave) {
		update(leave);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendLeave loadById(int id) {
		TeeAttendLeave intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendLeave getById(int id) {
		TeeAttendLeave intf = get(id);
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
			String hql = "delete from TeeAttendLeave where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeAttendLeave> selectPersonalLeave(TeePerson person , TeeAttendLeaveModel model) {
		String hql = "from TeeAttendLeave where user = ? and status = ?  order by  createTime desc";
		if( model.getStatus() == 0){//个人考勤---待审批
			hql = "from TeeAttendLeave where user = ? and (status = ? or status = 1)   order by  createTime desc";
		}
		if(model.getStatus()==9){
			Object[] values = {person};
			hql = "from TeeAttendLeave where user = ? and allow = 1  order by  createTime desc";
			List<TeeAttendLeave> list = (List<TeeAttendLeave>) executeQuery(hql,values);
			return list;
		}else{
			Object[] values = {person , model.getStatus()};
			List<TeeAttendLeave> list = (List<TeeAttendLeave>) executeQuery(hql,values);
			return list;
		}
	}	
	
	public  List<TeeAttendLeave> selectPersonalLeavePage(TeePerson person ,int firstResult,int pageSize,TeeDataGridModel dm ,TeeAttendLeaveModel model)throws ParseException{
		String hql = "from TeeAttendLeave where user = ? and status = ?  order by  createTime desc";
		List list = new ArrayList();
		if( model.getStatus() == 0){//个人考勤---待审批
			hql = "from TeeAttendLeave where user = ? and (status = ? or status = 1)   order by  createTime desc";
		}
		if(model.getStatus()==9){
//			Object[] values = {person};
			hql = "from TeeAttendLeave where user = ? and allow = 1  order by  createTime desc";
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			return (List<TeeAttendLeave>) pageFindByList(hql,firstResult, pageSize, list);
		}else{
//			Object[] values = {person , model.getStatus()};
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			
			if(model.getStatus() >= 0){
				list.add(model.getStatus());
			}
			return (List<TeeAttendLeave>) pageFindByList(hql, firstResult, pageSize, list);
		}
	}
	
	public  long selectPersonalLeaveCount(TeePerson person , TeeAttendLeaveModel model) throws ParseException {
		List list = new ArrayList();
		String hql = "select count(*) from TeeAttendLeave where user = ? and allow = 1";
		if(!TeeUtility.isNullorEmpty(person)){
			list.add(person);
		}
		
		long count = countByList(hql, list);
		return count;
	}
	
	
	/**
	 * 申请销毁
	 * @author syl
	 * @date 2014-2-5
	 * @param person
	 * @param model
	 */
	public  void destroyApply(TeePerson person , TeeAttendLeaveModel model) {
		Object[] values = {model.getStatus() , model.getDestroyDate().getTime() ,model.getSid() };
		String hql = "update TeeAttendLeave set status = ? ,destroyTime=?  where sid = ?";
		deleteOrUpdateByQuery(hql, values);
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
	public  List<TeeAttendLeave> getLeaderLeave(TeePerson person , TeeAttendLeaveModel model) {
		Object[] values = {person };
		String hql = "from TeeAttendLeave where leader = ? and ((status = 0 and  allow = 0) or status = 1 ) order by  createTime desc";
		List<TeeAttendLeave> list = (List<TeeAttendLeave>) executeQuery(hql,values);
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
	public  long getLeaderCount(TeePerson person , TeeAttendLeaveModel model) {
		Object[] values = {person };
		String hql = "select count(*) from TeeAttendLeave where leader = ? and ((status = 0 and  allow = 0) or status = 1 ) order by  createTime desc";
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
	public  List<TeeAttendLeave> getLeaderApprovLeave(TeePerson person , TeeAttendLeaveModel model) {
		Object[] values = {person , model.getAllow()};
		String hql = "from TeeAttendLeave where leader = ? and allow <> ? order by  createTime desc";
		List<TeeAttendLeave> list = (List<TeeAttendLeave>) executeQuery(hql,values);
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
	public  void approve(TeePerson person , TeeAttendLeaveModel model) {
		Object[] values = {model.getAllow() , model.getReason() ,model.getSid() };
		String hql = "update TeeAttendLeave set allow = ? , reason = ?  where sid = ?";
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
	public  void destroyLeave(TeePerson person , TeeAttendLeaveModel model) {
		Object[] values = {model.getStatus() , model.getSid() };
		String hql = "update TeeAttendLeave set status = ?   where sid = ?";
		deleteOrUpdateByQuery(hql, values);
	}

	public List<TeeAttendLeave> getLeaveByCondition(String deptIds,
			String startDateDesc, String endDateDesc) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendLeave leave where leave.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and leave.user.dept.uuid in ("+deptIds+") ";
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
		
		
		
		hql+=" order by leave.user.dept.deptNo asc,leave.user.uuid asc, leave.createTime asc group by leave.user.uuid";
		List<TeeAttendLeave> list = (List<TeeAttendLeave>) executeQueryByList(hql,param);
		return list;
	}
	
	public  List<TeeAttendLeave> getLeaveByConditionPage(String deptIds,
			String startDateDesc, String endDateDesc, int firstResult,int pageSize,TeeDataGridModel dm)throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendLeave leave where leave.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and leave.user.dept.uuid in ("+deptIds+")";
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
		
		hql+=" order by leave.user.dept.deptNo asc,leave.user.uuid asc, leave.createTime asc group by leave.user.uuid";
		return (List<TeeAttendLeave>) pageFindByList(hql, firstResult, pageSize, param);
		
	}
	
	public long getLeaveByConditionCount(String deptIds,
			String startDateDesc, String endDateDesc) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="select count(sid) from TeeAttendLeave leave where leave.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and leave.user.dept.uuid in ("+deptIds+")";
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
		
		
		//hql+=" group by leave.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}
	
	
	
	/**
	 *  判断一天和此用户是否有请假记录
	 * @author syl
	 * @date 2014-6-21
	 * @param dateStr
	 * @param person
	 * @return
	 * @throws ParseException
	 */
	public long  getLeaveCountByDate(Date date , TeePerson person) throws ParseException{
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
		String hql="select count(*) from TeeAttendLeave evection where evection.user.uuid="+person.getUuid()+" and evection.startTime<=? and evection.endTime>=? " +
				"and evection.allow=2";
		Object[] param = {MaxCal.getTimeInMillis() , minCal.getTimeInMillis()};
		long count = count(hql,param);
		return count;
	}
	
	
	/**
	 * @author nieyi
	 * 获取已经用了多少天年假
	 * @param loginUser
	 * @return
	 */
	public double getUsedAnnualLeaveDays(TeePerson loginUser,Calendar joinDate){
		Calendar cur = Calendar.getInstance();
		Calendar cl2 = Calendar.getInstance();
		Calendar cl = Calendar.getInstance();
		long joinDateLong=0;
		
		cl.set(Calendar.YEAR, cur.get(Calendar.YEAR)-1);
		cl.set(Calendar.MONTH, joinDate.get(Calendar.MONTH));
		cl.set(Calendar.DAY_OF_MONTH, joinDate.get(Calendar.DAY_OF_MONTH));
		
		cl2.set(Calendar.YEAR, cur.get(Calendar.YEAR));
		cl2.set(Calendar.MONTH, joinDate.get(Calendar.MONTH));
		cl2.set(Calendar.DAY_OF_MONTH, joinDate.get(Calendar.DAY_OF_MONTH));
		
		if(cl2.before(cur)){
			joinDateLong = cl2.getTimeInMillis();
		}else{
			joinDateLong = cl.getTimeInMillis();
		}
		double usedDays = 0;
		String sql="select sum(annual_leave) as usedDays from attend_leave lv where lv.leave_type =3 and lv.user_id="+loginUser.getUuid()+" and lv.allow=1 and lv.start_time>"+joinDateLong;
		Map map = executeNativeUnique(sql, null);
		usedDays = TeeStringUtil.getInteger(map.get("usedDays"), 0);
		return usedDays;
	}

	
	/**
	 * 获取可见部门下的请假申请记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws ParseException 
	 */
	public Long getViewPrivLeaveByConditionCount(List<TeeDepartment> listDept,
			String startDateDesc, String endDateDesc) throws ParseException {
		String deptIds="";
		for (TeeDepartment teeDepartment : listDept) {
			deptIds+=teeDepartment.getUuid()+",";
		}
		if(deptIds.endsWith(",")){
			deptIds=deptIds.substring(0,deptIds.length()-1);
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="select count(sid) from TeeAttendLeave leave where leave.allow =1  and leave.user.dept.uuid  in("+deptIds+")  ";
		
		
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
		
		
		hql+=" group by leave.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}

	
	
	/**
	 * 获取可见部门下的请假申请记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @param firstIndex
	 * @param rows
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeAttendLeave> getViewPrivLeaveByConditionPage(
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
		String hql ="from TeeAttendLeave leave where leave.allow =1 and leave.user.dept.uuid  in("+deptIds+")  ";
		
		
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
		
		hql+=" order by leave.createTime desc group by leave.user.uuid";
		return (List<TeeAttendLeave>) pageFindByList(hql, firstIndex, rows, param);
	}

/*	public List<Map> getAnnualLeaveByCondition(int deptId,String startDateDesc, String endDateDesc) {
		return null;
	}*/
}

