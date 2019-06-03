package com.beidasoft.xzzf.power.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.power.bean.BaseDiscretion;
import com.beidasoft.xzzf.power.model.DiscretionModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class DiscretionDao extends TeeBaseDao<BaseDiscretion> {
	public List<BaseDiscretion> getByPowerList(String id,TeeDataGridModel dataGridModel) {
		String hql = "FROM BaseDiscretion Where id='" + id + "'";
		List<BaseDiscretion> discretion = super.pageFind(hql,dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		return discretion;
	}


	/**
	 * 根据分页查找用户信息
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<BaseDiscretion> listByPage(int firstResult, int rows,
			DiscretionModel queryModel) {
		String hql = "from BaseDiscretion where 1=1 ";
		if(!TeeUtility.isNullorEmpty(queryModel.getPowerId())){
			hql+="and powerId ='"+queryModel.getPowerId()+"'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerCode())) {
			hql += " and powerCode like '%" + queryModel.getPowerCode() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getBreaklow())) {
			hql += " and breaklow like '%" + queryModel.getBreaklow() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLegalbasis())) {
			hql += " and legalbasis like '%" + queryModel.getLegalbasis()
					+ "%'";

		}
		return super.pageFind(hql, firstResult, rows, null);

	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return super.count("select count(id) from BaseDiscretion", null);
	}

	/**
	 * 重载
	 * 
	 * @return
	 */
	public long getTotal(DiscretionModel queryModel) {
		String hql = "select count(id) from BaseDiscretion where 1=1";
		if(!TeeUtility.isNullorEmpty(queryModel.getPowerId())){
			hql+="and powerId ='"+queryModel.getPowerId()+"'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerCode())) {
			hql += " and powerCode like '%" + queryModel.getPowerCode() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getBreaklow())) {
			hql += " and breaklow like '%" + queryModel.getBreaklow() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLegalbasis())) {
			hql += " and legalbasis like '%" + queryModel.getLegalbasis()
					+ "%'";

		}

		return super.count(hql, null);
	}

}