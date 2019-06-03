package com.tianee.oa.core.base.calendar.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.core.base.calendar.bean.TeeCalendarAffair;
import com.tianee.oa.core.base.calendar.dao.TeeCalendarAffairDao;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.base.calendar.model.TeeFullCalendarModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.quartz.TeeQuartzTypes;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author syl
 *
 */
@Service
public class TeeCalendarAffairService extends TeeBaseService {
	@Autowired
	private TeeCalendarAffairDao calendarDao ;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	private  static String[]  affairTypeNames = {"每日","每周","每月","每年"};//周期性是类型
	private static String[]  weekNames = {"一","二","三","四","五","六","日"};//周数组
	

	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdateCal(HttpServletRequest request , TeeCalendarAffairModel model ,TeePerson person) {
		TeeJson json = new TeeJson();
		List<TeeFullCalendarModel> calModelList = new ArrayList<TeeFullCalendarModel>();
		TeeFullCalendarModel calModel = new TeeFullCalendarModel();
		int beforeDay = TeeStringUtil.getInteger(request.getParameter("beforeDay"), 0);//提前多少天
		int beforeHour = TeeStringUtil.getInteger(request.getParameter("beforeHour"), 0);//提前多少小时
		int beforeMinute = TeeStringUtil.getInteger(request.getParameter("beforeMinute"), 0);//提前多少分钟
		
		TeeCalendarAffair cal = null;
		if(model.getSid() > 0){
			
			cal = calendarDao.get(model.getSid() );
			if(cal != null){
				cal.setContent(model.getContent());
				cal.setCalType(model.getCalType());
				cal.setCalAffType(model.getCalAffType());
				cal.setCalLevel(model.getCalLevel());
				cal.setStartTime(model.getStartDate().getTimeInMillis());
				cal.setEndTime(model.getEndDate().getTimeInMillis());
				if(!TeeUtility.isNullorEmpty(model.getActorIds())){//参与者
					List<TeePerson> list = personDao.getPersonByUuids(model.getActorIds());
					cal.setActor(list);
				}else{
					cal.setActor(null);
				}
				calendarDao.update(cal);
				json.setRtMsg("1");//更新
			}else{
				json.setRtState(false);
				json.setRtMsg("未找到日程");
				return json;
			}

		}else{
			cal = new TeeCalendarAffair();
			BeanUtils.copyProperties(model, cal);
			cal.setStartTime(model.getStartDate().getTimeInMillis());
			cal.setEndTime(model.getEndDate().getTimeInMillis());
			cal.setUser(person);
			cal.setOverStatus(0);
			if(!TeeUtility.isNullorEmpty(model.getActorIds())){//参与者
				List<TeePerson> list = personDao.getPersonByUuids(model.getActorIds());
				cal.setActor(list);
			}
			calendarDao.addCalendar(cal);
			json.setRtMsg("0");//新建
		}
		calModel = getFullCalModelByCal(cal , person);
		calModelList.add(calModel);
		String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"), "0");
		
		String userListIds = cal.getUser().getUuid() + "";
		//发送短消息提醒
		if(smsRemind.equals("1")){
			long beforTime = (beforeDay * 24 * 60 +   beforeHour  * 60  +  beforeMinute ) * 60 * 1000;
			Map requestData = new HashMap();
			requestData.put("content", "请查看日程安排！内容：" + cal.getContent());
			if(!TeeUtility.isNullorEmpty(model.getActorIds())){
				userListIds = userListIds + "," + model.getActorIds();
			}
////			System.out.println(userListIds);
//			if(beforTime > 0){//设置提前多少时间
//				Date remindDate = new Date();
//				long remindDateTime =remindDate.getTime() + beforTime;
//				remindDate.setTime(remindDateTime);
//				
//			}
			requestData.put("sendTime",null);
			requestData.put("userListIds",userListIds );
			
			requestData.put("moduleNo", "022");
			requestData.put("remindUrl", "/system/core/base/calendar/detail.jsp?id=" + cal.getSid());
			smsManager.sendSms(requestData, person);
			
		}
		
		//发送定时任务，定时任务推送
		TeeQuartzTask task = new TeeQuartzTask();
		task.setContent("日程安排定时提醒：" + cal.getContent());
		task.setFrom(person.getUserId());
		task.setModelId(String.valueOf(cal.getSid()));
		task.setModelNo("022");
		List<TeePerson> actors = cal.getActor();
		Calendar startData = model.getStartDate();
		task.setExp(TeeQuartzUtils.getPreRemindQuartzExpression(startData.get(Calendar.YEAR),
				startData.get(Calendar.MONTH)+1, 
				startData.get(Calendar.DATE), 
				startData.get(Calendar.HOUR_OF_DAY), 
				startData.get(Calendar.MINUTE), 
				startData.get(Calendar.SECOND), beforeDay, beforeHour, beforeMinute));
		task.setExpDesc("提前提醒");
		task.setType(TeeQuartzTypes.ONCE);
		task.setUrl("/system/core/base/calendar/detail.jsp?id=" + cal.getSid());
		task.setUrl1("/system/core/base/calendar/detail.jsp?id=" + cal.getSid());
		
		StringBuffer sb = new StringBuffer();
		if(actors!=null){
			for(TeePerson to : actors){
				sb.append(to.getUserId()+",");
			}
		}
		sb.append(person.getUserId());
		task.setTo(sb.toString());
		MessagePusher.push2Quartz(task);
		
		
		
		json.setRtData(calModelList);
		json.setRtState(true);
		return json;
	}
	
