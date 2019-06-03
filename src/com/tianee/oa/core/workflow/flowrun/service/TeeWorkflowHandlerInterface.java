package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeWorkflowHandlerInterface {

	/**
	 * 获取转交数据
	 * 判断流程步骤实例类型，topFlag
	 * 设置当前步骤实例flag状态标志
	 * 设置上一步骤
	 * @param runId
	 * @param flowId
	 * @param prcsId
	 * @param flowPrcs
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson getHandlerData(int runId, int frpSid,
			TeePerson loginUser);

	/**
	 * 获取编辑状态下的HTML模型
	 * @param runId
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson getEditHtmlModel(int runId, TeePerson loginUser);

	/**
	 * 表单预览
	 * @param runId
	 * @param flowId
	 * @param prcsId
	 * @param flowPrcs
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson getFormPrintExplore(int formId, TeePerson loginUser);

	/**
	 * 历史版本表单预览
	 * @param runId
	 * @param flowId
	 * @param prcsId
	 * @param flowPrcs
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson getHistoryFormPrintExplore(int formId,
			TeePerson loginUser);

	/**
	 * 自由流程转交
	 * @param runId 流程id
	 * @param flowId 
	 * @param prcsId
	 * @param flowPrcs
	 * @param opUsers
	 * @param prcsUsers
	 * @param person
	 * @return
	 */
	public abstract TeeJson turnNextFreeFlow(int runId, int flowId, int prcsId,
			int flowPrcs, String opUsers, String prcsUsers, TeePerson person);

	public abstract TeeJson turnEndFreeFlow(int runId, int flowId, int prcsId,
			int flowPrcs, TeePerson person);

	public abstract TeeJson countersignHandler(int runId, int flowId,
			int prcsId, int flowPrcs, TeePerson person);

	public abstract void updateAllPrcsUserState4(int runId, int prcsId);

	/***
	 * 更新上一步骤的办理状态 为4
	 * @param prcslist
	 * @return
	 */
	public abstract void updatePrePrcsUserState4(int runId, int prcsId);

	/**
	 * 更新当前步骤 当前办理人状态为 2
	 * @param runId
	 * @param prcsId
	 */
	public abstract void updateCurrPrcsUserState2(int runId, int prcsId,
			String personUuid);

	public abstract void updateRunName(int runId, String runName);

	public abstract void updateRunLevel(int runId, int level);

	/**
	 * 更新当前人办理状态为 3
	 * @param runId
	 * @param prcsId
	 * @param personUuid
	 */
	public abstract void updateCurrPrcsUserState3(int runId, int prcsId,
			String personUuid);

	/**
	 * 更新上一步骤所有办理人 状态为3
	 * @param frps
	 * @return
	 */
	public abstract void updatePrePrcsUserState3(int runId, int prcsId);

	/**
	 * 插入下一步骤 
	 * @param currFrp
	 * @param opUser
	 * @return
	 */
	public abstract boolean insetNextPrcsOpUser(TeeFlowRunPrcs currFrp,
			String opUser);

	/**
	 *插入下一步骤 经办人步骤集合
	 * @return
	 */
	public abstract List<TeeFlowRunPrcs> insetNextPrcs(TeeFlowRunPrcs currFrp,
			String prcsUsers);

	/**
	 * 获取流程操作数据
	 * @param runId
	 * @param flowId
	 * @param prcsId
	 * @param flowPrcs
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson getHandlerOptPrivData(int runId, int frpSid,
			TeePerson loginUser);

	/**
	 * 更新当前办理人状态为 办理中
	 * @param frp
	 * @return
	 */
	public abstract boolean updateCurrPrcsUserState(TeeFlowRunPrcs frp);

	/**
	 * 判断当前步骤是否可办理
	 * @param json
	 * @param frp
	 * @return
	 */
	public abstract boolean isHandleable(TeeJson json, TeeFlowRunPrcs frp);

	/**
	 * 获取表单办理中的HTML模型
	 * @param fieldCtrlModel
	 * @return
	 */
	public abstract String getFormHandleHtmlModel(TeeFlowType flowType,
			TeeForm form, TeeFlowRunPrcs frp, Map<String, String> datas,
			List<TeeFormItem> formItems, TeePerson loginUser);

	/**
	 * 获取表单编辑状态下的HTML模型
	 * @param flowRun
	 * @param datas
	 * @return
	 */
	public abstract String getFormEditHtmlModel(TeeFlowRun flowRun,
			Map<String, String> datas);

	/**
	 * 获取表单打印中的HTML办理模型
	 * @param fieldCtrlModel
	 * @return
	 */
	public abstract String getFormPrintHtmlModel(TeeFlowType flowType,
			TeeForm form, TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas, List<TeeFormItem> formItems,
			TeePerson loginUser);

	/**
	 * 处理宏标记
	 * @param flowRun
	 * @return
	 */
	public abstract String handlerMacroTag(TeeFlowRun flowRun, String pattern);

	/**
	 * 处理宏标记   新增会签意见宏标记
	 * @param flowRun
	 * @return
	 */
	public abstract String handlerMacroTag1(TeeFlowRun flowRun, String pattern,
			TeeFlowRunPrcs flowRunPrcs, TeePerson loginUser);

	public abstract TeeFormItem findFormItemByItemId(int itemId,
			List<TeeFormItem> items);

	/**
	 * 保存流程数据表单
	 * @param itemId
	 * @param items
	 */
	public abstract void saveFlowRunData(int runId, int flowId, int frpSid,
			Map datas, Map requestParam);

	/**
	 * 保存流程数据表单
	 * @param itemId
	 * @param items
	 */
	public abstract void editFlowRunData(int runId, int flowId, int frpSid,
			Map datas);

	public abstract TeeFormItem findFormItemByItemId(String id,
			List<TeeFormItem> items);

	public abstract TeeFormItem findFormItemByItemTitle(String title,
			List<TeeFormItem> items);

	/**
	 * 流程传阅----每个过程都查阅
	 */
	public abstract TeeJson view(HttpServletRequest request);

	public abstract TeeJson getInfosByFrpSid(HttpServletRequest request);

	/**
	 * 修改流程的保存状态
	 * @param runId
	 */
	public abstract void updateSaveStatus(int runId);

	/**
	 * 工作监控  ------ 流程干预
	 * @param request
	 * @return
	 */
	public abstract TeeJson intervention(HttpServletRequest request);

	//处理列表控件   将列表控件中的数据插入中间表
	public abstract void saveXlistData(int runId, int flowId, Map requestData);

	/**
	 * 根据runId获取流程类型id
	 * @param runId
	 * @return
	 */
	public abstract int getFlowIdByRunId(int runId);

}