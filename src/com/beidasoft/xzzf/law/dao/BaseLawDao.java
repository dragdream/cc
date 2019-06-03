package com.beidasoft.xzzf.law.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.law.bean.BaseLaw;
import com.beidasoft.xzzf.law.model.BaseLawModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class BaseLawDao extends TeeBaseDao<BaseLaw> {
	public List<BaseLaw> getByPowerList(String id,
			TeeDataGridModel dataGridModel) {
		String hql = "FROM BaseLaw Where id='" + id + "'";
		List<BaseLaw> law = super.pageFind(hql, dataGridModel.getFirstResult(),
				dataGridModel.getRows(), null);
		return law;
	}

	
	/**
	 * 根据分页查找用户信息
	 * 
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<BaseLaw> listByPage(int firstResult, int rows,
			BaseLawModel queryModel) {
		String hql = "from BaseLaw where 1=1 ";

		if (!TeeUtility.isNullorEmpty(queryModel.getLawNum())) {
			hql += " and lawNum like '%" + queryModel.getLawNum() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			hql += " and name like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getWord())) {
			hql += " and word like '%" + queryModel.getWord() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())) {
			hql += " and submitlawLevel like '%" + queryModel.getSubmitlawLevel() + "%'";

		}
		return super.pageFind(hql, firstResult, rows, null);
	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return super.count("select count(id) from BaseLaw", null);
	}

	/**
	 * 重载
	 * 
	 * @return
	 */
	public long getTotal(BaseLawModel queryModel) {
		String hql = "select count(id) from BaseLaw where 1=1";

		if (!TeeUtility.isNullorEmpty(queryModel.getLawNum())) {
			hql += " and lawNum like '%" + queryModel.getLawNum() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			hql += " and name like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getWord())) {
			hql += " and word like '%" + queryModel.getWord() + "%'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())) {
			hql += " and submitlawLevel like '%" + queryModel.getSubmitlawLevel() + "%'";

		}
		return super.count(hql, null);
	}

	public long getTotal(String id) {
		String hql = "select count(id) from BaseLawDetail  where lawId ='" + id+ "'";
		return super.count(hql, null);
	}

}