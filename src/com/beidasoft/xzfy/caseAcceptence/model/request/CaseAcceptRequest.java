package com.beidasoft.xzfy.caseAcceptence.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class CaseAcceptRequest implements Request{

	//案件ID
	private String caseId;
	
	//受理类型
	private String type;
	
	//受理时间
	private String acceptTime;
	
	//受理原因
	private String reason;
	
	//备注
	private String remark;
	
	@Override
	public void validate() {
		
		//校验案件ID是否为空
		ValidateUtils.validateEmpty(caseId);
		
		//校验案件是否存在特殊字符
		ValidateUtils.validateSpecialChar(caseId);
		
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

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
