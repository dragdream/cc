package com.beidasoft.zfjd.caseManager.commonCase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonData;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonDataDao;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseSearchModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonDataModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * @ClassName: CaseCommonDataService.java
 * @Description: 
 *
 * @author: mixue
 * @date: 2019年3月20日 上午11:28:55
 */
@Service
public class CaseCommonDataService extends TeeBaseService {
    
    @Autowired
    private CaseCommonDataDao caseCommonDataDao;

    public List<CaseCommonDataModel> findListByPage(CaseCommonDataModel caseCommonDataModel) {
        // TODO Auto-generated method stub
        List<CaseCommonDataModel> caseCommonDataModels = new ArrayList<>();
        List<CaseCommonData> caseCommonDatas = caseCommonDataDao.findListByPage(caseCommonDataModel);
        for (CaseCommonData caseCommonData : caseCommonDatas) {
            CaseCommonDataModel cModel = new CaseCommonDataModel();
            BeanUtils.copyProperties(caseCommonData, cModel);
            caseCommonDataModels.add(cModel);
        }
        return caseCommonDataModels;
    }
    
    public CaseCommonDataModel getCaseCommonDataById(String id) {
        // TODO Auto-generated method stub
        CaseCommonData caseCommonData = caseCommonDataDao.get(id);
        CaseCommonDataModel caseCommonDataModel = new CaseCommonDataModel();
        if (caseCommonData != null)
            BeanUtils.copyProperties(caseCommonData, caseCommonDataModel);
        return caseCommonDataModel;
    }
    
    public CaseCommonBaseSearchModel copyAllProperties(CaseCommonBase caseCommonBase){
        CaseCommonBaseSearchModel caseCommonBaseSearchModel = new CaseCommonBaseSearchModel();
        
        
        return caseCommonBaseSearchModel;
    }
}
