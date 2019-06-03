package com.tianee.oa.core.base.attend.dao;


import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.bean.TeeAttendDuty;
import com.tianee.oa.core.base.attend.bean.TeeAttendEvection;
import com.tianee.oa.core.base.attend.bean.TeeAttendHoliday;
import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.bean.TeeAttendOvertime;
import com.tianee.oa.core.base.attend.model.TeeAttendDutyModel;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Repository("dutyDao")
public class TeeAttendDutyDao  extends TeeBaseDao<TeeAttendDuty> {
	
	@Autowired
	private TeeAttendConfigDao attendConfigDao;
	
	
	@Autowired
	TeeSysParaDao sysParaDao;
	
	@Autowired
	TeeDutyComplaintDao dutyComplaintDao;//考情申诉
	
	@Autowired
	private TeeAttendHolidayDao holidayDao;
	
	@Autowired
	TeeAttendEvectionDao evectionDao;
	
	
	@Autowired
	TeeAttendOutDao outDao;
	
	@Autowired
	TeeAttendLeaveDao leaveDao;
	
	@Autowired
	TeeAttendOvertimeDao overTimeDao;
	
	@Autowired
	TeePersonDao personDao;
	
	@Autowired
	TeeDeptDao deptDao;
	
	@Autowired
	@Qualifier("attendSeniorConfigDao")
	TeeAttendSeniorConfigDao   seniorConfigDao;
	
	public void addDuty(TeeAttendDuty duty) {
		save(duty);
	}
	
	
	public void updateDuty(TeeAttendDuty duty) {
		update(duty);
	}

	public TeeAttendDuty loadById(int id) {
		TeeAttendDuty intf = load(id);
		return intf;
	}
	
