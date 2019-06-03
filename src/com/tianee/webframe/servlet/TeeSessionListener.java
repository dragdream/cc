package com.tianee.webframe.servlet;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeePersonDynamicInfo;
import com.tianee.oa.oaconst.TeeConst;

/**
 * Session监听
 * 
 * @author SYL
 * 
 */
public  class TeeSessionListener implements HttpSessionListener {
	public  static Map<String,HttpSession>  data=Collections.synchronizedMap(new HashMap<String,HttpSession>());//全局  存放session数据
	

	/**
	 * session创建
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	/**
	 * session销毁
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		//删除数据
		data.remove(sessionId);
		TeePerson person = (TeePerson) session.getAttribute(TeeConst.LOGIN_USER);
		if(sessionId!=null){
			//删除数据库中的数据
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			Session s = null;
			Transaction t = null;
			try{
				s = sessionFactory.openSession();
				t = s.beginTransaction();
				Query q = s.createQuery("from TeeUserOnline where sessionToken='"+sessionId+"'");
				List list = q.list();
				boolean hasExist = false;
				for(Object obj:list){
					s.delete(obj);
					hasExist = true;
				}
				if(person!=null){
					person = (TeePerson) s.get(TeePerson.class, person.getUuid());
					TeePersonDynamicInfo dynamicInfo = person.getDynamicInfo();
					if(dynamicInfo!=null){
						dynamicInfo.setOnline(dynamicInfo.getOnline()+(new Date().getTime()-dynamicInfo.getLastVisitTime().getTime()));
					}
					s.update(dynamicInfo);
				}
				
				
				t.commit();
			}catch(Exception e){
				t.rollback();
				e.printStackTrace();
			}finally{
				s.close();
			}
		}
	}

	
}
