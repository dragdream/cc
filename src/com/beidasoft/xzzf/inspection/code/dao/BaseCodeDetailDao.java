package com.beidasoft.xzzf.inspection.code.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.code.bean.BaseCodeDetail;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class BaseCodeDetailDao extends TeeBaseDao<BaseCodeDetail> {
	/**
	 * 查询代码表（根据MainKey）
	 * @param mainKey
	 * @return
	 */
	public List<BaseCodeDetail> getBaseCodeDetails(String mainKey) {
		String hql = " from BaseCodeDetail where mainKey = '"+ mainKey +"'";
		return super.find(hql, null);
	}
}
