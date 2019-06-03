package com.tianee.oa.core.workFlowFrame.insertor;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;

public interface TeeWorkFlowStartInsertorInterface {

	/**
	 * 流程创建 插入flowrun表 和flowrunPrcs表记录
	 * @author zhp
	 * @createTime 2013-10-23
	 * @editTime 下午09:25:04
	 * @desc
	 */
	public TeeFlowRunPrcs insertFlowData(TeeFlowType ft, TeePerson person,
			TeeForm form, String flowRunName, String businessKey);

	public TeeWorkFlowServiceContextInterface getServiceContext();

	public void setServiceContext(
			TeeWorkFlowServiceContextInterface serviceContext);

}