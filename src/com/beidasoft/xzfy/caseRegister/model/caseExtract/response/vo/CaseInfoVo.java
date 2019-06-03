package com.beidasoft.xzfy.caseRegister.model.caseExtract.response.vo;

import java.util.List;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseProcessLogInfo;
import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;

/**
 * 案件信息
 * 
 * @author cc
 * 
 */
public class CaseInfoVo {

    // 当事人信息
    private List<FyApplicant> fyApplicant;
    private List<FyRespondent> fyRespondent;
    private List<FyThirdParty> fyThirdParty;
    // 复议事项
    private List<FyCaseHandling> fyCaseHandling;

    // 流程表
    private List<CaseProcessLogInfo> caseProcessLogInfo;

    // 立案审查


    // 案件审理

    public List<FyApplicant> getFyApplicant() {
        return fyApplicant;
    }

    public void setFyApplicant(List<FyApplicant> fyApplicant) {
        this.fyApplicant = fyApplicant;
    }

    public List<FyRespondent> getFyRespondent() {
        return fyRespondent;
    }

    public void setFyRespondent(List<FyRespondent> fyRespondent) {
        this.fyRespondent = fyRespondent;
    }

    public List<FyThirdParty> getFyThirdParty() {
        return fyThirdParty;
    }

    public void setFyThirdParty(List<FyThirdParty> fyThirdParty) {
        this.fyThirdParty = fyThirdParty;
    }

    public List<FyCaseHandling> getFyCaseHandling() {
        return fyCaseHandling;
    }

    public void setFyCaseHandling(List<FyCaseHandling> fyCaseHandling) {
        this.fyCaseHandling = fyCaseHandling;
    }

    public List<CaseProcessLogInfo> getCaseProcessLogInfo() {
        return caseProcessLogInfo;
    }

    public void setCaseProcessLogInfo(List<CaseProcessLogInfo> caseProcessLogInfo) {
        this.caseProcessLogInfo = caseProcessLogInfo;
    }

}
