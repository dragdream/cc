package com.tianee.oa.core.workflow.workmanage.service;

import javax.servlet.http.HttpServletRequest;

import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowSeniorQueryTemplateServiceInterface {

	/**
	 * 新建   编辑
	 * @param request
	 * @return
	 */
	public abstract TeeJson addOrUpdate(HttpServletRequest request);

	/**
	 * 根据当前登陆人   流程id  获取相关的模板
	 * @param request
	 * @return
	 */
	public abstract TeeJson renderTempalte(HttpServletRequest request);

	/**
	 * 根据主键   获取详情
	 * @param request
	 * @return
	 */
	public abstract TeeJson getInfoBySid(HttpServletRequest request);

}