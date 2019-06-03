package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;

public class TeeExistUserValidator  implements TeeLoginValidator {


  public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) throws Exception {
    return person != null;
  }
 
  public String getValidatorType() {
    return TeeLoginErrorConst.LOGIN_NOTEXIST_USER;
  }

  public int getValidatorCode() {
    return TeeLoginErrorConst.LOGIN_NOTEXIST_USER_CODE;
  }

  public String getValidatorMsg() {
    return null;
  }

}
