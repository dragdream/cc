package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPriv;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowPrivDao;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrivModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeWorkFlowInfoModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFlowPrivServive extends TeeBaseService implements TeeFlowPrivServiveInterface {

	@Autowired
	private TeeFlowPrivDao flowPrivDao;

	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeServ;

	@Autowired
	private TeePersonDao presonDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeUserRoleDao roleDao;
	
	@Autowired
	private TeeWorkFlowServiceContextInterface flowServContext;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#addOrUpdatePriv(com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrivModel)
	 */
	@Override
	public TeeFlowPriv addOrUpdatePriv(TeeFlowPrivModel model) {
		TeeFlowPriv flowPriv = new TeeFlowPriv();
		BeanUtils.copyProperties(model, flowPriv);
		TeeFlowType flowType = flowTypeServ.get(model.getFlowTypeId());//流程
		if(flowType == null ){
			return flowPriv;
		}
		flowPriv.setFlowType(flowType);
		
		//授权范围(人员)
		if(!TeeUtility.isNullorEmpty(model.getPrivUsersId())){
			List<TeePerson> personList = presonDao.getPersonByUuids(model.getPrivUsersId()) ;
			Set<TeePerson> set = new HashSet<TeePerson>(personList);
			flowPriv.setPrivUsers(set);
		}
		
		//授权范围(部门)
		if(!TeeUtility.isNullorEmpty(model.getPrivDeptsId())){
			List<TeeDepartment> deptList = deptDao.getDeptListByUuids(model.getPrivDeptsId());
			Set<TeeDepartment> set = new HashSet<TeeDepartment>(deptList);
			flowPriv.setPrivDepts(set);
		}
		
		//授权范围(角色)
		if(!TeeUtility.isNullorEmpty(model.getPrivRolesId())){
			List<TeeUserRole> roleList = roleDao.getPrivListByUuids(model.getPrivRolesId());
			Set<TeeUserRole> set = new HashSet<TeeUserRole>(roleList);
			flowPriv.setPrivRoles(set);
		}
		//管理范围
		if(model.getPrivScope() == 3){//自定义
			//授权范围(部门)
			if(!TeeUtility.isNullorEmpty(model.getDeptPostId())){
				List<TeeDepartment> postDeptList = deptDao.getDeptListByUuids(model.getDeptPostId());
				Set<TeeDepartment> set = new HashSet<TeeDepartment>(postDeptList);
				flowPriv.setDeptPost(set);
			}
		}
		
		if (model.getSid() > 0) {//更新
			TeeFlowPriv flowPrivOld = flowPrivDao.selectPrivById(model.getSid());
			if(flowPrivOld != null ){
				BeanUtils.copyProperties(flowPriv, flowPrivOld);
				flowPrivDao.updateFlowPriv(flowPrivOld);
			}
			
		} else {
			flowPrivDao.addFlowPriv(flowPriv);
		}
		return flowPriv;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String flowTypeId = request.getParameter("flowTypeId");
		j.setTotal(flowPrivDao.countBytId(Integer.parseInt(flowTypeId)));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		Object parm[] = {};
	
		List<TeeFlowPriv> list = flowPrivDao.getFlowPrivPageFind(firstIndex , dm.getRows(),dm , Integer.parseInt(flowTypeId));// 查询
		List<TeeFlowPrivModel> modelList = new ArrayList<TeeFlowPrivModel>();
		if (list != null && list.size() > 0) {
			for (TeeFlowPriv flowPriv : list) {
				TeeFlowPrivModel um = new TeeFlowPrivModel();
				BeanUtils.copyProperties(flowPriv, um);
				if(flowPriv.getFlowType() != null){
					um.setFlowTypeId(flowPriv.getFlowType().getSid());
					um.setFlowTypeName(flowPriv.getFlowType().getFlowName());
				}
				if(flowPriv.getPrivUsers().size() > 0){//权限范围人员
					String privUsersId = "";
					String privUsersName = "";
					Iterator<TeePerson> it = flowPriv.getPrivUsers().iterator();
					while(it.hasNext()){
						TeePerson person = it.next();
						privUsersId = privUsersId  + person.getUuid() + ",";
						privUsersName= privUsersName + person.getUserName() + ",";
					}
					um.setPrivUsersId(privUsersId);
					um.setPrivUsersName(privUsersName);
				}
				
				if(flowPriv.getPrivDepts().size() > 0){//权限范围部门
					String privDeptsId = "";
					String privDeptsName = "";
					Iterator<TeeDepartment> it = flowPriv.getPrivDepts().iterator();
					while(it.hasNext()){
						TeeDepartment dept = it.next();
						privDeptsId = privDeptsId  + dept.getUuid() + ",";
						privDeptsName= privDeptsName + dept.getDeptName() + ",";
					}
					um.setPrivDeptsId(privDeptsId);
					um.setPrivDeptsName(privDeptsName);
				}
				
				
				
				if(flowPriv.getPrivRoles().size() > 0){//权限范围角色
					String privRolesId = "";
					String privRolesName = "";
					Iterator<TeeUserRole> it = flowPriv.getPrivRoles().iterator();
					while(it.hasNext()){
						TeeUserRole role = it.next();
						privRolesId = privRolesId  + role.getUuid() + ",";
						privRolesName= privRolesName + role.getRoleName() + ",";
					}
					um.setPrivRolesId(privRolesId);
					um.setPrivRolesName(privRolesName);
				}
				
				
				if(flowPriv.getDeptPost().size() > 0){//管理范围，自定义字符串
					String deptPostId = "";
					String deptPostName = "";
					Iterator<TeeDepartment> it = flowPriv.getDeptPost().iterator();
					while(it.hasNext()){
						TeeDepartment dept = it.next();
						deptPostId = deptPostId  + dept.getUuid() + ",";
						deptPostName= deptPostName + dept.getDeptName() + ",";
					}
					um.setDeptPostId(deptPostId);
					um.setDeptPostName(deptPostName);
				}
	
				modelList.add(um);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#selectPrivById(com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrivModel)
	 */
	@Override
	public TeeFlowPrivModel selectPrivById(TeeFlowPrivModel model) {
		TeeFlowPriv flowPriv = flowPrivDao.selectPrivById(model.getSid());
		if(flowPriv != null ){
			if(flowPriv.getPrivUsers().size() > 0){//权限范围人员
				String privUsersId = "";
				String privUsersName = "";
				Iterator<TeePerson> it = flowPriv.getPrivUsers().iterator();
				while(it.hasNext()){
					TeePerson person = it.next();
					privUsersId = privUsersId  + person.getUuid() + ",";
					privUsersName= privUsersName + person.getUserName() + ",";
				}
				model.setPrivUsersId(privUsersId);
				model.setPrivUsersName(privUsersName);
			}
			
			if(flowPriv.getPrivDepts().size() > 0){//权限范围部门
				String privDeptsId = "";
				String privDeptsName = "";
				Iterator<TeeDepartment> it = flowPriv.getPrivDepts().iterator();
				while(it.hasNext()){
					TeeDepartment dept = it.next();
					privDeptsId = privDeptsId  + dept.getUuid() + ",";
					privDeptsName= privDeptsName + dept.getDeptName() + ",";
				}
				model.setPrivDeptsId(privDeptsId);
				model.setPrivDeptsName(privDeptsName);
			}
			
			
			
			if(flowPriv.getPrivRoles().size() > 0){//权限范围角色
				String privRolesId = "";
				String privRolesName = "";
				Iterator<TeeUserRole> it = flowPriv.getPrivRoles().iterator();
				while(it.hasNext()){
					TeeUserRole role = it.next();
					privRolesId = privRolesId  + role.getUuid() + ",";
					privRolesName= privRolesName + role.getRoleName() + ",";
				}
				model.setPrivRolesId(privRolesId);
				model.setPrivRolesName(privRolesName);
			}
			
			if(flowPriv.getDeptPost().size() > 0){//管理范围，自定义字符串
				String deptPostId = "";
				String deptPostName = "";
				Iterator<TeeDepartment> it = flowPriv.getDeptPost().iterator();
				while(it.hasNext()){
					TeeDepartment dept = it.next();
					deptPostId = deptPostId  + dept.getUuid() + ",";
					deptPostName= deptPostName + dept.getDeptName() + ",";
				}
				model.setDeptPostId(deptPostId);
				model.setDeptPostName(deptPostName);
			}

			BeanUtils.copyProperties(flowPriv, model);
		}else{
			model = null;
		}
		return model;
		
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#delByIds(java.lang.String)
	 */
	@Override
	public int delByIds(String ids){
		List<TeeFlowPriv> list = flowPrivDao.getByIds(ids);//先查询，设置其他关联表为null
		for (int i = 0; i < list.size(); i++) {
			TeeFlowPriv priv = list.get(i);
			priv.setFlowType(null);
			priv.setDeptPost(null);
			priv.setPrivDepts(null);
			priv.setPrivRoles(null);
			priv.setPrivUsers(null);
		}
		int count  = flowPrivDao.delByIds(ids);
		
		return count;
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getMonitorFlowTypeTree(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<TeeZTreeModel>  getMonitorFlowTypeTree(HttpServletRequest request){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int pid = person.getUuid();
		TeeUserRole role = person.getUserRole();
		TeeDepartment dept = person.getDept();
		List<TeeZTreeModel> zmList = new ArrayList<TeeZTreeModel>();
		List list = flowPrivDao.getFlowPriv(pid, role.getUuid(), dept.getUuid(),"2");
		
		//全部流程
		TeeZTreeModel ztree = new TeeZTreeModel();//全部流程
		ztree.setId("0");
		ztree.setName("全部流程");
		ztree.setOpen(true);
		ztree.setParent(false);
		ztree.setpId("-1");
		ztree.setIconSkin("wfNode2");
		zmList.add(ztree);
		
		List flowSortList = new ArrayList();
		flowSortList.add(0);
		for (int i = 0; i < list.size(); i++) {
			TeeFlowType type = (TeeFlowType)list.get(i);
			TeeFlowSort sort = type.getFlowSort();
			int sortId = sort==null?0:sort.getSid();
			if(!flowSortList.contains(sortId)){
				flowSortList.add(sortId);
				if(sortId==0){
					//默认分类
					ztree = new TeeZTreeModel();
					ztree.setId("flowType0");
					ztree.setName("默认分类");
					ztree.setOpen(false);
					ztree.setParent(true);
					ztree.setDisabled(true);
					ztree.setpId("-1");
					ztree.setIconSkin("wfNode1");
					zmList.add(ztree);
				}
				TeeZTreeModel ztree1 = new TeeZTreeModel();
				ztree1.setId("flowType" + sortId);
				ztree1.setName(sort.getSortName());
				ztree1.setParent(true);
				ztree1.setpId("-1");
				ztree1.setIconSkin("wfNode1");
				ztree1.setOpen(true);
				ztree1.setDisabled(true);
				zmList.add(ztree1);
			}
			TeeZTreeModel ztree2 = new TeeZTreeModel();
			ztree2.setId(type.getSid() + "");
			ztree2.setName(type.getFlowName());
			ztree2.setParent(false);
			ztree2.setpId("flowType" + sortId);
			ztree2.setIconSkin("wfNode2");

			zmList.add(ztree2);
		}
		
		return zmList;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getMonitorFlowList(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getMonitorFlowList(TeeDataGridModel dm,HttpServletRequest request) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		
		int flowTypeId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);//流程Id  0-为全部流程
		String userType = request.getParameter("userType");//处理类型  1-当前主办人 2- 流程发起人
		int personId = TeeStringUtil.getInteger(request.getParameter("personId"), 0);//人员Id，主办人或者流程发起人Id
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);//流水号
		String runName = request.getParameter("runName");//名称/文号
		
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int pid = loginUser.getUuid();
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, pid);
		TeeUserRole role = loginUser.getUserRole();
		TeeDepartment dept = loginUser.getDept();
      
		String deptIds = "";
		int firstResult = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		int pageSize = dm.getRows();
		List runList = new ArrayList();
		List params = new ArrayList();
//		if( flowTypeId==null || "0".equals(flowTypeId)){//全部流程
//			List flowTypeList = flowPrivDao.getFlowPriv(pid, role.getUuid(), dept.getUuid(),"3");//得到全部流程
//			
//			//获取所有流程和权限部门Id字符串
//			List flowArray = new ArrayList(); 
//			for (int i = 0; i < flowTypeList.size(); i++) {
//				TeeFlowType flowType = (TeeFlowType)flowTypeList.get(i);
//				Map map = new HashMap();
//				map.put("flowTypeId", flowType.getSid());
//				String postDeptIds =  getPrivDeptIds(new ArrayList (flowType.getFlowPrivs()), dept);
//				map.put("postDeptIds", postDeptIds);
//				flowArray.add(map);
//			}
//			
//			long totalCount = flowServContext.getFlowRunPrcsService().JobMonitorAllTypeListCount(runName, TeeStringUtil.getInteger(runId, 0), 1, personId, flowArray, dm);
//			j.setTotal(totalCount);// 设置总记录数
//			
//			runList = flowServContext.getFlowRunPrcsService().JobMonitorAllTypeList(runName, TeeStringUtil.getInteger(runId, 0), 1, personId, flowArray, firstIndex,  dm.getRows(), dm);
//				
//			
//		}else{
//			if(TeeUtility.isInteger(flowTypeId)){//单个流程
//				List<TeeFlowPriv>  privList = flowPrivDao.getFlowPrivByFlowTypeId(Integer.parseInt(flowTypeId));
//				deptIds = getPrivDeptIds(privList, dept);
//			}
//			long totalCount = flowServContext.getFlowRunPrcsService().JobMonitorListCount(Integer.parseInt(flowTypeId), runName, TeeStringUtil.getInteger(runId, 0), Integer.parseInt(userType), personId ,deptIds);
//			j.setTotal(totalCount);// 设置总记录数
//			//获取流程实例步骤
//			 runList = flowServContext.getFlowRunPrcsService().JobMonitorList(Integer.parseInt(flowTypeId), runName,
//					 TeeStringUtil.getInteger(runId, 0), Integer.parseInt(userType), personId ,deptIds
//					,firstIndex, dm.getRows() ,dm);
//		}
		
		StringBuffer hql = new StringBuffer("from TeeFlowRunPrcs frp where frp.flag in (1,2) and frp.flowRun.endTime is null and frp.flowRun.delFlag=0 and frp.flowRun.isSave=1 ");
		
		if (flowTypeId != 0) {
			hql.append(" and frp.flowRun.flowType.sid = "+flowTypeId);
		} 
		if(!TeeUtility.isNullorEmpty(runName)){
			hql.append(" and frp.flowRun.runName like ?");
			params.add("%"+runName+"%");
		}
		if (runId != 0) {
			hql.append(" and frp.flowRun.runId  = " + runId);
		}
		
		if(personId!=0){
			if("1".equals(userType)){//当前主办人
				hql.append(" and frp.prcsUser.uuid  = " + personId);
			}else{//流程发起人
				hql.append(" and frp.flowRun.beginPerson.uuid  = " + personId);
			}
		}
		
		//得到管理权限流程
		List managePrivList = getQueryPostDeptsByAllFlow(loginUser, "1");
		//得到监控权限流程
		List monitorPrivList = getQueryPostDeptsByAllFlow(loginUser, "2");
		//得到查询权限流程
		List searchPrivList = getQueryPostDeptsByAllFlow(loginUser, "3");
		//得到编辑权限流程
		List editablePrivList = getQueryPostDeptsByAllFlow(loginUser, "4");
		
		StringBuffer myManage = new StringBuffer();
		Map queryData = null;
		//所有权限汇总
		List allPrivList = new ArrayList();
		for(int i=0;i<managePrivList.size();i++){
			queryData = (Map) managePrivList.get(i);
			allPrivList.add(queryData);
		}
		for(int i=0;i<monitorPrivList.size();i++){
			queryData = (Map) monitorPrivList.get(i);
			allPrivList.add(queryData);
		}
		for(int i=0;i<searchPrivList.size();i++){
			queryData = (Map) searchPrivList.get(i);
			allPrivList.add(queryData);
		}
		for(int i=0;i<editablePrivList.size();i++){
			queryData = (Map) editablePrivList.get(i);
			allPrivList.add(queryData);
		}
		
		 //获取真正有权限的集合
		List  realPrivList = new ArrayList();
		Map m=null;
		if (allPrivList.size() != 0) {
			for (int i = 0; i < allPrivList.size(); i++) {
				m = (Map) allPrivList.get(i);
				if (!String.valueOf(m.get("postDeptIds")).equals("")) {// 有权限
					realPrivList.add(m);
				}
			}
		}
		
		if(realPrivList.size()!=0){//有权限
			for(int i=0;i<realPrivList.size();i++){
				queryData = (Map) realPrivList.get(i);
				if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
					myManage.append("(frp.flowRun.flowType.sid="+queryData.get("flowTypeId"));
					String postDeptIds = String.valueOf(queryData.get("postDeptIds"));
					if(!postDeptIds.equals("0")){//加入限定部门条件
						if(postDeptIds.endsWith(",")){
							postDeptIds =postDeptIds.substring(0,postDeptIds.length()-1);
						}
						myManage.append(" and exists (select 1 from TeeDepartment dept where dept.uuid in ("+postDeptIds+") and frp.prcsUser.dept=dept)");
					}
					myManage.append(")");
					if(i!=realPrivList.size()-1){
						myManage.append(" or ");
					}
				}
			}
		}else{//无任何权限
			myManage.append(" frp.flowRun.flowType.sid=0 ");
		}
		
		
		if(!"".equals(myManage.toString())){
			hql.append(" and ("+myManage.toString()+")");
		}
		
		
		
		runList = simpleDaoSupport.pageFindByList(hql.toString(), firstResult, pageSize, params);
		long total = simpleDaoSupport.count("select count(*) "+hql, params.toArray());
		
		
		List<TeeWorkFlowInfoModel> l = new ArrayList<TeeWorkFlowInfoModel>();
	
		for (int j = 0; j < runList.size(); j++) {
			TeeWorkFlowInfoModel model = new TeeWorkFlowInfoModel();
			TeeFlowRunPrcs flowPrcs = (TeeFlowRunPrcs) runList.get(j);
			// BeanUtils.copyProperties(prcs,mod);
			Calendar crt = flowPrcs.getCreateTime();
			Calendar dt = flowPrcs.getEndTime();
			TeePerson fromUser = flowPrcs.getFromUser();
			TeeFlowProcess fp = flowPrcs.getFlowPrcs();
			TeePerson prcsUser = flowPrcs.getPrcsUser();
//			// 获取监控权限类型 1-管理 2-监控
//			List<TeeFlowPriv> privList = new ArrayList(flowPrcs.getFlowRun()
//					.getFlowType().getFlowPrivs());
//			int monitorPrivType = TeeStringUtil.getInteger(getAllPrivType(privList, dept,prcsUser), 0);
//			model.setFlowMonitorPrivType(monitorPrivType);
			
			boolean hasManagePriv = false;
			boolean hasMonitorPriv = false;
			boolean hasEditPriv = false;
			//查找出当前管理权限
			for(int i=0;i<managePrivList.size();i++){
				queryData = (Map) managePrivList.get(i);
				if(TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0)==TeeStringUtil.getInteger(flowPrcs.getFlowRun().getFlowType().getSid(), 0)){
					if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
						if(!String.valueOf(queryData.get("postDeptIds")).equals("0")){//加入限定部门条件
							if(TeeStringUtil.existString(TeeStringUtil.parseStringArray(queryData.get("postDeptIds")), flowPrcs.getFlowRun().getBeginPerson().getDept().getUuid()+"")){
								hasManagePriv = true;
							}
						}else{//有全部门权限
							hasManagePriv = true;
						}
					}
					break;
				}
			}
			//查找出当前监控权限
			for(int i=0;i<monitorPrivList.size();i++){
				queryData = (Map) monitorPrivList.get(i);
				if(TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0)==TeeStringUtil.getInteger(flowPrcs.getFlowRun().getFlowType().getSid(), 0)){
					if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
						if(!String.valueOf(queryData.get("postDeptIds")).equals("0")){//加入限定部门条件
							if(TeeStringUtil.existString(TeeStringUtil.parseStringArray(queryData.get("postDeptIds")), flowPrcs.getFlowRun().getBeginPerson().getDept().getUuid()+"")){
								hasMonitorPriv = true;
							}
						}else{//有全部门权限
							hasMonitorPriv = true;
						}
					}
					break;
				}
			}
			
			//查找出当前编辑权限
			for(int i=0;i<editablePrivList.size();i++){
				queryData = (Map) editablePrivList.get(i);
				if(TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0)==TeeStringUtil.getInteger(flowPrcs.getFlowRun().getFlowType().getSid(), 0)){
					if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
						if(!String.valueOf(queryData.get("postDeptIds")).equals("0")){//加入限定部门条件
							if(TeeStringUtil.existString(TeeStringUtil.parseStringArray(queryData.get("postDeptIds")), flowPrcs.getFlowRun().getBeginPerson().getDept().getUuid()+"")){
								hasEditPriv = true;
							}
						}else{//有全部门权限
							hasEditPriv = true;
						}
					}
					break;
				}
			}
			
			if(hasManagePriv){//管理
				model.getParams().put("turnNext", "");
				model.getParams().put("delegate", "");
				model.getParams().put("end", "");
				model.getParams().put("del", "");
			}
