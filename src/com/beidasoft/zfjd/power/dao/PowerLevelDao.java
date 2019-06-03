package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerLevel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerLevelDao extends TeeBaseDao<PowerLevel>{

    public void batchSave(List<PowerLevel> levels) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < levels.size(); i++) {
            session.save(levels.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(Power power) {
        String hql = " delete from PowerLevel where powerLevel.id = '" + power.getId() + "'";
        deleteOrUpdateByQuery(hql, null);
    }
}
