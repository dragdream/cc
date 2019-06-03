package com.beidasoft.xzfy.caseRegister.model.caseManager.response;

import java.util.List;
import com.beidasoft.xzfy.caseRegister.bean.FyAgent;
import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;

public class GetFyRespondentResp {

    private List<FyRespondent> fyRespondentList;

    private List<FyAgent> fyAgentList;

    public List<FyRespondent> getFyRespondentList() {
        return fyRespondentList;
    }

    public void setFyRespondentList(List<FyRespondent> fyRespondentList) {
        this.fyRespondentList = fyRespondentList;
    }

    public List<FyAgent> getFyAgentList() {
        return fyAgentList;
    }

    public void setFyAgentList(List<FyAgent> fyAgentList) {
        this.fyAgentList = fyAgentList;
    }

}
