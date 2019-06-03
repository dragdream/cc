package com.tianee.oa.subsys.budget.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.budget.bean.TeeDeptExcessBudget;
import com.tianee.oa.subsys.budget.bean.TeeUserExcessBudget;
import com.tianee.oa.subsys.budget.model.TeeDeptExcessBudgetModel;
import com.tianee.oa.subsys.budget.model.TeeUserExcessBudgetModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeDeptExcessBudgetService extends TeeBaseService {

	public TeeJson addObj(HttpServletRequest request,TeeDeptExcessBudgetModel model, String deptId, TeePerson person) {
		TeeJson json = new TeeJson();
		TeeDeptExcessBudget tdeb = new TeeDeptExcessBudget();
		 BeanUtils.copyProperties(model, tdeb);
		 TeeDepartment department = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, Integer.parseInt(deptId));
	      model.setDeptId(department.getUuid());
	      model.setDeptName(department.getDeptName());
	      model.setCrUserId(person.getUuid());
	      model.setCrUserName(person.getUserName());
	      
		 if(model.getCrUserId()!=0){
			 TeePerson tp=new TeePerson();
			 tp.setUuid(model.getCrUserId());
			 tdeb.setCrUser(tp);
		 }
		 if(model.getDeptId()!=0){
			 TeeDepartment tdp = new TeeDepartment();
			 tdp.setUuid(model.getDeptId());
			 tdeb.setDept(tdp);
		 }
		 
		 simpleDaoSupport.save(tdeb);	
		 //json.setRtMsg("保存成功");
		 json.setRtState(true);
		return json;
	}

}
