package com.beidasoft.xzfy.organPerson.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class OrganPersonInfoRequest implements Request{

	private String personId;
	
	@Override
	public void validate() {
		//判空
		ValidateUtils.validateEmpty(personId);
		//特殊字符
		ValidateUtils.validateSpecialChar(personId);
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
}
