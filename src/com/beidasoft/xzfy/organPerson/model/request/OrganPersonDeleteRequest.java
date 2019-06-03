package com.beidasoft.xzfy.organPerson.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class OrganPersonDeleteRequest implements Request{

	//ids
	private String personIds;
	
	@Override
	public void validate() {
		
		//判空
		ValidateUtils.validateEmpty(personIds);
		//特殊字符
		ValidateUtils.validateSpecialChar(personIds);
	}

	public String getPersonIds() {
		return personIds;
	}

	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}

}
