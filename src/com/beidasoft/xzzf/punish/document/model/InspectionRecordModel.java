package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

public class InspectionRecordModel {
	
	//现场检查文书表主键ID
	private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 当事人类别
    private String partyType;

    // 当事人名称
    private String partyName;

    // 单位当事人住所
    private String organAddress;

    // 单位当事人类型（代码表）
    private String organType;

    // 单位负责人姓名
    private String organLeadingName;

    // 单位负责人联系电话
    private String organLeadingTel;

    // 个人字号名称
    private String psnShopName;

    // 个人当事人性别
    private String psnSex;

    // 个人当事人联系电话
    private String psnTel;

    // 个人当事人住址
    private String psnAddress;

    // 个人当事人证件类型（代码表）
    private String psnType;

    // 个人当事人证件号码
    private String psnIdNo;

    // 现场负责人
    private String organSiteLeader;

    // 现场负责人职务
    private String organSiteDuties;
    // 现场负责人职务2
    private String organSiteDuties2;
    // 检查地点
    private String inspectionAddress;

    // 检查时间（开始）
    private String inspectionTimeStartStr;

    // 检查时间（结束）
    private String inspectionTimeEndStr;

    // 检查情况
    private String inspectionContent;

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

    // 现场负责人意见图
    private String siteLeaderOpinionBase64;
    
    // 现场负责人意见值
    private String siteLeaderOpinionValue;
    
    // 现场负责人意见
    private String siteLeaderOpinion;

    // 现场负责人签名图片
    private String siteLeaderBase64;
    // 现场负责人签名值
    private String siteLeaderValue;

    // 现场负责人签名位置
    private String siteLeaderPlace;

    // 现场负责人签名时间
    private String siteLeaderDateStr;

    // 执法单位ID（代码表）
    private String lawUnitId;

    // 执法单位名称
    private String lawUnitName;

    // 执法单位印章图片
    private String lawUnitSeal;

    // 执法单位印章值
    private String lawUnitValue;
    // 组织机构编号
    private String organsCode;

    // 组织机构名称
    private String organsName;
    
    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 执法单位印章位置
    private String lawUnitPlace;

    // 执法单位印章时间
    private String lawUnitDateStr;
    
    // 原文值
    private String originalValue;

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
    
    private String publicBase64;
    
    private String publicValue;
    
    private String publicPlace;

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

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
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

	public String getOrganSiteLeader() {
		return organSiteLeader;
	}

	public void setOrganSiteLeader(String organSiteLeader) {
		this.organSiteLeader = organSiteLeader;
	}

	public String getOrganSiteDuties() {
		return organSiteDuties;
	}

	public void setOrganSiteDuties(String organSiteDuties) {
		this.organSiteDuties = organSiteDuties;
	}

	public String getInspectionAddress() {
		return inspectionAddress;
	}

	public void setInspectionAddress(String inspectionAddress) {
		this.inspectionAddress = inspectionAddress;
	}

	public String getInspectionTimeStartStr() {
		return inspectionTimeStartStr;
	}

	public void setInspectionTimeStartStr(String inspectionTimeStartStr) {
		this.inspectionTimeStartStr = inspectionTimeStartStr;
	}

	public String getInspectionTimeEndStr() {
		return inspectionTimeEndStr;
	}

	public void setInspectionTimeEndStr(String inspectionTimeEndStr) {
		this.inspectionTimeEndStr = inspectionTimeEndStr;
	}

	public String getInspectionContent() {
		return inspectionContent;
	}

