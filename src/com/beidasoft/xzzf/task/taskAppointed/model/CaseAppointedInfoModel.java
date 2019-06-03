package com.beidasoft.xzzf.task.taskAppointed.model;

import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.org.bean.TeePerson;

public class CaseAppointedInfoModel {

	//执法办案唯一编号
	private String id; 
	
	//所属部门id
	private int organizationId;
	
	//数据创建日期
	private String createTimeStr;  
	
	//10-案件办理-立案任务，20-执法检查，检查任务
	private int taskType;
	
	//任务来源-10-检查送审立案，11-举报送审立案;20-xx举报送审检查
	private int taskRec;
	
	//案件来源表ID(暂不使用)
	private String taskRecId;
	
	//任务来源-10-检查送审立案，11-举报送审立案；20-xx举报送审检查
	private String taskRecName;   
	
	//事项名称
	private String taskName;   
	
	//任务来源人员id
	private int taskSendPersonId;
	
	//任务来源人员姓名
	private String taskSendPersonName;   
	
	//任务相关备注（预留字段）
	private String taskCommentsStr;   
	
	//分派处理方式10-指派立案，11-不予立案
	private int dealType;
	
	//额外备注栏，可存放退回原因等
	private String extraComment;   
	
	//删除标识：1-删除，0-未删除
	private int isDelete;
	
	//审送日期
	private String taskSendTimeStr;  
	
	//立案环节编号
	private int orderIndex; 
	
	private List<TeePerson> listPerson;
	
	private String majorPersonName;
	
	private int majorPersonId;
	
	private String minorPersonName;
	
	private int minorPersonId;
	
	private String mPerson;
	
	private String cPerson;
	 // 当事人类型
    private String litigantType;

    // 个人字号名称
    private String psnShopName;
    
    // 个人名称
    private String psnName;
    
    // 个人地址
    private String psnAddress;
    
    // 个人当事人联系电话
    private String psnTel;
    
    // 单位当事人名称
    private String OrganName;

    // 单位地址
    private String organAddress;

    // 单位负责人姓名
    private String organLeadingName;
    
    // 单位负责人联系电话
    private String organLeadingTel;
    
    // 单位当事人类型（代码表）
    private String organType;
    
 // 单位代号类型（代码表）
    private String organCodeType;
    
    // 单位代号（代码表）
    private String organCode;

 //10.24 追加属性
    // 举报形式
	private String reportForm; 
    
    // 举报来源
	private String reportrec; 
    
    // 检查内容
	private String checkContent; 
    
 // 检查地址
	private String checkAddr;
	
	 //案件处理时间（立案、不予立案的时间）
	private String disposeTimeStr;
	
	//案件处理时间（立案、不予立案的时间）开始
	private String disposeStartTimeStr;
	
	//案件处理时间（立案、不予立案的时间）结束
	private String disposeEndTimeStr;
	
	
	public String getPsnShopName() {
		return psnShopName;
	}

	public void setPsnShopName(String psnShopName) {
		this.psnShopName = psnShopName;
	}

	public String getOrganLeadingName() {
		return organLeadingName;
	}

	public void setOrganLeadingName(String organLeadingName) {
		this.organLeadingName = organLeadingName;
	}

	public String getPsnTel() {
		return psnTel;
	}

	public void setPsnTel(String psnTel) {
		this.psnTel = psnTel;
	}

	public String getOrganLeadingTel() {
		return organLeadingTel;
	}

	public void setOrganLeadingTel(String organLeadingTel) {
		this.organLeadingTel = organLeadingTel;
	}

	public String getPsnAddress() {
		return psnAddress;
	}

	public void setPsnAddress(String psnAddress) {
		this.psnAddress = psnAddress;
	}

	public String getDisposeStartTimeStr() {
		return disposeStartTimeStr;
	}

	public void setDisposeStartTimeStr(String disposeStartTimeStr) {
		this.disposeStartTimeStr = disposeStartTimeStr;
	}

	public String getDisposeEndTimeStr() {
		return disposeEndTimeStr;
	}

