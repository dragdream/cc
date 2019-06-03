package com.beidasoft.xzzf.informationEntry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryPunish;
import com.beidasoft.xzzf.informationEntry.bean.InforEntryReport;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 字表处罚决定DAO类
 */
@Repository
public class InforEntryPunishDao extends TeeBaseDao<InforEntryPunish> {

	/**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public InforEntryPunish getbyCaseId(String caseId){

		String hql = "from InforEntryPunish where caseId = '"+caseId+"'";
		
		List<InforEntryPunish> find = super.find(hql, null);
		
		if (!find.isEmpty()) {
			return find.get(0);
		}
		return null;
	}
	
}
