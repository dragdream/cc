package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerGist;
import com.beidasoft.zfjd.power.model.PowerGistModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerGistDao extends TeeBaseDao<PowerGist>{
    
    public void batchSave(List<PowerGist> gists) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < gists.size(); i++) {
            session.save(gists.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public void deleteByPowerId(Power power) {
        String hql = " delete from PowerGist where powerGist.id = '" + power.getId() + "'";
        deleteOrUpdateByQuery(hql, null);
    }

    public List<PowerGist> listByPage(int firstResult, int rows, PowerGistModel powerGistModel) {
        String hql = " from PowerGist tpg where powerGist.isDelete = 0 " ;
        if(powerGistModel.getLawName() != null && !"".equals(powerGistModel.getLawName())) {
            hql = hql + " and lawName like '%" + powerGistModel.getLawName() + "%' ";
        }
        if(powerGistModel.getPowerName() != null && !"".equals(powerGistModel.getPowerName())) {
            hql = hql + " and powerGist.name like '%" + powerGistModel.getPowerName() + "%' ";
        }
        if(powerGistModel.getPowerType() != null && !"-1".equals(powerGistModel.getPowerType())) {
            hql = hql + " and powerGist.powerType = '" + powerGistModel.getPowerType() + "' ";
        }
        if(powerGistModel.getGistType() != null && !"-1".equals(powerGistModel.getGistType())) {
            hql = hql + " and gistType = '" + powerGistModel.getGistType() + "' ";
        }
        if(powerGistModel.getSubjectId() != null && !"".equals(powerGistModel.getSubjectId())) {
            hql = hql + " and powerGist.subjectId = '" + powerGistModel.getSubjectId() + "' ";
        }
        if(powerGistModel.getSubjectIds() != null && !"".equals(powerGistModel.getSubjectIds())) {
            hql = hql + " and powerGist.subjectId in ('" + powerGistModel.getSubjectIds().replace(",", "','") + "') ";
        }
        
        return super.pageFind(hql + " order by powerGist.name, powerGist.powerType, gistType ", firstResult, rows, null);
    }
    
    public long listCount(PowerGistModel powerGistModel) {
        String hql = "select count(*) from PowerGist tpg where powerGist.isDelete = 0 " ;
        if(powerGistModel.getLawName() != null && !"".equals(powerGistModel.getLawName())) {
            hql = hql + " and lawName like '%" + powerGistModel.getLawName() + "%' ";
        }
        if(powerGistModel.getPowerName() != null && !"".equals(powerGistModel.getPowerName())) {
            hql = hql + " and powerGist.name like '%" + powerGistModel.getPowerName() + "%' ";
        }
        if(powerGistModel.getPowerType() != null && !"-1".equals(powerGistModel.getPowerType())) {
            hql = hql + " and powerGist.powerType = '" + powerGistModel.getPowerType() + "' ";
        }
        if(powerGistModel.getGistType() != null && !"-1".equals(powerGistModel.getGistType())) {
            hql = hql + " and gistType = '" + powerGistModel.getGistType() + "' ";
        }
        if(powerGistModel.getSubjectId() != null && !"".equals(powerGistModel.getSubjectId())) {
            hql = hql + " and powerGist.subjectId = '" + powerGistModel.getSubjectId() + "' ";
        }
        if(powerGistModel.getSubjectIds() != null && !"".equals(powerGistModel.getSubjectIds())) {
            hql = hql + " and powerGist.subjectId in ('" + powerGistModel.getSubjectIds().replace(",", "','") + "') ";
        }
        
        return super.count(hql, null);
    }
    
    public List<PowerGist> findGistsByPowerId(int firstResult, int rows, String powerId) {
        String hql = " from PowerGist where powerGist.id = '" + powerId + "' ";
        
        return super.pageFind(hql + " order by gistCode ", firstResult, rows, null);
    }
    
    public Long findGistsCountByPowerId(String powerId) {
        String hql = "select count(*) from PowerGist where powerGist.id = '" + powerId + "' ";
        
        return super.count(hql, null);
    }
    
    /**
     * 
    * @Function: findGistsByPowerIds()
    * @Description: 通过职权ID，依据类型查询依据
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月15日 下午4:57:41 
    *
     */
    public List<PowerGist> findGistsByPowerIds(int firstResult, int rows, PowerGistModel pgModel) {
        String hql = " from PowerGist where is_delete = 0 ";
        if (pgModel.getPowerId() != null && !"".equals(pgModel.getPowerId())) {
            hql = hql + " and powerGist.id in('" + pgModel.getPowerId().replace(",", "','") + "')  ";
        }
        if (pgModel.getGistType() != null && !"".equals(pgModel.getGistType())) {
            hql = hql + " and gist_type = '" + pgModel.getGistType() + "' ";
        }
        if (pgModel.getId() != null && !"".equals(pgModel.getId())) {
            hql = hql + " and id in('" + pgModel.getId().replace(",", "','") + "')  ";
        }
        return super.pageFind(hql + " order by gistCode ", firstResult, rows, null);
    }
    
}
