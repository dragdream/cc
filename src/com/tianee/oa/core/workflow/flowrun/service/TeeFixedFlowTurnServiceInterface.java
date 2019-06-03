package com.tianee.oa.core.workflow.flowrun.service;

import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;

public interface TeeFixedFlowTurnServiceInterface {

	/**
	 * 结束流程
	 * @param requestData
	 * @param loginPerson
	 */
	public abstract void turnEnd(Map requestData, TeePerson loginPerson);

	public abstract void setContext(TeeWorkFlowServiceContextInterface context);

	public abstract TeeWorkFlowServiceContextInterface getContext();

	public abstract void setSimpleDaoSupport(
			TeeSimpleDaoSupport simpleDaoSupport);

	public abstract TeeSimpleDaoSupport getSimpleDaoSupport();

	public abstract void preTurnNext(Map requestData);

	/**
	 * turnModel转交模型：
	 * [{prcsTo:'2',opFlag:'1',opUser:'1',prcsUsers:'1,2,3,4',parallelNode:'2'}]
	 */
	public abstract void turnNext(Map requestData, TeePerson loginPerson);

}