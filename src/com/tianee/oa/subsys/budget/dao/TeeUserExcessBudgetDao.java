package com.tianee.oa.subsys.budget.dao;




import java.util.List;

import org.springframework.stereotype.Repository;


import com.tianee.oa.subsys.budget.bean.TeeUserExcessBudget;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("userExcessBudget")
public class TeeUserExcessBudgetDao extends TeeBaseDao<TeeUserExcessBudget>{
	//Calendar c = Calendar.getInstance();
public List getue(int userId,int year,int month){
	Object[] values= {userId,String.valueOf(year),String.valueOf((month<10)?("0"+month):(month))};
	String hql="select tueb.excessAmount from TeeUserExcessBudget tueb  where tueb.user.uuid=? and tueb.year=? and tueb.month=?";
	List list = executeQuery(hql, values);
	return list;
}
}
