package com.beidasoft.xzzf.inspection.plan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_TASK")
public class InspectionTask {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//检查计划ID
	@Column(name = "INSPECTION_ID")
	private String inspectionId;
	
	//执法办案唯一编号ID
	@Column(name = "BASE_ID")
	private String baseId;
	
	//主办人
	@Column(name = "MAJOR_PERSON")
	private int majorPerson;
	
	//协办人
	@Column(name = "MINOR_PERSON")
	private int minorPerson;
	
	//检查对象ID
	@Column(name = "ORG_ID")
	private String orgId;
	
	//检查对象名称
	@Column(name = "ORG_NAME")
	private String orgName;
	
	//检查对象地址
	@Column(name = "ORG_ADDRESS")
	private String orgAddress;
	
	//实际检查日期
	@Column(name = "INSPECTION_TIME")
	private Date inspectionTime;
	
	//检查结果
	@Column(name = "RESULT")
	private int result;
	
	//是否立案
	@Column(name = "IS_FILING")
	private int isFiling;
	
	//当前状态
	@Column(name = "STATUS")
	private int status;
	
	//所属部门
	@Column(name = "DEPT_ID")
	private int deptId;

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

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public int getMajorPerson() {
		return majorPerson;
	}

	public void setMajorPerson(int majorPerson) {
		this.majorPerson = majorPerson;
	}

	public int getMinorPerson() {
		return minorPerson;
	}

	public void setMinorPerson(int minorPerson) {
		this.minorPerson = minorPerson;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public Date getInspectionTime() {
		return inspectionTime;
	}

	public void setInspectionTime(Date inspectionTime) {
		this.inspectionTime = inspectionTime;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getIsFiling() {
		return isFiling;
	}

	public void setIsFiling(int isFiling) {
		this.isFiling = isFiling;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	
	
}
