package com.tianee.oa.core.base.fixedAssets.dao;


import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("fixedAssetsInfoDao")
public class TeeFixedAssetsInfoDao extends TeeBaseDao<TeeFixedAssetsInfo> {

	/**
	 * 新增
	 * @author syl
	 * @date 2014-6-7
	 * @param obj
	 */
	public void add(TeeFixedAssetsInfo obj){
		save(obj);
	}
	
	
	/**
	 * 更新
	 * @author syl
	 * @date 2014-6-7
	 * @param obj
	 */
	public void edit(TeeFixedAssetsInfo obj){
		update(obj);
	}
	
	/**
	 * 获取ById
	 * @author syl
	 * @date 2014-6-7
	 * @param sid
	 * @return
	 */
	public TeeFixedAssetsInfo getById(int sid){
		TeeFixedAssetsInfo record = get(sid);
		return record;
	}
	
	
}
