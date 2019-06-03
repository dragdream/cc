package com.beidasoft.xzzf.punish.common.bean;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_PUNISH_BASE")
public class PunishBase {
	
	// 执法办案统一编号
    @Id
    @Column(name = "BASE_ID")
    private String baseId;

    // 案件编号
    @Column(name = "BASE_CODE")
    private String baseCode;
    
    // 案件标题
    @Column(name = "BASE_TITLE")
    private String baseTitle;

    // 案件来源ID
    @Column(name = "SOURCE_ID")
    private String sourceId;

    // 案件来源类型
    @Column(name = "SOURCE_TYPE")
    private String sourceType;

    // 当事人类型
    @Column(name = "LITIGANT_TYPE")
    private String litigantType;

    // 单位名称
    @Column(name = "ORGAN_NAME")
    private String OrganName;
    
    // 单位代号类型（唯一信用码、注册号等）
    @Column(name = "ORGAN_CODE_TYPE")
    private String OrganCodeType;
    
    // 单位社会唯一信用号
    @Column(name = "ORGAN_CODE")
    private String OrganCode;

    // 单位地址
    @Column(name = "ORGAN_ADDRESS")
    private String organAddress;

    // 单位负责人类型（代码表）
    @Column(name = "ORGAN_TYPE")
    private String organType;

    // 单位负责人姓名
    @Column(name = "ORGAN_LEADING_NAME")
    private String organLeadingName;

    // 单位负责人联系电话
    @Column(name = "ORGAN_LEADING_TEL")
    private String organLeadingTel;

    // 个人名称
    @Column(name = "PSN_NAME")
    private String psnName;
    
    // 个人字号名称
    @Column(name = "PSN_SHOP_NAME")
    private String psnShopName;

    // 个人当事人性别
    @Column(name = "PSN_SEX")
    private String psnSex;

    // 个人当事人联系电话
    @Column(name = "PSN_TEL")
    private String psnTel;

    // 个人当事人住址
    @Column(name = "PSN_ADDRESS")
    private String psnAddress;

    // 个人当事人证件类型（代码表）
    @Column(name = "PSN_TYPE")
    private String psnType;

    // 个人当事人证件号码
    @Column(name = "PSN_ID_NO")
    private String psnIdNo;
    
    //违法时间
    @Column(name = "ILLEGAL_DATE")
    private Date IllegalDate;
    
    //案件指派时间
    @Column(name = "APPOINT_TIME")
    private Calendar appointTime;
    
    //案件接收时间
    @Column(name = "RECEIVE_DATE")
    private Calendar receiveDate;
    
    // 立案日期
    @Column(name = "FILING_DATE")
    private Date filingDate;

    // 检查日期
    @Column(name = "INSPECTION_DATE")
    private Date inspectionDate;

    // 行政处罚日期
    @Column(name = "PUNISHMENT_DATE")
    private Date punishmentDate;

    // 处罚执行日期
    @Column(name = "PUNISHMENT_EXE_DATE")
    private Date punishmentExeDate;
    
    // 听证日期
    @Column(name = "HEARING_DATE")
    private Date hearingDate;

    // 结案日期
    @Column(name = "CLOSED_DATE")
    private Date closedDate;
    
    // 负责人ID
    @Column(name = "CHARGE_PSN_ID")
    private int chargePsnId;

    // 负责人姓名
    @Column(name = "CHARGE_PSN_NAME")
    private String chargePsnName;
    
    // 负责人部门ID
    @Column(name = "CHARGE_DEPT_ID")
    private int chargeDeptId;

    // 负责人部门名
    @Column(name = "CHARGE_DEPT_NAME")
    private String chargeDeptName;

    // 主办人ID
    @Column(name = "MAJOR_PERSON_ID")
    private int majorPersonId;
    
    // 主办人执法证号
    @Column(name = "MAJOR_PERSON_CODE")
    private String majorPersonCode;

    // 主办人姓名
    @Column(name = "MAJOR_PERSON_NAME")
    private String majorPersonName;
    
    // 协办人ID
    @Column(name = "MINOR_PERSON_ID")
    private int minorPersonId;
    
    // 协办人执法证号
    @Column(name = "MINOR_PERSON_CODE")
    private String minorPersonCode;

    // 协办人姓名
    @Column(name = "MINOR_PERSON_NAME")
    private String minorPersonName;

