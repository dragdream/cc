package com.beidasoft.zfjd.power.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerAdjustTacheInfo;
import com.beidasoft.zfjd.power.dao.PowerAdjustTacheInfoDao;
import com.beidasoft.zfjd.power.model.PowerAdjustTacheInfoModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class PowerAdjustTacheInfoService extends TeeBaseService{

    @Autowired
    private PowerAdjustTacheInfoDao adjustTacheInfoDao;
    
    public List<PowerAdjustTacheInfo> listByPage(TeeDataGridModel dm, String tacheId) {
        return adjustTacheInfoDao.listByPage(dm.getFirstResult(), dm.getRows(), tacheId);
    }
    
    public Long listCount(String tacheId) {
        return adjustTacheInfoDao.listCount(tacheId);
    }
    
    public void batchSave(List<PowerAdjustTacheInfo> adjustTacheInfos) {
        adjustTacheInfoDao.batchSave(adjustTacheInfos);
    }
    
    public List<PowerAdjustTacheInfoModel> getTacheInfos(String tacheId) {
        List<PowerAdjustTacheInfoModel> adjustTacheInfoModels = new ArrayList<PowerAdjustTacheInfoModel>();
        String sql = "select "
                + "tpat.id as TACHE_ID, "
                + "tpat.adjust_id, "
                + "tpaa.power_id, "
                + "tpaa.examine_state "
                + "from tbl_power_adjust_tache tpat "
                + "left join tbl_power_adjust_authority tpaa "
                + "on tpat.adjust_id = tpaa.adjust_id "
                + "where tpat.id = '" + tacheId + "'";
        List<Map> maps = simpleDaoSupport.executeNativeQuery(sql, null, 0, Integer.MAX_VALUE);
        PowerAdjustTacheInfoModel adjustTacheInfoModel = null;
        if(maps != null && maps.size() != 0) {
            for(int i = 0; i < maps.size(); i++) {
                adjustTacheInfoModel = new PowerAdjustTacheInfoModel();
                adjustTacheInfoModel.setExamineState(TeeStringUtil.getString(maps.get(i).get("EXAMINE_STATE"), ""));
                adjustTacheInfoModel.setPowerId(TeeStringUtil.getString(maps.get(i).get("POWER_ID"), ""));
                adjustTacheInfoModel.setTacheId(TeeStringUtil.getString(maps.get(i).get("TACHE_ID"), ""));
                adjustTacheInfoModel.setAdjustId(TeeStringUtil.getString(maps.get(i).get("ADJUST_ID"), ""));
                
                adjustTacheInfoModels.add(adjustTacheInfoModel);
            }
            
        }
        
        return adjustTacheInfoModels;
    }
}
