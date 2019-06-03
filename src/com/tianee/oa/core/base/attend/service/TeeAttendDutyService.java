package com.tianee.oa.core.base.attend.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.bean.TeeAttendDuty;
import com.tianee.oa.core.base.attend.bean.TeeAttendDutyRecord;
import com.tianee.oa.core.base.attend.bean.TeeAttendEvection;
import com.tianee.oa.core.base.attend.bean.TeeAttendHoliday;
import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.bean.TeeAttendOvertime;
import com.tianee.oa.core.base.attend.bean.TeeDutyComplaint;
import com.tianee.oa.core.base.attend.dao.TeeAttendConfigDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendDutyDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendEvectionDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendHolidayDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendLeaveDao;
import com.tianee.oa.core.base.attend.tcp.AttendsSync;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeAttendDutyService extends TeeBaseService{
	private static double EARTH_RADIUS = 6378.137;    
	@Autowired
	private TeeAttendDutyDao dutyDao;
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttendConfigDao attendConfigDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeAttendHolidayDao holidayDao;
	
	@Autowired
	private TeeAttendEvectionDao attendEvectionDao;
	
	@Autowired
	private TeeAttendLeaveDao attendLeaveDao;
	
	@Autowired
	private TeeSysParaDao sysParaDao;
	
	@Autowired
	private TeeAttendConfigService attendConfigService;
	
	@Autowired
	private TeeDeptService  deptService;
	
	@Autowired
	private TeeAttendSeniorConfigService  seniorConfigService;
	
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson addDuty(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int dutyType = TeeStringUtil.getInteger(request.getParameter("on"), 0);
		String remark = TeeStringUtil.getString(request.getParameter("remark"));
		String lng = TeeStringUtil.getString(request.getParameter("lng"));
		String lat = TeeStringUtil.getString(request.getParameter("lat"));
		
		//获取签到的手机型号
		String phoneModel=TeeStringUtil.getString(request.getParameter("phoneModel"));
		//获取当前时间   年月日
		String dateStr=sdf.format(Calendar.getInstance().getTime());
		
		TeeAttendConfig config =seniorConfigService.getAttendConfigByUserAndDate(person, dateStr);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String registerIp=request.getRemoteAddr();
		long hours = 0;
		TeeJson json = new TeeJson();
		TeeAttendDuty duty = new TeeAttendDuty();
		Calendar cl1=Calendar.getInstance();
		Calendar cl2=Calendar.getInstance();
		duty.setRegisterTime(cl1);//登记时，设置当前时间登记时间
		duty.setUserId(person.getUuid());
		duty.setRegisterIp(registerIp);
		if(!"".equals(lng) && !"".equals(lat)){//手机签到
			duty.setPosition(lng+","+lat);
			duty.setPhoneModel(phoneModel);//设置签到的手机型号
			//区域范围判断
			if(config!=null){
				/*if(!TeeUtility.isNullorEmpty(config.getCenterPosition())){//中心点不为空
					String centerPosition=config.getCenterPosition();
				    String[]arr=centerPosition.split(",");
				    double distance=getDistance(TeeStringUtil.getDouble(lat, 0),TeeStringUtil.getDouble(lng, 0),TeeStringUtil.getDouble(arr[1], 0),TeeStringUtil.getDouble(arr[0], 0));
					System.out.println(distance);
				    if(distance>config.getRadius()){//不在签到区域范围内
						json.setRtState(false);
						json.setRtMsg("您不在签到区域范围内！");
						return json;
					}	
				}*/
				//定义一个变量   
				boolean isRigth=false;
				//获取签到区域model
				String jsonAddress=config.getAddrJson();
				List<Map<String, String>> jsonMap=TeeJsonUtil.JsonStr2MapList(jsonAddress);
				if(jsonMap!=null&&jsonMap.size()>0){
					for (Map<String, String> map : jsonMap) {
						if(!TeeUtility.isNullorEmpty(map.get("centerPosition"))){//中心点不为空
							String centerPosition=map.get("centerPosition");
						    String[]arr=centerPosition.split(",");
						    double distance=getDistance(TeeStringUtil.getDouble(lat, 0),TeeStringUtil.getDouble(lng, 0),TeeStringUtil.getDouble(arr[1], 0),TeeStringUtil.getDouble(arr[0], 0));
							System.out.println(distance);
						    if(distance<=TeeStringUtil.getDouble(map.get("radius"), 0)){//在签到范围内
						    	isRigth=true;
						    	break;
							}
						}
					}
					
					if(!isRigth){//没有在签到范围内
						json.setRtState(false);
						json.setRtMsg("您不在签到区域范围内！");
						return json;
					}
				}
				
				
			}
		}
		
		duty.setRegisterType(config.getSid()+"");
		duty.setDutyType(dutyType);//第几次登记
		duty.setRemark(remark);
		String getRegisterTime="";
		
		
		Map params = TeeServletUtility.getParamMap(request);
		params.put(TeeConst.LOGIN_USER, person);
		
		//判断当前签到状态
		int flag = attendConfigService.isRegister(params);
		if(flag==1){
			json.setRtState(false);
			json.setRtMsg("您为考勤免签人，无需进行考勤登记！");
			return json;
		}else if(flag==2){
			json.setRtState(false);
			json.setRtMsg("当天为免签日，无需进行考勤登记！");
			return json;
		}else if(flag==3){
			json.setRtState(false);
			json.setRtMsg("当天为公休日，无需进行考勤登记！");
			return json;
		}else if(flag==4){
			json.setRtState(false);
			json.setRtMsg("您已在此时间段登记过，请勿重复登记！");
			return json;
		}else if(flag==-1){
			json.setRtState(false);
			json.setRtMsg("您不在该登记时间内！");
			return json;
		}
		//获取当前用户设置的签到方式
		String signWay = person.getSignWay();//获取当前登录者设置的签到方式   1--组合签到     2--移动端签到    3--网页签到
	    if(!TeeUtility.isNullorEmpty(signWay)){
	    	//经纬度不为空  说明是网页端签到
	    	if(("3").equals(signWay)){//只允许网页签到
	    		if((lat!=null&&lng!=null)||(lat!=""&&lng!="")){
	    			json.setRtState(false);
		    		json.setRtMsg("您选择的签到方式是网页端签到，当前是在移动端！");
		    		return json;
	    		}else{
	    			
	    		}
	    	}
	    	if(("2").equals(signWay)){//只允许移动端签到
	    		if((lat==null&&lng==null)||(lat==""&&lng=="")){
	    			json.setRtState(false);
		    		json.setRtMsg("您选择的签到方式是移动端签到，当前是在网页端！");
		    		return json;
	    		}else{
	    			
	    		}
	    	}
	    }
		
		
		if(dutyType==2){
			getRegisterTime= dutyDao.getRegisterTime(person.getUuid(), config.getSid(), cl2, config.getDutyTime2(), '0',2);
			if(!TeeUtility.isNullorEmpty(getRegisterTime) && !getRegisterTime.equals("未登记")){
				getRegisterTime=getRegisterTime.substring(0,5);
			}
		}
		if(dutyType==4){
			getRegisterTime= dutyDao.getRegisterTime(person.getUuid(), config.getSid(), cl2, config.getDutyTime4(), '0',4);
			if(!TeeUtility.isNullorEmpty(getRegisterTime) && !getRegisterTime.equals("未登记")){
				getRegisterTime=getRegisterTime.substring(0,8);
			}
		}
		if(dutyType ==6){
			getRegisterTime= dutyDao.getRegisterTime(person.getUuid(), config.getSid(), cl2, config.getDutyTime6(), '0',6);
			if(!TeeUtility.isNullorEmpty(getRegisterTime) && !getRegisterTime.equals("未登记")){
				getRegisterTime=getRegisterTime.substring(0,8);
			}
		}
		if(!TeeUtility.isNullorEmpty(getRegisterTime) && !getRegisterTime.equals("未登记")){
			Calendar regTime = Calendar.getInstance();
			String ymd = sf.format(cl1.getTime()).substring(0, 11);
			
			regTime.setTime(sf.parse(ymd+getRegisterTime));
		    hours = (cl1.getTimeInMillis()-regTime.getTimeInMillis())/(1000*3600);
		}
		duty.setHours(hours);
		dutyDao.addDuty(duty);
		json.setRtState(true);
		json.setRtMsg("登记成功！");
		return json;
	}

	/**
	 * 查询上下班记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getDutyByCondition(HttpServletRequest request) throws Exception {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int year = TeeStringUtil.getInteger(request.getParameter("year"), 0);
		int month = TeeStringUtil.getInteger(request.getParameter("month"), 0);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		
		if(userId>0){
			person = personDao.get(userId);
		}
		TeeJson json = new TeeJson();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month-1);
		int days = cl.getActualMaximum(Calendar.DATE);
        
		Calendar cl1 = Calendar.getInstance();
		cl1.set(Calendar.YEAR, year);
		cl1.set(Calendar.MONTH, month-1);
		cl1.set(Calendar.DAY_OF_MONTH,1);
		if(!TeeUtility.isNullorEmpty(startDateDesc) && !"null".equals(startDateDesc)){
			cl1.setTime(sf.parse(startDateDesc));
		}
		
		Calendar cl2 = Calendar.getInstance();
		cl2.set(Calendar.YEAR, year);
		cl2.set(Calendar.MONTH, month-1);
		cl2.set(Calendar.DAY_OF_MONTH,days);
		if(!TeeUtility.isNullorEmpty(endDateDesc) && !"null".equals(endDateDesc)){
			cl2.setTime(sf.parse(endDateDesc));
		}
		
		StringBuffer sb = new StringBuffer("[");
		if(cl1.get(Calendar.YEAR)<cl2.get(Calendar.YEAR)){
			String duty = null;
			int cha=cl2.get(Calendar.YEAR)-cl1.get(Calendar.YEAR);
			for(int p =1;p<=cha;p++){
				if(cl1.getActualMaximum(Calendar.DAY_OF_YEAR)-cl1.get(Calendar.DAY_OF_YEAR)>=0){
					int k =0;
					for(k=cl1.get(Calendar.DAY_OF_YEAR);k<=cl1.getActualMaximum(Calendar.DAY_OF_YEAR);k++){
					    duty = dutyDao.getDutyByCondition(k,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR));
						sb.append(duty+",");
					}	
					if(k>cl1.getActualMaximum(Calendar.DAY_OF_YEAR)){
						cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+1);
						cl1.set(Calendar.MONTH,0);
						cl1.set(Calendar.DAY_OF_YEAR,1);
						for(int h=cl1.get(Calendar.DAY_OF_YEAR);h<=cl2.get(Calendar.DAY_OF_YEAR);h++){
							duty = dutyDao.getDutyByCondition(h,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR));
							sb.append(duty+",");
						}	
					}
				}
				
				cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+p-1);
				cl1.set(Calendar.MONTH,0);
				cl1.set(Calendar.DAY_OF_YEAR,2);
			}
		}else if(cl1.get(Calendar.YEAR)==cl2.get(Calendar.YEAR)){
			for(int i=cl1.get(Calendar.DAY_OF_YEAR);i<=cl2.get(Calendar.DAY_OF_YEAR);i++){
				String duty = dutyDao.getDutyByCondition(i,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR));
				sb.append(duty+",");
			}
		
		}
		if(sb.length()>3){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		json.setRtData(TeeJsonUtil.JsonStr2MapList(sb.toString()));
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	/**
	 * 查询导出时间范围内的上下班记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getExportDutyByCondition(HttpServletRequest request) throws Exception {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userId = TeeStringUtil.getInteger(request.getAttribute("userId"), 0);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		int year = TeeStringUtil.getInteger(startDateDesc.substring(0,4),0);
		int month =TeeStringUtil.getInteger(startDateDesc.substring(5,7),0);
		
		if(userId>0){
			person = personDao.get(userId);
		}
		TeeJson json = new TeeJson();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month-1);
		int days = cl.getActualMaximum(Calendar.DATE);
		
		Calendar cl1 = Calendar.getInstance();
		cl1.set(Calendar.YEAR, year);
		cl1.set(Calendar.MONTH, month-1);
		cl1.set(Calendar.DAY_OF_MONTH,1);
		if(!TeeUtility.isNullorEmpty(startDateDesc) && !"null".equals(startDateDesc)){
			cl1.setTime(sf.parse(startDateDesc));
		}
		
		Calendar cl2 = Calendar.getInstance();
		cl2.set(Calendar.YEAR, year);
		cl2.set(Calendar.MONTH, month-1);
		cl2.set(Calendar.DAY_OF_MONTH,days);
		if(!TeeUtility.isNullorEmpty(endDateDesc) && !"null".equals(endDateDesc)){
			cl2.setTime(sf.parse(endDateDesc));
		}
		
		
		StringBuffer sb = new StringBuffer("[");
		if(cl1.get(Calendar.YEAR)<cl2.get(Calendar.YEAR)){
			String duty = null;
			int cha=cl2.get(Calendar.YEAR)-cl1.get(Calendar.YEAR);
			for(int p =1;p<=cha;p++){
				if(cl1.getActualMaximum(Calendar.DAY_OF_YEAR)-cl1.get(Calendar.DAY_OF_YEAR)>=0){
					int k =0;
					for(k=cl1.get(Calendar.DAY_OF_YEAR);k<=cl1.getActualMaximum(Calendar.DAY_OF_YEAR);k++){
					    duty = dutyDao.getDutyByCondition(k,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR));
					    sb.append(duty+",");
					}	
					if(k>cl1.getActualMaximum(Calendar.DAY_OF_YEAR)){
						cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+1);
						cl1.set(Calendar.MONTH,0);
						cl1.set(Calendar.DAY_OF_YEAR,1);
						for(int h=cl1.get(Calendar.DAY_OF_YEAR);h<=cl2.get(Calendar.DAY_OF_YEAR);h++){
							duty = dutyDao.getDutyByCondition(h,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR));
							sb.append(duty+",");
						}	
					}
				}
				
				cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+p-1);
				cl1.set(Calendar.MONTH,0);
				cl1.set(Calendar.DAY_OF_YEAR,2);
			}
		}else if(cl1.get(Calendar.YEAR)==cl2.get(Calendar.YEAR)){
			for(int i=cl1.get(Calendar.DAY_OF_YEAR);i<=cl2.get(Calendar.DAY_OF_YEAR);i++){
				String duty = dutyDao.getDutyByCondition(i,person.getUuid(),month-1,year);
				sb.append(duty+",");
			}
		
		}
		if(sb.length()>3){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		json.setRtData(TeeJsonUtil.JsonStr2MapList(sb.toString()));
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	

	public TeeJson delAttendData(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String userIds = TeeStringUtil.getString(request.getParameter("userIds"), "");
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"),"");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"),"");
		int registerType1 = TeeStringUtil.getInteger(request.getParameter("registerType1"),0);
		int registerType2 = TeeStringUtil.getInteger(request.getParameter("registerType2"),0);
		int registerType3 = TeeStringUtil.getInteger(request.getParameter("registerType3"),0);
		int registerType4 = TeeStringUtil.getInteger(request.getParameter("registerType4"),0);
		int registerType5 = TeeStringUtil.getInteger(request.getParameter("registerType5"),0);
		
		if(registerType1==1){
			delAttendDutyData(userIds,startDateDesc,endDateDesc);
		}
		if(registerType2==1){
			delAttendOutData(userIds,startDateDesc,endDateDesc);
		}
		if(registerType3==1){
			delAttendLeveData(userIds,startDateDesc,endDateDesc);
		}
		if(registerType4==1){
			delAttendOverTimeData(userIds,startDateDesc,endDateDesc);
		}
		if(registerType5==1){
			delAttendEvectionData(userIds,startDateDesc,endDateDesc);
		}
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return json;
	}
	
	/**
	 * 删除上下班记录
	 * @throws Exception 
	 */
	public void delAttendDutyData(String userIds,String startDateDesc,String endDateDesc) throws Exception{
		dutyDao.delAttendDutyData(userIds,startDateDesc,endDateDesc);
	}
	/**
	 * 删除请假记录
	 * @param userIds
	 * @param startDateDesc
	 * @param endDateDesc
	 * @throws Exception 
	 */
	public void delAttendLeveData(String userIds,String startDateDesc,String endDateDesc) throws Exception{
		dutyDao.delAttendLeveData(userIds,startDateDesc,endDateDesc);
	}
	
	/**
	 * 删除外出记录
	 * @param userIds
	 * @param startDateDesc
	 * @param endDateDesc
	 * @throws Exception 
	 */
	public void delAttendOutData(String userIds,String startDateDesc,String endDateDesc) throws Exception{
		dutyDao.delAttendOutData(userIds,startDateDesc,endDateDesc);
	}
	
	/*
	 * 删除加班记录
	 */
	public void delAttendOverTimeData(String userIds,String startDateDesc,String endDateDesc) throws Exception{
		dutyDao.delAttendOverTimeData(userIds,startDateDesc,endDateDesc);
	}
	
	/**
	 * 删除出差记录
	 * @param userIds
	 * @param startDateDesc
	 * @param endDateDesc
	 * @throws Exception 
	 */
	public void delAttendEvectionData(String userIds,String startDateDesc,String endDateDesc) throws Exception{
		dutyDao.delAttendEvectionData(userIds,startDateDesc,endDateDesc);
	}

	
	/**
	 * 获取考勤记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getRegisterRecord(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		String data = dutyDao.getRegisterRecord(deptId,startDateDesc,endDateDesc);
		json.setRtData(TeeJsonUtil.JsonStr2MapList(data));
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	
	/**
	 * 考勤统计
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=true)
	public TeeJson getRegisterRecordInfo(HttpServletRequest request) throws Exception {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		//TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<Map> list  = new ArrayList<Map>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		int year = Integer.parseInt(startDateDesc.substring(0,4)) ;//年
		int month =Integer.parseInt(startDateDesc.substring(5,7)) ;//月
		String paraValue = sysParaDao.getSysParaValue("NO_DUTY_USER"); // 获取免签人员
		Date start = null;
		Date end = null;
		if (startDateDesc != null) {
			start = TeeUtility.parseDate("yyyy-MM-dd", startDateDesc);
		}
		if (endDateDesc != null) {
			end = TeeUtility.parseDate("yyyy-MM-dd", endDateDesc);
		}
		// ---- 取规定上下班时间 -----
		Map attendConfig = new HashMap();
		Map regCount = new HashMap();// 存放所有排版类型 map key---》sid value
										// ->map(排版详情)
		
		List<TeeAttendConfig> configList = attendConfigDao.getConfigList();
		for (int i = 0; i < configList.size(); i++) {
			int num = 0;
			TeeAttendConfig config = configList.get(i);
			int seqId = config.getSid();
			String dutyName = config.getDutyName();
			String general = config.getGeneral();
			String getDutyTime1 = "";
			String getDutyTime2 = "";
			String getDutyTime3 = "";
			String getDutyTime4 = "";
			String getDutyTime5 = "";
			String getDutyTime6 = "";
			if(config.getDutyTime1() != null){
				getDutyTime1 = format2.format(config.getDutyTime1().getTime());
				num++;
			}
			if(config.getDutyTime2() != null){
				getDutyTime2 = format2.format(config.getDutyTime2().getTime());
				num++;
			}
			if(config.getDutyTime3() != null){
				getDutyTime3 = format2.format(config.getDutyTime3().getTime());
				num++;
			}
			if(config.getDutyTime4() != null){
				getDutyTime4 = format2.format(config.getDutyTime4().getTime());
				num++;
			}	if(config.getDutyTime5() != null){
				getDutyTime5 = format2.format(config.getDutyTime5().getTime());
				num++;
			}
			if(config.getDutyTime6() != null){
				getDutyTime6 = format2.format(config.getDutyTime6().getTime());
				num++;
			}
			Map map = new HashMap();
			map.put("SEQ_ID", seqId);
			map.put("DUTY_NAME", dutyName);
			map.put("GENERAL", general);
			map.put("DUTY_TIME1", getDutyTime1);
			map.put("DUTY_TIME2", getDutyTime2);
			map.put("DUTY_TIME3", getDutyTime3);
			map.put("DUTY_TIME4",getDutyTime4);
			map.put("DUTY_TIME5", getDutyTime5);
			map.put("DUTY_TIME6", getDutyTime6);

			map.put("DUTY_TYPE1", String.valueOf(config.getDutyType1()));
			map.put("DUTY_TYPE2",  String.valueOf(config.getDutyType2()));
			map.put("DUTY_TYPE3",  String.valueOf(config.getDutyType3()));
			map.put("DUTY_TYPE4", String.valueOf( config.getDutyType4()));
			map.put("DUTY_TYPE5",  String.valueOf(config.getDutyType5()));
			map.put("DUTY_TYPE6",  String.valueOf(config.getDutyType6()));
            map.put("num",num);
			int c = 0;// 上下班总次数
			int dutyOnTimes = 0;// 上班次数
			int dutyOffTimes = 0;// 下班次数
			for (int j = 1; j <= 6; j++) {
				String dutyTimeI = (String) map.get("DUTY_TIME" + j);
				String dutyTypeI = (String) map.get("DUTY_TYPE" + j);
				if (TeeUtility.isNullorEmpty(dutyTimeI)) {
					continue;
				}
				c++;
				if ("0".equals(dutyTypeI)) {
					dutyOnTimes++;
				} else {
					dutyOffTimes++;
				}
			}
			regCount.put(seqId + "", c);
			map.put("DUTY_ON_TIMES", dutyOnTimes);
			map.put("DUTY_OFF_TIMES", dutyOffTimes);
			attendConfig.put(seqId + "", map);
		}

		/*// 获取所有人员
		List<TeePerson> userList = personDao.getAllUserNoDelete();*/
		List<TeePerson> userList=null;
		// 获取所有节假日
		List<TeeAttendHoliday> holidayList = holidayDao.getHolidayList();
		if (deptId > 0) {//指定部门
			TeeDepartment dept = (TeeDepartment)deptDao.get(deptId);
			String  deptLevel = dept.getDeptParentLevel();
			if(TeeUtility.isNullorEmpty(deptLevel)){//如果是第一级部门
				deptLevel = dept.getGuid();
			}
			userList = personDao.selectDeptAndChildDeptPerson(deptId,
					deptLevel);
		}else{//有可见权限的所有部门人员	
			List <TeeDepartment>listDept = deptService.getDeptsByLoginUser(loginUser);
			userList=personDao.getViewPrivUserListByLoginUser(listDept,loginUser);
		}

		int lineCount = 0;

		int count = 0;
		for (int i = 0; i < userList.size(); i++) {
			TeePerson pTemp = userList.get(i);
			int seqId = pTemp.getUuid();
			int allHours = 0;
			int allMinites = 0;
			String userName = pTemp.getUserName();// 姓名
			String dutyTypeTmp = pTemp.getDutyType();// 排版Id
			
			String userDeptName="";
			if(!TeeUtility.isNullorEmpty(pTemp.getDept())){
				userDeptName= pTemp.getDept().getDeptName();// 部门
			}else{
				userDeptName="";
			}
            
			Map duty = (Map) attendConfig.get(dutyTypeTmp);// 获取排版详情
			if (duty == null) {
				continue;
			}
			String general = (String) duty.get("GENERAL");// 公休日
			int dutyOnTimes = (Integer) duty.get("DUTY_ON_TIMES");// 上班次数
			int dutyOffTimes = (Integer) duty.get("DUTY_OFF_TIMES");// 下班次数
			int num = (Integer) duty.get("num");// 需要签到次数
			lineCount++;
			int prefectCount = 0;// 全勤(天
			int earlyCount = 0;// 迟到
			int lateCount = 0;// 早退
			int dutyOnCount = 0;// 上班登记次数
			int dutyOffCount = 0;// 下班登记次数
			int dutyOnTotal = 0;// 上班总计数
			int dutyOffTotal = 0;// 下班总计数
			int overOnCount = 0;
			int overOffCount = 0;
			String allHoursMinites = "";// 时长

			for (Date j = start; end.compareTo(j) >= 0; j = new Date(
					j.getTime() + 24 * 3600 * 1000)) {
				String jj = TeeUtility.getDateTimeStr(j, format);
				Calendar dateTemp = Calendar.getInstance();
				dateTemp.setTime(j);
				
				int week = j.getDay();
				if(week == 0){//星期天
					week = 1;
				}else{
					week = week + 1;
				}
				int holiday = 0; // 0-正常上班 1- 公休日、节假日、出差、请假等
				int holiday1 = 0;

				/*
				 * if (T9WorkFlowUtility.findId(general, week + "")) { holiday =
				 * 1; }
				 */
				String generalTemp = "," + general + ",";
				String weekStr = ","+ week + ",";
				if (generalTemp.indexOf(weekStr) >= 0 && !isAddWorkDay(dateTemp)) {// 判断当天是否是设置的公休日
					holiday = 1;
				}
				if (holiday == 0) {
					if (checkIsHoliday(holidayList, dateTemp)) {// 是节假日
						holiday1 = 1;
						holiday = 1;
					}
				}
				if (holiday == 0) {
					long evectionCount = attendEvectionDao.getEvectionCountByDate(j, pTemp);
					if (evectionCount > 0) {
						holiday1 = 1;
						holiday = 1;
					}
				}

				if (holiday == 0) {
					long leaveCount = attendLeaveDao.getLeaveCountByDate(j,pTemp);
					if (leaveCount > 0) {
						holiday1 = 1;
						holiday = 1;
					}
				}
				if (holiday == 0) {
					dutyOnTotal += dutyOnTimes;
					dutyOffTotal += dutyOffTimes;
				}
				int perfectFlag = 0;

				//判断这一天是否在登记范围内，解决出项负数的问题
				
				if(holiday==0){
					List<TeeAttendDuty> dutyList = dutyDao.getOneDayAttendDuty(jj,
							pTemp);
					
					int oneDayCount = 0;
					Map oneDayReg = new HashMap();
					for (int k = 0; k < dutyList.size(); k++) {
						TeeAttendDuty dutyTemp = dutyList.get(k);
						oneDayCount++;
						Date d = dutyTemp.getRegisterTime().getTime();// .getTimestamp("REGISTER_TIME");
						oneDayReg.put(oneDayCount, d);
						int dutyTypeCount = (Integer) regCount.get(dutyTypeTmp);// 一个排版一天考勤总记录
						if (oneDayCount == dutyTypeCount && dutyTypeCount % 2 == 0
								&& oneDayCount % 2 == 0 && dutyTypeCount > 1
								&& oneDayCount > 1) {// 循环最后一次
							int cc = (Integer) regCount.get(dutyTypeTmp);// 第1-2次
							Date dd = (Date) oneDayReg.get(cc);
							Date dd2 = (Date) oneDayReg.get(cc - 1);
							long cha5 = dd.getTime();
							long cha4 = dd2.getTime();
							long cha1 = cha5 - cha4;
							long cha2 = 0;
							long cha3 = 0;
							
							if ((Integer) regCount.get(dutyTypeTmp) - 2 > 1) {// 第3-4次
								dd = (Date) oneDayReg.get(cc - 2);
								dd2 = (Date) oneDayReg.get(cc - 3);
								long cha7 = dd.getTime();
								long cha8 = dd2.getTime();
								cha2 = cha7 - cha8;
							}
							if ((Integer) regCount.get(dutyTypeTmp) - 4 > 1) {// 第5-6次
								dd = (Date) oneDayReg.get(cc - 4);
								dd2 = (Date) oneDayReg.get(cc - 5);
								long cha7 = dd.getTime();
								long cha8 = dd2.getTime();
								cha3 = cha7 - cha8;
							}
							allMinites += cha1 + cha2 + cha3;// 计算上班总时长
						}
						String registerType = dutyTemp.getRegisterType();// .getString("REGISTER_TYPE");//上下班
						int dutyType = 	dutyTemp.getDutyType();												// 1、2、3、4、5、6
						String dutyTime = (String) duty.get("DUTY_TIME"
								+ dutyType);// 时间
						String dutyType11 = (String) duty.get("DUTY_TYPE"
								+ dutyType);// 1-上班 、2-下班
						if ("".equals(dutyTime)) {
							continue;
						}
						if ("0".equals(dutyType11)) {
							if (this.compareTime(d, dutyTime) < 1) {// 登记时间比 上班时间早
								perfectFlag++;// 完美+ 1
							}
							
							if (holiday > 0 && holiday1 != 1) {// 节假日、周末、请假、出差
								overOnCount++;// + 1
								continue;
							}
							
							dutyOnCount++;// 上班+
							if (this.compareTime(d, dutyTime) == 1) {// 登记时间比上班时间完
								lateCount++;// 迟到
							}
						}
						if ("1".equals(dutyType11)) {
							if (this.compareTime(d, dutyTime) > -1) {// 登记时间比 下班时间晚
								perfectFlag++;// 完美+ 1
							}
							if (holiday > 0 && holiday != 1) {// 节假日、周末、请假、出差
								overOffCount++;// + 1
								continue;
							}
							dutyOffCount++;// 下班班+1
							if (this.compareTime(d, dutyTime) == -1) {// 登记时间比下班时间早
								earlyCount++;// 早退＋　１
							}
						}
					}
				}

				if (perfectFlag >= dutyOnTimes + dutyOffTimes) {// 全勤天
					prefectCount++;
				}
			}

			allMinites = allMinites / 1000; // 上班总时长
			allHours = allMinites / 3600;
			int hour1 = allMinites % 3600;
			int minite = hour1 / 60;
			if (allHours != 0 || minite != 0) {
				allHoursMinites = allHours + "时" + minite + "分";
			} else {
				allHoursMinites = "0时";
			}

			Map map = new HashMap();
			map.put("userId", pTemp.getUuid());
			map.put("userName", pTemp.getUserName());
			map.put("deptName", userDeptName);
			map.put("num", num);
			int tmp = dutyOnTotal - dutyOnCount;
			map.put("workOnNoRegisters", tmp);// 上班位登记
			map.put("perfectCount", prefectCount);// 全勤天
			map.put("lateNums", lateCount);// 迟到

			map.put("leaveEarlyNums", earlyCount);// 早退
			map.put("hours", allHoursMinites);// 上班市场
			tmp = dutyOffTotal - dutyOffCount;
			map.put("workOutNoRegisters", tmp);// 下班为登记
			if(attendConfigService.isNoRegisterUserIds(pTemp.getUuid())){
				map.put("workOnNoRegisters", 0);// 上班位登记
				map.put("perfectCount", 0);// 全勤天
				map.put("lateNums", 0);// 迟到
				map.put("leaveEarlyNums", 0);// 早退
				map.put("hours", "0时");// 上班市场
				map.put("workOutNoRegisters", 0);// 下班为登记
			}
			
			//获取外出天数
			double outDays=getOutDays(pTemp,start,end);
			//获取请假天数
			double leaveDays=getLeaveDays(pTemp,start,end);
			//获取出差天数
			double evectionDays=getEvectionDays(pTemp,start,end);
			//获取加班时长
			double overHours=getOverHours(pTemp,start,end);
			
			map.put("outDays", outDays);
			map.put("leaveDays", leaveDays);
			map.put("evectionDays", evectionDays);
			map.put("overHours", overHours);
			
			
			/* int nums=0;
			   int num1=0;*/
			// int g=1;
			 String dutyTimeDescNum1 ="";
			 String dutyTimeDescNum2 ="";
			 String dutyTimeDescNum3 ="";
			 String dutyTimeDescNum4 ="";
			 String dutyTimeDescNum5 ="";
			 String dutyTimeDescNum6 ="";
			 request.setAttribute("userId", pTemp.getUuid());
			 TeeJson jsonDuty = getExportDutyByCondition(request);
			 List<Map<String, String>> listDuty = (List<Map<String, String>>) jsonDuty.getRtData();
			 int change = listDuty.size();
			 for(int j =0;j<listDuty.size();j++){
			    	
			    	if(listDuty.get(j).get("dutyTimeDesc1")!=null && listDuty.get(j).get("dutyTimeDesc1")!="" && listDuty.get(j).get("dutyTimeDesc1")!="null"){
			    		String dutyTimeDesc1 = listDuty.get(j).get("dutyTimeDesc1");
			    		if(!"未登记".equals(dutyTimeDesc1)){
			    			dutyTimeDesc1 = dutyTimeDesc1.substring(0,5);
			    		}
			    		dutyTimeDescNum1 += dutyTimeDesc1+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc2")!=null && listDuty.get(j).get("dutyTimeDesc2")!="" && listDuty.get(j).get("dutyTimeDesc2")!="null"){
			    		String dutyTimeDesc2 = listDuty.get(j).get("dutyTimeDesc2");
			    		if(!"未登记".equals(dutyTimeDesc2)){
			    			dutyTimeDesc2 = dutyTimeDesc2.substring(0,5);
			    		}
			    		dutyTimeDescNum2 += dutyTimeDesc2+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc3")!=null && listDuty.get(j).get("dutyTimeDesc3")!="" && listDuty.get(j).get("dutyTimeDesc3")!="null"){
			    		String dutyTimeDesc3 = listDuty.get(j).get("dutyTimeDesc3");
			    		if(!"未登记".equals(dutyTimeDesc3)){
			    			dutyTimeDesc3 = dutyTimeDesc3.substring(0,5);
			    		}
			    		dutyTimeDescNum3 += dutyTimeDesc3+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc4")!=null && listDuty.get(j).get("dutyTimeDesc4")!="" && listDuty.get(j).get("dutyTimeDesc4")!="null"){
			    		String dutyTimeDesc4 = listDuty.get(j).get("dutyTimeDesc4");
			    		if(!"未登记".equals(dutyTimeDesc4)){
			    			dutyTimeDesc4 = dutyTimeDesc4.substring(0,5);
			    		}
			    		dutyTimeDescNum4 += dutyTimeDesc4+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc5")!=null && listDuty.get(j).get("dutyTimeDesc5")!="" && listDuty.get(j).get("dutyTimeDesc5")!="null"){
			    		String dutyTimeDesc5 = listDuty.get(j).get("dutyTimeDesc5");
			    		if(!"未登记".equals(dutyTimeDesc5)){
			    			dutyTimeDesc5 = dutyTimeDesc5.substring(0,5);
			    		}
			    		dutyTimeDescNum5 += dutyTimeDesc5+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc6")!=null && listDuty.get(j).get("dutyTimeDesc6")!="" && listDuty.get(j).get("dutyTimeDesc6")!="null"){
			    		String dutyTimeDesc6 = listDuty.get(j).get("dutyTimeDesc6");
			    		if(!"未登记".equals(dutyTimeDesc6)){
			    			dutyTimeDesc6 = dutyTimeDesc6.substring(0,5);
			    		}
			    		dutyTimeDescNum6 += dutyTimeDesc6+","; 
			    	}
			    }
			 map.put("dutyTimeDesc1", dutyTimeDescNum1);
			 map.put("dutyTimeDesc2", dutyTimeDescNum2);
			 map.put("dutyTimeDesc3", dutyTimeDescNum3);
			 map.put("dutyTimeDesc4", dutyTimeDescNum4);
			 map.put("dutyTimeDesc5", dutyTimeDescNum5);
			 map.put("dutyTimeDesc6", dutyTimeDescNum6);
			  // g++;
			
			
			list.add(map);
			count++;
		} 
		json.setRtData(list);
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}

	
	
	/**
	 * 根据用户   开始统计时间   结束统计时间    获取用户在这段统计时间内的加班时长
	 * @param pTemp
	 * @param start
	 * @param end
	 * @return
	 */
	private double getOverHours(TeePerson person, Date start, Date end) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取状态为审批通过 的加班申请
		String hql=" from TeeAttendOvertime where allow=1  and user.uuid="+person.getUuid();
		List<TeeAttendOvertime> overTimeList=simpleDaoSupport.find(hql, null);
		
		String startDesc=sdf.format(start)+" 00:00:00";
		String endDesc=sdf.format(end)+" 23:59:59";
		//开始统计时间
		Calendar countStart=TeeDateUtil.parseCalendar(startDesc);
		//结束统计时间
		Calendar countEnd=TeeDateUtil.parseCalendar(endDesc);
		
		long s=0;
		long e=0;
		Calendar s1=null;
		Calendar e1=null;
		double sumHours=0;
		for (TeeAttendOvertime teeAttendOvertime : overTimeList) {
			s=teeAttendOvertime.getStartTime();
			e=teeAttendOvertime.getEndTime();
			s1= Calendar.getInstance();
			e1= Calendar.getInstance();
			s1.setTimeInMillis(s);
			e1.setTimeInMillis(e);
			
			//开始时间小于 统计开始时间   结束时间大于 统计结束时间
			if(s1.compareTo(countStart)<=0&&e1.compareTo(countEnd)>=0){	
				sumHours+=teeAttendOvertime.getOverHours().doubleValue();
			}else if(s1.compareTo(countStart)>=0&&e1.compareTo(countEnd)<=0){//开始时间大于 统计开始时间    结束时间小于  统计结束时间
				sumHours+=teeAttendOvertime.getOverHours().doubleValue();
			}else if(s1.compareTo(countStart)<=0&&e1.compareTo(countStart)>=0&&e1.compareTo(countEnd)<=0){//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
				sumHours+=teeAttendOvertime.getOverHours().doubleValue();
			}else if(s1.compareTo(countStart)>=0&&s1.compareTo(countEnd)<=0&&e1.compareTo(countEnd)>=0){//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
				sumHours+=teeAttendOvertime.getOverHours().doubleValue();
			}else{
				continue;
			}	
		}
		
		return sumHours;
	}

	/**
	 * 根据用户   开始统计时间   结束统计时间    获取用户在这段统计时间内的出差天数
	 * @param pTemp
	 * @param start
	 * @param end
	 * @return
	 */
	private double getEvectionDays(TeePerson person, Date start, Date end) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取状态为审批通过 
		String hql=" from TeeAttendEvection where allow=1  and user.uuid="+person.getUuid();
		List<TeeAttendEvection> evectionList=simpleDaoSupport.find(hql, null);
		
		String startDesc=sdf.format(start)+" 00:00:00";
		String endDesc=sdf.format(end)+" 23:59:59";
		//开始统计时间
		Calendar countStart=TeeDateUtil.parseCalendar(startDesc);
		//结束统计时间
		Calendar countEnd=TeeDateUtil.parseCalendar(endDesc);
		
		double sumDays=0;
			
		TeeAttendConfig config=null;
		
		for(Calendar c=(Calendar) countStart.clone();c.compareTo(countEnd)<=0;c.set(Calendar.HOUR_OF_DAY,0),c.set(Calendar.MINUTE,0),c.set(Calendar.SECOND,0),c.add(Calendar.DATE, 1)){
			Calendar c1=null;
			Calendar c2=null;
			if(sdf.format(c.getTime()).equals(sdf.format(end))){//当前日期和结束日期是同一天
				c1=(Calendar) c.clone();
				c2=(Calendar) countEnd.clone();
			}else{//不是同一天
				c1=(Calendar) c.clone();
				c2=(Calendar) c.clone();
				c2.set(Calendar.HOUR_OF_DAY,23);
				c2.set(Calendar.MINUTE,59);
				c2.set(Calendar.SECOND,59);
			}
			
			config=seniorConfigService.getAttendConfigByUserAndDate(person, sdf.format(c1.getTime()));
			
			
			long s=0;
			long e=0;
			Calendar s1=null;
			Calendar e1=null;
			for (TeeAttendEvection eve : evectionList) {
				s=eve.getStartTime();
				e=eve.getEndTime();
				s1= Calendar.getInstance();
				e1= Calendar.getInstance();
				s1.setTimeInMillis(s);
				e1.setTimeInMillis(e);
				
				if(config!=null){
					//开始时间小于 统计开始时间   结束时间大于 统计结束时间
					if(s1.compareTo(c1)<=0&&e1.compareTo(c2)>=0){	
						sumDays+=(timeRangeCalcute(config,c1,c2));
					}else if(s1.compareTo(c1)>=0&&e1.compareTo(c2)<=0){//开始时间大于 统计开始时间    结束时间小于  统计结束时间
						sumDays+=(timeRangeCalcute(config,s1,e1));
					}else if(s1.compareTo(c1)<=0&&e1.compareTo(c1)>=0&&e1.compareTo(c2)<=0){//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
						sumDays+=(timeRangeCalcute(config,c1,e1));
					}else if(s1.compareTo(c1)>=0&&s1.compareTo(c2)<=0&&e1.compareTo(c2)>=0){//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
						sumDays+=(timeRangeCalcute(config,s1,c2));
					}else{
						continue;
					}
				}
				
			}	
		}
				
		return sumDays;
	}

	/**
	 * 根据用户   开始统计时间   结束统计时间    获取用户在这段统计时间内的請假天数
	 * @param pTemp
	 * @param start
	 * @param end
	 * @return
	 */
	private double getLeaveDays(TeePerson person, Date start, Date end) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取状态为审批通过 
		String hql=" from TeeAttendLeave where allow=1   and user.uuid="+person.getUuid();
		List<TeeAttendLeave> leaveList=simpleDaoSupport.find(hql, null);
		
		String startDesc=sdf.format(start)+" 00:00:00";
		String endDesc=sdf.format(end)+" 23:59:59";
		//开始统计时间
		Calendar countStart=TeeDateUtil.parseCalendar(startDesc);
		//结束统计时间
		Calendar countEnd=TeeDateUtil.parseCalendar(endDesc);
		

        double sumDays=0;
		
		TeeAttendConfig config=null;
		
		for(Calendar c=(Calendar) countStart.clone();c.compareTo(countEnd)<=0;c.set(Calendar.HOUR_OF_DAY,0),c.set(Calendar.MINUTE,0),c.set(Calendar.SECOND,0),c.add(Calendar.DATE, 1)){
			Calendar c1=null;
			Calendar c2=null;
			if(sdf.format(c.getTime()).equals(sdf.format(end))){//当前日期和结束日期是同一天
				c1=(Calendar) c.clone();
				c2=(Calendar) countEnd.clone();
			}else{//不是同一天
				c1=(Calendar) c.clone();
				c2=(Calendar) c.clone();
				c2.set(Calendar.HOUR_OF_DAY,23);
				c2.set(Calendar.MINUTE,59);
				c2.set(Calendar.SECOND,59);
			}
			
			config=seniorConfigService.getAttendConfigByUserAndDate(person, sdf.format(c1.getTime()));
			
			
			long s=0;
			long e=0;
			Calendar s1=null;
			Calendar e1=null;
			for (TeeAttendLeave leave : leaveList) {
				s=leave.getStartTime();
				e=leave.getEndTime();
				s1= Calendar.getInstance();
				e1= Calendar.getInstance();
				s1.setTimeInMillis(s);
				e1.setTimeInMillis(e);
				
				//开始时间小于 统计开始时间   结束时间大于 统计结束时间
				if(s1.compareTo(c1)<=0&&e1.compareTo(c2)>=0){	
					sumDays+=timeRangeCalcute(config,c1,c2);
				}else if(s1.compareTo(c1)>=0&&e1.compareTo(c2)<=0){//开始时间大于 统计开始时间    结束时间小于  统计结束时间
					sumDays+=timeRangeCalcute(config,s1,e1);
				}else if(s1.compareTo(c1)<=0&&e1.compareTo(c1)>=0&&e1.compareTo(c2)<=0){//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
					sumDays+=timeRangeCalcute(config,c1,e1);
				}else if(s1.compareTo(c1)>=0&&s1.compareTo(c2)<=0&&e1.compareTo(c2)>=0){//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
					sumDays+=timeRangeCalcute(config,s1,c2);
				}else{
					continue;
				}	
			}
			
			
			
		}
			
		return sumDays;
	}

	
	
	
	/**
	 * 根据用户   开始统计时间   结束统计时间    获取用户在这段统计时间内的外出天数
	 * @param pTemp
	 * @param start
	 * @param end
	 * @return
	 */
	private double getOutDays(TeePerson person, Date start, Date end) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取状态为审批通过 
		String hql=" from TeeAttendOut where allow=1  and user.uuid="+person.getUuid();
		List<TeeAttendOut> outList=simpleDaoSupport.find(hql, null);
		
		String startDesc=sdf.format(start)+" 00:00:00";
		String endDesc=sdf.format(end)+" 23:59:59";
		//开始统计时间
		Calendar countStart=TeeDateUtil.parseCalendar(startDesc);
		//结束统计时间
		Calendar countEnd=TeeDateUtil.parseCalendar(endDesc);
		
		
		
		double sumDays=0;
		
		TeeAttendConfig config=null;
		
		for(Calendar c=(Calendar) countStart.clone();c.compareTo(countEnd)<=0;c.set(Calendar.HOUR_OF_DAY,0),c.set(Calendar.MINUTE,0),c.set(Calendar.SECOND,0),c.add(Calendar.DATE, 1)){
			Calendar c1=null;
			Calendar c2=null;
			if(sdf.format(c.getTime()).equals(sdf.format(end))){//当前日期和结束日期是同一天
				c1=(Calendar) c.clone();
				c2=(Calendar) countEnd.clone();
			}else{//不是同一天
				c1=(Calendar) c.clone();
				c2=(Calendar) c.clone();
				c2.set(Calendar.HOUR_OF_DAY,23);
				c2.set(Calendar.MINUTE,59);
				c2.set(Calendar.SECOND,59);
			}
			
			config=seniorConfigService.getAttendConfigByUserAndDate(person, sdf.format(c1.getTime()));
			
			
			long s=0;
			long e=0;
			Calendar s1=null;
			Calendar e1=null;
			for (TeeAttendOut teeAttendOut : outList) {
				s=teeAttendOut.getStartTime();
				e=teeAttendOut.getEndTime();
				s1= Calendar.getInstance();
				e1= Calendar.getInstance();
				s1.setTimeInMillis(s);
				e1.setTimeInMillis(e);
				
				//开始时间小于 统计开始时间   结束时间大于 统计结束时间
				if(s1.compareTo(c1)<=0&&e1.compareTo(c2)>=0){	
					sumDays+=timeRangeCalcute(config,c1,c2);
				}else if(s1.compareTo(c1)>=0&&e1.compareTo(c2)<=0){//开始时间大于 统计开始时间    结束时间小于  统计结束时间
					sumDays+=timeRangeCalcute(config,s1,e1);
				}else if(s1.compareTo(c1)<=0&&e1.compareTo(c1)>=0&&e1.compareTo(c2)<=0){//开始时间小于 统计开始时间     结束时间大于统计开始时间   小于统计结束时间
					sumDays+=timeRangeCalcute(config,c1,e1);
				}else if(s1.compareTo(c1)>=0&&s1.compareTo(c2)<=0&&e1.compareTo(c2)>=0){//开始时间大于统计开始时间   开始时间小于统计结束时间   结束时间大于统计结束时间
					sumDays+=timeRangeCalcute(config,s1,c2);
				}else{
					continue;
				}	
			}
			
			
			
		}
		
		
		
		
		return sumDays;
	}


	/**
	 * 对比时间
	 * @author syl
	 * @date 2014-6-23
	 * @param date
	 * @param time
	 * @return
	 */
	public int compareTime(Date date , String time) {
	    String[] times = time.split(":");
	    int hours = date.getHours();
	    int seconds = date.getSeconds();
	    int minutes = date.getMinutes();
	    if (Integer.parseInt(times[0]) > hours) {
	      return -1;
	    } else if (Integer.parseInt(times[0]) < hours) {
	      return 1;
	    } else {
	      if (Integer.parseInt(times[1]) > minutes) {
	        return -1;
	      } else if (Integer.parseInt(times[1]) < minutes) {
	        return 1;
	      } else {
	        if (Integer.parseInt(times[2]) > seconds) {
	          return -1;
	        } else if (Integer.parseInt(times[2]) < seconds) {
	          return 1;
	        } else {
	          return 0;
	        }
	      }
	    }
	 }
	/**
	 * 判断是否是节假日
	 * @author syl
	 * @date 2014-6-23
	 * @param list
	 * @return
	 */
	public boolean checkIsHoliday(List<TeeAttendHoliday> list , Calendar date){
		boolean isHoliday = false;
		for (int i = 0;list!=null && i < list.size(); i++) {
			TeeAttendHoliday holiday = list.get(i);
			Calendar strat = holiday.getStartTime();
			Calendar end = holiday.getEndTime();
			if(date.compareTo(strat) >= 0 && date.compareTo(end) <= 0){//在这之间的
				return true;
			}
		}
		return isHoliday;
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
	 * 获取当前用户当天第几次签到
	 * @function: 
	 * @author: wyw
	 * @data: 2015年3月18日
	 * @param request
	 * @return
	 * @throws Exception TeeJson
	 */
	public TeeJson getRegisterStatue(HttpServletRequest request) throws Exception {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);//当前登录人
		
		List<TeeAttendDuty> list = dutyDao.getDutyList(person.getUuid());
		StringBuffer buffer  = new StringBuffer();
		if(list != null &&  list.size()>0){
			for(TeeAttendDuty duty:list){
				if(buffer.length()>0){
					buffer.append(",");
				}
				buffer.append(duty.getDutyType());
			}
		}
		Map map = new HashMap();
		map.put("registerStatue", buffer.toString());
		TeeJson json = new TeeJson();
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

	
	/**
	 * 考勤同步
	 * @param request
	 * @return
	 */
	public TeeJson attendSync(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
		
		//获取开始时间  结束时间
	    String beginTime=TeeStringUtil.getString(request.getParameter("beginTime"));
	    String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
	    String mess=beginTime+","+endTime;
	    
	   
	    Date bTime=null;
	    Date eTime=null;
		try {
			bTime=sdf1.parse(beginTime);
			eTime = sdf1.parse(endTime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    Calendar bc=Calendar.getInstance();
	    Calendar ec=Calendar.getInstance();
	    
	    bc.setTime(bTime);
	    ec.setTime(eTime);
	    
	    
	    //同步之前先清空数据库中这段时间内的数据
	    String hql=" delete from TeeAttendDuty where registerTime >= ? and registerTime <=? ";
	    simpleDaoSupport.executeUpdate(hql, new Object[]{bc,ec});
	    
	    DataOutputStream out = null;
	    DataInputStream in = null;
	    ByteArrayOutputStream o = null;
	    ByteArrayInputStream in_ = null;
	    GZIPInputStream gzip = null;
	    String accpet = null;
        try {
        	//服务器端   发送同步请求
			out = new DataOutputStream(AttendsSync.socket.getOutputStream());
			//发送时间段信息
		    out.writeUTF(mess);
		    // 读取客户端传过来信息的DataInputStream  
            in = new DataInputStream(AttendsSync.socket.getInputStream());  
            //读取一个整型，标识当前数据传输的大小
            int total = Integer.parseInt(in.readUTF());
            //读取接收到的字节
            byte[]b=new byte[total];
            in.read(b);
            
            o = new ByteArrayOutputStream();
            in_ = new ByteArrayInputStream(b);
            // 使用默认缓冲区大小创建新的输入流  
            gzip = new GZIPInputStream(in_);  
            byte[] buffer = new byte[512];
            int n = 0;  
            while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组  
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流  
                o.write(buffer, 0, n);  
            }  
            gzip.close();
            //使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串  
            accpet=o.toString();  
            
        } catch (Exception e) {
        	json.setRtState(false);
			e.printStackTrace();
			return json;
		} finally{
			try{
				out.close();
			}catch(Exception ex){}
			try{
				in.close();
			}catch(Exception ex){}
			try{
				o.close();
			}catch(Exception ex){}
			try{
				in_.close();
			}catch(Exception ex){}
		}
        
        
        List<Map<String,String>> mapList=TeeJsonUtil.JsonStr2MapList(accpet);
        TeeAttendDuty duty=null;
        Date registerTime=null;
        String registerTimeStr="";
        String checkType="";
        Calendar calendar = null;
        TeePerson user=null;
        TeeAttendConfig config=null;
        Calendar dutyTime1;
        Calendar dutyTime2;
        long time1=0;
        long time2=0;
        int year=0;
        int month=0;
        int date=0;
        String beginTodayStr="";
    	String endTodayStr="";
    	Date beginToday=null;
    	Date endToday=null;
        for (Map<String, String> map : mapList) {
        	duty=new TeeAttendDuty();
        	//获取用户
        	user=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(map.get("userId"),0));
        	
        	if(user!=null){
        		
        		//打卡类型
            	checkType=TeeStringUtil.getString(map.get("checkType"));
        		//打卡时间
            	registerTimeStr=TeeStringUtil.getString(map.get("checkTime"));
            
            	
            	
            	year=TeeStringUtil.getInteger(registerTimeStr.substring(0, 4), 0);
            	month=TeeStringUtil.getInteger(registerTimeStr.substring(5, 7), 0);
            	date=TeeStringUtil.getInteger(registerTimeStr.substring(8, 10), 0);
            	
            	beginTodayStr=year+"-"+month+"-"+date+" 00:00:00";
            	endTodayStr=year+"-"+month+"-"+date+" 23:59:59";
            	Calendar registerCalendar=Calendar.getInstance();
            	
            	try {
					beginToday=sdf.parse(beginTodayStr);
					endToday=sdf.parse(endTodayStr);
					registerTime=sdf.parse(registerTimeStr);
					registerCalendar.setTime(registerTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	//获取该用户  这天的排班类型
            	config=seniorConfigService.getAttendConfigByUserAndDate(user, year+"-"+month+"-"+date);
            	
            	
            	if(config!=null&&config.getSid()!=0){
        		     dutyTime1=config.getDutyTime1();
        			 dutyTime2=config.getDutyTime2();
        			 
        			 dutyTime1.set(year, month-1, date);
        			 dutyTime2.set(year, month-1, date);
        			 
        			 time1=dutyTime1.getTimeInMillis()+60*60*1000;//规定签到时间+1小时
        			 time2=dutyTime2.getTimeInMillis()-60*60*1000;//规定签退时间-1小时
        			 
        			 
        			 
        			 if("I".equals(checkType)){//签到  
        				 if(registerTime.getTime()>=beginToday.getTime()&& registerTime.getTime()<=time1){
        					 duty.setUserId(TeeStringUtil.getInteger(map.get("userId"),0)); 
        					 duty.setRegisterTime(registerCalendar);
        					 duty.setRegisterType(config.getSid()+"");
        					 duty.setDutyType(1);
        					 simpleDaoSupport.save(duty);
        				 }
        				 
        			 }else if("O".equals(checkType)){//签退
        				 if(registerTime.getTime()>=time2&&registerTime.getTime()<=endToday.getTime()){
        					 duty.setUserId(TeeStringUtil.getInteger(map.get("userId"),0)); 
        					 duty.setRegisterTime(registerCalendar);
        					 duty.setRegisterType(config.getSid()+"");
        					 duty.setDutyType(2);
        					 simpleDaoSupport.save(duty);
        				 }
        			 }	
        		}
        	}	
		}
        
        
        json.setRtState(true);  
		return json;
	}
	
	
	
	
/*	public static void main(String[] args) throws ParseException {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(",1,2,3,4".indexOf(",1,"));
		Date date = s.parse("2014-6-1");
		Date date2 = s.parse("2014-6-2");
		Date date3= s.parse("2014-6-3");
		Date date4= s.parse("2014-6-4");
		Date date5 = s.parse("2014-6-5");
		Date date6 = s.parse("2014-6-6");
		Date date7 = s.parse("2014-6-7");
		System.out.println(date.getDay() +":"+ date2.getDay() 
				+":"+date3.getDay() +":"+date4.getDay() +":"+date5.getDay() +":"+date6.getDay() +":"+date7.getDay());
	}*/
	
	
	 public static double getDistance(double lat1, double lng1, double lat2,    
             double lng2) {    
			double radLat1 = lat1 * Math.PI / 180.0;    
			double radLat2 = lat2 * Math.PI / 180.0;    
			double a = radLat1 - radLat2;    
			double b =( lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0) ;    
			double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)    
			+ Math.cos(radLat1) * Math.cos(radLat2)    
			* Math.pow(Math.sin(b / 2), 2)));    
			s = s * EARTH_RADIUS;    
			s = Math.round(s * 10000d) / 10000d;    
			s = s*1000;    
			return s;    
	}

	 
	/**
	 * 根据排班类型  获取两个时间段   之间的 时间差（只计算工作时间    排除节假日  周六日）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getTimeDiffByDutyConfig(String startTimeStr,String endTimeStr,TeePerson person) {
		TeeJson json=new TeeJson();
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd ");
		if(person!=null){
			Calendar start=TeeDateUtil.parseCalendar(startTimeStr);
			Calendar end=TeeDateUtil.parseCalendar(endTimeStr);
			Calendar c1 = null;
			Calendar c2 = null;
			
			//結束時間      年月日格式
	        String endTimeStr1=sdf.format(end.getTime());
	        String cStr="";    
	        double totalDays=0;
	        TeeAttendConfig config = null;
			for(Calendar c=(Calendar) start.clone();c.compareTo(end)<=0;c.set(Calendar.HOUR_OF_DAY, 0),c.set(Calendar.MINUTE, 0),c.set(Calendar.SECOND, 0),c.add(Calendar.DATE, 1)){
				cStr=sdf.format(c.getTime());
				
				if(cStr.equals(endTimeStr1)){//如果c与end是同一天，则传入的开始时间是c  结束时间是end
					c1 = (Calendar) c.clone();
					c2 = (Calendar) end.clone();
				 }else{//不在同一天   那么开始时间是c  结束时间是 c的23:59:59
					c1 = (Calendar) c.clone();
					c2 = (Calendar) c.clone();
					c2.set(Calendar.HOUR_OF_DAY, 23);
					c2.set(Calendar.MINUTE, 59);
					c2.set(Calendar.SECOND, 59);
				 }
				//System.out.println(TeeDateUtil.format(c1)+"   "+TeeDateUtil.format(c2));
				config = seniorConfigService.getAttendConfigByUserAndDate(person, sdf.format(c1.getTime()));
				
				totalDays+=(timeRangeCalcute(config,c1,c2)*1.00);
			}
			
     		json.setRtData(totalDays);
			json.setRtState(true);
		
		}else{
           json.setRtState(false);
           json.setRtMsg("用户不存在！");
		}
		return json;
	}

	
	
	/**
	 * 根据排版时刻表  计算时间间隔  是多长（天）
	 * @param strings
	 * @param startTimeStr
	 * @param endTimeStr
	 * @return
	 */
	private double timeRangeCalcute(TeeAttendConfig config, Calendar startTime,Calendar endTime) {
		List<String> signs=new ArrayList<String>();
		if(config!=null){
			if(config.getSid()==0){
				return 0;
			}else{
				if(config.getDutyTime1()!=null&&config.getDutyTime2()!=null){
					signs.add(TeeDateUtil.format(config.getDutyTime1().getTime(), "HH:mm"));
					signs.add(TeeDateUtil.format(config.getDutyTime2().getTime(), "HH:mm"));
				}
				
				if(config.getDutyTime3()!=null&&config.getDutyTime4()!=null){
					signs.add(TeeDateUtil.format(config.getDutyTime3().getTime(), "HH:mm"));
					signs.add(TeeDateUtil.format(config.getDutyTime4().getTime(), "HH:mm"));
				}
				
				if(config.getDutyTime5()!=null&&config.getDutyTime6()!=null){
					signs.add(TeeDateUtil.format(config.getDutyTime5().getTime(), "HH:mm"));
					signs.add(TeeDateUtil.format(config.getDutyTime6().getTime(), "HH:mm"));
				}
			}
			
		}else{
			signs.add("09:00");
			signs.add("18:00");
		}

		//请假开始时间
//		Calendar startTime = TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm", startTimeStr);
//		//请假结束时间
//		Calendar endTime = TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm", endTimeStr);
		
		//开始时间正点
		Calendar s = TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm", TeeDateUtil.format(startTime.getTime(), "yyyy-MM-dd")+" 00:00");
		//结束时间正点
		Calendar e = TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm", TeeDateUtil.format(endTime.getTime(), "yyyy-MM-dd")+" 23:59");
		
		
		//获取一天的工作时长
		double workHours = 0;//一天n小时上班
		Calendar on=null;
		Calendar off=null;
		for(int i=0;i<signs.size();i+=2){
			on=TeeDateUtil.parseCalendar("2017-06-23 "+TeeStringUtil.getString(signs.get(i)));
			off=TeeDateUtil.parseCalendar("2017-06-23 "+TeeStringUtil.getString(signs.get(i+1)));
			long l = off.getTimeInMillis()-on.getTimeInMillis();
			workHours+=l*1.00/(1000*60*60)*1.00;
		}
		
		
		//总天数
		double totalDays=0;
		boolean isGeneral  = false;
		boolean isHoliday  = false;
		// 获取所有节假日
		List<TeeAttendHoliday> holidayList = holidayDao.getHolidayList();
		
		
		
		//遍历日历
		for(Calendar s1=s;s1.compareTo(e)<=0;s1.add(Calendar.DATE,1)){
			//公休日判斷
			if(config!=null){
				isGeneral = dutyDao.isGeneral(config.getSid());
			}else{
				isGeneral = false;
			}
			
			if(isGeneral){
				continue;
			}
			
			//節假日判斷
			if(config!=null){
				isHoliday = checkIsHoliday(
						holidayList,s1); 
						
			}else{
				isHoliday = false;
			}
			
			if(isHoliday){
				continue;
			}
			
			
			
			
			//年月日  格式的日期
			String ymd = TeeDateUtil.format(s1.getTime(), "yyyy-MM-dd");
			//遍历签到时间
			for(int i=0;i<signs.size();i+=2){
				//设置a为临时请假开始时间，当天的00:00:00
				Calendar a = (Calendar) s1.clone();
				//设置b为临时请假结束时间，当天的23:59:59
				Calendar b = (Calendar) s1.clone();
				b.set(Calendar.HOUR_OF_DAY, 23);
				b.set(Calendar.MINUTE, 59);
				b.set(Calendar.SECOND, 59);
				
				//输出签到的时间段====================================================
				//System.out.println(TeeDateUtil.format(a)+"     "+TeeDateUtil.format(b));
				
				//如果临时的开始时间a不在请假范围内，则临时开始时间为请假的开始时间
				if(a.compareTo(startTime)<0 || a.compareTo(endTime)>0){
					a = startTime;
				}
				//如果临时的结束时间b不在请假范围内，则临时结束时间为请假的结束时间
				if(b.compareTo(startTime)<0 || b.compareTo(endTime)>0){
					b = endTime;
				}
				
				//输出签到的时间段====================================================
				//System.out.println(TeeDateUtil.format(a)+"     "+TeeDateUtil.format(b));
				
				//实际应该签到的时间
				Calendar signStartTime = TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm", ymd+" "+TeeStringUtil.getString(signs.get(i)));
				//实际应该签退的时间
				Calendar signEndTime = TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm",  ymd+" "+TeeStringUtil.getString(signs.get(i+1)));
				
				
				
				if(a.compareTo(signStartTime)<=0){
					a = signStartTime;
				}
				
				if(b.compareTo(signEndTime)>=0){
					b = signEndTime;
				}
				
				//输出签到的时间段====================================================
				//System.out.println(TeeDateUtil.format(a)+"     "+TeeDateUtil.format(b));
				
				long l = b.getTimeInMillis()-a.getTimeInMillis();
				if(l<0){//不在签到区域内
					continue;
				}
				double hours = l*1.00/(1000*60*60)*1.00;
				
				//System.out.println(hours);
				//计算公式   1/正常工作时长 = ?/计算出来的工作时长     ，则  ? = 计算出来的工作时长*(1/正常工作时长)
				totalDays+=(hours*(1/workHours));
				
			}
			
		}
		//return totalDays;
		return new BigDecimal(totalDays).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}  
	
	
	
	
	
	
	/**
	 * 考勤统计(用于定时任务向中间表插入数据)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=true)
	public TeeJson getRegisterRecordInfo(int deptId,String startDateDesc,String endDateDesc,PrintWriter pw) throws Exception {
		//TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(pw!=null){
			pw.write("<p>开始刷新考勤数据</p>");
			pw.flush();
		}
		
		TeeJson json = new TeeJson();
		List<Map> list  = new ArrayList<Map>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		int year = Integer.parseInt(startDateDesc.substring(0,4)) ;//年
		int month =Integer.parseInt(startDateDesc.substring(5,7)) ;//月
		String paraValue = sysParaDao.getSysParaValue("NO_DUTY_USER"); // 获取免签人员
		Date start = null;
		Date end = null;
		if (startDateDesc != null) {
			start = TeeUtility.parseDate("yyyy-MM-dd", startDateDesc);
		}
		if (endDateDesc != null) {
			end = TeeUtility.parseDate("yyyy-MM-dd", endDateDesc);
		}
		// ---- 取规定上下班时间 -----
		Map attendConfig = new HashMap();
		Map regCount = new HashMap();// 存放所有排版类型 map key---》sid value
										// ->map(排版详情)
		
		List<TeeAttendConfig> configList = attendConfigDao.getConfigList();
		for (int i = 0; i < configList.size(); i++) {
			int num = 0;
			TeeAttendConfig config = configList.get(i);
			int seqId = config.getSid();
			String dutyName = config.getDutyName();
			String general = config.getGeneral();
			String getDutyTime1 = "";
			String getDutyTime2 = "";
			String getDutyTime3 = "";
			String getDutyTime4 = "";
			String getDutyTime5 = "";
			String getDutyTime6 = "";
			if(config.getDutyTime1() != null){
				getDutyTime1 = format2.format(config.getDutyTime1().getTime());
				num++;
			}
			if(config.getDutyTime2() != null){
				getDutyTime2 = format2.format(config.getDutyTime2().getTime());
				num++;
			}
			if(config.getDutyTime3() != null){
				getDutyTime3 = format2.format(config.getDutyTime3().getTime());
				num++;
			}
			if(config.getDutyTime4() != null){
				getDutyTime4 = format2.format(config.getDutyTime4().getTime());
				num++;
			}	if(config.getDutyTime5() != null){
				getDutyTime5 = format2.format(config.getDutyTime5().getTime());
				num++;
			}
			if(config.getDutyTime6() != null){
				getDutyTime6 = format2.format(config.getDutyTime6().getTime());
				num++;
			}
			Map map = new HashMap();
			map.put("SEQ_ID", seqId);
			map.put("DUTY_NAME", dutyName);
			map.put("GENERAL", general);
			map.put("DUTY_TIME1", getDutyTime1);
			map.put("DUTY_TIME2", getDutyTime2);
			map.put("DUTY_TIME3", getDutyTime3);
			map.put("DUTY_TIME4",getDutyTime4);
			map.put("DUTY_TIME5", getDutyTime5);
			map.put("DUTY_TIME6", getDutyTime6);

			map.put("DUTY_TYPE1", String.valueOf(config.getDutyType1()));
			map.put("DUTY_TYPE2",  String.valueOf(config.getDutyType2()));
			map.put("DUTY_TYPE3",  String.valueOf(config.getDutyType3()));
			map.put("DUTY_TYPE4", String.valueOf( config.getDutyType4()));
			map.put("DUTY_TYPE5",  String.valueOf(config.getDutyType5()));
			map.put("DUTY_TYPE6",  String.valueOf(config.getDutyType6()));
            map.put("num",num);
			int c = 0;// 上下班总次数
			int dutyOnTimes = 0;// 上班次数
			int dutyOffTimes = 0;// 下班次数
			for (int j = 1; j <= 6; j++) {
				String dutyTimeI = (String) map.get("DUTY_TIME" + j);
				String dutyTypeI = (String) map.get("DUTY_TYPE" + j);
				if (TeeUtility.isNullorEmpty(dutyTimeI)) {
					continue;
				}
				c++;
				if ("0".equals(dutyTypeI)) {
					dutyOnTimes++;
				} else {
					dutyOffTimes++;
				}
			}
			regCount.put(seqId + "", c);
			map.put("DUTY_ON_TIMES", dutyOnTimes);
			map.put("DUTY_OFF_TIMES", dutyOffTimes);
			attendConfig.put(seqId + "", map);
		}

		/*// 获取所有人员
		List<TeePerson> userList = personDao.getAllUserNoDelete();*/
		List<TeePerson> userList=null;
		// 获取所有节假日
		List<TeeAttendHoliday> holidayList = holidayDao.getHolidayList();
		if (deptId > 0) {//指定部门
			TeeDepartment dept = (TeeDepartment)deptDao.get(deptId);
			String  deptLevel = dept.getDeptParentLevel();
			if(TeeUtility.isNullorEmpty(deptLevel)){//如果是第一级部门
				deptLevel = dept.getGuid();
			}
			userList = personDao.selectDeptAndChildDeptPerson(deptId,
					deptLevel);
		}

		int lineCount = 0;

		int count = 0;
		for (int i = 0; i < userList.size(); i++) {
			TeePerson pTemp = userList.get(i);
			int seqId = pTemp.getUuid();
			int allHours = 0;
			int allMinites = 0;
			String userName = pTemp.getUserName();// 姓名
			
			
			String userDeptName="";
			if(!TeeUtility.isNullorEmpty(pTemp.getDept())){
				userDeptName= pTemp.getDept().getDeptName();// 部门
			}else{
				userDeptName="";
			}
            
			lineCount++;
			int prefectCount = 0;// 全勤(天
			int earlyCount = 0;// 迟到
			int lateCount = 0;// 早退
			int dutyOnCount = 0;// 上班登记次数
			int dutyOffCount = 0;// 下班登记次数
			int dutyOnTotal = 0;// 上班总计数
			int dutyOffTotal = 0;// 下班总计数
			int overOnCount = 0;
			int overOffCount = 0;
			String allHoursMinites = "";// 时长

			
			String dutyTypeTmp="";
			Map duty =null;
			String general ="";
			int dutyOnTimes =0;// 上班次数
			int dutyOffTimes =0;// 下班次数
			int num =0;
			for (Date j = start; end.compareTo(j) >= 0; j = new Date(
					j.getTime() + 24 * 3600 * 1000)) {
				String jj = TeeUtility.getDateTimeStr(j, format);
				
				
				TeeAttendConfig config=seniorConfigService.getAttendConfigByUserAndDate(pTemp, jj);// 排版Id
				if(config==null){//无排班
					continue;
				}else{
					dutyTypeTmp = config.getSid()+"";
					duty = (Map) attendConfig.get(dutyTypeTmp);// 获取排版详情
					if (duty == null) {
						continue;
					}
					general = (String) duty.get("GENERAL");// 公休日
					dutyOnTimes = (Integer) duty.get("DUTY_ON_TIMES");// 上班次数
					dutyOffTimes = (Integer) duty.get("DUTY_OFF_TIMES");// 下班次数
					num = (Integer) duty.get("num");// 需要签到次数
					
				}
				
				
				
				Calendar dateTemp = Calendar.getInstance();
				dateTemp.setTime(j);
				
				int week = j.getDay();
				if(week == 0){//星期天
					week = 1;
				}else{
					week = week + 1;
				}
				int holiday = 0; // 0-正常上班 1- 公休日、节假日、出差、请假等
				int holiday1 = 0;

				/*
				 * if (T9WorkFlowUtility.findId(general, week + "")) { holiday =
				 * 1; }
				 */
				String generalTemp = "," + general + ",";
				String weekStr = ","+ week + ",";
				if ((config.getSid()==0) && !isAddWorkDay(dateTemp)) {// 判断当天是否是设置的公休日
					holiday = 1;
				}
				if (holiday == 0) {
					if (checkIsHoliday(holidayList, dateTemp)) {// 是节假日
						holiday1 = 1;
						holiday = 1;
					}
				}
				if (holiday == 0) {
					long evectionCount = attendEvectionDao.getEvectionCountByDate(j, pTemp);
					if (evectionCount > 0) {
						holiday1 = 1;
						holiday = 1;
					}
				}

				if (holiday == 0) {
					long leaveCount = attendLeaveDao.getLeaveCountByDate(j,pTemp);
					if (leaveCount > 0) {
						holiday1 = 1;
						holiday = 1;
					}
				}
				if (holiday == 0) {
					dutyOnTotal += dutyOnTimes;
					dutyOffTotal += dutyOffTimes;
				}
				int perfectFlag = 0;

				//判断这一天是否在登记范围内，解决出项负数的问题
				
				if(holiday==0){
					List<TeeAttendDuty> dutyList = dutyDao.getOneDayAttendDuty(jj,
							pTemp);
					
					int oneDayCount = 0;
					Map oneDayReg = new HashMap();
					for (int k = 0; k < dutyList.size(); k++) {
						TeeAttendDuty dutyTemp = dutyList.get(k);
						oneDayCount++;
						Date d = dutyTemp.getRegisterTime().getTime();// .getTimestamp("REGISTER_TIME");
						oneDayReg.put(oneDayCount, d);
						int dutyTypeCount = (Integer) regCount.get(dutyTypeTmp);// 一个排版一天考勤总记录
						if (oneDayCount == dutyTypeCount && dutyTypeCount % 2 == 0
								&& oneDayCount % 2 == 0 && dutyTypeCount > 1
								&& oneDayCount > 1) {// 循环最后一次
							int cc = (Integer) regCount.get(dutyTypeTmp);// 第1-2次
							Date dd = (Date) oneDayReg.get(cc);
							Date dd2 = (Date) oneDayReg.get(cc - 1);
							long cha5 = dd.getTime();
							long cha4 = dd2.getTime();
							long cha1 = cha5 - cha4;
							long cha2 = 0;
							long cha3 = 0;
							
							if ((Integer) regCount.get(dutyTypeTmp) - 2 > 1) {// 第3-4次
								dd = (Date) oneDayReg.get(cc - 2);
								dd2 = (Date) oneDayReg.get(cc - 3);
								long cha7 = dd.getTime();
								long cha8 = dd2.getTime();
								cha2 = cha7 - cha8;
							}
							if ((Integer) regCount.get(dutyTypeTmp) - 4 > 1) {// 第5-6次
								dd = (Date) oneDayReg.get(cc - 4);
								dd2 = (Date) oneDayReg.get(cc - 5);
								long cha7 = dd.getTime();
								long cha8 = dd2.getTime();
								cha3 = cha7 - cha8;
							}
							allMinites += cha1 + cha2 + cha3;// 计算上班总时长
						}
						String registerType = dutyTemp.getRegisterType();// .getString("REGISTER_TYPE");//上下班
						int dutyType = 	dutyTemp.getDutyType();												// 1、2、3、4、5、6
						String dutyTime = (String) duty.get("DUTY_TIME"
								+ dutyType);// 时间
						String dutyType11 = (String) duty.get("DUTY_TYPE"
								+ dutyType);// 0-上班 、1-下班
						if ("".equals(dutyTime)) {
							continue;
						}
						if ("0".equals(dutyType11)) {
							if (this.compareTime(d, dutyTime) < 1) {// 登记时间比 上班时间早
								perfectFlag++;// 完美+ 1
							}
							
							if (holiday > 0 && holiday1 != 1) {// 节假日、周末、请假、出差
								overOnCount++;// + 1
								continue;
							}
							
							dutyOnCount++;// 上班+
							if (this.compareTime(d, dutyTime) == 1) {// 登记时间比上班时间完
								lateCount++;// 迟到
							}
						}
						if ("1".equals(dutyType11)) {
							if (this.compareTime(d, dutyTime) > -1) {// 登记时间比 下班时间晚
								perfectFlag++;// 完美+ 1
							}
							if (holiday > 0 && holiday != 1) {// 节假日、周末、请假、出差
								overOffCount++;// + 1
								continue;
							}
							dutyOffCount++;// 下班班+1
							if (this.compareTime(d, dutyTime) == -1) {// 登记时间比下班时间早
								earlyCount++;// 早退＋　１
							}
						}
					}
				}

				if (perfectFlag >= dutyOnTimes + dutyOffTimes) {// 全勤天
					prefectCount++;
				}
			}

			allMinites = allMinites / 1000; // 上班总时长
			allHours = allMinites / 3600;
			int hour1 = allMinites % 3600;
			int minite = hour1 / 60;
			if (allHours != 0 || minite != 0) {
				allHoursMinites = allHours + "时" + minite + "分";
			} else {
				allHoursMinites = "0时";
			}

			Map map = new HashMap();
			map.put("userId", pTemp.getUuid());
			map.put("userName", pTemp.getUserName());
			map.put("deptName", userDeptName);
			map.put("num", num);
			int tmp = dutyOnTotal - dutyOnCount;
			map.put("workOnNoRegisters", tmp);// 上班位登记
			map.put("perfectCount", prefectCount);// 全勤天
			map.put("lateNums", lateCount);// 迟到

			map.put("leaveEarlyNums", earlyCount);// 早退
			map.put("hours", allHoursMinites);// 上班市场
			tmp = dutyOffTotal - dutyOffCount;
			map.put("workOutNoRegisters", tmp);// 下班为登记
			if(attendConfigService.isNoRegisterUserIds(pTemp.getUuid())){
				map.put("workOnNoRegisters", 0);// 上班位登记
				map.put("perfectCount", 0);// 全勤天
				map.put("lateNums", 0);// 迟到
				map.put("leaveEarlyNums", 0);// 早退
				map.put("hours", "0时");// 上班市场
				map.put("workOutNoRegisters", 0);// 下班为登记
			}
			
			
			
			int attendAssignDays=getAttendAssignDays(pTemp,start,end);
			int attendAssignNums=getAttendAssignNums(pTemp,start,end);
			//获取外出天数
			double outDays=getOutDays(pTemp,start,end);
			//获取请假天数
			double leaveDays=getLeaveDays(pTemp,start,end);
			//获取出差天数
			double evectionDays=getEvectionDays(pTemp,start,end);
			//获取加班时长
			double overHours=getOverHours(pTemp,start,end);
			//获取申诉次数
			int complainNum=getComplainNum(pTemp,start,end);
			
			map.put("outDays", outDays);
			map.put("leaveDays", leaveDays);
			map.put("evectionDays", evectionDays);
			map.put("overHours", overHours);
			map.put("attendAssignDays", attendAssignDays);
			map.put("attendAssignNums", attendAssignNums);
			map.put("complainNum", complainNum);
			
			/* int nums=0;
			   int num1=0;*/
			// int g=1;
			 String dutyTimeDescNum1 ="";
			 String dutyTimeDescNum2 ="";
			 String dutyTimeDescNum3 ="";
			 String dutyTimeDescNum4 ="";
			 String dutyTimeDescNum5 ="";
			 String dutyTimeDescNum6 ="";
			 
			 TeeJson jsonDuty = getExportDutyByCondition(pTemp.getUuid(),startDateDesc,endDateDesc);
			 List<Map<String, String>> listDuty = (List<Map<String, String>>) jsonDuty.getRtData();
			 int change = listDuty.size();
			 for(int j =0;j<listDuty.size();j++){
			    	
			    	if(listDuty.get(j).get("dutyTimeDesc1")!=null && listDuty.get(j).get("dutyTimeDesc1")!="" && listDuty.get(j).get("dutyTimeDesc1")!="null"){
			    		String dutyTimeDesc1 = listDuty.get(j).get("dutyTimeDesc1");
			    		if(!"未登记".equals(dutyTimeDesc1)){
			    			if(!TeeUtility.isNullorEmpty(dutyTimeDesc1)){
			    				dutyTimeDesc1= dutyTimeDesc1.substring(0,5);
			    			}
			    		}
			    		dutyTimeDescNum1 += dutyTimeDesc1+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc2")!=null && listDuty.get(j).get("dutyTimeDesc2")!="" && listDuty.get(j).get("dutyTimeDesc2")!="null"){
			    		String dutyTimeDesc2 = listDuty.get(j).get("dutyTimeDesc2");
			    		if(!"未登记".equals(dutyTimeDesc2)){
			    			if(!TeeUtility.isNullorEmpty(dutyTimeDesc2)){
			    				dutyTimeDesc2 = dutyTimeDesc2.substring(0,5);
			    			}
			    		}
			    		dutyTimeDescNum2 += dutyTimeDesc2+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc3")!=null && listDuty.get(j).get("dutyTimeDesc3")!="" && listDuty.get(j).get("dutyTimeDesc3")!="null"){
			    		String dutyTimeDesc3 = listDuty.get(j).get("dutyTimeDesc3");
			    		if(!"未登记".equals(dutyTimeDesc3)){
			    			if(!TeeUtility.isNullorEmpty(dutyTimeDesc3)){
			    				dutyTimeDesc3 = dutyTimeDesc3.substring(0,5);
			    			}
			    		}
			    		dutyTimeDescNum3 += dutyTimeDesc3+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc4")!=null && listDuty.get(j).get("dutyTimeDesc4")!="" && listDuty.get(j).get("dutyTimeDesc4")!="null"){
			    		String dutyTimeDesc4 = listDuty.get(j).get("dutyTimeDesc4");
			    		if(!"未登记".equals(dutyTimeDesc4)){
			    			if(!TeeUtility.isNullorEmpty(dutyTimeDesc4)){
			    				dutyTimeDesc4 = dutyTimeDesc4.substring(0,5);
			    			}	
			    			
			    		}
			    		dutyTimeDescNum4 += dutyTimeDesc4+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc5")!=null && listDuty.get(j).get("dutyTimeDesc5")!="" && listDuty.get(j).get("dutyTimeDesc5")!="null"){
			    		String dutyTimeDesc5 = listDuty.get(j).get("dutyTimeDesc5");
			    		if(!"未登记".equals(dutyTimeDesc5)){
			    			if(!TeeUtility.isNullorEmpty(dutyTimeDesc5)){
			    				dutyTimeDesc5 = dutyTimeDesc5.substring(0,5);
			    			}	
			    		}
			    		dutyTimeDescNum5 += dutyTimeDesc5+","; 
			    	}
			    	if(listDuty.get(j).get("dutyTimeDesc6")!=null && listDuty.get(j).get("dutyTimeDesc6")!="" && listDuty.get(j).get("dutyTimeDesc6")!="null"){
			    		String dutyTimeDesc6 = listDuty.get(j).get("dutyTimeDesc6");
			    		if(!"未登记".equals(dutyTimeDesc6)){
			    			if(!TeeUtility.isNullorEmpty(dutyTimeDesc6)){
			    				dutyTimeDesc6 = dutyTimeDesc6.substring(0,5);
			    			}	
			    			
			    		}
			    		dutyTimeDescNum6 += dutyTimeDesc6+","; 
			    	}
			    }
			 map.put("dutyTimeDesc1", dutyTimeDescNum1);
			 map.put("dutyTimeDesc2", dutyTimeDescNum2);
			 map.put("dutyTimeDesc3", dutyTimeDescNum3);
			 map.put("dutyTimeDesc4", dutyTimeDescNum4);
			 map.put("dutyTimeDesc5", dutyTimeDescNum5);
			 map.put("dutyTimeDesc6", dutyTimeDescNum6);
			  // g++;
			
			
			list.add(map);
			
			if(pw!=null){
				pw.write("<p>已刷新“"+map.get("userName")+"”的考勤数据。</p>");
				pw.flush();
			}
			count++;
		} 
		json.setRtData(list);
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		
		if(pw!=null){
			pw.write("<p>刷新完毕！</p><br/>");
			pw.flush();
		}
		return json;
	}
	
	
	
	/**
	 * 获取申诉次数
	 * @param pTemp
	 * @param start
	 * @param end
	 * @return
	 */
	private int getComplainNum(TeePerson person, Date start, Date end) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取状态为审批通过 
		String hql=" from TeeDutyComplaint where  user.uuid="+person.getUuid();
		List<TeeDutyComplaint> list=simpleDaoSupport.find(hql, null);
		
		String startDesc=sdf.format(start)+" 00:00:00";
		String endDesc=sdf.format(end)+" 23:59:59";
		//开始统计时间
		Calendar countStart=TeeDateUtil.parseCalendar(startDesc);
		//结束统计时间
		Calendar countEnd=TeeDateUtil.parseCalendar(endDesc);
		
		int count=0;
		if(list!=null&&list.size()>0){
			for (TeeDutyComplaint teeDutyComplaint : list) {
				String remarkTimeStr=teeDutyComplaint.getRemarkTimeStr();
				Calendar remarkTime=TeeDateUtil.parseCalendar(remarkTimeStr+" 12:00:00");
			    if(remarkTime.after(countStart)&&remarkTime.before(countEnd)){
			    	count++;
			    }
			}
		}
		
		return count;
	}

	/**
	 * 获取外勤次数
	 * @param pTemp
	 * @param start
	 * @param end
	 * @return
	 */
	private int getAttendAssignNums(TeePerson pTemp, Date start, Date end) {
		long count=0;
		
		Calendar s=Calendar.getInstance();
		s.setTime(start);
		s.set(Calendar.HOUR_OF_DAY,0);
		s.set(Calendar.MINUTE,0);
		s.set(Calendar.SECOND,0);
		
		Calendar e=Calendar.getInstance();
		e.setTime(end);
		e.set(Calendar.HOUR_OF_DAY,23);
		e.set(Calendar.MINUTE,59);
		e.set(Calendar.SECOND,59);
		
		count=simpleDaoSupport.count(" select count(*) from TeeAttendAssign where user.uuid=?  and  createTime >=?  and createTime <= ? ", new Object[]{pTemp.getUuid(),s,e});
		
		return TeeStringUtil.getInteger(count,0);
	}

	
	/**
	 * 获取外勤天数
	 * @param pTemp
	 * @param start
	 * @param end
	 * @return
	 */
	private int getAttendAssignDays(TeePerson pTemp, Date start, Date end) {
        long count=0;
		String dialect=TeeSysProps.getDialect();
        
		Calendar s=Calendar.getInstance();
		s.setTime(start);
		s.set(Calendar.HOUR_OF_DAY,0);
		s.set(Calendar.MINUTE,0);
		s.set(Calendar.SECOND,0);
		
		Calendar e=Calendar.getInstance();
		e.setTime(end);
		e.set(Calendar.HOUR_OF_DAY,23);
		e.set(Calendar.MINUTE,59);
		e.set(Calendar.SECOND,59);
		
		List list=new ArrayList();
		list.add(pTemp.getUuid());
		list.add(s);
		list.add(e);
		count=count=simpleDaoSupport.countSQLByList("select count(1) from (select distinct "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"create_Time")+" from attend_assign where user_id=?  and  create_Time >=?  and create_Time <= ?) temp ", list);
		return TeeStringUtil.getInteger(count,0);
	}

	/**
	 * 查询导出时间范围内的上下班记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getExportDutyByCondition(int userId,String startDateDesc,String endDateDesc) throws Exception {
		//TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeePerson person =null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		int year = TeeStringUtil.getInteger(startDateDesc.substring(0,4),0);
		int month =TeeStringUtil.getInteger(startDateDesc.substring(5,7),0);
		
		if(userId>0){
			person = personDao.get(userId);
		}
		TeeJson json = new TeeJson();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month-1);
		int days = cl.getActualMaximum(Calendar.DATE);
		
		Calendar cl1 = Calendar.getInstance();
		cl1.set(Calendar.YEAR, year);
		cl1.set(Calendar.MONTH, month-1);
		cl1.set(Calendar.DAY_OF_MONTH,1);
		if(!TeeUtility.isNullorEmpty(startDateDesc) && !"null".equals(startDateDesc)){
			cl1.setTime(sf.parse(startDateDesc));
		}
		
		Calendar cl2 = Calendar.getInstance();
		cl2.set(Calendar.YEAR, year);
		cl2.set(Calendar.MONTH, month-1);
		cl2.set(Calendar.DAY_OF_MONTH,days);
		if(!TeeUtility.isNullorEmpty(endDateDesc) && !"null".equals(endDateDesc)){
			cl2.setTime(sf.parse(endDateDesc));
		}
		
		
		StringBuffer sb = new StringBuffer("[");
		if(cl1.get(Calendar.YEAR)<cl2.get(Calendar.YEAR)){
			String duty = null;
			int cha=cl2.get(Calendar.YEAR)-cl1.get(Calendar.YEAR);
			for(int p =1;p<=cha;p++){
				if(cl1.getActualMaximum(Calendar.DAY_OF_YEAR)-cl1.get(Calendar.DAY_OF_YEAR)>=0){
					int k =0;
					for(k=cl1.get(Calendar.DAY_OF_YEAR);k<=cl1.getActualMaximum(Calendar.DAY_OF_YEAR);k++){
					    duty = dutyDao.getDutyByCondition(k,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR));
					    sb.append(duty+",");
					}	
					if(k>cl1.getActualMaximum(Calendar.DAY_OF_YEAR)){
						cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+1);
						cl1.set(Calendar.MONTH,0);
						cl1.set(Calendar.DAY_OF_YEAR,1);
						for(int h=cl1.get(Calendar.DAY_OF_YEAR);h<=cl2.get(Calendar.DAY_OF_YEAR);h++){
							duty = dutyDao.getDutyByCondition(h,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR));
							sb.append(duty+",");
						}	
					}
				}
				
				cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+p-1);
				cl1.set(Calendar.MONTH,0);
				cl1.set(Calendar.DAY_OF_YEAR,2);
			}
		}else if(cl1.get(Calendar.YEAR)==cl2.get(Calendar.YEAR)){
			for(int i=cl1.get(Calendar.DAY_OF_YEAR);i<=cl2.get(Calendar.DAY_OF_YEAR);i++){
				String duty = dutyDao.getDutyByCondition(i,person.getUuid(),month-1,year);
				sb.append(duty+",");
			}
		
		}
		if(sb.length()>3){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		json.setRtData(TeeJsonUtil.JsonStr2MapList(sb.toString()));
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	
	
    /**
     * 考情数据刷新
     * @param request
     * @param response
     */
	public void refreshData(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			PrintWriter pw = response.getWriter();
			int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			
			TeeDepartment dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,deptId);
			
			String month = TeeStringUtil.getString(request.getParameter("month"));
			String startDateDesc=month+"-01";
			
			Calendar c=TeeDateUtil.parseCalendar(startDateDesc);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			
			String endDateDesc=sdf.format(c.getTime());
			
			TeeJson json=getRegisterRecordInfo(deptId, startDateDesc, endDateDesc,pw);
			List<Map> dataList=(List<Map>) json.getRtData();
			int userId=0;
			TeePerson user=null;
			TeeAttendDutyRecord record=null;
		    if(dataList!=null&&dataList.size()>0){
		    	for (Map map : dataList) {
		    		 userId=TeeStringUtil.getInteger(map.get("userId"), 0);
			    	 user=(TeePerson) simpleDaoSupport.get(TeePerson.class,userId);
		    		
		    		List<TeeAttendDutyRecord> recordList=simpleDaoSupport.executeQuery(" from TeeAttendDutyRecord where month=? and user.uuid=? ", new Object[]{month,userId});
		    		
		    		if(recordList!=null&&recordList.size()>0){
		    			record=recordList.get(0);
		    		}else{
		    			record=new TeeAttendDutyRecord();
		    		}
					
		    		record.setDept(dept);
		    		record.setEvectionDays(TeeStringUtil.getDouble(map.get("evectionDays"), 0.0));
		    		record.setHours(TeeStringUtil.getString(map.get("hours")));
		    		record.setLateNums(TeeStringUtil.getInteger(map.get("lateNums"), 0));
		    		record.setLeaveDays(TeeStringUtil.getDouble(map.get("leaveDays"), 0.0));
				    record.setLeaveEarlyNums(TeeStringUtil.getInteger(map.get("leaveEarlyNums"),0));
		    	    record.setMonth(month);
		    	    record.setOutDays(TeeStringUtil.getDouble(map.get("outDays"), 0.0));
		    	    record.setOverHours(TeeStringUtil.getDouble(map.get("overHours"), 0.0));
		    	    record.setPerfectCount(TeeStringUtil.getInteger(map.get("perfectCount"),0));
		    	    
		    	    record.setUser(user);
		    	    record.setWorkOnNoRegisters(TeeStringUtil.getInteger(map.get("workOnNoRegisters"), 0));
		    	    record.setWorkOutNoRegisters(TeeStringUtil.getInteger(map.get("workOutNoRegisters"),0));
		    	    
		    	    
		    	    
		    	    //外勤天数   外勤次数
		    	    record.setAttendAssignDays(TeeStringUtil.getInteger(map.get("attendAssignDays"), 0));
		    	    record.setAttendAssignNums(TeeStringUtil.getInteger(map.get("attendAssignNums"), 0));
		    	    
		    	    //申诉次数
		    	    record.setComplainNum(TeeStringUtil.getInteger(map.get("complainNum"), 0));
		    	    if(recordList!=null&&recordList.size()>0){
		    	    	//更新
		    			simpleDaoSupport.update(record);
		    			//System.out.println("更新");
		    		}else{
		    			//新增
		    			simpleDaoSupport.save(record);
		    			//System.out.println("新增");
		    		}
		    	}
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 上下班记录
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getPersonalDutyByCondition(HttpServletRequest request) throws Exception {
		//flag=0  代表是从个人考勤--上下班记录过来的，可以申诉和查看申诉
		//flag=1 代表是其他地方过来的，只能查看申诉  没有申诉的操作
		int flag=TeeStringUtil.getInteger(request.getParameter("flag"),0);
		
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int year = TeeStringUtil.getInteger(request.getParameter("year"), 0);
		int month = TeeStringUtil.getInteger(request.getParameter("month"), 0);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		
		if(userId>0){
			person = personDao.get(userId);
		}
		TeeJson json = new TeeJson();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month-1);
		int days = cl.getActualMaximum(Calendar.DATE);
        
		Calendar cl1 = Calendar.getInstance();
		cl1.set(Calendar.YEAR, year);
		cl1.set(Calendar.MONTH, month-1);
		cl1.set(Calendar.DAY_OF_MONTH,1);
		if(!TeeUtility.isNullorEmpty(startDateDesc) && !"null".equals(startDateDesc)){
			cl1.setTime(sf.parse(startDateDesc));
		}
		
		Calendar cl2 = Calendar.getInstance();
		cl2.set(Calendar.YEAR, year);
		cl2.set(Calendar.MONTH, month-1);
		cl2.set(Calendar.DAY_OF_MONTH,days);
		if(!TeeUtility.isNullorEmpty(endDateDesc) && !"null".equals(endDateDesc)){
			cl2.setTime(sf.parse(endDateDesc));
		}
		
		StringBuffer sb = new StringBuffer("[");
		if(cl1.get(Calendar.YEAR)<cl2.get(Calendar.YEAR)){
			String duty = null;
			int cha=cl2.get(Calendar.YEAR)-cl1.get(Calendar.YEAR);
			for(int p =1;p<=cha;p++){
				if(cl1.getActualMaximum(Calendar.DAY_OF_YEAR)-cl1.get(Calendar.DAY_OF_YEAR)>=0){
					int k =0;
					for(k=cl1.get(Calendar.DAY_OF_YEAR);k<=cl1.getActualMaximum(Calendar.DAY_OF_YEAR);k++){
					    duty = dutyDao.getPersonalDutyByCondition(k,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR),flag);
						sb.append(duty+",");
					}	
					if(k>cl1.getActualMaximum(Calendar.DAY_OF_YEAR)){
						cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+1);
						cl1.set(Calendar.MONTH,0);
						cl1.set(Calendar.DAY_OF_YEAR,1);
						for(int h=cl1.get(Calendar.DAY_OF_YEAR);h<=cl2.get(Calendar.DAY_OF_YEAR);h++){
							duty = dutyDao.getPersonalDutyByCondition(h,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR),flag);
							sb.append(duty+",");
						}	
					}
				}
				
				cl1.set(Calendar.YEAR, cl1.get(Calendar.YEAR)+p-1);
				cl1.set(Calendar.MONTH,0);
				cl1.set(Calendar.DAY_OF_YEAR,2);
			}
		}else if(cl1.get(Calendar.YEAR)==cl2.get(Calendar.YEAR)){
			for(int i=cl1.get(Calendar.DAY_OF_YEAR);i<=cl2.get(Calendar.DAY_OF_YEAR);i++){
				String duty = dutyDao.getPersonalDutyByCondition(i,person.getUuid(),cl1.get(Calendar.MONTH),cl1.get(Calendar.YEAR),flag);
				sb.append(duty+",");
			}
		
		}
		if(sb.length()>3){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		json.setRtData(TeeJsonUtil.JsonStr2MapList(sb.toString()));
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	
	
}