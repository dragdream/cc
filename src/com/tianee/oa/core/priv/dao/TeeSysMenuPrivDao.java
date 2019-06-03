package com.tianee.oa.core.priv.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.priv.bean.TeeSysMenuPriv;
import com.tianee.oa.core.priv.model.TeeSysMenuPrivModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeeSysMenuPrivDao extends TeeBaseDao<TeeSysMenuPriv>{

	/**
	 * 增加菜单
	 * @param TeeSysMenuPriv
	 */
	public void addMenuPriv(TeeSysMenuPriv priv) {
		save(priv);	
	}
	
	/**
	 * 更新
	 * @param TeeSysMenuPriv
	 */
	public void updateMenuPriv(TeeSysMenuPriv priv) {
		update(priv);	
	}
	
	/**
	 * 查询 byId
	 * @param TeeSysMenuPriv
	 */
	public TeeSysMenuPriv selectById(int id) {
		return get(id);
	}

	/**
	 * 删除 byId
	 * @param TeeSysMenuPriv
	 */
	public void deleteById(int id) {
		delete(id);
	}
	
	
	/**
	 * 删除 byId
	 * @param TeeSysMenuPriv
	 */
	public void deleteByIds(String ids) {
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		String  hql = "delete from TeeSysMenuPriv where sid in (" + ids + ")";
		deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 全部删除 
	 * @param TeeSysMenuPriv
	 */
	public void deleteAll() {
		String  hql = "delete from TeeSysMenuPriv";
		deleteOrUpdateByQuery(hql, null);
	}

	/**
	 * 查询 所有记录
	 * @param 
	 */
	public List<TeeSysMenuPriv> selectAll() {
		String  hql = "from TeeSysMenuPriv";
		return executeQuery(hql, null);
	}
	
	/**
	 * 根据有效性 查询
	 * @param 
	 */
	public List<TeeSysMenuPriv> selectByPrivFlag(TeeSysMenuPriv priv) {
		Object[] values = {priv.getPrivFlag()};
		String  hql = "from TeeSysMenuPriv where  privFlag = ?";
		return executeQuery(hql, values);
	}
	
	
	
	/**
	 * 条件查询
	 * @param 
	 */
	public List<TeeSysMenuPriv> selectByTerm(TeeSysMenuPrivModel model , String menuIds) {
		List list = new ArrayList();
		String  hql = "from TeeSysMenuPriv where  1= 1 ";
		if(!TeeUtility.isNullorEmpty(model.getPrivFlag())){//有效行
			list.add(model.getPrivFlag());
			hql = hql + " and privFlag = ?";
		}
		
		if(!TeeUtility.isNullorEmpty(model.getPrivName())){//名称
			list.add("%" + model.getPrivName() + "%");
			hql = hql + " and privName like ?";
		}
		
		if(!TeeUtility.isNullorEmpty(model.getPrivUrl())){
			list.add("%" +  model.getPrivUrl() + "%");
			hql = hql + " and privUrl like ?";
		}
		return executeQueryByList(hql, list);
	}
	
	/**
	 * 权限控制  获取权限路径和 action/URL
	 * @param 
	 */
	public List<TeeSysMenuPriv> selectByPriv(TeeSysMenuPrivModel model , String menuIds) {
		if(TeeUtility.isNullorEmpty(menuIds)){
			return null;
		}
		if(menuIds.endsWith(",")){
			menuIds = menuIds.substring(0, menuIds.length() - 1);
		}
		if(TeeUtility.isNullorEmpty(model.getPrivFlag())){
			model.setPrivFlag("0");
		}
		Object[] values = { model.getPrivFlag()};
		String  hql = "from TeeSysMenuPriv priv where  (exists (select 1 from priv.menuPriv menu where menu.uuid in(" + menuIds + ")))"
				+ " and priv.privFlag = ? ";
	
		return executeQuery(hql, values);
	}
	
	/**
	 * 检查是否存在此路径
	 * @author syl
	 * @date 2014-2-25
	 * @param model
	 * @return
	 */
	public long checkExistUrl(TeeSysMenuPrivModel model){
		Object[] values = {model.getPrivUrl()};
		String  hql = "select count(sid) from TeeSysMenuPriv where  privUrl = ? ";
		return count(hql, values);
	}
	
}

