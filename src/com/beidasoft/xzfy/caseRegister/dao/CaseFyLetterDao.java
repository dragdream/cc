package com.beidasoft.xzfy.caseRegister.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyLetter;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseFyLetterDao extends TeeBaseDao<FyLetter> {

    /**
     * 书面来件登记/修改
     * 
     * @param fyLetterBean
     * @param caseId
     */
    public void addOrUpdateFyLetter(FyLetter fyLetterBean, String caseId) {
        if (null != fyLetterBean.getCaseId() && !"".equals(fyLetterBean.getCaseId())) {
            update(fyLetterBean);
        } else {
            fyLetterBean.setCaseId(caseId);
            save(fyLetterBean);
        }
    }

    /**
     * 根据caseId查询来件信息
     * 
     * @param caseId
     * @param object
     * @return
     */
    public FyLetter getFyLetterByCaseId(String caseId) {

        StringBuffer str = new StringBuffer();
        if (!"".equals(caseId) && null != caseId) {
            str.append(" from FyLetter where IS_DELETE = 0");
            str.append(" and CASE_ID ='");
            str.append(caseId);
            str.append("'");
            Session session = this.getSession();
            Query query = session.createQuery(str.toString());
            @SuppressWarnings("unchecked")
            List<FyLetter> listFyLetter = query.list();
            if (listFyLetter.size() > 0) {
                return listFyLetter.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
