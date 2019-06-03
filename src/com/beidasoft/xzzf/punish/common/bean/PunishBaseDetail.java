package com.beidasoft.xzzf.punish.common.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 案件办理标识表子表实体类
 */
@Entity
@Table(name="ZF_PUNISH_BASE_DETAIL")
public class PunishBaseDetail {
	
    // 主键
	@javax.persistence.Id
    @Column(name = "ID")
    private String Id;
    
    // 执法办案统一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 案件编号
    @Column(name = "BASE_CODE")
    private String baseCode;

    // 案件来源ID
    @Column(name = "SOURCE_ID")
    private String sourceId;

    // 案件来源类型
    @Column(name = "SOURCE_TYPE")
    private String sourceType;

    // 当事人类型
    @Column(name = "LITIGANT_TYPE")
    private String litigantType;

    // 单位当事人名称
    @Column(name = "ORGAN_NAME")
    private String organName;

    // 单位当事人住所
    @Column(name = "ORGAN_ADDRESS")
    private String organAddress;

    // 单位当事人类型（代码表）
    @Column(name = "ORGAN_TYPE")
    private String organType;

    // 单位负责人姓名
    @Column(name = "ORGAN_LEADING_NAME")
    private String organLeadingName;

    // 单位负责人联系电话
    @Column(name = "ORGAN_LEADING_TEL")
    private String organLeadingTel;

    // 个人名称
    @Column(name = "PSN_NAME")
    private String psnName;

    // 个人字号名称
    @Column(name = "PSN_SHOP_NAME")
    private String psnShopName;

    // 个人当事人性别
    @Column(name = "PSN_SEX")
    private String psnSex;

    // 个人当事人联系电话
    @Column(name = "PSN_TEL")
    private String psnTel;

    // 个人当事人住址
    @Column(name = "PSN_ADDRESS")
    private String psnAddress;

    // 个人当事人证件类型（代码表）
    @Column(name = "PSN_TYPE")
    private String psnType;

    // 个人当事人证件号码
    @Column(name = "PSN_ID_NO")
    private String psnIdNo;

    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
