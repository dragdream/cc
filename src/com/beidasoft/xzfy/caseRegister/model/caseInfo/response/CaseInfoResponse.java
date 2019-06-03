package com.beidasoft.xzfy.caseRegister.model.caseInfo.response;

import java.util.List;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.AcceptInfo;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.CaseHandling;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.ClientInfo;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.TrialInfo;

/**
 * 案件信息详情
 * 
 * @author A
 * 
 */
public class CaseInfoResponse {

    // 案件ID
    private String caseId;

    // 当事人信息
    private ClientInfo clientInfo;

    // 复议事项
    private List<CaseHandling> caseHandling;

    // 立案审查
    private AcceptInfo acceptInfo;

    // 案件审理
    private TrialInfo trialInfo;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }



    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public List<CaseHandling> getCaseHandling() {
        return caseHandling;
    }

    public void setCaseHandling(List<CaseHandling> caseHandling) {
        this.caseHandling = caseHandling;
    }

    public AcceptInfo getAcceptInfo() {
        return acceptInfo;
    }

    public void setAcceptInfo(AcceptInfo acceptInfo) {
        this.acceptInfo = acceptInfo;
    }

    public TrialInfo getTrialInfo() {
        return trialInfo;
    }

    public void setTrialInfo(TrialInfo trialInfo) {
        this.trialInfo = trialInfo;
    }
}
