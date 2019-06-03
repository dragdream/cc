package com.tianee.oa.core.workflow.flowmanage.dao;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;

@Repository
public class TeeFlowTypeDao extends TeeBaseDao<TeeFlowType>{
	
	/**
	 * 查询挂载当前表单的流程类型集合
	 * @param form
	 * @return
	 */
	public List<TeeFlowType> getFlowTypesByForm(TeeForm form){
		Session session = this.getSession();
		Criteria c = session.createCriteria(TeeFlowType.class,"flowType");
		c.add(Restrictions.eq("flowType.form.sid", form.getFormGroup()));
		return c.list();
	}
	
	public List<TeeFlowType> findByFlowSort(TeeFlowSort flowSort){
		if(flowSort==null){
			throw new RuntimeException("传入的流程分类为空");
		}
		return findByFlowSort(flowSort.getSid());
	}
	
	public List<TeeFlowType> findByFlowSort(int flowSortId){
		if(flowSortId>0){
			return this.find("from TeeFlowType flowType where flowType.flowSort.sid=? order by flowType.orderNo asc", new Object[]{flowSortId});
		}else{
			return this.find("from TeeFlowType flowType where flowType.flowSort is null order by flowType.orderNo asc", null);
		}
	}
	
	public List<TeeFlowType> findByFlowSort(TeePerson loginUser,boolean isAdmin,int flowSortId,int firstResult,int pageSize){
		if(isAdmin){//系统管理员
			if(flowSortId>0){
				return this.pageFind("from TeeFlowType flowType where flowType.flowSort.sid=? order by flowType.orderNo asc",firstResult, pageSize,new Object[]{flowSortId});
			}else{
				return this.pageFind("from TeeFlowType flowType where flowType.flowSort is null order by flowType.orderNo asc",firstResult, pageSize,new Object[]{});
			}
			
		}else{
			if(flowSortId>0){
				return this.pageFind("from TeeFlowType flowType where flowType.flowSort.sid=? and (flowType.dept.uuid=? or flowType.dept is null ) order by flowType.orderNo asc",firstResult, pageSize,new Object[]{flowSortId,loginUser.getDept().getUuid()});
			}else{
				return this.pageFind("from TeeFlowType flowType where flowType.flowSort is null and (flowType.dept.uuid=? or flowType.dept is null ) order by flowType.orderNo asc",firstResult, pageSize,new Object[]{loginUser.getDept().getUuid()});
			}
			
		}
		
	}
	
	public long findCountByFlowSort(int flowSortId,boolean isAdmin,TeePerson loginUser){
		if(isAdmin){//系统管理员
			if(flowSortId>0){
				return this.count("select count(*) from TeeFlowType flowType where flowType.flowSort.sid=?", new Object[]{flowSortId});
			}else{
				return this.count("select count(*) from TeeFlowType flowType where flowType.flowSort is null", new Object[]{});
			}
			
		}else{
			if(flowSortId>0){
				return this.count("select count(*) from TeeFlowType flowType where flowType.flowSort.sid=? and (flowType.dept.uuid=? or flowType.dept is null)", new Object[]{flowSortId,loginUser.getDept().getUuid()});
			}else{
				return this.count("select count(*) from TeeFlowType flowType where flowType.flowSort is null and (flowType.dept.uuid=? or flowType.dept is null)", new Object[]{loginUser.getDept().getUuid()});
			}
		}
		
		
	}
	
	public List<TeeFlowType> list(){
		return super.find("from TeeFlowType ft order by ft.orderNo asc", null);
	}
	
	public int getTheTotleOfFlowRunByFlowId(int flowId){
		Session session = getSession();
		Query query = session.createQuery("select count(*) from TeeFlowRun fr where fr.flowType.sid=?");
		query.setInteger(0, flowId);
		Long count = (Long) query.uniqueResult();
		return TeeStringUtil.getInteger(count, 0);
	}
	
	/**
	 * 判断自由流程是否有经办权限
	 * @param flowTypeId  -- 流程Id
	 * @param pId  -- 人员Id
	 * @param deptId -- 部门Id
	 * @param roleId  -- 角色Id
	 * @return
	 */
	public List<TeeFlowType> validatorFreeFlowTypePriv(int flowTypeId, int pId , int deptId , int roleId){
		Object[] values = {flowTypeId , pId , deptId , roleId};
		String hql = "select flowType from TeeFlowType flowType left join flowType.prcsUsers users left join  flowType.prcsDepts depts left join  flowType.prcsRoles roles  where flowType.sid = ? and ( users.uuid = ? or depts.uuid = ? or roles.uuid = ?) ";
		List<TeeFlowType> list = executeQuery(hql, values);
		return list ;
	}
	
