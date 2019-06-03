package com.beidasoft.xzfy.caseRegister.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CaseReviewMattersDao extends TeeBaseDao<FyCaseHandling> {

    /**
     * 复议事项登记（案件信息）/修改
     * 
     * @param fyCaseHandlingBean
     * @param caseId
     * @return
     */
    public String addOrUpdateReviewMatters(FyCaseHandling fyCaseHandlingBean, String caseId) throws Exception {
        if (null != caseId && !"".equals(caseId)) {
            updateCaseHand(fyCaseHandlingBean);
        } else {
            fyCaseHandlingBean.setCaseId(caseId);
            caseId = (String)save(fyCaseHandlingBean);
        }
        return caseId;
    }

    /**
     * 根据caseId查询复议事项
     * 
     * @param caseId
     * @return
     */
    public FyCaseHandling findReviewMattersByCaseId(String caseId) throws Exception {
        StringBuffer str = new StringBuffer();
        str.append(" from FyCaseHandling where 1=1");
        str.append(" and CASE_ID ='");
        str.append(caseId);
        str.append("'");
        List<FyCaseHandling> list = pageFind(str.toString(), 0, 10, null);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 更新复议事项
     * 
     * @param req
     */
    public void updateCaseHand(FyCaseHandling req) {
        StringBuffer str = new StringBuffer();
        str.append("update FY_CASE_HANDLING t set t.APPLICATION_DATE=?");
        str.append(",t.CATEGORY_CODE=?");
        str.append(",t.CATEGORY=?");// 行政类别管理
        str.append(",t.APPLICATION_ITEM_CODE=?");
        str.append(",t.APPLICATION_ITEM=?");// 申请事项

        str.append(",t.IS_NONFEASANCE=?");// 行政不作为
        str.append(",t.SPECIFIC_ADMINISTRATIVE_NAME=?");// 具体行政行为名称
        str.append(",t.SPECIFIC_ADMINISTRATIVE_NO=?");// 具体行政行为文号
        str.append(",t.SPECIFIC_ADMINISTRATIVE_DATE=?");// 具体行政行为做出日期
        str.append(",t.RECEIVED_PUNISH_DATE=?");// 收到处罚决定书日期
        str.append(",t.RECEIVED_SPECIFIC_WAY=?");// 得知该具体行为途径

        str.append(",t.NONFEASANCE_ITEM_CODE=?");
        str.append(",t.NONFEASANCE_ITEM=?");// 不作为事项
        str.append(",t.NONFEASANCE_DATE=?");// 申请人要求被申请人履行该项法定责任日期

        str.append(",t.SPECIFIC_ADMINISTRATIVE_DETAIL=?");// 具体行政行为
        str.append(",t.REQUEST_FOR_RECONSIDERATION=?");// 行政复议请求

        str.append(",t.IS_REVIEW=?");// 行政复议前置
        str.append(",t.IS_HOLD_HEARING=?");// 申请举行听证会
        str.append(",t.IS_DOCUMENT_REVIEW=?");// 申请对规范性文件审查
        str.append(",t.DOCUMENT_REVIEW_NAME=?");// 规范性文件名称

        str.append(",t.IS_COMPENSATION=?");// 申请赔偿
        str.append(",t.COMPENSATION_TYPE_CODE=?");
        str.append(",t.COMPENSATION_TYPE=?");// 赔偿请求类型
        str.append(",t.COMPENSATION_AMOUNT=?");// 赔偿请求金额
        str.append(" where t.case_id=?");
        String[] object = new String[25];
        object[0] = req.getApplicationDate();
        object[1] = req.getCategoryCode();
        object[2] = req.getCategory();
        object[3] = req.getApplicationItemCode();
        object[4] = req.getApplicationItem();

        object[5] = req.getIsNonfeasance() + "";

        object[6] = req.getSpecificAdministrativeName();
        object[7] = req.getSpecificAdministrativeNo();
        object[8] = req.getSpecificAdministrativeDate();
        object[9] = req.getReceivedPunishDate();
        object[10] = req.getReceivedSpecificWay();

        object[11] = req.getNonfeasanceItemCode();
        object[12] = req.getNonfeasanceItem();
        object[13] = req.getNonfeasanceDate();

        object[14] = req.getSpecificAdministrativeDetail();
        object[15] = req.getRequestForReconsideration();

        object[16] = req.getIsReview() + "";
        object[17] = req.getIsHoldHearing() + "";
        object[18] = req.getIsDocumentReview() + "";
        object[19] = req.getDocumentReviewName();

        object[20] = req.getIsCompensation() + "";
        object[21] = req.getCompensationTypeCode();
        object[22] = req.getCompensationType();
        object[23] = req.getCompensationAmount();

        object[24] = req.getCaseId();

        executeNativeUpdate(str.toString(), object);
    }
}
