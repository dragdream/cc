package com.tianee.oa.subsys.crm.core.base.model;
/**
 * 销售组
 * 
 * @author think
 * 
 */

public class TeeCrmSalesGroupModel {

	private int sid;// Sid 主键
	private int parentId;//上级ID
	private String parentName;//上级ID
	private String parentPath;//上级全路径
	private String groupName;// 销售组名称
	private int groupOrder;// 排序
	private int managerUserId;//负责人
	private String managerUserName;//
	private String managerMemberIds;//成员Ids
	private String managerMemberNames ;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupOrder() {
		return groupOrder;
	}
	public void setGroupOrder(int groupOrder) {
		this.groupOrder = groupOrder;
	}
	public int getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(int managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getManagerUserName() {
		return managerUserName;
	}
	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	public String getManagerMemberIds() {
		return managerMemberIds;
	}
	public void setManagerMemberIds(String managerMemberIds) {
		this.managerMemberIds = managerMemberIds;
	}
	public String getManagerMemberNames() {
		return managerMemberNames;
	}
	public void setManagerMemberNames(String managerMemberNames) {
		this.managerMemberNames = managerMemberNames;
	}
	

	

}
