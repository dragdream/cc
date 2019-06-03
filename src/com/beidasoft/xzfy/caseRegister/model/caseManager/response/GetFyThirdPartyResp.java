package com.beidasoft.xzfy.caseRegister.model.caseManager.response;

import java.util.List;

import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;

public class GetFyThirdPartyResp {
	
	private List<FyThirdParty> fyThirdPartyList;

	public List<FyThirdParty> getFyThirdPartyList() {
		return fyThirdPartyList;
	}

	public void setFyThirdPartyList(List<FyThirdParty> fyThirdPartyList) {
		this.fyThirdPartyList = fyThirdPartyList;
	}

}
