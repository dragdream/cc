package com.tianee.oa.subsys.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
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
import com.tianee.oa.subsys.project.bean.TeeProjectApprovalRule;
import com.tianee.oa.subsys.project.model.TeeProjectApprovalRuleModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeProjectApprovalRuleService extends TeeBaseService{

	@Autowired
	private TeeSysParaService sysParaService;
	@Autowired
	private TeeDeptService deptService;   
    @Autowired
	private TeeUserRoleService roleService;
	@Autowired
	private TeePersonService personService;
	
	/**
	 * 设置免审批规则
	 * @param request
	 * @return
	 */
	public TeeJson setNoApprovalRule(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String deptIds=request.getParameter("deptIds");
		String roleIds=request.getParameter("roleIds");
		String userIds=request.getParameter("userIds");
		
		sysParaService.updateSysPara("PROJECT_NO_APPROVAL_RULE_DEPT", deptIds);
		sysParaService.updateSysPara("PROJECT_NO_APPROVAL_RULE_ROLE", roleIds);
		sysParaService.updateSysPara("PROJECT_NO_APPROVAL_RULE_USER", userIds);
		
		json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 获取免审批规则的数据
	 * @param request
	 * @return
	 */
	public TeeJson getNoApprovalRule(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		Map<String,String> map=new HashMap<String,String>();
		//获取部门范围
		String deptIds=sysParaService.getSysParaValue("PROJECT_NO_APPROVAL_RULE_DEPT");
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
		String roleIds=sysParaService.getSysParaValue("PROJECT_NO_APPROVAL_RULE_ROLE");
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
		String userIds=sysParaService.getSysParaValue("PROJECT_NO_APPROVAL_RULE_USER");
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
	 * 添加/编辑审批规则
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdateApprovalRule(HttpServletRequest request,TeeProjectApprovalRuleModel model) {
		TeeJson json=new TeeJson();		
		int sid=TeeStringUtil.getInteger(model.getSid(), 0);
		if(sid>0){
			TeeProjectApprovalRule rule=(TeeProjectApprovalRule) simpleDaoSupport.get(TeeProjectApprovalRule.class,sid);
			rule.setApproverIds(model.getApproverIds());
			rule.setManageDeptIds(model.getManageDeptIds());
			simpleDaoSupport.update(rule);
			json.setRtMsg("编辑成功！");
		}else{
			TeeProjectApprovalRule rule=new TeeProjectApprovalRule();
			rule.setApproverIds(model.getApproverIds());
			rule.setManageDeptIds(model.getManageDeptIds());
			simpleDaoSupport.save(rule);
			json.setRtMsg("添加成功！");
		}
		json.setRtState(true);	
		return json;
	}



	/**
	 * 根据主键获取某一个审批规则的详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getApprovalRuleBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectApprovalRule rule=(TeeProjectApprovalRule) simpleDaoSupport.get(TeeProjectApprovalRule.class,sid);
			TeeProjectApprovalRuleModel model=parseToModel(rule);
			json.setRtData(model);
			json.setRtState(true);
			json.setRtMsg("数据获取成功!");	
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	/**
	 * 将审批规则转换成model对象
	 * @param rule
	 * @return
	 */
	private TeeProjectApprovalRuleModel parseToModel(TeeProjectApprovalRule rule) {
		TeeProjectApprovalRuleModel model=new TeeProjectApprovalRuleModel();
		BeanUtils.copyProperties(rule, model);
		if(!("").equals(rule.getApproverIds())){
			List<TeePerson> userList=personService.getPersonByUuids(rule.getApproverIds());
			String approverNames="";
			if(userList!=null){
				for (TeePerson teePerson : userList) {
					approverNames+=teePerson.getUserName()+",";
				}
				if(approverNames.endsWith(",")){
					approverNames=approverNames.substring(0,approverNames.length()-1);
				}
			}
			model.setApproverNames(approverNames);
		}
		
		if(!("").equals(rule.getManageDeptIds())){
			List<TeeDepartment> deptList=deptService.getDeptByUuids(rule.getManageDeptIds());
			String deptNames="";
			if(deptList!=null){
				for (TeeDepartment teeDepartment : deptList) {
					deptNames+=teeDepartment.getDeptName()+",";
				}
				if(deptNames.endsWith(",")){
					deptNames=deptNames.substring(0,deptNames.length()-1);
				}
			}
			model.setManageDeptNames(deptNames);
		}
		return model;
	}



	/**
	 * 获取审批规则列表
	 * @param request
	 * @return
	 */
	public TeeJson getApprovalRuleList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeProjectApprovalRule> list=simpleDaoSupport.executeQuery(" from TeeProjectApprovalRule ", null);
		List<TeeProjectApprovalRuleModel> modelList=new ArrayList<TeeProjectApprovalRuleModel>();
		TeeProjectApprovalRuleModel model=null;
		for (TeeProjectApprovalRule rule : list) {
			model=parseToModel(rule);
			modelList.add(model);
		}
		json.setRtState(true);
		json.setRtData(modelList);
		json.setRtMsg("数据获取成功！");
		return json;
	}



	/**
	 * 删除审批规则
	 * @param request
	 * @return
	 */
	public TeeJson deleteBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeProjectApprovalRule rule=(TeeProjectApprovalRule) simpleDaoSupport.get(TeeProjectApprovalRule.class,sid);
			simpleDaoSupport.deleteByObj(rule);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该审批规则不存在！");
		}
		return json;
	}



	/**
	 * 根据当前登陆人  获取项目的审批人
	 * @param request
	 * @return
	 */
	public TeeJson getApproverByLoginUser(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List<TeeProjectApprovalRule> ruleList=simpleDaoSupport.executeQuery("from TeeProjectApprovalRule rule", null);
		String deptIds="";
		String approverIds="";
		Set<Map> set=new HashSet<Map>();
		List<TeePerson> list=null;
		Map<String,String>map=null;
		for (TeeProjectApprovalRule teeProjectApprovalRule : ruleList) {
			deptIds=teeProjectApprovalRule.getManageDeptIds();
			String [] deptIdArray=deptIds.split(",");
		    for (String str : deptIdArray) {
				if(TeeStringUtil.getInteger(str, 0)==loginUser.getDept().getUuid()){
					approverIds=teeProjectApprovalRule.getApproverIds();
					list=personService.getPersonByUuids(approverIds);
					for (TeePerson person : list) {
						map=new HashMap<String,String>();
						map.put("uuid", person.getUuid()+"");
						map.put("userName", person.getUserName());
						set.add(map);
					}
					break;
				}
			}
		}
		json.setRtState(true);
		json.setRtData(set);
		return json;
	}


    /**
     * 判断当前登录人  是不是免审批
     * @param request
     * @return
     */
	public TeeJson isNoApprove(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String deptIds=sysParaService.getSysParaValue("PROJECT_NO_APPROVAL_RULE_DEPT");
		String roleIds=sysParaService.getSysParaValue("PROJECT_NO_APPROVAL_RULE_ROLE");
		String userIds=sysParaService.getSysParaValue("PROJECT_NO_APPROVAL_RULE_USER");
		
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

