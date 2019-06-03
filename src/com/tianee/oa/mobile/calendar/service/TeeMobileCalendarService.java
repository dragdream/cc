package com.tianee.oa.mobile.calendar.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.calendar.bean.TeeCalendarAffair;
import com.tianee.oa.core.base.calendar.dao.TeeCalendarAffairDao;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMobileCalendarService extends TeeBaseService{

	@Autowired
	private TeeCalendarAffairDao calendarAffairDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	/**
	 * 获取日程列表
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getListService(TeeDataGridModel dm, HttpServletRequest request ,  TeeCalendarAffairModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		
		String overStatus = model.getOverStatus() +"";
		String hql = "from TeeCalendarAffair tcal where tcal.calAffType = 0 and(tcal.user = ? or (exists (select 1 from tcal.actor actor where actor=?)))";
		List values = new ArrayList();
		values.add(user);
		values.add(user);

		if(model.getStartDate() != null){//开始时间
			 hql = hql + " and tcal.endTime >= ?";
			 values.add(model.getStartDate().getTimeInMillis());
		}
		 
		if(model.getEndDate() != null){//结束时间 
			 hql = hql + " and tcal.startTime <= ?";
			 values.add(model.getEndDate().getTimeInMillis());
		}
		if(model.getCalType() > 0){
			 hql = hql + " and tcal.calType = ?";
			 values.add(model.getCalType());
		}
	    if(!overStatus.equals("")){
		      if(overStatus.equals("1")){//未开始的
		        hql = hql + " and tcal.startTime >= ? and tcal.overStatus='0'";
		        values.add(new Date().getTime());
		      }
		      if(overStatus.equals("2")){//进行中的
		        hql = hql + " and tcal.startTime <= ? and (tcal.endTime >=? or tcal.endTime = 0)  and tcal.overStatus='0'";
		        values.add(new Date().getTime());
		        values.add(new Date().getTime());
		      }
		      if(overStatus.equals("3")){//已超时
		        hql = hql + " and tcal.endTime <= ? and tcal.overStatus='0'";
		        values.add(new Date().getTime());
		      }
		      if(overStatus.equals("4")){
		    	  hql = hql + "  and tcal.overStatus='1'";
		      }
		    }
	    
		j.setTotal(calendarAffairDao.countByList("select count(*) " + hql, values));// 设置总记录数
		hql = hql + " order by startTime desc";
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeCalendarAffair> list = calendarAffairDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), values);// 查
		List<Map> modelList = new ArrayList<Map>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map map = parseMap(list.get(i) ,user);
				modelList.add(map);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 *  获取日程 byId
	 * @param map
	 * @param model
	 * @return
	 */
	public TeeJson getById( TeeCalendarAffairModel model , TeePerson person ){
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCalendarAffair cal = calendarAffairDao.get(model.getSid());
			Map map = parseMap(cal, person);
			json.setRtData(map);
		}else{
			json.setRtMsg("该日程已被删除!");
			json.setRtState(false);
		}
		return json;
		
	}
	
	/**
	 * 新建或者保存
	 * @param model
	 * @param person
	 * @return
	 */
	public TeeJson addOrUpdate( TeeCalendarAffairModel model , TeePerson person ){
		TeeJson json = new TeeJson();
		TeeCalendarAffair cal ;
		if(model.getSid() > 0){
			TeeCalendarAffair calOld = calendarAffairDao.get(model.getSid());
			calOld.setStartTime(model.getStartDate().getTimeInMillis());
			calOld.setEndTime(model.getEndDate().getTimeInMillis());
			calOld.setContent(model.getContent());
			calOld.setCalType(model.getCalType());
			calOld.setCalLevel(model.getCalLevel());
			if(!TeeUtility.isNullorEmpty(model.getActorIds())){//参与者
				List<TeePerson> list = personDao.getPersonByUuids(model.getActorIds());
				calOld.setActor(list);
			
			}else{
				calOld.setActor(null);
			}
			calendarAffairDao.update(calOld);
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
			}else{
				cal.setActor(null);
			}
			calendarAffairDao.addCalendar(cal);
		}
		json.setRtState(true);
		return json;
		
	}
	
	
	/**
	 * 转换模型
	 * @param calendar
	 * @param user
	 * @return
	 */
	public Map parseMap(TeeCalendarAffair calendar , TeePerson user){
		String status = "0";// 进行中 判断判断状态
	    Date date = new Date();
        long dateTime = date.getTime();
        long begin = 0;
        long end = 0;
		Map<String, String> map = new HashMap<String, String>();
		if(calendar == null){
			return map;
		}
        map.put("sid", String.valueOf(calendar.getSid()));
        map.put("userId", calendar.getUser().getUuid() + "");
        map.put("calLevel", calendar.getCalLevel());
        map.put("calType", calendar.getCalType() + "");
        map.put("content", calendar.getContent());
        map.put("isDelete", "0");
        if(calendar.getUser().getUuid() == user.getUuid()){
        	 map.put("isDelete", "1");//能删除
        }
        String startTimeStr = "";
        String endTimeStr = "";
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (calendar.getStartTime() >0) {
        	Date sdate = new Date();
        	
        	sdate.setTime(calendar.getStartTime());
        	startTimeStr = dateFormat.format(sdate);
        	map.put("calTime", startTimeStr);
        } else {
          map.put("calTime", "");
        }
        if (calendar.getEndTime() > 0) {
        	Date edate = new Date();
        	edate.setTime(calendar.getEndTime());
        	endTimeStr = dateFormat.format(edate);
        	map.put("endTime", endTimeStr);
        } else {
          map.put("endTime", "");
        }

        String calendarTimeDecs = startTimeStr;//时间范围
        if(!endTimeStr.equals("") && startTimeStr.contains(endTimeStr.substring(0,10))){
        	calendarTimeDecs  = startTimeStr + " - " + endTimeStr.substring(11,16);
        }else{
        	calendarTimeDecs = calendarTimeDecs + " - " + endTimeStr;
        }
        map.put("calendarTimeDecs", calendarTimeDecs);
        map.put("overStatus", calendar.getOverStatus() + "");
        // System.out.println(calendar.getManagerId());
        if (calendar.getManager() != null) {
          map.put("managerName", calendar.getManager().getUserName());
        } else {
          map.put("managerName", "");
        }
        TeePerson  userPerson = calendar.getUser();
        String userId = "";
        String userName = "";
        if(userPerson != null){
        	userName = userPerson.getUserName();
        	userId= userPerson.getUuid() +"";
        }
        map.put("userId", userId);
        map.put("userName", userName);
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
        
        List<TeePerson> actors = calendar.getActor();
        StringBuffer actorNames = new StringBuffer();
        StringBuffer actorIds=new StringBuffer();
        
        for(TeePerson p:actors){
        	actorNames.append(p.getUserName()+",");
        	actorIds.append(p.getUuid()+",");
        }
        
        String actorIds1 = "";
        if(actorIds.length()!=0){
        	actorIds1=actorIds.substring(0, actorIds.length()-1);
        }
        String actorNames1="";
        if(actorNames.length()!=0){
        	actorNames1=actorNames.substring(0, actorNames.length()-1);
        }
        
        map.put("actorNames", actorNames1.toString());
        map.put("actorIds", actorIds1.toString());
        
        return map;
	}

	

}
