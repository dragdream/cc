package com.beidasoft.xzzf.punish.document.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;
import com.tianee.webframe.util.date.TeeDateUtil;

public class InquiryRecordModel {
	// 调查询问唯一ID
    private String id;

    // 询问地点
    private String askAddress;

    // 询问时间（开始）
    private String askTimeStartStr;

    // 询问时间（结束）
    private String askTimeEndStr;

    // 询问人ID
    private String majorPersonId;

    // 询问人姓名
    private String majorPersonName;

    // 询问人执法证号
    private String majorPersonCode;

    // 记录人ID
    private String minorPersonId;

    // 记录人姓名
    private String minorPersonName;

    // 记录人执法证号
    private String minorPersonCode;

    // 被询问人姓名
    private String askedName;

    // 被询问人性别（代码表）
    private String askedSex;

    // 被询问人出生年月
    private String askedBirthdayStr;

    // 被询问人联系电话
    private String askedTel;

    // 被询问人证件类型
    private String askedType;

    // 被询问人证件号码
    private String askedIdNo;

    // 被询问人工作单位
    private String askedUnit;

    // 被询问人住址
    private String askedAddress;

    // 询问内容记录
    private String askContent;

    // 询问人签名图片
    private String majorSignatureBase64;

    // 询问人签名值
    private String majorSignatureValue;

    // 询问人签名位置
    private String majorSignaturePlace;

    // 记录人签名图片
    private String minorSignatureBase64;

    // 记录人签名值
    private String minorSignatureValue;

    // 记录人签名位置
    private String minorSignaturePlace;

    // 询问人员签名时间
    private String askSignatureDateStr;

    // 被询问人签名图片
    private String askedSignatureBase64;

    // 被询问人签名值
    private String askedSignatureValue;

    // 被询问人签名位置
    private String askedSignaturePlace;

    // 被询问人签名时间
    private String askedSignatureDateStr;

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
    
    // 被询问人意见签名图片
    private String suggestionSignatureBase64;

    // 被询问人意见签名值
    private String suggestionSignatureValue;

    // 被询问人意见签名位置
    private String suggestionSignaturePlace;

    // 被询问人意见签名时间
    private String suggestionSignatureDateStr;

    // 询问笔录内容
	private List<RecordContentModel> meetingTopics = new ArrayList<RecordContentModel>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAskAddress() {
		return askAddress;
	}

	public void setAskAddress(String askAddress) {
		this.askAddress = askAddress;
	}

	public String getAskTimeStartStr() {
		return askTimeStartStr;
	}

	public void setAskTimeStartStr(String askTimeStartStr) {
		this.askTimeStartStr = askTimeStartStr;
	}

	public String getAskTimeEndStr() {
		return askTimeEndStr;
	}

	public void setAskTimeEndStr(String askTimeEndStr) {
		this.askTimeEndStr = askTimeEndStr;
	}

	public String getMajorPersonId() {
		return majorPersonId;
	}

	public void setMajorPersonId(String majorPersonId) {
		this.majorPersonId = majorPersonId;
	}

