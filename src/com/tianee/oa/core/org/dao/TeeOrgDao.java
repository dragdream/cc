package com.tianee.oa.core.org.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("orgDao")
public class TeeOrgDao  extends TeeBaseDao<TeeOrganization> {
	
	/**
	 * 增加组织
	 * @param TeeOrganization
	 */
	public void addOrg(TeeOrganization TeeOrg) {
		save(TeeOrg);	
	}
	
	/**
	 * 更新
	 * @param TeeOrganization
	 */
	public void updateOrg(TeeOrganization TeeOrg) {
		update(TeeOrg);	
	}
	
	/**
	 * 查询 byUuid
	 * @param TeeOrganization
	 */
	public TeeOrganization selectOrgByUuid(String uuid) {
		String hql = " from TeeOrganization org where org.uuid = ?";
	    String[] values = {uuid};
	    @SuppressWarnings("unchecked")
		List<TeeOrganization> list = (List<TeeOrganization>) executeQuery(hql, values);
        if(list.size() > 0){
        	return list.get(0);
        }
		return null;
	}
	
	
	/**
	 * 查询 byUuid
	 * @param TeeOrganization
	 */
	public TeeOrganization selectOrgById(String uuid) {
		TeeOrganization org = (TeeOrganization)get(uuid);
		return org;
	}
	/**
	 * 查询 条件查询 
	 * @param TeeOrganization
	 */
	public List<TeeOrganization> selectOrg(String hql ,Object[] values) {
	    @SuppressWarnings("unchecked")
	    
		List<TeeOrganization> list = (List<TeeOrganization>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 查询单位
	 * @param 
	 */
	
	public List<TeeOrganization> traversalOrg() {
	    String hql = " from TeeOrganization org ";
		@SuppressWarnings("unchecked")
		List<TeeOrganization> list = (List<TeeOrganization>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 删除
	 * @param TeeOrganization
	 */
	public void delSysOrg(TeeOrganization org) {
		delete(org);	
	}
	
	/**
	 * 增加 或者更新菜单
	 * @param TeeOrganization
	 */
	public void addOrUpdateOrg(TeeOrganization org) {
		saveOrUpdate(org);	
	}
	
	

}
