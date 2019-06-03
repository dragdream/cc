package com.tianee.oa.core.system.adapter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;

public class TeeLoginAdapter {
  
  private HttpServletRequest request;
  private TeePerson person;
  private TeeJson json;
  private Map sysParamap;
  
  public TeeLoginAdapter(HttpServletRequest   request, TeePerson person,TeeJson json , Map sysParamap) throws Exception{
    this.request = request;
    this.person = person;
    this.json = json;
    this.sysParamap = sysParamap;
  }
  
  public boolean isValid(TeeLoginValidator lv) throws Exception{
    return lv.isValid(this.request, this.person,this.json , this.sysParamap );
  }
  
  public boolean validate(TeeLoginValidator lv) throws Exception{
    if (lv.isValid(this.request, this.person ,this.json, this.sysParamap  )){
      return true;
    }
    else{
      
      String msg = lv.getValidatorMsg();
      if (msg == null || "".equals(msg.trim())){
        msg = "{}";
      }
      //返回到页面上的信息

      String retData = "{\"code\":" + lv.getValidatorCode() + ",\"msg\":" + msg + "}";
      TeeJsonUtil jsonUtil = new TeeJsonUtil();
      JSONObject jsonObj = jsonUtil.jsonString2Json(retData);
      if (lv.getValidatorCode() == TeeLoginErrorConst.LOGIN_PW_EXPIRED_CODE || lv.getValidatorCode() == TeeLoginErrorConst.LOGIN_INITIAL_PW_CODE) {
        String sessionToken = (String)request.getSession().getAttribute(TeeConst.SESSIONTOKEN);
        retData = "{\"code\":" + lv.getValidatorCode() + ",\"msg\":" + msg + ",\"seqId\":\"" + person.getUuid() +"\",\"sessionToken\":\""+ sessionToken +"\"}";
      }
      json.setRtMsg(lv.getValidatorType());
      json.setRtState(false);
      json.setRtData(jsonObj); 
      return false;
    }
  }
}
