package com.tianee.oa.core.base.hr.settings.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.settings.bean.TeeAnnualLeave;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("annualLeaveDao")
public class TeeAnnualLeaveDao extends TeeBaseDao<TeeAnnualLeave> {

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
			String hql = "delete from TeeAnnualLeave where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @function: 返回全部数据集合
	 * @author: wyw
	 * @data: 2014年9月4日
	 * @return List<TeeAnnualLeave>
	 */
	public List<TeeAnnualLeave> getObjList(){
		String hql = " from TeeAnnualLeave leave order by leave.yearCount";
		return executeQuery(hql, null);
	}

}
