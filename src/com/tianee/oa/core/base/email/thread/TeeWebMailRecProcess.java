package com.tianee.oa.core.base.email.thread;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.IntegerComparisonTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SizeTerm;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeWebMail;
import com.tianee.oa.core.base.email.bean.TeeWebMailRec;
import com.tianee.oa.core.base.email.util.TeeMimeMessageUtil;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 外部邮件接收处理，用于单条数据接收
 * @author LiTeng
 *
 */
public class TeeWebMailRecProcess implements Runnable{
	
	//任务标识
	private String id;
	//外部邮件对象
//	private TeeWebMail webMail;
	
	private Session hibernateSession = null;
	
	public TeeWebMailRecProcess(String id){
		this.id = id;
		
	}
	
	public String getId(){
		return id;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Session hibernateSession = null;
		TeePerson person = null;
		Properties props = null;
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		javax.mail.Session session = null;
		Store store = null;
		URLName urln = null;
		Folder folder = null;
		POP3Folder popinbox = null;
		Message[] messages = null;
		IMAPFolder imapinbox = null;
		MimeMessage mimeMessage = null;
		List<String> emailGuidDb = null;//存放邮件uid的
		try {
			SessionFactory sessionFactory = (SessionFactory) TeeBeanFactory.getBean("sessionFactory");
			if(sessionFactory==null){
				return;
			}
			hibernateSession = sessionFactory.openSession();
			Query query = hibernateSession.createQuery("from TeeWebMail where sid="+id);
			TeeWebMail webMail = (TeeWebMail) query.uniqueResult();
			if(webMail==null){
				return;
			}
			webMail.getUser().getUserId();
			person = webMail.getUser();
			
			//获取最后一次接收的时间
			query = hibernateSession.createQuery("select max(recTime) from TeeWebMailRec where recUser="+webMail.getUser().getUuid());
			Calendar lastSendTime = (Calendar) query.uniqueResult();
			
			hibernateSession.close();
			
			
			// 创建配置文件
			props = new Properties();
			if(webMail.getPopServer().contains("pop")){
				if(webMail.getPop3Ssl().equals("1")){
					props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
					props.setProperty("mail.pop3.socketFactory.fallback", "false");
					props.setProperty("mail.pop3.socketFactory.port",webMail.getPop3Port()+"");
					props.setProperty("mail.pop3.port", webMail.getPop3Port()+"");
				}
				props.setProperty("mail.pop3.connectiontimeout", String.valueOf(TeeSysProps.getInt("WEB_MAIL_REC_TIMEOUT")*1000));
				props.setProperty("mail.pop3.timeout", String.valueOf(TeeSysProps.getInt("WEB_MAIL_REC_TIMEOUT")*1000));
			}else{
				if(webMail.getPop3Ssl().equals("1")){
					props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
					props.setProperty("mail.imap.socketFactory.fallback", "false");
					props.setProperty("mail.imap.socketFactory.port",webMail.getPop3Port()+"");
					props.setProperty("mail.imap.port", webMail.getPop3Port()+"");
				}
				props.setProperty("mail.imap.connectiontimeout", String.valueOf(TeeSysProps.getInt("WEB_MAIL_REC_TIMEOUT")*1000));
				props.setProperty("mail.imap.timeout", String.valueOf(TeeSysProps.getInt("WEB_MAIL_REC_TIMEOUT")*1000));
			}
			
			//
			// 创建javamail会话
			try {
				session = javax.mail.Session.getInstance(props);
				if(webMail.getPopServer().contains("pop")){
					urln = new URLName("pop3", webMail.getPopServer(),
							Integer.parseInt(webMail.getPop3Port()), null,
							webMail.getEmailUser(), webMail.getEmailPass());
				}else{
					urln = new URLName("imap", webMail.getPopServer(),
							Integer.parseInt(webMail.getPop3Port()), null,
							webMail.getEmailUser(), webMail.getEmailPass());
				}
				
				store = session.getStore(urln);
//						System.out.println(urln);
				store.connect();

				folder = store.getFolder("INBOX");// 获取邮件箱
				folder.open(Folder.READ_ONLY);
//						profile = new FetchProfile();
//						profile.add(UIDFolder.FetchProfileItem.UID);
//						profile.add(FetchProfile.Item.ENVELOPE);
				folder.getNewMessageCount();
				
				emailGuidDb = getEmailUuid(webMail.getUser().getUuid());

				if (folder instanceof POP3Folder) { // pop3的形式
					popinbox = (POP3Folder) folder;
					messages = popinbox.getMessages();
//							popinbox.fetch(messages, profile);
					for (int i = messages.length-1; i >= 0; i--) {
						mimeMessage = (MimeMessage) messages[i];
						String uid = popinbox.getUID(mimeMessage);
//						System.out.println(TeeDateUtil.format(mimeMessage.getSentDate()));
						if(!checkIsRecivable(0, mimeMessage.getSentDate())){
							break;
						}
						
						//保存附件及mailBody
						try{
							processing(emailGuidDb,mimeMessage,uid,"x",person);
						}catch(Exception ex){
							ex.printStackTrace();
						}
//						if(webMail.getRecvDel()==1){
//							mimeMessage.setFlag(Flags.Flag.DELETED, true);
//						}
					}
				}else if (folder instanceof IMAPFolder) { // IMAP的形式
			        imapinbox = (IMAPFolder) folder;
			        messages = imapinbox.getMessages();
//					        imapinbox.fetch(messages, profile);
			        for (int i = messages.length-1; i >= 0; i--) {
			          mimeMessage = (MimeMessage) messages[i];
			          String uid = Long.toString(imapinbox.getUID(mimeMessage));
			          if(!checkIsRecivable(0, mimeMessage.getSentDate())){
							break;
						}
			          //保存附件及mailBody
			          try{
						processing(emailGuidDb,mimeMessage,uid,"x",person);
					}catch(Exception ex){
//								ex.printStackTrace();
					}
//			          if(webMail.getRecvDel()==1){
//							mimeMessage.setFlag(Flags.Flag.DELETED, true);
//						}
			        }
			      } else {
			        throw new Exception("不支持此类邮箱的协议类型!");
			      }

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					folder.close(true);
				} catch (Exception ex) {
				}
				try {
					store.close();
				} catch (Exception ex) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(hibernateSession!=null && hibernateSession.isOpen()){
				hibernateSession.close();
			}
		}
	}
	
	
	 public static Message[] search(Folder folder,Calendar lastSendTime) throws Exception {   
		 
		 Message[] folders = null;
		 
	        // 搜索主题包含美食的邮件   
	        // 搜索周一到今天收到的的所有邮件   
	        Calendar calendar = null;
	        if(lastSendTime == null){//如果最后接收的时间为空，则计算从今天开始往前推N天的邮件
	        	calendar = Calendar.getInstance();   
	        	calendar.add(Calendar.DATE, -TeeSysProps.getInt("WEB_MAIL_REC_DAYS"));
	        	
	        	SearchTerm comparisonTermGe = new SentDateTerm(ComparisonTerm.GE, calendar.getTime());

		        // 搜索大于或等100KB的所有邮件   
		        int mailSize = TeeSysProps.getInt("WEB_MAIL_REC_LIMIT")*1024*1024;
		        SearchTerm intComparisonTerm = new SizeTerm(IntegerComparisonTerm.LE, mailSize);
		        
		        AndTerm andTerm = new AndTerm(comparisonTermGe,intComparisonTerm);
		        
		        folders =  folder.search(andTerm); 
		        
	        }else{//如果最后接收时间不为空，则计算获取从今天开始往前推N天的邮件，并且是大于最后一天发送的邮件
	        	calendar = Calendar.getInstance();   
	        	calendar.add(Calendar.DATE, -TeeSysProps.getInt("WEB_MAIL_REC_DAYS"));
	        	
	        	if(calendar.getTimeInMillis()<lastSendTime.getTimeInMillis()){//获取时间段内的邮件
	        		SearchTerm comparisonTermGe = new SentDateTerm(ComparisonTerm.GE, lastSendTime.getTime());
	        		SearchTerm comparisonTermLe = new SentDateTerm(ComparisonTerm.LE, new Date());
	        		AndTerm andTerm = new AndTerm(comparisonTermGe,comparisonTermLe);
	        		
	        		// 搜索大于或等100KB的所有邮件   
			        int mailSize = TeeSysProps.getInt("WEB_MAIL_REC_LIMIT")*1024*1024;
			        SearchTerm intComparisonTerm = new SizeTerm(IntegerComparisonTerm.LE, mailSize);
			        
			        AndTerm andTerm1 = new AndTerm(intComparisonTerm,andTerm);
			        
			        folders =  folder.search(andTerm1);
	        		
	        	}else{//获取N天内的邮件
	        		SearchTerm comparisonTermGe = new SentDateTerm(ComparisonTerm.GE, calendar.getTime());

			        // 搜索大于或等100KB的所有邮件   
			        int mailSize = TeeSysProps.getInt("WEB_MAIL_REC_LIMIT")*1024*1024;
			        SearchTerm intComparisonTerm = new SizeTerm(IntegerComparisonTerm.LE, mailSize);
			        
			        AndTerm andTerm = new AndTerm(comparisonTermGe,intComparisonTerm);
			        folders =  folder.search(andTerm); 
	        	}
	        }
	          
	        return folders;
	    }   
	 
