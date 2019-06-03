package com.tianee.oa.core.workflow;

import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.customnumber.service.TeeCusNumberService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunLogServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowFeedBackServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowOptionPrivHelperInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;

public interface TeeWorkFlowServiceContextInterface {

	public abstract void setFlowFormService(
			TeeFlowFormServiceInterface flowFormService);

	public abstract void setFlowProcessService(
			TeeFlowProcessServiceInterface flowProcessService);

	public abstract void setFlowSortService(
			TeeFlowSortServiceInterface flowSortService);

	public abstract void setFlowTypeService(
			TeeFlowTypeServiceInterface flowTypeService);

	public abstract void setFormSortService(
			TeeFormSortServiceInterface formSortService);

	public abstract void setFlowRunService(TeeFlowRunServiceInterface flowRunService);

	public abstract void setFlowRunPrcsService(
			TeeFlowRunPrcsServiceInterface flowRunPrcsService);

	public abstract void setTeeWorkflowHelper(
			TeeWorkflowHelperInterface teeWorkflowHelper);

	public abstract void setFlowOptionPrivHelper(
			TeeWorkFlowOptionPrivHelperInterface flowOptionPrivHelper);

	public abstract void setPersonService(TeePersonService personService);

	public abstract TeeFlowFormServiceInterface getFlowFormService();

	public abstract TeeFlowProcessServiceInterface getFlowProcessService();

	public abstract TeeFlowSortServiceInterface getFlowSortService();

	public abstract TeeFlowTypeServiceInterface getFlowTypeService();

	public abstract TeeFormSortServiceInterface getFormSortService();

	public abstract TeeFlowRunServiceInterface getFlowRunService();

	public abstract TeeFlowRunPrcsServiceInterface getFlowRunPrcsService();

	public abstract TeeWorkflowHelperInterface getTeeWorkflowHelper();

	public abstract TeeWorkFlowOptionPrivHelperInterface getFlowOptionPrivHelper();

	public abstract TeePersonService getPersonService();

	public abstract void setWorkflowService(TeeWorkflowServiceInterface workflowService);

	public abstract TeeWorkflowServiceInterface getWorkflowService();

	public abstract void setFlowRunLogService(
			TeeFlowRunLogServiceInterface flowRunLogService);

	public abstract TeeFlowRunLogServiceInterface getFlowRunLogService();

	public abstract void setFeedbackService(
			TeeWorkFlowFeedBackServiceInterface feedbackService);

	public abstract TeeWorkFlowFeedBackServiceInterface getFeedbackService();

	public abstract void setFlowRunViewService(
			TeeFlowRunViewServiceInterface flowRunViewService);

	public abstract TeeFlowRunViewServiceInterface getFlowRunViewService();

	public abstract TeeAttachmentService getAttachService();

	public abstract void setAttachService(TeeAttachmentService attachService);

	public abstract TeeFlowRunDocServiceInterface getFlowRunDocService();

	public abstract void setFlowRunDocService(
			TeeFlowRunDocServiceInterface flowRunDocService);

	public abstract TeeCusNumberService getCusNumberService();

	public abstract void setCusNumberService(
			TeeCusNumberService cusNumberService);

}