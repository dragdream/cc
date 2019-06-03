package com.tianee.oa.core.base.message.dao;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.message.bean.TeeMessageGroup;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;



@Repository("messageGroupDao")
public class TeeMessageGroupDao extends TeeBaseDao<TeeMessageGroup>{
	/**
	 * 增加
	 * @param TeeMessage
	 */
	public void addMessageGroup(TeeMessageGroup message) {
		save(message);
	}
	
	/**
	 * 更新
	 * @param TeeMessage
	 */
	public void updateMessageGroup(TeeMessageGroup message) {
		update(message);
	}
	
	/**
	 * byId 查询
	 * @param 
	 */
	public TeeMessageGroup selectById(int id) {
		TeeMessageGroup intf = load(id);
		return intf;
	}
	
	/**
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeMessageGroup> select() {
		Object[] values = null;
		String hql = "from TeeMessageGroup";
		List<TeeMessageGroup> list = (List<TeeMessageGroup>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeMessageGroup> selectListByLoginPerson(int personId) {
		Object[] values = null;
		String hql = "from TeeMessageGroup where groupCreator = " + personId;
		List<TeeMessageGroup> list =  executeQuery(hql,values);
		return list;
	}
	
	/**
	 * 查询所有组
	 * @param 
	 */
	public  List<TeeMessageGroup> getAllGroup() {
		Object[] values = null;
		String hql = "from TeeMessageGroup order by orderNo asc";
		List<TeeMessageGroup> list =  executeQuery(hql,values);
		return list;
	}
	
	/**
	 * byId 查询
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * 更改状态 by Id
	 * @param  id  
	 * @param flag 状态 0 正常 1-停用
	 */
	public void updateFlag(int id , int flag) {
		String hql = "update TeeMessageGroup  set groupFlag = " + flag + "where sid = " + id;
		deleteOrUpdateByQuery(hql,null);
	}
}


	