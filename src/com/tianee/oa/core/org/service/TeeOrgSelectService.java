package com.tianee.oa.core.org.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.net.www.content.text.plain;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserGroup;
import com.tianee.oa.core.org.bean.TeeUserOnline;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.bean.TeeUserRoleType;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeeOrgDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserOnlineDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.model.TeeUserRoleModel;
import com.tianee.oa.core.priv.bean.TeeModulePriv;
import com.tianee.oa.core.priv.dao.TeeMenuGroupDao;
import com.tianee.oa.core.priv.dao.TeeModulePrivDao;
import com.tianee.oa.core.priv.model.TeeModulePrivModel;
import com.tianee.oa.core.priv.service.TeeModulePrivService;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeFixedFlowDataLoaderInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStrZipUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeOrgSelectService extends TeeBaseService {

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeDeptDao deptDao;

	@Autowired
	private TeeOrgDao orgDao;

	@Autowired
	private TeeUserRoleDao roleDao;

	@Autowired
	private TeeMenuGroupDao menuGroupDao;

	@Autowired
	private TeeModulePrivService modulePrivServ;
	
	@Autowired
	private TeeUserGroupService userGroupDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeDeptService deptService;
	
	@Autowired
	private TeeModulePrivService modulePrivService;
	
	@Autowired
	private TeeUserOnlineDao onlineDao;
	
	@Autowired
	private TeeModulePrivDao modelPrivDao;
	
	@Autowired
	private TeeFixedFlowDataLoaderInterface fixedFlowDataLoader;
	
	
	
	
	/**
	 * 一次性加载
	 * 获取部门树
	 * @author syl
	 * @date 2013-11-21
	 * @param request
	 * @return
	 */
	public TeeJson getOrgDeptTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
		List<TeeOrganization> list = orgDao.traversalOrg();
		if (list.size() > 0) {
			TeeOrganization org = list.get(0);
			TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + ";org", org.getUnitName(), true, "0", true, "pIconHome" , "");
			orgDeptList.add(ztree);
			
			List<TeeDepartment> deptList = deptDao.getAllDept(); //获取所有部门
			for (int i = 0; i < deptList.size(); i++) {
				TeeDepartment dept = deptList.get(i);
				TeeDepartment parentDept = dept.getDeptParent();
				String parentId = org.getSid() + ";org";
				if(parentDept !=  null){
					parentId = parentDept.getUuid() + "";
				}
				TeeZTreeModel deptZtree = new TeeZTreeModel(dept.getUuid()+ "",dept.getDeptName(), false, parentId, true, "deptNode" , "");
				orgDeptList.add(deptZtree);
			}
		}
		json.setRtData(orgDeptList);
		return json;
	}

	/**
	 * 发送部门权限（通用选择部门树）
	 * @param request
	 * @param modelId
	 * @return
	 */
	public TeeJson getSendPostDept(HttpServletRequest request ,  String modelId){
		TeeJson json = new TeeJson();
		TeePerson loginUser  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部  空为默认
		String isSingle = TeeStringUtil.getString(request.getParameter("isSingle"), "0");
		
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
		List<TeeOrganization> list = orgDao.traversalOrg();
		if (list.size() > 0) {
			TeeOrganization org = list.get(0);
			boolean isSingleType = false;
			if(isSingle.equals("1")){
				isSingleType = true;
			}
			TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + ";org", org.getUnitName(), true, "0", true, "pIconHome" , "" , false, false , isSingleType);
			orgDeptList.add(ztree);
			List<TeeDepartment>  deptList = new ArrayList();
			
			TeeModulePriv modulePriv = modulePrivServ.selectPrivEntityByUserId(loginUser.getUuid() + "", modelIdStr);;
			
			if(modulePriv!=null){
				if("0".equals(modulePriv.getDeptPriv())){//本部门
					deptList.add(loginUser.getDept());
				}else if("1".equals(modulePriv.getDeptPriv())){//全体
					deptList=simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d order by d.deptNo asc", null);
					deptList=simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where d.deptParent is null order by d.deptNo asc", null);
				}else if("2".equals(modulePriv.getDeptPriv())){//指定部门
					deptList.addAll(modulePriv.getDeptIds());
				}else if("3".equals(modulePriv.getDeptPriv())){//指定人员
					Set<TeePerson> persons = modulePriv.getUserIds();
					Set<TeeDepartment> depts = new HashSet();
					for(TeePerson p:persons){
						depts.addAll(p.getDeptIdOther());
						depts.add(p.getDept());
					}
					deptList.addAll(depts);
				}
				
			}else{
				deptList = deptService.getDeptsByLoginUser(loginUser); //deptDao.getAllDept();//获取所有的部门
			}
			
			
			
			boolean isAllDept = false;
//			List<TeeDepartment> checkDeptList = new  ArrayList<TeeDepartment>();
			
//			if(!TeePersonService.checkIsSuperAdmin(loginUser, "")){//不是超级管理员
//				if(modelIdStr.equals("")){//默认
//					isAllDept = true;
//				}else if(modelIdStr.equals("0")){//全部
//					isAllDept = true;
//				}else{
//					int modelIdInt = TeeStringUtil.getInteger(modelIdStr, 0);
//					TeeModulePriv modelPriv =modelPrivDao.selectPrivByUserId(loginUser.getUuid() , modelIdInt);
//					if(modelPriv != null){
//						if(modelPriv.getRolePriv().equals("2")){//所有角色
//							if(modelPriv.getDeptPriv().equals("0")){//本部门
//								checkDeptList.add(loginUser.getDept());
//							}else if(modelPriv.getDeptPriv().equals("1")){//全体部门
//								isAllDept = true;
//							}else if(modelPriv.getDeptPriv().equals("2")){//指定部门
//								checkDeptList = new ArrayList<TeeDepartment>(modelPriv.getDeptIds());
//							}else if(modelPriv.getDeptPriv().equals("3")){//指定人员
//								
//							}
//						}
//					}else{
//						isAllDept = true;
//					}
//				}
//			}else{
//				isAllDept = true;//admin超级管理员
//			}
			Map idMap = new HashMap();
			for (int i = 0; i < deptList.size(); i++) {
				idMap.put(String.valueOf(deptList.get(i).getUuid()), "");
			}
			
			TeeDepartment dept=null;
			boolean nocheck=false;
			TeeDepartment parentDept=null;
			String parentId = "";
			TeeZTreeModel deptZtree =null;
			for (int i = 0; i < deptList.size(); i++) {
				parentId = "";
				dept = deptList.get(i);
				nocheck = false;//不显示选择框
//				if(!isAllDept){//不是全体
//					if(checkDeptList.size() > 0){
//						for (int j = 0; j < checkDeptList.size(); j++) {
//							TeeDepartment checkDept = checkDeptList.get(j);
//							if(dept.getUuid() == checkDept.getUuid()){//包含部门
//								nocheck = false;
//								break;
//							}
//						}
//					}else{
//						continue;
//					}
//				}else{
//					nocheck = false;
//				}
				parentDept = dept.getDeptParent();
				
				if(parentDept !=  null){
					parentId = String.valueOf(parentDept.getUuid());
				}
				
				deptZtree = new TeeZTreeModel();
				deptZtree.setId(String.valueOf(dept.getUuid()));
				deptZtree.setName(dept.getDeptName());
				deptZtree.setParent(false);
				deptZtree.setpId(parentId);
				if(!idMap.containsKey(parentId)){
					deptZtree.setpId(org.getSid() + ";org");
				}
				deptZtree.setOnRight(false);
				//deptZtree.setOpen(true);
				deptZtree.setIconSkin("deptNode");
				if(dept.getDeptType()==2){
					deptZtree.setIconSkin("pIconHome");
				}
				deptZtree.setChecked(false);
				deptZtree.setNocheck(nocheck);
				orgDeptList.add(deptZtree);
			}
		}
		json.setRtData(orgDeptList);
		json.setRtState(true);
		// List<TeePerson> list = personDao.getPersonsByUuidsOrDeptIdsOrRoleIds(uuids, deptIds, roleIds);
		return json;
	}
	
	
	/**
	 * 发送部门权限（通用选择部门数组）
	 * @param request
	 * @param modelId
	 * @return
	 */
	public TeeJson getSendPostDeptList(HttpServletRequest request ,  String modelId){
		TeeJson json = new TeeJson();
		
		TeePerson loginUser  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部  空为默认
		String isSingle = TeeStringUtil.getString(request.getParameter("isSingle"), "0");
		String deptId = TeeStringUtil.getString(request.getParameter("deptId"));
				
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
	
		List<TeeDepartment>  deptList = deptService.getDeptsByLoginUser(loginUser);
       
		boolean isAllDept = false;
		//List<TeeDepartment> checkDeptList = new  ArrayList<TeeDepartment>();
		
//		if(!TeePersonService.checkIsSuperAdmin(loginUser, "")){//不是超级管理员
//			if(modelIdStr.equals("")){//默认
//				isAllDept = true;
//			}else if(modelIdStr.equals("0")){//全部
//				isAllDept = true;
//			}else{
//				int modelIdInt = TeeStringUtil.getInteger(modelIdStr, 0);
//				TeeModulePriv modelPriv =modelPrivDao.selectPrivByUserId(loginUser.getUuid() , modelIdInt);
//				if(modelPriv != null){
//					if(modelPriv.getRolePriv().equals("2")){//所有角色
//						if(modelPriv.getDeptPriv().equals("0")){//本部门
//							deptList.add(loginUser.getDept());
//						}else if(modelPriv.getDeptPriv().equals("1")){//全体部门
//							isAllDept = true;
//						}else if(modelPriv.getDeptPriv().equals("2")){//指定部门
//							deptList = new ArrayList<TeeDepartment>(modelPriv.getDeptIds());
//						}else if(modelPriv.getDeptPriv().equals("3")){//指定人员
//							
//						}
//					}
//				}else{
//					isAllDept = true;
//				}
//			}
//		}else{
//			isAllDept = true;//admin超级管理员
//		}
//		int deptIdInt= TeeStringUtil.getInteger(deptId, 0);
//		if(deptIdInt == 0){
//			if(isAllDept){//所有
//				deptList = deptDao.getAllDept();
//			}
//		}else{
//			TeeDepartment dept = deptDao.selectDeptById(Integer.parseInt(deptId));
//			String levelUuid = dept.getGuid();
//			if(!TeeUtility.isNullorEmpty(dept.getDeptParentLevel())){
//				levelUuid = dept.getDeptParentLevel()
//						;
//			}
//			if(isAllDept){
//				deptList = deptDao.getSelectDept(Integer.parseInt(deptId),levelUuid );
//			}else{
//				StringBuffer deptIds = new StringBuffer();
//				for (int i = 0; i < deptList.size(); i++) {
//					deptIds.append(deptList.get(i).getUuid() + ",");
//				}
//				deptList = deptDao.getSelectDept(Integer.parseInt(deptId),levelUuid , deptIds.toString());
//			}
//		}
		List<Map> mapList = new ArrayList<Map>();
		Map<String,String> map =null;
		String deptName ="";
		int uuid=0;
		TeeDepartment deptParent=null;
		int deptParentId=0;
		String deptParentLevel="";
		String[] deptStr =null;
		for (int i = 0; i < deptList.size(); i++) {
			map = new HashMap<String,String>();
			deptName = deptList.get(i).getDeptName();
			uuid = deptList.get(i).getUuid() ;
			deptParent =  deptList.get(i).getDeptParent();
			deptParentId= 0;
			if(deptParent != null){
				deptParentId =  deptParent.getUuid();
			}
			deptParentLevel = deptList.get(i).getDeptParentLevel();
			map.put("uuid", String.valueOf(uuid));
			map.put("deptName", deptName);
			deptStr = deptService.getSelectDeptStr( deptName, deptId , deptParentLevel);
			map.put("deptNameStr", deptStr[0]);
			map.put("deptLevel", deptStr[1]);
			map.put("deptParentId", String.valueOf(deptParentId));
			mapList.add(map);
		}
		json.setRtData(mapList);
		json.setRtState(true);
		// List<TeePerson> list = personDao.getPersonsByUuidsOrDeptIdsOrRoleIds(uuids, deptIds, roleIds);
		return json;
	}
	
	/**
	 * 发送角色
	 * @param request
	 * @param modelId
	 * @return
	 */
	public TeeJson getSendPostUserRole(HttpServletRequest request ,  String modelId){
		TeeJson json = new TeeJson();
		TeePerson loginUser  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeUserRole userRole = loginUser.getUserRole();
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部  空为默认

		String privOp = request.getParameter("privOp") == null ? "" : request.getParameter("privOp");
		List<TeeUserRole> list = new ArrayList<TeeUserRole>();
		List<TeeUserRoleModel> listM = new ArrayList<TeeUserRoleModel>(); 
	    boolean isController = true;
	    boolean isAdminPriv = TeePersonService.checkIsAdminPriv(loginUser);
		boolean isSuperAdminPriv = TeePersonService.checkIsSuperAdmin(loginUser,"");
		if(privOp.equals("1") || isAdminPriv ){//不需要控制权限
			isController = false;
		}
		if(privOp.equals("2") || isSuperAdminPriv ){//新建用户的时
			isController = false;
		}
		String  userRolePriv = "0";
		String userRoleIds = "";
		if(modelIdStr.equals("")){//默认
			isController = false;
		}else if(modelIdStr.equals("0")){//全部
			isController = false;
		}else{
			int modelIdInt = TeeStringUtil.getInteger(modelIdStr, 0);
			TeeModulePriv modelPriv =modelPrivDao.selectPrivByUserId(loginUser.getUuid() , modelIdInt);
			if(modelPriv != null){
				if(modelPriv.getDeptPriv().equals("1")){//全体部门
					if(modelPriv.getRolePriv().equals("0")){//低角色
						userRolePriv = modelPriv.getRolePriv();
					
					}else if(modelPriv.getRolePriv().equals("1")){//同角色或者低角色
						userRolePriv = modelPriv.getRolePriv();
						
					}else if(modelPriv.getRolePriv().equals("2")){//所有角色
						isController = false;
						
					}else if(modelPriv.getRolePriv().equals("3")){//指定角色	
						List<TeeUserRole> userRoleListTemp = new ArrayList<TeeUserRole>(modelPriv.getRoleIds());
						userRolePriv = modelPriv.getRolePriv();
						for (int i = 0; i < userRoleListTemp.size(); i++) {
							userRoleIds = userRoleIds + userRoleListTemp.get(i).getUuid() + ",";
						}
					}
				}else{
					json.setRtData(listM);
					json.setRtState(true);
				    return json;
				}
			}else{
				isController = false;
			}
		}
		list = roleDao.selectUserPrivListByPerson(isController, userRolePriv, userRole.getRoleNo(), userRoleIds);
		
		for (int i = 0; i <list.size(); i++) {
			TeeUserRoleModel modul = new TeeUserRoleModel();
			BeanUtils.copyProperties(list.get(i), modul);
			listM.add(modul);
		}
		
		json.setRtData(listM);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 发送角色   --- 权限树  和getSendPostUserRole方法一样
	 * @param request
	 * @param modelId
	 * @return
	 */
	public TeeJson getSendPostUserRoleTree(HttpServletRequest request ,  String modelId){
		TeeJson json = new TeeJson();
		TeePerson loginUser  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		TeeUserRole userRole = loginUser.getUserRole();
//		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部  空为默认
//
//		String privOp = request.getParameter("privOp") == null ? "" : request.getParameter("privOp");
//		List<TeeUserRole> list = new ArrayList<TeeUserRole>();
//		List<TeeUserRoleModel> listM = new ArrayList<TeeUserRoleModel>(); 
//	    boolean isController = true;
//	    boolean isAdminPriv = TeePersonService.checkIsAdminPriv(loginUser);
//		boolean isSuperAdminPriv = TeePersonService.checkIsSuperAdmin(loginUser,"");
//		if(privOp.equals("1") || isAdminPriv ){//不需要控制权限
//			isController = false;
//		}
//		if(privOp.equals("2") || isSuperAdminPriv ){//新建用户的时
//			isController = false;
//		}
//		String  userRolePriv = "0";
//		String userRoleIds = "";
//		if(modelIdStr.equals("")){//默认
//			isController = false;
//		}else if(modelIdStr.equals("0")){//全部
//			isController = false;
//		}else{
//			int modelIdInt = TeeStringUtil.getInteger(modelIdStr, 0);
//			TeeModulePriv modelPriv =modelPrivDao.selectPrivByUserId(loginUser.getUuid() , modelIdInt);
//			if(modelPriv != null){
//				if(modelPriv.getDeptPriv().equals("1")){//全体部门
//					if(modelPriv.getRolePriv().equals("0")){//低角色
//						userRolePriv = modelPriv.getRolePriv();
//					
//					}else if(modelPriv.getRolePriv().equals("1")){//同角色或者低角色
//						userRolePriv = modelPriv.getRolePriv();
//						
//					}else if(modelPriv.getRolePriv().equals("2")){//所有角色
//						isController = false;
//						
//					}else if(modelPriv.getRolePriv().equals("3")){//指定角色	
//						List<TeeUserRole> userRoleListTemp = new ArrayList<TeeUserRole>(modelPriv.getRoleIds());
//						userRolePriv = modelPriv.getRolePriv();
//						for (int i = 0; i < userRoleListTemp.size(); i++) {
//							userRoleIds = userRoleIds + userRoleListTemp.get(i).getUuid() + ",";
//						}
//					}
//				}else{
//					json.setRtData(listM);
//					json.setRtState(true);
//				    return json;
//				}
//			}else{
//				isController = false;
//			}
//		}
//		list = roleDao.selectUserPrivListByPerson(isController, userRolePriv, userRole.getRoleNo(), userRoleIds);
		
//		List <TeeZTreeModel> ztreeModelList = new ArrayList<TeeZTreeModel>();
//		TeeZTreeModel ztreeHome = new TeeZTreeModel("ALL", "选择全部角色", true, "0", true, "pIconHome" , "");
//		ztreeModelList.add(ztreeHome);
//		Set set = new HashSet();
//		for (int i = 0; i <list.size(); i++) {
//			TeeUserRole role = list.get(i);
//			TeeUserRoleType type = role.getUserRoleType();//角色分类
//			TeeZTreeModel ztree = new TeeZTreeModel();
//			if(type != null){
//				if(!set.contains(type.getSid())){
//					set.add(type.getSid());
//					//添加角色类型
//					TeeZTreeModel ztreeType = new TeeZTreeModel(type.getSid() + "_roleType", type.getTypeName(), true, "ALL", true, "" , "");
//					ztreeModelList.add(ztreeType);
//				}
//				 ztree.setpId(type.getSid() + "_roleType");
//			}else{
//				 ztree.setpId("1_roleType");
//			}
//			
//		    ztree.setId(role.getUuid() + "");
//		    ztree.setName(role.getRoleName());
//		    ztree.setParent(false);
//		    //ztree.setIconSkin("");
//		    ztreeModelList.add(ztree);
//		}
		
		
		
		List <TeeZTreeModel> ztreeModelList = new ArrayList<TeeZTreeModel>();
		
		//获取全部门
		List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(loginUser);
		//获取顶层组织架构
		TeeOrganization organization = orgDao.traversalOrg().get(0);
		TeeZTreeModel orgNode = new TeeZTreeModel("org_0", organization.getUnitName(), false, "ALL", true, "pIconHome" , "");
		ztreeModelList.add(orgNode);
		
		Map deptIdSet=new HashMap();
		for(TeeDepartment dept:deptList){
			if(!deptIdSet.containsKey(dept.getUuid())){
				deptIdSet.put(dept.getUuid(), "");
			}
		}
		
		
		StringBuffer deptIds = new StringBuffer();
		for(TeeDepartment dept:deptList){
			if(dept.getDeptParent()==null){//如果是顶级节点，则pid设置为org_0
				orgNode = new TeeZTreeModel("dept_"+dept.getUuid(), dept.getDeptName(), false, "org_0", false, "deptNode" , "");
				ztreeModelList.add(orgNode);
			}else{//如果不是顶级节点
				if(deptIdSet.containsKey(dept.getDeptParent().getUuid())){//如果父节点存在
					orgNode = new TeeZTreeModel("dept_"+dept.getUuid(), dept.getDeptName(), false, "dept_"+dept.getDeptParent().getUuid(), false, "deptNode" , "");
					ztreeModelList.add(orgNode);
				}else{
					orgNode = new TeeZTreeModel("dept_"+dept.getUuid(), dept.getDeptName(), false, "org_0", false, "deptNode" , "");
					ztreeModelList.add(orgNode);
				}
			}
			orgNode.setIconSkin("deptNode");
			if(dept.getDeptType()==2){
				orgNode.setIconSkin("pIconHome");
			}
			deptIds.append(dept.getUuid()+",");
			//deptIds+=dept.getUuid()+",";
		}
		
		
		//获取全局角色
		List<TeeUserRole> globalRoles = roleDao.executeQuery("from TeeUserRole where dept is null  order by roleNo asc", null);
		for(TeeUserRole role:globalRoles){
			orgNode = new TeeZTreeModel("role_"+role.getUuid(), role.getRoleName(), false, "org_0", false, "person_online_node" , "");
			ztreeModelList.add(orgNode);
		}
		
		//获取指定部门下面的角色
		List<TeeUserRole> deptRoles = roleDao.executeQuery("from TeeUserRole where "+TeeDbUtility.IN("dept.uuid", deptIds.toString())+" order by roleNo asc", null);
		for(TeeUserRole role:deptRoles){
			orgNode = new TeeZTreeModel("role_"+role.getUuid(), role.getRoleName(), false, "dept_"+role.getDept().getUuid(), false, "person_online_node" , "");
			ztreeModelList.add(orgNode);
		}
		
		json.setRtData(ztreeModelList);
		json.setRtState(true);

		return json;
	}
	
	
	/**
	 * 获取角色树，异步
	 * @param request
	 * @param modelId
	 * @return
	 */
	public TeeJson getOrgRoleTree4Async(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int deptId = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		
		List <TeeZTreeModel> ztreeModelList = new ArrayList<TeeZTreeModel>();
		
		//获取全部门
		List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(loginUser);
		TeeZTreeModel orgNode =null;
		boolean hasChild = false;
		long roleCount = 0;
		if(deptId==0){//获取顶级树结构
			List<TeeDepartment> rootDepts = deptService.selectDept("from TeeDepartment where deptParent is null  order by deptNo asc", null);
			for(TeeDepartment dept:rootDepts){
				hasChild = false;
				roleCount = 0;
				if(!deptList.contains(dept)){
					continue;
				}
				
				//判断部门下是否存在子部门或者角色
				if(dept.getChildren().size()!=0){
					hasChild = true;
				}
				roleCount = simpleDaoSupport.count("select count(uuid) from TeeUserRole where dept.uuid="+dept.getUuid(), null);
				if(roleCount!=0){
					hasChild = true;
				}
				
				orgNode = new TeeZTreeModel("dept_"+dept.getUuid()+"", dept.getDeptName(), hasChild, "0", false, "deptNode" , "");
				ztreeModelList.add(orgNode);
				
				if(dept.getDeptType()==2){
					orgNode.setIconSkin("pIconHome");
				}
			}
			
			//获取全局角色
			List<TeeUserRole> globalRoles = roleDao.executeQuery("from TeeUserRole where dept is null  order by roleNo asc", null);
			for(TeeUserRole role:globalRoles){
				orgNode = new TeeZTreeModel("role_"+role.getUuid(), role.getRoleName(), false, "0", false, "person_online_node" , "");
				ztreeModelList.add(orgNode);
			}
			
		}else{
			List<TeeDepartment> depts = deptService.selectDept("from TeeDepartment where deptParent.uuid="+deptId+" order by deptNo asc", null);
			for(TeeDepartment dept:depts){
				hasChild = false;
				roleCount = 0;
				if(!deptList.contains(dept)){
					continue;
				}
				
				//判断部门下是否存在子部门或者角色
				if(dept.getChildren().size()!=0){
					hasChild = true;
				}
				roleCount = simpleDaoSupport.count("select count(uuid) from TeeUserRole where dept.uuid="+dept.getUuid(), null);
				if(roleCount!=0){
					hasChild = true;
				}
				
				orgNode = new TeeZTreeModel("dept_"+dept.getUuid()+"", dept.getDeptName(), dept.getChildren().size()==0?false:true, deptId+"", false, "deptNode" , "");
				ztreeModelList.add(orgNode);
				
				if(dept.getDeptType()==2){
					orgNode.setIconSkin("pIconHome");
				}
			}
			
			//获取该部门下的角色
			List<TeeUserRole> globalRoles = roleDao.executeQuery("from TeeUserRole where dept.uuid="+deptId+"  order by roleNo asc", null);
			for(TeeUserRole role:globalRoles){
				orgNode = new TeeZTreeModel("role_"+role.getUuid(), role.getRoleName(), false, deptId+"", false, "person_online_node" , "");
				ztreeModelList.add(orgNode);
			}
		}
		
		
		json.setRtData(ztreeModelList);
		json.setRtState(true);
		return json;
	}
	

	/**
	 * 一次性加载 --- 带权限
	 * 获取部门树
	 * @author syl
	 * @date 2013-11-21
	 * @param request
	 * @return
	 */
	public TeeJson getOrgPostDeptTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
		List<TeeOrganization> list = orgDao.traversalOrg();
		if (list.size() > 0) {
			TeeOrganization org = list.get(0);
			TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + ";org", org.getUnitName(), true, "0", true, "pIconHome" , "");
			orgDeptList.add(ztree);
			
			List<TeeDepartment> deptList = deptDao.getAllDept(); //获取所有部门
			for (int i = 0; i < deptList.size(); i++) {
				TeeDepartment dept = deptList.get(i);
				TeeDepartment parentDept = dept.getDeptParent();
				String parentId = org.getSid() + ";org";
				if(parentDept !=  null){
					parentId = parentDept.getUuid() + "";
				}
				TeeZTreeModel deptZtree = new TeeZTreeModel(dept.getUuid()+ "",dept.getDeptName(), false, parentId, true, "deptNode" , "");
				orgDeptList.add(deptZtree);
			}
		}
		json.setRtData(orgDeptList);
		return json;
	}
	
	
	/**
	 * 查询 by deptId 部门Id 不包含下级部门人员以及辅助部门人员  且带条件
	 * @param TeePerson
	 * @param userFilter:人员ID字符串
	 */
	public List<TeePerson> selectPersonByDeptIdAndFilter(String deptId ,HttpServletRequest request) {
		String userFilter = switchUserFilter(request.getParameter("userFilter"));
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String moduleId = request.getParameter("moduleId");
		String privNoFlag = request.getParameter("privNoFlag");
		String deptFilter = request.getParameter("deptFilter");
		TeeModulePrivModel model = null;
		if(TeeUtility.isInteger(moduleId)){
			//如果存在按模块设置
			model = modulePrivServ.selectPrivByUserId(loginPerson.getUuid() + "", moduleId);
		}
	
		return personDao.selectPersonByDeptIdAndUserFilter(Integer.parseInt(deptId), userFilter ,deptFilter, loginPerson, model ,  privNoFlag);
	}
	
	
	
	/**
	 * 查询 by deptId  本部门以及所有下级部门的人员
	 * @param TeePerson
	 */
	public List<TeePerson> selectPersonDeptAndChildDeptAndFilter(String deptId,String deptLevel,HttpServletRequest request) {
		String userFilter = switchUserFilter(request.getParameter("userFilter"));
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String moduleId = request.getParameter("moduleId");
		String privNoFlag = TeeStringUtil.getString(request.getParameter("privNoFlag"),"");;
		String deptFilter = request.getParameter("deptFilter");
		TeeModulePrivModel model = null;
		if(TeeUtility.isInteger(moduleId)){
			//如果存在按模块设置
			model = modulePrivServ.selectPrivByUserId(loginPerson.getUuid() + "", moduleId);
		}
	
		return personDao.selectSendDeptAndChildDeptPersonAndUserFilter(Integer.parseInt(deptId),deptLevel,userFilter,deptFilter ,loginPerson ,model ,privNoFlag);
	}
	
	
	/**
	 * 查询 by roleOd 不包含辅助角色人员 
	 * @param TeePerson
	 */
	public List<TeePerson> selectPersonByRoleIdAndFilter(String roleId ,HttpServletRequest request) {
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String userFilter = switchUserFilter(request.getParameter("userFilter"));
		String deptFilter = request.getParameter("deptFilter");
		
		String moduleId = request.getParameter("moduleId");
		String privNoFlag = TeeStringUtil.getString(request.getParameter("privNoFlag"),"");;
		TeeModulePrivModel model = null;
		if(TeeUtility.isInteger(moduleId)){
			//如果存在按模块设置
			model = modulePrivServ.selectPrivByUserId(loginPerson.getUuid() + "", moduleId);
		}
		return personDao.selectPersonByRoleIdAndFilter(Integer.parseInt(roleId) , userFilter,deptFilter,loginPerson,model , privNoFlag);
	}
	
	
	
	/**
	 * 工作流转交  按角色选人
	 * @param roleId
	 * @param request
	 * @return
	 */
	public List<TeePerson> selectPersonByRoleIdAndFilterWorkFlow(String roleId ,HttpServletRequest request) {
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginPerson = personService.selectByUuid(loginPerson.getUuid());
		
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);//步骤ID
	    int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);//frpSid
	
	    //根据步骤id获取当前步骤的有条件的人员ids
	    
	    TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
	    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
	    
	    Map data = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
	    String userFilter = TeeStringUtil.getString(data.get("filterPersonIds"));
		
		List<TeePerson> personList=simpleDaoSupport.executeQuery(" from TeePerson p where (userRole.uuid = ? or exists(select 1 from p.userRoleOther ou where ou.uuid=? )) and deleteStatus <> '1' and ("+TeeDbUtility.IN("p.uuid", userFilter)+") order by p.userNo asc ", new Object[]{TeeStringUtil.getInteger(roleId, 0),TeeStringUtil.getInteger(roleId, 0)});
	
	    return personList;
	}
	
	
	
	/**
	 * 查询 byId
	 * @param TeeUserGroupDao
	 */
	public List<Map> selectUserGroupByUuidAndPersonPostAndModule(TeeUserGroup userGroup,String userFilter ,String deptFilter, TeePerson person , TeeModulePrivModel model ) {
		int isHave = 0;
		List<Map> pList = new ArrayList<Map>();
		List<TeePerson> list = userGroup.getUserList();
		userFilter = "," + userFilter + ",";
		
		String sp [] = deptFilter.split(",");
		
		for (int i = 0; i< list.size(); i++) {
			if(!userFilter.equals(",0,")){//不为零需要处理
				if(userFilter.indexOf("," + list.get(i).getUuid() + ",") < 0){
					continue;
				}
			}
			if(!checkPersonIsPrivByPostPrivAndModulePriv( list.get(i), person , model)){
				continue;
			}
			if(!"".equals(deptFilter) && !TeeStringUtil.existString(sp, person.getDept().getUuid()+"")){
				continue;
			}
			Map map = new HashMap();
			map.put("uuid", list.get(i).getUuid());
			map.put("userName", list.get(i).getUserName());
			if(list.get(i).getUserRole()!=null){
				map.put("userRoleStrName", list.get(i).getUserRole().getRoleName());
			}
			if(list.get(i).getDept()!=null){
				map.put("deptIdName", list.get(i).getDept().getDeptName());
			}
			map.put("orgName", deptService.getCurrentPersonOrgName(list.get(i)));
			isHave=personService.selectOnlineByUserId(list.get(i).getUuid());
			if(isHave!=0){
				map.put("userOnlineStatus","1");
			}else{
				map.put("userOnlineStatus","0");
			}
			pList.add(map);
		}
		
		
		return pList;
	}
	
	
	/**
	 * 	判断人员是否存在权限范围内， 管理范围和按模块设置
	 * @param p
	 * @param LoginPerson
	 * @param model  
	 * @return
	 */
	public boolean  checkPersonIsPrivByPostPrivAndModulePriv(TeePerson p,TeePerson LoginPerson , TeeModulePrivModel model){
		String hql = "";
		String deptId = p.getDept().getUuid() + "";
		String userId = p.getUuid() + "";
		int roleNo = p.getUserRole().getRoleNo();
		if(model == null || (model != null &&  model.getSid() <= 0) ){//如果没有按模块设置
			
			String postDeptIds = personService.getLoginPersonPostDept(LoginPerson);//获取系统当前登录人管理范围部门Id字符串
			boolean  isSuperAdmin = personService.checkIsSuperAdmin(LoginPerson, "");
			if(isSuperAdmin){//超级管理员
				return true;
			}else{
				if(postDeptIds.equals("0")) {// 全体范围，
					return true;
				}else if(postDeptIds.equals("")){//没有权限
					return false;
				}else{
					postDeptIds = "," + postDeptIds + ",";
					if(postDeptIds.indexOf("," + deptId + ",") >=  0){//判断部门是否存在
						return true;
					}else{
						return false;
					}
				}			
			}

		}else{
			if(model.getDeptPriv().equals("1") || model.getRolePriv().equals("2") ){
				return true;
			}else{
				String roleIds = model.getRoleIdStr();//指定角色
				String deptIds = model.getDeptIdStr();//指定部门
				String userIds = model.getUserIdStr();//指定人员
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
					if(LoginPerson.getDept().getUuid() == p.getDept().getUuid()){
						return true;
					}
				}else if(model.getDeptPriv().equals("1")){//全体部门
					return true;
				}else if(model.getDeptPriv().equals("2")){//指定部门
					deptIds = "," + deptIds + ",";
					if(deptIds.indexOf("," + deptId + ",") >=  0){//判断部门是否存在
						return true;
					}
					
				}else if(model.getDeptPriv().equals("3")){//指定人员
					userIds = "," + userIds + ",";
					if(userIds.indexOf("," + userId + ",") >=  0){//判断人员是否存在
						return true;
					}
				}
				if(model.getRolePriv().equals("0")){//低角色
					if(roleNo > LoginPerson.getUserRole().getRoleNo()  ){
						return true;
					}
				}else if(model.getRolePriv().equals("1")){//同角色或者低角色
					if(roleNo >= LoginPerson.getUserRole().getRoleNo()  ){
						return true;
					}
				}else if(model.getRolePriv().equals("2")){//所有角色
					return true;
				}else if(model.getRolePriv().equals("3")){//指定角色	
					roleIds = "," + roleIds + ",";
					if(roleIds.indexOf("," + roleNo + ",") >=  0){//判断人员是否存在
						return true;
					};
				}
			}
	
		}
		return false;
	}
	
	
	/**
	 * 通用选择人员，条件查询，根据用户Id或者用户姓名进行模糊查询
	 * 并且考虑当前登录人的管理范围
	 * userFilter:人员过滤条件
	 *  
	 */
	public List  getSelectUserByUserIdOrUserName(HttpServletRequest request) {
		List<Map> pList = new ArrayList<Map>();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String userInfo = request.getParameter("user");//查询用户
		String userFilter = switchUserFilter(request.getParameter("userFilter"));
		String moduleId = request.getParameter("moduleId");
		String privNoFlag = request.getParameter("privNoFlag");
		String deptFilter = request.getParameter("deptFilter");
		TeeModulePrivModel model = null;
		if(TeeUtility.isInteger(moduleId)){
			//如果存在按模块设置
			model = modulePrivServ.selectPrivByUserId(loginPerson.getUuid() + "", moduleId);
		}
		List<TeePerson> list = personDao.getSelectUserByUserIdOrUserName(userInfo , userFilter,deptFilter,loginPerson,model ,privNoFlag);
		
		List<TeeDepartment> deptListPriv = deptService.getDeptsByLoginUser(loginPerson);
		Map deptMap = new HashMap();
		for(TeeDepartment dept:deptListPriv){
			deptMap.put(dept.getUuid(), "");
		}
		
		int isHave = 0;
		TeePerson person = null;
		Map map = null;
		for (int i = 0; i < list.size(); i++) {
			person = list.get(i);
			
			if(!deptMap.containsKey(person.getDept().getUuid())){
				continue;
			}
			
			//String uuid = obj[0].toString();
		/*	String userName = (String)obj[1];
			uuids = uuids + obj[0] + ",";
			userNames = userNames + userName + ",";*/
			map = new HashMap();
			map.put("uuid", person.getUuid() );
			map.put("userName", person.getUserName());
			if(list.get(i).getUserRole()!=null){
				map.put("userRoleStrName", list.get(i).getUserRole().getRoleName());
			}
			if(list.get(i).getDept()!=null){
				map.put("deptIdName", list.get(i).getDept().getDeptName());
				map.put("deptId", list.get(i).getDept().getUuid());
			}
			map.put("orgName", deptService.getCurrentPersonOrgName(list.get(i)));
			//isHave=personService.selectOnlineByUserId(list.get(i).getUuid());
			map.put("performCode", list.get(i).getPerformCode());
			if(isHave!=0){
				map.put("userOnlineStatus","1");
			}else{
				map.put("userOnlineStatus","0");
			}
			pList.add(map);
		}
		return pList;
	}
	
	
	/**
	 * 通用选择部门，条件查询，根据部门名称进行模糊查询
	 * 并且考虑当前登录人的管理范围
	 * userFilter:人员过滤条件
	 *  
	 */
	public List  getSelectDeptByDeptName(HttpServletRequest request) {
		List<Map> pList = new ArrayList<Map>();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String deptInfo = request.getParameter("dept");//查询用户
		/*String userFilter = switchUserFilter(request.getParameter("userFilter"));
		String moduleId = request.getParameter("moduleId");
		String privNoFlag = request.getParameter("privNoFlag");
		String deptFilter = request.getParameter("deptFilter");
		TeeModulePrivModel model = null;*/
		/*if(TeeUtility.isInteger(moduleId)){
			//如果存在按模块设置
			model = modulePrivServ.selectPrivByUserId(loginPerson.getUuid() + "", moduleId);
		}*/
		List<TeeDepartment> list = deptDao.getSelectDeptByDeptName(deptInfo);
		for (int i = 0; i < list.size(); i++) {
			TeeDepartment dept = list.get(i);
			//String uuid = obj[0].toString();
		/*	String userName = (String)obj[1];
			uuids = uuids + obj[0] + ",";
			userNames = userNames + userName + ",";*/
			Map map = new HashMap();
			map.put("uuid", dept.getUuid() );
			map.put("deptName", dept.getDeptName());
			pList.add(map);
		}
		return pList;
	}
	
	
	
	/**
	 * 主界面查询 -- 不带权限
	 * @author syl
	 * @date 2013-12-3
	 * @param request
	 * @return
	 */
	public List  queryUserByUserIdOrUserName(HttpServletRequest request) {
		List<Map> pList = new ArrayList<Map>();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String userName = request.getParameter("userName");
		String name  = request.getParameter("name");
		List<TeePerson> list = personDao.queryUserByUserIdOrUserName(userName);
		for (int i = 0; i < list.size(); i++) {
			TeePerson person = list.get(i);
			//String uuid = obj[0].toString();
		/*	String userName = (String)obj[1];
			uuids = uuids + obj[0] + ",";
			userNames = userNames + userName + ",";*/
			Map map = new HashMap();
			map.put("id", person.getUuid() );
			map.put("name", person.getUserName() +";" + userName );
			
			
			map.put("sid", person.getUuid() );
			map.put("userName", person.getUserName() );
			map.put("userId", person.getUserId());
			String deptName = "";
			String userRoleName = "";
			if(person.getDept() != null){
				deptName = person.getDept().getDeptName();
			}
			if(person.getUserRole() != null){
				userRoleName = person.getUserRole().getRoleName();
			}
			map.put("deptName", deptName);
			map.put("userRoleName", userRoleName);
			pList.add(map);
		}
		return pList;
	}
	
	
	
	
	
	
	
	/**
	 * 获取所有在线人员
	 * @author syl
	 * @date 2013-11-19
	 * @param deptId
	 * @param LoginPerson
	 * @return
	 */
	public TeeJson  selectOrgOnlineUserTree(String deptId  ,TeePerson LoginPerson) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
		List orgPersonList = new ArrayList();
		List<TeeOrganization> list = orgDao.traversalOrg();
		if (list.size() > 0) {
			TeeOrganization org = list.get(0);
			TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + ";org", org.getUnitName(), true, "0", true, "pIconHome" , "");
			orgDeptList.add(ztree);
			
			//获取当前人员可见范围内的所有部门
			List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(LoginPerson);
			Map idMap = new HashMap();
			
			
			StringBuffer deptIds = new StringBuffer();
			for(TeeDepartment dept:deptList){
				deptIds.append(dept.getUuid()+",");
				idMap.put(dept.getUuid()+"", "");
			}
			if(deptIds.length()!=0){
				deptIds.deleteCharAt(deptIds.length()-1);
			}
			
			
			//获取在线人员的部门ID集合
			List<Integer> onlineDepts = personDao.getDeptUuidListByOnlineUserByDeptIds(deptIds.toString());
			StringBuffer deptIds1 = new StringBuffer();
			Map onlineIdMap = new HashMap();
			for(int id:onlineDepts){
				deptIds1.append(id+",");
			}
			if(deptIds1.length()!=0){
				deptIds1.deleteCharAt(deptIds1.length()-1);
			}
			
			//通过在线人员部门ID集合，获取deptFullIds集合
			List<String> deptFullIds = deptDao.getDeptFullIdsByDeptIds(deptIds1.toString());
			String sp[] = null;
			for(String deptFullId:deptFullIds){
				sp = deptFullId.split("/");
				for(String id:sp){
					if(!"".equals(id)){
						onlineIdMap.put(String.valueOf(id), "");
					}
				}
			}
			
			deptIds1.delete(0, deptIds1.length());
			Set<String> keys = onlineIdMap.keySet();
			for(String key:keys){
				deptIds1.append(key+",");
			}
			if(deptIds1.length()!=0){
				deptIds1.deleteCharAt(deptIds1.length()-1);
			}
			
			
			//获取所有在线的人员
			List<TeePerson> personList = personDao.getAllOnlineUserNoDeleteByDeptIds(deptIds.toString());//通过部门ID获取人员
			deptList = deptDao.getDeptByUuids(deptIds1.toString());
			
			for(TeeDepartment dept:deptList){
				ztree = new TeeZTreeModel(dept.getUuid() + ";dept", dept.getDeptName(), true, "0", true, "deptNode" , "");
				if(dept.getDeptType()==2){
					ztree.setIconSkin("pIconHome");
				}else{
					ztree.setIconSkin("deptNode");
				}
				if(dept.getDeptParent()==null){
					ztree.setpId(org.getSid() + ";org");
				}else{
					ztree.setpId(dept.getDeptParent().getUuid() + ";dept");
				}
				orgDeptList.add(ztree);
			}
			
			for(TeePerson person:personList){
				ztree = new TeeZTreeModel(person.getUuid() + ";person", person.getUserName(), false, person.getDept().getUuid()+";dept", false, "pIconHome" , "");
				if("0".equals(person.getSex())){
					ztree.setIconSkin("person_online_node");
				}else{
					ztree.setIconSkin("person_online_women_node");
				}
				
				orgDeptList.add(ztree);
			}
			
		}
		
		json.setRtData(orgDeptList);
		return json;
	}
	
	/**
	 * 获取所有上级部门的GUID
	 * @author syl
	 * @date 2014-2-14
	 * @param deptParentLevel  上级部门级别GUID长度字符串
	 * @param parentLevelSet  存放GUID Set
	 * @return
	 */
	public String getDeptParentLevel(String deptParentLevel , Set parentLevelSet){
		try {
			int levelLength = 32;
			if(!TeeUtility.isNullorEmpty(deptParentLevel)){
				int deptParentLevelLength = deptParentLevel.length();
				int deptParentLevelCount = deptParentLevelLength/levelLength;//级别数
				for (int i = 0; i < deptParentLevelCount; i++) {
					String temp = deptParentLevel.substring(levelLength*i, levelLength*(i+1));
					parentLevelSet.add(temp);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "";
	}
	
	/**
	 * 获取所有人员 不包括已删除人员
	 * @author syl
	 * @date 2013-11-19
	 * @param deptId
	 * @param LoginPerson
	 * @return
	 */
	@Transactional(readOnly=true)
	public TeeJson  getAllOrgUserTree(String deptId  ,TeePerson LoginPerson) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
		List orgPersonList = new ArrayList();
		List<TeeOrganization> list = orgDao.traversalOrg();
		if (list.size() > 0) {
			TeeOrganization org = list.get(0);
			TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "", org.getUnitName(), true, "0", true, "pIconHome" , "");
			orgDeptList.add(ztree);
			Map idMap = new HashMap();
			List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(LoginPerson);
			StringBuffer deptIds = new StringBuffer();
			for(TeeDepartment d:deptList){
				idMap.put(d.getUuid()+"", "");
				deptIds.append(d.getUuid()+",");
			}
			if(deptIds.length()!=0){
				deptIds.deleteCharAt(deptIds.length()-1);
			}
			
			for (int i = 0; i < deptList.size(); i++) {
				TeeDepartment dept = deptList.get(i);
				TeeDepartment parentDept = dept.getDeptParent();
				String parentId = org.getSid() + "";
				if(parentDept !=  null){
					parentId = parentDept.getUuid() + ";dept";
					if(!idMap.containsKey(parentDept.getUuid()+"")){
						parentId = org.getSid()+"";
					}
				}
				
				TeeZTreeModel deptZtree = null;
				if(dept.getDeptType()==2){
					deptZtree = new TeeZTreeModel(dept.getUuid()+ ";dept",dept.getDeptName(), false, parentId, true, "pIconHome" , "");
				}else{
					deptZtree = new TeeZTreeModel(dept.getUuid()+ ";dept",dept.getDeptName(), false, parentId, true, "deptNode" , "");
				}
				orgDeptList.add(deptZtree);
			}
			
			
			
			List<TeePerson> personList = personDao.getAllUserNoDeleteByDeptIds(deptIds.toString());//获取人员
			//获取在线用户
			List<TeeUserOnline> userOnlineList = onlineDao.listByDeptIds(deptIds.toString());
			TeePerson person = null;
			TeeDepartment personDept = null;
			List<TeeDepartment> otherDeptList = null;
			Iterator<TeeDepartment> it = null;
			TeeDepartment postDept = null;
			for (int i = 0; i < personList.size(); i++) {	
				person = personList.get(i);
				personDept = person.getDept();
				otherDeptList  = person.getDeptIdOther();//辅助部门
				//System.out.println(person.getUserName() + "---->" + otherDeptList.size());
				String parentId = org.getSid() + "";
				if(personDept != null){
					parentId = personDept.getUuid() + ";dept";
				}
				String iconSkin = "person_offline_node";
//				TeeUserOnline online =  onlineDao.getUseronlineByUserId(person.getUuid());
				for(TeeUserOnline userOnline:userOnlineList){
					if(userOnline.getUserId()==person.getUuid()){
						iconSkin = "person_online_node";
						if("0".equals(person.getSex())){
							iconSkin = "person_online_node";
						}else{
							iconSkin = "person_online_women_node";
						}
						break;
					}
				}
//				if(online != null  && online.getUserStatus() ==1){
//					iconSkin = "person_online_node";
//				}
//				int MAIN_ONLINE_STATUS = person.getMainOnlineStatus();
//				if(MAIN_ONLINE_STATUS == 1){//主开关在线
//					iconSkin = "person_online_node";
//				}
				it = otherDeptList.iterator();
				while(it.hasNext()){
					postDept = it.next();
					if(postDept != personDept){ //当前部门不等于辅助部门
						if(!idMap.containsKey(""+postDept.getUuid())){
							continue;
						}
						TeeZTreeModel personZtree = new TeeZTreeModel(person.getUuid() + ";personId", person.getUserName(), false, postDept.getUuid() + ";dept", true, iconSkin , TeePersonService.getPersonZtreeTitle( person) , true);
						orgPersonList.add(personZtree);
						personZtree.getParams().put("userId", person.getUserId());
					}
				}
				if(!idMap.containsKey(""+personDept.getUuid())){
					continue;
				}
				TeeZTreeModel personZtree = new TeeZTreeModel(person.getUuid() + ";personId", person.getUserName(), false, parentId, true, iconSkin , TeePersonService.getPersonZtreeTitle( person) , true);
				personZtree.getParams().put("userId", person.getUserId());
				orgPersonList.add(personZtree);
			}
			
			orgDeptList.addAll(0, orgPersonList);
			
		}
		
		json.setRtData(orgDeptList);
		return json;
	}
	
	
	
	
	/**
	 * 获取组织机构树  --- 获取带权限的部门树 （按模块权限设置、按管理范围）  start  开始
	 * @author syl
	 * @date 2014-2-9
	 * @param request
	 * @return
	 */
	public TeeJson getLeaderPostOrgTree(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		int userId = user.getUuid();
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String moduleIdStr = TeeStringUtil.getString(request.getParameter("moduleId"),"0");//模块Id
		String defaultOpenStr = TeeStringUtil.getString(request.getParameter("defaultOpen"),"0");//默认展开
		boolean defaultOpen = true;
		if(defaultOpenStr.equals("1")){
			defaultOpen = false;
		}
		List<TeeZTreeModel> deptTree = new ArrayList<TeeZTreeModel>();
		if (deptId == 0) {
			deptId = user.getDept().getUuid();
		}
		String deptIdStr = deptId + "";
		
		List<TeeDepartment> deptList = new ArrayList<TeeDepartment>();
		TeeModulePriv modulePriv = null;
		TeeModulePrivModel modulePrivModel = null;
		List<TeePerson> personList = new ArrayList<TeePerson>();
		boolean bingPerson = false;//是否绑定人员
		Map map = getDeptOrgPersonByPostAndModule(user, moduleIdStr);
		bingPerson = (Boolean) map.get("isBingPerson");
		deptList = (List<TeeDepartment>)map.get("deptList");
		personList = (List<TeePerson>)map.get("personList");
		if(!bingPerson){//不是绑定人员
			for (TeeDepartment dept : deptList) {
				String pid = "0";
				if (dept.getDeptParent() != null) {
					pid = dept.getDeptParent().getUuid() + ";dept";
				}
				TeeZTreeModel ztree = new TeeZTreeModel();
				ztree.setId(dept.getUuid() + ";dept");
				ztree.setName(dept.getDeptName());
				ztree.setOpen(defaultOpen);
				ztree.setpId(pid);
				// ztree.setParent(false);
				ztree.setIconSkin("deptNode");
				deptTree.add(ztree);
			}
			json.setRtData(deptTree);
			
			//personList = personDao.selectPersonByDeptsAndPostPriv(isSuperAdmin ,deptList ,user ,modulePrivModel );//根据部门 + 模块设置查询人员
		}
		
		for (TeePerson person : personList) {
			
			TeeZTreeModel ztree = new TeeZTreeModel();
			ztree.setId(person.getUuid() + "");
			ztree.setName(person.getUserName());
			ztree.setOpen(defaultOpen);
			String pid = "0";
			if(person.getDept() != null){
				pid = person.getDept().getUuid() +";dept";
			}
			ztree.setpId(pid);
			// ztree.setParent(false);
			ztree.setIconSkin("person_online_node");
			deptTree.add(ztree);
		
		}
		if(personList.size() == 0 ){
			deptIdStr = "";
		}else{
			deptIdStr = personList.get(0).getUuid() + "";
		}
		json.setRtData(deptTree);
		json.setRtMsg(deptIdStr);// 选中部门
		json.setRtState(true);
		//json.setRtMsg("{userIds:\"" + userIds + "\",userDatas:" + userDatas + "}" );// 指定人员
		return json;
		
	}
	
	
	/**
	 * 获取人员管理范围 ----  按模块授权和管理范围
	 * @author syl
	 * @date 2014-1-22
	 * @param person
	 * @param modulePriv
	 * @return
	 */
	public Map getDeptOrgPersonByPostAndModule(TeePerson person  ,String   moduleIdStr){
		Map map = new HashMap();
		boolean isAdminPriv = TeePersonService.checkIsAdminPriv(person);//是否是超级管理员
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person, "");
		List<TeeDepartment> deptList = new ArrayList<TeeDepartment>();
		TeeModulePriv modulePriv = null;
		TeeModulePrivModel modulePrivModel = null;
		List<TeePerson> personList = new ArrayList<TeePerson>();
		boolean bingPerson = false;//是否绑定人员
		if (isSuperAdmin) {// 超级管理员
			deptList = deptService.getDeptDao().getAllDept();
		} else {
			String moduleId = TeeStringUtil.getString(moduleIdStr,"0");// 按模块权限设置
			if (TeeUtility.isInteger(moduleId)) {
				// 如果存在按模块设置
				modulePriv = modulePrivService.getModelPrivDao().selectPrivByUserId(person.getUuid(),Integer.parseInt(moduleId));
				modulePrivModel = modulePrivService.pasreModelByModulePriv(modulePriv);
			}
			if (modulePriv != null) {// 存在
				String deptPriv = modulePriv.getDeptPriv();
				String roloPriv = modulePriv.getRolePriv();//角色类型
				if(deptPriv.equals("0")){//本部门
					deptList.add(person.getDept());
				}else if(deptPriv.equals("1")){//全体部门
					deptList = deptService.getDeptDao().getAllDept();
				}else if(deptPriv.equals("2")){//指定部门
					deptList.addAll(modulePriv.getDeptIds());
				}else if(deptPriv.equals("3")){//指定人员
					Set<TeePerson> pl =  modulePriv.getUserIds();
					Iterator< TeePerson> it = pl.iterator();
					int currPersonRoleNo = TeeStringUtil.getInteger(person.getUserRole().getRoleNo(),0);//登录用户角色排序号
					String userIds = "";
					String userDatas = "[";
					Set<TeeUserRole>  roloList = modulePriv.getRoleIds();
					while (it.hasNext()) {
						TeePerson p = it.next();
						int personRoleNo = 0;
						if(p.getUserRole() != null){
							personRoleNo = TeeStringUtil.getInteger(p.getUserRole().getRoleNo(),0);
						}else{
							continue;
						}
						if(roloPriv.equals("0") && currPersonRoleNo >= personRoleNo){//低角色
							continue;
						}else if(roloPriv.equals("1") && currPersonRoleNo > personRoleNo){//同角色或者低角色
							continue;	
						}else if(roloPriv.equals("2")){//所有角色
							
						}else if(roloPriv.equals("3") && !roloList.contains(p.getUserRole())){//指定角色
							continue;
						}
						personList.add(p);
					}

					userDatas = userDatas + "]";
					bingPerson = true;
				}
			} else {
				deptList = personService.getLoginPersonPostDeptBean(person);
			}
		}
		
		if(!bingPerson){//不是绑定人员
			personList = personDao.selectPersonByDeptsAndPostPriv(isSuperAdmin ,deptList ,person ,modulePrivModel );//根据部门 + 模块设置查询人员
		}
	
		map.put("deptList", deptList);
		map.put("isBingPerson", bingPerson);
		map.put("personList", personList);
		map.put("isSuperAdmin", isSuperAdmin);
		return map;
	}
	
	/**
	 * 获取通用选择部门
	 * @param request
	 * @return
	 */
	public TeeJson getSelectDept(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String deptId = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
		List<TeeDepartment>  list = new ArrayList<TeeDepartment>();
		if(TeeUtility.isNullorEmpty(deptId)){
			list = deptDao.getAllDept();
		}else{
			TeeDepartment dept = deptDao.selectDeptById(Integer.parseInt(deptId));
			String levelUuid = dept.getGuid();
			if(!TeeUtility.isNullorEmpty(dept.getDeptParentLevel())){
				levelUuid = dept.getDeptParentLevel();
			}
			list = deptDao.getSelectDept(Integer.parseInt(deptId),levelUuid);
		}
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		for (int i = 0; i <list.size(); i++) {
			Map<String,String> map = new HashMap<String,String>();
			String deptName = list.get(i).getDeptName();
			int uuid = list.get(i).getUuid() ;
			TeeDepartment deptParent =  list.get(i).getDeptParent();
			int deptParentId= 0;
			if(deptParent != null){
				deptParentId =  deptParent.getUuid();
			}
			String deptParentLevel = list.get(i).getDeptParentLevel();
			map.put("uuid", uuid + "");
			map.put("deptName", deptName);
			String[] deptStr = {};//getSelectDeptStr( deptName, deptId , deptParentLevel);
			map.put("deptNameStr", deptStr[0]);
			map.put("deptLevel", deptStr[1]);
			map.put("deptParentId", deptParentId + "");
			l.add(map);
		}
	    json.setRtData(l);
	    json.setRtState(true);
	    return json;
	}
	
	/**
	 * 获取系统模块功能
	 * @param map
	 * @return
	 */
	public TeeJson getSysModule(Map requestrMap){
		TeeJson json = new TeeJson();
		Map map = TeeModuleConst.MODULE_SORT_TYPE;
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	/*end 结束*/
	
	
	/*工作流程选人 start*/
    
    
		/**
		 *  获取部门  一次加载  ---- 工作流获取有人员的部门   --- 需要清空没有人的部门
		 * @author syl
		 * @date 2018-7-5
		 * @param request
		 * @return
		 */
		public TeeJson getSelectDeptTreeWorkFlow(HttpServletRequest request){
			List<TeeDepartment> listDept =  null;//主部门
			List<TeeDepartment> listOtherDept =  null;//辅助部门
		    List<TeeZTreeModel> deptTree = new  ArrayList<TeeZTreeModel>();
		    TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		    loginPerson = personService.selectByUuid(loginPerson.getUuid());
		    
		    TeeDepartment loginPresonDept = loginPerson.getDept();
		    TeeDepartmentModel loginPresonDeptModel =  new TeeDepartmentModel();//当前部门模版
		    int loginPresonDeptId = -1;
		    if(loginPresonDept != null){
		    	loginPresonDeptId = loginPresonDept.getUuid();
		    	BeanUtils.copyProperties(loginPresonDept, loginPresonDeptModel );
		    }
		    TeeJson json = new TeeJson();
		    int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);//步骤ID
		    int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);//frpSid
		
		    //根据步骤id获取当前步骤的有条件的人员ids
		    
		    TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
		    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		    
		    Map data = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
		    
		    
		    
