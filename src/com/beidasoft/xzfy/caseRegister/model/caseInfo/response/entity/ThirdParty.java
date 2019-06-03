package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

public class ThirdParty {

    /** 主键ID */
    private String id;

    /** 第三人姓名 */
    private String thirdPartyName;

    /** 证件类型代码:01 身份证 02 港澳居民通行证 99 其他 */
    private String certificateTypeCode;

    /** 证件类型:01 身份证 02 港澳居民通行证 99 其他 */
    private String certificateType;

    /** 证件号码 */
    private String certificateId;

    /** 性别代码 */
    private String sexCode;

    /** 性别 */
    private String sex;

    /** 民族代码 */
    private String nationCode;

    /** 民族 */
    private String nation;

    /** 第三人联系电话 */
    private String thirdPartyPhonenum;

    /** isAuthorization */
    private Integer isAuthorization;

    /** 电话 */
    private String phoneNum;

    /** 邮编 */
    private String postcode;

    /** 通信地址 */
    private String postAddress;

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

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
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

    public String getThirdPartyPhonenum() {
        return thirdPartyPhonenum;
    }

    public void setThirdPartyPhonenum(String thirdPartyPhonenum) {
        this.thirdPartyPhonenum = thirdPartyPhonenum;
    }

    public Integer getIsAuthorization() {
        return isAuthorization;
    }

    public void setIsAuthorization(Integer isAuthorization) {
        this.isAuthorization = isAuthorization;
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
