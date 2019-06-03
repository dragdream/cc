package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;

public class SendtoreturnModel {
	// 送达回证id
    private String id;

    // 接收单位印章图片
    private String reciveLawUnitSeal;

    // 接收单位印章值
    private String reciveLawUnitValue;

    // 接收单位印章位置
    private String reciveLawUnitPlace;
    
    // 组织编号
    private String organsCode;

    // 组织名称
    private String organsName;

    // 受送达人（单位）
    private String arrvelPeople;

    // 行政单位送达时间
    private String arrvelTimeStr;

    // 送达地点
    private String arrvelAddress;

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

    // 送达文书名称、编号
    private String arrvelNameOne;

    // 送达方式
    private String arrvelTypeOne;

    // 受送达人签名图片
    private String arrvelMajorSignatureBase64One;

    // 受送达人签名值
    private String arrvelMajorSignatureValueOne;

    // 受送达人签名位置
    private String arrvelMajorSignaturePlaceOne;

    // 受送达人签名时间
    private String arrvelTimeOneStr;
    
    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 送达文书名称、编号
    private String arrvelNameTwo;

    // 送达方式
    private String arrvelTypeTwo;

    // 受送达人签名图片
    private String arrvelMajorSignatureBase64Two;

    // 受送达人签名值
    private String arrvelMajorSignatureValueTwo;

    // 受送达人签名位置
    private String arrvelMajorSignaturePlaceTwo;

    // 受送达人签名时间
    private String arrvelTimeTwoStr;

    // 送达文书名称、编号
    private String arrvelNameThree;

    // 送达方式
    private String arrvelTypeThree;

    // 受送达人签名图片
    private String arrvelMajorSignatureBase64Three;

    // 受送达人签名值
    private String arrvelMajorSignatureValueThree;

    // 受送达人签名位置
    private String arrvelMajorSignaturePlaceThree;

    // 受送达人签名时间
    private String arrvelTimeThreeStr;

    // 送达文书1主键
    private String sendDoc1Id;

    // 送达文书2主键
    private String sendDoc2Id;

    // 送达文书3主键
    private String sendDoc3Id;

    // 备注
    private String note;

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

	public String getArrvelPeople() {
		return arrvelPeople;
	}

	public void setArrvelPeople(String arrvelPeople) {
		this.arrvelPeople = arrvelPeople;
	}
	
	public String getArrvelTimeStr() {
		return arrvelTimeStr;
	}

	public void setArrvelTimeStr(String arrvelTimeStr) {
		this.arrvelTimeStr = arrvelTimeStr;
	}

	public String getArrvelAddress() {
		return arrvelAddress;
	}