	/***
	 * 将日程转换为 fullCalendar对象
	 * @author syl
	 * @date 2014-1-5
	 * @param cal
	 * @return
	 */
	public TeeFullCalendarModel getFullCalModelByCal(TeeCalendarAffair cal , TeePerson loginUser){
		TeeFullCalendarModel calModel = new TeeFullCalendarModel();
		calModel.setId(cal.getSid());
		calModel.setTitle(cal.getContent());
		calModel.setAllDay(true);
		calModel.setClassName("fc-event-color");
		Date date = new Date();
		date.setTime(cal.getStartTime());//设置开始时间
		calModel.setStart(TeeDateUtil.format(date));
		calModel.setStartTime(calModel.getStart());
		if(cal.getCalAffType() == 1){//周期性事务
			calModel.setType("affair");
			calModel.setEditable(false);
			calModel.setAllDay(false);
			
			calModel.setClassName("fc-event-color-affair");
			if(cal.getUser().getUuid() == loginUser.getUuid()){//如果是系统当前登录人
				calModel.setDeleteable(true);
			}else{
				calModel.setDeleteable(false);
			}
			if(cal.getEndTime() > 0){
				date.setTime(cal.getEndTime());//设置结束时间时间
				calModel.setEnd(TeeDateUtil.format(date));
				calModel.setEndTime(calModel.getEnd());
			}
		}else{//日程
			calModel.setType("calendar");
			if(cal.getEndTime() > 0 && TeeDateUtil.getDaySpanTimeInMillis(cal.getEndTime(), cal.getStartTime()) == 0){//是同一天
				calModel.setAllDay(false);
			}
			
			if(!TeeUtility.isNullorEmpty(cal.getCalLevel()) && !cal.getCalLevel().equals("0") ){
				calModel.setClassName("fc-event-color" + cal.getCalLevel());
			}
			String status = "0";
			String overStatus = cal.getOverStatus() + "";
			
			status = isTimeOut(cal.getStartTime(), cal.getEndTime(), new Date(), overStatus);
			calModel.setStatus(status);
			if(status.equals("2")){//超时设置
		   		//calModel.setClassName("fc-event-color-red");
			}
			
			date.setTime(cal.getEndTime());//设置结束时间时间
			calModel.setEnd(TeeDateUtil.format(date));
			if(cal.getUser().getUuid() == loginUser.getUuid()){//如果是系统当前登录人
				calModel.setEditable(true);
				calModel.setDeleteable(true);
			}else{
				calModel.setEditable(false);
				calModel.setDeleteable(false);
				
			}
		}
		
		return calModel;
	}

