package com.beidasoft.xzzf.power.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.power.bean.BasePowerDetail;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerSelectDetailDao extends TeeBaseDao<BasePowerDetail> {
	/**
	 * 删除对象根据powerId删除
	 * 
	 * @param id
	 */
	public void deleteByPowerId(String powerId) {
		String hql = "Delete FROM BasePowerDetail Where powerId=?";
		Session session = this.getSession();
		Query q = session.createQuery(hql);
		q.setString(0, powerId);
		q.executeUpdate();
	}

	public List<BasePowerDetail> getByPower(String id) {
		String hql = "FROM BasePowerDetail Where powerId=?";
		Session session = this.getSession();
		Query q = session.createQuery(hql);
		q.setString(0, id);
		List<BasePowerDetail> detail = q.list();
		for (BasePowerDetail li : detail) {
			// System.out.println(li);
		}
		return detail;
	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	public List<BasePowerDetail> getDetailList(String id) {
		String hql = "FROM BasePowerDetail Where powerId='" + id + "'";
		return super.find(hql, null);
	}

}
