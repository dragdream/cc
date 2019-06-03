package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

/**
 * 查封扣押处理决定书
 * */
public class SealSeizureDecideModel {
	 // 查封扣押处理决定书主键ID
    private String id;

    // 文书名称类型（查封、扣押）
    private String docNameType;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 文书号类型（查封、扣押）
    private String docNumType;

    // 当事人
    private String partyName;

    // 查封扣押决定书名称类型（查封、扣押）
    private String docNameTypeSealSeizure;

    // 查封扣押(ID)
    private String samEvidenceId;
    
    // 查封扣押决定书文书区字
    private String docAreaSealSeizure;

    // 查封扣押决定书文书年度
    private String docYearSealSeizure;

    // 查封扣押决定书文书序号
    private String docNumSealSeizure;

    // 查封扣押决定书文书序号类型（查封、扣押）
    private String docNumTypeSealSeizure;

    // 场所或物品（复选框单选）
    private String placeOrGoods;

    // 物品清单文书区字
    private String docAreaGoods;

    // 物品清单文书年度
    private String docYearGoods;

    // 物品清单类型
    private String docTypeGoods;
    
    // 物品清单文书序号
    private String docNumGoods;

    // 物品处理范围（代码表）
    private String goodsProcessRange;

    // 处理决定类别
    private String processDecisionType;

    // 解除类别
    private String relieveType;

    // 延长类别
    private String extendType;

    // 延长至
    private String extendDateStr;

    // 鉴定时间（开始）
    private String checkupDateStartStr;

    // 鉴定时间（结束）
    private String checkupDateEndStr;

    // 鉴定前处理类型
    private String checkupBeforeType;

    // 鉴定前处理延期至
    private String checkupBeforBeforeStr;

    // 移送机构名称
    private String transferOrganName;

    // 其他处理
    private String otherProcess;

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

    // 执法主办人签名图片
    private String lawMajorSignatureBase64;

    // 执法主办人签名值
    private String lawMajorSignatureValue;

    // 执法主办人签名位置
    private String lawMajorSignaturePlace;

    // 执法主办人执法证号
    private String lawMajorCode;

    // 执法协办人签名图片
    private String lawMinorSignatureBase64;

    // 执法协办人签名值
    private String lawMinorSignatureValue;

    // 执法协办人签名位置
    private String lawMinorSignaturePlace;

    // 执法协办人执法证号
    private String lawMinorCode;

    // 行政机关落款印章图片
    private String lawUnitSeal;

    // 行政机关落款印章值
    private String lawUnitValue;

    // 行政机关落款印章位置
    private String lawUnitPlace;

    // 行政机关落款印章时间
    private String lawUnitDateStr;

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
    
    //物品清单主表ID
    private String docArticlesId;
    
    //物品清单文号
    private String docArticlesName;
    
    // 物品清单(内容)
    private String itemContain;
    
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

	public String getDocNameType() {
		return docNameType;
	}

