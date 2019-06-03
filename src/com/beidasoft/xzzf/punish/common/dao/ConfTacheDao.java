package com.beidasoft.xzzf.punish.common.dao;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ConfTacheDao  extends TeeBaseDao<ConfTache> {
	
	/**
	 * 获取环节信息
	 * 
	 * @param confTache
	 * @return
	 */
	public List<ConfTache> getConfTacheInfo(ConfTache confTache) {
		String hql = "from ConfTache where 1=1 ";
		if (StringUtils.isNotBlank(confTache.getConfTacheCode())) {
			hql += " and confTacheCode = '" + confTache.getConfTacheCode() + "'";
		}
		if (StringUtils.isNotBlank(confTache.getConfTacheUrl())) {
			hql += " and confTacheUrl like '%" + confTache.getConfTacheUrl() + "%'";
		}
		if (StringUtils.isNotBlank(confTache.getConfTacheName())) {
			hql += " and confTacheName like '%" + confTache.getConfTacheName() + "%'";
		}
		hql += " order by confTacheIndex";
		List<ConfTache> tacheFlowList = executeQuery(hql, null);
		return tacheFlowList;
	}
	
}