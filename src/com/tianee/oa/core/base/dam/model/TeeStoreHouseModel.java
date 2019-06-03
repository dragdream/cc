package com.tianee.oa.core.base.dam.model;





public class TeeStoreHouseModel{
	private int sid;
	
	private int orderNum;//排序号
	
	private String roomCode;//卷库号
	
	private String roomName;//卷库名称
	
	private String userIds;
	private String userNames;//所属人员
	
	private String deptIds;
	private String deptNames;//所属部门
	
	private String roleIds;
	private String roleNames;//所属角色
	
	private int createUserId;//创建人id
	
	private String createUserName;//创建人姓名
	
	private String createTimeStr;//创建时间
	
	private int parentId;//父Id
	private String parentName;//父卷库名称
	
	public String remark;//备注

	//private TeePerson borrowManager;//借閲管理员 
    private int borrowManagerId;//借阅管理员主键
    private String borrowManagerName;//借阅管理员姓名
    
    
    private int isDocDir;//是否是系统内置归档文件夹
    
    
    
    
    
	public int getIsDocDir() {
		return isDocDir;
	}


	public void setIsDocDir(int isDocDir) {
		this.isDocDir = isDocDir;
	}


	public int getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}


	public int getBorrowManagerId() {
		return borrowManagerId;
	}


	public void setBorrowManagerId(int borrowManagerId) {
		this.borrowManagerId = borrowManagerId;
	}


	public String getBorrowManagerName() {
		return borrowManagerName;
	}


	public void setBorrowManagerName(String borrowManagerName) {
		this.borrowManagerName = borrowManagerName;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getRoomCode() {
		return roomCode;
	}


	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getUserIds() {
		return userIds;
	}


	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}


	public String getUserNames() {
		return userNames;
	}


	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}


	public String getDeptIds() {
		return deptIds;
	}


	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}


	public String getDeptNames() {
		return deptNames;
	}


	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}


	public String getRoleIds() {
		return roleIds;
	}


	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}


	public String getRoleNames() {
		return roleNames;
	}


	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}


	public int getCreateUserId() {
		return createUserId;
	}


	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}


	public String getCreateUserName() {
		return createUserName;
	}


	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}


	public String getCreateTimeStr() {
		return createTimeStr;
	}


	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
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
	


	
	
	
}