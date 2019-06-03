package com.beidasoft.xzfy.caseRegister.model.caseManager.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class GetCaseInfoReq implements Request {

    private String caseId;

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
