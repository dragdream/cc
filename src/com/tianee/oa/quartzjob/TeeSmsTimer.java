package com.tianee.oa.quartzjob;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.onduty.bean.TeePbOnDuty;
import com.tianee.oa.core.base.onduty.dao.TeePbOnDutyDao;
import com.tianee.oa.core.base.onduty.model.TeePbOnDutyModel;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.dao.TeeSmsBodyDao;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSmsTimer extends TeeBaseService{
	
	@Autowired
	private TeePbOnDutyDao teePbOnDutyDao;
	
	@Autowired
	private  TeePersonDao personDao;
	
	@Autowired
	private TeeSmsSender smsSender;
	
	public void doTimmer(){
		Map requestData = new HashMap();
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.DAY_OF_MONTH, 1);
		String dataStr=TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd");
		List<TeePbOnDuty> find = teePbOnDutyDao.find("from TeePbOnDuty where creTime>=? and creTime<=?", new Object[]{TeeDateUtil.format(dataStr+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),TeeDateUtil.format(dataStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss")});			//白班值
		if(find!=null && find.size()>0){
			for(TeePbOnDuty d:find){
				String format = TeeDateUtil.format(d.getCreTime(), "yyyy-MM-dd");
				int bzUserId = d.getBzUser().getUuid();
				if(bzUserId>0){
					//addSmsPhone(person,bzUserId,"白班值",model.getCreTime());
					requestData.put("moduleNo", "093");
					requestData.put("userListIds", bzUserId);
					String str="您明天("+format+")有一个'白班值'班安排，请确认。";
					requestData.put("content", str);
					//requestData.put("sendTime", TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
					requestData.put("remindUrl", "/system/core/base/onduty/myManager.jsp");
					smsSender.sendSms(requestData, null);
				}
				//白班带
				int bdUserId = d.getBdUser().getUuid();
				if(bdUserId>0){
					//addSmsPhone(person,bdUserId,"白班带",model.getCreTime());
					requestData.put("moduleNo", "093");
					requestData.put("userListIds", bdUserId);
					String str="您明天("+format+")有一个'白班带'班安排，请确认。";
					requestData.put("content", str);
					//requestData.put("sendTime", TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
					requestData.put("remindUrl", "/system/core/base/onduty/myManager.jsp");
					smsSender.sendSms(requestData, null);
				}
				//夜班值
				int yzUserId = d.getYzUser().getUuid();
				if(yzUserId>0){
					//addSmsPhone(person,yzUserId,"夜班值",model.getCreTime());
					requestData.put("moduleNo", "093");
					requestData.put("userListIds", yzUserId);
					String str="您明天("+format+")有一个'夜班值'班安排，请确认。";
					requestData.put("content", str);
					//requestData.put("sendTime", TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
					requestData.put("remindUrl", "/system/core/base/onduty/myManager.jsp");
					smsSender.sendSms(requestData, null);
				}
				//夜班带
				int ydUserId =d.getYdUser().getUuid();
				if(ydUserId>0){
					//addSmsPhone(person,ydUserId,"夜班带",model.getCreTime());
					requestData.put("moduleNo", "093");
					requestData.put("userListIds", ydUserId);
					String str="您明天("+format+")有一个'夜班带'班安排，请确认。";
					requestData.put("content", str);
					//requestData.put("sendTime", TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
					requestData.put("remindUrl", "/system/core/base/onduty/myManager.jsp");
					smsSender.sendSms(requestData, null);
				}
			}
		}
		
		
	}
}
