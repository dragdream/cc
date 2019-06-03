package com.tianee.webframe.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;

import com.tianee.oa.core.general.service.TeeSysLogService;
import com.tianee.oa.oaconst.TeeActionKeys;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;

@Repository
public class TeeRequestExceptionInterceptor implements HandlerExceptionResolver{
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TeeSysLogService sysLogService;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex){
		// TODO Auto-generated method stub
//		try{
//			MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
//			//System.out.println("methodName="+methodNameResolver.getHandlerMethodName(request)+",url="+request.getRequestURI());
//			//System.out.println(handler.getClass().toString().replaceAll("class ", "").replaceAll("Controller", "Log").replaceAll("controller", "log"));
//			Class clazz = Class.forName(handler.getClass().toString().replaceAll("class ", "").replaceAll("Controller", "Log").replaceAll("controller", "log"));
//			Object obj = clazz.newInstance();
//			Method m = clazz.getMethod("errorLog", HttpServletRequest.class,String.class,Object.class,Exception.class);
//			Method m1 = clazz.getMethod("setSysLogService",TeeSysLogService.class);
//			m1.invoke(obj,sysLogService);
//		    m.invoke(obj, request,methodNameResolver.getHandlerMethodName(request),handler,ex);
////			//这块儿最好输出一个固定的JSON格式，例如{rtState:1,rtMsg:Error..(ex.toString())}
//		}catch(Exception e){
////			e.printStackTrace();
//		}
		ex.printStackTrace();
		
		TeeJson json = new TeeJson();
		json.setRtState(false);
		if(ex instanceof TeeOperationException){
			json.setRtMsg(ex.getMessage().replace("com.tianee.webframe.exps.TeeOperationException:", ""));
		}else{
			json.setRtMsg("");
		}
		
		
		try {
			PrintWriter pw = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			pw.write(TeeJsonUtil.toJson(json));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
