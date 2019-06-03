package com.tianee.oa.subsys.budget.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.commonword.bean.CommonWord;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.subsys.budget.bean.TeeUserExcessBudget;
import com.tianee.oa.subsys.budget.dao.TeeUserBudgetDao;
import com.tianee.oa.subsys.budget.model.TeeUserExcessBudgetModel;
import com.tianee.oa.subsys.vmeeting.bean.Meeting;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TeeUserExcessBudgetService  extends TeeBaseService  {
	
	
	public TeeJson addObj(HttpServletRequest request, TeeUserExcessBudgetModel model,String userId) {
		TeeJson json = new TeeJson();
		TeeUserExcessBudget tueb = new TeeUserExcessBudget();
		 BeanUtils.copyProperties(model, tueb);
		 TeePerson tperson = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(userId));
		 model.setCrUserId(tperson.getUuid());
		 model.setCrUserName(tperson.getUserName());
		 model.setUserId(tperson.getUuid());
		 model.setUserName(tperson.getUserName());
		 
		 if(model.getCrUserId()!=0){
			 TeePerson tp=new TeePerson();
			 tp.setUuid(model.getCrUserId());
			 tueb.setCrUser(tp);
		 }
		 if(model.getUserId()!=0){
			 TeePerson tp = new TeePerson();
			 tp.setUuid(model.getUserId());
			 tueb.setUser(tp);
		 }
		 
		 simpleDaoSupport.save(tueb);
		 json.setRtState(true);
		return json;
	}

}
