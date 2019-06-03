package com.tianee.oa.subsys.contract.model;

import javax.persistence.Column;


public class TeeContractRemindModel {
	private int sid;
	private String remindContent;
	private String remindTimeDesc;
	private String toUserIds;
	private String toUserNames;
	private String toUserUids;
	private int contractId;
	private String contractName;
	private int crUserId;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getRemindContent() {
		return remindContent;
	}
	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}
	public String getRemindTimeDesc() {
		return remindTimeDesc;
	}
	public void setRemindTimeDesc(String remindTimeDesc) {
		this.remindTimeDesc = remindTimeDesc;
	}
	public String getToUserIds() {
		return toUserIds;
	}
	public void setToUserIds(String toUserIds) {
		this.toUserIds = toUserIds;
	}
	public String getToUserNames() {
		return toUserNames;
	}
	public void setToUserNames(String toUserNames) {
		this.toUserNames = toUserNames;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public int getCrUserId() {
		return crUserId;
	}
	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}
	public String getToUserUids() {
		return toUserUids;
	}
	public void setToUserUids(String toUserUids) {
		this.toUserUids = toUserUids;
	}
	
	
}
