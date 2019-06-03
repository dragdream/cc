package com.tianee.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.webframe.util.db.TeeDbUtility;

public class ExpressionTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		importData4Sqlserver();
//		importData4Oracle();
		//批量替换文件索引
//		File file = new File("d:\\core");
//		replaceJava(file);
//		importData();
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection mysqlConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/tttt?characterEncoding=UTF-8","root","oaop2014");
//		mysqlConn.setAutoCommit(false);
//		PreparedStatement stmt = mysqlConn.prepareStatement("insert into t values(?)");
//		BufferedInputStream r = new BufferedInputStream(new FileInputStream("d:\\1.txt"));
//		byte b[] = new byte[r.available()];
//		r.read(b);
//		String text = new String(b);
//		r.close();
//		
//		for(int j=0;j<10000;j++){
//			for(int i=0;i<100;i++){
//				stmt.setString(1, (j*i)+"系统管理部门"+UUID.randomUUID().toString());
//				stmt.addBatch();
//			}
//			mysqlConn.commit();
//			stmt.executeBatch();
//			System.out.println(j);
//		}
//		mysqlConn.close();
//		String expression = "if(true){a=12;foobar = a;}else{foobar=2;}";
//		Map vars = new HashMap();
//		vars.put("foobar", "192");
//		MVEL.eval(expression, vars);
//		System.out.println(vars);
		
//		Expression ex = ExpressionFactory.createExpression("");
//		JexlContext jc = JexlHelper.createContext();
//		System.out.println(ex.evaluate(jc));
		