	/**
	 * @author syl   快速拖动更新
	 *  更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson updateChangeCal(TeeCalendarAffairModel model ,TeePerson person) {
		TeeJson json = new TeeJson();
		TeeFullCalendarModel calModel = new TeeFullCalendarModel();
		if(model.getSid() > 0){
			TeeCalendarAffair cal = calendarDao.getById(model.getSid());
			if(cal != null){
				cal.setStartTime(model.getStartDate().getTimeInMillis());
				cal.setEndTime(model.getEndDate().getTimeInMillis());
				calendarDao.update(cal);
				json.setRtState(true);
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("更新失败");
		return json;
	
	}
	
	/**
	 * 更改状态 --overStatus
	 * @author syl
	 * @date 2014-1-5
	 * @param model
	 * @param person
	 * @return
	 */
	public TeeJson updateOverStatus(TeeCalendarAffairModel model ,TeePerson person) {
		TeeJson json = new TeeJson();
		TeeFullCalendarModel calModel = new TeeFullCalendarModel();
		if(model.getSid() > 0){
			TeeCalendarAffair cal = calendarDao.getById(model.getSid());
			if(cal != null){
				cal.setOverStatus(model.getOverStatus());
				calendarDao.update(cal);
				json.setRtState(true);
				calModel = getFullCalModelByCal(cal , person);
				json.setRtData(calModel);
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("更新失败");
		return json;
	
	}
	
	
	/**
	 * @author syl
	 * 根据Id查询
	 * @param id
	 * @return
	 */
	public TeeJson selectById(String id) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(id, 0);
		TeeCalendarAffair cal  = calendarDao.getById(sid);
		TeeCalendarAffairModel model =  new TeeCalendarAffairModel();
		if(cal != null  && sid > 0){
			model =parseCalendarmodel(cal);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("未找到相关日程，该日程可能已被删除！");
		}
		json.setRtData(model);
		return json;
	}
	
	/***
	 * 将对象转换模版对襄樊
	 * @author syl
	 * @date 2014-1-12
	 * @param cal
	 * @return
	 */
	public 	TeeCalendarAffairModel parseCalendarmodel(TeeCalendarAffair cal){
		TeeCalendarAffairModel model =  new TeeCalendarAffairModel();
		BeanUtils.copyProperties(cal, model);
		if(cal.getStartTime() > 0){
			Date startDate = new Date();
			startDate.setTime(cal.getStartTime());
			model.setStartTimeStr(TeeDateUtil.format(startDate, "yyyy-MM-dd HH:mm"));
		}
		if(cal.getEndTime() > 0){
			Date startDate = new Date();
			startDate.setTime(cal.getEndTime());
			model.setEndTimeStr(TeeDateUtil.format(startDate, "yyyy-MM-dd HH:mm"));
		}
		if(cal.getManager() != null){//安排人
			model.setManagerId(cal.getManager().getUuid() + "");
			model.setManagerName(cal.getManager().getUserName());
		}
		String actorIds = "";
		String actorNames = "";
		if(cal.getActor() != null && cal.getActor().size()>0 ){//安排人
			for (int i = 0; i < cal.getActor().size() ; i++) {
				actorIds = actorIds + cal.getActor().get(i).getUuid() + ",";
				actorNames = actorNames + cal.getActor().get(i).getUserName() + ",";
			}
		}
		model.setActorIds(actorIds);
		model.setActorNames(actorNames);
		model.setUserId(cal.getUser().getUuid() + "");
		model.setUserName(cal.getUser().getUserName());
		Date currTime = new Date();
		String status = "0";
		String overStatus = cal.getOverStatus() + "";
		status = isTimeOut(cal.getStartTime(), cal.getEndTime(), currTime, overStatus);
		if(TeeUtility.isNullorEmpty(cal.getCalLevel())){
			model.setCalLevel("0");
		}
		model.setStatus(status);
		
		if(cal.getRemindTime() > 0){
			Calendar currSimpCal = TeeUtility.getMinTimeOfDayCalendar(null);//当前一天日程  去掉时分秒
			long test = currSimpCal.getTimeInMillis() + cal.getRemindTime() ;
			currSimpCal.setTimeInMillis(test);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date ss = currSimpCal.getTime();
			String remindTmeStr = TeeUtility.getDateStrByFormat(ss, format);
			model.setRemindTimeStr(remindTmeStr);
		}
		return model;
	}
	
	/**
	 * @author syl
	 * 删除   根据Id
	 * @param id
	 * @return
	 */
	public TeeJson delById(String id) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(id, 0);
		if(sid > 0){
			TeeCalendarAffair cal = calendarDao.get(sid);
			calendarDao.deleteByObj(cal);
			
			//推送消息中心，取消该定时任务
			TeeQuartzTask task = new TeeQuartzTask();
			task.setModelId(String.valueOf(cal.getSid()));
			task.setModelNo("022");
			task.setType(0);
			MessagePusher.push2Quartz(task);
		}
		
		json.setRtState(true);
		json.setRtData("");
		return json;
	}
	
