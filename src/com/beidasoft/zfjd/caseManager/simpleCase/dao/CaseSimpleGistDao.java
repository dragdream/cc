package com.beidasoft.zfjd.caseManager.simpleCase.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleGist;
import com.tianee.webframe.dao.TeeBaseDao;


@Repository
public class CaseSimpleGistDao extends TeeBaseDao<CaseSimpleGist>{
    
    /**
     ** 根据主表id删除
     * @param caseId
     */
    public void deleteByCaseId(String caseId){
        String hql = "delete from CaseSimpleGist where caseId = '"+caseId+"'";
        executeUpdate(hql, null);
    }
    
    /**
     * *根据主表id查询
     * @param caseId
     * @return
     */
    public List<CaseSimpleGist> getListByCaseId(String caseId){
        return super.find("from CaseSimpleGist where caseId = '"+caseId+"'");
    }
}
