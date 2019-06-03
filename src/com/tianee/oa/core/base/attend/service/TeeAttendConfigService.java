package com.tianee.oa.core.base.attend.service;

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

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.bean.TeeAttendDuty;
import com.tianee.oa.core.base.attend.bean.TeeAttendHoliday;
import com.tianee.oa.core.base.attend.dao.TeeAttendConfigDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendDutyDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendHolidayDao;
import com.tianee.oa.core.base.attend.model.TeeAttendConfigModel;
import com.tianee.oa.core.base.attend.model.TeeAttendHolidayModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeAttendConfigService extends TeeBaseService {
	@Autowired
	private TeeAttendConfigDao attendConfigDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeAttendHolidayDao holidayDao;

	@Autowired
	private TeeSmsManager smsManager;

	@Autowired
	TeeSysParaDao sysParaDao;

	@Autowired
	TeeAttendDutyDao dutyDao;
	
	
	@Autowired
	private TeeAttendSeniorConfigService  seniorService;

	public TeeJson addConfig(HttpServletRequest request, TeeAttendConfigModel model) throws Exception {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeAttendConfig config = new TeeAttendConfig();
		BeanUtils.copyProperties(model, config);
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc1())) {
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(model.getDutyTimeDesc1()));
			config.setDutyTime1(cl1);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc2())) {
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(model.getDutyTimeDesc2()));
			config.setDutyTime2(cl2);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc3())) {
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(sf.parse(model.getDutyTimeDesc3()));
			config.setDutyTime3(cl3);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc4())) {
			Calendar cl4 = Calendar.getInstance();
			cl4.setTime(sf.parse(model.getDutyTimeDesc4()));
			config.setDutyTime4(cl4);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc5())) {
			Calendar cl5 = Calendar.getInstance();
			cl5.setTime(sf.parse(model.getDutyTimeDesc5()));
			config.setDutyTime5(cl5);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc6())) {
			Calendar cl6 = Calendar.getInstance();
			cl6.setTime(sf.parse(model.getDutyTimeDesc6()));
			config.setDutyTime6(cl6);
		}
		attendConfigDao.addConfig(config);
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	public TeeJson addHoliday(HttpServletRequest request, TeeAttendHolidayModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeeAttendHoliday holiday = new TeeAttendHoliday();
		BeanUtils.copyProperties(model, holiday);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (!TeeUtility.isNullorEmpty(model.getStartTimeDesc())) {
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(model.getStartTimeDesc()));
			holiday.setStartTime(cl1);
		}
		if (!TeeUtility.isNullorEmpty(model.getEndTimeDesc())) {
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(model.getEndTimeDesc()));
			holiday.setEndTime(cl2);
		}
		holidayDao.addHoliday(holiday);
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	public TeeJson getConfig(HttpServletRequest request, TeeAttendConfigModel model) {
		TeeJson json = new TeeJson();
		List<TeeAttendConfig> list = attendConfigDao.getConfigList();
		List<TeeAttendConfigModel> listModel = new ArrayList<TeeAttendConfigModel>();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}

	public TeeAttendConfigModel parseModel(TeeAttendConfig config) {
		TeeAttendConfigModel model = new TeeAttendConfigModel();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		if (config == null) {
			return null;
		}
		BeanUtils.copyProperties(config, model);
		if (!TeeUtility.isNullorEmpty(config.getDutyTime1())) {

			model.setDutyTimeDesc1(sf.format(config.getDutyTime1().getTime()));
		}
		if (!TeeUtility.isNullorEmpty(config.getDutyTime2())) {

			model.setDutyTimeDesc2(sf.format(config.getDutyTime2().getTime()));
		}
		if (!TeeUtility.isNullorEmpty(config.getDutyTime3())) {

			model.setDutyTimeDesc3(sf.format(config.getDutyTime3().getTime()));
		}
		if (!TeeUtility.isNullorEmpty(config.getDutyTime4())) {

			model.setDutyTimeDesc4(sf.format(config.getDutyTime4().getTime()));
		}
		if (!TeeUtility.isNullorEmpty(config.getDutyTime5())) {

			model.setDutyTimeDesc5(sf.format(config.getDutyTime5().getTime()));
		}
		if (!TeeUtility.isNullorEmpty(config.getDutyTime6())) {

			model.setDutyTimeDesc6(sf.format(config.getDutyTime6().getTime()));
		}
		return model;
	}

	public TeeJson deleteById(int sid) {
		TeeJson json = new TeeJson();
		if (sid > 0) {
			attendConfigDao.delById(sid);
			json.setRtState(true);
			json.setRtMsg("删除成功!");
			return json;

		}
		json.setRtState(false);
		json.setRtMsg("未找到相关记录！");
		return json;
	}

	public TeeJson getById(int sid) {
		TeeJson json = new TeeJson();
		TeeAttendConfigModel model = new TeeAttendConfigModel();
		if (sid > 0) {
			TeeAttendConfig config = attendConfigDao.getById(sid);
			if (config != null) {
				model = parseModel(config);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关外出记录！");
		return json;
	}

	public TeeJson deleteHolidayById(int sid) {
		TeeJson json = new TeeJson();
		if (sid > 0) {
			holidayDao.delById(sid);
			json.setRtState(true);
			json.setRtMsg("删除成功!");
			return json;

		}
		json.setRtState(false);
		json.setRtMsg("未找到相关记录！");
		return json;
	}

	public TeeJson getHolidayById(int sid) {
		TeeJson json = new TeeJson();
		TeeAttendHolidayModel model = new TeeAttendHolidayModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (sid > 0) {
			TeeAttendHoliday holiday = holidayDao.getById(sid);
			if (holiday != null) {
				BeanUtils.copyProperties(holiday, model);
				if (!TeeUtility.isNullorEmpty(holiday.getStartTime())) {

					model.setStartTimeDesc(sf.format(holiday.getStartTime().getTime()));
				}
				if (!TeeUtility.isNullorEmpty(holiday.getEndTime())) {

					model.setEndTimeDesc(sf.format(holiday.getEndTime().getTime()));
				}
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关外出记录！");
		return json;
	}

	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Map requestDatas) {
		return attendConfigDao.datagrid(dm, requestDatas);
	}

	public TeeEasyuiDataGridJson datagridHoliday(TeeDataGridModel dm, Map requestDatas) {
		return holidayDao.datagrid(dm, requestDatas);
	}

	public TeeJson updateConfig(HttpServletRequest request, TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeeAttendConfig config = new TeeAttendConfig();
		BeanUtils.copyProperties(model, config);
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc1())) {
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(model.getDutyTimeDesc1()));
			config.setDutyTime1(cl1);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc2())) {
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(model.getDutyTimeDesc2()));
			config.setDutyTime2(cl2);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc3())) {
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(sf.parse(model.getDutyTimeDesc3()));
			config.setDutyTime3(cl3);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc4())) {
			Calendar cl4 = Calendar.getInstance();
			cl4.setTime(sf.parse(model.getDutyTimeDesc4()));
			config.setDutyTime4(cl4);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc5())) {
			Calendar cl5 = Calendar.getInstance();
			cl5.setTime(sf.parse(model.getDutyTimeDesc5()));
			config.setDutyTime5(cl5);
		}
		if (!TeeUtility.isNullorEmpty(model.getDutyTimeDesc6())) {
			Calendar cl6 = Calendar.getInstance();
			cl6.setTime(sf.parse(model.getDutyTimeDesc6()));
			config.setDutyTime6(cl6);
		}
		attendConfigDao.updateConfig(config);
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("修改成功！");
		return json;
	}

	public TeeJson updateHoliday(HttpServletRequest request, TeeAttendHolidayModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeeAttendHoliday holiday = new TeeAttendHoliday();
		BeanUtils.copyProperties(model, holiday);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (!TeeUtility.isNullorEmpty(model.getStartTimeDesc())) {
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(model.getStartTimeDesc()));
			holiday.setStartTime(cl1);
		}
		if (!TeeUtility.isNullorEmpty(model.getEndTimeDesc())) {
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(model.getEndTimeDesc()));
			holiday.setEndTime(cl2);
		}
		holidayDao.updateHoliday(holiday);
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("修改成功！");
		return json;
	}

	public TeeJson setGeneral(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String general = "";
		for (int i = 1; i < 8; i++) {
			int check = TeeStringUtil.getInteger(request.getParameter("general" + i), 0);
			if (check == 1) {
				general += i + ",";
			}
		}
		if (sid > 0) {
			TeeAttendConfig config = attendConfigDao.getById(sid);
			config.setGeneral(general);
			attendConfigDao.updateConfig(config);
			json.setRtState(true);
			json.setRtMsg("设置成功！");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("设置失败！");
		return json;
	}

	/**
	 * 保存考勤登记时间段设置
	 * 
	 * @param requestDatas
	 */
	public void saveAttendTimes(Map requestDatas) {
		String workonBeforeMin = TeeStringUtil.getString(requestDatas.get("workonBeforeMin"), "0");
		String workonAfterMin = TeeStringUtil.getString(requestDatas.get("workonAfterMin"), "0");
		String workoutBeforeMin = TeeStringUtil.getString(requestDatas.get("workoutBeforeMin"), "0");
		String workoutAfterMin = TeeStringUtil.getString(requestDatas.get("workoutAfterMin"), "0");
		TeeSysPara sysPara1 = new TeeSysPara();
		if (sysParaDao.getSysPara("WORKON_BEFORE_MIN") == null) {// 上班登记时间提前分钟数设置
			sysPara1.setParaName("WORKON_BEFORE_MIN");
			sysPara1.setParaValue(workonBeforeMin);
			sysParaDao.save(sysPara1);
		} else {
			sysPara1 = sysParaDao.getSysPara("WORKON_BEFORE_MIN");
			sysPara1.setParaValue(workonBeforeMin);
			sysParaDao.update(sysPara1);
		}
		TeeSysPara sysPara2 = new TeeSysPara();
		if (sysParaDao.getSysPara("WORKON_AFTER_MIN") == null) {// 上班登记时间段延后分钟数
			sysPara2.setParaName("WORKON_AFTER_MIN");
			sysPara2.setParaValue(workonAfterMin);
			sysParaDao.save(sysPara2);
		} else {
			sysPara2 = sysParaDao.getSysPara("WORKON_AFTER_MIN");
			sysPara2.setParaValue(workonAfterMin);
			sysParaDao.update(sysPara2);
		}

		TeeSysPara sysPara3 = new TeeSysPara();
		if (sysParaDao.getSysPara("WORKOUT_BEFORE_MIN") == null) {// 下班登记时间提前分钟数设置
			sysPara3.setParaName("WORKOUT_BEFORE_MIN");
			sysPara3.setParaValue(workoutBeforeMin);
			sysParaDao.save(sysPara3);
		} else {
			sysPara3 = sysParaDao.getSysPara("WORKOUT_BEFORE_MIN");
			sysPara3.setParaValue(workoutBeforeMin);
			sysParaDao.update(sysPara3);
		}
		TeeSysPara sysPara4 = new TeeSysPara();
		if (sysParaDao.getSysPara("WORKOUT_AFTER_MIN") == null) {// 下班登记时间段延后分钟数
			sysPara4.setParaName("WORKOUT_AFTER_MIN");
			sysPara4.setParaValue(workoutAfterMin);
			sysParaDao.save(sysPara4);
		} else {
			sysPara4 = sysParaDao.getSysPara("WORKOUT_AFTER_MIN");
			sysPara4.setParaValue(workoutAfterMin);
			sysParaDao.update(sysPara4);
		}

	}

	/**
	 * 获取考勤时间段设置
	 * 
	 * @return
	 */
	public Map getAttendTimes() {
		Map returnedData = new HashMap();
		String workonBeforeMin = "";
		String workonAfterMin = "";
		String workoutBeforeMin = "";
		String workoutAfterMin = "";
		if (sysParaDao.getSysPara("WORKON_BEFORE_MIN") != null) {
			workonBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_BEFORE_MIN").getParaValue(), "0");
		}
		if (sysParaDao.getSysPara("WORKON_AFTER_MIN") != null) {
			workonAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_AFTER_MIN").getParaValue(), "0");
		}
		if (sysParaDao.getSysPara("WORKOUT_BEFORE_MIN") != null) {
			workoutBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN").getParaValue(), "0");
		}
		if (sysParaDao.getSysPara("WORKOUT_AFTER_MIN") != null) {
			workoutAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_AFTER_MIN").getParaValue(), "0");
		}
		returnedData.put("workonBeforeMin", workonBeforeMin);
		returnedData.put("workonAfterMin", workonAfterMin);
		returnedData.put("workoutBeforeMin", workoutBeforeMin);
		returnedData.put("workoutAfterMin", workoutAfterMin);
		return returnedData;
	}

	/**
	 * 保存考勤免签人员段设置
	 * 
	 * @param requestDatas
	 */
	public void saveNoRegisterPerson(Map requestDatas) {
		String noRegisterUserIds = TeeStringUtil.getString(requestDatas.get("noRegisterUserIds"), "");
		String noRegisterUserNames = TeeStringUtil.getString(requestDatas.get("noRegisterUserNames"), "");
		TeeSysPara sysPara1 = new TeeSysPara();
		if (sysParaDao.getSysPara("NO_REGISTER_USERIDS") == null) {// 上班登记时间提前分钟数设置
			sysPara1.setParaName("NO_REGISTER_USERIDS");
			sysPara1.setParaValue(noRegisterUserIds);
			sysParaDao.save(sysPara1);
		} else {
			sysPara1 = sysParaDao.getSysPara("NO_REGISTER_USERIDS");
			sysPara1.setParaValue(noRegisterUserIds);
			sysParaDao.update(sysPara1);
		}
		TeeSysPara sysPara2 = new TeeSysPara();
		if (sysParaDao.getSysPara("NO_REGISTER_USERNAMES") == null) {// 上班登记时间段延后分钟数
			sysPara2.setParaName("NO_REGISTER_USERNAMES");
			sysPara2.setParaValue(noRegisterUserNames);
			sysParaDao.save(sysPara2);
		} else {
			sysPara2 = sysParaDao.getSysPara("NO_REGISTER_USERNAMES");
			sysPara2.setParaValue(noRegisterUserNames);
			sysParaDao.update(sysPara2);
		}

	}

	/**
	 * 获取考勤免签人员段设置
	 * 
	 * @return
	 */
	public Map getNoRegisterPerson() {
		Map returnedData = new HashMap();
		String noRegisterUserIds = "";
		String noRegisterUserNames = "";
		if (sysParaDao.getSysPara("NO_REGISTER_USERIDS") != null) {
			noRegisterUserIds = TeeStringUtil.getString(sysParaDao.getSysPara("NO_REGISTER_USERIDS").getParaValue(), "");
		}
		if (sysParaDao.getSysPara("NO_REGISTER_USERNAMES") != null) {
			noRegisterUserNames = TeeStringUtil.getString(sysParaDao.getSysPara("NO_REGISTER_USERNAMES").getParaValue(), "");
		}
		returnedData.put("noRegisterUserIds", noRegisterUserIds);
		returnedData.put("noRegisterUserNames", noRegisterUserNames);
		return returnedData;
	}

	/**
	 * 根据当前登录人获取其考勤的排班类型
	 * 
	 * @param request
	 * @return
	 */
	public TeeAttendConfigModel getDutyConfigByUser(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取当天时间
		Calendar c=Calendar.getInstance();
		String dateStr=sdf.format(c.getTime());
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		if (userId > 0) {
			person = personDao.get(userId);
		}
		
		
		
		TeeAttendConfig config = seniorService.getAttendConfigByUserAndDate(person, dateStr);
		if (config != null) {
			
			if(config.getSid()==0){
				return null;
			}else{
				return parseModel(config);
			}
			
		}

		
		return null;
	}
	
	/**
	 * 根据当前登录人获取其考勤的排班类型(移动端使用)  当天
	 * 
	 * @param request
	 * @return
	 */
	public List<Map> getDutyConfigByUser4Mobile(Map params,TeePerson loginUser) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<Map> list = new ArrayList();
		TeePerson person = loginUser;
//		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
//		if (userId > 0) {
			person = personDao.get(person.getUuid());
			params.put(TeeConst.LOGIN_USER, person);
//		}

	    //获取当前时间
	    Calendar nowTime=Calendar.getInstance();
	    String dateStr=sdf.format(nowTime.getTime());
		TeeAttendConfig config = seniorService.getAttendConfigByUserAndDate(person, dateStr);
		if (config == null) {//无排班
			return list;
		}else{
			if(config.getSid()==0){//休息
				return list;
			}else{
				Calendar s1 = config.getDutyTime1();
				Calendar s2 = config.getDutyTime2();
				Calendar s3 = config.getDutyTime3();
				Calendar s4 = config.getDutyTime4();
				Calendar s5 = config.getDutyTime5();
				Calendar s6 = config.getDutyTime6();
				
				List<Calendar> cList = new ArrayList();
				cList.add(s1);
				cList.add(s2);
				cList.add(s3);
				cList.add(s4);
				cList.add(s5);
				cList.add(s6);
				
				//从考勤记录中获取第一次考勤记录
				Map data = null;
				
				for(int i=0;i<cList.size();i++){
					if(cList.get(i)==null){
						return list;
					}
					params.put("on", i+1);
					params.put("registerTime", TeeDateUtil.format(cList.get(i).getTime(), "HH:mm:ss"));
					
					data = new HashMap();
					data.put("on", (i+1));
					try {
						data.put("registTime", TeeStringUtil.getString(getRegisterTime4Mobile(params,loginUser)));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						data.put("sysTime", TeeDateUtil.format(cList.get(i).getTime(), "HH:mm:ss"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						data.put("status", isRegister(params));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					list.add(data);
				}
			}
		}
		
		

		return list;
	}
	
	
	/**
	 * 获取当前登陆人指定年月的签到记录，供日历查看(移动端使用)
	 * 
	 * @param request
	 * @return
	 */
	public Set<Integer> getMonthAttendInfos4Mobile(Map params,TeePerson loginUser) {
		Set<Integer> list = new HashSet();
		int year = TeeStringUtil.getInteger(params.get("year"), 0);
		int month = TeeStringUtil.getInteger(params.get("month"), 0);
		
		Calendar start = Calendar.getInstance();
		start.set(Calendar.YEAR, year);
		start.set(Calendar.MONTH, month-1);
		start.set(Calendar.DATE, 1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		
		Calendar end = Calendar.getInstance();
		end.set(Calendar.YEAR, year);
		end.set(Calendar.MONTH, month-1);
		end.set(Calendar.DATE, end.getActualMaximum(Calendar.DATE));
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		
		List<TeeAttendDuty> dutyList = simpleDaoSupport.find("from TeeAttendDuty where userId=? and ?<=registerTime and registerTime<=?", 
						new Object[]{loginUser.getUuid(),start,end});
		
		for(TeeAttendDuty duty:dutyList){
			list.add(duty.getRegisterTime().get(Calendar.DATE));
		}
		
		return list;
	}
	
	
	/**
	 * 获取指定日期下的签到记录
	 * 
	 * @param request
	 * @return
	 */
	public List<Map> getDateAttendRecords(Map params,TeePerson loginUser) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<Map> list = new ArrayList();
		Calendar date = TeeDateUtil.parseCalendar((String)params.get("date"));
		
		Calendar start = (Calendar) date.clone();
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		
		Calendar end = (Calendar) date.clone();
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		
		
		List<TeeAttendDuty> dutyList = simpleDaoSupport.find("from TeeAttendDuty where userId=? and ?<=registerTime and registerTime<=? order by dutyType asc", 
						new Object[]{loginUser.getUuid(),start,end});
		
		TeeAttendConfig config = seniorService.getAttendConfigByUserAndDate(loginUser, sdf.format(date.getTime()));
		
		if(config!=null){
			if(config.getSid()==0){
				return list;
			}else{
				Calendar s1 = config.getDutyTime1();
				Calendar s2 = config.getDutyTime2();
				Calendar s3 = config.getDutyTime3();
				Calendar s4 = config.getDutyTime4();
				Calendar s5 = config.getDutyTime5();
				Calendar s6 = config.getDutyTime6();
				
				List<Calendar> cList = new ArrayList();
				cList.add(s1);
				cList.add(s2);
				cList.add(s3);
				cList.add(s4);
				cList.add(s5);
				cList.add(s6);
				
				for(TeeAttendDuty duty:dutyList){
					Map data = new HashMap();
					data.put("on", duty.getDutyType());
					data.put("registTime", TeeDateUtil.format(duty.getRegisterTime().getTime(), "HH:mm:ss"));
					data.put("remark", duty.getRemark());
					data.put("position", duty.getPosition());
					int index = 1;
					for(Calendar cld:cList){
						if(cld==null){
							break;
						}
						cld.set(Calendar.YEAR, date.get(Calendar.YEAR));
						cld.set(Calendar.MONTH, date.get(Calendar.MONTH));
						cld.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
						System.out.println(TeeDateUtil.format(duty.getRegisterTime().getTime(), "yyyy-MM-dd HH:mm:ss"));
						System.out.println(TeeDateUtil.format(cld.getTime(), "yyyy-MM-dd HH:mm:ss"));
						if(index==duty.getDutyType()){
							if(duty.getRegisterTime().before(cld)){//如果登记时间在规定时间之前
								if(index%2==0){//下班情况
									data.put("mark", 1);//早退
								}else{
									data.put("mark", 0);//正常
								}
							}else if(duty.getRegisterTime().after(cld)){//如果登记时间在规定时间之后
								if(index%2!=0){//上班情况
									data.put("mark", 2);//迟到
								}else{
									data.put("mark", 0);//正常
								}
							}else{
								data.put("mark", 0);//正常
							}
			
						}
						index++;
					}
					
					
					list.add(data);
				}
			}
		}else{
			return list;
		}
		
		
		
		
		
		return list;
	}
	
	/**
	 * 判断当前时间是否可以登记 -1:不在登记时间内 ； 0:在登记时间内，1：当前用户属于免签人，2：当前日期为免签节日；3：当前日期为公休日
	 * ;4:已登记
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int isRegister(Map params) throws Exception {
		TeePerson person = (TeePerson) params.get(TeeConst.LOGIN_USER);// 当前登录人
		String registerTime = TeeStringUtil.getString(params.get("registerTime"), "00:00:00");
		String on = TeeStringUtil.getString(params.get("on"), "0");// 用于表示是上班还是下班
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		//String dutyType = person.getDutyType();
		String workonBeforeMin = "";
		String workonAfterMin = "";
		String workoutBeforeMin = "";
		String workoutAfterMin = "";
        //获取当前时间 年月日
		String dateStr=sf.format(Calendar.getInstance().getTime());
		TeeAttendConfig config = seniorService.getAttendConfigByUserAndDate(person,dateStr );
		if (config != null) {
			if (isNoRegisterUserIds(person.getUuid())) {// 当前登录人属于免签范围
				return 1;
			}
			if (isHoliday()) {// 当前日期为免签节日
				return 2;
			}
			Calendar curCl = Calendar.getInstance();
			if (isGeneral(config.getSid()) && !isAddWorkDay(curCl)) { // 当前日期为公休日
				return 3;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cl1 = Calendar.getInstance();
			String dutyTime = sf.format(cl1.getTime()) + " " + registerTime;// 当天的规定登记时间
			if (on.equals("1") || on.equals("3") || on.equals("5")) {// 上班登记
				if (sysParaDao.getSysPara("WORKON_BEFORE_MIN") != null) {
					workonBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_BEFORE_MIN").getParaValue(), "0");
				}
				if (sysParaDao.getSysPara("WORKON_AFTER_MIN") != null) {
					workonAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_AFTER_MIN").getParaValue(), "0");
				}
				Calendar cl2 = Calendar.getInstance();// 上班提前登记时间
				cl2.setTime(sdf.parse(dutyTime));
				cl2.add(Calendar.MINUTE, -Integer.parseInt(workonBeforeMin));

				Calendar cl3 = Calendar.getInstance();// 上班延后登记时间
				cl3.setTime(sdf.parse(dutyTime));
				cl3.add(Calendar.MINUTE, Integer.parseInt(workonAfterMin));

				if (isRegistered(person.getUuid(), config.getSid(), cl2, cl3, on)) {
					return 4;
				}

				if (cl1.after(cl2) && cl1.before(cl3)) {
					return 0;
				}

			} else {// 下班登记
				if (sysParaDao.getSysPara("WORKOUT_BEFORE_MIN") != null) {
					workoutBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN").getParaValue(), "0");
				}
				if (sysParaDao.getSysPara("WORKOUT_AFTER_MIN") != null) {
					workoutAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_AFTER_MIN").getParaValue(), "0");
				}

				Calendar cl2 = Calendar.getInstance();// 下班提前登记时间
				cl2.setTime(sdf.parse(dutyTime));
				cl2.add(Calendar.MINUTE, -Integer.parseInt(workoutBeforeMin));

				Calendar cl3 = Calendar.getInstance();// 下班延后登记时间
				cl3.setTime(sdf.parse(dutyTime));
				cl3.add(Calendar.MINUTE, Integer.parseInt(workoutAfterMin));

				if (isRegistered(person.getUuid(),config.getSid(), cl2, cl3, on)) {
					return 4;
				}

				if (cl1.after(cl2) && cl1.before(cl3)) {
					return 0;
				}

			}
		}

		return -1;
	}

	/**
	 * 当前用户是否为免签人
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isNoRegisterUserIds(int userId) {
		String noRegisterUserIds = "";// 免签用户Id
		if (sysParaDao.getSysPara("NO_REGISTER_USERIDS") != null) {
			noRegisterUserIds = TeeStringUtil.getString(sysParaDao.getSysPara("NO_REGISTER_USERIDS").getParaValue(), "");
			if (!TeeUtility.isNullorEmpty(noRegisterUserIds)) {
				String[] ids = noRegisterUserIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					if (Integer.parseInt(ids[i]) == userId) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断当前日期是不是免签节日
	 * 
	 * @return
	 */
	public boolean isHoliday() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		String curTimeDesc = sf.format(cl.getTime())+" 00:00:00";
		try {
			cl.setTime(sf.parse(curTimeDesc));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TeeAttendHoliday> holidayList = holidayDao.getHolidayList();
		for (TeeAttendHoliday holiday : holidayList) {
			if (holiday != null) {
				Calendar startDate = holiday.getStartTime();
				Calendar endDate = holiday.getEndTime();
				if ((cl.after(startDate) && cl.before(endDate) )|| (cl.equals(startDate) || cl.equals(endDate))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断当前日期是不是公休日
	 * 
	 * @return
	 */
	public boolean isGeneral(int attendConfigId) {
		if(attendConfigId==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断当前日期是不是补假日
	 * 
	 * @return
	 */
	public boolean isAddWorkDay(Calendar cl) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String curDateDesc = sf.format(cl.getTime())+" 00:00:00";
			cl.setTime(sf.parse(curDateDesc));
			List<TeeAttendHoliday> list = holidayDao.getWorkDayList();
			for(TeeAttendHoliday holiday:list){
				if(cl.equals(holiday.getStartTime())){
					return true;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断是否登记过了
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isRegistered(int userId, int dutyId, Calendar cl1, Calendar cl2, String on) throws Exception {
		List<TeeAttendDuty> list = dutyDao.getDutyList(userId, dutyId, on);
		if (list.size() > 0) {
			for (TeeAttendDuty duty : list) {
				Calendar registerTime = duty.getRegisterTime();
				if (registerTime.after(cl1) && registerTime.before(cl2)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getRegisterTime(Map request,TeePerson loginUser) throws Exception {
		loginUser = personDao.get(loginUser.getUuid());
		String registerTime = TeeStringUtil.getString(request.get("registerTime"), "00:00:00");
		String on = TeeStringUtil.getString(request.get("on"), "0");// 用于表示是上班还是下班
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		//String dutyType = loginUser.getDutyType();
		String workonBeforeMin = "";
		String workonAfterMin = "";
		String workoutBeforeMin = "";
		String workoutAfterMin = "";
		String registerTimes = "";
		
		String dateStr=sf.format(Calendar.getInstance().getTime());
		
		TeeAttendConfig config = seniorService.getAttendConfigByUserAndDate(loginUser, dateStr);// 当前用户的排班类型
		if (config != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar cl1 = Calendar.getInstance();
			String dutyTime = sf.format(cl1.getTime()) + " " + registerTime;// 当天的规定登记时间
			Calendar ruleTime = Calendar.getInstance();
			ruleTime.setTime(sdf.parse(dutyTime));
			String flag = "";
			if (on.equals("1") || on.equals("3") || on.equals("5")) {// 上班登记
				if (sysParaDao.getSysPara("WORKON_BEFORE_MIN") != null) {
					workonBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_BEFORE_MIN").getParaValue(), "0");
				}
				if (sysParaDao.getSysPara("WORKON_AFTER_MIN") != null) {
					workonAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_AFTER_MIN").getParaValue(), "0");
				}
				Calendar cl2 = Calendar.getInstance();// 上班提前登记时间
				cl2.setTime(sdf.parse(dutyTime));
				cl2.add(Calendar.MINUTE, -Integer.parseInt(workonBeforeMin));

				Calendar cl3 = Calendar.getInstance();// 上班延后登记时间
				cl3.setTime(sdf.parse(dutyTime));
				cl3.add(Calendar.MINUTE, Integer.parseInt(workonAfterMin));
				Calendar time = getTimes(loginUser.getUuid(), config.getSid(), cl2, cl3, on);
				if (time != null) {
					if (time.after(ruleTime)) {
						flag = "<span style='color:red'>&nbsp;&nbsp;&nbsp;迟到</span>";
					}
					registerTimes = sdf.format(time.getTime()) + flag;
				}

			} else {// 下班登记
				if (sysParaDao.getSysPara("WORKOUT_BEFORE_MIN") != null) {
					workoutBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN").getParaValue(), "0");
				}
				if (sysParaDao.getSysPara("WORKOUT_AFTER_MIN") != null) {
					workoutAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_AFTER_MIN").getParaValue(), "0");
				}

				Calendar cl2 = Calendar.getInstance();// 下班提前登记时间
				cl2.setTime(sdf.parse(dutyTime));
				cl2.add(Calendar.MINUTE, -Integer.parseInt(workoutBeforeMin));

				Calendar cl3 = Calendar.getInstance();// 下班延后登记时间
				cl3.setTime(sdf.parse(dutyTime));
				cl3.add(Calendar.MINUTE, Integer.parseInt(workoutAfterMin));
				Calendar time = getTimes(loginUser.getUuid(), config.getSid(), cl2, cl3, on);
				if (time != null) {
					if (time.before(ruleTime)) {
						flag = "<span style='color:red'>&nbsp;&nbsp;&nbsp;早退</span>";
					}
					registerTimes = sdf.format(time.getTime()) + flag;
				}
			}
		}

		return registerTimes;
	}
	
	
	public String getRegisterTime4Mobile(Map request,TeePerson loginUser) throws Exception {
		loginUser = personDao.get(loginUser.getUuid());
		String registerTime = TeeStringUtil.getString(request.get("registerTime"), "00:00:00");
		String on = TeeStringUtil.getString(request.get("on"), "0");// 用于表示是上班还是下班
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String dutyType = loginUser.getDutyType();
		String workonBeforeMin = "";
		String workonAfterMin = "";
		String workoutBeforeMin = "";
		String workoutAfterMin = "";
		String registerTimes = "";
		if (!TeeUtility.isNullorEmpty(dutyType)) {
			TeeAttendConfig config = attendConfigDao.getById(Integer.parseInt(dutyType));// 当前用户的排班类型
			if (config != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Calendar cl1 = Calendar.getInstance();
				String dutyTime = sf.format(cl1.getTime()) + " " + registerTime;// 当天的规定登记时间
				Calendar ruleTime = Calendar.getInstance();
				ruleTime.setTime(sdf.parse(dutyTime));
				String flag = "";
				if (on.equals("1") || on.equals("3") || on.equals("5")) {// 上班登记
					if (sysParaDao.getSysPara("WORKON_BEFORE_MIN") != null) {
						workonBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_BEFORE_MIN").getParaValue(), "0");
					}
					if (sysParaDao.getSysPara("WORKON_AFTER_MIN") != null) {
						workonAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_AFTER_MIN").getParaValue(), "0");
					}
					Calendar cl2 = Calendar.getInstance();// 上班提前登记时间
					cl2.setTime(sdf.parse(dutyTime));
					cl2.add(Calendar.MINUTE, -Integer.parseInt(workonBeforeMin));

					Calendar cl3 = Calendar.getInstance();// 上班延后登记时间
					cl3.setTime(sdf.parse(dutyTime));
					cl3.add(Calendar.MINUTE, Integer.parseInt(workonAfterMin));
					Calendar time = getTimes(loginUser.getUuid(), Integer.parseInt(dutyType), cl2, cl3, on);
					if (time != null) {
						if (time.after(ruleTime)) {
							flag = "";
						}
						registerTimes = sdf.format(time.getTime()) + flag;
					}

				} else {// 下班登记
					if (sysParaDao.getSysPara("WORKOUT_BEFORE_MIN") != null) {
						workoutBeforeMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN").getParaValue(), "0");
					}
					if (sysParaDao.getSysPara("WORKOUT_AFTER_MIN") != null) {
						workoutAfterMin = TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_AFTER_MIN").getParaValue(), "0");
					}

					Calendar cl2 = Calendar.getInstance();// 下班提前登记时间
					cl2.setTime(sdf.parse(dutyTime));
					cl2.add(Calendar.MINUTE, -Integer.parseInt(workoutBeforeMin));

					Calendar cl3 = Calendar.getInstance();// 下班延后登记时间
					cl3.setTime(sdf.parse(dutyTime));
					cl3.add(Calendar.MINUTE, Integer.parseInt(workoutAfterMin));
					Calendar time = getTimes(loginUser.getUuid(), Integer.parseInt(dutyType), cl2, cl3, on);
					if (time != null) {
						if (time.before(ruleTime)) {
							flag = "";
						}
						registerTimes = sdf.format(time.getTime()) + flag;
					}
				}
			}
		}
		return registerTimes;
	}

	/**
	 * 获得登记时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public Calendar getTimes(int userId, int dutyId, Calendar cl1, Calendar cl2, String on) throws Exception {
		List<TeeAttendDuty> list = dutyDao.getDutyList(userId, dutyId, on);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (list.size() > 0) {
			for (TeeAttendDuty duty : list) {
				Calendar registerTime = duty.getRegisterTime();
				if (registerTime.after(cl1) && registerTime.before(cl2)) {
					return registerTime;
				}
			}
		}
		return null;
	}

	
	
	/**
	 * 添加工作日，如补假
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public TeeJson addWorkDay(HttpServletRequest request, TeeAttendHolidayModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeeAttendHoliday holiday = new TeeAttendHoliday();
		BeanUtils.copyProperties(model, holiday);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if(!TeeUtility.isNullorEmpty(model.getStartTimeDesc())){
			Calendar cl1=Calendar.getInstance();
			cl1.setTime(sf.parse(model.getStartTimeDesc()));
			holiday.setStartTime(cl1);
		}
		holiday.setParentId(model.getParentId());
		holidayDao.addHoliday(holiday);
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}
	
	public TeeJson deleteWorkDayByParentId(int sid) {
		TeeJson json = new TeeJson();
		if(sid > 0){
			holidayDao.deleteWorkDayByParentId(sid);
			json.setRtState(true);
			json.setRtMsg("删除成功!");
			return json;
			
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关记录！");
		return json;
	}
	
	
	public TeeEasyuiDataGridJson getWorkDayList(TeeDataGridModel dm,Map requestDatas) {
		return holidayDao.getWorkDayList(dm, requestDatas);
	}
	//获取节假日
	public List<TeeAttendHoliday> findDayList(String year,String month){
		String beginDay=year+"-"+month+"-01 00:00:00";
		Calendar begin = getCalendar(beginDay);
		Calendar end = getCalendar(beginDay);
		end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        List<TeeAttendHoliday> find = holidayDao.find("from TeeAttendHoliday where (endTime>=? and startTime<=?) or (startTime<=? and endTime>=?) or (startTime>=? and endTime<=?)",new Object[]{begin,begin,end,end,begin,end});
		return find;
	}
	//字符串变
	public Calendar getCalendar(String time){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		Calendar calendar=null;
		try {
			date = sdf.parse(time);
			calendar= Calendar.getInstance();
			calendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}
	//获取某年某月的最后一天
	public String getLastDayOfMonth(int year,int month){
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}
	
}

