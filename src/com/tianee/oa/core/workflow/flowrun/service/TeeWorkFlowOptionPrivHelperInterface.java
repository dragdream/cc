package com.tianee.oa.core.workflow.flowrun.service;

import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;

public interface TeeWorkFlowOptionPrivHelperInterface {

	/**
	 * 判断是否显示转交 会签 
	 *  return 0 为不可以转办
	 *  return 1 为可以转办
	 *  return 2 为可以会签
	 */
	public abstract int checkPrivTurnNext(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 判断是否可以回退
	 * 0 为不可回退
	 * 1 为可以回退
	 */
	public abstract int checkPrivTurnBack(TeeFlowType flowType);

	public abstract int checkPrivTurnBack(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 判断是否可以结束流程
	 * 0 可以结束流程
	 * 1 不可以结束流程
	 */
	public abstract int checkPrivTurnend(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 初始化操作权限
	 * 
	 */
	public abstract Map initworkHandlerOptionPriv(TeeFlowRunPrcs flowRunPrcs);

}