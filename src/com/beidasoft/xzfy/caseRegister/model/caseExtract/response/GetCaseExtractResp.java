package com.beidasoft.xzfy.caseRegister.model.caseExtract.response;

public class GetCaseExtractResp {

    private String caseId;// 案件ID
    private String caseNum; // 案件编号
    private String postTypeCode; // 案件来源编码
    private String postType; // 案件来源
    private String name; // 姓名/组织名称
    private String respondentName; // 被申请人名称
    private String caseStatusCode; // 案件状态
    private String caseStatus; // 案件状态
    private String applicationDate; // 收到复议申请日期
    private String remainderTime; // 剩余时常
    private String mergerCase; // 并案

    public String getPostTypeCode() {
        return postTypeCode;
    }

    public void setPostTypeCode(String postTypeCode) {
        this.postTypeCode = postTypeCode;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    // public String getCaseChiledStatusCode() {
    // return caseChiledStatusCode;
    // }
    //
    // public void setCaseChiledStatusCode(String caseChiledStatusCode) {
    // this.caseChiledStatusCode = caseChiledStatusCode;
    // }
    //
    // public String getCaseChiledStatus() {
    // return caseChiledStatus;
    // }
    //
    // public void setCaseChiledStatus(String caseChiledStatus) {
    // this.caseChiledStatus = caseChiledStatus;
    // }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getRemainderTime() {
        return remainderTime;
    }

    public void setRemainderTime(String remainderTime) {
        this.remainderTime = remainderTime;
    }

    public String getMergerCase() {
        return mergerCase;
    }

    public void setMergerCase(String mergerCase) {
        this.mergerCase = mergerCase;
    }

    public String getCaseStatusCode() {
        return caseStatusCode;
    }

    public void setCaseStatusCode(String caseStatusCode) {
        this.caseStatusCode = caseStatusCode;
    }

}
