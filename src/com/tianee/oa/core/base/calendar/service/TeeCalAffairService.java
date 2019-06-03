package com.tianee.oa.core.base.calendar.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.core.base.calendar.bean.TeeCalendarAffair;
import com.tianee.oa.core.base.calendar.dao.TeeCalAffairDao;
import com.tianee.oa.core.base.calendar.dao.TeeCalendarAffairDao;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.base.calendar.model.TeeFullCalendarModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.quartz.TeeQuartzTypes;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 * 
 */
@Service
public class TeeCalAffairService extends TeeBaseService {
	@Autowired
	private TeeCalAffairDao calAffairDao;

	@Autowired
	private TeeCalendarAffairDao calendarAffairDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeCalendarAffairService calendarAffairService;

	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * @author syl 新增 或者 更新
	 * @param message
	 * @param person
	 *            系统当前登录人
	 * @return
	 * @throws ParseException
	 */
	public TeeJson addOrUpdateAffair(HttpServletRequest request,
			HttpServletResponse response, TeeCalendarAffairModel model,
			TeePerson person) throws ParseException {
		TeeJson json = new TeeJson();
		List<TeeFullCalendarModel> calModelList = new ArrayList<TeeFullCalendarModel>();
		TeeCalendarAffair affair = new TeeCalendarAffair();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		// 获取开始和结束时间
		String startTime = request.getParameter("startTimeStr");
		String endTime = request.getParameter("endTimeStr");
		Date currTime = new Date();
		Calendar[] dateList = null;
		List<TeeFullCalendarModel> listM = new ArrayList<TeeFullCalendarModel>();
		if (!TeeUtility.isNullorEmpty(startTime)
				&& !TeeUtility.isNullorEmpty(endTime)) {
			Date fromDate = TeeUtility.parseDate("yyyy-MM-dd", startTime);
			Date toDate = TeeUtility.parseDate("yyyy-MM-dd", endTime);
			dateList = TeeDateUtil.getDaySpanCalendar(fromDate, toDate);
		}

		Date curDate = new Date();
		Calendar currSimpCal = TeeUtility.getMinTimeOfDayCalendar(null);// 当前一天日程
																		// 去掉时分秒
		long maxTime = (23 * 60 * 60 + 59 * 60 + 59) * 1000;// 一天最大毫秒

		String curDateStr = dateFormat.format(curDate);
		String isWeekend = request.getParameter("isWeekend");

		// System.out.println(type);
		// 判断今天是否可以为提醒时间

		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);// 周
		int day = calendar.get(Calendar.DATE);// 日
		int month = calendar.get(Calendar.MONTH);// 月
		if (week == 0) {
			week = 7;
		} else {
			week = week - 1;
		}
		month = month + 1;
		if (isWeekend != null) {// 是否包含周末 1-排除
			affair.setIsWeekend(1);
		}
		String remindTime = request.getParameter("remindTime");
		String remindDate = "";

		if (model.getRemindType() == 3) {// 周
			remindDate = request.getParameter("remindDate3");
			// remindTime = request.getParameter("remindTime3");
		}
		if (model.getRemindType() == 4) {// 月
			remindDate = request.getParameter("remindDate4");
			// remindTime = request.getParameter("remindTime4");
		}
		if (model.getRemindType() == 5) {// 年
			remindDate = request.getParameter("remindDate5Mon") + "-"
					+ request.getParameter("remindDate5Day");
			// remindTime = request.getParameter("remindTime5");
		}
		if (model.getStartDate() != null) {// 开始时间
			affair.setStartTime(model.getStartDate().getTimeInMillis());
		} else {
			affair.setStartTime(calendar.getTimeInMillis());
		}
		if (model.getEndDate() != null) {// 结束时间
			affair.setEndTime(model.getEndDate().getTimeInMillis());
		}
		if (TeeUtility.isNullorEmpty(remindTime)) {
			affair.setRemindTime(maxTime);
		} else {
			String currTimeStr = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
			Date data1 = TeeUtility.parseDate(currTimeStr);
			currTimeStr = currTimeStr + " " + remindTime;
			Date data2 = TeeUtility.parseDate(currTimeStr);
			calendar.setTime(data2);// 设置带时分秒
			long time2 = calendar.getTimeInMillis();
			calendar.setTime(data1);// 设置不带时分秒
			long time1 = calendar.getTimeInMillis();
			long time = time2 - time1;
			affair.setRemindTime(time);
		}

