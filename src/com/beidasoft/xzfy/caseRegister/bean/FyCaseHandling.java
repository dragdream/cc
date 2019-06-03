package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 案件信息
 * 
 * @author chumc
 * 
 */
@Entity
@Table(name = "FY_CASE_HANDLING")
public class FyCaseHandling {

    @OneToOne
    @JoinColumn(name = "CASE_ID")
    private FyLetter fyLetter;

    @OneToOne
    @JoinColumn(name = "CASE_ID")
    private FyReception fyReception;

    @Id
    @Column(name = "CASE_ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String caseId; // 案件ID

    @Column(name = "CASE_NUM")
    private String caseNum; // 案件编号

    @Column(name = "APPLICATION_TYPE_CODE")
    private String applicationTypeCode; // 复议申请方式代码

    @Column(name = "APPLICATION_TYPE")
    private String applicationType; // 复议申请方式

    @Column(name = "APPLICATION_DATE")
    private String applicationDate; // 收到复议申请日期

    @Column(name = "APPLICANTS_NUM")
    private int applicationsNum; // 复议申请人数

    @Column(name = "CATEGORY_CODE")
    private String categoryCode; // 行政管理类别代码

    @Column(name = "CATEGORY")
    private String category; // 行政管理类别

    @Column(name = "APPLICATION_ITEM_CODE")
    private String applicationItemCode; // 申请行政复议事项代码

    @Column(name = "APPLICATION_ITEM")
    private String applicationItem; // 申请行政复议事项

    @Column(name = "IS_NONFEASANCE")
    private int isNonfeasance; // 是否行政不作为

    @Column(name = "SPECIFIC_ADMINISTRATIVE_NAME")
    private String specificAdministrativeName; // 具体行政行为名称

    @Column(name = "SPECIFIC_ADMINISTRATIVE_DETAIL")
    private String specificAdministrativeDetail; // 具体行政行为

    @Column(name = "SPECIFIC_ADMINISTRATIVE_NO")
    private String specificAdministrativeNo; // 具体行政行为文号

    @Column(name = "SPECIFIC_ADMINISTRATIVE_DATE")
    private String specificAdministrativeDate; // 具体行政行为作出时间

    @Column(name = "REQUEST_FOR_RECONSIDERATION")
    private String requestForReconsideration; // 复议请求

    @Column(name = "FACTS_AND_REASONS")
    private String factsAndReasons; // 事实和理由

    @Column(name = "IS_REVIEW")
    private int isReview; // 是否复议前置

    @Column(name = "IS_COMPENSATION")
    private int isCompensation; // 是否申请赔偿

    @Column(name = "COMPENSATION_TYPE_CODE")
    private String compensationTypeCode; // 赔偿类型代码

    @Column(name = "COMPENSATION_TYPE")
    private String compensationType; // 赔偿类型

    @Column(name = "IS_DOCUMENT_REVIEW")
    private String isDocumentReview; // 是否申请规范性文件审查

    @Column(name = "DOCUMENT_REVIEW_NAME")
    private String documentReviewName; // 规范性文件名称

    @Column(name = "IS_HOLD_HEARING")
    private int isHoldHearing; // 是否申请听证会

    @Column(name = "IS_DELAY")
    private int isDelay; // 是否延期

    @Column(name = "DELAY_DAYS")
    private int delayDays; // 延期天数

    @Column(name = "RESPONDENT_TYPE_CODE")
    private String respondentTypeCode; // 被申请人类型代码

    @Column(name = "RESPONDENT_TYPE")
    private String respondentType; // 被申请人类型

    @Column(name = "AREA_CODE")
    private String areaCode; // 被申请人行政区划

    @Column(name = "RESPONDENT_NAME")
    private String respondentName; // 被申请人名称

    @Column(name = "DEAL_MAN1_ID")
    private String dealMan1Id; // 第一承办人ID

    @Column(name = "DEAL_MAN2_ID")
    private String dealMan2Id; // 第二承办人ID

    @Column(name = "DEAL_MAN1_NAME")
    private String dealMan1Name; // 第一承办人姓名

    @Column(name = "DEAL_MAN2_NAME")
    private String dealMan2Name; // 第二承办人姓名

    @Column(name = "ORGAN_ID")
    private String organId; // 复议机关ID

    @Column(name = "IS_SPOT_INVEST")
    private int isSpotInvest; // 是否实地查证

    @Column(name = "CASE_STATUS_CODE")
    private String caseStatusCode; // 案件状态代码

    @Column(name = "CASE_STATUS")
    private String caseStatus; // 案件状态

    @Column(name = "CASE_SUB_STATUS_CODE")
    private String caseSubStatusCode; // 子案件状态代码

    @Column(name = "CASE_SUB_STATUS")
    private String caseSubStatus; // 子案件状态

    @Column(name = "CASE_SUB_BREAK_REASON")
    private String caseSubBreakReason; // 子案件中止原因

    @Column(name = "CASE_SUB_BREAK_TIME")
    private String caseSubBreakTime; // 子案件中止时间

    @Column(name = "CASE_SUB_RESTORE_REASON")
    private String caseSubRestoreReason; // 子案件恢复原因

    @Column(name = "CASE_SUB_EXTENSION_REASON")
    private String caseSubExtensionReason; // 子案件延期原因

    @Column(name = "CASE_SUB_END_REASON")
    private String caseSubEndReason; // 子案件结案原因

    @Column(name = "SETTLE_TYPE_CODE")
    private String settleTypeCode; // 结案类型代码

    @Column(name = "SETTLE_TYPE")
    private String settleType; // 结案类型

    @Column(name = "SETTLE_DATE")
    private String settleDate; // 结案时间

    @Column(name = "SETTLE_DOCUMENT_NUM")
    private String settleDocumentNum; // 结案法律文书文号

    @Column(name = "IS_IMPLEMENT")
    private String isImplement; // 行政复议意见书是否落实

    @Column(name = "IS_SHARE")
    private int isShare; // 是否共享

    @Column(name = "IS_PUBLIC")
    private int isPublic; // 是否公开

    @Column(name = "RECEIVED_PUNISH_DATE")
    private String receivedPunishDate; // 收到处罚决定书的日期

    @Column(name = "RECEIVED_SPECIFIC_WAY")
    private String receivedSpecificWay; // 得知该具体行为途径

    @Column(name = "IS_MERGER_CASE")
    private String isMergerCase; // 是否并案

    @Column(name = "MERGER_CASE_ID")
    private String mergerCaseId; // 并案Id(子案件caseId)

    @Column(name = "MERGER_CASE_NUM")
    private String mergerCaseNum; // 案件并案数量

    @Column(name = "CREATED_USER_ID")
    private String createdUserId; // 创建人Id

    @Column(name = "CREATED_USER")
    private String createdUser; // 创建人

    @Column(name = "CREATED_TIME")
    private String createTime; // 创建时间

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId; // 修改人ID

    @Column(name = "MODIFIED_USER")
    private String modifiedUser; // 修改人

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime; // 修改时间

    @Column(name = "IS_DELETE")
    private int isDelete; // 是否删除

    @Column(name = "CASE_SUB_EXTENSION_START_TIME")
    private String caseSubRestoreStartTime; // 延期开始时间

    @Column(name = "CASE_SUB_EXTENSION_EBD_TIME")
    private String caseSubRestoreEndTime; // 延期结束时间

    @Column(name = "CASE_SUB_RESTORE_TIME")
    private String caseSubRestoreTime; // 案件恢复日期

    @Column(name = "DISCOVERED_PROBLEMS")
    private String discoveredProblems; // 案件审理中发现的问题

    @Column(name = "PROPOSAL_ADVISE")
    private String proposalAdvise; // 案件审理给出的建议

    @Column(name = "COMPENSATION_AMOUNT")
    private String compensationAmount; // 赔偿金额

    @Column(name = "IS_APPROVAL")
    private int isApproval; // 是否审批

    @Column(name = "NONFEASANCE_ITEM_CODE")
    private String nonfeasanceItemCode; // 具体行政不作为code

    @Column(name = "NONFEASANCE_ITEM")
    private String nonfeasanceItem; // 具体行政不作为事项

    @Column(name = "NONFEASANCE_DATE")
    private String nonfeasanceDate; // 具体行政不作为时间
    
    public String getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(String compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public int getIsApproval() {
        return isApproval;
    }

    public void setIsApproval(int isApproval) {
        this.isApproval = isApproval;
    }

    public String getDiscoveredProblems() {
        return discoveredProblems;
    }

    public void setDiscoveredProblems(String discoveredProblems) {
        this.discoveredProblems = discoveredProblems;
    }

    public String getProposalAdvise() {
        return proposalAdvise;
    }

    public void setProposalAdvise(String proposalAdvise) {
        this.proposalAdvise = proposalAdvise;
    }

    public String getCaseSubRestoreStartTime() {
        return caseSubRestoreStartTime;
    }

    public void setCaseSubRestoreStartTime(String caseSubRestoreStartTime) {
        this.caseSubRestoreStartTime = caseSubRestoreStartTime;
    }

    public String getCaseSubRestoreEndTime() {
        return caseSubRestoreEndTime;
    }

    public void setCaseSubRestoreEndTime(String caseSubRestoreEndTime) {
        this.caseSubRestoreEndTime = caseSubRestoreEndTime;
    }

    public String getCaseSubRestoreTime() {
        return caseSubRestoreTime;
    }

    public void setCaseSubRestoreTime(String caseSubRestoreTime) {
        this.caseSubRestoreTime = caseSubRestoreTime;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public String getApplicationTypeCode() {
        return applicationTypeCode;
    }

    public void setApplicationTypeCode(String applicationTypeCode) {
        this.applicationTypeCode = applicationTypeCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public int getApplicationsNum() {
        return applicationsNum;
    }

    public void setApplicationsNum(int applicationsNum) {
        this.applicationsNum = applicationsNum;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getApplicationItemCode() {
        return applicationItemCode;
    }

    public void setApplicationItemCode(String applicationItemCode) {
        this.applicationItemCode = applicationItemCode;
    }

    public String getApplicationItem() {
        return applicationItem;
    }

    public void setApplicationItem(String applicationItem) {
        this.applicationItem = applicationItem;
    }

    public int getIsNonfeasance() {
        return isNonfeasance;
    }

    public void setIsNonfeasance(int isNonfeasance) {
        this.isNonfeasance = isNonfeasance;
    }

    public String getSpecificAdministrativeName() {
        return specificAdministrativeName;
    }

    public void setSpecificAdministrativeName(String specificAdministrativeName) {
        this.specificAdministrativeName = specificAdministrativeName;
    }

    public String getSpecificAdministrativeDetail() {
        return specificAdministrativeDetail;
    }

    public void setSpecificAdministrativeDetail(String specificAdministrativeDetail) {
        this.specificAdministrativeDetail = specificAdministrativeDetail;
    }

    public String getMergerCaseNum() {
        return mergerCaseNum;
    }

    public void setMergerCaseNum(String mergerCaseNum) {
        this.mergerCaseNum = mergerCaseNum;
    }

    public String getSpecificAdministrativeNo() {
        return specificAdministrativeNo;
    }

    public void setSpecificAdministrativeNo(String specificAdministrativeNo) {
        this.specificAdministrativeNo = specificAdministrativeNo;
    }

    public String getSpecificAdministrativeDate() {
        return specificAdministrativeDate;
    }

    public void setSpecificAdministrativeDate(String specificAdministrativeDate) {
        this.specificAdministrativeDate = specificAdministrativeDate;
    }

    public String getFactsAndReasons() {
        return factsAndReasons;
    }

    public void setFactsAndReasons(String factsAndReasons) {
        this.factsAndReasons = factsAndReasons;
    }

    public int getIsReview() {
        return isReview;
    }

    public void setIsReview(int isReview) {
        this.isReview = isReview;
    }

    public int getIsCompensation() {
        return isCompensation;
    }

    public void setIsCompensation(int isCompensation) {
        this.isCompensation = isCompensation;
    }

    public String getCompensationTypeCode() {
        return compensationTypeCode;
    }

    public void setCompensationTypeCode(String compensationTypeCode) {
        this.compensationTypeCode = compensationTypeCode;
    }

    public String getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(String compensationType) {
        this.compensationType = compensationType;
    }

    public String getIsDocumentReview() {
        return isDocumentReview;
    }

    public void setIsDocumentReview(String isDocumentReview) {
        this.isDocumentReview = isDocumentReview;
    }

    public String getDocumentReviewName() {
        return documentReviewName;
    }

    public void setDocumentReviewName(String documentReviewName) {
        this.documentReviewName = documentReviewName;
    }

    public int getIsHoldHearing() {
        return isHoldHearing;
    }

    public void setIsHoldHearing(int isHoldHearing) {
        this.isHoldHearing = isHoldHearing;
    }

    public int getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(int isDelay) {
        this.isDelay = isDelay;
    }

    public int getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(int delayDays) {
        this.delayDays = delayDays;
    }

    public String getRespondentTypeCode() {
        return respondentTypeCode;
    }

    public void setRespondentTypeCode(String respondentTypeCode) {
        this.respondentTypeCode = respondentTypeCode;
    }

    public String getRespondentType() {
        return respondentType;
    }

    public void setRespondentType(String respondentType) {
        this.respondentType = respondentType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public String getDealMan1Id() {
        return dealMan1Id;
    }

    public void setDealMan1Id(String dealMan1Id) {
        this.dealMan1Id = dealMan1Id;
    }

    public String getDealMan2Id() {
        return dealMan2Id;
    }

    public void setDealMan2Id(String dealMan2Id) {
        this.dealMan2Id = dealMan2Id;
    }

    public String getDealMan1Name() {
        return dealMan1Name;
    }

    public void setDealMan1Name(String dealMan1Name) {
        this.dealMan1Name = dealMan1Name;
    }

    public String getDealMan2Name() {
        return dealMan2Name;
    }

    public void setDealMan2Name(String dealMan2Name) {
        this.dealMan2Name = dealMan2Name;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public int getIsSpotInvest() {
        return isSpotInvest;
    }

    public void setIsSpotInvest(int isSpotInvest) {
        this.isSpotInvest = isSpotInvest;
    }

    public String getCaseStatusCode() {
        return caseStatusCode;
    }

    public void setCaseStatusCode(String caseStatusCode) {
        this.caseStatusCode = caseStatusCode;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseSubStatusCode() {
        return caseSubStatusCode;
    }

    public void setCaseSubStatusCode(String caseSubStatusCode) {
        this.caseSubStatusCode = caseSubStatusCode;
    }

    public String getCaseSubStatus() {
        return caseSubStatus;
    }

    public void setCaseSubStatus(String caseSubStatus) {
        this.caseSubStatus = caseSubStatus;
    }

    public String getCaseSubBreakReason() {
        return caseSubBreakReason;
    }

    public void setCaseSubBreakReason(String caseSubBreakReason) {
        this.caseSubBreakReason = caseSubBreakReason;
    }

    public String getCaseSubRestoreReason() {
        return caseSubRestoreReason;
    }

    public void setCaseSubRestoreReason(String caseSubRestoreReason) {
        this.caseSubRestoreReason = caseSubRestoreReason;
    }

    public String getCaseSubExtensionReason() {
        return caseSubExtensionReason;
    }

    public void setCaseSubExtensionReason(String caseSubExtensionReason) {
        this.caseSubExtensionReason = caseSubExtensionReason;
    }

    public String getCaseSubEndReason() {
        return caseSubEndReason;
    }

    public void setCaseSubEndReason(String caseSubEndReason) {
        this.caseSubEndReason = caseSubEndReason;
    }

    public String getSettleTypeCode() {
        return settleTypeCode;
    }

    public void setSettleTypeCode(String settleTypeCode) {
        this.settleTypeCode = settleTypeCode;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettleDocumentNum() {
        return settleDocumentNum;
    }

    public void setSettleDocumentNum(String settleDocumentNum) {
        this.settleDocumentNum = settleDocumentNum;
    }

    public String getIsImplement() {
        return isImplement;
    }

    public void setIsImplement(String isImplement) {
        this.isImplement = isImplement;
    }

    public int getIsShare() {
        return isShare;
    }

    public void setIsShare(int isShare) {
        this.isShare = isShare;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getReceivedPunishDate() {
        return receivedPunishDate;
    }

    public void setReceivedPunishDate(String receivedPunishDate) {
        this.receivedPunishDate = receivedPunishDate;
    }

    public String getReceivedSpecificWay() {
        return receivedSpecificWay;
    }

    public void setReceivedSpecificWay(String receivedSpecificWay) {
        this.receivedSpecificWay = receivedSpecificWay;
    }

    public String getIsMergerCase() {
        return isMergerCase;
    }

    public void setIsMergerCase(String isMergerCase) {
        this.isMergerCase = isMergerCase;
    }

    public String getMergerCaseId() {
        return mergerCaseId;
    }

    public void setMergerCaseId(String mergerCaseId) {
        this.mergerCaseId = mergerCaseId;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getRequestForReconsideration() {
        return requestForReconsideration;
    }

    public void setRequestForReconsideration(String requestForReconsideration) {
        this.requestForReconsideration = requestForReconsideration;
    }

    public String getCaseSubBreakTime() {
        return caseSubBreakTime;
    }

    public void setCaseSubBreakTime(String caseSubBreakTime) {
        this.caseSubBreakTime = caseSubBreakTime;
    }

    public FyLetter getFyLetter() {
        return fyLetter;
    }

    public void setFyLetter(FyLetter fyLetter) {
        this.fyLetter = fyLetter;
    }

    public FyReception getFyReception() {
        return fyReception;
    }

    public void setFyReception(FyReception fyReception) {
        this.fyReception = fyReception;
    }

	public String getNonfeasanceItemCode() {
		return nonfeasanceItemCode;
	}

	public void setNonfeasanceItemCode(String nonfeasanceItemCode) {
		this.nonfeasanceItemCode = nonfeasanceItemCode;
	}

	public String getNonfeasanceItem() {
		return nonfeasanceItem;
	}

	public void setNonfeasanceItem(String nonfeasanceItem) {
		this.nonfeasanceItem = nonfeasanceItem;
	}

	public String getNonfeasanceDate() {
		return nonfeasanceDate;
	}

	public void setNonfeasanceDate(String nonfeasanceDate) {
		this.nonfeasanceDate = nonfeasanceDate;
	}

}
