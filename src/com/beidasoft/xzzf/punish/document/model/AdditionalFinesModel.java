package com.beidasoft.xzzf.punish.document.model;

import java.util.Map;

public class AdditionalFinesModel {
	    private String id;

	    // 文书区字
	    private String docArea;

	    // 文书年度
	    private String docYear;

	    // 文书序号
	    private String docNum;

	    // 当事人名称
	    private String partyName;

	    // 处罚决定书送达日期
	    private String penaltyDecisoinSentDateStr;

	    // 处罚决定书文书区字
	    private String docAreaFines;

	    // 处罚决定书文书年度
	    private String docYearFines;

	    // 处罚决定书文书序号
	    private String docNumFines;

	    // 罚款金额
	    private String finesSum;

	    // 履行截止日期
	    private String closingDateStr;

	    // 加处罚款金额
	    private String addFinesSum;

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

	    // 执法单位ID（代码表）
	    private String lawUnitId;

	    // 执法单位名称
	    private String lawUnitName;

	    // 执法单位印章图片
	    private String lawUnitSeal;

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

	    //是否备注
	    private String isRemarks;
	    
	    //备注
	    private String remarks;
	    
	    // 送达方式（代码表）
	    private String sendType;

	    // 送达地点
	    private String sendAddress;

	    // 组织机构编号
	    private String organsCode;

	    // 组织机构名称
	    private String organsName;

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

		public String getDocAreaFines() {
			return docAreaFines;
		}

		public void setDocAreaFines(String docAreaFines) {
			this.docAreaFines = docAreaFines;
		}

		public String getDocYearFines() {
			return docYearFines;
		}

		public void setDocYearFines(String docYearFines) {
			this.docYearFines = docYearFines;
		}

		public String getDocNumFines() {
			return docNumFines;
		}

		public void setDocNumFines(String docNumFines) {
			this.docNumFines = docNumFines;
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

		public String getLawUnitSeal() {
			return lawUnitSeal;
		}

		public void setLawUnitSeal(String lawUnitSeal) {
			this.lawUnitSeal = lawUnitSeal;
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

		public String getPenaltyDecisoinSentDateStr() {
			return penaltyDecisoinSentDateStr;
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

		public void setPenaltyDecisoinSentDateStr(String penaltyDecisoinSentDateStr) {
			this.penaltyDecisoinSentDateStr = penaltyDecisoinSentDateStr;
		}

		public String getFinesSum() {
			return finesSum;
		}

		public void setFinesSum(String finesSum) {
			this.finesSum = finesSum;
		}

		public String getClosingDateStr() {
			return closingDateStr;
		}

		public void setClosingDateStr(String closingDateStr) {
			this.closingDateStr = closingDateStr;
		}

		public String getAddFinesSum() {
			return addFinesSum;
		}

		public void setAddFinesSum(String addFinesSum) {
			this.addFinesSum = addFinesSum;
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

		public String getUpdateTimeStr() {
			return updateTimeStr;
		}

		public void setUpdateTimeStr(String updateTimeStr) {
			this.updateTimeStr = updateTimeStr;
		}

		public Map<String, String> getDocInfo(String caseCode) {
			// TODO Auto-generated method stub
			return null;
		}

}
