package com.tianee.oa.subsys.contract.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.contract.bean.TeeContractCategory;
import com.tianee.oa.subsys.contract.bean.TeeContractSort;

public class TeeContractModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sid;
	private String contractName;//合同名称
	private int categoryId;//合同分类
	private String categoryName;
	private int contractSortId;//合同类型
	private String contractSortName;
	private String contractCode;//合同编号
	private int deptId;//所属部门
	private String deptName;
	private String bisUser;//业务员
	private Long verTime;//签订时间
	private Long startTime;//开始时间
	private Long endTime;//结束时间
	private double amount;//合同额
	private int operUserId;//合同录入人
	private String operUserName;//合同录入人
	private String partyAUnit;//甲方单位
	private String partyACharger;//甲方负责人
	private String partyAContact;//甲方联系方式
	private String partyBUnit;//乙方单位
	private String partyBCharger;//乙方负责人
	private String partyBContact;//乙方联系方式
	private String content;//合同内容
	private String remark;//合同备注
	private int runId;//相关流程ID
	private String attachIds;//附件集合
	private List<TeeAttachmentModel> attachList = new ArrayList();
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getContractSortId() {
		return contractSortId;
	}
	public void setContractSortId(int contractSortId) {
		this.contractSortId = contractSortId;
	}
	public String getContractSortName() {
		return contractSortName;
	}
	public void setContractSortName(String contractSortName) {
		this.contractSortName = contractSortName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getBisUser() {
		return bisUser;
	}
	public void setBisUser(String bisUser) {
		this.bisUser = bisUser;
	}
	public Long getVerTime() {
		return verTime;
	}
	public void setVerTime(Long verTime) {
		this.verTime = verTime;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getOperUserId() {
		return operUserId;
	}
	public void setOperUserId(int operUserId) {
		this.operUserId = operUserId;
	}
	public String getOperUserName() {
		return operUserName;
	}
	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}
	public String getPartyAUnit() {
		return partyAUnit;
	}
	public void setPartyAUnit(String partyAUnit) {
		this.partyAUnit = partyAUnit;
	}
	public String getPartyACharger() {
		return partyACharger;
	}
	public void setPartyACharger(String partyACharger) {
		this.partyACharger = partyACharger;
	}
	public String getPartyAContact() {
		return partyAContact;
	}
	public void setPartyAContact(String partyAContact) {
		this.partyAContact = partyAContact;
	}
	public String getPartyBUnit() {
		return partyBUnit;
	}
	public void setPartyBUnit(String partyBUnit) {
		this.partyBUnit = partyBUnit;
	}
	public String getPartyBCharger() {
		return partyBCharger;
	}
	public void setPartyBCharger(String partyBCharger) {
		this.partyBCharger = partyBCharger;
	}
	public String getPartyBContact() {
		return partyBContact;
	}
	public void setPartyBContact(String partyBContact) {
		this.partyBContact = partyBContact;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public String getAttachIds() {
		return attachIds;
	}
	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}
	public List<TeeAttachmentModel> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<TeeAttachmentModel> attachList) {
		this.attachList = attachList;
	}
	
	
}
