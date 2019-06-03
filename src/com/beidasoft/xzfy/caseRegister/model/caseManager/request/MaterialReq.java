package com.beidasoft.xzfy.caseRegister.model.caseManager.request;

import java.util.List;
import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.caseRegister.bean.FyMaterial;

public class MaterialReq implements Request {

    private String caseId;

    private String caseTypeCode;

    private List<FyMaterial> listFyMaterial;

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseTypeCode() {
        return caseTypeCode;
    }

    public void setCaseTypeCode(String caseTypeCode) {
        this.caseTypeCode = caseTypeCode;
    }

    public List<FyMaterial> getListFyMaterial() {
        return listFyMaterial;
    }

    public void setListFyMaterial(List<FyMaterial> listFyMaterial) {
        this.listFyMaterial = listFyMaterial;
    }

}
