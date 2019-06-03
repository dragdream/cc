package com.tianee.oa.core.workflow.workmanage.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeFlowPrivModel extends TeeBaseModel{
	private int sid;
	private int flowTypeId;
	private String flowTypeName;
	private int privType;
	private int privScope;	
	private String privUsersId;
	private String privUsersName;
    private String privDeptsId;
    private String privDeptsName;
	private String privRolesId;
	private String privRolesName;
    private String deptPostId;
    private String deptPostName;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public String getFlowTypeName() {
		return flowTypeName;
	}
	public void setFlowTypeName(String flowTypeName) {
		this.flowTypeName = flowTypeName;
	}
	public int getPrivType() {
		return privType;
	}
	public void setPrivType(int privType) {
		this.privType = privType;
	}
	public int getPrivScope() {
		return privScope;
	}
	public void setPrivScope(int privScope) {
		this.privScope = privScope;
	}
	public String getPrivUsersId() {
		return privUsersId;
	}
	public void setPrivUsersId(String privUsersId) {
		this.privUsersId = privUsersId;
	}
	public String getPrivUsersName() {
		return privUsersName;
	}
	public void setPrivUsersName(String privUsersName) {
		this.privUsersName = privUsersName;
	}
	public String getPrivDeptsId() {
		return privDeptsId;
	}
	public void setPrivDeptsId(String privDeptsId) {
		this.privDeptsId = privDeptsId;
	}
	public String getPrivDeptsName() {
		return privDeptsName;
	}
	public void setPrivDeptsName(String privDeptsName) {
		this.privDeptsName = privDeptsName;
	}
	public String getPrivRolesId() {
		return privRolesId;
	}
	public void setPrivRolesId(String privRolesId) {
		this.privRolesId = privRolesId;
	}
	public String getPrivRolesName() {
		return privRolesName;
	}
	public void setPrivRolesName(String privRolesName) {
		this.privRolesName = privRolesName;
	}
	public String getDeptPostId() {
		return deptPostId;
	}
	public void setDeptPostId(String deptPostId) {
		this.deptPostId = deptPostId;
	}
	public String getDeptPostName() {
		return deptPostName;
	}
	public void setDeptPostName(String deptPostName) {
		this.deptPostName = deptPostName;
	}
	
	
}
