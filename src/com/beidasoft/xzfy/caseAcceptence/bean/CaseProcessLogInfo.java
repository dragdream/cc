package com.beidasoft.xzfy.caseAcceptence.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FY_PROCESS_FLOW")
public class CaseProcessLogInfo {

    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;

    // 处理环节
    @Column(name = "PROCESS_ITEM")
    private String processItem;

    // 处理开始时间
    @Column(name = "START_TIME")
    private String startTime;

    // 处理结束时间
    @Column(name = "END_TIME")
    private String endTime;

    // 处理结果
    @Column(name = "DEAL_RESULT")
    private String dealResult;

    // 原因
    @Column(name = "REASON")
    private String reason;

    // 备注
    @Column(name = "REMARK")
    private String remark;

    // 是否生成文书:0 否 1 是
    @Column(name = "IS_CREATE_DOCUMENT")
    private String isCreateDoc;

    // 结案类型
    @Column(name = "CASE_CLOSE_TYPE")
    private String caseCloseType;

    // 结案时间
    @Column(name = "CASE_CLOSE_TIME")
    private String caseCloseTime;

    // 归档目录
    @Column(name = "ARCHIVE_DIR")
    private String archiveDir;

    // 归档类型:01 正卷 02 副卷 03 公示
    @Column(name = "ARCHIVE_TYPE")
    private String archiveType;

    // 第一承办人
    @Column(name = "DEAL_MAN1_ID")
    private String dealManFirstId;

    // 第二承办人
    @Column(name = "DEAL_MAN2_ID")
    private String dealManSecondId;

    // 创建人
    @Column(name = "CREATED_USER")
    private String createdUser;

    // 创建人ID
    @Column(name = "CREATED_USER_Id")
    private String createdUserId;

    // 创建时间
    @Column(name = "CREATED_TIME")
    private String createdTime;

    // 更新人
    @Column(name = "MODIFIED_USER")
    private String modifiedUser;

    // 更新人Id
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;

    // 更新时间
    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    // 是否删除:0 否 1 是
    @Column(name = "IS_DELETE")
    private String idDelete;

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

    public String getProcessItem() {
        return processItem;
    }

    public void setProcessItem(String processItem) {
        this.processItem = processItem;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsCreateDoc() {
        return isCreateDoc;
    }

    public void setIsCreateDoc(String isCreateDoc) {
        this.isCreateDoc = isCreateDoc;
    }

    public String getCaseCloseType() {
        return caseCloseType;
    }

    public void setCaseCloseType(String caseCloseType) {
        this.caseCloseType = caseCloseType;
    }

    public String getCaseCloseTime() {
        return caseCloseTime;
    }

    public void setCaseCloseTime(String caseCloseTime) {
        this.caseCloseTime = caseCloseTime;
    }

    public String getArchiveDir() {
        return archiveDir;
    }

    public void setArchiveDir(String archiveDir) {
        this.archiveDir = archiveDir;
    }

    public String getArchiveType() {
        return archiveType;
    }

    public void setArchiveType(String archiveType) {
        this.archiveType = archiveType;
    }

    public String getDealManFirstId() {
        return dealManFirstId;
    }

    public void setDealManFirstId(String dealManFirstId) {
        this.dealManFirstId = dealManFirstId;
    }

    public String getDealManSecondId() {
        return dealManSecondId;
    }

    public void setDealManSecondId(String dealManSecondId) {
        this.dealManSecondId = dealManSecondId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getIdDelete() {
        return idDelete;
    }

    public void setIdDelete(String idDelete) {
        this.idDelete = idDelete;
    }

}