//			String moduleId = request.getParameter("moduleId");
//			String privNoFlag = TeeStringUtil.getString(request.getParameter("privNoFlag"),"");
			
	/*		//根据流程获取过滤人员信息
			int flowPrcs = TeeStringUtil.getInteger(request.getParameter("flowPrcs") , 0);
			int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
			
			TeeFlowRunPrcs flowRun = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
			TeeFlowProcess procss = flowRun.getFlowPrcs();
			Map childNode = fixedFlowDataLoader.getFixedFlowProcessInfo(procss,flowRun,loginPerson);
			String userFilterStr = (String) childNode.get("userFilter");*/
			
//			List<TeePerson> personList = personDao.selectPersonByDeptIdAndUserFilterToWorkFlow(0, prcsId, loginPerson, privNoFlag);
		    

			List personData = new ArrayList();
			
			List personDataCurrDept = new ArrayList();
			
	
//			Set onlineDeptGuidSet = new HashSet(); //人员部门GuId
//			for (int i = 0; i < personList.size(); i++) {
//				TeePerson person = personList.get(i);
//				TeeDepartment personDept = person.getDept();
//				int personDeptId = 0;
//				if(personDept != null){
//					personDeptId = personDept.getUuid();
//				}
//				TeePersonModel personModel = new TeePersonModel();
//				personModel.setUuid(person.getUuid());
//				personModel.setUserName(person.getUserName());
//				if(personDeptId == loginPresonDeptId){//与系统当前登录人部门是相同的 
//					personDataCurrDept.add(personModel);
//				}
//				personData.add(personModel);
//				
//				List<TeeDepartment> otherDeptList  = person.getDeptIdOther();//辅助部门
//				//System.out.println(person.getUserName() + "---->" + otherDeptList.size());
//				if(personDept != null){
//					onlineDeptGuidSet.add(personDept.getGuid());//添加人员当前部门Guid
//					String deptParentLevel = personDept.getDeptParentLevel();//上级部门GUID级别
//					getDeptParentLevel(deptParentLevel, onlineDeptGuidSet);
//				}
//
//				/*if(otherDeptList != null  && otherDeptList.size() > 0){//辅助部门暂不考虑
//					for (int j = 0; j < otherDeptList.size(); j++) {//循环辅助部门
//						TeeDepartment postDept = otherDeptList.get(j);
//						if(postDept != personDept){ //当前部门不等于辅助部门
//							onlineDeptGuidSet.add(postDept.getGuid());//添加辅助部门Guid
//							String deptParentLevel = postDept.getDeptParentLevel();//上级部门GUID级别
//							getDeptParentLevel(deptParentLevel, onlineDeptGuidSet);	
//						}
//					}
//				}*/
//			}
			
			//获取当前登陆人可以差看到的部门范围
