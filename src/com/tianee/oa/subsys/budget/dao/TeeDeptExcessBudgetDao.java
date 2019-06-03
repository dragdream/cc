package com.tianee.oa.subsys.budget.dao;




import java.util.List;

import org.springframework.stereotype.Repository;




import com.tianee.oa.subsys.budget.bean.TeeDeptExcessBudget;
import com.tianee.oa.subsys.budget.bean.TeeUserExcessBudget;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("deptExcessBudget")
public class TeeDeptExcessBudgetDao extends TeeBaseDao<TeeDeptExcessBudget>{
	//Calendar c = Calendar.getInstance();
public List getde(int deptId,int year,int month){
	Object[] values= {deptId,String.valueOf(year),String.valueOf((month<10)?("0"+month):(month))};
	String hql="select excessAmount from TeeDeptExcessBudget where dept.uuid=? and year=? and month=?";
	List list = executeQuery(hql, values);
	return list;
}
}
