package com.tianee.oa.core.workflow.flowrun.service;

import java.util.Map;

import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeRunRelServiceInterface {

	/**
	 * 获取计划
	 * @param requestData
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson listSchedule(Map requestData,
			TeeDataGridModel dm);

	/**
	 * 获取任务
	 * @param requestData
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson listTask(Map requestData,
			TeeDataGridModel dm);

	/**
	 * 获取客户
	 * @param requestData
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson listCustomer(Map requestData,
			TeeDataGridModel dm);

	/**
	 * 获取流程
	 * @param requestData
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson listFlowRun(Map requestData,
			TeeDataGridModel dm);

	public abstract void addScheduleRel(String scheduleId, int targetRunId);

	public abstract void delScheduleRel(String relUuid);

	public abstract TeeEasyuiDataGridJson listScheduleRel(Map requestData,
			TeeDataGridModel dm);

	public abstract void addTaskRel(int taskId, int targetRunId);

	public abstract void delTaskRel(String relUuid);

	public abstract TeeEasyuiDataGridJson listTaskRel(Map requestData,
			TeeDataGridModel dm);

	public abstract void addCustomerRel(int customerId, int targetRunId);

	public abstract void delCustomerRel(String relUuid);

	public abstract TeeEasyuiDataGridJson listCustomerRel(Map requestData,
			TeeDataGridModel dm);

	public abstract void addFlowRunRel(int runId, int targetRunId);

	public abstract void delFlowRunRel(String relUuid);

	public abstract TeeEasyuiDataGridJson listFlowRunRel(Map requestData,
			TeeDataGridModel dm);

	/**
	 * 获取相关数据的数量
	 * @param runId
	 * @return
	 */
	public abstract Map getDataCounts(int runId);

}