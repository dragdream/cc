package com.beidasoft.xzfy.caseRegister.model.caseManager.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Reception;

public class ReceptionReq implements Request {

    private Reception reception;

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }

    public Reception getReception() {
        return reception;
    }

    public void setReception(Reception reception) {
        this.reception = reception;
    }

}
