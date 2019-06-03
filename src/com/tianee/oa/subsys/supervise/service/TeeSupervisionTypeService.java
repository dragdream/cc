package com.tianee.oa.subsys.supervise.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;
import com.tianee.oa.subsys.supervise.bean.TeeSupervisionType;
import com.tianee.oa.subsys.supervise.model.TeeSupervisionTypeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSupervisionTypeService extends TeeBaseService{
   
	/**
	 * 根据分类主键获取分类详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的分类的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeSupervisionType type=(TeeSupervisionType) simpleDaoSupport.get(TeeSupervisionType.class,sid);
			TeeSupervisionTypeModel model=parseToModel(type);
			json.setRtState(true);
			json.setRtData(model);
			json.setRtMsg("数据获取成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}

	
	
	/**
	 * 将分类转换成model
	 * @param type
	 * @return
	 */
	private TeeSupervisionTypeModel parseToModel(TeeSupervisionType type) {
		TeeSupervisionTypeModel model=new TeeSupervisionTypeModel();
		if(type!=null){
			BeanUtils.copyProperties(type,model);
			if(type.getParent()!=null){
				model.setParentTypeName(type.getParent().getTypeName());
				model.setParentTypeSid(type.getParent().getSid());
			}
		    //人员权限
			String userNames="";
			String userIds="";
			for (TeePerson person : type.getUsers()) {
					userNames+=person.getUserName()+",";
					userIds+=person.getUuid()+",";
				
			}
			if(userNames.endsWith(",")){
				userNames=userNames.substring(0,userNames.length()-1);
			}
			if(userIds.endsWith(",")){
				userIds=userIds.substring(0,userIds.length()-1);
			}
			 //部门权限
			String deptNames="";
			String deptIds="";
			for (TeeDepartment dept : type.getDepts()) {
					deptNames+=dept.getDeptName()+",";
					deptIds+=dept.getUuid()+",";
			}
			if(deptNames.endsWith(",")){
				deptNames=deptNames.substring(0,deptNames.length()-1);
			}
			if(deptIds.endsWith(",")){
				deptIds=deptIds.substring(0,deptIds.length()-1);
			}
			//角色
			String roleNames="";
			String roleIds="";
			for (TeeUserRole role : type.getRoles()) {
					roleNames+=role.getRoleName()+",";
					roleIds+=role.getUuid()+",";
			}
			if(roleNames.endsWith(",")){
				roleNames=roleNames.substring(0,roleNames.length()-1);
			}
			if(roleIds.endsWith(",")){
				roleIds=roleIds.substring(0,roleIds.length()-1);
			}
			
			model.setDeptIds(deptIds);
			model.setDeptName(deptNames);
			model.setUserIds(userIds);
			model.setUserNames(userNames);
			model.setRoleIds(roleIds);
			model.setRoleNames(roleNames);
			
		}
		
		return model;
	}



	/**
	 * 新增/编辑
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeSupervisionTypeModel model) {
		TeeJson json=new TeeJson();
		if(model.getSid()>0){//编辑
			TeeSupervisionType type=parseToSupType(model);
			simpleDaoSupport.update(type);
			json.setRtState(true);
			json.setRtMsg("编辑成功！");
		}else{//新增
			TeeSupervisionType type=parseToSupType(model);
			simpleDaoSupport.save(type);
			json.setRtState(true);
			json.setRtMsg("新建成功！");
		}	
		return json;
	}



	/**
	 * 将model类转换成实体类
	 * @param model
	 * @return
	 */
	private TeeSupervisionType parseToSupType(TeeSupervisionTypeModel model) {
		TeeSupervisionType type=null;
		if(model.getSid()>0){//编辑
			type=(TeeSupervisionType) simpleDaoSupport.get(TeeSupervisionType.class,model.getSid());
			BeanUtils.copyProperties(model,type);
			
			
			TeeSupervisionType parent=(TeeSupervisionType) simpleDaoSupport.get(TeeSupervisionType.class,model.getParentTypeSid());
		    type.setParent(parent);
		    
		    if(!("").equals(model.getUserIds())){
		    	String[] userIdArray=model.getUserIds().split(",");
		        TeePerson user=null;
		        type.getUsers().clear();
		    	for (String str : userIdArray) {
					int userId=TeeStringUtil.getInteger(str,0);
					user=(TeePerson) simpleDaoSupport.get(TeePerson.class,userId);
					type.getUsers().add(user);
				}
		    }else{
		    	type.getUsers().clear();
		    }
		
		    if(!("").equals(model.getDeptIds())){
		    	String[] deptIdArray=model.getDeptIds().split(",");
		        TeeDepartment dept=null;
		        type.getDepts().clear();
		    	for (String str : deptIdArray) {
					int deptId=TeeStringUtil.getInteger(str,0);
					dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,deptId);
					type.getDepts().add(dept);
				}
		    }else{
		    	 type.getDepts().clear();
		    }
		    
		    if(!("").equals(model.getRoleIds())){
		    	String[] roleIdArray=model.getRoleIds().split(",");
		        TeeUserRole role=null;
		        type.getRoles().clear();
		    	for (String str : roleIdArray) {
					int roleId=TeeStringUtil.getInteger(str,0);
					role=(TeeUserRole) simpleDaoSupport.get(TeeUserRole.class,roleId);
					type.getRoles().add(role);
				}
		    }else{
		    	type.getRoles().clear();
		    }
		
		}else{
			type=new TeeSupervisionType();
			BeanUtils.copyProperties(model,type);
			TeeSupervisionType parent=(TeeSupervisionType) simpleDaoSupport.get(TeeSupervisionType.class,model.getParentTypeSid());
		    type.setParent(parent);
		    
		    if(!("").equals(model.getUserIds())){
		    	String[] userIdArray=model.getUserIds().split(",");
		        TeePerson user=null;
		        type.getUsers().clear();
		    	for (String str : userIdArray) {
					int userId=TeeStringUtil.getInteger(str,0);
					user=(TeePerson) simpleDaoSupport.get(TeePerson.class,userId);
					type.getUsers().add(user);
				}
		    }else{
		    	 type.getUsers().clear();
		    }
		
		    if(!("").equals(model.getDeptIds())){
		    	String[] deptIdArray=model.getDeptIds().split(",");
		        TeeDepartment dept=null;
		        type.getDepts().clear();
		    	for (String str : deptIdArray) {
					int deptId=TeeStringUtil.getInteger(str,0);
					dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,deptId);
					type.getDepts().add(dept);
				}
		    }else{
		    	 type.getDepts().clear();
		    }
		    
		    if(!("").equals(model.getRoleIds())){
		    	String[] roleIdArray=model.getRoleIds().split(",");
		        TeeUserRole role=null;
		        type.getRoles().clear();
		    	for (String str : roleIdArray) {
					int roleId=TeeStringUtil.getInteger(str,0);
					role=(TeeUserRole) simpleDaoSupport.get(TeeUserRole.class,roleId);
					type.getRoles().add(role);
				}
		    }else{
		    	type.getRoles().clear();
		    }
		}
		return type;
	}



	/**
	 * 根据当前登录的用户    获取分类列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public List getSupTypeList(HttpServletRequest request) {
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List<TeeSupervisionType> typeList = null;
		//判断当前登录的用户是不是系统管理员
		if(TeePersonService.checkIsAdminPriv(loginUser)){
			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t order by t.orderNum ",new Object[]{});	
		}else{
			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t where ((exists (select 1 from t.users user where user.uuid=?)) or (exists (select 1 from  t.depts dept where dept.uuid=?)) or (exists (select 1 from t.roles role where role.uuid=?)))order by t.orderNum ",new Object[]{loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
		}
		
		List<Map> typeMapList = new ArrayList();
		
		//找出所有一级节点
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeSupervisionTypeModel model=null;
		for (TeeSupervisionType type : typeList) {
			model=parseToModel(type);
			if(type.getParent()==null){
				Map map=new HashMap();
				map.put("sid", model.getSid());
				map.put("typeName", model.getTypeName());
				map.put("userNames",model.getUserNames());
				map.put("roleNames", model.getRoleNames());
				map.put("deptNames", model.getDeptNames());
				map.put("children", new ArrayList());
				map.put("pId", 0);
				typeMapList.add(map);
			}
		}
		//从一级节点开始往下找
		for(Map data:typeMapList){
			setChildInfos(typeList,typeMapList,data,loginUser);
		}
		return typeMapList;
	}



	private void setChildInfos(List<TeeSupervisionType> typeList,
			List<Map> typeMapList, Map typeMap, TeePerson loginUser) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//先获取该节点下面的所有子节点
		List<Map> childList = new ArrayList();
		//将taskMap的所有子节点加入到childList中
		TeeSupervisionTypeModel model=null;
		for(TeeSupervisionType type:typeList){
			if(type.getParent()!=null){	
				if(type.getParent().getSid()==Integer.parseInt(typeMap.get("sid")+"")){
					model=parseToModel(type);
					Map map=new HashMap();
					map.put("sid", model.getSid());
					map.put("typeName", model.getTypeName());
					map.put("userNames", model.getUserNames());
					map.put("roleNames", model.getRoleNames());
					map.put("deptNames", model.getDeptNames());
					map.put("pId", type.getParent().getSid());
					map.put("children", new ArrayList());
					
					childList.add(map);
				}	
			}	
		}
		((List)typeMap.get("children")).addAll(childList);
		
		for(Map data:childList){
			setChildInfos(typeList,typeMapList,data,loginUser);
		}
		
	}



	/**
	 * 删除督办任务分类
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request,
			TeeSupervisionTypeModel model) {
		TeeJson json=new TeeJson();
		//获取页面上传来的分类主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeSupervisionType type=(TeeSupervisionType) simpleDaoSupport.get(TeeSupervisionType.class,sid);
		   //获取该分类下的督办任务
			List<TeeSupervision> supList=simpleDaoSupport.executeQuery(" from TeeSupervision t where t.type.sid=? ", new Object[]{sid});
		   //获取该分类的子分类
			List<TeeSupervisionType>childrenList=simpleDaoSupport.executeQuery(" from TeeSupervisionType t where t.parent.sid=? ", new Object[]{sid});
			
			
			if(childrenList!=null&&childrenList.size()>0){
				json.setRtMsg("该分类下有子分类，暂且不能删除！");
				json.setRtState(false);
			}else{
				
				if(supList!=null&&supList.size()>0){
					json.setRtMsg("该分类下有督办任务，暂且不能删除！");
					json.setRtState(false);
				}else{
					simpleDaoSupport.deleteByObj(type);
					json.setRtState(true);
					json.setRtMsg("删除成功！");
				}
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}


    /**
     * 获取当前人有权限的  并且不包含当前分类的分类列表
     * @param request
     * @param dm
     * @return
     */
	public List getOtherSupTypeList(HttpServletRequest request,
			TeeDataGridModel dm) {
        TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的当前分类的主键
        int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		
        List<TeeSupervisionType> typeList = null;
        if(sid>0){
        	//判断当前登录的用户是不是系统管理员
    		if(TeePersonService.checkIsAdminPriv(loginUser)){
    			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t where t.sid!=?  order by t.orderNum ",new Object[]{sid});	
    		}else{
    			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t where ((exists (select 1 from t.users user where user.uuid=?)) or (exists (select 1 from  t.depts dept where dept.uuid=?)) or (exists (select 1 from t.roles role where role.uuid=?))) and t.sid!=?  order by t.orderNum ",new Object[]{loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid(),sid});
    		}
        }else{
        	//判断当前登录的用户是不是系统管理员
    		if(TeePersonService.checkIsAdminPriv(loginUser)){
    			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t order by t.orderNum ",new Object[]{});	
    		}else{
    			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t where ((exists (select 1 from t.users user where user.uuid=?)) or (exists (select 1 from  t.depts dept where dept.uuid=?)) or (exists (select 1 from t.roles role where role.uuid=?)))order by t.orderNum ",new Object[]{loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
    		}
        }
		
		
		List<Map> typeMapList = new ArrayList();
		
		//找出所有一级节点
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeSupervisionTypeModel model=null;
		for (TeeSupervisionType type : typeList) {
			model=parseToModel(type);
			if(type.getParent()==null){
				Map map=new HashMap();
				map.put("sid", model.getSid());
				map.put("typeName", model.getTypeName());
				map.put("userNames",model.getUserNames());
				map.put("roleNames", model.getRoleNames());
				map.put("deptNames", model.getDeptNames());
				map.put("children", new ArrayList());
				
				typeMapList.add(map);
			}
		}
		//从一级节点开始往下找
		for(Map data:typeMapList){
			setChildInfos(typeList,typeMapList,data,loginUser);
		}
		return typeMapList;
	}

	/**
	 * 获取督办分类树
	 * @param request
	 * @return
	 */
	public TeeJson getSupTypeTree(HttpServletRequest request){
		//获取当前登陆的用户
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取所有有权限的督办分类树
		List<TeeSupervisionType> typeList = null;
		//判断当前登录的用户是不是系统管理员
		if(TeePersonService.checkIsAdminPriv(loginUser)){
			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t order by t.orderNum ",new Object[]{});	
		}else{
			 typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t where ((exists (select 1 from t.users user where user.uuid=?)) or (exists (select 1 from  t.depts dept where dept.uuid=?)) or (exists (select 1 from t.roles role where role.uuid=?)))order by t.orderNum ",new Object[]{loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
		}
		//创建一个树节点对象集合模型
		List<TeeZTreeModel> treeList = new ArrayList();
		
		TeeZTreeModel ztreeModel = null;
		for(TeeSupervisionType type:typeList){
			ztreeModel = new TeeZTreeModel();
			ztreeModel.setId(type.getSid()+"");
			ztreeModel.setName(type.getTypeName());
			if(type.getParent()!=null){
				ztreeModel.setpId(type.getParent().getSid()+"");
			}else{
				ztreeModel.setpId("0");
			}
			treeList.add(ztreeModel);		
		}
		json.setRtState(true);
		json.setRtData(treeList);
		json.setRtMsg("数据获取成功！");
		return json;
	}



	/**
	 * 获取所有的督办任务列表   无权限控制
	 * @param request
	 * @param dm
	 * @return
	 */
	public List getAllSupTypeList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);        
        List<TeeSupervisionType> typeList = simpleDaoSupport.executeQuery(" from TeeSupervisionType t order by t.orderNum ",new Object[]{});	
		
		List<Map> typeMapList = new ArrayList();
		
		//找出所有一级节点
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeSupervisionTypeModel model=null;
		for (TeeSupervisionType type : typeList) {
			model=parseToModel(type);
			if(type.getParent()==null){
				Map map=new HashMap();
				map.put("sid", model.getSid());
				map.put("typeName", model.getTypeName());
				map.put("userNames",model.getUserNames());
				map.put("roleNames", model.getRoleNames());
				map.put("deptNames", model.getDeptNames());
				map.put("children", new ArrayList());
				
				typeMapList.add(map);
			}
		}
		//从一级节点开始往下找
		for(Map data:typeMapList){
			setChildInfos(typeList,typeMapList,data,loginUser);
		}
		return typeMapList;
	}
	
	
}
