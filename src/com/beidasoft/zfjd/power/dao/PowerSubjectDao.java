package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerSubject;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerSubjectDao extends TeeBaseDao<PowerSubject>{
    
    public List<PowerSubject> findSubjectsByPowerId(int firstResult, int rows, String powerId) {
        String hql = " from PowerSubject where powerSubject.id = '" + powerId + "' ";
        return super.pageFind(hql , firstResult, rows, null);
    }
    
    public Long findSubjectsCountByPowerId(String powerId) {
        String hql = "select count(*) from PowerSubject where powerSubject.id = '" + powerId + "' ";
        return super.count(hql, null);
    }
    /**
     * 根据主体id查询
     * @param firstResult
     * @param rows
     * @param subjectId
     * @return
     */
    public List<PowerSubject> findSubjectsBySubjectrId(int firstResult, int rows, String subjectId) {
        String hql = " from PowerSubject where isDelete = 0 and subject.id = '" + subjectId + "' ";
        return super.pageFind(hql , firstResult, rows, null);
    }
    /**
     * 根据主体id查询
     * @param subjectId
     * @return
     */
    public Long findSubjectsCountBySubjectId(String subjectId) {
        String hql = "select count(*) from PowerSubject where isDelete = 0 and subject.id = '" + subjectId + "' ";
        return super.count(hql, null);
    }
    
}
