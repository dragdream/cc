package com.beidasoft.zfjd.caseManager.simpleCase.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimplePower;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseSimplePowerDao extends TeeBaseDao<CaseSimplePower>{
    
    /**
     ** 根据案件主表id查询
     * @param caseId
     * @return
     */
    public List<CaseSimplePower> getListByCaseId(String caseId){
        return super.find("from CaseSimplePower where caseId = '"+caseId+"' ");
    }
    
    /**
     ** 根据主表id删除
     * @param caseId
     */
    public void deleteByCaseId(String caseId){
        String hql = "delete from CaseSimplePower where caseId = '"+caseId+"'";
        executeUpdate(hql, null);
    }
    
}
