package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.bean.DocArticlesDetail;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

public class ArticlesMainModel {

	private String id;

	// 文书区字
	private String docArea;

	// 文书年度
	private String docYear;

	// 文书序号
	private String docNum;

	// 合计计量单位
	private String goodsUnits;

	// 合计数量
	private int goodsSums;

	// 合计备注
	private String goodsRemarks;

	  // 当事人现场负责人或受托人意见
    private String siteLeaderOpinion;
	
	// 当事人现场负责人或受托人意见
    private String siteLeaderOpinionBase64;

    // 当事人现场负责人或受托人意见盖章值
    private String siteLeaderOpinionValue;

    // 当事人现场负责人或受托人意见盖章位置
    private String siteLeaderOpinionPlace;

	// 当事人签名或盖章图片
	private String siteLeaderSignatureBase64;

	// 当事人签名或盖章值
	private String siteLeaderValue;

	// 当事人签名或盖章位置
	private String siteLeaderPlace;

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

	// 行政机关落款印章图片
	private String lawUnitSignatureBase64;

	// 行政机关落款印章值
	private String lawUnitSignatureValue;

	// 行政机关落款印章位置
	private String lawUnitSignaturePlace;

	// 行政机关落款印章时间
	private String lawUnitDateStr;

	// 当事人签名或盖章时间
	private String siteLeaderDateStr;
	
	  //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    //被绑定的物品清单
    private String bind;
    
    //被绑定的查封扣押决定书文号
    private String sealseizureId;
    
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

	private String dataArray;

	private List<DocArticlesDetail> docArticlesDetails;

	// 组织机构编号
	private String organsCode;

	// 组织机构名称
	private String organsName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDataArray() {
		return dataArray;
	}

	public void setDataArray(String dataArray) {
		this.dataArray = dataArray;
	}

	public List<DocArticlesDetail> getDocArticlesDetails() {
		return docArticlesDetails;
	}

	public void setDocArticlesDetails(List<DocArticlesDetail> docArticlesDetails) {
		this.docArticlesDetails = docArticlesDetails;
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

	public String getLawUnitDateStr() {
		return lawUnitDateStr;
	}

	public void setLawUnitDateStr(String lawUnitDateStr) {
		this.lawUnitDateStr = lawUnitDateStr;
	}

	public String getSiteLeaderDateStr() {
		return siteLeaderDateStr;
	}

	public void setSiteLeaderDateStr(String siteLeaderDateStr) {
		this.siteLeaderDateStr = siteLeaderDateStr;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getGoodsUnits() {
		return goodsUnits;
	}

	public void setGoodsUnits(String goodsUnits) {
		this.goodsUnits = goodsUnits;
	}

	public int getGoodsSums() {
		return goodsSums;
	}

	public void setGoodsSums(int goodsSums) {
		this.goodsSums = goodsSums;
	}

	public String getGoodsRemarks() {
		return goodsRemarks;
	}

	public void setGoodsRemarks(String goodsRemarks) {
		this.goodsRemarks = goodsRemarks;
	}

	public String getSiteLeaderOpinion() {
		return siteLeaderOpinion;
	}

	public void setSiteLeaderOpinion(String siteLeaderOpinion) {
		this.siteLeaderOpinion = siteLeaderOpinion;
	}

	public String getSiteLeaderSignatureBase64() {
		return siteLeaderSignatureBase64;
	}

	public void setSiteLeaderSignatureBase64(String siteLeaderSignatureBase64) {
		this.siteLeaderSignatureBase64 = siteLeaderSignatureBase64;
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

	public String getLawUnitSignatureBase64() {
		return lawUnitSignatureBase64;
	}

	public void setLawUnitSignatureBase64(String lawUnitSignatureBase64) {
		this.lawUnitSignatureBase64 = lawUnitSignatureBase64;
	}

	public String getLawUnitSignatureValue() {
		return lawUnitSignatureValue;
	}

	public void setLawUnitSignatureValue(String lawUnitSignatureValue) {
		this.lawUnitSignatureValue = lawUnitSignatureValue;
	}

	public String getLawUnitSignaturePlace() {
		return lawUnitSignaturePlace;
	}

	public void setLawUnitSignaturePlace(String lawUnitSignaturePlace) {
		this.lawUnitSignaturePlace = lawUnitSignaturePlace;
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

	public String getSiteLeaderOpinionPlace() {
		return siteLeaderOpinionPlace;
	}

	public void setSiteLeaderOpinionPlace(String siteLeaderOpinionPlace) {
		this.siteLeaderOpinionPlace = siteLeaderOpinionPlace;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
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

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public String getSealseizureId() {
		return sealseizureId;
	}

	public void setSealseizureId(String sealseizureId) {
		this.sealseizureId = sealseizureId;
	}

	// 预览
	public Map<String, String> getDocInfo(String caseCode) {

		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		// 文号文
		content.put("文号文", docArea);
		// 文号年
		content.put("文号年", docYear);
		// 文号
		content.put("文号", docNum);

		// 单位合计
		content.put("单位合计", goodsUnits);

		// 数量合计
		content.put("数量合计", goodsSums + "");

		// 单位合计
		content.put("备注合计", goodsRemarks);

		// 现场负责人或受托人意见
		content.put("现场负责人或受托人意见", siteLeaderOpinion);

		// 主办人执法证号
		content.put("主办人执法证号", majorCode);

		// 协办人执法证号
		content.put("协办人执法证号", minorCode);

		// 当事人盖章日期
		content.put("当事人盖章日期", siteLeaderDateStr);

		// 行政机关盖章日期
		content.put("行政机关盖章日期", lawUnitDateStr);

		// 物品列表信息
		content.put("物品列表信息", dataArray);

		// 页眉
		content.put("页眉", caseCode);

		// 主办人签名
		content.put("主办人签名", DocCommonUtils.cut(majorSignatureBase64));
		// 主办人签名
		content.put("协办人签名", DocCommonUtils.cut(minorSignatureBase64));
		// 行政机关落款
		// content.put("行政机关落款", DocCommonUtils.cut(lawUnitSignatureBase64));

		return content;
	}

}
