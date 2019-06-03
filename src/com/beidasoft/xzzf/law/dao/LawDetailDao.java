package com.beidasoft.xzzf.law.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.law.bean.BaseLawDetail;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class LawDetailDao extends TeeBaseDao<BaseLawDetail> {

	public List<BaseLawDetail> getLawListById(String id,
			TeeDataGridModel dataGridModel) {
		String hql = "FROM BaseLawDetail Where lawId = '" + id + "'";
		List<BaseLawDetail> detail = super.pageFind(hql,
				dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		return detail;
	}
}
