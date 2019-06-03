package com.tianee.oa.subsys.contract.model;

public class TeeContractCategoryModel {
	private int sid;
	private String name;
	private String viewPrivIds;
	private String viewPrivNames;
	private String managePrivIds;
	private String managePrivNames;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getViewPrivIds() {
		return viewPrivIds;
	}
	public void setViewPrivIds(String viewPrivIds) {
		this.viewPrivIds = viewPrivIds;
	}
	public String getViewPrivNames() {
		return viewPrivNames;
	}
	public void setViewPrivNames(String viewPrivNames) {
		this.viewPrivNames = viewPrivNames;
	}
	public String getManagePrivIds() {
		return managePrivIds;
	}
	public void setManagePrivIds(String managePrivIds) {
		this.managePrivIds = managePrivIds;
	}
	public String getManagePrivNames() {
		return managePrivNames;
	}
	public void setManagePrivNames(String managePrivNames) {
		this.managePrivNames = managePrivNames;
	}
	
	
}
