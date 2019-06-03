package com.beidasoft.zfjd.adminCoercion.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionMeasure;
import com.beidasoft.zfjd.adminCoercion.model.CoercionMeasureModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;

/**
 * 强制措施管理DAO类
 */
@Repository
public class CoercionMeasureDao extends TeeBaseDao<CoercionMeasure> {

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
    public List<CoercionMeasure> listByPage(int start, int length, CoercionMeasureModel coercionMeasureModel) {
        String hql = "from CoercionMeasure measure where ";
        if (coercionMeasureModel.getCoercionCaseId() != null && !"".equals(coercionMeasureModel.getCoercionCaseId())) {
            hql = hql + "  coercionCaseId = '" + coercionMeasureModel.getCoercionCaseId() + "' ";
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
    public long listCount(CoercionMeasureModel coercionMeasureModel) {
        String hql = "select count(*) from CoercionMeasure measure where ";
        if (coercionMeasureModel.getCoercionCaseId() != null && !"".equals(coercionMeasureModel.getCoercionCaseId())) {
            hql = hql + " coercionCaseId = '" + coercionMeasureModel.getCoercionCaseId() + "' ";
        }
        return count(hql, null);
    }

    /**
     * 保存强制措施管理数据
     *
     * @param beanInfo
     */
    public void udpateMeasureInfo(CoercionMeasure beanInfo) {
        String hql = "update CoercionMeasure set updateDate =? ";
        hql = hql + ", measureType = '" + beanInfo.getMeasureType() + "' ";
        hql = hql + ", enforceStep = '" + beanInfo.getEnforceStep() + "' ";
        hql = hql + "where id = '" + beanInfo.getId() + "' ";
        System.out.println(hql);
        Object[] fObject = new Object[1];
        fObject[0] = beanInfo.getUpdateDate();
        deleteOrUpdateByQuery(hql, fObject);
    }

    /**
     * 保存强制措施管理数据
     *
     * @param beanInfo
     */
    public void updateMeasureApplyInfo(CoercionMeasureModel modelInfo) {
        Map<String, Object> params = new HashMap<>();
        // ****设置更新字段
        params.put("updateDate", new Date());
        // 申请日期
        if (modelInfo.getApplyDateStr() != null && !"".equals(modelInfo.getApplyDateStr())) {
            params.put("applyDate", TeeDateUtil.format(modelInfo.getApplyDateStr(), "yyyy-MM-dd"));
        } else {
            params.put("applyDate", null);
        }
        // 批准日期
        if (modelInfo.getApproveDateStr() != null && !"".equals(modelInfo.getApproveDateStr())) {
            params.put("approveDate", TeeDateUtil.format(modelInfo.getApproveDateStr(), "yyyy-MM-dd"));

        } else {
            params.put("approveDate", null);
        }
        // 批准人

        params.put("approvePerson", modelInfo.getApprovePerson());
        // 决定书文号

        params.put("coercionCode", modelInfo.getCoercionCode());
        // 强制措施决定书送达日期
        if (modelInfo.getCdSendDateStr() != null && !"".equals(modelInfo.getCdSendDateStr())) {
            params.put("cdSendDate", TeeDateUtil.format(modelInfo.getCdSendDateStr(), "yyyy-MM-dd"));

        } else {
            params.put("cdSendDate", null);
        }
        // 送达方式

        params.put("cdSendType", modelInfo.getCdSendType());
        // 行政强制措施期限(起)
        if (modelInfo.getMeasureDateLimitStartStr() != null && !"".equals(modelInfo.getMeasureDateLimitStartStr())) {
            params.put("measureDateLimitStart",
                    TeeDateUtil.format(modelInfo.getMeasureDateLimitStartStr(), "yyyy-MM-dd"));

        } else {
            params.put("measureDateLimitStart", null);
        }

        // 行政强制措施期限(止)
        if (modelInfo.getMeasureDateLimitEndStr() != null && !"".equals(modelInfo.getMeasureDateLimitEndStr())) {
            params.put("measureDateLimitEnd", TeeDateUtil.format(modelInfo.getMeasureDateLimitEndStr(), "yyyy-MM-dd"));

        } else {
            params.put("measureDateLimitEnd", null);
        }

        // 实施强制措施日期
        if (modelInfo.getMeasureEnforceDateStr() != null && !"".equals(modelInfo.getMeasureEnforceDateStr())) {
            params.put("measureEnforceDate", TeeDateUtil.format(modelInfo.getMeasureEnforceDateStr(), "yyyy-MM-dd"));

        } else {
            params.put("measureEnforceDate", null);
        }
        // ** 查封
        // 当事人是否在场

        params.put("isPartyPresent", modelInfo.getIsPartyPresent());
        // 强制措施对象

        params.put("measureTargetType", modelInfo.getMeasureTargetType());

        // 见证人姓名

        params.put("witnessName", modelInfo.getWitnessName());

        // 见证人联系方式

        params.put("witnessContactWay", modelInfo.getWitnessContactWay());

        // 查封原因

        params.put("closeDownReason", modelInfo.getCloseDownReason());

        // 设施、场所名称

        params.put("closeDownPlaceName", modelInfo.getCloseDownPlaceName());

        // 设施、场所地址

        params.put("closeDownPlaceAddr", modelInfo.getCloseDownPlaceAddr());

        // 财物名称、数量、金额

        params.put("closeDownPropertyInfo", modelInfo.getCloseDownPropertyInfo());

        // ** 扣押
        // 当事人是否在场

        params.put("isDetentionPartyPresent", modelInfo.getIsDetentionPartyPresent());

        // 见证人姓名

        params.put("detentionWitnessName", modelInfo.getDetentionWitnessName());

        // 见证人联系方式

        params.put("detentionWitnessContactWay", modelInfo.getDetentionWitnessContactWay());

        // 财物名称、数量、金额

        params.put("detentionPropertyInfo", modelInfo.getDetentionPropertyInfo());

        // 扣押原因

        params.put("detentionReason", modelInfo.getDetentionReason());

        // ** 冻结
        // 冻结金额

        params.put("freezeNumber", modelInfo.getFreezeNumber());

        // 金融机构名称

        params.put("freezeOrganization", modelInfo.getFreezeOrganization());

        // 通知冻结日期
        if (modelInfo.getFreezeNoticeDateStr() != null && !"".equals(modelInfo.getFreezeNoticeDateStr())) {
            params.put("freezeNoticeDate", TeeDateUtil.format(modelInfo.getFreezeNoticeDateStr(), "yyyy-MM-dd"));

        } else {
            params.put("freezeNoticeDate", null);
        }
        
        // ** 限制公民人身自由
        // 是否通知家属或所在单位
        params.put("isNoticeFamily", modelInfo.getIsNoticeFamily());

        // 原因
        params.put("notNoticeReason", modelInfo.getNotNoticeReason());
        
        // 流程状态
        params.put("enforceStep", modelInfo.getEnforceStep());
        update(params, modelInfo.getId());
    }

    /**
     * 保存强制措施处理结果管理数据
     *
     * @param beanInfo
     */
    public void updateMeasureResultInfo(CoercionMeasureModel beanInfo) {
        String hql = "update CoercionMeasure set updateDate =? ";
        hql = hql + ", enforceStep = '" + beanInfo.getEnforceStep() + "' ";
        if (beanInfo.getMeasureDealWay() != null) {
            hql = hql + ", measureDealWay = '" + beanInfo.getMeasureDealWay() + "' ";
        }else{
            hql = hql + ", measureDealWay = null ";
        }
//        hql = hql + ", enforceStep = '" + beanInfo.getEnforceStep() + "' ";
        hql = hql + "where id = '" + beanInfo.getId() + "' ";
        System.out.println(hql);
        Object[] fObject = new Object[1];
        fObject[0] = new Date();
        deleteOrUpdateByQuery(hql, fObject);
    }
}
