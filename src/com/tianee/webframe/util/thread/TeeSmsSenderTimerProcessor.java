package com.tianee.webframe.util.thread;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.phoneSms.sender.ManDaoSender;
import com.tianee.oa.core.phoneSms.sender.MeiLianRuanTongSender;
import com.tianee.oa.core.phoneSms.sender.SmsSender;
import com.tianee.oa.core.phoneSms.sender.VoSmsSender;
import com.tianee.webframe.util.global.TeeSysProps;

public class TeeSmsSenderTimerProcessor implements Runnable{
	SessionFactory sessionFactory=null;
	TeeSmsSendPhone sendPhone=null;
	String result = "";
	Session session = null;
	Transaction tx = null;
	
	public TeeSmsSenderTimerProcessor(String result,TeeSmsSendPhone sendPhone,SessionFactory sessionFactory){
		this.result=result;
		this.sendPhone=sendPhone;
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	public void run() {
		try {
          //  System.out.println("nn");
			
			//result = send(sendPhone.getFromName(),sendPhone.getPhone(),sendPhone.getContent());
			SmsSender smsSender = null;
			smsSender = new MeiLianRuanTongSender();
			
			
			//发送短信
			result=smsSender.send(sendPhone.getFromName(), sendPhone.getPhone(), sendPhone.getContent());
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
            Query q=null;
			if(result.indexOf("0")!=-1){//发送成功  
				q=session.createQuery("update TeeSmsSendPhone set sendFlag=1 where sid=?");
				q.setParameter(0, sendPhone.getSid());
				q.executeUpdate();
				//sendPhone.setSendFlag(1);
				
			}else{//发送超时
				q=session.createQuery("update TeeSmsSendPhone set sendFlag=2 where sid=?");
				q.setParameter(0, sendPhone.getSid());
				q.executeUpdate();
				//sendPhone.setSendFlag(2);
				
			}
			tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

}
