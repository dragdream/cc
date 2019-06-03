package com.beidasoft.zfjd.caseManager.simpleCase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimplePower;
import com.beidasoft.zfjd.caseManager.simpleCase.dao.CaseSimplePowerDao;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimplePowerModel;
import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerSubject;
import com.beidasoft.zfjd.power.model.PowerModel;
import com.beidasoft.zfjd.power.model.PowerSubjectModel;
import com.beidasoft.zfjd.power.service.PowerService;
import com.beidasoft.zfjd.power.service.PowerSubjectService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class CaseSimplePowerService extends TeeBaseService{
    
    @Autowired
    private CaseSimplePowerDao simplePowerDao;
    @Autowired
    private PowerSubjectService powerSubjectService;
    @Autowired
    private PowerService powerService;
    
    /**
     ** 根据执法人员id查询
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel,PowerModel powerModel, HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        // 接收查询集合 
        List<Power> powerList = null;
        // bean对应的model 
        List<PowerModel> powerModelList = new ArrayList<PowerModel>();
        // 查询集合总数
        Long count = null;
        powerList = powerService.findPowerByIds(tModel, powerModel);
        count = powerService.findPowerByIdsCount(powerModel);
        // 定义model
        PowerModel powModel = null;
        if (powerList != null && powerList.size() > 0) {
            for (int i = 0; i < powerList.size(); i++) {
                powModel = new PowerModel();
                Power power = powerList.get(i);
                // 将tempBasic赋值给cModel
                BeanUtils.copyProperties(power, powModel);
                powerModelList.add(powModel);
            }
        }
        if (TeeUtility.isNullorEmpty(powerModel.getIds())) {
            json.setRows(new ArrayList<PowerModel>());
            json.setTotal(TeeStringUtil.getLong("0", 0));
            return json;
        }
        json.setRows(powerModelList);
        json.setTotal(count);
        return json;
    }
    
    /**
     ** 根据主体查询执法行为
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson findListByZhuTi(TeeDataGridModel tModel, PowerSubjectModel powerSubjectModel, HttpServletRequest request){
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        //根据主体id查询执法职权id
        List<PowerSubject> powerSubList = powerSubjectService.findSubjectsBySubjectrId(tModel,powerSubjectModel.getSubjectId());
        long total = powerSubjectService.findSubjectsCountBySubjectId(powerSubjectModel.getSubjectId());
        //查询主体
        //Subject subject = subjectService.getById(subPersonModel.getSubjectId());
        //循环
        List<PowerModel> powerModel = new ArrayList<PowerModel>();
        if (!powerSubList.isEmpty()) {
            for (PowerSubject powerSubject : powerSubList) {
                Power power = powerSubject.getPowerSubject();
                PowerModel model = new PowerModel();
                BeanUtils.copyProperties(power, model);
                powerModel.add(model);
            }
        }
        json.setRows(powerModel);
        json.setTotal(total);
        return json;
    }
    /**
     ** 保存
     * @return
     */
    public TeeJson saveSimplePower(CaseSimplePowerModel powerModel){
        TeeJson json = new TeeJson();
        String[] ids = powerModel.getIds().split(",");
        for (String id : ids) {
            //违法行为
            Power power = powerService.get(id);
            CaseSimplePower simplePower = new CaseSimplePower();
            simplePower.setId(UUID.randomUUID().toString());
            simplePower.setCaseId(powerModel.getCaseId());
            simplePower.setPowerId(power.getId());
            simplePower.setPowerCode(power.getCode());
            simplePower.setPowerName(power.getName());
            simplePower.setCreateDate(new Date());
            simplePowerDao.save(simplePower);
        }
        json.setRtState(true);
        return json ;
    }
    
    /**
     ** 根据案件主表id查询
     * @param caseId
     * @return
     */
    public List<CaseSimplePower> getListByCaseId(String caseId){
        return simplePowerDao.getListByCaseId(caseId);
    }
    
    /**
     ** 批量删除
     * @return
     */
    public void deleteByCaseId(String caseId){
        simplePowerDao.deleteByCaseId(caseId);
    }
    
    
}
