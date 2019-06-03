package com.beidasoft.xzzf.clue.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 线索管理表实体类
 */
@Entity
@Table(name="ZF_CLUE_BASE")
public class Clue {
    // 线索管理表主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 线索关联执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 线索来源（代码表）
    @Column(name = "CLUE_SOURCE")
    private String clueSource;

    // 线索来源备注（手动填写）
    @Column(name = "CLUE_SOURCE_REMARK")
    private String clueSourceRemark;

    // 举报形式（代码表）
    @Column(name = "REPORT_FORM")
    private String reportForm;

    // 其他省（代码表）
    @Column(name = "ANOTHER_PROVINCE")
    private int anotherProvince;

    // 其他市（代码表）
    @Column(name = "ANOTHER_CITY")
    private int anotherCity;

    // 其他单位
    @Column(name = "ANOTHER_EMPLOYEE")
    private String anotherEmployee;

    // 举报类别（代码表）
    @Column(name = "REPORT_TYPE")
    private int reportType;

    // 来文标题
    @Column(name = "DOCUMENT_TITLE")
    private String documentTitle;

    // 来文编号
    @Column(name = "DOCUMENT_CODE")
    private String documentCode;
    
    //文号
    @Column(name = "DOC_NUMS")
    private String docNums;

    // 来文日期
    @Column(name = "DOCUMENT_DATE")
    private Date documentDate;

    // 举报日期
    @Column(name = "REPORT_DATE")
    private Date reportDate;

    // 举报内容
    @Column(name = "REPORT_CONTENT")
    private String reportContent;

    // 举报材料
    @Column(name = "REPORT_MATERIALS")
    private String reportMaterials;

    // 备注
    @Column(name = "REMARKS")
    private String remarks;

    // 送审意见
    @Column(name = "SUBMIT_CONTENT")
    private String submitContent;

    // 处理意见
    @Column(name = "DEAL_CONTENT")
    private String dealContent;

    // 地区
    @Column(name = "AREA")
    private String area;

    // 被举报人类型
    @Column(name = "REPORTED_TYPE")
    private int reportedType;

    // 要求奖励
    @Column(name = "IS_REWARD")
    private int isReward;

    // 要求回复
    @Column(name = "IS_REPLY")
    private int isReply;

    // 被举报人姓名
    @Column(name = "PERSONAL_REPORTED_NAME")
    private String personalReportedName;

    // 被举报人个体工商户名
    @Column(name = "PERSONAL_REPORTED_SHOP_NAME")
    private String personalReportedShopName;

    // 被举报人联系方式
    @Column(name = "PERSONAL_REPORTED_TEL")
    private String personalReportedTel;

    // 被举报人地址
    @Column(name = "PERSONAL_REPORTED_ADDRESS")
    private String personalReportedAddress;

    // 被举报人邮箱
    @Column(name = "PERSONAL_REPORTED_MAIL")
    private String personalReportedMail;

    // 被举报组织机构名称
    @Column(name = "ORGAN_REPORTED_NAME")
    private String organReportedName;

    // 被举报组织机构代码
    @Column(name = "ORGAN_REPORTED_CODE")
    private String organReportedCode;

    // 被举报组织地址
    @Column(name = "ORGAN_REPORTED_ADDRESS")
    private String organReportedAddress;

    // 被举报组织负责人类型
    @Column(name = "ORGAN_REPORTED_PERSON_TYPE")
    private int organReportedPersonType;

    // 被举报组织负责人名
    @Column(name = "ORGAN_REPORTED_PERSON_NAME")
    private String organReportedPersonName;

    // 被举报组织法人联系方式
    @Column(name = "ORGAN_REPORTED_PERSON_TEL")
    private String organReportedPersonTel;

    // 是否多次被举报
    @Column(name = "IS_REPORTEDS")
    private int isReporteds;

    // 是否受理
    @Column(name = "IS_ACCEPT")
    private int isAccept;
    
    // 受理时间
    @Column(name = "ACCEPT_TIME")
    private Date AcceptTime;

    // 受理人Id
    @Column(name = "ACCEPT_USER_ID")
    private int acceptUserId;

    // 受理人名
    @Column(name = "ACCEPT_USER_NAME")
    private String acceptUserName;

    // 不受理原因
    @Column(name = "DENIAL_REASON")
    private String denialReason;

    // 办理单位
    @Column(name = "DEAL_DEPART")
    private int dealDepart;

    // 办理时间
    @Column(name = "DEAL_TIME")
    private int dealTime;

    // 办理时间备注（手动输入）
    @Column(name = "DEAL_TIME_REMARK")
    private int dealTimeRemark;

    // 是否通过确认（1.直接发送，2.提交审批）
    @Column(name = "IS_ADMIT")
    private int isAdmit;

    // 创建人ID
    @Column(name = "CREATE_USER_ID")
    private int createUserId;

    // 创建人名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 修改人ID
    @Column(name = "UPDATE_USER_ID")
    private int updateUserId;

    // 修改人名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;
    
    // 修改日期
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    // 当前状态
    @Column(name = "STAUS")
    private int staus;
    
    //审批环节外挂流程的runId   在审批结束后需要用到
    @Column(name = "FLOW_RUN_ID")
    private int flowRunId;
    
    //部门负责人意见
    @Column(name = "LEADER_CONTENT")
    private String leaderContent;
    
    //部门负责人意见时间
    @Column(name = "LEADER_TIME")
    private Date leaderTime;

    // 删除标志
    @Column(name = "IS_DELETE")
    private int isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getClueSource() {
        return clueSource;
    }

