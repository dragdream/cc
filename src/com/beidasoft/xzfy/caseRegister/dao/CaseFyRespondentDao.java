package com.beidasoft.xzfy.caseRegister.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseFyRespondentDao extends TeeBaseDao<FyRespondent> {
    /**
     * 被申请人信息登记
     * 
     * @param fyRespondentList
     */
    public void addFyRespondent(List<FyRespondent> fyRespondentList) throws Exception {
        int BATCH_MAX_ROW = 100000;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < fyRespondentList.size(); i++) {
                FyRespondent fyRespondent = fyRespondentList.get(i);
                fyRespondent.setIsDelete(0);
                session.save(fyRespondent);
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
     * 根据caseId查询被申请人信息
     * 
     * @param caseId
     * @return
     */
    public List<FyRespondent> findFyRespondentByCaseId(String caseId) {

        StringBuffer str = new StringBuffer();
        str.append(" from FyRespondent where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        List<FyRespondent> listFyRespondent = pageFind(str.toString(), 0, 10, null);
        return listFyRespondent;
    }

    /**
     * 删除被申请人信息
     * 
     * @param caseId
     */
    public void updateFyRespondentByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append("update FY_RESPONDENT set ");
        str.append(" IS_DELETE='");
        str.append(1);
        str.append("'");
        str.append(" where CASE_ID='");
        str.append(caseId);
        str.append("'");
        executeNativeUpdate(str.toString(), null);
    }
}
