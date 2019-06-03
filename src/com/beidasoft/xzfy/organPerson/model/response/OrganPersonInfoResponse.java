package com.beidasoft.xzfy.organPerson.model.response;


import com.beidasoft.xzfy.base.model.response.Response;
import com.beidasoft.xzfy.organPerson.bean.OrganPersonInfo;

public class OrganPersonInfoResponse extends Response{
	
	//组织机关列表
	private OrganPersonInfo person;
		
	public OrganPersonInfoResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

	public OrganPersonInfo getPerson() {
		return person;
	}

	public void setPerson(OrganPersonInfo person) {
		this.person = person;
	}
	
}
