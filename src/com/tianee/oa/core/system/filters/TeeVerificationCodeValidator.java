package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

public class TeeVerificationCodeValidator implements TeeLoginValidator {
  String isSecureCard = "0";
  String returnValue = "0";

  public boolean isValid(HttpServletRequest request, TeePerson person,TeeJson json , Map sysParaMap) throws Exception {
    String verifyCode = TeeStringUtil.getString(request.getParameter("verifyCode")).toLowerCase();
    String sessionVerifyCode = TeeStringUtil.getString(request.getSession().getAttribute(TeeConst.VALIDATE_CODE)).toLowerCase();
    if("0".equals(TeeSysProps.getString("LOGIN_BY_CODE"))){
    	return true;
    }
    if(!verifyCode.equals(sessionVerifyCode)){
    	return false;
    }
    return true;
  }

  
  public int getValidatorCode() {
    return TeeLoginErrorConst.VERIFICATION_CODE_CODE;
  }

  
  public String getValidatorType() {
    return TeeLoginErrorConst.VERIFICATION_CODE_ERROR;
  }
  
  public String getValidatorMsg() {
    // TODO Auto-generated method stub
    return null;
  }

}
