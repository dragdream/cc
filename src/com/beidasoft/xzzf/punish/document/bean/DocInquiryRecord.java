package com.beidasoft.xzzf.punish.document.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ZF_DOC_INQUIRY_RECORD")
public class DocInquiryRecord {
    // 调查询问唯一ID
    @Id
    @Column(name = "ID")
    private String id;

    // 询问地点
    @Column(name = "ASK_ADDRESS")
    private String askAddress;

    // 询问时间（开始）
    @Column(name = "ASK_TIME_START")
    private Date askTimeStart;

    // 询问时间（结束）
    @Column(name = "ASK_TIME_END")
    private Date askTimeEnd;

    // 询问人ID
    @Column(name = "MAJOR_PERSON_ID")
    private String majorPersonId;

    // 询问人姓名
    @Column(name = "MAJOR_PERSON_NAME")
    private String majorPersonName;

    // 询问人执法证号
    @Column(name = "MAJOR_PERSON_CODE")
    private String majorPersonCode;

    // 记录人ID
    @Column(name = "MINOR_PERSON_ID")
    private String minorPersonId;

    // 记录人姓名
    @Column(name = "MINOR_PERSON_NAME")
    private String minorPersonName;

    // 记录人执法证号
    @Column(name = "MINOR_PERSON_CODE")
    private String minorPersonCode;

    // 被询问人姓名
    @Column(name = "ASKED_NAME")
    private String askedName;

    // 被询问人性别（代码表）
    @Column(name = "ASKED_SEX")
    private String askedSex;

    // 被询问人出生年月
    @Column(name = "ASKED_BIRTHDAY")
    private Date askedBirthday;

    // 被询问人联系电话
    @Column(name = "ASKED_TEL")
    private String askedTel;

    // 被询问人证件类型
    @Column(name = "ASKED_TYPE")
    private String askedType;

    // 被询问人证件号码
    @Column(name = "ASKED_ID_NO")
    private String askedIdNo;

    // 被询问人工作单位
    @Column(name = "ASKED_UNIT")
    private String askedUnit;

    // 被询问人住址
    @Column(name = "ASKED_ADDRESS")
    private String askedAddress;

    // 询问内容记录
    @Column(name = "ASK_CONTENT")
    private String askContent;

    // 询问人签名图片
    @Lob
    @Column(name = "MAJOR_SIGNATURE_BASE64")
    private String majorSignatureBase64;

    // 询问人签名值
    @Column(name = "MAJOR_SIGNATURE_VALUE")
    private String majorSignatureValue;

    // 询问人签名位置
    @Column(name = "MAJOR_SIGNATURE_PLACE")
    private String majorSignaturePlace;

    // 记录人签名图片
    @Lob
    @Column(name = "MINOR_SIGNATURE_BASE64")
    private String minorSignatureBase64;

    // 记录人签名值
    @Column(name = "MINOR_SIGNATURE_VALUE")
    private String minorSignatureValue;

    // 记录人签名位置
    @Column(name = "MINOR_SIGNATURE_PLACE")
    private String minorSignaturePlace;

    // 询问人员签名时间
    @Column(name = "ASK_SIGNATURE_DATE")
    private Date askSignatureDate;

    // 被询问人签名图片
    @Lob
    @Column(name = "ASKED_SIGNATURE_BASE64")
    private String askedSignatureBase64;

    // 被询问人签名值
    @Column(name = "ASKED_SIGNATURE_VALUE")
    private String askedSignatureValue;

    // 被询问人签名位置
    @Column(name = "ASKED_SIGNATURE_PLACE")
    private String askedSignaturePlace;

    // 被询问人签名时间
    @Column(name = "ASKED_SIGNATURE_DATE")
    private Date askedSignatureDate;
    
    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

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
    
    // 被询问人意见签名图片
    @Lob
    @Column(name = "ASKED_SUGGESTION_BASE64")
    private String suggestionSignatureBase64;

    // 被询问人意见签名值
    @Column(name = "ASKED_SUGGESTION_VALUE")
    private String suggestionSignatureValue;

    // 被询问人意见签名位置
    @Column(name = "ASKED_SUGGESTION_PLACE")
    private String suggestionSignaturePlace;

    // 被询问人意见签名时间
    @Column(name = "ASKED_SUGGESTION_DATE")
    private Date suggestionSignatureDate;

    // 询问笔录内容
	@OneToMany(mappedBy="docInquiryRecord", fetch=FetchType.LAZY)
	private List<DocRecordContent> meetingTopics = new ArrayList<DocRecordContent>(0);

	public String getId() {
		return id;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getAskAddress() {
		return askAddress;
	}

	public void setAskAddress(String askAddress) {
		this.askAddress = askAddress;
	}

	public Date getAskTimeStart() {
		return askTimeStart;
	}

	public void setAskTimeStart(Date askTimeStart) {
		this.askTimeStart = askTimeStart;
	}

	public Date getAskTimeEnd() {
		return askTimeEnd;
	}

	public void setAskTimeEnd(Date askTimeEnd) {
		this.askTimeEnd = askTimeEnd;
	}

	public String getMajorPersonId() {
		return majorPersonId;
	}

	public void setMajorPersonId(String majorPersonId) {
		this.majorPersonId = majorPersonId;
	}

	public String getMajorPersonName() {
		return majorPersonName;
	}

	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}

