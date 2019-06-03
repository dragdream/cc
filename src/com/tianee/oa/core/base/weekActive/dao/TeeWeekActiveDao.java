package com.tianee.oa.core.base.weekActive.dao;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.weekActive.bean.TeeWeekActive;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("weekActiveDao")
public class TeeWeekActiveDao extends TeeBaseDao<TeeWeekActive>{
	
	/**
	 * 增加
	 * @date 2014年4月26日
	 * @author 
	 * @param weekActive
	 */
	public void add(TeeWeekActive weekActive) {
		save(weekActive);
	}
	
	/**
	 * 查询
	 * @date 2014年4月26日
	 * @author 
	 * @param id
	 * @return
	 */
	public TeeWeekActive getById(int id){
		TeeWeekActive weekActive = load(id);
		return weekActive;
	}
	
	/**
	 * 根据日期获取数据
	 * @date 2014年5月11日
	 * @author 
	 * @param person
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeWeekActive> getActiveByWeekDao(TeePerson person,String beginDate,String endDate,String activeUser) throws ParseException{
		String beginDateStr = beginDate + " 00:00:00";
		String endDateStr = endDate + " 23:59:59";
		
		Date beginDate1 = TeeUtility.parseDate(beginDateStr);
		Date endDate2 = TeeUtility.parseDate(endDateStr);
		Object[] values = {beginDate1,endDate2,activeUser};
		
		String hql = "from TeeWeekActive where activeStartTime >=? and activeStartTime <=? and activeUser=?";
		List<TeeWeekActive> list = (List<TeeWeekActive>) executeQuery(hql,values);
		return list;
	}
	
	
	/**
	 * 删除
	 * @function: 
	 * @author: wyw
	 * @data: 2015年2月5日
	 * @param ids void
	 */
	public void delByIds(String ids) {
		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeWeekActive where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	
	
	
	

}
