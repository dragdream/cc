package com.beidasoft.zfjd.caseManager.simpleCase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimplePunish;
import com.beidasoft.zfjd.caseManager.simpleCase.dao.CaseSimplePunishDao;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimplePunishModel;
import com.beidasoft.zfjd.power.bean.PowerGist;
import com.beidasoft.zfjd.power.model.PowerGistModel;
import com.beidasoft.zfjd.power.service.PowerGistService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class CaseSimplePunishService extends TeeBaseService{
    
    @Autowired
    private CaseSimplePunishDao simplePunishDao;
    @Autowired
    private PowerGistService gistService;
    
    /**
     ** 根据职权id查询
     * @param tModel
     * @param pgModel
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, PowerGistModel pgModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        pgModel.setGistType("02");
        List<PowerGist> list = gistService.findGistsByPowerIds(tModel, pgModel);
        List<PowerGistModel> modelList = new ArrayList<PowerGistModel>();
        for (PowerGist powerGist : list) {
            PowerGistModel gistModel = new PowerGistModel();
            BeanUtils.copyProperties(powerGist, gistModel);
            modelList.add(gistModel);
        }
        if (TeeUtility.isNullorEmpty(pgModel.getPowerId())) {
            json.setRows(new ArrayList<PowerGistModel>());
            json.setTotal(TeeStringUtil.getLong("0", 0));
            return json;
        }
        json.setRows(modelList);
        json.setTotal(TeeStringUtil.getLong(modelList.size(), 0));
        return json;
    }
    /**
     ** 保存
     * @return
     */
    public TeeJson saveSimplePunish(CaseSimplePunishModel punishModel){
        TeeJson json = new TeeJson();
        String[] ids = punishModel.getIds().split(",");
        for (String id : ids) {
            //职权依据表
            PowerGist powerGist = gistService.getById(id);
            CaseSimplePunish simplePunish = new CaseSimplePunish();
            simplePunish.setId(UUID.randomUUID().toString());
            simplePunish.setCaseId(punishModel.getCaseId());
            simplePunish.setGistId(powerGist.getId());
            simplePunish.setLawName(powerGist.getLawName());
            simplePunish.setStrip(powerGist.getGistStrip());
            simplePunish.setFund(powerGist.getGistFund());
            simplePunish.setItem(powerGist.getGistItem());
            simplePunish.setGistCatalog(powerGist.getGistCatalog());
            simplePunish.setContent(powerGist.getContent());
            simplePunish.setCreateDate(new Date());
            simplePunishDao.save(simplePunish);
        }
        json.setRtState(true);
        return json;
    }
    
    /**
     ** 根据主表id删除
     * @param caseId
     */
    public void deleteByCaseId(String caseId){
        simplePunishDao.deleteByCaseId(caseId);
    }
    
    /**
     ** 根据主表id查询
     * @return
     */
    public List<CaseSimplePunish> getListByCaseId(String caseId){
        return simplePunishDao.getListByCaseId(caseId);
    }
    
}
