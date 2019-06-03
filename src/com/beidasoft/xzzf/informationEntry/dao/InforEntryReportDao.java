package com.beidasoft.xzzf.informationEntry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryReport;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 子表结案报告书DAO类
 */
@Repository
public class InforEntryReportDao extends TeeBaseDao<InforEntryReport> {
	
	/**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public InforEntryReport getbyCaseId(String caseId){

		String hql = "from InforEntryReport where caseId = '"+caseId+"'";
		
		List<InforEntryReport> find = super.find(hql, null);
		
		if (!find.isEmpty()) {
			return find.get(0);
		}
		return null;
	}
}
