package com.tianee.oa.quartzjob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.dao.TeeSysLogDao;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;

@Service
public class TeeSavingSysLogTimer extends TeeBaseService{
	
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		List<TeeSysLog> newSysLogs = new ArrayList();
		synchronized (TeeServiceLoggingInterceptor.sysLogsBufferdPool) {
			//获取日志池中的数据
			List<TeeSysLog> sysLogs = TeeServiceLoggingInterceptor.sysLogsBufferdPool;
			newSysLogs.addAll(sysLogs);
			sysLogs.clear();
		}
		
		for(TeeSysLog sysLog:newSysLogs){
			simpleDaoSupport.save(sysLog);
		}
		newSysLogs.clear();
		newSysLogs = null;
		
	}
}
