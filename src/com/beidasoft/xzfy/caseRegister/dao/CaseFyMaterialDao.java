package com.beidasoft.xzfy.caseRegister.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.beidasoft.xzfy.caseRegister.bean.FyMaterial;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseFyMaterialDao extends TeeBaseDao<FyMaterial> {

    /**
     * 案件资料登记FyMaterial
     * 
     * @param addCaseMaterialReq
     */
    public void addFyMaterial(List<FyMaterial> listFyMaterial) {

        int BATCH_MAX_ROW = 1000;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < listFyMaterial.size(); i++) {
                if ("".equals(listFyMaterial.get(i).getId())
                        || null == listFyMaterial.get(i).getId()) {
                    listFyMaterial.get(i).setId(StringUtils.getUUId());

                }
                if (!"".equals(listFyMaterial.get(i).getCaseId())) {
                    listFyMaterial.get(i).setCaseId(listFyMaterial.get(i).getCaseId());
                }
                if (!"".equals(listFyMaterial.get(i).getCaseTypeCode())) {
                    listFyMaterial.get(i).setCaseTypeCode(listFyMaterial.get(i).getCaseTypeCode());
                }
                session.save(listFyMaterial.get(i));
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
     * 案件资料删除
     * 
     * @param addCaseMaterialReq
     * 
     */
    public void deleteByList(String caseId, String caseCode) {

        StringBuffer str = new StringBuffer();
        if ((!"".equals(caseId) && null != caseId) || (!"".equals(caseCode) && null != caseCode)) {
            str.append("delete from FY_MATERIAL where 1=1");
        }
        if (!"".equals(caseId) && null != caseId) {
            str.append(" and CASE_ID = " + "'" + caseId + "'");
        }
        if (!"".equals(caseCode) && null != caseCode) {
            str.append(" and CASE_TYPE_CODE = " + "'" + caseCode + "'");
        }
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(str.toString());
            query.executeUpdate();
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
     * 根据案件ID和案件材料类型编码删除案件是否存在
     * 
     * @param listFyMaterial
     * @return
     */
    public String selectCountByList(List<FyMaterial> listFyMaterial) {
        String hqlString =
                "select id from FyMaterial p where p.caseId = '"
                        + listFyMaterial.get(0).getCaseId() + "' and caseTypeCode ='"
                        + listFyMaterial.get(0).getCaseId() + "'";
        Session session = this.getSession();
        Query query = session.createQuery(hqlString);
        return query.getQueryString();
    }

    /**
     * 根据caseId和caseTypeCode查询案件材料信息
     * 
     * @param caseId
     * @param caseTypeCode
     * @return
     */
    public List<FyMaterial> findCaseMaterial(String caseId, String caseTypeCode) {

        StringBuffer str = new StringBuffer();
        str.append(" from FyMaterial where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyMaterial> listFyMaterial = query.list();
        return listFyMaterial;
    }
}
