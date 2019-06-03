package com.tianee.webframe.util.global;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
/**
 * 
 * @author zhp
 * @createTime 2013-10-1
 * @desc
 */
public class TeeBeanFactory {
	public static synchronized Object getBean(String name){
		try{
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			if(context==null){
				return null;
			}
			return context.getBean(name);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
