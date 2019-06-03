package com.beidasoft.xzzf.informationEntry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryDecision;
import com.beidasoft.xzzf.informationEntry.bean.InforEntryItem;
import com.beidasoft.xzzf.informationEntry.model.InforEntryDecisionModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 子表查封扣押决定书DAO类
 */
@Repository
public class InforEntryDecisionDao extends TeeBaseDao<InforEntryDecision> {
	/**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public InforEntryDecision getbyCaseId(String caseId){
		String hql = "from InforEntryDecision where caseId = '"+caseId+"'";
		List<InforEntryDecision> items = super.find(hql, null);
		if (!items.isEmpty()) {
			return items.get(0);
		}
		return null;
	}
	
}
