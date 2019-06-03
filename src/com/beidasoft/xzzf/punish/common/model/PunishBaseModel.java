package com.beidasoft.xzzf.punish.common.model;

import java.util.List;

import com.beidasoft.xzzf.punish.common.bean.AffiliatedPerson;


public class PunishBaseModel {

	//执法办案统一编号
	private String baseId;
    
    // 案件编号
    private String baseCode;
    
    //案件标题
    private String baseTitle;

    // 案件来源ID
    private String sourceId;

    // 案件来源类型
    private String sourceType;

    // 案件年度流水号
    private String code;

    // 当事人类型
    private String litigantType;

    // 单位当事人名称
    private String OrganName;

    // 单位当事人住所
    private String organAddress;
    
    // 单位代号类型（唯一信用码、注册号等）
    private String OrganCodeType;
    
    // 单位社会唯一信用号
    private String OrganCode;

    // 单位当事人类型（代码表）
    private String organType;

    // 单位负责人姓名
    private String organLeadingName;

    // 单位负责人联系电话
    private String organLeadingTel;

    // 个人名称
    private String psnName;
    
    // 个人字号名称
    private String psnShopName;

    // 个人当事人性别
    private String psnSex;

    // 个人当事人联系电话
    private String psnTel;

    // 个人当事人住址
    private String psnAddress;

    // 个人当事人证件类型（代码表）
    private String psnType;

    // 个人当事人证件号码
    private String psnIdNo;
    
    //违法时间
    private String IllegalDateStr;
    
    //指派时间
    private String appointTimeStr;
    
    //接收时间
    private String receiveDateStr;

    // 立案日期
    private String filingDateStr;

    // 检查日期
    private String inspectionDateStr;

    // 行政处罚日期
    private String punishmentDateStr;

    // 处罚执行日期
    private String punishmentExeDateStr;

    // 听证日期
    private String hearingDateStr;
    
    // 结案日期
    private String closedDateStr;
    
    // 负责人ID
    private int chargePsnId;

    // 负责人姓名
    private String chargePsnName;
    
    // 负责人部门ID
    private int chargeDeptId;

    // 负责人部门名
    private String chargeDeptName;

    // 主办人ID
    private int majorPersonId;
    
    // 主办人执法证号
    private String majorPersonCode;

    // 主办人姓名
    private String majorPersonName;

    // 协办人ID
    private int minorPersonId;
    
    // 协办人执法证号
    private String minorPersonCode;

    // 协办人姓名
    private String minorPersonName;

    // 部门ID
    private int departmentId;

    // 部门名称
    private String departmentName;

    // 状态
    private String status;
    
    //参与人信息集合
    private String psnArr;
    
    private List<AffiliatedPerson> psnArrList;
    
    //立案日期开始
    private String filingDateStartStr;
    
    //立案日期结束
    private String filingDateEndStr;
    
    //检查日期开始
    private String inspectionDateStartStr;
    
    //检查日期结束
    private String inspectionDateEndStr;
    
    //听证日期开始
    private String hearingDateStartStr;
    
    //听证日期结束
    private String hearingDateEndStr;
    
    //是否申请立案
    private int isRegister;
    
    //是否立案
    private int isApply;
    
    // 案件指派唯一标识
    private String taskId;
    
    // 是否听证 0.不听证 1.听证
    private int isHearing;
    
  //是否结案0.不结案 1.结案IS_CLOSED
    private int isClosed;
    
    // 归档类型
    private String punishType;
    
    // 归档标识
    private String punishFlg;
    
    // 借阅标识
    private String borrowingFlg;
    
    //领域类型
    private String domainType;
    
    //首条回复
    private String firstReply;
    
    //回复数量
    private String replyCount;
    
    private String dealTime;
    
    private int start;
    
    private int length;
    
    //立案是否超时，0未超时，1已超时，默认为0
    private int isFilingDelay;
    
    private int isHasVideo;
    
	public String getFilingDateStartStr() {
		return filingDateStartStr;
	}

