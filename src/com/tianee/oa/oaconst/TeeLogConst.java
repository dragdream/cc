package com.tianee.oa.oaconst;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 日志通用常量定义
 * 
 * @author cxt
 * 
 */
public class TeeLogConst {

	
	
	/**
	 * 日志编码设定
	 */
	public static final String[] LOGIN_LOG = {"01","系统登录"};

	public static final String[] WORKFLOW_LOG = {"02","工作流日志"};
	
	public static final String[] EMAIL_LOG = {"03","邮件日志"};
	
	public static final String[] ORG_LOG = {"04","组织管理日志"};
	
	public static final String[] SYS_LOG = {"05","系统日志"};
	
	public static final String[] STARTUP_LOG = {"06","服务启动日志"};
	
	public static final String[] SHUTDOWN_LOG = {"07","服务停止日志"};
	
	public static final String dbms = TeeSysProps.getString("dialect");
	
	public static Calendar cal(){
		Date date=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public static String date(Date date){
		//Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = sdf.format(date);

		return dateTime;
	}
	
	public static String STARTUP_LOG_SEQ(){
		String dateTime = "";
		if(dbms.equals("mysql")){
			dateTime = "'"+date(new Date())+"'";
		}else if(dbms.equals("oracle")){
			dateTime = "to_date('"+date(new Date())+"','YYYY-MM-DD HH24:MI:SS')";
		}else if(dbms.equals("sqlserver")){
			dateTime = "GETDATE()";
		}else if(dbms.equals("kingbase")){
			dateTime = "NOW()";
		}else if(dbms.equals("dameng")){
			dateTime = "NOW()";
		}
		
		String remark = "服务启动";
		
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
		
		String STARTUP_LOG_SEQ = "insert into SYS_LOG(uuid,time,remark,type,error_flag) values ('"+uuid+"',"+dateTime+",'"+remark+"','000A',0)";
		
		System.out.println(STARTUP_LOG_SEQ);
		
		return STARTUP_LOG_SEQ;
	}
	
	public static String SHUTDOWN_LOG_SEQ(){
		
		String dateTime = "";
		if(dbms.equals("mysql")){
			dateTime = "'"+date(new Date())+"'";
		}else if(dbms.equals("oracle")){
			dateTime = "to_date('"+date(new Date())+"','YYYY-MM-DD HH24:MI:SS')";
		}else if(dbms.equals("sqlserver")){
			dateTime = "getdate()";
		}else if(dbms.equals("kingbase")){
			dateTime = "now()";
		}
		
		String remark = "服务结束";
		
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
		
		String SHUTDOWN_LOG_SEQ = "insert into SYS_LOG(uuid,time,remark,type) values ('"+uuid+"',"+dateTime+",'"+remark+"','"+SHUTDOWN_LOG[1]+"')";
		
		//System.out.println(SHUTDOWN_LOG_SEQ);
		
		return SHUTDOWN_LOG_SEQ;
	}
	
	public static String LOG_GROUP(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String dateTime = sdf.format(new Date());
		String LOG_GROUP = "";
		if(dbms.equals("mysql")){
			LOG_GROUP = "SELECT DATE_FORMAT( time, '%Y-%m-%d' ) as time , COUNT( * ) FROM sys_log where month(time) <> month(now()) GROUP BY DATE_FORMAT( time, '%Y-%m-%d' )";
		}else if(dbms.equals("oracle")){
			LOG_GROUP = "SELECT (to_char(time,'yyyymm')) as time , COUNT( * ) FROM sys_log where to_char(time,'yyyymm') <> '"+dateTime+"' GROUP BY  (to_char(time,'yyyymm'))";
		}else if(dbms.equals("sqlserver")){
			LOG_GROUP = "SELECT (CONVERT(varchar(6), time, 112)) as time , COUNT( * ) FROM sys_log where CONVERT(varchar(6), time, 112) <> '"+dateTime+"' GROUP BY  (CONVERT(varchar(6), time, 112))";
		}else if(dbms.equals("kingbase")){
			LOG_GROUP = "SELECT DATE_FORMAT(time,'%Y%m') as time , COUNT( * ) FROM sys_log where DATE_FORMAT(time,'%Y%m') <> '"+dateTime+"' GROUP BY  DATE_FORMAT(time,'%Y%m')";
		}else if(dbms.equals("dameng")){
			LOG_GROUP = "SELECT to_char(time,'yyyymm') as time , COUNT( * ) FROM sys_log where to_char(time,'yyyymm') <> '"+dateTime+"' GROUP BY  to_char(time,'yyyymm')";
		}
		//System.out.println(LOG_GROUP);
		
		return LOG_GROUP;
	}
	
	public static String DELETE_SQL(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String dateTime = sdf.format(new Date());
		String DELETE_SQL = "";
		if(dbms.equals("mysql")){
			DELETE_SQL = "delete from SYS_LOG where month(time)<>month(now())";
		}else if(dbms.equals("oracle")){
			DELETE_SQL = "delete from SYS_LOG where to_char(time,'yyyymm') <> '"+dateTime+"'";
		}else if(dbms.equals("sqlserver")){
			DELETE_SQL = "delete from SYS_LOG where (CONVERT(varchar(6), time, 112)) <> '"+dateTime+"'";
		}else if(dbms.equals("kingbase")){
			DELETE_SQL = "delete from SYS_LOG where DATE_FORMAT(time,'%Y%m') <> '"+dateTime+"'";
		}else if(dbms.equals("dameng")){
			DELETE_SQL = "delete from SYS_LOG where to_char(time,'yyyymm') <> '"+dateTime+"'";
		}
		//System.out.println(DELETE_SQL);
		
		return DELETE_SQL;
	}
	
	
	public static String QUERY(String tableName,String time){
		
		String query = "";
		if(dbms.equals("mysql")){
			query = "insert into " + tableName +" select * from SYS_LOG where month(time)=month('"+time+"')";
		}else if(dbms.equals("oracle")){
			query = "insert into " + tableName +" select * from SYS_LOG where to_char(time,'yyyymm')='"+time+"'";
		}else if(dbms.equals("sqlserver")){
			query = "insert into " + tableName +" select * from SYS_LOG where (CONVERT(varchar(6), time, 112))='"+time+"'";
		}else if(dbms.equals("kingbase")){
			query = "insert into " + tableName +" select * from SYS_LOG where DATE_FORMAT(time,'%Y%m')='"+time+"'";
		}
		return query;
	}
	
	
public static String INSERT_ARCHIVES(String tableName){
		
		String INSERT_SQL = "";
		if(dbms.equals("mysql")){
			INSERT_SQL = "insert into archives(table_name) values('"+tableName+"')";
		}else if(dbms.equals("oracle")){
			INSERT_SQL = "insert into archives(sid,table_name) values(archives_seq.nextval,'"+tableName+"')";
		}else if(dbms.equals("sqlserver")){
			INSERT_SQL = "insert into archives(table_name) values('"+tableName+"')";
		}else if(dbms.equals("kingbase")){
			INSERT_SQL = "insert into archives(sid,table_name) values(archives_seq.nextval,'"+tableName+"')";
		}else if(dbms.equals("dameng")){
			INSERT_SQL = "insert into archives(table_name) values('"+tableName+"')";
		}
		
		return INSERT_SQL;
	}
	
}
