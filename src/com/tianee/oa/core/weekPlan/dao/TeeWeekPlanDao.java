package com.tianee.oa.core.weekPlan.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;




import com.tianee.oa.core.weekPlan.bean.TeeWeekPlan;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository
public class TeeWeekPlanDao extends TeeBaseDao<TeeWeekPlan>{
	
	public Object get(Class clazz,Serializable id) {
		Session session = this.getSession();
		return session.get(clazz, id);
	}
	
}
