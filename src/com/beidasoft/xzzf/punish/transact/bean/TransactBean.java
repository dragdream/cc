package com.beidasoft.xzzf.punish.transact.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ZF_TRANSACT")
public class TransactBean {
	
	@Id
	@Column(name = "ID")
	private String id;								//执法办案任务主键
	
	@Column(name = "BASE_ID")
	private String baseId;							//执法办案统一编号
	
	@Column(name = "CODE")
	private String code;						//案件年度流水号
	
	@Column(name = "LITIGANT_TYPE")
	private String litigantType;				//当事人类型

	@Column(name = "LITIGANT_NAME")
	private String litigantName;				//当事人名称
	
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

	@Column(name = "FILING_DATE")
	private Calendar filingDate;					//立案日期

	@Column(name = "INSPECTION_DATE")
	private Calendar inspectionDate;				//检查日期

	@Column(name = "PUNISHMENT_DATE")
	private Calendar punishmentDate;				//行政处罚日期

	@Column(name = "PUNISHMENT_EXE_DATE")
	private Calendar punishmentExeDate;				//处罚执行日期

	@Column(name = "CLOSED_DATE")
	private Calendar closedDate;					//结案日期

	@Column(name = "MAJOR_PERSON_ID")
	private int majorPersonId;					//主办人ID

	@Column(name = "MAJOR_PERSON_NAME")
	private String majorPersonName;				//主办人姓名

	@Column(name = "MINOR_PERSON_ID")
	private int minorPersonId;					//协办人ID

	@Column(name = "MINOR_PERSON_NAME")
	private String minorPersonName;				//协办人姓名

	@Column(name = "DEPARTMENT_ID")
	private int departmentId;					//部门ID

	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;				//部门名称

	@Column(name = "STATUS")
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

	public Calendar getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Calendar filingDate) {
		this.filingDate = filingDate;
	}

	public Calendar getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Calendar inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public Calendar getPunishmentDate() {
		return punishmentDate;
	}

	public void setPunishmentDate(Calendar punishmentDate) {
		this.punishmentDate = punishmentDate;
	}

	public Calendar getPunishmentExeDate() {
		return punishmentExeDate;
	}

	public void setPunishmentExeDate(Calendar punishmentExeDate) {
		this.punishmentExeDate = punishmentExeDate;
	}

	public Calendar getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Calendar closedDate) {
		this.closedDate = closedDate;
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
