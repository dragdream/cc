package com.tianee.oa.core.workflow.workmanage.model;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeFlowDocTemplateModel {
	private int sid;
	//private TeeFlowType flowType;//所属流程
	private int flowTypeId;
	private String templateName;//模板名称
	private String pluginClassPath;//插件类路径
	//private TeeAttachment attach;//模板附件
	private int attachId;
	private String attachName;
	
	private TeeAttachmentModel attachModel;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getPluginClassPath() {
		return pluginClassPath;
	}

	public void setPluginClassPath(String pluginClassPath) {
		this.pluginClassPath = pluginClassPath;
	}

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public TeeAttachmentModel getAttachModel() {
		return attachModel;
	}

	public void setAttachModel(TeeAttachmentModel attachModel) {
		this.attachModel = attachModel;
	}
	
	
	
	
}
