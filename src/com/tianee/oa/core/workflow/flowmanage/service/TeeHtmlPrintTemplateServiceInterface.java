package com.tianee.oa.core.workflow.flowmanage.service;

import javax.servlet.http.HttpServletRequest;

import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeHtmlPrintTemplateServiceInterface {

	/**
	 * 添加/编辑Html打印模板
	 * @param request
	 * @return
	 */
	public abstract TeeJson addOrUpdate(HttpServletRequest request);

	/**
	 *根据flowTypeId获取打印模板
	 * @param request
	 * @return
	 */
	public abstract TeeJson list(HttpServletRequest request);

	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public abstract TeeJson getInfoBySid(HttpServletRequest request);

	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	public abstract TeeJson deleteBySid(HttpServletRequest request);

	/**
	 * 根据流程类型获取所有的表单项
	 * @param request
	 * @return
	 */
	public abstract TeeJson getBasicFormItemsByFlowType(
			HttpServletRequest request);

	/**
	 * 打印模板设计
	 * @param request
	 * @return
	 */
	public abstract TeeJson updateTplContent(HttpServletRequest request);

	/**
	 * 根据runId  获取相关的HTML打印模板
	 * @param request
	 * @return
	 */
	public abstract TeeJson listByRunId(HttpServletRequest request);

	/**
	 * html打印预览
	 * @param request
	 * @return
	 */
	public abstract String printExplore(HttpServletRequest request);

}