package com.tianee.oa.sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.base.notify.dao.TeeNotifyInfoDao;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class TeeMoveDataService extends TeeBaseService{


	@Autowired
	private TeeNotifyDao notifyDao;

	@Autowired
	private TeeNotifyInfoDao notifyInfoDao;
	
	public void syncNotify(TeeNotify notify) {
		notifyDao.addNotify(notify);
	}
	
	public void syncRecord(TeeNotifyInfo notifyInfo) {
		notifyInfoDao.addNotify(notifyInfo);;
	}
}
