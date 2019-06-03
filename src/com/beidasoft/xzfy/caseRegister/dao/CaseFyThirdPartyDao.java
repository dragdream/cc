package com.beidasoft.xzfy.caseRegister.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseFyThirdPartyDao extends TeeBaseDao<FyThirdParty> {

    /**
     * 第三人信息登记
     * 
     * @param fyThirdPartyList
     */
    public void addFyThirdParty(List<FyThirdParty> fyThirdPartyList) throws Exception {

        int BATCH_MAX_ROW = 1000;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < fyThirdPartyList.size(); i++) {
                FyThirdParty fyThirdParty = fyThirdPartyList.get(i);
                fyThirdParty.setIsDelete(0);
                session.save(fyThirdParty);
                if (i % BATCH_MAX_ROW == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * 根据caseId查询第三人信息
     * 
     * @param caseId
     * @return
     */
    public List<FyThirdParty> findFyThirdPartyByCaseId(String caseId) {

        StringBuffer str = new StringBuffer();
        str.append(" from FyThirdParty where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyThirdParty> listFyThirdParty = query.list();
        return listFyThirdParty;
    }

    /**
     * 删除第三人信息
     * 
     * @param caseId
     */
    public void updateFyThirdPartyByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append("update FY_THIRD_PARTY set ");
        str.append(" IS_DELETE='");
        str.append(1);
        str.append("'");
        str.append(" where CASE_ID='");
        str.append(caseId);
        str.append("'");
        executeNativeUpdate(str.toString(), null);
    }
}
