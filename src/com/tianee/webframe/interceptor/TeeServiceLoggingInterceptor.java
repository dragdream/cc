package com.tianee.webframe.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.service.TeeSysLogService;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.annotation.TeeLoggingAntProcessor;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

/**
 * 日志AOP方法拦截器，拦截服务层方法，并检测是否存在日志注解
 * @author kakalion
 *
 */
public class TeeServiceLoggingInterceptor implements MethodInterceptor{
	/**
	 * 系统日志缓冲池，用来进行日志记录缓冲的，并进行定时入库
	 */
	public static List<TeeSysLog> sysLogsBufferdPool = Collections.synchronizedList(new ArrayList());
	
	@Override
	public Object invoke(MethodInvocation invoke) throws Throwable {
		// TODO Auto-generated method stub
		TeeLoggingAntProcessor loggingAntProcess = new TeeLoggingAntProcessor();
		Method method = invoke.getMethod();
		
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		
		Object returned = null;//方法返回值
		String logDesc = null;//日志描述
		boolean hasEx = false;//是否存在异常
		Logger logger = null;
		Exception _ex = null;
		try{
			returned = invoke.proceed();
		}catch(Exception ex){
			hasEx = true;
			//记录错误日志
			logger = Logger.getLogger(invoke.getClass());
			logger.error(ex);
			_ex = ex;
			throw ex;
		}finally{
			if(method.getName().endsWith("Service")){
				logDesc = loggingAntProcess.renderTemplate(info, method,invoke.getArguments(),returned);
				if(logDesc!=null){
					TeeSysLog sysLog = new TeeSysLog();
					if(info!=null){
						TeeLoggingAnt annotation = method.getAnnotation(TeeLoggingAnt.class);
						sysLog.setIp(info.getIpAddress());
						sysLog.setPersonId(info.getUserSid());
						sysLog.setUserId(info.getUserId());
						sysLog.setUserName(info.getUserName());
						sysLog.setTime(Calendar.getInstance());
						sysLog.setRemark(logDesc);
						sysLog.setType(annotation.type());
						if(hasEx){
							sysLog.setErrorLog(_ex.toString());
							sysLog.setErrorFlag(1);
						}else{
							sysLog.setErrorFlag(0);
						}
						synchronized (sysLogsBufferdPool) {
							sysLogsBufferdPool.add(sysLog);
						}
					}
					
				}
			}
		}
		return returned;
	}
}