//			List<TeeDepartment> depts = deptService.getDeptsByLoginUser(loginPerson);
			
			
			List<TeePerson> curDeptPersonList = simpleDaoSupport.find("from TeePerson p where p.dept.uuid="+loginPresonDeptId+" and "+TeeDbUtility.IN("p.uuid", TeeStringUtil.getString(data.get("filterPersonIds"))), null);
			for(TeePerson p : curDeptPersonList){
				Map data0 = new HashMap();
				data0.put("uuid", p.getUuid());
				data0.put("userName", p.getUserName());
				if(p.getUserRole()!=null){
					data0.put("userRoleStrName", p.getUserRole().getRoleName());
				}
				if(p.getDept()!=null){
					data0.put("deptIdName", p.getDept().getDeptName());
				}
				data0.put("orgName", deptService.getCurrentPersonOrgName(p));
				personDataCurrDept.add(data0);
			}
			
			
			listDept = simpleDaoSupport.find("select distinct(p.dept) from TeePerson p where "+TeeDbUtility.IN("p.uuid", TeeStringUtil.getString(data.get("filterPersonIds"))), null);
			listOtherDept=simpleDaoSupport.find("select distinct(p.deptIdOther) from TeePerson p where "+TeeDbUtility.IN("p.uuid", TeeStringUtil.getString(data.get("filterPersonIds"))), null);
			
			//主部门和辅助部门放一起
			if(listOtherDept!=null&&listOtherDept.size()>0){
				for (TeeDepartment dept : listOtherDept) {
					if(!listDept.contains(dept)){
						listDept.add(dept);
					}
				}
			}
			
			
			//同时加入有权限部门的上级部门
			//先复制一个listDept
			List<TeeDepartment> listTemp=new ArrayList<TeeDepartment>();
			for (TeeDepartment teeDepartment : listDept) {
				listTemp.add(teeDepartment);
			}
			
			String deptFullId="";
			String[]deptFullIdArray=null;
			TeeDepartment deptTemp=null;
			
			
			for (TeeDepartment dept : listTemp) {
				deptFullId=dept.getDeptFullId();
				deptFullIdArray=deptFullId.substring(1,deptFullId.length()).split("/");
			    if(deptFullIdArray!=null&&deptFullIdArray.length>0){
			    	for (String str : deptFullIdArray) {
			    		deptTemp=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,TeeStringUtil.getInteger(str,0));
			    		if(deptTemp!=null&&!listDept.contains(deptTemp)){
			    			listDept.add(deptTemp);
			    		}
					}
			    	
			    }
			}
			
			Collections.sort(listDept);
			
		    for(TeeDepartment dept : listDept){
		    	String pid = "0";
		    	if(dept.getDeptParent()!=null){
		    		pid= dept.getDeptParent().getUuid() + "";
		    	}
//		    	if(!onlineDeptGuidSet.contains(dept.getGuid())){//过滤没有人员的部门
//					continue;
//				}
//		    	if(!depts.contains(dept)){
//		    		continue;
//		    	}
		    	
		    	TeeZTreeModel ztree = new TeeZTreeModel();
		    	ztree.setId(dept.getUuid() + "");
		    	ztree.setName(dept.getDeptName());
		    	ztree.setOpen(true);
		    	ztree.setpId(pid);
		    	//ztree.setParent(false);
		    	
		    	if(dept.getDeptType()==2){
		    		ztree.setIconSkin("pIconHome");
		    	}else{
		    		ztree.setIconSkin("deptNode");
		    	}
			    deptTree.add(ztree);
		    }
		    Map map  =  new HashMap();
		    map.put("ztreeData", deptTree);
		    if(personDataCurrDept.size() > 0){//有当前部门
		    	 map.put("personData", personDataCurrDept);
		    	 map.put("currDept", loginPresonDeptModel);
		    }else{
		    	map.put("personData", personData);
		    }
		   
		    
		    json.setRtData(map);
		    json.setRtState(true);
		    return json;
		}
		
	

		public TeeJson getSelectUserByDeptWorkFlow(HttpServletRequest request){
			TeeJson json = new TeeJson();
			
			TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			loginPerson = personService.selectByUuid(loginPerson.getUuid());
			
			int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
			int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
			int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			
			TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
		    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		    
			
			Map personData = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
			String userFilter = TeeStringUtil.getString(personData.get("filterPersonIds"));
			
			List<Map> datas = simpleDaoSupport.getMaps("select p.uuid as UUID,p.userName as USERNAME from TeePerson p where (p.dept.uuid="+deptId+" or exists(select 1 from p.deptIdOther od where od.uuid="+deptId+" )) and "+TeeDbUtility.IN("p.uuid", userFilter)+" order by p.userNo asc", null);
			TeePerson p = null;
			for(Map data:datas){
				p = personDao.get(Integer.parseInt(data.get("UUID")+""));
				data.put("uuid", data.get("UUID"));
				data.put("userName", data.get("USERNAME"));
				int isHave =personService.selectOnlineByUserId(TeeStringUtil.getInteger(data.get("UUID"), 0));
				if(isHave!=0){
					data.put("userOnlineStatus", 1);
				}else{
					data.put("userOnlineStatus", 0);
				}
				if(p.getUserRole()!=null){
					data.put("userRoleStrName", p.getUserRole().getRoleName());
				}
				if(p.getDept()!=null){
					data.put("deptIdName", p.getDept().getDeptName());
				}
				data.put("orgName", deptService.getCurrentPersonOrgName(p));
			}
			
			json.setRtState(true);
			json.setRtData(datas);
			
			return json;
		}
		
		
		public TeeJson getSelectUserByUserIdOrUserNameWorkflow(HttpServletRequest request){
			TeeJson json = new TeeJson();
			
			String key = TeeStringUtil.getString(request.getParameter("user"));
			
			TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			loginPerson = personService.selectByUuid(loginPerson.getUuid());
			
			int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
			int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
			
			TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
		    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		    
			
			Map personData = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
			String userFilter = TeeStringUtil.getString(personData.get("filterPersonIds"));
			
			List<Map> datas = simpleDaoSupport.getMaps("select p.uuid as UUID,p.userName as USERNAME from TeePerson p where (p.userId like '%"+TeeDbUtility.formatString(key)+"%' or p.userName like '%"+TeeDbUtility.formatString(key)+"%') and "+TeeDbUtility.IN("p.uuid", userFilter)+" order by p.userNo asc", null);
			TeePerson p = null;
			for(Map data:datas){
				p = personDao.get(Integer.parseInt(data.get("UUID")+""));
				data.put("uuid", data.get("UUID"));
				data.put("userName", data.get("USERNAME"));
				int isHave =personService.selectOnlineByUserId(TeeStringUtil.getInteger(data.get("UUID"), 0));
				if(isHave!=0){
					data.put("userOnlineStatus", 1);
				}else{
					data.put("userOnlineStatus", 0);
				}
				if(p.getUserRole()!=null){
					data.put("userRoleStrName", p.getUserRole().getRoleName());
				}
				if(p.getDept()!=null){
					data.put("deptIdName", p.getDept().getDeptName());
				}
				data.put("orgName", deptService.getCurrentPersonOrgName(p));
			}
			
			json.setRtState(true);
			json.setRtData(datas);
			
			return json;
		}
		
		
		public TeeJson getSelectUserByCurrentDeptWorkflow(HttpServletRequest request){
			TeeJson json = new TeeJson();
			
			TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			loginPerson = personService.selectByUuid(loginPerson.getUuid());
			
			int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
			int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
			
			TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
		    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		    
			
			Map personData = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
			String userFilter = TeeStringUtil.getString(personData.get("filterPersonIds"));
			
			List<Map> datas = simpleDaoSupport.getMaps("select p.uuid as UUID,p.userName as USERNAME from TeePerson p where p.dept.uuid="+loginPerson.getDept().getUuid()+" and "+TeeDbUtility.IN("p.uuid", userFilter)+" order by p.userNo asc", null);
			for(Map data:datas){
				data.put("uuid", data.get("UUID"));
				data.put("userName", data.get("USERNAME"));
				int isHave =personService.selectOnlineByUserId(TeeStringUtil.getInteger(data.get("UUID"), 0));
				if(isHave!=0){
					data.put("userOnlineStatus", 1);
				}else{
					data.put("userOnlineStatus", 0);
				}
			}
			
			json.setRtState(true);
			json.setRtData(datas);
			
			return json;
		}
		
		
		/**
		 * 查询在线人员 --工作流选人
		 * @author syl
		 * @date 2014-2-23
		 * @param request
		 * @return
		 */
		public TeeJson getUserOnlineWorkFlow(HttpServletRequest request){
		    TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		    TeeDepartment loginPresonDept = loginPerson.getDept();
		    TeeDepartmentModel loginPresonDeptModel =  new TeeDepartmentModel();//当前部门模版
		    int loginPresonDeptId = -1;
		    if(loginPresonDept != null){
		    	loginPresonDeptId = loginPresonDept.getUuid();
		    	BeanUtils.copyProperties(loginPresonDept, loginPresonDeptModel );
		    }
		    TeeJson json = new TeeJson();
		    String userFilter = switchUserFilter(request.getParameter("userFilter"));
		
			String moduleId = request.getParameter("moduleId");
			String privNoFlag = TeeStringUtil.getString(request.getParameter("privNoFlag"),"");
			List<TeePerson> personList =personDao.selectOnlinePersonUserFilterToWorkFlow(0, userFilter, loginPerson, privNoFlag);
			List<TeePersonModel> modelList = new ArrayList<TeePersonModel>();
			for (int i = 0; i < personList.size(); i++) {
				TeePersonModel personModel = new TeePersonModel();
				BeanUtils.copyProperties(personList.get(i), personModel);
				personModel.setUserOnlineStatus("1");
				modelList.add(personModel);
			}
			json.setRtState(true);
			json.setRtData(modelList);
			return json;
		}
		
	
	/*工作流选人end*/
	
	
	
	
	
	
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

	public void setDeptDao(TeeDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setOrgDao(TeeOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setRoleDao(TeeUserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setMenuGroupDao(TeeMenuGroupDao menuGroupDao) {
		this.menuGroupDao = menuGroupDao;
	}



	public void setModulePrivServ(TeeModulePrivService modulePrivServ) {
		this.modulePrivServ = modulePrivServ;
	}



	public void setUserGroupDao(TeeUserGroupService userGroupDao) {
		this.userGroupDao = userGroupDao;
	}



	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}

	
	public String switchUserFilter(String userFilter){
		if(userFilter==null){
			return "0";
		}
		userFilter = userFilter.replace(" ", "+");
		//将原字符串压缩两次
		return TeeStrZipUtil.totalDecompress(userFilter, 2);
		
	}
	
	/**
	 * 异步加载树
	 * @param deptId
	 * @param LoginPerson
	 * @return
	 */
	public TeeJson selectOrgTreeAll(String deptId, TeePerson LoginPerson) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>();
		try {
			if (!TeeUtility.isNullorEmpty(deptId)) {
				String postDeptIds = personService.getLoginPersonPostDept(LoginPerson);// 获取系统当前登录人管理范围部门Id字符串
				String[] idArray = deptId.split(";");
				if (idArray.length >= 2) {
					Object[] values = { Integer.parseInt(idArray[0]) };
					if (idArray[1].equals("dept")) {// 部门
						List<TeeDepartment> list = deptDao
								.selectDept(
										"from TeeDepartment  where deptParent.uuid = ? ",
										values);
						for (int i = 0; i < list.size(); i++) {
							TeeDepartment dept = list.get(i);
							boolean isParent = false;
							if (deptDao.checkExists(dept)) {// 如果存在下级获取用户
								isParent = true;
							}
							TeeZTreeModel ztree = new TeeZTreeModel(
									dept.getUuid() + ";dept",
									dept.getDeptName(), isParent, deptId, true,
									"deptNode", "");

							orgDeptList.add(ztree);

						}
					} else if (idArray[1].equals("person")) {// 人员不处理

					}

				} else {// 上级是单位获取所有是第一级的部门
					List<TeeDepartment> list = deptDao.getFisrtDept();
					for (int i = 0; i < list.size(); i++) {
						TeeDepartment dept = list.get(i);
						boolean isParent = false;
						if (deptDao.checkExists(dept)) {// 如果存在下级部门
							isParent = true;
						}
						TeeZTreeModel ztree = new TeeZTreeModel(dept.getUuid()
								+ ";dept", dept.getDeptName(), isParent,
								deptId, true, "deptNode", "");
						orgDeptList.add(ztree);
					}
				}
			} else {// 获取单位
				List<TeeOrganization> list = orgDao.traversalOrg();
				if (list.size() > 0) {
					TeeOrganization org = list.get(0);
					TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "",
							org.getUnitName(), true, "0", true, "pIconHome", "");
					orgDeptList.add(ztree);
				}
			}
			json.setRtMsg("获取成功");
			json.setRtData(orgDeptList);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			json.setRtMsg(e.getMessage());
			json.setRtState(false);
		}

		return json;
	}

	
	/**
	 * 获取当前登陆人有管理权限的部门树
	 * @param request
	 * @param moduleId
	 * @return
	 */
	public TeeJson getSelectDeptTreePost(HttpServletRequest request,
			String modelId) {
		TeeJson json = new TeeJson();
		TeePerson loginUser  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部  空为默认
		String isSingle = TeeStringUtil.getString(request.getParameter("isSingle"), "0");
		
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
		List<TeeOrganization> list = orgDao.traversalOrg();
		if (list.size() > 0) {
			TeeOrganization org = list.get(0);
			boolean isSingleType = false;
			if(isSingle.equals("1")){
				isSingleType = true;
			}
			TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + ";org", org.getUnitName(), true, "0", true, "pIconHome" , "" , false, false , isSingleType);
			orgDeptList.add(ztree);
			List<TeeDepartment>  deptList=null;
			
			
			if (TeePersonService.checkIsSuperAdmin(loginUser, "")) {
				deptList=deptDao.getAllDeptSimple();
			}else{
				if (loginUser.getPostPriv() == 0) {// 本部门
					deptList=new ArrayList<TeeDepartment>();
					deptList.add(loginUser.getDept());
				} else if (loginUser.getPostPriv() == 1) {// 所有
					deptList=deptDao.getAllDeptSimple();
				} else if (loginUser.getPostPriv() == 2) {// 指定部门
					deptList = loginUser.getPostDept();
					
				} else if (loginUser.getPostPriv() == 3) {// 本部以及下级所有部门
					String level = loginUser.getDept().getUuid() + ",";
					if (!TeeUtility
							.isNullorEmpty(loginUser.getDept().getDeptParentLevel())) {// 如果不是第一级部门
						level = loginUser.getDept().getDeptParentLevel();
					} else {// 如果是第一级部门，则把uuID当做级别
						level = loginUser.getDept().getGuid();
					}
					deptList = deptDao
							.getAllChildDeptByLevlSimple(level);//下级部门
					deptList.add(loginUser.getDept());//本部门
				}else if (loginUser.getPostPriv() == 4) {// 本机构
					deptList = deptService.getDeptsByLoginUser(loginUser);
				}
				
			}
			

			boolean isAllDept = false;

			Map idMap = new HashMap();
			for (int i = 0; i < deptList.size(); i++) {
				idMap.put(deptList.get(i).getUuid()+"", "");
			}
			String parentId = "";
			TeeDepartment parentDept = null;
			boolean nocheck = false;
			TeeDepartment dept = null;
			TeeZTreeModel deptZtree = null;
			for (int i = 0; i < deptList.size(); i++) {
				parentId = "";
				dept = deptList.get(i);
				nocheck = false;//不显示选择框
				parentDept = dept.getDeptParent();
				
				if(parentDept !=  null){
					parentId = parentDept.getUuid() + "";
				}
				
				deptZtree = new TeeZTreeModel();
				deptZtree.setId(dept.getUuid()+ "");
				deptZtree.setName(dept.getDeptName());
				deptZtree.setParent(false);
				deptZtree.setpId(parentId);
				if(!idMap.containsKey(parentId)){
					deptZtree.setpId(org.getSid() + ";org");
				}
				deptZtree.setOnRight(false);
				deptZtree.setOpen(false);
				deptZtree.setIconSkin("deptNode");
				if(dept.getDeptType()==2){
					deptZtree.setIconSkin("pIconHome");
				}
				deptZtree.setChecked(false);
				deptZtree.setNocheck(nocheck);
				orgDeptList.add(deptZtree);
			}
		}
		json.setRtData(orgDeptList);
		json.setRtState(true);
		return json;
	}

	public List<Map> selectPostDeptByDeptName(HttpServletRequest request) {
		List<Map> pList = new ArrayList<Map>();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String deptInfo = request.getParameter("dept");
		
		//获取当前登陆人有管理权限的部门
		String postDeptIds=getLoginPersonPostDept(loginPerson);
		
		List<TeeDepartment> list = deptDao.selectPostDeptByDeptName(deptInfo,postDeptIds);
		for (int i = 0; i < list.size(); i++) {
			TeeDepartment dept = list.get(i);
			
			Map map = new HashMap();
			map.put("uuid", dept.getUuid() );
			map.put("deptName", dept.getDeptName());
			pList.add(map);
		}
		return pList;
	}

	
	/**
	 * 根据系统当前登录人获取管理范围(deptId字符串)
	 * 
	 * @param person
	 * @return
	 */
	public String getLoginPersonPostDept(TeePerson person) {
		String deptIds = "";
		if (TeePersonService.checkIsSuperAdmin(person, "")) {
			return "0";
		}
		if (person.getPostPriv() == 0) {// 本部门
			return person.getDept().getUuid() + "";
		} else if (person.getPostPriv() == 1) {// 所有
			return "0";
		} else if (person.getPostPriv() == 2) {// 指定部门
			List<TeeDepartment> deptList = person.getPostDept();
			for (int i = 0; i < deptList.size(); i++) {
				deptIds = deptIds + deptList.get(i).getUuid() + ",";
			}
		} else if (person.getPostPriv() == 3) {// 本部以及下级所有部门
			deptIds=person.getDept().getUuid()+",";//本部门
			String level = person.getDept().getUuid() + ",";
			if (!TeeUtility
					.isNullorEmpty(person.getDept().getDeptParentLevel())) {// 如果不是第一级部门
				level = person.getDept().getDeptParentLevel();
			} else {// 如果是第一级部门，则把uuID当做级别
				level = person.getDept().getGuid();
			}
			List<TeeDepartment> deptChildList = deptDao
					.getAllChildDeptByLevl(level);
			for (int i = 0; i < deptChildList.size(); i++) {
				deptIds = deptIds + deptChildList.get(i).getUuid() + ",";
			}
		}else if (person.getPostPriv() == 4) {// 本机构
			List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(person);
			for (int i = 0; i < deptList.size(); i++) {
				deptIds = deptIds + deptList.get(i).getUuid() + ",";
			}
		}
		if (deptIds.endsWith(",")) {
			deptIds = deptIds.substring(0, deptIds.length() - 1);
		}
		return deptIds;
	}

	public TeeJson getPersonByUserGroupWorkFlow(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String userGroupId = request.getParameter("userGroupId");
		
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginPerson = personService.selectByUuid(loginPerson.getUuid());
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);//步骤ID
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);//frpSid
		
		//根据步骤id获取当前步骤的有条件的人员ids
		    
		TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
		TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		    
		Map data = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
		String userFilter=TeeStringUtil.getString(data.get("filterPersonIds"));
		
		
		TeeUserGroup  group=(TeeUserGroup) simpleDaoSupport.get(TeeUserGroup.class,TeeStringUtil.getInteger(userGroupId,0));
		List<TeePerson> userList=null;
		if(group!=null){
			userList=group.getUserList();
		}
		
		if(userList!=null&&userList.size()>0){
			userList = (List<TeePerson>) simpleDaoSupport.filteredList(userList, "where "+TeeDbUtility.IN("uuid", userFilter), null);
		}
		List<Map> pList = new ArrayList<Map>();
		
		Map m=null;
		int isHave = 0;
		if(userList!=null&&userList.size()>0){
			for (TeePerson p : userList) {
				m=new HashMap();
			    m.put("uuid",p.getUuid());
			    m.put("userName",p.getUserName());
			    isHave=personService.selectOnlineByUserId(p.getUuid());
				if(isHave!=0){
					m.put("userOnlineStatus","1");
				}else{
					m.put("userOnlineStatus","0");
				}
				if(p.getUserRole()!=null){
					m.put("userRoleStrName", p.getUserRole().getRoleName());
				}
				if(p.getDept()!=null){
					m.put("deptIdName", p.getDept().getDeptName());
				}
				m.put("orgName", deptService.getCurrentPersonOrgName(p));
				pList.add(m);
			}
		}
		
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		
		return json;
	}

	
	
	/**
	 * 获取工作流程有权限办理的人员列表
	 * @param request
	 * @return
	 */
	public TeeJson getSelectUserWorkFlow(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginPerson = personService.selectByUuid(loginPerson.getUuid());
		
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		
		TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
	    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
	    
		
		Map personData = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
		String userFilter = TeeStringUtil.getString(personData.get("filterPersonIds"));
		
		List<Map> datas = simpleDaoSupport.getMaps("select p.uuid as UUID,p.userName as USERNAME from TeePerson p where  "+TeeDbUtility.IN("p.uuid", userFilter)+" order by p.userNo asc", null);
		TeePerson p = null;
		for(Map data:datas){
			p = personDao.get(Integer.parseInt(data.get("UUID")+""));
			data.put("uuid", data.get("UUID"));
			data.put("userName", data.get("USERNAME"));
			int isHave =personService.selectOnlineByUserId(TeeStringUtil.getInteger(data.get("UUID"), 0));
			if(isHave!=0){
				data.put("userOnlineStatus", 1);
			}else{
				data.put("userOnlineStatus", 0);
			}
			if(p.getUserRole()!=null){
				data.put("userRoleStrName", p.getUserRole().getRoleName());
			}
			if(p.getDept()!=null){
				data.put("deptIdName", p.getDept().getDeptName());
			}
			data.put("orgName", deptService.getCurrentPersonOrgName(p));
		}
		
		json.setRtState(true);
		json.setRtData(datas);
		
		return json;
	}
	
	
}
