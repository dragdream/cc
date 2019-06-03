package com.tianee.oa.core.workFlowFrame.validator;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;

public interface TeeWorkFlowBasicInfoValidatorServiceInterface {

	/**
	 * 判断当前流程实例步骤是否第一步
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public abstract boolean flowRunPrcsIsFirstValide(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 判断当前流程实例步骤是否已存在主办人会签-------主要考虑"先签收者为主办"
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public abstract boolean flowRunPrcsIsExtisTopFlagValide(
			TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 判断流程实例步骤或者流程实例是否被删除
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public abstract boolean flowRunIsDelValide(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 判断流程实例步骤是否已转交 或者已结束
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public abstract boolean flowRunPrcsIsTurnValide(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 判断流程实例步骤是否是当前登录人办理
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public abstract boolean flowRunPrcsIsCurrentLoginUserValide(
			TeePerson person, TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 判断当前流程实例步骤是否是最后一个人会签-------主要考虑"无主办会签"
	 * @param flowtype
	 * @return
	 */
	public abstract boolean flowRunPrcsIsLasthandValide(
			TeeFlowRunPrcs flowRunPrcs);

	public abstract TeeFlowRunPrcsDao getFlowRunPrcsDao();

	public abstract void setFlowRunPrcsDao(TeeFlowRunPrcsDao flowRunPrcsDao);

	public abstract void setFlowPrivValidatorService(
			TeeWorkFlowPrivValidatorServiceInterface flowPrivValidatorService);

	public abstract void setWorkFlowHeplper(
			TeeWorkflowHelperInterface workFlowHeplper);

}