package com.tianee.oa.core.workFlowFrame.creator;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workFlowFrame.TeeWorkFlowFactoryContextInterface;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.model.TeeBisRunModel;

public interface TeeFlowCreatorInterface {

	/**
	 * 创建流程
	 * @param ft
	 * @param person
	 * @param bisRunModel
	 * @return
	 */
	public abstract TeeFlowRunPrcs CreateNewWork(TeeFlowType ft,
			TeePerson person, TeeBisRunModel bisRunModel, boolean validation);

	/**
	 * @param ft
	 * @param person
	 * @return
	 */
	public abstract TeeFlowRunPrcs CreateNewWork(TeeFlowType ft,
			TeePerson person, TeeBisRunModel bisRunModel);

	public abstract void setServiceContext(
			TeeWorkFlowServiceContextInterface serviceContext);

	public abstract TeeWorkFlowServiceContextInterface getServiceContext();

	public abstract TeeWorkFlowFactoryContextInterface getFactoryContext();

	public abstract void setFactoryContext(
			TeeWorkFlowFactoryContextInterface factoryContext);

}