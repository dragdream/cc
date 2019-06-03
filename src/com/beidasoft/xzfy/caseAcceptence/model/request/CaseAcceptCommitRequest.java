package com.beidasoft.xzfy.caseAcceptence.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class CaseAcceptCommitRequest implements Request{

	//案件ID
	private String caseId;
	
	//完结类型
	private String type;
	
	//第一承办人
	private String dealManFirstId;
	
	//第二承办人
	private String dealManSecondId;

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

	public String getDealManFirstId() {
		return dealManFirstId;
	}

	public void setDealManFirstId(String dealManFirstId) {
		this.dealManFirstId = dealManFirstId;
	}

	public String getDealManSecondId() {
		return dealManSecondId;
	}

	public void setDealManSecondId(String dealManSecondId) {
		this.dealManSecondId = dealManSecondId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
