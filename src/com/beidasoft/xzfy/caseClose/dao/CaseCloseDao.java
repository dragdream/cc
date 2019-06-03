package com.beidasoft.xzfy.caseClose.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseClose.bean.CaseCloseInfo;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseCloseDao extends TeeBaseDao<Object>{

	
	/**
	 * 获取并案的案件ID集合
	 * @param caseId
	 * @return
	 */
	public List<Object> getCaseMergeIds(String caseId){
		List<Object> list = new ArrayList<Object>();
		StringBuffer str = new StringBuffer();
		str.append("select case_id from FY_CASE_HANDLING where mergerCaseId='");
		str.append(caseId);
		str.append("'");
		list = getBySql(str.toString(), new Object());
		return list;
	}
	
	/**
	 * 更新案件结案状态
	 * @param caseClose
	 */
	public void updateCaseStatus(CaseCloseInfo caseClose){
		
		StringBuffer str = new StringBuffer();
		str.append("update FY_CASE_HANDLING set ");
		str.append(" CASE_STATUS_CODE='");
		str.append(caseClose.getCaseStatusCode());
		str.append("',");
		str.append(" CASE_STATUS='");
		str.append(caseClose.getCaseStatus());
		str.append("',");
		str.append(" CASE_SUB_STATUS_CODE='");
		str.append(caseClose.getCaseChildeStatusCode());
		str.append("',");
		str.append(" CASE_SUB_STATUS='");
		str.append(caseClose.getCaseChildeStatus());
		//更新人
		str.append("',");
		str.append(" MODIFIED_USER='");
		str.append(caseClose.getModifiedUser());
		str.append("',");
		str.append(" MODIFIED_USER_ID='");
		str.append(caseClose.getModifiedUserId());
		str.append("',");
		str.append(" MODIFIED_TIME='");
		str.append(caseClose.getModifiedTime());
		
		str.append("' where CASE_ID in(");
		str.append(caseClose.getCaseIds());
		str.append(")");
		executeNativeUpdate(str.toString(), null);
	}
}
