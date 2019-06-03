package com.tianee.oa.core.general.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeIpRule;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("ipRuleDao")
public class TeeIpRuleDao  extends TeeBaseDao<TeeIpRule>  {
	/**
	 * 增加
	 * @param TeeIpRule
	 */
	public void addIpRule(TeeIpRule intf) {
		save(intf);	
	}
	
	/**
	 * 更新
	 * @param TeeIpRule
	 */
	public void updaIpRule(TeeIpRule intf){
		update(intf);	
	}
	
	
	/**
	 * byId 查询
	 * @param id
	 */
	public TeeIpRule getById(int id){
		return get(id);
	}
	
	/**
	 * byId 删除
	 * @param id  
	 */
	public void deleteById(int id){
		 delete(id);
	}
	

	/**
	 *  查询所有记录
	 * @param 
	 */
	public List<TeeIpRule> getAll(){
		String hql = "from TeeIpRule";
		List<TeeIpRule>  list = executeQuery(hql, null);
		return list;
	}
	
	

	/**
	 *  根据日志类型 查询所有记录
	 * @param 
	 */
	public List<TeeIpRule> getAllByType(String ipType){
		String hql = "from TeeIpRule";
		if(!TeeUtility.isNullorEmpty(ipType)){
			hql = hql + " where ipType = '" + ipType + "' ";
		}
		List<TeeIpRule>  list = executeQuery(hql, null);
		return list;
	}
	
}


