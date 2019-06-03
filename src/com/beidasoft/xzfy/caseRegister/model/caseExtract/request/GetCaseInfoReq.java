package com.beidasoft.xzfy.caseRegister.model.caseExtract.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class GetCaseInfoReq implements Request {

  private String caseId;

  private String type;

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

  @Override
  public void validate() {
    // TODO Auto-generated method stub

  }

}
