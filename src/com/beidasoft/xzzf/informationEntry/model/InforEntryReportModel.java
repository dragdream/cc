package com.beidasoft.xzzf.informationEntry.model;

/**
 * 子表结案报告书MODEL类
 */
public class InforEntryReportModel {
    // 主键ID
    private String id;

    // 案件ID
    private String caseId;

    //判断
    private String editFlag;
    
    // 结案报告文号
    private String reportSymbol;

    // 处罚时间
    private String punishDateStr;

    // 处罚内容
    private String punishContent;

    // 案情概要
    private String summary;

    // 执行情况
    private String situation;

    // 创建人
    private String createName;

    // 创建人ID
    private String createId;

    // 创建时间
    private String createDateStr;

    // 创建人部门
    private String createDept;

    // 修改人
    private String updateName;

    // 修改人ID
    private String updateId;

    // 修改时间
    private String updateDateStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getReportSymbol() {
        return reportSymbol;
    }

    public void setReportSymbol(String reportSymbol) {
        this.reportSymbol = reportSymbol;
    }

    public String getPunishDateStr() {
        return punishDateStr;
    }

    public void setPunishDateStr(String punishDateStr) {
        this.punishDateStr = punishDateStr;
    }

    public String getPunishContent() {
        return punishContent;
    }

    public void setPunishContent(String punishContent) {
        this.punishContent = punishContent;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

}
