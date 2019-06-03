package com.tianee.oa.core.base.pm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.pm.model.TeeMyEmployeeModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeMyEmployeeService extends TeeBaseService{
	
	public List<TeeMyEmployeeModel> getMyEmployees(int uuid){
		String hql = "from TeePerson p where p.dept.";
		return null;
	}
	
}
