package com.tianee.oa.core.base.fixedAssets.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsRecord;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("fixedAssetsRecordDao")
public class TeeFixedAssetsRecordDao extends TeeBaseDao<TeeFixedAssetsRecord> {

	/**
	 * 新增
	 * @author syl
	 * @date 2014-6-7
	 * @param obj
	 */
	public void add(TeeFixedAssetsRecord obj){
		save(obj);
	}
	
	
	/**
	 * 更新
	 * @author syl
	 * @date 2014-6-7
	 * @param obj
	 */
	public void edit(TeeFixedAssetsRecord obj){
		update(obj);
	}
	
	/**
	 * 获取ById
	 * @author syl
	 * @date 2014-6-7
	 * @param sid
	 * @return
	 */
	public TeeFixedAssetsRecord getById(int sid){
		TeeFixedAssetsRecord record = get(sid);
		return record;
	}
	
	/**
	 * 根据固定资产获取所有领用和返库记录
	 * @author syl
	 * @date 2014-6-7
	 * @param assetsId
	 * @param optType   0 - 领用   1- 返库    2-报修  3-报修返库  - 4 报废  空全部
	 * @return
	 */
	public List<TeeFixedAssetsRecord> selectByAssetsId(int assetsId , String optType){
		List values = new ArrayList();
		values.add(assetsId);
		String hql = "from TeeFixedAssetsRecord record where record.fixedAssets.sid = ? ";
		if(!TeeUtility.isNullorEmpty(optType)){
			hql = hql = " and optType = ?";
			values.add(optType);
		}
		hql = hql + " order by record.optDate desc";
		List<TeeFixedAssetsRecord> list = executeQueryByList(hql, values);
		return list;
	}
	
}
