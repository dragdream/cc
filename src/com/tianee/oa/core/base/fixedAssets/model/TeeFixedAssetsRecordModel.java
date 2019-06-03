package com.tianee.oa.core.base.fixedAssets.model;

import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeFixedAssetsRecordModel {
	private int sid;
	private String  deptId;//申请部门
	private String deptName ;//
	private String userId;//申请人
	private String userName;
	private String fixedAssetsId;//固定资产
	private String fixedAssetsName;//
	private String assetCode ;//编号

	private String optDateStr;//领用、返库时间
	
	private String optReason;//操作原因

	private String optRemark;//操作备注

	private String optType;//操作类型  0 - 领用   1- 返库
	private String optTypeDesc ;
	
	private String optAddress;//报废存档位置  --- 报废字段专用
	private String repairUnit;//维修单位
	private String repairUser;//维修负责人
	private String telphone;//联系电话
	private double repairCost;//维修费用
	private int repairConfirm;//维修确认
	
	private List<TeeAttachmentModel> attacheModels;
	
	private int runId;//流程ID
	private String runPrcsName;//
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFixedAssetsId() {
		return fixedAssetsId;
	}
	public void setFixedAssetsId(String fixedAssetsId) {
		this.fixedAssetsId = fixedAssetsId;
	}
	public String getFixedAssetsName() {
		return fixedAssetsName;
	}
	public void setFixedAssetsName(String fixedAssetsName) {
		this.fixedAssetsName = fixedAssetsName;
	}
	public String getOptDateStr() {
		return optDateStr;
	}
	public void setOptDateStr(String optDateStr) {
		this.optDateStr = optDateStr;
	}
	public String getOptReason() {
		return optReason;
	}
	public void setOptReason(String optReason) {
		this.optReason = optReason;
	}
	public String getOptRemark() {
		return optRemark;
	}
	public void setOptRemark(String optRemark) {
		this.optRemark = optRemark;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}

	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public String getRunPrcsName() {
		return runPrcsName;
	}
	public void setRunPrcsName(String runPrcsName) {
		this.runPrcsName = runPrcsName;
	}
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}
	public String getOptAddress() {
		return optAddress;
	}
	public void setOptAddress(String optAddress) {
		this.optAddress = optAddress;
	}
	public String getOptTypeDesc() {
		return optTypeDesc;
	}
	public void setOptTypeDesc(String optTypeDesc) {
		this.optTypeDesc = optTypeDesc;
	}
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getRepairUnit() {
		return repairUnit;
	}
	public void setRepairUnit(String repairUnit) {
		this.repairUnit = repairUnit;
	}
	public String getRepairUser() {
		return repairUser;
	}
	public void setRepairUser(String repairUser) {
		this.repairUser = repairUser;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public double getRepairCost() {
		return repairCost;
	}
	public void setRepairCost(double repairCost) {
		this.repairCost = repairCost;
	}
	public int getRepairConfirm() {
		return repairConfirm;
	}
	public void setRepairConfirm(int repairConfirm) {
		this.repairConfirm = repairConfirm;
	}
	
	
}
