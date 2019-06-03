package com.tianee.webframe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 公共的 简单的 辅助型的业务 逻辑
 * @author zhp
 * @createTime 2014-1-23
 * @desc
 */
@Service
public class TeeSimpleService extends TeeBaseService{

	public List<TeePerson> getToRolePersons(String toRoleIds){
		return  (List<TeePerson>)simpleDaoSupport.find("from TeePerson p where p.userRole.uuid in ("+toRoleIds+")", null);
	}
	
	public List<TeePerson> getToDeptPersons(String toRoleIds){
		return  (List<TeePerson>)simpleDaoSupport.find("from TeePerson p where p.dept.uuid in ("+toRoleIds+")", null);
	}
	
	public List<TeePerson> getToUserPersons(String toUserIds){
		
		return  (List<TeePerson>)simpleDaoSupport.find("from TeePerson p where p.uuid in ("+toUserIds+")", null);
	}
	
	/**
	 * 去重复
	 * @author zhp
	 * @createTime 2014-1-16
	 * @editTime 上午10:45:33
	 * @desc
	 */
	public List<TeePerson> filterExistPerson(List<TeePerson> toUsers){
		if(toUsers == null){
			return null;
		}
		List<TeePerson> target = new ArrayList<TeePerson>();
		for(int i=0;i<toUsers.size();i++){
			TeePerson p =  (TeePerson)toUsers.get(i);
			if(!target.contains(p)){
				target.add(p);
			}
		}
		return target;
	}
}
