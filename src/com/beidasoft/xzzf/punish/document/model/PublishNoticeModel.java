package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

/**
 * 行政处罚听证通知书实体类
 */

public class PublishNoticeModel {
    // 行政处罚听证通知书主键ID
    private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 单位当事人名称
    private String organName;

    // 行政处罚听证会时间
    private String docDateStr;

    // 行政处罚听证会地址
    private String docAddress;

    // 行政处罚听证会内容
    private String docContent;

    // 行政处罚听证会主持人
    private String compereName;

    // 行政处罚听证会记录人
    private String recorderName;

    // 联系人
    private String contactName;

    // 联系人联系方式
    private String contactTel;

    // 行政机关落款印章图片
    private String lawUnitSeal;

    // 行政机关落款印章值
    private String lawUnitValue;

    // 行政机关落款印章位置
    private String lawUnitPlace;

    // 行政机关落款印章时间
    private String lawUnitDateStr;

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
    private String minorSignaturePlaceStr;

    // 送达时间
    private String deliverDateStr;

    // 受送达人签名或盖章图片
    private String receiverBase64;

    // 受送达人签名或盖章值
    private String receiverValue;

    // 受送达人签名或盖章位置
    private String receiverPlace;

    // 受送达人签名或盖章时间
    private String receiverDateStr;

    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 送达方式
    private String deliverType;

    // 送达地点
    private String deliverAddress;

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

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getDocDateStr() {
		return docDateStr;
	}

	public void setDocDateStr(String docDateStr) {
		this.docDateStr = docDateStr;
	}

	public String getDocAddress() {
		return docAddress;
	}

	public void setDocAddress(String docAddress) {
		this.docAddress = docAddress;
	}

	public String getDocContent() {
		return docContent;
	}

	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	public String getCompereName() {
		return compereName;
	}

	public void setCompereName(String compereName) {
		this.compereName = compereName;
	}

	public String getRecorderName() {
		return recorderName;
	}

	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
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

	public String getMinorSignaturePlaceStr() {
		return minorSignaturePlaceStr;
	}

	public void setMinorSignaturePlaceStr(String minorSignaturePlaceStr) {
		this.minorSignaturePlaceStr = minorSignaturePlaceStr;
	}

	public String getDeliverDateStr() {
		return deliverDateStr;
	}

	public void setDeliverDateStr(String deliverDateStr) {
		this.deliverDateStr = deliverDateStr;
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

	public String getReceiverDateStr() {
		return receiverDateStr;
	}

	public void setReceiverDateStr(String receiverDateStr) {
		this.receiverDateStr = receiverDateStr;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getDeliverAddress() {
		return deliverAddress;
	}

	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
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

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		// 文书区字
		content.put("文号文", docArea);
	    // 文书年度
		content.put("文号年", docYear);
		// 文书序号
		content.put("文号", docNum);
		// 单位当事人名称
		content.put("当事人名称", organName);
		// 行政处罚听证会时间
		content.put("听证会时间", docDateStr);
		// 行政处罚听证会地址
		content.put("听证会地址", docAddress);
		// 行政处罚听证会内容
		content.put("听证会案由", docContent);
		// 行政处罚听证会主持人
		content.put("听证主持人", compereName);	
		// 行政处罚听证会记录人
		content.put("听证记录人", recorderName);
		// 联系人
		content.put("联系人", contactName);
		// 联系人联系方式
		content.put("联系电话", contactTel);
		// 行政机关落款印章图片
		content.put("行政机关落款", "");
		// 行政机关落款印章时间
		content.put("盖章时间", lawUnitDateStr);							
		// 主办人签名图片
		content.put("送达主办人签名", DocCommonUtils.cut(majorSignatureBase64));							
		// 协办人签名图片
		content.put("送达协办人签名", DocCommonUtils.cut(minorSignatureBase64));							
		// 送达时间
		content.put("送达时间", deliverDateStr);							
		// 受送达人签名或盖章图片
		content.put("受送达人签名", receiverBase64);							
		// 受送达人签名或盖章时间
		content.put("签收时间", receiverDateStr);							
		// 送达方式
		String deliverTypeName = "";
		if ("1".equals(deliverType)) {
			deliverTypeName = "直接送达";
		} else if ("2".equals(deliverType)) {
			deliverTypeName = "留置送达";
		} else if ("3".equals(deliverType)) {
			deliverTypeName = "委托送达";
		} else if ("4".equals(deliverType)) {
			deliverTypeName = "邮寄送达";
		} else if ("5".equals(deliverType)) {
			deliverTypeName = "公告送达";
		}
		content.put("送达方式", deliverTypeName);
		// 送达地点
		content.put("送达地点", deliverAddress);							
		//页眉
		content.put("页眉", caseCode);
		return content;
	}
	
}
