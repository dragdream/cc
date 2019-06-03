package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

public class NotificationcorrectionModel {
	 // 责令改正主键ID
    private String id;

    // 责令文改字
    private String docWorld;

    // 责令 第
    private String docNo;

    // 责令 号
    private String docNumber;

    // 当事人姓名
    private String partyName;

    // 案由期间
    private String caseTimeStr;

    private String caseTimeEndStr;
    // 案件行为
    private String caseCause;
    
    // 案件行为id
    private String causeAction;
    
    // 相关规定
    private String lawCase;

    // 立即改正上述行为
    private String atonce;

    // 整改日期之前
    private String beforeStr;


    // 整改内容
    private String correction;

    // 送达主办人签名图片
    private String deliverMajorSignatureBase64;

    // 送达主办人签名值
    private String deliverMajorSignatureValue;

    // 送达主办人签名位置
    private String deliverMajorSignaturePlace;

    // 送达协办人签名图片
    private String deliverMinorSignatureBase64;

    // 送达协办人签名值
    private String deliverMinorSignatureValue;

    // 送达协办人签名位置
    private String deliverMinorSignaturePlace;

    // 送达时间
    private String deliverDateStr;
    
    // 签收时间
    private String signTimeStr;

    // 组织机构编号
    private String organsCode;

    // 组织机构名称
    private String organsName;
    
    // 送达方式
    private String deliveryWay;

    // 送达地点
    private String deliveryPlace;

    private String receiverBase64;

    // 受送达人签名或盖章值
    private String receiverValue;

    // 受送达人签名或盖章位置
    private String receiverPlace;
    
    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 行政机关落款和印章图片
    private String sealSignatureBase64;

    // 行政机关落款和印章位置
    private String sealPalce;

    // 行政机关落款和印章时间
    private String sealTimeStr;

    // 行政机关落款和印章值
    private String sealValue;

    // 附件地址
    private String enclosureAddress;

    // 执法办案唯一编号
    private String baseId;

    // 删除标记
    private String delFlg;

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

	public String getDocWorld() {
		return docWorld;
	}

