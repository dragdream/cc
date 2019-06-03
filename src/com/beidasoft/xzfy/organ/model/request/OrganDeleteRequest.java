package com.beidasoft.xzfy.organ.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class OrganDeleteRequest implements Request {

	//批量删除或者单个删除
	private String orgIds;
	
	@Override
	public void validate() {
		
		//校验非空
		ValidateUtils.validateEmpty(orgIds);
		
		//校验特殊字符
		ValidateUtils.validateSpecialChar(orgIds);
	}
	public String getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

}
