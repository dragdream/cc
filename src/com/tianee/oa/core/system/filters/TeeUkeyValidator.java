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

public class TeeUkeyValidator implements TeeLoginValidator {
  String isSecureCard = "0";
  String returnValue = "0";

  public boolean isValid(HttpServletRequest request, TeePerson person,TeeJson json , Map sysParaMap) throws Exception {
    String dogId = TeeStringUtil.getString(request.getParameter("dogId"));
    String dogName= TeeStringUtil.getString(request.getParameter("dogName"));
    //String sessionVerifyCode = TeeStringUtil.getString(request.getSession().getAttribute(TeeConst.VALIDATE_CODE)).toLowerCase();
      if(("").equals(person.getKeySN())||person.getKeySN()==null){//不需要Ukey验证
    	  return true;
      }else{//需要Ukey
    	if(("").equals(dogId)){//无法连接到设备
    		return false;
    	}else{
    	   if(dogId.equals(person.getKeySN())&&dogName.equals(person.getUserId())){
    		   return true;  		   
    	   }else{
    		   return false;
    	   }	
    	}
      }
  }

  
  public int getValidatorCode() {
    return TeeLoginErrorConst.UKEY_CODE;
  }

  
  public String getValidatorType() {
    return TeeLoginErrorConst.UKEY_CODE_ERROR;
  }
  
  public String getValidatorMsg() {
    // TODO Auto-generated method stub
    return null;
  }

}
