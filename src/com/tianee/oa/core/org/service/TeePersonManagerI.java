package com.tianee.oa.core.org.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeeOrgDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.priv.dao.TeeModulePrivDao;
import com.tianee.oa.core.priv.model.TeeModulePrivModel;
import com.tianee.oa.core.priv.service.TeeModulePrivService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeePersonManagerI  extends TeeBaseService{

	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeUserRoleDao roleDao;
	
	@Autowired
	private TeeModulePrivDao modelPrivDao;
	
	@Autowired
	private TeeOrgDao orgDao;
	
	@Autowired
	private TeeModulePrivService modulePrivService;
	

	/**
	 * 根据 人员  、 部门、 角色 Id字符串  获取所有人员 --无权限
	 * @author syl
	 * @date 2014-3-11
	 * @param loginUser
	 * @param uuids
	 * @param deptIds
	 * @param roleIds
	 * @return
	 */
	public List<TeePerson> getPersonByUuidsOrDeptIdsOrRoleIds(TeePerson loginUser , String uuids , String deptIds , String roleIds){
		 List<TeePerson> list = personDao.getPersonsByUuidsOrDeptIdsOrRoleIds(uuids, deptIds, roleIds);
		 return list;
	}
	

	/**
	 * 获取管理人员ID字符串范围 -- 按模块范围
	 * @param loginUser
	 * @param modelId
	 * @return
	 */
	public TeeManagerPostPersonDataPrivModel getManagerPostPersonIdsPriv(Map map,  String modelId , String userFilter){
		TeePerson loginUser  = (TeePerson) map.get(TeeConst.LOGIN_USER);
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部 空为默认
		TeeManagerPostPersonDataPrivModel dataPrivModel = new TeeManagerPostPersonDataPrivModel();
		
		String queryUserColumn = TeeStringUtil.getString(map.get("queryUserColumn"), "uuid");//查询用户类型 0 - 查询用户uuid（sid） 1-查询 用户userId
		
		List<Integer> list = new ArrayList<Integer>();//uuid
		List<String> list2 = new ArrayList<String>();//userId
		dataPrivModel.setPrivType("");
	    if(modelIdStr.equals(0)){
	    	dataPrivModel.setPrivType("ALL");
	    }else if(modelIdStr.equals("")){
	    	dataPrivModel.setPrivType("ALL");
	    }else{
	    	TeeModulePrivModel model = null;
			if(TeeUtility.isInteger(modelIdStr)){
				//如果存在按模块设置
				model = modulePrivService.selectPrivByUserId(loginUser.getUuid() + "", modelIdStr);
			}
			String andHQL = getManagerPostPrivHqlStrByPersonPostPrivAndModulePriv(loginUser, model );
			if(andHQL.equals("")){//所有
				dataPrivModel.setPrivType("ALL");
			}else if(andHQL.equals("0")){//空
				dataPrivModel.setPrivType("0");
			}else{
				 
				 TeeUserRole role = loginUser.getUserRole();
				 if(role == null){
			    	dataPrivModel.setPrivType("0");
				 }else{
					 Object[] values = {};
					 String hql  = "select "+queryUserColumn+" from TeePerson where  deleteStatus <> '1' "; 
					 
					 if(!TeeUtility.isNullorEmpty(andHQL)){
							hql = hql + andHQL;
				      }
						
					  if(!userFilter.equals("0")){//需要处理
						  if(userFilter.endsWith(",")){
								userFilter = userFilter.substring(0, userFilter.length()-1);
						  }
						  hql = hql +  " and uuid  IN (" + userFilter + ") ";
					  }
					  hql = hql + "order by userRole.roleNo ,userNo";
					
					  if(queryUserColumn.equals("userId")){//查询userId字段
						  list2 = (List<String>) simpleDaoSupport.executeQuery(hql, values);
					  }else{
						  list = (List<Integer>) simpleDaoSupport.executeQuery(hql, values);
					  }
				 }
			}
	    }
	    list.add(loginUser.getUuid());//要加入自己的
	    list2.add(loginUser.getUserId());
	    dataPrivModel.setPersonIds(list);
	    dataPrivModel.setPersonUserIds(list2);
	    return dataPrivModel;
	}

	/**
	 * 获取管理人员范围 -- 按模块范围
	 * @param loginUser
	 * @param modelId
	 * @return
	 */
	public TeeManagerPostPersonDataPrivModel getManagerPostPersonPriv(Map map,  String modelId , String userFilter){
		TeePerson loginUser  = (TeePerson) map.get(TeeConst.LOGIN_USER);
	
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部 空为默认
		TeeManagerPostPersonDataPrivModel dataPrivModel = new TeeManagerPostPersonDataPrivModel();
		List<TeePerson>  list = new ArrayList<TeePerson> ();
		dataPrivModel.setPrivType("");
	    if(modelIdStr.equals(0)){
	    	dataPrivModel.setPrivType("ALL");
	    }else if(modelIdStr.equals("")){
	    	dataPrivModel.setPrivType("ALL");
	    }else{
	    	TeeModulePrivModel model = null;
			if(TeeUtility.isInteger(modelIdStr)){
				//如果存在按模块设置
				model = modulePrivService.selectPrivByUserId(loginUser.getUuid() + "", modelIdStr);
			}
			String andHQL = getManagerPostPrivHqlStrByPersonPostPrivAndModulePriv(loginUser, model );
			if(andHQL.equals("")){//所有
				dataPrivModel.setPrivType("ALL");
			}else if(andHQL.equals("0")){//空
				dataPrivModel.setPrivType("0");
			}else{
				list = personDao.selectManagerPostPerson( loginUser, model , userFilter ,andHQL);
			}
			
	    }
	    dataPrivModel.setPersonList(list);
	    return dataPrivModel;
	}
	
	
	/**
	 * 根据部门Id管理人员范围 -- 按模块范围
	 * @param request
	 * @param modelId  模块Id
	 * @param userFilter 过滤人员I的字符串  0 -不处理  ;空字符串为无权限 ；其他过滤人员ID字符串
	 * @return
	 * @throws Exception
	 */
	public TeeJson getSelectUserByDept(HttpServletRequest request ,Map map) throws Exception{
		TeeJson json = new TeeJson();
		String  deptId = TeeStringUtil.getString(request.getParameter("deptId"),"0") ;
		try {
			List<TeePerson> list  =  new ArrayList<TeePerson>();
			String moduleId = TeeStringUtil.getString((String)map.get("moduleId"));///模块Id
			String userFilter = TeeStringUtil.getString((String)map.get("userFilter"));//userFilter 过滤人员I的字符串  0 -不处理  ;空字符串为无权限 ；其他过滤人员ID字符串
			list = getManagerPostPersonPrivByDept(request, moduleId, deptId, userFilter);
			List<TeePersonModel> listModel  = new ArrayList<TeePersonModel>();
			String presonIds = "";
			for (int i = 0; i < list.size(); i++) {
				TeePersonModel model = new TeePersonModel();
				BeanUtils.copyProperties(list.get(i),model );
				listModel.add(model);
				presonIds = presonIds + model.getUuid() + ",";
			}
			if(presonIds.endsWith(",")){
				presonIds = presonIds.substring(0, presonIds.length() -1);
			}
			json.setRtMsg(presonIds);
			json.setRtData(listModel);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			System.out.println(ex.getMessage());
		}
		return json;
	}
	
	/**
	 * 根据部门Id管理人员范围 -- 按模块范围
	 * @param loginUser
	 * @param modelId
	 * @return
	 */
	public List<TeePerson> getManagerPostPersonPrivByDept(HttpServletRequest request ,  String modelId , String deptId,String userFilter){
		TeePerson loginUser  = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部 空为默认
		int dept = TeeStringUtil.getInteger(deptId, 0);//部门Id
		List<TeePerson> list = new ArrayList<TeePerson>();
		boolean isAll = false;//是否获取所有的
    	TeeModulePrivModel model = null;
	    if(modelIdStr.equals(0)){
	    	isAll = true;
	    }else if(modelIdStr.equals("")){
	    	isAll = true;
	    }else{

			if(TeeUtility.isInteger(modelIdStr)){
				//如果存在按模块设置
				model = modulePrivService.selectPrivByUserId(loginUser.getUuid() + "", modelIdStr);
			}
			String andHQL = getManagerPostPrivHqlStrByPersonPostPrivAndModulePriv(loginUser, model );
			if(andHQL.equals("")){//所有
				isAll = true;
			}else if(andHQL.equals("0")){//空
				isAll = false;
				return list;
			}else{
			    list = personDao.selectManagerPostPersonByDept( loginUser, model , dept,userFilter ,andHQL);
			    return list;
			}
	    }
	    list = personDao.selectManagerPostPersonByDept( loginUser, model , dept,userFilter ,"0");
	    return list;
	}


	/**
	 * 获取登录人员的管理范围  --按模块
	 * @param person
	 * @param model
	 * 如果返回 0 代表没有权限,可以不用执行hql语句，直接返回空lisit人员即可
	 */
	public   String  getManagerPostPrivHqlStrByPersonPostPrivAndModulePriv(TeePerson person , TeeModulePrivModel model){
		String hql = "";
		if(model == null || (model != null &&  model.getSid() <= 0) ){//如果没有按模块设置
			String postDeptIds =  "";//TeePersonService.getLoginPersonPostDept(person);//获取系统当前登录人管理范围部门Id字符串
			
			boolean  isSuperAdmin = TeePersonService.checkIsSuperAdmin(person, "");
			if(isSuperAdmin){//超级管理员
				return "";
			}else{
				if(person.getDept() == null){//没有部门
					return "0";
				}
				postDeptIds = person.getDept().getUuid() + "";
				/*下级所有部门
				 * String level = person.getDept().getUuid() + ",";
				if(!TeeUtility.isNullorEmpty(person.getDept().getDeptParentLevel())){//如果不是第一级部门
					level = person.getDept().getDeptParentLevel();
				}else{//如果是第一级部门，则把uuID当做级别
					level = person.getDept().getGuid();
				}
				List<TeeDepartment> deptChildList = deptDao.getAllChildDeptByLevl(level);
				for (int i = 0; i < deptChildList.size(); i++) {
					postDeptIds = postDeptIds + deptChildList.get(i).getUuid() +  ",";
				}*/
				if(postDeptIds.equals("")){//没有权限
					postDeptIds = "0";
					return "0";
				}
				hql = hql + " and dept.uuid in (" + postDeptIds + ") and  userRole.roleNo > " + person.getUserRole().getRoleNo();
			}

		}else{
			
			//部门和角色交集(and）
			//if(!model.getDeptPriv().equals("1") &&   !model.getRolePriv().equals("2")){//部门权限不是全体部门且不是所有角色
				String roleIds = model.getRoleIdStr();//指定角色
				String deptIds = model.getDeptIdStr();//指定部门
				String userIds = model.getUserIdStr();//指定人员
				hql = "";
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
				if(!hql.equals("")){
					hql = " and (" + hql;
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
				if(!hql.equals("")){
					hql = hql + ") ";
				}
				
			}
		//}
		return hql;
	}
	
	
	
	/**
	 * 获取发送人员ID和userId字符串范围 -- 按模块范围   可传部门Id
	 * @param loginUser
	 * @param modelId
	 * @param userFilter 人员过滤，0-不控制  空-返回无数据 ，其他人员Id字符串逗号分隔
	 * @return
	 */
	public TeeManagerPostPersonDataPrivModel getSendPostPersonIdsPriv(Map map,  String modelId , String userFilter){
		TeePerson loginUser  = (TeePerson) map.get(TeeConst.LOGIN_USER);
		String modelIdStr =  TeeStringUtil.getString(modelId , ""); //0-全部 空为默认
		TeeManagerPostPersonDataPrivModel dataPrivModel = new TeeManagerPostPersonDataPrivModel();
		
		String queryUserColumn = TeeStringUtil.getString(map.get("queryUserColumn"), "uuid");//查询用户类型 0 - 查询用户uuid（sid） 1-查询 用户userId
		int deptId = TeeStringUtil.getInteger(map.get("deptId"), 0);//部门Id
		List values = new ArrayList();
		List<Integer> list = new ArrayList<Integer>();//uuid
		List<String> list2 = new ArrayList<String>();//userId
		dataPrivModel.setPrivType("");
	    if(modelIdStr.equals(0)){
	    	dataPrivModel.setPrivType("ALL");
	    }else if(modelIdStr.equals("")){
	    	dataPrivModel.setPrivType("ALL");
	    }else{
	    	TeeModulePrivModel model = null;
			if(TeeUtility.isInteger(modelIdStr)){
				//如果存在按模块设置
				model = modulePrivService.selectPrivByUserId(loginUser.getUuid() + "", modelIdStr);
			}
			String andHQL = personDao.getSendPrivHqlStrByPersonPostPrivAndModulePriv(loginUser, model);
			if(andHQL.equals("")){//所有
				dataPrivModel.setPrivType("ALL");
			}else if(andHQL.equals("0")){//空
				dataPrivModel.setPrivType("0");
			}else{
				 
				 TeeUserRole role = loginUser.getUserRole();
				 if(role == null){
			    	dataPrivModel.setPrivType("0");
				 }else{
					 String hql  = "select "+queryUserColumn+" from TeePerson where  deleteStatus <> '1' "; 
					 
					 if(deptId > 0){
					    	hql = hql + " and dept.uuid = ?";
					    	values.add(deptId);
					 }
					 if(!TeeUtility.isNullorEmpty(andHQL)){
							hql = hql + andHQL;
				      }
						
					  if(!userFilter.equals("0")){//需要处理
						  if(userFilter.endsWith(",")){
								userFilter = userFilter.substring(0, userFilter.length()-1);
						  }
						  hql = hql +  " and uuid  IN (" + userFilter + ") ";
					  }
					  hql = hql + "order by userRole.roleNo ,userNo";
					
					  if(queryUserColumn.equals("userId")){//查询userId字段
						  list2 = (List<String>) simpleDaoSupport.executeQueryByList(hql, values);
					  }else{
						  list = (List<Integer>) simpleDaoSupport.executeQueryByList(hql, values);
					  }
				 }
			}
	    }
	    list.add(loginUser.getUuid());//要加入自己的
	    list2.add(loginUser.getUserId());
	    dataPrivModel.setPersonIds(list);
	    dataPrivModel.setPersonUserIds(list2);
	    return dataPrivModel;
	}
	
	
	/**
	 * By部门查询人员,且带条件查询   和权限管理
	 * @param 部门UUID
	 * @param userFilter : 人员id字符串
	 *  @param privNoFlag : 是否需要处理权限控制   1-处理  其它不处理
	 */
	public List<TeePerson> selectPersonByDeptIdAndUserFilter(int deptId , String userFilter,TeePerson person ,TeeModulePrivModel model , String  privNoFlag) {
	    @SuppressWarnings("unchecked")
	    String privNoFlagTemp = TeeStringUtil.getString(privNoFlag, "0");
	   // Object[] values = {deptId};
	    List values = new ArrayList();
	    String hql  = " from TeePerson where    deleteStatus <> '1' "; 
	    if(deptId > 0){
	    	hql = hql + " and dept.uuid = ?";
	    	values.add(deptId);
	    }
	    if(privNoFlagTemp.equals( "1" )){//处理
	    	String andHql = personDao.getSendPrivHqlStrByPersonPostPrivAndModulePriv(person, model);
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
			hql = hql +  " and uuid  IN (" + userFilter + ") ";
		}
		hql = hql + "order by userNo";
	    List<TeePerson> list = (List<TeePerson>) simpleDaoSupport.executeQueryByList(hql, values);
		return list;
	}
	
}
