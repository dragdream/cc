package com.beidasoft.xzzf.queries.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.queries.bean.PowerBaseDetail;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository("xzzf_powerDetailDao")
public class PowerDetailDao extends TeeBaseDao<PowerBaseDetail>{
	public List<PowerBaseDetail> getByPower(String id) {
		String hql = "FROM PowerBaseDetail Where powerId=?";
		Session session = this.getSession();
		Query q = session.createQuery(hql);
		q.setString(0, id);
		List<PowerBaseDetail> detail = q.list();
		for (PowerBaseDetail li : detail) {
			// System.out.println(li);
		}
		return detail;
	}

	public List<PowerBaseDetail> getDetailList(String id) {
		String hql = "FROM PowerBaseDetail Where powerId='" + id + "'";
		return super.find(hql, null);
	}

}