	public void setInspectionContent(String inspectionContent) {
		this.inspectionContent = inspectionContent;
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

	public String getSiteLeaderOpinionBase64() {
		return siteLeaderOpinionBase64;
	}

	public void setSiteLeaderOpinionBase64(String siteLeaderOpinionBase64) {
		this.siteLeaderOpinionBase64 = siteLeaderOpinionBase64;
	}

	public String getSiteLeaderOpinionValue() {
		return siteLeaderOpinionValue;
	}

	public void setSiteLeaderOpinionValue(String siteLeaderOpinionValue) {
		this.siteLeaderOpinionValue = siteLeaderOpinionValue;
	}

	public String getSiteLeaderOpinion() {
		return siteLeaderOpinion;
	}

	public void setSiteLeaderOpinion(String siteLeaderOpinion) {
		this.siteLeaderOpinion = siteLeaderOpinion;
	}

	public String getSiteLeaderBase64() {
		return siteLeaderBase64;
	}

	public void setSiteLeaderBase64(String siteLeaderBase64) {
		this.siteLeaderBase64 = siteLeaderBase64;
	}

	public String getSiteLeaderValue() {
		return siteLeaderValue;
	}

	public void setSiteLeaderValue(String siteLeaderValue) {
		this.siteLeaderValue = siteLeaderValue;
	}

	public String getSiteLeaderPlace() {
		return siteLeaderPlace;
	}

	public void setSiteLeaderPlace(String siteLeaderPlace) {
		this.siteLeaderPlace = siteLeaderPlace;
	}

	public String getSiteLeaderDateStr() {
		return siteLeaderDateStr;
	}

	public void setSiteLeaderDateStr(String siteLeaderDateStr) {
		this.siteLeaderDateStr = siteLeaderDateStr;
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

	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
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

	public String getOrganSiteDuties2() {
		return organSiteDuties2;
	}

	public void setOrganSiteDuties2(String organSiteDuties2) {
		this.organSiteDuties2 = organSiteDuties2;
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

	public String getPublicBase64() {
		return publicBase64;
	}

	public void setPublicBase64(String publicBase64) {
		this.publicBase64 = publicBase64;
	}

	public String getPublicValue() {
		return publicValue;
	}

	public void setPublicValue(String publicValue) {
		this.publicValue = publicValue;
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

	public String getPublicPlace() {
		return publicPlace;
	}

	public void setPublicPlace(String publicPlace) {
		this.publicPlace = publicPlace;
	}


	public Map<String, String> getDocInfo(String caseCode) {
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		// 文号文
		content.put("文号文", docArea);
	    // 文号年
		content.put("文号年", docYear);
		// 文号
		content.put("文号", docNum);
		
		if ("2".equals(partyType)) {
			// 单位信息
			// 当事人名称
			content.put("当事人名称", organLeadingName);
			// 当事人营业执照上的地址
			content.put("当事人营业执照上的地址", organAddress);
			// 当事人类型
			String organTypeName = "";
			if ("1".equals(organType)) {
				organTypeName = "法定代表人";
			} else if ("2".equals(organType)) {
				organTypeName = "负责人";
			} else if ("3".equals(organType)) {
				organTypeName = "投资人";
			} else if ("4".equals(organType)) {
				organTypeName = "其他";
			}
			content.put("当事人类型", organTypeName);
			// 法人姓名
			content.put("法人姓名", organLeadingName);
			// 法人电话
			content.put("法人电话", organLeadingTel);
		} else {
			// 个人信息
			// 当事人姓名
			content.put("当事人姓名", partyName);
			// 字号名称
			content.put("字号名称", psnShopName);
			// 性别
			if ("1".equals(psnSex)) {
				content.put("性别", "男");
			} else if ("2".equals(psnSex)) {
				content.put("性别", "女");
			} else {
				content.put("性别", "");
			}
			// 联系电话
			content.put("联系电话", psnTel);
			// 当事人住址
			content.put("当事人住址", psnAddress);
			// 证件名称
			if ("2".equals(psnType)) {
				content.put("证件名称", "护照");
			} else if ("1".equals(psnType)) {
				content.put("证件名称", "身份证");
			} else {
				content.put("证件名称", "");
			}
			// 证件号码
			content.put("证件号码", psnIdNo);
		}
		
		// 现场负责人
		content.put("现场负责人", organSiteLeader);
		// 职务
		content.put("职务", organSiteDuties);
		// 检查（勘验）地点
		content.put("检查（勘验）地点", inspectionAddress);
		// 检查（勘验）开始时间
		content.put("检查（勘验）开始时间", inspectionTimeStartStr);
		// 检查（勘验）结束时间
		content.put("检查（勘验）结束时间", inspectionTimeEndStr);
		// 现场检查情况
		content.put("现场检查情况", inspectionContent);
		// 执法人员1签名
		content.put("执法人员1签名", DocCommonUtils.cut(majorSignatureBase64));
		// 执法人员2签名
		content.put("执法人员2签名", DocCommonUtils.cut(minorSignatureBase64));
		// 执法证号1
		content.put("执法证号1", majorCode);
		// 执法证号2
		content.put("执法证号2", minorCode);
		// 当事人职务
		content.put("当事人职务", organSiteDuties);
		// 行政机关落款
		content.put("行政机关落款", DocCommonUtils.cut(lawUnitSeal));
		// 当事人签字日期
		content.put("当事人签字日期", siteLeaderDateStr);
		// 行政机关盖章日期
		content.put("行政机关盖章日期", lawUnitDateStr);
		// 页眉
		content.put("页眉", caseCode);

		return content;
	}
}
