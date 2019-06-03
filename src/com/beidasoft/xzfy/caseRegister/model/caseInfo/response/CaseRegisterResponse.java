package com.beidasoft.xzfy.caseRegister.model.caseInfo.response;

import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.CaseHandling;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.ClientInfo;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Letter;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Reception;

/**
 * 登记信息详情
 * 
 * @author chumc
 * 
 */
public class CaseRegisterResponse {

    // 案件ID
    private String caseId;

    // 案件状态编码
    private String caseStatusCode;

    // 案件状态
    private String caseStatus;

    // 复议申请方式代码
    private String applicationTypeCode;

    // 复议申请方式
    private String applicationType;

    // 来件信息
    private Letter letter;

    // 接待信息
    private Reception reception;

    // 当事人信息
    private ClientInfo clientInfo;

    // 复议事项
    private CaseHandling caseHandling;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
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

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public Reception getReception() {
        return reception;
    }

    public void setReception(Reception reception) {
        this.reception = reception;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public CaseHandling getCaseHandling() {
        return caseHandling;
    }

    public void setCaseHandling(CaseHandling caseHandling) {
        this.caseHandling = caseHandling;
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

}
