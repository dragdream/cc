package com.tianee.oa.core.base.hr.recruit.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.recruit.bean.TeeHrPool;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrPoolModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("hrPoolDao")
public class TeeHrPoolDao  extends TeeBaseDao<TeeHrPool> {


	/**
	 * 多个删除
	 * @author syl
	 * @date 2014-6-21
	 * @param ids
	 */
	public void deleteByIds(String ids){
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = " delete from TeeHrPool where sid in (" + ids+ ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 查询多个ById
	 * @author syl
	 * @date 2014-6-21
	 * @param ids
	 */
	public List<TeeHrPool> selectByIds(String ids){
		List<TeeHrPool> list = new ArrayList<TeeHrPool>();
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "  from TeeHrPool where sid in (" + ids+ ")";
			list =  executeQuery(hql,  null);
		}
		return list;
	}
	
	/**
	 * 查询---模糊查询
	 * @author syl
	 * @date 2014-6-27
	 * @param model
	 * @return
	 */
	public List<TeeHrPool> selectPool(TeeHrPoolModel model){
		String hql = "from TeeHrPool pool where 1=1";
		List values = new ArrayList();
		if(!TeeUtility.isNullorEmpty(model.getEmployeeName())){
			hql = hql + " and employeeName like ?";
			values.add( "%"+ model.getEmployeeName() + "%");
		}
		hql = hql + " order by employeeName";
		List<TeeHrPool> list = executeQueryByList(hql, values);
		return list;
	}
}
