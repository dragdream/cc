package com.beidasoft.zfjd.adminCoercion.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionCasePower;
import com.beidasoft.zfjd.adminCoercion.model.CoercionMeasureModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionPerformModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 强制案件基础表DAO类
 */
@Repository
public class CoercionCasePowerDao extends TeeBaseDao<CoercionCasePower> {

    /**
     * 
     * @Function: deleteCasePower()
     * @Description: 通过案件ID，删除子表违法行为信息
     *
     * @param: cBase
     *             参数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月16日 下午6:01:33
     *
     */
    public void deleteCasePowerByCaseId(CoercionMeasureModel measureModel, CoercionPerformModel performModel) {
        StringBuffer hql = new StringBuffer();
        boolean optFlag = false;
        hql.append(" delete from CoercionCasePower where ");
        if (measureModel != null && measureModel.getId() != null) {
            hql.append("coercionMeasurePower.id = '" + measureModel.getId() + "'");
            optFlag = true;
        }
        if (performModel != null && performModel.getId() != null) {
            if (optFlag) {
                hql.append(" and ");
            } else {
                optFlag = true;
            }
            hql.append("coercionPerformPower.id = '" + performModel.getId() + "'");
        }
        if (optFlag) {
            deleteOrUpdateByQuery(hql.toString(), null);
        }
    }

    /**
     * 
     * @Function: saveBatchCasePower()
     * @Description: 批量保存违法行为信息
     *
     * @param: casePowers
     *             依据违法行为集合参数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月16日 下午6:03:51
     *
     */
    public void saveBatchCasePower(List<CoercionCasePower> powerInfoList) {
        if (powerInfoList != null && powerInfoList.size() > 0) {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            for (int i = 0; i < powerInfoList.size(); i++) {
                session.save(powerInfoList.get(i));
            }
            tx.commit();
            session.close();
        }
    }
}
