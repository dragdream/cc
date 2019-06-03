package com.tianee.oa.subsys.supervise.model;


public class TeeSupervisionTypeModel {
	
	private int sid;//自增id
	
	private String typeName ;//类型名称
	
	private int orderNum ;//排序号
	
	//private TeeSupervisionType parent;//父分类
	
	private String parentTypeName;//父分类名称
	
	private int parentTypeSid;//父分类主键
	
	private String userNames;
	
	private String userIds;
	
	private String roleNames;
	
	private String roleIds;
	
	private String deptNames;
	
	private String deptIds;
	
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptName(String deptNames) {
		this.deptNames = deptNames;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
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
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getParentTypeName() {
		return parentTypeName;
	}
	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}
	public int getParentTypeSid() {
		return parentTypeSid;
	}
	public void setParentTypeSid(int parentTypeSid) {
		this.parentTypeSid = parentTypeSid;
	}
	
}
