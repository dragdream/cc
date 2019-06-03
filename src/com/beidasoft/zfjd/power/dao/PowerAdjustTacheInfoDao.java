package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerAdjustTacheInfo;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerAdjustTacheInfoDao extends TeeBaseDao<PowerAdjustTacheInfo>{
    
    public List<PowerAdjustTacheInfo> listByPage(int firstResult, int rows, String tacheId) {
        String hql = " from PowerAdjustTacheInfo where adjustTache.id = '" + tacheId + "'" ;
        return super.find(hql, null);
    }
    
    public Long listCount(String tacheId) {
        String hql = "select count(*) from PowerAdjustTacheInfo where adjustTache.id = '" + tacheId + "'" ;
        return super.count(hql, null);
    }
    
    public void batchSave(List<PowerAdjustTacheInfo> adjustTacheInfos) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < adjustTacheInfos.size(); i++) {
            session.save(adjustTacheInfos.get(i));
        }
        tx.commit();
        session.close();
    }
}
