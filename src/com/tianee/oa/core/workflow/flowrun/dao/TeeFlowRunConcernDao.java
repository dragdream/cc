package com.tianee.oa.core.workflow.flowrun.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunConcern;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFlowRunConcernDao extends TeeBaseDao<TeeFlowRunConcern>{
	/**
	 * 获取关注信息
	 * @param flowRun
	 * @param person
	 * @return
	 */
	public TeeFlowRunConcern getConcern(TeeFlowRun flowRun,TeePerson person){
		Session session = this.getSession();
		Query query = session.createQuery("from TeeFlowRunConcern frc where frc.flowRun=? and frc.person=?");
		query.setParameter(0, flowRun);
		query.setParameter(1, person);
		return (TeeFlowRunConcern) query.uniqueResult();
	}
	
	/**
	 * 获取关注信息
	 * @param flowRun
	 * @param person
	 * @return
	 */
	public TeeFlowRunConcern getConcern(int runId,int personId){
		Session session = this.getSession();
		Query query = session.createQuery("from TeeFlowRunConcern frc where frc.flowRun.runId=? and frc.person.uuid=?");
		query.setParameter(0, runId);
		query.setParameter(1, personId);
		return (TeeFlowRunConcern) query.uniqueResult();
	}
	
	public TeeFlowRunConcern cancelConcern(int runId,int personId){
		Session session = this.getSession();
		Query query = session.createQuery("from TeeFlowRunConcern frc where frc.flowRun.runId=? and frc.person.uuid=?");
		query.setParameter(0, runId);
		query.setParameter(1, personId);
		TeeFlowRunConcern obj = (TeeFlowRunConcern) query.uniqueResult();
		this.deleteByObj(obj);
		return obj;
	}
	
	public List<TeePerson> getConcernUsersByRunId(int runId){
		Session session = this.getSession();
		Query query = session.createQuery("from TeePerson p where exists (select 1 from TeeFlowRunConcern frc where frc.person=p and frc.flowRun.runId=?)");
		query.setParameter(0, runId);
		Iterator<TeePerson> it = query.iterate();
		List<TeePerson> list = new ArrayList<TeePerson>();
		if(it!=null){
			while(it.hasNext()){
				list.add(it.next());
			}
		}
		return list;
	}
}