	public void setFilingDateStartStr(String filingDateStartStr) {
		this.filingDateStartStr = filingDateStartStr;
	}

	public String getFilingDateEndStr() {
		return filingDateEndStr;
	}

	public void setFilingDateEndStr(String filingDateEndStr) {
		this.filingDateEndStr = filingDateEndStr;
	}

	public String getInspectionDateStartStr() {
		return inspectionDateStartStr;
	}

	public void setInspectionDateStartStr(String inspectionDateStartStr) {
		this.inspectionDateStartStr = inspectionDateStartStr;
	}

	public String getInspectionDateEndStr() {
		return inspectionDateEndStr;
	}

	public void setInspectionDateEndStr(String inspectionDateEndStr) {
		this.inspectionDateEndStr = inspectionDateEndStr;
	}

	
	public String getHearingDateStr() {
		return hearingDateStr;
	}

	public void setHearingDateStr(String hearingDateStr) {
		this.hearingDateStr = hearingDateStr;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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


	public String getIllegalDateStr() {
		return IllegalDateStr;
	}

	public void setIllegalDateStr(String illegalDateStr) {
		IllegalDateStr = illegalDateStr;
	}

	public String getAppointTimeStr() {
		return appointTimeStr;
	}

	public void setAppointTimeStr(String appointTimeStr) {
		this.appointTimeStr = appointTimeStr;
	}

	public String getReceiveDateStr() {
		return receiveDateStr;
	}

	public void setReceiveDateStr(String receiveDateStr) {
		this.receiveDateStr = receiveDateStr;
	}

	public String getFilingDateStr() {
		return filingDateStr;
	}

	public void setFilingDateStr(String filingDateStr) {
		this.filingDateStr = filingDateStr;
	}

	public String getInspectionDateStr() {
		return inspectionDateStr;
	}

	public void setInspectionDateStr(String inspectionDateStr) {
		this.inspectionDateStr = inspectionDateStr;
	}

	public String getPunishmentDateStr() {
		return punishmentDateStr;
	}

	public void setPunishmentDateStr(String punishmentDateStr) {
		this.punishmentDateStr = punishmentDateStr;
	}

	public String getPunishmentExeDateStr() {
		return punishmentExeDateStr;
	}

	public void setPunishmentExeDateStr(String punishmentExeDateStr) {
		this.punishmentExeDateStr = punishmentExeDateStr;
	}

	public String getClosedDateStr() {
		return closedDateStr;
	}

	public void setClosedDateStr(String closedDateStr) {
		this.closedDateStr = closedDateStr;
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

	public String getPsnArr() {
		return psnArr;
	}

	public void setPsnArr(String psnArr) {
		this.psnArr = psnArr;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<AffiliatedPerson> getPsnArrList() {
		return psnArrList;
	}

	public void setPsnArrList(List<AffiliatedPerson> psnArrList) {
		this.psnArrList = psnArrList;
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

	public int getIsHearing() {
		return isHearing;
	}

	public void setIsHearing(int isHearing) {
		this.isHearing = isHearing;
	}

	public String getHearingDateStartStr() {
		return hearingDateStartStr;
	}

	public void setHearingDateStartStr(String hearingDateStartStr) {
		this.hearingDateStartStr = hearingDateStartStr;
	}

	public String getHearingDateEndStr() {
		return hearingDateEndStr;
	}

	public void setHearingDateEndStr(String hearingDateEndStr) {
		this.hearingDateEndStr = hearingDateEndStr;
	}

	public int getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}

	public String getMajorPersonCode() {
		return majorPersonCode;
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

	public void setMajorPersonCode(String majorPersonCode) {
		this.majorPersonCode = majorPersonCode;
	}

	public String getMinorPersonCode() {
		return minorPersonCode;
	}

	public void setMinorPersonCode(String minorPersonCode) {
		this.minorPersonCode = minorPersonCode;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public String getFirstReply() {
		return firstReply;
	}

	public void setFirstReply(String firstReply) {
		this.firstReply = firstReply;
	}

	public String getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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
	
	
}
