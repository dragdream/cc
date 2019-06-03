package com.tianee.oa.core.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.tianee.webframe.service.TeeBaseService;

/**
 * 工作流 Context 类
 *  此状态对象为多立
 *  主要每一个流程请求都会创建一个TeeWorkFlowServiceContext 对象，
 *  该对象提供各种针对工作流的服务接口  包括流程的状态信息 配置 全局变量等
 *  
 *  说明 需要在每个接口中 反向持有TeeWorkFlowServiceContext 引用
 * @author zhp
 *
 */
@Service
public class TeeWorkFlowServiceContext extends TeeBaseService implements TeeWorkFlowServiceContextInterface{
 
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	@Autowired
	private TeeFlowProcessServiceInterface flowProcessService;
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeService;
	@Autowired
	private TeeFormSortServiceInterface formSortService;
	@Autowired
	private TeeFlowRunServiceInterface flowRunService;
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	@Autowired
	private TeeWorkflowHelperInterface teeWorkflowHelper;
	@Autowired
	private TeeWorkFlowOptionPrivHelperInterface flowOptionPrivHelper;
	@Autowired
	private TeePersonService personService;
	@Autowired
	private TeeWorkflowServiceInterface workflowService;
	@Autowired
	private TeeFlowRunLogServiceInterface flowRunLogService;
	@Autowired
	private TeeWorkFlowFeedBackServiceInterface feedbackService;
	@Autowired
	private TeeAttachmentService attachService;
	@Autowired
	private TeeFlowRunViewServiceInterface flowRunViewService;
	@Autowired
	private TeeFlowRunDocServiceInterface flowRunDocService;
	@Autowired
	private TeeCusNumberService cusNumberService;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowFormService(com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface)
	 */
	@Override
	public void setFlowFormService(TeeFlowFormServiceInterface flowFormService) {
		this.flowFormService = flowFormService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowProcessService(com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface)
	 */
	@Override
	public void setFlowProcessService(TeeFlowProcessServiceInterface flowProcessService) {
		this.flowProcessService = flowProcessService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowSortService(com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface)
	 */
	@Override
	public void setFlowSortService(TeeFlowSortServiceInterface flowSortService) {
		this.flowSortService = flowSortService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowTypeService(com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface)
	 */
	@Override
	public void setFlowTypeService(TeeFlowTypeServiceInterface flowTypeService) {
		this.flowTypeService = flowTypeService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFormSortService(com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface)
	 */
	@Override
	public void setFormSortService(TeeFormSortServiceInterface formSortService) {
		this.formSortService = formSortService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowRunService(com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunService)
	 */
	@Override
	public void setFlowRunService(TeeFlowRunServiceInterface flowRunService) {
		this.flowRunService = flowRunService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowRunPrcsService(com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsService)
	 */
	@Override
	public void setFlowRunPrcsService(TeeFlowRunPrcsServiceInterface flowRunPrcsService) {
		this.flowRunPrcsService = flowRunPrcsService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setTeeWorkflowHelper(com.tianee.oa.util.workflow.TeeWorkflowHelperInterface)
	 */
	@Override
	public void setTeeWorkflowHelper(TeeWorkflowHelperInterface teeWorkflowHelper) {
		this.teeWorkflowHelper = teeWorkflowHelper;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowOptionPrivHelper(com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowOptionPrivHelper)
	 */
	@Override
	public void setFlowOptionPrivHelper(
			TeeWorkFlowOptionPrivHelperInterface flowOptionPrivHelper) {
		this.flowOptionPrivHelper = flowOptionPrivHelper;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setPersonService(com.tianee.oa.core.org.service.TeePersonService)
	 */
	@Override
	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowFormService()
	 */
	@Override
	public TeeFlowFormServiceInterface getFlowFormService() {
		return flowFormService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowProcessService()
	 */
	@Override
	public TeeFlowProcessServiceInterface getFlowProcessService() {
		return flowProcessService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowSortService()
	 */
	@Override
	public TeeFlowSortServiceInterface getFlowSortService() {
		return flowSortService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowTypeService()
	 */
	@Override
	public TeeFlowTypeServiceInterface getFlowTypeService() {
		return flowTypeService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFormSortService()
	 */
	@Override
	public TeeFormSortServiceInterface getFormSortService() {
		return formSortService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowRunService()
	 */
	@Override
	public TeeFlowRunServiceInterface getFlowRunService() {
		return flowRunService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowRunPrcsService()
	 */
	@Override
	public TeeFlowRunPrcsServiceInterface getFlowRunPrcsService() {
		return flowRunPrcsService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getTeeWorkflowHelper()
	 */
	@Override
	public TeeWorkflowHelperInterface getTeeWorkflowHelper() {
		return teeWorkflowHelper;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowOptionPrivHelper()
	 */
	@Override
	public TeeWorkFlowOptionPrivHelperInterface getFlowOptionPrivHelper() {
		return flowOptionPrivHelper;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getPersonService()
	 */
	@Override
	public TeePersonService getPersonService() {
		return personService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setWorkflowService(com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowService)
	 */
	@Override
	public void setWorkflowService(TeeWorkflowServiceInterface workflowService) {
		this.workflowService = workflowService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getWorkflowService()
	 */
	@Override
	public TeeWorkflowServiceInterface getWorkflowService() {
		return workflowService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowRunLogService(com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunLogService)
	 */
	@Override
	public void setFlowRunLogService(TeeFlowRunLogServiceInterface flowRunLogService) {
		this.flowRunLogService = flowRunLogService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowRunLogService()
	 */
	@Override
	public TeeFlowRunLogServiceInterface getFlowRunLogService() {
		return flowRunLogService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFeedbackService(com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowFeedBackServic)
	 */
	@Override
	public void setFeedbackService(TeeWorkFlowFeedBackServiceInterface feedbackService) {
		this.feedbackService = feedbackService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFeedbackService()
	 */
	@Override
	public TeeWorkFlowFeedBackServiceInterface getFeedbackService() {
		return feedbackService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowRunViewService(com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewService)
	 */
	@Override
	public void setFlowRunViewService(TeeFlowRunViewServiceInterface flowRunViewService) {
		this.flowRunViewService = flowRunViewService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowRunViewService()
	 */
	@Override
	public TeeFlowRunViewServiceInterface getFlowRunViewService() {
		return flowRunViewService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getAttachService()
	 */
	@Override
	public TeeAttachmentService getAttachService() {
		return attachService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setAttachService(com.tianee.oa.core.attachment.service.TeeAttachmentService)
	 */
	@Override
	public void setAttachService(TeeAttachmentService attachService) {
		this.attachService = attachService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getFlowRunDocService()
	 */
	@Override
	public TeeFlowRunDocServiceInterface getFlowRunDocService() {
		return flowRunDocService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setFlowRunDocService(com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocService)
	 */
	@Override
	public void setFlowRunDocService(TeeFlowRunDocServiceInterface flowRunDocService) {
		this.flowRunDocService = flowRunDocService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#getCusNumberService()
	 */
	@Override
	public TeeCusNumberService getCusNumberService() {
		return cusNumberService;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface#setCusNumberService(com.tianee.oa.core.customnumber.service.TeeCusNumberService)
	 */
	@Override
	public void setCusNumberService(TeeCusNumberService cusNumberService) {
		this.cusNumberService = cusNumberService;
	}
	
	
	
}
