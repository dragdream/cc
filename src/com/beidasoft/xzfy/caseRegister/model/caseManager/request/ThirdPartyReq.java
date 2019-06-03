package com.beidasoft.xzfy.caseRegister.model.caseManager.request;

import java.util.List;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Agent;

public class ThirdPartyReq implements Request {

    private String operationType; // 操作类型 "01"为登记/"02"为填报

    private String caseId; // 案件ID

    private List<FyThirdParty> thirdPartyList; // 第三人

    private List<Agent> agentList; // 第三人代理人

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

    public List<FyThirdParty> getThirdPartyList() {
        return thirdPartyList;
    }

    public void setThirdPartyList(List<FyThirdParty> thirdPartyList) {
        this.thirdPartyList = thirdPartyList;
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
