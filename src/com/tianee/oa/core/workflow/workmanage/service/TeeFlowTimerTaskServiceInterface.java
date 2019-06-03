package com.tianee.oa.core.workflow.workmanage.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowTimerTask;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowTimerTaskModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeFlowTimerTaskServiceInterface {

	public abstract String getFlowName(int flowTypeId);

	public abstract TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			int userId, int flowId);

	public abstract TeeFlowTimerTaskModel getFlowTimerTaskById(int sid);

	/**
	 * 获取可执行的任务
	 * @return
	 */
	public abstract List<TeeFlowTimerTask> listExecutableTask();

	/**
	 * 自动处理可执行任务，包括发起短信提醒
	 */
	public abstract void autoProcessingExecutableTask();

	public abstract void update(TeeFlowTimerTask task);

	public abstract TeeFlowTimerTask get(int sid);

	public abstract void add(TeeFlowTimerTask task);

	public abstract void deleteById(int sid);

}