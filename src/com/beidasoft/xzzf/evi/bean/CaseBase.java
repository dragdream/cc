package com.beidasoft.xzzf.evi.bean;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZF_PUNISH_BASE")
public class CaseBase {
	// 执法办案统一编号
    @Id
    @Column(name = "BASE_ID")
    private String baseId;

    // 案件编号
    @Column(name = "BASE_CODE")
    private String baseCode;

    // 案件来源ID
    @Column(name = "SOURCE_ID")
    private String sourceId;

    // 案件来源类型
    @Column(name = "SOURCE_TYPE")
    private String sourceType;

    // 当事人类型
    @Column(name = "LITIGANT_TYPE")
    private String litigantType;

    // 单位当事人名称
    @Column(name = "ORGAN_NAME")
    private String OrganName;

    // 单位当事人住所
    @Column(name = "ORGAN_ADDRESS")
    private String organAddress;

    // 单位当事人类型（代码表）
    @Column(name = "ORGAN_TYPE")
    private String organType;

    // 单位负责人姓名
    @Column(name = "ORGAN_LEADING_NAME")
    private String organLeadingName;

    // 单位负责人联系电话
    @Column(name = "ORGAN_LEADING_TEL")
    private String organLeadingTel;

    // 个人名称
    @Column(name = "PSN_NAME")
    private String psnName;
    
    // 个人字号名称
    @Column(name = "PSN_SHOP_NAME")
    private String psnShopName;

    // 个人当事人性别
    @Column(name = "PSN_SEX")
    private String psnSex;

    // 个人当事人联系电话
    @Column(name = "PSN_TEL")
    private String psnTel;

    // 个人当事人住址
    @Column(name = "PSN_ADDRESS")
    private String psnAddress;

    // 个人当事人证件类型（代码表）
    @Column(name = "PSN_TYPE")
    private String psnType;

    // 个人当事人证件号码
    @Column(name = "PSN_ID_NO")
    private String psnIdNo;

    // 立案日期
    @Column(name = "FILING_DATE")
    private Date filingDate;

    // 检查日期
    @Column(name = "INSPECTION_DATE")
    private Date inspectionDate;

    // 行政处罚日期
    @Column(name = "PUNISHMENT_DATE")
    private Date punishmentDate;

    // 处罚执行日期
    @Column(name = "PUNISHMENT_EXE_DATE")
    private Date punishmentExeDate;
    
    // 听证日期
    @Column(name = "HEARING_DATE")
    private Date hearingDate;

    // 结案日期
    @Column(name = "CLOSED_DATE")
    private Date closedDate;
    
    // 负责人ID
    @Column(name = "CHARGE_PSN_ID")
    private int chargePsnId;

    // 负责人姓名
    @Column(name = "CHARGE_PSN_NAME")
    private String chargePsnName;
    
    // 负责人部门ID
    @Column(name = "CHARGE_DEPT_ID")
    private int chargeDeptId;

    // 负责人部门名
    @Column(name = "CHARGE_DEPT_NAME")
    private String chargeDeptName;

    // 主办人ID
    @Column(name = "MAJOR_PERSON_ID")
    private int majorPersonId;

    // 主办人姓名
    @Column(name = "MAJOR_PERSON_NAME")
    private String majorPersonName;

    // 协办人ID
    @Column(name = "MINOR_PERSON_ID")
    private int minorPersonId;

    // 协办人姓名
    @Column(name = "MINOR_PERSON_NAME")
    private String minorPersonName;

    // 部门ID
    @Column(name = "DEPARTMENT_ID")
    private int departmentId;

    // 部门名称
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    // 状态
    @Column(name = "STATUS")
    private String status;
    
    // 案件指派唯一标识
    @Column(name = "TASK_ID")
    private String taskId;
    
    // 是否申请立案  0.已申请  1.未申请
    @Column(name = "IS_REGISTER")
    private int isRegister;  
    
