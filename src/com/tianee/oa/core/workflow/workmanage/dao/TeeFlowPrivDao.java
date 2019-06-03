package com.tianee.oa.core.workflow.workmanage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPriv;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("flowPriv")
public class TeeFlowPrivDao extends TeeBaseDao<TeeFlowPriv> {
	/**
	 * 流程权限
	 * 
	 * @param TeeFlowPriv
	 */
	public void addFlowPriv(TeeFlowPriv flowPriv) {
		save(flowPriv);
	}

	/**
	 * 更新流程权限
	 * 
	 * @param TeePerson
	 */
	public void updateFlowPriv(TeeFlowPriv flowPriv) {
		update(flowPriv);
	}

	/**
	 * 查询 byid
	
	 * 
	 * @param TeeDepartment
	 */
	public TeeFlowPriv selectPrivById(int uuid) {
		TeeFlowPriv TeeDept = (TeeFlowPriv) get(uuid);
		return TeeDept;
	}
	
	
	/*
	 * 根据hql 查询数量
	 */
	 public Long countBytId(int flowTypeId) {
		 String hql = " from TeeFlowPriv where  flowType.sid = " + flowTypeId ;
		 String totalHql = " select count(*) " + hql;
		 return count(totalHql,null);
	}
	 
		/*
		 * 根据hql 查询数量
		 */
	public  List<TeeFlowPriv> getFlowPrivPageFind(int firstResult,int pageSize,TeeDataGridModel dm,int flowTypeId) { 
		Object[] param = {flowTypeId};
		String hql = " from TeeFlowPriv where  flowType.sid = ?";// order by " + dm.getSort() + " " + dm.getOrder(); 
		return pageFind(hql, firstResult, pageSize, param);
	 }
	
	/**
	 * 删除多个
	 * @param ids
	 * @return
	 */
	public int delByIds(String ids){
		if(ids.endsWith(",")){
			ids = ids.substring(ids.length() - 1);
		}
		String hql = "delete from TeeFlowPriv where sid in (" + ids + ")";
		return deleteOrUpdateByQuery(hql, null);
	}
	
	

	/**
	 * 查询多个  by ids
	 * @param ids
	 * @return
	 */
	public List<TeeFlowPriv> getByIds(String ids){
		if(ids.endsWith(",")){
			ids = ids.substring(ids.length() - 1);
		}
		String hql = " from TeeFlowPriv where sid in (" + ids + ")";
		return executeQuery(hql, null);
	}
	
	
	/**
	 * 根据人员id  部门id  角色Id  获取工作监控管理权限
	 * @param pid
	 * @param roleId
	 * @param deptId
	 * @param queryType 查询类型 1-管理（包括监控、查询）  2-监控（包括查询） 3-查询; 4-编辑 ；5-评论；6-公文分发反馈；7-公文传阅反馈
	 * @return
	 */
	public  List<TeeFlowPriv> getFlowPriv(int pid,int roleId,int deptId,String queryType) { 
		if(TeeUtility.isNullorEmpty(queryType)){
			queryType = "0";
		}
		Object[] param = {pid,deptId,roleId,Integer.parseInt(queryType)};
		String hql = "from TeeFlowType ft where exists (select 1 from TeeFlowPriv priv where priv.flowType=ft and (" +
				" exists (select 1 from priv.privUsers person where person.uuid=?) " +
				" or exists (select 1 from priv.privDepts dept where dept.uuid=?) " +
				" or exists (select 1 from priv.privRoles role where role.uuid=?) " ;
		hql+=") and priv.privType = ? )";
				
		/*
		if(queryType.equals("1")){
					hql = hql + " and priv.privType = 1 "; 
				}else if(queryType.equals("2")){
					hql = hql + " and (priv.privType = 2 ) "; 
				}else if(queryType.equals("3")){
					hql = hql + " and  (priv.privType = 3)"; 
				}else if(queryType.equals("4")){
					hql = hql + " and  (priv.privType = 4)"; 
				}
				hql+=")";
			*/
				//" and (priv.privType = 1 or priv.privType = 2) "; 
		List<TeeFlowPriv> list=  executeQuery(hql,  param);
		return  list;
	 }
	
	
	/**
	 * 根据流程Id 查询监控权限记录 -- 管理、监控、查询
	 * @author syl
	 * @date 2013-12-2
	 * @param flowId
	 * @return
	 */
	public  List<TeeFlowPriv> getFlowPrivByFlowTypeId(int flowId) { 
		Object[] param = {flowId};
		String hql = " select priv from TeeFlowPriv priv " +
				" left join priv.flowType ft" +
				" where  ft.sid = ?" +
				" and (priv.privType = 1 or priv.privType = 2 or  priv.privType = 3) "; 
		List<TeeFlowPriv> list=  executeQuery(hql,  param);
		return  list;
	 }
	
	
	
	
	

