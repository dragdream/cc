package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 取证情况及证据说明实体类
 */
@Entity
@Table(name="ZF_DOC_ECIDENCEDESCRIPTION")
public class DocEvidencedescription {
    // 取证情况及证据说明唯一ID 
    @Id
    @Column(name = "ID")
    private String id;

    // 当事人姓名
    @Column(name = "CLIENT_NAME")
    private String clientName;

    // 取证时间(开始)
    @Column(name = "EVIDENCE_START_TIME")
    private Date evidenceStartTime;

    // 取证时间(结束)
    @Column(name = "EVIDENCE_END_TIME")
    private Date evidenceEndTime;

    // 取证地点
    @Column(name = "EVIDENCE_ADDR")
    private String evidenceAddr;

    // 取证人
    @Column(name = "EVIDENCE_PERSON")
    private String evidencePerson;

    // 当事人现场负责人或受托人意见
    @Column(name = "CLIENT_IDEA")
    private String clientIdea;
    
    // 当事人现场负责人或受托人意见图
    @Column(name = "CLIENT_IDEA_SIGNATURE_BASE64")
    private String clientIdeaSignatureBase64;
    
    // 当事人现场负责人或受托人意见值
    @Column(name = "CLIENT_IDEA_SIGNATURE_VALUE")
    private String clientIdeaSignatureValue;
    
    // 当事人现场负责人或受托人意见位置
    @Column(name = "CLIENT_IDEA_SIGNATURE_PLACE")
    private String clientIdeaSignaturePlace;
    
    // 当事人现场负责人或受托人签名
    @Column(name = "CLIENT_PERSON_SIGNATURE_BASE64")
    private String clientPersonSignatureBase64;

    // 当事人现场负责人或受托人签名值
    @Column(name = "CLIENT_PERSON_SIGNATURE_VALUE")
    private String clientPersonSignatureValue;
    
    // 当事人现场负责人或受托人签名位置
    @Column(name = "CLIENT_PERSON_SIGNATURE_PLACE")
    private String clientPersonSignaturePlace;
    
    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 当事人签名图片
    @Lob
    @Column(name = "CLIENT_SIGNATURE_BASE64")
    private String clientSignatureBase64;

    // 当事人签名值
    @Lob
    @Column(name = "CLIENT_SIGNATURE_VALUE")
    private String clientSignatureValue;

    // 当事人签名位置
    @Column(name = "CLIENT_SIGNATURE_PLACE")
    private String clientSignaturePlace;

    // 主办人签名图片
    @Lob
    @Column(name = "MAJOR_SIGNATURE_BASE64")
    private String majorSignatureBase64;

    // 主办人签名值
    @Lob
    @Column(name = "MAJOR_SIGNATURE_VALUE")
    private String majorSignatureValue;

    // 主办人签名位置
    @Column(name = "MAJOR_SIGNATURE_PLACE")
    private String majorSignaturePlace;

    // 协办人签名图片
    @Lob
    @Column(name = "MINOR_SIGNATURE_BASE64")
    private String minorSignatureBase64;

    // 协办人签名值
    @Lob
    @Column(name = "MINOR_SIGNATURE_VALUE")
    private String minorSignatureValue;

    // 协办人签名位置
    @Column(name = "MINOR_SIGNATURE_PLACE")
    private String minorSignaturePlace;

    //执法人盖章时间
    @Column(name = "ENFORCEMENT_STAMP_DATA")
    private Date enforcementStampData;
    
    //当事人盖章时间
    @Column(name = "CLIENT_STAMP_DATA")
    private Date clientStampData;
    
    // 附件地址
    @Column(name = "PROSESS_UNIT")
    private String prosessUnit;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 执法环节ID
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人ID
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人ID
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getEvidenceStartTime() {
		return evidenceStartTime;
	}

	public void setEvidenceStartTime(Date evidenceStartTime) {
		this.evidenceStartTime = evidenceStartTime;
	}


	public Date getEvidenceEndTime() {
		return evidenceEndTime;
	}

	public void setEvidenceEndTime(Date evidenceEndTime) {
		this.evidenceEndTime = evidenceEndTime;
	}

	public String getEvidenceAddr() {
		return evidenceAddr;
	}

	public void setEvidenceAddr(String evidenceAddr) {
		this.evidenceAddr = evidenceAddr;
	}

	public String getEvidencePerson() {
		return evidencePerson;
	}

	public void setEvidencePerson(String evidencePerson) {
		this.evidencePerson = evidencePerson;
	}