	public String getMajorPersonName() {
		return majorPersonName;
	}

	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}

	public String getMajorPersonCode() {
		return majorPersonCode;
	}

	public void setMajorPersonCode(String majorPersonCode) {
		this.majorPersonCode = majorPersonCode;
	}

	public String getMinorPersonId() {
		return minorPersonId;
	}

	public void setMinorPersonId(String minorPersonId) {
		this.minorPersonId = minorPersonId;
	}

	public String getMinorPersonName() {
		return minorPersonName;
	}

	public void setMinorPersonName(String minorPersonName) {
		this.minorPersonName = minorPersonName;
	}

	public String getMinorPersonCode() {
		return minorPersonCode;
	}

	public void setMinorPersonCode(String minorPersonCode) {
		this.minorPersonCode = minorPersonCode;
	}

	public String getAskedName() {
		return askedName;
	}

	public void setAskedName(String askedName) {
		this.askedName = askedName;
	}

	public String getAskedSex() {
		return askedSex;
	}

	public void setAskedSex(String askedSex) {
		this.askedSex = askedSex;
	}

	public String getAskedBirthdayStr() {
		return askedBirthdayStr;
	}

	public void setAskedBirthdayStr(String askedBirthdayStr) {
		this.askedBirthdayStr = askedBirthdayStr;
	}

	public String getAskedTel() {
		return askedTel;
	}

	public void setAskedTel(String askedTel) {
		this.askedTel = askedTel;
	}

	public String getAskedType() {
		return askedType;
	}

	public void setAskedType(String askedType) {
		this.askedType = askedType;
	}

	public String getAskedIdNo() {
		return askedIdNo;
	}

	public void setAskedIdNo(String askedIdNo) {
		this.askedIdNo = askedIdNo;
	}

	public String getAskedUnit() {
		return askedUnit;
	}

	public void setAskedUnit(String askedUnit) {
		this.askedUnit = askedUnit;
	}

	public String getAskedAddress() {
		return askedAddress;
	}

	public void setAskedAddress(String askedAddress) {
		this.askedAddress = askedAddress;
	}

	public String getAskContent() {
		return askContent;
	}

	public void setAskContent(String askContent) {
		this.askContent = askContent;
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

	public String getAskSignatureDateStr() {
		return askSignatureDateStr;
	}

	public void setAskSignatureDateStr(String askSignatureDateStr) {
		this.askSignatureDateStr = askSignatureDateStr;
	}

	public String getAskedSignatureBase64() {
		return askedSignatureBase64;
	}

	public void setAskedSignatureBase64(String askedSignatureBase64) {
		this.askedSignatureBase64 = askedSignatureBase64;
	}

	public String getAskedSignatureValue() {
		return askedSignatureValue;
	}

	public void setAskedSignatureValue(String askedSignatureValue) {
		this.askedSignatureValue = askedSignatureValue;
	}

	public String getAskedSignaturePlace() {
		return askedSignaturePlace;
	}

	public void setAskedSignaturePlace(String askedSignaturePlace) {
		this.askedSignaturePlace = askedSignaturePlace;
	}

	public String getAskedSignatureDateStr() {
		return askedSignatureDateStr;
	}

	public void setAskedSignatureDateStr(String askedSignatureDateStr) {
		this.askedSignatureDateStr = askedSignatureDateStr;
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

	public List<RecordContentModel> getMeetingTopics() {
		return meetingTopics;
	}

	public void setMeetingTopics(List<RecordContentModel> meetingTopics) {
		this.meetingTopics = meetingTopics;
	}
	
	public String getSuggestionSignatureBase64() {
		return suggestionSignatureBase64;
	}

	public void setSuggestionSignatureBase64(String suggestionSignatureBase64) {
		this.suggestionSignatureBase64 = suggestionSignatureBase64;
	}

	public String getSuggestionSignatureValue() {
		return suggestionSignatureValue;
	}

	public void setSuggestionSignatureValue(String suggestionSignatureValue) {
		this.suggestionSignatureValue = suggestionSignatureValue;
	}

	public String getSuggestionSignaturePlace() {
		return suggestionSignaturePlace;
	}

	public void setSuggestionSignaturePlace(String suggestionSignaturePlace) {
		this.suggestionSignaturePlace = suggestionSignaturePlace;
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

	public String getSuggestionSignatureDateStr() {
		return suggestionSignatureDateStr;
	}

	public void setSuggestionSignatureDateStr(String suggestionSignatureDateStr) {
		this.suggestionSignatureDateStr = suggestionSignatureDateStr;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		
		// 询问内容记录
		content.put("内容记录", askContent);
		
		//通过TeeDateUtil 里的 format  获取时间 并切割为字符串  后传值
		Date askTimeStart = TeeDateUtil.format(askTimeStartStr,"yyyy年MM月dd日HH时mm分");
		askTimeStartStr = TeeDateUtil.format(askTimeStart,"yyyy-MM-dd-HH-mm");
		String[] askTimeStartArr = askTimeStartStr.split("-");
		//询问时间年1
		content.put("询问时间年1", askTimeStartArr[0]);
		
		//询问时间月1
		content.put("询问时间月1", askTimeStartArr[1]);
		
		//询问时间日1
		content.put("询问时间日1", askTimeStartArr[2]);
		
		//询问时间时1
		content.put("询问时间时1", askTimeStartArr[3]);
		
		//询问时间分1
		content.put("询问时间分1", askTimeStartArr[4]);
		
		//通过TeeDateUtil 里的 format  获取时间 并切割为字符串  后传值
		Date askTimeEnd = TeeDateUtil.format(askTimeEndStr,"HH时mm分");
		askTimeEndStr = TeeDateUtil.format(askTimeEnd,"HH-mm");
		String[] askTimeEndArr = askTimeEndStr.split("-");
		//询问时间时2
		content.put("询问时间时2", askTimeEndArr[0]);
		
		//询问时间分2
		content.put("询问时间分2", askTimeEndArr[1]);
		
		//询问人姓名
		content.put("询问人姓名", majorPersonName);
		
		// 询问人执法证号
		content.put("询问人执法证号", majorPersonCode);
		
		//记录人姓名
		content.put("记录人姓名", minorPersonName);
		
		// 记录人执法证号
		content.put("记录人执法证号", minorPersonCode);
		
		// 被询问人姓名
		content.put("被询问人姓名", askedName);
		
		// 被询问人性别（代码表）
		if ("1".equals(askedSex)) {
			content.put("被询问人性别", "男");
		} else if ("2".equals(askedSex)) {
			content.put("被询问人性别", "女");
		}
		
		// 被询问人出生年月
		content.put("被询问人出生年月", askedBirthdayStr);
		
		// 被询问人联系电话
		content.put("被询问人联系电话", askedTel);
		
		// 被询问人证件名称
		if ("1".equals(askedType)) {
			content.put("被询问人证件名称", "身份证");
		} else if ("2".equals(askedType)) {
			content.put("被询问人证件名称", "护照");
		}
		
		// 被询问人证件号码
		content.put("被询问人证件号码", askedIdNo);
		
		// 被询问人工作单位
		content.put("被询问人工作单位", askedUnit);
		
		// 被询问人住址
		content.put("被询问人住址", askedAddress);
		
		// 询问人签名图片
		content.put("询问人签名", DocCommonUtils.cut(majorSignatureBase64));
		
		// 记录人签名图片
		content.put("记录人签名", DocCommonUtils.cut(minorSignatureBase64));
		
		// 询问人员签名时间
		content.put("签名时间", askSignatureDateStr);
		
//		// 被询问人签名图片
//		content.put("被询问人签名图片", DocCommonUtils.cut(askedSignatureBase64));
		
//		// 被询问人签名时间
//		content.put("被询问人签名时间", askedSignatureDateStr);
		//询问地点
		content.put("询问地点", askAddress);
		// 页眉
		content.put("页眉", caseCode);

		return content;
	}
}
