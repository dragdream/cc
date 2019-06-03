package com.tianee.oa.core.base.weekActive.service;

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

import com.tianee.oa.core.base.weekActive.bean.TeeWeekActive;
import com.tianee.oa.core.base.weekActive.dao.TeeWeekActiveDao;
import com.tianee.oa.core.base.weekActive.model.TeeWeekActiveModel;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeWeekActiveService extends TeeBaseService {

	@Autowired
	private TeeWeekActiveDao dao;
	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeSmsSender smsSender;
	
	@Autowired
	private TeePersonService personService;
	
	/**
	 * @function: 新建信息
	 * @author: wyw
	 * @data: 2015年2月3日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeWeekActiveModel model) {
		TeeJson json = new TeeJson();
		try {
			TeeWeekActive weekActive = new TeeWeekActive();
			String activeUserStr = TeeUtility.null2Empty(model.getActiveUserId());
			if (activeUserStr.endsWith(",")) {
				activeUserStr = activeUserStr.substring(0, activeUserStr.length() - 1);
			}
			weekActive.setCreateUser(loginPerson);
			weekActive.setCreateTime(new Date());
			weekActive.setOverStatus("1");
			weekActive.setActiveContent(model.getActiveContent());
            weekActive.setDeptAndUser(model.getDeptAndUser());
            weekActive.setAdderss(model.getAdderss());
            weekActive.setSwOrxw(model.getSwOrxw());
			weekActive.setActiveUser(activeUserStr);
			paresObj(weekActive, model);
			dao.save(weekActive);
			List<Map> list = personService.getHongdongTz();
			if(list!=null && list.size()>0){
				String userIds="";
				for(int i=0;i<list.size();i++){
					userIds+=list.get(i).get("uuid")+",";
				}
				if(!"".equals(userIds)){
					userIds=userIds.substring(0, userIds.length()-1);
				}
				Map requestData = new HashMap();
				requestData.put("moduleNo", "102");
				requestData.put("userListIds", userIds);
				requestData.put("content", model.getActiveUserName()+"于 ["+model.getActiveStart()+"] 有一个活动安排");
				requestData.put("remindUrl", "/system/core/base/weekActive/index.jsp?activeUser="+model.getActiveUserId());
				smsSender.sendSms(requestData, loginPerson);
			}
			json.setRtState(true);
			json.setRtMsg("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * @function:编辑
	 * @author: wyw
	 * @data: 2015年2月3日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeWeekActiveModel model) {
		TeeJson json = new TeeJson();
		try {
			String activeUserStr = TeeUtility.null2Empty(model.getActiveUserId());
			if (activeUserStr.endsWith(",")) {
				activeUserStr = activeUserStr.substring(0, activeUserStr.length() - 1);
			}
			TeeWeekActive weekActive = dao.get(model.getSid());
			if (weekActive != null) {
				weekActive.setActiveContent(model.getActiveContent());
				weekActive.setActiveUser(activeUserStr);
				weekActive.setDeptAndUser(model.getDeptAndUser());
		        weekActive.setAdderss(model.getAdderss());
		        weekActive.setSwOrxw(model.getSwOrxw());
				//weekActive.setActiveUser(activeUserStr);
				paresObj(weekActive, model);
				dao.update(weekActive);
				List<Map> list = personService.getHongdongTz();
				if(list!=null && list.size()>0){
					String userIds="";
					for(int i=0;i<list.size();i++){
						userIds+=list.get(i).get("uuid")+",";
					}
					if(!"".equals(userIds)){
						userIds=userIds.substring(0, userIds.length()-1);
					}
					Map requestData = new HashMap();
					requestData.put("moduleNo", "102");
					requestData.put("userListIds", userIds);
					requestData.put("content", model.getActiveUserName()+"于 ["+model.getActiveStart()+"] 有一个活动安排");
					requestData.put("remindUrl", "/system/core/base/weekActive/index.jsp?activeUser="+model.getActiveUserId());
					smsSender.sendSms(requestData, loginPerson);
				}
			}

			json.setRtState(true);
			json.setRtMsg("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 参数转换
	 * 
	 * @date 2014年4月26日
	 * @author
	 * @param weekActive
	 * @param model
	 * @throws Exception
	 */
	public void paresObj(TeeWeekActive weekActive, TeeWeekActiveModel model) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = model.getActiveStart();
		Date date = format.parse(startDate);
		if (!TeeUtility.isNullorEmpty(model.getActiveStart())) {
			weekActive.setActiveStartTime(date);
		}
		if (!TeeUtility.isNullorEmpty(model.getActiveEnd())) {
			weekActive.setActiveEndTime(TeeUtility.parseDate(model.getActiveEnd()));
		}
	}

	/**
	 * 根据日期获取数据
	 * 
	 * @date 2014年5月10日
	 * @author
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	public TeeJson getWeekActiveList(Map requestMap) throws ParseException {
		TeePerson person = (TeePerson) requestMap.get(TeeConst.LOGIN_USER);
		String beginDate = (String) requestMap.get("beginDate");
		String endDate = (String) requestMap.get("endDate");
		String activeUser = (String)requestMap.get("activeUser");

		TeeJson json = new TeeJson();

		List<TeeWeekActiveModel> activeList = new ArrayList<TeeWeekActiveModel>();

		List<TeeWeekActive> webkActives = dao.getActiveByWeekDao(person, beginDate, endDate,activeUser);
		if (webkActives != null && webkActives.size() > 0) {
			for (TeeWeekActive weekActive : webkActives) {
				TeeWeekActiveModel model = parseModel(weekActive);
				model.setActiveContent(model.getActiveContent().replaceAll("\n", "<br/>"));
				model.setDeptAndUser(model.getDeptAndUser().replaceAll("\n", "<br/>"));
				model.setAdderss(model.getAdderss().replaceAll("\n", "<br/>"));
				activeList.add(model);
			}
		}
		json.setRtData(activeList);
		json.setRtState(true);

		return json;
	}
	
	
	public TeeJson getWeekActiveList2(TeePerson person) throws ParseException {
		Map<String, String> weekDate = getWeekDate();
		String beginDate = weekDate.get("mondayDate");
		String endDate = weekDate.get("sundayDate");
		
		TeeJson json = new TeeJson();

		List<TeeWeekActiveModel> activeList = new ArrayList<TeeWeekActiveModel>();

		List<TeeWeekActive> webkActives = dao.getActiveByWeekDao(person, beginDate, endDate,person.getUuid()+"");
		if (webkActives != null && webkActives.size() > 0) {
			for (TeeWeekActive weekActive : webkActives) {
				TeeWeekActiveModel parseModel = parseModel(weekActive);
				parseModel.setZhou(dateToWeek(TeeStringUtil.getString(weekActive.getActiveStartTime(), "yyyy-MM-dd")));
				activeList.add(parseModel);
			}
		}
		json.setRtData(activeList);
		json.setRtState(true);

		return json;
	}
	
	public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	/**
     * 当前时间所在一周的周一和周日时间
     * @param time 当前时间
     * @return
     */
    public static Map<String,String> getWeekDate() {
        Map<String,String> map = new HashMap<String,String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

         Calendar cal = Calendar.getInstance();  
         cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
         int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
         if(dayWeek==1){
             dayWeek = 8;
         }

         cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
         Date mondayDate = cal.getTime();
         String weekBegin = sdf.format(mondayDate); //周一 

         cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
         Date sundayDate = cal.getTime();
         String weekEnd = sdf.format(sundayDate); //周日 

         map.put("mondayDate", weekBegin);
         map.put("sundayDate", weekEnd);
        return map;
    }
	
	

	public TeeWeekActiveModel parseModel(TeeWeekActive obj) {
		TeeWeekActiveModel model = new TeeWeekActiveModel();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		model.setSid(obj.getSid());
		model.setActiveContent(obj.getActiveContent());
		model.setDeptAndUser(obj.getDeptAndUser());
		model.setAdderss(obj.getAdderss());
		if (!TeeUtility.isNullorEmpty(obj.getActiveUser())) {
			List<TeePerson> personList = personDao.getPersonByUuids(obj.getActiveUser());
			StringBuffer userIdBuffer = new StringBuffer();
			StringBuffer userNameBuffer = new StringBuffer();
			if (personList != null && personList.size() > 0) {
				for (TeePerson person : personList) {
					if (userIdBuffer.length() > 0) {
						userIdBuffer.append(",");
					}
					if (userNameBuffer.length() > 0) {
						userNameBuffer.append(",");
					}
					userIdBuffer.append(person.getUuid());
					userNameBuffer.append(person.getUserName());
				}
			}
			model.setActiveUserId(userIdBuffer.toString());
			model.setActiveUserName(userNameBuffer.toString());
		} else {
			model.setActiveUserId("");
			model.setActiveUserName("");
		}
		if (obj.getCreateUser() != null) {
			model.setCreateUserId(obj.getCreateUser().getUuid());
			model.setCreateUserName(obj.getCreateUser().getUserName());
		} else {
			model.setCreateUserId(0);
			model.setCreateUserName("");
		}
        Date startDate  = obj.getActiveStartTime();
        String strDate = format.format(startDate);
		if (!TeeUtility.isNullorEmpty(obj.getActiveStartTime())) {
			model.setActiveStart(strDate);
		} else {
			model.setActiveStart("");
		}
		if (!TeeUtility.isNullorEmpty(obj.getActiveEndTime())) {
			model.setActiveEnd(TeeUtility.getDateTimeStr(obj.getActiveEndTime()));
		} else {
			model.setActiveEnd("");
		}
		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateTimeStr(obj.getCreateTime()));
		} else {
			model.setActiveEnd("");
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年2月5日
	 * @param requestMap
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(Map requestMap, TeePerson person, TeeWeekActiveModel model) {
		String sids = (String) requestMap.get("sids");
		dao.delByIds(sids);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeWeekActiveModel model) {
		TeeJson json = new TeeJson();
		if (model.getSid() > 0) {
			TeeWeekActive obj = dao.get(model.getSid());
			if (obj != null) {
				model = parseModel(obj);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}

	public TeeJson checkHongdong(TeePerson person,TeeWeekActiveModel model) {
		TeeJson json=new TeeJson();
		if(model.getSid()<=0){
			Map executeNativeUnique = dao.executeNativeUnique("select * from WEEK_ACTIVE where ACTIVE_USER=? and ACTIVE_START_TIME=? and SW_OR_XW=?", new Object[]{model.getActiveUserId(),model.getActiveStart()+" 00:00:00",model.getSwOrxw()});
			if(executeNativeUnique==null){
				json.setRtState(true);
			}else{
				json.setRtState(false);
			}
		}else{
			json.setRtState(true);
		}
		return json;
	}

	
	/**
	 * 获取本周其他领导的事务安排
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getCurrWeekOtherLeaderList(HttpServletRequest request) throws ParseException {
		TeeJson  json=new TeeJson();
		//获取当前登录人的信息
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取本周的开始时间和结束时间
		Map<String,String> map=getWeekDate();
		String weekBeginDateStr=map.get("mondayDate")+" 00:00:00";
		String weekEndDateStr=map.get("sundayDate")+" 23:59:59";
	    
		Date beginDate = TeeUtility.parseDate(weekBeginDateStr);
		Date endDate = TeeUtility.parseDate(weekEndDateStr);
		
		String hql = "from TeeWeekActive where activeStartTime >=? and activeStartTime <=? and activeUser!=? order by sid desc";
		List<TeeWeekActive> list=simpleDaoSupport.executeQuery(hql,new Object[]{beginDate,endDate,loginUser.getUuid()+""});
		List<TeeWeekActiveModel> modelList=new ArrayList<TeeWeekActiveModel>();
		TeeWeekActiveModel model=null;
		if(list!=null){
			for (TeeWeekActive w : list) {
				model=parseModel(w);
				modelList.add(model);
			}
		}
		
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

	
	/**
	 * 获取本人本周的事务安排
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getCurrWeekMyselfList(HttpServletRequest request) throws ParseException {
		TeeJson  json=new TeeJson();
		//获取当前登录人的信息
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取本周的开始时间和结束时间
		Map<String,String> map=getWeekDate();
		String weekBeginDateStr=map.get("mondayDate")+" 00:00:00";
		String weekEndDateStr=map.get("sundayDate")+" 23:59:59";
	    
		Date beginDate = TeeUtility.parseDate(weekBeginDateStr);
		Date endDate = TeeUtility.parseDate(weekEndDateStr);
		
		String hql = "from TeeWeekActive where activeStartTime >=? and activeStartTime <=? and activeUser=? order by sid desc";
		List<TeeWeekActive> list=simpleDaoSupport.executeQuery(hql,new Object[]{beginDate,endDate,loginUser.getUuid()+""});
		List<TeeWeekActiveModel> modelList=new ArrayList<TeeWeekActiveModel>();
		TeeWeekActiveModel model=null;
		if(list!=null){
			for (TeeWeekActive w : list) {
				model=parseModel(w);
				modelList.add(model);
			}
		}
		
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

}
