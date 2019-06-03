package com.beidasoft.zfjd.power.model;

public class PowerAdjustModel {
    private String id;
    
    // 批次号
    private String batchCode;
    
    // 创建人
    private int userId;
    
    // 创建人姓名
    private String userName;
    
    // 创建日期
    private String createDateStr;
    
    // 提交部门
    private String departmentId;
    
    // 部门名称
    private String departmentName;
    
    // 本批次审核职权总数
    private Integer examineSum;
    
    // 选中需要提交的职权
    private String selectedPowerId;
    
    // 调整原因
    private String adjustReason;
    
    // 前台展示变更原因
    private String adjustReasonShow;
    
    // 当前审核状态
    private String currentStatus;
    
    // 监督部门ID
    private String supDeptId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getExamineSum() {
        return examineSum;
    }

    public void setExamineSum(Integer examineSum) {
        this.examineSum = examineSum;
    }

    public String getSelectedPowerId() {
        return selectedPowerId;
    }

    public void setSelectedPowerId(String selectedPowerId) {
        this.selectedPowerId = selectedPowerId;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    public String getAdjustReasonShow() {
        return adjustReasonShow;
    }

    public void setAdjustReasonShow(String adjustReasonShow) {
        this.adjustReasonShow = adjustReasonShow;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

	public String getSupDeptId() {
		return supDeptId;
	}

	public void setSupDeptId(String supDeptId) {
		this.supDeptId = supDeptId;
	}
    
    
}
