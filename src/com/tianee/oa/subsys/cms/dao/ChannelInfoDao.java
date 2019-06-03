package com.tianee.oa.subsys.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("channelInfoDao")
public class ChannelInfoDao extends TeeBaseDao<ChannelInfo> {
	
	
	public ChannelInfo getChannelInfoById(int ChnlId){
		ChannelInfo channelInfo=new ChannelInfo();
		Session session=sessionFactory.openSession();
		Transaction trans=session.beginTransaction();
		channelInfo=(ChannelInfo) session.createQuery("from ChannelInfo where sid=?")
				.setParameter(0, ChnlId)
				.uniqueResult();
		trans.commit();
		session.close();
		return channelInfo;
	}

}
