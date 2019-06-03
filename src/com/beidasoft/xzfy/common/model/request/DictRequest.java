package com.beidasoft.xzfy.common.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class DictRequest implements Request{

	private String type;
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
