package com.beidasoft.xzzf.punish.document.model;



public class CasedealModel {

	 // 案件处理呈批主键ID
    private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;
    
    //姓名名称
    private String partyName;
    
    // 名称(下拉框)
    private String cardName;

    // 号码
    private String cardNum;

    // 当事人
    private String parties;

    // 当事人内容
    private String partiesContent;

    // 联系电话
    private String phoneNumber;

    // 住址
    private String address;

    // 发现原因
    private String dailyCheck;

    // 案由
    private String cause;

    // 检查日期
    private String checktime;

    // 立案日期
    private String registerTimeStr;

    // 现场检查内容
    private String checkContent;

    // 当事人申辩情况
    private String saidContent;

    // 量罚情形
    private String penalty;

    // 承办人意见
    private String agentAdvice;
    
    //承办人签名位置(主)
    private String majorSingaturePlace;
    
    // 承办人签名图片(主)
    private String majorSignatureBase64;
    
    // 承办人签名值(主)
    private String majorSignatureValue;

    // 承办人签名位置(协)
    private String minorSigaturePlace;

    // 承办人签名图片(协)
    private String minorSignatureBase64;

    // 承办人签名值(协)
    private String minorSignatureValue;

    // 承办人签名日期
    private String sealtimeStr;

    // 承办部门意见
    private String content;

    // 承办部门签名值
    private String contentSignatureValue;

    // 承办部门签名位置
    private String contentSigaturePlace;

    // 承办部门签名图片
    private String contentSignatureBase64;

    // 承办部门签名时间
    private String contentTimeStr;

    // 法制部门意见
    private String lawAdvice;

    // 法制部门签名位置
    private String lawSignaturePlace;

    // 法制部门签名值
    private String lawSignatureValue;

    // 法制部门签名图片
    private String lawSignatureBase64;

    // 法制部门签名时间
    private String lawTimeStr;

    // 主管领导意见
    private String leaderAdvice;

    // 主管领导签名值
    private String leaderSignatureValue;

    // 主管领导签名位置
    private String leaderSignaturePlace;

    // 主管领导签名图片
    private String leaderSignatureBase64;

    // 主管领导签名时间
    private String leaderTimeStr;
    
  

    // 附件地址
    private String enclosureAddress;

    // 删除标记
    private String delFlg;

    // 执法办案唯一编号
    private String baseId;

    // 执法环节ID
    private String lawLinkId;

    // 创建人ID
    private String createUserId;

    // 创建人姓名
    private String createUserName;

    // 创建时间
    private String createTimeStr;

    // 变更人ID
    private String updateUserId;

    // 变更人姓名
    private String updateUserName;

    // 变更时间
    private String updateTimeStr;
    
    // 警告（复选框）
    private String warn;

    // 没收违法所得（复选框）
    private String confiscateReceive;

    // 没收非法财物（复选框）
    private String confiscateIllegality;

    // 罚金（根据裁量基准）
    private String penalSum;

    // 责令整顿（复选框）
    private String isStopBusiness;

    // 吊销许可（复选框）
    private String isRevokeLicense;

    // 没收所得金额
    private String confiscateReceiveContent;

    // 没收财物名
    private String confiscateIllegalityName;

    // 没收财物规格
    private String confiscateIllegalityUnit;

    // 没收财物数量
    private String confiscateIllegalitySum;

    // 罚金
    private String penalSumContent;

    // 整顿时长
    private String stopBusinessTime;

    // 吊销证书名称
    private String revokeLicenseName;
    
    // 是否集体讨论
    private String isGroupDiscussion;
    
    
 // 行政处罚决定书主键ID
    private String pId;

    // 文书区字
    private String pDocArea;

    // 文书年度
    private String pDocYear;

    // 文书序号
    private String pDocNum;

    // 当事人
    private String pPerson;

    // 号码类型(复选框单选)
    private String pNumberType;

    // 号码内容
    private String pNumberContent;

    // 单位当事人类型（代码表）
    private String pOrganType;

