package com.beidasoft.xzzf.punish.document.model;




public class CasemovelistModel {
	// 案件移送单id
    private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 名字
    private String partyName;

    // 当事人类型
    private String partyType;
    
    //负责人名称
    private String partyTypeName;
    
    // 当事人联系电话
    private String partyPhone;

    // 当事人住址
    private String partyAddress;

    // 案由
    private String causeAction;
    
    // 案由名
    private String causeActionName;

    // 案件来源
    private String caseSource;

    // 案情概要及移送理由
    private String caseOverview;

    // 移送清单文书区字
    private String moveDocArea;

    // 移送清单文书年度
    private String moveDocYear;

    // 移送清单文书序号
    private String moveDocNum;

    // 移送单位意见
    private String moveAdvice;

    // 移送人签名图片
    private String moveMajorSignatureBase64;

    // 移送人签名值
    private String moveMajorSignatureValue;

    // 移送人签名位置
    private String moveMajorSignaturePlace;
    
    // 移送人2签名图片
    private String moveMinorSignatureBase64;

    // 移送人2签名值
    private String moveMinorSignatureValue;

    // 移送人2签名位置
    private String moveMinorSignaturePlace;

    // 移送人联系电话
    private String movePhone;

    // 移送单位印章图片
    private String moveLawUnitSeal;

    // 移送单位印章值
    private String moveLawUnitValue;

    // 移送单位印章位置
    private String moveLawUnitPlace;

    // 移送单位印章时间
    private String moveLawUnitDateStr;

    // 接收单位意见
    private String reciveAdvice;

    // 接收人签名图片
    private String reciveMajorSignatureBase64;

    // 接收人签名值
    private String reciveMajorSignatureValue;

    // 接收人签名位置
    private String reciveMajorSignaturePlace;

    // 接收人联系电话
    private String recivePhone;

    // 接收单位印章图片
    private String reciveLawUnitSeal;

    // 接收单位印章值
    private String reciveLawUnitValue;

    // 接收单位印章位置
    private String reciveLawUnitPlace;

    // 接收单位印章时间
    private String reciveLawUnitDateStr;

    // 移送组织机构编号
    private String organsCode;

    // 移送组织机构名称
    private String organsName;

    // 接收组织机构编号
    private String organsCodeTo;

    // 接收组织机构名称
    private String organsNameTo;

    // 附件地址
    private String enclosureAddress;

    // 删除标记
    private String delFlg;

    // 执法环节id
    private String lawLinkId;

    // 变更人id
    private String updateUserId;

    // 变更人姓名
    private String updateUserName;

    // 变更时间
    private String updateTimeStr;

    // 执法办案唯一id
    private String baseId;

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

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getPartyPhone() {
		return partyPhone;
	}

	public void setPartyPhone(String partyPhone) {
		this.partyPhone = partyPhone;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getCauseAction() {
		return causeAction;
	}

	public void setCauseAction(String causeAction) {
		this.causeAction = causeAction;
	}

	public String getCaseSource() {
		return caseSource;
	}

	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}

	public String getCaseOverview() {
		return caseOverview;
	}

	public void setCaseOverview(String caseOverview) {
		this.caseOverview = caseOverview;
	}

	public String getMoveDocArea() {
		return moveDocArea;
	}

	public void setMoveDocArea(String moveDocArea) {
		this.moveDocArea = moveDocArea;
	}

	public String getMoveDocYear() {
		return moveDocYear;
	}

	public void setMoveDocYear(String moveDocYear) {
		this.moveDocYear = moveDocYear;
	}

	public String getMoveDocNum() {
		return moveDocNum;
	}

	public void setMoveDocNum(String moveDocNum) {
		this.moveDocNum = moveDocNum;
	}

	public String getMoveAdvice() {
		return moveAdvice;
	}

	public void setMoveAdvice(String moveAdvice) {
		this.moveAdvice = moveAdvice;
	}

	public String getMoveMajorSignatureBase64() {
		return moveMajorSignatureBase64;
	}

	public void setMoveMajorSignatureBase64(String moveMajorSignatureBase64) {
		this.moveMajorSignatureBase64 = moveMajorSignatureBase64;
	}

	public String getMoveMajorSignatureValue() {
		return moveMajorSignatureValue;
	}

	public void setMoveMajorSignatureValue(String moveMajorSignatureValue) {
		this.moveMajorSignatureValue = moveMajorSignatureValue;
	}

	public String getMoveMajorSignaturePlace() {
		return moveMajorSignaturePlace;
	}

	public void setMoveMajorSignaturePlace(String moveMajorSignaturePlace) {
		this.moveMajorSignaturePlace = moveMajorSignaturePlace;
	}

	public String getMovePhone() {
		return movePhone;
	}

	public void setMovePhone(String movePhone) {
		this.movePhone = movePhone;
	}

	public String getMoveLawUnitSeal() {
		return moveLawUnitSeal;
	}

