package com.tianee.oa.core.workFlowFrame.dataloader;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;

public interface TeeWorkFlowDataFactoryInterface {

	/**
	 * 暂时认为获取表单处理数据的逻辑是相同的！
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public abstract Map getHandlerData(Map requestData, TeePerson loginPerson);

	/**
	 * 获取流程的之前步骤
	 * @param frpSid
	 * @param loginPerson
	 * @return
	 */
	public abstract List<Map> getPrePrcsList(int frpSid, TeePerson loginPerson);

	/**
	 * 获取指定回退的步骤
	 * @param frpSid
	 * @param runId
	 * @param flowId
	 * @param person
	 * @return
	 */
	public abstract Object getBackPrcs(int frpSid, int flowId, int runId,
			TeePerson person);

	/**
	 * 获取该步骤的直属上一步骤的已走过的步骤，专属与固定流程
	 * @param request
	 * @return
	 */
	public abstract List<Map> getPreReachablePrcsList(int frpSid,
			TeePerson loginPerson);

	/**
	 * 获取经办人过滤串
	 * @param request
	 * @return
	 */
	public abstract String getPrcsUserFilter(int frpSid, TeePerson loginPerson);

	/**
	 * 获取当前步骤经办人
	 * @param request
	 * @return
	 */
	public abstract List<Map> getCurPrcsUsers(int frpSid, TeePerson loginPerson);

	public abstract Map getTurnHandlerData(Map requestData,
			TeePerson loginPerson);

	/**
	 * 获取流程最新版本的表单
	 * @return
	 */
	public abstract TeeForm getLatestForm(TeeFlowType flowType);

	/**
	 * 获取流程runName
	 * @author zhp
	 * @createTime 2013-10-23
	 * @editTime 下午08:32:26
	 * @desc
	 */
	public abstract String getFlowRunName(TeeFlowType flowType);



}