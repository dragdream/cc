package org.apache.commons.dbutils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.pagin.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * 采用Apache dbutils操作数据库的工具类,采用C3P0--DataSource
 *
 * <pre>
 * 方式一:使用默认的数据源(jdbc/eBuilder)
 *    DBUtil tool=new DBUtil();///操作完毕默认关闭连接
 *    DBUtil tool=new DBUtil(false);//设置不关闭连接
 *    使用完之后关闭连接 tool.close();
 * 方式二:使用自定义数据源
 *    String dataSourceName=&quot;&quot;;
 *    DBUtil tool=new DBUtil(dataSourceName);///操作完毕默认关闭连接
 *    DBUtil tool=new DBUtil(dataSourceName,false);//操作完毕不关闭连接
 *    使用完之后关闭连接 tool.close();
 * 方式三:使用自定义数据源
 *    java.sql.DataSource dataSource=null;
 *    DBUtil tool=new DBUtil(dataSource);///操作完毕默认关闭连接
 *    DBUtil tool=new DBUtil(dataSource,false);//操作完毕不关闭连接
 *    使用完之后关闭连接 tool.close();
 * 方式四:使用指定的数据库连接
 *    java.sql.connection=null;
 *    DBUtil tool=new DBUtil(connection);///操作完毕默认关闭连接
 *    DBUtil tool=new DBUtil(dataSource,false);//操作完毕不关闭连接
 *    使用完之后关闭连接 tool.close();
 * </pre>
 *
 * @author 杨伦亮 Jun 13, 2011
 */
public class DbUtils {
	// Spring可注入DataSource
	private Connection connection = null;
	// ***********静态变量
	private boolean CloseConnection = true;
	private static final Log log = LogFactory.getLog(DbUtils.class);
	public static final String DEFAULT_JNDI = "java:comp/env/jdbc/eBuilder";
	private JDBCPaginRunner run=new JDBCPaginRunner();//分页扩展类
	private RowProcessor filterClob=new NoClobRowProcessor();//扩展类
	/**
	 * 采用默认数据库连接(jdbc/eBuilder)操作完毕默认关闭连接
	 */
	public DbUtils() {
		CloseConnection=true;
	}
	/**
	 * 采用默认数据库连接(jdbc/eBuilder)可指定是否操作完毕关闭连接
	 */
	public DbUtils(boolean closeConnection) {
		CloseConnection = closeConnection;
	}
	/**
	 * 采用指定的数据库连接，操作完毕默认关闭连接
	 *
	 * @param connection
	 */
	public DbUtils(Connection connection) {
		this.connection = connection;
	}
	/**
	 * 采用指定的数据库连接，可指定是否操作完毕关闭连接
	 *
	 * @param connection
	 * @param closeConnection
	 */
	public DbUtils(Connection connection, boolean closeConnection) {
		this.connection = connection;
		CloseConnection = closeConnection;
	}
	
	/**
	 * Execute a batch of SQL INSERT, UPDATE, or DELETE queries.
	 *
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            An array of query replacement parameters. Each row in this
	 *            array is one set of batch replacement values.
	 * @return The number of rows updated per statement.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public int[] batch(String sql, Object[][] params) throws Exception {
		Connection conn = this.getConnection();
		int[] rows = null;
		
		try {
			rows = run.batch(conn, sql, params);
		} finally {
			close(conn);
		}

		return rows;
	}
	/**
	 * 关闭连接
	 *
	 * @param connection
	 */
	public void close() {
		closeQuietly(connection);
	} 
	/**
	 * Executes the given INSERT, UPDATE, or DELETE SQL statement without any
	 * replacement parameters.
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @return The number of rows updated.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public int executeUpdate(String sql) throws Exception {
		Connection conn = this.getConnection();
		int rows = 0;
		
		try {
			rows = run.update(conn, sql);
		} finally {
			close(conn);
		}
		return rows;
	}

	/**
	 * Executes the given INSERT, UPDATE, or DELETE SQL statement with a single
	 * replacement parameter.
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return The number of rows updated.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public int executeUpdate(String sql, Object param) throws Exception {
		Connection conn = this.getConnection();
		int rows = 0;
		
		try {
			rows = run.update(conn, sql, param);
		} finally {
			close(conn);
		}

		return rows;
	}

	/**
	 * Executes the given INSERT, UPDATE, or DELETE SQL statement.
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @param params
	 *            Initializes the PreparedStatement's IN (i.e. '?') parameters.
	 * @return The number of rows updated.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public int executeUpdate(String sql, Object[] params) throws Exception {
		Connection conn = this.getConnection();
		int rows = 0;
		
		try {

			rows = run.update(conn, sql, params);

		} finally {
			close(conn);
		}

		return rows;
	}
	/**
	 * 执行插入，带方言，返回主键值
	 * @param sql
	 * @param params
	 * @param dialect
	 * @return
	 * @throws Exception
	 */
	public Object executeInsert(String sql,String dialect, Object[] params) throws Exception {
		Connection conn = this.getConnection();
		Object key = null;
		
		try {

			key = run.insert(conn, sql,dialect, params);

		} finally {
			close(conn);
		}

		return key;
	}
	