	public void setArrvelAddress(String arrvelAddress) {
		this.arrvelAddress = arrvelAddress;
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

	public String getArrvelNameOne() {
		return arrvelNameOne;
	}

	public void setArrvelNameOne(String arrvelNameOne) {
		this.arrvelNameOne = arrvelNameOne;
	}

	public String getArrvelTypeOne() {
		return arrvelTypeOne;
	}

	public void setArrvelTypeOne(String arrvelTypeOne) {
		this.arrvelTypeOne = arrvelTypeOne;
	}

	public String getArrvelMajorSignatureBase64One() {
		return arrvelMajorSignatureBase64One;
	}

	public void setArrvelMajorSignatureBase64One(
			String arrvelMajorSignatureBase64One) {
		this.arrvelMajorSignatureBase64One = arrvelMajorSignatureBase64One;
	}

	public String getArrvelMajorSignatureValueOne() {
		return arrvelMajorSignatureValueOne;
	}

	public void setArrvelMajorSignatureValueOne(String arrvelMajorSignatureValueOne) {
		this.arrvelMajorSignatureValueOne = arrvelMajorSignatureValueOne;
	}

	public String getArrvelMajorSignaturePlaceOne() {
		return arrvelMajorSignaturePlaceOne;
	}

	public void setArrvelMajorSignaturePlaceOne(String arrvelMajorSignaturePlaceOne) {
		this.arrvelMajorSignaturePlaceOne = arrvelMajorSignaturePlaceOne;
	}

	public String getArrvelTimeOneStr() {
		return arrvelTimeOneStr;
	}

	public void setArrvelTimeOneStr(String arrvelTimeOneStr) {
		this.arrvelTimeOneStr = arrvelTimeOneStr;
	}

	public String getArrvelNameTwo() {
		return arrvelNameTwo;
	}

	public void setArrvelNameTwo(String arrvelNameTwo) {
		this.arrvelNameTwo = arrvelNameTwo;
	}

	public String getArrvelTypeTwo() {
		return arrvelTypeTwo;
	}

	public void setArrvelTypeTwo(String arrvelTypeTwo) {
		this.arrvelTypeTwo = arrvelTypeTwo;
	}

	public String getArrvelMajorSignatureBase64Two() {
		return arrvelMajorSignatureBase64Two;
	}

	public void setArrvelMajorSignatureBase64Two(
			String arrvelMajorSignatureBase64Two) {
		this.arrvelMajorSignatureBase64Two = arrvelMajorSignatureBase64Two;
	}

	public String getArrvelMajorSignatureValueTwo() {
		return arrvelMajorSignatureValueTwo;
	}

	public void setArrvelMajorSignatureValueTwo(String arrvelMajorSignatureValueTwo) {
		this.arrvelMajorSignatureValueTwo = arrvelMajorSignatureValueTwo;
	}

	public String getArrvelMajorSignaturePlaceTwo() {
		return arrvelMajorSignaturePlaceTwo;
	}

	public void setArrvelMajorSignaturePlaceTwo(String arrvelMajorSignaturePlaceTwo) {
		this.arrvelMajorSignaturePlaceTwo = arrvelMajorSignaturePlaceTwo;
	}

	public String getArrvelTimeTwoStr() {
		return arrvelTimeTwoStr;
	}

	public void setArrvelTimeTwoStr(String arrvelTimeTwoStr) {
		this.arrvelTimeTwoStr = arrvelTimeTwoStr;
	}

	public String getArrvelNameThree() {
		return arrvelNameThree;
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

	public void setArrvelNameThree(String arrvelNameThree) {
		this.arrvelNameThree = arrvelNameThree;
	}

	public String getArrvelTypeThree() {
		return arrvelTypeThree;
	}

	public void setArrvelTypeThree(String arrvelTypeThree) {
		this.arrvelTypeThree = arrvelTypeThree;
	}

	public String getArrvelMajorSignatureBase64Three() {
		return arrvelMajorSignatureBase64Three;
	}

	public void setArrvelMajorSignatureBase64Three(
			String arrvelMajorSignatureBase64Three) {
		this.arrvelMajorSignatureBase64Three = arrvelMajorSignatureBase64Three;
	}

	public String getArrvelMajorSignatureValueThree() {
		return arrvelMajorSignatureValueThree;
	}

	public void setArrvelMajorSignatureValueThree(
			String arrvelMajorSignatureValueThree) {
		this.arrvelMajorSignatureValueThree = arrvelMajorSignatureValueThree;
	}

	public String getArrvelMajorSignaturePlaceThree() {
		return arrvelMajorSignaturePlaceThree;
	}

	public void setArrvelMajorSignaturePlaceThree(
			String arrvelMajorSignaturePlaceThree) {
		this.arrvelMajorSignaturePlaceThree = arrvelMajorSignaturePlaceThree;
	}

	public String getArrvelTimeThreeStr() {
		return arrvelTimeThreeStr;
	}

	public void setArrvelTimeThreeStr(String arrvelTimeThreeStr) {
		this.arrvelTimeThreeStr = arrvelTimeThreeStr;
	}

	public String getSendDoc1Id() {
		return sendDoc1Id;
	}

	public void setSendDoc1Id(String sendDoc1Id) {
		this.sendDoc1Id = sendDoc1Id;
	}

	public String getSendDoc2Id() {
		return sendDoc2Id;
	}

	public void setSendDoc2Id(String sendDoc2Id) {
		this.sendDoc2Id = sendDoc2Id;
	}

	public String getSendDoc3Id() {
		return sendDoc3Id;
	}

	public void setSendDoc3Id(String sendDoc3Id) {
		this.sendDoc3Id = sendDoc3Id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
    
	public Map<String, String> getDocInfo(String caseCode) {
		
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		return content;
		
	}
}
