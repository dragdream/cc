package com.beidasoft.xzfy.caseRegister.model.caseInfo.response;

import java.util.List;

import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Applicant;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Respondent;
import com.beidasoft.xzfy.caseRegister.model.entity.Material;

/**
 * 案件受理详情
 * 
 * @author A
 * 
 */
public class CaseAcceptResponse {

    // 案件ID
    private String caseId;
    // 申请人列表
    private List<Applicant> applicantList;
    // 被申请人信息
    private List<Respondent> respondentList; // 被申请人信息
    // 行政复议请求
    private String requestForReconsideration; // 复议请求
    // 事实与原因
    private String factsAndReasons; // 复议请求(事实和理由)
    // 申请材料列表
    private List<Material> applyMaterialList;
    // 立案材料列表
    private List<Material> filingMaterialList;
    // 审理材料列表
    private List<Material> hearingMaterialList;

    private String caseSubStatusCode; // 子案件状态代码

    private String caseSubStatus; // 子案件状态

    private int isApproval; // 是否审批

    // 受理时间
    private String acceptTime;
    // 受理结果
    private String acceptResult;
    // 理由原因
    private String reason;
    // 备注
    private String remark;

    public int getIsApproval() {
        return isApproval;
    }

    public void setIsApproval(int isApproval) {
        this.isApproval = isApproval;
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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public List<Applicant> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<Applicant> applicantList) {
        this.applicantList = applicantList;
    }

    public List<Respondent> getRespondentList() {
        return respondentList;
    }

    public void setRespondentList(List<Respondent> respondentList) {
        this.respondentList = respondentList;
    }

    public String getRequestForReconsideration() {
        return requestForReconsideration;
    }

    public void setRequestForReconsideration(String requestForReconsideration) {
        this.requestForReconsideration = requestForReconsideration;
    }

    public String getFactsAndReasons() {
        return factsAndReasons;
    }

    public void setFactsAndReasons(String factsAndReasons) {
        this.factsAndReasons = factsAndReasons;
    }

    public List<Material> getApplyMaterialList() {
        return applyMaterialList;
    }

    public void setApplyMaterialList(List<Material> applyMaterialList) {
        this.applyMaterialList = applyMaterialList;
    }

    public List<Material> getFilingMaterialList() {
        return filingMaterialList;
    }

    public void setFilingMaterialList(List<Material> filingMaterialList) {
        this.filingMaterialList = filingMaterialList;
    }

    public List<Material> getHearingMaterialList() {
        return hearingMaterialList;
    }

    public void setHearingMaterialList(List<Material> hearingMaterialList) {
        this.hearingMaterialList = hearingMaterialList;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptResult() {
        return acceptResult;
    }

    public void setAcceptResult(String acceptResult) {
        this.acceptResult = acceptResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
