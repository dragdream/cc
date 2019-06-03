package com.tianee.oa.core.base.hr.training.record.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.training.record.bean.TeeTrainingRecord;
import com.tianee.oa.core.base.hr.training.record.model.TeeTrainingRecordModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("trainingRecordDao")
public class TeeTrainingRecordDao extends TeeBaseDao<TeeTrainingRecord> {
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
	public List<TeeTrainingRecord> getRecordPageFind(int firstResult,
			int pageSize, TeeDataGridModel dm,  TeeTrainingRecordModel model)
			throws ParseException {
		String hql = "from TeeTrainingRecord  where  1 = 1  ";
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
			String hql = "delete from TeeTrainingRecord where sid in ("
					+ ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
			
}
