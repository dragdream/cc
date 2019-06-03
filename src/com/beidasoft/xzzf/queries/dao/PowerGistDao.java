package com.beidasoft.xzzf.queries.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.queries.bean.PowerBaseGist;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository("Xzzf_PowerGistDao")
public class PowerGistDao extends TeeBaseDao<PowerBaseGist>{

	public List<PowerBaseGist> getByPower(String id) {
		String hql = "FROM PowerBaseGist Where powerId=?";
		Session session = this.getSession();
		Query q = session.createQuery(hql);
		q.setString(0, id);
		List<PowerBaseGist> gist = q.list();
		for (PowerBaseGist li : gist) {
			// System.out.println(li);
		}
		return gist;
	}

	public List<PowerBaseGist> getGistList(String id) {
		String hql = "FROM PowerBaseGist Where powerId='" + id + "'";
		return super.find(hql, null);
	}

}
