package com.beidasoft.xzzf.punish.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishCalendar;
import com.beidasoft.xzzf.punish.common.dao.PunishCalendarDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
@Service
public class PunishCalendarService extends TeeBaseService{
	@Autowired
	PunishCalendarDao calendarDao;
	
	/**
	 * 新增计时器
	 * @param pCalendar
	 * @return
	 */
	public TeeJson addPCalendar(PunishCalendar pCalendar){
		TeeJson json = new TeeJson();
		pCalendar.setId(UUID.randomUUID().toString());
		//获取开始时间戳
		long startTime = pCalendar.getStartTime();
	    //取到结束时间戳
	    long closedTime = pCalendar.getClosedTime();
	    Date startTimeD = new Date(startTime);
	    Date closedTimeD = new Date(closedTime);
	    int countDown = TeeDateUtil.getDaySpan(startTimeD, closedTimeD)+1;
	    if (TeeDateUtil.getDaySpan(startTimeD,new Date())!=0) {
	    	countDown = countDown - TeeDateUtil.getDaySpan(startTimeD,new Date());
		}
	    //计算倒计时天数
	    pCalendar.setCountDown(countDown);
		saveObj(pCalendar);
		json.setRtState(true);
		json.setRtMsg("新增成功");
		return json;
	}
	/**
	 * 倒计时暂停/开始
	 * @param newCalendar
	 * @param startTimes
	 * @param endTimes
	 * @return
	 */
	public TeeJson changePCalendar(PunishCalendar newCalendar,long startTimes,long endTimes){
		TeeJson json = new TeeJson();
		String primaryId = newCalendar.getPrimaryId();
		//根据唯一键 查找对应计时器
		PunishCalendar oldCalendar = getObjByPId(primaryId);
		if (startTimes!=0&&endTimes==0) {
			//暂停倒计时
			oldCalendar.setStatus("200");
			Date startTimed = new Date(startTimes);
			Date nowDated = new Date();
			int days = TeeDateUtil.getDaySpan(startTimed,nowDated);
			if (days > 0) {
				oldCalendar.setCountDown(oldCalendar.getCountDown()+days);
				updateObj(oldCalendar);
			}
			json.setRtData("已暂停倒计时");
			json.setRtState(true);
		}else if (startTimes!=0&&endTimes!=0) {
			//继续倒计时或减去对应天数
			//获取结束时间 
			Date closedTime = new Date(oldCalendar.getClosedTime());
			Calendar closedTimec = Calendar.getInstance();
			closedTimec.setTime(closedTime);
			int changeTime = TeeDateUtil.getDaySpan(new Date(startTimes),new Date(endTimes));
			//更改结束日期
			closedTimec.add(Calendar.DAY_OF_YEAR, changeTime);
			oldCalendar.setClosedTime(closedTimec.getTime().getTime());
			
			if ("100".equals(oldCalendar.getStatus())) {
				//如果倒计时未暂停，直接加上需要暂停的天数
				int newCountDown = oldCalendar.getCountDown()+changeTime;
				oldCalendar.setCountDown(newCountDown);
				updateObj(oldCalendar);
				json.setRtData("已更改剩余天数");
				json.setRtState(true);
			}else if ("200".equals(oldCalendar.getStatus())) {
				//如果倒计时暂停中，开始倒计时
				oldCalendar.setStatus("100");
				Date endDated = new Date(endTimes);
				Date nowDated = new Date();
				int days = TeeDateUtil.getDaySpan(endDated,nowDated);
				if (days > 0) {
					int newCountDown = oldCalendar.getCountDown()-days;
					if (newCountDown<0) {
						//已超时
						oldCalendar.setCountDown(-1);
					} else {
						oldCalendar.setCountDown(newCountDown);
					}
					updateObj(oldCalendar);
					json.setRtData("已继续倒计时");
					json.setRtState(true);
				}
			}
		}
		return json;
	}
	
	
	/**
	 * 查封扣押延期
	 * @param newCalendar
	 * @param delayTimes
	 * @return
	 */
	public TeeJson changePunishCalendar(PunishCalendar newCalendar,long delayTimes){
		TeeJson json = new TeeJson();
		String primaryId = newCalendar.getPrimaryId();
		//根据唯一键 查找对应计时器
		PunishCalendar oldCalendar = getObjByPId(primaryId);
		if (delayTimes != 0) {
			//获取结束时间 
			Date closedTime = new Date(oldCalendar.getClosedTime());
			
			int changeTime = TeeDateUtil.getDaySpan(new Date(closedTime.getTime()),new Date(delayTimes));
			if (changeTime>=1&&changeTime<=30) {
				//更改结束日期
				oldCalendar.setClosedTime(delayTimes);
				oldCalendar.setCountDown(changeTime);
				json.setRtData("延期成功");
				updateObj(oldCalendar);
				json.setRtState(true);
			} else {
				json.setRtData("只能延期30天");
				json.setRtState(false);
			}
		}
		return json;
	}
	
