package com.tianee.webframe.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

/**
 * controller请求拦截器
 * @author kakalion
 *
 */
public class TeeRequestInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		TeeRequestInfoContext.clear();
		
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		TeeRequestInfo info = new TeeRequestInfo();
		
		//组装线程请求信息
		TeePerson loginPerson = (TeePerson) arg0.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(loginPerson!=null){
			info.setUserId(loginPerson.getUserId());
			info.setUserName(loginPerson.getUserName());
			info.setUserSid(loginPerson.getUuid());
		}
		
		info.setIpAddress(arg0.getRemoteAddr());
		info.setRequest(arg0.getParameterMap());
		
		Enumeration<String> en = arg0.getParameterNames();
		Map requestMap = new HashMap();
		String key = null;
		while(en.hasMoreElements()){
			key = en.nextElement();
			requestMap.put(key, arg0.getParameter(key));
		}
		info.setRequest(requestMap);
		
		Map sessionMap = new HashMap();
		Enumeration<String> en1 = arg0.getSession().getAttributeNames();
		while(en1.hasMoreElements()){
			key = en1.nextElement();
			sessionMap.put(key, arg0.getSession().getAttribute(key));
		}
		
		info.setSession(sessionMap);
		
		TeeRequestInfoContext.setRequestInfo(info);
		
		
		return true;
	}

}
