package com.beidasoft.xzzf.informationEntry.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 子表结案报告书实体类
 */
@Entity
@Table(name="ZF_INFOR_ENTRY_REPORT")
public class InforEntryReport {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;

    // 结案报告文号
    @Column(name = "REPORT_SYMBOL")
    private String reportSymbol;

    // 处罚时间
    @Column(name = "PUNISH_DATE")
    private Date punishDate;

    // 处罚内容
    @Column(name = "PUNISH_CONTENT")
    private String punishContent;

    // 案情概要
    @Column(name = "SUMMARY")
    private String summary;

    // 执行情况
    @Column(name = "SITUATION")
    private String situation;

    // 创建人
    @Column(name = "CREATE_NAME")
    private String createName;

    // 创建人ID
    @Column(name = "CREATE_ID")
    private String createId;

    // 创建时间
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 创建人部门
    @Column(name = "CREATE_DEPT")
    private String createDept;

    // 修改人
    @Column(name = "UPDATE_NAME")
    private String updateName;

    // 修改人ID
    @Column(name = "UPDATE_ID")
    private String updateId;

    // 修改时间
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

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

    public Date getPunishDate() {
        return punishDate;
    }

    public void setPunishDate(Date punishDate) {
        this.punishDate = punishDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
