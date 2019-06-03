package com.beidasoft.zfjd.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.dao.SysBusinessRelationDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 监督业务管理表SERVICE类
 */
@Service
public class SysBusinessRelationService extends TeeBaseService {

    @Autowired
    private SysBusinessRelationDao sysBusinessRelationDao;

    /**
     * 保存监督业务管理表数据
     *
     * @param beanInfo
     */
    public void save(SysBusinessRelation sysBusinessRelation) {
    	sysBusinessRelationDao.save(sysBusinessRelation);
	}


    /**
     * 获取监督业务管理表数据
     *
     * @param id
     * @return     */
    public SysBusinessRelation getById(String id) {

        return sysBusinessRelationDao.get(id);
    }
}
