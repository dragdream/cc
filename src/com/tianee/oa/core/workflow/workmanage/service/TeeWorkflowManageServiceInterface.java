package com.tianee.oa.core.workflow.workmanage.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeWorkflowManageServiceInterface {

	/**
	 * 流程管理，结束流程
	 * @param runId
	 * @return
	 */
	public abstract TeeFlowRun endRun(int runId);

	/**
	 * 流程管理，恢复流程
	 * @param runId
	 * @return
	 */
	public abstract TeeFlowRun recoverRun(int runId);

	/**
	 * 流程管理，删除流程
	 * @param runId
	 * @return
	 */
	public abstract TeeFlowRun delRun(int runId);

	/**
	 * 流程管理，销毁流程
	 * @param runId
	 * @return
	 */
	public abstract TeeFlowRun destroyRun(int runId);

	/**
	 * 委托代理
	 * @param frpSid
	 * @param delegateTo
	 * @return
	 */
	public abstract TeeFlowRunPrcs flowRunDelegate(int frpSid, int delegateTo);

	/**
	 * 收回委托
	 * @param frpSid
	 */
	public abstract void takebackDelegate(int frpSid);

	/**
	 * 工作移交
	 */
	public abstract void handover(String flowIds, int fromUser, int toUser,
			Calendar beginTime, Calendar endTime, int beginRunId, int endRunId,
			TeePerson loginUser);

	/**
	 * 收回工作
	 * @param frpSid
	 */
	public abstract void takebackWorks(int frpSid);

	/**
	 * 获取可被委托的人员ID
	 * @param frpSid
	 * @return
	 */
	public abstract List<Integer> getDelegatedUserFilters(int frpSid,
			TeePerson loginPerson);

	/**
	 * 获取委托处理数据，前端数据
	 * @param frpSid
	 * @param loginPerson
	 * @return
	 */
	public abstract Map getDelegateHandlerData(int frpSid, TeePerson loginPerson);


	/**
	 * 工作查询  批量删除流程
	 * @param runIds
	 * @return
	 */
	public abstract TeeJson delRunBatch(String runIds);

}