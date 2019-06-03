package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

public class TeeInitialPwdValidator  implements TeeLoginValidator {


  public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) throws Exception {
	  String SEC_INIT_PASS = TeeStringUtil.getString((String)sysParamap.get("SEC_INIT_PASS") , "0");
	  if(SEC_INIT_PASS.equals("1") && person.getLastPassTime() == null){
		return false;
	}
    return true;
  }
 
  public String getValidatorType() {
    return TeeLoginErrorConst.LOGIN_INITIAL_PW;
  }

  public int getValidatorCode() {
    return TeeLoginErrorConst.LOGIN_INITIAL_PW_CODE;
  }

  public String getValidatorMsg() {
    return null;
  }

}
