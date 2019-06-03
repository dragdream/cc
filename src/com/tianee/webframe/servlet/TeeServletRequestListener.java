package com.tianee.webframe.servlet;


import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 请求监听者

 * @author syl
 *
 */
public class TeeServletRequestListener implements ServletRequestListener {
  public void requestInitialized(ServletRequestEvent sre) {
    HttpServletRequest request = (HttpServletRequest)sre.getServletRequest();
    try {
      if (TeeServletUtility.isGbkCode(request)) {
        request.setCharacterEncoding("GBK");
      }else {
        request.setCharacterEncoding(TeeConst.DEFAULT_CODE);
      }
    }catch(Exception ex) {      
    }
    
    
    /*request.getSession().setAttribute(TeeConst.CURR_REQUEST_FLAG, true);
    request.getSession().setAttribute(TeeConst.CURR_REQUEST_ADDRESS, request.getRemoteAddr());*/
    
  }
  public void requestDestroyed(ServletRequestEvent sre) {    
    HttpServletRequest request = (HttpServletRequest)sre.getServletRequest();
  /*  System.out.println(request);
    if(request.getSession() != null){
    	 request.getSession().removeAttribute(TeeConst.CURR_REQUEST_FLAG);
    	 request.getSession().removeAttribute(TeeConst.CURR_REQUEST_ADDRESS);
    }
   
    */
    
  }
}
