package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowRunServiceInterface {

	public abstract TeeFlowRun get(int runId);

	/**
	 * 删除
	 */
	public abstract void delete(TeeFlowRun flowRun);

	/**
	 * 删除
	 */
	public abstract void delete(int runId);

	public abstract void save(TeeFlowRun flowRun);

	/**
	 * 更新
	 */
	public abstract void update(TeeFlowRun flowRun);

	public abstract List<TeeFlowRun> getChildFlowRuns(int runId);

	//清空数据
	public abstract void clearRunDatasService(int flowId);

	public abstract TeeEasyuiDataGridJson getFlowRunListByFlowId(HttpServletRequest request,TeeDataGridModel dataGridModel,TeeFlowRunModel queryModel);

	public abstract TeeJson copyBodyAttachement(HttpServletRequest request);
    
	//数据同步  发文管理
	public abstract TeeJson dataSync(HttpServletRequest request);

	//数据管理  签报管理
	public abstract TeeJson dataSync1(HttpServletRequest request);

	//根据frpSid判断当前步骤是否允许归档
	public abstract TeeJson checkIsArchive(HttpServletRequest request);
    //根据runId获取flowType的文档类型
	public abstract TeeJson getDocTypeByRunId(HttpServletRequest request);

	public abstract TeeJson findPrcsId(HttpServletRequest request);

	public abstract TeeJson updateEndTimeByPrcsId(HttpServletRequest request);


}