package com.beidasoft.xzzf.punish.common.model;

/**
 * 案件办理标识表子表MODEL类
 */
public class PunishBaseDetailModel {
    // 执法办案统一编号
    private String baseId;
    
    // 主键
    private String Id;
    
    // 案件编号
    private String baseCode;

    // 案件来源ID
    private String sourceId;

    // 案件来源类型
    private String sourceType;

    // 当事人类型
    private String litigantType;

    // 单位当事人名称
    private String organName;

    // 单位当事人住所
    private String organAddress;

    // 单位当事人类型（代码表）
    private String organType;

    // 单位负责人姓名
    private String organLeadingName;

    // 单位负责人联系电话
    private String organLeadingTel;

    // 个人名称
    private String psnName;

    // 个人字号名称
    private String psnShopName;

    // 个人当事人性别
    private String psnSex;

    // 个人当事人联系电话
    private String psnTel;

    // 个人当事人住址
    private String psnAddress;

    // 个人当事人证件类型（代码表）
    private String psnType;

    // 个人当事人证件号码
    private String psnIdNo;

    // 创建日期
    private String createDateStr;

    public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getLitigantType() {
        return litigantType;
    }

    public void setLitigantType(String litigantType) {
        this.litigantType = litigantType;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganAddress() {
        return organAddress;
    }

    public void setOrganAddress(String organAddress) {
        this.organAddress = organAddress;
    }

    public String getOrganType() {
        return organType;
    }

    public void setOrganType(String organType) {
        this.organType = organType;
    }

    public String getOrganLeadingName() {
        return organLeadingName;
    }

    public void setOrganLeadingName(String organLeadingName) {
        this.organLeadingName = organLeadingName;
    }

    public String getOrganLeadingTel() {
        return organLeadingTel;
    }

    public void setOrganLeadingTel(String organLeadingTel) {
        this.organLeadingTel = organLeadingTel;
    }

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }

    public String getPsnShopName() {
        return psnShopName;
    }

    public void setPsnShopName(String psnShopName) {
        this.psnShopName = psnShopName;
    }

    public String getPsnSex() {
        return psnSex;
    }

    public void setPsnSex(String psnSex) {
        this.psnSex = psnSex;
    }

    public String getPsnTel() {
        return psnTel;
    }

    public void setPsnTel(String psnTel) {
        this.psnTel = psnTel;
    }

    public String getPsnAddress() {
        return psnAddress;
    }

    public void setPsnAddress(String psnAddress) {
        this.psnAddress = psnAddress;
    }

    public String getPsnType() {
        return psnType;
    }

    public void setPsnType(String psnType) {
        this.psnType = psnType;
    }

    public String getPsnIdNo() {
        return psnIdNo;
    }

    public void setPsnIdNo(String psnIdNo) {
        this.psnIdNo = psnIdNo;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
