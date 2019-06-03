package com.beidasoft.xzfy.caseRegister.model.caseExtract.response.vo;

import java.util.List;
import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyLetter;
import com.beidasoft.xzfy.caseRegister.bean.FyReception;
import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;

/**
 * 登记信息
 * 
 * @author chumc
 * 
 */
public class RegisterInfoVo {

    // 来件信息
    private List<FyLetter> fyLetter;
    // 接待信息
    private List<FyReception> fyReception;
    // 当事人信息
    private List<FyApplicant> fyApplicant;
    private List<FyRespondent> fyRespondent;
    private List<FyThirdParty> fyThirdParty;
    // 复议事项
    private List<FyCaseHandling> fyCaseHandling;

    public List<FyLetter> getFyLetter() {
        return fyLetter;
    }

    public void setFyLetter(List<FyLetter> fyLetter) {
        this.fyLetter = fyLetter;
    }

    public List<FyReception> getFyReception() {
        return fyReception;
    }

    public void setFyReception(List<FyReception> fyReception) {
        this.fyReception = fyReception;
    }

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
}
