package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

public class TeeForbidLoginValidator  implements TeeLoginValidator {


  public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json, Map sysParamap) throws Exception {
	  String notLogin = TeeStringUtil.getString(person.getNotLogin() , "0");
	  String notWebLogin = TeeStringUtil.getString(person.getNotWebLogin() , "0");
	  String notPcLogin = TeeStringUtil.getString(person.getNotPcLogin() , "0");
	  String notMoblieLogin = TeeStringUtil.getString(person.getNotMobileLogin() , "0");
	  String loginType = TeeStringUtil.getString(request.getParameter("CLIENT"), "1");
	if(notLogin.equals("0")){
		if(("1").equals(loginType)){
			if(("0").equals(notWebLogin)){
				return true;
			}
			return false;
		}else if(("2").equals(loginType)){
			if(("0").equals(notPcLogin)){
				return true;
			}
			return false;
		}else{
			if(("0").equals(notMoblieLogin)){
				return true;
			}
			return false;
		}
	}
    return false;
  }
 
  public String getValidatorType() {
    return TeeLoginErrorConst.LOGIN_FORBID_LOGIN;
  }

  public int getValidatorCode() {
    return TeeLoginErrorConst.LOGIN_FORBID_LOGIN_CODE;
  }

  public String getValidatorMsg() {
    return null;
  }

}
