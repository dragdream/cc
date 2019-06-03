package com.tianee.oa.core.priv.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;

@Repository
public class TeeMenuGroupDao extends TeeBaseDao<TeeMenuGroup> {
	/**
	 * 
	 * 增加菜单组
	 * 
	 * @param TeeMenuGroup
	 */
	public void addMenuGroup(TeeMenuGroup menuGroup) {
		save(menuGroup);
	}

	/**
	 * 更新
	 * 
	 * @param TeeMenuGroup
	 */
	public void updateMenuGroup(TeeMenuGroup menuGroup) {
		update(menuGroup);
	}

	/**
	 * 查询 byId
	 * 
	 * @param TeeSysMenu
	 */
	public TeeMenuGroup selectById(int uuid) {
		TeeMenuGroup menu = (TeeMenuGroup) get( uuid);
		return menu;
	}

	/**
	 * 查询 条件查询
	 * 
	 * @param TeeSysMenu
	 */
	public List<TeeMenuGroup> selectMenuGroup(String hql, Object[] values) {
		List<TeeMenuGroup> list = (List<TeeMenuGroup>) executeQuery(hql, values);
		return list;
	}

	/**
	 * 删除 by Object
	 * 
	 * @param TeeSysMenu
	 */
	public void delSysMenu(TeeMenuGroup TeeSysMenu) {
		delete(TeeSysMenu);
	}

	/**
	 * 删除 by Query
	 * 
	 * @param TeeSysMenu
	 */
	public void delSysMenu(String hql, Object[] values) {
		deleteOrUpdateByQuery(hql, values);
	}

	/**
	 * 增加 或者更新菜单
	 * 
	 * @param TeeSysMenu
	 */
	public void addOrUpdateMenu(TeeMenuGroup menuGroup) {
		saveOrUpdate(menuGroup);
	}

	/**
	 * 获取菜单组分页
	 * 
	 * @param hql
	 * @param firstResult
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public List<TeeMenuGroup> pageMenuGroupFind(final String hql,
			int firstResult, int pageSize, Object param[]) {
		List<TeeMenuGroup> list = (List<TeeMenuGroup>) pageFind(hql,firstResult, pageSize, param);
		return list;
	}

	/**
	 * 获取菜单组分页
	 * 
	 * @param hql
	 * @param firstResult
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public List<TeeMenuGroup> selectGroupMenu(String hql ,Object values[]) {
		List<TeeMenuGroup> list = (List<TeeMenuGroup>)executeQuery(hql, values);
		return list;
	}
	
	

	/**
	 * 根据uuid字符串获取的 菜单组对象List
	 * @return
	 */
	public List<TeeMenuGroup> getMenuGroupListByUuids(String uuids){
		uuids = TeeStringUtil.getSqlStringParse(uuids);
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String hql = "from TeeMenuGroup where uuid in (" +uuids+ ")";
		List<TeeMenuGroup> list = executeQuery(hql, null);
		return list;
	}
	
	/**
	 * 删除
	 * @return  by uuid字符串
	 */
	public int delMenuGroupByUuids(String uuids){
		uuids = TeeStringUtil.getSqlStringParse(uuids);
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String hql = "delete TeeMenuGroup where uuid in (" +uuids+ ")";
		return deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 删除 与人员的中间表记录
	 * @return 
	 */
	public int delGroupPerson(String uuids){
		uuids = TeeStringUtil.getSqlStringParse(uuids);
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String sql = "delete from PERSON_MENU_GROUP where MENU_GROUP_UUID in (" +uuids+ ")";
		return getByExectSql(sql, null);
	}
	
	/**
	 * 获取所有菜单组 
	 * 
	 * @return
	 */
	public List selectAll() {
		String sql = "select uuid , menu_group_name FROM menu_group order by MENU_GROUP_NO";
		return getBySql(sql);
	}
	
	
/*	*//**
	 * 根据角色uuid字符串获取的菜单对象List  级联查询
	 * @return
	 *//*
	public List<TeeSysMenu> getSysMenuListByUuids(String uuids){
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String hql = "from TeeDepartment where uuid in (" +uuids+ ")";
		List<TeeSysMenu> list = executeQuery(hql, null);
		return list;
	}*/
	
	public List<TeeMenuGroup> getGroupListByDept(TeeDepartment dept) {
		Object[] values = { dept};
		String hql = "from TeeMenuGroup t where exists (select 1 from t.depts depts where depts = ? )";
		List<TeeMenuGroup> list = executeQuery(hql, values);
		return list;
	}
	
	public List<TeeMenuGroup> getGroupListByDeptUuid(int deptUuid) {
		Object[] values = {deptUuid};
		//String hql = "from TeeMenuGroup t where exists(select 1 from t.dept d where d.orgDept.uuid = ? )";
		String hql = "from TeeMenuGroup t where t.dept.uuid = ? )";
		List<TeeMenuGroup> list = executeQuery(hql, values);
		return list;
	}

}
