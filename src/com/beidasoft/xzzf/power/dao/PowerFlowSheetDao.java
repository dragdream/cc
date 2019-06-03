package com.beidasoft.xzzf.power.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.power.bean.BasePowerFlowsheet;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerFlowSheetDao extends TeeBaseDao<BasePowerFlowsheet> {

	/**
	 * 删除对象根据powerId删除
	 * 
	 * @param id
	 */
	public void deleteByPowerId(String powerId) {
		String hql = "Delete FROM BasePowerFlowsheet Where powerId=?";
		Session session = this.getSession();
		Query q = session.createQuery(hql);
		q.setString(0, powerId);
		q.executeUpdate();
	}

	public List<BasePowerFlowsheet> getByPower(String id) {
		String hql = "FROM BasePowerFlowsheet Where powerId=?";
		Session session = this.getSession();
		Query q = session.createQuery(hql);
		q.setString(0, id);
		List<BasePowerFlowsheet> detail = q.list();
		return detail;

	}
}
