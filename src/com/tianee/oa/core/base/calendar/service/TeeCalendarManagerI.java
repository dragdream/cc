package com.tianee.oa.core.base.calendar.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.core.base.calendar.bean.TeeCalendarAffair;
import com.tianee.oa.core.base.calendar.dao.TeeCalendarAffairDao;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.quartz.TeeQuartzTypes;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Service
public class TeeCalendarManagerI  extends TeeBaseService {
	@Autowired
	private TeeCalendarAffairDao calendarDao ;
	
	@Autowired
	private TeePersonDao personDao;
	
	/*@Autowired
	private TeeSmsManager smsManager;*/
	
	/**
	 * 创建日程安排
	 * @author syl
	 * @date 2014-2-19
	 * @param person  安排人员（领导）
	 * @param noticePersons  通知人员
	 * @param model  模版
	 * @return
	 */
	public Map createCalendar(TeePerson person ,TeeCalendarAffairModel model){
		Map map = new HashMap();
		List<TeePerson> actorIds = new ArrayList<TeePerson> ();
		if(!TeeUtility.isNullorEmpty(model.getActorIds())){//内部出席人员
			actorIds = personDao.getPersonByUuids(model.getActorIds());
		}
		
		TeeCalendarAffair calendar = new TeeCalendarAffair();
		BeanUtils.copyProperties(model, calendar);
		Date startTime  = new Date();
		startTime.setTime(model.getStartTime());
//		Date endTime = new Date();
//		if(!TeeUtility.isNullorEmpty(model.getStartTimeStr())){
//			
//			try {
//				startTime = TeeUtility.parseDate("yyyy-MM-dd" , model.getStartTimeStr());
//				endTime =  TeeUtility.parseDate("yyyy-MM-dd" , model.getEndTimeStr());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			calendar.setStartTime(startTime.getTime());
//			calendar.setEndTime(endTime.getTime());
//		}
		calendar.setActor(actorIds);
		calendar.setOverStatus(0);
		calendar.setManager(person);
		calendar.setStartTime(startTime.getTime());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		System.out.println(sdf.format(startTime));
		TeeCalendarAffair calendarTemp = new TeeCalendarAffair();
		BeanUtils.copyProperties(calendar, calendarTemp);
		calendarTemp.setUser(person);
		calendarTemp.setActor(actorIds);
		calendarDao.deleteOrUpdateByQuery("delete from TeeCalendarAffair where user.uuid=? and ((startTime<=? and endTime>=?) or (startTime<=? and startTime>=?) or (endTime>=? and endTime<=?))", new Object[]{person.getUuid(),calendarTemp.getStartTime(),calendarTemp.getEndTime(),calendarTemp.getEndTime(),calendarTemp.getStartTime(),calendarTemp.getStartTime(),calendarTemp.getEndTime()});
		calendarDao.addCalendar(calendarTemp);
		
		
		//发送定时任务
		TeeQuartzTask task = new TeeQuartzTask();
		task.setContent(model.getContent());
		task.setModelId(String.valueOf(calendarTemp.getSid()));
		task.setModelNo("022");
		task.setUrl("/system/core/base/calendar/affair/detail.jsp?id="
				+ calendarTemp.getSid());
		task.setUrl1("/system/core/base/calendar/affair/detail.jsp?id="
				+ calendarTemp.getSid());
		task.setFrom(person.getUserId());
		task.setType(TeeQuartzTypes.ONCE);
		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		task.setExp(TeeQuartzUtils.getPreRemindQuartzExpression(c.get(Calendar.YEAR), 
				c.get(Calendar.MONTH)+1, 
				c.get(Calendar.DATE), 
				c.get(Calendar.HOUR_OF_DAY), 
				c.get(Calendar.MINUTE), 
				c.get(Calendar.SECOND), model.getPreHours(), model.getPreMinutes(), 0));
		
		StringBuffer sb = new StringBuffer();
		for(TeePerson p :actorIds){
			sb.append(p.getUserId()+",");
		}
		sb.append(person.getUserId());
		task.setTo(sb.toString());
		
		MessagePusher.push2Quartz(task);
		
		return map;
	}
}


