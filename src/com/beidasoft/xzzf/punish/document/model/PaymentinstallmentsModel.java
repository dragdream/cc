package com.beidasoft.xzzf.punish.document.model;

import java.util.Map;

public class PaymentinstallmentsModel {
    // 延期、分期缴纳罚款审批书唯一ID 
    private String id;

    // 延期、分期
    private String payType;

    // 缴纳罚款批准书(区文)
    private String payAddr;

    // 缴纳罚款批准书(年份)
    private String payYear;

    // 缴纳罚款批准书(序列)
    private String paySquence;

    // 当事人名称（姓名)
    private String partName;

    // 取处罚决定书送达日期
    private String sendDataStr;

    // 行政处罚决定(区文)
    private String punishAddr;

    // 行政处罚决定(年份)
    private String punishYear;

    // 行政处罚决定(序列)
    private String punishSquence;

    // 截止日期
    private String lastDataStr;

    // 罚款
    private String sumFine;

    // 缴款方式
    private String payWay;

    // 延期截止日期
    private String extensionDateStr;

    // 分期缴纳罚款第一期
    private String firstPhase;

    // 分期缴纳罚款第一期结束时间
    private String firstPhaseDataStr;

    // 分期缴纳罚款第一期金额
    private String firstPhaseMoney;

    // 分期缴纳罚款第二期
    private String twoPhase;

    // 分期缴纳罚款第二期结束时间
    private String twoPhaseDataStr;

    // 分期缴纳罚款第二期金额
    private String twoPhaseMoney;

    // 分期缴纳罚款第三期
    private String threePhase;

    // 分期缴纳罚款第三期结束时间
    private String threePhaseDataStr;

    // 分期缴纳罚款第三期金额
    private String threePhaseMoney;

    // 行政机关签名图片
    private String organizationSignatureBase64;

    // 行政机关签名值
    private String organizationSignatureValue;

    // 行政机关签名位置
    private String organizationSignaturePlace;

    // 行政机关盖章图片
    private String organizationStampBase64;

    // 行政机关盖章值
    private String organizationStampValue;

    // 行政机关盖章位置
    private String organizationStampPlace;

    // 行政机关落款和印章时间
    private String stampDateStr;

    // 主办人签名图片
    private String majorSignatureBase64;

    // 主办人签名值
    private String majorSignatureValue;

    // 主办人签名位置
    private String majorSignaturePlace;

    // 协办人签名图片
    private String minorSignatureBase64;

    // 协办人签名值
    private String minorSignatureValue;

    // 协办人签名位置
    private String minorSignaturePlace;

    // 送达时间
    private String sendDateStr;

    // 受送达人签名图片
    private String grantSignatureBase64;

    // 受送达人签名值
    private String grantSignatureValue;

    // 受送达人签名位置
    private String grantSignaturePlace;

    // 受送达人盖章图片
    private String grantStampBase64;

    // 受送达人盖章值
    private String grantStampValue;

    // 受送达人盖章位置
    private String grantStampPlace;

    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 签收时间
    private String receiptDateStr;

    // 送达方式
    private String grantedWay;

    // 送达地点
    private String grantedAddr;

    // 组织机构编号
    private String organsCode;

    // 组织机构名称
    private String organsName;

    // 附件地址
    private String prosessUnit;

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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayAddr() {
		return payAddr;
	}

	public void setPayAddr(String payAddr) {
		this.payAddr = payAddr;
	}

	public String getPayYear() {
		return payYear;
	}

	public void setPayYear(String payYear) {
		this.payYear = payYear;
	}

	public String getPaySquence() {
		return paySquence;
	}

