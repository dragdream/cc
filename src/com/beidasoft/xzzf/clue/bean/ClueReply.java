package com.beidasoft.xzzf.clue.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_CLUE_REPLY")
public class ClueReply {
	//主键
	@Id
    @Column(name = "ID")
	private String id;
	
	//线索ID
	@Column(name = "CLUE_ID")
	private String clueId;
	
	//当前回复类型
	@Column(name = "REPLY_TYPE")
	private Integer replyType;
	
	//回复内容
	@Column(name = "REPLY_CONTENT")
	private String replyContent;
	
	// 查处数量
	@Column(name = "PUNISH_COUNT")
	private String punishCount;
	
	// 罚没款
	@Column(name = "PUNISH_MONEY")
	private String punishMoney;
	
	//收缴非法出版
	@Column(name = "PUNISH_BOOK")
	private String punishBook;
	
	//收缴其他设备
	@Column(name = "PUNISH_OTHER_GOODS")
	private String punishOtherGoods;
	
	//删除有害信息
	@Column(name = "DELETE_BAD")
	private String deleteBad;
	
	//其他说明
	@Column(name = "ANOTHER_REMARK")
	private String anotherRemark;
	
	//回复时间
	@Column(name = "REPLY_DATE")
	private Date replyDate;
	
	//回复人员ID
	@Column(name = "CREATE_USER_ID")
	private int createUserId;
	
	//回复人员名称
	@Column(name = "CREATE_USER_NAME")
	private String createUserName;
	
	//当前状态
	@Column(name = "STATE")
	private String state;
	
	//处理人id
	@Column(name = "TRANSACT_USER_ID")
	private int transferUserId;
	
	//处理人姓名
	@Column(name = "TRANSACT_USER_NAME")
	private String transferUserName;
	
	//是否对外回复
	@Column(name = "IS_REPLY_EXTERNAL")
	private int isReplyExternal;
	
	//对外回复类型
	@Column(name = "REPLY_EXTERNAL_TYPE")
	private String replyExternalType;
	
	//是否切换回复阶段
	@Column(name = "IS_CHANGE_TYPE")
	private int isChangeType;
	
	//回复操作备注
	@Column(name = "REMARK")
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

	public Integer getReplyType() {
		return replyType;
	}

	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
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

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
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
