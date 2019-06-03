package com.beidasoft.xzzf.queries.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.queries.bean.PowerBase;
import com.beidasoft.xzzf.queries.model.PowerBaseModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class PowerBaseDao  extends TeeBaseDao<PowerBase>{
	
	public List<PowerBase> listByPage(int firstResult, int rows,
			PowerBaseModel queryModel) {
		String hql = "from PowerBase where 1=1 ";
		if (!TeeUtility.isNullorEmpty(queryModel.getCode())) {
			hql += " and code like '%" + queryModel.getCode() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			hql += " and name like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerLevel())) {
			hql += " and powerLevel like '%" + queryModel.getPowerLevel() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerType())) {
			hql += " and powerType like '%" + queryModel.getPowerType() + "%'";

		}
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	
	
	public long getTotal(PowerBaseModel queryModel) {
		String hql = "select count(id) from PowerBase where 1=1";
		if (!TeeUtility.isNullorEmpty(queryModel.getCode())) {
			hql += " and code like '%" + queryModel.getCode() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			hql += " and name like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerLevel())) {
			hql += " and powerLevel like '%" + queryModel.getPowerLevel() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerType())) {
			hql += " and powerType like '%" + queryModel.getPowerType() + "%'";

		}
		return super.count(hql, null);
	}




}
