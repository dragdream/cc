package com.tianee.oa.core.workflow.flowrun.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;

@Repository
public class TeeFlowRunDao extends TeeBaseDao<TeeFlowRun> {

	public int getDealCount(int userId, int flowId,StringBuffer managerHql) {
		
//		 String hql =
//		 "select count(flowRun.runId) from TeeFlowRun flowRun where flowRun.delFlag=0 AND flowRun.flowType="+flowId+" and flowRun.endTime is null and ((exists (select 1 from TeeFlowRunPrcs frp where frp.flowRun=flowRun and frp.prcsUser.uuid="+userId+")) or (flowRun.beginPerson.uuid="+userId+") or (exists (select 1 from TeeFlowRunConcern frc where frc.flowRun=flowRun and frc.person.uuid="+userId+")) or (exists (select 1 from TeeFlowRunView frv where frv.flowRun=flowRun and frv.viewPerson.uuid="+userId+")) )";
		
		String hql = "select count(fr.runId) from TeeFlowRun as fr "
				+ " where fr.delFlag=0 and fr.isSave=1 and fr.flowType.sid="
				+ flowId + " and  fr.endTime is null ";
		hql+=managerHql.toString();
		long total = count(hql, null);
		return TeeStringUtil.getInteger(total, 0);
		
	}                          

	public int getOverCount(int userId, int flowId,StringBuffer managerHql) {
		String hql = "select count(fr.runId) from TeeFlowRun fr where fr.delFlag=0 and fr.isSave=1 AND fr.flowType="
				+ flowId
				+ " and fr.endTime is not null ";
		hql+=managerHql.toString();
		long total = count(hql, null);
		return TeeStringUtil.getInteger(total, 0);
	}

}
