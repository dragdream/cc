package com.beidasoft.xzfy.caseRegister.model.caseManager.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Letter;

public class FyLetterReq implements Request {

    private Letter letter;

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

}
