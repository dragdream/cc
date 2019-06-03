package com.tianee.oa.core.system.filters;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

public class TeePwdExpiredValidator  implements TeeLoginValidator {


  public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) throws Exception {
	  String SEC_PASS_FLAG = TeeStringUtil.getString((String)sysParamap.get("SEC_PASS_FLAG") , "0");
	  int SEC_PASS_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_PASS_TIME") , 0);
	  if(SEC_PASS_FLAG.equals("1")){
		return checkTime(person, SEC_PASS_TIME);
	  }
	  return true;
  }
  /**
   * 校验登录时间是否过期
   * @param person
   * @param SEC_PASS_TIME
   * @return
   */
  public boolean checkTime(TeePerson person , int SEC_PASS_TIME){
	  Date date = new Date();
      Date passDate = person.getLastPassTime();
      if (passDate == null){
        return false;
      }
      long  SEC_PASS_TIME_TEMP = SEC_PASS_TIME;
      long seconds = date.getTime() - passDate.getTime();
      long days =  SEC_PASS_TIME_TEMP* (3600000 * 24);
      
      return seconds <  days;
  }
  public String getValidatorType() {
    return TeeLoginErrorConst.LOGIN_PW_EXPIRED;
  }

  public int getValidatorCode() {
    return TeeLoginErrorConst.LOGIN_PW_EXPIRED_CODE;
  }

  public String getValidatorMsg() {
    return null;
  }

}
