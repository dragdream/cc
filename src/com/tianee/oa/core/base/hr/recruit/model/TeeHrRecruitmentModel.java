package com.tianee.oa.core.base.hr.recruit.model;


public class TeeHrRecruitmentModel {
	private int sid;
	private int planId;//计划id
	private String planName;// 计划名称
	private int hrPoolId;// 应聘人id(人才库id)
	private String hrPoolName;// 应聘人姓名
	private String position;//招聘岗位
	private String oaName;//OA中用户名
	private int createUserId;// 创建者id
	private String createUserName;// 创建者名称
	private int recordPersonId;// 录用负责人id
	private String recordPersonName;// 录用负责人名称
	private String recordTimeStr;// 录入日期
	private int deptId;// 招聘部门id
	private String deptName;// 招聘部门名称
	private String employeeType;// 员工类型编号
	private String employeeTypeName;// 员工类型名称
	private String administrationLevel;// 行政等级
	private String jobPosition;// 职务
	private String presentPosition;// 职称编号
	private String presentPositionName;// 职称名称
	private String onBoardingTimeStr;// 正式入职时间
	private String startingSalaryTimeStr;// 正式起薪时间
	private String remark;// 备注
	private int createDeptId;// 创建者部门id
	private String createDeptName;// 创建者部门名称
	private String createTimeStr;// 登记时间(创建时间)
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public int getHrPoolId() {
		return hrPoolId;
	}
	public void setHrPoolId(int hrPoolId) {
		this.hrPoolId = hrPoolId;
	}
	public String getHrPoolName() {
		return hrPoolName;
	}
	public void setHrPoolName(String hrPoolName) {
		this.hrPoolName = hrPoolName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOaName() {
		return oaName;
	}
	public void setOaName(String oaName) {
		this.oaName = oaName;
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
	public int getRecordPersonId() {
		return recordPersonId;
	}
	public void setRecordPersonId(int recordPersonId) {
		this.recordPersonId = recordPersonId;
	}
	public String getRecordPersonName() {
		return recordPersonName;
	}
	public void setRecordPersonName(String recordPersonName) {
		this.recordPersonName = recordPersonName;
	}
	public String getRecordTimeStr() {
		return recordTimeStr;
	}
	public void setRecordTimeStr(String recordTimeStr) {
		this.recordTimeStr = recordTimeStr;
	}
	public String getEmployeeType() {
		return employeeType;
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
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getAdministrationLevel() {
		return administrationLevel;
	}
	public void setAdministrationLevel(String administrationLevel) {
		this.administrationLevel = administrationLevel;
	}
	public String getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	public String getPresentPosition() {
		return presentPosition;
	}
	public void setPresentPosition(String presentPosition) {
		this.presentPosition = presentPosition;
	}
	
	
	public String getOnBoardingTimeStr() {
		return onBoardingTimeStr;
	}
	public void setOnBoardingTimeStr(String onBoardingTimeStr) {
		this.onBoardingTimeStr = onBoardingTimeStr;
	}
	public String getStartingSalaryTimeStr() {
		return startingSalaryTimeStr;
	}
	public void setStartingSalaryTimeStr(String startingSalaryTimeStr) {
		this.startingSalaryTimeStr = startingSalaryTimeStr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getEmployeeTypeName() {
		return employeeTypeName;
	}
	public void setEmployeeTypeName(String employeeTypeName) {
		this.employeeTypeName = employeeTypeName;
	}
	public String getPresentPositionName() {
		return presentPositionName;
	}
	public void setPresentPositionName(String presentPositionName) {
		this.presentPositionName = presentPositionName;
	}
	

}
