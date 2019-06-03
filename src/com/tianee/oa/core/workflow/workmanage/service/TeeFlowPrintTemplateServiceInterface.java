package com.tianee.oa.core.workflow.workmanage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPrintTemplate;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowPrintTemplateServiceInterface {

	public abstract TeeFlowPrintTemplate addOrUpdateTpl(
			TeeFlowPrintTemplateModel model, HttpServletRequest request);

	/**
	 * 更新设计模版
	 * @param model
	 * @param request
	 * @return
	 */
	public abstract TeeFlowPrintTemplate updateModulDesigner(
			TeeFlowPrintTemplateModel model, HttpServletRequest request);

	/**
	 * 更新 更换地城模版
	 * @param model
	 * @param request
	 * @return
	 */
	public abstract TeeFlowPrintTemplate updateModul(
			TeeFlowPrintTemplateModel model, HttpServletRequest request);

	/**
	 * 
	 * @param request
	 * @return
	 */
	public abstract TeeFlowPrintTemplateModel getById(HttpServletRequest request);

	/**
	 * 获取节点基本信息
	 * @param request
	 * @return
	 */
	public abstract TeeJson getByIdInfo(HttpServletRequest request);

	/**
	 * 
	 * @param request
	 * @return
	 */
	public abstract void delById(String sid);

	/**
	 * 根据流程Id  获取 打印模版
	 * @param request
	 * @return
	 */
	public abstract List<TeeFlowPrintTemplateModel> selectModulByFlowType(
			String flowTypeId);

	/**
	 * 获取可打印模版  自由流都可以打印      固定流根据步骤权限设置
	 * @author syl
	 * @date 2013-11-24
	 * @param ft  流程对象
	 * @param fp 流程步骤
	 * @return
	 */
	public abstract List<TeeFlowPrintTemplateModel> selectModulByFlowTypeAndPrcsId(
			TeeFlowType ft, TeeFlowProcess fp);

	/**
	 * 获取可打印模版  自由流都可以打印      固定流根据步骤权限设置
	 * @author syl
	 * @date 2013-11-24
	 * @param ft  流程对象
	 * @param fp 流程步骤
	 * @return
	 */
	public abstract List<TeeFlowPrintTemplateModel> selectModulByFlowTypeAndPrcsId(
			int flowId, int type, int flowPrcsSid);

	public abstract List<TeeFlowPrintTemplateModel> selectModulByFlowRunPrcs(
			int frpSid);


	/**
	 * 渲染AIP签批模板
	 * @param request
	 * @return
	 */
	public abstract TeeJson renderTemplate(HttpServletRequest request);

}