package com.tianee.webframe.util.dynamic;

import java.lang.reflect.Method;

public final class TeeClassRunner {
	
	public static Object exec(String className,String methodName,Object params[]) throws Exception{
		Class clazz = Class.forName(className);
		Class paramsClass [] = null;
		if(params!=null){
			paramsClass = new Class[params.length];
			for(int i=0;i<params.length;i++){
				paramsClass[i] = params[i].getClass();
			}
		}
		
		Method method = clazz.getMethod(methodName, paramsClass);
		Object obj = clazz.newInstance();
		
		return method.invoke(obj, params);
	}
}
