package com.beidasoft.xzfy.caseRegister.model.caseManager.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.caseRegister.model.entity.CaseHandReq;

public class ReviewMattersReq implements Request {

    private CaseHandReq caseHandling;

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }

	public CaseHandReq getCaseHandling() {
		return caseHandling;
	}

	public void setCaseHandling(CaseHandReq caseHandling) {
		this.caseHandling = caseHandling;
	}


}
