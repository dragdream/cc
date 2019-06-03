package com.beidasoft.xzfy.organ.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class OrganAddRequest implements Request{

	private String organIds;
	
	private String organNames;
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	public String getOrganIds() {
		return organIds;
	}

	public void setOrganIds(String organIds) {
		this.organIds = organIds;
	}

	public String getOrganNames() {
		return organNames;
	}

	public void setOrganNames(String organNames) {
		this.organNames = organNames;
	}

}