    // 部门ID
    @Column(name = "DEPARTMENT_ID")
    private int departmentId;

    // 部门名称
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    // 状态
    @Column(name = "STATUS")
    private String status;
    
    // 案件指派唯一标识
    @Column(name = "TASK_ID")
    private String taskId;
    
    // 是否申请立案  0.已申请  1.未申请
    @Column(name = "IS_REGISTER")
    private int isRegister;  
    
    // 是否立案  0.已立案  1.未立案
    @Column(name = "IS_APPLY")
    private int isApply;

    // 是否听证 0.不听证 1.听证
    @Column(name = "IS_HEARING")
    private int isHearing;
    
    //是否结案0.不结案 1.结案IS_CLOSED
    @Column(name = "IS_CLOSED")
    private int isClosed;
    
    //是否检查合格 0.不合格 1.合格
    @Column(name = "IS_UNERROR")
    private int isUnerror;
    
    // 归档类型
    @Column(name = "PUNISH_TYPE")
    private String punishType;
    
    // 归档标识
    @Column(name = "PUNISH_FLG")
    private String punishFlg;
    
    // 借阅标识
    @Column(name = "BORROWING_FLG")
    private String borrowingFlg;
    
    //领域类型
    @Column(name = "DOMAIN_TYPE")
    private String domainType;
    
    //是否已经最终回复
    @Column(name = "IS_FINAL_REPLY")
    private int isFinalReply;
    
    //是否上报至法制办，0未上报，1已上报，默认为0
    @Column(name = "REPORT_OFFICE")
    private int reportOffice;
    
    //是否上报至文化部，0未上报，1已上报，默认为0
    @Column(name = "REPORT_CULTURE")
    private int reportCulture;
    
    //立案是否超时，0未超时，1已超时，默认为0
    @Column(name = "IS_FILING_DELAY")
    private int isFilingDelay;
    
    //有无执法视频，0无，1有，默认0
    @Column(name = "IS_HAS_VIDEO")
    private int isHasVideo;
    
	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public String getBaseTitle() {
		return baseTitle;
	}

	public void setBaseTitle(String baseTitle) {
		this.baseTitle = baseTitle;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getLitigantType() {
		return litigantType;
	}

	public void setLitigantType(String litigantType) {
		this.litigantType = litigantType;
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

	public String getOrganLeadingName() {
		return organLeadingName;
	}

	public void setOrganLeadingName(String organLeadingName) {
		this.organLeadingName = organLeadingName;
	}

	public String getOrganLeadingTel() {
		return organLeadingTel;
	}

	public void setOrganLeadingTel(String organLeadingTel) {
		this.organLeadingTel = organLeadingTel;
	}

	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}

	public String getPsnShopName() {
		return psnShopName;
	}

	public void setPsnShopName(String psnShopName) {
		this.psnShopName = psnShopName;
	}

	public String getPsnSex() {
		return psnSex;
	}

	public void setPsnSex(String psnSex) {
		this.psnSex = psnSex;
	}

	public String getPsnTel() {
		return psnTel;
	}

	public void setPsnTel(String psnTel) {
		this.psnTel = psnTel;
	}

	public String getPsnAddress() {
		return psnAddress;
	}

	public void setPsnAddress(String psnAddress) {
		this.psnAddress = psnAddress;
	}

	public String getPsnType() {
		return psnType;
	}

	public void setPsnType(String psnType) {
		this.psnType = psnType;
	}

	public String getPsnIdNo() {
		return psnIdNo;
	}

	public void setPsnIdNo(String psnIdNo) {
		this.psnIdNo = psnIdNo;
	}

	public Date getIllegalDate() {
		return IllegalDate;
	}

	public void setIllegalDate(Date illegalDate) {
		IllegalDate = illegalDate;
	}

	public Calendar getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Calendar appointTime) {
		this.appointTime = appointTime;
	}

	public Calendar getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Calendar receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public Date getPunishmentDate() {
		return punishmentDate;
	}

	public void setPunishmentDate(Date punishmentDate) {
		this.punishmentDate = punishmentDate;
	}

	public Date getPunishmentExeDate() {
		return punishmentExeDate;
	}

	public void setPunishmentExeDate(Date punishmentExeDate) {
		this.punishmentExeDate = punishmentExeDate;
	}

