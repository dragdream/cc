package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerSubjectTemp;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerSubjectTempDao extends TeeBaseDao<PowerSubjectTemp>{

    public void batchSave(List<PowerSubjectTemp> subjectTemps) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < subjectTemps.size(); i++) {
            session.save(subjectTemps.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(PowerTemp powerTemp) {
        String hql = " delete from PowerSubjectTemp where powerSubjectTemp.id = '" + powerTemp.getId() + "'";
        deleteOrUpdateByQuery(hql, null);
    }
    
    public List<PowerSubjectTemp> findSubjectsByPowerId(int firstResult, int rows, String powerId) {
        String hql = " from PowerSubjectTemp where powerSubjectTemp.id = '" + powerId + "' ";
        return super.pageFind(hql , firstResult, rows, null);
    }
    
    public Long findSubjectsCountByPowerId(String powerId) {
        String hql = "select count(*) from PowerSubjectTemp where powerSubjectTemp.id = '" + powerId + "' ";
        return super.count(hql, null);
    }
}
