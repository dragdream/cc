package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;


public class PutonrecordModel {

	private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 名字
    private String partyName;

    // 当事人证件名称及编号
    private String idName;
    
    // 当事人证件名称及编号
    private String idNameCode;

    // 当事人类型
    private String partyType;

    // 当事人姓名
    private String partyPersonName;
    
    // 当事人联系电话
    private String partyPhone;

    // 当事人住址
    private String partyAddress;

    // 案件来源
    private String caseSource;

    // 案由
    private String causeAction;
    
    // 案由名
    private String causeActionName;

    // 案情概要
    private String caseOverview;

    // 承办人员意见
    private String undertakePersonalIdea;

    // 承办人员主办人签名图片
    private String majorSignatureBase64;

    // 承办人员主办人签名值
    private String majorSignatureValue;

    // 承办人员主办人签名位置
    private String majorSignaturePlace;

    // 承办人员协办人签名图片
    private String minorSignatureBase64;

    // 承办人员协办人签名值
    private String minorSignatureValue;

    // 承办人员协办人签名位置
    private String minorSignaturePlace;

    // 承办人员签名时间
    private String undertakePersonalTimeStr;

    // 承办部门意见
    private String undertakeDepartmentIdea;

    // 承办部门负责人签名图片
    private String departmentPersonSignatureBase64;

    // 承办部门负责人签名值
    private String departmentPersonSignatureValue;

    // 承办部门负责人签名位置
    private String departmentPersonSignaturePlace;

    // 承办部门签名时间
    private String undertakeDepartmentTimeStr;

    // 备注
    private String note;

    // 附件地址
    private String enclosureAddress;

    // 删除标记
    private String delFlg;

    // 执法环节id
    private String lawLinkId;

    // 变更人id
    private String updateUserId;

    // 变更人姓名
    private String updateUserName;

    // 变更时间
    private String updateTimeStr;

    // 执法办案唯一id
    private String baseId;

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

	
	public String getPartyPersonName() {
		return partyPersonName;
	}

	public void setPartyPersonName(String partyPersonName) {
		this.partyPersonName = partyPersonName;
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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getPartyPhone() {
		return partyPhone;
	}

	public void setPartyPhone(String partyPhone) {
		this.partyPhone = partyPhone;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getCaseSource() {
		return caseSource;
	}

	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}

	public String getCauseAction() {
		return causeAction;
	}

	public void setCauseAction(String causeAction) {
		this.causeAction = causeAction;
	}

	public String getCaseOverview() {
		return caseOverview;
	}

	public void setCaseOverview(String caseOverview) {
		this.caseOverview = caseOverview;
	}

	public String getUndertakePersonalIdea() {
		return undertakePersonalIdea;
	}

	public void setUndertakePersonalIdea(String undertakePersonalIdea) {
		this.undertakePersonalIdea = undertakePersonalIdea;
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

	
	public String getUndertakePersonalTimeStr() {
		return undertakePersonalTimeStr;
	}

	public void setUndertakePersonalTimeStr(String undertakePersonalTimeStr) {
		this.undertakePersonalTimeStr = undertakePersonalTimeStr;
	}

	public String getUndertakeDepartmentTimeStr() {
		return undertakeDepartmentTimeStr;
	}

	public void setUndertakeDepartmentTimeStr(String undertakeDepartmentTimeStr) {
		this.undertakeDepartmentTimeStr = undertakeDepartmentTimeStr;
	}

	public String getUndertakeDepartmentIdea() {
		return undertakeDepartmentIdea;
	}

	public void setUndertakeDepartmentIdea(String undertakeDepartmentIdea) {
		this.undertakeDepartmentIdea = undertakeDepartmentIdea;
	}

	public String getDepartmentPersonSignatureBase64() {
		return departmentPersonSignatureBase64;
	}

	public void setDepartmentPersonSignatureBase64(
			String departmentPersonSignatureBase64) {
		this.departmentPersonSignatureBase64 = departmentPersonSignatureBase64;
	}

	public String getDepartmentPersonSignatureValue() {
		return departmentPersonSignatureValue;
	}

	public void setDepartmentPersonSignatureValue(
			String departmentPersonSignatureValue) {
		this.departmentPersonSignatureValue = departmentPersonSignatureValue;
	}

	public String getDepartmentPersonSignaturePlace() {
		return departmentPersonSignaturePlace;
	}

	public void setDepartmentPersonSignaturePlace(
			String departmentPersonSignaturePlace) {
		this.departmentPersonSignaturePlace = departmentPersonSignaturePlace;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
    
	public String getIdNameCode() {
		return idNameCode;
	}

	public void setIdNameCode(String idNameCode) {
		this.idNameCode = idNameCode;
	}

	public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
	}

	//预览
	public Map<String, String> getDocInfo(String caseCode) {
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		// 文号文
		content.put("文号文", docArea);
	    // 文号年
		content.put("文号年", docYear);
		// 文号
		content.put("文号", docNum);
		
		//名称
		content.put("名称", partyName);
		
		//证件名称及编号
		content.put("证件名称及编号", idName);
		
		//当事人类型
		content.put("当事人类型", partyType);
		
		//当事人姓名
		content.put("当事人姓名", partyPersonName);

		//联系电话
		content.put("联系电话", partyPhone);
		
		//住所
		content.put("住所", partyAddress);

		//案件来源
		content.put("案件来源", caseSource);
		
		//案由
		content.put("案由", causeAction);
		
		//案件概要
		content.put("案件概要", caseOverview);
		
		//承办人员意见
		content.put("承办人员意见", undertakePersonalIdea);
		
		// 页眉
		content.put("页眉", caseCode);

		
		// 主办人签名
		content.put("主办人签名", majorSignatureBase64);
		// 协办人签名
		content.put("协办人签名", minorSignatureBase64);
		// 承办部门负责人签名
		content.put("承办部门负责人签名", departmentPersonSignatureBase64);
		
		//承办部门负责人意见
		content.put("承办部门负责人意见", undertakeDepartmentIdea);

		//案件来源
		content.put("承办人员签名时间", undertakePersonalTimeStr);
		
		//承办部门负责人签名时间
		content.put("承办部门负责人签名时间", undertakeDepartmentTimeStr);
		
		//案件概要
		content.put("备注", note);
		return content;
	}
    
}