	public String getMajorPersonCode() {
		return majorPersonCode;
	}

	public void setMajorPersonCode(String majorPersonCode) {
		this.majorPersonCode = majorPersonCode;
	}

	public String getMinorPersonId() {
		return minorPersonId;
	}

	public void setMinorPersonId(String minorPersonId) {
		this.minorPersonId = minorPersonId;
	}

	public String getMinorPersonName() {
		return minorPersonName;
	}

	public void setMinorPersonName(String minorPersonName) {
		this.minorPersonName = minorPersonName;
	}

	public String getMinorPersonCode() {
		return minorPersonCode;
	}

	public void setMinorPersonCode(String minorPersonCode) {
		this.minorPersonCode = minorPersonCode;
	}

	public String getAskedName() {
		return askedName;
	}

	public void setAskedName(String askedName) {
		this.askedName = askedName;
	}

	public String getAskedSex() {
		return askedSex;
	}

	public void setAskedSex(String askedSex) {
		this.askedSex = askedSex;
	}

	public Date getAskedBirthday() {
		return askedBirthday;
	}

	public void setAskedBirthday(Date askedBirthday) {
		this.askedBirthday = askedBirthday;
	}

	public String getAskedTel() {
		return askedTel;
	}

	public void setAskedTel(String askedTel) {
		this.askedTel = askedTel;
	}

	public String getAskedType() {
		return askedType;
	}

	public void setAskedType(String askedType) {
		this.askedType = askedType;
	}

	public String getAskedIdNo() {
		return askedIdNo;
	}

	public void setAskedIdNo(String askedIdNo) {
		this.askedIdNo = askedIdNo;
	}

	public String getAskedUnit() {
		return askedUnit;
	}

	public void setAskedUnit(String askedUnit) {
		this.askedUnit = askedUnit;
	}

	public String getAskedAddress() {
		return askedAddress;
	}

	public void setAskedAddress(String askedAddress) {
		this.askedAddress = askedAddress;
	}

	public String getAskContent() {
		return askContent;
	}

	public void setAskContent(String askContent) {
		this.askContent = askContent;
	}

	public String getMajorSignaturePlace() {
		return majorSignaturePlace;
	}

	public void setMajorSignaturePlace(String majorSignaturePlace) {
		this.majorSignaturePlace = majorSignaturePlace;
	}

	public String getMinorSignaturePlace() {
		return minorSignaturePlace;
	}

	public void setMinorSignaturePlace(String minorSignaturePlace) {
		this.minorSignaturePlace = minorSignaturePlace;
	}

	public Date getAskSignatureDate() {
		return askSignatureDate;
	}

	public void setAskSignatureDate(Date askSignatureDate) {
		this.askSignatureDate = askSignatureDate;
	}

	public String getAskedSignaturePlace() {
		return askedSignaturePlace;
	}

	public void setAskedSignaturePlace(String askedSignaturePlace) {
		this.askedSignaturePlace = askedSignaturePlace;
	}

	public Date getAskedSignatureDate() {
		return askedSignatureDate;
	}

	public void setAskedSignatureDate(Date askedSignatureDate) {
		this.askedSignatureDate = askedSignatureDate;
	}

	public String getEnclosureAddress() {
		return enclosureAddress;
	}

	public void setEnclosureAddress(String enclosureAddress) {
		this.enclosureAddress = enclosureAddress;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<DocRecordContent> getMeetingTopics() {
		return meetingTopics;
	}

	public void setMeetingTopics(List<DocRecordContent> meetingTopics) {
		this.meetingTopics = meetingTopics;
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

	public String getAskedSignatureBase64() {
		return askedSignatureBase64;
	}

	public void setAskedSignatureBase64(String askedSignatureBase64) {
		this.askedSignatureBase64 = askedSignatureBase64;
	}

	public String getAskedSignatureValue() {
		return askedSignatureValue;
	}

	public void setAskedSignatureValue(String askedSignatureValue) {
		this.askedSignatureValue = askedSignatureValue;
	}

	public String getSuggestionSignatureBase64() {
		return suggestionSignatureBase64;
	}

	public void setSuggestionSignatureBase64(String suggestionSignatureBase64) {
		this.suggestionSignatureBase64 = suggestionSignatureBase64;
	}

	public String getSuggestionSignatureValue() {
		return suggestionSignatureValue;
	}

	public void setSuggestionSignatureValue(String suggestionSignatureValue) {
		this.suggestionSignatureValue = suggestionSignatureValue;
	}

	public String getSuggestionSignaturePlace() {
		return suggestionSignaturePlace;
	}

	public void setSuggestionSignaturePlace(String suggestionSignaturePlace) {
		this.suggestionSignaturePlace = suggestionSignaturePlace;
	}

	public Date getSuggestionSignatureDate() {
		return suggestionSignatureDate;
	}

	public void setSuggestionSignatureDate(Date suggestionSignatureDate) {
		this.suggestionSignatureDate = suggestionSignatureDate;
	}
	

}
