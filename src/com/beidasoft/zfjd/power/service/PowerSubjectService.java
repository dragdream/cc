package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerSubject;
import com.beidasoft.zfjd.power.dao.PowerSubjectDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PowerSubjectService extends TeeBaseService{
    @Autowired
    private PowerSubjectDao subjectDao;
    
    public List<PowerSubject> findSubjectsByPowerId(TeeDataGridModel dm, String powerId) {
        return subjectDao.findSubjectsByPowerId(dm.getFirstResult(), dm.getRows(), powerId);
    }
    
    public Long findSubjectsCountByPowerId(String powerId) {
        return subjectDao.findSubjectsCountByPowerId(powerId);
    }
    
    public List<PowerSubject> findSubjectsBySubjectrId(TeeDataGridModel dm, String subjectId) {
        return subjectDao.findSubjectsBySubjectrId(dm.getFirstResult(), dm.getRows(), subjectId);
    }
    
    public Long findSubjectsCountBySubjectId(String subjectId) {
        return subjectDao.findSubjectsCountBySubjectId(subjectId);
    }
}
