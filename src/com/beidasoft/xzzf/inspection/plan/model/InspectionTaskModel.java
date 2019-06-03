package com.beidasoft.xzzf.inspection.plan.model;

public class InspectionTaskModel {
	//主键
	private String id;
	
	//检查计划ID
	private String inspectionId;
	
	//执法办案唯一编号ID
	private String baseId;
	
	//主办人
	private int majorPerson;
	//主办人名称
	private String majorPersonName;
	//主办人执法证号
	private String majorPersonCode;
	
	//协办人
	private int minorPerson;
	//协办人名称
	private String minorPersonName;
	//协办人执法证号
	private String minorPersonCode;
	
	//所属部门名称
	private String personDept;
	//所属部门id
	private int deptUuid;
	
	//检查对象ID
	private String orgId;
	
	//检查对象代码
	private String orgCode;
	//检查对象名称
	private String orgName;
	//检查对象类型
	private String orgType;
	
	//检查对象地址
	private String orgAddress;
	
	//实际检查日期
	private String inspectionTimeStr;
	
	//检查结果
	private int result;
	
	//是否立案
	private int isFiling;
	
	//当前状态
	private int status;

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

	public String getMajorPersonName() {
		return majorPersonName;
	}

	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}

	public int getMinorPerson() {
		return minorPerson;
	}

	public void setMinorPerson(int minorPerson) {
		this.minorPerson = minorPerson;
	}

	public String getMinorPersonName() {
		return minorPersonName;
	}

	public void setMinorPersonName(String minorPersonName) {
		this.minorPersonName = minorPersonName;
	}

	public String getPersonDept() {
		return personDept;
	}

	public void setPersonDept(String personDept) {
		this.personDept = personDept;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getInspectionTimeStr() {
		return inspectionTimeStr;
	}

	public void setInspectionTimeStr(String inspectionTimeStr) {
		this.inspectionTimeStr = inspectionTimeStr;
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

	public String getMajorPersonCode() {
		return majorPersonCode;
	}

	public void setMajorPersonCode(String majorPersonCode) {
		this.majorPersonCode = majorPersonCode;
	}

	public String getMinorPersonCode() {
		return minorPersonCode;
	}

	public void setMinorPersonCode(String minorPersonCode) {
		this.minorPersonCode = minorPersonCode;
	}

	public int getDeptUuid() {
		return deptUuid;
	}

	public void setDeptUuid(int deptUuid) {
		this.deptUuid = deptUuid;
	}
	
}
