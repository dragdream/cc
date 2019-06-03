package com.beidasoft.xzzf.transferred.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fanxi 
 * 执法文书管理实体类
 */
@Entity
@Table(name = "ZF_DOC_TRANSFERRED")
public class DocTransferred {
	// 执法文书管理唯一ID
	@Id
	@Column(name = "ID")
	private String id;

	// 案件的序号
	@Column(name = "TRANSID")
	private int transId;

	// 当事人类型
	@Column(name = "PARTY_TYPE")
	private String partyType;

	// 当事人
	@Column(name = "PARTY_NAME")
	private String partyName;

	// 案件办结日期
	@Column(name = "TRANSFERRED_TRANS_TIME")
	private Date transferredTransTime;

	// 执行单位
	@Column(name = "PERFORM_UNIT")
	private String performUnit;

	
	// 案件编号
	@Column(name = "BASE_ID")
	private String baseId;

	// 创建人ID
	@Column(name = "CREATE_USER_ID")
	private String createUserId;

	// 创建人姓名
	@Column(name = "CREATE_USER_NAME")
	private String createUserName;

	// 创建时间
	@Column(name = "CREATE_TIME")
	private Date createTime;

	// 变更人ID
	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	// 变更人姓名
	@Column(name = "UPDATE_USER_NAME")
	private String updateUserName;

	// 变更时间
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Date getTransferredTransTime() {
		return transferredTransTime;
	}

	public void setTransferredTransTime(Date transferredTransTime) {
		this.transferredTransTime = transferredTransTime;
	}

	public String getPerformUnit() {
		return performUnit;
	}

	public void setPerformUnit(String performUnit) {
		this.performUnit = performUnit;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
