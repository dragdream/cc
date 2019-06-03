package com.tianee.oa.core.base.email;

import java.util.HashMap;
import java.util.List;

import com.tianee.oa.core.attachment.bean.TeeAttachment;

public class WebMailSender {
	/**
	 * @author CXT
	 * @param smtpServer :smtp.163.com
	 * @param from :cxt7800219@163.com
	 * @param displayName :附件测试
	 * @param username :cxt7800219@163.com
	 * @param password :chuxingtao21
	 * @param to :279599173@qq.com
	 * @param subject :附件测试
	 * @param content :邮件带附件测试
	 */
	public String sendWebMail(String smtpServer,String smtpPort,String from,String displayName,String username,String password,String to,String subject,String content,List<TeeAttachment> list){   
        //设置邮件   
		WebMail mail = new WebMail(smtpServer,from,displayName,username,password,to,subject,content);
		//Mail mail = new Mail("smtp.163.com","cxt7800219@163.com","附件测试","cxt7800219@163.com","chuxingtao21","279599173@qq.com","附件测试","邮件带附件测试");
		for(TeeAttachment attach : list){
			mail.addAttachfile(attach.getAttachmentPath()+attach.getAttachmentName());
		}
		//mail.addAttachfile("D:\\附件1.zip");
		//mail.addAttachfile("D:\\附件2.zip");
		HashMap map = mail.send(list,smtpPort);
		String message = (String) map.get("message");
		return message;
	}  

}
