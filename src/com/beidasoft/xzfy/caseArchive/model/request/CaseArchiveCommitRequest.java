package com.beidasoft.xzfy.caseArchive.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class CaseArchiveCommitRequest implements Request{

	//案件ID
	private String caseId;
	
	//归档类型
	private String type;
	
	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	//归档管理用到前台传过来的url串
	private String urls;
	
	@Override
	public void validate() {
		
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
