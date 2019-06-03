package com.beidasoft.zfjd.adminCoercion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionCourtPerform;
import com.beidasoft.zfjd.adminCoercion.dao.CoercionCourtPerformDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 法院强制执行信息SERVICE类
 */
@Service
public class CoercionCourtPerformService extends TeeBaseService {

    @Autowired
    private CoercionCourtPerformDao coercionCourtPerformDao;

    /**
     * 保存法院强制执行信息数据
     *
     * @param beanInfo
     */
    public void save(CoercionCourtPerform beanInfo) {

        coercionCourtPerformDao.saveOrUpdate(beanInfo);
    }
    
    /**
     * 获取法院强制执行信息数据
     *
     * @param id
     * @return     */
    public CoercionCourtPerform getById(String id) {

        return coercionCourtPerformDao.get(id);
    }
    
    /**
     * 获取法院强制执行信息数据
     *
     * @param id
     * @return     */
    public List<CoercionCourtPerform> getByCoercionCaseId(String id) {

        return coercionCourtPerformDao.getByCoercionCaseId(id);
    }
    
}
