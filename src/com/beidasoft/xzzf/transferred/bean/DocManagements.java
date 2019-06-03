package com.beidasoft.xzzf.transferred.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fanxi
 * 案件文书目录实体类
 */
@Entity
@Table(name = "ZF_DOC_MANAGEMENT")
public class DocManagements {
	// 案件文件目录唯一ID
	@Id
	@Column(name = "ID")
	private String id;
	
	
	//文号文
	@Column(name = "MANAADD")
	private String manaAdd;
	
	//文号年
	@Column(name = "MANAYEAR")
	private String manaYear;
	
	//文号
	@Column(name = "MANANUM")
	private String manaNum;
	
	// 序号
	@Column(name = "MANAGEID")
	private String manageId;

	// 文号
	@Column(name = "TITANIC_NUMBER")
	private String titanicNumber;

	// 责任者
	@Column(name = "RESPONSIBLE")
	private String responsible;

	// 题目
	@Column(name = "MANAGE_TITLE")
	private String manageTitle;

	// 办结日期
	@Column(name = "TRANSFERRED_TIME")
	private String transferredTime;

	// 页号
	@Column(name = "PAGE_NUMBER")
	private String pageNumber;

	// 备注
	@Column(name = "MANAGE_NOTE")
	private String manageNote;

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
	
	// 流程ID
	@Column(name = "RUN_ID")
	private int runId;
	
	//文书预览路径
	@Column(name = "MANA_FILE_PATH")
	private String manaFilePath;

	//案件名称
	@Column(name = "PARTY_CASE_NAME")
	private String partyCaseName;
	
	//案件来源
	@Column(name = "SOURCE_TYPE")
	private String sourceType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public String getManageTitle() {
		return manageTitle;
	}

	public void setManageTitle(String manageTitle) {
		this.manageTitle = manageTitle;
	}

	public String getManageNote() {
		return manageNote;
	}

	public void setManageNote(String manageNote) {
		this.manageNote = manageNote;
	}

	public String getTitanicNumber() {
		return titanicNumber;
	}

	public void setTitanicNumber(String titanicNumber) {
		this.titanicNumber = titanicNumber;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}



	public String getTransferredTime() {
		return transferredTime;
	}

	public void setTransferredTime(String transferredTime) {
		this.transferredTime = transferredTime;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
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

	public String getManaAdd() {
		return manaAdd;
	}

	public void setManaAdd(String manaAdd) {
		this.manaAdd = manaAdd;
	}

	public String getManaYear() {
		return manaYear;
	}

	public void setManaYear(String manaYear) {
		this.manaYear = manaYear;
	}

	public String getManaNum() {
		return manaNum;
	}

	public void setManaNum(String manaNum) {
		this.manaNum = manaNum;
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

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getManaFilePath() {
		return manaFilePath;
	}

	public void setManaFilePath(String manaFilePath) {
		this.manaFilePath = manaFilePath;
	}

	public String getPartyCaseName() {
		return partyCaseName;
	}

	public void setPartyCaseName(String partyCaseName) {
		this.partyCaseName = partyCaseName;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	
}
