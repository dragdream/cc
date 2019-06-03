package com.tianee.oa.core.workflow.flowrun.model;

import javax.persistence.Column;

public class TeeDocNumModel {
	private int sid;
	private String docName;//文号名称
	private int orderNo;
	private String docStyle;//文号样式
	private String deptPrivIds;
	private String deptPrivNames;
	private String rolePrivIds;
	private String rolePrivNames;
	private String userPrivIds;
	private String userPrivNames;
	private String flowIds;//绑定流程id串
	private String flowNames;//绑定流程id串
	private int resetYear = 1;//清零年限，表示几年进行清零
	private int resetStamp;//清零年，主要标记哪年清零过
	private int currNum = 0;//当前文号值，从0开始
	private int countNum;//文号计数值
	
	
	
	
	public int getCountNum() {
		return countNum;
	}
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getDocStyle() {
		return docStyle;
	}
	public void setDocStyle(String docStyle) {
		this.docStyle = docStyle;
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
	public void setFlowIds(String flowIds) {
		this.flowIds = flowIds;
	}
	public String getFlowIds() {
		return flowIds;
	}
	public void setFlowNames(String flowNames) {
		this.flowNames = flowNames;
	}
	public String getFlowNames() {
		return flowNames;
	}
	public int getResetYear() {
		return resetYear;
	}
	public void setResetYear(int resetYear) {
		this.resetYear = resetYear;
	}
	public int getResetStamp() {
		return resetStamp;
	}
	public void setResetStamp(int resetStamp) {
		this.resetStamp = resetStamp;
	}
	public int getCurrNum() {
		return currNum;
	}
	public void setCurrNum(int currNum) {
		this.currNum = currNum;
	}
	
}
