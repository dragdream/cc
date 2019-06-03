package com.beidasoft.zfjd.adminCoercion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionCaseBasic;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionCaseGist;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionCasePower;
import com.beidasoft.zfjd.adminCoercion.dao.CoercionCaseBasicDao;
import com.beidasoft.zfjd.adminCoercion.dao.CoercionCaseGistDao;
import com.beidasoft.zfjd.adminCoercion.dao.CoercionCasePowerDao;
import com.beidasoft.zfjd.adminCoercion.model.CoercionCaseBasicModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionMeasureModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionPerformModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 强制案件基础表SERVICE类
 */
@Service
public class CoercionCaseBasicService extends TeeBaseService {

    @Autowired
    private CoercionCaseBasicDao coercionCaseBasicDao;
    
    @Autowired
    private CoercionCasePowerDao coercionCasePowerDao;
    
    @Autowired
    private CoercionCaseGistDao coercionCaseGistDao;

    /**
     * 保存强制案件基础表数据
     *
     * @param beanInfo
     */
    public void save(CoercionCaseBasic beanInfo) {
        coercionCaseBasicDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取强制案件基础表数据
     *
     * @param id
     * @return     */
    public CoercionCaseBasic getById(String id) {
        return coercionCaseBasicDao.get(id);
    }
    
    /**
     * 获取强制案件基础表数据
     *
     * @param id
     * @return     */
    public List<CoercionCaseBasic> getCoercionBaseByOtherSrc(CoercionCaseBasicModel coercionCaseBasicModel) {
        
        return coercionCaseBasicDao.getCoercionBaseByOtherSrc(coercionCaseBasicModel);
    } 
    
    /**
     * 分页获取符合条件的强制案件列表（对填报）
     *
     * @param id
     * @return     */
    public List<CoercionCaseBasic> listByPage(TeeDataGridModel dm, CoercionCaseBasicModel coercionCaseBasicModel) {
        return coercionCaseBasicDao.listByPage(dm.getFirstResult(), dm.getRows(), coercionCaseBasicModel);
    }
    
    /**
     * 分页获取符合条件的强制案件总数（对填报）
     *
     * @param id
     * @return     */
    public long listCount(CoercionCaseBasicModel coercionCaseBasicModel) {
        return coercionCaseBasicDao.listCount(coercionCaseBasicModel);
    }
    
    /**
     * 分页获取符合条件的强制案件列表（对查询）
     *
     * @param id
     * @return     */
    public List<CoercionCaseBasic> coercionSearchListByPage(TeeDataGridModel dm, CoercionCaseBasicModel coercionCaseBasicModel) {
        return coercionCaseBasicDao.coercionSearchListByPage(dm.getFirstResult(), dm.getRows(), coercionCaseBasicModel);
    }
    
    /**
     * 分页获取符合条件的强制案件总数（对查询）
     *
     * @param id
     * @return     */
    public long coercionSearchListCount(CoercionCaseBasicModel coercionCaseBasicModel) {
        return coercionCaseBasicDao.coercionSearchListCount(coercionCaseBasicModel);
    }
    
    /**
     * 保存强制案件职权表数据
     *
     * @param beanInfo
     */
    public void saveCasePower(CoercionMeasureModel measureModel, CoercionPerformModel performModel, List<CoercionCasePower> powerInfoList) {
      //先删除违法行为信息, 再插入新的违法信息
        coercionCasePowerDao.deleteCasePowerByCaseId(measureModel, performModel);
        coercionCasePowerDao.saveBatchCasePower(powerInfoList);
    }
    
    /**
     * 保存强制案件依据表数据
     *
     * @param beanInfo
     */
    public void saveCaseGist(CoercionMeasureModel measureModel, CoercionPerformModel performModel, List<CoercionCaseGist> gistInfoList) {
        //先删除违法依据信息, 再插入新的违法依据信息
        coercionCaseGistDao.deleteCaseGistByCaseId(measureModel, performModel);
        coercionCaseGistDao.saveBatchCaseGist(gistInfoList);
        
    }
}
