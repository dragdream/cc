package com.tianee.oa.core.base.hr.recruit.dao;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.recruit.bean.TeeHrFilter;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("hrFilterDao")
public class TeeHrFilterDao extends TeeBaseDao<TeeHrFilter>{

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
			String hql = " delete from TeeHrFilter where sid in (" + ids+ ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}	

	
}
