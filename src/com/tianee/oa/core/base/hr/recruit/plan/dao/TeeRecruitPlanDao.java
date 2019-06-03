package com.tianee.oa.core.base.hr.recruit.plan.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.base.hr.recruit.plan.model.TeeRecruitPlanModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("recruitPlanDao")
public class TeeRecruitPlanDao extends
		TeeBaseDao<TeeRecruitPlan> {

	public long getQueryCount(TeePerson person,
			TeeRecruitPlanModel model) {
		Object[] param = {person.getUuid()};
		String hql = "select count(sid) from TeeRecruitPlan  where  1 = 1  and createUser.uuid=?";
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
	public List<TeeRecruitPlan> getMeetPageFind(int firstResult,
			int pageSize, TeeDataGridModel dm, TeeRecruitPlanModel model)
			throws ParseException {
		String hql = "from TeeRecruitPlan  where  1 = 1  ";
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
			String hql = "delete from TeeRecruitPlan where sid in ("
					+ ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
     * 根据创建人和状态获取总数
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public  long getStatusCountDao(TeePerson person , TeeRecruitPlanModel model) {
        Object[] values = {person.getUuid()  ,  model.getPlanStatus()};
        String hql = "select count(sid) from TeeRecruitPlan  where approvePerson.uuid = ? and planStatus = ?  ";
        long count = count(hql, values);
        return count;
    }

    /**
     * 根据
     * @author syl
     * @date 2014-6-25
     * @param model
     * @return
     * @throws ParseException
     */
	public List<TeeRecruitPlan> getApprovPlanList(TeeRecruitPlanModel model){
		String hql = "from TeeRecruitPlan  where  planStatus = 1 ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(model.getPlanName())){
			list.add("%" + model.getPlanName() + "%");
			hql = hql + " and planName like ?";
		}
		
		hql = hql + " order by planName";
		return executeQueryByList(hql, list);
	}

}
