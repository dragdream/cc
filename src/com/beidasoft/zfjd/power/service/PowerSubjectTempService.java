package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerSubjectTemp;
import com.beidasoft.zfjd.power.dao.PowerSubjectTempDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;


@Service
public class PowerSubjectTempService {
    
    @Autowired
    private PowerSubjectTempDao subjectTempDao;

    public List<PowerSubjectTemp> findSubjectsByPowerId(TeeDataGridModel dm, String powerId) {
        return subjectTempDao.findSubjectsByPowerId(dm.getFirstResult(), dm.getRows(), powerId);
    }
    
    public Long findSubjectsCountByPowerId(String powerId) {
        return subjectTempDao.findSubjectsCountByPowerId(powerId);
    }
}
