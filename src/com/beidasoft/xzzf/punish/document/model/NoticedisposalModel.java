package com.beidasoft.xzzf.punish.document.model;

import java.util.List;

import com.beidasoft.xzzf.punish.document.bean.DocArticlesMain;

public class NoticedisposalModel {

	private String id;

    // 抽样取证物品处理通知书（区文）
    private String sampleAddr;

    // 抽样取证物品处理通知书（年份）
    private String sampleYear;

    // 抽样取证物品处理通知书（序列）
    private String sampleSquence;

    // 当事人名称
    private String partName;

    // 抽样取证凭证(区文)
    private String samEvidenceAddr;

    // 抽样取证凭证(年份)
    private String samEvidenceYear;

    // 抽样取证凭证(序列)
    private String samEvidenceSquence;

    // 物品清单(区文)
    private String itemAddr;

    // 物品清单(年份)
    private String itemYear;

    // 物品清单(序列)
    private String itemSquence;

    // 检查物品类型（全部、部分）
    private String checkType;

    // 处理决定（1:退还当事人、2:送鉴定机构鉴定、3：随案件移送、4：其他）
    private int processWay;

    // 随案件移送部门
    private String moveUnit;

    // 其他
    private String otherContent;

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
    
    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
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

    // 鉴定起始时间
    private String startDataStr;

    // 鉴定结束时间
    private String endDataStr;
    
    // 行政机关落款和印章时间
    private String stampDateStr;

    // 送达时间
    private String sendDateStr;
    
    // 签收时间
    private String receiptDateStr;
    
    // 主送达人签名图片
    private String masendSignatureBase64;

    // 主送达人签名值
    private String masendSignatureValue;

    // 主送达人签名位置
    private String masendSignaturePlace;

    // 协送达人签名图片
    private String misendSignatureBase64;

    // 协送达人签名值
    private String misendSignatureValue;

    // 协送达人签名位置
    private String misendSignaturePlace;
    
    //物品清单列表
    private List<DocArticlesMain> listArticlesMain;
    
    // 抽样取证凭证(ID)
    private String samEvidenceId;
    
    // 抽样取证凭证(内容)
    private String samEvidenceContain;

    // 物品清单(ID)
    private String itemID;
    
    // 物品清单(内容1)
    private String itemContainOne;
    
    // 物品清单(内容2)
    private String itemContainTwo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSamEvidenceId() {
		return samEvidenceId;
	}

	public void setSamEvidenceId(String samEvidenceId) {
		this.samEvidenceId = samEvidenceId;
	}

	public String getSamEvidenceContain() {
		return samEvidenceContain;
	}

	public void setSamEvidenceContain(String samEvidenceContain) {
		this.samEvidenceContain = samEvidenceContain;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemContainOne() {
		return itemContainOne;
	}

	public void setItemContainOne(String itemContainOne) {
		this.itemContainOne = itemContainOne;
	}

	public String getItemContainTwo() {
		return itemContainTwo;
	}

	public void setItemContainTwo(String itemContainTwo) {
		this.itemContainTwo = itemContainTwo;
	}

	public String getSampleAddr() {
		return sampleAddr;
	}

	public void setSampleAddr(String sampleAddr) {
		this.sampleAddr = sampleAddr;
	}

	public String getSampleYear() {
		return sampleYear;
	}

	public void setSampleYear(String sampleYear) {
		this.sampleYear = sampleYear;
	}

	public String getSampleSquence() {
		return sampleSquence;
	}

	public void setSampleSquence(String sampleSquence) {
		this.sampleSquence = sampleSquence;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getSamEvidenceAddr() {
		return samEvidenceAddr;
	}

	public void setSamEvidenceAddr(String samEvidenceAddr) {
		this.samEvidenceAddr = samEvidenceAddr;
	}

	public String getSamEvidenceYear() {
		return samEvidenceYear;
	}

	public void setSamEvidenceYear(String samEvidenceYear) {
		this.samEvidenceYear = samEvidenceYear;
	}

	public String getSamEvidenceSquence() {
		return samEvidenceSquence;
	}

	public void setSamEvidenceSquence(String samEvidenceSquence) {
		this.samEvidenceSquence = samEvidenceSquence;
	}

	public String getItemAddr() {
		return itemAddr;
	}

	public void setItemAddr(String itemAddr) {
		this.itemAddr = itemAddr;
	}

	public String getItemYear() {
		return itemYear;
	}

	public void setItemYear(String itemYear) {
		this.itemYear = itemYear;
	}

	public String getItemSquence() {
		return itemSquence;
	}

	public void setItemSquence(String itemSquence) {
		this.itemSquence = itemSquence;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public int getProcessWay() {
		return processWay;
	}

	public void setProcessWay(int processWay) {
		this.processWay = processWay;
	}

	public String getMoveUnit() {
		return moveUnit;
	}

	public void setMoveUnit(String moveUnit) {
		this.moveUnit = moveUnit;
	}

	public String getOtherContent() {
		return otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
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

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getStartDataStr() {
		return startDataStr;
	}

	public void setStartDataStr(String startDataStr) {
		this.startDataStr = startDataStr;
	}

	public String getEndDataStr() {
		return endDataStr;
	}

	public void setEndDataStr(String endDataStr) {
		this.endDataStr = endDataStr;
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

	public String getMasendSignatureBase64() {
		return masendSignatureBase64;
	}

	public void setMasendSignatureBase64(String masendSignatureBase64) {
		this.masendSignatureBase64 = masendSignatureBase64;
	}

	public String getMasendSignatureValue() {
		return masendSignatureValue;
	}

	public void setMasendSignatureValue(String masendSignatureValue) {
		this.masendSignatureValue = masendSignatureValue;
	}

	public String getMasendSignaturePlace() {
		return masendSignaturePlace;
	}

	public void setMasendSignaturePlace(String masendSignaturePlace) {
		this.masendSignaturePlace = masendSignaturePlace;
	}

	public String getMisendSignatureBase64() {
		return misendSignatureBase64;
	}

	public void setMisendSignatureBase64(String misendSignatureBase64) {
		this.misendSignatureBase64 = misendSignatureBase64;
	}

	public String getMisendSignatureValue() {
		return misendSignatureValue;
	}

	public void setMisendSignatureValue(String misendSignatureValue) {
		this.misendSignatureValue = misendSignatureValue;
	}

	public String getMisendSignaturePlace() {
		return misendSignaturePlace;
	}

	public void setMisendSignaturePlace(String misendSignaturePlace) {
		this.misendSignaturePlace = misendSignaturePlace;
	}

	public List<DocArticlesMain> getListArticlesMain() {
		return listArticlesMain;
	}

	public void setListArticlesMain(List<DocArticlesMain> listArticlesMain) {
		this.listArticlesMain = listArticlesMain;
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
    
}
