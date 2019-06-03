package com.tianee.oa.core.workflow.flowrun.service;

import javax.servlet.http.HttpServletRequest;

import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowRunAipTemplateServiceInterface {

	/**
	 * 判断当前模板的中间附件是否存在  不存在则生成   存在则取出来
	 * @param request
	 * @return
	 */
	public TeeJson isExist(HttpServletRequest request);

	/**
	 * 根据流程id查是否存在关联的签批单
	 * @param request
	 * @return
	 */
	public TeeJson getListByRunId(HttpServletRequest request);

	public TeeJson getListByRunId(int runId);

}