	/**
	 * 
	 * @param person
	 * @return
	 */
	public List<TeeFlowType> getCreatablePrivFlowListByPerson(TeePerson person) {
		List<TeeFlowType> fts = new ArrayList<TeeFlowType>();
		
		//获取所有流程
		List<TeeFlowType> flowTypes = find("from TeeFlowType ft where " +
				//自由流程经办权限
				"" +
					"(" +
						"exists (select 1 from ft.prcsUsers ft_prcsUsers where ft_prcsUsers=?) or " +//自由流程经办人员
						"exists (select 1 from ft.prcsDepts ft_prcsDepts where ft_prcsDepts=? or exists (select 1 from TeePerson p where p=? and exists (select 1 from p.deptIdOther deptIdOther where deptIdOther=ft_prcsDepts))) or " + //自由流程经办部门
						"exists (select 1 from ft.prcsRoles ft_prcsRoles where ft_prcsRoles=? or exists (select 1 from TeePerson p where p=? and exists (select 1 from p.userRoleOther userRoleOther where userRoleOther=ft_prcsRoles )))"+//自由流程经办角色
					")"+
				" order by ft.orderNo asc", new Object[]{person,person.getDept(),person,person.getUserRole(),person});
		
		//获取所有流程
//				List<TeeFlowType> flowTypes = find("from TeeFlowType ft where " +
//						//自由流程经办权限
//						"(ft.type=2 and " +
//							"(" +
//								"exists (select 1 from ft.prcsUsers ft_prcsUsers where ft_prcsUsers=?) or " +//自由流程经办人员
//								"exists (select 1 from ft.prcsDepts ft_prcsDepts where ft_prcsDepts=? or exists (select 1 from TeePerson p where p=? and exists (select 1 from p.deptIdOther deptIdOther where deptIdOther=ft_prcsDepts))) or " + //自由流程经办部门
//								"exists (select 1 from ft.prcsRoles ft_prcsRoles where ft_prcsRoles=? or exists (select 1 from TeePerson p where p=? and exists (select 1 from p.userRoleOther userRoleOther where userRoleOther=ft_prcsRoles )))"+//自由流程经办角色
//							")"+
//						") or " +
//						"(ft.type=1 and " +
//						//固定流程经办权限（获取其第一步骤的经办权限）
//							"exists " +
//							"(" +
//							"select 1 from ft.processList processList where processList.prcsType=1 and " +
//								"(" +
//									"exists (select 1 from processList.prcsUser prcsUser where prcsUser=?) or " +//固定流程第一步经办人员
//									"exists (select 1 from processList.prcsDept prcsDept where prcsDept=? or exists (select 1 from TeePerson p where p=? and exists (select 1 from p.deptIdOther deptIdOther where deptIdOther=prcsDept))) or " +//固定流程第一步骤经办部门
//									"exists (select 1 from processList.prcsRole prcsRole where prcsRole=? or exists (select 1 from TeePerson p where p=? and exists (select 1 from p.userRoleOther userRoleOther where userRoleOther=prcsRole )))" +//固定流程第一步骤经办角色
//								")"+
//							")" +
//						") order by ft.orderNo asc", new Object[]{person,person.getDept(),person,person.getUserRole(),person,person,person.getDept(),person,person.getUserRole(),person});
		
		return flowTypes;
	}

	/**
	 * 获取当前用户可以编辑的流程
	 * @param id
	 * @param loginUser
	 * @param isAdmin
	 * @return
	 */
	public List<TeeFlowType> findByFlowSort1(int id, TeePerson loginUser,
			boolean isAdmin) {
		if(isAdmin){//系统管理员
			if(id>0){
				return this.find("from TeeFlowType flowType where flowType.flowSort.sid=? order by flowType.orderNo asc", new Object[]{id});
			}else{
				return this.find("from TeeFlowType flowType where flowType.flowSort is null order by flowType.orderNo asc", null);
			}
			
		}else{
			if(id>0){
				return this.find("from TeeFlowType flowType where flowType.flowSort.sid=? and (flowType.dept.uuid=? or flowType.dept is null) order by flowType.orderNo asc", new Object[]{id,loginUser.getDept().getUuid()});
			}else{
				return this.find("from TeeFlowType flowType where flowType.flowSort is null  and (flowType.dept.uuid=? or flowType.dept is null)  order by flowType.orderNo asc", new Object[]{loginUser.getDept().getUuid()});
			}
		}
		
	}
	
}
