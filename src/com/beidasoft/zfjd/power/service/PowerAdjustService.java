package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerAdjust;
import com.beidasoft.zfjd.power.dao.PowerAdjustAuthorityDao;
import com.beidasoft.zfjd.power.dao.PowerAdjustDao;
import com.beidasoft.zfjd.power.dao.PowerAdjustTacheDao;
import com.beidasoft.zfjd.power.model.PowerAdjustModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PowerAdjustService extends TeeBaseService{
    
    @Autowired
    private PowerAdjustDao adjustDao;
    
    @Autowired
    private PowerAdjustTacheDao adjustTacheDao;
    
    @Autowired
    private PowerAdjustAuthorityDao adjustAuthorityDao;
    
    public List<PowerAdjust> listByPage(TeeDataGridModel dm, PowerAdjustModel adjustModel) {
        return adjustDao.listByPage(dm.getFirstResult(), dm.getRows(), adjustModel);
    }
    
    public Long listCount(PowerAdjustModel adjustModel) {
        return adjustDao.listCount(adjustModel);
    }
    
    public PowerAdjust getById(String id) {
        return adjustDao.getById(id);
    }
    
    public void bindRunId(String id, int runId) {
        adjustDao.bindRunId(id, runId);
    }
    
    public void closedAdjust(PowerAdjust powerAdjust) {
        adjustDao.closedAdjust(powerAdjust);
    }
}
