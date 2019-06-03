package com.beidasoft.xzzf.power.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.power.bean.BasePowerLevel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerSelectLevelDao extends TeeBaseDao<BasePowerLevel> {
	/**
	 * 删除对象根据powerId删除
	 * 
	 * @param id
	 */
	public void deleteByPowerId(String powerId) {
		super.delete(powerId);
	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	public List<BasePowerLevel> getLevelList(String id) {
		String hql = "FROM BasePowerLevel Where powerId='" + id + "'";
		return super.find(hql, null);
	}
}
