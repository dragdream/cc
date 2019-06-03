package com.beidasoft.xzzf.inspection.plan.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_DEPT")
public class InspectionDept {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//案件唯一ID
	@Column(name = "INSPECTION_ID")
	private String inspectionId;
	
	//检查计划执行部门ID
	@Column(name = "EXECUTE_DEPARTMENT")
	private int executeDepartment;
	
	//检查计划执行部门名称
	@Column(name = "EXECUTE_DEPARTMENT_NAME")
	private String executeDepartmentName;
	
	//执行状态
	@Column(name = "EXECUTE_STATUS")
	private int executeStatus;
	
	//部门检查类型
	@Column(name = "DEPARTMENT_INSPECT_TYPE")
	private String departmentInspectType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}

	public int getExecuteDepartment() {
		return executeDepartment;
	}

	public void setExecuteDepartment(int executeDepartment) {
		this.executeDepartment = executeDepartment;
	}

	public String getExecuteDepartmentName() {
		return executeDepartmentName;
	}

	public void setExecuteDepartmentName(String executeDepartmentName) {
		this.executeDepartmentName = executeDepartmentName;
	}

	public int getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(int executeStatus) {
		this.executeStatus = executeStatus;
	}

	public String getDepartmentInspectType() {
		return departmentInspectType;
	}

	public void setDepartmentInspectType(String departmentInspectType) {
		this.departmentInspectType = departmentInspectType;
	}
	
	
}
