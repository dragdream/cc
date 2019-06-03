package com.tianee.oa.core.workflow.flowrun.service;

import java.util.Map;

import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeDataSelServiceInterface {

	/**
	 * 获取数据列表
	 * @param itemSid
	 * @param runId
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getDataList(int itemSid, int runId,
			Map<String, String> formDatas);

	/**
	 * 获取元数据
	 * @param itemSid
	 * @param runId
	 * @return
	 */
	public abstract Map<String, String> getMetaData(int itemSid, int runId,
			Map<String, String> formDatas);

}