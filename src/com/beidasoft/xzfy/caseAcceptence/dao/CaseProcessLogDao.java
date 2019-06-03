package com.beidasoft.xzfy.caseAcceptence.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseAcceptence.bean.CaseProcessLogInfo;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseProcessLogDao extends TeeBaseDao<CaseProcessLogInfo>{

	/**
	 * 保存流程日志
	 * @param process
	 */
	public void addCaseProcessLog(CaseProcessLogInfo process){
		save(process);
	}
	
}
