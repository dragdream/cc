package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerLevelTemp;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerLevelTempDao extends TeeBaseDao<PowerLevelTemp>{

    public void batchSave(List<PowerLevelTemp> levels) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < levels.size(); i++) {
            session.save(levels.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(PowerTemp power) {
        String hql = " delete from PowerLevelTemp where powerLevelTemp.id = '" + power.getId() + "'";
        deleteOrUpdateByQuery(hql, null);
    }
}
