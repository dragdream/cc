package com.tianee.oa.core.base.pm.model;



public class TeeOnDutyModel {
	private int uuid;//主键
	private String pbType;//排班类型
	private String pbTypeDesc;//排班类型描述
	private String zbType;//值班类型
	private String zbTypeDesc;//值班类型描述
	private String beginTimeStr;//开始日期
	private String endTimeStr;//结束日期
	private String demand;//值班要求
	private String remark;//备注
	private int  userUuid;//值班人员主键
	private String userName;//值班人员姓名
	private int deptId;//值班人员所属部门id
	private String deptName;//值班人员所属部门名称
	
	
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getPbType() {
		return pbType;
	}
	public void setPbType(String pbType) {
		this.pbType = pbType;
	}
	public String getZbType() {
		return zbType;
	}
	public void setZbType(String zbType) {
		this.zbType = zbType;
	}
	
	public String getDemand() {
		return demand;
	}
	public void setDemand(String demand) {
		this.demand = demand;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(int userUuid) {
		this.userUuid = userUuid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getBeginTimeStr() {
		return beginTimeStr;
	}
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getPbTypeDesc() {
		return pbTypeDesc;
	}
	public void setPbTypeDesc(String pbTypeDesc) {
		this.pbTypeDesc = pbTypeDesc;
	}
	public String getZbTypeDesc() {
		return zbTypeDesc;
	}
	public void setZbTypeDesc(String zbTypeDesc) {
		this.zbTypeDesc = zbTypeDesc;
	}
	
	
}
