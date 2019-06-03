package com.tianee.oa.core.org.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeeUserOnline;
import com.tianee.webframe.dao.TeeBaseDao;


@Repository("userOnlineDao")
public class TeeUserOnlineDao extends TeeBaseDao<TeeUserOnline>{
	/**
	 * 新建
	 * @author syl
	 * @date 2013-11-19
	 * @param userOnline
	 */
	public void addUserOnline(TeeUserOnline userOnline) {
		save(userOnline);	
	}
	/**
	 * 更新
	 * @author syl
	 * @date 2013-11-19
	 * @param TeeDept
	 */
	public void UpdateUserOnline(TeeUserOnline userOnline) {
		update(userOnline);	
	}
	
	public List<TeeUserOnline> list(){
		return executeQuery("from TeeUserOnline", null);
	}
	
	public List<TeeUserOnline> listByDeptIds(String deptIds){
		return executeQuery("from TeeUserOnline", null);
	}
	
	/**
	 * 删除
	 * @author syl
	 * @date 2013-11-19
	 * @ 
	 * @param sessionToken 
	 */
	public void deleteBySessionToken(String sessionToken) {
		Object values[] = {sessionToken};
		String hql = "delete from TeeUserOnline where sessionToken = ?";
		deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * 更新在线状态
	 * @author syl
	 * @date 2013-11-19
	 * @ 
	 * @param sessionToken 
	 */
	public void updateBySessionToken(String sessionToken , int userStatus) {
		Object values[] = {userStatus, sessionToken};
		String hql = "update TeeUserOnline set userStatus = ?  where sessionToken = ?";
		deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * 单个查询在线状态
	 * @author syl
	 * @date 2014-2-23
	 * @param uuid
	 * @return
	 */
	public TeeUserOnline getUseronlineByUserId(int uuid){
		String hql = "from TeeUserOnline where userId = "+uuid;
		Session session = getSession();
		Query q = session.createQuery(hql);
		List<TeeUserOnline> list = q.list();
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	public TeeUserOnline getUseronlineBySessionToken(String sessionId){
		String hql = "from TeeUserOnline where sessionToken = '"+sessionId+"'";
		Session session = getSession();
		Query q = session.createQuery(hql);
		return (TeeUserOnline) q.uniqueResult();
	}
	
	
	/**
	 * 按人员Id字符串    查询在线状态
	 * @author syl
	 * @date 2014-2-23
	 * @param uuid
	 * @return
	 */
	public List<TeeUserOnline> getUseronlineByIds(String uuids){
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() - 1);
		}
		String hql = "from TeeUserOnline where userId in (" +uuids + ")";
		return executeQuery(hql, null);
	}
	
	
	/**
	 * 查询byId
	 * @author syl
	 * @date 2013-11-19
	 * @param TeeDept
	 */
	public TeeUserOnline selectById(int id) {
		return get(id);	
	}
}
