package com.tianee.oa.core.base.hr.training.plan.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeTrainingPlanModel {
	private int sid;
	private String planNo;// 计划编号
	private String planName;// 计划名称
	private String planChannel;// 培训渠道id
	private String planChannelName;// 培训渠道名称
	
	private double planCost;// 培训预算
	private String courseStartTimeStr;// 开课时间
	private String courseEndTimeStr;// 开课结束时间
	private int approvePersonId;// // 审批人id
	private String approvePersonName;// 审批人名称
	private String approveTimeStr;// 审批时间
	private String approveComment;// 审批意见
	private int planStatus = 0;// 计划状态;0-新建（待审批）; 1-批准; 2-不批准
	private int joinNum;// 计划培训人数
	private String joinDeptIdStr;// 参与培训部门,sid串，多个用逗号隔开
	private String joinDeptNameStr;// 参与培训部门,名称串，多个用逗号隔开
	private String joinPersonIdStr;// 参与培训人员,sid串，多个用逗号隔开
	private String joinPersonNameStr;// 参与培训人员名称串，多个用逗号隔开

	private String planRequires;// 培训要求
	private String institutionName;// 培训机构名称
	private String institutionInfo;// 培训机构相关信息
	private String institutionContact;// 培训机构联系人
	private String instituContactInfo;// 培训机构联系人相关信息
	private String courseName;// 培训课程名称
	private int hostDepartmentsId;// // 主办部门id
	private String hostDepartmentsName;// 主办部门名称
	private int chargePersonId;// 负责人id
	private String chargePersonName;// 负责人名称
	private double courseHours;// 总课时
	private double coursePay;// 实际费用
	private String courseTypes;// 培训形式id
	private String courseTypesName;// 培训形式名称
	
	private String description;// 培训说明
	private String remark;// 培训备注
	private String address;// 培训地点
	private String content;// 培训内容
	private int createUserId;// 创建者id
	private String createUserName;// 创建者名称
	private int createDeptId;// 创建者部门id
	private String createDeptName;// 创建者部门名称
	private String createTimeStr;// 登记时间(创建时间)
	
	private List<TeeAttachmentModel> attacheModels;//附件
	private String attacheIds;//附件Ids字符串  以逗号分隔 

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

	public String getPlanChannel() {
		return planChannel;
	}

	public void setPlanChannel(String planChannel) {
		this.planChannel = planChannel;
	}

	public double getPlanCost() {
		return planCost;
	}

	public void setPlanCost(double planCost) {
		this.planCost = planCost;
	}

	public String getCourseStartTimeStr() {
		return courseStartTimeStr;
	}

	public void setCourseStartTimeStr(String courseStartTimeStr) {
		this.courseStartTimeStr = courseStartTimeStr;
	}

	public String getCourseEndTimeStr() {
		return courseEndTimeStr;
	}

	public void setCourseEndTimeStr(String courseEndTimeStr) {
		this.courseEndTimeStr = courseEndTimeStr;
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

	public String getApproveTimeStr() {
		return approveTimeStr;
	}

	public void setApproveTimeStr(String approveTimeStr) {
		this.approveTimeStr = approveTimeStr;
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

	public int getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(int joinNum) {
		this.joinNum = joinNum;
	}

	public String getJoinDeptIdStr() {
		return joinDeptIdStr;
	}

	public void setJoinDeptIdStr(String joinDeptIdStr) {
		this.joinDeptIdStr = joinDeptIdStr;
	}

	public String getJoinDeptNameStr() {
		return joinDeptNameStr;
	}

	public void setJoinDeptNameStr(String joinDeptNameStr) {
		this.joinDeptNameStr = joinDeptNameStr;
	}

	public String getJoinPersonIdStr() {
		return joinPersonIdStr;
	}

	public void setJoinPersonIdStr(String joinPersonIdStr) {
		this.joinPersonIdStr = joinPersonIdStr;
	}

	public String getJoinPersonNameStr() {
		return joinPersonNameStr;
	}

	public void setJoinPersonNameStr(String joinPersonNameStr) {
		this.joinPersonNameStr = joinPersonNameStr;
	}

	public String getPlanRequires() {
		return planRequires;
	}

	public void setPlanRequires(String planRequires) {
		this.planRequires = planRequires;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getInstitutionInfo() {
		return institutionInfo;
	}

	public void setInstitutionInfo(String institutionInfo) {
		this.institutionInfo = institutionInfo;
	}

	public String getInstitutionContact() {
		return institutionContact;
	}

	public void setInstitutionContact(String institutionContact) {
		this.institutionContact = institutionContact;
	}

	public String getInstituContactInfo() {
		return instituContactInfo;
	}

	public void setInstituContactInfo(String instituContactInfo) {
		this.instituContactInfo = instituContactInfo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getHostDepartmentsId() {
		return hostDepartmentsId;
	}

	public void setHostDepartmentsId(int hostDepartmentsId) {
		this.hostDepartmentsId = hostDepartmentsId;
	}

	public String getHostDepartmentsName() {
		return hostDepartmentsName;
	}

	public void setHostDepartmentsName(String hostDepartmentsName) {
		this.hostDepartmentsName = hostDepartmentsName;
	}

	public int getChargePersonId() {
		return chargePersonId;
	}

	public void setChargePersonId(int chargePersonId) {
		this.chargePersonId = chargePersonId;
	}

	public String getChargePersonName() {
		return chargePersonName;
	}

	public void setChargePersonName(String chargePersonName) {
		this.chargePersonName = chargePersonName;
	}


	public double getCoursePay() {
		return coursePay;
	}

	public void setCoursePay(double coursePay) {
		this.coursePay = coursePay;
	}

	public String getCourseTypes() {
		return courseTypes;
	}

	public void setCourseTypes(String courseTypes) {
		this.courseTypes = courseTypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public double getCourseHours() {
		return courseHours;
	}

	public void setCourseHours(double courseHours) {
		this.courseHours = courseHours;
	}

	public String getPlanChannelName() {
		return planChannelName;
	}

	public void setPlanChannelName(String planChannelName) {
		this.planChannelName = planChannelName;
	}

	public String getCourseTypesName() {
		return courseTypesName;
	}

	public void setCourseTypesName(String courseTypesName) {
		this.courseTypesName = courseTypesName;
	}
	

}