    // 单位当事人名称
    private String pOrganName;

    // 住所（住址）
    private String pOrganAddress;

    // 案件来源
    private String pCaseSource;

    // 案件时间
    private String pCaseDateStr;

    // 执法单位ID（代码表）
    private String pLawUnitId;

    // 检查地址
    private String pCheckAddr;

    // 案由
    private String pCauseAction;

    // 违法时间
    private String pCaseTimeStr;

    // 现场检查内容
    private String pCheckContent;

    // 证据内容
    private String pEvidenceContent;

    // 案由法规
    private String pCauseActionRegulation;

    // 违法行为
    private String pCauseBehavior;

    // 行政处罚事先告知书送达时间
    private String pSendDatePenalityStr;

    // 行政处罚事先告知书文书区字
    private String pDocAreaPenality;

    // 行政处罚事先告知书文书年度
    private String pDocYearPenality;

    // 行政处罚事先告知书文书序号
    private String pDocNumPenality;

    // 案由处罚条款
    private String pCauseActionPublish;

    // 组织机构编号
    private String pOrgansCode;

    // 组织机构名称
    private String pOrgansName;

    // 附件地址
    private String pEnclosureAddress;

    // 人民政府ID
    private String govId;

    // 人民政府名称
    private String govName;

    // 部委ID
    private String ministriesId;

    // 部委名称
    private String ministriesName;

    // 市区ID
    private String areaId;

    // 市区名称
    private String areaName;

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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
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

	public String getRegisterTimeStr() {
		return registerTimeStr;
	}

