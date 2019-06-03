package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerFlowsheetTemp;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerFlowsheetTempDao extends TeeBaseDao<PowerFlowsheetTemp>{
    
    public void batchSave(List<PowerFlowsheetTemp> flowsheets) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < flowsheets.size(); i++) {
            session.save(flowsheets.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(PowerTemp power) {
        String hql = " delete from PowerFlowsheetTemp where powerFlowsheetTemp.id = '" + power.getId() + "'";
        
        deleteOrUpdateByQuery(hql, null);
    }
}
