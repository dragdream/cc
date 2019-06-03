package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 来件信息
 * 
 * @author cc
 * 
 */
@Entity
@Table(name = "FY_LETTER")
public class FyLetter {

    @Id
    @Column(name = "CASE_ID")
    private String caseId; // 案件ID

    @Column(name = "LETTER_TYPE_CODE")
    private String letterTypeCode; // 收件类型代码

    @Column(name = "LETTER_TYPE")
    private String letterType; // 收件类型

    @Column(name = "RECEIVE_DATE")
    private String receiveDate; // 接收日期

    @Column(name = "SENDER_NAME")
    private String senderName; // 来信人姓名

    @Column(name = "SENDER_PHONE")
    private String senderPhone; // 来信人电话

    @Column(name = "SENDER_POSTCODE")
    private String senderPostCode; // 来信人邮编

    @Column(name = "SENDER_ADDRESS")
    private String senderAddress; // 来信人通信地址

    @Column(name = "CREATED_USER_ID")
    private String createdUserId; // 创建人Id

    @Column(name = "CREATED_USER")
    private String createdUser; // 创建人

    @Column(name = "CREATED_TIME")
    private String createTime; // 创建时间

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId; // 修改人ID

    @Column(name = "MODIFIED_USER")
    private String modifiedUser; // 修改人

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime; // 修改时间

    @Column(name = "IS_DELETE")
    private int isDelete; // 是否删除

    @Column(name = "LETTER_NUM")
    private int letterNum; // 来文编号

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

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getLetterNum() {
        return letterNum;
    }

    public void setLetterNum(int letterNum) {
        this.letterNum = letterNum;
    }

}
