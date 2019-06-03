package com.tianee.oa.subsys.doc.model;

public class TeeDocumentRecMappingModel {
	private String uuid;//自增id
	private int deptId;
	private String deptName;
	private String privUserIds;
	private String privUserNames;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	
}
