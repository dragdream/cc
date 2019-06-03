package com.tianee.oa.core.system.filters;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

public class TeePwdErrorValidator implements TeeLoginValidator {
	  String msg =  TeeLoginErrorConst.LOGIN_RETRY_ERROR;
	  public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) throws Exception {
		  String SEC_RETRY_BAN = TeeStringUtil.getString((String)sysParamap.get("SEC_RETRY_BAN") , "0");//登录错误次数限制（是否限制？
		  int SEC_RETRY_TIMES = TeeStringUtil.getInteger((String)sysParamap.get("SEC_RETRY_TIMES") , 0);//密码输错×××次之后
		  int SEC_BAN_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_BAN_TIME") , 0);//××分钟之内禁止再次登录
		  
		  if(SEC_RETRY_BAN.equals("1")){
			  Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组 
			  return checkTime(person, SEC_RETRY_TIMES ,SEC_BAN_TIME, cookies);
		  }
		  return true;
	  }
	  /**
	   * 校验登录登录错误次数
	   * @param person
	   * @param SEC_PASS_TIME
	   * @return
	   */
	  public boolean checkTime(TeePerson person , int SEC_RETRY_TIMES , int SEC_BAN_TIME , Cookie[] cookies){
		  
		  String LOGIN_ERROR_COUNT_VALUE = "";//登录错误数
		  String LOGIN_LAST_ERROR_TIME_VALUE = "";//最后一次登录错误时间
		  if(cookies!=null){
			  for(Cookie cookie : cookies){ 
			      String personLoginErrorName = cookie.getName();// get the cookie name 
			      if(personLoginErrorName.equals("LOGIN_ERROR_COUNT_" + person.getUuid())){
			    	  LOGIN_ERROR_COUNT_VALUE = cookie.getValue(); // get the cookie value 
			      }
			      if(personLoginErrorName.equals("LOGIN_LAST_ERROR_TIME_VALUE_" + person.getUuid())){
			    	  LOGIN_LAST_ERROR_TIME_VALUE = cookie.getValue(); // get the cookie value 
			      }
			      
			  } 
		  }
		  
		  int LOGIN_ERROR_COUNT = TeeStringUtil.getInteger(LOGIN_ERROR_COUNT_VALUE, 0);
		  long LOGIN_LAST_ERROR_TIME = TeeStringUtil.getLong(LOGIN_LAST_ERROR_TIME_VALUE, 0);
		  if(LOGIN_ERROR_COUNT >= SEC_RETRY_TIMES){
			  Date date = new Date();
		      Date passDate = person.getLastPassTime();
		      if (passDate == null){
		        return true;
		      }
		      long seconds = date.getTime() - LOGIN_LAST_ERROR_TIME;
		      long min = SEC_BAN_TIME * 1000 * 60;
		      
		      if(seconds <=  min){
		    	  msg = "登录密码错误次数超过" + SEC_RETRY_TIMES + "次，等待" + SEC_BAN_TIME + "分钟后重新登录！"  ;
		    	  return false;
		      }
		  }
		 return true;
	  }
	  public String getValidatorType() {
	    return msg;
	  }

	  public int getValidatorCode() {
	    return TeeLoginErrorConst.LOGIN_RETRY_ERROR_CODE;
	  }

	  public String getValidatorMsg() {
	    return null;
	  }

}