	public void setDocWorld(String docWorld) {
		this.docWorld = docWorld;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getCaseTimeStr() {
		return caseTimeStr;
	}

	public void setCaseTimeStr(String caseTimeStr) {
		this.caseTimeStr = caseTimeStr;
	}

	public String getCaseCause() {
		return caseCause;
	}

	public void setCaseCause(String caseCause) {
		this.caseCause = caseCause;
	}

	public String getLawCase() {
		return lawCase;
	}

	public void setLawCase(String lawCase) {
		this.lawCase = lawCase;
	}

	public String getAtonce() {
		return atonce;
	}

	public void setAtonce(String atonce) {
		this.atonce = atonce;
	}

	public String getBeforeStr() {
		return beforeStr;
	}

	public void setBeforeStr(String beforeStr) {
		this.beforeStr = beforeStr;
	}

	public String getCorrection() {
		return correction;
	}

	public void setCorrection(String correction) {
		this.correction = correction;
	}

	public String getDeliverMajorSignatureBase64() {
		return deliverMajorSignatureBase64;
	}

	public void setDeliverMajorSignatureBase64(String deliverMajorSignatureBase64) {
		this.deliverMajorSignatureBase64 = deliverMajorSignatureBase64;
	}

	public String getDeliverMajorSignatureValue() {
		return deliverMajorSignatureValue;
	}

	public void setDeliverMajorSignatureValue(String deliverMajorSignatureValue) {
		this.deliverMajorSignatureValue = deliverMajorSignatureValue;
	}

	public String getDeliverMajorSignaturePlace() {
		return deliverMajorSignaturePlace;
	}

	public void setDeliverMajorSignaturePlace(String deliverMajorSignaturePlace) {
		this.deliverMajorSignaturePlace = deliverMajorSignaturePlace;
	}

	public String getDeliverMinorSignatureBase64() {
		return deliverMinorSignatureBase64;
	}

	public void setDeliverMinorSignatureBase64(String deliverMinorSignatureBase64) {
		this.deliverMinorSignatureBase64 = deliverMinorSignatureBase64;
	}

	public String getDeliverMinorSignatureValue() {
		return deliverMinorSignatureValue;
	}

	public void setDeliverMinorSignatureValue(String deliverMinorSignatureValue) {
		this.deliverMinorSignatureValue = deliverMinorSignatureValue;
	}

	public String getDeliverMinorSignaturePlace() {
		return deliverMinorSignaturePlace;
	}

	public void setDeliverMinorSignaturePlace(String deliverMinorSignaturePlace) {
		this.deliverMinorSignaturePlace = deliverMinorSignaturePlace;
	}

	public String getDeliverDateStr() {
		return deliverDateStr;
	}

	public void setDeliverDateStr(String deliverDateStr) {
		this.deliverDateStr = deliverDateStr;
	}

	public String getSignTimeStr() {
		return signTimeStr;
	}

	public String getCaseTimeEndStr() {
		return caseTimeEndStr;
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

	public void setCaseTimeEndStr(String caseTimeEndStr) {
		this.caseTimeEndStr = caseTimeEndStr;
	}

	public String getReceiverBase64() {
		return receiverBase64;
	}

	public void setReceiverBase64(String receiverBase64) {
		this.receiverBase64 = receiverBase64;
	}

	public String getReceiverValue() {
		return receiverValue;
	}

	public void setReceiverValue(String receiverValue) {
		this.receiverValue = receiverValue;
	}

	public String getReceiverPlace() {
		return receiverPlace;
	}

	public void setReceiverPlace(String receiverPlace) {
		this.receiverPlace = receiverPlace;
	}

	public void setSignTimeStr(String signTimeStr) {
		this.signTimeStr = signTimeStr;
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

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public String getDeliveryPlace() {
		return deliveryPlace;
	}

	public void setDeliveryPlace(String deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}


	public String getSealSignatureBase64() {
		return sealSignatureBase64;
	}

	public void setSealSignatureBase64(String sealSignatureBase64) {
		this.sealSignatureBase64 = sealSignatureBase64;
	}

	public String getSealPalce() {
		return sealPalce;
	}

	public void setSealPalce(String sealPalce) {
		this.sealPalce = sealPalce;
	}

	public String getSealTimeStr() {
		return sealTimeStr;
	}

	public void setSealTimeStr(String sealTimeStr) {
		this.sealTimeStr = sealTimeStr;
	}

	public String getSealValue() {
		return sealValue;
	}

	public void setSealValue(String sealValue) {
		this.sealValue = sealValue;
	}

	public String getEnclosureAddress() {
		return enclosureAddress;
	}

	public void setEnclosureAddress(String enclosureAddress) {
		this.enclosureAddress = enclosureAddress;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
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

	
	public String getCauseAction() {
		return causeAction;
	}

	public void setCauseAction(String causeAction) {
		this.causeAction = causeAction;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		//文号文
		content.put("文号文", docWorld);
		//文号年
		content.put("文号年", docNo);
		//文号
		content.put("文号", docNumber);
		//当事人姓名
		content.put("当事人名称", partyName);
		//违法行为发生时间
		content.put("违法行为发生时间", caseTimeStr);
		//违法行为
		content.put("违法行为", caseCause);
		//法规条款
		content.put("法规条款", lawCase);
		//责令内容
		String correctionName = "";
		if ("0".equals(atonce)) {
			correctionName="立即改正上述行为";
		} else {
			correctionName="在"+beforeStr +"前，作出如下整改："+correction;
		}
		content.put("责令内容", correctionName);
		//行政机关落款
		content.put("行政机关落款", organsName);
		//盖章时间
		content.put("盖章时间", sealTimeStr);
		//主办人签名
		content.put("主办人签名", DocCommonUtils.cut(deliverMajorSignatureBase64));
		//协办人签名
		content.put("协办人签名", DocCommonUtils.cut(deliverMinorSignatureBase64));
		//送达时间
		content.put("送达时间", deliverDateStr);
		//受送达人签名
		content.put("受送达人签名", DocCommonUtils.cut(receiverBase64));
		//签收时间
		content.put("签收时间", signTimeStr);
		//送达方式
		String deliveryWayName = "";
		if ("0".equals(deliveryWay)) {
			deliveryWayName = "直接送达";
		} else if ("1".equals(deliveryWay)) {
			deliveryWayName = "留置送达";
		} else if ("2".equals(deliveryWay)) {
			deliveryWayName = "委托送达";
		} else if ("3".equals(deliveryWay)) {
			deliveryWayName = "邮寄送达";
		} else {
			deliveryWayName = "公告送达";
		}
		content.put("送达方式", deliveryWayName);
		//送达地点
		content.put("送达地点", deliveryPlace);
		//页眉
		content.put("页眉", caseCode);
		return content;
	} 

}
