package com.beidasoft.xzzf.task.caseInterface.dao;

import java.util.List;

import com.beidasoft.xzzf.task.taskAppointed.model.CaseAppointedInfoModel;
import com.tianee.webframe.dao.TeeBaseDao;

public class CaseInfoProgressDao extends TeeBaseDao<Object>{

	/**
	 * 查询全部案件信息
	 */
	public void getCaseEnforceInfo(int firstResult,int rows, CaseAppointedInfoModel queryModel) {
		String hql = "select ci.id,ci.baseId,ci.organizationId,ci.createTime,ci.taskType,ci.taskRec,ci.taskRecId,ci.taskRecName,ci.taskName,"
				+ "ci.taskSendPersonId,ci.taskSendPersonName,ci.taskComments,ci.dealType,ci.extraComments,ci.isDelete,ci.taskSendTime "
				+ "from CaseAppointedInfo ci,CaseHandPerson cp where ci.baseId=cp.baseId";
	//	return super.pageFind(hql,null);
	}

	public void getCaseInfoProgress(String apointedSql) {
		// TODO Auto-generated method stub
		
	}
}
