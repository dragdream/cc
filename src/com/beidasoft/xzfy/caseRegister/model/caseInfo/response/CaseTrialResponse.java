package com.beidasoft.xzfy.caseRegister.model.caseInfo.response;

import java.util.List;

import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Applicant;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Respondent;
import com.beidasoft.xzfy.caseRegister.model.entity.Material;

/**
 * 案件审理详情
 * 
 * @author A
 * 
 */
public class CaseTrialResponse {

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
    // 结案类型
    private String trialType;
    // 理由原因
    private String reason;
    // 备注
    private String remark;
    // 是否申请赔偿
    private String isPay;
    // 赔偿类型
    private String payType;
    // 赔偿金额
    private String pay;

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

    public String getTrialType() {
        return trialType;
    }

    public void setTrialType(String trialType) {
        this.trialType = trialType;
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

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

}