//			
			if(hasMonitorPriv){//监控
				model.getParams().put("turnNext", "");
				model.getParams().put("delegate", "");
			}
			
			if(hasEditPriv){//编辑
				model.getParams().put("edit", "");
			}
			
			/*//根据主办经办权限进行操作控制，如果是非主办，则去掉转交操作
			if(flowPrcs.getOpFlag()!=1){
				model.getParams().remove("turnNext");
			}*/
			
			String sFlowPrcName = "";
			if (crt != null) {
				model.setCreateTime(TeeDateUtil.format(crt.getTime()));
			}
			if (dt != null) {
				model.setEndTime(TeeDateUtil.format(dt.getTime()));
			}
			model.setDelFlag(flowPrcs.getDelFlag());
			if (fromUser != null) {
				model.setFromUserId(fromUser.getUuid() + "");
			}
			model.setOpFlag(flowPrcs.getOpFlag());
			model.setPrcsFlag(flowPrcs.getFlag());
			model.setPrcsId(flowPrcs.getPrcsId());
			model.setPrcsTimeDesc("总用时："+TeeDateUtil.getPassedTimeDesc(flowPrcs.getBeginTime(), flowPrcs.getEndTime()));
			if (fp != null) {
				sFlowPrcName = fp.getPrcsName();
			}
			model.setPrcsName("第" + flowPrcs.getPrcsId() + "步：" + sFlowPrcName);
			if (prcsUser != null) {
				model.setPrcsUserId(prcsUser.getUuid() + "");
				model.setPrcsUserName(prcsUser.getUserName());
			}
			model.setTimeout(flowPrcs.getTimeout());
			model.setTimeoutFlag(flowPrcs.getTimeoutFlag());
			model.setTopFlag(flowPrcs.getTopFlag());
			model.setFlowName(flowPrcs.getFlowRun().getFlowType().getFlowName());
			model.setRunName(flowPrcs.getFlowRun().getRunName());
			model.setRunId(flowPrcs.getFlowRun().getRunId());
			model.setPrcsUserName(flowPrcs.getPrcsUser().getUserName());
			model.setFlowId(flowPrcs.getFlowRun().getFlowType().getSid());
			
			if(flowPrcs.getFlowRun().getFlowType().getType()==1){
				model.setTypeFlag(1);//固定流程
			}else{
				model.setTypeFlag(2);//自由流程
			}
			model.setFrpSid(flowPrcs.getSid());
			model.setBeginUserName(flowPrcs.getFlowRun().getBeginPerson().getUserName());
			l.add(model);
		}
		json.setTotal(total);
		json.setRows(l);// 设置返回的行
		return json;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getPrivDeptIds(java.util.List, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public String getPrivDeptIds(List<TeeFlowPriv> privList, TeePerson loginUser){
		Set<TeeDepartment> deptList = new HashSet<TeeDepartment>();
		String deptIds = "";
		for (int i = 0; i < privList.size(); i++) {
			TeeFlowPriv priv = privList.get(i);
			int privScope  = priv.getPrivScope();
			int privType = priv.getPrivType();
			if(privScope == 0 && priv.getPrivUsers().contains(loginUser)){//如果为全体范围  并且当前 人员有管理权限
				deptIds = "0";
				return deptIds;
			}else if(privScope == 1 && priv.getPrivUsers().contains(loginUser)){//本部门,包括下级部门
				String level = "";
				if(!TeeUtility.isNullorEmpty(loginUser.getDept().getDeptParentLevel())){
					level = loginUser.getDept().getDeptParentLevel();
				}else{
					level = loginUser.getDept().getGuid();
				}
				List<TeeDepartment> deptChildList = deptDao.getAllChildDeptByLevl(level);
				deptList.addAll(deptChildList);
				deptList.add(loginUser.getDept());
			}else if(privScope == 2 && priv.getPrivUsers().contains(loginUser)){//本部门
				deptList.add(loginUser.getDept());
			}else if(privScope == 3 && priv.getPrivUsers().contains(loginUser)){//自定义部门
				deptList.addAll(priv.getDeptPost());
			}
		}
		Iterator<TeeDepartment> it = deptList.iterator();
		while(it.hasNext()){
			TeeDepartment d = it.next();
			deptIds = deptIds + d.getUuid() + ",";
			
		}
		return deptIds;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getAllPrivType(java.util.List, com.tianee.oa.core.org.bean.TeeDepartment, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public String getAllPrivType(List<TeeFlowPriv> privList, TeeDepartment dept,TeePerson TeePerson){
		
		String mainPostType = "";
		String deptIds = "";
		String postType = "";
		Set privSet = new HashSet();
		for (int i = 0; i < privList.size(); i++) {
			
			Set deptList = new HashSet();
			TeeFlowPriv priv = privList.get(i);
			if( priv.getPrivScope() == 4 ||priv.getPrivScope() == 5){//如果是编辑、点评，跳过
				continue;
			}
			int privScope  = priv.getPrivScope();
			int privType = priv.getPrivType();
			if(privScope == 0){//如果为全体范围
				deptIds = "0";
			}else if(privScope == 1){//本部门,包括下级部门
				String level = "";
				if(!TeeUtility.isNullorEmpty(dept.getDeptParentLevel())){
					level = dept.getDeptParentLevel();
				}else{
					level = dept.getGuid();
				}
				List<TeeDepartment> deptChildList = deptDao.getAllChildDeptByLevl(level);
				for (int j = 0; j < deptChildList.size(); j++) {
					deptList.add(deptChildList.get(j).getUuid());
				}
				//deptList.addAll(deptChildList);
				deptList.add(dept.getUuid());
			}else if(privScope == 2){//本部门
				deptList.add(dept.getUuid());
			}else if(privScope == 3){//自定义部门
				Iterator<TeeDepartment> it = priv.getDeptPost().iterator();
				while(it.hasNext()){
					deptList.add(it.next().getUuid());
				}
			
				//deptList.addAll(priv.getDeptPost());
			}
			
			if(deptIds.equals("0") || deptList.contains(TeePerson.getDept().getUuid())){
				if(privType == 1){//管理
					privSet.add(1);
				}else if(privType == 2){//监控
					privSet.add(2);
				}else if(privType == 3){//查询
					privSet.add(4);
				}else if(privType == 4){//编辑
					privSet.add(8);
				}else if(privType == 5){//点评
					privSet.add(16);
				}

			}
			
		}
		Iterator it = privSet.iterator();
		int type = 0;
		while(it.hasNext()){
			type = type +  (Integer)it.next();
		}
		postType = type +"";
		return postType;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getMonitorPrivType(java.util.List, com.tianee.oa.core.org.bean.TeeDepartment, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public String getMonitorPrivType(List<TeeFlowPriv> privList, TeeDepartment dept,TeePerson TeePerson){
		
		String mainPostType = "";
		String deptIds = "";
		String postType = "";
		for (int i = 0; i < privList.size(); i++) {
			
			Set deptList = new HashSet();
			TeeFlowPriv priv = privList.get(i);
			if( priv.getPrivScope() == 4 ||priv.getPrivScope() == 5){//如果是编辑、点评，跳过
				continue;
			}
			int privScope  = priv.getPrivScope();
			int privType = priv.getPrivType();
			if(privScope == 0){//如果为全体范围
				deptIds = "0";
			}else if(privScope == 1){//本部门,包括下级部门
				String level = "";
				if(!TeeUtility.isNullorEmpty(dept.getDeptParentLevel())){
					level = dept.getDeptParentLevel();
				}else{
					level = dept.getGuid();
				}
				List<TeeDepartment> deptChildList = deptDao.getAllChildDeptByLevl(level);
				for (int j = 0; j < deptChildList.size(); j++) {
					deptList.add(deptChildList.get(j).getUuid());
				}
				//deptList.addAll(deptChildList);
				deptList.add(dept.getUuid());
			}else if(privScope == 2){//本部门
				deptList.add(dept.getUuid());
			}else if(privScope == 3){//自定义部门
				Iterator<TeeDepartment> it = priv.getDeptPost().iterator();
				while(it.hasNext()){
					deptList.add(it.next().getUuid());
				}
			
				//deptList.addAll(priv.getDeptPost());
			}
			if(privType == 1){//管理
				if(deptIds.equals("0")){
					postType = "1";
					return postType;
				}else{
					if(deptList.contains(TeePerson.getDept().getUuid())){//如果包含主部门
						postType = "1";
						return postType;
					}
				}
			}else if(privType == 2){//监控
				if(!postType.equals("1") && !postType.equals("2")){//如果已经是管理或者监控权限了，
					if(deptIds.equals("0")){
						postType = "2";
					}else{
						if(deptList.contains(TeePerson.getDept().getUuid())){//包括主办人部门
							postType = "2";
						}
					}
				}
				
			}else if(privType == 3){//查询
				if(!postType.equals("1") && !postType.equals("2") &&  !postType.equals("3")){//如果已经是管理、监控、查询权限了，
					if(deptIds.equals("0")){
						postType = "3";
					}else{
						if(deptList.contains(TeePerson.getDept().getUuid())){//包括主办人部门
							postType = "3";
						}
					}
					
				}
				
			}
		}
		return postType;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getQueryPostDeptsByFlowType(int, com.tianee.oa.core.org.bean.TeePerson, java.lang.String)
	 */
	@Override
	public String getQueryPostDeptsByFlowType(int flowTypeId , TeePerson person , String queryType){
		List<TeeFlowPriv>  privList =  new ArrayList<TeeFlowPriv>();
		if(queryType.equals("1")){// 1-管理
			privList = flowPrivDao.getSelectManageFlowPrivByFlowTypeId(flowTypeId,person);
		}else if(queryType.equals("3")){//3-查询
			privList = flowPrivDao.getSelectQueryFlowPrivByFlowTypeId(flowTypeId,person);
		}else if(queryType.equals("4")){//3-编辑
			privList = flowPrivDao.getSelectEditFlowPrivByFlowTypeId(flowTypeId, person);
		}		
		String deptIds = getPrivDeptIds(privList, person);
		return deptIds;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getQueryPostDeptsByAllFlow(com.tianee.oa.core.org.bean.TeePerson, java.lang.String)
	 */
	@Override
	public List getQueryPostDeptsByAllFlow( TeePerson person , String queryType){
		List flowTypeList  = new ArrayList();
		person = presonDao.selectPersonById(person.getUuid());
		flowTypeList = flowPrivDao.getFlowPriv(person.getUuid(), person.getUserRole().getUuid(), person.getDept().getUuid(),queryType);//根据监控类型 获取对应的流程
		//获取所有流程和权限部门Id字符串
		List flowArray = new ArrayList(); 
		for (int i = 0; i < flowTypeList.size(); i++) {
			TeeFlowType flowType = (TeeFlowType)flowTypeList.get(i);
			Map map = new HashMap();
			map.put("flowTypeId", flowType.getSid());
			String postDeptIds =  getPrivDeptIds(new ArrayList (flowType.getFlowPrivs()), person);
			map.put("postDeptIds", postDeptIds);
			flowArray.add(map);
		}
		
		return flowArray;
	}

	
	


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#setPresonDao(com.tianee.oa.core.org.dao.TeePersonDao)
	 */
	@Override
	public void setPresonDao(TeePersonDao presonDao) {
		this.presonDao = presonDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#setFlowTypeServ(com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface)
	 */
	@Override
	public void setFlowTypeServ(TeeFlowTypeServiceInterface flowTypeServ) {
		this.flowTypeServ = flowTypeServ;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#setDeptDao(com.tianee.oa.core.org.dao.TeeDeptDao)
	 */
	@Override
	public void setDeptDao(TeeDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#setRoleDao(com.tianee.oa.core.org.dao.TeeUserRoleDao)
	 */
	@Override
	public void setRoleDao(TeeUserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#setFlowServContext(com.tianee.oa.core.workflow.TeeWorkFlowServiceContext)
	 */
	@Override
	public void setFlowServContext(TeeWorkFlowServiceContextInterface flowServContext) {
		this.flowServContext = flowServContext;
	}

	
	

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface#getHasMonitorPrivFlowTypeIds(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getHasMonitorPrivFlowTypeIds(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int pid = loginUser.getUuid();
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, pid);
		TeeUserRole role = loginUser.getUserRole();
		TeeDepartment dept = loginUser.getDept();
      
		String deptIds = "";
		List runList = new ArrayList();
		List params = new ArrayList();
		
		StringBuffer hql = new StringBuffer(" select distinct(frp.flowType.sid) from TeeFlowRunPrcs frp where frp.flag in (1,2) and frp.flowRun.endTime is null and frp.flowRun.delFlag=0 and frp.flowRun.isSave=1 ");
		
		
		//得到管理权限流程
		List managePrivList = getQueryPostDeptsByAllFlow(loginUser, "1");
		//得到监控权限流程
		List monitorPrivList = getQueryPostDeptsByAllFlow(loginUser, "2");
		//得到查询权限流程
		List searchPrivList = getQueryPostDeptsByAllFlow(loginUser, "3");
		//得到编辑权限流程
		List editablePrivList = getQueryPostDeptsByAllFlow(loginUser, "4");
		
		StringBuffer myManage = new StringBuffer();
		Map queryData = null;
		//所有权限汇总
		List allPrivList = new ArrayList();
		for(int i=0;i<managePrivList.size();i++){
			queryData = (Map) managePrivList.get(i);
			allPrivList.add(queryData);
		}
		for(int i=0;i<monitorPrivList.size();i++){
			queryData = (Map) monitorPrivList.get(i);
			allPrivList.add(queryData);
		}
		for(int i=0;i<searchPrivList.size();i++){
			queryData = (Map) searchPrivList.get(i);
			allPrivList.add(queryData);
		}
		for(int i=0;i<editablePrivList.size();i++){
			queryData = (Map) editablePrivList.get(i);
			allPrivList.add(queryData);
		}
		
		 //获取真正有权限的集合
		List  realPrivList = new ArrayList();
		Map m=null;
		if (allPrivList.size() != 0) {
			for (int i = 0; i < allPrivList.size(); i++) {
				m = (Map) allPrivList.get(i);
				if (!String.valueOf(m.get("postDeptIds")).equals("")) {// 有权限
					realPrivList.add(m);
				}
			}
		}
		
		if(realPrivList.size()!=0){//有权限
			for(int i=0;i<realPrivList.size();i++){
				queryData = (Map) realPrivList.get(i);
				if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
					myManage.append("(frp.flowRun.flowType.sid="+queryData.get("flowTypeId"));
					String postDeptIds = String.valueOf(queryData.get("postDeptIds"));
					if(!postDeptIds.equals("0")){//加入限定部门条件
						if(postDeptIds.endsWith(",")){
							postDeptIds =postDeptIds.substring(0,postDeptIds.length()-1);
						}
						myManage.append(" and exists (select 1 from TeeDepartment dept where dept.uuid in ("+postDeptIds+") and frp.prcsUser.dept=dept)");
					}
					myManage.append(")");
					if(i!=realPrivList.size()-1){
						myManage.append(" or ");
					}
				}
			}
		}else{//无任何权限
			myManage.append(" frp.flowRun.flowType.sid=0 ");
		}
		
		
		if(!"".equals(myManage.toString())){
			hql.append(" and ("+myManage.toString()+")");
		}
		
		
		
		runList = simpleDaoSupport.executeQuery(hql.toString(), null);
		
		String privFlowIds="";	
		for (int j = 0; j < runList.size(); j++) {
			privFlowIds+=runList.get(j)+",";
		}

		if(privFlowIds.endsWith(",")){
			privFlowIds=privFlowIds.substring(0,privFlowIds.length()-1);
		}
		
		json.setRtState(true);
		json.setRtData(privFlowIds);
		return json;
	}

}
