package com.beidasoft.xzfy.caseRegister.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyReception;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseFyReceptionDao extends TeeBaseDao<FyReception> {

    /**
     * 当面接待登记/修改
     * 
     * @param fyReceptionBean
     * @param caseId
     */
    public void addOrUpdateFyReception(FyReception fyReceptionBean, String caseId) {
        if (null != fyReceptionBean.getCaseId() && !"".equals(fyReceptionBean.getCaseId())) {
            update(fyReceptionBean);
        } else {
            fyReceptionBean.setCaseId(caseId);
            save(fyReceptionBean);
        }
    }

    /**
     * 根据caseId查询接待信息
     * 
     * @param caseId
     * @return
     */
    public FyReception getFyReceptionByCaseId(String caseId) {

        StringBuffer str = new StringBuffer();
        str.append(" from FyReception where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyReception> listFyReception = query.list();
        return listFyReception.get(0);
    }
}
