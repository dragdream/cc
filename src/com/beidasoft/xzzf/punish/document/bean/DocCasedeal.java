package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 案件处理呈批表实体类
 */
@Entity
@Table(name="ZF_DOC_CASEDEAL")
public class DocCasedeal {
    // 案件处理呈批主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 文书区字
    @Column(name = "DOC_AREA")
    private String docArea;

    // 文书年度
    @Column(name = "DOC_YEAR")
    private String docYear;

    // 文书序号
    @Column(name = "DOC_NUM")
    private String docNum;
    
    //姓名名称
    @Column(name = "PARTY_NAME")
    private String partyName;
    
    // 名称(下拉框)
    @Column(name = "CARD_NAME")
    private String cardName;

    // 号码
    @Column(name = "CARD_NUM")
    private String cardNum;

    // 当事人
    @Column(name = "PARTIES")
    private String parties;

    // 当事人内容
    @Column(name = "PARTIES_CONTENT")
    private String partiesContent;

    // 联系电话
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    // 住址
    @Column(name = "ADDRESS")
    private String address;

    // 发现原因
    @Column(name = "DAILY_CHECK")
    private String dailyCheck;

    // 案由
    @Column(name = "CAUSE")
    private String cause;

    // 检查日期
    @Column(name = "CHECKTIME")
    private String checktime;

    // 立案日期
    @Column(name = "REGISTER_TIME")
    private Date registerTime;

    // 现场检查内容
    @Column(name = "CHECK_CONTENT")
    private String checkContent;

    // 当事人申辩情况
    @Column(name = "SAID_CONTENT")
    private String saidContent;

    // 量罚情形
    @Column(name = "PENALTY")
    private String penalty;

    // 承办人意见
    @Column(name = "AGENT_ADVICE")
    private String agentAdvice;
    
    //承办人签名位置(主)
    @Column(name = "MAJOR_SIGNATURE_PLACE")
    private String majorSingaturePlace;
    
    // 承办人签名图片(主)
    @Lob
    @Column(name = "MAJOR_SIGNATURE_BASE64")
    private String majorSignatureBase64;
    
    // 承办人签名值(主)
    @Lob
    @Column(name = "MAJOR_SIGNATURE_VALUE")
    private String majorSignatureValue;

    // 承办人签名位置(协)
    @Column(name = "MINOR_SIGNATURE_PLACE")
    private String minorSigaturePlace;

    // 承办人签名图片(协)
    @Lob
    @Column(name = "MINOR_SIGNATURE_BASE64")
    private String minorSignatureBase64;

    // 承办人签名值(协)
    @Lob
    @Column(name = "MINOR_SIGNATURE_VALUE")
    private String minorSignatureValue;

    // 承办人签名日期
    @Column(name = "SEALTIME")
    private Date sealtime;

    // 承办部门意见
    @Column(name = "CONTENT")
    private String content;

    // 承办部门签名值
    @Lob
    @Column(name = "CONTENT_SIGNATURE_VALUE")
    private String contentSignatureValue;

    // 承办部门签名位置
    @Column(name = "CONTENT_SIGNATURE_PLACE")
    private String contentSigaturePlace;

    // 承办部门签名图片
    @Lob
    @Column(name = "CONTENT_SIGNATURE_BASE64")
    private String contentSignatureBase64;

    // 承办部门签名时间
    @Column(name = "CONTENT_TIME")
    private Date contentTime;

    // 法制部门意见
    @Column(name = "LAW_ADVICE")
    private String lawAdvice;

    // 法制部门签名位置
    @Column(name = "LAW_SIGNATURE_PLACE")
    private String lawSignaturePlace;

    // 法制部门签名值
    @Lob
    @Column(name = "LAW_SIGNATURE_VALUE")
    private String lawSignatureValue;

    // 法制部门签名图片
    @Lob
    @Column(name = "LAW_SIGNATURE_BASE64")
    private String lawSignatureBase64;

    // 法制部门签名时间
    @Column(name = "LAW_TIME")
    private Date lawTime;

    // 主管领导意见
    @Column(name = "LEADER_ADVICE")
    private String leaderAdvice;

    // 主管领导签名值
    @Lob
    @Column(name = "LEADER_SIGNATURE_VALUE")
    private String leaderSignatureValue;

    // 主管领导签名位置
    @Column(name = "LEADER_SIGNATURE_PLACE")
    private String leaderSignaturePlace;

    // 主管领导签名图片
    @Lob
    @Column(name = "LEADER_SIGNATURE_BASE64")
    private String leaderSignatureBase64;

