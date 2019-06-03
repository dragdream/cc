package com.tianee.oa.core.base.calendar.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.calendar.bean.TeeCalendarAffair;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author syl
 *
 */
@Repository("calAffairDao")
public class TeeCalAffairDao extends TeeBaseDao<TeeCalendarAffair> {

	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeCalendarAffair> selectAll(TeePerson person) {
		Object[] values = {person , person};
		String hql = "from TeeCalendarAffair tcal where tcal.calAffType= 1 and (tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?)))";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQuery(hql,values);
		return list;
	}
	
	
	/**
	 * 
	 * @author syl
	 * @date 2014-1-11
	 * @return
	 */
	public  List<TeeCalendarAffair> query(TeeCalendarAffairModel  model, TeePerson person , String startTime , String endTime) {
		List values = new ArrayList();
		String hql = "from TeeCalendarAffair tcal where tcal.calAffType= 1 and (tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?)))";
		values.add(person);
		values.add(person);
		if(!TeeUtility.isNullorEmpty(startTime)){
			Date date = TeeDateUtil.format(startTime, "yyyy-MM-dd");
			values.add(date.getTime());
			hql = hql + " and tcal.startTime >= ?";
		}
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			Date date = TeeDateUtil.format(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
			values.add(date.getTime());
			hql = hql + " and tcal.endTime <= ?";
		}
		if(!TeeUtility.isNullorEmpty(model.getContent())){
    		hql = hql + " and tcal.content like ?";
    		values.add("%" + model.getContent() + "%");
		}
		
		hql  = hql + "  order by tcal.startTime";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQueryByList(hql, values);
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
	public  List<TeeCalendarAffair> querySmsRemindTask(TeeCalendarAffairModel  model, TeePerson person , long startTime , long endTime) {
		List values = new ArrayList();
		String hql = "from TeeCalendarAffair tcal where tcal.calAffType= 1 and (tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?)))";
		values.add(person);
		values.add(person);
		if(!TeeUtility.isNullorEmpty(startTime)){
			values.add(endTime);
			hql = hql + " and tcal.startTime <= ?";
		}
		if(!TeeUtility.isNullorEmpty(endTime)){
			values.add(startTime);
			hql = hql + " and (tcal.endTime >= ? or tcal.endTime =0 ) ";//结束时间大于等于（起始时间） 或者结束时间为空
		}
	
		hql = hql + " and tcal.lastRemind < " +  startTime;//最后一次提醒时间比当前凌晨还要小//1392112818000
		
		
		hql  = hql + "  order by tcal.startTime";
		List<TeeCalendarAffair> list = (List<TeeCalendarAffair>) executeQueryByList(hql, values);
		return list;
	}

	
}


