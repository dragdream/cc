package com.tianee.oa.core.base.attend.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendHoliday;
import com.tianee.oa.core.base.attend.model.TeeAttendHolidayModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("attendHolidayDao")
public class TeeAttendHolidayDao  extends TeeBaseDao<TeeAttendHoliday> {
	
	public void addHoliday(TeeAttendHoliday holiday) {
		save(holiday);
	}
	
	
	public void updateHoliday(TeeAttendHoliday holiday) {
		update(holiday);
	}

	public TeeAttendHoliday loadById(int id) {
		TeeAttendHoliday intf = load(id);
		return intf;
	}
	
	public TeeAttendHoliday getById(int id) {
		TeeAttendHoliday intf = get(id);
		return intf;
	}
	
	
	public void delById(int id) {
		delete(id);
	}
	

	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeAttendHoliday where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	

	public  List<TeeAttendHoliday> getHolidayList() {
		String hql = "from TeeAttendHoliday holiday where parentId = 0 order by  sid asc";
		List<TeeAttendHoliday> list = executeQuery(hql, null);
		return list;
	}	
	public  List<TeeAttendHoliday> getHolidayList(Calendar cl) {
		List param = new ArrayList();
		String hql = "from TeeAttendHoliday holiday where holiday.startTime < ? and holiday.endTime>? and holiday.parentId = 0 order by  sid asc";
		param.add(cl);
		param.add(cl);
		List<TeeAttendHoliday> list = executeQueryByList(hql, param);
		return list;
	}	
	
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from TeeAttendHoliday oc where 1=1 and oc.parentId=0";
		long total = count("select count(*) "+hql, null);
		hql+=" order by oc.sid asc";
		List rows = new ArrayList();
		List<TeeAttendHoliday> list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeAttendHoliday holiday:list){
			TeeAttendHolidayModel model = new TeeAttendHolidayModel();
			BeanUtils.copyProperties(holiday, model);
			if(!TeeUtility.isNullorEmpty(holiday.getStartTime())){
				
				model.setStartTimeDesc(sf.format(holiday.getStartTime().getTime()));
			}
			if(!TeeUtility.isNullorEmpty(holiday.getEndTime())){
				
				model.setEndTimeDesc(sf.format(holiday.getEndTime().getTime()));
			}
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public void deleteWorkDayByParentId(int parentId){
		String hql = "delete from TeeAttendHoliday where parentId in (" + parentId + ")";
		deleteOrUpdateByQuery(hql, null);
		
	}
	
	
	public TeeEasyuiDataGridJson getWorkDayList(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		int parentId = TeeStringUtil.getInteger(requestDatas.get("parentId"), 0);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from TeeAttendHoliday oc where parentId<>0 and parentId=" + parentId;
		hql+=" order by oc.sid asc ";
		long total = count("select count(*) "+hql, null);
		List rows = new ArrayList();
		List<TeeAttendHoliday> list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeAttendHoliday holiday:list){
			TeeAttendHolidayModel model = new TeeAttendHolidayModel();
			BeanUtils.copyProperties(holiday, model);
			if(!TeeUtility.isNullorEmpty(holiday.getStartTime())){
				
				model.setStartTimeDesc(sf.format(holiday.getStartTime().getTime()));
			}
			if(!TeeUtility.isNullorEmpty(holiday.getEndTime())){
				
				model.setEndTimeDesc(sf.format(holiday.getEndTime().getTime()));
			}
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	/**
	 * 获取补假日
	 * @param year
	 * @param month
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeAttendHoliday> getWorkDayByDate(int year,int month) throws ParseException{
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, year); //year 为 int 
		time.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0           
		int maxDay = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数 
		
		String minDate = year + "-" + month  + "-01";
		String maxDate = year + "-" + month  + "-" + maxDay;
		
		List param = new ArrayList();
		String hql = "from TeeAttendHoliday holiday where holiday.startTime <= ? and holiday.startTime>=? and holiday.parentId<>0 order by  holiday.startTime asc";
		time.setTime(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", maxDate + " 00:00:00"));
		Calendar time2 = Calendar.getInstance();
		time2.setTime(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", minDate + " 00:00:00"));
		
		param.add(time);
		param.add(time2);
		List<TeeAttendHoliday> list = executeQueryByList(hql, param);
		return list;
		
	}
	
	/**
	 * 获取补假日
	 * @param year
	 * @param month
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeAttendHoliday> getWorkDayList() throws ParseException{
		List param = new ArrayList();
		String hql = "from TeeAttendHoliday holiday where holiday.parentId<>0";
		List<TeeAttendHoliday> list = executeQueryByList(hql, param);
		return list;
		
	}
}