//		TeeRegexpAnalyser an = new TeeRegexpAnalyser("{1} and ( {2} or {2})");
//		String text = an.replace(new String[]{"\\{[0-9]+\\}"}, new TeeExpFetcher() {
//			
//			@Override
//			public String parse(String pattern) {
//				// TODO Auto-generated method stub
//				int itemId = TeeStringUtil.getInteger(pattern.replaceAll("[\\{\\}]", ""), 0);
//				
//				return itemId+"";
//			}
//		});
//		System.out.println(text);
//		YUICompressor.main(new String[]{"c:\\1.js","-o","c:\\1.js","--charset","utf-8"});
	}
	
	public static void replaceJava(File file) throws Exception
	 {
	  if(file.isFile() || file.list().length == 0)
	  {
	   if(file.getName().endsWith(".java")){
		   StringBuffer buffer = new StringBuffer();
		   BufferedReader reader = new BufferedReader(new FileReader(file));//读出每一行，追加到内存中
		   String line = null;
		   int lineCount = 1;
		   while((line = reader.readLine())!=null){
			   if(lineCount==1 && line.indexOf(".bean")==-1){//如果第一行中不包含bean的话，则跳出
				  break;
			   }
			   if(lineCount==2){//如果是第二行的话，则引入一个包~
				   buffer.append("import org.hibernate.annotations.Index;\r\n");
			   }
			   buffer.append(line+"\r\n");
			   if(line.indexOf("@ManyToOne")!=-1){
				   buffer.append("	@Index(name=\"IDX"+UUID.randomUUID().toString().replace("-", "").substring(0,27)+"\")"+"\r\n");
			   }
			   lineCount++;
		   }
		   reader.close();//关闭流
		   
		   if(lineCount!=1){//如果全部处理过，则进行替换文件
			   file.delete();//删除文件
			   
			   BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			   writer.write(buffer.toString());
			   writer.close();
		   }
	   }
	  }
	  else
	  {
	   File[] files = file.listFiles();
	   for(File f : files)
	   {
		replaceJava(f);
	   }
	   }
	 }

	/**
	 * 导入数据至sqlserver
	 * @throws Exception
	 */
	public static void importData4Sqlserver() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection mysqlConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oaop?characterEncoding=UTF-8","root","oaop2014");
		Connection oracleConn = DriverManager.getConnection("jdbc:sqlserver://192.168.3.218:1433;DatabaseName=oaop","sa","oaop2014");
		DbUtils dbUtils = new DbUtils(mysqlConn);
		DbUtils dbUtils1 = new DbUtils(oracleConn);
		oracleConn.setAutoCommit(true);
		Statement stmt = mysqlConn.createStatement();
		ResultSet rs = stmt.executeQuery("show tables");
		List<String> tables = new ArrayList<String>();
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		rs.close();
		
		StringBuffer sb = new StringBuffer();
		List<String> fields = new ArrayList<String>();
		
		//创建额外附加表
		try{
			dbUtils1.executeUpdate("CREATE TABLE sys_date(date varchar(255))");
			dbUtils1.executeUpdate("CREATE TABLE sys_month(MONTH varchar(255),MONTH1 varchar(255),MONTH2 varchar(255),MONTH3 varchar(255))");
			dbUtils1.executeUpdate("CREATE TABLE sys_tmp(A varchar(255))");
			dbUtils1.executeUpdate("CREATE TABLE sys_year(year varchar(255))");
		}catch(Exception ex){
			
		}
		
		List<Map> datas = null;
		List params = new ArrayList();
		for(String tableName:tables){
//			if(!tableName.toLowerCase().equals("bis_data_fetch")){
//				continue;
//			}
			
			datas = dbUtils.queryToMapList("select * from "+tableName);
			fields.clear();
			rs = stmt.executeQuery("desc "+tableName);
			while(rs.next()){
				fields.add(rs.getString(1));
			}
			sb.delete(0, sb.length());
			sb.append("insert into "+tableName+"(");
			for(int i=0;i<fields.size();i++){
				sb.append(fields.get(i));
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			
			sb.append(") values(");
			for(int i=0;i<fields.size();i++){
				sb.append("?");
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			sb.append(")");
			int count = 1;
//			try{
//				dbUtils1.executeUpdate("set IDENTITY_INSERT "+tableName+" on;", null);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			for(Map data:datas){
				count++;
				params.clear();
				for(int i=0;i<fields.size();i++){
					params.add(data.get(fields.get(i)));
				}
				try{
					dbUtils1.executeUpdate("set IDENTITY_INSERT "+tableName+" on;"+sb.toString()+";",params.toArray());
				}catch(Exception e){
					e.printStackTrace();
					try{
						dbUtils1.executeUpdate(sb.toString(), params.toArray());
					}catch(Exception ex){
						ex.printStackTrace();
					}
//					e.printStackTrace();
				}
//				if(count%100==0){
//					oracleConn.commit();
//				}
			}
//			oracleConn.commit();
			rs.close();
		}
	}
	
	
	/**
	 * 导入数据至oracle
	 * @throws Exception
	 */
	public static void importData4Oracle() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection mysqlConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oaop?characterEncoding=UTF-8","root","oaop2014");
		Connection oracleConn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.3.218:1521:orcl","oaop","oaop2014");
		DbUtils dbUtils = new DbUtils(mysqlConn);
		DbUtils dbUtils1 = new DbUtils(oracleConn);
		oracleConn.setAutoCommit(true);
		Statement stmt = mysqlConn.createStatement();
		ResultSet rs = stmt.executeQuery("show tables");
		List<String> tables = new ArrayList<String>();
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		rs.close();
		
		StringBuffer sb = new StringBuffer();
		List<String> fields = new ArrayList<String>();
		
		//创建额外附加表
		try{
			dbUtils1.executeUpdate("CREATE TABLE sys_date(date varchar(255))");
			dbUtils1.executeUpdate("CREATE TABLE sys_month(MONTH varchar(255),MONTH1 varchar(255),MONTH2 varchar(255),MONTH3 varchar(255))");
			dbUtils1.executeUpdate("CREATE TABLE sys_tmp(A varchar(255))");
			dbUtils1.executeUpdate("CREATE TABLE sys_year(year varchar(255))");
		}catch(Exception ex){
			
		}
		
		
		List<Map> datas = null;
		List params = new ArrayList();
		for(String tableName:tables){
//			if(!tableName.toLowerCase().equals("bis_data_fetch")){
//				continue;
//			}
			
			datas = dbUtils.queryToMapList("select * from "+tableName);
			fields.clear();
			rs = stmt.executeQuery("desc "+tableName);
			while(rs.next()){
				fields.add(rs.getString(1));
			}
			sb.delete(0, sb.length());
			sb.append("insert into "+tableName+"(");
			for(int i=0;i<fields.size();i++){
				sb.append(fields.get(i));
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			
			sb.append(") values(");
			for(int i=0;i<fields.size();i++){
				sb.append("?");
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			sb.append(")");
			int count = 1;
			try{
				dbUtils1.queryToMap("select count(*) from "+tableName);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			for(Map data:datas){
				count++;
				params.clear();
				for(int i=0;i<fields.size();i++){
					params.add(data.get(fields.get(i)));
				}
				try{
					dbUtils1.executeUpdate(sb.toString(), params.toArray());
				}catch(Exception e){
					e.printStackTrace();
				}
//				if(count%100==0){
//					oracleConn.commit();
//				}
			}
			
//			oracleConn.commit();
			
		}
		
		/**
		 * 将序列设置为4000，除了RUN_ID流水号
		 */
		List<String> sequences = getSequences();//获取所有序列描述
		for(String seq:sequences){
			//如果是流程ID，则跳过
			if(seq.toUpperCase().equals("FLOW_RUN_SEQ")){
				continue;
			}
			//移除之前的序列
			try{
				dbUtils1.executeUpdate("drop sequence "+seq);
			}catch(Exception e){
				
			}
			//更新序列值至4000
			try{
				dbUtils1.executeUpdate("CREATE SEQUENCE "+seq+" INCREMENT by 1 start with 4000 MAXVALUE 9223372036854775807 NOCYCLE nocache");
			}catch(Exception e){
				
			}
		}
	}
	
	
	
	/**
	 * 导入数据至达梦
	 * @throws Exception
	 */
	public static void importData4Dameng() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("dm.jdbc.driver.DmDriver");
		Connection mysqlConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/oaop?characterEncoding=UTF-8","root","oaop2014");
		Connection oracleConn = DriverManager.getConnection("jdbc:dm://localhost:5236","root","oaop2014");
		DbUtils dbUtils = new DbUtils(mysqlConn);
		DbUtils dbUtils1 = new DbUtils(oracleConn);
		oracleConn.setAutoCommit(false);
		Statement stmt = mysqlConn.createStatement();
		ResultSet rs = stmt.executeQuery("show tables");
		List<String> tables = new ArrayList<String>();
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		rs.close();
		
		StringBuffer sb = new StringBuffer();
		List<String> fields = new ArrayList<String>();
		
		List<Map> datas = null;
		List params = new ArrayList();
		for(String tableName:tables){
//			if(!tableName.toLowerCase().equals("bis_data_fetch")){
//				continue;
//			}
			
			datas = dbUtils.queryToMapList("select * from "+tableName);
			fields.clear();
			rs = stmt.executeQuery("desc "+tableName);
			while(rs.next()){
				fields.add(rs.getString(1));
			}
			sb.delete(0, sb.length());
			sb.append("insert into "+tableName+"(");
			for(int i=0;i<fields.size();i++){
				sb.append(fields.get(i));
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			
			sb.append(") values(");
			for(int i=0;i<fields.size();i++){
				sb.append("?");
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			sb.append(")");
			int count = 1;
//			try{
//				dbUtils1.executeUpdate("set IDENTITY_INSERT "+tableName+" on;", null);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			for(Map data:datas){
				count++;
				params.clear();
				//检测表是否存在
				try{
					dbUtils1.queryToMapList("select * from "+tableName);
				}catch(Exception e){break;}
				for(int i=0;i<fields.size();i++){
					params.add(data.get(fields.get(i)));
				}
				try{
					dbUtils1.executeUpdate("set IDENTITY_INSERT "+tableName+" on;"+sb.toString(), params.toArray());
				}catch(Exception e){
					try{
						dbUtils1.executeUpdate(sb.toString(), params.toArray());
					}catch(Exception ex){
						ex.printStackTrace();
					}
					e.printStackTrace();
				}
				if(count%100==0){
					oracleConn.commit();
				}
			}
			oracleConn.commit();
			rs.close();
		}
	}
	
	
	
	/**
	 * 导入数据至kingbase
	 * @throws Exception
	 */
	public static void importData4Kingbase() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.kingbase.Driver");
		Connection mysqlConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oaop?characterEncoding=UTF-8","root","oaop2014");
		Connection oracleConn = DriverManager.getConnection("jdbc:kingbase://localhost:54321/oaop","oaop","oaop2014");
		DbUtils dbUtils = new DbUtils(mysqlConn);
		DbUtils dbUtils1 = new DbUtils(oracleConn);
		oracleConn.setAutoCommit(true);
		Statement stmt = mysqlConn.createStatement();
		ResultSet rs = stmt.executeQuery("show tables");
		List<String> tables = new ArrayList<String>();
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		rs.close();
		
		StringBuffer sb = new StringBuffer();
		List<String> fields = new ArrayList<String>();
		
		//创建额外附加表
		dbUtils1.executeUpdate("CREATE TABLE sys_date(date varchar(255))");
		dbUtils1.executeUpdate("CREATE TABLE sys_month(MONTH varchar(255),MONTH1 varchar(255),MONTH2 varchar(255),MONTH3 varchar(255))");
		dbUtils1.executeUpdate("CREATE TABLE sys_tmp(A varchar(255))");
		dbUtils1.executeUpdate("CREATE TABLE sys_year(year varchar(255))");
		
		List<Map> datas = null;
		List params = new ArrayList();
		for(String tableName:tables){
//			if(!tableName.toLowerCase().equals("bis_data_fetch")){
//				continue;
//			}
			
			datas = dbUtils.queryToMapList("select * from "+tableName);
			fields.clear();
			rs = stmt.executeQuery("desc "+tableName);
			while(rs.next()){
				fields.add(rs.getString(1));
			}
			sb.delete(0, sb.length());
			sb.append("insert into "+tableName+"(");
			for(int i=0;i<fields.size();i++){
				sb.append(fields.get(i));
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			
			sb.append(") values(");
			for(int i=0;i<fields.size();i++){
				sb.append("?");
				if(i!=fields.size()-1){
					sb.append(",");
				}
			}
			sb.append(")");
			int count = 1;
			try{
				dbUtils1.queryToMap("select count(*) from "+tableName);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			for(Map data:datas){
				count++;
				params.clear();
				for(int i=0;i<fields.size();i++){
					params.add(data.get(fields.get(i)));
				}
				try{
					dbUtils1.executeUpdate(sb.toString(), params.toArray());
				}catch(Exception e){
					e.printStackTrace();
				}
//				if(count%100==0){
//					oracleConn.commit();
//				}
			}
			
//			oracleConn.commit();
			
			rs.close();
		}
		
		/**
		 * 将序列设置为4000，除了RUN_ID流水号
		 */
		List<String> sequences = getSequences();//获取所有序列描述
		for(String seq:sequences){
			//如果是流程ID，则跳过
			if(seq.toUpperCase().equals("FLOW_RUN_SEQ")){
				continue;
			}
			//移除之前的序列
			try{
				dbUtils1.executeUpdate("drop sequence "+seq);
			}catch(Exception e){
				
			}
			//更新序列值至4000
			try{
				dbUtils1.executeUpdate("CREATE SEQUENCE "+seq+" INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 4000 CACHE 1 NO CYCLE");
			}catch(Exception e){
				
			}
		}
	}
	
	/**
	 * 获取所有序列名称
	 * @return
	 */
	public static List<String> getSequences(){
		List<String> list = new ArrayList();
		list.addAll(scanFiles(new File("E:\\personaliy\\workspace_product\\oaop\\core")));
		list.addAll(scanFiles(new File("E:\\personaliy\\workspace_product\\oaop\\src")));
		return list;
	}
	
	private static List<String> scanFiles(File file){
		List<String> list = new ArrayList<String>();
		if(file.isDirectory()){
			File files[] = file.listFiles();
			for(File f:files){
				list.addAll(scanFiles(f));
			}
		}else if(file.getName().endsWith("java") && !file.getName().startsWith("ExpressionTest")){//文件读取
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String line = null;
				while((line=bufferedReader.readLine())!=null){
					int index = line.indexOf("sequenceName=");
					if(index!=-1){
						int start = index+14;
						int end = line.indexOf("\")");
						list.add(line.substring(start,end));
						break;
					}
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return list;
	}
}
