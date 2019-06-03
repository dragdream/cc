package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;


public class AskbookModel {
	 // 调查询问通知书UUID
    private String id;

    // 当事人姓名
    private String partyName;

    // 行为
    private String partyAction;

    // 到达时间
    private String arravelTimeStr;

    // 地址
    private String surveyAddress;

    // 材料
    private String surveyMaterials;

    // 联系人
    private String surveyPeople;

    // 联系电话
    private String surveyPhone;

    // 盖章时间
    private String sealTimeStr;

    // 执法单位印章图片
    private String lawUnitSeal;

    // 执法单位印章值
    private String lawUnitValue;

    // 执法单位印章位置
    private String lawUnitPlace;

    // 执法单位印章时间
    private String lawUnitDateStr;

    // 组织机构编号
    private String organsCode;

    // 组织机构名称
    private String organsName;

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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyAction() {
		return partyAction;
	}

	public void setPartyAction(String partyAction) {
		this.partyAction = partyAction;
	}

	public String getArravelTimeStr() {
		return arravelTimeStr;
	}

	public void setArravelTimeStr(String arravelTimeStr) {
		this.arravelTimeStr = arravelTimeStr;
	}

	public String getSurveyAddress() {
		return surveyAddress;
	}

	public void setSurveyAddress(String surveyAddress) {
		this.surveyAddress = surveyAddress;
	}

	public String getSurveyMaterials() {
		return surveyMaterials;
	}

	public void setSurveyMaterials(String surveyMaterials) {
		this.surveyMaterials = surveyMaterials;
	}

	public String getSurveyPeople() {
		return surveyPeople;
	}

	public void setSurveyPeople(String surveyPeople) {
		this.surveyPeople = surveyPeople;
	}

	public String getSurveyPhone() {
		return surveyPhone;
	}

	public void setSurveyPhone(String surveyPhone) {
		this.surveyPhone = surveyPhone;
	}

	public String getSealTimeStr() {
		return sealTimeStr;
	}

	public void setSealTimeStr(String sealTimeStr) {
		this.sealTimeStr = sealTimeStr;
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
	
	public Map<String, String> getDocInfo(String caseCode) {
		
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		
		//当事人姓名
		content.put("当事人姓名", partyName);
		//行为内容
		content.put("行为内容", partyAction);
		//时间年
		content.put("时间年", arravelTimeStr.substring(0,4));
		//时间月
		content.put("时间月", arravelTimeStr.substring(4,6));
		//时间日
		content.put("时间日", arravelTimeStr.substring(6,8));
		//时间时
		content.put("时间时", arravelTimeStr.substring(8,10));
		
		//地址
		String surveyAddressName = "";
		if ("1".equals(surveyAddress)) {
			surveyAddressName = "经营许可证";
		} else if ("2".equals(surveyAddress)) {
			surveyAddressName = "营业执照";
		} else if ("3".equals(surveyAddress)) {
			surveyAddressName = "ICP证或ICP备案材料";
		} else if ("4".equals(surveyAddress)) {
			surveyAddressName = "法定代表人或负责人身份证明";
		} else if ("5".equals(surveyAddress)) {
			surveyAddressName = "如委托他人前来，须出具有效的授权委托书及被委托人的有效身份证明";
		} else {
			surveyAddressName = "其他";
		}
		content.put("地址", surveyAddressName);
		
		//材料
		content.put("材料", surveyMaterials);
		//联系人
		content.put("联系人", surveyPeople);
		//联系电话
		content.put("联系电话", surveyPhone);
		//行政机关落款
		content.put("行政机关落款", DocCommonUtils.cut(lawUnitSeal));
		//通知下发时间
		content.put("通知下发时间", lawUnitDateStr);
		//页眉
		content.put("页眉", caseCode);
		
		return content;
	}
    

}
