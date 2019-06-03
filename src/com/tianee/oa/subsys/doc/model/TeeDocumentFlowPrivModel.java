package com.tianee.oa.subsys.doc.model;

public class TeeDocumentFlowPrivModel {
	private String uuid;//自增id
	private int flowId;
	private String flowName;
	private String privDeptIds;
	private String privDeptNames;
	private String privRoleIds;
	private String privRoleNames;
	private String privUserIds;
	private String privUserNames;
	public String getPrivRoleIds() {
		return privRoleIds;
	}
	public void setPrivRoleIds(String privRoleIds) {
		this.privRoleIds = privRoleIds;
	}
	public String getPrivRoleNames() {
		return privRoleNames;
	}
	public void setPrivRoleNames(String privRoleNames) {
		this.privRoleNames = privRoleNames;
	}
	public String getPrivUserIds() {
		return privUserIds;
	}
	public void setPrivUserIds(String privUserIds) {
		this.privUserIds = privUserIds;
	}
	public String getPrivUserNames() {
		return privUserNames;
	}
	public void setPrivUserNames(String privUserNames) {
		this.privUserNames = privUserNames;
	}
	private String fieldMapping = "{}";//json格式字段映射     {表单字段1:内置字段,表单字段2:内置字段}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getPrivDeptIds() {
		return privDeptIds;
	}
	public void setPrivDeptIds(String privDeptIds) {
		this.privDeptIds = privDeptIds;
	}
	public String getPrivDeptNames() {
		return privDeptNames;
	}
	public void setPrivDeptNames(String privDeptNames) {
		this.privDeptNames = privDeptNames;
	}
	public String getFieldMapping() {
		return fieldMapping;
	}
	public void setFieldMapping(String fieldMapping) {
		this.fieldMapping = fieldMapping;
	}
	
	
}
