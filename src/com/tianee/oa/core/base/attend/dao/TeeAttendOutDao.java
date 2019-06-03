package com.tianee.oa.core.base.attend.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.model.TeeAttendOutModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("attendOutDao")
public class TeeAttendOutDao  extends TeeBaseDao<TeeAttendOut> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeAttendOut
	 */
	public void addAttendOut(TeeAttendOut attendOut) {
		save(attendOut);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeAttendOut
	 */
	public void updateAttendOut(TeeAttendOut attendOut) {
		update(attendOut);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendOut loadById(int id) {
		TeeAttendOut intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendOut getById(int id) {
		TeeAttendOut intf = get(id);
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
			String hql = "delete from TeeAttendOut where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeAttendOut> selectPersonalOut(TeePerson person , TeeAttendOutModel model) {
		if(model.getStatus()==9){
			Object[] values = {person};
			String hql = "from TeeAttendOut where user = ? and allow = 1  order by  createTime desc";
			List<TeeAttendOut> list = (List<TeeAttendOut>) executeQuery(hql,values);
			return list;
		}else{
			Object[] values = {person , model.getStatus()};
			String hql = "from TeeAttendOut where user = ? and status = ?  order by  createTime desc";
			List<TeeAttendOut> list = (List<TeeAttendOut>) executeQuery(hql,values);
			return list;
		}
	}	
	
	public  List<TeeAttendOut> selectPersonalOutPage(TeePerson person ,int firstResult,int pageSize,TeeDataGridModel dm , TeeAttendOutModel model)throws ParseException{
		List list = new ArrayList();
		if(model.getStatus()==9){
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			String hql = "from TeeAttendOut where user = ? and allow = 1  order by  createTime desc";
			return (List<TeeAttendOut>) pageFindByList(hql, firstResult, pageSize, list);
		}else{
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			if(TeeUtility.isNullorEmpty(model.getStatus())){
				list.add(model.getStatus());
			}
			String hql = "from TeeAttendOut where user = ? and status = ?  order by  createTime desc";
			return (List<TeeAttendOut>) pageFindByList(hql, firstResult, pageSize, list);
		}
	}	
	
	public  long selectPersonalOutCount(TeePerson person, TeeAttendOutModel model)throws ParseException{
		List list = new ArrayList();
		if(model.getStatus()==9){
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			String hql = "select count(sid) from TeeAttendOut where user = ? and allow = 1";
			long count = countByList(hql, list);
			return count;
		}else{
			if(!TeeUtility.isNullorEmpty(person)){
				list.add(person);
			}
			if(TeeUtility.isNullorEmpty(model.getStatus())){
				list.add(model.getStatus());
			}
			String hql = "select count(sid) from TeeAttendOut where user = ? and status = ?";
			
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
	public  List<TeeAttendOut> getManagerOut(TeePerson person , TeeAttendOutModel model) {
		Object[] values = {person , model.getStatus() , model.getAllow()};
		String hql = "from TeeAttendOut where leader = ? and status = ? and allow = ? order by  createTime desc";
		List<TeeAttendOut> list = (List<TeeAttendOut>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * 获取已审批记录
	 * @author syl
	 * @date 2014-2-5
	 * @param person
	 * @param model
	 * @return
	 */
	public  List<TeeAttendOut> getManagerApprovOut(TeePerson person , TeeAttendOutModel model) {
		Object[] values = {person , model.getAllow()};
		String hql = "from TeeAttendOut where leader = ? and allow <> ? order by  createTime desc";
		List<TeeAttendOut> list = (List<TeeAttendOut>) executeQuery(hql,values);
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
	public  void approve(TeePerson person , TeeAttendOutModel model) {
		Object[] values = {model.getAllow() , model.getReason() ,model.getSid() };
		String hql = "update TeeAttendOut set allow = ? , reason = ?  where sid = ?";
		deleteOrUpdateByQuery(hql, values);
	}	
	
	
	
	
	/**
	 * 获取待审批 记录数量
	 * @author syl
	 * @date 2014-2-5
	 * @param person
	 * @param model
	 * @return
	 */
	public  long getLeaderCount(TeePerson person , TeeAttendOutModel model) {
		Object[] values = {person , model.getStatus() , model.getAllow()};
		String hql = "select count(*) from TeeAttendOut where leader = ? and status = ? and allow = ? order by  createTime desc";
		long count = count(hql, values);
		return count;
	}	
	
	
	/**
	 * 个人桌面模块
	 * @author syl
	 * @date 2014-2-13
	 * @param person
	 * @param model
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<TeeAttendOut> getDeskTop(TeePerson person , TeeAttendOutModel model , long startTime , long endTime){
		Object[] values = { model.getStatus() , model.getAllow() , startTime};
		String hql = "from TeeAttendOut where  status = ? and allow = ?  and submitTime =?    order by  createTime desc";
		List<TeeAttendOut> list = (List<TeeAttendOut>) executeQuery(hql,values);
		return list;
	}

	
	/**
	 * @author ny
	 * @param deptId
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws Exception 
	 */
	public List<TeeAttendOut> getOutByCondition(String deptIds,String startDateDesc, String endDateDesc) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendOut out where out.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and out.user.dept.uuid in ("+deptIds+") ";
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
		
		hql+=" order by out.user.dept.deptNo asc ,out.user.uuid asc ,out.createTime asc group by out.user.uuid";
		List<TeeAttendOut> list = (List<TeeAttendOut>) executeQueryByList(hql,param);
		return list;
	}
	
	public  List<TeeAttendOut> getOutByConditionPage(String deptIds,
			String startDateDesc, String endDateDesc, int firstResult,int pageSize,TeeDataGridModel dm)throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
		String hql ="from TeeAttendOut out where out.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and out.user.dept.uuid in ("+deptIds+") ";
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
		
		hql+=" order by out.user.dept.deptNo asc,out.user.uuid asc,out.createTime asc group by out.user.uuid";
		return (List<TeeAttendOut>) pageFindByList(hql, firstResult, pageSize, param);
		
	}
	
	public  long getOutByConditionCount(String deptIds,
			String startDateDesc, String endDateDesc) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List param =new ArrayList();
        String hql ="select count(sid) from TeeAttendOut out where out.allow =1 ";
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			hql +=" and out.user.dept.uuid in ("+deptIds+") ";
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
		//hql+=" group by out.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}

	
	
	/**
	 * 获取当前登陆人又可见权限的部门下人员的 外出记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws ParseException 
	 */
	public Long getViewPrivOutByConditionCount(List<TeeDepartment> listDept,
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
        String hql ="select count(sid) from TeeAttendOut out where out.allow =1 and out.user.dept.uuid in("+deptIds+") ";
		
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
        
		hql+=" group by out.user.uuid";
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}

	
	/**
	 * 获取当前登陆人又可见权限的部门下人员的 外出记录
	 * @param listDept
	 * @param startDateDesc
	 * @param endDateDesc
	 * @param firstIndex
	 * @param rows
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeAttendOut> getViewPrivOutByConditionPage(
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
		String hql ="from TeeAttendOut out where out.allow =1  and out.user.dept.uuid in ("+deptIds+") ";
		
	
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
		
		hql+=" order by out.createTime desc group by out.user.uuid";
		return (List<TeeAttendOut>) pageFindByList(hql, firstIndex, rows, param);
	}
	
}