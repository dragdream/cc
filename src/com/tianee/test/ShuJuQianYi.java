package com.tianee.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tianee.webframe.util.str.TeeStringUtil;
public class ShuJuQianYi {
	public static void main(String[] args) throws Exception {
		//insertToNumber();
		getInsert();
	}
	
	//添加    sms_body  sms
	public static void insertToNumber() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/oaop1?characterEncoding=UTF-8","root","oaop2014");
		Statement statement = conn.createStatement();
		ResultSet rs=null;
		PreparedStatement ps=null;
		List<Map<String,Object>> list = getMessAges();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				int fromId = TeeStringUtil.getInteger(map.get("fromId"),0);
				int deleteFlag = TeeStringUtil.getInteger(map.get("deleteFlag"),0);
				String time = TeeStringUtil.getString(map.get("sendTime")+"000");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
			    String sendTime = df.format(Long.parseLong(time)); 
				String content = TeeStringUtil.getString(map.get("content"));
				int toId = TeeStringUtil.getInteger(map.get("toId"),0);
				int remindFlag = TeeStringUtil.getInteger(map.get("remindFlag"),0);
				String bodyUuid = getUUID();
			    String sql="insert into sms_body set UUID='"+bodyUuid+"',content='"+content+"',DEL_FLAG="+deleteFlag+",from_id="+fromId+",send_flag=1,send_time='"+sendTime+"'";
				ps= conn.prepareStatement(sql);
				ps.executeUpdate();
				
				String smsUuid = getUUID();
				String sql2="insert into sms set UUID='"+smsUuid+"',to_id="+toId+",remind_flag="+remindFlag+",delete_flag="+deleteFlag+",body_id='"+bodyUuid+"'";
				ps= conn.prepareStatement(sql2);
				ps.executeUpdate();
				
