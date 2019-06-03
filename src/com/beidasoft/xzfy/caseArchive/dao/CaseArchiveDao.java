package com.beidasoft.xzfy.caseArchive.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseArchive.bean.CaseArchiveInfo;
import com.tianee.webframe.dao.TeeBaseDao;

@SuppressWarnings("rawtypes")
@Repository
public class CaseArchiveDao extends TeeBaseDao{

	
	/**
	 * 更新
	 * @param caseClose
	 */
	public void updateCaseStatus(CaseArchiveInfo caseArchive){
		StringBuffer str = new StringBuffer();
		str.append("update FY_CASE_HANDLING set ");
		str.append(" CASE_STATUS_CODE='");
		str.append(caseArchive.getCaseStatusCode());
		str.append("',");
		str.append(" CASE_STATUS='");
		str.append(caseArchive.getCaseStatus());
		str.append("',");
		str.append(" CASE_SUB_STATUS_CODE='");
		str.append(caseArchive.getCaseChildeStatusCode());
		str.append("',");
		str.append(" CASE_SUB_STATUS='");
		str.append(caseArchive.getCaseChildeStatus());
		//更新人
		str.append("',");
		str.append(" MODIFIED_USER='");
		str.append(caseArchive.getModifiedUser());
		str.append("',");
		str.append(" MODIFIED_USER_ID='");
		str.append(caseArchive.getModifiedUserId());
		str.append("',");
		str.append(" MODIFIED_TIME='");
		str.append(caseArchive.getModifiedTime());
		
		str.append("' where CASE_ID in(");
		str.append(caseArchive.getCaseIds());
		str.append(")");
		executeNativeUpdate(str.toString(), null);
	}
}