	/**
	 * 获得数据库的连接
	 * @return Connection
	 */
	public synchronized Connection getConnection() throws Exception {
		try{
			if(connection==null||connection.isClosed()){
//				connection=new ComboPooledDataSource().getConnection();
			}
		}catch (SQLException e) {
			log.error("数据库连接失败:"+e.getMessage());
		}catch (Exception e) {
			log.error(e);
		}
		return connection;
	}
	/**
	 * Execute an SQL SELECT query without any replacement parameters and place
	 * the column values from the first row in an Object[]. Usage Demo:
	 *
	 * <pre>
	 * Object[] result = searchToArray(sql);
	 * if (result != null) {
	 * 	for (int i = 0; i &lt; result.length; i++) {
	 * 		System.out.println(result[i]);
	 * 	}
	 * }
	 * </pre>
	 *
	 * @param sql
	 *            The SQL to execute.
	 * @return An Object[] or null if there are no rows in the ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Object[] queryToArray(String sql) throws Exception {
		Connection conn = this.getConnection();
		Object[] result = null;
		
		ResultSetHandler h = new ArrayHandler(filterClob);
		try {
			result = (Object[]) run.query(conn, sql, h);
		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * place the column values from the first row in an Object[].
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return An Object[] or null if there are no rows in the ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Object[] queryToArray(String sql, Object param) throws Exception {
		Connection conn = this.getConnection();
		Object[] result = null;
		
		ResultSetHandler h = new ArrayHandler(filterClob);
		try {
			result = (Object[]) run.query(conn, sql, param, h);
		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * Executes the given SELECT SQL query and place the column values from the
	 * first row in an Object[].
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return An Object[] or null if there are no rows in the ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Object[] queryToArray(String sql, Object[] params) throws Exception {
		Connection conn = this.getConnection();
		Object[] result = null;
		
		ResultSetHandler h = new ArrayHandler(filterClob);
		try {
			result = (Object[]) run.query(conn, sql, params, h);
		} finally {
			close(conn);
		}
		return result;
	}
	/**
	 * 分页扩展 
	 * @param sql
	 * @param params
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Object[] queryToArray(String sql, Object[] params,int ...limit) throws Exception {
		Connection conn = this.getConnection();
		Object[] result = null;
		
		ResultSetHandler h = new ArrayHandler(filterClob);
		try {
			result = (Object[]) run.limit(false,conn, sql, params, h, limit);
		} finally {
			close(conn);
		}
		return result;
	}
	/**
	 * Execute an SQL SELECT query without any replacement parameters and place
	 * the ResultSet into a List of Object[]s Usage Demo:
	 *
	 * <pre>
	 * ArrayList result = queryToArrayList(sql);
	 * Iterator iterator = result.iterator();
	 * while (iterator.hasNext()) {
	 * 	Object[] temp = (Object[]) iterator.next();
	 * 	for (int i = 0; i &lt; temp.length; i++) {
	 * 		System.out.println(temp[i]);
	 * 	}
	 * }
	 * </pre>
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @return A List of Object[]s, never null.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public ArrayList queryToArrayList(String sql) throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		
		ResultSetHandler h = new ArrayListHandler(filterClob);
		try {
			result = (ArrayList) run.query(conn, sql, h);
		} finally {
			close(conn);
		}
		return result;
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * place the ResultSet into a List of Object[]s
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return A List of Object[]s, never null.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public ArrayList queryToArrayList(String sql, Object param)
			throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		
		ResultSetHandler h = new ArrayListHandler(filterClob);
		try {
			result = (ArrayList) run.query(conn, sql, param, h);
		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * Executes the given SELECT SQL query and place the ResultSet into a List
	 * of Object[]s
	 *
	 * @param sql
	 *            The SQL statement to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return A List of Object[]s, never null.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public ArrayList queryToArrayList(String sql, Object[] params)
			throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		
		ResultSetHandler h = new ArrayListHandler(filterClob);
		try {

			result = (ArrayList) run.query(conn, sql, params, h);

		} finally {
			close(conn);
		}

		return result;
	}
	public ArrayList queryToArrayList(String sql, Object[] params,int ...limit)
			throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		ResultSetHandler h = new ArrayListHandler(filterClob);
		try {
			result = (ArrayList) run.limit(false,conn, sql, params,h, limit);

		} finally {
			close(conn);
		}

		return result;
	}
	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * converts the first ResultSet into a Map object. Usage Demo:
	 *
	 * <pre>
	 * Map result = queryToMap(sql);
	 * System.out.println(map.get(columnName));
	 * </pre>
	 *
	 * @param sql
	 *            The SQL to execute.
	 * @return A Map with the values from the first row or null if there are no
	 *         rows in the ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Map queryToMap(String sql) throws Exception {
		Connection conn = this.getConnection();
		Map result = null;
		
		ResultSetHandler h = new MapHandler(filterClob);
		try {

			result = (Map) run.query(conn, sql, h);

		} finally {
			close(conn);
		}

		return result;
	}
	
	private void filterMapField(Map result){
		Set<String> keys = result.keySet();
		for(String key : keys){
			if(result.get(key)!=null && result.get(key) instanceof oracle.sql.TIMESTAMP){
				oracle.sql.TIMESTAMP timestamp = (oracle.sql.TIMESTAMP)result.get(key);
				try {
					result.put(key, timestamp.timestampValue().toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.put(key, "");
				}
			}
		}
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * converts the first ResultSet into a Map object.
	 *
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return A Map with the values from the first row or null if there are no
	 *         rows in the ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Map queryToMap(String sql, Object param) throws Exception {
		Connection conn = this.getConnection();
		Map result = null;
		
		ResultSetHandler h = new MapHandler(filterClob);
		try {
			result = (Map) run.query(conn, sql, param, h);

		} finally {
			close(conn);
		}
		return result;
	}
	/**
	 * Executes the given SELECT SQL query and converts the first ResultSet into
	 * a Map object.
	 *
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return A Map with the values from the first row or null if there are no
	 *         rows in the ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Map queryToMap(String sql, Object[] params) throws Exception {
		Connection conn = this.getConnection();
		Map result = null;
		
		ResultSetHandler h = new MapHandler(filterClob);
		try {
			result = (Map) run.query(conn, sql, params, h);
		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * converts the ResultSet into a List of Map objects. Usage Demo:
	 *
	 * <pre>
	 * ArrayList result = queryToMapList(sql);
	 * Iterator iterator = result.iterator();
	 * while (iterator.hasNext()) {
	 * 	Map map = (Map) iterator.next();
	 * 	System.out.println(map.get(columnName));
	 * }
	 * </pre>
	 *
	 * @param sql
	 *            The SQL to execute.
	 * @return A List of Maps, never null.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public ArrayList queryToMapList(String sql) throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		
		ResultSetHandler h = new MapListHandler(filterClob);
		try {

			result = (ArrayList) run.query(conn, sql, h);

		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * converts the ResultSet into a List of Map objects.
	 *
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return A List of Maps, never null.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public ArrayList queryToMapList(String sql, Object param) throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		
		ResultSetHandler h = new MapListHandler(filterClob);
		try {
			result = (ArrayList) run.query(conn, sql, param, h);
		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * 执行给定的SELECT SQL查询，并将其ResultSet转换成的List of Map objects.
	 * @param sql 执行的Sql
	 * @param params 用此数据补充Sql中的参数
	 * @return A List of Maps, never null.
	 */
	public ArrayList queryToMapList(String sql, Object[] params)
			throws Exception {
		Connection conn = this.getConnection();
		
		ResultSetHandler h = new MapListHandler(filterClob);
		try {
			return (ArrayList) run.query(conn, sql, params, h);
		} finally {
			close(conn);
		}
	}
	/**
	 * 执行给定的SELECT SQL查询,返回指定的指针之间数据,并将其ResultSet转换成的List of Map objects.
	 * @param sql 执行的Sql
	 * @param params 用此数据补充Sql中的参数
	 * @param limit 用此数组表示数据开始的指针和结束的指针
	 * @return A List of Maps, never null.
	 */
	public ArrayList queryToMapList(String sql, Object[] params,int ...limit)
			throws Exception {
		Connection conn = this.getConnection();
		ResultSetHandler h = new MapListHandler(filterClob);
		try {
			return (ArrayList) run.limit(false,conn, sql, params, h,limit);
		}catch (Exception e) {
			log.error("我发现错误："+e);
			throw e;
		} finally {
			close(conn);
		}
	}
	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * Convert the first row of the ResultSet into a bean with the Class given
	 * in the parameter. Usage Demo:
	 *
	 * <pre>
	 * String sql = &quot;SELECT * FROM test&quot;;
	 * Test test = (Test) queryToBean(Test.class, sql);
	 * if (test != null) {
	 * 	System.out.println(&quot;test:&quot; + test.getPropertyName());
	 * }
	 * </pre>
	 *
	 * @param type
	 *            The Class of beans.
	 * @param sql
	 *            The SQL to execute.
	 * @return An initialized JavaBean or null if there were no rows in the
	 *         ResultSet.
	 */
	public Object queryToBean(Class type, String sql) throws Exception {
		Connection conn = this.getConnection();
		Object result = null;
		
		ResultSetHandler h = new BeanHandler(type);
		try {

			result = run.query(conn, sql, h);

		} finally {
			close(conn);
		}

		return result;
	}
	
	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * Convert the first row of the ResultSet into a bean with the Class given
	 * in the parameter.
	 *
	 * @param type
	 *            The Class of beans.
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return An initialized JavaBean or null if there were no rows in the
	 *         ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Object queryToBean(Class type, String sql, Object param)
			throws Exception {
		Connection conn = this.getConnection();
		Object result = null;
		
		ResultSetHandler h = new BeanHandler(type);
		try {

			result = run.query(conn, sql, param, h);

		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * Executes the given SELECT SQL query and Convert the first row of the
	 * ResultSet into a bean with the Class given in the parameter.
	 *
	 * @param type
	 *            The Class of beans.
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return An initialized JavaBean or null if there were no rows in the
	 *         ResultSet.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public Object queryToBean(Class type, String sql, Object[] params)
			throws Exception {
		Connection conn = this.getConnection();
		Object result = null;
		
		ResultSetHandler h = new BeanHandler(type);
		try {
			result = run.query(conn, sql, params, h);
		} finally {
			close(conn);
		}
		return result;
	}

	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * Convert the ResultSet rows into a List of beans with the Class given in
	 * the parameter. Usage Demo:
	 *
	 * <pre>
	 * ArrayList result = queryToBeanList(Test.class, sql);
	 * Iterator iterator = result.iterator();
	 * while (iterator.hasNext()) {
	 * 	Test test = (Test) iterator.next();
	 * 	System.out.println(test.getPropertyName());
	 * }
	 * </pre>
	 *
	 * @param type
	 *            The Class that objects returned from handle() are created
	 *            from.
	 * @param sql
	 *            The SQL to execute.
	 * @return A List of beans (one for each row), never null.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public ArrayList queryToBeanList(Class type, String sql) throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		
		ResultSetHandler h = new BeanListHandler(type);
		try {
			result = (ArrayList) run.query(conn, sql, h);
		} finally {
			close(conn);
		}
		return result;
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * Convert the ResultSet rows into a List of beans with the Class given in
	 * the parameter.
	 *
	 * @param type
	 *            The Class that objects returned from handle() are created
	 *            from.
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return A List of beans (one for each row), never null.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public ArrayList queryToBeanList(Class type, String sql, Object param)
			throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		
		ResultSetHandler h = new BeanListHandler(type);
		try {

			result = (ArrayList) run.query(conn, sql, param, h);

		} finally {
			close(conn);
		}

		return result;
	}

	/**
	 * Executes the given SELECT SQL query and Convert the ResultSet rows into a
	 * List of beans with the Class given in the parameter.
	 *
	 * @param type
	 *            The Class that objects returned from handle() are created
	 *            from.
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return A List of beans (one for each row), never null.
	 */
	public ArrayList queryToBeanList(Class type, String sql, Object[] params)
			throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		ResultSetHandler h = new BeanListHandler(type);
		try {

			result = (ArrayList) run.query(conn, sql, params, h);

		} finally {
			close(conn);
		}

		return result;
	}
	/**
	 * Executes the given SELECT SQL query and Convert the ResultSet rows into a
	 * List of beans with the Class given in the parameter.
	 *
	 * @param type
	 *            The Class that objects returned from handle() are created
	 *            from.
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return A List of beans (one for each row), never null.
	 */
	public ArrayList queryToBeanList(Class type, String sql, Object[] params,int ...limit)
			throws Exception {
		Connection conn = this.getConnection();
		ArrayList result = null;
		ResultSetHandler h = new BeanListHandler(type);
		try {
			result = (ArrayList) run.limit(false,conn, sql, params, h,limit);
		} finally {
			close(conn);
		}

		return result;
	}
	/**
	 * 分页是否返回总数
	 * @param getCount
	 * @param sql
	 * @param objs
	 * @param h
	 * @param limit
	 * @return
	 */
	public Page limit(Boolean getCount,String sql,Object[]objs,ResultSetHandler h,int ...limit){
		Connection conn = null;
		try {
			conn=this.getConnection();
			ArrayList result = (ArrayList) run.limit(getCount,conn, sql, objs, h,limit);
			long count=run.getCount();
			return new Page(count, result);
		}catch (Exception e) {
			return null;
		} finally {
			close();
		}
	}

