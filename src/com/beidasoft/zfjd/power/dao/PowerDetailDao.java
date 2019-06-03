package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerDetail;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerDetailDao extends TeeBaseDao<PowerDetail>{
    
    public void batchSave(List<PowerDetail> details) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < details.size(); i++) {
            session.save(details.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(Power power) {
        String hql = " delete from PowerDetail where powerDetail.id = '" + power.getId() + "' ";

        deleteOrUpdateByQuery(hql, null);
    }
}
