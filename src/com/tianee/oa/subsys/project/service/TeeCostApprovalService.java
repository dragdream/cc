package com.tianee.oa.subsys.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class TeeCostApprovalService extends TeeBaseService{

	@Autowired
	private TeeSysParaService sysParaService;
	@Autowired
	private TeePersonService personService;
	/**
	 * 设置费用审批人员
	 * @param request
	 * @return
	 */
	public TeeJson setCostApprovalRules(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		
		String userIds=request.getParameter("userIds");
		
		sysParaService.updateSysPara("PROJECT_COST_APPROVAL_RULE_USER", userIds);
		
		json.setRtState(true);
		return json;
	}
    
	
	
    /**
     * 获取费用审批人员
     * @param request
     * @return
     */
	public TeeJson getCostApprovalRule(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		Map<String,String> map=new HashMap<String,String>();
		//获取人员范围
		String userIds=sysParaService.getSysParaValue("PROJECT_COST_APPROVAL_RULE_USER");
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
	 * 获取费用审批人员
	 * @param request
	 * @return
	 */
	public TeeJson getApprover(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<Map> list=new ArrayList<Map>();
		//获取人员范围
		String userIds=sysParaService.getSysParaValue("PROJECT_COST_APPROVAL_RULE_USER");
		List<TeePerson> userList=personService.getPersonByUuids(userIds);
		if(userList!=null){
			Map map=null;
			for (TeePerson teePerson : userList) {
				map=new HashMap();
				map.put("uuid", teePerson.getUuid());
				map.put("userName", teePerson.getUserName());
				list.add(map);
			}
		}
		json.setRtData(list);
		json.setRtState(true);
		
		return json;
	}

}
