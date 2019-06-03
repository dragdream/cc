package com.beidasoft.zfjd.power.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerAdjustTache;
import com.beidasoft.zfjd.power.model.PowerAdjustTacheModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;

@Repository
public class PowerAdjustTacheDao extends TeeBaseDao<PowerAdjustTache>{

    public List<PowerAdjustTache> getByAdjustId(String id) {
        String hql = " from PowerAdjustTache where powerAdjustTache.id = '" + id + "'";
        return super.find(hql + " order by examineDate desc", null);
    }
    
    public void saveOrUpdate(PowerAdjustTache powerAdjustTache) {
        super.saveOrUpdate(powerAdjustTache);
    }
    
    public void saveTache(PowerAdjustTacheModel adjustTacheModel) {
        String hql = " update PowerAdjustTache set ";
        List<Object> paramsList = new ArrayList<Object>();
        
        hql = hql + "examineView =? ";
        paramsList.add(adjustTacheModel.getExamineView());
        if(adjustTacheModel.getClosedDateStr() != null && !"".equals(adjustTacheModel.getClosedDateStr())) {
            hql = hql + " , closedDate =?";
            paramsList.add(TeeDateUtil.format(adjustTacheModel.getClosedDateStr(), "yyyy-MM-dd HH:mm"));
        }
        
        hql = hql + "where id =? ";
        paramsList.add(adjustTacheModel.getId());
        
        deleteOrUpdateByQuery(hql, paramsList.toArray());
    }
    
    public List<PowerAdjustTache> listByPage(int firstResult, int rows, String adjustId) {
        String hql = " from PowerAdjustTache where powerAdjustTache.id = '" + adjustId + "'";
        return find(hql + " order by examineDate", null);
    }
    
    public Long listCount(String adjustId) {
        String hql = " select count(*) from PowerAdjustTache where powerAdjustTache.id = '" + adjustId + "'";
        return count(hql, null);
    }
}
