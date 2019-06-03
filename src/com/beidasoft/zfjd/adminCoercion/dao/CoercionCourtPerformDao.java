package com.beidasoft.zfjd.adminCoercion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionCourtPerform;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 法院强制执行信息DAO类
 */
@Repository
public class CoercionCourtPerformDao extends TeeBaseDao<CoercionCourtPerform> {

    /**
     * 获取法院强制执行信息数据
     *
     * @param id
     * @return
     */
    public List<CoercionCourtPerform> getByCoercionCaseId(String id) {
        if (id != null && !"".equals(id)) {
            String hql = " from CoercionCourtPerform where coercionCaseId = '" + id + "' ";
            return find(hql);
        } else {
            return null;
        }
    }
}
