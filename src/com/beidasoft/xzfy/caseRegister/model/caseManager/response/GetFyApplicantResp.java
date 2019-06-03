package com.beidasoft.xzfy.caseRegister.model.caseManager.response;

import java.util.List;
import com.beidasoft.xzfy.caseRegister.bean.FyAgent;
import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;

public class GetFyApplicantResp {

    private List<FyApplicant> applicantList;

    private List<FyApplicant> otherApplicantList;

    private List<FyAgent> agentList;// 代理人

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

    public List<FyAgent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<FyAgent> agentList) {
        this.agentList = agentList;
    }

}
