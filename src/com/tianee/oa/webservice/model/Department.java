package com.tianee.oa.webservice.model;

public class Department {
	private int id;
	private String deptName;
	private int parentId;
	private int deptNo;
	private int deptType = 1;
	private String deptFullPath;
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getDeptFullPath() {
		return deptFullPath;
	}
	public void setDeptFullPath(String deptFullPath) {
		this.deptFullPath = deptFullPath;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public int getDeptType() {
		return deptType;
	}
	public void setDeptType(int deptType) {
		this.deptType = deptType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
