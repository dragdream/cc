package com.tianee.oa.core.workflow.flowrun.model;


public class TeeFlowRunAipTemplateModel {

	private int sid;//主键

	//private TeeFlowRun flowRun;//关联流程
    private int runId;
    private String runName;
	//private TeeFlowPrintTemplate template;//关联模板
    private int templateId;
    private String templateName;
	//private TeeAttachment attachment;//关联附件
    private int attachId;
    private String attachName;
    private String attachExt;
	public int getSid() {
		return sid;
	}
	public int getRunId() {
		return runId;
	}
	public String getRunName() {
		return runName;
	}
	public int getTemplateId() {
		return templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public int getAttachId() {
		return attachId;
	}
	public String getAttachName() {
		return attachName;
	}
	public String getAttachExt() {
		return attachExt;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public void setAttachExt(String attachExt) {
		this.attachExt = attachExt;
	}
    
    
    
}
