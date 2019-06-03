package com.tianee.oa.core.workflow.workmanage.service;

import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeWorkDestroyServiceInterface {

	/**
	 * 查询
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson query(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);

	/**
	 * 流程销毁（单独一条）
	 * @param runId
	 */
	public abstract TeeFlowRun destroy(int runId);

	/**
	 * 流程还原
	 * @param runId
	 */
	public abstract TeeFlowRun restore(int runId);

	public abstract void setSimpleDaoSupport(
			TeeSimpleDaoSupport simpleDaoSupport);


}