	public void setMoveLawUnitSeal(String moveLawUnitSeal) {
		this.moveLawUnitSeal = moveLawUnitSeal;
	}

	public String getMoveLawUnitValue() {
		return moveLawUnitValue;
	}

	public void setMoveLawUnitValue(String moveLawUnitValue) {
		this.moveLawUnitValue = moveLawUnitValue;
	}

	public String getMoveLawUnitPlace() {
		return moveLawUnitPlace;
	}

	public void setMoveLawUnitPlace(String moveLawUnitPlace) {
		this.moveLawUnitPlace = moveLawUnitPlace;
	}

	public String getMoveLawUnitDateStr() {
		return moveLawUnitDateStr;
	}

	public void setMoveLawUnitDateStr(String moveLawUnitDateStr) {
		this.moveLawUnitDateStr = moveLawUnitDateStr;
	}

	public String getReciveAdvice() {
		return reciveAdvice;
	}

	public void setReciveAdvice(String reciveAdvice) {
		this.reciveAdvice = reciveAdvice;
	}

	public String getReciveMajorSignatureBase64() {
		return reciveMajorSignatureBase64;
	}

	public void setReciveMajorSignatureBase64(String reciveMajorSignatureBase64) {
		this.reciveMajorSignatureBase64 = reciveMajorSignatureBase64;
	}

	public String getReciveMajorSignatureValue() {
		return reciveMajorSignatureValue;
	}

	public void setReciveMajorSignatureValue(String reciveMajorSignatureValue) {
		this.reciveMajorSignatureValue = reciveMajorSignatureValue;
	}

	public String getReciveMajorSignaturePlace() {
		return reciveMajorSignaturePlace;
	}

	public void setReciveMajorSignaturePlace(String reciveMajorSignaturePlace) {
		this.reciveMajorSignaturePlace = reciveMajorSignaturePlace;
	}

	public String getRecivePhone() {
		return recivePhone;
	}

	public void setRecivePhone(String recivePhone) {
		this.recivePhone = recivePhone;
	}

	public String getReciveLawUnitSeal() {
		return reciveLawUnitSeal;
	}

	public void setReciveLawUnitSeal(String reciveLawUnitSeal) {
		this.reciveLawUnitSeal = reciveLawUnitSeal;
	}

	public String getReciveLawUnitValue() {
		return reciveLawUnitValue;
	}

	public void setReciveLawUnitValue(String reciveLawUnitValue) {
		this.reciveLawUnitValue = reciveLawUnitValue;
	}

	public String getReciveLawUnitPlace() {
		return reciveLawUnitPlace;
	}

	public void setReciveLawUnitPlace(String reciveLawUnitPlace) {
		this.reciveLawUnitPlace = reciveLawUnitPlace;
	}

	public String getReciveLawUnitDateStr() {
		return reciveLawUnitDateStr;
	}

	public void setReciveLawUnitDateStr(String reciveLawUnitDateStr) {
		this.reciveLawUnitDateStr = reciveLawUnitDateStr;
	}
	
	public String getOrgansCode() {
		return organsCode;
	}

	public void setOrgansCode(String organsCode) {
		this.organsCode = organsCode;
	}

	public String getOrgansName() {
		return organsName;
	}

	public void setOrgansName(String organsName) {
		this.organsName = organsName;
	}

	public String getOrgansCodeTo() {
		return organsCodeTo;
	}

	public void setOrgansCodeTo(String organsCodeTo) {
		this.organsCodeTo = organsCodeTo;
	}

	public String getOrgansNameTo() {
		return organsNameTo;
	}

	public void setOrgansNameTo(String organsNameTo) {
		this.organsNameTo = organsNameTo;
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

	public String getLawLinkId() {
		return lawLinkId;
	}

	public void setLawLinkId(String lawLinkId) {
		this.lawLinkId = lawLinkId;
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

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getPartyTypeName() {
		return partyTypeName;
	}

	public void setPartyTypeName(String partyTypeName) {
		this.partyTypeName = partyTypeName;
	}

	public String getMoveMinorSignatureBase64() {
		return moveMinorSignatureBase64;
	}

	public void setMoveMinorSignatureBase64(String moveMinorSignatureBase64) {
		this.moveMinorSignatureBase64 = moveMinorSignatureBase64;
	}

	public String getMoveMinorSignatureValue() {
		return moveMinorSignatureValue;
	}

	public void setMoveMinorSignatureValue(String moveMinorSignatureValue) {
		this.moveMinorSignatureValue = moveMinorSignatureValue;
	}

	public String getMoveMinorSignaturePlace() {
		return moveMinorSignaturePlace;
	}

	public void setMoveMinorSignaturePlace(String moveMinorSignaturePlace) {
		this.moveMinorSignaturePlace = moveMinorSignaturePlace;
	}

	public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
	}
    
    
    
}
