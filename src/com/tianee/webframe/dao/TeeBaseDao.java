package com.tianee.webframe.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 基础Dao层，主要放置通用的一些方法 该Dao为基础Dao的基类 Dao的类 可以从 该Dao继承 今后要做平台 这种类要写好
 * 尽量把BaseDao些的标准一些作为一个标杆 大家可以参考这里的代码
 */
@Repository
public class TeeBaseDao<T> {
	private Class<T> clazz;
	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * 构造方法中，初始化泛型DAO
	 * 
	 */
	public TeeBaseDao() {
		this.clazz = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.clazz = (Class<T>) p[0];
		}
	}

	/**
	 * 重构一下构造方法
	 * 
	 * @param sessionFactory
	 *            获取sessionFactory
	 */
	public TeeBaseDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取Session 对象 封装一下方便一下 用了一下断言 这个hibernate 4 文档里面有
	 * 
	 * @return
	 */
	public Session getSession() {
		Assert.notNull(sessionFactory, "sessionFactory 对象为空!");
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 存储实体对象
	 * 
	 * @param o
	 *            要存储的实体对象 这里的objectId 作为返回值 主要是考虑到主键不一定是 uuid 或者有可能是 id自增的 所以类型
	 *            不一定为Internet 或者 String 所以 改成Object了 如果要用的话 直接强制转换就行了
	 * @return
	 */
	public Serializable save(T o) {
		Session session = this.getSession();
		Object objectId = session.save(o);
		return (Serializable) objectId;
	}

	/**
	 * 保存或更新 操作
	 * 
	 * @param o
	 */
	public void saveOrUpdate(T o) {
		// Transaction tx = null;
		Session session = this.getSession();
		session.saveOrUpdate(o);
	}

	/**
	 * 更新对象操作
	 * 
	 * @param o
	 *            实体对象
	 */
	public void update(T o) {
		Session session = this.getSession();
		session.update(o);
	}
	
	public void update(Map updateItem,Serializable id) {
		StringBuffer hql = new StringBuffer();
		//获取对象主键名称
		Field fields[] = clazz.getDeclaredFields();
		String idFieldName = null;
		String entityName = clazz.getSimpleName();
		List params = new ArrayList();
		for(Field field:fields){
			if(field.getAnnotation(Id.class)!=null){
				idFieldName = field.getName();
				break;
			}
		}
		
		hql.append("update "+entityName+" set ");
		
		Set<String> keys = updateItem.keySet();
		if(keys.size()==0){
			return ;
		}
		int i=0;
		for(String key : keys){
			if(updateItem.get(key)==null){
				hql.append(key+"=null");
			}else{
				hql.append(key+"=?");
				params.add(updateItem.get(key));
			}
			if(i!=keys.size()-1){
				hql.append(",");
			}
			i++;
		}
		
		hql.append(" where "+idFieldName+"=?");
		params.add(id);
		
		//加入绑定参数
		Session session = getSession();
		Query query = session.createQuery(hql.toString());
		i=0;
		for(Object param:params){
			query.setParameter(i, param);
			i++;
		}
		query.executeUpdate();
	}
	

	/**
	 * 获取对象 load 方法调用session的load 主要目的利用hibernate 一级缓存 弊端 是不能立刻发sql语句执行查询 除非设置了
	 * fecchType 为eager的时候才可以 所以可能会出现 懒加载问题 呵呵 不过这个经典的 问题再hibernate 4
	 * 里面已经被解决的很好了 这就是我为什么要用hibernate 4.1 + spring 3.2的原因
	 * 
	 * @param id
	 * @return
	 */
	public T load(Serializable id) {
		Session session = this.getSession();
		T object = null;
		object = (T) session.load(clazz, id);
		return object;
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 *            对象Id
	 * @return
	 */
	public T get(Serializable id) {
		Session session = this.getSession();
		T object = null;
		object = (T) session.get(clazz, id);
		return object;
	}
	/**
	 * 
	 * @author zhp
	 * @createTime 2014-1-3
	 * @editTime 下午01:24:34
	 * @desc
	 */
	public T loadSingleObject(String hql,final Object[] param) {
		Session session = this.getSession();
		Query q = null;
		q = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (T) q.uniqueResult();
	}
	/**
	 * 删除实体对象 这里是根据id删除
	 * 
	 * @param id
	 */
	public void delete(Serializable id) {
		deleteByObj(load(id));
	}

	/**
	 * 删除实体
	 * 
	 * @param obj
	 *            实体对象
	 */
	public void deleteByObj(T obj) {
		Session session = this.getSession();
		session.delete(obj);
		return;
	}

	/**
	 * 逐个删除，保证缓存与数据库的统一 呵呵
	 * 
	 * @param ids序列化的
	 *            数据结构
	 * @return
	 */
	public int deleteAll(Serializable... ids) {
		for (Serializable id : ids) {
			deleteByObj(load(id));
		}
		return ids.length;
	}

	/**
	 * 按条件删除获取更新 使用占位符的方式填充值 也可以用 T 请注意：like对应的值格式："%"+username+"%" Hibernate
	 * Query
	 * 
	 * @param hql
	 * @param valus
	 *            占位符号?对应的值，顺序必须一一对应 可以为空对象数组，但是不能为null
	 * @return
	 */
	public int deleteOrUpdateByQuery(final String hql, final Object[] values) {
		Session session = this.getSession();
		int result = 0;
		Query query = getSession().createQuery(hql);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		result = query.executeUpdate();
		return result;

	}

	/**
	 * 按照hibernate 的doc 里面介绍的 17.10. Detached queries and subqueries 支持分页
	 * 
	 * @param detachedCriteria
	 *            Criteria条件
	 * @param start
	 *            开始记录
	 * @param limit
	 *            要获取的条数
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<T> doExecute(final DetachedCriteria detachedCriteria,
			final Integer start, final Integer limit) {
		DetachedCriteria dcriteria = detachedCriteria;
		Session session = this.getSession();
		List result = null;
		Criteria criteria = dcriteria.getExecutableCriteria(session);
		criteria.setProjection(null);// 避免先做了统计查询，使得统计的sql仍然存在，就象缓存一样,清空!! 大家记得哦
		if (start != null) {
			criteria.setFirstResult(start);
		}
		if (limit != null) {
			criteria.setMaxResults(limit);
		}
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		result = criteria.list();

		return result;
	}

	public int executeUpdate(String hql,Object objs[]){
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		if(objs!=null){
			for(int i=0;i<objs.length;i++){
				query.setParameter(i, objs[i]);
			}
		}
		return query.executeUpdate();
	}
	
	
	public int executeUpdateByList(String hql,List list){
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				query.setParameter(i, list.get(i));
			}
		}
		return query.executeUpdate();
	}
	
	/**
	 * 执行本地语句更新
	 * @param hql
	 * @param objs
	 * @return
	 */
	public int executeNativeUpdate(String sql,Object objs[]){
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(objs!=null){
			for(int i=0;i<objs.length;i++){
				query.setParameter(i, objs[i]);
			}
		}
		return query.executeUpdate();
	}
	
	/**
	 * 执行本地语句更新
	 * @param hql
	 * @param objs
	 * @return
	 */
	public List<Map> executeNativeQuery(String sql,Object objs[],int firstResult,int pageSize){
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(objs!=null){
			for(int i=0;i<objs.length;i++){
				query.setParameter(i, objs[i]);
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	/**
	 * 执行本地语句更新
	 * @param hql
	 * @param objs
	 * @return
	 */
	public Map executeNativeUnique(String sql,Object objs[]){
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(objs!=null){
			for(int i=0;i<objs.length;i++){
				query.setParameter(i, objs[i]);
			}
		}
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map) query.uniqueResult();
	}
	
	/**
	 * 使用占位符的方式填充值 也可以用 T 请注意：like对应的值格式："%"+username+"%" Hibernate Query
	 * 
	 * @param hql
	 * @param valus
	 *            占位符号?对应的值，顺序必须一一对应 可以为空对象数组，但是不能为null
	 * @return
	 */
	public List<T> executeQuery(final String hql, final Object[] values) {
		// Transaction tx = null;
		Session session = this.getSession();
		List<T> result = null;
		Query query = getSession().createQuery(hql);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		result = query.list();
		return result;
	}

	public List<T> find(final String hql, Object... param) {
		Session session = this.getSession();
		List<T> result = null;
		Query query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		result = query.list();
		return result;
	}

	/**
	 * 分页
	 * 
	 * @param hql
	 *            : hql语句
	 * @param firstResult
	 *            : 开始索引
	 * @param pageSize
	 *            : 去多少记录
	 * @param param
	 *            : 参数
	 * @return
	 */
	public List<T> pageFind(String hql, int firstResult, int pageSize,
			Object param[]) {
		Session session = this.getSession();
		List<T> result = null;
		Query query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		result = query.list();
		return result;

	}

	/**
	 * 分页
	 * 
	 * @param hql
	 *            : hql语句
	 * @param firstResult
	 *            : 开始索引
	 * @param pageSize
	 *            : 去多少记录
	 * @param param
	 *            : 参数
	 * @return
	 */
	public List<T> pageFindByList(final String hql, int firstResult,
			int pageSize, List list) {
		Session session = this.getSession();
		List<T> result = null;
		Query query = session.createQuery(hql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				query.setParameter(i, list.get(i));
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		result = query.list();
		return result;

	}

	/*
	 * 根据hql 查询数量 param 查询参数 hql 查询语句
	 */
	public Long count(String hql, Object param[]) {
//		int index = hql.indexOf("order by");
//		if(index!=-1){
//			hql = hql.substring(0,index);
//		}
		Session session = this.getSession();
		Object lon = 0L;
		Query q = null;
		q = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		lon = q.uniqueResult();
		if(lon==null){
			lon = new Long(0);
		}
		return Long.parseLong(lon.toString());
	}

	/*
	 * 根据hql 查询数量 param 查询参数 hql 查询语句
	 */
	public Long countByList(String hql, List list) {
		Session session = this.getSession();
		Long lon = 0L;
		Query q = null;
		q = session.createQuery(hql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				q.setParameter(i, list.get(i));
			}
		}
		lon = (Long) q.uniqueResult();
		return lon;
	}

	/*
	 * 本地sql 根据sql 查询数量 param 查询参数 hql 查询语句
	 */
	public Long countSQLByList(String sql, List list) {
		Session session = this.getSession();
		Long lon = 0L;
		Query q = null;
		q = session.createSQLQuery(sql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				q.setParameter(i, list.get(i));
			}
		}
		Object result = q.uniqueResult();
		if(result instanceof BigInteger){
			BigInteger bigInt = (BigInteger) q.uniqueResult();
			lon = Long.parseLong(bigInt.toString());
		}else{
			lon = Long.parseLong(result.toString());
		}
		
		
		return lon;
	}

	/**
	 * 按装条件查询 Bysql syl
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public List<T> getBySql(String sql, Object... param) {
		Session session = this.getSession();
		List<T> result = null;
		Query query = session.createSQLQuery(sql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		result = query.list();
		return result;
	}

	public int getByExectSql(String sql, Object[] param) {
		Session session = this.getSession();
		List<T> result = null;
		Query query = session.createSQLQuery(sql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}

		return query.executeUpdate();
	}

	/**
	 * 按装条件查询 Bysql syl
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public List<T> getBySql(String sql, Object[] param, Class<T> objClass) {
		Session session = this.getSession();
		List<T> result = null;
		Query query = session.createSQLQuery(sql).addEntity(objClass);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		result = query.list();
		return result;
	}

	/**
	 * 使用占位符的方式填充值 也可以用 T syl 请注意：like对应的值格式："%"+username+"%" Hibernate Query
	 * 
	 * @param hql
	 * @param valus
	 *            占位符号?对应的值，顺序必须一一对应 可以为空对象数组，但是不能为null
	 * @return
	 */
	public List<T> executeQueryByList(final String hql, List list) {
		Session session = this.getSession();
		List<T> result = null;
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < list.size(); i++) {
			query.setParameter(i, list.get(i));
		}
		result = query.list();
		return result;

	}

	/**
	 * 获取对象sessionFactory 注入
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 我想还是提供一个set方法 好扩展 sessionFactory的来源方式
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 关闭session
	 * 
	 * @param sesion
	 *            数据库session对象
	 */
	public static void closeSession(Session sesion) {
		if (sesion != null) {
			sesion.close();
		}
		return;
	}
	
	/**
	 * 获取数据库本地连接
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException{
		return SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
	}

}