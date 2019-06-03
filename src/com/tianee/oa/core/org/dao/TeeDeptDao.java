package com.tianee.oa.core.org.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("deptDao")
public class TeeDeptDao  extends TeeBaseDao<TeeDepartment> {
	
	/**
	 * 增加组织
	 * @param TeeDepartment
	 */
	public void addDept(TeeDepartment TeeDept) {
		save(TeeDept);	
	}
	
	/**
	 * 更新
	 * @param TeeDepartment
	 */
	public void updateDept(TeeDepartment TeeDept) {
		update(TeeDept);	
	}
	
	/**
	 * 查询 byUuid
	 * @param TeeDepartment
	 */
	public TeeDepartment selectDeptByUuid(int uuid) {
		String hql = " from TeeDepartment org where org.uuid = ? ";
	    Object[] values = {uuid};
	    @SuppressWarnings("unchecked")
		List<TeeDepartment> list = (List<TeeDepartment>) executeQuery(hql, values);
        if(list.size() > 0){
        	return list.get(0);
        }
		return null;
	}
	
	
	/**
	 * 查询 byUuid
	 * @param TeeDepartment
	 */
	public TeeDepartment selectDeptById(int uuid) {
		//System.out.println(uuid);
		TeeDepartment TeeDept = (TeeDepartment)get(uuid);
		return TeeDept;
	}
	
