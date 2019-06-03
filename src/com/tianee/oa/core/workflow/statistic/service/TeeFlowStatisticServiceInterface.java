package com.tianee.oa.core.workflow.statistic.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeFlowStatisticServiceInterface {

	/**
	 * 超时统计图形
	 * @param request
	 * @return
	 */
	public abstract List<Map> timeoutStatistic(Map requestData);

	/**
	 * 流程处理情况
	 * @param request
	 * @return
	 */
	public abstract List<Map> handleStatistic(Map requestData);

	/**
	 * 流程办理情况
	 * @param request
	 * @return
	 */
	public abstract List<Map> handle0Statistic(Map requestData);

	/**
	 * 获取统计的流程数据
	 * @param requestData
	 * @param loginUser
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getStatisticFlowData(Map requestData,
			TeePerson loginUser, TeeDataGridModel dm);

}