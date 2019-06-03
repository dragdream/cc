package com.tianee.oa.core.org.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeeUserGroup;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository("sysUserGroupDao")
public class TeeUserGroupDao extends TeeBaseDao<TeeUserGroup> {

	/**
	 * 获取公共分组
	 * @param 
	 */
	
	public List<TeeUserGroup> getUserGroup() {
	    String hql = " from TeeUserGroup sysPara where userId = 0 order by orderNo asc";
	    //System.out.println(hql);
		List<TeeUserGroup> list = (List<TeeUserGroup>) executeQuery(hql,null);
		return list;
	}
	
	

	/**
	 * 获取获取个人用户分组
	 * @param 
	 */
	
	public List<TeeUserGroup> getUserGroupByPersonUuid(int personId) {
	    String hql = " from TeeUserGroup sysPara where userId = " + personId + " order by orderNo asc";
	    //System.out.println(hql);
		List<TeeUserGroup> list = (List<TeeUserGroup>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 获取获取公共和个人用户分组
	 * @param 
	 */
	
	public List<TeeUserGroup> getPublicUserGroupAndPerson(int personId) {
	    String hql = " from TeeUserGroup sysPara where userId = 0 or userId = " + personId + " order by userId, orderNo asc";
	    //System.out.println(hql);
		List<TeeUserGroup> list = (List<TeeUserGroup>) executeQuery(hql,null);
		return list;
	}
	
	
	/**
	 * 搜索主编码
	 * @param 
	 */
	
	public List<TeeUserGroup> selectUserGroup(String hql,Object[] values) {
	    
		List<TeeUserGroup> list = (List<TeeUserGroup>) executeQuery(hql,values);
		return list;
	}
	
 	
	/**
	 * 查询 byId
	 * @param TeeUserGroup
	 */
	public TeeUserGroup selectUserGroupById(int uuid) {
		TeeUserGroup userGroup = (TeeUserGroup)get(uuid);
		return userGroup;
	}
	
	
	/**
	 * 删除
	 * @param TeeUserGroup
	 */
	public void delUserGroup(String hql ,Object[] values) {
		deleteOrUpdateByQuery(hql, values);
	}

}
