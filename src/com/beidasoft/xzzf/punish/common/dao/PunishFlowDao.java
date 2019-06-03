package com.beidasoft.xzzf.punish.common.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beetl.ext.fn.ParseInt;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PunishFlowDao extends TeeBaseDao<PunishFLow> {

	public List<PunishFLow> getPunishFlowInfo(int firstResult, int rows, int sid) {
		String hql = "from PunishFLow";
		return super.pageFind(hql, firstResult, rows, null);
	}

	/**
	 * 环节对应流程信息
	 * @param lawLinkId 
	 */
	public List<PunishFLow> getFlowcase(String baseId, String confFlowName, String lawLinkId) {
		String hql = " from PunishFLow where 1=1 ";

		hql += " and baseId= '" + baseId + "' ";

		if (StringUtils.isNotBlank(confFlowName)) {
			hql += " and confFlowName like '%" + confFlowName + "%' ";
		}
		if (StringUtils.isNotBlank(lawLinkId)) {
			hql += " and tacheId ='" + lawLinkId + "' ";
		}
		hql += " order by time desc";

		return super.find(hql, null);

	}

	/**
	 * 执法文书案件目录序号排序及baseid
	 */
	public List<PunishFLow> getManageCase(String baseId) {
		String hql = " from PunishFLow where 1=1 ";
		hql += " and baseId= '" + baseId + "' ";
		hql += " order by contentsDate asc";
		return super.find(hql, null);
	}
	
	/**
	 * 查询案件的所有文书
	 * @param baseId
	 * @return
	 */
	public List<PunishFLow> getByBaseId(String baseId) {
		String hql = " from PunishFLow where 1=1 ";
		hql += " and baseId= '" + baseId + "' ";
		hql += " order by time asc";
		return super.find(hql, null);
	}
	/**
	 * 环节对应流程信息
	 */
	public List<PunishFLow> getFlowcase(String baseId, int flowId) {
		String hql = " from PunishFLow where baseId='" + baseId
				+ " ' and confFlowId = " + flowId;
		return super.find(hql, null);
	}

	/**
	 * 根据id获取
	 * 
	 * @param runId
	 * @return
	 */
	public PunishFLow getByRunId(String runId) {
		Session session = this.getSession();
		String hql = "from PunishFLow where 1=1 ";
		hql += " and punishRunId = " + runId;
		PunishFLow flow = (PunishFLow) session.createQuery(hql).uniqueResult();
		// PunishFLow flow = executeQuery(hql, null);
		return flow;
	}
    /**
     * 根据id和流程名字获取
     * @param runId
     * @param ConfFlowName
     * @param confFlowName 
     * @return
     */
	public PunishFLow getByRunIdOrconfFlowName(String runId , String confFlowName) {
		Session session = this.getSession();
		String hql = "from PunishFLow where 1=1 ";
		hql += " and punishRunId = " + runId;
		hql += " and confFlowName = '" + confFlowName +"'";
		PunishFLow flow = (PunishFLow) session.createQuery(hql).uniqueResult();
		// PunishFLow flow = executeQuery(hql, null);
		return flow;
	}
	/**
	 * 获取当前环节的所有文书
	 * @param baseId
	 * @param tacheId
	 * @param userId
	 * @return
	 */
	public List<PunishFLow> getFlowByTacheId(String baseId, String tacheId, int userId) {
		String hql = " from PunishFLow where baseId='" + baseId + "' and tacheId = '" + tacheId + "'";
		
		if(userId != 0) {
			hql = hql + " and majorId = " + userId;
		}				
		return super.find(hql + " order by time", null);
	}

	/**
	 * 获取当前环节的所有文书
	 * @param baseId
	 * @param tacheId
	 * @param userId
	 * @return
	 */
	public List<PunishFLow> getFlowAssign(String baseId, String tacheId, int userId) {
		String hql = " from PunishFLow where baseId='" + baseId + "' and tacheId = '" + tacheId + "'";
		
		if(userId != 0) {
			hql = hql + " and majorId <> " + userId;
		}				
		return super.find(hql + " order by time", null);
	}

	public PunishFLow getFlowByRunIdOrFlowName(String runId, String flowName) {
		Session session = this.getSession();
		int runId2 = Integer.parseInt(runId);
		String hql = " from PunishFLow where punishRunId =" + runId2 + " and confFlowName = '" + flowName + "'";
		
		PunishFLow flow = (PunishFLow) session.createQuery(hql).uniqueResult();
		return flow;
	}

	public List<PunishFLow> getListByRunId(String runId) {
		String hql = "from PunishFLow where punishRunId ='" + runId + "' ";
		return super.find(hql, null);
	}
}