				System.out.println(bodyUuid+"        "+smsUuid);
			}
		}
	}
	
	public static List<Map<String,Object>> getMessAges() throws Exception{
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.31.150:3336/TD_OA?characterEncoding=UTF-8","root","*HM2Ll!#7VCg1K_19F#3@tJ");
		Statement statement = conn.createStatement();
		String sql="select * from message";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("fromId", rs.getObject("FROM_UID"));
			map.put("deleteFlag", rs.getObject("DELETE_FLAG"));
			map.put("sendTime", rs.getObject("SEND_TIME"));
			map.put("content", rs.getObject("CONTENT"));
			
			map.put("toId", rs.getObject("TO_UID"));
			map.put("remindFlag", rs.getObject("REMIND_FLAG"));
		    listMap.add(map);  
		}
		return listMap;
	}
	
	  public static String getUUID() {  
	        return UUID.randomUUID().toString().replace("-", "");  
	    }  
	
	// 添加 email  email_body表
	public static void getInsert() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/oaop1?characterEncoding=UTF-8","root","oaop2014");
		Statement statement = conn.createStatement();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Map<String,Object>> mapList = getMapList2();
		if(mapList!=null && mapList.size()>0){
			for(int i=0;i<mapList.size();i++){
				Map<String, Object> map = mapList.get(i);
				  String content = TeeStringUtil.getString(map.get("content"));//邮件内容 
				  String compressContent = TeeStringUtil.getString(map.get("compressContent"));// 压缩后的邮件内容
				  String fromWebMail = TeeStringUtil.getString(map.get("fromWebMail"));// 从哪个外部邮箱发来
				  String important = TeeStringUtil.getString(map.get("important"));// 重要程度 空 - 一般邮件 ,1 - 重要 ,2 - 非常重要
				  String emailLevel = TeeStringUtil.getString(map.get("emailLevel"));// 邮件级别   系统编码
				  String sendFlag = TeeStringUtil.getString(map.get("sendFlag"));// 是否已发送 0-未发送 1-已发送
				  String smsRemind = TeeStringUtil.getString(map.get("smsRemind"));// 是否使用短信提醒 0-不提醒 1-提醒
				  String subject = TeeStringUtil.getString(map.get("subject"));// 邮件标题
				  String toWebmail = TeeStringUtil.getString(map.get("toWebmail"));// 外部收件人邮箱串 ,用逗号分隔
				  int bodySid = TeeStringUtil.getInteger(map.get("bodySid"), 0);
				  int length=0;
				  if(toWebmail!=null && !"".equals(toWebmail)){
					  String[] split = toWebmail.split(",");
					  length = split.length;
				  }
				  String fromuser = TeeStringUtil.getString(map.get("fromuser"));// 发件人
				  String time = TeeStringUtil.getString(map.get("sendTime")+"000");
				  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
			      String dateTime = df.format(Long.parseLong(time)); 
				  ResultSet set = statement.executeQuery("select * from person where USER_ID='"+fromuser+"'");
                  int sid=0;
                  String userName="";
				  if(set.next()){
                	  sid = TeeStringUtil.getInteger(set.getString("UUID"),0);
                	  userName=set.getString("USER_NAME");
                  }
				  set.close();
				  String blindCarbonCopy = TeeStringUtil.getString(map.get("blindCarbonCopy"));// 密送
				  String blindStr="";
				  if(blindCarbonCopy!=null && !"".equals(blindCarbonCopy)){
					  String[] split = blindCarbonCopy.split(",");
					  for(int j=0;j<split.length;j++){
						  ResultSet set2 = statement.executeQuery("select * from person where USER_ID='"+split[j]+"'");
						  if(set2.next()){
							  blindStr+=set2.getString("UUID")+",";
		                  }
						  set2.close();
					  }
					  if(blindStr!=null && !"".equals(blindStr)){
						  blindStr=blindStr.substring(0, blindStr.length()-1);
					  }
				  }
				  String carbonCopy = TeeStringUtil.getString(map.get("carbonCopy"));// 抄送
				  String carbonCopyStr="";
				  if(carbonCopy!=null && !"".equals(carbonCopy)){
					  String[] split = carbonCopy.split(",");
					  for(int j=0;j<split.length;j++){
						  ResultSet set2 = statement.executeQuery("select * from person where USER_ID='"+split[j]+"'");
						  if(set2.next()){
							  carbonCopyStr+=set2.getString("UUID")+",";
		                  }
						  set2.close();
					  }
					  if(carbonCopyStr!=null && !"".equals(carbonCopyStr)){
						  carbonCopyStr=carbonCopyStr.substring(0, carbonCopyStr.length()-1);
					  }
				  }
				  String recipient = TeeStringUtil.getString(map.get("recipient"));// 被发送人
				  String recipientStr="";
				  if(recipient!=null && !"".equals(recipient)){
					  String[] split = recipient.split(",");
					  for(int j=0;j<split.length;j++){
						  ResultSet set2 = statement.executeQuery("select * from person where USER_ID='"+split[j]+"'");
						  if(set2.next()){
							  recipientStr+=set2.getString("UUID")+",";
		                  }
						  set2.close();
					  }
					  if(recipientStr!=null && !"".equals(recipientStr)){
						  recipientStr=recipientStr.substring(0, recipientStr.length()-1);
					  }
				  }
				  int integer = TeeStringUtil.getInteger(map.get("ifWebMail"),0);// 是否外部文件
				  String sql="insert into mail_body set IF_WEBMAIL="+integer+",RECIPIENT='"+recipientStr+"',CARBON_COPY='"+carbonCopyStr+"',blind_Carbon_Copy='"+blindStr+"'"
				  		+ ",FROM_ID="+sid+",TO_WEBMAIL='"+toWebmail+"',SUBJECT='"+subject+"',SMS_REMIND='"+smsRemind+"',SEND_FLAG='"+sendFlag+"',SEND_TIME='"+dateTime+"'"
				  				+ ",EMAIL_JJCD='"+emailLevel+"',IMPORTANT='"+important+"',FROM_WEB_MAIL='"+fromWebMail+"',COMPRESS_CONTENT='"+compressContent+"'"
				  						+ ",CONTENT='"+content+"',pub_type=0,WEBMAIL_COUNT="+length+",NAME_ORDER='"+userName+"'";
				ps= conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				int a=ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				rs.next();
				int id=rs.getInt(1);
				System.out.println(a+"    "+id);
				
				List<Map<String,String>> mapList2 = getMapList(bodySid);
				if(mapList2!=null && mapList2.size()>0){
					for(int j=0;j<mapList2.size();j++){
						Map<String, String> map2 = mapList2.get(i);
						String toUser = map2.get("toUser");
						ResultSet set3 = statement.executeQuery("select * from person where USER_ID='"+toUser+"'");
						int userId=0;
						if(set3.next()){
							userId = TeeStringUtil.getInteger(set3.getString("UUID"),0);
						}
						int receipt = TeeStringUtil.getInteger(map.get("receipt"),0);
						int readFlag = TeeStringUtil.getInteger(map.get("readFlag"),0);
						int mailBody = TeeStringUtil.getInteger(map.get("mailBody"),0);
						int mailBox = TeeStringUtil.getInteger(map.get("mailBox"),0);
						int deleteFlag = TeeStringUtil.getInteger(map.get("deleteFlag"),0);
						sql="insert into mail set TO_ID="+userId+",READ_FLAG="+readFlag+",DELETE_FLAG="+deleteFlag+",BOX_ID="+mailBox+",BODY_ID="+id+",RECEIPT="+receipt;
						ps= conn.prepareStatement(sql);
						ps.executeUpdate();
					}
				}
				
			}
		}
        ps.close();
		conn.close();
	}
	
	
	public static List<Map<String,String>> getMapList(int bodySid) throws Exception{
		List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.31.150:3336/TD_OA?characterEncoding=UTF-8","root","*HM2Ll!#7VCg1K_19F#3@tJ");
		Statement statement = conn.createStatement();
		String sql="select * from email where BODY_ID="+bodySid;
		ResultSet rs = statement.executeQuery(sql);
		  while(rs.next()){
			  Map<String,String> map=new HashMap<String,String>();
			  map.put("toUser", rs.getString("TO_ID"));//收件人
			 // map.put("receiveType", rs.getString("TD_ID"));//接收类型
			  map.put("receipt", rs.getString("RECEIPT"));//是否请求阅读收条
			  map.put("readFlag", rs.getString("READ_FLAG"));//邮件读取标记
			  map.put("mailBody", rs.getString("BODY_ID"));//邮件体ID
			  map.put("mailBox", rs.getString("BOX_ID"));//邮件箱ID
			  map.put("deleteFlag", rs.getString("DELETE_FLAG"));//邮件删除标记 
			  listMap.add(map);
		  }
		  rs.close();
		  conn.close();
		  return listMap;
	}
	
	
	//email表
