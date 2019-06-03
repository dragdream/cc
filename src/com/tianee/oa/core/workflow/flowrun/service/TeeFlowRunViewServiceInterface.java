package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunView;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunViewModel;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowRunViewServiceInterface {

	/**
	 * 添加流程查阅
	 * @param flowRunView
	 */
	public abstract void addFlowRunView(TeeFlowRunView flowRunView);

	/**
	 * 流程查阅
	 * @param runId
	 * @param prcsId
	 * @param flowPrcs
	 * @param personUuid
	 */
	public abstract void viewLookup(int runId, int personUuid);

	/**
	 * 流程催办
	 * @param runId
	 * @param personUuid
	 */
	public abstract void flowRunUrge(int runId, TeePerson loginPerson);

	/**
	 * 获取查阅情况列表
	 * @param runId
	 * @return
	 */
	public abstract List<TeeFlowRunView> getViewsList(int runId);

	/**
	 * 获取查阅情况模型列表
	 * @param runId
	 * @return
	 */
	public abstract List<TeeFlowRunViewModel> getViewsModelList(int runId);

	/**
	 * 获取查阅情况模型列表
	 * @param runId
	 * @return
	 */
	public abstract TeeAttachmentModel getDocModel(int runId);


	/**
	 * 检查会签意见必填
	 * @param runId
	 * @param loginPerson
	 * @param frpSid
	 * @return
	 */
	public abstract TeeJson checkFbRequired(int runId, TeePerson loginPerson,
			int frpSid);

}