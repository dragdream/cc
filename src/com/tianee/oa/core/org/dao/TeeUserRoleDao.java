package com.tianee.oa.core.org.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("userRolelDao")
public class TeeUserRoleDao  extends TeeBaseDao<TeeUserRole> {

	/**
	 * page TeeDataGridModel.page 当前页
	 * rows TeeDataGridModel.rows 每页显示记录数
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	public List<TeeUserRole> loadList(String hql, int page, int rows, List<Object> param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	/**
	 * page TeeDataGridModel.page 当前页
	 * rows TeeDataGridModel.rows 每页显示记录数
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	public List<TeeUserRole> loadList(String hql, int page, int rows, Object... param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	/**
	 * 获取全部角色
	 * @return
	 */
	public List<TeeUserRole> selectUserPrivList(){
		String hql = "from TeeUserRole order by roleNo";
		List<TeeUserRole> list = executeQuery(hql, null);
		return list;
	}
	
	/**
	 * 根据用户的角色查询 角色列表
	 * @param isAdminPriv
	 * @param isSuperAdmin
	 * @param roleNo  角色排序号
	 * @return
	 */
	public List<TeeUserRole> selectUserPrivListByPersonInfo(boolean isAdminPriv , boolean isSuperAdmin , int roleNo){
		String hql = "from TeeUserRole where 1=1 ";
		if(isAdminPriv != true && isSuperAdmin != true){//不是管理员角色，也不是超级管理员角色
			hql = hql + " and roleNo > " + roleNo;
		}
		hql = hql + " order by roleNo";
		List<TeeUserRole> list = executeQuery(hql, null);
		return list;
	}
	
	
	/**
	 * 校验角色排序号是否存在
	 * @param uuid ：uuid
	 * @param roleNo ： 排序号
	 * @return
	 */
	public boolean checkRoleNoExist(int uuid , int roleNo) {
		String sql = "from TeeUserRole where roleNo = ? and uuid != ?" ;//与删除人员也不能同名
		Object[] values = {roleNo,uuid};
		List<TeeUserRole> list = executeQuery(sql, values);		
		if(list.size() > 0){
			return true;
		}
		return false;		
	} 
	
	/**
	 * 判断角色是否存在
	 * @author syl
	 * @date 2013-11-15
	 * @param roleName
	 * @return
	 */
	public TeeUserRole getUserRoleByRoleName(String roleName) {
		TeeUserRole role = new TeeUserRole();
		String hql = "from TeeUserRole where roleName = ?" ;
		Object[] values = {roleName};
		List<TeeUserRole> list = executeQuery(hql, values);		
		if(list.size() > 0){
			return list.get(0);
		}
		return null;		
	} 
	
	
	/**
	 * by UUId
	 * @return
	 */
	public TeeUserRole selectByUUId(int uuid){
		return (TeeUserRole)get(uuid);
	}
	
	
	
	/**
	 * 根据角色uuid字符串获取的角色对象List
	 * @return
	 */
	public List<TeeUserRole> getPrivListByUuids(String uuids){
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		Object[] values = {uuids};
		String hql = "from TeeUserRole where uuid in (" +uuids+ ")";
		List<TeeUserRole> list = executeQuery(hql, null);
		return list;
	}
	
	/**
	 * 根据角色uuid字符串获取的角色对象数组的roleName和uuid
	 * 
	 * @param roleUuids: 角色uuid字符串
	 */
	public String[]  getRoleNameAndUuidByUuids(String roleUuids) {
		//String[] personUuidsArr = personUuids.split(",");
		roleUuids = TeeStringUtil.getSqlStringParse(roleUuids);
		if(roleUuids.endsWith(",")){
			roleUuids = roleUuids.substring(0, roleUuids.length() -1);
		}
		String[] str = new String[2];
		String sql = "select UUID,ROLE_NAME from USER_ROLE where UUID in (" +roleUuids+ ")";
        Object[] values = {roleUuids};
		List list = getBySql(sql);
		String uuids = "";
		String userNames = "";
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String uuid = (String)obj[0];
			String userName = (String)obj[1];
			uuids = uuids + uuid + ",";
			userNames = userNames + userName + ",";
		}
		str[0] = uuids;
		str[1] = userNames;
		return str;
	}
	
	
	/**
	 * 获取上一个或者下一个 
	 * @author syl
	 * @date 2013-12-29
	 * @param id
	 * @param sortNo 排序号
	 * @param type 0-上移 1-下移
	 * @return
	 */
	public TeeUserRole getPrevOrNext(int id , int sortNo , String type){
		Object[] values = {id , sortNo};
		String hql = "from TeeUserRole where uuid <> ?";
		if(type.equals("0")){
			hql = hql = " and roleNo <= ?  order by roleNo desc";
		}else{
			hql = hql = " and roleNo >= ? order by roleNo desc";
		}
		List<TeeUserRole> list = executeQuery(hql, null);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/*
	 * 更新排序号
	 */
	public void updateRoleSort(int id , int sort ){
		Object[] values = {sort , id};
		String hql = "update TeeUserRole set roleNo = ?  where uuid = ? ";
		deleteOrUpdateByQuery(hql, values);
	}
	
	
	/**
	 * 获取角色，权限控制
	 * @param isController 是否控制  true:控制  false:不控制
	 * @param roleType  0-低角色 1- 同角色或者低角色 2-所有角色 3-指定角色	
	 * @param roleNo 角色排序号
	 * @param userRoleIds 角色字符串
	 * @return
	 */
	public List<TeeUserRole> selectUserPrivListByPerson(boolean isController , String roleType, int roleNo , String userRoleIds){
		String hql = "from TeeUserRole where 1=1 ";
		List<TeeUserRole> list = new ArrayList<TeeUserRole>();
		if(isController){//控制
			if(roleType.equals("0")){
				hql = hql + " and roleNo > " + roleNo;
			}else if(roleType.equals("1")){
				hql = hql + " and roleNo >= " + roleNo;
			}else if(roleType.equals("2")){
				
			}else if(roleType.equals("3")){
			
				if(TeeUtility.isNullorEmpty(userRoleIds)){
					return list;
				}
				if(userRoleIds.endsWith(",")){
					userRoleIds = userRoleIds.substring(0, userRoleIds.length() - 1);
				}
				hql = hql + " and uuid in (" + userRoleIds + ")";
			}
		}
		hql = hql + " order by roleNo";
		list = executeQuery(hql, null);
		return list;
	}

	
	/**
	 * 根据角色id串   获取角色的集合
	 * @param roleUuids
	 * @return
	 */
	public List<TeeUserRole> getUserRoleByUuids(String roleUuids) {
		if(roleUuids==null || "".equals(roleUuids)){
			roleUuids = "0";
		}
		if(roleUuids.endsWith(",")){
			roleUuids = roleUuids.substring(0, roleUuids.length() -1);
		}
      //  Object[] values ={personUuids};
        String hql  = " from TeeUserRole  where uuid in ("+roleUuids+ ")";
		return find(hql, null);
	}
}
