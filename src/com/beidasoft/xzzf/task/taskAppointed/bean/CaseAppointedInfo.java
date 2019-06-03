package com.beidasoft.xzzf.task.taskAppointed.bean;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZF_TASK_APPOINTED")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class CaseAppointedInfo{

	@Id
	@Column(name="ID")
	private String id; //本表数据ID
	
	@Column(name="ORGANIZATION_ID")
	private int organizationId;//所属部门id
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;  //数据创建日期
	
	@Column(name="TASK_TYPE")
	private int taskType;//10-案件办理-立案任务，20-执法检查，检查任务
	
	@Column(name="TASK_REC")
	private int taskRec;//任务来源-10-检查送审立案，11-举报送审立案;20-xx举报送审检查
	
	@Column(name="TASK_REC_ID")
	private String taskRecId;//案件来源表ID(暂不使用)
	
	@Column(name="TASK_REC_NAME")
	private String taskRecName;   //任务来源-10-检查送审立案，11-举报送审立案；20-xx举报送审检查
	
	@Column(name="TASK_NAME")
	private String taskName;   //事项名称
	
	@Column(name="TASK_SEND_PERSON_ID")
	private int taskSendPersonId;//任务来源人员id
	
	@Column(name="TASK_SEND_PERSON_NAME")
	private String taskSendPersonName;   //任务来源人员姓名
	
	@Column(name="TASK_COMMENTS")
	private String taskComments;   //任务相关备注（预留字段）
	
	@Column(name="DEAL_TYPE")
	private int dealType;//9 案件办结，分派处理方式10-指派立案，11-不予立案
	
	@Column(name="EXTRA_COMMENTS")
	private String extraComments;   //额外备注栏，可存放退回原因等
	
	@Column(name="IS_DELETE")
	private int isDelete;//删除标识：1-删除，0-未删除
	
	@Column(name="TASK_SEND_TIME")
	private Date taskSendTime;  //审送日期

//10.20日追加
	 // 当事人类型   1.公民（非个体工商户）2.单位 3.公民（个体工商户，需要保存字号名称）
    @Column(name = "LITIGANT_TYPE")
    private String litigantType;      

    // 个人字号名称
    @Column(name = "PSN_SHOP_NAME")
    private String psnShopName;
    
    // 个人名称
    @Column(name = "PSN_NAME")
    private String psnName;
    
    // 个人地址
    @Column(name = "PSN_ADDRESSE")
    private String psnAddress;
    
    // 个人当事人联系电话
    @Column(name = "PSN_TEL")
    private String psnTel;
    
    // 单位名称
    @Column(name = "ORGAN_NAME")
    private String OrganName;

    // 单位地址
    @Column(name = "ORGAN_ADDRESS")
    private String organAddress;

    // 单位负责人姓名
    @Column(name = "ORGAN_LEADING_NAME")
    private String organLeadingName;
    
    // 单位负责人联系电话
    @Column(name = "ORGAN_LEADING_TEL")
    private String organLeadingTel;
    
    // 单位当事人类型（代码表）
    @Column(name = "ORGAN_TYPE")
    private String organType;
    
    // 单位代号类型（代码表）
    @Column(name = "ORGAN_CODE_TYPE")
    private String organCodeType;
    
    // 单位代号（代码表）
    @Column(name = "ORGAN_CODE")
    private String organCode;
    
//10.24 追加属性
    // 举报形式
    @Column(name = "REPORT_FORM")
	private String reportForm; 
    
    // 举报来源
    @Column(name = "REPORT_REC")
	private String reportrec; 
    
    // 检查内容
    @Column(name = "CHECK_CONTENT")
	private String checkContent; 
    
    // 检查地址
    @Column(name = "CHECK_ADDR")
	private String checkAddr;
    
    //案件处理时间（立案、不予立案的时间）
    @Column(name="DISPOSE_TIME")
	private Calendar disposeTime;
    
    
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

	public Calendar getDisposeTime() {
		return disposeTime;
	}

	public void setDisposeTime(Calendar disposeTime) {
		this.disposeTime = disposeTime;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskRecId() {
		return taskRecId;
	}

	public void setTaskRecId(String taskRecId) {
		this.taskRecId = taskRecId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	
	
	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
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

	public String getTaskComments() {
		return taskComments;
	}

	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

	public int getDealType() {
		return dealType;
	}

	public void setDealType(int dealType) {
		this.dealType = dealType;
	}

	public String getExtraComments() {
		return extraComments;
	}

	public void setExtraComments(String extraComments) {
		this.extraComments = extraComments;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getTaskSendTime() {
		return taskSendTime;
	}

	public void setTaskSendTime(Date taskSendTime) {
		this.taskSendTime = taskSendTime;
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

//	public List<CaseHandPerson> getListCaseHandPerson() {
//		return listCaseHandPerson;
//	}
//
//	public void setListCaseHandPerson(List<CaseHandPerson> listCaseHandPerson) {
//		this.listCaseHandPerson = listCaseHandPerson;
//	}
	
	
	
	
}
