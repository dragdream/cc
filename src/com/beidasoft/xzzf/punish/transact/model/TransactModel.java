package com.beidasoft.xzzf.punish.transact.model;

public class TransactModel {
	
	private String id;   						//执法办案任务主键
	private String baseId;							//执法办案统一编号
	private String code;						//案件年度流水号
	private String litigantType;				//当事人类型
	private String litigantName;				//当事人名称
	private String unitName;				    //当事人名称(单位)
	private String psnName;				        //当事人名称(个人)
	private String filingDateStr;				//立案日期（前台展示）
	private String inspectionDateStr;			//检查日期（前台展示）
	private String punishmentDateStr;			//行政处罚日期（前台展示）
	private String punishmentExeDateStr;		//处罚执行日期（前台展示）
	private String closedDateStr;				//结案日期（前台展示）
	private int majorPersonId;					//主办人ID
	private String majorPersonName;				//主办人姓名
	private int minorPersonId;					//协办人ID
	private String minorPersonName;				//协办人姓名
	private int departmentId;					//部门ID
	private String departmentName;				//部门名称
	private String status;						//状态
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLitigantType() {
		return litigantType;
	}
	public void setLitigantType(String litigantType) {
		this.litigantType = litigantType;
	}
	public String getLitigantName() {
		return litigantName;
	}
	public void setLitigantName(String litigantName) {
		this.litigantName = litigantName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getPsnName() {
		return psnName;
	}
	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}
	public String getFilingDateStr() {
		return filingDateStr;
	}
	public void setFilingDateStr(String filingDateStr) {
		this.filingDateStr = filingDateStr;
	}
	public String getInspectionDateStr() {
		return inspectionDateStr;
	}
	public void setInspectionDateStr(String inspectionDateStr) {
		this.inspectionDateStr = inspectionDateStr;
	}
	public String getPunishmentDateStr() {
		return punishmentDateStr;
	}
	public void setPunishmentDateStr(String punishmentDateStr) {
		this.punishmentDateStr = punishmentDateStr;
	}
	public String getPunishmentExeDateStr() {
		return punishmentExeDateStr;
	}
	public void setPunishmentExeDateStr(String punishmentExeDateStr) {
		this.punishmentExeDateStr = punishmentExeDateStr;
	}
	public String getClosedDateStr() {
		return closedDateStr;
	}
	public void setClosedDateStr(String closedDateStr) {
		this.closedDateStr = closedDateStr;
	}
	public int getMajorPersonId() {
		return majorPersonId;
	}
	public void setMajorPersonId(int majorPersonId) {
		this.majorPersonId = majorPersonId;
	}
	public String getMajorPersonName() {
		return majorPersonName;
	}
	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}
	public int getMinorPersonId() {
		return minorPersonId;
	}
	public void setMinorPersonId(int minorPersonId) {
		this.minorPersonId = minorPersonId;
	}
	public String getMinorPersonName() {
		return minorPersonName;
	}
	public void setMinorPersonName(String minorPersonName) {
		this.minorPersonName = minorPersonName;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
		
}
