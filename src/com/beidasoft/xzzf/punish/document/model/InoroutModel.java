package com.beidasoft.xzzf.punish.document.model;

import java.util.Map;

public class InoroutModel {
	  // 入库出库id
    private String id;

    // 入库/出库单
    private String inoutName;

    // 移交部门
    private String moveDepartment;

    // 当事人
    private String onePeople;

    // 文书编号
    private String bookNumber;
    
    // 入库出库
    private String inoutName2;
    // 入库出库单编号
    private String inoutNumber;

    // 图书成品册
    private String bookCe;

    // 图书内文散页令
    private String bookLing;

    // 图书备注
    private String bookNote;

    // 报纸成品份
    private String paperFen;

    // 报纸内文散页令
    private String paperLing;

    // 报纸备注
    private String paperNote;

    // 期刊成品本
    private String periodicalBen;

    // 期刊内文散页令
    private String periodicalLing;

    // 期刊备注
    private String periodicalNote;

    // 光盘成品张
    private String diskZhang;

    // 光盘母盘/母带张
    private String diskMotherZhang;

    // 光盘备注
    private String diskNote;

    // 录音录像带成品盒
    private String videoHe;

    // 录音录像带母盘/母带盒
    private String videoMotherPan;

    // 录音录像带备注
    private String videoNote;

    // 其他物品
    private String othersGoods;

    // 备注
    private String notesNot;

    // 送交主办人签名图片
    private String moveMajorSignatureBase64;

    // 送交主办人签名值
    private String moveMajorSignatureValue;

    // 送交主办人签名位置
    private String moveMajorSignaturePlace;

    // 送交协办人签名图片
    private String move2MajorSignatureBase64;

    // 送交协办人签名值
    private String move2MajorSignatureValue;

    // 送交协办人签名位置
    private String move2MajorSignaturePlace;

    // 送交人日期
    private String moveDateStr;

    // 接收主办人签名图片
    private String reciveMajorSignatureBase64;

    // 接收主办人签名值
    private String reciveMajorSignatureValue;

    // 接收主办人签名位置
    private String reciveMajorSignaturePlace;

    // 接收协办人签名图片
    private String recive2MajorSignatureBase64;

    // 接收协办人签名值
    private String recive2MajorSignatureValue;

    // 接收协办人签名位置
    private String recive2MajorSignaturePlace;

    // 接收人日期
    private String reciveDateStr;

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

	public String getInoutName() {
		return inoutName;
	}

	public void setInoutName(String inoutName) {
		this.inoutName = inoutName;
	}

	public String getMoveDepartment() {
		return moveDepartment;
	}

	public void setMoveDepartment(String moveDepartment) {
		this.moveDepartment = moveDepartment;
	}

	public String getOnePeople() {
		return onePeople;
	}

	public void setOnePeople(String onePeople) {
		this.onePeople = onePeople;
	}

	public String getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getInoutNumber() {
		return inoutNumber;
	}

	public void setInoutNumber(String inoutNumber) {
		this.inoutNumber = inoutNumber;
	}

	public String getBookCe() {
		return bookCe;
	}

	public void setBookCe(String bookCe) {
		this.bookCe = bookCe;
	}

	public String getBookLing() {
		return bookLing;
	}

	public void setBookLing(String bookLing) {
		this.bookLing = bookLing;
	}

	public String getBookNote() {
		return bookNote;
	}

	public void setBookNote(String bookNote) {
		this.bookNote = bookNote;
	}

	public String getPaperFen() {
		return paperFen;
	}

	public void setPaperFen(String paperFen) {
		this.paperFen = paperFen;
	}

	public String getPaperLing() {
		return paperLing;
	}

	public void setPaperLing(String paperLing) {
		this.paperLing = paperLing;
	}

	public String getPaperNote() {
		return paperNote;
	}

	public void setPaperNote(String paperNote) {
		this.paperNote = paperNote;
	}

	public String getPeriodicalBen() {
		return periodicalBen;
	}

