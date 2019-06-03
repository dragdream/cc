package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunLogModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeFlowRunLogServiceInterface {

	public abstract void save(TeeFlowRunLog frl);

	/**
	 * 自动保存日志，只需传入FlowRunPrcs步骤实例
	 * @param frp
	 * @param frl
	 */
	public abstract void saveLogAutomatically(TeeFlowRunPrcs frp,
			TeeFlowRunLog frl);

	/**
	 * 查询流程日志（未完成）
	 * @param frl
	 * @param datagridModel
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson datagrid(TeeFlowRunLog frl,
			TeeDataGridModel datagridModel);

	/**
	 * 获取流程日志列表
	 * @param runId
	 * @return
	 */
	public abstract List<TeeFlowRunLogModel> getFlowRunLogs(int runId);


}