package com.tianee.oa.subsys.budget.dao;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.budget.bean.TeeBudgetReg;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("budgetRegDao")
public class TeeBudgetRegDao extends TeeBaseDao<TeeBudgetReg> {

	/**
	 * @function: 删除数据
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param ids
	 *            void
	 */
	public void delByIds(String ids) {
		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeBudgetReg where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	
	
	
}
