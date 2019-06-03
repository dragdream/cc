package com.beidasoft.xzfy.caseClose.model.request;

import java.util.List;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.caseRegister.bean.FyMaterial;
import com.beidasoft.xzfy.utils.ValidateUtils;

public class CaseCloseSaveRequest implements Request{

	private  String caseId;
	
	//案件材料信息
	private List<FyMaterial> listFyMaterial;
	
	@Override
	public void validate() {
		//非空
		ValidateUtils.validateEmpty(caseId);
		//特殊字符
		ValidateUtils.validateSpecialChar(caseId);
	}

	public List<FyMaterial> getListFyMaterial() {
		return listFyMaterial;
	}
	public void setListFyMaterial(List<FyMaterial> listFyMaterial) {
		this.listFyMaterial = listFyMaterial;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

}
