package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 申请人信息
 * 
 * @author cc
 * 
 */
@Entity
@Table(name = "FY_APPLICANT")
public class FyApplicant {

    /** 主键 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String id;

    /** 案件ID */
    @Column(name = "CASE_ID")
    private String caseId;

    /** 申请人类别代码:01 公民 02 法人或组织 */
    @Column(name = "APPLICANT_TYPE_CODE")
    private String applicantTypeCode;

    /** 申请人类别 */
    @Column(name = "APPLICANT_TYPE")
    private String applicantType;

    /** 姓名/组织名称 */
    @Column(name = "NAME")
    private String name;

    /**
     * 代表人类别代码:01 法人代表 02 负责人 03 合伙人 04 被共同推选的组织成员 99 其他
     */
    @Column(name = "REPRESENTATIVE_TYPE_CODE")
    private String representativeTypeCode;

    /** 代表人姓名 */
    @Column(name = "REPRESENTATIVE_NAME")
    private String representativeName;

    /** 证件类型代码:01 身份证 02 港澳居民通行证 99 其他 */
    @Column(name = "CERTIFICATE_TYPE_CODE")
    private String certificateTypeCode;

    /** 证件类型代码:01 身份证 02 港澳居民通行证 99 其他 */
    @Column(name = "CERTIFICATE_TYPE")
    private String certificateType;

    /** 证件号码 */
    @Column(name = "CERTIFICATE_ID")
    private String certificateId;

    /** 性别代码 */
    @Column(name = "SEX_CODE")
    private String sexCode;

    /** 性别 */
    @Column(name = "SEX")
    private String sex;

    /** 民族代码 */
    @Column(name = "NATION_CODE")
    private String nationCode;

    /** 民族 */
    @Column(name = "NATION")
    private String nation;

    /** 电话 */
    @Column(name = "PHONE_NUM")
    private String phoneNum;

    /** 邮编 */
    @Column(name = "POSTCODE")
    private String postCode;

    /** 通信地址 */
    @Column(name = "POST_ADDRESS")
    private String postAddress;

    /** 创建人 */
    @Column(name = "CREATED_USER")
    private String createdUser;

    /** 创建时间:yyyy/MM/dd HH24:mi:ss */
    @Column(name = "CREATED_TIME")
    private String createdTime;

    /** 修改人 */
    @Column(name = "MODIFIED_USER")
    private String modifiedUser;

    /** 修改时间 */
    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    /** 是否删除:0 否 1 是 */
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    /** 是否有授权书 */
    @Column(name = "IS_AUTHORIZATION")
    private Integer isAuthorization;

    /** 是否其他申请人 */
    @Column(name = "IS_OTHER_APPLICANT")
    private Integer isOtherApplicant;

    /** 备份数据case_Id */
    @Column(name = "BACKUP_CASE_ID")
    private String backupCaseId;

    /** 创建人Id */
    @Column(name = "CREATED_USER_ID")
    private String createdUserId;

    /** 修改人ID */
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

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

    public String getApplicantTypeCode() {
        return applicantTypeCode;
    }

    public void setApplicantTypeCode(String applicantTypeCode) {
        this.applicantTypeCode = applicantTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepresentativeTypeCode() {
        return representativeTypeCode;
    }

    public void setRepresentativeTypeCode(String representativeTypeCode) {
        this.representativeTypeCode = representativeTypeCode;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getCertificateTypeCode() {
        return certificateTypeCode;
    }

    public void setCertificateTypeCode(String certificateTypeCode) {
        this.certificateTypeCode = certificateTypeCode;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getSexCode() {
        return sexCode;
    }

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
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

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsAuthorization() {
        return isAuthorization;
    }

    public void setIsAuthorization(Integer isAuthorization) {
        this.isAuthorization = isAuthorization;
    }

    public Integer getIsOtherApplicant() {
        return isOtherApplicant;
    }

    public void setIsOtherApplicant(Integer isOtherApplicant) {
        this.isOtherApplicant = isOtherApplicant;
    }

    public String getBackupCaseId() {
        return backupCaseId;
    }

    public void setBackupCaseId(String backupCaseId) {
        this.backupCaseId = backupCaseId;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

}
