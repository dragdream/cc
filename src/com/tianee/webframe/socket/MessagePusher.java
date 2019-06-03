package com.tianee.webframe.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.quartzjob.TeeImPushTimer;
import com.tianee.webframe.util.db.TeeHibernateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;

public final class MessagePusher {
	
	/**
	 * 推送定时任务
	 * @param task
	 */
	public static void push2Quartz(TeeQuartzTask task){
		try {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			SessionFactory sf  = (SessionFactory)wac.getBean("sessionFactory"); 
			Session session = null;
			Transaction transaction = null;
			Query query = null;
			try{
				session = sf.openSession();
				transaction = session.beginTransaction();
				if(task.getType()==0){//删除
					query = session.createQuery("update TeeQuartzTask set deleteStatus=1 where modelNo='"+task.getModelNo()+"' and modelId='"+task.getModelId()+"'");
					query.executeUpdate();
				}else{//删除，并增加新的节点
					query = session.createQuery("update TeeQuartzTask set deleteStatus=1 where modelNo='"+task.getModelNo()+"' and modelId='"+task.getModelId()+"'");
					query.executeUpdate();
					
					//此处可支持server任务哈希分配节点
					task.setNode("server1");
					session.save(task);
				}
				transaction.commit();
			}catch(Exception ex){
				transaction.rollback();
			}finally{
				if(session!=null && session.isConnected()){
					session.close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 将推送的消息存入消息缓存队列
	 * @param datagram
	 */
	public static void push2Im(Map datagram){
		synchronized (TeeImPushTimer.msgBuffQueue) {
			TeeImPushTimer.msgBuffQueue.add(datagram);
		}
	}
}
