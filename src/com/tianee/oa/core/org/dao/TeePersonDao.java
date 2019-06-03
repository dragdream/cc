package com.tianee.oa.core.org.dao;


import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserOnline;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.model.TeeModulePrivModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.secure.TeePassEncrypt;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("personDao")
public class TeePersonDao  extends TeeBaseDao<TeePerson> {
	
	/**
	 * 增加组织
	 * @param TeeDepartment
	 */
	public void addPerson(TeePerson TeeDept) {
		save(TeeDept);	
	}
	
	/**
	 * 更新
	 * @param TeePerson
	 */
	public void updatePerson(TeePerson TeeDept) {
		update(TeeDept);	
	}
	
	/**
	 * 获取所有人员 包括已删除人员
	 * @author syl
	 * @date 2013-11-20
	 * @return
	 */
	public List<TeePerson> getAllUser() {
	    @SuppressWarnings("unchecked")
	    String hql  = " from TeePerson  order by userRole.roleNo asc";
	    Session session = getSession();
	    Query query = session.createQuery(hql);
	    Iterator<TeePerson> it = query.iterate();
	    List<TeePerson> list = new ArrayList();
	    while(it.hasNext()){
	    	list.add(it.next());
	    }
//		List<TeePerson> list = (List<TeePerson>) gets
		return list;
	}
	
	/**
	 * 获取所有人员 不包括已删除人员
	 * @author syl
	 * @date 2013-11-20
	 * @return
	 */
	public List<TeePerson> getAllUserNoDelete() {
	    @SuppressWarnings("unchecked")
	    String hql  = "select uuid from TeePerson  where  deleteStatus <> '1'  order by userRole.roleNo , userNo";
		List<TeePerson> list = new ArrayList<TeePerson>();
		List uuidList = executeQuery(hql, null);
		TeePerson p = null;
		for(Object uuid:uuidList){
			p = load(TeeStringUtil.getInteger(uuid, 0));
			if(p!=null){
				list.add(p);
			}
		}
		return list;
	}
	
	public List<TeePerson> getAllUserNoDeletePage(int firstResult,int pageSize,TeeDataGridModel dm)throws ParseException{
		@SuppressWarnings("unchecked")
	    String hql  = "select uuid from TeePerson  where  deleteStatus <> '1'  order by userRole.roleNo , userNo";
		List<TeePerson> list = new ArrayList<TeePerson>();
		List param =new ArrayList();
		List uuidList = pageFindByList(hql, firstResult, pageSize, param);
		TeePerson p = null;
		for(Object uuid:uuidList){
			p = load(TeeStringUtil.getInteger(uuid, 0));
			if(p!=null){
				list.add(p);
			}
		}
		return list;
	}
	
	public  long getAllUserNoDeleteCount() throws ParseException {
		@SuppressWarnings("unchecked")
	    String hql  = "select count(*) from TeePerson  where  deleteStatus <> '1'  order by userRole.roleNo , userNo";
		List<TeePerson> list = new ArrayList<TeePerson>();
		List param =new ArrayList();
		long count = countByList(hql, param)==null?0L:countByList(hql, param);
		return count;
	}
	
	
	
	/**
	 * 获取所有人员 不包括已删除人员
	 * @author syl
	 * @date 2013-11-20
	 * @return
	 */
	public List<TeePerson> getAllUserNoDeleteByDeptIds(String deptIds) {
	    String hql  = "select uuid from TeePerson p where  deleteStatus <> '1' and ("+TeeDbUtility.IN("p.dept.uuid", deptIds)+" or exists(select 1 from p.deptIdOther deptIdOther where "+TeeDbUtility.IN("deptIdOther.uuid", deptIds)+")) order by userRole.roleNo , userNo";
		List<TeePerson> list = new ArrayList<TeePerson>();
		List uuidList = executeQuery(hql, null);
		TeePerson p = null;
		for(Object uuid:uuidList){
			p = load(TeeStringUtil.getInteger(uuid, 0));
			if(p!=null){
				list.add(p);
			}
		}
		return list;
	}
	
	/**
	 * 获取所有在线人员 不包括已删除人员
	 * @author syl
	 * @date 2013-11-20
	 * @return
	 */
	public List<TeePerson> getAllOnlineUserNoDelete() {
	    String hql  = "select p.uuid as UUID from TeePerson p  where  p.deleteStatus <> '1' and exists (select 1 from TeeUserOnline uo where uo.userId=p.uuid) order by p.userRole.roleNo , p.userNo";
	    List uuidList = executeQuery(hql, null);
	    
	    List<TeePerson> list = new ArrayList();
	    for(Object uuid:uuidList){
	    	list.add(get(TeeStringUtil.getInteger(uuid, 0)));
	    }
		return list;
	}
	
