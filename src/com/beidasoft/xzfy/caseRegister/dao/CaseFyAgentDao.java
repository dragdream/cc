package com.beidasoft.xzfy.caseRegister.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyAgent;
import com.beidasoft.xzfy.caseRegister.bean.FyAgentRelation;
import com.beidasoft.xzfy.caseRegister.common.NumEnum;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Agent;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseFyAgentDao extends TeeBaseDao<FyAgent> {

    /**
     * 代理人信息登记/修改
     * 
     * @param agentList
     */
    public void addFyAgent(List<FyAgent> agentList) {

        int BATCH_MAX_ROW = 1000;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < agentList.size(); i++) {
                FyAgent fyAgent = agentList.get(i);
                fyAgent.setIsDelete(0);
                session.save(fyAgent);
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
     * 代理人代理关系
     * 
     * @param fyAgentRelationList
     */
    public void addFyAgentRelationList(List<FyAgentRelation> fyAgentRelationList) {
        int BATCH_MAX_ROW = 1000;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < fyAgentRelationList.size(); i++) {
                FyAgentRelation fyAgentRelation = fyAgentRelationList.get(i);
                fyAgentRelation.setIsDelete(0);
                session.save(fyAgentRelationList.get(i));
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
     * 根据代理人类型编码查询代理人
     * 
     * @param caseId
     * @param agentTypeCode
     * @return
     */
    public List<FyAgent> findFyAgentByCaseId(String caseId, String agentTypeCode) {

        StringBuffer str = new StringBuffer();
        str.append(" from FyAgent where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        if (agentTypeCode != null) {
            str.append(" and TYPE ='");
            str.append(agentTypeCode);
            str.append("'");
        }
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyAgent> listFyAgent = query.list();
        return listFyAgent;
    }

    /**
     * 代理人信息查询
     * 
     * @param caseId
     * @param zero
     * @return
     */
    public List<Agent> findAgentByTypeCode(String caseId, String type) {

        if (caseId != null && !"".equals(caseId)) {
            String tableName = null;
            if (NumEnum.NUM.getOne().equals(type)) {
                tableName = "FY_APPLICANT";
            } else if (NumEnum.NUM.getTwo().equals(type)) {
                tableName = "FY_RESPONDENT";
            } else if (NumEnum.NUM.getThree().equals(type)) {
                tableName = "FY_THIRD_PARTY";
            }
            // 拼接SQL
            StringBuffer str = new StringBuffer();
            str.append("SELECT A.ID,A.CASE_ID as caseId," + "A.AGENT_TYPE_CODE as agentTypeCode,"
                + "A.AGENT_TYPE as agentType, " + "A.AGENT_NAME as agentName," + "A.CARD_TYPE_CODE as cardTypeCode,"
                + "A.CARD_TYPE as cardType," + "A.ID_CARD as idCard," + "A.PHONE as phone,"
                + "A.IS_AUTHORIZATION as isAuthorization," + "A.TYPE as type");
            if (type != null && !"".equals(type)) {
                if ("FY_APPLICANT".equals(tableName)) {
                    str.append(",C.NAME as agentParent,C.ID as agentParentId");
                } else if ("FY_RESPONDENT".equals(tableName)) {
                    str.append(",C.RESPONDENT_NAME as agentParent,C.ID as agentParentId");
                } else if ("FY_THIRD_PARTY".equals(tableName)) {
                    str.append(",C.THIRD_PARTY_NAME as agentParent,C.ID as agentParentId");
                }
            }
            str.append(" FROM FY_AGENT A");
            str.append(" LEFT JOIN FY_AGENT_RELATION B ON B.AGENT_ID = A.ID");
            if (tableName != null && !"".equals(tableName)) {
                str.append(" LEFT JOIN ");
                str.append(tableName);
                str.append(" C ON C.ID = B.RELATION_ID");
            }

            str.append(" WHERE A.CASE_ID = '");
            str.append(caseId);
            str.append("'");
            if (type != null && !"".equals(type)) {
                str.append(" and A.TYPE ='");
                str.append(type);
                str.append("'");
            }
            str.append(" and A.IS_DELETE=0");
            Session session = this.getSession();
            Query query = session.createSQLQuery(str.toString());

            @SuppressWarnings("unchecked")
            List<Object[]> result = query.list();
            List<Agent> list = new ArrayList<Agent>();
            Agent resp = null;
            for (Object[] arr : result) {
                resp = com.beidasoft.xzfy.utils.StringUtils.arrayToObject(arr, Agent.class);
                list.add(resp);
            }
            return list;
        } else {
            return null;
        }

    }

    /**
     * 代理人信息删除
     * 
     * @param caseId
     * @param agentTypeCode
     */
    public void updateFyAgent(String caseId, String type) {

        StringBuffer str = new StringBuffer();
        str.append("update FY_AGENT set ");
        str.append(" IS_DELETE='");
        str.append(1);
        str.append("'");
        str.append(" where CASE_ID='");
        str.append(caseId);
        str.append("'");
        str.append(" and TYPE='");
        str.append(type);
        str.append("'");
        executeNativeUpdate(str.toString(), null);
    }

}