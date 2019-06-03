package com.beidasoft.xzzf.punish.classicCase.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 经典案例实体类
 */
@Entity
@Table(name="ZF_CLASSIC_CASE")
public class ClassicCase {
    @Id
    @Column(name = "ID")
    private String id; // 经典案例ID

    @Column(name = "FILING_DATE")
    private Date filingDate; // 立案日期

    @Column(name = "CLOSED_DATE")
    private Date closedDate; // 案件办结标志、未结案时该值为空

    @Column(name = "SOURCE_TYPE")
    private String sourceType; // 案件来源

    @Column(name = "CASE_TYPE")
    private String caseType; // 案件类别

    @Column(name = "BASE_TITLE")
    private String baseTitle; // 案件标题

    @Column(name = "MAJOR_PERSON_ID")
    private int majorPersonId; // 主办人id
    
    @Column(name = "MAJOR_PERSON_NAME")
    private String majorPersonName; // 主办人姓名
    
    @Column(name = "MINOR_PERSON_ID")
    private int minorPersonId; // 协办人id
    
    @Column(name = "MINOR_PERSON_NAME")
    private String minorPersonName; // 协办人姓名

    @Column(name = "DEL_FLAG")
    private String delFlag; // 删除标志，0未删除，1已删除

    @Column(name = "ORIGIN")
    private String origin; // 此经典案例来源，1关联基础案例，2上传附件方式

    @Column(name = "BASE_CODE")
    private String baseCode; // 立案编号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getBaseTitle() {
		return baseTitle;
	}

	public void setBaseTitle(String baseTitle) {
		this.baseTitle = baseTitle;
	}

	

	public String getMajorPersonName() {
		return majorPersonName;
	}

	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}

	

	public int getMajorPersonId() {
		return majorPersonId;
	}

	public void setMajorPersonId(int majorPersonId) {
		this.majorPersonId = majorPersonId;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

    
}