	/**
	 * 获取所有在线人员 不包括已删除人员（通过部门过滤）
	 * @author syl
	 * @date 2013-11-20
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<TeePerson> getAllOnlineUserNoDeleteByDeptIds(String deptIds) {
	    String hql  = "select p.uuid as UUID from TeePerson p  where  p.deleteStatus <> '1' and ("+TeeDbUtility.IN("p.dept.uuid", deptIds)+" or exists(select 1 from p.deptIdOther deptIdOther where "+TeeDbUtility.IN("deptIdOther.uuid", deptIds)+")) and exists (select 1 from TeeUserOnline uo where uo.userId=p.uuid) order by p.userRole.roleNo , p.userNo";
	    List uuidList = executeQuery(hql, null);
	    
	    List<TeePerson> list = new ArrayList();
	    for(Object uuid:uuidList){
	    	list.add(get(TeeStringUtil.getInteger(uuid, 0)));
	    }
		return list;
	}
	
	/**
	 * 获取所有在线人员 不包括已删除人员（通过部门过滤）
	 * @author syl
	 * @date 2013-11-20
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Integer> getDeptUuidListByOnlineUserByDeptIds(String deptIds) {
	    String sql  = "select distinct(person.dept_id) as DEPTID from user_online,person where person.uuid=user_online.user_id and "+TeeDbUtility.IN("person.dept_id", deptIds);
	    List<Integer> list = new ArrayList();
	    List<Map> list0 = executeNativeQuery(sql, null, 0, Integer.MAX_VALUE);
	    
	    for(Map data:list0){
	    	list.add(TeeStringUtil.getInteger(data.get("DEPTID").toString(), 0));
	    }
	    
	    return list;
	}
	
	/**
	 * 查询 byUuid
	 * @param TeeDepartment
	 */
	public TeePerson selectPersonById(int uuid) {
		//System.out.println(uuid);
		TeePerson TeeDept = (TeePerson)load(uuid);
		return TeeDept;
	}
	/**
	 * By部门查询人员
	 * @param 部门UUID
	 */
	public List<TeePerson> selectPersonByDeptId(String deptId) {
	    @SuppressWarnings("unchecked")
	    Object[] values = {deptId};
	    String hql  = " from TeePerson where dept_id = ?  and deleteStatus <> '1' order by userNo";
		List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	
	/**
	 * 
	 * @author syl
	 * @date 2014-1-15
	 * @param depts
	 * @return
	 */
	public List<TeePerson> selectPersonByDepts(List<TeeDepartment> depts) {
	    @SuppressWarnings("unchecked")
	    Object[] values = {depts};
	    String hql  = " select p from TeePerson p  where  p.dept in(?)  order by p.userRole.userNo";
	   List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * By用户姓名查询人员
	 * @param person
	 */
	public List<TeePerson> getPersonByUserName(String userName) {
		String hql = "from TeePerson person where person.userName = ? and deleteStatus <> '1' ";
		String[] param = {userName};
		List<TeePerson> list = (List<TeePerson>) executeQuery(hql, param);
		return list;
	}
	
	/**
	 * By部门查询人员,且带条件查询   和权限管理
	 * @param 部门UUID
	 * @param userFilter : 人员id字符串
	 *  @param privNoFlag : 是否需要处理权限控制   1-处理  其它不处理
	 */
	public List<TeePerson> selectPersonByDeptIdAndUserFilter(int deptId , String userFilter,String deptFilter,TeePerson person ,TeeModulePrivModel model , String  privNoFlag) {
	    @SuppressWarnings("unchecked")
	    String privNoFlagTemp = TeeStringUtil.getString(privNoFlag, "0");
	    Object[] values = {deptId,deptId};
	    String hql  = "select uuid from TeePerson p where (dept.uuid = ? or exists(select 1 from p.deptIdOther od where od.uuid=? ))  and deleteStatus <> '1' "; 
	    String andHql = getSendPrivHqlStrByPersonPostPrivAndModulePriv(person, model);
		
		if(andHql.equals("0")){
			return new ArrayList();
		}else {
			if(!TeeUtility.isNullorEmpty(andHql)){
				hql = hql + andHql;
			}
		}
		
	    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
			return new ArrayList();
		}
	    
	    if(!TeeUtility.isNullorEmpty(deptFilter) && (model==null || model.getSid()==0)){//查询deptFilter
			hql += " and ("+TeeDbUtility.IN("p.dept.uuid", deptFilter)+" or exists (select 1 from p.deptIdOther deptIdOther where "+TeeDbUtility.IN("deptIdOther.uuid", deptFilter)+")) ";
//			System.out.println(hql);
	    }
	    
		if(!userFilter.equals("0")){//需要处理
			if(userFilter.endsWith(",")){
				userFilter = userFilter.substring(0, userFilter.length()-1);
			}
			hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
		}
		hql = hql + "order by userNo asc";
		List uuidList;
		List<TeePerson> list = new ArrayList();
		uuidList =  executeQuery(hql, values);
		for(Object uuid:uuidList){
			list.add(load(TeeStringUtil.getInteger(uuid, 0)));
		}
		return list;
	}
	
	
	/**
	 * 获取当前用户管理权限  --- 按模块设置  
	 * @param model  模块设置
	 * @param userFilter : 人员id字符串
	 */
	public List<TeePerson> selectManagerPostPerson(TeePerson person ,TeeModulePrivModel model , String userFilter , String andHql) {
	    @SuppressWarnings("unchecked")
	    List<TeePerson> list = new ArrayList<TeePerson>();
	    TeeUserRole role = person.getUserRole();
	    if(role == null){
	    	return list;
	    }
	    Object[] values = {role.getRoleNo()};
	    String hql  = " from TeePerson where userRole.roleNo > ? and deleteStatus <> '1' "; 
	   // String andHql = personManagerI.getManagerPostPrivHqlStrByPersonPostPrivAndModulePriv(person, model);
		
		if(andHql.equals("0")){
			return new ArrayList();
		}else {
			if(!TeeUtility.isNullorEmpty(andHql)){
				hql = hql + andHql;
			}
		}
		
	    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
			return new ArrayList();
		}
		if(!userFilter.equals("0")){//需要处理
			if(userFilter.endsWith(",")){
				userFilter = userFilter.substring(0, userFilter.length()-1);
			}
			hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
		}
		hql = hql + "order by userRole.roleNo ,userNo";
	    list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 获取当前用户管理权限ByDeptId  --- 按模块设置  
	 * @param model  模块设置
	 * @param userFilter : 人员id字符串  0-不处理  空字符串-无 
	 */
	public List<TeePerson> selectManagerPostPersonByDept(TeePerson person ,TeeModulePrivModel model , int deptId,String userFilter , String andHql) {
	    @SuppressWarnings("unchecked")
	    List<TeePerson> list = new ArrayList<TeePerson>();
	    TeeUserRole role = person.getUserRole();
	    if(role == null){
	    	return list;
	    }
	    Object[] values = { deptId};
	    String hql  = " from TeePerson where  dept.uuid = ? and deleteStatus <> '1' "; 
	    boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person, "");
	    if(!isSuperAdmin){
	    	if(model == null){//没有设置按模块设置
				hql = hql + " and userRole.roleNo > " + role.getRoleNo();
			}else{
				if(andHql.equals("0")){
					return new ArrayList();
				}else {
					if(!TeeUtility.isNullorEmpty(andHql)){
						hql = hql + andHql;
					}
				}
			}
		    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
				return new ArrayList();
			}
			if(!userFilter.equals("0")){//需要处理
				if(userFilter.endsWith(",")){
					userFilter = userFilter.substring(0, userFilter.length()-1);
				}
				hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
			}
	    }
		
