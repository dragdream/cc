package com.tianee.webframe.servlet;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeePersonDynamicInfo;
import com.tianee.oa.core.org.bean.TeeUserOnline;
import com.tianee.webframe.util.cache.RedisClient;

@Repository
public class TeeApplicationInit implements ApplicationListener{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public static boolean hasInit = false;

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// TODO Auto-generated method stub
		if(!hasInit){
			hasInit = true;
			//开启监听线程
			new Thread(new CheckSessionInvalid()).start();
		}
		
	}
	
	private class CheckSessionInvalid implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Set<String> keys = null;
			while(true){
				
				try {
					Thread.sleep(1000*60*2);//每2分钟扫描一次
					
					keys = TeeSessionListener.data.keySet();
					for(String key:keys){
						if(!RedisClient.getInstance().exists(key)){
							destorySession(key);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
		private void destorySession(String sessionId){
			//删除数据
			TeeSessionListener.data.remove(sessionId);
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
					List<TeeUserOnline> list = q.list();
					for(TeeUserOnline obj:list){
						TeePerson person = (TeePerson) s.get(TeePerson.class, obj.getUserId());
						if(person!=null){
							TeePersonDynamicInfo dynamicInfo = person.getDynamicInfo();
							if(dynamicInfo!=null){
								dynamicInfo.setOnline(dynamicInfo.getOnline()+(new Date().getTime()-dynamicInfo.getLastVisitTime().getTime()));
							}
							s.update(dynamicInfo);
						}
						s.delete(obj);
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
	
}
