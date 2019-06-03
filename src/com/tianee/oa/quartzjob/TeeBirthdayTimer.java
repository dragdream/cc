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
public class TeeBirthdayTimer extends TeeBaseService{
	
	@Autowired
	private  TeePersonDao personDao;
	
	@Autowired
	private TeeSmsSender smsSender;
	
	public void doTimmer(){
		Map requestData = new HashMap();
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		String dataStr=TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd");
		List<TeePerson> find = personDao.find("from TeePerson where birthday>=? and birthday<=?", new Object[]{TeeDateUtil.format(dataStr+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),TeeDateUtil.format(dataStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss")});
		if(find!=null && find.size()>0){
			for(TeePerson d:find){
					requestData.put("moduleNo", "111");
					requestData.put("userListIds", d.getUuid());
					String str=d.getUserName()+"同志，祝您生日快乐!";
					requestData.put("content", str);
					requestData.put("remindUrl", "/system/core/base/onduty/birthDay.jsp");
					smsSender.sendSms(requestData, null);
			}
		}
		
		
	}
}
