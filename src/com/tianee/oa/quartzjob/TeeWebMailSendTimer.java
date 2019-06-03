package com.tianee.oa.quartzjob;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.email.WebMail;
import com.tianee.oa.core.base.email.util.MailFile;
import com.tianee.oa.core.base.email.util.WebMailUtil;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeWebMailSendTimerProcessor;
import com.tianee.webframe.util.thread.TeeWebMailSendTimerThreadPool;

@Service
public class TeeWebMailSendTimer extends TeeBaseService{
	
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		Connection dbConn = null;
		try{
			dbConn = TeeDbUtility.getConnection(null);
			dbConn.setAutoCommit(true);
			
			DbUtils dbUtils = new DbUtils(dbConn);
			//获取系统设置外部邮箱发送次数
			int sendNumber=TeeSysProps.getInt("WEB_MAIL_SEND_NUMBER");
			//获取所有未发送的待发外部邮件
			/*List<Map> list = dbUtils.queryToMapList("select task.*,"
					+ "webmail.*,"
					+ "body.subject as SUBJECT,"
					+ "body.content as CONTENT "
					+ " from WEB_MAIL_SEND_TASK task left outer join WEB_MAIL webmail on webmail.sid=task.from_,MAIL_BODY body where task.status_ = 0  or (task.status_ = -1  and task.send_number < "+sendNumber+" ) and body.sid=task.MAIL_BODY_ID");*/
			
			
			List<Map> list = dbUtils.queryToMapList("select task.*,"
					+ "webmail.*,"
					+ "body.subject as SUBJECT,"
					+ "body.content as CONTENT "
					+ " from WEB_MAIL_SEND_TASK task left outer join WEB_MAIL webmail on webmail.sid=task.from_ left outer join MAIL_BODY body on  body.sid=task.MAIL_BODY_ID where task.status_ = 0  or (task.status_ = -1  and task.send_number < "+sendNumber+" ) ");
			//System.out.println("需要发送的邮件个数："+list.size());
			
			String smtp = "";  
		    String from = "";  
		    String to = "";  
		    String copyto = null;
		    String subject = "";  
		    String content = "";  
		    String username="";  
		    String password="";
		    boolean returned = false;
		    
		    List<Map> attachList = null;
		    MailFile files [] = null;
		    
		    for(Map data:list){
		    	smtp = TeeStringUtil.getString(data.get("SMTP_SERVER"));
		    	from = TeeStringUtil.getString(data.get("EMAIL_USER"));
		    	to = TeeStringUtil.getString(data.get("TO_"));
		    	subject = TeeStringUtil.getString(data.get("SUBJECT"));
		    	content = TeeStringUtil.getString(data.get("CONTENT"));
		    	username = TeeStringUtil.getString(data.get("EMAIL_USER"));
		    	password = TeeStringUtil.getString(data.get("EMAIL_PASS"));
		    	
		    	//System.out.println("主键："+data.get("UUID"));
		    	//正在发送
		    	dbUtils.executeUpdate("update WEB_MAIL_SEND_TASK set status_= 2 where uuid='"+data.get("UUID")+"'");
		    	
		    	//获取邮件附件
		    	attachList = dbUtils.queryToMapList(
		    			"select space.space_path as SPACE_PATH,"
		    			+ "attach.* "
		    			+ " from attachment attach,attach_space space where space.sid=attach.attach_space and attach.model='email' and attach.model_Id='"+data.get("MAIL_BODY_ID")+"'");
		    	
		    	files = new MailFile[attachList.size()];
		    	
		    	int index = 0;
		    	for(Map attach:attachList){
		    		MailFile mailFile = new MailFile();
		    		mailFile.setFileName(String.valueOf(attach.get("FILE_NAME")));
		    		mailFile.setFilePath(String.valueOf(attach.get("SPACE_PATH")+"/"+attach.get("MODEL")+"/"+attach.get("ATTACHMENT_PATH")+"/"+attach.get("ATTACHMENT_NAME")));
		    		
		    		File file = new File(mailFile.getFilePath());
		    		//如果附件超过所上传的最大文件，则禁止上传该文件
		    		if(file.length()>(TeeSysProps.getLong("WEB_MAIL_UPLOAD_LIMIT")*1024*1024)){
		    			continue;
		    		}
		    		
		    		files[index++] = mailFile;
		    	}
		    /*	//发送邮件
		    	returned = WebMailUtil.send(smtp, from, to, copyto, subject, content, username, password, files);
		    	if(returned==true){
		    		dbUtils.executeUpdate("update WEB_MAIL_SEND_TASK set status_= 1 where uuid='"+data.get("UUID")+"'");
		    	}else{
		    		dbUtils.executeUpdate("update WEB_MAIL_SEND_TASK set status_= -1 where uuid='"+data.get("UUID")+"'");
		    	}*/
		    	
		    	Runnable webMailSendTimerProcess=new TeeWebMailSendTimerProcessor(smtp,from,to,copyto,subject
		        		,content,username,password,returned,data,files);
		    	TeeWebMailSendTimerThreadPool.getInstance().execute(webMailSendTimerProcess);
		    }
			
			dbConn.setAutoCommit(false);
		}catch(Exception e){
//			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(dbConn);
		}
	}
}
