package com.beidasoft.xzzf.punish.common.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.PunishTache;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PunishTacheDao  extends TeeBaseDao<PunishTache> {
	
	public List<PunishTache> getPunishFlowInfo(int firstResult, int rows,int sid) {
		
		String hql = "from PunishFLow";
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	//对应环节
	public List<PunishTache> getFlow(String baseId,String punishTacheId) {
		String hql = " from PunishTache where 1=1 ";
		hql += " and baseId = '" +  baseId + "'";
		return super.find(hql, null);
	}
	
	public List<PunishTache> getbyindex(String baseId, String index) {
		String hql = " from PunishTache where 1 = 1 ";
		hql += " and baseId = '" + baseId +"'";
		hql += " and confTacheIndex = " + index;
		return super.find(hql, null);
	}
}