	 public static boolean checkIsRecivable(int size,Date mailDate) throws Exception {   
		 
		 	int mailSize = TeeSysProps.getInt("WEB_MAIL_REC_LIMIT")*1024*1024;
	        // 搜索主题包含美食的邮件   
	        // 搜索周一到今天收到的的所有邮件   
	        Calendar calendar = Calendar.getInstance();   
        	calendar.add(Calendar.DATE, -TeeSysProps.getInt("WEB_MAIL_REC_DAYS"));
        	
        	if(size<=mailSize && calendar.getTime().compareTo(mailDate)==-1){
        		return true;
        	}
	          
	        return false;
	    }   
	
	private List<String> getEmailUuid(int userSid) {
		List<String> emailGuid = null;
		Session session = null;
		try {
			SessionFactory sessionFactory = (SessionFactory) TeeBeanFactory.getBean("sessionFactory");
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("select uuid from TeeWebMailRec rec where recUser="+ userSid + "");
			
			emailGuid = query.list();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return emailGuid;
	}


	/**
	 * 处理webmail
	 * @param mimeMessage
	 * @param messageUid
	 * @param userName
	 * @param person
	 * @throws Exception
	 */
	private void processing(List<String> emailGuidDb,Message mimeMessage,
			String messageUid, String userName,
			TeePerson person) throws Exception {
		
		//判断当前邮件是否已经标记接收
		if (emailGuidDb.contains(messageUid)) {
			return ;
		}

		TeeMailBody webmailBody = new TeeMailBody();
		String fromMail = TeeMimeMessageUtil.getFrom(mimeMessage);
		// System.out.println("messageUid:"+messageUid);
		webmailBody.setWebMailUid(messageUid);
		webmailBody.setSendTime(TeeMimeMessageUtil.getSentDate(mimeMessage));
		webmailBody.setSubject(TeeMimeMessageUtil.getSubject(mimeMessage));
		webmailBody.setFromWebMail(fromMail);
		webmailBody.setToWebMail(TeeMimeMessageUtil.getMailAddress(mimeMessage,
				"TO"));
		webmailBody.setCcWebMail(TeeMimeMessageUtil.getMailAddress(mimeMessage,
				"CC"));
		
		
		//判断当前邮件是否在可接收的条件内
		if(!checkIsRecivable(mimeMessage.getSize(),webmailBody.getSendTime())){
			return;
		}
		
		System.out.println("收取新邮件！！！"+TeeDateUtil.format(new Date()));
		
		//将当前邮件uid加入rec接收数据表中
		
		try{
			SessionFactory sessionFactory = (SessionFactory) TeeBeanFactory.getBean("sessionFactory");
			hibernateSession = sessionFactory.openSession();//开启新的session
			hibernateSession.beginTransaction();//开启事务
			TeeWebMailRec mailRec = new TeeWebMailRec();
			mailRec.setRecTime(Calendar.getInstance());
			mailRec.setRecUser(person.getUuid());
			mailRec.setSuccess(0);
			mailRec.setUuid(messageUid);
			Calendar c = Calendar.getInstance();
			c.setTime(webmailBody.getSendTime());
			mailRec.setSendTime(c);
			hibernateSession.save(mailRec);
			hibernateSession.getTransaction().commit();
			emailGuidDb.add(messageUid);
		}catch(Exception ex){
			//ex.printStackTrace();
			return;
		}finally{
			if(hibernateSession!=null && hibernateSession.isOpen()){
				hibernateSession.close();
			}
		}
		
		int webLimit = TeeSysProps.getInt("WEB_MAIL_REC_LIMIT")*1024*1024;//最大收取限制  M
		int size = mimeMessage.getSize();
		if(size>webLimit){//如果超出最大收取范围，则不保存该附件
			return;
		}
		
		webmailBody.setContent(TeeMimeMessageUtil
				.getBody(mimeMessage, userName));
		String isHtml = TeeMimeMessageUtil.isHtml(mimeMessage) ? "0" : "1";
		webmailBody.setIsHtml(isHtml);
		webmailBody.setIfWebMail(1);

		List<TeeAttachment> attaches = null;
		
		TeeBaseUpload baseUpload = (TeeBaseUpload) TeeBeanFactory.getBean("teeBaseUpload");
		attaches = TeeMimeMessageUtil
		.saveAttachMent(mimeMessage, baseUpload,person);
		
//		if (TeeMimeMessageUtil.hasAttachment(mimeMessage)) {
//			int size = mimeMessage.getSize();
//			if ( (webLimit) < size) {
//				/**
//				 * 邮件是否太大了，邮件太大了就只加载文本
//				 */
//				
//			} else {
//			}
//		}
		
		try{
			SessionFactory sessionFactory = (SessionFactory) TeeBeanFactory.getBean("sessionFactory");
			hibernateSession = sessionFactory.openSession();//开启新的session
			hibernateSession.beginTransaction();//开启事务
			webmailBody.setSendFlag("1");
			//保存mailBody
			hibernateSession.save(webmailBody);
			//保存mail
			TeeMail mail = new TeeMail();
			mail.setMailBody(webmailBody);
			mail.setToUser(person);
			hibernateSession.save(mail);
			
			if(attaches!=null){
				//保存附件关联关系
				for(TeeAttachment attach:attaches){
					attach.setModelId(String.valueOf(webmailBody.getSid()));
					hibernateSession.update(attach);
				}
			}
			
			//保存消息
			TeeSmsBody smsBody = new TeeSmsBody();
	    	smsBody.setContent("您有一封外部邮件["+webmailBody.getSubject()+"],请注意查收");
	    	smsBody.setFromId(0);
	    	smsBody.setRemindUrl("/system/core/email/readEmailByMailId.jsp?sid="+mail.getSid()+"&closeOptFlag=1");
	    	smsBody.setSendTime(Calendar.getInstance());
	    	smsBody.setModuleNo("019");
	    	smsBody.setSendFlag(1);
	    	hibernateSession.save(smsBody);
	    	
	    	TeeSms sms = new TeeSms();
	    	sms.setToId(person.getUuid());
			sms.setSmsBody(smsBody);
			sms.setDeleteFlag(0);
			sms.setRemindTime(new Date());
			hibernateSession.save(sms);
			
			//提交事务
			hibernateSession.getTransaction().commit();
			
			//发送消息
			Map map = new HashMap();
			map.put("from", smsBody.getFromId());
			map.put("to", person.getUserId());
			map.put("content", smsBody.getContent());
			map.put("url", smsBody.getRemindUrl());
			map.put("url1", smsBody.getRemindUrl1());
			map.put("t", 3);
			map.put("time", TeeDateUtil.format(new Date()));
			map.put("no", smsBody.getModuleNo());
			MessagePusher.push2Im(map);
		}catch(Exception ex){
			hibernateSession.getTransaction().rollback();
		}finally{
			if(hibernateSession!=null && hibernateSession.isOpen()){
				hibernateSession.close();
			}
		}
	}
}