	/**
	 * @author syl
	 * 删除   根据Ids
	 * @param id
	 * @return
	 */
	public TeeJson delByIds(String ids) {
		TeeJson json = new TeeJson();
		calendarDao.delByIds(ids);
		json.setRtState(true);
		json.setRtData("");
		return json;
	}
	
	
	
	/**
	 * 
	 * @author syl
	 * @date 2013-12-24
	 * @param request
	 * @param person  系统当前登录人
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson selectByTime(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String view = TeeStringUtil.getString(request.getParameter("view") , "week");//默认为周视图
		List<TeeCalendarAffair> list = calendarDao.selectByTime(person, startTime, endTime);	
		Date currTime = new Date();
		List<TeeFullCalendarModel> listM = new ArrayList<TeeFullCalendarModel> ();
		
		Date fromDate = TeeUtility.parseDate("yyyy-MM-dd", startTime);
		Date toDate = TeeUtility.parseDate("yyyy-MM-dd", endTime);
		Calendar[] dateList = TeeDateUtil.getDaySpanCalendar(fromDate, toDate);
		for (int i = 0; i < list.size(); i++) {
			TeeFullCalendarModel calModel = new TeeFullCalendarModel();
			TeeCalendarAffair cal = list.get(i);
			if(cal.getCalAffType() == 1){//周期性事务
				//calModel = getFullCalModelByCal(cal ,person);
				 List<TeeFullCalendarModel> affairListModel = getIsUseAffair(cal, dateList, person);
				 listM.addAll(affairListModel);
			}else{//日程
				calModel = getFullCalModelByCal(cal ,person);	
				listM.add(calModel);
			}
	
		}
		json.setRtState(true);
		json.setRtData(listM);
		return json;
	}
	

	
	/**
	 * 将周期性事务转换为有效的事务，如果周 为多个 ，如果是月，也是多个
	 * @author syl
	 * @date 2014-1-18
	 * @param affair 周期性事务对象
	 * @param dateList  日期数组  日、周、月
	 * @return
	 */
	public List<TeeFullCalendarModel> getIsUseAffair(TeeCalendarAffair affair ,Calendar[] dateList , TeePerson person){
		List<TeeFullCalendarModel> affList = new ArrayList<TeeFullCalendarModel>();
	    int type = affair.getRemindType();//提醒类型
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
		if(dateList == null){
			return affList;
		}
	    for (int i = 0; i < dateList.length; i++) {//排除最后一个日期
			Calendar cal = dateList[i];
//			long time = cal.getTimeInMillis() + affair.getRemindTime();
			long time = cal.getTimeInMillis() + affair.getRemindTime();
			Date date = new Date();
			date.setTime(time);
			//calStratTime.add(Calendar.MILLISECOND, (int) affair.getRemindTime());
			TeeFullCalendarModel model = new TeeFullCalendarModel();
			int weekInt = cal.get(Calendar.DAY_OF_WEEK);// 周几

			Calendar tmpCal = Calendar.getInstance();
			tmpCal.setTimeInMillis(affair.getStartTime());
			tmpCal.set(Calendar.HOUR_OF_DAY, 0);
			tmpCal.set(Calendar.MINUTE, 0);
			tmpCal.set(Calendar.SECOND, 0);
			if(tmpCal.getTimeInMillis() >= time ){
				continue;
			}
		
			// 判断日提醒
			if (type == 2) {
				int isWeekend = affair.getIsWeekend();
				if (affair.getEndTime() > 0) {// 结束时间不为空
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大
						if (isWeekend == 1 ) {// 排除周末
							if( weekInt != 1 && weekInt != 7){
								model = getFullCalModelByCal(affair ,person);
								model.setRemindTypeDesc("每日");
								model.setStart(TeeUtility.getDateTimeStr(date));
								model.setEnd("");
								affList.add(model);//
							}
							
						} else {
							model = getFullCalModelByCal(affair ,person);	
							model.setStart(TeeUtility.getDateTimeStr(date));
							model.setRemindTypeDesc("每日");
							model.setEnd("");
							affList.add(model);//
						}
					}
				} else {
					if (isWeekend == 1 && weekInt != 1 && weekInt != 7) {// 排除周末
						model = getFullCalModelByCal(affair ,person);	
						model.setStart(TeeUtility.getDateTimeStr(date));
						model.setRemindTypeDesc("每日");
						model.setEnd("");
						affList.add(model);//
					} else {
						model = getFullCalModelByCal(affair ,person);	
						model.setStart(TeeUtility.getDateTimeStr(date));
						model.setRemindTypeDesc("每日");
						model.setEnd("");
						
						affList.add(model);//
					}
				}
				// 判断周提醒
			} else if (type == 3) {
				if (affair.getEndTime() > 0) {// 结束时间不为空
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大

					} else {
						continue;
					}
				}

				String week = affair.getRemindDate();
				int today = cal.get(Calendar.DAY_OF_WEEK);// 获取星期几
				if (today == 1) {
					today = 7;
				} else {
					today = today - 1;
				}
				if (String.valueOf(today).equals(week)) {// 相等
					model = getFullCalModelByCal(affair ,person);	
					model.setStart(TeeUtility.getDateTimeStr(date));
					model.setRemindTypeDesc( "每周" + weekNames[TeeStringUtil.getInteger(affair.getRemindDate(), 0)-1]);
					model.setEnd("");
					affList.add(model);//
				}
				// 判断月提醒

			} else if (type == 4) {
				if (affair.getEndTime() > 0) {
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大
					} else {
						continue;
					}
				}
				String d = TeeStringUtil.getString(affair.getRemindDate(), "");
				int day = cal.get(Calendar.DAY_OF_MONTH);
				if (d.equals(day + "")) {
					model = getFullCalModelByCal(affair ,person);	
					model.setStart(TeeUtility.getDateTimeStr(date));
					model.setEnd("");
					model.setRemindTypeDesc( "每月" + affair.getRemindDate() + "日");
					affList.add(model);//
				}
				// 按年提醒
			} else if (type == 5) {
				if (affair.getEndTime() > 0) {
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大
					} else {
						continue;
					}
				}
				String monthday = affair.getRemindDate();
				String m = monthday.split("-")[0];
				String d = monthday.split("-")[1];
				if (m.length() == 1) {
					m = "0" + m;
				}
				if (d.length() == 1) {
					d = "0" + d;
				}

				//System.out.println(dateFormat.format(cal.getTime())+"---------" + m + "-" + d);
				if (dateFormat.format(cal.getTime()).equals(m + "-"+  d)) {
					model = getFullCalModelByCal(affair ,person);	
					model.setStart(TeeUtility.getDateTimeStr(date));
					model.setEnd("");
					model.setRemindTypeDesc( "每年" + affair.getRemindDate().split("-")[0] + "月" + affair.getRemindDate().split("-")[1] + "日");
					affList.add(model);//
				}
			}
		}
		return affList;    
	}
	
	/**
	 * 判断日程进行状态
	 * @author syl
	 * @date 2014-1-5
	 * @param startTime
	 * @param endTime
	 * @param currTime
	 * @param overStatus 状态
	 * @return
	 */
	public  String isTimeOut(long startTime  , long endTime , Date currTime , String overStatus1){
		//boolean isTimeOut = false;
		String status = "0";
	    if ((overStatus1 == null || overStatus1.equals("0") || overStatus1.trim().equals("")) ) {
	        if (currTime.getTime() > endTime) {
	            status = "2";// 超时
	        }
	    }else{
	    	status = overStatus1;
	    }
		
        return status;
	}
	
	/**
	 * 个人日程查询
	 * @author syl
	 * @date 2014-1-3
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> queryCal(HttpServletRequest request ,TeeCalendarAffairModel model){
		 TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		 List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
	      String sendTimeMin = request.getParameter("sendTimeMin");
	      String sendTimeMax = request.getParameter("sendTimeMax");
	      String calLevel = request.getParameter("calLevel");
	      String calType = request.getParameter("calType");
	      // System.out.println(calType);
	      String overStatus = request.getParameter("overStatus");
	      String content = request.getParameter("content");

	      
	      List<TeeCalendarAffair> calendarList  = calendarDao.queryCal(user, model);
	      
	      Date date = new Date();
	      long dateTime = date.getTime();
	      long begin = 0;
	      long end = 0;
	      for (int i = 0; i < calendarList.size(); i++) {
	        String status = "0";// 进行中 判断判断状态

	        Map<String, String> map = new HashMap<String, String>();
	        TeeCalendarAffair calendar = calendarList.get(i);
	        map.put("seqId", String.valueOf(calendar.getSid()));
	        map.put("userId", calendar.getUser().getUuid() + "");
	        map.put("calLevel", calendar.getCalLevel());
	        map.put("calType", calendar.getCalType() + "");
	        map.put("content", calendar.getContent());
	        map.put("isDelete", "0");
	        if(calendar.getUser().getUuid() == user.getUuid()){
	        	 map.put("isDelete", "1");//能删除
	        }
	        if (calendar.getStartTime() >0) {
	        	Date sdate = new Date();
	        	sdate.setTime(calendar.getStartTime());
	        	map.put("calTime", TeeDateUtil.getDateTimeStr(sdate));
	        } else {
	          map.put("calTime", "");
	        }
	        if (calendar.getEndTime() > 0) {
	        	Date edate = new Date();
	        	edate.setTime(calendar.getEndTime());
	         	map.put("endTime", TeeDateUtil.getDateTimeStr(edate));
	        } else {
	          map.put("endTime", "");
	        }

	        map.put("overStatus", calendar.getOverStatus() + "");
	        // System.out.println(calendar.getManagerId());
	        if (calendar.getManager() != null) {
	          map.put("managerName", calendar.getManager().getUserName());
	        } else {
	          map.put("managerName", "");
	        }
	        String overStatus1 = calendar.getOverStatus() + "";
	        if (overStatus1 == null || overStatus1.equals("0")
	            || overStatus1.trim().equals("")) {
	          begin = calendar.getStartTime();
	          end = calendar.getEndTime();
	          if (dateTime < begin) {
	            status = "1";// 未开始

	          }
	          if (dateTime > end) {
	            status = "2";// 超时
	          }
	        }
	        map.put("status", status);
	        list.add(map);
	      }
		return list;
	}
	
	
	
	/**
	 * 导出日程安排数据
	 * @author syl
	 * @date 2014-1-22
	 * @param model
	 * @param loginPerson
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportCalendarToCsv(HttpServletRequest request ,TeeCalendarAffairModel model){
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
	    String sendTimeMin = request.getParameter("startTime");
	    String sendTimeMax = request.getParameter("endTime");
	    String calType = request.getParameter("calType");
	    List<TeeCalendarAffair> calendarList  = calendarDao.queryCal(user, model);
		 
		for (int i = 0; i <calendarList.size(); i++) {
			TeeCalendarAffair calendar = calendarList.get(i);
			TeePerson person = calendar.getUser();
			TeeDataRecord dr = new TeeDataRecord();
			
			String deptName = "";//部门名称
	
			if(person.getDept() != null){
				deptName = person.getDept().getDeptName();
			}
			dr.addField("部门", deptName);
			dr.addField("姓名", person.getUserName());
			String startTime = "";
			String endTime = "";
			Date date = new Date();
			if(calendar.getStartTime() > 0){
				date.setTime(calendar.getStartTime());
				startTime = format.format(date);
			}
			
			if(calendar.getEndTime() > 0){
				date.setTime(calendar.getEndTime());
				endTime = format.format(date);
			}
			
			dr.addField("开始时间", startTime);
			dr.addField("结束时间", endTime);
			
			String calTypeStr = "工作事务";
			if(calendar.getCalType() == 2){
				calTypeStr = "个人事务";
			}
			dr.addField("事务类型", calTypeStr);
			dr.addField("事务内容", calendar.getContent());
			String managerName = "";
			if(calendar.getManager() != null){
				managerName = calendar.getManager().getUserName();
			}
			dr.addField("安排人", managerName);
			list.add(dr);
		}
		return list;
	}
	
	/**
	 * 导出日程
	 * @author syl
	 * @date 2014-1-23
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson importCalendar(HttpServletRequest request) throws Exception{
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		StringBuffer sb = new StringBuffer("S");
		String infoStr= "";
		
		int importSuccessCount = 0;
		try {
			MultipartFile  file = multipartRequest.getFile("importFile");
			if(!file.isEmpty() ){
				//获取真实文件名称
				String realName = file.getOriginalFilename();
				ArrayList<TeeDataRecord> dbL = TeeCSVUtil.CVSReader(file.getInputStream(), "GBK");
				List<TeePerson> addDeptList = new ArrayList<TeePerson>();
				for (int i = 0; i < dbL.size(); i++) {
					TeeDataRecord dr = dbL.get(i);
					TeeCalendarAffair calendar = new TeeCalendarAffair();
					String userName = TeeStringUtil.getString(dr.getValueByName("姓名"));
					String deptName = TeeStringUtil.getString(dr.getValueByName("部门"));
					String startTime = TeeStringUtil.getString(dr.getValueByName("开始时间"));
					String endTime = TeeStringUtil.getString(dr.getValueByName("结束时间"));
					int calType = TeeStringUtil.getInteger(dr.getValueByName("事务类型") , 1);
					String content = TeeStringUtil.getString(dr.getValueByName("事务内容"));
					String managerName = TeeStringUtil.getString(dr.getValueByName("安排人"));
					if(TeeUtility.isNullorEmpty(userName)){
						continue;
					}
					
					if(TeeUtility.isNullorEmpty(startTime)){
						continue;
					}
					Date startDate = TeeDateUtil.parseDateByPattern(startTime);
					if(startDate == null){
						continue;
					}
					if(TeeUtility.isNullorEmpty(endTime)){
						continue;
					}
					Date endDate = TeeDateUtil.parseDateByPattern(endTime);
					if(endDate == null){
						continue;
					}
					/*List<TeePerson> personlist = personDao.getPersonByUserName(userName);
					if(personlist.size() > 0){
						
					}*/
					calendar.setCalType(calType);
					calendar.setContent(content);
					calendar.setOverStatus(0);//完成状态
					calendar.setUser(loginPerson);
					calendar.setCalAffType(0);//日程
					calendar.setStartTime(startDate.getTime());
					calendar.setEndTime(endDate.getTime());
					calendarDao.addCalendar(calendar);
					importSuccessCount++;
			
				}		
			}else{
				json.setRtState(false);
				json.setRtMsg("文件为空！");
				return json;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		
		sb.append("");
		json.setRtData(sb);
		json.setRtState(true);
		json.setRtMsg(importSuccessCount + "");
		return json;
	}
	
	
	/**
	 * 个人桌面 --- 
	 * @author syl
	 * @date 2014-2-12
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getDesktop(HttpServletRequest request) throws ParseException{
		TeeJson json = new TeeJson();
		String queryCalendarType = TeeStringUtil.getString(request.getParameter("queryCalendarType") , "todayCalendar");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    TeeCalendarAffairModel affairmodel = new TeeCalendarAffairModel();
	    
	    List<TeeCalendarAffairModel> listModel = new ArrayList<TeeCalendarAffairModel>();
	    List<TeeCalendarAffairModel> listRoundModel = new ArrayList<TeeCalendarAffairModel>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    int userId = person.getUuid();
	    Calendar calendar = Calendar.getInstance();
	    int week = calendar.get(Calendar.DAY_OF_WEEK);
	    int day = calendar.get(Calendar.DATE);
	    int month = calendar.get(Calendar.MONTH);
	    if(week==1){
	      week = 7;
	    }else{
	      week = week-1;
	    }
	    month = month+1;
	    Date dateCur = new Date();  
	    String curDate = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
	    Date startTimeDate = TeeUtility.parseDate("yyyy-MM-dd", curDate);
		Date endTimeDate = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", curDate + " 23:59:59");
	    if(queryCalendarType.equals("roundCalendar")){//取近日 10天之内
	    	Date roundDate  = TeeUtility.getDayAfter(dateCur, 10);//10天后的日期
	    	curDate = TeeUtility.getDateStrByFormat(roundDate, dateFormat);
	    	endTimeDate = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", curDate + " 23:59:59");
	    }
	  
	    
	  
	    

	    
		
	
	    long beginTime = startTimeDate.getTime();////T9DBUtility.getDateFilter("BEGIN_TIME", curDate + " 23:59:59", "<=");
	    long endTime = endTimeDate.getTime();//T9DBUtility.getDateFilter("END_TIME", curDate , ">=");
	    List<TeeCalendarAffair> affairList = calendarDao.getDesktop(affairmodel ,person , beginTime ,endTime);
		for (int i = 0; i < affairList.size(); i++) {
			TeeCalendarAffair affair = affairList.get(i);
			boolean isUserAffair = false;
			 TeeCalendarAffairModel calModel = new TeeCalendarAffairModel();
			if (affair.getCalAffType() == 1) {// 周期性事务
				int isWeekEnd = affair.getIsWeekend();// 是否排除周末

				if (affair.getRemindType() == 2) {// 按日提醒
					if (isWeekEnd == 1) {// 排除周末
						if (week != 6 && week != 7) {
							isUserAffair = true;
						}
					} else {
						isUserAffair = true;
					}

				} else if (affair.getRemindType() == 3) {// 按周提醒
					if (affair.getRemindDate().equals(String.valueOf(week))) {
						isUserAffair = true;
					}
				} else if (affair.getRemindType() == 4
						&& affair.getRemindDate().equals(String.valueOf(day))) {// 月提醒
					isUserAffair = true;
				} else if (affair.getRemindType() == 4
						&& affair.getRemindDate().equals(
								String.valueOf(month) + "-"
										+ String.valueOf(day))) {// 年提醒
					isUserAffair = true;
				}

			} else {
				isUserAffair = true;
			}
			if (isUserAffair) {
				calModel = parseCalendarmodel(affair);
				listModel.add(calModel);
			}
		}
		
		/*Map map = new HashMap();
		map.put("today", listModel);//今天
		map.put("round", listRoundModel);//近日10天内
*/		json.setRtState(true);
		json.setRtData(listModel);
		return json;
		
	}
	
	
	/**
	 * 桌面小模块数据请求
	 * @author syl
	 * @date 2014-2-12
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public List desktopPortletDataRequest(TeePerson loginPerson,String date){
		Calendar begin = TeeDateUtil.parseCalendar(date+" 00:00:00");
		Calendar end = TeeDateUtil.parseCalendar(date+" 23:59:59");
		String sql="select "
				+ "c.sid as sid,"
				+ "c.startTime as startTime,"
				+ "c.calLevel as calLevel,"
				+ "c.content as content "
				+ " from TeeCalendarAffair c where c.calAffType=0 and ((startTime>="+begin.getTimeInMillis()+" and endTime<="+end.getTimeInMillis()+") or (startTime<="+begin.getTimeInMillis()+" and endTime>="+end.getTimeInMillis()+") or (endTime<="+end.getTimeInMillis()+" and endTime>="+begin.getTimeInMillis()+") or (startTime>="+begin.getTimeInMillis()+" and startTime<="+end.getTimeInMillis()+"))"+" "
				+ " and (c.user.uuid="+loginPerson.getUuid()+" or exists (select 1 from c.actor a where a.uuid="+loginPerson.getUuid()+")) order by c.startTime asc";
		List<Map> list = simpleDaoSupport.getMaps(sql, null);
		return list;
	}
	
	
	public Set desktopPortletData4GetMonthData(TeePerson loginPerson,int year,int month){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String monthStr = month<10?("0"+month):month+"";
		Calendar begin = TeeDateUtil.parseCalendar(year+"-"+monthStr+"-01 00:00:00");
		int maxDate = begin.getActualMaximum(Calendar.DATE);
		Calendar end = TeeDateUtil.parseCalendar(year+"-"+monthStr+"-"+(maxDate<10?("0"+maxDate):maxDate+"")+" 23:59:59");
		
		List<TeeCalendarAffair> list = simpleDaoSupport.find(" from TeeCalendarAffair c where  ((startTime>="+begin.getTimeInMillis()+" and endTime<="+end.getTimeInMillis()+") or (startTime<="+begin.getTimeInMillis()+" and endTime>="+end.getTimeInMillis()+") or (endTime>="+end.getTimeInMillis()+" and startTime<="+end.getTimeInMillis()+") or (startTime<="+begin.getTimeInMillis()+" and endTime>="+begin.getTimeInMillis()+")) and (c.user.uuid="+loginPerson.getUuid()+" or exists (select 1 from c.actor a where a.uuid="+loginPerson.getUuid()+")) and c.calAffType=0 order by c.startTime asc", null);
		Set returnList = new HashSet();
		
		Calendar c = Calendar.getInstance();
		TeeCalendarAffair cal=null;
		String startStr="";
		String endStr="";
		try {
			for(int i=0;i<list.size();i++){
				cal=list.get(i);
				//开始时间
				Calendar startTime = Calendar.getInstance();
				startTime.setTimeInMillis(cal.getStartTime());
				startStr=sdf.format(startTime.getTime())+" 00:00:00";
				startTime.setTime((Date)sdf1.parse(startStr));
				//startTime.setTimeInMillis(startTime.getTimeInMillis());
				
				
				//结束时间
				Calendar endTime = Calendar.getInstance();
				endTime.setTimeInMillis(cal.getEndTime());
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

	

	public TeeCalendarAffairDao getCalendarDao() {
		return calendarDao;
	}

	public void setCalendarDao(TeeCalendarAffairDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	public TeePersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}


	
	
}


