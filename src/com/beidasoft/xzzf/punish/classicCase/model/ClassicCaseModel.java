package com.beidasoft.xzzf.punish.classicCase.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

/**
 * 经典案例model
 */
public class ClassicCaseModel {
    private String id; // 经典案例ID

    private String filingDateStr; // 立案日期

    private String closedDateStr; // 案件办结标志、未结案时该值为空

    private String sourceType; // 案件来源

    private String caseType; // 案件类别

    private String baseTitle; // 案件标题

    private int majorPersonId; // 主办人id
    
    private String majorPersonName; // 主办人姓名
    
    private int minorPersonId; // 协办人id
    
    private String minorPersonName; // 协办人姓名

    private String delFlag; // 删除标志，0未删除，1已删除

    private String origin; // 此经典案例来源，1关联基础案例，2上传附件方式

    private String baseCode; // 立案编号，年份（4位数字）+单位编号（3位数字）+所属民政领域（2位数字）+序号（4位数字）

    private String caseAttachment;	//附件ids
	
	private List<TeeAttachmentModel> attachModels;//附件

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilingDateStr() {
		return filingDateStr;
	}

	public void setFilingDateStr(String filingDateStr) {
		this.filingDateStr = filingDateStr;
	}

	public String getClosedDateStr() {
		return closedDateStr;
	}

	public void setClosedDateStr(String closedDateStr) {
		this.closedDateStr = closedDateStr;
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

	public String getCaseAttachment() {
		return caseAttachment;
	}

	public void setCaseAttachment(String caseAttachment) {
		this.caseAttachment = caseAttachment;
	}

	public List<TeeAttachmentModel> getAttachModels() {
		return attachModels;
	}

	public void setAttachModels(List<TeeAttachmentModel> attachModels) {
		this.attachModels = attachModels;
	}
	
	
}
