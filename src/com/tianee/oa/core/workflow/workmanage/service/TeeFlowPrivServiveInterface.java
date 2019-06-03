package com.tianee.oa.core.workflow.workmanage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPriv;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrivModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowPrivServiveInterface {

	public abstract TeeFlowPriv addOrUpdatePriv(TeeFlowPrivModel model);

	/**
	 * 通用列表
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			HttpServletRequest request);

	/**
	 * 查询 byid
	 * 
	 * @param TeeDepartment
	 */
	public abstract TeeFlowPrivModel selectPrivById(TeeFlowPrivModel model);

	/**
	 * 删除多个 
	 * @param ids
	 * @return
	 */
	public abstract int delByIds(String ids);

	/**
	 * 工作监控--- 获取有监控权限的流程树
	 * @param response
	 * @return
	 */
	public abstract List<TeeZTreeModel> getMonitorFlowTypeTree(
			HttpServletRequest request);

	/**
	 * 查询通用列表   监控权限列表
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getMonitorFlowList(
			TeeDataGridModel dm, HttpServletRequest request);

	/**
	 * 获取一个流程的范围范围部门Id字符串串 ---其中部门串可优化，返回ID对象List
	 * @param privList
	 * @param dept
	 * @return
	 */
	public abstract String getPrivDeptIds(List<TeeFlowPriv> privList,
			TeePerson loginUser);

	/**
	 * 获取所有管理权限类型，管理   、监控、查询、编辑、点评
	 * @param privList 权限规则
	 * @param dept 登录人部门
	 * @param TeePerson  登录人
	 * @return
	 */
	public abstract String getAllPrivType(List<TeeFlowPriv> privList,
			TeeDepartment dept, TeePerson TeePerson);

	/**
	 * 获取监控权限类型，管理   、监控
	 * @param privList 权限规则
	 * @param dept 登录人部门
	 * @param TeePerson  登录人
	 * @return
	 */
	public abstract String getMonitorPrivType(List<TeeFlowPriv> privList,
			TeeDepartment dept, TeePerson TeePerson);

	/**
	 * 工作查询  by flowTypeId  单个流程
	 * @param flowTypeId 工作流程类型
	 * @param person 
	 * @param queryType  查询类型  1-管理  2-查询
	 * @return {flowTypeId:1 ,postDeptIds:'1,2,3'} 
	 */
	public abstract String getQueryPostDeptsByFlowType(int flowTypeId,
			TeePerson person, String queryType);

	/**
	 * 工作查询  获取全部流程权限
	 * @param person 
	 * @param queryType  查询类型  1-管理  2-监控 3-查询 4-编辑 ；5-评论；6-公文分发反馈；7-公文传阅反馈
	 * @return  [{flowTypeId:1 ,postDeptIds:'1,2,3'} , flowTypeId:2 ,postDeptIds:'1,3'}]
	 */
	public abstract List getQueryPostDeptsByAllFlow(TeePerson person,
			String queryType);


	public abstract void setPresonDao(TeePersonDao presonDao);

	public abstract void setFlowTypeServ(
			TeeFlowTypeServiceInterface flowTypeServ);

	public abstract void setDeptDao(TeeDeptDao deptDao);

	public abstract void setRoleDao(TeeUserRoleDao roleDao);

	public abstract void setFlowServContext(
			TeeWorkFlowServiceContextInterface flowServContext);

	/**
	 * 获取有监控权限的流程类型的ids
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeJson getHasMonitorPrivFlowTypeIds(
			HttpServletRequest request);

}