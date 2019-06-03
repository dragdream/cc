package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerDiscretionary;
import com.beidasoft.zfjd.power.model.PowerDiscretionaryModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class PowerDiscretionaryDao extends TeeBaseDao<PowerDiscretionary>{
    
    /**
     * 
    * @Function: listByPage
    * @Description: 分页查询自由裁量权
    *
    * @param:firstResult
    * @param:rows
    * @param:discretionaryModel
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenqr
    * @date: 2019年1月14日 上午10:58:29 
    *
     */
    public List<PowerDiscretionary> listByPage(int firstResult, int rows, PowerDiscretionaryModel discretionaryModel) {
        String hql = " from PowerDiscretionary where isDelete = 0 ";
        if(discretionaryModel.getIllegalFact() != null && !"".equals(discretionaryModel.getIllegalFact())) {
            hql = hql + " and illegalFact like '%" + discretionaryModel.getIllegalFact() + "%'";
        }
        if(discretionaryModel.getPunishStandard() != null && !"".equals(discretionaryModel.getPunishStandard())) {
            hql = hql + " and punishStandard like '%" + discretionaryModel.getPunishStandard() + "%'";
        }
        if(discretionaryModel.getPowerId() != null && !"".equals(discretionaryModel.getPowerId())) {
            hql = hql + " and powerDeiscretionary.id = '" + discretionaryModel.getPowerId() + "'";
        }
        return super.pageFind(hql + " order by createDate DESC ", firstResult, rows, null);
    }
    /**
     * 
    * @Function: listCount
    * @Description: 自由裁量权分页总数
    *
    * @param:discretionaryModel
    * @return：
    * @throws：
    *
    * @author: chenqr
    * @date: 2019年1月14日 上午11:00:17 
    *
     */
    public Long listCount(PowerDiscretionaryModel discretionaryModel) {
        String hql = "select count(*) from PowerDiscretionary where isDelete = 0 ";
        if(discretionaryModel.getIllegalFact() != null && !"".equals(discretionaryModel.getIllegalFact())) {
            hql = hql + " and illegalFact like '%" + discretionaryModel.getIllegalFact() + "%'";
        }
        if(discretionaryModel.getPunishStandard() != null && !"".equals(discretionaryModel.getPunishStandard())) {
            hql = hql + " and punishStandard like '%" + discretionaryModel.getPunishStandard() + "%'";
        }
        if(discretionaryModel.getPowerId() != null && !"".equals(discretionaryModel.getPowerId())) {
            hql = hql + " and powerDeiscretionary.id = '" + discretionaryModel.getPowerId() + "'";
        }
        return super.count(hql, null);
    }
    
    public PowerDiscretionary get(String id) {
        return super.get(id);
    }
    
    public void saveOrUpdate(PowerDiscretionary discretionary) {
        super.saveOrUpdate(discretionary);
    }
    
    public void deleteById(String id) {
        String hql = " update PowerDiscretionary set isDelete = 1 where id = '" + id + "'" ;
        
        deleteOrUpdateByQuery(hql, null);
    }
    
    /**
     * 
    * @Function: listDiscretionaryByPowerIds()
    * @Description: 通过职权ID查询自由裁量基准
    *
    * @param: discretionaryModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月21日 下午6:12:04 
    *
     */
    public List<PowerDiscretionary> listDiscretionaryByPowerIds(int firstResult, int rows, PowerDiscretionaryModel discretionaryModel) {
        String hql = " from PowerDiscretionary where isDelete = 0 ";
        if(!TeeUtility.isNullorEmpty(discretionaryModel.getPowerId())) {
            hql = hql + " and powerDeiscretionary.id in('" + discretionaryModel.getPowerId().replace(",", "','") + "') ";
        }
        if (!TeeUtility.isNullorEmpty(discretionaryModel.getId())) {
            hql = hql + " and id in ('" + discretionaryModel.getId().replace(",", "','") + "') ";
        }
        return super.pageFind(hql + " order by createDate DESC ", firstResult, rows, null);
    }
}
