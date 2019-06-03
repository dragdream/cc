package com.beidasoft.xzfy.caseRegister.model.registrationCompleted.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class CaseRegisteredReq implements Request {

	private String caseId;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}
