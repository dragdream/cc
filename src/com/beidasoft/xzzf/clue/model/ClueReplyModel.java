package com.beidasoft.xzzf.clue.model;

import javax.persistence.Column;


public class ClueReplyModel {
	//主键
	private String id;
	
	//线索ID
	private String clueId;
	
	//当前回复类型
	private int replyType;
	
	//当前回复类型字符串
	private String replyTypeStr;
	
	//回复内容
	private String replyContent;
	
	// 查处数量
	private String punishCount;

	// 罚没款
	private String punishMoney;

	// 收缴非法出版
	private String punishBook;

	// 收缴其他设备
	private String punishOtherGoods;

	// 删除有害信息
	private String deleteBad;

	// 其他说明
	private String anotherRemark;

	//回复时间
	private String replyDateStr;
	
	//回复人员ID
	private int createUserId;
	
	//回复人员名称
	private String createUserName;
	
	//当前状态
	private String state;
	
	//处理人id
	private int transferUserId;
	
	//处理人姓名
	private String transferUserName;
	
	//是否对外回复
	private int isReplyExternal;
	
	//对外回复类型
	private String replyExternalType;
	
	//是否切换回复阶段
	private int isChangeType;
	
	//回复操作备注
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClueId() {
		return clueId;
	}

	public void setClueId(String clueId) {
		this.clueId = clueId;
	}

	public int getReplyType() {
		return replyType;
	}

	public void setReplyType(int replyType) {
		this.replyType = replyType;
	}

	public String getReplyTypeStr() {
		return replyTypeStr;
	}

	public void setReplyTypeStr(String replyTypeStr) {
		this.replyTypeStr = replyTypeStr;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getPunishCount() {
		return punishCount;
	}

	public void setPunishCount(String punishCount) {
		this.punishCount = punishCount;
	}

	public String getPunishMoney() {
		return punishMoney;
	}

	public void setPunishMoney(String punishMoney) {
		this.punishMoney = punishMoney;
	}

	public String getPunishBook() {
		return punishBook;
	}

	public void setPunishBook(String punishBook) {
		this.punishBook = punishBook;
	}

	public String getPunishOtherGoods() {
		return punishOtherGoods;
	}

	public void setPunishOtherGoods(String punishOtherGoods) {
		this.punishOtherGoods = punishOtherGoods;
	}

	public String getDeleteBad() {
		return deleteBad;
	}

	public void setDeleteBad(String deleteBad) {
		this.deleteBad = deleteBad;
	}

	public String getAnotherRemark() {
		return anotherRemark;
	}

	public void setAnotherRemark(String anotherRemark) {
		this.anotherRemark = anotherRemark;
	}

	public String getReplyDateStr() {
		return replyDateStr;
	}

	public void setReplyDateStr(String replyDateStr) {
		this.replyDateStr = replyDateStr;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getTransferUserId() {
		return transferUserId;
	}

	public void setTransferUserId(int transferUserId) {
		this.transferUserId = transferUserId;
	}

	public String getTransferUserName() {
		return transferUserName;
	}

	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}

	public int getIsReplyExternal() {
		return isReplyExternal;
	}

	public void setIsReplyExternal(int isReplyExternal) {
		this.isReplyExternal = isReplyExternal;
	}

	public String getReplyExternalType() {
		return replyExternalType;
	}

	public void setReplyExternalType(String replyExternalType) {
		this.replyExternalType = replyExternalType;
	}

	public int getIsChangeType() {
		return isChangeType;
	}

	public void setIsChangeType(int isChangeType) {
		this.isChangeType = isChangeType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
