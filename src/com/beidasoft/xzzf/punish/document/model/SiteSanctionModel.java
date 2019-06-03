package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;


public class SiteSanctionModel {
	//id
	private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 单位名称
    private String organName;

    // 单位代码类型（代码表）
    private String organCodeType;

    // 单位代码
    private String organCode;

    // 单位住所
    private String organAddress;

    // 单位当事人类型（代码表）
    private String organType;

    // 单位负责人姓名
    private String organLeadingName;

    // 单位负责人联系电话
    private String organLeadingTel;

    // 个人名称
    private String penName;

    // 个人字号名称
    private String psnShopName;

    // 个人性别
    private String psnSex;

    // 个人联系电话
    private String psnTel;

    // 个人住址
    private String psnAddress;

    // 个人证件类型（代码表）
    private String psnType;

    // 个人证件号码
    private String psnIdNo;

    // 违法事实和证据
    private String illegalFactsEvidence;

    // 处罚理由和依据
    private String punishmentBasis;

    // 处罚内容
    private String punishmentContent;

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

    // 被处罚人签名图片
    private String siteLeaderSignatureBase64;

    // 被处罚人签名值
    private String siteLeaderSignatureValue;

    // 被处罚人签名位置
    private String siteLeaderSignaturePlace;

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

    // 组织编号
    private String organsCode;

    // 组织名称
    private String organsName;
    
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

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganCodeType() {
		return organCodeType;
	}

	public void setOrganCodeType(String organCodeType) {
		this.organCodeType = organCodeType;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOrganAddress() {
		return organAddress;
	}

	public void setOrganAddress(String organAddress) {
		this.organAddress = organAddress;
	}

	public String getOrganType() {
		return organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getOrganLeadingName() {
		return organLeadingName;
	}

	public void setOrganLeadingName(String organLeadingName) {
		this.organLeadingName = organLeadingName;
	}

	public String getOrganLeadingTel() {
		return organLeadingTel;
	}

	public void setOrganLeadingTel(String organLeadingTel) {
		this.organLeadingTel = organLeadingTel;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName(String penName) {
		this.penName = penName;
	}

	public String getPsnShopName() {
		return psnShopName;
	}

	public void setPsnShopName(String psnShopName) {
		this.psnShopName = psnShopName;
	}

	public String getPsnSex() {
		return psnSex;
	}

	public void setPsnSex(String psnSex) {
		this.psnSex = psnSex;
	}

	public String getPsnTel() {
		return psnTel;
	}

	public void setPsnTel(String psnTel) {
		this.psnTel = psnTel;
	}

	public String getPsnAddress() {
		return psnAddress;
	}

	public void setPsnAddress(String psnAddress) {
		this.psnAddress = psnAddress;
	}

	public String getPsnType() {
		return psnType;
	}

	public void setPsnType(String psnType) {
		this.psnType = psnType;
	}

	public String getPsnIdNo() {
		return psnIdNo;
	}

	public void setPsnIdNo(String psnIdNo) {
		this.psnIdNo = psnIdNo;
	}

	public String getIllegalFactsEvidence() {
		return illegalFactsEvidence;
	}

	public void setIllegalFactsEvidence(String illegalFactsEvidence) {
		this.illegalFactsEvidence = illegalFactsEvidence;
	}

	public String getPunishmentBasis() {
		return punishmentBasis;
	}

	public void setPunishmentBasis(String punishmentBasis) {
		this.punishmentBasis = punishmentBasis;
	}

	public String getPunishmentContent() {
		return punishmentContent;
	}

	public void setPunishmentContent(String punishmentContent) {
		this.punishmentContent = punishmentContent;
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


	
	public String getSiteLeaderSignatureBase64() {
		return siteLeaderSignatureBase64;
	}

	public void setSiteLeaderSignatureBase64(String siteLeaderSignatureBase64) {
		this.siteLeaderSignatureBase64 = siteLeaderSignatureBase64;
	}

	public String getSiteLeaderSignatureValue() {
		return siteLeaderSignatureValue;
	}

	public void setSiteLeaderSignatureValue(String siteLeaderSignatureValue) {
		this.siteLeaderSignatureValue = siteLeaderSignatureValue;
	}

	public String getSiteLeaderSignaturePlace() {
		return siteLeaderSignaturePlace;
	}

	public void setSiteLeaderSignaturePlace(String siteLeaderSignaturePlace) {
		this.siteLeaderSignaturePlace = siteLeaderSignaturePlace;
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

	public Map<String, String> getDocInfo(String caseCode) {
		
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		
		// 页眉
		content.put("页眉", caseCode);
		// 联系电话
		content.put("联系电话", psnTel);
		//行政机关落款
		content.put("行政机关落款", organsName);
		// 被处罚人签名
		content.put("被处罚人签名", siteLeaderSignatureValue);
		// 证件号码
		content.put("证件号码", psnIdNo);
		// 证件名称（代码表）
		if ("2".equals(psnType)) {
			content.put("证件名称", "护照");
		} else if ("1".equals(psnType)) {
			content.put("证件名称", "身份证");
		} else {
			content.put("证件名称", "");
		}
		// 违法事实和证据
		content.put("违法事实和证据", illegalFactsEvidence);
		// 部委
		content.put("部委", ministriesName);
		//日期（执法单位印章时间）
		content.put("日期", lawUnitDateStr);
		//个人地址
		content.put("个人地址", psnAddress);
		//代码类型
		String organCodeTypeName = "";
		if ("1".equals(organCodeType)) {
			organCodeTypeName="统一社会信用代码";
		} else if ("2".equals(organCodeType)) {
			organCodeTypeName = "注册号";
		} else {
			organCodeTypeName = "其他";
		}
		content.put("代码类型", organCodeTypeName);
		//号码
		content.put("号码", organCode);
		// 负责人类型（代码表）
		String organTypeName = "";
		if ("1".equals(organType)) {
			organTypeName = "法定代表人";
		} else if ("2".equals(organType)) {
			organTypeName = "负责人";
		} else if ("3".equals(organType)) {
			organTypeName = "投资人";
		} else {
			organTypeName = "其他";
		}
		content.put("负责人类型", organTypeName);
		//负责人名称
		content.put("负责人名称", organLeadingName);
		//当事人名称
		content.put("当事人名称", organName);
		//区名
		content.put("区名", areaName);
		//单位地址
		content.put("单位地址", organAddress);
		//单位联系电话
		content.put("单位联系电话", organLeadingTel);
		//处罚内容
		content.put("处罚内容", punishmentContent);
		//处罚理由和依据
		content.put("处罚理由和依据", punishmentBasis);
		//字号名称
		content.put("字号名称", psnShopName);
		//个人姓名
		content.put("个人姓名", penName);
		//性别
		if ("1".equals(psnSex)) {
			content.put("性别", "男");
		} else if ("2".equals(psnSex)) {
			content.put("性别", "女");
		} else {
			content.put("性别", "");
		}
		//执法证号1
		content.put("执法证号1", majorCode);
		//执法证号2
		content.put("执法证号2", minorCode);
		//执法人员1签名
		content.put("执法人员1签名", DocCommonUtils.cut(majorSignatureBase64));
		//执法人员2签名
		content.put("执法人员2签名", DocCommonUtils.cut(minorSignatureBase64));
		//政府名
		content.put("政府名", govName);
		//文号文
		content.put("文号文", docArea);
		//文号年
		content.put("文号年", docYear);
		//文号
		content.put("文号", docNum);
		
		
		return content;
	}
	
}