	public String getClientIdea() {
		return clientIdea;
	}

	public void setClientIdea(String clientIdea) {
		this.clientIdea = clientIdea;
	}

	public String getClientSignatureBase64() {
		return clientSignatureBase64;
	}

	public void setClientSignatureBase64(String clientSignatureBase64) {
		this.clientSignatureBase64 = clientSignatureBase64;
	}

	public String getClientSignatureValue() {
		return clientSignatureValue;
	}

	public void setClientSignatureValue(String clientSignatureValue) {
		this.clientSignatureValue = clientSignatureValue;
	}

	public String getClientSignaturePlace() {
		return clientSignaturePlace;
	}

	public void setClientSignaturePlace(String clientSignaturePlace) {
		this.clientSignaturePlace = clientSignaturePlace;
	}

	public String getMajorSignatureBase64() {
		return majorSignatureBase64;
	}

	public void setMajorSignatureBase64(String majorSignatureBase64) {
		this.majorSignatureBase64 = majorSignatureBase64;
	}

	public String getMajorSignatureValue() {
		return majorSignatureValue;
	}

	public void setMajorSignatureValue(String majorSignatureValue) {
		this.majorSignatureValue = majorSignatureValue;
	}

	public String getMajorSignaturePlace() {
		return majorSignaturePlace;
	}

	public void setMajorSignaturePlace(String majorSignaturePlace) {
		this.majorSignaturePlace = majorSignaturePlace;
	}

	public String getMinorSignatureBase64() {
		return minorSignatureBase64;
	}

	public void setMinorSignatureBase64(String minorSignatureBase64) {
		this.minorSignatureBase64 = minorSignatureBase64;
	}

	public String getMinorSignatureValue() {
		return minorSignatureValue;
	}

	public void setMinorSignatureValue(String minorSignatureValue) {
		this.minorSignatureValue = minorSignatureValue;
	}

	public String getMinorSignaturePlace() {
		return minorSignaturePlace;
	}

	public void setMinorSignaturePlace(String minorSignaturePlace) {
		this.minorSignaturePlace = minorSignaturePlace;
	}

	public Date getEnforcementStampData() {
		return enforcementStampData;
	}

	public void setEnforcementStampData(Date enforcementStampData) {
		this.enforcementStampData = enforcementStampData;
	}

	public Date getClientStampData() {
		return clientStampData;
	}

	public String getClientIdeaSignatureBase64() {
		return clientIdeaSignatureBase64;
	}

	public void setClientIdeaSignatureBase64(String clientIdeaSignatureBase64) {
		this.clientIdeaSignatureBase64 = clientIdeaSignatureBase64;
	}

	public String getClientIdeaSignatureValue() {
		return clientIdeaSignatureValue;
	}

	public void setClientIdeaSignatureValue(String clientIdeaSignatureValue) {
		this.clientIdeaSignatureValue = clientIdeaSignatureValue;
	}

	public String getClientIdeaSignaturePlace() {
		return clientIdeaSignaturePlace;
	}

	public void setClientIdeaSignaturePlace(String clientIdeaSignaturePlace) {
		this.clientIdeaSignaturePlace = clientIdeaSignaturePlace;
	}

	public String getClientPersonSignatureBase64() {
		return clientPersonSignatureBase64;
	}

	public void setClientPersonSignatureBase64(String clientPersonSignatureBase64) {
		this.clientPersonSignatureBase64 = clientPersonSignatureBase64;
	}

	public String getClientPersonSignatureValue() {
		return clientPersonSignatureValue;
	}

	public void setClientPersonSignatureValue(String clientPersonSignatureValue) {
		this.clientPersonSignatureValue = clientPersonSignatureValue;
	}

	public String getClientPersonSignaturePlace() {
		return clientPersonSignaturePlace;
	}

	public void setClientPersonSignaturePlace(String clientPersonSignaturePlace) {
		this.clientPersonSignaturePlace = clientPersonSignaturePlace;
	}

	public void setClientStampData(Date clientStampData) {
		this.clientStampData = clientStampData;
	}

	public String getProsessUnit() {
		return prosessUnit;
	}

	public void setProsessUnit(String prosessUnit) {
		this.prosessUnit = prosessUnit;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getLawLinkId() {
		return lawLinkId;
	}

	public void setLawLinkId(String lawLinkId) {
		this.lawLinkId = lawLinkId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getIsRemarks() {
		return isRemarks;
	}

	public void setIsRemarks(String isRemarks) {
		this.isRemarks = isRemarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
