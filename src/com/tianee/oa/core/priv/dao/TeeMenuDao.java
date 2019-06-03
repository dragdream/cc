package com.tianee.oa.core.priv.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
@Repository
public class TeeMenuDao  extends TeeBaseDao<TeeSysMenu>{

	/**
	 * 增加菜单
	 * @param TeeSysMenu
	 */
	public void addMenu(TeeSysMenu TeeSysMenu) {
		save(TeeSysMenu);	
	}
	
	/**
	 * 更新
	 * @param TeeSysMenu
	 */
	public void updateMenu(TeeSysMenu TeeSysMenu) {
		update(TeeSysMenu);	
	}
	
	
	
	/**
	 * 查询 byId
	 * @param TeeSysMenu
	 */
	public TeeSysMenu selectMenuByUuid(String uuid) {
		String hql = " from TeeSysMenu t where t.uuid = ?";
	    String[] values = {uuid};
	    List<TeeSysMenu> list = (List<TeeSysMenu>) executeQuery(hql, values);
        if(list.size() > 0){
        	return list.get(0);
        }
		return null;
	}
	
	
	/**
	 * 查询 byId
	 * @param TeeSysMenu
	 */
	public TeeSysMenu selectMenuById(int uuid) {
		/*String hql = " from TeeSysMenu t where t.uuid = ?";
	    String[] values = {uuid};
	    List<TeeSysMenu> list = (List<TeeSysMenu>) executeQuery(hql, values);
        if(list.size() > 0){
        	return list.get(0);
        }*/
		
		TeeSysMenu menu = (TeeSysMenu)get(uuid);
		return menu;
	}
	
	/**
	 * 获取第一级菜单
	 * @param 
	 */
	public List<TeeSysMenu> selectParentMenu() {
		String hql = " from TeeSysMenu t where  length(t.menuId)= ?  order by t.menuId";
		
		List<TeeSysMenu> list = new ArrayList<TeeSysMenu>();
		if(TeeSysProps.getDialect().equals("kingbase")){//人大金仓数据库
			Object[]  values = {3L};
			list =  ( List<TeeSysMenu> )executeQuery(hql, values);
		}else{
			Object[]  values = {3};
			list = ( List<TeeSysMenu> )executeQuery(hql, values);
		}
		  
		return list;
	}
	/**
	 * 查询 条件查询 
	 * @param TeeSysMenu
	 */
	public List<TeeSysMenu> selectMenu(String hql ,Object[] values) {
	    List<TeeSysMenu> list = (List<TeeSysMenu>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 删除
	 * @param TeeSysMenu
	 */
	public void delSysMenu(TeeSysMenu TeeSysMenu) {
		delete(TeeSysMenu);	
	}
	/**
	 * 删除
	 * @param TeeSysMenu
	 */
	public void delSysMenu(String hql ,Object[] values) {
		deleteOrUpdateByQuery(hql, values);
	}
	/**
	 * 增加 或者更新菜单
	 * @param TeeSysMenu
	 */
	public void addOrUpdateMenu(TeeSysMenu TeeSysMenu) {
		saveOrUpdate(TeeSysMenu);	
	}
	

	
	/**
	 * 根据角色uuid字符串获取的菜单对象List
	 * @return
	 */
	public List<TeeSysMenu> getSysMenuListByUuids(String uuids){
		uuids = TeeStringUtil.getSqlStringParse(uuids);
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String hql = "from TeeSysMenu where uuid in (" +uuids+ ")";
		List<TeeSysMenu> list = executeQuery(hql, null);
		return list;
	}
	
	

	/**
	 * 根据角色uuid字符串获取的菜单对象List
	 * @return
	 */
	public List<TeeSysMenu> getSysMenuListByMenuGroupUuids(String uuids){
		//uuids = TeeStringUtil.getSqlStringParse(uuids);
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String hql = "select   t from TeeSysMenu t left join t.menuGroup tg  where tg.uuid in  (" + uuids + ") order by length(t.menuId) , t.menuId ";
		List<TeeSysMenu> list = executeQuery(hql, null);
		return list;
	}
	
	
	/**
	 * 查询菜单 --包括下级菜单
	 * @param TeeSysMenu
	 */
	public List<TeeSysMenu> selectMenuAndChildMenuByUuid(TeeSysMenu menu) {
		String hql = " from TeeSysMenu t where  t.menuId like ? ";
		Object[] values = {menu.getMenuId() + "%"};
		return executeQuery(hql, values);
	}
	

	/**
	 * 删除菜单单 --包括下级菜单
	 * @param TeeSysMenu
	 */
	public int  delMenuAndChildMenuByUuid(TeeSysMenu menu) {
		String hql = " delete TeeSysMenu t where  t.menuId like ? ";
		Object[] values = {menu.getMenuId() + "%"};
		return deleteOrUpdateByQuery(hql, values);
	}
	
	
	/**
	 * 更新菜单模块权限
	 * @param TeeSysMenu
	 */
	public int  updateMenuModuleNo(TeeSysMenu menu) {
		String hql = " update  TeeSysMenu set menuModuleNo = ?  where  uuid =  ? ";
		Object[] values = {menu.getMenuModuleNo() , menu.getUuid()};
		return deleteOrUpdateByQuery(hql, values);
	}
	
	
	/**
	 * 所有菜单
	 * @param TeeSysMenu
	 */
	public List<TeeSysMenu> getSysMenuTree() {
		String hql = " from TeeSysMenu t    order by t.menuId";
		return executeQuery(hql, null);
	}

	/**
	 * 获取所有叶子节点 -- by person
	 * @author syl
	 * @date 2014-3-17
	 * @param uuids 菜单组Ids字符串 以逗号分隔
	 * @return
	 */
	public List<TeeSysMenu> getChildSysMenuListByMenuGroupUuids(String uuids) {
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String hql = "select   t from TeeSysMenu t left join t.menuGroup tg  where tg.uuid in  (" + uuids + ")  and (t.menuCode  is not null or t.menuCode = '')  order by length(t.menuId) , t.menuId ";
		List<TeeSysMenu> list = executeQuery(hql, null);
		return list;
	}

	
	
	/**
	 * 
	 * @param groupIds
	 * @param sysId
	 * @return
	 */
	public List<TeeSysMenu> getSysMenuListByMenuGroupUuids2(String groupIds,
			int sysId) {
		if(groupIds.endsWith(",")){
			groupIds = groupIds.substring(0, groupIds.length() -1);
		}
		String hql = "select   t from TeeSysMenu t left join t.menuGroup tg left join t.sys s where tg.uuid in  (" + groupIds + ") and ((s is null) or (s.sid="+sysId+")) order by length(t.menuId) , t.menuId ";
		List<TeeSysMenu> list = executeQuery(hql, null);
		return list;
	}
	
	public List<TeeSysMenu> getSysMenuListBySysId(String sysId){
		//String sql = "SELECT t1.* FROM sys_menu t1 where EXISTS (select 1 from sys_menu t2 where t2.SYS_ID = ? and t2.MENU_ID = left(t1.MENU_ID,3)) order by t1.MENU_ID ";
		String sql = "SELECT t1.* FROM sys_menu t1 where t1.SYS_ID = ? order by t1.MENU_ID ";		
		return this.getBySql(sql, new Object[] {sysId},TeeSysMenu.class);
	}
	
}