	public void setDisposeEndTimeStr(String disposeEndTimeStr) {
		this.disposeEndTimeStr = disposeEndTimeStr;
	}

	public String getDisposeTimeStr() {
		return disposeTimeStr;
	}

	public void setDisposeTimeStr(String disposeTimeStr) {
		this.disposeTimeStr = disposeTimeStr;
	}

	public String getReportForm() {
		return reportForm;
	}

	public void setReportForm(String reportForm) {
		this.reportForm = reportForm;
	}

	public String getReportrec() {
		return reportrec;
	}

	public void setReportrec(String reportrec) {
		this.reportrec = reportrec;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public String getCheckAddr() {
		return checkAddr;
	}

	public void setCheckAddr(String checkAddr) {
		this.checkAddr = checkAddr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public int getTaskRec() {
		return taskRec;
	}

	public void setTaskRec(int taskRec) {
		this.taskRec = taskRec;
	}

	public String getTaskRecId() {
		return taskRecId;
	}

	public void setTaskRecId(String taskRecId) {
		this.taskRecId = taskRecId;
	}

	public String getTaskRecName() {
		return taskRecName;
	}

	public void setTaskRecName(String taskRecName) {
		this.taskRecName = taskRecName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskSendPersonId() {
		return taskSendPersonId;
	}

	public void setTaskSendPersonId(int taskSendPersonId) {
		this.taskSendPersonId = taskSendPersonId;
	}

	public String getTaskSendPersonName() {
		return taskSendPersonName;
	}

	public void setTaskSendPersonName(String taskSendPersonName) {
		this.taskSendPersonName = taskSendPersonName;
	}

	public String getTaskCommentsStr() {
		return taskCommentsStr;
	}

	public void setTaskCommentsStr(String taskCommentsStr) {
		this.taskCommentsStr = taskCommentsStr;
	}

	public int getDealType() {
		return dealType;
	}

	public void setDealType(int dealType) {
		this.dealType = dealType;
	}

	public String getExtraComment() {
		return extraComment;
	}

	public void setExtraComment(String extraComment) {
		this.extraComment = extraComment;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getTaskSendTimeStr() {
		return taskSendTimeStr;
	}

	public void setTaskSendTimeStr(String taskSendTimeStr) {
		this.taskSendTimeStr = taskSendTimeStr;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<TeePerson> getListPerson() {
		return listPerson;
	}

	public void setListPerson(List<TeePerson> listPerson) {
		this.listPerson = listPerson;
	}

	public String getMajorPersonName() {
		return majorPersonName;
	}

	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}

	public int getMajorPersonId() {
		return majorPersonId;
	}

	public void setMajorPersonId(int majorPersonId) {
		this.majorPersonId = majorPersonId;
	}

	public String getMinorPersonName() {
		return minorPersonName;
	}

	public void setMinorPersonName(String minorPersonName) {
		this.minorPersonName = minorPersonName;
	}

	public int getMinorPersonId() {
		return minorPersonId;
	}

	public void setMinorPersonId(int minorPersonId) {
		this.minorPersonId = minorPersonId;
	}




	public String getmPerson() {
		return mPerson;
	}

	public void setmPerson(String mPerson) {
		this.mPerson = mPerson;
	}

	public String getcPerson() {
		return cPerson;
	}

	public void setcPerson(String cPerson) {
		this.cPerson = cPerson;
	}

	public String getLitigantType() {
		return litigantType;
	}

	public void setLitigantType(String litigantType) {
		this.litigantType = litigantType;
	}

	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}

	public String getOrganName() {
		return OrganName;
	}

	public void setOrganName(String organName) {
		OrganName = organName;
	}

	public String getOrganAddress() {
		return organAddress;
	}

	public void setOrganAddress(String organAddress) {
		this.organAddress = organAddress;
	}

	public String getOrganType() {
		return organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getOrganCodeType() {
		return organCodeType;
	}

	public void setOrganCodeType(String organCodeType) {
		this.organCodeType = organCodeType;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	
}
