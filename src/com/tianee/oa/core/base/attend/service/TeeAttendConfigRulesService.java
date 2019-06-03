package com.tianee.oa.core.base.attend.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.bean.TeeAttendConfigRules;
import com.tianee.oa.core.base.attend.model.TeeAttendConfigModel;
import com.tianee.oa.core.base.attend.model.TeeAttendConfigRulesModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserRoleService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeAttendConfigRulesService extends TeeBaseService{

	@Autowired
	private  TeePersonService personService;
	
	@Autowired
	private  TeeDeptService deptService;
	
	@Autowired
	private  TeeUserRoleService roleService;
	
	
	@Autowired
	private TeeAttendConfigService  attendConfigService;
	
	/**
	 * 新建/编辑固定排班类型
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeAttendConfigRulesModel model) {
		TeeJson json=new TeeJson();
		int sid=model.getSid();
		
		String userIds=TeeStringUtil.getString(model.getUserIds());
		String deptIds=TeeStringUtil.getString(model.getDeptIds());
		String roleIds=TeeStringUtil.getString(model.getRoleIds());
		
		//获取所有关联的人
		List<TeePerson>list=new ArrayList<TeePerson>();
		List<TeePerson>list1=null;
		List<TeePerson>list2=null;
		List<TeePerson>list3=null;
		if(!TeeUtility.isNullorEmpty(userIds)){
			list1=personService.getPersonByUuids(userIds);
		}
		
		if(!TeeUtility.isNullorEmpty(deptIds)){
			list2=personService.getPersonByDeptIds(deptIds);
		}
		
		if(!TeeUtility.isNullorEmpty(roleIds)){
			list3=personService.selectPersonByRoleId(roleIds);
		}
		if(list1!=null){
			list.addAll(list1);
		}
		if(list2!=null){
			list.addAll(list2);
		}
		if(list3!=null){
			list.addAll(list3);
		}
		
		
		Set<TeePerson> persons=new HashSet<TeePerson>(list);
		
		if(sid>0){//编辑
			TeeAttendConfigRules rule=(TeeAttendConfigRules) simpleDaoSupport.get(TeeAttendConfigRules.class,sid);
			//判断是否存在人员冲突
			for (TeePerson teePerson : persons) {
				
				String hql="select count(*) from TeeAttendConfigRules r where (exists (select 1 from r.users user where user.uuid=? ) or exists (select 1 from r.depts dept where dept.uuid=? ) or exists (select 1 from r.roles role where role.uuid=? )) and  r.sid<>? ";
			    long count=simpleDaoSupport.count(hql, new Object[]{teePerson.getUuid(),teePerson.getDept()!=null?teePerson.getDept().getUuid():0,teePerson.getUserRole()!=null?teePerson.getUserRole().getUuid():0,sid});
				if(count>0){
					json.setRtState(false);
					json.setRtMsg("排班数据人员有冲突！");
					return json;
				}
			    
			}
			
			
			
			rule.setConfigModel(model.getConfigModel());
		    rule.setConfigName(model.getConfigName());
		    
		    rule.getUsers().clear();
		    rule.getDepts().clear();
		    rule.getRoles().clear();
		    
			
			List<TeePerson> userList=null;
			List<TeeDepartment> deptList=null;
			List<TeeUserRole> roleList=null;
			if(!TeeUtility.isNullorEmpty(userIds)){
				userList=personService.getPersonByUuids(userIds);
			}
			
			if(!TeeUtility.isNullorEmpty(deptIds)){
				deptList=deptService.getDeptByUuids(deptIds);
			}
			
			if(!TeeUtility.isNullorEmpty(roleIds)){
				roleList=roleService.getUserRoleByUuids(roleIds);
			}
			
			if(userList!=null){
				Set<TeePerson>users=new HashSet<TeePerson>(userList);
				rule.setUsers(users);
			}
			
			if(deptList!=null){
				Set<TeeDepartment>depts=new HashSet<TeeDepartment>(deptList);
				rule.setDepts(depts);
			}
			
			if(roleList!=null){
				Set<TeeUserRole>roles=new HashSet<TeeUserRole>(roleList);
				rule.setRoles(roles);
			}

			
			simpleDaoSupport.update(rule);
		    
		}else{//新增
			//判断是否存在人员冲突
			for (TeePerson teePerson : persons) {
				
				String hql="select count(*) from TeeAttendConfigRules r where (exists (select 1 from r.users user where user.uuid=? ) or exists (select 1 from r.depts dept where dept.uuid=? ) or exists (select 1 from r.roles role where role.uuid=? )) ";
			    long count=simpleDaoSupport.count(hql, new Object[]{teePerson.getUuid(),teePerson.getDept()!=null?teePerson.getDept().getUuid():0,teePerson.getUserRole()!=null?teePerson.getUserRole().getUuid():0});
				if(count>0){
					json.setRtState(false);
					json.setRtMsg("排班数据人员有冲突！");
					return json;
				}
			    
			}
			
			
			
			TeeAttendConfigRules rule=new TeeAttendConfigRules();
			BeanUtils.copyProperties(model, rule);
			
		    
			List<TeePerson> userList=null;
			List<TeeDepartment> deptList=null;
			List<TeeUserRole> roleList=null;
			if(!TeeUtility.isNullorEmpty(userIds)){
				userList=personService.getPersonByUuids(userIds);
			}
			
			if(!TeeUtility.isNullorEmpty(deptIds)){
				deptList=deptService.getDeptByUuids(deptIds);
			}
			
			if(!TeeUtility.isNullorEmpty(roleIds)){
				roleList=roleService.getUserRoleByUuids(roleIds);
			}
			if(userList!=null){
				Set<TeePerson>users=new HashSet<TeePerson>(userList);
				rule.setUsers(users);
			}
			
			if(deptList!=null){
				Set<TeeDepartment>depts=new HashSet<TeeDepartment>(deptList);
				rule.setDepts(depts);
			}
			
			if(roleList!=null){
				Set<TeeUserRole>roles=new HashSet<TeeUserRole>(roleList);
				rule.setRoles(roles);
			}

			simpleDaoSupport.save(rule);
		}
		
		json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 获取固定排班列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		String hql = "from TeeAttendConfigRules r where 1=1 ";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		hql+=" order by r.sid asc";
		List<TeeAttendConfigRulesModel> rows = new ArrayList<TeeAttendConfigRulesModel>();
		List<TeeAttendConfigRules> list = simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		TeeAttendConfigRulesModel model = null;
		for(TeeAttendConfigRules r:list){
			model=parseToModel(r);
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}



	
	/**
	 * 实体类转换成model
	 * @param r
	 * @return
	 */
	private TeeAttendConfigRulesModel parseToModel(TeeAttendConfigRules r) {
		TeeAttendConfigRulesModel model=new TeeAttendConfigRulesModel();
		BeanUtils.copyProperties(r, model);
		
		
		String userNames="";
		String userIds="";
		if(r.getUsers()!=null){
			Set<TeePerson> users=r.getUsers();
			for (TeePerson teePerson : users) {
				userNames+=teePerson.getUserName()+",";
				userIds+=teePerson.getUuid()+",";
			}
			
			if(userNames.endsWith(",")){
				userNames=userNames.substring(0,userNames.length()-1);
			}
			if(userIds.endsWith(",")){
				userIds=userIds.substring(0,userIds.length()-1);
			}
		}
		model.setUserIds(userIds);
		model.setUserNames(userNames);
		
		String deptIds="";
		String deptNames="";
		if(r.getDepts()!=null){
			Set<TeeDepartment> depts=r.getDepts();
			for (TeeDepartment d:depts) {
				deptIds+=d.getUuid()+",";
				deptNames+=d.getDeptName()+",";
			}
			
			if(deptIds.endsWith(",")){
				deptIds=deptIds.substring(0,deptIds.length()-1);
			}
			if(deptNames.endsWith(",")){
				deptNames=deptNames.substring(0,deptNames.length()-1);
			}
		}
		model.setDeptIds(deptIds);
		model.setDeptNames(deptNames);
		
		
		
		String roleNames="";
		String roleIds="";
		if(r.getRoles()!=null){
			Set<TeeUserRole> roles=r.getRoles();
			for (TeeUserRole teeUserRole : roles) {
				roleNames+=teeUserRole.getRoleName()+",";
				roleIds+=teeUserRole.getUuid()+",";
			}
			
			if(roleNames.endsWith(",")){
				roleNames=roleNames.substring(0,roleNames.length()-1);
			}
			if(roleIds.endsWith(",")){
				roleIds=roleIds.substring(0,roleIds.length()-1);
			}
			
		}
		
		model.setRoleIds(roleIds);
		model.setRoleNames(roleNames);
		
		String configModel=r.getConfigModel();
		Map<String,String> m=TeeJsonUtil.JsonStr2Map(configModel);
		List<Map> list=new ArrayList<Map>();
		Map map=null;
		TeeAttendConfig config=null;
		TeeAttendConfigModel  acModel=null;
		String attendConfigTime1="";
		String attendConfigTime2="";
		String attendConfigTime3="";
		for (String str : m.keySet()) {
			map=new LinkedHashMap();
			map.put(str, m.get(str));
			int attendConfigId=TeeStringUtil.getInteger(m.get(str), 0);
			if(attendConfigId==0){
				map.put("attendConfigTime1", "");
				map.put("attendConfigTime2", "");
				map.put("attendConfigTime3", "");
			}else{
				config=(TeeAttendConfig) simpleDaoSupport.get(TeeAttendConfig.class,attendConfigId);
				acModel =attendConfigService.parseModel(config);
				if(!TeeUtility.isNullorEmpty(acModel.getDutyTimeDesc1())&&!TeeUtility.isNullorEmpty(acModel.getDutyTimeDesc2())){
					attendConfigTime1=acModel.getDutyTimeDesc1()+"-"+acModel.getDutyTimeDesc2();
				}
                if(!TeeUtility.isNullorEmpty(acModel.getDutyTimeDesc3())&&!TeeUtility.isNullorEmpty(acModel.getDutyTimeDesc4())){
                	attendConfigTime2=acModel.getDutyTimeDesc3()+"-"+acModel.getDutyTimeDesc4();
				}
                if(!TeeUtility.isNullorEmpty(acModel.getDutyTimeDesc5())&&!TeeUtility.isNullorEmpty(acModel.getDutyTimeDesc6())){
                	attendConfigTime3=acModel.getDutyTimeDesc5()+"-"+acModel.getDutyTimeDesc6();
				}
                map.put("attendConfigTime1", attendConfigTime1);
				map.put("attendConfigTime2", attendConfigTime2);
				map.put("attendConfigTime3", attendConfigTime3);
			}
			
			list.add(map);
		}
		
	    model.setConfigModelStr(JSONArray.fromObject(list).toString());
		
		return model;
	}



	/**
	 * 根据主键进行删除
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeAttendConfigRules rule=(TeeAttendConfigRules) simpleDaoSupport.get(TeeAttendConfigRules.class,sid);
		if(rule!=null){
			rule.getUsers().clear();
			rule.getDepts().clear();
			rule.getRoles().clear();
			
			simpleDaoSupport.deleteByObj(rule);
			
			json.setRtState(true);
			json.setRtMsg("删除失败！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据信息获取失败！");
		}
		
		return json;
	}



	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid =TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeAttendConfigRules rule=(TeeAttendConfigRules) simpleDaoSupport.get(TeeAttendConfigRules.class,sid);
		if(rule!=null){
			TeeAttendConfigRulesModel model=parseToModel(rule);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		return json;
	}

}
