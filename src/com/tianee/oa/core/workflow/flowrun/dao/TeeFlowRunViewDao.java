package com.tianee.oa.core.workflow.flowrun.dao;

import java.util.Calendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunView;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFlowRunViewDao extends TeeBaseDao<TeeFlowRunView>{
	/**
	 * 流程查阅
	 * @param runId
	 * @param personUuid
	 */
	public void viewLookup(int runId,int personUuid){
		Session session = this.getSession();
		Query query = session.createQuery("update TeeFlowRunView frv set frv.viewTime=?,frv.viewFlag=1 where frv.flowRun.runId=? and frv.viewPerson.uuid=? and frv.viewFlag!=1");
		query.setParameter(0, Calendar.getInstance());
		query.setParameter(1, runId);
		query.setParameter(2, personUuid);
		query.executeUpdate();
	}
}
