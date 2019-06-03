package com.beidasoft.xzfy.caseRegister.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseAcceptence.bean.CaseProcessLogInfo;
import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyLetter;
import com.beidasoft.xzfy.caseRegister.bean.FyMaterial;
import com.beidasoft.xzfy.caseRegister.bean.FyReception;
import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;
import com.beidasoft.xzfy.caseRegister.model.caseExtract.request.GetCaseExtractReq;
import com.beidasoft.xzfy.caseRegister.model.caseExtract.response.GetCaseExtractResp;
import com.tianee.webframe.dao.TeeBaseDao;

@SuppressWarnings("rawtypes")
@Repository
public class CaseExtractDao extends TeeBaseDao {

    /**
     * 案件提取
     * 
     * @param caseExtractReq
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<GetCaseExtractResp> getCaseExtract(GetCaseExtractReq getCaseExtractReq) {

        String hql =
            "SELECT A .CASE_ID AS caseId, " + "A .CASE_NUM AS caseNum, " + "A .APPLICATION_TYPE_CODE AS postTypeCode,"
                + "A .APPLICATION_TYPE AS postType, " + "B. NAME AS NAME, " + "C. RESPONDENT_NAME AS respondentName, "
                + "A .CASE_STATUS_CODE AS caseStatusCode, " + "A .CASE_STATUS AS caseStatus, "
                + "A .APPLICATION_DATE AS applicationDate, " + "A .APPLICATION_DATE AS remainderTime, "
                + "A .MERGER_CASE_NUM AS mergerCase FROM FY_CASE_HANDLING A "
                + "LEFT JOIN (SELECT T .CASE_ID, LISTAGG (T . NAME, ',') "
                + "WITHIN GROUP (ORDER BY T .CASE_ID) AS NAME " + "FROM FY_APPLICANT T GROUP BY T .case_id) B "
                + "ON B.CASE_ID = A .CASE_ID " + "LEFT JOIN (SELECT E .CASE_ID,LISTAGG (E .RESPONDENT_NAME,',') "
                + "WITHIN GROUP (ORDER BY E .CASE_ID) AS RESPONDENT_NAME "
                + "FROM FY_RESPONDENT E GROUP BY E .case_id) C " + "ON C.CASE_ID = A .CASE_ID "
                + "WHERE A .is_delete = 0 " + "AND A .CASE_STATUS_CODE IN ('01')";

        StringBuffer str = new StringBuffer();
        str.append(hql);
        // 案件编码
        if (!StringUtils.isEmpty(getCaseExtractReq.getCaseNum())) {
            str.append(" AND A.CASE_NUM = ").append(getCaseExtractReq.getCaseNum());
        }
        // 申请人
        if (!StringUtils.isEmpty(getCaseExtractReq.getName())) {
            str.append(" AND B.NAME LIKE '%").append(getCaseExtractReq.getName()).append("%' ");
        }
        // 被申请人
        if (!StringUtils.isEmpty(getCaseExtractReq.getRespondentName())) {
            str.append(" AND C.RESPONDENT_NAME LIKE '%").append(getCaseExtractReq.getRespondentName()).append("%' ");
        }
        // 接待人
        if (!StringUtils.isEmpty(getCaseExtractReq.getDealMan1Id())) {
            str.append(" AND A.DEAL_MAN1_ID LIKE '%").append(getCaseExtractReq.getDealMan1Id()).append("%' ");
        }
        // 申请日期
        if (!StringUtils.isEmpty(getCaseExtractReq.getEndTime())
            && !StringUtils.isEmpty(getCaseExtractReq.getStartTime())) {
            str.append(" AND A.APPLICATION_DATE BETWEEN ").append(getCaseExtractReq.getStartTime());
            str.append(" AND ").append(getCaseExtractReq.getEndTime());
        }
        str.append(" ORDER BY A .APPLICATION_DATE DESC");
        Session session = this.getSession();
        Query query = session.createSQLQuery(str.toString());
        int start = (getCaseExtractReq.getPage() - 1) * getCaseExtractReq.getRows();
        query.setFirstResult(start);
        query.setMaxResults(getCaseExtractReq.getRows());
        // 返回结果封装成对象
        List<Object[]> result = query.list();
        List<GetCaseExtractResp> list = new ArrayList<GetCaseExtractResp>();
        GetCaseExtractResp resp = new GetCaseExtractResp();
        for (Object[] arr : result) {
            resp = com.beidasoft.xzfy.utils.StringUtils.arrayToObject(arr, GetCaseExtractResp.class);
            list.add(resp);
        }
        return list;
    }

    /**
     * 案件信息表
     * 
     * @param caseId
     * @return
     */
    public List<FyCaseHandling> findFyCaseHandlingByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from FyCaseHandling where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyCaseHandling> fyCaseHandlingList = query.list();
        return fyCaseHandlingList;
    }

    /**
     * 来件信息
     * 
     * @param caseId
     * @return
     */
    public List<FyLetter> findFyLetterByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from FyLetter where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyLetter> fyLetterList = query.list();
        return fyLetterList;
    }

    /**
     * 接待信息
     * 
     * @param caseId
     * @return
     */
    public List<FyReception> findFyReceptionByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from FyReception where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyReception> fyReceptionList = query.list();
        return fyReceptionList;
    }

    /**
     * 当事人信息
     * 
     * @param caseId
     * @return
     */
    public List<FyApplicant> findFyApplicantByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from FyApplicant where IS_DELETE=0");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyApplicant> fyApplicantList = query.list();
        return fyApplicantList;
    }

    /**
     * 被申请人信息
     * 
     * @param caseId
     * @return
     */
    public List<FyRespondent> findFyRespondentByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from FyRespondent where IS_DELETE=0");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyRespondent> fyRespondentList = query.list();
        return fyRespondentList;
    }

    /**
     * 第三人信息
     * 
     * @param caseId
     * @return
     */
    public List<FyThirdParty> findFyThirdPartyByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from FyThirdParty where IS_DELETE=0");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyThirdParty> fyThirdPartyList = query.list();
        return fyThirdPartyList;
    }

    /**
     * 案件办理流程成
     * 
     * @param caseId
     * @return
     */
    public List<CaseProcessLogInfo> findCaseProcessLogInfoByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from CaseProcessLogInfo where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<CaseProcessLogInfo> caseProcessLogInfoList = query.list();
        return caseProcessLogInfoList;
    }

    /**
     * 案件材料
     * 
     * @param caseId
     * @return
     */
    public List<FyMaterial> findFyMaterialByCaseId(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append(" from FyMaterial where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        Session session = this.getSession();
        Query query = session.createQuery(str.toString());
        @SuppressWarnings("unchecked")
        List<FyMaterial> fyMaterialList = query.list();
        return fyMaterialList;
    }
}
