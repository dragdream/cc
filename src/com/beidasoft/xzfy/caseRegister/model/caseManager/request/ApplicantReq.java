package com.beidasoft.xzfy.caseRegister.model.caseManager.request;

import java.util.List;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Agent;

public class ApplicantReq implements Request {

    private String operationType; // 操作类型 "01"为登记/"02"为填报

    private String caseId; // 案件ID

    private List<FyApplicant> applicantList; // 申请人

    private List<FyApplicant> otherApplicantList; // 其他申请人

    private List<Agent> agentList; // 代理人

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

    public List<FyApplicant> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<FyApplicant> applicantList) {
        this.applicantList = applicantList;
    }

    public List<FyApplicant> getOtherApplicantList() {
        return otherApplicantList;
    }

    public void setOtherApplicantList(List<FyApplicant> otherApplicantList) {
        this.otherApplicantList = otherApplicantList;
    }

    public List<Agent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }
}
