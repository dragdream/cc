package com.tianee.oa.core.org.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeeUserRoleType;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository("userRoleTypeDao")
public class TeeUserRoleTypeDao   extends TeeBaseDao<TeeUserRoleType> {
	
	/**
	 * 获取所有
	 * @return
	 */
	public List<TeeUserRoleType> getAllRoleType(){
		String hql = "from TeeUserRoleType order by typeSort asc";
		return executeQuery(hql, null);
	}
	
	/**
	 * 删除 byId
	 * @return
	 */
	public int deleteById(int id){
		String hql = "delete from TeeUserRoleType where sid = ?";
		Object[] values = {id};
		return deleteOrUpdateByQuery(hql, values);
	}
}
