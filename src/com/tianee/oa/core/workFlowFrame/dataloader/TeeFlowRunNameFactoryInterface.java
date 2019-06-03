package com.tianee.oa.core.workFlowFrame.dataloader;

public interface TeeFlowRunNameFactoryInterface {

	/**
	 * 获取默认的runName
	 * 
	 * @author zhp
	 * @createTime 2013-10-22
	 * @editTime 下午08:42:18
	 * @desc
	 */
	public String getDefaultRunName(String flowName);

	/**
	 * 获取流程 runName
	 * @author zhp
	 * @createTime 2013-10-23
	 * @editTime 下午08:31:09
	 * @desc
	 */
	public String getFlowRunName(String flowName);

}