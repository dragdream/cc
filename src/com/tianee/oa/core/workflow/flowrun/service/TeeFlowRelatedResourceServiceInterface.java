package com.tianee.oa.core.workflow.flowrun.service;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowRelatedResourceServiceInterface {

	/**
	 * 相关流程列表 (我发起的 或者经我办理的) 分页处理
	 * @param dm
	 * @param object
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson relatedFlowRunList(
			TeeDataGridModel dm, HttpServletRequest request);

	/**
	 * 增加相关资源
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeJson add(TeeDataGridModel dm, HttpServletRequest request);

	/**
	 * 根据流程id 和 type   获取相关资源信息
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeJson getRelatedResourceByRunId(TeeDataGridModel dm,
			HttpServletRequest request);

	/**
	 * 相关任务列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson relatedTaskList(TeeDataGridModel dm,
			HttpServletRequest request);

	/**
	 * 相关客户列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson relatedCustomerList(
			TeeDataGridModel dm, HttpServletRequest request);

	/**
	 * 相关项目  （我创建的    我参与的）
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson relatedProjectList(
			TeeDataGridModel dm, HttpServletRequest request);

	/**
	 * 根据主键删除相关资源
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeJson delBySid(HttpServletRequest request);

}