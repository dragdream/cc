package com.tianee.oa.quartzjob;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.phoneSms.sender.ManDaoSender;
import com.tianee.oa.core.phoneSms.sender.MeiLianRuanTongSender;
import com.tianee.oa.core.phoneSms.sender.SmsSender;
import com.tianee.oa.core.phoneSms.sender.VoSmsSender;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.thread.TeeSmsSenderTimerProcessor;
import com.tianee.webframe.util.thread.TeeSmsSenderTimerThreadPool;
import com.vosms.impl.VOSmsImpl;

@Service
public class TeeSmsSenderTimer extends TeeBaseService{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void doTimmer(){
		List<TeeSmsSendPhone> sendList=null;
		
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		if(!"0".equals(TeeSysProps.getString("SMS_OPEN"))){//进行
			Session session = null;
			Transaction tx = null;
			try{
				session = sessionFactory.openSession();
				tx = session.beginTransaction();
				Calendar c=Calendar.getInstance();
				Query query = session.createQuery("from TeeSmsSendPhone where (sendFlag=0 or (sendFlag=2 and sendNumber<?))  and sendTime<=? ");
				query.setParameter(1, c);
				query.setParameter(0, TeeSysProps.getInt("SMS_PHONE_SEND_NUMBER"));
				sendList = query.list();
				//设置状态为正在发送中    并且改变发送数量
				Query query1=session.createQuery("update TeeSmsSendPhone set sendFlag=3 ,sendNumber=sendNumber+1 where (sendFlag=0 or (sendFlag=2 and sendNumber<?))  and sendTime<=?");
				query1.setParameter(1, c);
				query1.setParameter(0, TeeSysProps.getInt("SMS_PHONE_SEND_NUMBER"));
				query1.executeUpdate();
			    
				//进行事务提交
				tx.commit();
				
				//System.out.println("需要发送的短信的数量："+sendList.size());
				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				session.close();
				
				//发送短信
				String result = "";
				
				try{
					for(TeeSmsSendPhone sendPhone:sendList){
						TeeSmsSenderTimerProcessor process=new TeeSmsSenderTimerProcessor(result, sendPhone, sessionFactory);
					    TeeSmsSenderTimerThreadPool.getInstance().execute(process);
					}
				}catch(Exception ex1){
					ex1.printStackTrace();
				}
			}
			
		}
	}
	
	
	
	
	private String send(String fromUser,String phone,String content){
		SmsSender smsSender = null;
		if("1".equals(TeeSysProps.getString("SMS_OPEN"))){//美联软通
			smsSender = new MeiLianRuanTongSender();
		}else if("2".equals(TeeSysProps.getString("SMS_OPEN"))){//漫道
			smsSender = new ManDaoSender();
		}else if("3".equals(TeeSysProps.getString("SMS_OPEN"))){//维欧短信猫
			smsSender = new VoSmsSender();
		}
		return smsSender.send(fromUser, phone, content);
	}
}
