package com.tianee.oa.sync.log.model;

public class TeeSyncLogModel {

	private int sid;
	private String userName;//用户名
	private String userId;//用户英文名
	private String deptName;//所属部门
	private String deptParentName;//上级部门
	private String roleName;//角色
	private String gender;//性别
	private String email;//email
	private String phoneNum;//手机
	private String subDeptName;//提交人所属部门
	private String submitUserName;//提交人姓名
	private String createTimeStr;//创建时间
	private String operation;//操作 0 ：新增人员 1： 修改人员 2：删除人员 3：新增部门 4：修改部门 5：删除部门
	private String requestJson;//发送json
	private String systemName;//所属系统
	private String minCreateTime;//查询的时间范围
	private String maxCreateTime;//查询的时间范围
	private String syncFlag;//同步标识
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSubDeptName() {
		return subDeptName;
	}
	public void setSubDeptName(String subDeptName) {
		this.subDeptName = subDeptName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMinCreateTime() {
		return minCreateTime;
	}
	public void setMinCreateTime(String minCreateTime) {
		this.minCreateTime = minCreateTime;
	}
	public String getMaxCreateTime() {
		return maxCreateTime;
	}
	public void setMaxCreateTime(String maxCreateTime) {
		this.maxCreateTime = maxCreateTime;
	}
	public String getSubmitUserName() {
		return submitUserName;
	}
	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptParentName() {
		return deptParentName;
	}
	public void setDeptParentName(String deptParentName) {
		this.deptParentName = deptParentName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getRequestJson() {
		return requestJson;
	}
	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSyncFlag() {
		return syncFlag;
	}
	public void setSyncFlag(String syncFlag) {
		this.syncFlag = syncFlag;
	}
	
}
