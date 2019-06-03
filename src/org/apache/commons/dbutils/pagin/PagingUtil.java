package org.apache.commons.dbutils.pagin;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.NoClobRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 基于DBUtils分页小工具，通用所有数据库基于Bean形式的分页,语法借鉴了Mysql。
 * <pre>
 *  PagingUtil util=new PagingUtil(UP_INFO.class);//分页为需要实例化
 * </pre>
 * @author 杨伦亮
 * 下午10:08:14
 */
public class PagingUtil{
	private Class tClass;
	private static boolean getMaxRow=false;
	private static final Log log=LogFactory.getLog(PagingUtil.class);
	public PagingUtil(){
	}
	/**
	 * 设置是否在分页的时候统计总数
	 * @param getMaxROw
	 */
	public PagingUtil(boolean getMaxROw){
		this.getMaxRow=getMaxROw;
	}
	public PagingUtil(Class clasType){
		this.tClass=clasType;
	}
	public PagingUtil(Class clasType,boolean getMaxRow){
		this.tClass=clasType;
		this.getMaxRow=getMaxRow;
	}
	/**
	 * Array分页
	 * @param sql
	 * @param obj
	 * @param limit
	 * @return Page
	 */
	public static Page toArrayList(String sql,Object[]obj,int ...limit){
		try{
			return new ArrayPagin().find(sql, obj, limit);
		}catch (Exception e) {
			log.debug(e);
		}
		return null;
	}
	/**
	 * 返回数据可设置是否计算总记录数量
	 * @param sql
	 * @param obj
	 * @param limit
	 * @return Page
	 */
	public static Page toArrayList(boolean getMax,String sql,Object[]obj,int ...limit){
		getMaxRow=getMax;
		return toArrayList(sql, obj, limit);
	}
	/**
	 * 分页后返回MapList
	 * @param sql
	 * @param obj
	 * @param limit
	 * @return
	 */
	public static Page toMapList(String sql,Object[]obj,int ...limit){
		try{
			return new MapPagin().find(sql, obj, limit);
		}catch (Exception e) {
			log.debug(e);
		}
		return null;
	}
	/**
	 * 返回数据可设置是否计算总记录数量
	 * @param sql
	 * @param obj
	 * @param limit
	 * @return Page
	 */
	public static Page toMapList(boolean getMax,String sql,Object[]obj,int ...limit){
		getMaxRow=getMax;
		return toMapList(sql, obj, limit);
	}
	/**
	 * 返回BeanList分页
	 * @param sql
	 * @param obj
	 * @param limit
	 * @return
	 */
	public Page toBeanList(String sql,Object[]obj,int ...limit){
		try{
			return new BeanPagin().find(tClass,sql, obj, limit);
		}catch (Exception e) {
			log.debug(e);
		}
		return null;
	}
	
	/**Array分页*/
	private static final class ArrayPagin{
		private RowProcessor filterClob=new NoClobRowProcessor();//扩展类
		public Page find(String sql, Object[] args,int...limit)
				throws Exception {
			ResultSetHandler h=new ArrayHandler(filterClob);
			return new DbUtils().limit(getMaxRow,sql, args,h,limit);
		}
	}
	/**Bean分页*/
	private static final class BeanPagin{
		private RowProcessor filterClob=new NoClobRowProcessor();//扩展类
		public Page find(Class type,String sql, Object[] args,int...limit)
				throws Exception {
			ResultSetHandler h=new BeanHandler(type);
			return new DbUtils().limit(getMaxRow,sql, args,h,limit);
		}
	}
	/**MapList分页*/
	private static final class MapPagin{
		private RowProcessor filterClob=new NoClobRowProcessor();//扩展类
		public Page find(String sql, Object[] args,int...limit)
				throws Exception {
			ResultSetHandler h=new MapListHandler(filterClob);
			return new DbUtils().limit(getMaxRow,sql, args,h,limit);
		}
	}
}
