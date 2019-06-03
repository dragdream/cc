package com.tianee.oa.core.base.email.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.tianee.webframe.util.global.TeeSysProps;


public class WebMailUtil { 

	private MimeMessage mimeMsg; //MIME邮件对象 
	private Session session; //邮件会话对象 
	private Properties props; //系统属性 
	private boolean needAuth = false; //smtp是否需要认证 
	//smtp认证用户名和密码 
	private String username; 
	private String password; 
	private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象 
	 
	/**
	 * Constructor
	 * @param smtp 邮件发送服务器
	 */
	public WebMailUtil(String smtp){ 
		setSmtpHost(smtp); 
		createMimeMessage(); 
	} 

	/**
	 * 设置邮件发送服务器
	 * @param hostName String 
	 */
	public void setSmtpHost(String hostName) { 
//		System.out.println("设置系统属性：mail.smtp.host = "+hostName); 
		if(props == null)
			props = System.getProperties(); //获得系统属性对象 	
		props.put("mail.smtp.host",hostName); //设置SMTP主机 
	} 


	/**
	 * 创建MIME邮件对象  
	 * @return
	 */
	public boolean createMimeMessage() 
	{ 
		try { 
//			System.out.println("准备获取邮件会话对象！"); 
			session = Session.getDefaultInstance(props,null); //获得邮件会话对象 
		} 
		catch(Exception e){ 
//			System.err.println("获取邮件会话对象时发生错误！"+e); 
			return false; 
		} 
	
//		System.out.println("准备创建MIME邮件对象！"); 
		try { 
			mimeMsg = new MimeMessage(session); //创建MIME邮件对象 
			mp = new MimeMultipart(); 
		
			return true; 
		} catch(Exception e){ 
//			System.err.println("创建MIME邮件对象失败！"+e); 
			return false; 
		} 
	} 	
	
	/**
	 * 设置SMTP是否需要验证
	 * @param need
	 */
	public void setNeedAuth(boolean need) { 
//		System.out.println("设置smtp身份认证：mail.smtp.auth = "+need); 
		if(props == null) props = System.getProperties(); 
		if(need){ 
			props.put("mail.smtp.auth","true"); 
		}else{ 
			props.put("mail.smtp.auth","false"); 
		} 
	} 

	/**
	 * 设置用户名和密码
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name,String pass) { 
		username = name; 
		password = pass; 
	} 

	/**
	 * 设置邮件主题
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) { 
//		System.out.println("设置邮件主题！"); 
		try{ 
			mimeMsg.setSubject(mailSubject); 
			return true; 
		} 
		catch(Exception e) { 
//			System.err.println("设置邮件主题发生错误！"); 
			return false; 
		} 
	}
	
	/** 
	 * 设置邮件正文
	 * @param mailBody String 
	 */ 
	public boolean setBody(String mailBody) { 
		try{ 
			BodyPart bp = new MimeBodyPart(); 
			bp.setContent(""+mailBody,"text/html;charset=utf-8"); 
			mp.addBodyPart(bp); 
		
			return true; 
		} catch(Exception e){ 
//		System.err.println("设置邮件正文时发生错误！"+e); 
		return false; 
		} 
	} 
	/** 
	 * 添加附件
	 * @param filename String 
	 */ 
	public boolean addFileAffix(MailFile mailFile) { 
		if(mailFile==null){
			return true;
		}
		try{ 
			BodyPart bp = new MimeBodyPart(); 
			FileDataSource fileds = new FileDataSource(mailFile.getFilePath()); 
			bp.setDataHandler(new DataHandler(fileds)); 
			bp.setFileName(MimeUtility.encodeText(mailFile.getFileName()));
			
			mp.addBodyPart(bp); 
			
			return true; 
		} catch(Exception e){ 
			return false; 
		} 
	} 
	
