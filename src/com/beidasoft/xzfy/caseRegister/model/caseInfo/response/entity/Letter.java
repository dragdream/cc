package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

public class Letter {

    private String operationType; // 操作类型 "01"为登记/"02"为填报
    private String applicationTypeCode; // 复议申请方式代码
    private String applicationType; // 复议申请方式
    private String caseId; // 案件ID
    private String letterTypeCode; // 收件类型代码
    private String letterType; // 收件类型
    private String receiveDate; // 接收日期
    private String senderName; // 来信人姓名
    private String senderPhone; // 来信人电话
    private String senderPostCode; // 来信人邮编
    private String senderAddress; // 来信人通信地址
    private String letterNum; // 来文编号

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getApplicationTypeCode() {
        return applicationTypeCode;
    }

    public void setApplicationTypeCode(String applicationTypeCode) {
        this.applicationTypeCode = applicationTypeCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getLetterTypeCode() {
        return letterTypeCode;
    }

    public void setLetterTypeCode(String letterTypeCode) {
        this.letterTypeCode = letterTypeCode;
    }

    public String getLetterType() {
        return letterType;
    }

    public void setLetterType(String letterType) {
        this.letterType = letterType;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderPostCode() {
        return senderPostCode;
    }

    public void setSenderPostCode(String senderPostCode) {
        this.senderPostCode = senderPostCode;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getLetterNum() {
        return letterNum;
    }

    public void setLetterNum(String letterNum) {
        this.letterNum = letterNum;
    }

}
