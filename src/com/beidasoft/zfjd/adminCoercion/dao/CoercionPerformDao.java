package com.beidasoft.zfjd.adminCoercion.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionPerform;
import com.beidasoft.zfjd.adminCoercion.model.CoercionPerformModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;

/**
 * 强制执行信息表DAO类
 */
@Repository
public class CoercionPerformDao extends TeeBaseDao<CoercionPerform> {

    /**
     * 
     * @Function: PowerDao.java
     * @Description: 分页查询职权列表
     *
     * @param: 职权列表信息
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:57:56
     *
     */
    public List<CoercionPerform> listByPage(int start, int length, CoercionPerformModel coercionPerformModel) {
        String hql = "from CoercionPerform perform where ";
        if (coercionPerformModel.getCoercionCaseId() != null && !"".equals(coercionPerformModel.getCoercionCaseId())) {
            hql = hql + "  coercionCaseId = '" + coercionPerformModel.getCoercionCaseId() + "' ";
        }

        return pageFind(hql, start, length, null);
    }

    /**
     * 
     * @Function: PowerDao.java
     * @Description: 分页查询总数
     *
     * @param: 分页查询的总数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:58:29
     *
     */
    public long listCount(CoercionPerformModel coercionPerformModel) {
        String hql = "select count(*) from CoercionPerform perform where ";
        if (coercionPerformModel.getCoercionCaseId() != null && !"".equals(coercionPerformModel.getCoercionCaseId())) {
            hql = hql + " coercionCaseId = '" + coercionPerformModel.getCoercionCaseId() + "' ";
        }
        return count(hql, null);
    }

    /**
     * 新增强制执行管理数据或保存第一步数据
     *
     * @param beanInfo
     */
    public void updatePerformTypeInfo(CoercionPerform beanInfo) {
        String hql = "update CoercionPerform set updateDate =? ";
        hql = hql + ", performType = '" + beanInfo.getPerformType() + "' ";
        // hql = hql + ", enforceStep = '" + beanInfo.getEnforceStep() + "' ";
        hql = hql + "where id = '" + beanInfo.getId() + "' ";
        System.out.println(hql);
        Object[] fObject = new Object[1];
        fObject[0] = beanInfo.getUpdateDate();
        deleteOrUpdateByQuery(hql, fObject);
    }

