package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerAdjustTache;
import com.beidasoft.zfjd.power.dao.PowerAdjustTacheDao;
import com.beidasoft.zfjd.power.model.PowerAdjustTacheModel;
import com.incors.plaf.alloy.m;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PowerAdjustTacheService extends TeeBaseService{

    @Autowired
    private PowerAdjustTacheDao adjustTacheDao;
    
    public List<PowerAdjustTache> getByAdjustId(String id) {
        return adjustTacheDao.getByAdjustId(id);
    }
    
    public void saveOrUpdate(PowerAdjustTache adjustTache) {
        adjustTacheDao.saveOrUpdate(adjustTache);
    }
    
    public void saveTache(PowerAdjustTacheModel adjustTacheModel) {
        adjustTacheDao.saveTache(adjustTacheModel);
    }
    
    public List<PowerAdjustTache> listByPage(TeeDataGridModel dm, String adjustId) {
        return adjustTacheDao.listByPage(dm.getFirstResult(), dm.getRows(), adjustId);
    }
    
    public Long listCount(String adjustId) {
        return adjustTacheDao.listCount(adjustId);
    }
    
}