	/**
	 * 查询所有执行中的计时器
	 * @return
	 */
	public List<PunishCalendar> getInExecution(){
		return calendarDao.getInExecution();
	}
	/**
	 * 查询全部
	 * @return
	 */
	public List<PunishCalendar> getObjList(){
		List<PunishCalendar> list = calendarDao.getObjList();
		for (PunishCalendar punishCalendar : list) {
			punishCalendar.getUser();
		}
		return list;
	}
	/**
	 * 根据id查询计时器
	 * @param id
	 * @return
	 */
	public PunishCalendar getObjById(String id){
		return calendarDao.get(id); 
	}
	/**
	 * 新增计时器
	 * @param calendar
	 */
	public void saveObj(PunishCalendar pCalendar){
		calendarDao.save(pCalendar);
	}
	/**
	 * 修改计时器
	 * @param calendar
	 */
	public void updateObj(PunishCalendar calendar){
		calendarDao.update(calendar);
	}
	/**
	 * 根据对象删除计时器
	 * @param calendar
	 */
	public void deleteByObj(PunishCalendar calendar){
		calendarDao.deleteByObj(calendar);
	}
	/**
	 * 根据id删除计时器
	 * @param id
	 */
	public void deleteById(String id){
		calendarDao.delete(id);
	}
	/**
	 * 根据业务表id查询相关计时器
	 * @param primaryId
	 * @return
	 */
	public PunishCalendar getObjByPId(String primaryId){
		return calendarDao.getObjByPId(primaryId);
	}
	
	public Set monthList(TeePerson loginPerson, int year, int month, String baseId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String monthStr = month < 10 ? ("0" + month) : month + "";
		Calendar begin = TeeDateUtil.parseCalendar(year+"-"+monthStr+"-01 00:00:00");
		int maxDate = begin.getActualMaximum(Calendar.DATE);
		Calendar end = TeeDateUtil.parseCalendar(year+"-"+monthStr+"-"+(maxDate<10?("0"+maxDate):maxDate+"")+" 23:59:59");
		
		List<PunishCalendar> list = simpleDaoSupport.find(" from PunishCalendar c where "
				+ " ((startTime>="+begin.getTimeInMillis()+" and closedTime<="+end.getTimeInMillis()+") or "
				+ " (startTime<="+begin.getTimeInMillis()+" and closedTime>="+end.getTimeInMillis()+") or "
				+ " (closedTime>="+end.getTimeInMillis()+" and startTime<="+end.getTimeInMillis()+") or "
				+ " (startTime<="+begin.getTimeInMillis()+" and closedTime>="+begin.getTimeInMillis()+")) "
				+ " and baseId = '" + baseId + "'" 
				+ " and c.status = '100' order by c.startTime asc", null);
		Set returnList = new HashSet();
		
		Calendar c = Calendar.getInstance();
		PunishCalendar cal=null;
		String startStr="";
		String endStr="";
		try {
			for(int i=0;i<list.size();i++){
				cal = list.get(i);
				//开始时间
				Calendar startTime = Calendar.getInstance();
				startTime.setTimeInMillis(cal.getStartTime());
				startStr=sdf.format(startTime.getTime())+" 00:00:00";
				startTime.setTime((Date)sdf1.parse(startStr));
				//startTime.setTimeInMillis(startTime.getTimeInMillis());
				
				
				//结束时间
				Calendar endTime = Calendar.getInstance();
				endTime.setTimeInMillis(cal.getClosedTime());
				endStr=sdf.format(endTime.getTime())+" 23:59:59";
				endTime.setTime((Date)sdf1.parse(endStr));
				//endTime.setTimeInMillis(endTime.getTimeInMillis());
				
				for(Calendar j=startTime;startTime.before(endTime);j.add(Calendar.DATE, 1)){
					//System.out.println(j.get(Calendar.MONTH));
					
					if(j.get(Calendar.MONTH)==(month-1)){
						returnList.add(j.get(Calendar.DATE));
					}
					
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	/**
	 * 桌面小模块数据请求
	 * @author syl
	 * @date 2014-2-12
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public List<PunishCalendar> getPunishCalendar(TeePerson loginPerson,String date, String baseId){
		
		return calendarDao.listByBaseId(loginPerson, date, baseId);
	}
}
