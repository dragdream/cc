package com.tianee.oa.core.workflow.flowrun.service;

public interface TeeFlowSeqServiceInterface {

	/**
	 * 获取流程类型编号
	 * @param flowId
	 * @return
	 */
	public abstract int getFlowTypeNumbering(int flowId);

	/**
	 * 删除编号
	 * @param flowId
	 */
	public abstract void deleteFlowTypeNumbering(int flowId);

	/**
	 * 生成流程类型编号
	 * @param flowId
	 * @return
	 */
	public abstract int generateFlowTypeNumbering(int flowId);

	/**
	 * 创建流程类型编号
	 * @param flowId
	 */
	public abstract void createFlowTypeNumbering(int flowId);

}