	public void setPaySquence(String paySquence) {
		this.paySquence = paySquence;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPunishAddr() {
		return punishAddr;
	}

	public void setPunishAddr(String punishAddr) {
		this.punishAddr = punishAddr;
	}

	public String getPunishYear() {
		return punishYear;
	}

	public void setPunishYear(String punishYear) {
		this.punishYear = punishYear;
	}

	public String getPunishSquence() {
		return punishSquence;
	}

	public void setPunishSquence(String punishSquence) {
		this.punishSquence = punishSquence;
	}

	public String getSumFine() {
		return sumFine;
	}

	public void setSumFine(String sumFine) {
		this.sumFine = sumFine;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getFirstPhase() {
		return firstPhase;
	}

	public void setFirstPhase(String firstPhase) {
		this.firstPhase = firstPhase;
	}

	public String getFirstPhaseMoney() {
		return firstPhaseMoney;
	}

	public void setFirstPhaseMoney(String firstPhaseMoney) {
		this.firstPhaseMoney = firstPhaseMoney;
	}

	public String getTwoPhase() {
		return twoPhase;
	}

	public void setTwoPhase(String twoPhase) {
		this.twoPhase = twoPhase;
	}

	public String getTwoPhaseMoney() {
		return twoPhaseMoney;
	}

	public void setTwoPhaseMoney(String twoPhaseMoney) {
		this.twoPhaseMoney = twoPhaseMoney;
	}

	public String getThreePhase() {
		return threePhase;
	}

	public void setThreePhase(String threePhase) {
		this.threePhase = threePhase;
	}

	public String getThreePhaseMoney() {
		return threePhaseMoney;
	}

	public void setThreePhaseMoney(String threePhaseMoney) {
		this.threePhaseMoney = threePhaseMoney;
	}

	public String getOrganizationSignatureBase64() {
		return organizationSignatureBase64;
	}

	public void setOrganizationSignatureBase64(String organizationSignatureBase64) {
		this.organizationSignatureBase64 = organizationSignatureBase64;
	}

	public String getOrganizationSignatureValue() {
		return organizationSignatureValue;
	}

	public void setOrganizationSignatureValue(String organizationSignatureValue) {
		this.organizationSignatureValue = organizationSignatureValue;
	}

	public String getOrganizationSignaturePlace() {
		return organizationSignaturePlace;
	}

	public void setOrganizationSignaturePlace(String organizationSignaturePlace) {
		this.organizationSignaturePlace = organizationSignaturePlace;
	}

	public String getOrganizationStampBase64() {
		return organizationStampBase64;
	}

	public void setOrganizationStampBase64(String organizationStampBase64) {
		this.organizationStampBase64 = organizationStampBase64;
	}

	public String getOrganizationStampValue() {
		return organizationStampValue;
	}

	public void setOrganizationStampValue(String organizationStampValue) {
		this.organizationStampValue = organizationStampValue;
	}

	public String getOrganizationStampPlace() {
		return organizationStampPlace;
	}

	public void setOrganizationStampPlace(String organizationStampPlace) {
		this.organizationStampPlace = organizationStampPlace;
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

	public String getGrantSignatureBase64() {
		return grantSignatureBase64;
	}

	public void setGrantSignatureBase64(String grantSignatureBase64) {
		this.grantSignatureBase64 = grantSignatureBase64;
	}

	public String getGrantSignatureValue() {
		return grantSignatureValue;
	}

	public void setGrantSignatureValue(String grantSignatureValue) {
		this.grantSignatureValue = grantSignatureValue;
	}

	public String getGrantSignaturePlace() {
		return grantSignaturePlace;
	}

	public void setGrantSignaturePlace(String grantSignaturePlace) {
		this.grantSignaturePlace = grantSignaturePlace;
	}

	public String getGrantStampBase64() {
		return grantStampBase64;
	}

	public void setGrantStampBase64(String grantStampBase64) {
		this.grantStampBase64 = grantStampBase64;
	}

	public String getGrantStampValue() {
		return grantStampValue;
	}

	public void setGrantStampValue(String grantStampValue) {
		this.grantStampValue = grantStampValue;
	}

	public String getGrantStampPlace() {
		return grantStampPlace;
	}

	public void setGrantStampPlace(String grantStampPlace) {
		this.grantStampPlace = grantStampPlace;
	}

	public String getGrantedWay() {
		return grantedWay;
	}

	public void setGrantedWay(String grantedWay) {
		this.grantedWay = grantedWay;
	}

	public String getGrantedAddr() {
		return grantedAddr;
	}

	public void setGrantedAddr(String grantedAddr) {
		this.grantedAddr = grantedAddr;
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

	public String getSendDataStr() {
		return sendDataStr;
	}

	public void setSendDataStr(String sendDataStr) {
		this.sendDataStr = sendDataStr;
	}

	public String getLastDataStr() {
		return lastDataStr;
	}

	public void setLastDataStr(String lastDataStr) {
		this.lastDataStr = lastDataStr;
	}

	public String getExtensionDateStr() {
		return extensionDateStr;
	}

	public void setExtensionDateStr(String extensionDateStr) {
		this.extensionDateStr = extensionDateStr;
	}

	public String getFirstPhaseDataStr() {
		return firstPhaseDataStr;
	}

	public void setFirstPhaseDataStr(String firstPhaseDataStr) {
		this.firstPhaseDataStr = firstPhaseDataStr;
	}

	public String getTwoPhaseDataStr() {
		return twoPhaseDataStr;
	}

	public void setTwoPhaseDataStr(String twoPhaseDataStr) {
		this.twoPhaseDataStr = twoPhaseDataStr;
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

	public String getThreePhaseDataStr() {
		return threePhaseDataStr;
	}

	public void setThreePhaseDataStr(String threePhaseDataStr) {
		this.threePhaseDataStr = threePhaseDataStr;
	}

	public String getStampDateStr() {
		return stampDateStr;
	}

	public void setStampDateStr(String stampDateStr) {
		this.stampDateStr = stampDateStr;
	}

	public String getSendDateStr() {
		return sendDateStr;
	}

	public void setSendDateStr(String sendDateStr) {
		this.sendDateStr = sendDateStr;
	}

	public String getReceiptDateStr() {
		return receiptDateStr;
	}

	public void setReceiptDateStr(String receiptDateStr) {
		this.receiptDateStr = receiptDateStr;
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
