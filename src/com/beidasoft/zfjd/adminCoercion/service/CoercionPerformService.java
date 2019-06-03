package com.beidasoft.zfjd.adminCoercion.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionPerform;
import com.beidasoft.zfjd.adminCoercion.dao.CoercionPerformDao;
import com.beidasoft.zfjd.adminCoercion.model.CoercionPerformModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 强制执行信息表SERVICE类
 */
@Service
public class CoercionPerformService extends TeeBaseService {

    @Autowired
    private CoercionPerformDao coercionPerformDao;

    /**
     * 保存强制执行信息表数据
     *
     * @param beanInfo
     */
    public void save(CoercionPerform beanInfo) {
        
        coercionPerformDao.saveOrUpdate(beanInfo);
    }

    /**
     * 更新强制执行-执行方式信息
     *
     * @param beanInfo
     */
    public void updatePerformTypeInfo(CoercionPerform beanInfo) {
        coercionPerformDao.updatePerformTypeInfo(beanInfo);
    }
    
    /**
     * 更新强制执行-催告信息
     *
     * @param beanInfo
     */
    public void updatePerformPress(CoercionPerformModel beanInfo) {

        coercionPerformDao.updatePerformPress(beanInfo);
    }
    
    /**
     * 更新强制执行-强制申请信息
     *
     * @param beanInfo
     */
    public void updatePerformApply(CoercionPerformModel beanInfo) {

        coercionPerformDao.updatePerformApply(beanInfo);
    }
    
    /**
     * 更新强制执行-事项处理信息
     *
     * @param beanInfo
     */
    public void updatePerformEnforce(CoercionPerformModel beanInfo) {

        coercionPerformDao.updatePerformEnforce(beanInfo);
    }
    /**
     * 获取强制执行信息表数据
     *
     * @param id
     * @return     */
    public CoercionPerform getById(String id) {

        return coercionPerformDao.get(id);
    }
    
    /**
     * 分页获取符合条件的强制措施列表
     *
     * @param id
     * @return     */
    public List<CoercionPerform> listByPage(TeeDataGridModel dm, CoercionPerformModel coercionPerformModel) {
        return coercionPerformDao.listByPage(dm.getFirstResult(), dm.getRows(), coercionPerformModel);
    }
    
    /**
     * 获取符合条件的强制措施总数
     *
     * @param id
     * @return     */
    public long listCount(CoercionPerformModel coercionPerformModel) {
        return coercionPerformDao.listCount(coercionPerformModel);
    }
}