//	public static void insert2() throws Exception{
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/oaop1?characterEncoding=UTF-8","root","oaop2014");
//		Statement statement = conn.createStatement();
//		PreparedStatement ps=null;
//		List<Map<String,String>> mapList = getMapList();
//		if(mapList!=null && mapList.size()>0){
//			for(int i=0;i<mapList.size();i++){
//				Map<String, String> map = mapList.get(i);
//				String toUser = map.get("toUser");
//				ResultSet set = statement.executeQuery("select * from person where USER_ID='"+toUser+"'");
//				int sid=0;
//				if(set.next()){
//					sid = TeeStringUtil.getInteger(set.getString("UUID"),0);
//				}
//				System.out.println(sid+"---"+toUser);
//				//int receiveType = TeeStringUtil.getInteger(map.get("receiveType"),0);
//				int receipt = TeeStringUtil.getInteger(map.get("receipt"),0);
//				int readFlag = TeeStringUtil.getInteger(map.get("readFlag"),0);
//				int mailBody = TeeStringUtil.getInteger(map.get("mailBody"),0);
//				int mailBox = TeeStringUtil.getInteger(map.get("mailBox"),0);
//				int deleteFlag = TeeStringUtil.getInteger(map.get("deleteFlag"),0);
//				String sql="insert into mail set TO_ID="+sid+",READ_FLAG="+readFlag+",DELETE_FLAG="+deleteFlag+",BOX_ID="+mailBox+",BODY_ID="+mailBody+",RECEIPT="+receipt;
//				ps= conn.prepareStatement(sql);
//				int a=ps.executeUpdate();
//				System.out.println(a);
//				
//			}
//		}
//        ps.close();
//		conn.close();
//	}
	
	public static List<Map<String,Object>> getMapList2() throws Exception{
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.31.150:3336/TD_OA?characterEncoding=UTF-8","root","*HM2Ll!#7VCg1K_19F#3@tJ");
		Statement statement = conn.createStatement();
		String sql="select * from email_body";
		ResultSet rs = statement.executeQuery(sql);
		  while(rs.next()){
			  Map<String,Object> map=new HashMap<String,Object>();
			 // map.put("webmailCount", rs.getString("TD_ID"));//外部收件人数（用于保存i）
			 // map.put("pubType", rs.getString("TD_ID"));//收件人规则：  0=指定人员  1=所有人员
			 // map.put("webmailHtml", rs.getString("TD_ID"));//外部邮件html
			  map.put("fromuser", rs.getString("FROM_ID"));// 发件人
			  //map.put("nameOrder", rs.getString("TD_ID"));//
			  map.put("content", rs.getString("CONTENT"));//邮件内容 
			  map.put("compressContent", rs.getString("COMPRESS_CONTENT"));// 压缩后的邮件内容
			  map.put("fromWebMail", rs.getString("FROM_WEBMAIL"));// 从哪个外部邮箱发来
			  map.put("important", rs.getString("IMPORTANT"));// 重要程度 空 - 一般邮件 ,1 - 重要 ,2 - 非常重要
			  map.put("emailLevel", rs.getString("SECRET_LEVEL"));// 邮件级别   系统编码
			  map.put("sendFlag", rs.getString("SEND_FLAG"));// 是否已发送 0-未发送 1-已发送
			  map.put("sendTime", rs.getString("SEND_TIME"));// 发送时间
			  map.put("smsRemind", rs.getString("SMS_REMIND"));// 是否使用短信提醒 0-不提醒 1-提醒
			  map.put("subject", rs.getString("SUBJECT"));// 邮件标题
			  map.put("toWebmail", rs.getString("TO_WEBMAIL"));// 外部收件人邮箱串 ,用逗号分隔
			  map.put("blindCarbonCopy", rs.getString("SECRET_TO_ID"));// 密送
			  map.put("carbonCopy", rs.getString("COPY_TO_ID"));// 抄送
			  map.put("recipient", rs.getString("TO_ID2"));// 被发送人
			  map.put("ifWebMail", rs.getString("IS_WF"));// 是否外部文件
			  map.put("bodySid", rs.getObject("BODY_ID"));
			  listMap.add(map);
		  }
		  rs.close();
		  conn.close();
		  return listMap;
	}
	
	
	//
	

	

	
	

	
}