	public TeeAttendDuty getById(int id) {
		TeeAttendDuty intf = get(id);
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
			String hql = "delete from TeeAttendDuty where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	

	public  List<TeeAttendDuty> getdutyList() {
		String hql = "from TeeAttendDuty order by  sid asc";
		List<TeeAttendDuty> list = (List<TeeAttendDuty>) executeQuery(hql,null);
		return list;
	}	
	
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		String hql = "from TeeAttendDuty oc where 1=1 ";
		hql+=" order by oc.sid asc";
		long total = count("select count(*) "+hql, null);
		List rows = new ArrayList();
		List<TeeAttendDuty> list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeAttendDuty duty:list){
			TeeAttendDutyModel model = new TeeAttendDutyModel();
			BeanUtils.copyProperties(duty, model);
			if(!TeeUtility.isNullorEmpty(duty.getRegisterTime())){
				
				model.setRegisterTimeDesc(sf.format(duty.getRegisterTime().getTime()));
			}
			rows.add(model);
		}
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	
	public  List<TeeAttendDuty> getDutyList(int userId,int dutyId,String on) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cl1 =Calendar.getInstance();
		Calendar cl2 =Calendar.getInstance();
		String curDate = sf.format(cl1.getTime());
		cl1.setTime(sdf.parse(curDate+" 00:00:00"));
		cl2.setTime(sdf.parse(curDate+" 23:59:59"));
		List values = new ArrayList();
		values.add(cl1);
		values.add(cl2);
		String hql = "from TeeAttendDuty d where d.userId ="+userId+" and d.registerType="+dutyId+" and d.registerTime>? and d.registerTime<? and d.dutyType="+on+" order by  sid asc";
		List<TeeAttendDuty> list = (List<TeeAttendDuty>) executeQueryByList(hql,values);
		return list;
	}

	/**
	 * 获取当前用户排班类型下，每一天的登记记录
	 * @param i
	 * @param uuid
	 * @param dutyType
	 * @param month
	 * @param year
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public String getDutyByCondition(int i, int uuid,int month, int year) throws  Exception {
		TeePerson person=personDao.get(uuid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month);
		cl.set(Calendar.DAY_OF_YEAR,i);
		List<TeeAttendHoliday> holidayList = holidayDao.getHolidayList();
		
		String dateStr=sf.format(cl.getTime());
 		TeeAttendConfig config = seniorConfigDao.getAttendConfigByUserAndDate(person, dateStr);
		
		if(config==null){//没有排班
			return "{dutyDate:\""+dateStr+"\",week:"+cl.get(Calendar.DAY_OF_WEEK)+",dutyId:\"\",isGeneral:\"false\",isHoliday:\"false\"}";
		}else{
			if(config.getSid()==0){//休息日
				return "{dutyDate:\""+dateStr+"\",week:"+cl.get(Calendar.DAY_OF_WEEK)+",dutyId:\"0\",isGeneral:\"true\",isHoliday:\"false\"}";
			}else{//正常排班
				sb.append("{dutyDate:\""+sf.format(cl.getTime())+"\",week:"+cl.get(Calendar.DAY_OF_WEEK)+",dutyId:"+config.getSid()+",isGeneral:\""+isGeneral(config.getSid())+"\",");
				sb.append("isHoliday:\""+isHoliday(i,month,year,holidayList)+"\",");
				sb.append("isNoRegisterUserIds:\""+isNoRegisterUserIds(uuid)+"\",");
				if(config.getDutyTime1()!=null){
					sb.append("dutyTimeDesc1:\""+getRegisterTime(uuid,config.getSid(),cl,config.getDutyTime1(),config.getDutyType1(),1)+"\",");
				    sb.append("isLeave1:\""+isLeave(uuid,cl,config.getDutyTime1())+"\",");
				    sb.append("isEvection1:\""+isEvection(uuid,cl,config.getDutyTime1())+"\",");
				    sb.append("isOut1:\""+isOut(uuid,cl,config.getDutyTime1())+"\",");
				}else{
					sb.append("dutyTimeDesc1:\"\",");
				    sb.append("isLeave1:\"false\",");
				    sb.append("isEvection1:\"false\",");
				    sb.append("isOut1:\"false\",");
				}
				if(config.getDutyTime2()!=null){
					sb.append("dutyTimeDesc2:\""+getRegisterTime(uuid,config.getSid(),cl,config.getDutyTime2(),config.getDutyType2(),2)+"\",");
					sb.append("isLeave2:\""+isLeave(uuid,cl,config.getDutyTime2())+"\",");
					sb.append("isEvection2:\""+isEvection(uuid,cl,config.getDutyTime2())+"\",");
					sb.append("isOut2:\""+isOut(uuid,cl,config.getDutyTime2())+"\",");
				}else{
					sb.append("dutyTimeDesc2:\"\",");
				    sb.append("isLeave2:\"false\",");
				    sb.append("isEvection2:\"false\",");
				    sb.append("isOut2:\"false\",");
				}
				if(config.getDutyTime3()!=null){
					sb.append("dutyTimeDesc3:\""+getRegisterTime(uuid,config.getSid(),cl,config.getDutyTime3(),config.getDutyType3(),3)+"\",");
					sb.append("isLeave3:\""+isLeave(uuid,cl,config.getDutyTime3())+"\",");
					sb.append("isEvection3:\""+isEvection(uuid,cl,config.getDutyTime3())+"\",");
					sb.append("isOut3:\""+isOut(uuid,cl,config.getDutyTime3())+"\",");
				}else{
					sb.append("dutyTimeDesc3:\"\",");
				    sb.append("isLeave3:\"false\",");
				    sb.append("isEvection3:\"false\",");
				    sb.append("isOut3:\"false\",");
				}
				if(config.getDutyTime4()!=null){
					sb.append("dutyTimeDesc4:\""+getRegisterTime(uuid,config.getSid(),cl,config.getDutyTime4(),config.getDutyType4(),4)+"\",");
					sb.append("isLeave4:\""+isLeave(uuid,cl,config.getDutyTime4())+"\",");
					sb.append("isEvection4:\""+isEvection(uuid,cl,config.getDutyTime4())+"\",");
					sb.append("isOut4:\""+isOut(uuid,cl,config.getDutyTime4())+"\",");
				}else{
					sb.append("dutyTimeDesc4:\"\",");
				    sb.append("isLeave4:\"false\",");
				    sb.append("isEvection4:\"false\",");
				    sb.append("isOut4:\"false\",");
				}
				if(config.getDutyTime5()!=null){
					sb.append("dutyTimeDesc5:\""+getRegisterTime(uuid,config.getSid(),cl,config.getDutyTime5(),config.getDutyType5(),5)+"\",");
					sb.append("isLeave5:\""+isLeave(uuid,cl,config.getDutyTime5())+"\",");
					sb.append("isEvection5:\""+isEvection(uuid,cl,config.getDutyTime5())+"\",");
					sb.append("isOut5:\""+isOut(uuid,cl,config.getDutyTime5())+"\",");
				}else{
					sb.append("dutyTimeDesc5:\"\",");
				    sb.append("isLeave5:\"false\",");
				    sb.append("isEvection5:\"false\",");
				    sb.append("isOut5:\"false\",");
				}
				if(config.getDutyTime6()!=null){
					sb.append("dutyTimeDesc6:\""+getRegisterTime(uuid,config.getSid(),cl,config.getDutyTime6(),config.getDutyType6(),6)+"\",");
					sb.append("isLeave6:\""+isLeave(uuid,cl,config.getDutyTime6())+"\",");
					sb.append("isEvection6:\""+isEvection(uuid,cl,config.getDutyTime6())+"\",");
					sb.append("isOut6:\""+isOut(uuid,cl,config.getDutyTime6())+"\",");
				}else{
					sb.append("dutyTimeDesc6:\"\",");
				    sb.append("isLeave6:\"false\",");
				    sb.append("isEvection6:\"false\",");
				    sb.append("isOut6:\"false\",");
				}
				sb=sb.deleteCharAt(sb.length()-1);
				sb.append("}");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param userId
	 * @param dutyId
	 * @param cl 要查询的那一天的日期
	 * @param cl1 排班类型每次登记规定的时间
	 * @param type 上班 还是下班  num代表第几次签到
	 * @return
	 * @throws Exception
	 */
	public  String getRegisterTime(int userId,int dutyId,Calendar cl,Calendar cl1,char type,int num) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cl2 =Calendar.getInstance();
		String curDate = sf.format(cl.getTime());
		cl.setTime(sdf.parse(curDate+" 00:00"));
		cl2.setTime(sdf.parse(curDate+" 23:59"));
		List values = new ArrayList();
		values.add(cl);
		values.add(cl2);
		String hql = "from TeeAttendDuty d where d.userId ="+userId+" and d.registerType="+dutyId+" and d.registerTime>? and d.registerTime<? and dutyType="+num+" order by  sid asc";
		List<TeeAttendDuty> list = (List<TeeAttendDuty>) executeQueryByList(hql,values);
		String workonBeforeMin="";
		String workonAfterMin="";
		String workoutBeforeMin="";
		String workoutAfterMin="";
		String dutyTime = sf.format(cl.getTime())+" "+sdf.format(cl1.getTime()).substring(10,16);//规定登记时间
		Calendar ruleTime = Calendar.getInstance();
		ruleTime.setTime(sdf.parse(dutyTime));
		String flag="";
		String posRender = null;
		String contextPath = TeeSysProps.getString("contextPath");
		if(type=='0'){//上班登记
			if(sysParaDao.getSysPara("WORKON_BEFORE_MIN")!=null){
				workonBeforeMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_BEFORE_MIN").getParaValue(),"0");
			}
			if(sysParaDao.getSysPara("WORKON_AFTER_MIN")!=null){
				workonAfterMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_AFTER_MIN").getParaValue(),"0");
			}
			Calendar cl3 = Calendar.getInstance();//上班提前登记时间
			cl3.setTime(sdf.parse(dutyTime));
			cl3.add(Calendar.MINUTE, -Integer.parseInt(workonBeforeMin));
			
			Calendar cl4 = Calendar.getInstance();//上班延后登记时间
			cl4.setTime(sdf.parse(dutyTime));
			cl4.add(Calendar.MINUTE, Integer.parseInt(workonAfterMin));
			
			for(TeeAttendDuty duty:list){
				Calendar registerTime = duty.getRegisterTime();
				if(registerTime.after(cl3) && registerTime.before(cl4) && (duty.getDutyType()==1 || duty.getDutyType()==3 || duty.getDutyType()==5)){
					if(registerTime.after(ruleTime)){
						flag="<span style='color:red'>&nbsp;&nbsp;&nbsp;迟到</span>";
					}
					if(!TeeUtility.isNullorEmpty(duty.getPhoneModel())){//手机型号
						posRender="<br/>"+duty.getPhoneModel();
					}else{
						posRender="";
					}
					if(!TeeUtility.isNullorEmpty(duty.getRemark())){
						if(!TeeUtility.isNullorEmpty(duty.getPosition())){
							posRender += "<br/><span class=\\\"remark\\\" onclick=\\\"openFullWindow('"+contextPath+"/system/core/base/attend/duty/position.jsp?pos="+duty.getPosition()+ "&address=" +  URLEncoder.encode(duty.getRemark(),"UTF-8")+ "')\\\">"+duty.getRemark()+"</span>";
						}else{
							posRender += "<br/>"+ duty.getRemark();
						}
					}else{
						posRender+="";
					}
					
					
					return sf2.format(registerTime.getTime())+flag+posRender;
				}
			}
			
		}else{//下班登记
			if(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN")!=null){
				workoutBeforeMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN").getParaValue(),"0");
			}
			if(sysParaDao.getSysPara("WORKOUT_AFTER_MIN")!=null){
				workoutAfterMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_AFTER_MIN").getParaValue(),"0");
			}
			
			Calendar cl3 = Calendar.getInstance();//下班提前登记时间
			cl3.setTime(sdf.parse(dutyTime));
			cl3.add(Calendar.MINUTE, -Integer.parseInt(workoutBeforeMin));
			
			Calendar cl4 = Calendar.getInstance();//下班延后登记时间
			cl4.setTime(sdf.parse(dutyTime));
			cl4.add(Calendar.MINUTE, Integer.parseInt(workoutAfterMin));
			for(TeeAttendDuty duty:list){
				Calendar registerTime = duty.getRegisterTime();
				if(registerTime.after(cl3) && registerTime.before(cl4) && (duty.getDutyType()==2 || duty.getDutyType()==4 || duty.getDutyType()==6)){
					if(registerTime.before(ruleTime)){
						flag="<span style='color:red'>&nbsp;&nbsp;&nbsp;早退</span>";
					}
					if(!TeeUtility.isNullorEmpty(duty.getPhoneModel())){//手机型号
						posRender="<br/>"+duty.getPhoneModel();
					}else{
						posRender="";
					}
					if(!TeeUtility.isNullorEmpty(duty.getRemark())){
						if(!TeeUtility.isNullorEmpty(duty.getPosition())){
							posRender += "<br/><span class=\\\"remark\\\" onclick=\\\"openFullWindow('"+contextPath+"/system/core/base/attend/duty/position.jsp?pos="+duty.getPosition()+"&address=" +  URLEncoder.encode(duty.getRemark(),"UTF-8")+"')\\\">"+duty.getRemark()+"</span>";
						}else{
							posRender += "<br/>"+ duty.getRemark();
						}
					}else{
						posRender+="";
					}
					
					return sf2.format(registerTime.getTime())+flag+posRender;
				}
			}
		}
		return "未登记";
	}
	
	
	
	
	/**
	 * 判断当前日期是不是公休日
	 * @return
	 */
	public boolean isGeneral(int dutyId){
		if(dutyId==0){
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
	 * 判断当前日期是不是免签节日
	 * @return
	 */
	public boolean isHoliday(int i,int month, int year,List<TeeAttendHoliday> holidayList){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month);
		cl.set(Calendar.DAY_OF_YEAR,i);
		String curDateDesc = sf.format(cl.getTime());
		if(holidayList.size()>0){
			for(TeeAttendHoliday holiday:holidayList){
				if(holiday!=null){
					Calendar startDate = holiday.getStartTime();
					Calendar endDate = holiday.getEndTime();
					String startDateDesc = sf.format(startDate.getTime());
					String endDateDesc = sf.format(endDate.getTime());
					if((cl.after(startDate) && cl.before(endDate)) || curDateDesc.equals(startDateDesc) || curDateDesc.equals(endDateDesc)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 当前用户是否为免签人
	 * @param userId
	 * @return
	 */
	public boolean isNoRegisterUserIds(int userId){
		String noRegisterUserIds="";//免签用户Id
		if(sysParaDao.getSysPara("NO_REGISTER_USERIDS")!=null){
			noRegisterUserIds=TeeStringUtil.getString(sysParaDao.getSysPara("NO_REGISTER_USERIDS").getParaValue(),"");
			if(!TeeUtility.isNullorEmpty(noRegisterUserIds)){
				String[] ids=noRegisterUserIds.split(",");
				for(int i=0;i<ids.length;i++){
					if(Integer.parseInt(ids[i])==userId){
						return true;
					}
				}
			}
		}
		return false;
	}


	public void delAttendDutyData(String userIds, String startDateDesc,String endDateDesc) throws Exception {
		List param =new  ArrayList();
		String hql="from  TeeAttendDuty duty where 1=1";
		if(userIds.length()>0){
			hql+=" and duty.userId in ("+userIds+")";
//			param.add(userIds);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));
			hql+=" and duty.registerTime>=? ";
			param.add(cl1);
			
			
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
			hql+=" and duty.registerTime<=? ";
			param.add(cl2);
		}
		List<TeeAttendDuty> list = executeQueryByList(hql, param);
		if(list.size()>0){
			for(TeeAttendDuty duty:list){
				delById(duty.getSid());
			}
		}
	}


	public void delAttendLeveData(String userIds, String startDateDesc,
			String endDateDesc) throws Exception {
		List param =new  ArrayList();
		String hql="from TeeAttendLeave leave where 1=1 and leave.allow=1";
		if(userIds.length()>0){
			hql+=" and leave.user.uuid in ("+userIds+")";
			//param.add(userIds);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));
			hql+=" and leave.startTime>=? ";
			param.add(cl1.getTimeInMillis());
			
			
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
			hql+=" and leave.endTime<=? ";
			param.add(cl2.getTimeInMillis());
		}
		List<TeeAttendLeave> list =leaveDao.executeQueryByList(hql, param);
		if(list.size()>0){
			for(TeeAttendLeave leave:list){
				leaveDao.delById(leave.getSid());
			}
		}
		
	}


	public void delAttendOutData(String userIds, String startDateDesc,
			String endDateDesc) throws Exception {
		List param =new  ArrayList();
		String hql="from TeeAttendOut out where 1=1 and out.allow=1";
		if(userIds.length()>0){
			hql+=" and out.user.uuid in ("+userIds+")";
			//param.add(userIds);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));
			hql+=" and out.startTime>=? ";
			param.add(cl1.getTimeInMillis());
			
			
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
			hql+=" and out.endTime<=? ";
			param.add(cl2.getTimeInMillis());
		}
		List<TeeAttendOut> list =outDao.executeQueryByList(hql, param);
		if(list.size()>0){
			for(TeeAttendOut out:list){
				outDao.delById(out.getSid());
			}
		}
	}


	public void delAttendOverTimeData(String userIds, String startDateDesc,
			String endDateDesc) throws Exception {
		List param =new  ArrayList();
		String hql="from TeeAttendOvertime overtime where 1=1 and overtime.allow=1";
		if(userIds.length()>0){
			hql+=" and overtime.user.uuid in ("+userIds+")";
			//param.add(userIds);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));
			hql+=" and overtime.startTime>=? ";
			param.add(cl1.getTimeInMillis());
			
			
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
			hql+=" and overtime.endTime<=? ";
			param.add(cl2.getTimeInMillis());
		}
		List<TeeAttendOvertime> list =overTimeDao.executeQueryByList(hql, param);
		if(list.size()>0){
			for(TeeAttendOvertime overtime:list){
				overTimeDao.delById(overtime.getSid());
			}
		}
	}


	public void delAttendEvectionData(String userIds, String startDateDesc,
			String endDateDesc) throws Exception {
		List param =new  ArrayList();
		String hql="from TeeAttendEvection evection where 1=1 and evection.allow=1";
		if(userIds.length()>0){
			hql+=" and evection.user.uuid in ("+userIds+")";
//			param.add(userIds);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc+" 00:00:00"));
			hql+=" and evection.startTime>=? ";
			param.add(cl1.getTimeInMillis());
			
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc+" 23:59:59"));
			hql+=" and evection.endTime<=? ";
			param.add(cl2.getTimeInMillis());
		}
		List<TeeAttendEvection> list =evectionDao.executeQueryByList(hql, param);
		if(list.size()>0){
			for(TeeAttendEvection evection:list){
				overTimeDao.delById(evection.getSid());
			}
		}
	}


	/**
	 * 获取上下班统计结果
	 * @param deptId
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws Exception 
	 */
	public String getRegisterRecord(int deptId, String startDateDesc,String endDateDesc) throws Exception {
		StringBuffer sb =new StringBuffer("[");
		List<TeePerson> userList = personDao.getAllUserNoDelete();
		List<TeeAttendHoliday> holidayList = holidayDao.getHolidayList();
		if(deptId>0){
			TeeDepartment dept = deptDao.get(deptId);
			userList = personDao.selectDeptAndChildDeptPerson(deptId, dept.getDeptParentLevel());
		}
		if(userList.size()>0){
			for(TeePerson user:userList){
				if(isNoRegisterUserIds(user.getUuid())){
					continue;
				}
				int workOnNoRegisters = getWorkOnNoRegisters(user.getUuid(),startDateDesc,endDateDesc,holidayList)[0];
				int lateNums = getWorkOnNoRegisters(user.getUuid(),startDateDesc,endDateDesc,holidayList)[1];
				int workOutNoRegisters = getWorkOutNoRegisters(user.getUuid(),startDateDesc,endDateDesc,holidayList)[0];
				int leaveEarlyNums = getWorkOutNoRegisters(user.getUuid(),startDateDesc,endDateDesc,holidayList)[1];
				long hours = getWorkhours(user.getUuid(),startDateDesc,endDateDesc);
				int allInDays = 0;//全勤天数
				sb.append("{userId:"+user.getUuid()+",deptName:\""+user.getDept().getDeptName()+"\",userName:\""+user.getUserName()+"\",workOnNoRegisters:"+workOnNoRegisters+",");
				sb.append("lateNums:"+lateNums+",");
				sb.append("leaveEarlyNums:"+leaveEarlyNums+",");
				sb.append("hours:"+hours+",");
				sb.append("workOutNoRegisters:"+workOutNoRegisters+"},");
				
			}
		}
		if(sb.length()>3){
			sb=sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	
	/**
	 * @author ny
	 * 获取上班时长
	 */
	public long getWorkhours(int userId, String startDateDesc,String endDateDesc ){
		TeePerson user = personDao.get(userId);
		long times = 0;
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
			Calendar startTime = Calendar.getInstance();
			startTime.setTime(sf.parse(startDateDesc+" 00:00:00"));
			Calendar endTime = Calendar.getInstance();
			endTime.setTime(sf.parse(endDateDesc+" 23:59:59"));
			String hql ="from TeeAttendDuty duty where duty.registerType='"+user.getDutyType()+"' and duty.registerTime>? and duty.registerTime<? and duty.userId="+userId;
			List param = new ArrayList();
			param.add(startTime);
			param.add(endTime);
			List<TeeAttendDuty> list = executeQueryByList(hql, param);
			if(list.size()>0){
				for(TeeAttendDuty duty :list){
					times += duty.getHours();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return times;
	}
	
	
	/**
	 * 获取上班未登记数 及 迟到次数
	 * @param userId
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws Exception
	 */
	public int[] getWorkOnNoRegisters(int userId, String startDateDesc,String endDateDesc,List<TeeAttendHoliday> holidayList) throws Exception{
		int[] nums={0,0};
		int workOnNoRegisters=0;
		int lateNums=0;
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		TeePerson user = personDao.get(userId);
		TeeAttendConfig config = attendConfigDao.getById(Integer.parseInt(user.getDutyType()));
		Calendar cl1 = Calendar.getInstance();
		cl1.setTime(sf1.parse(startDateDesc));
		Calendar cl2 = Calendar.getInstance();
		cl2.setTime(sf1.parse(endDateDesc));
		for(int i=cl1.get(Calendar.DAY_OF_YEAR);i<=cl2.get(Calendar.DAY_OF_YEAR);i++){
			Calendar cl = Calendar.getInstance();
			cl.set(Calendar.DAY_OF_YEAR, i);
			//公休日上班登记未登陆不计入上班未登录总数中
			if(isGeneral(Integer.parseInt(user.getDutyType()))){
				continue;
			}
			//节假日上班登记未登陆不计入上班未登录总数中
			if(isHoliday(cl.get(Calendar.DAY_OF_YEAR),cl.get(Calendar.MONTH),cl.get(Calendar.YEAR),holidayList)){
				continue;
			}
			if(config.getDutyTime1()!=null && config.getDutyType1()=='0'){
				String registerTime = getRegisterTime(userId,Integer.parseInt(user.getDutyType()),cl,config.getDutyTime1(),config.getDutyType1(),1);
				if(registerTime.equals("未登记")){
					workOnNoRegisters++;
				}
				if(registerTime.endsWith("</span>")){
					lateNums++;
				}
			}
			if(config.getDutyTime3()!=null && config.getDutyType3()=='0'){
				String registerTime = getRegisterTime(userId,Integer.parseInt(user.getDutyType()),cl,config.getDutyTime3(),config.getDutyType3(),3);
				if(registerTime.equals("未登记")){
					workOnNoRegisters++;
				}
				if(registerTime.endsWith("</span>")){
					lateNums++;
				}
			}
			if(config.getDutyTime5()!=null && config.getDutyType5()=='0'){
				String registerTime = getRegisterTime(userId,Integer.parseInt(user.getDutyType()),cl,config.getDutyTime5(),config.getDutyType5(),5);
				if(registerTime.equals("未登记")){
					workOnNoRegisters++;
				}
				if(registerTime.endsWith("</span>")){
					lateNums++;
				}
			}
		}
		nums[0]=workOnNoRegisters;
		nums[1]=lateNums;
		return nums;
	}
	
	
	/**
	 * 获取下班未登记数 和 早退次数
	 * @param userId
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws Exception
	 */
	public int[] getWorkOutNoRegisters(int userId, String startDateDesc,String endDateDesc,List<TeeAttendHoliday> holidayList) throws Exception{
		int[] nums={0,0};
		int workOutNoRegisters=0;
		int leaveEarlyNums=0;
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		TeePerson user = personDao.get(userId);
		TeeAttendConfig config = attendConfigDao.getById(Integer.parseInt(user.getDutyType()));
		Calendar cl1 = Calendar.getInstance();
		cl1.setTime(sf1.parse(startDateDesc));
		Calendar cl2 = Calendar.getInstance();
		cl2.setTime(sf1.parse(endDateDesc));
		for(int i=cl1.get(Calendar.DAY_OF_YEAR);i<=cl2.get(Calendar.DAY_OF_YEAR);i++){
			Calendar cl = Calendar.getInstance();
			cl.set(Calendar.DAY_OF_YEAR, i);
			//公休日上班登记未登陆不计入上班未登录总数中
			if(isGeneral(Integer.parseInt(user.getDutyType()))){
				continue;
			}
			//节假日上班登记未登陆不计入上班未登录总数中
			if(isHoliday(cl.get(Calendar.DAY_OF_YEAR),cl.get(Calendar.MONTH),cl.get(Calendar.YEAR),holidayList)){
				continue;
			}
			if(config.getDutyTime2()!=null && config.getDutyType2()=='1'){
				String registerTime = getRegisterTime(userId,Integer.parseInt(user.getDutyType()),cl,config.getDutyTime2(),config.getDutyType2(),2);
				if(registerTime.equals("未登记")){
					workOutNoRegisters++;
				}
				if(registerTime.endsWith("</span>")){
					leaveEarlyNums++;
				}
			}
			if(config.getDutyTime4()!=null && config.getDutyType4()=='1'){
				String registerTime = getRegisterTime(userId,Integer.parseInt(user.getDutyType()),cl,config.getDutyTime4(),config.getDutyType4(),4);
				if(registerTime.equals("未登记")){
					workOutNoRegisters++;
				}
				if(registerTime.endsWith("</span>")){
					leaveEarlyNums++;
				}
			}
			if(config.getDutyTime6()!=null && config.getDutyType6()=='1'){
				String registerTime = getRegisterTime(userId,Integer.parseInt(user.getDutyType()),cl,config.getDutyTime6(),config.getDutyType6(),6);
				if(registerTime.equals("未登记")){
					workOutNoRegisters++;
				}
				if(registerTime.endsWith("</span>")){
					leaveEarlyNums++;
				}
			}
		}
		nums[0]=workOutNoRegisters;
		nums[1]=leaveEarlyNums;
		return  nums;
	}
	
	/**
	 * 是否请假
	 * @param userId
	 * @param cl
	 * @return
	 */
	public boolean isLeave(int userId,Calendar cl,Calendar dutyTime){
		boolean flag = false;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			cl.set(Calendar.HOUR_OF_DAY, dutyTime.get(Calendar.HOUR_OF_DAY));
			cl.set(Calendar.MINUTE, dutyTime.get(Calendar.MINUTE));
			cl.set(Calendar.SECOND, dutyTime.get(Calendar.SECOND));
			
			
			String hql=" from TeeAttendLeave leave where leave.user.uuid="+userId+" and leave.startTime<=? and leave.endTime>=? and leave.allow=1";
			Object[] param = {cl.getTimeInMillis(),cl.getTimeInMillis()};
			List<TeeAttendLeave> list = leaveDao.executeQuery(hql,param);
			if(list.size()>0){
				flag = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}
	/**
	 * 是否出差
	 * @param userId
	 * @param cl
	 * @return
	 */
	public boolean isEvection(int userId,Calendar cl,Calendar dutyTime){
		boolean flag = false;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			cl=TeeDateUtil.parseCalendar("yyyy-MM-dd",format.format(cl.getTime()));
			
			
			String hql=" from TeeAttendEvection evection where evection.user.uuid="+userId+" and evection.startTime<=? and evection.endTime>=? and evection.allow=1";
			Object[] param = {cl.getTimeInMillis() , cl.getTimeInMillis()};
			List<TeeAttendEvection> list = evectionDao.executeQuery(hql,param);
			if(list.size()>0){
				flag = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 是否外出
	 * @param userId
	 * @param cl
	 * @return
	 */
	public boolean isOut(int userId,Calendar cl,Calendar dutyTime){
		boolean flag = false;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			cl.set(Calendar.HOUR_OF_DAY, dutyTime.get(Calendar.HOUR_OF_DAY));
			cl.set(Calendar.MINUTE, dutyTime.get(Calendar.MINUTE));
			cl.set(Calendar.SECOND, dutyTime.get(Calendar.SECOND));
			
			
			String hql=" from TeeAttendOut out where out.user.uuid="+userId+" and out.startTime<=? and out.endTime>=? and out.allow=1";
			Object[] param = {cl.getTimeInMillis() , cl.getTimeInMillis()};
			List<TeeAttendOut> list = outDao.executeQuery(hql,param);
			if(list.size()>0){
				flag = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 获取一日中登记的记录  by 用户
	 * @author syl
	 * @date 2014-6-23
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeAttendDuty> getOneDayAttendDuty(String dateStr , TeePerson person) throws ParseException{

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date  = format.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//一天最大 2014-2-2 23:59:59
		Calendar maxCal = Calendar.getInstance();
		maxCal.setTime(sf.parse(dateStr+" 23:59:59"));
	
		//一天最小 2014-2-2 0:0:0
		Calendar minCal = Calendar.getInstance();
		minCal.setTime(sf.parse(dateStr+" 00:00:00"));
		String hql = "from TeeAttendDuty  duty where duty.userId = " + person.getUuid() + " and duty.registerTime >= ? and duty.registerTime <=? order by duty.registerTime";
		Object[] values = {minCal, maxCal};
		List<TeeAttendDuty> list  = executeQuery(hql, values);
		return list;
	}
	
	
	
	/**
	 * 获取当前用户当天签到数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年3月18日
	 * @param userId
	 * @return
	 * @throws Exception List<TeeAttendDuty>
	 */
	public  List<TeeAttendDuty> getDutyList(int userId) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cl1 =Calendar.getInstance();
		Calendar cl2 =Calendar.getInstance();
		String curDate = sf.format(cl1.getTime());
		cl1.setTime(sdf.parse(curDate+" 00:00:00"));
		cl2.setTime(sdf.parse(curDate+" 23:59:59"));
		List values = new ArrayList();
		values.add(cl1);
		values.add(cl2);
		String hql = "from TeeAttendDuty d where d.userId ="+userId+" and d.registerTime>=? and d.registerTime<=?  order by  sid asc";
		List<TeeAttendDuty> list = (List<TeeAttendDuty>) executeQueryByList(hql,values);
		return list;
	}


	/**
	 * 个人考勤 --- 上下班记录
	 * @param h
	 * @param uuid
	 * @param i
	 * @param j
	 * @return
	 * @throws Exception 
	 */
	public String getPersonalDutyByCondition(int i, int uuid,int month, int year,int flag) throws Exception {
		//flag=0  代表是从个人考勤--上下班记录过来的，可以申诉和查看申诉
		//flag=1 代表是其他地方过来的，只能查看申诉  没有申诉的操作
		TeePerson person=personDao.get(uuid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month);
		cl.set(Calendar.DAY_OF_YEAR,i);
		List<TeeAttendHoliday> holidayList = holidayDao.getHolidayList();
		
		String dateStr=sf.format(cl.getTime());
 		TeeAttendConfig config = seniorConfigDao.getAttendConfigByUserAndDate(person, dateStr);
		
		if(config==null){//没有排班
			return "{dutyDate:\""+dateStr+"\",week:"+cl.get(Calendar.DAY_OF_WEEK)+",dutyId:\"\",isGeneral:\"false\",isHoliday:\"false\"}";
		}else{
			if(config.getSid()==0){//休息日
				return "{dutyDate:\""+dateStr+"\",week:"+cl.get(Calendar.DAY_OF_WEEK)+",dutyId:\"0\",isGeneral:\"true\",isHoliday:\"false\"}";
			}else{//正常排班
				sb.append("{dutyDate:\""+sf.format(cl.getTime())+"\",week:"+cl.get(Calendar.DAY_OF_WEEK)+",dutyId:"+config.getSid()+",isGeneral:\""+isGeneral(config.getSid())+"\",");
				sb.append("isHoliday:\""+isHoliday(i,month,year,holidayList)+"\",");
				sb.append("isNoRegisterUserIds:\""+isNoRegisterUserIds(uuid)+"\",");
				if(config.getDutyTime1()!=null){
					sb.append("dutyTimeDesc1:\""+getPersonalRegisterTime(flag,uuid,config.getSid(),cl,config.getDutyTime1(),config.getDutyType1(),1)+"\",");
				    sb.append("isLeave1:\""+isLeave(uuid,cl,config.getDutyTime1())+"\",");
				    sb.append("isEvection1:\""+isEvection(uuid,cl,config.getDutyTime1())+"\",");
				    sb.append("isOut1:\""+isOut(uuid,cl,config.getDutyTime1())+"\",");
				}else{
					sb.append("dutyTimeDesc1:\"\",");
				    sb.append("isLeave1:\"false\",");
				    sb.append("isEvection1:\"false\",");
				    sb.append("isOut1:\"false\",");
				}
				if(config.getDutyTime2()!=null){
					sb.append("dutyTimeDesc2:\""+getPersonalRegisterTime(flag,uuid,config.getSid(),cl,config.getDutyTime2(),config.getDutyType2(),2)+"\",");
					sb.append("isLeave2:\""+isLeave(uuid,cl,config.getDutyTime2())+"\",");
					sb.append("isEvection2:\""+isEvection(uuid,cl,config.getDutyTime2())+"\",");
					sb.append("isOut2:\""+isOut(uuid,cl,config.getDutyTime2())+"\",");
				}else{
					sb.append("dutyTimeDesc2:\"\",");
				    sb.append("isLeave2:\"false\",");
				    sb.append("isEvection2:\"false\",");
				    sb.append("isOut2:\"false\",");
				}
				if(config.getDutyTime3()!=null){
					sb.append("dutyTimeDesc3:\""+getPersonalRegisterTime(flag,uuid,config.getSid(),cl,config.getDutyTime3(),config.getDutyType3(),3)+"\",");
					sb.append("isLeave3:\""+isLeave(uuid,cl,config.getDutyTime3())+"\",");
					sb.append("isEvection3:\""+isEvection(uuid,cl,config.getDutyTime3())+"\",");
					sb.append("isOut3:\""+isOut(uuid,cl,config.getDutyTime3())+"\",");
				}else{
					sb.append("dutyTimeDesc3:\"\",");
				    sb.append("isLeave3:\"false\",");
				    sb.append("isEvection3:\"false\",");
				    sb.append("isOut3:\"false\",");
				}
				if(config.getDutyTime4()!=null){
					sb.append("dutyTimeDesc4:\""+getPersonalRegisterTime(flag,uuid,config.getSid(),cl,config.getDutyTime4(),config.getDutyType4(),4)+"\",");
					sb.append("isLeave4:\""+isLeave(uuid,cl,config.getDutyTime4())+"\",");
					sb.append("isEvection4:\""+isEvection(uuid,cl,config.getDutyTime4())+"\",");
					sb.append("isOut4:\""+isOut(uuid,cl,config.getDutyTime4())+"\",");
				}else{
					sb.append("dutyTimeDesc4:\"\",");
				    sb.append("isLeave4:\"false\",");
				    sb.append("isEvection4:\"false\",");
				    sb.append("isOut4:\"false\",");
				}
				if(config.getDutyTime5()!=null){
					sb.append("dutyTimeDesc5:\""+getPersonalRegisterTime(flag,uuid,config.getSid(),cl,config.getDutyTime5(),config.getDutyType5(),5)+"\",");
					sb.append("isLeave5:\""+isLeave(uuid,cl,config.getDutyTime5())+"\",");
					sb.append("isEvection5:\""+isEvection(uuid,cl,config.getDutyTime5())+"\",");
					sb.append("isOut5:\""+isOut(uuid,cl,config.getDutyTime5())+"\",");
				}else{
					sb.append("dutyTimeDesc5:\"\",");
				    sb.append("isLeave5:\"false\",");
				    sb.append("isEvection5:\"false\",");
				    sb.append("isOut5:\"false\",");
				}
				if(config.getDutyTime6()!=null){
					sb.append("dutyTimeDesc6:\""+getPersonalRegisterTime(flag,uuid,config.getSid(),cl,config.getDutyTime6(),config.getDutyType6(),6)+"\",");
					sb.append("isLeave6:\""+isLeave(uuid,cl,config.getDutyTime6())+"\",");
					sb.append("isEvection6:\""+isEvection(uuid,cl,config.getDutyTime6())+"\",");
					sb.append("isOut6:\""+isOut(uuid,cl,config.getDutyTime6())+"\",");
				}else{
					sb.append("dutyTimeDesc6:\"\",");
				    sb.append("isLeave6:\"false\",");
				    sb.append("isEvection6:\"false\",");
				    sb.append("isOut6:\"false\",");
				}
				sb=sb.deleteCharAt(sb.length()-1);
				sb.append("}");
			}
		}
		return sb.toString();
	}


	
	/**
	 * 个人考情   上下班记录
	 * @param uuid
	 * @param sid
	 * @param cl 
	 * @param dutyTime6
	 * @param dutyType6
	 * @param i  num代表第几次签到
	 * @return  
	 * @throws Exception 
	 */
	private String getPersonalRegisterTime(int flag1,int userId,int dutyId,Calendar cl,Calendar cl1,char type,int num) throws Exception {
		//flag=0  代表是从个人考勤--上下班记录过来的，可以申诉和查看申诉
		//flag=1 代表是其他地方过来的，只能查看申诉  没有申诉的操作
		//日期   例如：2018-06-01   2018-06-12
		String remarkTimeStr=TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd");
		//判断当前登陆人   在当前日期（remarkTimeStr）第num次签到     是否存在申诉记录  
	    int ope=dutyComplaintDao.getComplaintOperation(remarkTimeStr,num,userId,cl1);
		String opeStr="";
	    if(ope==0){
	    	opeStr="";
		}else if(ope==1){
			opeStr="<a href='#' onclick=\\\"viewComplain('"+remarkTimeStr+"',"+num+","+userId+");\\\">查看申诉</a>";
		}else if(ope==2&&flag1==0){
			opeStr="<a href='#' onclick=\\\"complain('"+remarkTimeStr+"',"+num+");\\\">申诉</a>";
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cl2 =Calendar.getInstance();
		String curDate = sf.format(cl.getTime());
		cl.setTime(sdf.parse(curDate+" 00:00"));
		cl2.setTime(sdf.parse(curDate+" 23:59"));
		List values = new ArrayList();
		values.add(cl);
		values.add(cl2);
		String hql = "from TeeAttendDuty d where d.userId ="+userId+" and d.registerType="+dutyId+" and d.registerTime>? and d.registerTime<? and dutyType="+num+" order by  sid asc";
		List<TeeAttendDuty> list = (List<TeeAttendDuty>) executeQueryByList(hql,values);
		String workonBeforeMin="";
		String workonAfterMin="";
		String workoutBeforeMin="";
		String workoutAfterMin="";
		String dutyTime = sf.format(cl.getTime())+" "+sdf.format(cl1.getTime()).substring(10,16);//规定登记时间
		Calendar ruleTime = Calendar.getInstance();
		ruleTime.setTime(sdf.parse(dutyTime));
		String flag="";
		String posRender = null;
		String contextPath = TeeSysProps.getString("contextPath");
		if(type=='0'){//上班登记
			if(sysParaDao.getSysPara("WORKON_BEFORE_MIN")!=null){
				workonBeforeMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_BEFORE_MIN").getParaValue(),"0");
			}
			if(sysParaDao.getSysPara("WORKON_AFTER_MIN")!=null){
				workonAfterMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKON_AFTER_MIN").getParaValue(),"0");
			}
			Calendar cl3 = Calendar.getInstance();//上班提前登记时间
			cl3.setTime(sdf.parse(dutyTime));
			cl3.add(Calendar.MINUTE, -Integer.parseInt(workonBeforeMin));
			
			Calendar cl4 = Calendar.getInstance();//上班延后登记时间
			cl4.setTime(sdf.parse(dutyTime));
			cl4.add(Calendar.MINUTE, Integer.parseInt(workonAfterMin));
			
			for(TeeAttendDuty duty:list){
				Calendar registerTime = duty.getRegisterTime();
				if(registerTime.after(cl3) && registerTime.before(cl4) && (duty.getDutyType()==1 || duty.getDutyType()==3 || duty.getDutyType()==5)){
					if(registerTime.after(ruleTime)){
						flag="<span style='color:red'>&nbsp;&nbsp;&nbsp;迟到</span>&nbsp;&nbsp;&nbsp;&nbsp;"+opeStr;
					}
					if(!TeeUtility.isNullorEmpty(duty.getPhoneModel())){//手机型号
						posRender="<br/>"+duty.getPhoneModel();
					}else{
						posRender="";
					}
					if(!TeeUtility.isNullorEmpty(duty.getRemark())){
						if(!TeeUtility.isNullorEmpty(duty.getPosition())){
							posRender += "<br/><span class=\\\"remark\\\" onclick=\\\"openFullWindow('"+contextPath+"/system/core/base/attend/duty/position.jsp?pos="+duty.getPosition()+ "&address=" +  URLEncoder.encode(duty.getRemark(),"UTF-8")+ "')\\\">"+duty.getRemark()+"</span>";
						}else{
							posRender += "<br/>"+ duty.getRemark();
						}
					}else{
						posRender+="";
					}
					
					
					return sf2.format(registerTime.getTime())+flag+posRender;
				}
			}
			
		}else{//下班登记
			if(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN")!=null){
				workoutBeforeMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_BEFORE_MIN").getParaValue(),"0");
			}
			if(sysParaDao.getSysPara("WORKOUT_AFTER_MIN")!=null){
				workoutAfterMin=TeeStringUtil.getString(sysParaDao.getSysPara("WORKOUT_AFTER_MIN").getParaValue(),"0");
			}
			
			Calendar cl3 = Calendar.getInstance();//下班提前登记时间
			cl3.setTime(sdf.parse(dutyTime));
			cl3.add(Calendar.MINUTE, -Integer.parseInt(workoutBeforeMin));
			
			Calendar cl4 = Calendar.getInstance();//下班延后登记时间
			cl4.setTime(sdf.parse(dutyTime));
			cl4.add(Calendar.MINUTE, Integer.parseInt(workoutAfterMin));
			for(TeeAttendDuty duty:list){
				Calendar registerTime = duty.getRegisterTime();
				if(registerTime.after(cl3) && registerTime.before(cl4) && (duty.getDutyType()==2 || duty.getDutyType()==4 || duty.getDutyType()==6)){
					if(registerTime.before(ruleTime)){
						flag="<span style='color:red'>&nbsp;&nbsp;&nbsp;早退</span>&nbsp;&nbsp;&nbsp;&nbsp;"+opeStr;
					}
					if(!TeeUtility.isNullorEmpty(duty.getPhoneModel())){//手机型号
						posRender="<br/>"+duty.getPhoneModel();
					}else{
						posRender="";
					}
					if(!TeeUtility.isNullorEmpty(duty.getRemark())){
						if(!TeeUtility.isNullorEmpty(duty.getPosition())){
							posRender += "<br/><span class=\\\"remark\\\" onclick=\\\"openFullWindow('"+contextPath+"/system/core/base/attend/duty/position.jsp?pos="+duty.getPosition()+"&address=" +  URLEncoder.encode(duty.getRemark(),"UTF-8")+"')\\\">"+duty.getRemark()+"</span>";
						}else{
							posRender += "<br/>"+ duty.getRemark();
						}
					}else{
						posRender+="";
					}
					
					return sf2.format(registerTime.getTime())+flag+posRender;
				}
			}
		}
		return "未登记&nbsp;&nbsp;&nbsp;&nbsp;"+opeStr;
	}
	
	
	
	
}