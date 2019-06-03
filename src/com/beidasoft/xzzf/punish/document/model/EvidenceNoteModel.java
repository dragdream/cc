package com.beidasoft.xzzf.punish.document.model;

import java.util.Map;

public class EvidenceNoteModel {

	 private String id;

	    // 文书区字
	    private String docArea;

	    // 文书年度
	    private String docYear;

	    // 文书序号
	    private String docNum;

	    // 当事人名称
	    private String partyName;

	    // 违法行为
	    private String illegalAct;
	    
	    // 案由id
	    private String causeAction;

	    // 法律条款
	    private String statuteClause;

	    // 保存方式（代码表）
	    private String saveWay;

	    // 保存地点
	    private String saveAddress;

	    // 保存期限（开始）
	    private String saveDateStartStr;

	    // 保存期限（结束）
	    private String saveDateEndStr;

	    // 主办人签名图片
	    private String majorSignatureBase64;

	    // 主办人签名值
	    private String majorSignatureValue;

	    // 主办人签名位置
	    private String majorSignaturePlace;

	    // 主办人执法证号
	    private String majorCode;

	    // 协办人签名图片
	    private String minorSignatureBase64;

	    // 协办人签名值
	    private String minorSignatureValue;

	    // 协办人签名位置
	    private String minorSignaturePlace;

	    // 协办人执法证号
	    private String minorCode;

	    // 物品清单文书区字
	    private String docAreaGoods;

	    // 物品清单文书年度
	    private String docYearGoods;

	    // 物品清单文书序号
	    private String docNumGoods;
	    
	    // 物品清单内容
	    private String itemContain;

//未使用	    // 执法单位ID（代码表）
	    private String lawUnitId;

	    // 执法单位名称
	    private String lawUnitName;

	    // 执法单位印章图片
	    private String lawUnitSealBase64;

	    // 执法单位印章值
	    private String lawUnitValue;

	    // 执法单位印章位置
	    private String lawUnitPlace;

	    // 执法单位印章时间
	    private String lawUnitDateStr;

	    // 送达人（主）签名图片
	    private String senderMjrSignatureBase64;

	    // 送达人（主）签名值
	    private String senderMjrSignatureValue;

	    // 送达人（主）签名位置
	    private String senderMjrSignaturePlace;

	    // 送达人（协）签名图片
	    private String senderMnrSignatureBase64;

	    // 送达人（协）签名值
	    private String senderMnrSignatureValue;

	    // 送达人（协）签名位置
	    private String senderMnrSignaturePlace;

	    // 送达时间
	    private String sendTimeStr;

	    // 受送达人签名图片
	    private String receiverSignatureBase64;

	    // 受送达人签名值
	    private String receiverSignatureValue;

	    // 受送达人签名位置
	    private String receiverSignaturePlace;

	    // 受送达人签名时间
	    private String receiverSignatureDateStr;

	    // 送达方式（代码表）
	    private String sendType;

	    // 送达地点
	    private String sendAddress;

	    //是否备注
	    private String isRemarks;
	    
	    //备注
	    private String remarks;
	    
	    // 附件地址
	    private String enclosureAddress;

	    // 删除标记
	    private String delFlg;

	    // 执法办案唯一编号
	    private String baseId;

	    // 执法环节ID
	    private String lawLinkId;

	    // 变更人ID
	    private String updateUserId;

	    // 变更人姓名
	    private String updateUserName;

	    // 变更时间
	    private String updateTimeStr;
	    
	    //组织机构名称
	    private String organsName;
	    

		public String getItemContain() {
			return itemContain;
		}

		public void setItemContain(String itemContain) {
			this.itemContain = itemContain;
		}

		public String getOrgansName() {
			return organsName;
		}

		public void setOrgansName(String organsName) {
			this.organsName = organsName;
		}

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

		public String getIllegalAct() {
			return illegalAct;
		}

		public void setIllegalAct(String illegalAct) {
			this.illegalAct = illegalAct;
		}

		public String getStatuteClause() {
			return statuteClause;
		}

		public void setStatuteClause(String statuteClause) {
			this.statuteClause = statuteClause;
		}

		public String getSaveWay() {
			return saveWay;
		}

		public void setSaveWay(String saveWay) {
			this.saveWay = saveWay;
		}

		public String getSaveAddress() {
			return saveAddress;
		}

