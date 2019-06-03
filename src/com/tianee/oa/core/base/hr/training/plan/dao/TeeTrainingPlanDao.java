package com.tianee.oa.core.base.hr.training.plan.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.training.plan.bean.TeeTrainingPlan;
import com.tianee.oa.core.base.hr.training.plan.model.TeeTrainingPlanModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("trainingPlanDao")
public class TeeTrainingPlanDao extends
		TeeBaseDao<TeeTrainingPlan> {

	public long getQueryCount(TeePerson person,
			TeeTrainingPlanModel model) {
		Object[] param = {person.getUuid()};
		String hql = "select count(sid) from TeeTrainingPlan  where  1 = 1  and createUser.uuid=?";
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
	public List<TeeTrainingPlan> getMeetPageFind(int firstResult,
			int pageSize, TeeDataGridModel dm, TeeTrainingPlanModel model)
			throws ParseException {
		String hql = "from TeeTrainingPlan  where  1 = 1  ";
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
			String hql = "delete from TeeTrainingPlan where sid in ("
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
    public  long getStatusCountDao(TeePerson person , TeeTrainingPlanModel model) {
        Object[] values = {person.getUuid()  ,  model.getPlanStatus()};
        String hql = "select count(sid) from TeeTrainingPlan  where approvePerson.uuid = ? and planStatus = ?  ";
        long count = count(hql, values);
        return count;
    }

    /**
     * 获取所有的记录
     * @author syl
     * @date 2014-6-21
     * @return
     */
    public List<TeeTrainingPlan> getAllPlan(){
    	String hql = "from TeeTrainingPlan";
    	List<TeeTrainingPlan> list = executeQuery(hql, null);
    	return list;
    }
}
