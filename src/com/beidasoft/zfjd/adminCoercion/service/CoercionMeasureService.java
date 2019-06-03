package com.beidasoft.zfjd.adminCoercion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionMeasure;
import com.beidasoft.zfjd.adminCoercion.dao.CoercionMeasureDao;
import com.beidasoft.zfjd.adminCoercion.model.CoercionMeasureModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 强制措施管理SERVICE类
 */
@Service
public class CoercionMeasureService extends TeeBaseService {

    @Autowired
    private CoercionMeasureDao coercionMeasureDao;

    /**
     * 保存强制措施管理数据
     *
     * @param beanInfo
     */
    public void save(CoercionMeasure beanInfo) {

        coercionMeasureDao.saveOrUpdate(beanInfo);
    }

    /**
     * 新增强制措施管理数据或保存第一步数据
     *
     * @param beanInfo
     */
    public void udpateMeasureInfo(CoercionMeasure beanInfo) {

        coercionMeasureDao.udpateMeasureInfo(beanInfo);
    }
    
    /**
     * 保存强制措施第二步数据
     *
     * @param modelInfo
     */
    public void updateMeasureApplyInfo(CoercionMeasureModel modelInfo) {

        coercionMeasureDao.updateMeasureApplyInfo(modelInfo);
    }
    
    /**
     * 保存强制措施第三步数据
     *
     * @param modelInfo
     */
    public void updateMeasureResultInfo(CoercionMeasureModel modelInfo) {

        coercionMeasureDao.updateMeasureResultInfo(modelInfo);
    }
    
    /**
     * 获取强制措施管理数据
     *
     * @param id
     * @return     */
    public CoercionMeasure getById(String id) {

        return coercionMeasureDao.get(id);
    }
    
    /**
     * 分页获取符合条件的强制措施列表
     *
     * @param id
     * @return     */
    public List<CoercionMeasure> listByPage(TeeDataGridModel dm, CoercionMeasureModel coercionMeasureModel) {
        return coercionMeasureDao.listByPage(dm.getFirstResult(), dm.getRows(), coercionMeasureModel);
    }
    
    /**
     * 获取符合条件的强制措施总数
     *
     * @param id
     * @return     */
    public long listCount(CoercionMeasureModel coercionMeasureModel) {
        return coercionMeasureDao.listCount(coercionMeasureModel);
    }
}
