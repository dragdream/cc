package com.tianee.oa.sync.log.dao;

import org.springframework.stereotype.Repository;

import com.tianee.oa.sync.log.bean.TeeOutSystemSyncLog;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeSyncLogDao extends TeeBaseDao<TeeOutSystemSyncLog>{
	
	public void addLog(TeeOutSystemSyncLog log){
		save(log);
	}
	
	public void updateLog(TeeOutSystemSyncLog log){
		update(log);
	}
}