	public Date getHearingDate() {
		return hearingDate;
	}

	public void setHearingDate(Date hearingDate) {
		this.hearingDate = hearingDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public int getChargePsnId() {
		return chargePsnId;
	}

	public void setChargePsnId(int chargePsnId) {
		this.chargePsnId = chargePsnId;
	}

	public String getChargePsnName() {
		return chargePsnName;
	}

	public void setChargePsnName(String chargePsnName) {
		this.chargePsnName = chargePsnName;
	}

	public int getChargeDeptId() {
		return chargeDeptId;
	}

	public void setChargeDeptId(int chargeDeptId) {
		this.chargeDeptId = chargeDeptId;
	}

	public String getChargeDeptName() {
		return chargeDeptName;
	}

	public void setChargeDeptName(String chargeDeptName) {
		this.chargeDeptName = chargeDeptName;
	}

	public int getMajorPersonId() {
		return majorPersonId;
	}

	public void setMajorPersonId(int majorPersonId) {
		this.majorPersonId = majorPersonId;
	}

	public String getMajorPersonName() {
		return majorPersonName;
	}

	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}

	public int getMinorPersonId() {
		return minorPersonId;
	}

	public void setMinorPersonId(int minorPersonId) {
		this.minorPersonId = minorPersonId;
	}

	public String getMajorPersonCode() {
		return majorPersonCode;
	}

	public void setMajorPersonCode(String majorPersonCode) {
		this.majorPersonCode = majorPersonCode;
	}

	public String getMinorPersonCode() {
		return minorPersonCode;
	}

	public void setMinorPersonCode(String minorPersonCode) {
		this.minorPersonCode = minorPersonCode;
	}

	public String getMinorPersonName() {
		return minorPersonName;
	}

	public void setMinorPersonName(String minorPersonName) {
		this.minorPersonName = minorPersonName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(int isRegister) {
		this.isRegister = isRegister;
	}

	public int getIsApply() {
		return isApply;
	}

	public void setIsApply(int isApply) {
		this.isApply = isApply;
	}

	public int getIsHearing() {
		return isHearing;
	}

	public void setIsHearing(int isHearing) {
		this.isHearing = isHearing;
	}

	public int getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}

	public int getIsUnerror() {
		return isUnerror;
	}

	public void setIsUnerror(int isUnerror) {
		this.isUnerror = isUnerror;
	}

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}

	public String getPunishFlg() {
		return punishFlg;
	}

	public void setPunishFlg(String punishFlg) {
		this.punishFlg = punishFlg;
	}

	public String getBorrowingFlg() {
		return borrowingFlg;
	}

	public void setBorrowingFlg(String borrowingFlg) {
		this.borrowingFlg = borrowingFlg;
	}

	public String getOrganCodeType() {
		return OrganCodeType;
	}

	public void setOrganCodeType(String organCodeType) {
		OrganCodeType = organCodeType;
	}

	public String getOrganCode() {
		return OrganCode;
	}

	public void setOrganCode(String organCode) {
		OrganCode = organCode;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public int getIsFinalReply() {
		return isFinalReply;
	}

	public void setIsFinalReply(int isFinalReply) {
		this.isFinalReply = isFinalReply;
	}

	public int getReportOffice() {
		return reportOffice;
	}

	public void setReportOffice(int reportOffice) {
		this.reportOffice = reportOffice;
	}

	public int getReportCulture() {
		return reportCulture;
	}

	public void setReportCulture(int reportCulture) {
		this.reportCulture = reportCulture;
	}

	public int getIsFilingDelay() {
		return isFilingDelay;
	}

	public void setIsFilingDelay(int isFilingDelay) {
		this.isFilingDelay = isFilingDelay;
	}

	public int getIsHasVideo() {
		return isHasVideo;
	}

	public void setIsHasVideo(int isHasVideo) {
		this.isHasVideo = isHasVideo;
	}

    
//	@OneToMany(mappedBy="punishBase", fetch=FetchType.LAZY)
//	private List<PunishTache>  punishTaches =new ArrayList<PunishTache>(0);
    
//	public List<PunishTache> getPunishTaches() {
//		return punishTaches;
//	}
//
//	public void setPunishTaches(List<PunishTache> punishTaches) {
//		this.punishTaches = punishTaches;
//	}

}
