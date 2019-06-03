package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;


/**
 * 行政处罚事先告知书实体类
 */
public class AdministrativePenalityModel {

    private String id;

    // 文书区字

    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 当事人
    private String personName;

    // 涉嫌（案由）
    private String cause;

    // 案由名
    private String causeActionName;
    
    // 违法行为
    private String caseName;

    // 涉案法律
    private String lawCase;

    // 涉案法律
    private String lawCause;

    // 警告（复选框）
    private String warn;

    // 没收违法所得（复选框）
    private String confiscateReceive;

    // 没收非法财物（复选框）
    private String confiscateIllegality;

    // 处罚裁量基准（复选框）
    private String isNotice;

    // 罚金（根据裁量基准）
    private String penalSum;

    // 违法经营额（复选框）
    private String confiscateManage;

    // 违法所得（复选框）
    private String confiscateReceived;

    // 罚款倍数（起）
    private String mulripleStart;

    // 罚款倍数（末）
    private String mulripleEnd;

    // 责令整顿（复选框）
    private String isStopBusiness;

    // 吊销许可（复选框）
    private String isRevokeLicense;

    // 较大数额罚款处罚决定（复选框）
    private String isPenalty;

    // 责令整顿处罚决定（复选框）
    private String isStop;

    // 吊销许可处罚决定（复选框）
    private String isRevoke;

    // 要求陈述申辩（复选框）
    private String isDefend;

    // 要求听证（复选框）
    private String isListen;

    // 当事人签名或盖章图片
    private String siteLeaderSignature;
    
   // 当事人签名或盖章图片
    private String siteLeaderSignatureBase64;

    // 当事人签名或盖章值
    private String siteLeaderValue;
    
    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 当事人签名或盖章位置
    private String siteLeaderPlace;

    // 行政机关落款印章图片
    private String lawUnitSealBase64;

    // 行政机关落款印章值
    private String lawUnitValue;

    // 行政机关落款印章位置
    private String lawUnitPlace;

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

    // 变更时间
    private String updateTimeStr;

    // 行政机关落款印章时间
    private String lawUnitDateStr;
    
    // 当事人签名或盖章时间
    private String siteLeaderDateStr;
    
    // 违法时间
    private String causeTimeStr;
    
    //违法时间后
    private String causeTimeEndStr;
    
    // 变更人ID
    private String updateUserId;

    // 变更人姓名
    private String updateUserName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
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

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getLawCase() {
		return lawCase;
	}

	public void setLawCase(String lawCase) {
		this.lawCase = lawCase;
	}

	public String getLawCause() {
		return lawCause;
	}

	public void setLawCause(String lawCause) {
		this.lawCause = lawCause;
	}

