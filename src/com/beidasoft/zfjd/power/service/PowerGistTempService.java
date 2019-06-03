package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerGistTemp;
import com.beidasoft.zfjd.power.dao.PowerGistTempDao;
import com.beidasoft.zfjd.power.model.PowerGistTempModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;

@Service("powerGistTempService")
public class PowerGistTempService {

    @Autowired
    private PowerGistTempDao powerGistDao;
    
    public List<PowerGistTemp> listByPage(TeeDataGridModel dm, PowerGistTempModel powerGistModel) {
        return powerGistDao.listByPage(dm.getFirstResult(), dm.getRows(), powerGistModel);
    }
    
    public long listCount(PowerGistTempModel powerGistModel) {
        return powerGistDao.listCount(powerGistModel);
    }
    
    public List<PowerGistTemp> findGistTempsByPowerId(TeeDataGridModel dm, String powerId) {
        return powerGistDao.findGistTempsByPowerId(dm.getFirstResult(), dm.getRows(), powerId);
    }
    
    public long findGistTempsCountByPowerId(String powerId) {
        return powerGistDao.findGistTempsCountByPowerId(powerId);
    }
}