		hql = hql + "order by userRole.roleNo ,userNo";
	    list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}

	
	/**
	 * By部门查询人员,且带条件查询   和权限管理  ---  所有角色的人员
	 * @param 部门UUID
	 * @param userFilter : 人员id字符串
	 */
	public List<TeePerson> selectPersonByDeptsAndPostPriv(boolean isSuperAdmin , List<TeeDepartment> depts ,TeePerson person ,TeeModulePrivModel model) {
	    @SuppressWarnings("unchecked")
	    String deptIds = "";
	    for (int i = 0; i < depts.size(); i++) {
	    	deptIds = deptIds + depts.get(i).getUuid() + ",";
		}
	    if(!deptIds.equals("")){
	    	deptIds = deptIds.substring(0, deptIds.length() - 1);
	    }
	    int roleNo = 0;//角色排序号
	    if(person.getUserRole() != null){
	    	roleNo = TeeStringUtil.getInteger(person.getUserRole().getRoleNo(), 0);
	    }
	    Object[] values = null;
	    String hql  = " from TeePerson  where dept.uuid in ("+deptIds+")  and deleteStatus <> '1' "; 
	    if(model == null || model.getSid() == 0 ){//没有按模块设置
	    	hql = hql + " and userRole.roleNo > " + roleNo;
	    }
	    if(isSuperAdmin){//是超级管理员
	    	hql = "from TeePerson where deleteStatus <> '1'";
	    	values = null;
	    }else{
	    	String andHql = getPostPrivHqlStrByPersonPostPrivAndModulePriv(person, model);//按模块或者管理范围
	    	if(andHql.equals("0")){
				return new ArrayList();
			}else {
				if(!TeeUtility.isNullorEmpty(andHql)){
					hql = hql + andHql;
				}
			}
	    }
		
		hql = hql + " order by userRole.roleNo";
	    List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	
	/**
	 * By部门查询人员--包括辅助部门相关人员 且人员在线
	 * @author syl
	 * @date 2013-11-19
	 * @param deptId
	 * @return
	 */
	public List<TeePerson> selectOnlinePersonByDeptIdandOtherDept(int deptId) {
	    @SuppressWarnings("unchecked")
	    Object[] values = {deptId,deptId};
	    String hql  = " select p from TeePerson p left join  p.deptIdOther d left join p.userOnline online where (d.uuid = ? or p.dept.uuid = ?) and p.deleteStatus <> '1'  and online.userStatus = 0  order by p.userNo";
	   List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	/**
	 * By部门查询人员--包括辅助部门相关人员
	 * @param 部门UUID
	 */
	public List<TeePerson> selectPersonByDeptIdandOtherDept(int deptId) {
	    @SuppressWarnings("unchecked")
	    Object[] values = {deptId,deptId};
	    String hql  = " select p from TeePerson p left join  p.deptIdOther d where (d.uuid = ? or p.dept.uuid = ?) and p.deleteStatus <> '1' order by p.userNo";
	   List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	/**
	 * By部门查询人员数量--包括辅助部门相关人员
	 * @param 部门UUID
	 */
	public long selectPersonCountByDeptIdandOtherDept(int deptId) {
	    @SuppressWarnings("unchecked")
	    Object[] values = {deptId,deptId};
	    String hql  = " select count(p.uuid) from TeePerson p left join  p.deptIdOther d where (d.uuid = ? or p.dept.uuid = ?) and p.deleteStatus <> '1'";
	   //List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
	   long total = count(hql, values);
		return total;
	}
	
	/**
	 * By部门查询人员--包括辅助部门相关人员且带管理范围权限
	 * @param 部门UUID
	 * @param postDeptIds 
	 * @param   userRoleNo 用户角色排序号
	 * @param   isSuperAdmin 是否为超级管理员
	 */
	public List<TeePerson> selectPersonByDeptIdAndOtherDeptAndPostDept(int deptId,boolean  isSuperAdmin ,String postDeptIds , int userRoleNo ,String isAdmin) {
	    if(StringUtils.isBlank(isAdmin)) {
	    	isAdmin = "0";
	    }
	    Object[] values = {deptId,deptId,isAdmin};

		@SuppressWarnings("unchecked")
	    List<TeePerson> list = new ArrayList<TeePerson>();
	    List uuidList = null;
	    String hql  = " select p.uuid as UUID from TeePerson p left join p.deptIdOther d  left join p.userRole ul where (d.uuid = ? or p.dept.uuid = ?) and p.isAdmin = ? and p.deleteStatus <> '1' " ;
	    if(postDeptIds.equals("")){//为空则没有管理范围
	    	return list ;
	    }else if(postDeptIds.equals("0")) {//全体范围，
	    	
	    }else{
	    	if(postDeptIds.endsWith(",")){
	    		postDeptIds = postDeptIds.substring(0, postDeptIds.length() - 1);
	    	}
	    	hql = hql + " and "+TeeDbUtility.IN("p.dept.uuid", postDeptIds); 
	    }
	    if(!isSuperAdmin && !"1".equals(isAdmin)){//如果不是超级管理员
	    	hql = hql + " and ul.roleNo > " + userRoleNo ; 
	    }
	    hql = hql +	"order by p.userNo  ";
	    uuidList = executeQuery(hql, values);
	    TeePerson p = null;
	    for(Object uuid:uuidList){
	    	p = load(TeeStringUtil.getInteger(uuid, 0));
	    	list.add(p);
	    }
		return list;
	}
	
	
	
	
	/**
	 * By部门查询人员
	 * @param 角色UUid
	 */
	public List<TeePerson> selectPersonByRoleId(int roleId) {
	    @SuppressWarnings("unchecked")
	    Object[] values = {roleId};
	    String hql  = " from TeePerson where userRole.uuid = ? and deleteStatus <> '1' order by userNo";
		List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * By部门查询人员 且带条件 包括管理权限
	 * 
	 * @param 角色UUid
	 * @param  userFilter 人员ID字符串
	 *  * @param privNoFlag : 是否需要处理权限控制   1- 处理  及需要处理管理和权限设置
	 */
	public List<TeePerson> selectPersonByRoleIdAndFilter(int roleId,String userFilter,String deptFilter,TeePerson person ,TeeModulePrivModel model,String privNoFlag ) {
	    Object[] values = {roleId,roleId};
	    String hql  = " from TeePerson p where (userRole.uuid = ? or exists(select 1 from p.userRoleOther ou where ou.uuid=? ) )and deleteStatus <> '1'";
	    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
			return new ArrayList<TeePerson>();
		}
	    
	    if(!TeeUtility.isNullorEmpty(deptFilter)){//针对部门过滤
			hql+= " and ("+TeeDbUtility.IN("dept.uuid", deptFilter)+" or exists (select 1 from p.deptIdOther deptIdOther1 where "+TeeDbUtility.IN("deptIdOther1.uuid", deptFilter)+"))";
		}
	    
	    String privNoFlagTemp = TeeStringUtil.getString(privNoFlag, "0");
//	    if(privNoFlagTemp.equals("1")){//带权限
	    	 String andHql = getSendPrivHqlStrByPersonPostPrivAndModulePriv(person,
	 				model);
	 		if (andHql.equals("0")) {
	 			return new ArrayList<TeePerson>();
	 		} else {
	 			if (!TeeUtility.isNullorEmpty(andHql)) {
	 				hql = hql + andHql;
	 			}
	 		}
	    	
//	    }

		if(!userFilter.equals("0")){//需要处理
			if(userFilter.endsWith(",")){
				userFilter = userFilter.substring(0, userFilter.length()-1);
			}
			hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
		}
		hql = hql + "  order by userNo";
		List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 获取本部门和所有下级部门人员
	 * @param deptId  ： 部门uuid 
	 * @param deptLevel ： 部门级别uuId
	 * @return
	 */
	public List<TeePerson> selectDeptAndChildDeptPerson(int deptId,String deptLevel) {
	    Object[] values = {deptId ,deptLevel + "%"};
	    String hql  = "select person from TeePerson person left join person.dept d where" +
	    	  " ( d.uuid = ? or  d.deptParentLevel like ? )  and person.deleteStatus != '1'";
	 /*   String sql = "select person.uuid , person.user_name from  "
	    		+ "tee_person person left join tee_department dept on person.dept_id = dept.uuid "
	    		+ " where person.dept_id = ? ||  order by user_no ";*/
		List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	public List<TeePerson> selectDeptAndChildDeptPersonPage(int deptId,String deptLevel,int firstResult,int pageSize,TeeDataGridModel dm)throws ParseException {
//	    Object[] values = {deptId ,deptLevel + "%"};
	    List param =new ArrayList();
	    param.add(deptId);
	    param.add(deptLevel + "%");
	    String hql  = "select person from TeePerson person left join person.dept d where" +
	    	  " ( d.uuid = ? or  d.deptParentLevel like ? )  and person.deleteStatus != '1'";
	 /*   String sql = "select person.uuid , person.user_name from  "
	    		+ "tee_person person left join tee_department dept on person.dept_id = dept.uuid "
	    		+ " where person.dept_id = ? ||  order by user_no ";*/
		List<TeePerson> list = (List<TeePerson>) pageFindByList(hql, firstResult, pageSize, param);
		return list;
	}
	
	public long selectDeptAndChildDeptPersonCount(int deptId,String deptLevel) throws ParseException {
		 List param =new ArrayList();
		    param.add(deptId);
		    param.add(deptLevel + "%");
		    String hql  = "select count(*) from TeePerson person left join person.dept d where" +
		    	  " ( d.uuid = ? or  d.deptParentLevel like ? )  and person.deleteStatus != '1'";
		    long count = countByList(hql, param)==null?0L:countByList(hql, param);
			return count;
		    
	}
	
	

	/**
	 * 获取本部门和下级部门, 且带人员查询 ---发送权限
	 * @param deptId  ： 部门uuid 
	 * @param deptLevel ： 部门级别uuId
	 * @param userFilter : 人员Id字符串
	 * @param privNoFlag : 是否需要处理权限控制   1- 处理  及需要处理管理和权限设置
	 * @return
	 */
	public List<TeePerson> selectSendDeptAndChildDeptPersonAndUserFilter(int deptId,String deptLevel ,String userFilter,String deptFilter,TeePerson person ,TeeModulePrivModel model ,String privNoFlag ) {
	    Object[] values = {deptId,deptId ,deptLevel + "%"};
	    String hql  = "select uuid from TeePerson p where p.deleteStatus='0' and " +
	    	  " (exists(select 1 from p.deptIdOther od where od.uuid=? ) or  dept.uuid = ? or  dept.deptParentLevel like ? )  and  deleteStatus <> '1'";
	    List<TeePerson> list = new ArrayList<TeePerson>();
	    String privNoFlagTemp = TeeStringUtil.getString(privNoFlag, "0");
//	    if(privNoFlagTemp.equals("1")){//带权限
	    	
			String andHql = getSendPrivHqlStrByPersonPostPrivAndModulePriv(person,
					model);
			if (andHql.equals("0")) {
				return list;
			} else {
				if (!TeeUtility.isNullorEmpty(andHql)) {
					hql = hql + andHql;
				}
			}
//	    }
	
	    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
			return new ArrayList();
		}
	    
	    if(!TeeUtility.isNullorEmpty(deptFilter) && (model==null || model.getSid()==0)){//查询deptFilter
			hql += " and ("+TeeDbUtility.IN("p.dept.uuid", deptFilter)+" or exists (select 1 from p.deptIdOther deptIdOther where "+TeeDbUtility.IN("deptIdOther.uuid", deptFilter)+")) ";
		}
	    
		if(!userFilter.equals("0")){//需要处理
			if(userFilter.endsWith(",")){
				userFilter = userFilter.substring(0, userFilter.length()-1);
			}
			hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
		}
		hql = hql + "  order by userNo asc";
		List uuidList = null;
		uuidList =  executeQuery(hql, values);
		for(Object uuid:uuidList){
			list.add(load(TeeStringUtil.getInteger(uuid, 0)));
		}
		return list;
	}
	
	/**
	 * 获取本部门和下级部门, 且带人员查询 ---管理权限
	 * @param deptId  ： 部门uuid 
	 * @param deptLevel ： 部门级别uuId
	 * @param userFilter : 人员Id字符串
	 * @param privNoFlag : 是否需要处理权限控制   1- 处理  及需要处理管理和权限设置
	 * @return
	 */
	public List<TeePerson> selectManagerPostDeptAndChildDeptPersonAndUserFilter(int deptId,String deptLevel ,String userFilter,TeePerson person ,TeeModulePrivModel model ,String privNoFlag ) {
	    Object[] values = {deptId ,deptLevel + "%"};
	    String hql  = " from TeePerson where" +
	    	  " ( dept.uuid = ? or  dept.deptParentLevel like ? )  and  deleteStatus <> '1'";
	    List<TeePerson> list = new ArrayList<TeePerson>();
	    String privNoFlagTemp = TeeStringUtil.getString(privNoFlag, "0");
//	    if(privNoFlagTemp.equals("1")){//带权限
	    	
			String andHql = getSendPrivHqlStrByPersonPostPrivAndModulePriv(person,
					model);
			if (andHql.equals("0")) {
				return list;
			} else {
				if (!TeeUtility.isNullorEmpty(andHql)) {
					hql = hql + andHql;
				}
			}
//	    }
	
	    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
			return new ArrayList();
		}
		if(!userFilter.equals("0")){//需要处理
			if(userFilter.endsWith(",")){
				userFilter = userFilter.substring(0, userFilter.length()-1);
			}
			hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
		}
		hql = hql + "  order by userNo";
	    list = (List<TeePerson>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 删除
	 * @param TeeDepartment
	 */
	public void delSysDept(TeePerson TeeDept) {
		delete(TeeDept);	
	}
	
	/**
	 * 删除  更改状态
	 * @param 
	 * status:删除状态 0-正常  1-已删除
	 */
	public int updateDelPersonByUuids(String  personUuids ,String status) {
		if(personUuids.endsWith(",")){
			personUuids = personUuids.substring(0, personUuids.length() -1);
		}
		TeePassEncrypt passEn = new TeePassEncrypt();
		String sql = "update TeePerson	set deleteStatus = ? where uuid in(" + personUuids + ")";
		Object[] values = {status};
		int count = deleteOrUpdateByQuery(sql, values);
		return count;
	}
	/**
	 * 校验用户名是否存在
	 * @param uuid ：uuid
	 * @param userId ： 用户名
	 * @return
	 */
	public boolean checkUserExist(int uuid , String userId) {
		//String sql = "select uuid from PERSON	where user_id = ? and uuid != ? and delete_status != '1'" ;//
		String sql = "select uuid from PERSON	where user_id = ? and uuid != ?" ;//与删除人员也不能同名
		Object[] values = {userId,uuid};
		List list =  getBySql(sql, values);
		if(list.size() > 0){
			return true;
		}
		return false;		
	} 
	
	/**
	 * 校验用户名是否存在  --高速波
	 * @param uuid ：uuid
	 * @param userId ： 用户名
	 * @return
	 */
	public boolean checkGsbUserExist(int uuid , String userId) {
		String sql = "select uuid from PERSON	where GSB_USER_ID = ? and uuid != ?" ;//与删除人员也不能同名
		Object[] values = {userId,uuid};
		List list =  getBySql(sql, values);
		if(list.size() > 0){
			return true;
		}
		return false;		
	} 
	
	/**
	 * 校验用户名昵称是否存在
	 * @param uuid ：uuid
	 * @param nickName ： 昵称
	 * @return
	 */
	public boolean checkNickNameExist(int uuid , String nickName) {
		String hql = "select count(*) from TeePerson	where nickName = ? and uuid != ?  and deleteStatus <> '1' " ;
		Object[] values = {nickName , uuid};
		long count = count(hql, values);
		if(count > 0){
			return true;
		}
		return false;		
	}
	/*
	 * 根据hql 查询数量   --包括辅助部门
	 */
	 public Long countByDeptId(String deptId ,boolean  isSuperAdmin ,String postDeptIds , int userRoleNo ,String isAdmin) {
		 
		 //String hql  = " select p from TeePerson p left join p.deptIdOther d  left join p.userRole ul where d.uuid = ? or p.dept.uuid = ? and p.deleteStatus <> '1' " ;
		 Object[] param = {isAdmin};
		 String hql = " from TeePerson t where isAdmin = ? and (exists (select 1 from t.deptIdOther deptOther where deptOther.uuid = "+deptId+")  or  dept.uuid = " + deptId + ") and deleteStatus <> '1'";
		 hql = " select count(*) " + hql;
		 List<TeePerson> list = new ArrayList<TeePerson>();
		 if (postDeptIds.equals("")) {// 为空则没有管理范围
			 return 0L;
		 } else if (postDeptIds.equals("0")) {// 全体范围，

		 } else {
			 if (postDeptIds.endsWith(",")) {
			 	postDeptIds = postDeptIds
						.substring(0, postDeptIds.length() - 1);
			 } 
			 hql = hql + " and t.dept.uuid in (" + postDeptIds + ")";
		}
		if (!isSuperAdmin && !"1".equals(isAdmin)) {// 如果不是超级管理员
			hql = hql + " and t.userRole.roleNo > " + userRoleNo;
		}
		 return count(hql,param);
	}
	 
	/**
	 * 用户管理 --- 按部门查询  管理范围 和 角色  包括辅助部门
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param deptId
	 * @param person
	 * @return
	 */
	public  List<TeePerson> getPersonPageFind(int firstResult,int pageSize,TeeDataGridModel dm,int deptId,boolean  isSuperAdmin ,String postDeptIds , int userRoleNo ,String isAdmin) { 
		
		Object[] param = {isAdmin,deptId};
		String hql = " from TeePerson t where isAdmin = ? and (exists (select 1 from t.deptIdOther deptOther where deptOther.uuid = "+deptId+")  or t.dept.uuid = ? )   and t.deleteStatus <> '1' ";
		List<TeePerson> list = new ArrayList<TeePerson>();
	    if(postDeptIds.equals("")){//为空则没有管理范围
	    	return list ;
	    }else if(postDeptIds.equals("0")) {//全体范围，
	    	
	    }else{
	    	if(postDeptIds.endsWith(",")){
	    		postDeptIds = postDeptIds.substring(0, postDeptIds.length() - 1);
	    	}
	    	hql = hql + " and t.dept.uuid in (" + postDeptIds + ")"; 
	    }
	    if(!isSuperAdmin && !"1".equals(isAdmin)){//如果不是超级管理员
	    	hql = hql + " and t.userRole.roleNo > " + userRoleNo ; 
	    }
	    hql = hql + " order by t.userNo "  +  dm.getOrder();
	    
	 
		return pageFind(hql, firstResult, pageSize, param);
	 }
	
	
	/**
	 * 根据部门uuid串获取的所有人员
	 * 
	 * @param deptIds: 部门uuid字符串
	 */
	public List<TeePerson>  getPersonByDeptIds(String deptIds) {
        List<TeePerson> list = new ArrayList<TeePerson>();
        if(TeeUtility.isNullorEmpty(deptIds)){
        	return list;
        }
        String hql  = " from TeePerson  where "+TeeDbUtility.IN("dept.uuid", deptIds)+" and deleteStatus <> '1' order by userNo asc";
		return find(hql, null);

	}
	
	
	/**
	 * 根据人员uuid字符串获取的所有人员
	 * 
	 * @param personUuids: 人员uuid字符串
	 */
	public List<TeePerson>  getPersonByUuids(String personUuids) {
		if(personUuids==null || "".equals(personUuids)){
			personUuids = "0";
		}
		if(personUuids.endsWith(",")){
			personUuids = personUuids.substring(0, personUuids.length() -1);
		}
      //  Object[] values ={personUuids};
        String hql  = " from TeePerson  where uuid in ("+personUuids+ ") order by userNo asc";
		return find(hql, null);
	}
	

	/**
	 * 根据人员uuid字符串获取的所有人员的userName和uuid
	 * 
	 * @param personUuids: 人员uuid字符串
	 * String[0]-人员UUID字符串，逗号分隔
	 * String[1]-人员姓名字符串，逗号分隔
	 * String[2]-人员ID字符串，逗号分隔
	 */
	public String[]  getPersonNameAndUuidByUuids(String personUuids) {
		String[] str = new String[3];
		String uuids = "";
		String userNames = "";
		String userIds = "";
		if(!TeeUtility.isNullorEmpty(personUuids)){
			if(personUuids.endsWith(",")){
				personUuids = personUuids.substring(0, personUuids.length() -1);
			}
			String sql = "select UUID,USER_NAME,USER_ID from PERSON where UUID in (" +personUuids+ ")";
	        Object[] values = {personUuids};
			List list = getBySql(sql);
			
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				//String uuid = obj[0].toString();
				String userName = (String)obj[1];
				uuids = uuids + obj[0];
				userNames = userNames + userName;
				userIds = userIds + obj[2];
				if(i!=list.size()-1){
					uuids+=",";
					userNames+=",";
					userIds+=",";
				}
			}
		}
		
		str[0] = uuids;
		str[1] = userNames;
		str[2] = userIds;
		return str;
	}
	
	
	
	/**
	 * 人员登录
	 * 根据人员名称查询人员
	 * 
	 * @param 
	 */
	public TeePerson  getPersonByUserId(String userId) {
//        Object[] values ={userId };
        String hql  = " from TeePerson  where userId = ? and deleteStatus <> '1' ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, userId);
        TeePerson person = (TeePerson) query.uniqueResult();
		return person;
	}
	
	/**
	 * 人员登录
	 * 根据高速波云平台账号查询人员
	 * 
	 * @param 
	 */
	public TeePerson  getPersonByCloudUserId(String userId) {
//        Object[] values ={userId };
        String hql  = " from TeePerson  where gsbUserId = ? and deleteStatus <> '1' ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, userId);
        TeePerson person = (TeePerson) query.uniqueResult();
		return person;
	}
	
	/**
	   * 更新最后一次登录信息
	   * @return
	   * @throws Exception
	   */
	  public void updateLastVisitInfo(int uuid, String ip) throws Exception {
		  if(TeeUtility.isNullorEmpty(ip)){
			  ip = "";
		  }
		  String hql = "update from TeePersonDynamicInfo set lastVisitTime = current_date() , lastVisitIp = ? where person.uuid = ?";
		  Object[] values = {ip,uuid};
		  executeUpdate(hql, values);
	  }
	  
	  

		/**
		 * 根据人员uuid获取人员姓名
		 */
		public String  getPersonNameByUuid(String personUuid) {
			if(!TeeUtility.isNullorEmpty(personUuid)){
				String sql = "select USER_NAME from PERSON where UUID = '" +personUuid+"'";
				List list = getBySql(sql);
				if(list.size() > 0){
					String obj = (String) list.get(0);
					//String userName = (String)obj[0];
					return obj;
				}
			}
			return "";
		}
		

		/**
		 * 条件查询----权限范围控制
		 */
		public List<TeePerson>  queryPerson(TeePersonModel model ,boolean  isSuperAdmin ,String postDeptIds , int userRoleNo) {
			String hql = "from TeePerson p where p.deleteStatus != '1' ";
			List list = new ArrayList();
			if(!TeeUtility.isNullorEmpty(model.getUserId())){
				hql = hql + " and userId like ?";
				list.add("%" + model.getUserId() + "%");
			}
			if(!TeeUtility.isNullorEmpty(model.getUserName())){
				hql = hql + " and userName like ?";
				list.add("%" + model.getUserName() + "%");
			}
			if(!TeeUtility.isNullorEmpty(model.getByname())){
				hql = hql + " and byName like ?";
				list.add("%" + model.getByname() + "%");
			}
			
			if(!TeeUtility.isNullorEmpty(model.getSex())){
				hql = hql + " and sex = ? ";
				list.add(model.getSex());
			}
			if(!TeeUtility.isNullorEmpty(model.getUserRoleStr())){
				hql = hql + " and (userRole.uuid =  " + model.getUserRoleStr()+") or exists (select 1 from p.userRoleOther userRoleOther where userRoleOther.uuid="+model.getUserRoleStr()+")";
				//list.add( model.getUserRoleStr() );
			}
			
			if(!TeeUtility.isNullorEmpty(model.getDeptId())){
				hql = hql + " and dept.uuid =  " + model.getDeptId() ;
			//	list.add( model.getDeptId());
			}
			
			if(!TeeUtility.isNullorEmpty(model.getPostDeptStr())){
				hql = hql + " and postPriv = ?";
				list.add(model.getPostPriv());
			}
			
			if(!TeeUtility.isNullorEmpty(model.getNotLogin())){
				hql = hql + " and notLogin = ?";
				list.add( model.getNotLogin());
			}
			
			if(!TeeUtility.isNullorEmpty(model.getNotViewTable())){
				hql = hql + " and notViewTable = ?";
				list.add( model.getNotViewTable());
			}
			if(!TeeUtility.isNullorEmpty(model.getNotViewUser())){
				hql = hql + " and notViewUser = ?";
				list.add(model.getNotViewUser());
			}
			
			if(!TeeUtility.isNullorEmpty(model.getNotSearch())){
				hql = hql + " and notSearch = ?";
				list.add( model.getNotSearch());
			}

			if(!TeeUtility.isNullorEmpty(model.getIsAdmin())){
				hql = hql + " and isAdmin = ?";
				list.add( model.getIsAdmin());
			}
			
			if(postDeptIds.equals("")){//为空则没有管理范围
			    return list ;
			}else if(postDeptIds.equals("0")) {//全体范围，
			    	
			}else{
				if(postDeptIds.endsWith(",")){
			    	postDeptIds = postDeptIds.substring(0, postDeptIds.length() - 1);
			    }
			    hql = hql + " and p.dept.uuid in (" + postDeptIds + ")"; 
			}
			if(!isSuperAdmin){//如果不是超级管理员
			    hql = hql + " and p.userRole.roleNo > " + userRoleNo ; 
			}
			hql = hql +	"order by p.userRole.roleNo ,p.userNo  ";
			
			return executeQueryByList(hql, list);
		}
		
		
		
  
		/**
		 * 清空人员密码
		 * personUuids:人员UUId字符串 以逗号分隔
		 * @param 
		 */
		public int clearPassword(String personUuids) {
			//personUuids = TeeStringUtil.getSqlStringParse(personUuids);
			if(personUuids.endsWith(",")){
				personUuids = personUuids.substring(0, personUuids.length() -1);
			}
			try {
				String md5Str = TeePassEncryptMD5.cryptDynamic("");
				String sql = "update TeePerson	set pwd = ?,lastPassTime=null where uuid in(" + personUuids + ")";
				Object[] values = {md5Str};
				int count = deleteOrUpdateByQuery(sql, values);
				return count;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;

		}

		/**
		 * 修改人员密码
		 * personUuids:人员UUId字符串
		 * password:明文密码
		 * @param 
		 */
		public int updatePassword(String uuid,String password) {
			try {
				String md5Str = TeePassEncryptMD5.cryptDynamic(password);
				String sql = "update TeePerson	set pwd = ? where uuid =" + uuid ;
				Object[] values = {md5Str};
				int count = deleteOrUpdateByQuery(sql, values);
				return count;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
		
		
		/***
		 * 按条件查询---部门、人员、角色 （不包括辅助的）
		 * @param deptIds ： 部门Id字符串
		 * @param roleIds:角色ID字符串
		 * @param userIds:人员Id字符串
		 * @return
		 */
			public ScrollableResults  queryPersonByDeptOrRoleOrUuid(String deptIds ,String roleIds , String uuids) {
				//范围
				//uuids = TeeStringUtil.getSqlStringParse(uuids);
				
				if(uuids.endsWith(",")){
					uuids = uuids.substring(0, uuids.length() -1);
				}
				
				if(deptIds.endsWith(",")){
					deptIds = deptIds.substring(0, deptIds.length() -1);
				}
				
				if(roleIds.endsWith(",")){
					roleIds = roleIds.substring(0, roleIds.length() -1);
				}
				String hql = "select p from TeePerson p ";
				String terms = "";
				boolean statu = false;
				//部门，
				if(!TeeUtility.isNullorEmpty(deptIds)){
					terms =  "  p.dept.uuid in (" + deptIds + ")";
					statu = true;
				}
				//角色
				if(!TeeUtility.isNullorEmpty(roleIds)){
					if(statu){
						terms =  terms + " or p.userRole.uuid  in (" + roleIds + ") ";
					}else{
						terms =  terms + " p.userRole.uuid  in (" + roleIds + ")";
					}
					statu = true;	
				}
				if(!TeeUtility.isNullorEmpty(uuids)){
					if(statu){
						terms = terms + " or p.uuid in (" + uuids + ")";
					}else{
						terms = terms + "  p.uuid in (" + uuids + ")";
					}
				}
				hql = hql + " where " + terms +    " order by p.userNo";
				
				Session session = this.getSession();
				ScrollableResults re =  session.createQuery(hql).scroll(ScrollMode.FORWARD_ONLY);
				return re;
			}
	

		/***
		 * 按条件查询---部门、人员、角色 （包括辅助的）
		 * @param deptIds ： 部门Id字符串
		 * @param roleIds:角色ID字符串
		 * @param userIds:人员Id字符串
		 * @return
		 */
			@Transactional(readOnly=true)
			public List<TeePerson>  queryPersonByDeptOrRoleOrUuidAndOther(String deptIds ,String roleIds , String uuids) {
				//范围
				//uuids = TeeStringUtil.getSqlStringParse(uuids);
				List<TeePerson>  list = new ArrayList<TeePerson>();
				if(uuids.endsWith(",")){
					uuids = uuids.substring(0, uuids.length() -1);
				}
				
				if(deptIds.endsWith(",")){
					deptIds = deptIds.substring(0, deptIds.length() -1);
				}
				
				if(roleIds.endsWith(",")){
					roleIds = roleIds.substring(0, roleIds.length() -1);
				}
				String hql = "select p from TeePerson p ";
				String terms = "";
				boolean statu = false;
				//部门，包括辅助部门
				if(!TeeUtility.isNullorEmpty(deptIds)){
					hql = hql + " left join p.deptIdOther dept";
					terms =  "  p.dept.uuid in (" + deptIds + ") or dept.uuid in (" + deptIds + ")";
					statu = true;
				}
				//角色，包括辅助角色
				if(!TeeUtility.isNullorEmpty(roleIds)){
					hql = hql + " left join p.userRoleOther role";
					if(statu){
						terms =  terms + " or p.userRole.uuid  in (" + roleIds + ") or role.uuid in (" +  roleIds + ")";
					}else{
						terms =  terms + " p.userRole.uuid  in (" + roleIds + ") or role.uuid in (" +  roleIds + ")";
					}
					statu = true;	
				}
				if(!TeeUtility.isNullorEmpty(uuids)){
					if(statu){
						terms = terms + " or p.uuid in (" + uuids + ")";
					}else{
						terms = terms + "  p.uuid in (" + uuids + ")";
					}
				}
				if(terms.equals("")){
					return list;
				}
				hql = hql + " where " + terms +    " order by p.userNo";
				
				list = executeQuery(hql, null);
				return list;
			}
		
			/***
			 * 工作流人员过滤 按条件查询---部门、人员、角色 （不包括辅助的）
			 * @param deptIds ： 部门Id字符串
			 * @param roleIds:角色ID字符串
			 * @param userIds:人员Id字符串
			 * @param filterHql:过滤人员条件
			 * @return
			 */
				public Iterator<TeePerson>  queryPersonByDeptOrRoleOrUuidAndFlowFilter(String deptIds ,String roleIds , String uuids, String filterHql) {
					Session session = getSession();
					List<TeePerson> list = new ArrayList();
					//范围
					String hql = "from TeePerson p where p.deleteStatus='0' and (1!=1 ";
					String terms = "";
					boolean statu = false;
					//部门，
					if(!TeeUtility.isNullorEmpty(deptIds)){
						deptIds = TeeStringUtil.formatIdsQuote(deptIds);
						terms =  " or p.dept.uuid in (" + deptIds + ") or exists (select 1 from p.deptIdOther other where other.uuid in ("+deptIds+")) ";
					}
					//角色
					if(!TeeUtility.isNullorEmpty(roleIds)){
						roleIds = TeeStringUtil.formatIdsQuote(roleIds);
						terms =  terms + " or p.userRole.uuid  in (" + roleIds + ") or exists (select 1 from p.userRoleOther other where other.uuid in ("+roleIds+")) ";	
					}
					//人员
					if(!TeeUtility.isNullorEmpty(uuids)){
						uuids = TeeStringUtil.formatIdsQuote(uuids);
						terms = terms + " or p.uuid in (" + uuids + ")";
					}
					
					hql = hql + terms+"" ;
					if(!filterHql.equals("( 1 = 1 )")){//不为空
						hql = hql + " or " + filterHql ;
					}
					hql = hql + ") order by p.userNo asc";
					
//					if(filterHql.equals("( 1 = 1 )")){
//						Query query = session.createQuery("from TeePerson where uuid=0");
//						return query.iterate();
//					}
					Query query = session.createQuery(hql);
					
//					List uuidList = null;
//					uuidList = executeQuery(hql, null);
//					for(Object uuid:uuidList){
//						list.add(load(TeeStringUtil.getInteger(uuid, 0)));
//					}
					
					return query.iterate();
				}
		

		/**
		 * 
		 * 通用选择人员，条件查询，根据用户Id或者用户姓名进行模糊查询
		 * @param userInfo 人员信息
		 * @param userFilter
		 * @param privNoFlag : 是否需要处理权限控制   1-处理  其它不处理
		 * @param preson 系统登录人员
		 * @param model
		 * @return
		 */
		public List<TeePerson>  getSelectUserByUserIdOrUserName(String userInfo,String userFilter,String deptFilter,TeePerson person ,TeeModulePrivModel model,String privNoFlag ) {
			Object[] obj = {"%" + userInfo + "%",  "%" + userInfo + "%"};
			String hql = "from TeePerson p where deleteStatus <> '1' and (userName like ? or userId LIKE ? )  ";
			List<TeePerson> list = new ArrayList<TeePerson>();
//			if(privNoFlag.equals("1")){
				String andHql = getSendPrivHqlStrByPersonPostPrivAndModulePriv(person, model);
				if(andHql.equals("0")){
					return list;
				}else {
					if(!TeeUtility.isNullorEmpty(andHql)){
						hql = hql + andHql;
					}
				}
//			}
		
			if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
				return new ArrayList();
			}
			
			if(!TeeUtility.isNullorEmpty(deptFilter)){//针对部门过滤
				hql+= " and ("+TeeDbUtility.IN("dept.uuid", deptFilter)+" or exists (select 1 from p.deptIdOther deptIdOther1 where "+TeeDbUtility.IN("deptIdOther1.uuid", deptFilter)+"))";
			}
			
			if(!userFilter.equals("0")){//需要处理
				if(userFilter.endsWith(",")){
					userFilter = userFilter.substring(0, userFilter.length() - 1);
				}
				hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
			}
			hql = hql + " order by userNo";
			list = executeQuery(hql,obj);

			return list;
		}
		
		
		/**
		 * 主界面查询条件  无权限
		 * @author syl
		 * @date 2013-12-3
		 * @return
		 */
		public List<TeePerson>  queryUserByUserIdOrUserName( String  userName) {
			Object[] values= {"%" + userName + "%" , "%" + userName + "%" };
			String hql = "from TeePerson p where deleteStatus <> '1' and ( p.userName like ? or p.userId LIKE ? ) ";
			List<TeePerson> list = new ArrayList<TeePerson>();
	
			hql = hql + " order by p.userRole, p.userNo";
			list = executeQuery(hql,values);

			return list;
		}

		
		/**
		 * 获取登录人员的发送范围hql  考虑按模块设置
		 * @param person
		 * @param model
		 * 如果返回 0 代表没有权限,可以不用执行hql语句，直接返回空lisit人员即可
		 */
		public String  getSendPrivHqlStrByPersonPostPrivAndModulePriv(TeePerson person , TeeModulePrivModel model){
			String hql = "";
			if(model == null || (model != null &&  model.getSid() <= 0) ){//如果没有按模块设置
				return "";
			}else{
				
				//部门和角色交集(and）
				//if(!model.getDeptPriv().equals("1") &&   !model.getRolePriv().equals("2")){//部门权限不是全体部门且不是所有角色
					String roleIds = model.getRoleIdStr();//指定角色
					String deptIds = model.getDeptIdStr();//指定部门
					String userIds = model.getUserIdStr();//指定人员
					hql = " and (";
					if(roleIds.endsWith(",")){
						roleIds = roleIds.substring(0, roleIds.length() - 1);
					}
					if(deptIds.endsWith(",")){
						deptIds = deptIds.substring(0, deptIds.length() - 1);
					}
					if(userIds.endsWith(",")){
						userIds = userIds.substring(0, userIds.length() - 1);
					}
					if(model.getDeptPriv().equals("0")){//本部门
						hql = hql + "  dept.uuid = " + person.getDept().getUuid();
					}else if(model.getDeptPriv().equals("1")){//全体部门
						hql = hql + " 1=1 ";
					}else if(model.getDeptPriv().equals("2")){//指定部门
						if(deptIds.equals("")){//
							deptIds = "0";
						}
						hql = hql + "  dept.uuid in (" + deptIds + ")";
					}else if(model.getDeptPriv().equals("3")){//指定人员
						if(userIds.equals("")){
							userIds = "0";
						}
						hql = hql + "  uuid in (" + userIds + ")";
					}
					
					if(model.getRolePriv().equals("0")){//低角色
						hql = hql + " and userRole.roleNo > " + person.getUserRole().getRoleNo();
					}else if(model.getRolePriv().equals("1")){//同角色或者低角色
						hql = hql + " and userRole.roleNo >= " + person.getUserRole().getRoleNo();
					}else if(model.getRolePriv().equals("2")){//所有角色
						
					}else if(model.getRolePriv().equals("3")){//指定角色
						if(roleIds.equals("")){
							roleIds = "0";
						}
						hql = hql + " and userRole.uuid in (" + roleIds + ")";
					}
					hql = hql + ") ";
				}
			//}
			return hql;
		}

		
	
		
		/**
		 * 获取登录人员的管理范围hql  考虑按模块设置 ---之前版本
		 * @param person
		 * @param model
		 * 如果返回 0 代表没有权限,可以不用执行hql语句，直接返回空lisit人员即可
		 */
		public String  getPostPrivHqlStrByPersonPostPrivAndModulePriv(TeePerson person , TeeModulePrivModel model){
			String hql = "0";
//			if(model == null || (model != null &&  model.getSid() <= 0) ){//如果没有按模块设置
//				String postDeptIds = personService.getLoginPersonPostDept(person);//获取系统当前登录人管理范围部门Id字符串
//				boolean  isSuperAdmin = personService.checkIsSuperAdmin(person, "");
//				if(isSuperAdmin){//超级管理员
//					return "";
//				}else{
//					if(postDeptIds.equals("0")) {// 全体范围，
//						return "";
//					}else if(postDeptIds.equals("")){//没有权限
//						postDeptIds = "0";
//						return "0";
//					}			
//					hql = hql + " and dept.uuid in (" + postDeptIds + ")";
//				}
	
//			}else{
				
				//部门和角色交集(and）
//				if(!model.getDeptPriv().equals("1") &&   !model.getRolePriv().equals("2")){//部门权限不是全体部门且不是所有角色
//					String roleIds = model.getRoleIdStr();//指定角色
//					String deptIds = model.getDeptIdStr();//指定部门
//					String userIds = model.getUserIdStr();//指定人员
//					hql = " and (";
//					if(roleIds.endsWith(",")){
//						roleIds = roleIds.substring(0, roleIds.length() - 1);
//					}
//					if(deptIds.endsWith(",")){
//						deptIds = deptIds.substring(0, deptIds.length() - 1);
//					}
//					if(userIds.endsWith(",")){
//						userIds = userIds.substring(0, userIds.length() - 1);
//					}
//					if(model.getDeptPriv().equals("0")){//本部门
//						hql = hql + "  dept.uuid = " + person.getDept().getUuid();
//					}else if(model.getDeptPriv().equals("1")){//全体部门
//						hql = hql + " 1=1 ";
//					}else if(model.getDeptPriv().equals("2")){//指定部门
//						if(deptIds.equals("")){//
//							deptIds = "0";
//						}
//						hql = hql + "  dept.uuid in (" + deptIds + ")";
//					}else if(model.getDeptPriv().equals("3")){//指定人员
//						if(userIds.equals("")){
//							userIds = "0";
//						}
//						hql = hql + "  uuid in (" + userIds + ")";
//					}
//					
//					if(model.getRolePriv().equals("0")){//低角色
//						hql = hql + " and userRole.roleNo > " + person.getUserRole().getRoleNo();
//					}else if(model.getRolePriv().equals("1")){//同角色或者低角色
//						hql = hql + " and userRole.roleNo >= " + person.getUserRole().getRoleNo();
//					}else if(model.getRolePriv().equals("2")){//所有角色
//						
//					}else if(model.getRolePriv().equals("3")){//指定角色
//						if(roleIds.equals("")){
//							roleIds = "0";
//						}
//						hql = hql + " and userRole.uuid in (" + roleIds + ")";
//					}
//					hql = hql + ") ";
//				}
//			}
			return hql;
		}


		/**
		 * 更新人员快捷展示桌面
		 * @param 
		 */
		public int updatePersonPortletByUuids(int personId , TeePersonModel model) {
			String sql = "update TeePerson	set portletCol = ? ,portletLeftWidth = ?, portletCenterWidth = ?" +
					",portletRightWidth = ?  where uuid = " + personId ;
			Object[] values = {model.getPortletCol(),model.getPortletLeftWidth() , model.getPortletCenterWidth() , model.getPortletRightWidth()};
			int count = deleteOrUpdateByQuery(sql, values);
			return count;
		}
		
		
		/**
		 * 更新人员昵称和头像
		 * @param 
		 */
		public int updateAvatar(int personId , TeePersonModel model) {
			String sql = "update TeePerson	set avatar = ? ,nickName = ? where uuid = " + personId ;
			Object[] values = {model.getAvatar(),model.getNickName() };
			int count = deleteOrUpdateByQuery(sql, values);
			return count;
		}
		
		
		/**
		 * 获取部门为空的人员
		 * @author syl
		 * @date 2013-11-18
		 * @return
		 */
		public List<TeePerson> selectNullDeptPerson() {
		    String hql  = " from TeePerson where dept is null and deleteStatus<>'1' ";
			List<TeePerson> list = (List<TeePerson>) executeQuery(hql, null);
			return list;
		}
		

		/**
		 * 获取部门为空的人员 且为在线人员
		 * @author syl
		 * @date 2013-11-18
		 * @return
		 */
		public List<TeePerson> selectNullDeptNoOnlinePerson() {
		    String hql  = " from TeePerson where dept is null and  userOnline.userStatus = 0  ";
			List<TeePerson> list = (List<TeePerson>) executeQuery(hql, null);
			return list;
		}

		/**
		 * 更新在线时长
		 * @author syl
		 * @date 2013-11-24
		 * @param uuid
		 * @param online
		 */
		public void updateOnline(int uuid , int online) {
			Object[] values = {online , uuid};
		    String hql  = " update TeePerson set online = ? where uuid = ?" ;
			deleteOrUpdateByQuery(hql, values);
		}
		
		/**
		 * 查询在线人数
		 * @author syl
		 * @date 2013-12-2
		 * @param uuid
		 * @param online
		 */
		public long queryOnlineCount() {
			Object[] values = null;
		    String sql = "select count(1) from (select distinct(user_id) from user_online) t" ;
		    Session session = sessionFactory.getCurrentSession();
		    SQLQuery query = session.createSQLQuery(sql);
		    long count = TeeStringUtil.getLong(query.uniqueResult(), 0);
			return count;
		}
		
		
		
		/**
		 * By部门查询人员,且带条件查询   和权限管理
		 * @param 部门UUID
		 * @param userFilter : 人员id字符串
		 * @param privNoFlag : 是否需要处理权限控制   1-处理  其它不处理
		 */
		public List<TeePerson> selectPersonByDeptIdAndUserFilterToWorkFlow(int deptId , String userFilter,TeePerson person ,String  privNoFlag) {
		    @SuppressWarnings("unchecked")
		    String privNoFlagTemp = TeeStringUtil.getString(privNoFlag, "0");
		    Object[] values = {};
		    String hql  = " from TeePerson where  deleteStatus <> '1' "; 
			
		    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
				return new ArrayList();
			}
		    
			if(!userFilter.equals("0")){//需要处理
				if(userFilter.endsWith(",")){
					userFilter = userFilter.substring(0, userFilter.length()-1);
				}
				hql = hql +  " and "+TeeDbUtility.IN("uuid", userFilter);
			}
			hql = hql + "order by userNo";
		    List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
			return list;
		}
		
		
		/**
		 * 查询人在线人员,且带条件查询   和权限管理   
		 * @param 部门UUID
		 * @param userFilter : 人员id字符串
		 * * @param privNoFlag : 是否需要处理权限控制   1-处理  其它不处理
		 */
		public List<TeePerson> selectOnlinePersonUserFilterToWorkFlow(int deptId , String userFilter,TeePerson person ,String  privNoFlag) {
		    @SuppressWarnings("unchecked")
		    String privNoFlagTemp = TeeStringUtil.getString(privNoFlag, "0");
		    Object[] values = {};
		    String hql  = " from TeePerson p where  p.deleteStatus <> '1' and exists (select 1 from TeeUserOnline uo where uo.userId=p.uuid) " ;
			
		    if(TeeUtility.isNullorEmpty(userFilter)){//为空则不查询
				return new ArrayList();
			}
			if(!userFilter.equals("0")){//需要处理
				if(userFilter.endsWith(",")){
					userFilter = userFilter.substring(0, userFilter.length()-1);
				}
				hql = hql +  " and "+TeeDbUtility.IN("p.uuid", userFilter);
			}
			hql = hql + "order by p.userRole.roleNo , p.userNo";
		    List<TeePerson> list = (List<TeePerson>) executeQuery(hql, values);
			return list;
		}
		
		
		
		/**
		 * 根据 人员 、部门、角色 Ids 查询所有人员  
		 * @author syl
		 * @date 2014-3-11
		 * @param uuids
		 * @param deptIds
		 * @param roleIds
		 * @return
		 */
		public List<TeePerson> getPersonsByUuidsOrDeptIdsOrRoleIds(String uuids , String deptIds , String roleIds){
			List<TeePerson> list = new ArrayList<TeePerson>();
			String hql = "select p from TeePerson p  where   p.deleteStatus <> '1' and ( ";
			boolean exists = false;
			if(TeeUtility.isNullorEmpty(roleIds) &&
				TeeUtility.isNullorEmpty(deptIds) &&
				TeeUtility.isNullorEmpty(uuids)){
				return list;
			}
			
			if(!TeeUtility.isNullorEmpty(uuids)){
				if(uuids.endsWith(",")){
					uuids = uuids.substring(0, uuids.length() - 1);
				}
				hql =  hql + " p.uuid  in (" + uuids + ")";	
				exists = true;
			}
			if(!TeeUtility.isNullorEmpty(roleIds)){
				if(roleIds.endsWith(",")){
					roleIds = roleIds.substring(0, roleIds.length() - 1);
				}
				if(exists){
					hql = hql + " or";
				}
				//hql =  hql + " p.userRole.uuid  in (" + roleIds + ")  or exists (select 1 from p.userRoleOther other where other.uuid in ("+roleIds+") )";	
				hql =  hql + "  p.userRole.uuid  in (" + roleIds + ")";	
				exists = true;
			}
			if(!TeeUtility.isNullorEmpty(deptIds)){
				if(deptIds.endsWith(",")){
					deptIds = deptIds.substring(0, deptIds.length() - 1);
				}
				if(exists){
					hql = hql + " or";
				}
				//hql =  hql + " p.dept.uuid  in (" + deptIds + ")  or exists (select 1 from p.deptIdOther deptOther where deptOther.uuid in ("+deptIds+")) ";
				hql =  hql + " p.dept.uuid  in (" + deptIds + ")";	
			}
			
			hql = hql + " ) order by p.dept.uuid ";
			
			
			list = (List<TeePerson>) executeQuery(hql, null);
			return list;
		}
		
		
		/**
		 * 更新人员的桌面菜单设置   --- 第二套风格
		 * @author syl
		 * @date 2014-3-20
		 * @param person
		 * @return
		 */
		public int updatePersonMenuParamSet(TeePerson person){
			String sql = "update  TeePerson	set menuParamSet = ? where uuid = " + person.getUuid();
			Object[] values = {person.getMenuParamSet()};
			int count = deleteOrUpdateByQuery(sql, values);
			return count;
		}
		
		/**
		 * 获取已删除的人员  和 外部人员（部门为空）
		 * @author syl
		 * @date 2014-6-1
		 * @return
		 */
		public List<TeePerson> queryDeletePerson(){
			String hql = "from TeePerson p where p.deleteStatus = '1' or p.dept is null";
			List<TeePerson> list = (List<TeePerson>) executeQuery(hql, null);
			return list;
		}
		

		/**
		 * 删除 by userId
		 * @param userId
		 * @return
		 */
		public int deletePersonByUserId(String userId){
			String hql = "delete from TeePerson where userId = ?";
			Object[] values = {userId};
			return deleteOrUpdateByQuery(hql, values);
		}

		/**
		 * 还原离职人员
		 * @param uuid
		 */
		public void reductionPerson(int uuid) {
			String hql="update TeePerson p set p.deleteStatus=0 where p.uuid=?";
			Object[] values = {uuid};
			deleteOrUpdateByQuery(hql, values);
			
		}
        
		/**
		 * 查询统计当前人员是否在线
		 * @param uuid
		 * @return
		 */
		public int selectOnlineByUserId(int uuid) {
			//Object[] values = {uuid};
			String sql = "select count(*) from (select * from user_online where user_id  = '" +uuid+"')  t ";
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery(sql);
			int count = TeeStringUtil.getInteger(query.uniqueResult(), 0);
			return count;
		}
	
		/**
		 * 获取指定人员的直属下级
		 * @param userId
		 * @return
		 */
		public List<TeePerson> getUnderlines(int userId){
			String hql = "from TeePerson p where p.deleteStatus != '1' and p.leader.uuid="+userId;
			List<TeePerson> list = (List<TeePerson>) executeQuery(hql, null);
			return list;
		}

		
		/**
		 * 根据可见范围的部门  获取当前登录人可见部门下的 所有人员
		 * @param listDept
		 * @param loginUser
		 * @return
		 */
		public List<TeePerson> getViewPrivUserListByLoginUser(List<TeeDepartment> listDept,
				TeePerson loginUser) {
			List<TeePerson> userList=null;
			String deptIds="";
			for (TeeDepartment teeDepartment : listDept) {
				deptIds+=teeDepartment.getUuid()+",";
			}
			if(deptIds.endsWith(",")){
				deptIds=deptIds.substring(0,deptIds.length()-1);
			}
			
			String hql = "from TeePerson p where p.deleteStatus != '1' and p.dept.uuid in ("+deptIds+")  ";
			//System.out.println("语句："+hql);
			userList= (List<TeePerson>) executeQuery(hql, new Object[]{});
			
			return userList;
		}

		
		/**
		 * 获取当前登录人  可见部门下的所有人员數量
		 * @param loginUser
		 * @param listDept
		 * @return
		 */
		public Long getViewPrivAllUserNoDeleteCount(TeePerson loginUser,
				List<TeeDepartment> listDept) {
			@SuppressWarnings("unchecked")
			
			String deptIds="";
			for (TeeDepartment teeDepartment : listDept) {
				deptIds+=teeDepartment.getUuid()+",";
			}
			if(deptIds.endsWith(",")){
				deptIds=deptIds.substring(0,deptIds.length()-1);
			}	
		    String hql  = "select count(*) from TeePerson  where  deleteStatus <> '1' and  dept.uuid in ("+deptIds+")   order by dept.uuid asc";
			List<TeePerson> list = new ArrayList<TeePerson>();
			List param =new ArrayList();
			long count = countByList(hql, param)==null?0L:countByList(hql, param);
			return count;
		}

		
		/**
		 * 获取当前登录人  可见部门下的所有人员
		 * @param firstIndex
		 * @param rows
		 * @param dm
		 * @param loginUser
		 * @param listDept
		 * @return
		 */
		public List<TeePerson> getViewPrivAllUserNoDeletePage(int firstIndex,
				int rows, TeeDataGridModel dm, TeePerson loginUser,
				List<TeeDepartment> listDept) {
			@SuppressWarnings("unchecked")
			
			String deptIds="";
			for (TeeDepartment teeDepartment : listDept) {
				deptIds+=teeDepartment.getUuid()+",";
			}
			if(deptIds.endsWith(",")){
				deptIds=deptIds.substring(0,deptIds.length()-1);
			}	
			
		    String hql  = "select uuid from TeePerson  where  deleteStatus <> '1'  and dept.uuid in ("+deptIds+")  order by dept.uuid asc";
			List<TeePerson> list = new ArrayList<TeePerson>();
			List param =new ArrayList();
			List uuidList = pageFindByList(hql, firstIndex, rows, param);
			TeePerson p = null;
			for(Object uuid:uuidList){
				p = load(TeeStringUtil.getInteger(uuid, 0));
				if(p!=null){
					list.add(p);
				}
			}
			return list;
		}
}