	/**
	 * 查询 by  niqueId  部门唯一标示，与其他系统对接用
	 * @param TeeDepartment
	 */
	public TeeDepartment selectDeptByUniqueId(String uniqueId) {
		//System.out.println(uuid);
		String hql  = "from TeeDepartment where uniqueId = ?";
		Object[] values = {uniqueId};
		List<TeeDepartment> list  = executeQuery(hql, values);
		TeeDepartment dept = null;
		if(list.size() > 0){
			dept = list.get(0);
		}
		return dept;
	}
	/**
	 * 查询 条件查询 
	 * @param TeeDepartment
	 */
	public List<TeeDepartment> selectDept(String hql ,Object[] values) {
	    @SuppressWarnings("unchecked")
	    
		List<TeeDepartment> list = (List<TeeDepartment>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 遍历表
	 * @param TeeDepartment
	 */
	
	public List<TeeDepartment> traversalDept() {
	    String hql = " from TeeOrganization org ";
		@SuppressWarnings("unchecked")
		List<TeeDepartment> list = (List<TeeDepartment>) executeQuery(hql,null);
		return list;
	}
	
	
	/**
	 * 删除
	 * @param TeeDepartment
	 */
	public void delSysDept(TeeDepartment TeeDept) {
		delete(TeeDept);	
	}
	
	

	

	/**
	 * 检查是否有下级部门
	 * @param TeeDepartment
	 */
	
	public boolean checkExists(TeeDepartment dept) {
	    String hql = " from TeeDepartment where deptParent.uuid = ?";
	    Object[] values = {dept.getUuid()};
		List<TeeDepartment> list = (List<TeeDepartment>) executeQuery(hql,values);
		if(list.size() > 0){
			return true;
		}
		return false;
	}
	/**
	 * 获取下级节点部门（不包括当前部门）  递归
	 * @param sql
	 * @param values
	 * @return
	 */
	  
  public List<TeeDepartment> getAllChildDept(int uuid,List<TeeDepartment> AllDept) throws Exception{
 
	List<TeeDepartment> list = new ArrayList<TeeDepartment>();
    String query = "select uuid, DEPT_NAME from DEPARTMENT where DEPT_PARENT=? order by DEPT_NO, DEPT_NAME asc";
    Statement stmt = null;
    ResultSet rs = null;
    Object[] values = {uuid};
    try {
    	list =  getBySql(query, values,TeeDepartment.class);
    } catch (Exception ex) {
    	throw ex;
    }
    AllDept.addAll(list);
    for(int i = 0; i < list.size(); i++){
    	TeeDepartment dept = list.get(i);
    	getAllChildDept(dept.getUuid(),AllDept);
    }
    return AllDept;
  }
  

  
	/**
	 *查询所有部门
	 * 
	 */
	public List<TeeDepartment> getAllDept() {
	    String hql = "select uuid from TeeDepartment  order by length(deptParentLevel) asc, deptNo asc ";
	    List uuidList = executeQuery(hql, null);
		List<TeeDepartment> list = new ArrayList();
		for(Object uuid:uuidList){
			list.add(get(TeeStringUtil.getInteger(uuid, 0)));
		}
		return list;
	}
	
	public List<TeeDepartment> getAllDeptSimple() {
	    String hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d order by length(d.deptParentLevel) asc, d.deptNo asc ";
	    String hql1 = "select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where d.deptParent is null order by length(d.deptParentLevel) asc, d.deptNo asc ";
	    List list = new ArrayList();
	    list.addAll(executeQuery(hql, null));
	    list.addAll(executeQuery(hql1, null));
		return list;
	}
	
	/* 更新 下级部门级别
	* @param oldMenuId : 更改之前编号
	* @param newMenuId: 新编号
	* @param type 更新状态，1-从第一级变成不是第一级，0或者其他为从不是第一级变成其它级别都可以
	*/
	public void updateDept(String oldMenuId , String currDeptParentId,int uuid ,String type) {
		int newMenuIdLength = currDeptParentId.length();
		int oldMenuIdLength = oldMenuId.length();
		String hql = " update TeeDepartment t set t.deptParentLevel = case  when 1=1 then  concat('" +currDeptParentId + "',substring(t.deptParentLevel," + (oldMenuIdLength + 1 ) + ",length(t.deptParentLevel))) end   where  t.deptParentLevel like ? and uuid != ?  and t.deptParentLevel != ?";
		Object[] values1 = { oldMenuId + "%", uuid};
		Object[] values2 = { oldMenuId + "%", uuid,oldMenuId};
		if(type.equals("1")){
			hql = " update TeeDepartment t set t.deptParentLevel = case  when 1=1 then  concat('" +currDeptParentId + "',t.deptParentLevel) end   where  t.deptParentLevel like ? and uuid != ? ";		
			int count = deleteOrUpdateByQuery(hql, values1);
		}else{
			int count = deleteOrUpdateByQuery(hql, values2);
		}
	}
	/**
	 * 获取所有下级部门  by 根据部门级别
	 * uuid
	 * @param uuid
	 */
	public List<TeeDepartment>  getAllChildDeptByLevl(String uuid) {
		Object[] values = { uuid + "%"};
		String hql = " from  TeeDepartment   where deptParentLevel like ?  ";
		return find(hql, values);
	}
	
	public List<TeeDepartment>  getAllChildDeptByLevlSimple(String uuid) {
		Object[] values = { uuid + "%"};
		String hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from  TeeDepartment   where deptParentLevel like ?  ";
		String hql1 = "select new TeeDepartment(uuid,deptName,deptFullId) from  TeeDepartment  where deptParentLevel like ?  and deptParent is null";
		List list = new ArrayList();
		list.addAll(find(hql, values));
		list.addAll(find(hql1, values));
		return list;
	}
	
	/**
	 * 获取所有的下级部门    根据dept_full_id
	 * uuid
	 * @param uuid
	 */
	public List<TeeDepartment>  getAllChildDept(String fullId) {
		Object[] values = { fullId + "%"};
		String hql = " from  TeeDepartment   where deptFullId like ?  ";
		return find(hql, values);
	}
	
	/**
	 * 获取所有是一级部门
	 * uuid
	 * @param uuid
	 */
	public List<TeeDepartment>  getFisrtDept() {
		return find("from TeeDepartment  where deptParent is null  order by deptNo asc", null);
	}
	
	/**
	 * 获取所有是一级部门
	 * uuid
	 * @param uuid
	 */
	public List<TeeDepartment>  getFisrtDeptSimple() {
		return find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment  where deptParent is null  order by deptNo asc", null);
	}
	
	
	/**
	 * 获取所有当前下级部门  --不是所有下级节点
	 * uuid
	 * @param uuid
	 */
	public List<TeeDepartment>  getChildDept(int uuid) {
        Object[] values ={uuid};
		List<TeeDepartment> list = find("from TeeDepartment  where deptParent.uuid = ?  order by deptNo asc ", values);
   		return list;
	}
	
	/**
	 * 获取所有当前下级部门  --不是所有下级节点
	 * uuid
	 * @param uuid
	 */
	public List<TeeDepartment>  getChildDeptSimple(int uuid) {
        Object[] values ={uuid};
		List<TeeDepartment> list = find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d where d.deptParent.uuid = ?  order by d.deptNo asc ", values);
		return list;
	}
	
	/**
	 * 获取所有当前下级部门  --所有下级节点
	 * uuid
	 * @param uuid
	 */
	public List<TeeDepartment>  getSelectDept(int uuid,String levelUuid) {
        Object[] values ={uuid,levelUuid + "%"};
		List<TeeDepartment> list = find("from TeeDepartment  where  uuid = ?  or deptParentLevel like ? order by length(deptParentLevel), deptNo  ", values);
   		return list;
	}
	
	/**
	 * 获取所有当前下级部门  --所有下级节点  且属于范围的部门
	 * @param uuid
	 * @param levelUuid  部门级别
	 * @param postDeptIds  部门Ids 字符串 
	 * @return
	 */
	public List<TeeDepartment>  getSelectDept(int uuid,String levelUuid , String postDeptIds) {
        Object[] values ={uuid,levelUuid + "%"};
        List<TeeDepartment> list = new ArrayList<TeeDepartment>();
        if(!TeeUtility.isNullorEmpty(postDeptIds)){
        	if(postDeptIds.endsWith(",")){
        		postDeptIds = postDeptIds.substring(0, postDeptIds.length() - 1);
        	}
        	String inSql = TeeDbUtility.IN("uuid", postDeptIds);//处理多个Ids，截取
        	list = find("from TeeDepartment  where  (uuid = ?  or deptParentLevel like ?)  and " + inSql + " order by length(deptParentLevel), deptNo  ", values);
        }
		return list;
	}
	
	
	
	/**
	 * 根据部门uuid字符串获取的部门对象List
	 * @return
	 */
	public List<TeeDepartment> getDeptListByUuids(String uuids){
		//uuids = TeeStringUtil.getSqlStringParse(uuids);
		if(uuids.endsWith(",")){
			uuids = uuids.substring(0, uuids.length() -1);
		}
		String hql = "from TeeDepartment where uuid in (" +uuids+ ")";
		List<TeeDepartment> list = executeQuery(hql, null);
		return list;
	}
	
	
	
	/**
	 * 根据部门名称判断部门是否存在 ---可扩展至上级部门条件--- 可扩展多级，以斜杠分割
	 * @author syl
	 * @date 2013-11-14
	 * @param deptName
	 * @param parentDeptName
	 * @return
	 */
	public Map checkExistedDept(String deptName , String parentDeptName ){
		String hql = "from TeeDepartment where deptName = ?";
		//String[] values = {deptName.trim()};
		List list = new ArrayList();
		list.add(deptName);
		TeeDepartment dept = null;
		Map map = new HashMap();
		long count  = 0;
		if(!TeeUtility.isNullorEmpty(parentDeptName.trim())){
			/*String parentDeptNameArray[] = parentDeptName.split("/");//多级查询
			for (int i = 0; i < parentDeptNameArray.length; i++) {
				dept = getDeptByName(parentDeptNameArray[i]);
				if(dept == null){
					break;
				}
			}*/
			dept = getParentDeptByFullName(parentDeptName);
			if(dept != null){
				hql =  hql + " and deptParent.uuid = ? ";
				list.add(dept.getUuid());
			}
		}else{
			hql =  hql + " and deptParent is null ";
		}
		hql = "select count(*) "+ hql ;
		count = countByList(hql, list);
		map.put("parentDept", dept);
		if(count > 0){
			map.put("exist", true);
		}else{
			map.put("exist", false);
		}
		return map;
	}
	
	/**
	 * 检查部门是否存在  --- 新增或者更新
	 * @param deptName
	 * @param parentDeptId
	 * @return
	 */
	public boolean checkExistDeptName(String deptName ,int sid ,int parentDeptId){
		String hql = "select count(*) from TeeDepartment where deptName = ?";
		List list = new ArrayList();
		list.add(deptName.trim());
		if(parentDeptId > 0){
			hql = hql + " and deptParent.uuid = ? ";
			list.add(parentDeptId);
		}
		if(sid > 0){
			hql = hql + " and uuid != ? ";
			list.add(sid);
		}
		long count = countByList(hql, list);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 根据部门名称 获取部门对象
	 * @author syl
	 * @date 2013-11-14
	 * @param deptName
	 * @return
	 */
	public TeeDepartment getDeptByName(String deptName){
		String hql = "from TeeDepartment where deptName = ?";
		String[] values = {deptName.trim()};
		List<TeeDepartment> list = executeQuery(hql, values);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据部门全路径名称 获取部门对象
	 * @author syl
	 * @date 2013-11-14
	 * @param deptName
	 * @return
	 */
	public TeeDepartment getParentDeptByFullName(String deptFullName){
		if(!deptFullName.startsWith("/")){
			deptFullName =  "/" + deptFullName;
		}
		String hql = "from TeeDepartment where deptFullName = ?";
		String[] values = {deptFullName.trim()};
		List<TeeDepartment> list = executeQuery(hql, values);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据部门名称全称获取部门对象
	 * @param deptFullName
	 * @return
	 */
	public TeeDepartment getDeptByFullName(String deptFullName){
		String hql = "from TeeDepartment where deptFullName = ?";
		String[] values = {deptFullName.trim()};
		List<TeeDepartment> list = executeQuery(hql, values);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据部门uuid  获取部门集合
	 * @param deptManageIds
	 * @return
	 */
	public List<TeeDepartment> getDeptByUuids(String deptUuids) {
		if(deptUuids==null || "".equals(deptUuids)){
			deptUuids = "0";
		}
		if(deptUuids.endsWith(",")){
			deptUuids = deptUuids.substring(0, deptUuids.length() -1);
		}
      //  Object[] values ={personUuids};
        String hql  = " from TeeDepartment  where uuid in ("+deptUuids+ ")";
		return find(hql, null);
	}
	
	public List<TeeDepartment> getDeptByUuidsSimple(String deptUuids) {
		if(deptUuids==null || "".equals(deptUuids)){
			deptUuids = "0";
		}
		if(deptUuids.endsWith(",")){
			deptUuids = deptUuids.substring(0, deptUuids.length() -1);
		}
        String hql  = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment  where "+TeeDbUtility.IN("uuid", deptUuids)+" order by deptNo asc";
		List<TeeDepartment> list = new ArrayList();
		list.addAll(find(hql, null));
		hql  = "select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment  where "+TeeDbUtility.IN("uuid", deptUuids)+" and deptParent is null order by deptNo asc";
		list.addAll(find(hql, null));
        return list;
	}

			/**
		 * 
		 * 通用选择部门，条件查询，根据部门名称进行模糊查询
		 * @param userInfo 部门信息
		 * @param deptInfo
		 * @param privNoFlag : 是否需要处理权限控制   1-处理  其它不处理
		 * @param preson 系统登录人员
		 * @param model
		 * @return
		 */
		public List<TeeDepartment>  getSelectDeptByDeptName(String deptInfo) {
			Object[] obj = {"%" + deptInfo + "%"};
			String hql = "from TeeDepartment  where 1=1 and deptName like ? ";
			List<TeeDepartment> list = new ArrayList<TeeDepartment>();
//			if(privNoFlag.equals("1")){
			/*	String andHql = getSendPrivHqlStrByPersonPostPrivAndModulePriv(person, model);
				if(andHql.equals("0")){
					return list;
				}else {
					if(!TeeUtility.isNullorEmpty(andHql)){
						hql = hql + andHql;
					}
				}*/
//			}
	
			hql = hql + " order by deptNo";
			list = find(hql, obj);

			return list;
		}

		
		/**
		 * 搜索当前登陆人  有管理权限的部门
		 * @param deptInfo
		 * @param postDeptIds
		 * @return
		 */
			public List<TeeDepartment> selectPostDeptByDeptName(
					String deptInfo, String postDeptIds) {
	     		Object[] obj = {"%" + deptInfo + "%"};
				String hql = "from TeeDepartment  where 1=1 and deptName like ? ";
				if(!"0".equals(postDeptIds)){
					hql+=" and uuid in ("+postDeptIds+") ";
				}
				List<TeeDepartment> list = new ArrayList<TeeDepartment>();
//				
		
				hql = hql + " order by deptNo";
				list = find(hql, obj);

				return list;
			}
			
		public List getDeptFullIdsByDeptIds(String deptIds){
			return executeQuery("select deptFullId from TeeDepartment where "+TeeDbUtility.IN("uuid", deptIds), null); 
		}
		
		
	public void setChildDeptUnit(TeeDepartment parentDept) {
		List<TeeDepartment> children = this.getChildDept(parentDept.getUuid());
		if(children != null) {
			for(TeeDepartment dept : children) {
				if(dept.getDeptType()!=2) {
					dept.setUnit(parentDept.getUnit());
					this.updateDept(dept);
					//递归处理下级
					this.setChildDeptUnit(dept);
					//更新本级
				}
			}			
		}
	}
	
}