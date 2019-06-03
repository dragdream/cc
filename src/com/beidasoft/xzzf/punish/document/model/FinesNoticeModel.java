package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

public class FinesNoticeModel {
	// 催告书唯一ID
	private String id;

	// 文书区字
	private String docArea;

	// 文书年度
	private String docYear;

	// 文书序号
	private String docNum;

	// 当事人名称
	private String partyName;

	// 执法机关名称
	private String agencyName;

	// 处罚决定书选择状态
	private String finesChkState;

	// 处罚决定书送达日期
	private String penaltyDecisoinSentDateStr;

	// 处罚决定书文书区字
	private String docAreaFines;

	// 处罚决定书文书年度
	private String docYearFines;

	// 处罚决定书文书序号
	private String docNumFines;

	// 加处罚决定书选择状态
	private String addFinesChkState;

	// 加处罚决定书送达日期
	private String addPenaltyDecisoinSentDateStr;

	// 加处罚决定书文书区字
	private String docAreaAddfines;

	// 加处罚决定书文书年度
	private String docYearAddfines;

	// 加处罚决定书文书序号
	private String docNumAddfines;

	// 罚款金额选择状态
	private String finesSumChkState;

	// 罚款金额
	private String finesSum;

	// 违法所得选择状态
	private String illegalIncomeChkState;

	// 违法所得金额
	private String illegalIncomeSum;

	// 加处罚款金额选择状态
	private String addfinesSumChkState;

	// 加处罚款金额
	private String addfinesSum;

	// 停业整顿选择状态
	private String closeDownChkState;

	// 停业整顿天数
	private String closeDownDays;

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

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getFinesChkState() {
		return finesChkState;
	}

	public void setFinesChkState(String finesChkState) {
		this.finesChkState = finesChkState;
	}

	public String getPenaltyDecisoinSentDateStr() {
		return penaltyDecisoinSentDateStr;
	}

