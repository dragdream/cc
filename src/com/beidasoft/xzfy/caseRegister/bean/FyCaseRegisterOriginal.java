package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 案件登记原始记录
 * 
 * @author chumc
 * 
 */
@Entity
@Table(name = "FY_CASE_REGISTER_ORIGINAL")
public class FyCaseRegisterOriginal {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String id; // 主键id

    @Column(name = "CASE_ID")
    private String caseId; // 案件ID

    @Column(name = "APPLICANT_INFO")
    private String applicantInfo; // 申请人信息

    @Column(name = "CASE_HANDLING_INFO")
    private String caseHandlingInfo; // 案件信息

    @Column(name = "LETTER_INFO")
    private String letterInfo; // 来件信息

    @Column(name = "RECEPTION_INFO")
    private String receptionInfo; // 接待信息

    @Column(name = "MATERIAL_INFO")
    private String materialInfo; // 案件资料信息

    @Column(name = "RESPONDENT_INFO")
    private String respondentInfo; // 被申请人信息

    @Column(name = "THIRD_PARTY_INFO")
    private String thirdPartyInfo; // 第三人人信息

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

    public String getApplicantInfo() {
        return applicantInfo;
    }

    public void setApplicantInfo(String applicantInfo) {
        this.applicantInfo = applicantInfo;
    }

    public String getCaseHandlingInfo() {
        return caseHandlingInfo;
    }

    public void setCaseHandlingInfo(String caseHandlingInfo) {
        this.caseHandlingInfo = caseHandlingInfo;
    }

    public String getLetterInfo() {
        return letterInfo;
    }

    public void setLetterInfo(String letterInfo) {
        this.letterInfo = letterInfo;
    }

    public String getReceptionInfo() {
        return receptionInfo;
    }

    public void setReceptionInfo(String receptionInfo) {
        this.receptionInfo = receptionInfo;
    }

    public String getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(String materialInfo) {
        this.materialInfo = materialInfo;
    }

    public String getRespondentInfo() {
        return respondentInfo;
    }

    public void setRespondentInfo(String respondentInfo) {
        this.respondentInfo = respondentInfo;
    }

    public String getThirdPartyInfo() {
        return thirdPartyInfo;
    }

    public void setThirdPartyInfo(String thirdPartyInfo) {
        this.thirdPartyInfo = thirdPartyInfo;
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

}
