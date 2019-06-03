package com.tianee.webframe.util.db;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.tools.ant.filters.StringInputStream;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.global.TeeSysProps;


public class TeeDbUtility {
	
	private static Map<String,ComboPooledDataSource> dataSources = new HashMap();
	
	/**
	 * 日期类型转字符串日期格式2012-02-03
	 * @param field
	 * @return
	 */
	public static String DATE2CHAR_Y_M_D(String dialect,String field){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = "date_format("+field+",'%Y-%m-%d')";
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "to_char("+field+",'yyyy-mm-dd')";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "CONVERT(varchar(100), "+field+", 23)";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "DATE_FORMAT("+field+",'%Y-%m-%d')";
		}
		return render;
	}
	
	public static String DATE2CHAR_D(String dialect,String field){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = "date_format("+field+",'%d')";
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "to_char("+field+",'dd')";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "CONVERT(varchar(100), "+field+", 23)";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "DATE_FORMAT("+field+",'%d')";
		}
		return render;
	}
	
	/**
	 * 字段后字符串拼接
	 * @param field
	 * @return
	 */
	public static String CONCAT_AFTER(String dialect,String field,String appendStr){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = "concat("+field+",'"+appendStr+"')";
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "concat("+field+",'"+appendStr+"')";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "cast("+field+" as char) +'"+appendStr+"'";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "concat("+field+",'"+appendStr+"')";
		}
		return render;
	}
	
	/**
	 * 字段前字符串拼接
	 * @param field
	 * @return
	 */
	public static String CONCAT_BEFORE(String dialect,String field,String appendStr){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = "concat('"+appendStr+"',"+field+")";
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "concat('"+appendStr+"',"+field+")";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "cast("+field+" as char) +'"+appendStr+"'";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "concat('"+appendStr+"',"+field+")";
		}
		return render;
	}
	
	 /**
	   * 日期转换函数封装
	   * @param dateStr
	   * @return
	   */
	  private static String DATE2CHAR_FIELD(String dialect ,String field ) throws SQLException  {
	    if (dialect.equals("sqlserver")) {
	      return "CONVERT(varchar, " + field + ", 20)";
	    }else if (dialect.equals("mysql")) {
	      return "date_format("+field +", \'%Y-%m-%d %H:%i:%S\')";
	    }else if (dialect.equals("oracle")) {
	      return "to_char(" + field + ", \'yyyy-MM-dd hh24:mi:ss\')";
	    }else if (dialect.equals("kingbase")) {
		  return  "DATE_FORMAT("+field+",'%Y-%m-%d %H:%i:%S')"; 
		}else {
	      throw new SQLException("not accepted dbms");
	    }
	  }
	/**
	 * 日期类型转字符串日期格式03
	 * @param field
	 * @return
	 */
	public static String DATE2CHAR_M(String dialect,String field){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = "date_format("+field+",'%m')";
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "to_char("+field+",'mm')";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "CONVERT(varchar(2), "+field+", 1)";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "DATE_FORMAT("+field+",'%m')";
		}
		return render;
	}
	
	/**
	 * 日期类型转字符串日期格式03
	 * @param field
	 * @return
	 */
	public static String DATE2CHAR_Y(String dialect,String field){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = "date_format("+field+",'%Y')";
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "to_char("+field+",'yyyy')";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "CONVERT(varchar(4), "+field+", 23)";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "DATE_FORMAT("+field+",'%Y')";
		}
		return render;
	}
	
	/**
	 * 日期类型转字符串日期格式2012-02
	 * @param field
	 * @return
	 */
	public static String DATE2CHAR_Y_M(String dialect,String field){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = "date_format("+field+",'%Y-%m')";
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "to_char("+field+",'yyyy-mm')";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "CONVERT(varchar(10), "+field+", 120)";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "DATE_FORMAT("+field+",'%Y-%m')";
		}
		return render;
	}
	
	/**
	   * 取得当月筛选条件
	   * @param dbms
	   * @param fieldName
	   * @param fieldValue
	   * @param opt
	   * @return
	   * @throws Exception
	   */
	  public static String getMonthFilter(String dbms, String fieldName, Date date) throws Exception {
	    if (date == null) {
	      date = new Date();
	    }
	    StringBuffer rtBuf = new StringBuffer();
	    String[] dateLimitStr = TeeDateUtil.getMonthLimitStr(date);
	    String wrapFieldName = DATE2CHAR_FIELD( dbms , fieldName);
	    rtBuf.append(wrapFieldName);
	    rtBuf.append(">=");
	    rtBuf.append("\'");
	    rtBuf.append(dateLimitStr[0]);
	    rtBuf.append("\' and ");
	    rtBuf.append(wrapFieldName);
	    rtBuf.append("<=");
	    rtBuf.append("\'");
	    rtBuf.append(dateLimitStr[1]);         
	    rtBuf.append("\'");
	    return rtBuf.toString();
	  }
	  /**
	   * 取得当年筛选条件

	   * @param dbms
	   * @param fieldName
	   * @param fieldValue
	   * @param opt
	   * @return
	   * @throws Exception
	   */
	  public static String getYearFilter(String dbms, String fieldName, Date date) throws Exception {
	    if (date == null) {
	      date = new Date();
	    }
	    StringBuffer rtBuf = new StringBuffer();
	    
	    String[] dateLimitStr = TeeDateUtil.getYearLimitStr(date);
	    
	    String wrapFieldName = DATE2CHAR_FIELD( dbms , fieldName);;
	    rtBuf.append(wrapFieldName);
	    rtBuf.append(">=");
	    rtBuf.append("\'");
	    rtBuf.append(dateLimitStr[0]);
	    rtBuf.append("\' and ");
	    rtBuf.append(wrapFieldName);
	    rtBuf.append("<=");
	    rtBuf.append("\'");
	    rtBuf.append(dateLimitStr[1]);         
	    rtBuf.append("\'");
	    
	    return rtBuf.toString();
	  }
	
	/**
	 * 获取指定年，指定月份的日期字符串分隔，例如  2012-05-01,2012-05-02,……2012-05-31
	 * @return
	 */
	public static String GET_DAY_OF_MONTH_STR(Integer year,Integer month){
		StringBuffer sb = new StringBuffer();
		Calendar first = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		first.set(Calendar.YEAR, year);
		first.set(Calendar.MONTH, month-1);
		first.set(Calendar.DAY_OF_MONTH, first.getActualMinimum(Calendar.DAY_OF_MONTH));//本月1号
		last.set(Calendar.YEAR, year);
		last.set(Calendar.MONTH, month-1);
		last.set(Calendar.DAY_OF_MONTH, first.getActualMaximum(Calendar.DAY_OF_MONTH));//本月最后一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for(;first.compareTo(last)<=0;first.add(Calendar.DAY_OF_MONTH, 1)){
			sb.append("'"+sdf.format(first.getTime())+"'");
			sb.append(",");
		}
		if(sb.length()!=0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	/**
	 * 获取指定年份的月份字符串
	 * @param year
	 * @return
	 */
	public static String GET_MONTH_OF_YEAR_STR(Integer year){
		StringBuffer sb = new StringBuffer();
		Calendar first = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		first.set(Calendar.YEAR, year);
		first.set(Calendar.MONTH, 0);
		first.set(Calendar.DAY_OF_MONTH, first.getActualMinimum(Calendar.DAY_OF_MONTH));//本月1号
		
		last.set(Calendar.YEAR, year);
		last.set(Calendar.MONTH, 11);
		last.set(Calendar.DAY_OF_MONTH, first.getActualMaximum(Calendar.DAY_OF_MONTH));//本月最后一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		for(;first.compareTo(last)<=0;first.add(Calendar.MONTH, 1)){
			sb.append("'"+sdf.format(first.getTime())+"'");
			sb.append(",");
		}
		if(sb.length()!=0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	//返回指定年度的月份
	public static String GET_LONG_MONTH_BETWEEN_STR(String field,Integer year,Integer month){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.set(Calendar.YEAR, year);
		c1.set(Calendar.MONTH, month-1);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		
		c2.set(Calendar.YEAR, year);
		c2.set(Calendar.MONTH, month-1);
		c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.set(Calendar.MILLISECOND, 999);
		
		return "("+field+">="+c1.getTime().getTime()+" and "+field+"<="+c2.getTime().getTime()+")";
	}
	
	/**
	 * 获取指定年的指定月份的第一天Long长整型
	 * @param year
	 * @param month
	 * @return
	 */
	public static String GET_LONG_MONTH_FIRST_STR(Integer year,Integer month){
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, year);
		c1.set(Calendar.MONTH, month-1);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		return String.valueOf(c1.getTime().getTime());
	}
	
	/**
	 * 获取指定年的指定月份的第一天Long长整型
	 * @param year
	 * @param month
	 * @return
	 */
	public static String GET_LONG_MONTH_LAST_STR(Integer year,Integer month){
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, year);
		c2.set(Calendar.MONTH, month-1);
		c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.set(Calendar.MILLISECOND, 999);
		return String.valueOf(c2.getTime().getTime());
	}
	
	public static void main(String[] args) {
		Properties properties = new Properties();
		try {
			properties.load(new StringInputStream("acquireIncrement=1\ninitialPoolSize=100"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取元数据信息
	 * @param tableName
	 * @return
	 */
	public static Map<String,Integer> getTableMetaData(String tableName,TeeDataSource dataSource){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Map<String,Integer> metaData = new HashMap<String,Integer>();
		try{
			conn = getConnection(dataSource);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from "+tableName+" where 1!=1");
			rsmd = rs.getMetaData();
			int c = rsmd.getColumnCount();
			String colName;
			for(int i=1;i<=c;i++){
				colName = rsmd.getColumnLabel(i);
				metaData.put(colName.toUpperCase(), rsmd.getColumnType(i));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
			TeeDbUtility.close(stmt, rs);
		}
		return metaData;
	}
	
	/**
	 * 通过TeeDataSource获取连接对象
	 * @param tableName
	 * @return
	 */
	public static Connection getConnection(TeeDataSource dataSource){
		Connection conn = null;
		
		//内部数据源，不用创建新的数据源连接
		if(dataSource==null){
			SessionFactory sessionFactory = (SessionFactory) TeeBeanFactory.getBean("sessionFactory");
			try {
				conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//外部数据源
//			String key = dataSource.getUrl()+dataSource.getUserName()+dataSource.getPassWord();
//			key = String.valueOf(key);
//			ComboPooledDataSource basicDataSource = null;
//			synchronized (dataSources) {
//				basicDataSource = dataSources.get(key);
//				if(basicDataSource==null){
//					basicDataSource = new ComboPooledDataSource();
//					basicDataSource.setUser(dataSource.getUserName());
//					basicDataSource.setPassword(dataSource.getPassWord());
//					basicDataSource.setJdbcUrl(dataSource.getUrl());
//					try {
//						basicDataSource.setDriverClass(dataSource.getDriverClass());
//					} catch (PropertyVetoException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					Properties properties = new Properties();
//					try {
//						properties.load(new StringInputStream(dataSource.getConfigModel()));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					Map data = new HashMap();
//					data.putAll(properties);
//					try {
//						org.apache.commons.beanutils.BeanUtils.copyProperties(basicDataSource, data);
//					} catch (IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (InvocationTargetException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					dataSources.put(key, basicDataSource);
//				}
//			}
//			
//			try {
//				conn = basicDataSource.getConnection();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			try {
				Class.forName(dataSource.getDriverClass());
				conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassWord());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(conn!=null){
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	/**
	 * 启动后获取connection
	 * @author syl
	 * @date 2013-11-24
	 * @return
	 */
	public static Connection getConnection(){
		String db_driver = TeeSysProps.getString("db_driver");
		String db_url = TeeSysProps.getString("db_url");
		String usr = TeeSysProps.getString("db_username");
		String db_password = TeeSysProps.getString("db_password");
		
		TeeDataSource dataSource = new TeeDataSource();
		dataSource.setDriverClass(db_driver);
		dataSource.setPassWord(db_password);
		dataSource.setUrl(db_url);
		dataSource.setUserName(usr);
		dataSource.setConfigModel(TeeSysProps.getString("db_config"));
		
		
		return getConnection(dataSource);
	}
	
	/**
	 * 关闭
	 * @author syl
	 * @date 2013-11-24
	 * @param stmt
	 * @param rs
	 */
	public static void close(Statement stmt,ResultSet rs){
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 关闭
	 * @author syl
	 * @date 2013-11-24
	 * @param stmt
	 * @param rs
	 */
	public static void close(Statement stmt,ResultSet rs , Connection conn){
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭数据连接
	 * @author syl
	 * @date 2013-11-24
	 * @param conn
	 */
	public static void closeConn(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void rollback(Connection conn){
		if(conn!=null){
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String TO_CHAR(String dialect,String field){
		String render = null;
		if("mysql".equals(dialect.toLowerCase())){
			render = field;
		}else if("oracle".equals(dialect.toLowerCase())){
			render = "to_char(substr("+field+",1,4000))";
		}else if("sqlserver".equals(dialect.toLowerCase())){
			render = "CAST("+field+" as varchar)";
		}else if("kingbase".equals(dialect.toLowerCase())){
			render = "to_char("+field+")";
		}
		return render;
	}
	
	/**
	 * 处理IN  字符串过多时，截断式拼凑sql
	 * @param field
	 * @param ids
	 * @return
	 */
	public static String IN(String field,String ids){
		if(ids==null || "".equals(ids)){
			return " 1!=1 ";
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		String sp [] = null;
		StringBuffer sb = new StringBuffer("(");
		sp = ids.split(",");
		int max = 1000;
		int delta = sp.length/max<=0?1:(sp.length/max+(sp.length%max==0?0:1));
		for(int i=0;i<delta;i++){
			sb.append("("+field+" in (");
			for(int j=max*i;j<max*i+max && j<sp.length;j++){
				sb.append(sp[j]);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("))");
			if(i!=delta-1){
				sb.append(" or ");
			}
		}
		return sb.toString()+")";
	}
	
	/**
	 * 处理IN  字符串过多时，截断式拼凑sql
	 * @param field
	 * @param ids
	 * @return
	 */
	public static String NOT_IN(String field,String ids){
		if(ids==null || "".equals(ids)){
			return " 1!=1 ";
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		String sp [] = null;
		StringBuffer sb = new StringBuffer("(");
		sp = ids.split(",");
		int max = 1000;
		int delta = sp.length/max<=0?1:(sp.length/max+(sp.length%max==0?0:1));
		for(int i=0;i<delta;i++){
			sb.append("("+field+" not in (");
			for(int j=max*i;j<max*i+max && j<sp.length;j++){
				sb.append(sp[j]);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("))");
			if(i!=delta-1){
				sb.append(" or ");
			}
		}
		return sb.toString()+")";
	}
	
	/**
	 * 处理IN  字符串过多时，截断式拼凑sql
	 * @param field
	 * @param ids
	 * @return
	 */
	public static String IN(String field, List<Integer> ids){
		if(ids==null || ids.size()==0){
			return "(1!=1)";
		}
		StringBuffer sb = new StringBuffer("(");
		int max = 1000;
		int delta = ids.size()/max<=0?1:(ids.size()/max+(ids.size()%max==0?0:1));
		for(int i=0;i<delta;i++){
			sb.append("("+field+" in (");
			for(int j=max*i;j<max*i+max && j<ids.size();j++){
				sb.append(ids.get(j));
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("))");
			if(i!=delta-1){
				sb.append(" or ");
			}
		}
		return sb.toString()+")";
	}
	
	/**
	 * 处理IN  字符串过多时，截断式拼凑sql
	 * @param field
	 * @param ids
	 * @return
	 */
	public static String IN_STR(String field, List<String> ids){
		StringBuffer sb = new StringBuffer("(");
		int max = 1000;
		int delta = ids.size()/max<=0?1:(ids.size()/max+(ids.size()%max==0?0:1));
		for(int i=0;i<delta;i++){
			sb.append("("+field+" in (");
			for(int j=max*i;j<max*i+max && j<ids.size();j++){
				sb.append("'"+ids.get(j)+"'");
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("))");
			if(i!=delta-1){
				sb.append(" or ");
			}
		}
		return sb.toString()+")";
	}
	
	/**
	 * 过滤匹配like的字符串
	 * @param likeStr
	 * @return
	 */
	public static String formatString(String likeStr){
		return likeStr.replace("%", "\\%").replace("'", "''");
	}
}