	public void setPenaltyDecisoinSentDateStr(String penaltyDecisoinSentDateStr) {
		this.penaltyDecisoinSentDateStr = penaltyDecisoinSentDateStr;
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

	public String getAddFinesChkState() {
		return addFinesChkState;
	}

	public void setAddFinesChkState(String addFinesChkState) {
		this.addFinesChkState = addFinesChkState;
	}

	public String getAddPenaltyDecisoinSentDateStr() {
		return addPenaltyDecisoinSentDateStr;
	}

	public void setAddPenaltyDecisoinSentDateStr(
			String addPenaltyDecisoinSentDateStr) {
		this.addPenaltyDecisoinSentDateStr = addPenaltyDecisoinSentDateStr;
	}

	public String getDocAreaAddfines() {
		return docAreaAddfines;
	}

	public void setDocAreaAddfines(String docAreaAddfines) {
		this.docAreaAddfines = docAreaAddfines;
	}

	public String getDocYearAddfines() {
		return docYearAddfines;
	}

	public void setDocYearAddfines(String docYearAddfines) {
		this.docYearAddfines = docYearAddfines;
	}

	public String getDocNumAddfines() {
		return docNumAddfines;
	}

	public void setDocNumAddfines(String docNumAddfines) {
		this.docNumAddfines = docNumAddfines;
	}

	public String getFinesSumChkState() {
		return finesSumChkState;
	}

	public void setFinesSumChkState(String finesSumChkState) {
		this.finesSumChkState = finesSumChkState;
	}
	
	public String getFinesSum() {
		return finesSum;
	}

	public void setFinesSum(String finesSum) {
		this.finesSum = finesSum;
	}

	public String getIllegalIncomeChkState() {
		return illegalIncomeChkState;
	}

	public void setIllegalIncomeChkState(String illegalIncomeChkState) {
		this.illegalIncomeChkState = illegalIncomeChkState;
	}
	
	public String getIllegalIncomeSum() {
		return illegalIncomeSum;
	}

	public void setIllegalIncomeSum(String illegalIncomeSum) {
		this.illegalIncomeSum = illegalIncomeSum;
	}

	public String getAddfinesSumChkState() {
		return addfinesSumChkState;
	}

	public void setAddfinesSumChkState(String addfinesSumChkState) {
		this.addfinesSumChkState = addfinesSumChkState;
	}
	
	public String getAddfinesSum() {
		return addfinesSum;
	}

	public void setAddfinesSum(String addfinesSum) {
		this.addfinesSum = addfinesSum;
	}

	public String getCloseDownChkState() {
		return closeDownChkState;
	}

	public void setCloseDownChkState(String closeDownChkState) {
		this.closeDownChkState = closeDownChkState;
	}
	
	public String getCloseDownDays() {
		return closeDownDays;
	}

	public void setCloseDownDays(String closeDownDays) {
		this.closeDownDays = closeDownDays;
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

	public String getLawUnitDateStr() {
		return lawUnitDateStr;
	}

	public void setLawUnitDateStr(String lawUnitDateStr) {
		this.lawUnitDateStr = lawUnitDateStr;
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

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
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

	public String getReceiverSignatureDateStr() {
		return receiverSignatureDateStr;
	}

	public void setReceiverSignatureDateStr(String receiverSignatureDateStr) {
		this.receiverSignatureDateStr = receiverSignatureDateStr;
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

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Map<String, String> getDocInfo(String caseCode) {

		// 文书内容
		Map<String, String> content = new HashMap<String, String>();

		// 文号文
		content.put("文号文", docArea);
		// 文号字
		content.put("文号字", docYear);
		// 文号
		content.put("文号", docNum);
		// 当事人姓名
		content.put("当事人姓名", partyName);
		// 本机关
		content.put("本机关", agencyName);
		// 行政处罚决定书
		String chkState = "";
		if ("1".equals(finesChkState)) {
			chkState += "于" + penaltyDecisoinSentDateStr + "向你（单位）送达文执罚〔"
					+ docYearFines + "〕第" + docNumFines + "号《行政处罚决定书》\n";
		}
		if ("1".equals(addFinesChkState)) {
			chkState += "于" + addPenaltyDecisoinSentDateStr + "向你（单位）送达文加罚〔"
					+ docYearAddfines + "〕第" + docNumAddfines
					+ "号《行政处罚加处罚款决定书》\n";
		}

		content.put("行政处罚决定书", chkState);
		// 法定义务
		String obligation = "";
		if ("1".equals(finesSumChkState)) {
			obligation += "缴纳罚款:" + finesSum +"元\n";
		}
		if ("1".equals(illegalIncomeChkState)) {
			obligation += "缴纳违法所得:" + illegalIncomeSum +"元\n";
		}
		if ("1".equals(addfinesSumChkState)) {
			obligation += "缴纳加处罚款:" + addfinesSum +"元\n";
		}
		if ("1".equals(closeDownChkState)) {
			obligation += "停业整顿:" + closeDownDays +"天\n";
		}
		content.put("法定义务", obligation);
		// 行政机关落款
		content.put("行政机关落款", organsName);
		// 日期
		content.put("日期", lawUnitDateStr);
		// 主办人签名
		content.put("主办人签名", DocCommonUtils.cut(senderMjrSignatureBase64));
		// 协办人签名
		content.put("协办人签名", DocCommonUtils.cut(senderMnrSignatureBase64));
		// 送达时间
		content.put("送达时间", sendTimeStr);
		// 签收时间
		content.put("签收时间", receiverSignatureDateStr);
		// 送达方式
		if ("1".equals(sendType)) {
			content.put("送达方式", "直接送达");
		} else if ("2".equals(sendType)) {
			content.put("送达方式", "留置送达");
		} else if ("3".equals(sendType)) {
			content.put("送达方式", "委托送达");
		} else if ("4".equals(sendType)) {
			content.put("送达方式", "邮寄送达");
		} else if ("5".equals(sendType)) {
			content.put("送达方式", "公告送达");
		} else {
			content.put("送达方式", "");
		}
		// 送达地点
		content.put("送达地点", sendAddress);

		// 页眉
		content.put("页眉", caseCode);

		return content;
	}
}
