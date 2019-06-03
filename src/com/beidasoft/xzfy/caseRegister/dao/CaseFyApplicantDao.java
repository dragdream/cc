package com.beidasoft.xzfy.caseRegister.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseFyApplicantDao extends TeeBaseDao<FyApplicant> {

    /**
     * 申请人信息登记
     * 
     * @param fyApplicantList
     */
    public void addFyApplicant(List<FyApplicant> fyApplicantList) {

        int BATCH_MAX_ROW = 1000;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < fyApplicantList.size(); i++) {
                FyApplicant fyApplicant = fyApplicantList.get(i);
                fyApplicant.setIsDelete(0);
                session.save(fyApplicant);
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

    public List<FyApplicant> findFyApplicantByCaseId(String caseId) {

        StringBuffer str = new StringBuffer();
        str.append(" from FyApplicant where IS_DELETE = 0");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyApplicant> listFyApplicant = query.list();
        return listFyApplicant;
    }

    /**
     * 根据caseId删除来件信息
     * 
     * @param caseId
     */
    public void updateFyApplicantByCaseId(String caseId) {

        StringBuffer str = new StringBuffer();
        str.append("update FY_APPLICANT set ");
        str.append(" IS_DELETE='");
        str.append(1);
        str.append("'");
        str.append(" where CASE_ID='");
        str.append(caseId);
        str.append("'");
        executeNativeUpdate(str.toString(), null);

    }
}
