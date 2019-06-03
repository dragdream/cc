package com.beidasoft.zfjd.subject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.subject.bean.SubjectSubpower;
import com.beidasoft.zfjd.subject.dao.SubjectSubpowerDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 主体职权类别关系表SERVICE类
 */
@Service
public class SubjectSubpowerService extends TeeBaseService {

    @Autowired
    private SubjectSubpowerDao subjectSubpowerDao;

    /**
     * 保存主体职权类别关系表数据
     *
     * @param beanInfo
     */
    public void save(SubjectSubpower beanInfo) {

        subjectSubpowerDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取主体职权类别关系表数据
     *
     * @param id
     * @return     */
    public SubjectSubpower getById(String id) {

        return subjectSubpowerDao.get(id);
    }
}
