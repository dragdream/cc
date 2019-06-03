package com.tianee.webframe.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 * 通用DAO实现类，集成BaseDao的方法，适用于所有实体类
 * @author lt
 *
 */
@Repository("teeSimpleDaoSupport")
public class TeeSimpleDaoSupport extends TeeBaseDao{
	
	public Object load(Class clazz,Serializable id) {
		Session session = this.getSession();
		return session.load(clazz, id);
	}
	
	public Object get(Class clazz,Serializable id) {
		Session session = this.getSession();
		return session.get(clazz, id);
	}
	
	public Object unique(String hql,Object params[]){
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		if(params!=null){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		return query.uniqueResult();
	}
	
	/**
	 * 过滤集合
	 * @param list
	 * @param filterHql
	 * @return
	 */
	public List<?> filteredList(List<?> list,String filterHql,Object params[]){
		Session session = this.getSession();
		Query query = session.createFilter(list,filterHql);
		if(params!=null){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}
	
	public List<?> filteredSet(Set<?> list,String filterHql,Object params[]){
		Session session = this.getSession();
		Query query = session.createFilter(list,filterHql);
		if(params!=null){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}
	
	public Map getMap(String hql,Object params[]){
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		if(params!=null){
			int i=0;
			for(Object param:params){
				query.setParameter(i, param);
				i++;
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map) query.uniqueResult();
	}
	
	public List<Map> getMaps(String hql,Object params[],int firstResult,int pageSize){
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		if(params!=null){
			int i=0;
			for(Object param:params){
				query.setParameter(i, param);
				i++;
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map>) query.list();
	}
	
	public List<Map> getMaps(String hql,Object params[]){
		return getMaps(hql,params,0,Integer.MAX_VALUE);
	}
	
	public void delete(Class clazz,Serializable id){
		Object obj = this.getSession().get(clazz, id);
		if(obj!=null){
			this.getSession().delete(obj);
		}
	}
}
