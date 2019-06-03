package com.tianee.oa.core.base.address.dao;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.address.bean.TeeAddress;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TeeAddressDao extends TeeBaseDao<TeeAddress> {

	
	/**
	 * 根据Ids  删除  批量伤处
	 * @author syl
	 * @date 2014-3-16
	 * @param ids
	 * @return
	 */
	public long delelteByIds(String ids){
		long count = 0;
		if(TeeUtility.isNullorEmpty(ids)){
			return count;
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		String hql = "delete from TeeAddress where sid in (" + ids + ")" ;
		return deleteOrUpdateByQuery(hql, null);
	}
	
}
