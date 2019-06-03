package com.tianee.oa.core.system.imp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeLoginValidator {
  /**
   * 
   * @param request
   * @param person
   * @param json
   * @param sysParamap  关于登录系统参数
   * @return
   * @throws Exception
   */
  public boolean isValid(HttpServletRequest request, TeePerson person,TeeJson json , Map sysParamap) throws Exception;
  public String getValidatorType();
  public int getValidatorCode();
  public String getValidatorMsg();
}