	   /**
     * Close a <code>Connection</code>, avoid closing if null.
     *
     * @param conn Connection to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(Connection conn) throws SQLException {
//        if (conn != null) {
//            conn.close();
//        }
    }

    /**
     * Close a <code>ResultSet</code>, avoid closing if null.
     *
     * @param rs ResultSet to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    /**
     * Close a <code>Statement</code>, avoid closing if null.
     *
     * @param stmt Statement to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    /**
     * Close a <code>Connection</code>, avoid closing if null and hide
     * any SQLExceptions that occur.
     *
     * @param conn Connection to close.
     */
    public static void closeQuietly(Connection conn) {
        try {
            close(conn);
        } catch (SQLException e) {
            // quiet
        }
    }

    /**
     * Close a <code>Connection</code>, <code>Statement</code> and 
     * <code>ResultSet</code>.  Avoid closing if null and hide any 
     * SQLExceptions that occur.
     *
     * @param conn Connection to close.
     * @param stmt Statement to close.
     * @param rs ResultSet to close.
     */
    public static void closeQuietly(Connection conn, Statement stmt,
            ResultSet rs) {

        try {
            closeQuietly(rs);
        } finally {
            try {
                closeQuietly(stmt);
            } finally {
                closeQuietly(conn);
            }
        }

    }