	public void setRegisterTimeStr(String registerTimeStr) {
		this.registerTimeStr = registerTimeStr;
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

	public String getSealtimeStr() {
		return sealtimeStr;
	}

	public void setSealtimeStr(String sealtimeStr) {
		this.sealtimeStr = sealtimeStr;
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

	public String getContentTimeStr() {
		return contentTimeStr;
	}

	public void setContentTimeStr(String contentTimeStr) {
		this.contentTimeStr = contentTimeStr;
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

	public String getLawTimeStr() {
		return lawTimeStr;
	}

	public void setLawTimeStr(String lawTimeStr) {
		this.lawTimeStr = lawTimeStr;
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

	public String getLeaderTimeStr() {
		return leaderTimeStr;
	}

	public void setLeaderTimeStr(String leaderTimeStr) {
		this.leaderTimeStr = leaderTimeStr;
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

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpDocArea() {
		return pDocArea;
	}

	public void setpDocArea(String pDocArea) {
		this.pDocArea = pDocArea;
	}

	public String getpDocYear() {
		return pDocYear;
	}

	public void setpDocYear(String pDocYear) {
		this.pDocYear = pDocYear;
	}

	public String getpDocNum() {
		return pDocNum;
	}

	public void setpDocNum(String pDocNum) {
		this.pDocNum = pDocNum;
	}

	public String getpPerson() {
		return pPerson;
	}

	public void setpPerson(String pPerson) {
		this.pPerson = pPerson;
	}

	public String getpNumberType() {
		return pNumberType;
	}

	public void setpNumberType(String pNumberType) {
		this.pNumberType = pNumberType;
	}

	public String getpNumberContent() {
		return pNumberContent;
	}

	public void setpNumberContent(String pNumberContent) {
		this.pNumberContent = pNumberContent;
	}

	public String getpOrganType() {
		return pOrganType;
	}

	public void setpOrganType(String pOrganType) {
		this.pOrganType = pOrganType;
	}

	public String getpOrganName() {
		return pOrganName;
	}

	public void setpOrganName(String pOrganName) {
		this.pOrganName = pOrganName;
	}

	public String getpOrganAddress() {
		return pOrganAddress;
	}

	public void setpOrganAddress(String pOrganAddress) {
		this.pOrganAddress = pOrganAddress;
	}

	public String getpCaseSource() {
		return pCaseSource;
	}

	public void setpCaseSource(String pCaseSource) {
		this.pCaseSource = pCaseSource;
	}

	public String getpCaseDateStr() {
		return pCaseDateStr;
	}

	public void setpCaseDateStr(String pCaseDateStr) {
		this.pCaseDateStr = pCaseDateStr;
	}

	public String getpLawUnitId() {
		return pLawUnitId;
	}

	public void setpLawUnitId(String pLawUnitId) {
		this.pLawUnitId = pLawUnitId;
	}

	public String getpCheckAddr() {
		return pCheckAddr;
	}

	public void setpCheckAddr(String pCheckAddr) {
		this.pCheckAddr = pCheckAddr;
	}

	public String getpCauseAction() {
		return pCauseAction;
	}

	public void setpCauseAction(String pCauseAction) {
		this.pCauseAction = pCauseAction;
	}

	public String getpCaseTimeStr() {
		return pCaseTimeStr;
	}

	public void setpCaseTimeStr(String pCaseTimeStr) {
		this.pCaseTimeStr = pCaseTimeStr;
	}

	public String getpCheckContent() {
		return pCheckContent;
	}

	public void setpCheckContent(String pCheckContent) {
		this.pCheckContent = pCheckContent;
	}

	public String getpEvidenceContent() {
		return pEvidenceContent;
	}

	public void setpEvidenceContent(String pEvidenceContent) {
		this.pEvidenceContent = pEvidenceContent;
	}

	public String getpCauseActionRegulation() {
		return pCauseActionRegulation;
	}

	public void setpCauseActionRegulation(String pCauseActionRegulation) {
		this.pCauseActionRegulation = pCauseActionRegulation;
	}

	public String getpCauseBehavior() {
		return pCauseBehavior;
	}

	public void setpCauseBehavior(String pCauseBehavior) {
		this.pCauseBehavior = pCauseBehavior;
	}

	public String getpSendDatePenalityStr() {
		return pSendDatePenalityStr;
	}

	public void setpSendDatePenalityStr(String pSendDatePenalityStr) {
		this.pSendDatePenalityStr = pSendDatePenalityStr;
	}

	public String getpDocAreaPenality() {
		return pDocAreaPenality;
	}

	public void setpDocAreaPenality(String pDocAreaPenality) {
		this.pDocAreaPenality = pDocAreaPenality;
	}

	public String getpDocYearPenality() {
		return pDocYearPenality;
	}

	public void setpDocYearPenality(String pDocYearPenality) {
		this.pDocYearPenality = pDocYearPenality;
	}

	public String getpDocNumPenality() {
		return pDocNumPenality;
	}

	public void setpDocNumPenality(String pDocNumPenality) {
		this.pDocNumPenality = pDocNumPenality;
	}

	public String getpCauseActionPublish() {
		return pCauseActionPublish;
	}

	public void setpCauseActionPublish(String pCauseActionPublish) {
		this.pCauseActionPublish = pCauseActionPublish;
	}

	public String getpOrgansCode() {
		return pOrgansCode;
	}

	public void setpOrgansCode(String pOrgansCode) {
		this.pOrgansCode = pOrgansCode;
	}

	public String getpOrgansName() {
		return pOrgansName;
	}

	public void setpOrgansName(String pOrgansName) {
		this.pOrgansName = pOrgansName;
	}

	public String getpEnclosureAddress() {
		return pEnclosureAddress;
	}

	public void setpEnclosureAddress(String pEnclosureAddress) {
		this.pEnclosureAddress = pEnclosureAddress;
	}

	public String getGovId() {
		return govId;
	}

	public void setGovId(String govId) {
		this.govId = govId;
	}

	public String getGovName() {
		return govName;
	}

	public void setGovName(String govName) {
		this.govName = govName;
	}

	public String getMinistriesId() {
		return ministriesId;
	}

	public void setMinistriesId(String ministriesId) {
		this.ministriesId = ministriesId;
	}

	public String getMinistriesName() {
		return ministriesName;
	}

	public void setMinistriesName(String ministriesName) {
		this.ministriesName = ministriesName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
    
    
}
