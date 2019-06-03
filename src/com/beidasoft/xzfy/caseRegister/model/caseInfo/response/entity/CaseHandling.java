package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

/**
 * 复议事项
 * 
 * @author cc
 * 
 */
public class CaseHandling {

    private String operationType; // 操作类型 "01"为登记/"02"为填报
    private String caseId; // 案件ID
    private String applicationDate; // 收到复议申请日期
    private String categoryCode; // 行政管理类别
    private String category; // 行政管理类别
    private String applicationItemCode; // 申请行政复议事项
    private String applicationItem; // 申请行政复议事项

    private int isNonfeasance; // 是否行政不作为

    private String specificAdministrativeName; // 具体行政行为名称
    private String specificAdministrativeNo; // 具体行政行为文号
    private String specificAdministrativeDate; // 具体行政行为作出时间
    private String receivedPunishDate; // 收到处罚决定书的日期
    private String receivedSpecificWay; // 得知该具体行为途径

    private String nonfeasanceItemCode; // 行政不作为事项编码
    private String nonfeasanceItem; // 行政不作为事项
    private String nonfeasanceDate; // 履行职责日期

    private String specificAdministrativeDetail; // 具体行政行为
    private String requestForReconsideration; // 复议请求

    private int isReview; // 是否复议前置
    private int isHoldHearing; // 是否申请听证会
    private String isDocumentReview; // 是否申请规范性文件审查
    private String documentReviewName; // 规范性文件名称

    private int isCompensation; // 是否申请赔偿
    private String compensationTypeCode; // 赔偿类型代码
    private String compensationType; // 赔偿类型
    private String compensationAmount; // 赔偿金额

    private String caseStatusCode; // 案件状态代码
    private String caseStatus; // 案件状态
    private String caseSubStatusCode; // 子案件状态代码
    private String caseSubStatus; // 子案件状态

    public String getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(String compensationType) {
        this.compensationType = compensationType;
    }

    public String getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(String compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public String getCaseStatusCode() {
        return caseStatusCode;
    }

    public void setCaseStatusCode(String caseStatusCode) {
        this.caseStatusCode = caseStatusCode;
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

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getDocumentReviewName() {
        return documentReviewName;
    }

    public void setDocumentReviewName(String documentReviewName) {
        this.documentReviewName = documentReviewName;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getApplicationItem() {
        return applicationItem;
    }

    public void setApplicationItem(String applicationItem) {
        this.applicationItem = applicationItem;
    }

    public String getSpecificAdministrativeName() {
        return specificAdministrativeName;
    }

    public void setSpecificAdministrativeName(String specificAdministrativeName) {
        this.specificAdministrativeName = specificAdministrativeName;
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

    public int getIsNonfeasance() {
        return isNonfeasance;
    }

    public void setIsNonfeasance(int isNonfeasance) {
        this.isNonfeasance = isNonfeasance;
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

    public String getSpecificAdministrativeDetail() {
        return specificAdministrativeDetail;
    }

    public void setSpecificAdministrativeDetail(String specificAdministrativeDetail) {
        this.specificAdministrativeDetail = specificAdministrativeDetail;
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

    public int getIsHoldHearing() {
        return isHoldHearing;
    }

    public void setIsHoldHearing(int isHoldHearing) {
        this.isHoldHearing = isHoldHearing;
    }

    public String getIsDocumentReview() {
        return isDocumentReview;
    }

    public void setIsDocumentReview(String isDocumentReview) {
        this.isDocumentReview = isDocumentReview;
    }

    public String getRequestForReconsideration() {
        return requestForReconsideration;
    }

    public void setRequestForReconsideration(String requestForReconsideration) {
        this.requestForReconsideration = requestForReconsideration;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getApplicationItemCode() {
        return applicationItemCode;
    }

    public void setApplicationItemCode(String applicationItemCode) {
        this.applicationItemCode = applicationItemCode;
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
