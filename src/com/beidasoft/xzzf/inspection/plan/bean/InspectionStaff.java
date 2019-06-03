package com.beidasoft.xzzf.inspection.plan.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_STAFF")
public class InspectionStaff {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//案件唯一ID
	@Column(name = "INSPECTION_ID")
	private String inspectionId;
	
	//执法检查人员ID
	@Column(name = "STAFF_ID")
	private int staffId;
	
	//执法人员姓名
	@Column(name = "STAFF_NAME")
	private String staffName;
	
	//执法人员编号
	@Column(name = "STAFF_CODE")
	private String staffCode;
	
	//所在部门id
	@Column(name = "DEPT_ID")
	private int deptId;
	
	//所在部门名称
	@Column(name = "DEPT_NAME")
	private String deptName;
	
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

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
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
