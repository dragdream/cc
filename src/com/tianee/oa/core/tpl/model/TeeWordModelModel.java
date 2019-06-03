package com.tianee.oa.core.tpl.model;

public class TeeWordModelModel {
	private int sid;
	private String modelName;
	private String modelDesc;
	private int attachId;
	private String fileName;
	private int privRanges;
	private int sortNo;
	private String wordModelType;//套红类型
	private String userPrivIds;
	private String userPrivNames;
	private String deptPrivIds;
	private String deptPrivNames;
	private String rolePrivIds;
	private String rolePrivNames;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelDesc() {
		return modelDesc;
	}
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}
	public int getPrivRanges() {
		return privRanges;
	}
	public void setPrivRanges(int privRanges) {
		this.privRanges = privRanges;
	}
	public String getUserPrivIds() {
		return userPrivIds;
	}
	public void setUserPrivIds(String userPrivIds) {
		this.userPrivIds = userPrivIds;
	}
	public String getUserPrivNames() {
		return userPrivNames;
	}
	public void setUserPrivNames(String userPrivNames) {
		this.userPrivNames = userPrivNames;
	}
	public String getDeptPrivIds() {
		return deptPrivIds;
	}
	public void setDeptPrivIds(String deptPrivIds) {
		this.deptPrivIds = deptPrivIds;
	}
	public String getDeptPrivNames() {
		return deptPrivNames;
	}
	public void setDeptPrivNames(String deptPrivNames) {
		this.deptPrivNames = deptPrivNames;
	}
	public String getRolePrivIds() {
		return rolePrivIds;
	}
	public void setRolePrivIds(String rolePrivIds) {
		this.rolePrivIds = rolePrivIds;
	}
	public String getRolePrivNames() {
		return rolePrivNames;
	}
	public void setRolePrivNames(String rolePrivNames) {
		this.rolePrivNames = rolePrivNames;
	}
	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}
	public int getAttachId() {
		return attachId;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	public String getWordModelType() {
		return wordModelType;
	}
	public void setWordModelType(String wordModelType) {
		this.wordModelType = wordModelType;
	}
}