    // 主管领导签名时间
    @Column(name = "LEADER_TIME")
    private Date leaderTime;
    

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
    
    
    // 警告（复选框）
    @Column(name = "WARN")
    private String warn;

    // 没收违法所得（复选框）
    @Column(name = "CONFISCATE_RECEIVE")
    private String confiscateReceive;

    // 没收非法财物（复选框）
    @Column(name = "CONFISCATE_ILLEGALITY")
    private String confiscateIllegality;

    // 罚金（根据裁量基准）
    @Column(name = "PENAL_SUM")
    private String penalSum;

    // 责令整顿（复选框）
    @Column(name = "IS_STOP_BUSINESS")
    private String isStopBusiness;

    // 吊销许可（复选框）
    @Column(name = "IS_REVOKE_LICENSE")
    private String isRevokeLicense;

    // 没收所得金额
    @Column(name = "CONFISCATE_RECEIVE_CONTENT")
    private String confiscateReceiveContent;

    // 没收财物名
    @Column(name = "CONFISCATE_ILLEGALITY_NAME")
    private String confiscateIllegalityName;

    // 没收财物规格
    @Column(name = "CONFISCATE_ILLEGALITY_UNIT")
    private String confiscateIllegalityUnit;

    // 没收财物数量
    @Column(name = "CONFISCATE_ILLEGALITY_SUM")
    private String confiscateIllegalitySum;

    // 罚金
    @Column(name = "PENAL_SUM_CONTENT")
    private String penalSumContent;

    // 整顿时长
    @Column(name = "STOP_BUSINESS_TIME")
    private String stopBusinessTime;

    // 吊销证书名称
    @Column(name = "REVOKE_LICENSE_NAME")
    private String revokeLicenseName;
    
    // 是否集体讨论
    @Column(name = "IS_GROUP_DISCUSSION")
    private String isGroupDiscussion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocArea() {
		return docArea;
	}

	public void setDocArea(String docArea) {
		this.docArea = docArea;
	}

	public String getDocYear() {
		return docYear;
	}

