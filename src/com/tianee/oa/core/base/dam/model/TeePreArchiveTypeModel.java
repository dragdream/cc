package com.tianee.oa.core.base.dam.model;


public class TeePreArchiveTypeModel {

	private int sid;
	
	private String typeName;//分类名称
	
	private int sortNo;//排序号

	//private TeePerson manager;//档案管理员
	private int managerId;//档案管理员主键
	private String managerName;//管理员名称
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	
	
}
