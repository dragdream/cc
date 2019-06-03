package com.beidasoft.xzzf.transferred.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;


/**
 * @author fanxi 
 * 案件文书目录
 */
public class ManagementsModel {
	// 案件文件目录唯一ID
	private String id;
	
	
	//文号文
	private String manaAdd;
	
	//文号年
	private String manaYear;
	
	//文号
	private String manaNum;

	// 序号
	private String manageId;

	// 文号
	private String titanicNumber;

	// 责任者
	private String responsible;

	// 题目
	private String manageTitle;

	// 办结日期
	private String transferredTime;

	// 页号
	private String pageNumber;

	// 备注
	private String manageNote;
	
	//文书预览ID
	private String manaFilePath;
	
	//存放文书+附件页面信息
	private List<ManagementsModel> manaList;
	
	//存放保存时候从 js 
	private String manaListStr;

	// 案件编号
	private String baseId;

	// 变更人ID
	private String updateUserId;

	// 变更人姓名
	private String updateUserName;

	// 变更时间
	private String updateTimeStr;
	
	// 创建人ID
	private String createUserId;

	// 创建人姓名
	private String createUserName;

	// 创建时间
	private Date createTime;
	
	//流程ID
	private int runId;
	
	//案件名称
	private String partyCaseName;
	
	//案件来源
	private String sourceType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
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

	public String getManageTitle() {
		return manageTitle;
	}

	public void setManageTitle(String manageTitle) {
		this.manageTitle = manageTitle;
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

	public String getManageNote() {
		return manageNote;
	}

	public void setManageNote(String manageNote) {
		this.manageNote = manageNote;
	}

	public String getManaFilePath() {
		return manaFilePath;
	}

	public void setManaFilePath(String manaFilePath) {
		this.manaFilePath = manaFilePath;
	}

	public List<ManagementsModel> getManaList() {
		return manaList;
	}

	public void setManaList(List<ManagementsModel> manaList) {
		this.manaList = manaList;
	}

	public String getManaListStr() {
		return manaListStr;
	}

	public void setManaListStr(String manaListStr) {
		this.manaListStr = manaListStr;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
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

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
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
