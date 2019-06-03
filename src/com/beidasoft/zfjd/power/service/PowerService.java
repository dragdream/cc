package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.dao.PowerDao;
import com.beidasoft.zfjd.power.dao.PowerDetailDao;
import com.beidasoft.zfjd.power.dao.PowerFlowsheetDao;
import com.beidasoft.zfjd.power.dao.PowerGistDao;
import com.beidasoft.zfjd.power.dao.PowerLevelDao;
import com.beidasoft.zfjd.power.model.PowerModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;



@Service
public class PowerService extends TeeBaseService{
    @Autowired
    private PowerDao powerDao;
    
    @Autowired
    private PowerGistDao gistDao;
    
    @Autowired
    private PowerDetailDao detailDao;
    
    @Autowired
    private PowerLevelDao levelDao;
    
    @Autowired
    private PowerFlowsheetDao flowsheetDao;
    
    public List<Power> listByPage(TeeDataGridModel dm, PowerModel powerModel, OrgCtrlInfoModel orgCtrl) {
        return powerDao.listByPage(dm.getFirstResult(), dm.getRows(), powerModel, orgCtrl);
    }
    
    public long listCount(PowerModel powerModel, OrgCtrlInfoModel orgCtrl) {
        return powerDao.listCount(powerModel, orgCtrl);
    }
    
    public Power get(String id) {
        return powerDao.get(id);
    }
    
    public void savePower(Power power) throws Exception{
        
        // 重新写入职权分类子表
        detailDao.deleteByPowerId(power);
        detailDao.batchSave(power.getDetails());
        
        // 重新写入职权流程图子表
        flowsheetDao.deleteByPowerId(power);
        flowsheetDao.batchSave(power.getFlows());
        
        // 重新写入职权层级子表
        levelDao.deleteByPowerId(power);
        levelDao.batchSave(power.getLevels());
        
        // 重新写入职权依据子表
        gistDao.deleteByPowerId(power);
        gistDao.batchSave(power.getGists());
        
        
        
        //更新或保存职权信息
        powerDao.saveOrUpdate(power);
    }
    
    public List<Power> findPowerByIds(TeeDataGridModel dm, PowerModel powerModel) {
        return powerDao.findPowerByIds(dm.getFirstResult(), dm.getRows(), powerModel);
    }
    
    public long findPowerByIdsCount(PowerModel powerModel) {
        return powerDao.findPowerByIdsCount(powerModel);
    }
    
    public List<Power> getPowerByActSubject(TeeDataGridModel dm, PowerModel powerModel) {
        return powerDao.getPowerByActSubject(dm.getFirstResult(), dm.getRows(), powerModel);
    }
    
    public Long getPowerCountByActSubject(PowerModel powerModel) {
        return powerDao.getPowerCountByActSubject(powerModel);
    }
    
    public List<Power> getPowerByActSubjects(TeeDataGridModel dm, PowerModel powerModel) {
        return powerDao.getPowerByActSubjects(dm.getFirstResult(), dm.getRows(), powerModel);
    }
    
    public Long getPowerCountByActSubjects(PowerModel powerModel) {
        return powerDao.getPowerCountByActSubjects(powerModel);
    }
    
    public void revokePower(String batchCode, String ids) {
        powerDao.revokePower(batchCode, ids);
    }
}