    // 是否立案  0.已立案  1.未立案
    @Column(name = "IS_APPLY")
    private int isApply;

    // 是否听证 0.不听证 1.听证
    @Column(name = "IS_HEARING")
    private int isHearing;
    
    // 归档类型
    @Column(name = "PUNISH_TYPE")
    private String punishType;
    
    // 归档标识
    @Column(name = "PUNISH_FLG")
    private String punishFlg;

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getLitigantType() {
		return litigantType;
	}

	public void setLitigantType(String litigantType) {
		this.litigantType = litigantType;
	}

	public String getOrganName() {
		return OrganName;
	}

	public void setOrganName(String organName) {
		OrganName = organName;
	}

	public String getOrganAddress() {
		return organAddress;
	}

	public void setOrganAddress(String organAddress) {
		this.organAddress = organAddress;
	}

	public String getOrganType() {
		return organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getOrganLeadingName() {
		return organLeadingName;
	}

	public void setOrganLeadingName(String organLeadingName) {
		this.organLeadingName = organLeadingName;
	}

	public String getOrganLeadingTel() {
		return organLeadingTel;
	}

	public void setOrganLeadingTel(String organLeadingTel) {
		this.organLeadingTel = organLeadingTel;
	}

	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}

	public String getPsnShopName() {
		return psnShopName;
	}

	public void setPsnShopName(String psnShopName) {
		this.psnShopName = psnShopName;
	}

	public String getPsnSex() {
		return psnSex;
	}

	public void setPsnSex(String psnSex) {
		this.psnSex = psnSex;
	}

	public String getPsnTel() {
		return psnTel;
	}

	public void setPsnTel(String psnTel) {
		this.psnTel = psnTel;
	}

	public String getPsnAddress() {
		return psnAddress;
	}

	public void setPsnAddress(String psnAddress) {
		this.psnAddress = psnAddress;
	}

	public String getPsnType() {
		return psnType;
	}

	public void setPsnType(String psnType) {
		this.psnType = psnType;
	}

	public String getPsnIdNo() {
		return psnIdNo;
	}

	public void setPsnIdNo(String psnIdNo) {
		this.psnIdNo = psnIdNo;
	}

	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public Date getPunishmentDate() {
		return punishmentDate;
	}

	public void setPunishmentDate(Date punishmentDate) {
		this.punishmentDate = punishmentDate;
	}

	public Date getPunishmentExeDate() {
		return punishmentExeDate;
	}

	public void setPunishmentExeDate(Date punishmentExeDate) {
		this.punishmentExeDate = punishmentExeDate;
	}

	public Date getHearingDate() {
		return hearingDate;
	}

	public void setHearingDate(Date hearingDate) {
		this.hearingDate = hearingDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public int getChargePsnId() {
		return chargePsnId;
	}

	public void setChargePsnId(int chargePsnId) {
		this.chargePsnId = chargePsnId;
	}

	public String getChargePsnName() {
		return chargePsnName;
	}

	public void setChargePsnName(String chargePsnName) {
		this.chargePsnName = chargePsnName;
	}

	public int getChargeDeptId() {
		return chargeDeptId;
	}

	public void setChargeDeptId(int chargeDeptId) {
		this.chargeDeptId = chargeDeptId;
	}

	public String getChargeDeptName() {
		return chargeDeptName;
	}

	public void setChargeDeptName(String chargeDeptName) {
		this.chargeDeptName = chargeDeptName;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(int isRegister) {
		this.isRegister = isRegister;
	}

	public int getIsApply() {
		return isApply;
	}

	public void setIsApply(int isApply) {
		this.isApply = isApply;
	}

	public int getIsHearing() {
		return isHearing;
	}

	public void setIsHearing(int isHearing) {
		this.isHearing = isHearing;
	}

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}

	public String getPunishFlg() {
		return punishFlg;
	}

	public void setPunishFlg(String punishFlg) {
		this.punishFlg = punishFlg;
	}
}
