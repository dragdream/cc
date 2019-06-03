package com.tianee.oa.core.workflow.flowmanage.dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFlowProcessDao extends TeeBaseDao<TeeFlowProcess>{
	/**
	 * 获取流程步骤集合
	 * @param flowTypeId
	 * @return
	 */
	public List<TeeFlowProcess> findFlowProcessByFlowType(int flowTypeId){
		List<TeeFlowProcess> list = new ArrayList<TeeFlowProcess>();
		Session session = this.getSession();
		Query query = session.createQuery("from TeeFlowProcess flowProcess where flowProcess.flowType.sid=? order by flowProcess.sid asc");
		query.setParameter(0, flowTypeId);
		list = query.list();
//		Iterator<TeeFlowProcess> it = query.iterate();
//		while(it!=null && it.hasNext()){
//			list.add(it.next());
//		}
		return list;
	}
	
	/**
	 * 将节点集合按照排序号排序
	 * @param flowProcessList
	 * @return
	 */
	public List<TeeFlowProcess> sortedFlowProcessList(List<TeeFlowProcess> flowProcessList){
		Session s = this.getSession();
		Query q = s.createFilter(flowProcessList, "order by this.sortNo asc");
		return q.list();
	}
	
	/**
	 * 获取流程节点
	 * @param flowId
	 * @param prcsId
	 * @return
	 */
	public TeeFlowProcess getFlowProcess(int flowId,int prcsId){
		Session session = super.getSession();
		Criteria c = session.createCriteria(TeeFlowProcess.class,"flowProcess");
		c.add(Restrictions.eq("flowProcess.prcsId", prcsId))
		.add(Restrictions.eq("flowProcess.flowType.sid", flowId));
		return (TeeFlowProcess) c.uniqueResult();
	}
	
	/**
	 * 获取所有可执行节点
	 * @param person
	 * @return
	 */
	public List<TeeFlowProcess> getStartNodeList(TeePerson person){
		Session session = super.getSession();
		Query query = session.createQuery("from TeeFlowProcess fp where fp.prcsId=1");
		List<TeeFlowProcess> list = query.list();
		List<TeeFlowProcess> filterList = new ArrayList<TeeFlowProcess>();
		Query tmp = null;
		List rs = null;
		for(TeeFlowProcess fp:list){
			//是否有经办人权限
			tmp = session.createFilter(fp.getPrcsUser(), "select this.uuid where this.uuid='402882ec4090acbf014090af6d5a0002'");
			rs = tmp.list();
			if(rs!=null && rs.size()!=0){
				filterList.add(fp);
				continue;
			}
			
			//是否有经办部门权限
			tmp = session.createFilter(fp.getPrcsDept(), "select this.uuid where this.uuid='"+1+"'");
			rs = tmp.list();
			if(rs!=null && rs.size()!=0){
				filterList.add(fp);
				continue;
			}
			
			//是否有经办角色权限
			tmp = session.createFilter(fp.getPrcsRole(), "select this.uuid where this.uuid='"+1+"'");
			rs = tmp.list();
			if(rs!=null && rs.size()!=0){
				filterList.add(fp);
				continue;
			}
		}
		return filterList;
	}
	
	/**
	 * 获取该流程的起始节点
	 * @param flowId
	 * @return
	 */
	public TeeFlowProcess getStartNodeByFlowId(int flowId){
		Session session = super.getSession();
		Query query = session.createQuery("from TeeFlowProcess fp where fp.prcsId=1 and fp.flowType.sid="+flowId);
		return (TeeFlowProcess) query.uniqueResult();
	}
	
	
	/**
	 * 获取 流程步骤 --- byIds
	 * @param sids - 步骤Id字符串串 以逗号分隔
	 * @return
	 */
	public List<TeeFlowProcess> getPrcsByIds(String sids){
		if(sids.endsWith(",")){
			sids  = sids.substring(0, sids.length() -1 );
		}
		Session session = super.getSession();
		Query query = session.createQuery("from TeeFlowProcess  where sid in (" + sids + ")");
		return query.list();
	}
	
	/**
	 * 获取流程步骤中最大步骤号
	 * @param flowId
	 * @return
	 */
	public int getMaxPrcsId(int flowId){
		Session session = this.getSession();
		Query query = session.createQuery("select max(fp.prcsId) from TeeFlowProcess fp where fp.flowType.sid=?");
		query.setInteger(0, flowId);
		Integer max = (Integer) query.uniqueResult();
		return max==null?0:max;
	}
	
	public TeeFlowProcess getStartNode(int flowId){
		Session session = this.getSession();
		Query query = session.createQuery("from TeeFlowProcess fp where fp.prcsId=1 and fp.flowType.sid="+flowId);
		return (TeeFlowProcess) query.uniqueResult();
	}
	
	public TeeFlowProcess getEndNode(int flowId){
		Session session = this.getSession();
		Query query = session.createQuery("from TeeFlowProcess fp where fp.prcsId=0 and fp.flowType.sid="+flowId);
		return (TeeFlowProcess) query.uniqueResult();
	}
	
}
