package com.tianee.oa.core.workflow.flowrun.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TeeFlowRunPrcsDao extends TeeBaseDao<TeeFlowRunPrcs> {

	@Autowired
	private TeePersonDao personDao;
	
	/**
	 * 获取未接收的工作
	 * 
	 * @param person
	 * @return
	 */
	public List<TeeFlowRunPrcs> getNoReceivedWorks(TeePerson person,
			int firstResult, int pageSize,String sort,String order) {
		String hql = "from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0" +
				" and frp.flag=1 and frp.prcsUser.uuid=? order by "+sort+" "+order;
		List list = this.pageFind(hql,firstResult ,pageSize,new Object[]{person.getUuid()});
		return list;
	}
	
	public long getNoReceivedWorksCount(TeePerson person) {
		String hql = "select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0" +
		" and frp.flag=1 and frp.prcsUser.uuid=? ";
		return count(hql, new Object[]{person.getUuid()});
	}
	
	/**
	 * 获取已接受但未办理的工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public List<TeeFlowRunPrcs> getReceivedWorks(TeePerson person,
			int firstResult, int pageSize,String sort,String order) {
		String hql = "from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0" +
		" and frp.flag=2 and frp.prcsUser.uuid=? order by "+sort+" "+order;
		return this.pageFind(hql,firstResult ,pageSize,new Object[]{person.getUuid()});
	}
	
	public long getReceivedWorksCount(TeePerson person) {
		String hql = "select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0" +
		" and frp.flag=2 and frp.prcsUser.uuid=?";
		return count(hql, new Object[]{person.getUuid()});
	}

	/**
	 * 获取未办理的工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public List<TeeFlowRunPrcs> getNoHandledWorks(TeePerson person,
			int firstResult, int pageSize,String sort,String order) {
		String hql = "from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0" +
		" and frp.flag in (1,2) and frp.prcsUser.uuid=? order by "+sort+" "+order;
		return this.pageFind(hql,firstResult ,pageSize,new Object[]{person.getUuid()});
	}
	
	/**
	 * 获取未办理的工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public long getNoHandledWorksCount(TeePerson person) {
		String hql = "select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0" +
		" and frp.flag in (1,2) and frp.prcsUser.uuid=?";
		return this.count(hql,new Object[]{person.getUuid()});
	}

	/**
	 * 获取已办结的工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public List<TeeFlowRunPrcs> getHandledWorks(TeePerson person,
			int firstResult, int pageSize,String sort,String order) {
		Query query = this
				.getSession()
				.createQuery(
						"from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsId="
								+ "(select max(tmp.prcsId) from TeeFlowRunPrcs tmp where tmp.flowRun.runId=frp.flowRun.runId and tmp.prcsUser.uuid=frp.prcsUser.uuid and tmp.prcsUser.uuid=? ) and frp.flag in (3,4) "
								+ "order by "+sort+" "+order);
		query.setParameter(0, person.getUuid());
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	public long getHandledWorksCount(TeePerson person) {
		Query query = this
				.getSession()
				.createQuery(
						"select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsId="
								+ "(select max(tmp.prcsId) from TeeFlowRunPrcs tmp where tmp.flowRun.runId=frp.flowRun.runId and tmp.prcsUser.uuid=frp.prcsUser.uuid and tmp.prcsUser.uuid=? ) and frp.flag in (3,4) "
								+ "");
		query.setParameter(0, person.getUuid());
		return (Long) query.uniqueResult();
	}

	public List<TeeFlowRunPrcs> listByRunId(int runId){
		return find("from TeeFlowRunPrcs frp where frp.flowRun.runId=? order by frp.prcsId asc ,frp.topFlag desc", new Object[]{runId});
	}

	/**
	 * 拼接流程监控权限 语句
	 * 0 代表全部 如果有0则 不为全体部门
	 * 如果为空 则不拼接 此条件
	 * @author zhp
	 * @createTime 2013-10-5
	 * @editTime 下午03:07:47
	 * @desc
	 */
	protected String makeJobMonitorWhereSql(List list){
		StringBuffer sb = new StringBuffer("");
		String resultStr = "";
		if(list == null){
			return "";
		}
		boolean isHavaFlag =false;
		
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			int flowTypeId = (Integer)map.get("flowTypeId");
			String postDeptIds = (String)map.get("postDeptIds");
			if(TeeUtility.isNullorEmpty(postDeptIds)){
				continue;
			}
			if(postDeptIds.endsWith(",")){
				postDeptIds = postDeptIds.substring(0, postDeptIds.length() -1);
			}
			if("0".equals(postDeptIds)){
				sb.append("or ( frp.flowRun.flowType.sid = "+flowTypeId+") " );
				isHavaFlag = true;
			}else{
				sb.append("or ( frp.flowRun.flowType.sid = "+flowTypeId + " and  frp.prcsUser.dept.uuid in ("+postDeptIds+")) ");
				isHavaFlag = true;
			}
			if(isHavaFlag){
				resultStr = sb.toString();
				resultStr = resultStr.substring(2);
			}
		}
		
		return resultStr;
	}
	
	/**
	 * 获取流程监控数据
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午09:19:22
	 * @desc
	 */
	public List<TeeFlowRunPrcs> JobMonitorAllTypeList(String runName,
			int runId, int flagType, String pid,List deptWhereList,int firstResult,int pageSize,TeeDataGridModel md) {
		boolean runNameFlag = false;
		StringBuffer sb = new StringBuffer();
		sb.append("from TeeFlowRunPrcs frp where   frp.flag in (1,2) and frp.opFlag = 1 and frp.flowRun.delFlag=0 and frp.flowRun.endTime is null");
		
		if(!TeeUtility.isNullorEmpty(runName)){
			runNameFlag = true;
			sb.append(" and frp.flowRun.runName like '%"+runName+"%'");
		}
		if (runId != 0) {
			sb.append(" and frp.flowRun.runId  = " + runId);
		}
		if (!TeeUtility.isNullorEmpty(pid)) {
			if (pid.endsWith(",")) {
				pid = pid.substring(0, pid.length()-1);
			}
			
			if (flagType == 1) {
				sb.append(" and frp.prcsUser.uuid = " + pid);
				
			} else if (flagType == 2) {
				String runIds = getRunIds(pid);
				if (!TeeUtility.isNullorEmpty(runIds)) {
					sb.append(" and frp.flowRun.runId  in (" + runIds+")");
				}
			}
		}
		/**
		 * 拼接 全部流程 权限 sql
		 */
		String sWhere = "";
		sWhere = makeJobMonitorWhereSql(deptWhereList);
		if(TeeUtility.isNullorEmpty(sWhere)){
			return new ArrayList<TeeFlowRunPrcs>();// 没有权限查询流程数据
		}
			sb.append(" and ( " + sWhere + ") ");
		
		sb.append(" order by frp.sid " + md.getOrder());
		Query query = this.getSession().createQuery(sb.toString());

		 query.setFirstResult(firstResult);
		 query.setMaxResults(pageSize);
		return query.list();
	}
	
	/**
	 * 获取流程监控数据
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午09:19:22
	 * @desc
	 */
	public Long JobMonitorAllTypeListCount(String runName,
			int runId, int flagType, String pid,List deptWhereList,TeeDataGridModel md) {
		boolean runNameFlag = false;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from TeeFlowRunPrcs frp where   frp.flag in (1,2) and frp.opFlag = 1 and frp.flowRun.delFlag=0 and frp.flowRun.endTime is null");
		
		if(!TeeUtility.isNullorEmpty(runName)){
			runNameFlag = true;
			sb.append(" and frp.flowRun.runName = ?");
		}
		if (runId != 0) {
			sb.append(" and frp.flowRun.runId  = " + runId);
		}
		if (!TeeUtility.isNullorEmpty(pid)) {
			if (pid.endsWith(",")) {
				pid = pid.substring(0, pid.length()-1);
			}
			
			if (flagType == 1) {
				sb.append(" and frp.prcsUser.uuid = " + pid);
				
			} else if (flagType == 2) {
				String runIds = getRunIds(pid);
				if (!TeeUtility.isNullorEmpty(runIds)) {
					sb.append(" and frp.flowRun.runId  in (" + runIds+")");
				}
			}
		}
		/**
		 * 拼接 全部流程 权限 sql
		 */
		String sWhere = "";
		sWhere = makeJobMonitorWhereSql(deptWhereList);
		if(TeeUtility.isNullorEmpty(sWhere)){
			return 0L;// 没有权限查询流程数据
		}
		sb.append(" and ( " + sWhere + ") ");
		
		sb.append(" order by frp.sid " + md.getOrder());
		Query query = this.getSession().createQuery(sb.toString());

		if(runNameFlag){
			 query.setParameter(0, runName);
		}
		return  (Long)query.uniqueResult();
	}
	/**
	 * 获取流程监控数据
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午09:19:22
	 * @desc
	 */
	public List<TeeFlowRunPrcs> JobMonitorList(int flowTypeId, String runName,
			int runId, int flagType, String pid,String deptids,int firstResult,int pageSize,TeeDataGridModel md) {
		boolean runNameFlag = false;
		StringBuffer sb = new StringBuffer();
		//sb.append("select frp.flowRunfrom TeeFlowRunPrcs frp where   frp.flag in (1,2)");
//		if(flagType == 2){
//			sb.append("select  fr  from TeeFlowRunPrcs frp left join  frp.flowRun fr where   frp.flag in (1,2) and frp.opFlag = 1");
//		}else{
//		}
		sb.append("from TeeFlowRunPrcs frp where   frp.flag in (1,2) and frp.topFlag = 1 and frp.flowRun.endTime is null");
		if (flowTypeId == 0) {
			return new ArrayList<TeeFlowRunPrcs>();// 暂时处理全部查询情况
		} else {
			sb.append(" and frp.flowRun.flowType.sid = "+flowTypeId);
		}
		if(!TeeUtility.isNullorEmpty(runName)){
			runNameFlag = true;
			sb.append(" and frp.flowRun.runName like '%"+runName+"%'");
		}
		if (runId != 0) {
			sb.append(" and frp.flowRun.runId  = " + runId);
		}
		
		if(deptids.endsWith(",")){
			deptids = deptids.substring(0,deptids.length()-1);
		}
		if (!TeeUtility.isNullorEmpty(pid)) {
			if (pid.endsWith(",")) {
				pid = pid.substring(0, pid.length()-1);
			}
			
			if (flagType == 1) {
				sb.append(" and frp.prcsUser.uuid = " + pid);
				
			} else if (flagType == 2) {
				String runIds = getRunIds(pid,deptids);
				if (!TeeUtility.isNullorEmpty(runIds)) {
					sb.append(" and frp.flowRun.runId  in (" + runIds+")");
				}
			}
		}
	
		if(TeeUtility.isNullorEmpty(deptids)){//如果为空，则没有权限
			return new ArrayList<TeeFlowRunPrcs>();// 暂时处理全部查询情况
		}
		if(!TeeUtility.isNullorEmpty(deptids) && !"0".equals(deptids)){//不等于空且不等为0，等于0获取所有记录
			sb.append(" and frp.prcsUser.dept.uuid in( "+deptids+") ");
		}
		sb.append(" order by frp.sid " + md.getOrder());
		Query query = this.getSession().createQuery(sb.toString());

		if(runNameFlag){
			 query.setParameter(0, runName);
		}
		 query.setFirstResult(firstResult);
		 query.setMaxResults(pageSize);
		return query.list();
	}
	/**
	 * 获取流程监控总数量 用于分页
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午11:23:11
	 * @desc
	 */
	public long JobMonitorListCount(int flowTypeId, String runName,
			int runId, int flagType, String pid,String deptids) {
		boolean runNameFlag = false;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from TeeFlowRunPrcs frp where   frp.flag in (1,2) and frp.opFlag = 1 and frp.flowRun.endTime is null");
		if (flowTypeId == 0) {
			return 0;// 暂时处理全部查询情况
		} else {
			sb.append(" and frp.flowRun.flowType.sid = "+flowTypeId);
		}
		if(!TeeUtility.isNullorEmpty(runName)){
			runNameFlag = true;
			sb.append(" and frp.flowRun.runName = ?");
		}
		if (runId != 0) {
			sb.append(" and frp.flowRun.runId  = " + runId);
		}
		if(deptids.endsWith(",")){
			deptids = deptids.substring(0,deptids.length()-1);
		}
		if (!TeeUtility.isNullorEmpty(pid)) {
			if (pid.endsWith(",")) {
				pid = pid.substring(0, pid.length()-1);
			}
			
			
			if (flagType == 1) {//主办
				sb.append(" and frp.prcsUser = " + pid);
				
			} else if (flagType == 2) {
				String runIds = getRunIds(pid,deptids);
				if (!TeeUtility.isNullorEmpty(runIds)) {
					sb.append(" and frp.flowRun.runId  in (" + runIds+")");
				}
			}
		}
		if(TeeUtility.isNullorEmpty(deptids)){//如果为空，则没有权限
			return 0L;
		}
		if(!TeeUtility.isNullorEmpty(deptids) && !"0".equals(deptids)){//不等于空且不等为0，等于0获取所有记录
			sb.append(" and frp.prcsUser.dept.uuid in( "+deptids+") ");
		}
		Query query = this.getSession().createQuery(sb.toString());

		if(runNameFlag){
			 query.setParameter(0, runName);
		}
		return (Long) query.uniqueResult();
	}
	/**
	 * 获取 指定部门人员 为发起人的 runId 字符串 如：1，2，3
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午10:32:37
	 * @desc
	 */
	public String getRunIds(String pid,String deptids) {
		String hql = "from TeeFlowRunPrcs frp where frp.prcsId =1 and frp.prcsUser.uuid = "+ pid;
		if(!TeeUtility.isNullorEmpty(deptids) &&! "0".equals(deptids)){
			hql = hql + " and frp.prcsUser.dept.uuid in( "+deptids+")";
		}
		Query query = this.getSession().createQuery(hql);
		List<TeeFlowRunPrcs> list = query.list();
		boolean flag = false;
		String sids = "";
		for (int i = 0; i < list.size(); i++) {
			TeeFlowRunPrcs prc = list.get(i);
			int runId = prc.getFlowRun().getRunId();
			sids = sids + "," + runId;
			flag = true;
		}
		if (flag) {
			sids = sids.substring(1);
		}
		return sids;
	}
	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-5
	 * @editTime 下午03:53:31
	 * @desc
	 */
	public String getRunIds(String pid) {
		return getRunIds(pid,"0");
	}
	/**
	 * 获取已办结信息
	 * 
	 * @param runId
	 * @param uuid
	 * @return
	 */
	public List<TeeFlowRunPrcs> getExtraWorksByFlowRun(int runId, String uuid) {
		return super
				.find(
						"from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.prcsUser.uuid=? order by frp.sid asc",
						new Object[] { runId, uuid });
	}

	/**
	 * 查找flowrunprcs
	 * 
	 * @param runId
	 * @param flowId
	 * @param prcsId
	 * @param flowPrcs
	 * @param userUuid
	 * @return
	 */
	public TeeFlowRunPrcs findByComplex(int runId, int flowId, int prcsId,
			int flowPrcs, int userUuid) {
		Session session = super.getSession();
		Query query = session
				.createQuery(""
						+ "from TeeFlowRunPrcs as frp where frp.flowRun.runId=? and frp.prcsId=? and (frp.flowPrcs.prcsId=? or frp.flowPrcs is null) and frp.prcsUser.uuid=?");
		query.setParameter(0, runId);
		query.setParameter(1, prcsId);
		query.setParameter(2, flowPrcs);
		query.setParameter(3, userUuid);

		return (TeeFlowRunPrcs) query.uniqueResult();
	}

	/**
	 * 查找当前步骤 所有办理人的步骤集合
	 * 
	 * @param runId
	 * @param flowId
	 * @param prcsId
	 * @param flowPrcs
	 * @return
	 */
	public List<TeeFlowRunPrcs> findByComplex(int runId, int flowId,
			int prcsId, int flowPrcs) {
		Session session = super.getSession();
		Query query = session
				.createQuery(""
						+ "from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.prcsId=? and (frp.flowPrcs.prcsId=? or frp.flowPrcs is null) ");
		query.setParameter(0, runId);
		query.setParameter(1, prcsId);
		query.setParameter(2, flowPrcs);

		return (List<TeeFlowRunPrcs>) query.list();
	}

	/**
	 * 更新
	 * 
	 * @param hql
	 * @return
	 */
	public int updateByHql(String hql) {
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}

	public int updateAllPrcsUserState4(String hql) {
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		query.setCalendar(0, Calendar.getInstance());
		return query.executeUpdate();
	}

	public int updateCurrPrcsUserState2(String hql) {
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		query.setCalendar(0, Calendar.getInstance());
		return query.executeUpdate();
	}

	/**
	 * 查询本步骤经办人
	 * 
	 * @param runId
	 * @param flowPrcs
	 * @param prcsId
	 * @return
	 */
	public Set<TeePerson> getPersonsByCurPrcsUsers(int runId, int flowPrcs,
			int prcsId) {
		Session session = super.getSession();
		Query query = session
				.createQuery("select frp.prcsUser from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.prcsId=? and frp.flowPrcs.sid=?");
		query.setParameter(0, runId);
		query.setParameter(1, prcsId);
		query.setParameter(2, flowPrcs);
		List<TeePerson> list = query.list();
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.addAll(list);
		return set;
	}

	/**
	 * 查询某流程所有经办人
	 * 
	 * @param runId
	 * @param flowPrcs
	 * @param prcsId
	 * @return
	 */
	public Set<TeePerson> getPersonsByFlowRun(int runId) {
		Session session = super.getSession();
		Query query = session
				.createQuery("select frp.prcsUser from TeeFlowRunPrcs frp where frp.flowRun.runId=?");
		query.setParameter(0, runId);
		List<TeePerson> list = query.list();
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.addAll(list);
		return set;
	}

	/**
	 * 根据上一步骤选择经办人
	 * 
	 * @param frp
	 * @return
	 */
	public Set<TeePerson> getPersonsByPrePrcs(TeeFlowRunPrcs frp) {
		Session session = super.getSession();
		Query query = session
				.createQuery("select frp.prcsUser from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.prcsId=?");
		query.setParameter(0, frp.getFlowRun().getRunId());
		query.setParameter(1, frp.getPrcsId() - 1);
		List<TeePerson> list = query.list();
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.addAll(list);
		return set;
	}

	/**
	 * 获取制定流程的 所有流程信息
	 * 
	 * @author zhp
	 * @createTime 2013-10-1
	 * @editTime 下午05:59:10
	 * @desc
	 */
	public List<TeeFlowRunPrcs> getAllFlowRunPrcsByRunId(int runId) {
		Session session = super.getSession();
		Query query = session
				.createQuery(""
						+ "from TeeFlowRunPrcs frp where frp.flowRun.runId = ? order by frp.createTime asc");
		query.setParameter(0, runId);
		return (List<TeeFlowRunPrcs>) query.list();
	}
	
	/**
	 * 先接受为主办处理方法
	 * @param runId
	 * @param prcsId
	 */
