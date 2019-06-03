package com.beidasoft.xzfy.caseAcceptence.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class CaseMergeRequest implements Request{

	//主案件Id
	private String caseId;
	
	//合并案件ID
	private String mergeCaseIds;
	
	@Override
	public void validate() {
		
		//判空
		ValidateUtils.validateEmpty(caseId);
		//特殊字符
		ValidateUtils.validateSpecialChar(caseId);
		
		//判空
		ValidateUtils.validateEmpty(mergeCaseIds);
		//特殊字符
		ValidateUtils.validateSpecialChar(mergeCaseIds);
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getMergeCaseIds() {
		return mergeCaseIds;
	}

	public void setMergeCaseIds(String mergeCaseIds) {
		this.mergeCaseIds = mergeCaseIds;
	}

}