	public void setPeriodicalBen(String periodicalBen) {
		this.periodicalBen = periodicalBen;
	}

	public String getPeriodicalLing() {
		return periodicalLing;
	}

	public void setPeriodicalLing(String periodicalLing) {
		this.periodicalLing = periodicalLing;
	}

	public String getPeriodicalNote() {
		return periodicalNote;
	}

	public void setPeriodicalNote(String periodicalNote) {
		this.periodicalNote = periodicalNote;
	}

	public String getDiskZhang() {
		return diskZhang;
	}

	public void setDiskZhang(String diskZhang) {
		this.diskZhang = diskZhang;
	}

	public String getDiskMotherZhang() {
		return diskMotherZhang;
	}

	public void setDiskMotherZhang(String diskMotherZhang) {
		this.diskMotherZhang = diskMotherZhang;
	}

	public String getDiskNote() {
		return diskNote;
	}

	public void setDiskNote(String diskNote) {
		this.diskNote = diskNote;
	}

	public String getVideoHe() {
		return videoHe;
	}

	public void setVideoHe(String videoHe) {
		this.videoHe = videoHe;
	}

	public String getVideoMotherPan() {
		return videoMotherPan;
	}

	public void setVideoMotherPan(String videoMotherPan) {
		this.videoMotherPan = videoMotherPan;
	}

	public String getVideoNote() {
		return videoNote;
	}

	public void setVideoNote(String videoNote) {
		this.videoNote = videoNote;
	}

	public String getOthersGoods() {
		return othersGoods;
	}

	public void setOthersGoods(String othersGoods) {
		this.othersGoods = othersGoods;
	}

	public String getNotesNot() {
		return notesNot;
	}

	public void setNotesNot(String notesNot) {
		this.notesNot = notesNot;
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

	public String getMove2MajorSignatureBase64() {
		return move2MajorSignatureBase64;
	}

	public void setMove2MajorSignatureBase64(String move2MajorSignatureBase64) {
		this.move2MajorSignatureBase64 = move2MajorSignatureBase64;
	}

	public String getMove2MajorSignatureValue() {
		return move2MajorSignatureValue;
	}

	public void setMove2MajorSignatureValue(String move2MajorSignatureValue) {
		this.move2MajorSignatureValue = move2MajorSignatureValue;
	}

	public String getMove2MajorSignaturePlace() {
		return move2MajorSignaturePlace;
	}

	public void setMove2MajorSignaturePlace(String move2MajorSignaturePlace) {
		this.move2MajorSignaturePlace = move2MajorSignaturePlace;
	}

	public String getMoveDateStr() {
		return moveDateStr;
	}

	public void setMoveDateStr(String moveDateStr) {
		this.moveDateStr = moveDateStr;
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

	public String getRecive2MajorSignatureBase64() {
		return recive2MajorSignatureBase64;
	}

	public void setRecive2MajorSignatureBase64(String recive2MajorSignatureBase64) {
		this.recive2MajorSignatureBase64 = recive2MajorSignatureBase64;
	}

	public String getRecive2MajorSignatureValue() {
		return recive2MajorSignatureValue;
	}

	public void setRecive2MajorSignatureValue(String recive2MajorSignatureValue) {
		this.recive2MajorSignatureValue = recive2MajorSignatureValue;
	}

	public String getRecive2MajorSignaturePlace() {
		return recive2MajorSignaturePlace;
	}

	public void setRecive2MajorSignaturePlace(String recive2MajorSignaturePlace) {
		this.recive2MajorSignaturePlace = recive2MajorSignaturePlace;
	}

	public String getReciveDateStr() {
		return reciveDateStr;
	}

	public String getInoutName2() {
		return inoutName2;
	}

	public void setInoutName2(String inoutName2) {
		this.inoutName2 = inoutName2;
	}

	public void setReciveDateStr(String reciveDateStr) {
		this.reciveDateStr = reciveDateStr;
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
		// TODO Auto-generated method stub
		return null;
	}

}
