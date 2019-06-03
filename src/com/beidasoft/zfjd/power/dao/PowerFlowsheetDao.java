package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerFlowsheet;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerFlowsheetDao extends TeeBaseDao<PowerFlowsheet>{
    
    public void batchSave(List<PowerFlowsheet> flowsheets) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < flowsheets.size(); i++) {
            session.save(flowsheets.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(Power power) {
        String hql = " delete from PowerFlowsheet where powerFlowsheet.id = '" + power.getId() + "'";
        
        deleteOrUpdateByQuery(hql, null);
    }
}
