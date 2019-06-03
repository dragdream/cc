package com.beidasoft.xzzf.punish.common.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.PunishCalendar;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class PunishCalendarDao extends TeeBaseDao<PunishCalendar>{
	/**
	 * 查询所有计时器
	 * @return
	 */
	public List<PunishCalendar> getObjList(){
		return super.find("from PunishCalendar", null);
	}
	/**
	 * 查询所有执行中的计时器
	 * @return
	 */
	public List<PunishCalendar> getInExecution(){
		return super.find("from PunishCalendar where status = 100", null);
	}
	/**
	 * 根据业务表id查询相关计时器
	 * @param primaryId
	 * @return
	 */
	public PunishCalendar getObjByPId(String primaryId){
		primaryId = TeeDbUtility.formatString(primaryId);
		PunishCalendar punishCalendar = null;
		List<PunishCalendar> list = super.find("from PunishCalendar where primaryId = '"+primaryId+"'", null);
		if (!TeeUtility.isNullorEmpty(list)) {
			punishCalendar=list.get(0);
		}
		return punishCalendar;
	}
	
	public List<PunishCalendar> listByBaseId(TeePerson loginPerson,String date, String baseId) {
		Calendar begin = TeeDateUtil.parseCalendar(date+" 00:00:00");
		Calendar end = TeeDateUtil.parseCalendar(date+" 23:59:59");
		String sql= " from PunishCalendar c where c.status='100' and "
				+ " ((startTime>="+begin.getTimeInMillis()+" and closedTime<="+end.getTimeInMillis()+") or "
				+ " (startTime<="+begin.getTimeInMillis()+" and closedTime>="+end.getTimeInMillis()+") or "
				+ " (closedTime<="+end.getTimeInMillis()+" and closedTime>="+begin.getTimeInMillis()+") or "
				+ " (startTime>="+begin.getTimeInMillis()+" and startTime<="+end.getTimeInMillis()+"))"+" "
				+ " and baseId = '" + baseId + "'"
				+ " order by c.startTime asc";
		return super.find(sql, null);
	}
}
