package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerOperation;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerOperationDao extends TeeBaseDao<PowerOperation>{
    
    public void batchSave(List<PowerOperation> operations) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < operations.size(); i++) {
            session.save(operations.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public List<PowerOperation> getPowerOpts(String powerTempId) {
        String hql = " from PowerOperation where powerOptTemp.id = '" + powerTempId + "'";
        return super.find(hql, null);
    }

}
