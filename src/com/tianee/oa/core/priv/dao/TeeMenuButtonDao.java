package com.tianee.oa.core.priv.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.priv.bean.TeeMenuButton;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeMenuButtonDao  extends TeeBaseDao<TeeMenuButton>{

	public List<TeeMenuButton> getAll() {
		String hql = " from TeeMenuButton t ";
	    String[] values = {};
	    List<TeeMenuButton> list = (List<TeeMenuButton>) executeQuery(hql, values);
	    return list;
	}

	public List<Map> getBtnPrivByMenuUuid(int personId,int menuId) {
		String sql = "select t1.*,IF(ISNULL(t2.BUTTON_ID),'0','1') IS_PRIV " + 
			      " from " + 
			      "  (select * from menu_button mb where mb.MENU_ID = ? ) t1  " + 
			      " LEFT OUTER JOIN " + 
			      "  (select distinct mgmb.BUTTON_ID " + 
			      "     from person p, " + 
			      "          person_menu_group pmg, " + 
			      "          menu_group_menu_button mgmb " + 
			      "    where p.uuid = pmg.PERSON_UUID " + 
			      "      and pmg.MENU_GROUP_UUID = mgmb.GROUP_UUID " + 
			      "      and p.uuid = ?) t2 " + 
			      "       ON t1.ID = t2.BUTTON_ID ";
		Object[] param = {menuId,personId};
		
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, menuId);
		query.setParameter(1, personId);		

		List<Map> result = query.list();;
		return result;
	}
	
}
