package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 第三人信息
 * 
 * @author cc
 * 
 */
@Entity
@Table(name = "FY_THIRD_PARTY")
public class FyThirdParty {

    /** 主键:主键 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String id;

    /** 案件ID */
    @Column(name = "CASE_ID")
    private String caseId;

    /** 第三人姓名 */
    @Column(name = "THIRD_PARTY_NAME")
    private String thirdPartyName;

    /** 第三人联系电话 */
    @Column(name = "THIRD_PARTY_PHONENUM")
    private String thirdPartyPhonenum;

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

    /** isAuthorization */
    @Column(name = "IS_AUTHORIZATION")
    private Integer isAuthorization;

    /** 创建人Id */
    @Column(name = "CREATED_USER_ID")
    private String createdUserId;

    /** 修改人ID */
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;

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

    /** 民族代码 */
    @Column(name = "NATION")
    private String nation;

    /** 电话 */
    @Column(name = "PHONE_NUM")
    private String phoneNum;

    /** 邮编 */
    @Column(name = "POSTCODE")
    private String postcode;

    /** 通信地址 */
    @Column(name = "POST_ADDRESS")
    private String postAddress;

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

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
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

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public String getThirdPartyPhonenum() {
        return thirdPartyPhonenum;
    }

    public void setThirdPartyPhonenum(String thirdPartyPhonenum) {
        this.thirdPartyPhonenum = thirdPartyPhonenum;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }
}
