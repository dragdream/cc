package com.tianee.oa.subsys.salManage.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.salManage.bean.TeeSalAccount;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccountPerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("teeSalAccountPersonDao")
public class TeeSalAccountPersonDao extends TeeBaseDao<TeeSalAccountPerson>{	
	
	
	/**
	 * 根据账套获取属于该账套的员工记录
	 * @param personId
	 * @return
	 */
	public List<TeeSalAccountPerson> getListByAcountId(int accountId){
		Object[] values = {accountId};
		String hql = "from TeeSalAccountPerson sap where sap.account.sid = ?" ; 
		List<TeeSalAccountPerson> salDataList  = executeQuery(hql, values);
		return salDataList;
	}
	
	/**
	 * 删除 byId
	 * 
	 * @param personId
	 * @return
	 */
	public void delById(int sid){
		String hql = "delete from TeeSalAccountPerson where sid = ?";
		deleteOrUpdateByQuery(hql, new Object[] {sid});
	}
	

	/**
	 * 删除 byId
	 * 
	 * @param personId
	 * @return
	 */
	public void delByIds(String ids ,int accountId ){
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeSalAccountPerson where account.sid =? and sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, new Object[] {accountId});
		}
		
	}
	
	/**
	 * 删除 by 账套Id
	 * 
	 * @param personId
	 * @return
	 */
	public int delByAccountId(int accountId){
		String hql = "delete from TeeSalAccountPerson sap where sap.account.sid = ?";
		return deleteOrUpdateByQuery(hql, new Object[] {accountId});
	}
	
}