		public void setSaveAddress(String saveAddress) {
			this.saveAddress = saveAddress;
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

		public String getMajorCode() {
			return majorCode;
		}

		public void setMajorCode(String majorCode) {
			this.majorCode = majorCode;
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

		public String getMinorCode() {
			return minorCode;
		}

		public void setMinorCode(String minorCode) {
			this.minorCode = minorCode;
		}

		public String getDocAreaGoods() {
			return docAreaGoods;
		}

		public void setDocAreaGoods(String docAreaGoods) {
			this.docAreaGoods = docAreaGoods;
		}

		public String getDocYearGoods() {
			return docYearGoods;
		}

		public void setDocYearGoods(String docYearGoods) {
			this.docYearGoods = docYearGoods;
		}

		public String getDocNumGoods() {
			return docNumGoods;
		}

		public void setDocNumGoods(String docNumGoods) {
			this.docNumGoods = docNumGoods;
		}

		public String getLawUnitId() {
			return lawUnitId;
		}

		public void setLawUnitId(String lawUnitId) {
			this.lawUnitId = lawUnitId;
		}

		public String getLawUnitName() {
			return lawUnitName;
		}

		public void setLawUnitName(String lawUnitName) {
			this.lawUnitName = lawUnitName;
		}



		public String getLawUnitSealBase64() {
			return lawUnitSealBase64;
		}

		public void setLawUnitSealBase64(String lawUnitSealBase64) {
			this.lawUnitSealBase64 = lawUnitSealBase64;
		}

		public String getLawUnitValue() {
			return lawUnitValue;
		}

		public void setLawUnitValue(String lawUnitValue) {
			this.lawUnitValue = lawUnitValue;
		}

		public String getLawUnitPlace() {
			return lawUnitPlace;
		}

		public void setLawUnitPlace(String lawUnitPlace) {
			this.lawUnitPlace = lawUnitPlace;
		}

		public String getSenderMjrSignatureBase64() {
			return senderMjrSignatureBase64;
		}

		public void setSenderMjrSignatureBase64(String senderMjrSignatureBase64) {
			this.senderMjrSignatureBase64 = senderMjrSignatureBase64;
		}

		public String getSenderMjrSignatureValue() {
			return senderMjrSignatureValue;
		}

		public void setSenderMjrSignatureValue(String senderMjrSignatureValue) {
			this.senderMjrSignatureValue = senderMjrSignatureValue;
		}

		public String getSenderMjrSignaturePlace() {
			return senderMjrSignaturePlace;
		}

		public void setSenderMjrSignaturePlace(String senderMjrSignaturePlace) {
			this.senderMjrSignaturePlace = senderMjrSignaturePlace;
		}

		public String getSenderMnrSignatureBase64() {
			return senderMnrSignatureBase64;
		}

		public void setSenderMnrSignatureBase64(String senderMnrSignatureBase64) {
			this.senderMnrSignatureBase64 = senderMnrSignatureBase64;
		}

		public String getSenderMnrSignatureValue() {
			return senderMnrSignatureValue;
		}

		public void setSenderMnrSignatureValue(String senderMnrSignatureValue) {
			this.senderMnrSignatureValue = senderMnrSignatureValue;
		}

		public String getSenderMnrSignaturePlace() {
			return senderMnrSignaturePlace;
		}

		public void setSenderMnrSignaturePlace(String senderMnrSignaturePlace) {
			this.senderMnrSignaturePlace = senderMnrSignaturePlace;
		}

		public String getReceiverSignatureBase64() {
			return receiverSignatureBase64;
		}

		public void setReceiverSignatureBase64(String receiverSignatureBase64) {
			this.receiverSignatureBase64 = receiverSignatureBase64;
		}

		public String getReceiverSignatureValue() {
			return receiverSignatureValue;
		}

		public void setReceiverSignatureValue(String receiverSignatureValue) {
			this.receiverSignatureValue = receiverSignatureValue;
		}

		public String getReceiverSignaturePlace() {
			return receiverSignaturePlace;
		}

		public void setReceiverSignaturePlace(String receiverSignaturePlace) {
			this.receiverSignaturePlace = receiverSignaturePlace;
		}

		public String getSendType() {
			return sendType;
		}

		public void setSendType(String sendType) {
			this.sendType = sendType;
		}

		public String getSendAddress() {
			return sendAddress;
		}

		public void setSendAddress(String sendAddress) {
			this.sendAddress = sendAddress;
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

		public String getSaveDateStartStr() {
			return saveDateStartStr;
		}

		public void setSaveDateStartStr(String saveDateStartStr) {
			this.saveDateStartStr = saveDateStartStr;
		}

		public String getSaveDateEndStr() {
			return saveDateEndStr;
		}

		public void setSaveDateEndStr(String saveDateEndStr) {
			this.saveDateEndStr = saveDateEndStr;
		}

		public String getLawUnitDateStr() {
			return lawUnitDateStr;
		}

		public void setLawUnitDateStr(String lawUnitDateStr) {
			this.lawUnitDateStr = lawUnitDateStr;
		}

		public String getSendTimeStr() {
			return sendTimeStr;
		}

		public void setSendTimeStr(String sendTimeStr) {
			this.sendTimeStr = sendTimeStr;
		}

		public String getReceiverSignatureDateStr() {
			return receiverSignatureDateStr;
		}

		public void setReceiverSignatureDateStr(String receiverSignatureDateStr) {
			this.receiverSignatureDateStr = receiverSignatureDateStr;
		}

		public String getCauseAction() {
			return causeAction;
		}

		public void setCauseAction(String causeAction) {
			this.causeAction = causeAction;
		}

		public Map<String, String> getDocInfo(String caseCode) {
			// TODO Auto-generated method stub
			return null;
		}
	    
	    
}
