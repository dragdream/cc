package com.tianee.oa.subsys.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserRoleService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectCreatePrivService extends TeeBaseService{
	
    @Autowired
	private TeeSysParaService sysParaService;
    
    
    @Autowired
	private TeeDeptService deptService;
    
    
    @Autowired
	private TeeUserRoleService roleService;
    
    @Autowired
   	private TeePersonService personService;
	
	/**
	 * 获取项目新建权限值
	 * @param request
	 * @return
	 */
	public TeeJson getData(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		Map<String,String> map=new HashMap<String,String>();
		//获取部门范围
		String deptIds=sysParaService.getSysParaValue("PROJECT_CREATE_PRIV_DEPT");
		List<TeeDepartment> deptList=deptService.getDeptByUuids(deptIds);
		String deptNames="";
		if(deptList!=null){
			for (TeeDepartment teeDepartment : deptList) {
				deptNames+=teeDepartment.getDeptName()+",";
			}
			if(deptNames.endsWith(",")){
				deptNames=deptNames.substring(0,deptNames.length()-1);
			}
		}
		map.put("deptIds", deptIds);
		map.put("deptNames", deptNames);
		
		//获取角色范围
		String roleIds=sysParaService.getSysParaValue("PROJECT_CREATE_PRIV_ROLE");
		List<TeeUserRole> roleList=roleService.getUserRoleByUuids(roleIds);
		String roleNames="";
		if(roleList!=null){
			for (TeeUserRole teeUserRole : roleList) {
				roleNames+=teeUserRole.getRoleName()+",";
			}
			if(roleNames.endsWith(",")){
				roleNames=roleNames.substring(0,roleNames.length()-1);
			}
		}
		map.put("roleIds", roleIds);
		map.put("roleNames", roleNames);		
		

		//获取人员范围
		String userIds=sysParaService.getSysParaValue("PROJECT_CREATE_PRIV_USER");
		List<TeePerson> userList=personService.getPersonByUuids(userIds);
		String userNames="";
		if(userList!=null){
			for (TeePerson teePerson : userList) {
				userNames+=teePerson.getUserName()+",";
			}
			if(userNames.endsWith(",")){
				userNames=userNames.substring(0,userNames.length()-1);
			}
		}
		map.put("userIds", userIds);
		map.put("userNames", userNames);	
		
		
		
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("获取成功！");
		return json;
	}

	/**
	 * 设置项目新建权限值
	 * @param request
	 * @return
	 */
	public TeeJson setData(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String deptIds=request.getParameter("deptIds");
		String roleIds=request.getParameter("roleIds");
		String userIds=request.getParameter("userIds");
		
		sysParaService.updateSysPara("PROJECT_CREATE_PRIV_DEPT", deptIds);
		sysParaService.updateSysPara("PROJECT_CREATE_PRIV_ROLE", roleIds);
		sysParaService.updateSysPara("PROJECT_CREATE_PRIV_USER", userIds);
		
		json.setRtState(true);
		return json;
	}

	/**
	 * 判断当前登陆人有没有创建项目的权利
	 * @param request
	 * @return
	 */
	public TeeJson isCreateByLoginUser(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String deptIds=sysParaService.getSysParaValue("PROJECT_CREATE_PRIV_DEPT");
		String roleIds=sysParaService.getSysParaValue("PROJECT_CREATE_PRIV_ROLE");
		String userIds=sysParaService.getSysParaValue("PROJECT_CREATE_PRIV_USER");
		
		String [] deptArray=deptIds.split(",");
		String [] roleArray=roleIds.split(",");
		String [] userArray=userIds.split(",");
		
		if(person!=null){
			int uuid=person.getUuid();
			int roleId=person.getUserRole().getUuid();
			int deptId=person.getDept().getUuid();
    
			if(deptArray.length>0){
				for(int i=0;i<deptArray.length;i++){
					if(TeeStringUtil.getInteger(deptArray[i], 0)==deptId){
						json.setRtState(true);
						json.setRtData(1);
						return json;
					}
				}
			}
			
			if(roleArray.length>0){
				for(int i=0;i<roleArray.length;i++){
					if(TeeStringUtil.getInteger(roleArray[i], 0)==roleId){
						json.setRtState(true);
						json.setRtData(1);
						return json;
					}
				}	
			}
			
			if(userArray.length>0){
				for(int i=0;i<userArray.length;i++){
					if(TeeStringUtil.getInteger(userArray[i], 0)==uuid){
						json.setRtState(true);
						json.setRtData(1);
						return json;
					}
				}
			}

			json.setRtState(true);
			json.setRtData(0);
		}
		return json;
	}

	
}
