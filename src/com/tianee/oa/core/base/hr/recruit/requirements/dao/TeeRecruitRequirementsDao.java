package com.tianee.oa.core.base.hr.recruit.requirements.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.recruit.requirements.bean.TeeRecruitRequirements;
import com.tianee.oa.core.base.hr.recruit.requirements.model.TeeRecruitRequirementsModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("recruitRequirementsDao")
public class TeeRecruitRequirementsDao extends TeeBaseDao<TeeRecruitRequirements> {

	public long getQueryCount(TeePerson person, TeeRecruitRequirementsModel model) {
		Object[] param = { person.getUuid() };
		String hql = "select count(sid) from TeeRecruitRequirements  where  1 = 1  and createUser.uuid=?";
		long count = count(hql, param);
		return count;

	}

	/**
	 * 分页查询
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	public List<TeeRecruitRequirements> getMeetPageFind(int firstResult, int pageSize, TeeDataGridModel dm, TeeRecruitRequirementsModel model) throws ParseException {
		String hql = "from TeeRecruitRequirements  where  1 = 1  ";
		List list = new ArrayList();
		if (TeeUtility.isNullorEmpty(dm.getSort())) {
			dm.setSort("createTime");
			dm.setOrder("desc");
		}
		hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstResult, pageSize, list);
	}

	/**
	 * 多个删除
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param ids
	 */
	public void delByIds(String ids) {
		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeRecruitRequirements where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 获取多个对象
	 * @date 2015-8-8
	 * @author syl
	 * @param ids
	 */
	public List<TeeRecruitRequirements> getByIds(String ids) {
		List<TeeRecruitRequirements> list = new ArrayList<TeeRecruitRequirements>();
		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = " from TeeRecruitRequirements where sid in (" + ids + ")";
			list = executeQuery(hql, null);
		}
		return list;
	}
}
