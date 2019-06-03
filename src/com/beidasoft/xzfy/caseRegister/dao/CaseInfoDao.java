package com.beidasoft.xzfy.caseRegister.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseInfoDao extends TeeBaseDao<FyCaseHandling> {

    /**
     * 初始化案件信息
     * 
     * @param fyCaseHandlingBean
     */
    public void initCaseInfo(FyCaseHandling fyCaseHandlingBean) {
        save(fyCaseHandlingBean);
    }

}
