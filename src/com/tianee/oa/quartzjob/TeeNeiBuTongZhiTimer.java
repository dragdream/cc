package com.tianee.oa.quartzjob;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.subsys.project.bean.TeeNeiBuTiXing;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class TeeNeiBuTongZhiTimer extends TeeBaseService{
	
	@Autowired
	private TeeSmsSender smsSender;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	public void doTimmer(){
		Map requestData = new HashMap();
		Date date=new Date();
		String format = TeeDateUtil.format(date, "yyyy-MM-dd");
		String format1=format+" 00:00:00";
		String format2=format+" 23:59:59";
		List<TeeNeiBuTiXing> find = simpleDaoSupport.find("from TeeNeiBuTiXing where creTime>="+format1+" and creTime<="+format2, null);
		if(find!=null && find.size()>0){
			for(TeeNeiBuTiXing nb:find){
				requestData.put("moduleNo", "103");
				requestData.put("userListIds", nb.getUserIds());
				requestData.put("content", nb.getContent());
				//requestData.put("sendTime", TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
				requestData.put("remindUrl", "");
				smsSender.sendSms(requestData, null);
			}
		}
		
	}
}