	/**
	 * 工作查询接口 ---- 我管理权限的范围
	 * @param flowId
	 * @param personId
	 * @return
	 */
	public  List<TeeFlowPriv> getSelectManageFlowPrivByFlowTypeId(int flowId  ,TeePerson person) { 
		Object[] param = {flowId  };
		String hql = " select priv from TeeFlowPriv priv " +
				" left join priv.flowType ft" +
				" where  ft.sid = ?" +
				" (priv.privUsers.uuid = " + person.getUuid() + 
				" or priv.privDepts.uuid = " + person.getDept().getUuid() + 
				" or priv.privRoles.uuid = " + person.getUserRole().getUuid() + ")" +
				" and priv.privType = 1 "; 
		List<TeeFlowPriv> list=  executeQuery(hql,  param);
		return  list;
	 }
	
	
	/**
	 * 工作查询接口 ---- 我监控权限(包括管理类型)的范围
	 * @param flowId
	 * @param personId
	 * @return
	 */
	public  List<TeeFlowPriv> getSelectMonitorFlowPrivByFlowTypeId(int flowId  ,TeePerson person) { 
		Object[] param = {flowId  };
		String hql = " select priv from TeeFlowPriv priv " +
				" left join priv.flowType ft" +
				" where  ft.sid = ?" +
				" (priv.privUsers.uuid = " + person.getUuid() + 
				" or priv.privDepts.uuid = " + person.getDept().getUuid() + 
				" or priv.privRoles.uuid = " + person.getUserRole().getUuid() + ")" +
				" and (priv.privType = 1 or pric.privType = 2) "; 
		List<TeeFlowPriv> list=  executeQuery(hql,  param);
		return  list;
	 }
	
	
	/**
	 * 工作查询接口 ---- 我查询的范围,主要是管理、监控、查询的权限
	 * @param flowId
	 * @param personId
	 * @return
	 */
	public  List<TeeFlowPriv> getSelectQueryFlowPrivByFlowTypeId(int flowId  ,TeePerson person) { 
		Object[] param = {flowId  };
		String hql = " select priv from TeeFlowPriv priv " +
				" left join priv.flowType ft" +
				" where  ft.sid = ?" +
				" (priv.privUsers.uuid = " + person.getUuid() + 
				" or priv.privDepts.uuid = " + person.getDept().getUuid() + 
				" or priv.privRoles.uuid = " + person.getUserRole().getUuid() + ")" +
				" and (priv.privType = 1 or priv.privType = 2 or priv.privType = 3) "; 
		List<TeeFlowPriv> list=  executeQuery(hql,  param);
		return  list;
	 }
	
	/**
	 * 工作查询接口 ---- 我查询的范围,主要是管理、监控、查询的权限
	 * @param flowId
	 * @param personId
	 * @return
	 */
	public  List<TeeFlowPriv> getSelectEditFlowPrivByFlowTypeId(int flowId  ,TeePerson person) { 
		Object[] param = {flowId };
		String hql = " select priv from TeeFlowPriv priv " +
				" where  priv.flowType.sid = ? and " +
				" (exists (select 1 from priv.privUsers privUsers where privUsers.uuid = " + person.getUuid() +")"+ 
				" or exists (select 1 from priv.privDepts privDepts where privDepts.uuid = " + person.getDept().getUuid() +")"+ 
				" or exists (select 1 from priv.privRoles privRoles where privRoles.uuid = " + person.getUserRole().getUuid() + "))" +
				" and (priv.privType = 4) "; 
		List<TeeFlowPriv> list=  executeQuery(hql,  param);
		return  list;
	 }
}
