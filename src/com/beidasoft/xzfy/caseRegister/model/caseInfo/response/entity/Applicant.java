package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

public class Applicant {

    private String id; // 主键id
    private String applicantTypeCode; // 申请人类别代码
    private String applicantType; // 申请人类别
    private String name; // 姓名/组织名称
    private String certificateTypeCode; // 证件类型代码
    private String certificateType; // 证件类型
    private String certificateId; // 证件号码
    private String sexCode; // 性别代码
    private String sex; // 性别
    private String nationCode; // 民族代码
    private String nation; // 民族
    private String phoneNum; // 电话
    private String postCode; // 邮编
    private String postAddress; // 通信地址
    private String isAuthorization; // 是否有授权书
    private String isOtherApplicant; // 是否其他申请人

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

    public String getApplicantTypeCode() {
        return applicantTypeCode;
    }

    public void setApplicantTypeCode(String applicantTypeCode) {
        this.applicantTypeCode = applicantTypeCode;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
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

    public String getIsAuthorization() {
        return isAuthorization;
    }

    public void setIsAuthorization(String isAuthorization) {
        this.isAuthorization = isAuthorization;
    }

    public String getIsOtherApplicant() {
        return isOtherApplicant;
    }

    public void setIsOtherApplicant(String isOtherApplicant) {
        this.isOtherApplicant = isOtherApplicant;
    }

}