	/** 
	 * 设置发信人
	 * @param from String 
	 */ 
	public boolean setFrom(String from,String fromUser) { 
//		System.out.println("设置发信人！"); 
		try{ 
			mimeMsg.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromUser,MimeUtility.mimeCharset("utf-8"), null))); //设置发信人 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 
	} 
	/** 
	 * 设置收信人
	 * @param to String 
	 */ 
	public boolean setTo(String to){ 
		if(to == null)return false; 
		try{ 
			mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to)); 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 	
	} 
	
	/** 
	 * 设置抄送人
	 * @param copyto String  
	 */ 
	public boolean setCopyTo(String copyto) 
	{ 
		if(copyto == null)return false; 
		try{ 
		mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto)); 
		return true; 
		} 
		catch(Exception e) 
		{ return false; } 
	} 
	
	/** 
	 * 发送邮件
	 */ 
	public boolean sendOut() 
	{ 
		try{ 
			mimeMsg.setContent(mp); 
			mimeMsg.saveChanges(); 
			//设置连接超时 和  读取超时
			//mail.smtp.connectiontimeout和mail.smtp.timeout
			props.put("mail.smtp.connectiontimeout",1000*(TeeSysProps.getInt("WEB_MAIL_TIME_OUT")));
			props.put("mail.smtp.timeout",1000*(TeeSysProps.getInt("WEB_MAIL_TIME_OUT")) );
			
			Session mailSession = Session.getInstance(props,null); 
			Transport transport = mailSession.getTransport("smtp"); 
			transport.connect((String)props.get("mail.smtp.host"),username,password); 
			transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO)); 
//			transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC)); 
			//transport.send(mimeMsg); 
			
			
			transport.close(); 
			
			return true; 
		} catch(Exception e) { 
			e.printStackTrace();
			return false; 
		} 
	} 

	/**
	 * 调用sendOut方法完成邮件发送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
//	public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password) {
//		WebMailUtil theMail = new WebMailUtil(smtp);
//		theMail.setNeedAuth(true); //需要验证
//		
//		if(!theMail.setSubject(subject)) return false;
//		if(!theMail.setBody(content)) return false;
//		if(!theMail.setTo(to)) return false;
//		if(!theMail.setFrom(from)) return false;
//		theMail.setNamePass(username,password);
//		
//		if(!theMail.sendOut()) return false;
//		return true;
//	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带抄送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
//	public static boolean sendAndCc(String smtp,String from,String to,String copyto,String subject,String content,String username,String password) {
//		WebMailUtil theMail = new WebMailUtil(smtp);
//		theMail.setNeedAuth(true); //需要验证
//		
//		if(!theMail.setSubject(subject)) return false;
//		if(!theMail.setBody(content)) return false;
//		if(!theMail.setTo(to)) return false;
//		if(!theMail.setCopyTo(copyto)) return false;
//		if(!theMail.setFrom(from)) return false;
//		theMail.setNamePass(username,password);
//		
//		if(!theMail.sendOut()) return false;
//		return true;
//	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带附件
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename 附件路径
	 * @return
	 */
//	public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password,String filename) {
//		WebMailUtil theMail = new WebMailUtil(smtp);
//		theMail.setNeedAuth(true); //需要验证
//		
//		if(!theMail.setSubject(subject)) return false;
//		if(!theMail.setBody(content)) return false;
//		if(!theMail.addFileAffix(filename)) return false; 
//		if(!theMail.setTo(to)) return false;
//		if(!theMail.setFrom(from)) return false;
//		theMail.setNamePass(username,password);
//		
//		if(!theMail.sendOut()) return false;
//		return true;
//	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带附件和抄送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 */
	public static boolean send(String smtp,String from,String fromUserName,String to,String copyto,String subject,String content,String username,String password,MailFile mailFiles[]) {
		WebMailUtil theMail = new WebMailUtil(smtp);
		theMail.setNeedAuth(true); //需要验证
		
		if(subject!=null){
			try {
				if(!theMail.setSubject(MimeUtility.encodeText(subject,MimeUtility.mimeCharset("utf-8"), null))) return false;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(content!=null){
			if(!theMail.setBody(content)) return false;
		}
		if(mailFiles!=null){
			for(MailFile mailFile:mailFiles){
				if(!theMail.addFileAffix(mailFile)) return false; 	
			}
		}
		if(to!=null){
			if(!theMail.setTo(to)) return false;
		}
		if(copyto!=null){
			if(!theMail.setCopyTo(copyto)) return false;
		}
		if(from!=null){
			if(!theMail.setFrom(from,fromUserName)) return false;
		}
		
		theMail.setNamePass(username,password);
		
		if(!theMail.sendOut()) return false;
		return true;
	}
	
	public static boolean send(String smtp,String from,String to,String copyto,String subject,String content,String username,String password,MailFile mailFiles[]) {
		return send(smtp, from, "", to, copyto, subject, content, username, password, mailFiles);
	}
	
	public static void main(String[] args) {
		send("smtp.seatelgroup.com", "admin@seatelgroup.com","", "liteng@zatp.com.cn", "", "这个是主题", "内容！！", "admin@seatelgroup.com", "p@ssw0rd1", null);
	}
	
} 

