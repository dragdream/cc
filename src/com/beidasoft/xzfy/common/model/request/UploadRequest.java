package com.beidasoft.xzfy.common.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class UploadRequest implements Request{

	private String type;
	
	private String caseId;
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

}
