package com.tianee.oa.core.base.applicationSystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.tianee.oa.core.base.applicationSystem.bean.TeeApplicationSystemMaintain;
import com.tianee.oa.core.base.applicationSystem.dao.TeeApplicationSystemMaintainDao;
import com.tianee.oa.core.base.applicationSystem.model.TeeApplicationSystemMaintainModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserRoleService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeApplicationSystemMaintainService extends TeeBaseService{

	@Autowired
	private TeeApplicationSystemMaintainDao appSystemDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeDeptService deptService;

	@Autowired
	private TeeUserRoleService userRoleService;
	
	
	/**
	 * 根据主键获取对象
	 * @return
	 */
	public TeeApplicationSystemMaintain getById(int sid){
		TeeApplicationSystemMaintain sys=(TeeApplicationSystemMaintain) simpleDaoSupport.get(TeeApplicationSystemMaintain.class,sid);
		return sys;
	}
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeApplicationSystemMaintain sys=(TeeApplicationSystemMaintain) simpleDaoSupport.get(TeeApplicationSystemMaintain.class,sid);
		TeeApplicationSystemMaintainModel  model=parseToModel(sys);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}

	
	/**
	 * 将实体类转换成model
	 * @param sys
	 * @return
	 */
	private TeeApplicationSystemMaintainModel parseToModel(
			TeeApplicationSystemMaintain sys) {
		TeeApplicationSystemMaintainModel model=new TeeApplicationSystemMaintainModel();
		BeanUtils.copyProperties(sys, model);
		
		//处理人员权限
		String userIds="";
		String userNames="";
		if(sys.getUserList()!=null&&sys.getUserList().size()>0){
		   for (TeePerson p: sys.getUserList()) {
			   userIds+=p.getUuid()+",";
			   userNames+=p.getUserName()+",";
		    }
		}
		if(userIds.endsWith(",")){
			userIds=userIds.substring(0,userIds.length()-1);
		}
		
		if(userNames.endsWith(",")){
			userNames=userNames.substring(0,userNames.length()-1);
		}
		model.setUserIds(userIds);
		model.setUserNames(userNames);
		//处理部门权限
		String deptIds="";
		String deptNames="";
		if(sys.getDeptList()!=null&&sys.getDeptList().size()>0){
			   for (TeeDepartment d: sys.getDeptList()) {
				   deptIds+=d.getUuid()+",";
				   deptNames+=d.getDeptName()+",";
			    }
		}
		if(deptIds.endsWith(",")){
			deptIds=deptIds.substring(0,deptIds.length()-1);
		}
		
		if(deptNames.endsWith(",")){
			deptNames=deptNames.substring(0,deptNames.length()-1);
		}
		model.setDeptIds(deptIds);
		model.setDeptNames(deptNames);
		
		//处理角色权限
		String roleIds="";
		String roleNames="";
		if(sys.getRoleList()!=null&&sys.getRoleList().size()>0){
			for (TeeUserRole role : sys.getRoleList()) {
				roleIds+=role.getUuid()+",";
				roleNames+=role.getRoleName()+",";
			}
		}
		if(roleIds.endsWith(",")){
			roleIds=roleIds.substring(0,roleIds.length()-1);
		}
		
		if(roleNames.endsWith(",")){
			roleNames=roleNames.substring(0,roleNames.length()-1);
		}
		model.setRoleIds(roleIds);
		model.setRoleNames(roleNames);
		
		return model;
	}


	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String systemName=TeeStringUtil.getString(request.getParameter("systemName"));
		String url=TeeStringUtil.getString(request.getParameter("url"));
		//人员权限
		String userIds=TeeStringUtil.getString(request.getParameter("userIds"));
        List<TeePerson> userList=personService.getPersonByUuids(userIds);
		//部门权限
		String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
		List<TeeDepartment> deptList=deptService.getDeptByUuids(deptIds);
		//角色权限
		String roleIds=TeeStringUtil.getString(request.getParameter("roleIds"));
		List<TeeUserRole> roleList=userRoleService.getUserRoleByUuids(roleIds);
		TeeApplicationSystemMaintain sys=null;
		if(sid>0){//编辑
			sys=(TeeApplicationSystemMaintain) simpleDaoSupport.get(TeeApplicationSystemMaintain.class,sid);
			sys.setSystemName(systemName);
			sys.setUrl(url);
			sys.getUserList().clear();
			sys.setUserList(userList);
			sys.getDeptList().clear();
			sys.setDeptList(deptList);
			sys.getRoleList().clear();
			sys.setRoleList(roleList);
			simpleDaoSupport.saveOrUpdate(sys);
			
		}else{//新建
			sys=new TeeApplicationSystemMaintain();
			sys.setSystemName(systemName);
			sys.setUrl(url);
			sys.setUserList(userList);
			sys.setDeptList(deptList);
			sys.setRoleList(roleList);
			simpleDaoSupport.saveOrUpdate(sys);
		}
		json.setRtState(true);
		return json;
	}


	
	/**
	 * 根据主键进行批量删除
	 * @param request
	 * @return
	 */
	public TeeJson delByIds(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台传来的ids
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		
		//清空菜单关联的信息
		String hql1=" update TeeSysMenu set sys=null where sys!=null and  "+TeeDbUtility.IN("sys.sid", ids);
		simpleDaoSupport.executeUpdate(hql1, null);
		//删除
		String hql=" delete from TeeApplicationSystemMaintain where "+TeeDbUtility.IN("sid", ids);
		simpleDaoSupport.executeUpdate(hql,null);
		json.setRtState(true);
		return json;
	}


	
	/**
	 * 获取列表信息分页
	 * @param dm
	 * @param response
	 * @return
	 */
	public TeeEasyuiDataGridJson getList(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        String hql=" from  TeeApplicationSystemMaintain where 1=1 ";
		
        j.setTotal(simpleDaoSupport.count("select count(sid) "+hql, null));
        
        List<TeeApplicationSystemMaintain> sysList=simpleDaoSupport.pageFind(hql+" order by sid asc", (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
        List<TeeApplicationSystemMaintainModel> modelList=new ArrayList<TeeApplicationSystemMaintainModel>();
        TeeApplicationSystemMaintainModel model=null;
        if(sysList!=null&&sysList.size()>0){
        	for (TeeApplicationSystemMaintain ss : sysList) {
				model=parseToModel(ss);
				modelList.add(model);
			}
        }
        j.setRows(modelList);
		return j;
	}


	
	/**
	 * 获取列表信息 不分页
	 * @param request
	 * @return
	 */
	public TeeJson getAll(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		 String hql=" from  TeeApplicationSystemMaintain where 1=1 "; 
        List<TeeApplicationSystemMaintain> sysList=simpleDaoSupport.executeQuery(hql+" order by sid asc",null);
        List<TeeApplicationSystemMaintainModel> modelList=new ArrayList<TeeApplicationSystemMaintainModel>();
        TeeApplicationSystemMaintainModel model=null;
        if(sysList!=null&&sysList.size()>0){
        	for (TeeApplicationSystemMaintain ss : sysList) {
				model=parseToModel(ss);
				modelList.add(model);
			}
        }
        json.setRtState(true);
        json.setRtData(modelList);
		return json;
	}

	
	
	/**
	 * 获取当前登陆人有权限的系统应用列表
	 * @param request
	 * @return
	 */
	public TeeJson getPrivListByLoginUser(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人的信息
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		
		
		/*
		String hql=" from TeeApplicationSystemMaintain sys where "
				+ " (exists (select 1 from sys.userList user where user.uuid=?)) "
				+ " or (exists (select 1 from sys.deptList dept where dept.uuid=?))"
				+ " or (exists (select 1 from sys.roleList role where role.uuid=?))";
		
		List<TeeApplicationSystemMaintain> list=simpleDaoSupport.executeQuery(hql, new Object[]{loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});

		TeeApplicationSystemMaintainModel model=null;
		List<TeeApplicationSystemMaintainModel> modelList=new ArrayList<TeeApplicationSystemMaintainModel>();
		if(list!=null&&list.size()>0){
			for (TeeApplicationSystemMaintain s : list) {
				model=parseToModel(s);
				modelList.add(model);
			}
		}
		*/
		
		List<TeeMenuGroup> groupList = loginUser.getMenuGroups();
		Map<Integer,TeeApplicationSystemMaintain> systemMap = new HashMap<Integer,TeeApplicationSystemMaintain>();
		for(TeeMenuGroup group : groupList) {
			List<TeeApplicationSystemMaintain> sysems = group.getAppSystems();
			if(sysems!=null) {
				for(TeeApplicationSystemMaintain sys: sysems) {
					systemMap.put(sys.getSid(), sys);
				}
			}
		}
		List<TeeApplicationSystemMaintainModel> modelList = new ArrayList<TeeApplicationSystemMaintainModel>();
		for (Map.Entry<Integer, TeeApplicationSystemMaintain> entry : systemMap.entrySet()) { 
			TeeApplicationSystemMaintainModel model=parseToModel(entry.getValue());
			modelList.add(model);
		}
		//
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

	public List<TeeApplicationSystemMaintain> selectAppSystemList(){
		return appSystemDao.selectAppSystemList();
	}
	
	public List<TeeApplicationSystemMaintain> selectSystem(String hql, Object[] values) {
		List<TeeApplicationSystemMaintain> list = appSystemDao.executeQuery(hql, values);
		return list;
	}


}