    public void setClueSource(String clueSource) {
        this.clueSource = clueSource;
    }

    public String getClueSourceRemark() {
        return clueSourceRemark;
    }

    public void setClueSourceRemark(String clueSourceRemark) {
        this.clueSourceRemark = clueSourceRemark;
    }

    public String getReportForm() {
        return reportForm;
    }

    public void setReportForm(String reportForm) {
        this.reportForm = reportForm;
    }

    public int getAnotherProvince() {
        return anotherProvince;
    }

    public void setAnotherProvince(int anotherProvince) {
        this.anotherProvince = anotherProvince;
    }

    public int getAnotherCity() {
        return anotherCity;
    }

    public void setAnotherCity(int anotherCity) {
        this.anotherCity = anotherCity;
    }

    public String getAnotherEmployee() {
        return anotherEmployee;
    }

    public void setAnotherEmployee(String anotherEmployee) {
        this.anotherEmployee = anotherEmployee;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocNums() {
		return docNums;
	}

	public void setDocNums(String docNums) {
		this.docNums = docNums;
	}

	public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getReportMaterials() {
        return reportMaterials;
    }

    public void setReportMaterials(String reportMaterials) {
        this.reportMaterials = reportMaterials;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSubmitContent() {
        return submitContent;
    }

    public void setSubmitContent(String submitContent) {
        this.submitContent = submitContent;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getReportedType() {
        return reportedType;
    }

    public void setReportedType(int reportedType) {
        this.reportedType = reportedType;
    }

    public int getIsReward() {
        return isReward;
    }

    public void setIsReward(int isReward) {
        this.isReward = isReward;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    public String getPersonalReportedName() {
        return personalReportedName;
    }

    public void setPersonalReportedName(String personalReportedName) {
        this.personalReportedName = personalReportedName;
    }

    public String getPersonalReportedShopName() {
        return personalReportedShopName;
    }

    public void setPersonalReportedShopName(String personalReportedShopName) {
        this.personalReportedShopName = personalReportedShopName;
    }

    public String getPersonalReportedTel() {
        return personalReportedTel;
    }

    public void setPersonalReportedTel(String personalReportedTel) {
        this.personalReportedTel = personalReportedTel;
    }

    public String getPersonalReportedAddress() {
        return personalReportedAddress;
    }

    public void setPersonalReportedAddress(String personalReportedAddress) {
        this.personalReportedAddress = personalReportedAddress;
    }

    public String getPersonalReportedMail() {
        return personalReportedMail;
    }

    public void setPersonalReportedMail(String personalReportedMail) {
        this.personalReportedMail = personalReportedMail;
    }

    public String getOrganReportedName() {
        return organReportedName;
    }

    public void setOrganReportedName(String organReportedName) {
        this.organReportedName = organReportedName;
    }

    public String getOrganReportedCode() {
        return organReportedCode;
    }

    public void setOrganReportedCode(String organReportedCode) {
        this.organReportedCode = organReportedCode;
    }

    public String getOrganReportedAddress() {
        return organReportedAddress;
    }

    public void setOrganReportedAddress(String organReportedAddress) {
        this.organReportedAddress = organReportedAddress;
    }

    public int getOrganReportedPersonType() {
        return organReportedPersonType;
    }

    public void setOrganReportedPersonType(int organReportedPersonType) {
        this.organReportedPersonType = organReportedPersonType;
    }

    public String getOrganReportedPersonName() {
        return organReportedPersonName;
    }

    public void setOrganReportedPersonName(String organReportedPersonName) {
        this.organReportedPersonName = organReportedPersonName;
    }

    public String getOrganReportedPersonTel() {
        return organReportedPersonTel;
    }

    public void setOrganReportedPersonTel(String organReportedPersonTel) {
        this.organReportedPersonTel = organReportedPersonTel;
    }

    public int getIsReporteds() {
        return isReporteds;
    }

    public void setIsReporteds(int isReporteds) {
        this.isReporteds = isReporteds;
    }

    public int getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(int isAccept) {
        this.isAccept = isAccept;
    }

    public int getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(int acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public String getAcceptUserName() {
        return acceptUserName;
    }

    public void setAcceptUserName(String acceptUserName) {
        this.acceptUserName = acceptUserName;
    }

    public String getDenialReason() {
        return denialReason;
    }

    public void setDenialReason(String denialReason) {
        this.denialReason = denialReason;
    }

    public int getDealDepart() {
        return dealDepart;
    }

    public void setDealDepart(int dealDepart) {
        this.dealDepart = dealDepart;
    }

    public int getDealTime() {
        return dealTime;
    }

    public void setDealTime(int dealTime) {
        this.dealTime = dealTime;
    }

    public int getDealTimeRemark() {
        return dealTimeRemark;
    }

    public void setDealTimeRemark(int dealTimeRemark) {
        this.dealTimeRemark = dealTimeRemark;
    }

    public int getIsAdmit() {
        return isAdmit;
    }

    public void setIsAdmit(int isAdmit) {
        this.isAdmit = isAdmit;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public int getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }

    public int getFlowRunId() {
		return flowRunId;
	}

	public void setFlowRunId(int flowRunId) {
		this.flowRunId = flowRunId;
	}

	public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

	public Date getAcceptTime() {
		return AcceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		AcceptTime = acceptTime;
	}

	public String getLeaderContent() {
		return leaderContent;
	}

	public void setLeaderContent(String leaderContent) {
		this.leaderContent = leaderContent;
	}

	public Date getLeaderTime() {
		return leaderTime;
	}

	public void setLeaderTime(Date leaderTime) {
		this.leaderTime = leaderTime;
	}

}