		affair.setContent(model.getContent());
		affair.setCalType(model.getCalType());
		affair.setRemindDate(remindDate);
		affair.setRemindType(model.getRemindType());
		affair.setUser(person);
		if (model.getIsWeekend() == 1) {
			affair.setIsWeekend(1);
		} else {
			affair.setIsWeekend(0);
		}

		if (!TeeUtility.isNullorEmpty(model.getActorIds())) {// 参与者
			List<TeePerson> list = personDao.getPersonByUuids(model
					.getActorIds());
			affair.setActor(list);
		}

		request.setAttribute("remindDate", remindDate);
		request.setAttribute("remindTime", remindTime);
		if (model.getSid() > 0) {
			TeeCalendarAffair affairOld = calendarAffairDao.getById(model
					.getSid());
			if (affairOld != null) {
				BeanUtils.copyProperties(model, affairOld);
				affairOld.setIsWeekend(affair.getIsWeekend());
				affairOld.setEndTime(affair.getEndTime());
				affairOld.setStartTime(affair.getStartTime());
				affairOld.setActor(affair.getActor());
				affairOld.setRemindDate(affair.getRemindDate());
				affairOld.setRemindTime(affair.getRemindTime());
				calAffairDao.update(affairOld);
				json.setRtMsg("1");// 更新
				/*
				 * calModel =
				 * calendarAffairService.getFullCalModelByCal(affairOld ,
				 * person); calModel.setId(affairOld.getSid());
				 */
				List<TeeFullCalendarModel> affairListModel = calendarAffairService
						.getIsUseAffair(affairOld, dateList, person);
				calModelList.addAll(affairListModel);
				json.setRtData(calModelList);
				json.setRtState(true);

				affair = affairOld;

				// 处理短信提醒
				selectCalAffairRemindByToday(request, response, affair);
				// return json;
			} else {
				json.setRtState(false);
				json.setRtMsg("未找到周期性事务，更新失败");
				return json;
			}

		} else {
			affair.setCalAffType(1);
			calendarAffairDao.addCalendar(affair);
			// 处理短信提醒
			selectCalAffairRemindByToday(request, response, affair);
			json.setRtMsg("0");// 新建
		}

		/*
		 * calModel = calendarAffairService.getFullCalModelByCal(affair ,
		 * person); calModel.setId(affair.getSid());
		 */
		List<TeeFullCalendarModel> affairListModel = calendarAffairService
				.getIsUseAffair(affair, dateList, person);
		calModelList.addAll(affairListModel);
		json.setRtData(calModelList);
		json.setRtState(true);

