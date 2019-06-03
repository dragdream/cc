package com.beidasoft.xzfy.caseClose.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class CaseCloseCommitRequest implements Request{

	//案件ID
	private String caseIds;
	
	@Override
	public void validate() {
		
		//校验案件ID为空
		ValidateUtils.validateEmpty(caseIds);
		//校验案件ID特殊字符		
		ValidateUtils.validateSpecialChar(caseIds);
	}

	public String getCaseIds() {
		return caseIds;
	}

	public void setCaseIds(String caseIds) {
		this.caseIds = caseIds;
	}
}
