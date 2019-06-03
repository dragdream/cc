package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerDetailTemp;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerDetailTempDao extends TeeBaseDao<PowerDetailTemp>{
    
    public void batchSave(List<PowerDetailTemp> details) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < details.size(); i++) {
            session.save(details.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(PowerTemp power) {
        String hql = " delete from PowerDetailTemp where powerDetailTemp.id = '" + power.getId() + "' ";

        deleteOrUpdateByQuery(hql, null);
    }
}
