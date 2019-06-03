package com.tianee.oa.core.org.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;
public class TeeUserRoleModel  extends TeeBaseModel{


	private int uuid;

	private String roleName;//角色名称
	private Integer roleNo;//角色编码 排序
	private String personsUuid;
	
	private int roleTypeId;//
	private String roleTypeName;//分类名称
	private double salary;//岗位工资
	private String salaryLevelModel;//薪资级别  [{1:2000,2:3000,3:4000}]
	private int deptId;
	private String deptName;
	
	private String statistics;//人员统计   主角色用户/辅助角色用户
	
	
	
	public String getStatistics() {
		return statistics;
	}
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(Integer roleNo) {
		this.roleNo = roleNo;
	}
	public String getPersonsUuid() {
		return personsUuid;
	}
	public void setPersonsUuid(String personsUuid) {
		this.personsUuid = personsUuid;
	}
	public int getRoleTypeId() {
		return roleTypeId;
	}
	public void setRoleTypeId(int roleTypeId) {
		this.roleTypeId = roleTypeId;
	}
	public String getRoleTypeName() {
		return roleTypeName;
	}
	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getSalaryLevelModel() {
		return salaryLevelModel;
	}
	public void setSalaryLevelModel(String salaryLevelModel) {
		this.salaryLevelModel = salaryLevelModel;
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
	
}
