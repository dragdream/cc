package com.tianee.oa.subsys.salManage.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.salManage.bean.TeeSalFlow;
import com.tianee.oa.subsys.salManage.model.TeeSalFlowModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author CXT
 *
 */
@Repository("salFlowDao")
public class TeeSalFlowDao extends TeeBaseDao<TeeSalFlow>{

	/**
	 * 删除by ids
	 * @param ids
	 */
	public void deleteByIds(String ids){
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeSalFlow where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * 判断是否存在
	 * @param model
	 * @return
	 */
	public boolean checkFlow(TeeSalFlowModel model){
		String hql = "select count(*) from TeeSalFlow where accountId = ?"
				+ " and salYear = ? and salMonth = ?";
		List list = new ArrayList();
		list.add(model.getAccountId());
		list.add(model.getSalYear());
		list.add(model.getSalMonth());
		if(model.getSid() > 0){
			hql = hql + " and sid <> ?";
			list.add(model.getSid());
		}
		long count = countByList(hql, list);
		if(count > 0){
			return true;
		}
		return false;
	}
}