//	public void firstReceiveFirstOpPrcs(int runId,int prcsId,int flowPrcsSid,int personUuid){
//		Session session  = super.getSession();
//		//查询出当前步骤实例中，已接收的步骤实例数量
//		Query query = session.createQuery("select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.runId = " + runId + " and frp.prcsId="+prcsId+" and (frp.flowPrcs.sid="+flowPrcsSid+" or frp.flowPrcs is null) and frp.topFlag = 1 and frp.opFlag = 3  ");
//		long count = (Long) query.uniqueResult();
//		if(count==0){//如果都没有接收并办理，则更新自己的经办权限状态
//			query = session.createQuery("update TeeFlowRunPrcs frp set frp.topFlag=1 where frp.flowRun.runId=? and frp.prcsId=? and frp.prcsUser.uuid=?");
//		}
//	}

	public static void main(String[] args) {
		System.out.println("123".substring(1));
	}
	
	/**
	 * 判断当前步骤下是否存在未办理完成的经办人，或者只存在一个未办理的人！
	 * @param runId
	 * @param prcsId
	 * @param flowPrcsSid
	 * @return
	 */
	public boolean checkFlowRunPrcsHasExistUnhandledPrcsUser(int runId,int prcsId,int flowPrcsSid){
		String hql = "select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.runId="+runId+" and frp.prcsId="+prcsId+" and (frp.flowPrcs.sid="+flowPrcsSid+" or frp.flowPrcs is null) and frp.flag in (1,2)";
		long count = count(hql, null);
		if(count > 1){
			return true;
		}
		return false;
	}
	
	/**
	 * @author syl
	 * 判断"先签收者为主办"是否已存在主办人会签
	 * @param runId
	 * @return
	 */
	public boolean checkFlowRunPrcsIsExtisHand(int runId,int prcsId,int flowPrcsSid) {
		String hql = "select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.runId = " + runId + " and frp.prcsId="+prcsId+" and (frp.flowPrcs.sid="+flowPrcsSid+" or frp.flowPrcs is null) and frp.topFlag = 1 and frp.opFlag = 3  ";
		long count = count(hql, null);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * @author syl
	 * 判断“无主办会签”是否是最后一个人会签
	 * @param runId   流程实例Id
	 * @param runPrcsId 步骤实例Id
	 * @return
	 */
	public boolean checkFlowRunPrcsIsLastHand(int runId,int prcsId,int flowPrcsSid) {
		String hql = " select count(*) from TeeFlowRunPrcs frp where frp.flowRun.runId = " + runId + " and frp.opFlag=2 and frp.prcsId="+prcsId+" and (frp.flowPrcs.sid="+flowPrcsSid+" or frp.flowPrcs is null) and frp.flag in (1,2,5) ";
		long count = count(hql, null);
		return count==1?true:false;
	}
	
	/**
	 *  @author syl
	 * 获取自由流程与转交下一步骤s
	 * @param flowRunPrcs
	 * @return
	 */
	public TeeFlowRunPrcs getFreeFlowAdvanceOverTurnNextPrcs(int currentPrcsId , int runId){
		String hql = "from TeeFlowRunPrcs where prcsId = " + (currentPrcsId + 1) + " and runId = " + runId + " and flag = 5";
		Session session = super.getSession();
		Query query = session.createQuery(hql);				
		TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) query.uniqueResult();
		return flowRunPrcs;
	}
	/**
	 * 获取 预设步骤
	 * @author zhp
	 * @createTime 2013-11-5
	 * @editTime 上午01:14:11
	 * @desc
	 */
	public List<TeeFlowRunPrcs> getFreeFlowPreFlowRunPrcs(int currentPrcsId , int runId){
		List<TeeFlowRunPrcs> list = new ArrayList<TeeFlowRunPrcs>();
		String hql = "from TeeFlowRunPrcs frp where  frp.flowRun.runId = " + runId + " order by frp.prcsId asc";
		Session session = super.getSession();
		Query query = session.createQuery(hql);				
		list = query.list();
		return list;
	}
	
	/**
	 * 获取流程最大实际步骤ID
	 * @param runId
	 * @return
	 */
	public int getMaxPrcsId(int runId){
		Object o = null;
		String hql = "select max(frp.prcsId) from TeeFlowRunPrcs frp where frp.flowRun.runId = "+runId+" and frp.flowPrcs.prcsType!=5";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		 o = 	query.uniqueResult();
		if( o == null){
			return 0;
		}
		return (Integer)o;
	
	}
	
	/**
	 * 自由流程获取最大步骤
	 * @author zhp
	 * @createTime 2013-11-19
	 * @editTime 下午09:27:22
	 * @desc
	 */
	public int getFreeFlowMaxPrcsId(int runId){
		Object o = null;
		String hql = "select max(frp.prcsId) from TeeFlowRunPrcs frp where frp.flowRun.runId = "+runId;
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		 o = 	query.uniqueResult();
		if( o == null){
			return 0;
		}
		return (Integer)o;
	
	}

	/**
	 * 根据并发节点步骤号，获取聚合节点的所有步骤实例集合
	 * @param runId
	 * @param parallelPrcsId
	 * @return
	 */
	public List<TeeFlowRunPrcs> getAggregationFlowRunPrcs(int runId,int parallelPrcsId){
		String hql = "from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.parallelPrcsId=? and frp.flowPrcs.prcsType=5";
		return this.find(hql, new Object[]{runId,parallelPrcsId});
	}
	
	/**
	 * 获取没有完结的并发步骤节点数量
	 * @param runId
	 * @param parallelPrcsId
	 * @return
	 */
	public long getNoHandledParallelFlowRunPrcs(int runId,int parallelPrcsId){
		String hql = "select count(*) from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.parallelPrcsId=? and frp.flag in (1,2) and frp.flowPrcs.prcsType!=5";
		return count(hql, new Object[]{runId,parallelPrcsId});
	}
	
	/**
	 * 更新该步骤之前步骤实例办理人
	 * @param runId
	 * @param parent
	 */
	public int updatePrePrcsUser(int runId,int prcsId,String parent){
		String hql = "update TeeFlowRunPrcs frp set frp.flag=4 where frp.flowRun.runId="+runId+" and frp.prcsId<"+prcsId+"";
		if(!"".equals(parent) && parent!=null){
			hql+=" and frp.flowPrcs.sid in ("+parent+")";
		}
		
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}
	
	/**
	 * 获取全部经办人的uuids
	 * @param runId
	 * @return
	 */
	public List<Integer> getAllPrcsUsersUuid(int runId){
		String hql = "select distinct(frp.prcsUser.uuid) from TeeFlowRunPrcs frp where frp.flowRun.runId=?";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, runId);
		return query.list();
	}
	
	/**
	 * 获取全部经办人的uuids
	 * @param runId
	 * @return
	 */
	public List<TeePerson> getAllPrcsUsers(int runId){
		List<Integer> uuids = getAllPrcsUsersUuid(runId);
		List<TeePerson> persons = new ArrayList();
		for(int uuid:uuids){
			persons.add(personDao.get(uuid));
		}
		
		return persons;
	}
	
	/**
	 * 获取当前步骤经办人uuids
	 * @param runId
	 * @param prcsId
	 * @param flowPrcs
	 * @return
	 */
	public List<Integer> getCurrentPrcsUsersUuid(int runId,int prcsId,int flowPrcs){
		String hql = "select distinct(frp.prcsUser.uuid) from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.prcsId=? and (frp.flowPrcs.sid=? or frp.flowPrcs is null)";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, runId);
		query.setParameter(1, prcsId);
		query.setParameter(2, flowPrcs);
		return query.list();
	}
	
	/**
	 * 某流程是否存在未办理完的工作
	 * @param runId
	 * @return
	 */
	public boolean hasExsitUnhandedWorks(int runId){
		String hql = "select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.flag in (1,2,5)";
		long c = count(hql, new Object[]{runId});
		if(c>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取某一步骤的主办步骤实例
	 * @param prcsId
	 * @return
	 */
	public TeeFlowRunPrcs getOpFlowRunPrcs(int prcsId){
		String hql = "from TeeFlowRunPrcs frp where frp.prcsId=? and frp.topFlag=1";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, prcsId);
		return (TeeFlowRunPrcs) query.uniqueResult();
	}
	
	/**
	 * 获取未被接收的代理工作
	 * @param frpSid
	 * @return
	 */
	public List<Integer> getNoReceivedDelegatedWorks(int frpSid){
		List<Integer> list = new ArrayList<Integer>();
		Session session = this.getSession();
		//先获取该步骤对应的办理人ID和步骤ID
		Query query = session.createQuery("select frp.prcsUser.uuid,frp.prcsId,frp.flowRun.runId from TeeFlowRunPrcs frp where frp.sid="+frpSid);
		Object[] params = (Object[]) query.uniqueResult();
		int prcsUser = TeeStringUtil.getInteger(params[0], 0);
		int prcsId = TeeStringUtil.getInteger(params[1], 0);
		int runId = TeeStringUtil.getInteger(params[2], 0);
		
		//再查找出当前步骤下没有被接收的代理工作
		query = session.createQuery("select frp.sid,frp.flag from TeeFlowRunPrcs frp where (frp.otherUser.uuid="+prcsUser+" or frp.fromUser.uuid="+prcsUser+") and frp.prcsId="+prcsId+" and frp.flag=1 and frp.flowRun.runId="+runId);
		list = query.list();
		return list;
	}
	
	/**
	 * 更新当前步骤实例的办理标识为已接收
	 * @param frpSid
	 */
	public void updateFlowRunPrcsPrcsFlag2NoHandled(int frpSid){
		Session session = this.getSession();
		Query query = session.createQuery("update TeeFlowRunPrcs frp set frp.flag=2,frp.endTime=null where frp.sid="+frpSid);
		query.executeUpdate();
	}
	
	/**
	 * 更新当前步骤实例的办理标识为已接收并且是主办
	 * @param frpSid
	 */
	public void updateFlowRunPrcsPrcsFlagAndTopFlag2NoHandled(int frpSid){
		Session session = this.getSession();
		Query query = session.createQuery("update TeeFlowRunPrcs frp set frp.flag=2,frp.topFlag=1,frp.endTime=null,frp.endTimeStamp=0 where frp.sid="+frpSid);
		query.executeUpdate();
	}
	
	public boolean checkCurrentWorkTakebackPriv(int frpSid){
		Session session = this.getSession();
		Query query = session.createQuery("select frp.flag from TeeFlowRunPrcs frp where frp.sid="+frpSid);
		int flag = TeeStringUtil.getInteger(query.uniqueResult(), 0);
		if(flag==4){
			return false;
		}
		return true;
	}
	
	/**
	 * 删除下一步骤的所有工作
	 * @param frpSid
	 */
	public void deleteNextPrcs(int frpSid){
		Session session = this.getSession();
		//先获取当前步骤的prcsId和flowPrcsId
		Query query = session.createQuery("select frp.prcsId,frp.flowPrcs.sid,frp.flowRun.runId from TeeFlowRunPrcs frp where frp.sid="+frpSid);
		Object[] params = (Object[]) query.uniqueResult();
		int prcsId = TeeStringUtil.getInteger(params[0], 0);
		int flowPrcsId = TeeStringUtil.getInteger(params[1], 0);
		int runId = TeeStringUtil.getInteger(params[2], 0);
		
		query = session.createQuery("delete from TeeFlowRunPrcs frp where frp.prcsId="+(prcsId+1)+" and frp.flowRun.runId="+(runId)+" and (frp.flowPrcs is null or frp.parent like '"+flowPrcsId+"')");
		query.executeUpdate();
	}
	
	/**
	 * 获取某流程的唯一子流程
	 * @param runId
	 * @param flowPrcsId
	 * @return
	 */
	public List<TeeFlowRun> getChildFlowRuns(int runId){
		Session session = this.getSession();
		Query query = session.createQuery("from TeeFlowRun fr where fr.pRunId="+runId);
		return query.list();
	}

	
	/**
	 * 抢占式办理  谁先接收谁为主办，并删除掉当前步骤下其他经办人的任务
	 * @param frp
	 */
	public void deleteOtherPrcs(TeeFlowRunPrcs frp) {
		Session session = this.getSession();
		Query query = session.createQuery("delete from TeeFlowRunPrcs frp where frp.prcsId="+frp.getPrcsId()+" and frp.flowRun.runId="+frp.getFlowRun().getRunId()+" and frp.flowPrcs.sid="+frp.getFlowPrcs().getSid()+"  and  frp.topFlag=0 ");
		query.executeUpdate();	
	}
	
}
