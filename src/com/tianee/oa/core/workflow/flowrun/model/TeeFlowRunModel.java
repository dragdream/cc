package com.tianee.oa.core.workflow.flowrun.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeFlowRunModel {

	private String runId;//流水号
	private String runName;//流程名称
	private List<TeeAttachmentModel> attachmentModels;
	
	
	public String getRunId() {
		return runId;
	}
	public void setRunId(String runId) {
		this.runId = runId;
	}
	public String getRunName() {
		return runName;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public List<TeeAttachmentModel> getAttachmentModels() {
		return attachmentModels;
	}
	public void setAttachmentModels(List<TeeAttachmentModel> attachmentModels) {
		this.attachmentModels = attachmentModels;
	}
}
