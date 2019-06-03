package com.tianee.oa.core.priv.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.priv.bean.TeeModulePriv;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository
public class TeeModulePrivDao extends TeeBaseDao<TeeModulePriv> {
	/**
	 * 增加菜单
	 * @param TeeModulePriv
	 */
	public void addModulePriv(TeeModulePriv priv) {
		save(priv);	
	}
	
	/**
	 * 更新
	 * @param TeeModulePriv
	 */
	public void updateModulePriv(TeeModulePriv priv) {
		update(priv);	
	}
	
	

	/**
	 * 查询 byId
	 * @param TeeModulePriv
	 */
	public void delById(TeeModulePriv  priv) {
		deleteByObj(priv);

	}
	
	
	/**
	 * 查询 byId
	 * @param TeeModulePriv
	 */
	public TeeModulePriv selectById(int sid) {
		String hql = " from TeeModulePriv where sid = ?";
	    Object[] values = {sid};
	    List<TeeModulePriv> list = (List<TeeModulePriv>) executeQuery(hql, values);
        if(list.size() > 0){
        	return list.get(0);
        }
		return null;
	}
	
	/**
	 * 查询 
	 * @param personId:人员Id
	 */
	public TeeModulePriv selectPrivByUserId(int personId ,int model) {
		String hql = " from TeeModulePriv t where t.userId.uuid = ? and t.moduleId = ?";
	    Object[] values = {personId,model};
	    List<TeeModulePriv> list = (List<TeeModulePriv>) executeQuery(hql, values);
        if(list.size() > 0){
        	return list.get(0);
        }
		return null;
	}	
}