	public void setDocYear(String docYear) {
		this.docYear = docYear;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getParties() {
		return parties;
	}

	public void setParties(String parties) {
		this.parties = parties;
	}

	public String getPartiesContent() {
		return partiesContent;
	}

	public void setPartiesContent(String partiesContent) {
		this.partiesContent = partiesContent;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDailyCheck() {
		return dailyCheck;
	}

	public void setDailyCheck(String dailyCheck) {
		this.dailyCheck = dailyCheck;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public String getSaidContent() {
		return saidContent;
	}

	public void setSaidContent(String saidContent) {
		this.saidContent = saidContent;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getAgentAdvice() {
		return agentAdvice;
	}

	public void setAgentAdvice(String agentAdvice) {
		this.agentAdvice = agentAdvice;
	}

	public String getMajorSingaturePlace() {
		return majorSingaturePlace;
	}

	public void setMajorSingaturePlace(String majorSingaturePlace) {
		this.majorSingaturePlace = majorSingaturePlace;
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

	public String getMinorSigaturePlace() {
		return minorSigaturePlace;
	}

	public void setMinorSigaturePlace(String minorSigaturePlace) {
		this.minorSigaturePlace = minorSigaturePlace;
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

	public Date getSealtime() {
		return sealtime;
	}

	public void setSealtime(Date sealtime) {
		this.sealtime = sealtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentSignatureValue() {
		return contentSignatureValue;
	}

	public void setContentSignatureValue(String contentSignatureValue) {
		this.contentSignatureValue = contentSignatureValue;
	}

	public String getContentSigaturePlace() {
		return contentSigaturePlace;
	}

	public void setContentSigaturePlace(String contentSigaturePlace) {
		this.contentSigaturePlace = contentSigaturePlace;
	}

	public String getContentSignatureBase64() {
		return contentSignatureBase64;
	}

	public void setContentSignatureBase64(String contentSignatureBase64) {
		this.contentSignatureBase64 = contentSignatureBase64;
	}

	public Date getContentTime() {
		return contentTime;
	}

	public void setContentTime(Date contentTime) {
		this.contentTime = contentTime;
	}

	public String getLawAdvice() {
		return lawAdvice;
	}

	public void setLawAdvice(String lawAdvice) {
		this.lawAdvice = lawAdvice;
	}

	public String getLawSignaturePlace() {
		return lawSignaturePlace;
	}

	public void setLawSignaturePlace(String lawSignaturePlace) {
		this.lawSignaturePlace = lawSignaturePlace;
	}

	public String getLawSignatureValue() {
		return lawSignatureValue;
	}

	public void setLawSignatureValue(String lawSignatureValue) {
		this.lawSignatureValue = lawSignatureValue;
	}

	public String getLawSignatureBase64() {
		return lawSignatureBase64;
	}

	public void setLawSignatureBase64(String lawSignatureBase64) {
		this.lawSignatureBase64 = lawSignatureBase64;
	}

	public Date getLawTime() {
		return lawTime;
	}

	public void setLawTime(Date lawTime) {
		this.lawTime = lawTime;
	}

	public String getLeaderAdvice() {
		return leaderAdvice;
	}

	public void setLeaderAdvice(String leaderAdvice) {
		this.leaderAdvice = leaderAdvice;
	}

	public String getLeaderSignatureValue() {
		return leaderSignatureValue;
	}

	public void setLeaderSignatureValue(String leaderSignatureValue) {
		this.leaderSignatureValue = leaderSignatureValue;
	}

	public String getLeaderSignaturePlace() {
		return leaderSignaturePlace;
	}

	public void setLeaderSignaturePlace(String leaderSignaturePlace) {
		this.leaderSignaturePlace = leaderSignaturePlace;
	}

	public String getLeaderSignatureBase64() {
		return leaderSignatureBase64;
	}

	public void setLeaderSignatureBase64(String leaderSignatureBase64) {
		this.leaderSignatureBase64 = leaderSignatureBase64;
	}

	public Date getLeaderTime() {
		return leaderTime;
	}

	public void setLeaderTime(Date leaderTime) {
		this.leaderTime = leaderTime;
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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String getConfiscateReceive() {
		return confiscateReceive;
	}

	public void setConfiscateReceive(String confiscateReceive) {
		this.confiscateReceive = confiscateReceive;
	}

	public String getConfiscateIllegality() {
		return confiscateIllegality;
	}

	public void setConfiscateIllegality(String confiscateIllegality) {
		this.confiscateIllegality = confiscateIllegality;
	}

	public String getPenalSum() {
		return penalSum;
	}

	public void setPenalSum(String penalSum) {
		this.penalSum = penalSum;
	}

	public String getIsStopBusiness() {
		return isStopBusiness;
	}

	public void setIsStopBusiness(String isStopBusiness) {
		this.isStopBusiness = isStopBusiness;
	}

	public String getIsRevokeLicense() {
		return isRevokeLicense;
	}

	public void setIsRevokeLicense(String isRevokeLicense) {
		this.isRevokeLicense = isRevokeLicense;
	}

	public String getConfiscateReceiveContent() {
		return confiscateReceiveContent;
	}

	public void setConfiscateReceiveContent(String confiscateReceiveContent) {
		this.confiscateReceiveContent = confiscateReceiveContent;
	}

	public String getConfiscateIllegalityName() {
		return confiscateIllegalityName;
	}

	public void setConfiscateIllegalityName(String confiscateIllegalityName) {
		this.confiscateIllegalityName = confiscateIllegalityName;
	}

	public String getConfiscateIllegalityUnit() {
		return confiscateIllegalityUnit;
	}

	public void setConfiscateIllegalityUnit(String confiscateIllegalityUnit) {
		this.confiscateIllegalityUnit = confiscateIllegalityUnit;
	}

	public String getConfiscateIllegalitySum() {
		return confiscateIllegalitySum;
	}

	public void setConfiscateIllegalitySum(String confiscateIllegalitySum) {
		this.confiscateIllegalitySum = confiscateIllegalitySum;
	}

	public String getPenalSumContent() {
		return penalSumContent;
	}

	public void setPenalSumContent(String penalSumContent) {
		this.penalSumContent = penalSumContent;
	}

	public String getStopBusinessTime() {
		return stopBusinessTime;
	}

	public void setStopBusinessTime(String stopBusinessTime) {
		this.stopBusinessTime = stopBusinessTime;
	}

	public String getRevokeLicenseName() {
		return revokeLicenseName;
	}

	public void setRevokeLicenseName(String revokeLicenseName) {
		this.revokeLicenseName = revokeLicenseName;
	}

	public String getIsGroupDiscussion() {
		return isGroupDiscussion;
	}

	public void setIsGroupDiscussion(String isGroupDiscussion) {
		this.isGroupDiscussion = isGroupDiscussion;
	}
}
