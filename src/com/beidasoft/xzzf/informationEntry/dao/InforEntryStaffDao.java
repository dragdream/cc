package com.beidasoft.xzzf.informationEntry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryStaff;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 子表执法人员DAO类
 */
@Repository
public class InforEntryStaffDao extends TeeBaseDao<InforEntryStaff> {
	
	/**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public List<InforEntryStaff> getbyCaseId(String caseId){
		String hql = "from InforEntryStaff where caseId = '"+caseId+"'";
		List<InforEntryStaff> staffs = super.find(hql, null);
		return staffs;
	}
	
}
