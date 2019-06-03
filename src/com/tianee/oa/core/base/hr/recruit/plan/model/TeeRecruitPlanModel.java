package com.tianee.oa.core.base.hr.recruit.plan.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.hr.recruit.requirements.bean.TeeRecruitRequirements;
import com.tianee.oa.core.base.hr.recruit.requirements.model.TeeRecruitRequirementsModel;

public class TeeRecruitPlanModel {
	private int sid;
	private String planNo;// 计划编号
	private String planName;// 计划名称
	private String planDitch;// 招聘渠道,01- 网络招聘 ;02-招聘会招聘; 03-人才猎头推荐
	private double planCost;// 预算费用
	private int planRecrNum;// 招聘人数
	private String startDateStr;// 开始日期
	private String endDateStr;// 结束日期
	private String recruitDescription;// 招聘说明
	private String recruitRemark;// 招聘备注
	private int approvePersonId;// // 审批人id
	private String approvePersonName;// 审批人名称
	private String approveDateStr;// 审批日期
	private String approveComment;// 审批意见
	private int planStatus = 0;// 计划状态;0-新建; 1-批准; 2-不批准
	private int createUserId;// 创建者id
	private String createUserName;// 创建者名称
	private int createDeptId;// 创建者部门id
	private String createDeptName;// 创建者部门名称
	private String requDeptStr;// 需求部门,sid串
	private String requDeptStrName;// 需求部门名称串
	private String createTimeStr;// 登记时间(创建时间)
	
	private List<TeeAttachmentModel> attacheModels;//附件
	private String attacheIds;//附件Ids字符串  以逗号分隔 
	
	private List<TeeRecruitRequirementsModel> recruitRequirementsModelList;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanDitch() {
		return planDitch;
	}
	public void setPlanDitch(String planDitch) {
		this.planDitch = planDitch;
	}
	public int getPlanRecrNum() {
		return planRecrNum;
	}
	
	public double getPlanCost() {
		return planCost;
	}
	public void setPlanCost(double planCost) {
		this.planCost = planCost;
	}
	public void setPlanRecrNum(int planRecrNum) {
		this.planRecrNum = planRecrNum;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getRecruitDescription() {
		return recruitDescription;
	}
	public void setRecruitDescription(String recruitDescription) {
		this.recruitDescription = recruitDescription;
	}
	public String getRecruitRemark() {
		return recruitRemark;
	}
	public void setRecruitRemark(String recruitRemark) {
		this.recruitRemark = recruitRemark;
	}
	public int getApprovePersonId() {
		return approvePersonId;
	}
	public void setApprovePersonId(int approvePersonId) {
		this.approvePersonId = approvePersonId;
	}
	public String getApprovePersonName() {
		return approvePersonName;
	}
	public void setApprovePersonName(String approvePersonName) {
		this.approvePersonName = approvePersonName;
	}
	public String getApproveDateStr() {
		return approveDateStr;
	}
	public void setApproveDateStr(String approveDateStr) {
		this.approveDateStr = approveDateStr;
	}
	public String getApproveComment() {
		return approveComment;
	}
	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}
	public int getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
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
	public int getCreateDeptId() {
		return createDeptId;
	}
	public void setCreateDeptId(int createDeptId) {
		this.createDeptId = createDeptId;
	}
	public String getCreateDeptName() {
		return createDeptName;
	}
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	public String getRequDeptStr() {
		return requDeptStr;
	}
	public void setRequDeptStr(String requDeptStr) {
		this.requDeptStr = requDeptStr;
	}
	public String getRequDeptStrName() {
		return requDeptStrName;
	}
	public void setRequDeptStrName(String requDeptStrName) {
		this.requDeptStrName = requDeptStrName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}
	public String getAttacheIds() {
		return attacheIds;
	}
	public void setAttacheIds(String attacheIds) {
		this.attacheIds = attacheIds;
	}
	public List<TeeRecruitRequirementsModel> getRecruitRequirementsModelList() {
		return recruitRequirementsModelList;
	}
	public void setRecruitRequirementsModelList(
			List<TeeRecruitRequirementsModel> recruitRequirementsModelList) {
		this.recruitRequirementsModelList = recruitRequirementsModelList;
	}

}
