package com.beidasoft.xzfy.caseRegister.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseRegisterOriginal;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CompletedDao extends TeeBaseDao<FyCaseRegisterOriginal> {

	public void addOrUpdateCaseRegistered(FyCaseRegisterOriginal fyCaseRegisterOriginal) {
		save(fyCaseRegisterOriginal);
	}

}