    /**
     * 新增强制执行管理数据或保存第一步数据
     *
     * @param beanInfo
     */
    public void updatePerformPress(CoercionPerformModel beanInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put("updateDate", new Date());
        params.put("punishCodeBefore", beanInfo.getPunishCodeBefore());
        if (beanInfo.getPunishDateBeforeStr() != null && !"".equals(beanInfo.getPunishDateBeforeStr())) {
            params.put("punishDateBefore", TeeDateUtil.format(beanInfo.getPunishDateBeforeStr(), "yyyy-MM-dd"));
        } else {
            params.put("punishDateBefore", null);
        }
        if (beanInfo.getPressSendDateStr() != null && !"".equals(beanInfo.getPressSendDateStr())) {
            params.put("pressSendDate", TeeDateUtil.format(beanInfo.getPressSendDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("pressSendDate", null);
        }
        params.put("pressSendType", beanInfo.getPressSendType());
        params.put("originalMoney", beanInfo.getOriginalMoney());
        if (beanInfo.getOriginalDateStr() != null && !"".equals(beanInfo.getOriginalDateStr())) {
            params.put("originalDate", TeeDateUtil.format(beanInfo.getOriginalDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("originalDate", null);
        }
        params.put("addFindMoney", beanInfo.getAddFindMoney());
        if (beanInfo.getSecondPressDateStr() != null && !"".equals(beanInfo.getSecondPressDateStr())) {
            params.put("secondPressDate", TeeDateUtil.format(beanInfo.getSecondPressDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("secondPressDate", null);
        }
        params.put("secondPressType", beanInfo.getSecondPressType());
        update(params, beanInfo.getId());
    }

    /**
     * 新增强制执行管理数据或保存第一步数据
     *
     * @param beanInfo
     */
    public void updatePerformApply(CoercionPerformModel beanInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put("updateDate", new Date());
        if (beanInfo.getPunishDateBeforeStr() != null && !"".equals(beanInfo.getPunishDateBeforeStr())) {
            params.put("applyDate", TeeDateUtil.format(beanInfo.getApplyDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("applyDate", null);
        }

        if (beanInfo.getPunishDateBeforeStr() != null && !"".equals(beanInfo.getPunishDateBeforeStr())) {
            params.put("approveDate", TeeDateUtil.format(beanInfo.getApproveDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("approveDate", null);
        }

        params.put("approvePerson", beanInfo.getApprovePerson());

        update(params, beanInfo.getId());
    }

    /**
     * 新增强制执行管理数据或保存第一步数据
     *
     * @param beanInfo
     */
    public void updatePerformEnforce(CoercionPerformModel beanInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put("updateDate", new Date());
        params.put("coercionPerformCode", beanInfo.getCoercionPerformCode());
        if (beanInfo.getPerformEnforceDateStr() != null && !"".equals(beanInfo.getPerformEnforceDateStr())) {
            params.put("performEnforceDate", TeeDateUtil.format(beanInfo.getPerformEnforceDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("performEnforceDate", null);
        }
        if (beanInfo.getDecideSendDateStr() != null && !"".equals(beanInfo.getDecideSendDateStr())) {
            params.put("decideSendDate", TeeDateUtil.format(beanInfo.getDecideSendDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("decideSendDate", null);
        }
        params.put("decideSendType", beanInfo.getDecideSendType());
        params.put("transDeposit", beanInfo.getTransDeposit());
        params.put("transOrganization", beanInfo.getTransOrganization());
        if (beanInfo.getTransNoticeDateStr() != null && !"".equals(beanInfo.getTransNoticeDateStr())) {
            params.put("transNoticeDate", TeeDateUtil.format(beanInfo.getTransNoticeDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("transNoticeDate", null);
        }
        params.put("auctionAddr", beanInfo.getAuctionAddr());
        if (beanInfo.getAuctionDateStr() != null && !"".equals(beanInfo.getAuctionDateStr())) {
            params.put("auctionDate", TeeDateUtil.format(beanInfo.getAuctionDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("auctionDate", null);
        }
        params.put("auctionUse", beanInfo.getAuctionUse());
        params.put("replaceObject", beanInfo.getReplaceObject());
        params.put("replaceDeposit", beanInfo.getReplaceDeposit());
        params.put("replaceSupervise", beanInfo.getReplaceSupervise());
        if (beanInfo.getReplaceEnforceDateStr() != null && !"".equals(beanInfo.getReplaceEnforceDateStr())) {
            params.put("replaceEnforceDate", TeeDateUtil.format(beanInfo.getReplaceEnforceDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("replaceEnforceDate", null);
        }
        //
        params.put("isAgreementEnforce", beanInfo.getIsAgreementEnforce());
        if (beanInfo.getAgreeSignDateStr() != null && !"".equals(beanInfo.getAgreeSignDateStr())) {
            params.put("agreeSignDate", TeeDateUtil.format(beanInfo.getAgreeSignDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("agreeSignDate", null);
        }

        params.put("agreeContent", beanInfo.getAgreeContent());
        params.put("agreeEnforceCondition", beanInfo.getAgreeEnforceCondition());
        //
        params.put("isEnforceReturn", beanInfo.getIsEnforceReturn());
        if (beanInfo.getEnforceReturnDateStr() != null && !"".equals(beanInfo.getEnforceReturnDateStr())) {
            params.put("enforceReturnDate", TeeDateUtil.format(beanInfo.getEnforceReturnDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("enforceReturnDate", null);
        }

        params.put("enforceReturnReason", beanInfo.getEnforceReturnReason());
        params.put("enforceReturnContent", beanInfo.getEnforceReturnContent());
        //
        params.put("isEndEnforce", beanInfo.getIsEndEnforce());
        if (beanInfo.getEndEnforceSendDateStr() != null && !"".equals(beanInfo.getEndEnforceSendDateStr())) {
            params.put("endEnforceSendDate", TeeDateUtil.format(beanInfo.getEndEnforceSendDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("endEnforceSendDate", null);
        }

        params.put("endSendType", beanInfo.getEndSendType());
        params.put("enforceEndReason", beanInfo.getEnforceEndReason());
        //
        params.put("isBreakEnforce", beanInfo.getIsBreakEnforce());
        if (beanInfo.getBreakEnforceDateStr() != null && !"".equals(beanInfo.getBreakEnforceDateStr())) {
            params.put("breakEnforceDate", TeeDateUtil.format(beanInfo.getBreakEnforceDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("breakEnforceDate", null);
        }

        if (beanInfo.getRelwaseEnforceDateStr() != null && !"".equals(beanInfo.getRelwaseEnforceDateStr())) {
            params.put("relwaseEnforceDate", TeeDateUtil.format(beanInfo.getRelwaseEnforceDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("relwaseEnforceDate", null);
        }

        params.put("enforceBreakReason", beanInfo.getEnforceBreakReason());

        update(params, beanInfo.getId());
    }
}
