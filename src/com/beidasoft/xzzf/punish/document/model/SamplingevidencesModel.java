package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

public class SamplingevidencesModel {
        // 送达信息确认书唯一ID 
	 	private String id;
	 	  
	    // 抽样取证（区字）
	    private String sampleAddr;

	    // 抽样取证（年度）
	    private String sampleYear;

	    // 抽样取证（序列）
	    private String sampleSquence;

	    // 当事人名称（姓名）
	    private String clientName;

	    // 你（单位）
	    private String unitName;

	    // 案件行为id
	    private String causeAction;
	    
	    // 案由名
	    private String causeActionName;
	    
	    // 违法行为(立案表中的案由)
	    private String initiateCause;

	    // 存放地址 （勘验笔录的检查地址）
	    private String checkAddr;

	    // 抽样取证方式
	    private String extractWay;

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

	    // 物品清单(区文)
	    private String itemAddr;

	    // 物品清单（年度）
	    private String itemYear;

	    // 物品清单(序列)
	    private String itemSquence;

	    // 物品清单内容
	    private String itemContain;
	    
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
	    // 送达人主办人签名图片
	    private String senterMajorSignatureBase64;
	    
	    // 送达人主办人签名值
	    private String senterMajorSignatureValue;
	    
	    // 送达人主办人签名位置
	    private String senterMajorSignaturePlace;

	    // 送达人协办人签名图片
	    private String senterMinorSignatureBase64;
	    
	    // 送达人协办人签名值
	    private String senterMinorSignatureValue;
	    
	    // 送达人协办人签名位置
	    private String senterMinorSignaturePlace;

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

	    //是否备注
	    private String isRemarks;
	    
	    //备注
	    private String remarks;
	    
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
	    
	    //比例
	    private String proportion;

		

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public String getClientName() {
			return clientName;
		}
		public void setClientName(String clientName) {
			this.clientName = clientName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getInitiateCause() {
			return initiateCause;
		}
		public void setInitiateCause(String initiateCause) {
			this.initiateCause = initiateCause;
		}
		public String getCheckAddr() {
			return checkAddr;
		}
		public void setCheckAddr(String checkAddr) {
			this.checkAddr = checkAddr;
		}
		public String getExtractWay() {
			return extractWay;
		}
		public void setExtractWay(String extractWay) {
			this.extractWay = extractWay;
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

		public String getItemContain() {
			return itemContain;
		}
		public void setItemContain(String itemContain) {
			this.itemContain = itemContain;
		}
		public void setItemSquence(String itemSquence) {
			this.itemSquence = itemSquence;
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
		public String getReceiptDateStr() {
			return receiptDateStr;
		}
		public void setReceiptDateStr(String receiptDateStr) {
			this.receiptDateStr = receiptDateStr;
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
		
		public String getProportion() {
			return proportion;
		}
		public void setProportion(String proportion) {
			this.proportion = proportion;
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
		
		public String getSenterMajorSignatureBase64() {
			return senterMajorSignatureBase64;
		}
		public void setSenterMajorSignatureBase64(String senterMajorSignatureBase64) {
			this.senterMajorSignatureBase64 = senterMajorSignatureBase64;
		}
		public String getSenterMajorSignatureValue() {
			return senterMajorSignatureValue;
		}
		public void setSenterMajorSignatureValue(String senterMajorSignatureValue) {
			this.senterMajorSignatureValue = senterMajorSignatureValue;
		}
		public String getSenterMajorSignaturePlace() {
			return senterMajorSignaturePlace;
		}
		public void setSenterMajorSignaturePlace(String senterMajorSignaturePlace) {
			this.senterMajorSignaturePlace = senterMajorSignaturePlace;
		}
		public String getSenterMinorSignatureBase64() {
			return senterMinorSignatureBase64;
		}
		public void setSenterMinorSignatureBase64(String senterMinorSignatureBase64) {
			this.senterMinorSignatureBase64 = senterMinorSignatureBase64;
		}
		public String getSenterMinorSignatureValue() {
			return senterMinorSignatureValue;
		}
		public void setSenterMinorSignatureValue(String senterMinorSignatureValue) {
			this.senterMinorSignatureValue = senterMinorSignatureValue;
		}
		public String getSenterMinorSignaturePlace() {
			return senterMinorSignaturePlace;
		}
		public void setSenterMinorSignaturePlace(String senterMinorSignaturePlace) {
			this.senterMinorSignaturePlace = senterMinorSignaturePlace;
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
		public String getCauseAction() {
			return causeAction;
		}
		public void setCauseAction(String causeAction) {
			this.causeAction = causeAction;
		}
		public String getCauseActionName() {
			return causeActionName;
		}
		public void setCauseActionName(String causeActionName) {
			this.causeActionName = causeActionName;
		}
		public Map<String, String> getDocInfo(String caseCode) {
			
			// 文书内容
			Map<String, String> content = new HashMap<String, String>();
			// 文号文
			content.put("文号文", sampleAddr);
		    // 文号年
			content.put("文号年", sampleYear);
			// 文号
			content.put("文号", sampleSquence);
			//当事人名称
			content.put("当事人名称", clientName);
			//案情概要行为
			content.put("案情概要行为", unitName);
			//案由
			content.put("案由", initiateCause);
			//检查地址
			content.put("检查地址", checkAddr);
			// 方式
		    content.put("方式", extractWay);
		    //比例
		    content.put("比例", proportion);
			//主办人签名
			content.put("主办人签名", DocCommonUtils.cut(majorSignatureBase64));
			//主办人执法证号
			content.put("主办人执法证号", majorCode);
			//协办人签名
			content.put("协办人签名", DocCommonUtils.cut( minorSignatureBase64));
			//协办人执法证号
			content.put("协办人执法证号", minorCode);
			//附件描述
			content.put("附件描述", "《物品清单》〔" + itemAddr + "〕文〔" + itemYear + "〕第〔"  + itemSquence + "〕号");//
			//行政机关落款
			content.put("行政机关落款", organizationStampPlace);//
			//盖章时间
			content.put("盖章时间", stampDateStr);
			//送达主办人签名
			content.put("送达主办人签名", DocCommonUtils.cut(majorSignatureBase64));
			//送达协办人签名
			content.put("送达协办人签名", DocCommonUtils.cut( minorSignatureBase64));
			//送达时间
			content.put("送达时间", sendDateStr);//
			//签收时间
			content.put("签收时间", receiptDateStr);//
			//送达方式
			content.put("送达方式", grantedWay);
			//送达地点
			content.put("送达地点", grantedAddr);//
			//页眉
			content.put("页眉", caseCode);
	
			return content;
		}
}

	

		
	    
	    

