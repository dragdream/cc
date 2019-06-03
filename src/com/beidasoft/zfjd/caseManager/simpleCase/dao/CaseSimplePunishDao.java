package com.beidasoft.zfjd.caseManager.simpleCase.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimplePunish;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseSimplePunishDao extends TeeBaseDao<CaseSimplePunish>{
    
    /**
     ** 根据主表id删除
     * @param caseId
     */
    public void deleteByCaseId(String caseId){
        String hql = "delete from CaseSimplePunish where caseId = '"+caseId+"'";
        executeUpdate(hql, null);
    }
    
    /**
     ** 根据主表id查询
     * @param caseId
     * @return
     */
    public List<CaseSimplePunish> getListByCaseId(String caseId) {
        return super.find("from CaseSimplePunish where caseId = '"+caseId+"'");
    }
}
