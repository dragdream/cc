package com.beidasoft.xzzf.power.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.power.bean.BasePowerGist;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerSelectGistDao extends TeeBaseDao<BasePowerGist> {
	public List<BasePowerGist> getByPowerList(String id,
			TeeDataGridModel dataGridModel) {
		String hql = "FROM BasePowerGist Where powerId='" + id + "'";
		List<BasePowerGist> gist = super.pageFind(hql,
				dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		return gist;
	}
}
