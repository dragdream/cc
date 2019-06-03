package com.tianee.webframe.util.thread;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.oa.core.base.email.util.MailFile;
import com.tianee.oa.core.base.email.util.WebMailUtil;
import com.tianee.webframe.util.db.TeeDbUtility;

public class TeeWebMailSendTimerProcessor implements Runnable{
	String smtp = "";  
    String from = "";  
    String to = "";  
    String copyto = null;
    String subject = "";  
    String content = "";  
    String username="";  
    String password="";
    boolean returned = false;
    Map data=null;
    MailFile files [] = null;
    
    Connection dbConn = null;
    
    
    public TeeWebMailSendTimerProcessor(String smtp,String from,String to,String copyto,String subject
    		,String content,String username,String password,boolean returned,Map data,MailFile files []){
    	this.smtp=smtp;
    	this.from=from;
    	this.to=to;
    	this.copyto=copyto;
    	this.subject=subject;
    	this.content=content;
    	this.username=username;
    	this.password=password;
    	this.returned=returned;
    	this.data=data;
    	this.files=files; 	
    }
    
	@Override
	public void run() {
		try {
			dbConn = TeeDbUtility.getConnection(null);
			dbConn.setAutoCommit(true);
			
			DbUtils dbUtils = new DbUtils(dbConn);
				
			//发送邮件
			returned = WebMailUtil.send(smtp, from, to, copyto, subject, content, username, password, files);
  
			//设置发送状态和发送次数
			if(returned==true){
				
				dbUtils.executeUpdate("update WEB_MAIL_SEND_TASK set status_= 1 ,send_number=send_number+1 where uuid='"+data.get("UUID")+"'");
			}else{
				
				dbUtils.executeUpdate("update WEB_MAIL_SEND_TASK set status_= -1 ,send_number=send_number+1 where uuid='"+data.get("UUID")+"'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(dbConn);
		}
	
		
	}

}
