package com.tianee.oa.subsys.project.model;


public class TeeProjectCostModel {
	
	private int sid;//自增id
	
	private double amount ;//费用金额
	
	//private Date  createTime ;//创建时间
	private String createTimeStr;
	
	//private TeePerson creater;//创建人
	private String createrName;
	
	private int createrId;//创建人主键
	
	//private TeePerson approver;//审批人员
	private String approverName;//审批人姓名
	
	private int approverId;//审批人主键
	
	//private TeeProjectCostType costType;//费用类型
	private int costTypeId;//费用类型主键
	
	private String costTypeName;//费用类型名称
	
	private String  description ;//费用说明
	
	private int  status ;//状态
	
	private String  projectId ;//项目主键
	
	private String projectName;//项目名称
	
	
	private String refusedReason;//拒绝原因

	public String getRefusedReason() {
		return refusedReason;
	}

	public void setRefusedReason(String refusedReason) {
		this.refusedReason = refusedReason;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public int getApproverId() {
		return approverId;
	}

	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	public int getCostTypeId() {
		return costTypeId;
	}

	public void setCostTypeId(int costTypeId) {
		this.costTypeId = costTypeId;
	}

	public String getCostTypeName() {
		return costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