    /**
     * Close a <code>ResultSet</code>, avoid closing if null and hide any
     * SQLExceptions that occur.
     *
     * @param rs ResultSet to close.
     */
    public static void closeQuietly(ResultSet rs) {
        try {
            close(rs);
        } catch (SQLException e) {
            // quiet
        }
    }

    /**
     * Close a <code>Statement</code>, avoid closing if null and hide
     * any SQLExceptions that occur.
     *
     * @param stmt Statement to close.
     */
    public static void closeQuietly(Statement stmt) {
        try {
            close(stmt);
        } catch (SQLException e) {
            // quiet
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null.
     *
     * @param conn Connection to close.
     * @throws SQLException if a database access error occurs
     */
    public static void commitAndClose(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.commit();
            } finally {
                conn.close();
            }
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null 
     * and hide any SQLExceptions that occur.
     *
     * @param conn Connection to close.
     */
    public static void commitAndCloseQuietly(Connection conn) {
        try {
            commitAndClose(conn);
        } catch (SQLException e) {
            // quiet
        }
    }

    /**
     * Loads and registers a database driver class.
     * If this succeeds, it returns true, else it returns false.
     *
     * @param driverClassName of driver to load
     * @return boolean <code>true</code> if the driver was found, otherwise <code>false</code>
     */
    public static boolean loadDriver(String driverClassName) {
        try {
            Class.forName(driverClassName).newInstance();
            return true;

        } catch (ClassNotFoundException e) {
            return false;

        } catch (IllegalAccessException e) {
            // Constructor is private, OK for DriverManager contract
            return true;

        } catch (InstantiationException e) {
            return false;

        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Print the stack trace for a SQLException to STDERR.
     *
     * @param e SQLException to print stack trace of
     */
    public static void printStackTrace(SQLException e) {
        printStackTrace(e, new PrintWriter(System.err));
    }

    /**
     * Print the stack trace for a SQLException to a 
     * specified PrintWriter. 
     *
     * @param e SQLException to print stack trace of
     * @param pw PrintWriter to print to
     */
    public static void printStackTrace(SQLException e, PrintWriter pw) {

        SQLException next = e;
        while (next != null) {
            next.printStackTrace(pw);
            next = next.getNextException();
            if (next != null) {
                pw.println("Next SQLException:");
            }
        }
    }

    /**
     * Print warnings on a Connection to STDERR.
     *
     * @param conn Connection to print warnings from
     */
    public static void printWarnings(Connection conn) {
        printWarnings(conn, new PrintWriter(System.err));
    }

    /**
     * Print warnings on a Connection to a specified PrintWriter. 
     *
     * @param conn Connection to print warnings from
     * @param pw PrintWriter to print to
     */
    public static void printWarnings(Connection conn, PrintWriter pw) {
        if (conn != null) {
            try {
                printStackTrace(conn.getWarnings(), pw);
            } catch (SQLException e) {
                printStackTrace(e, pw);
            }
        }
    }

    /**
     * Rollback any changes made on the given connection.
     * @param conn Connection to rollback.  A null value is legal.
     * @throws SQLException if a database access error occurs
     */
    public static void rollback(Connection conn) throws SQLException {
        if (conn != null) {
            conn.rollback();
        }
    }
    
    /**
     * Performs a rollback on the <code>Connection</code> then closes it, 
     * avoid closing if null.
     *
     * @param conn Connection to rollback.  A null value is legal.
     * @throws SQLException if a database access error occurs
     * @since DbUtils 1.1
     */
    public static void rollbackAndClose(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.rollback();
            } finally {
                conn.close();
            }
        }
    }

    /**
     * Performs a rollback on the <code>Connection</code> then closes it, 
     * avoid closing if null and hide any SQLExceptions that occur.
     *
     * @param conn Connection to rollback.  A null value is legal.
     * @since DbUtils 1.1
     */
    public static void rollbackAndCloseQuietly(Connection conn) {
        try {
            rollbackAndClose(conn);
        } catch (SQLException e) {
            // quiet
        }
    }
}