	public void setDocNameType(String docNameType) {
		this.docNameType = docNameType;
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

	public String getDocNumType() {
		return docNumType;
	}

	public void setDocNumType(String docNumType) {
		this.docNumType = docNumType;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getDocNameTypeSealSeizure() {
		return docNameTypeSealSeizure;
	}

	public void setDocNameTypeSealSeizure(String docNameTypeSealSeizure) {
		this.docNameTypeSealSeizure = docNameTypeSealSeizure;
	}

	public String getDocAreaSealSeizure() {
		return docAreaSealSeizure;
	}

	public void setDocAreaSealSeizure(String docAreaSealSeizure) {
		this.docAreaSealSeizure = docAreaSealSeizure;
	}

	public String getDocYearSealSeizure() {
		return docYearSealSeizure;
	}

	public void setDocYearSealSeizure(String docYearSealSeizure) {
		this.docYearSealSeizure = docYearSealSeizure;
	}

	public String getDocNumSealSeizure() {
		return docNumSealSeizure;
	}

	public void setDocNumSealSeizure(String docNumSealSeizure) {
		this.docNumSealSeizure = docNumSealSeizure;
	}

	public String getDocNumTypeSealSeizure() {
		return docNumTypeSealSeizure;
	}

	public void setDocNumTypeSealSeizure(String docNumTypeSealSeizure) {
		this.docNumTypeSealSeizure = docNumTypeSealSeizure;
	}

	public String getPlaceOrGoods() {
		return placeOrGoods;
	}

	public void setPlaceOrGoods(String placeOrGoods) {
		this.placeOrGoods = placeOrGoods;
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

	public String getGoodsProcessRange() {
		return goodsProcessRange;
	}

	public void setGoodsProcessRange(String goodsProcessRange) {
		this.goodsProcessRange = goodsProcessRange;
	}

	public String getProcessDecisionType() {
		return processDecisionType;
	}

	public void setProcessDecisionType(String processDecisionType) {
		this.processDecisionType = processDecisionType;
	}

	public String getRelieveType() {
		return relieveType;
	}

	public void setRelieveType(String relieveType) {
		this.relieveType = relieveType;
	}

	public String getExtendType() {
		return extendType;
	}

	public void setExtendType(String extendType) {
		this.extendType = extendType;
	}

	public String getExtendDateStr() {
		return extendDateStr;
	}

	public void setExtendDateStr(String extendDateStr) {
		this.extendDateStr = extendDateStr;
	}

	public String getCheckupDateStartStr() {
		return checkupDateStartStr;
	}

	public void setCheckupDateStartStr(String checkupDateStartStr) {
		this.checkupDateStartStr = checkupDateStartStr;
	}

	public String getCheckupDateEndStr() {
		return checkupDateEndStr;
	}

	public void setCheckupDateEndStr(String checkupDateEndStr) {
		this.checkupDateEndStr = checkupDateEndStr;
	}

	public String getCheckupBeforeType() {
		return checkupBeforeType;
	}

	public void setCheckupBeforeType(String checkupBeforeType) {
		this.checkupBeforeType = checkupBeforeType;
	}

	public String getCheckupBeforBeforeStr() {
		return checkupBeforBeforeStr;
	}

	public void setCheckupBeforBeforeStr(String checkupBeforBeforeStr) {
		this.checkupBeforBeforeStr = checkupBeforBeforeStr;
	}

	public String getTransferOrganName() {
		return transferOrganName;
	}

	public void setTransferOrganName(String transferOrganName) {
		this.transferOrganName = transferOrganName;
	}

	public String getOtherProcess() {
		return otherProcess;
	}

	public void setOtherProcess(String otherProcess) {
		this.otherProcess = otherProcess;
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

	public String getLawMajorSignatureBase64() {
		return lawMajorSignatureBase64;
	}

	public void setLawMajorSignatureBase64(String lawMajorSignatureBase64) {
		this.lawMajorSignatureBase64 = lawMajorSignatureBase64;
	}

	public String getLawMajorSignatureValue() {
		return lawMajorSignatureValue;
	}

	public void setLawMajorSignatureValue(String lawMajorSignatureValue) {
		this.lawMajorSignatureValue = lawMajorSignatureValue;
	}

	public String getLawMajorSignaturePlace() {
		return lawMajorSignaturePlace;
	}

	public void setLawMajorSignaturePlace(String lawMajorSignaturePlace) {
		this.lawMajorSignaturePlace = lawMajorSignaturePlace;
	}

	public String getLawMajorCode() {
		return lawMajorCode;
	}

	public void setLawMajorCode(String lawMajorCode) {
		this.lawMajorCode = lawMajorCode;
	}

	public String getLawMinorSignatureBase64() {
		return lawMinorSignatureBase64;
	}

	public void setLawMinorSignatureBase64(String lawMinorSignatureBase64) {
		this.lawMinorSignatureBase64 = lawMinorSignatureBase64;
	}

	public String getLawMinorSignatureValue() {
		return lawMinorSignatureValue;
	}

	public void setLawMinorSignatureValue(String lawMinorSignatureValue) {
		this.lawMinorSignatureValue = lawMinorSignatureValue;
	}

	public String getLawMinorSignaturePlace() {
		return lawMinorSignaturePlace;
	}

	public void setLawMinorSignaturePlace(String lawMinorSignaturePlace) {
		this.lawMinorSignaturePlace = lawMinorSignaturePlace;
	}

	public String getLawMinorCode() {
		return lawMinorCode;
	}

	public void setLawMinorCode(String lawMinorCode) {
		this.lawMinorCode = lawMinorCode;
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

	public String getDocTypeGoods() {
		return docTypeGoods;
	}

	public void setDocTypeGoods(String docTypeGoods) {
		this.docTypeGoods = docTypeGoods;
	}

	public String getDocArticlesId() {
		return docArticlesId;
	}

	public void setDocArticlesId(String docArticlesId) {
		this.docArticlesId = docArticlesId;
	}

	public String getDocArticlesName() {
		return docArticlesName;
	}

	public void setDocArticlesName(String docArticlesName) {
		this.docArticlesName = docArticlesName;
	}

	public String getItemContain() {
		return itemContain;
	}

	public void setItemContain(String itemContain) {
		this.itemContain = itemContain;
	}

	public String getSamEvidenceId() {
		return samEvidenceId;
	}

	public void setSamEvidenceId(String samEvidenceId) {
		this.samEvidenceId = samEvidenceId;
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

	public void setItemContainTwo(String itemContainTwo) {
		this.itemContainTwo = itemContainTwo;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		
		//处理选项
		String docNameTypeName = "";
		if("0".equals(docNameType)){
			docNameTypeName = "查封";
		} else {
			docNameTypeName = "扣押";
		}
		content.put("处理选项", docNameTypeName);
		
		//文号文
		content.put("文号文", docArea);
		
		//文号字
		String docNumTypeName = "";
		if("0".equals(docNumType)){
			docNumTypeName = "封";
		} else {
			docNumTypeName = "扣";
		}
		content.put("文号字", docNumTypeName);
		//文号年
		content.put("文号年", docYear);
		//文号
		content.put("文号", docNum);
		
		
		
		//当事人名称
		content.put("当事人名称", partyName);
		
		//决定书类型
		String docNameTypeSealSeizureName = "";
		if("0".equals(docNameTypeSealSeizure)){
			docNameTypeSealSeizureName = "查封";
		} else {
			docNameTypeSealSeizureName = "扣押";
		}
		content.put("决定书类型", docNameTypeSealSeizureName);
		
		
		//决定书文号
		String TypeName = "";
		if("0".equals(docNumTypeSealSeizure)){
			TypeName = "查封";
		} else {
			TypeName = "扣押";
		}
		content.put("决定书文号", "〔" + docAreaSealSeizure + "）文〔" + TypeName +"〕字〔" + docYearSealSeizure + "〕第" + docNumSealSeizure + "〕号") ;
		
		
		
		
		//物品范围
		String placeOrGoodsName = "";
		if("0".equals(placeOrGoods)){
			placeOrGoodsName = "场所";
		} else {
			placeOrGoodsName = "物品";
		}
		content.put("物品范围", placeOrGoodsName);
		
		//物品清单文号
		content.put("物品清单文号", "（物品明细详见《物品清单》（" + docAreaGoods + "）文〔" + docYearGoods + "〕第" + docNumGoods + "号）");
		
		
		//处理选项1
		String handleName = "";
		if("0".equals(docTypeGoods)){
			handleName = "查封";
		} else {
			handleName = "扣押";
		}
		content.put("处理选项1", handleName);
		
		
		
		//物品范围1
		String goodsProcessRangeName = "";
		if("0".equals(goodsProcessRange)){
			goodsProcessRangeName = "场所";
		} else if ("1".equals(goodsProcessRange)) {
				goodsProcessRangeName = "全部";
		} else {
				goodsProcessRangeName = "部分";
		}
		content.put("物品范围1", goodsProcessRangeName);
		
		
		
		//处理决定
//		String processDecisionType1 = "";
//		if ("1".equals(processDecisionType)) {
//			processDecisionType1 = "解除";
//		} else if ("2".equals(processDecisionType)) {
//			processDecisionType1 = "延长";
//		} else if ("3".equals(processDecisionType)) {
//			processDecisionType1 = "于";
//		} else if ("4".equals(processDecisionType)) {
//			processDecisionType1 = "随案件移送";
//		} else  {
//			processDecisionType1 = "";
//		}
//		String relieveType1 = "";
//		if ("1".equals(relieveType)) {
//			relieveType1 = "查封";
//		} else {
//			relieveType1 = "扣押";
//		}
//		String extendType1 = "";
//		if ("1".equals(extendType)) {
//			extendType1 = "查封";
//		} else {
//			extendType1 = "扣押";
//		}
//		String checkupBeforeType1 = "";
//		if ("1".equals(checkupBeforeType)) {
//			checkupBeforeType1 = "查封";
//		} else if ("2".equals(checkupBeforeType)) {
//			checkupBeforeType1 = "扣押";
//		} else if ("3".equals(checkupBeforeType)) {
//			checkupBeforeType1 = "延长查封";
//		} else if ("4".equals(checkupBeforeType)) {
//			checkupBeforeType1 = "延长扣押";
//		}
//		content.put("处理决定", processDecisionType1 +  relieveType1 + "措施。" + processDecisionType1 + extendType1 + "期限至" 
//		  + extendDateStr + "。" + processDecisionType1 + checkupDateStartStr + "至" + checkupDateEndStr + "送鉴定机构鉴定，" + checkupBeforeType1
//		  + "期限顺延至" + checkupBeforBeforeStr + "。" + processDecisionType1 + "处理" + processDecisionType1);
//		
		
		
		//人民政府名称
		content.put("人民政府名称", govName);
		//部委名称
		content.put("部委名称", ministriesName);
		//区域名称
		content.put("区域名称", areaName);
		
		
		
		//主办人签名
		content.put("主办人签名", DocCommonUtils.cut(lawMajorSignatureBase64));
		//主办人执法证号
		content.put("主办人执法证号", lawMajorCode);
		//协办人签名
		content.put("主办人签名", DocCommonUtils.cut(lawMinorSignatureBase64));
		//协办人执法证号
		content.put("协办人执法证号", lawMinorCode);
		
		
		
		
		//附件描述
		content.put("附件描述", "《物品清单》（" + docAreaGoods + "）文〔" + docYearGoods + "〕第" + docNumGoods + "号）");

		
		
		
		
		//行政机关落款
		content.put("行政机关落款", lawUnitPlace);
		//盖章日期
		content.put("盖章日期", lawUnitDateStr);
		//送达主办人签名
		content.put("送达主办人签名", DocCommonUtils.cut(deliverMajorSignatureBase64));
		//送达协办人签名
		content.put("送达协办人签名", DocCommonUtils.cut(deliverMinorSignatureBase64));
		//送达时间
		content.put("送达时间", deliverDateStr);
		
		//签收时间
		content.put("签收时间", receiverDateStr);
		
		//送达方式
		content.put("送达方式", deliverType);
		//送达地点
		content.put("送达地点", deliverAddress);
		//页眉
		content.put("页眉", caseCode);
		return content;
		
	}
}
