package com.tianee.oa.subsys.salManage.dao;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.tianee.oa.subsys.salManage.bean.TeeHrInsurancePara;
import com.tianee.webframe.dao.TeeBaseDao;



/**
 * 
 * @author CXT
 *
 */
@Repository("hrParaDao")
public class TeeHrInsuranceParaDao extends TeeBaseDao<TeeHrInsurancePara>{

	public void add(TeeHrInsurancePara item){
		save(item);
	}
	
	public List<TeeHrInsurancePara> hrParaList(){
		Object[] values = {};
		String hql = "from TeeHrInsurancePara bookType"; 
		List<TeeHrInsurancePara> list = executeQuery(hql, values);
		return list;
	}
	
	public void update(TeeHrInsurancePara item){
		Session session = this.getSession();
		session.merge(item);
	}
	
}
