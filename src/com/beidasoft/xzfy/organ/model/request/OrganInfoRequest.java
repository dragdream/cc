package com.beidasoft.xzfy.organ.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class OrganInfoRequest implements Request{

	private String deptId;
	
	@Override
	public void validate() {
		
		//校验非空
		ValidateUtils.validateEmpty(deptId);
		
		//校验特殊字符
		ValidateUtils.validateSpecialChar(deptId);
		
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
