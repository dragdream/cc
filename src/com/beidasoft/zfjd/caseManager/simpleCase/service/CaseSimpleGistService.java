package com.beidasoft.zfjd.caseManager.simpleCase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleGist;
import com.beidasoft.zfjd.caseManager.simpleCase.dao.CaseSimpleGistDao;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleGistModel;
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
public class CaseSimpleGistService extends TeeBaseService{
    
    @Autowired
    private CaseSimpleGistDao simpleGistDao;
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
        pgModel.setGistType("01");
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
    public TeeJson saveSimpleGist(CaseSimpleGistModel gistModel){
        TeeJson json = new TeeJson();
        String[] ids = gistModel.getIds().split(",");
        for (String id : ids) {
            //职权依据表
            PowerGist powerGist = gistService.getById(id);
            CaseSimpleGist simpleGist = new CaseSimpleGist();
            simpleGist.setId(UUID.randomUUID().toString());
            simpleGist.setCaseId(gistModel.getCaseId());
            simpleGist.setGistId(powerGist.getId());
            simpleGist.setLawName(powerGist.getLawName());
            simpleGist.setStrip(powerGist.getGistStrip());
            simpleGist.setFund(powerGist.getGistFund());
            simpleGist.setItem(powerGist.getGistItem());
            simpleGist.setGistCatalog(powerGist.getGistCatalog());
            simpleGist.setContent(powerGist.getContent());
            simpleGist.setCreateDate(new Date());
            simpleGistDao.save(simpleGist);
        }
        json.setRtState(true);
        return json ;
    }
    
    /**
     ** 根据主表id删除
     * @param caseId
     */
    public void deleteByCaseId(String caseId){
        simpleGistDao.deleteByCaseId(caseId);
    }
    
    /**
     ** 根据主表id查询
     * @param caseId
     * @return
     */
    public List<CaseSimpleGist> getListByCaseId(String caseId){
        return simpleGistDao.getListByCaseId(caseId);
    }
}
