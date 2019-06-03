package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerAdjustAuthority;
import com.beidasoft.zfjd.power.dao.PowerAdjustAuthorityDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

import net.sf.json.JSONObject;

@Service
public class PowerAdjustAuthorityService extends TeeBaseService{

    @Autowired
    private PowerAdjustAuthorityDao adjustAuthorityDao;
    
    public List<PowerAdjustAuthority> listByPage(TeeDataGridModel dm, String id) {
        return adjustAuthorityDao.listByPage(dm.getFirstResult(), dm.getRows(), id);
    }
    
    public Long listCount(String id) {
        return adjustAuthorityDao.listCount(id);
    }
    
    public void examinePower(String id, String examineState) {
        adjustAuthorityDao.examinePower(id, examineState);
    }
    
    public List<PowerAdjustAuthority> listByAdjustId(String adjustId) {
        return adjustAuthorityDao.listByAdjustId(adjustId);
    }
    
    public JSONObject getExamineInfoById(String adjustId) {
        return adjustAuthorityDao.getExamineInfoById(adjustId);
    }
}
