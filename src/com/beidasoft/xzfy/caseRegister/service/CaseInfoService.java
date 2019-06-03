package com.beidasoft.xzfy.caseRegister.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.dao.CaseInfoDao;

@Service
public class CaseInfoService {

	@Autowired
	private CaseInfoDao caseInfoDao;
	
	/**
	 * 初始化案件信息
	 * @param caseId
	 * @param time 
	 * @param loginUser 
	 * @param caseStatusCode
	 * @param caseStatus
	 */
	public void initCaseInfo(FyCaseHandling fyCaseHandlingBean) throws Exception {
		caseInfoDao.initCaseInfo(fyCaseHandlingBean);
	}

}