		return json;
	}

	/***
	 * 获取所有当前用户相关周期性事务
	 * 
	 * @author syl
	 * @date 2014-1-7
	 * @param person
	 * @return
	 */
	public TeeJson getAllAffair(TeePerson person) {
		List<TeeCalendarAffair> list = calAffairDao.selectAll(person);
		List<TeeCalendarAffairModel> listModel = new ArrayList<TeeCalendarAffairModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCalendarAffair affair = list.get(i);
			TeeCalendarAffairModel model = new TeeCalendarAffairModel();
			model = parseAffairModel(affair);
			listModel.add(model);
		}
		TeeJson json = new TeeJson();
		json.setRtData(listModel);
		json.setRtMsg("");
		json.setRtState(true);
		return json;
	}

	/***
	 * 获取所有当前用户相关周期性事务
	 * 
	 * @author syl
	 * @date 2014-1-7
	 * @param person
	 * @return
	 */
	public TeeJson queryAffair(HttpServletRequest request, TeePerson person) {
		TeeCalendarAffairModel affairmodel = new TeeCalendarAffairModel();
		String content = request.getParameter("contentQuery");
		String startTime = request.getParameter("startTime");// 开始时间
		String endTime = request.getParameter("endTime");// 结束时间
		affairmodel.setContent(content);
		List<TeeCalendarAffair> list = calAffairDao.query(affairmodel, person,
				startTime, endTime);
		List<TeeCalendarAffairModel> listModel = new ArrayList<TeeCalendarAffairModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCalendarAffair affair = list.get(i);
			TeeCalendarAffairModel model = new TeeCalendarAffairModel();
			model = parseAffairModel(affair);
			listModel.add(model);
		}
		TeeJson json = new TeeJson();
		json.setRtData(listModel);
		json.setRtMsg("");
		json.setRtState(true);
		return json;
	}

	/**
	 * @author syl 根据Id查询
	 * @param id
	 * @return
	 */
	public TeeJson selectById(String id) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(id, 0);
		TeeCalendarAffair affair = calendarAffairDao.getById(sid);
		TeeCalendarAffairModel model = new TeeCalendarAffairModel();
		if (affair != null) {
			model = parseAffairModel(affair);
			json.setRtData(model);
			json.setRtState(true);
		} else {
			json.setRtState(false);
			json.setRtMsg("未找到相关日程,该可能已被删除！");
		}
		json.setRtData(model);
		return json;
	}

	/**
	 * @author syl 删除 根据Id
	 * @param id
	 * @return
	 */
	public TeeJson delById(String id) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(id, 0);
		if (sid > 0) {
			TeeCalendarAffair cal = calendarAffairDao.get(sid);
			calendarAffairDao.deleteByObj(cal);
			
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
	 * 转换模型
	 * 
	 * @author syl
	 * @date 2014-1-9
	 * @param affair
	 * @return
	 */
	public TeeCalendarAffairModel parseAffairModel(TeeCalendarAffair affair) {
		TeeCalendarAffairModel model = new TeeCalendarAffairModel();
		Calendar cal = Calendar.getInstance();
		BeanUtils.copyProperties(affair, model);
		if (affair.getManager() != null) {// 安排人
			model.setManagerId(affair.getManager().getUuid() + "");
			model.setManagerName(affair.getManager().getUserName());
		} else {
			model.setManagerId("");
			model.setManagerName("");
		}
		String actorIds = "";
		String actorNames = "";
		if (affair.getActor() != null && affair.getActor().size() > 0) {// 安排人
			for (int i = 0; i < affair.getActor().size(); i++) {
				actorIds = actorIds + affair.getActor().get(i).getUuid() + ",";
				actorNames = actorNames
						+ affair.getActor().get(i).getUserName() + ",";
			}
		}
		if (affair.getStartTime() > 0) {
			cal.setTimeInMillis(affair.getStartTime());
			model.setStartTimeStr(TeeUtility.getDateTimeStr(cal.getTime()));

		}
		if (affair.getEndTime() > 0) {
			cal.setTimeInMillis(affair.getEndTime());
			model.setEndTimeStr(TeeUtility.getDateTimeStr(cal.getTime()));
		}
		if (affair.getRemindTime() > 0) {
			Calendar currSimpCal = TeeUtility.getMinTimeOfDayCalendar(null);// 当前一天日程
																			// 去掉时分秒
			long test = currSimpCal.getTimeInMillis() + affair.getRemindTime();
			currSimpCal.setTimeInMillis(test);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date ss = currSimpCal.getTime();
			String remindTmeStr = TeeUtility.getDateStrByFormat(ss, format);
			model.setRemindTimeStr(remindTmeStr);
		}
		model.setActorIds(actorIds);
		model.setActorNames(actorNames);
		model.setUserId(affair.getUser().getUuid() + "");
		model.setUserName(affair.getUser().getUserName());
		return model;
	}

	/**
	 * @author syl 删除 根据Ids
	 * @param id
	 * @return
	 */
	public TeeJson delByIds(String ids) {
		TeeJson json = new TeeJson();
		calendarAffairDao.delByIds(ids);
		json.setRtState(true);
		json.setRtData("");
		return json;
	}

	/**
	 * 查询当天需要短信提醒的周期性事务 以及更新新周期性事物最后一次提醒时间
	 * 
	 * @author syl
	 * @date 2014-2-11
	 * @param request
	 * @param response
	 * @throws ParseException
	 * @throws Exception
	 */
	public void selectCalAffairRemindByToday(HttpServletRequest request,
			HttpServletResponse response, TeeCalendarAffair affair)
			throws ParseException {

		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		int isWeekEnd = affair.getIsWeekend();// 是否排除周末
		
		// 处理定时任务
		TeeQuartzTask task = new TeeQuartzTask();
		task.setContent("周期性事务：" + affair.getContent());
		task.setModelId(String.valueOf(affair.getSid()));
		task.setModelNo("022");
		task.setUrl("/system/core/base/calendar/affair/detail.jsp?id="
				+ affair.getSid());
		task.setUrl1("/system/core/base/calendar/affair/detail.jsp?id="
				+ affair.getSid());
		task.setFrom(person.getUserId());
		
		String remindTime = request.getParameter("remindTime");
		String remindTimeSp[] = remindTime.split(":");
		int hours = Integer.parseInt(remindTimeSp[0]);
		int minutes = Integer.parseInt(remindTimeSp[1]);
		int seconds = Integer.parseInt(remindTimeSp[2]);
		
		if (affair.getRemindType() == 2) {// 按日提醒
			task.setType(TeeQuartzTypes.DAY);
			if (isWeekEnd == 1) {// 排除周末
				task.setExp(TeeQuartzUtils.getDayQuartzExpression(hours,minutes, seconds, new int[]{1,2,3,4,5}));
			}else{
				task.setExp(TeeQuartzUtils.getDayQuartzExpression(hours,minutes, seconds, null));
			}
		} else if (affair.getRemindType() == 3) {// 按周提醒
			task.setType(TeeQuartzTypes.WEEK);
			int week = Integer.parseInt(affair.getRemindDate());
			task.setExp(TeeQuartzUtils.getWeekQuartzExpression(week, hours, minutes, seconds));
		} else if (affair.getRemindType() == 4) {// 月提醒
			task.setType(TeeQuartzTypes.MONTH);
			int month = Integer.parseInt(affair.getRemindDate());
			task.setExp(TeeQuartzUtils.getMonthQuartzExpression(month, hours, minutes, seconds));
		} else if (affair.getRemindType() == 5) {// 年提醒
			task.setType(TeeQuartzTypes.YEAR);//
			String sp[] = affair.getRemindDate().split("-");
			int month = Integer.parseInt(sp[0]);
			int date = Integer.parseInt(sp[1]);
			task.setExp(TeeQuartzUtils.getYearQuartzExpression(month, date, hours, minutes, seconds));
		}
		
		StringBuffer sb = new StringBuffer();
		
		//推送相关人士
		List<TeePerson> list = affair.getActor();
		if(list!=null){
			for(TeePerson p:list){
				sb.append(p.getUserId()+",");
			}
		}
		
		sb.append(person.getUserId());
		task.setTo(sb.toString());
		MessagePusher.push2Quartz(task);

		// for (int i = 0; i < affairList.size(); i++) {
		// TeeCalendarAffair affair = affairList.get(i);
		// int isWeekEnd = affair.getIsWeekend();//是否排除周末
		// boolean isUserAffair = false;
		// if(affair.getRemindType() == 2){//按日提醒
		// if(isWeekEnd ==1 ){//排除周末
		// if(week!=6&&week!=7){
		// isUserAffair = true;
		// }
		// }else{
		// isUserAffair = true;
		// }
		//
		// }else if(affair.getRemindType() == 3){//按周提醒
		// if(affair.getRemindDate().equals(String.valueOf(week))){
		// isUserAffair = true;
		// }
		// }else if(affair.getRemindType() == 4 &&
		// affair.getRemindDate().equals(String.valueOf(day))){//月提醒
		// isUserAffair = true;
		// }else if(affair.getRemindType() == 4 &&
		// affair.getRemindDate().equals(String.valueOf(month)+"-"+String.valueOf(day))){//年提醒
		// isUserAffair = true;
		// }
		// if(isUserAffair){
		// Map requestData = new HashMap();
		// requestData.put("content", "日程事务提醒!内容：" + affair.getContent());
		// String userListIds = affair.getUser().getUuid() + "";
		// List<TeePerson> listPerson = affair.getActor();
		// if(listPerson != null){
		// for (int j = 0; j < listPerson.size() ; j++) {
		// userListIds = userListIds + "," + listPerson.get(j).getUuid() ;
		// }
		// }
		// requestData.put("userListIds",userListIds );
		// requestData.put("moduleNo", "022");
		// long remindTime = affair.getRemindTime();
		// Date newDate = new Date();
		// newDate.setTime(beginTime + remindTime);
		// requestData.put("sendTime",TeeUtility.getDateTimeStr(newDate) );
		// requestData.put("remindUrl",
		// "/system/core/base/calendar/affair/detail.jsp?id=" + affair.getSid());
		// if(affair.getManager() != null){
		// person = affair.getManager();
		// }
		// smsManager.sendSms(requestData, person);
		// //更新最后一次提醒时间
		// affair.setLastRemind(beginTime + remindTime);
		// calendarAffairDao.updateCalendar(affair);
		// }
		// }
	}

	public void setCalAffairDao(TeeCalAffairDao calAffairDao) {
		this.calAffairDao = calAffairDao;
	}

	public void setCalendarAffairDao(TeeCalendarAffairDao calendarAffairDao) {
		this.calendarAffairDao = calendarAffairDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

	public void setCalendarAffairService(
			TeeCalendarAffairService calendarAffairService) {
		this.calendarAffairService = calendarAffairService;
	}

}
