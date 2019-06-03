package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;

public interface TeeFlowInfoChartServiceInterface {



	/**
	 * 获取图表需要的 步骤信息
	 * @author zhp
	 * @createTime 2013-10-1
	 * @editTime 下午06:12:27
	 * @desc
	 */
	public abstract List loadAllPrcsInfo(int runId);

	public abstract List getFlowRunChildData(int runId);

}