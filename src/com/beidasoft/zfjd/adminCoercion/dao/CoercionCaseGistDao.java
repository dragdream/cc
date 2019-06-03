package com.beidasoft.zfjd.adminCoercion.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionCaseGist;
import com.beidasoft.zfjd.adminCoercion.model.CoercionMeasureModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionPerformModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 强制案件基础表DAO类
 */
@Repository
public class CoercionCaseGistDao extends TeeBaseDao<CoercionCaseGist> {

    /**
     * 
     * @Function: deleteCasePower()
     * @Description: 通过案件ID，删除子表违法行为信息
     *
     * @param: cBase 参数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月16日 下午6:01:33
     *
     */
    public void deleteCaseGistByCaseId(CoercionMeasureModel measureModel, CoercionPerformModel performModel) {
        StringBuffer hql = new StringBuffer();
        boolean optFlag = false;
        hql.append(" delete from CoercionCaseGist where ");
        if (measureModel != null && measureModel.getId() != null) {
            hql.append("coercionMeasureGist.id = '" + measureModel.getId() + "'");
            optFlag = true;
        }
        if (performModel != null && performModel.getId() != null) {
            if (optFlag) {
                hql.append(" and ");
            } else {
                optFlag = true;
            }
            hql.append("coercionPerformGist.id = '" + performModel.getId() + "'");
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
     * @param: gistInfoList
     *             依据违法行为集合参数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月16日 下午6:03:51
     *
     */
    public void saveBatchCaseGist(List<CoercionCaseGist> gistInfoList) {
        if (gistInfoList != null && gistInfoList.size() > 0) {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            for (int i = 0; i < gistInfoList.size(); i++) {
                session.save(gistInfoList.get(i));
            }
            tx.commit();
            session.close();
        }
    }
}
