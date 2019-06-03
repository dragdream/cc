package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerGistTemp;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.beidasoft.zfjd.power.model.PowerGistTempModel;
import com.beidasoft.zfjd.power.model.PowerTempModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerGistTempDao extends TeeBaseDao<PowerGistTemp>{
    
    public void batchSave(List<PowerGistTemp> gists) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < gists.size(); i++) {
            session.save(gists.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(PowerTemp power) {
        String hql = " delete from PowerGistTemp where powerGistTemp.id = '" + power.getId() + "'";
        deleteOrUpdateByQuery(hql, null);
    }

    public List<PowerGistTemp> listByPage(int firstResult, int rows, PowerGistTempModel powerGistModel) {
        
        String hql = " from PowerGistTemp tpg where isDelete = 0 " ;
        
        if(powerGistModel.getPowerType() != null && !"".equals(powerGistModel.getPowerType())) {
            hql = hql + " and powerGist.powerType = '" + powerGistModel.getPowerType() + "' ";
        }
        
        if(powerGistModel.getLawName() != null && !"".equals(powerGistModel.getLawName())) {
            hql = hql + " and powerGist.powerType = '" + powerGistModel.getPowerType() + "' ";
        }
        
        if(powerGistModel.getSubjectId() != null) {
            hql = hql + " and powerGist.subjectId = '" + powerGistModel.getSubjectId() + "' ";
        }
        
        return super.pageFind(hql + " order by gistCode ", firstResult, rows, null);
    }
    
    public long listCount(PowerGistTempModel powerGistModel) {
        String hql = "select count(*) from PowerGistTemp tpg where isDelete = 0 " ;
        
        if(powerGistModel.getPowerId() != null && !"".equals(powerGistModel.getPowerId())) {
            hql = hql + " and powerGist.id = '" + powerGistModel.getPowerId() + "' ";
        }
        
        if(powerGistModel.getPowerType() != null && !"".equals(powerGistModel.getPowerType())) {
            hql = hql + " and powerGist.powerType = '" + powerGistModel.getPowerType() + "' ";
        }
        
        if(powerGistModel.getLawName() != null && !"".equals(powerGistModel.getLawName())) {
            hql = hql + " and powerGist.powerType = '" + powerGistModel.getPowerType() + "' ";
        }
        
        if(powerGistModel.getSubjectId() != null) {
            hql = hql + " and powerGist.subjectId = '" + powerGistModel.getSubjectId() + "' ";
        }
        return super.count(hql, null);
    }
    
    public List<PowerGistTemp> findGistTempsByPowerId(int firstResult, int rows, String powerId) {
        String hql = " from PowerGistTemp where powerGistTemp.id = '" + powerId + "' ";
        
        return super.pageFind(hql + " order by gistCode ", firstResult, rows, null);
    }
    
    public Long findGistTempsCountByPowerId(String powerId) {
        String hql = "select count(*) from PowerGistTemp where powerGistTemp.id = '" + powerId + "' ";
        
        return super.count(hql, null);
    }
}
