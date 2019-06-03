package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;


public class TransferlistModel {
	// 证据材料移送清单id
    private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 文件、资料类
    private String fileContent;

    // 文件、资料类备注
    private String fileNote;

    // 资产物品类
    private String moneyGoods;

    // 资产物品类备注
    private String goodsNotes;

    // 其他类
    private String otherGoods;

    // 其他类备注
    private String otherNote;

    // 移送人签名图片
    private String moveMajorSignatureBase64;

    // 移送人签名值
    private String moveMajorSignatureValue;

    // 移送人签名位置
    private String moveMajorSignaturePlace;

    // 移送单位印章图片
    private String moveLawUnitSeal;

    // 移送单位印章值
    private String moveLawUnitValue;

    // 移送单位印章位置
    private String moveLawUnitPlace;

    // 移送单位印章时间
    private String moveLawUnitDateStr;

    // 接收人签名图片
    private String reciveMajorSignatureBase64;

    // 接收人签名值
    private String reciveMajorSignatureValue;

    // 接收人签名位置
    private String reciveMajorSignaturePlace;

    // 接收单位印章图片
    private String reciveLawUnitSeal;

    // 接收单位印章值
    private String reciveLawUnitValue;

    // 接收单位印章位置
    private String reciveLawUnitPlace;

    // 接收单位印章时间
    private String reciveLawUnitDateStr;

    // 移送组织机构编号
    private String organsCode;

    // 移送组织机构名称
    private String organsName;

    // 接收组织机构编号
    private String organsCodeTo;

    // 接收组织机构名称
    private String organsNameTo;

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

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileNote() {
		return fileNote;
	}

	public void setFileNote(String fileNote) {
		this.fileNote = fileNote;
	}

	public String getMoneyGoods() {
		return moneyGoods;
	}

	public void setMoneyGoods(String moneyGoods) {
		this.moneyGoods = moneyGoods;
	}

	public String getGoodsNotes() {
		return goodsNotes;
	}

	public void setGoodsNotes(String goodsNotes) {
		this.goodsNotes = goodsNotes;
	}

	public String getOtherGoods() {
		return otherGoods;
	}

	public void setOtherGoods(String otherGoods) {
		this.otherGoods = otherGoods;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public String getMoveMajorSignatureBase64() {
		return moveMajorSignatureBase64;
	}

	public void setMoveMajorSignatureBase64(String moveMajorSignatureBase64) {
		this.moveMajorSignatureBase64 = moveMajorSignatureBase64;
	}

	public String getMoveMajorSignatureValue() {
		return moveMajorSignatureValue;
	}

	public void setMoveMajorSignatureValue(String moveMajorSignatureValue) {
		this.moveMajorSignatureValue = moveMajorSignatureValue;
	}

	public String getMoveMajorSignaturePlace() {
		return moveMajorSignaturePlace;
	}

	public void setMoveMajorSignaturePlace(String moveMajorSignaturePlace) {
		this.moveMajorSignaturePlace = moveMajorSignaturePlace;
	}

	public String getMoveLawUnitSeal() {
		return moveLawUnitSeal;
	}

	public void setMoveLawUnitSeal(String moveLawUnitSeal) {
		this.moveLawUnitSeal = moveLawUnitSeal;
	}

	public String getMoveLawUnitValue() {
		return moveLawUnitValue;
	}

	public void setMoveLawUnitValue(String moveLawUnitValue) {
		this.moveLawUnitValue = moveLawUnitValue;
	}

	public String getMoveLawUnitPlace() {
		return moveLawUnitPlace;
	}

	public void setMoveLawUnitPlace(String moveLawUnitPlace) {
		this.moveLawUnitPlace = moveLawUnitPlace;
	}

	public String getMoveLawUnitDateStr() {
		return moveLawUnitDateStr;
	}

	public void setMoveLawUnitDateStr(String moveLawUnitDateStr) {
		this.moveLawUnitDateStr = moveLawUnitDateStr;
	}

	public String getReciveMajorSignatureBase64() {
		return reciveMajorSignatureBase64;
	}

	public void setReciveMajorSignatureBase64(String reciveMajorSignatureBase64) {
		this.reciveMajorSignatureBase64 = reciveMajorSignatureBase64;
	}

	public String getReciveMajorSignatureValue() {
		return reciveMajorSignatureValue;
	}

	public void setReciveMajorSignatureValue(String reciveMajorSignatureValue) {
		this.reciveMajorSignatureValue = reciveMajorSignatureValue;
	}

	public String getReciveMajorSignaturePlace() {
		return reciveMajorSignaturePlace;
	}

	public void setReciveMajorSignaturePlace(String reciveMajorSignaturePlace) {
		this.reciveMajorSignaturePlace = reciveMajorSignaturePlace;
	}

	public String getReciveLawUnitSeal() {
		return reciveLawUnitSeal;
	}

	public void setReciveLawUnitSeal(String reciveLawUnitSeal) {
		this.reciveLawUnitSeal = reciveLawUnitSeal;
	}

	public String getReciveLawUnitValue() {
		return reciveLawUnitValue;
	}

	public void setReciveLawUnitValue(String reciveLawUnitValue) {
		this.reciveLawUnitValue = reciveLawUnitValue;
	}

	public String getReciveLawUnitPlace() {
		return reciveLawUnitPlace;
	}

	public void setReciveLawUnitPlace(String reciveLawUnitPlace) {
		this.reciveLawUnitPlace = reciveLawUnitPlace;
	}

	public String getReciveLawUnitDateStr() {
		return reciveLawUnitDateStr;
	}

	public void setReciveLawUnitDateStr(String reciveLawUnitDateStr) {
		this.reciveLawUnitDateStr = reciveLawUnitDateStr;
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

	public String getOrgansCodeTo() {
		return organsCodeTo;
	}

	public void setOrgansCodeTo(String organsCodeTo) {
		this.organsCodeTo = organsCodeTo;
	}

	public String getOrgansNameTo() {
		return organsNameTo;
	}

	public void setOrgansNameTo(String organsNameTo) {
		this.organsNameTo = organsNameTo;
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
		
		// 文号文
		content.put("文号文", docArea);
		// 文号字
		content.put("文号字", docYear);
		// 文号
		content.put("文号", docNum);
		// 文件内容
		content.put("文件内容", fileContent);
		// 文件备注
		content.put("文件备注", fileNote);
		// 资产内容
		content.put("资产内容", moneyGoods);
		// 资产备注
		content.put("资产备注", goodsNotes);
		// 其他类内容
		content.put("其他类内容", otherGoods);
		// 其他类备注
		content.put("其他类备注", otherNote);
		// 移送人签名
		content.put("移送人签名", DocCommonUtils.cut(moveMajorSignatureBase64));
		// 移送单位
		content.put("移送单位", DocCommonUtils.cut(moveLawUnitSeal));
		// 移送人签字日期
		content.put("移送人签字日期", moveLawUnitDateStr);
		// 接收人签名
		content.put("接收人签名", DocCommonUtils.cut(reciveMajorSignatureBase64));
		// 接收单位
		content.put("接收单位", DocCommonUtils.cut(reciveLawUnitSeal));
		// 接收人签字日期
		content.put("接收人签字日期", reciveLawUnitDateStr);
		// 页眉
		content.put("页眉", caseCode);
		
		return content;
	}
}