	public String getWarn() {
		return warn;
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

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String getConfiscateReceive() {
		return confiscateReceive;
	}

	public void setConfiscateReceive(String confiscateReceive) {
		this.confiscateReceive = confiscateReceive;
	}

	public String getConfiscateIllegality() {
		return confiscateIllegality;
	}

	public void setConfiscateIllegality(String confiscateIllegality) {
		this.confiscateIllegality = confiscateIllegality;
	}

	public String getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(String isNotice) {
		this.isNotice = isNotice;
	}

	public String getPenalSum() {
		return penalSum;
	}

	public void setPenalSum(String penalSum) {
		this.penalSum = penalSum;
	}

	public String getConfiscateManage() {
		return confiscateManage;
	}

	public void setConfiscateManage(String confiscateManage) {
		this.confiscateManage = confiscateManage;
	}

	public String getConfiscateReceived() {
		return confiscateReceived;
	}

	public void setConfiscateReceived(String confiscateReceived) {
		this.confiscateReceived = confiscateReceived;
	}

	

	public String getMulripleStart() {
		return mulripleStart;
	}

	public void setMulripleStart(String mulripleStart) {
		this.mulripleStart = mulripleStart;
	}

	public String getMulripleEnd() {
		return mulripleEnd;
	}

	public void setMulripleEnd(String mulripleEnd) {
		this.mulripleEnd = mulripleEnd;
	}

	public String getIsStopBusiness() {
		return isStopBusiness;
	}

	public void setIsStopBusiness(String isStopBusiness) {
		this.isStopBusiness = isStopBusiness;
	}

	public String getIsRevokeLicense() {
		return isRevokeLicense;
	}

	public void setIsRevokeLicense(String isRevokeLicense) {
		this.isRevokeLicense = isRevokeLicense;
	}

	public String getIsPenalty() {
		return isPenalty;
	}

	public void setIsPenalty(String isPenalty) {
		this.isPenalty = isPenalty;
	}

	public String getIsStop() {
		return isStop;
	}

	public void setIsStop(String isStop) {
		this.isStop = isStop;
	}

	public String getIsRevoke() {
		return isRevoke;
	}

	public void setIsRevoke(String isRevoke) {
		this.isRevoke = isRevoke;
	}

	public String getIsDefend() {
		return isDefend;
	}

	public void setIsDefend(String isDefend) {
		this.isDefend = isDefend;
	}

	public String getIsListen() {
		return isListen;
	}

	public void setIsListen(String isListen) {
		this.isListen = isListen;
	}

	public String getSiteLeaderSignature() {
		return siteLeaderSignature;
	}

	public void setSiteLeaderSignature(String siteLeaderSignature) {
		this.siteLeaderSignature = siteLeaderSignature;
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

	public String getLawUnitSealBase64() {
		return lawUnitSealBase64;
	}

	public void setLawUnitSealBase64(String lawUnitSealBase64) {
		this.lawUnitSealBase64 = lawUnitSealBase64;
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

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getOrgansName() {
		return organsName;
	}

	public void setOrgansName(String organsName) {
		this.organsName = organsName;
	}

	public String getCauseTimeEndStr() {
		return causeTimeEndStr;
	}

	public void setCauseTimeEndStr(String causeTimeEndStr) {
		this.causeTimeEndStr = causeTimeEndStr;
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

	public String getCauseTimeStr() {
		return causeTimeStr;
	}

	public void setCauseTimeStr(String causeTimeStr) {
		this.causeTimeStr = causeTimeStr;
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

	public String getSiteLeaderSignatureBase64() {
		return siteLeaderSignatureBase64;
	}

	public void setSiteLeaderSignatureBase64(String siteLeaderSignatureBase64) {
		this.siteLeaderSignatureBase64 = siteLeaderSignatureBase64;
	}

	public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		Map<String, String> content = new HashMap<String, String>();
		//文号文
		content.put("文号文", docArea);
		//文号年
		content.put("文号年", docYear);
		//文号
		content.put("文号", docNum);
		//当事人名称
		content.put("当事人名称", personName);
		//案由
		content.put("案由", cause);
		//违法行为发生期间
		content.put("违法行为发生期间", causeTimeStr);
		//违法行为
		content.put("违法行为", caseName);
		//法规条款
		content.put("法规条款", lawCase);
		//处罚条款
		content.put("处罚条款", lawCause);
		//处罚内容
		String typeString1 ="";
		if ("01".equals(confiscateManage)){
			typeString1 ="违法经营额";
		} else if ("01".equals(confiscateManage)){
			typeString1 ="违法所得";
		}
		String typeNameString = "";
		if ("1".equals(isRevokeLicense)) {
			typeNameString = "警告";
		} else if ("2".equals(isRevokeLicense)) {
			typeNameString = "没收违法所得";
		} else if ("3".equals(isRevokeLicense)) {
			typeNameString = "没收非法财物";
		} else if ("4".equals(isRevokeLicense)) {
			typeNameString = "根据《北京市文化市场综合执法行政处罚裁量基准》，处" + penalSum + "元以下或者"
					+ typeString1 + mulripleStart + "倍至" + mulripleEnd + "倍幅度的罚款；";
		} else if ("5".equals(isRevokeLicense)) {
			typeNameString = "责令停业整顿";
		} else if ("6".equals(isRevokeLicense)) {
			typeNameString = "吊销许可证";
		} 
		content.put("处罚内容", typeNameString );
		
		//行政处罚选项
		String typeName2 = "";
		if ("1".equals(isPenalty)) {
			typeName2 = "较大数额罚款";
		} else if ("2".equals(isPenalty)) {
			typeName2 = "责令停业整顿";
		} else if ("3".equals(isPenalty)) {
			typeName2 = "吊销许可证";
		} 
		content.put("行政处罚选项", typeName2 );
		
		//是否陈述申辩
		String isDefendName = "";
		if ("1".equals(isListen)) {
			isDefendName = "要求陈述申辩";
		} else {
			isDefendName = "不陈述申辩";
		}
		content.put("是否陈述申辩", isDefendName);
	    
		//是否要求听证
		String isListenName = "";
		if ("1".equals(isListen)) {
			isListenName = "要求听证";
		} else {
			isListenName = "不听证";
		}
		content.put("是否要求听证", isListenName);
		//当事人签名
		content.put("当事人签名", siteLeaderSignature);
		//行政机关落款
		content.put("行政机关落款", organsName);
		//当事人签名时间
		content.put("当事人签名时间", siteLeaderDateStr);
		//行政机构盖章时间
		content.put("行政机构盖章时间", lawUnitDateStr);
		//页眉
		content.put("页眉", caseCode);
		return content;
	}
    
}
