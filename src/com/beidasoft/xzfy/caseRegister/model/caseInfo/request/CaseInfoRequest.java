package com.beidasoft.xzfy.caseRegister.model.caseInfo.request;

import com.beidasoft.xzfy.base.model.request.Request;

/**
 * 案件详情请求
 * @author A
 *
 */
public class CaseInfoRequest implements Request {

	//案件ID
	private String caseId;
	//案件详情类型
	private String type;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
