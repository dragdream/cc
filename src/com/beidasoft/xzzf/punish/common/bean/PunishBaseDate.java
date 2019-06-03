package com.beidasoft.xzzf.punish.common.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 案件办理时间表实体类
 */
@Entity
@Table(name="ZF_PUNISH_BASE_DATE")
public class PunishBaseDate {
    // 执法办案统一编号
	@Id
    @Column(name = "BASE_ID")
    private String baseId;

    // 立案日期
    @Column(name = "LASP_DATE")
    private Date laspDate;

    // 现场检查（勘验）检查开始
    @Column(name = "XCJCKYBL_START_DATE")
    private Date xcjckyblStartDate;

    // 现场检查（勘验）检查结束
    @Column(name = "XCJCKYBL_END_DATE")
    private Date xcjckyblEndDate;

    // 现场检查（勘验）签章时间
    @Column(name = "XCJCKYBL_SEAL_DATE")
    private Date xcjckyblSealDate;

    // 现场检查（勘验）当事人签字
    @Column(name = "XCJCKYBL_SIGN_DATE")
    private Date xcjckyblSignDate;

    // 物品清单签章时间
    @Column(name = "WPQD_SEAL_DATE")
    private Date wpqdSealDate;

    // 物品清单当事人签字时间
    @Column(name = "WPQD_SIGN_DATE")
    private Date wpqdSignDate;

    // 行政处罚时间（通用）
    @Column(name = "XZCFJDS_DATE")
    private Date xzcfjdsDate;

    // 查封决定书开始时间
    @Column(name = "CFJDS_START_DATE")
    private Date cfjdsStartDate;

    // 查封决定书结束时间
    @Column(name = "CFJDS_END_DATE")
    private Date cfjdsEndDate;

    // 查封决定书签章时间
    @Column(name = "CFJDS_SEAL_DATE")
    private Date cfjdsSealDate;

    // 查封决定书送达时间
    @Column(name = "CFJDS_ARRIVE_DATE")
    private Date cfjdsArriveDate;

    // 查封决定书签收时间
    @Column(name = "CFJDS_RECEIVE_DATE")
    private Date cfjdsReceiveDate;

    // 扣押决定书开始时间
    @Column(name = "KYJDS_START_DATE")
    private Date kyjdsStartDate;

    // 扣押决定书结束时间
    @Column(name = "KYJDS_END_DATE")
    private Date kyjdsEndDate;

    // 扣押决定书签章时间
    @Column(name = "KYJDS_SEAL_DATE")
    private Date kyjdsSealDate;

    // 扣押决定书送达时间
    @Column(name = "KYJDS_ARRIVE_DATE")
    private Date kyjdsArriveDate;

    // 扣押决定书签收时间
    @Column(name = "KYJDS_RECEIVE_DATE")
    private Date kyjdsReceiveDate;

    // 取证情况及证据说明取证开始
    @Column(name = "QZQKJZJSM_START_DATE")
    private Date qzqkjzjsmStartDate;

    // 取证情况及证据说明取证结束
    @Column(name = "QZQKJZJSM_END_DATE")
    private Date qzqkjzjsmEndDate;

    // 取证情况证据说明当事人签字
    @Column(name = "QZQKJZJSM_SIGN_DATE")
    private Date qzqkjzjsmSignDate;

    // 抽样取证凭证签章时间
    @Column(name = "CYQZPZ_SEAL_DATE")
    private Date cyqzpzSealDate;

    // 抽样取证凭证送达时间
    @Column(name = "CYQZPZ_ARRIVE_DATE")
    private Date cyqzpzArriveDate;

    // 抽样取证凭证签收时间
    @Column(name = "CYQZPZ_RECEIVE_DATE")
    private Date cyqzpzReceiveDate;

    // 审批表抽样取证
    @Column(name = "SPB_CYQZ_DATE")
    private Date spbCyqzDate;

    // 审批表抽样取证物品处理
    @Column(name = "SPB_CYQZWQCL_DATE")
    private Date spbCyqzwqclDate;

    // 审批表证据先行登记保存
    @Column(name = "SPB_ZJXXDJBC_DATE")
    private Date spbZjxxdjbcDate;

    // 审批表证据先行登记保存处理 
    @Column(name = "SPB_ZJXXDJBCCL_DATE")
    private Date spbZjxxdjbcclDate;

    // 审批表查封/扣押
    @Column(name = "SPB_CFKY_DATE")
    private Date spbCfkyDate;

    // 审批表延长查封/扣押
    @Column(name = "SPB_YCCFKY_DATE")
    private Date spbYccfkyDate;

    // 审批表解除查封/扣押
    @Column(name = "SPB_JCCFKY_DATE")
    private Date spbJccfkyDate;

    // 审批表移送案件
    @Column(name = "SPB_YSAJ_DATE")
    private Date spbYsajDate;

    // 审批表案件延期办理
    @Column(name = "SPB_AJYQBL_DATE")
    private Date spbAjyqblDate;

    // 审批表延期/分期缴纳罚款
    @Column(name = "SPB_YQFQJNFK_DATE")
    private Date spbYqfqjnfkDate;

    // 审批表出库
    @Column(name = "SPB_CK_DATE")
    private Date spbCkDate;

    // 审批表撤销案件
    @Column(name = "SPB_CXAJ_DATE")
    private Date spbCxajDate;

    // 审批表其他
    @Column(name = "SPB_QT_DATE")
    private Date spbQtDate;

    // 查封处理决定书延长到
    @Column(name = "CFCLJDS_DELAY_DATE")
    private Date cfcljdsDelayDate;

    // 查封处理决定书鉴定开始
    @Column(name = "CFCLJDS_APPRAISAL_DATE")
    private Date cfcljdsAppraisalDate;

    // 查封处理决定书鉴定结束
    @Column(name = "CFCLJDS_END_DATE")
    private Date cfcljdsEndDate;

    // 查封处理决定书鉴定顺延到
    @Column(name = "CFCLJDS_APPRAISAL_DELAY_DATE")
    private Date cfcljdsAppraisalDelayDate;

    // 查封处理决定书送达时间
    @Column(name = "CFCLJDS_ARRIVE_DATE")
    private Date cfcljdsArriveDate;

    // 查封处理决定书签收时间
    @Column(name = "CFCLJDS_RECEIVE_DATE")
    private Date cfcljdsReceiveDate;

    // 查封处理决定书签章时间
    @Column(name = "CFCLJDS_SEAL_DATE")
    private Date cfcljdsSealDate;

    // 扣押处理决定书延长到
    @Column(name = "KYCLJDS_DELAY_DATE")
    private Date kycljdsDelayDate;

    // 扣押处理决定书鉴定开始
    @Column(name = "KYCLJDS_APPRAISAL_DATE")
    private Date kycljdsAppraisalDate;

    // 扣押处理决定书鉴定结束
    @Column(name = "KYCLJDS_END_DATE")
    private Date kycljdsEndDate;

    // 扣押处理决定书鉴定顺延到
    @Column(name = "KYCLJDS_APPRAISAL_DELAY_DATE")
    private Date kycljdsAppraisalDelayDate;

    // 扣押处理决定书送达时间
    @Column(name = "KYCLJDS_ARRIVE_DATE")
    private Date kycljdsArriveDate;

    // 扣押处理决定书签收时间
    @Column(name = "KYCLJDS_RECEIVE_DATE")
    private Date kycljdsReceiveDate;

    // 扣押处理决定书签章时间
    @Column(name = "KYCLJDS_SEAL_DATE")
    private Date kycljdsSealDate;

    // 调查询问笔录询问开始时间
    @Column(name = "DCXWBL_START_DATE")
    private Date dcxwblStartDate;

    // 调查询问笔录询问结束时间
    @Column(name = "DCXWBL_END_DATE")
    private Date dcxwblEndDate;

    // 证据材料移送清单移送时间
    @Column(name = "ZJCLYSQD_DCQZ_ARRIVE_DATE")
    private Date zjclysqdDcqzArriveDate;

    // 证据材料移送清单接收时间
    @Column(name = "ZJCLYSQD_DCQZ_RECEIVE_DATE")
    private Date zjclysqdDcqzReceiveDate;

    // 送达回证送达时间
    @Column(name = "SDHZ_DCQZ_ARRIVE_DATE")
    private Date sdhzDcqzArriveDate;

    // 证据先行登记保存期限开始
    @Column(name = "ZJXXDJBCTZS_SATRT_DATE")
    private Date zjxxdjbctzsSatrtDate;

    // 证据先行登记保存期限结束
    @Column(name = "ZJXXDJBCTZS_END_DATE")
    private Date zjxxdjbctzsEndDate;

    // 证据先行登记保存移送时间
    @Column(name = "ZJXXDJBCTZS_ARRIVE_DATE")
    private Date zjxxdjbctzsArriveDate;

    // 证据先行登记保存接收时间
    @Column(name = "ZJXXDJBCTZS_RECEIVE_DATE")
    private Date zjxxdjbctzsReceiveDate;

    // 证据先行登记保存签章时间
    @Column(name = "ZJXXDJBCTZS_SEAL_DATE")
    private Date zjxxdjbctzsSealDate;

    // 证据先行登记保存处理决定书鉴定开始
    @Column(name = "ZJXXDJBCCLJDS_SATRT_DATE")
    private Date zjxxdjbccljdsSatrtDate;

    // 证据先行登记保存处理决定书鉴定结束
    @Column(name = "ZJXXDJBCCLJDS_END_DATE")
    private Date zjxxdjbccljdsEndDate;

    // 证据先行登记保存处理决定书签章时间
    @Column(name = "ZJXXDJBCCLJDS_SEAL_DATE")
    private Date zjxxdjbccljdsSealDate;

    // 证据先行登记保存处理决定书送达时间
    @Column(name = "ZJXXDJBCCLJDS_ARRIVE_DATE")
    private Date zjxxdjbccljdsArriveDate;

    // 证据先行登记保存处理决定书接收时间
    @Column(name = "ZJXXDJBCCLJDS_RECEIVE_DATE")
    private Date zjxxdjbccljdsReceiveDate;

    // 抽样取证物品处理通知书鉴定开始
    @Column(name = "CYQZWQCLTZS_SATRT_DATE")
    private Date cyqzwqcltzsSatrtDate;

    // 抽样取证物品处理通知书鉴定结束
    @Column(name = "CYQZWQCLTZS_END_DATE")
    private Date cyqzwqcltzsEndDate;

    // 抽样取证物品处理通知书签章时间
    @Column(name = "CYQZWQCLTZS_SEAL_DATE")
    private Date cyqzwqcltzsSealDate;

    // 抽样取证物品处理通知书送达时间
    @Column(name = "CYQZWQCLTZS_ARRIVE_DATE")
    private Date cyqzwqcltzsArriveDate;

    // 抽样取证物品处理通知书接收时间
    @Column(name = "CYQZWQCLTZS_RECEIVE_DATE")
    private Date cyqzwqcltzsReceiveDate;

    // 案件移送单移送时间
    @Column(name = "AJYSD_DCQZ_SEND_DATE")
    private Date ajysdDcqzSendDate;

    // 责令改正通知书整改时间
    @Column(name = "ZLGZTZS_MODIFY_DATE")
    private Date zlgztzsModifyDate;

    // 责令改正通知书签章时间
    @Column(name = "ZLGZTZS_SEAL_DATE")
    private Date zlgztzsSealDate;

    // 责令改正通知书送达时间
    @Column(name = "ZLGZTZS_ARRIVE_DATE")
    private Date zlgztzsArriveDate;

    // 责令改正通知书签收时间
    @Column(name = "ZLGZTZS_RECEIVE_DATE")
    private Date zlgztzsReceiveDate;

    // 行政处罚事先告知书签章时间
    @Column(name = "XZCFSXGZS_SEAL_DATE")
    private Date xzcfsxgzsSealDate;

    // 行政处罚事先告知书当事人签字
    @Column(name = "XZCFSXGZS_SIGN_DATE")
    private Date xzcfsxgzsSignDate;

    // 行政处罚履行催告书签章时间
    @Column(name = "XZCFLXCGS_SEAL_DATE")
    private Date xzcflxcgsSealDate;

    // 行政处罚履行催告书送达时间
    @Column(name = "XZCFLXCGS_ARRIVE_DATE")
    private Date xzcflxcgsArriveDate;

    // 行政处罚履行催告书签收时间
    @Column(name = "XZCFLXCGS_RECEIVE_DATE")
    private Date xzcflxcgsReceiveDate;

    // 证据材料移送清单移送时间
    @Column(name = "ZJCLYSQD_ZGGZ_ARRIVE_DATE")
    private Date zjclysqdZggzArriveDate;

    // 证据材料移送清单接收时间
    @Column(name = "ZJCLYSQD_ZGGZ_RECEIVE_DATE")
    private Date zjclysqdZggzReceiveDate;

    // 送达回证送达时间
    @Column(name = "SDHZ_ZGGZ_ARRIVE_DATE")
    private Date sdhzZggzArriveDate;

    // 案件移送单移送时间
    @Column(name = "AJYSD_ZGGZ_SEND_DATE")
    private Date ajysdZggzSendDate;

    // 案件处理呈批表领导签字时间
    @Column(name = "AJCLCPB_SEAL_DATE")
    private Date ajclcpbSealDate;

    // 送达回证送达时间(案件呈批)
    @Column(name = "SDHZ_AJCP_ARRIVE_DATE")
    private Date sdhzAjcpArriveDate;

    // 案件集体讨论记录开始时间
    @Column(name = "AJJTTL_ATART_DATE")
    private Date ajjttlAtartDate;

    // 案件集体讨论记录结束时间
    @Column(name = "AJJTTL_END_DATE")
    private Date ajjttlEndDate;

    // 入库出库单移送时间
    @Column(name = "RKCKD_ARRIVE_DATE")
    private Date rkckdArriveDate;

    // 入库出库单接收时间
    @Column(name = "RKCKD_RECEIVE_DATE")
    private Date rkckdReceiveDate;

    // 结案报告领导签字时间
    @Column(name = "JABG_SEAL_DATE")
    private Date jabgSealDate;

    // 延期缴纳罚款批准书延期至
    @Column(name = "YQJNFKPZS_DELAY_DATE")
    private Date yqjnfkpzsDelayDate;

    // 延期缴纳罚款批准书签章时间
    @Column(name = "YQJNFKPZS_SEAL_DATE")
    private Date yqjnfkpzsSealDate;

    // 延期缴纳罚款批准书送达时间
    @Column(name = "YQJNFKPZS_ARRIVE_DATE")
    private Date yqjnfkpzsArriveDate;

    // 延期缴纳罚款批准书签收时间
    @Column(name = "YQJNFKPZS_RECELVE_DATE")
    private Date yqjnfkpzsRecelveDate;

    // 分期缴纳罚款批准书第一期
    @Column(name = "FQJNFKPZS_ONE_DATE")
    private Date fqjnfkpzsOneDate;

    // 分期缴纳罚款批准书第二期
    @Column(name = "FQJNFKPZS_TWO_DATE")
    private Date fqjnfkpzsTwoDate;

    // 分期缴纳罚款批准书第三期
    @Column(name = "FQJNFKPZS_THREE_DATE")
    private Date fqjnfkpzsThreeDate;

    // 分期缴纳罚款批准书签章时间
    @Column(name = "FQJNFKPZS_SEAL_DATE")
    private Date fqjnfkpzsSealDate;

    // 分期缴纳罚款批准书送达时间
    @Column(name = "FQJNFKPZS_ARRIVE_DATE")
    private Date fqjnfkpzsArriveDate;

    // 分期缴纳罚款批准书签收时间
    @Column(name = "FQJNFKPZS_RECELVE_DATE")
    private Date fqjnfkpzsRecelveDate;

    // 强制执行申请书批准时间
    @Column(name = "QZZXSQS_SEAL_DATE")
    private Date qzzxsqsSealDate;

    // 行政处罚加处罚款决定书签章时间
    @Column(name = "XZCFJCFKJDS_SEAL_DATE")
    private Date xzcfjcfkjdsSealDate;

    // 行政处罚加处罚款决定书送达时间
    @Column(name = "XZCFJCFKJDS_ARRIVE_DATE")
    private Date xzcfjcfkjdsArriveDate;

    // 行政处罚加处罚款决定书签收时间
    @Column(name = "XZCFJCFKJDS_RECELVE_DATE")
    private Date xzcfjcfkjdsRecelveDate;

    // 行政处罚听证通知书听证时间
    @Column(name = "XZCFTZTZS_START_DATE")
    private Date xzcftztzsStartDate;

    // 行政处罚听证通知书签章时间
    @Column(name = "XZCFTZTZS_SEAL_DATE")
    private Date xzcftztzsSealDate;

    // 行政处罚听证通知书送达时间
    @Column(name = "XZCFTZTZS_ARRIVE_DATE")
    private Date xzcftztzsArriveDate;

    // 行政处罚听证通知书签收时间
    @Column(name = "XZCFTZTZS_RECELVE_DATE")
    private Date xzcftztzsRecelveDate;

    // 听证公告签章时间
    @Column(name = "TZGG_SEAL_DATE")
    private Date tzggSealDate;

    // 行政处罚听证笔录开始时间
    @Column(name = "XZCFTZBL_START_DATE")
    private Date xzcftzblStartDate;

    // 行政处罚听证笔录结束时间
    @Column(name = "XZCFTZBL_END_DATE")
    private Date xzcftzblEndDate;

    // 行政处罚听证报告签字时间
    @Column(name = "XZCFTZBG_SEAL_DATE")
    private Date xzcftzbgSealDate;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public Date getLaspDate() {
        return laspDate;
    }

    public void setLaspDate(Date laspDate) {
        this.laspDate = laspDate;
    }

    public Date getXcjckyblStartDate() {
        return xcjckyblStartDate;
    }

    public void setXcjckyblStartDate(Date xcjckyblStartDate) {
        this.xcjckyblStartDate = xcjckyblStartDate;
    }

    public Date getXcjckyblEndDate() {
        return xcjckyblEndDate;
    }

    public void setXcjckyblEndDate(Date xcjckyblEndDate) {
        this.xcjckyblEndDate = xcjckyblEndDate;
    }

    public Date getXcjckyblSealDate() {
        return xcjckyblSealDate;
    }

    public void setXcjckyblSealDate(Date xcjckyblSealDate) {
        this.xcjckyblSealDate = xcjckyblSealDate;
    }

    public Date getXcjckyblSignDate() {
        return xcjckyblSignDate;
    }

    public void setXcjckyblSignDate(Date xcjckyblSignDate) {
        this.xcjckyblSignDate = xcjckyblSignDate;
    }

    public Date getWpqdSealDate() {
        return wpqdSealDate;
    }

    public void setWpqdSealDate(Date wpqdSealDate) {
        this.wpqdSealDate = wpqdSealDate;
    }

    public Date getWpqdSignDate() {
        return wpqdSignDate;
    }

    public void setWpqdSignDate(Date wpqdSignDate) {
        this.wpqdSignDate = wpqdSignDate;
    }

    public Date getXzcfjdsDate() {
        return xzcfjdsDate;
    }

    public void setXzcfjdsDate(Date xzcfjdsDate) {
        this.xzcfjdsDate = xzcfjdsDate;
    }

    public Date getCfjdsStartDate() {
        return cfjdsStartDate;
    }

    public void setCfjdsStartDate(Date cfjdsStartDate) {
        this.cfjdsStartDate = cfjdsStartDate;
    }

    public Date getCfjdsEndDate() {
        return cfjdsEndDate;
    }

    public void setCfjdsEndDate(Date cfjdsEndDate) {
        this.cfjdsEndDate = cfjdsEndDate;
    }

    public Date getCfjdsSealDate() {
        return cfjdsSealDate;
    }

    public void setCfjdsSealDate(Date cfjdsSealDate) {
        this.cfjdsSealDate = cfjdsSealDate;
    }

    public Date getCfjdsArriveDate() {
        return cfjdsArriveDate;
    }

    public void setCfjdsArriveDate(Date cfjdsArriveDate) {
        this.cfjdsArriveDate = cfjdsArriveDate;
    }

    public Date getCfjdsReceiveDate() {
        return cfjdsReceiveDate;
    }

    public void setCfjdsReceiveDate(Date cfjdsReceiveDate) {
        this.cfjdsReceiveDate = cfjdsReceiveDate;
    }

    public Date getKyjdsStartDate() {
        return kyjdsStartDate;
    }

    public void setKyjdsStartDate(Date kyjdsStartDate) {
        this.kyjdsStartDate = kyjdsStartDate;
    }

    public Date getKyjdsEndDate() {
        return kyjdsEndDate;
    }

    public void setKyjdsEndDate(Date kyjdsEndDate) {
        this.kyjdsEndDate = kyjdsEndDate;
    }

    public Date getKyjdsSealDate() {
        return kyjdsSealDate;
    }

    public void setKyjdsSealDate(Date kyjdsSealDate) {
        this.kyjdsSealDate = kyjdsSealDate;
    }

    public Date getKyjdsArriveDate() {
        return kyjdsArriveDate;
    }

    public void setKyjdsArriveDate(Date kyjdsArriveDate) {
        this.kyjdsArriveDate = kyjdsArriveDate;
    }

    public Date getKyjdsReceiveDate() {
        return kyjdsReceiveDate;
    }

    public void setKyjdsReceiveDate(Date kyjdsReceiveDate) {
        this.kyjdsReceiveDate = kyjdsReceiveDate;
    }

    public Date getQzqkjzjsmStartDate() {
        return qzqkjzjsmStartDate;
    }

    public void setQzqkjzjsmStartDate(Date qzqkjzjsmStartDate) {
        this.qzqkjzjsmStartDate = qzqkjzjsmStartDate;
    }

    public Date getQzqkjzjsmEndDate() {
        return qzqkjzjsmEndDate;
    }

    public void setQzqkjzjsmEndDate(Date qzqkjzjsmEndDate) {
        this.qzqkjzjsmEndDate = qzqkjzjsmEndDate;
    }

    public Date getQzqkjzjsmSignDate() {
        return qzqkjzjsmSignDate;
    }

    public void setQzqkjzjsmSignDate(Date qzqkjzjsmSignDate) {
        this.qzqkjzjsmSignDate = qzqkjzjsmSignDate;
    }

    public Date getCyqzpzSealDate() {
        return cyqzpzSealDate;
    }

    public void setCyqzpzSealDate(Date cyqzpzSealDate) {
        this.cyqzpzSealDate = cyqzpzSealDate;
    }

    public Date getCyqzpzArriveDate() {
        return cyqzpzArriveDate;
    }

    public void setCyqzpzArriveDate(Date cyqzpzArriveDate) {
        this.cyqzpzArriveDate = cyqzpzArriveDate;
    }

    public Date getCyqzpzReceiveDate() {
        return cyqzpzReceiveDate;
    }

    public void setCyqzpzReceiveDate(Date cyqzpzReceiveDate) {
        this.cyqzpzReceiveDate = cyqzpzReceiveDate;
    }

    public Date getSpbCyqzDate() {
        return spbCyqzDate;
    }

    public void setSpbCyqzDate(Date spbCyqzDate) {
        this.spbCyqzDate = spbCyqzDate;
    }

    public Date getSpbCyqzwqclDate() {
        return spbCyqzwqclDate;
    }

    public void setSpbCyqzwqclDate(Date spbCyqzwqclDate) {
        this.spbCyqzwqclDate = spbCyqzwqclDate;
    }

    public Date getSpbZjxxdjbcDate() {
        return spbZjxxdjbcDate;
    }

    public void setSpbZjxxdjbcDate(Date spbZjxxdjbcDate) {
        this.spbZjxxdjbcDate = spbZjxxdjbcDate;
    }

    public Date getSpbZjxxdjbcclDate() {
        return spbZjxxdjbcclDate;
    }

    public void setSpbZjxxdjbcclDate(Date spbZjxxdjbcclDate) {
        this.spbZjxxdjbcclDate = spbZjxxdjbcclDate;
    }

    public Date getSpbCfkyDate() {
        return spbCfkyDate;
    }

    public void setSpbCfkyDate(Date spbCfkyDate) {
        this.spbCfkyDate = spbCfkyDate;
    }

    public Date getSpbYccfkyDate() {
        return spbYccfkyDate;
    }

    public void setSpbYccfkyDate(Date spbYccfkyDate) {
        this.spbYccfkyDate = spbYccfkyDate;
    }

    public Date getSpbJccfkyDate() {
        return spbJccfkyDate;
    }

    public void setSpbJccfkyDate(Date spbJccfkyDate) {
        this.spbJccfkyDate = spbJccfkyDate;
    }

    public Date getSpbYsajDate() {
        return spbYsajDate;
    }

    public void setSpbYsajDate(Date spbYsajDate) {
        this.spbYsajDate = spbYsajDate;
    }

    public Date getSpbAjyqblDate() {
        return spbAjyqblDate;
    }

    public void setSpbAjyqblDate(Date spbAjyqblDate) {
        this.spbAjyqblDate = spbAjyqblDate;
    }

    public Date getSpbYqfqjnfkDate() {
        return spbYqfqjnfkDate;
    }

    public void setSpbYqfqjnfkDate(Date spbYqfqjnfkDate) {
        this.spbYqfqjnfkDate = spbYqfqjnfkDate;
    }

    public Date getSpbCkDate() {
        return spbCkDate;
    }

    public void setSpbCkDate(Date spbCkDate) {
        this.spbCkDate = spbCkDate;
    }

    public Date getSpbCxajDate() {
        return spbCxajDate;
    }

    public void setSpbCxajDate(Date spbCxajDate) {
        this.spbCxajDate = spbCxajDate;
    }

    public Date getSpbQtDate() {
        return spbQtDate;
    }

    public void setSpbQtDate(Date spbQtDate) {
        this.spbQtDate = spbQtDate;
    }

    public Date getCfcljdsDelayDate() {
        return cfcljdsDelayDate;
    }

    public void setCfcljdsDelayDate(Date cfcljdsDelayDate) {
        this.cfcljdsDelayDate = cfcljdsDelayDate;
    }

    public Date getCfcljdsAppraisalDate() {
        return cfcljdsAppraisalDate;
    }

    public void setCfcljdsAppraisalDate(Date cfcljdsAppraisalDate) {
        this.cfcljdsAppraisalDate = cfcljdsAppraisalDate;
    }

    public Date getCfcljdsEndDate() {
        return cfcljdsEndDate;
    }

    public void setCfcljdsEndDate(Date cfcljdsEndDate) {
        this.cfcljdsEndDate = cfcljdsEndDate;
    }

    public Date getCfcljdsAppraisalDelayDate() {
        return cfcljdsAppraisalDelayDate;
    }

    public void setCfcljdsAppraisalDelayDate(Date cfcljdsAppraisalDelayDate) {
        this.cfcljdsAppraisalDelayDate = cfcljdsAppraisalDelayDate;
    }

    public Date getCfcljdsArriveDate() {
        return cfcljdsArriveDate;
    }

    public void setCfcljdsArriveDate(Date cfcljdsArriveDate) {
        this.cfcljdsArriveDate = cfcljdsArriveDate;
    }

    public Date getCfcljdsReceiveDate() {
        return cfcljdsReceiveDate;
    }

    public void setCfcljdsReceiveDate(Date cfcljdsReceiveDate) {
        this.cfcljdsReceiveDate = cfcljdsReceiveDate;
    }

    public Date getCfcljdsSealDate() {
        return cfcljdsSealDate;
    }

    public void setCfcljdsSealDate(Date cfcljdsSealDate) {
        this.cfcljdsSealDate = cfcljdsSealDate;
    }

    public Date getKycljdsDelayDate() {
        return kycljdsDelayDate;
    }

    public void setKycljdsDelayDate(Date kycljdsDelayDate) {
        this.kycljdsDelayDate = kycljdsDelayDate;
    }

    public Date getKycljdsAppraisalDate() {
        return kycljdsAppraisalDate;
    }

    public void setKycljdsAppraisalDate(Date kycljdsAppraisalDate) {
        this.kycljdsAppraisalDate = kycljdsAppraisalDate;
    }

    public Date getKycljdsEndDate() {
        return kycljdsEndDate;
    }

    public void setKycljdsEndDate(Date kycljdsEndDate) {
        this.kycljdsEndDate = kycljdsEndDate;
    }

    public Date getKycljdsAppraisalDelayDate() {
        return kycljdsAppraisalDelayDate;
    }

    public void setKycljdsAppraisalDelayDate(Date kycljdsAppraisalDelayDate) {
        this.kycljdsAppraisalDelayDate = kycljdsAppraisalDelayDate;
    }

    public Date getKycljdsArriveDate() {
        return kycljdsArriveDate;
    }

    public void setKycljdsArriveDate(Date kycljdsArriveDate) {
        this.kycljdsArriveDate = kycljdsArriveDate;
    }

    public Date getKycljdsReceiveDate() {
        return kycljdsReceiveDate;
    }

    public void setKycljdsReceiveDate(Date kycljdsReceiveDate) {
        this.kycljdsReceiveDate = kycljdsReceiveDate;
    }

    public Date getKycljdsSealDate() {
        return kycljdsSealDate;
    }

    public void setKycljdsSealDate(Date kycljdsSealDate) {
        this.kycljdsSealDate = kycljdsSealDate;
    }

    public Date getDcxwblStartDate() {
        return dcxwblStartDate;
    }

    public void setDcxwblStartDate(Date dcxwblStartDate) {
        this.dcxwblStartDate = dcxwblStartDate;
    }

    public Date getDcxwblEndDate() {
        return dcxwblEndDate;
    }

    public void setDcxwblEndDate(Date dcxwblEndDate) {
        this.dcxwblEndDate = dcxwblEndDate;
    }

    public Date getZjclysqdDcqzArriveDate() {
        return zjclysqdDcqzArriveDate;
    }

    public void setZjclysqdDcqzArriveDate(Date zjclysqdDcqzArriveDate) {
        this.zjclysqdDcqzArriveDate = zjclysqdDcqzArriveDate;
    }

    public Date getZjclysqdDcqzReceiveDate() {
        return zjclysqdDcqzReceiveDate;
    }

    public void setZjclysqdDcqzReceiveDate(Date zjclysqdDcqzReceiveDate) {
        this.zjclysqdDcqzReceiveDate = zjclysqdDcqzReceiveDate;
    }

    public Date getSdhzDcqzArriveDate() {
        return sdhzDcqzArriveDate;
    }

    public void setSdhzDcqzArriveDate(Date sdhzDcqzArriveDate) {
        this.sdhzDcqzArriveDate = sdhzDcqzArriveDate;
    }

    public Date getZjxxdjbctzsSatrtDate() {
        return zjxxdjbctzsSatrtDate;
    }

    public void setZjxxdjbctzsSatrtDate(Date zjxxdjbctzsSatrtDate) {
        this.zjxxdjbctzsSatrtDate = zjxxdjbctzsSatrtDate;
    }

    public Date getZjxxdjbctzsEndDate() {
        return zjxxdjbctzsEndDate;
    }

    public void setZjxxdjbctzsEndDate(Date zjxxdjbctzsEndDate) {
        this.zjxxdjbctzsEndDate = zjxxdjbctzsEndDate;
    }

    public Date getZjxxdjbctzsArriveDate() {
        return zjxxdjbctzsArriveDate;
    }

    public void setZjxxdjbctzsArriveDate(Date zjxxdjbctzsArriveDate) {
        this.zjxxdjbctzsArriveDate = zjxxdjbctzsArriveDate;
    }

    public Date getZjxxdjbctzsReceiveDate() {
        return zjxxdjbctzsReceiveDate;
    }

    public void setZjxxdjbctzsReceiveDate(Date zjxxdjbctzsReceiveDate) {
        this.zjxxdjbctzsReceiveDate = zjxxdjbctzsReceiveDate;
    }

    public Date getZjxxdjbctzsSealDate() {
        return zjxxdjbctzsSealDate;
    }

    public void setZjxxdjbctzsSealDate(Date zjxxdjbctzsSealDate) {
        this.zjxxdjbctzsSealDate = zjxxdjbctzsSealDate;
    }

    public Date getZjxxdjbccljdsSatrtDate() {
        return zjxxdjbccljdsSatrtDate;
    }

    public void setZjxxdjbccljdsSatrtDate(Date zjxxdjbccljdsSatrtDate) {
        this.zjxxdjbccljdsSatrtDate = zjxxdjbccljdsSatrtDate;
    }

    public Date getZjxxdjbccljdsEndDate() {
        return zjxxdjbccljdsEndDate;
    }

    public void setZjxxdjbccljdsEndDate(Date zjxxdjbccljdsEndDate) {
        this.zjxxdjbccljdsEndDate = zjxxdjbccljdsEndDate;
    }

    public Date getZjxxdjbccljdsSealDate() {
        return zjxxdjbccljdsSealDate;
    }

    public void setZjxxdjbccljdsSealDate(Date zjxxdjbccljdsSealDate) {
        this.zjxxdjbccljdsSealDate = zjxxdjbccljdsSealDate;
    }

    public Date getZjxxdjbccljdsArriveDate() {
        return zjxxdjbccljdsArriveDate;
    }

    public void setZjxxdjbccljdsArriveDate(Date zjxxdjbccljdsArriveDate) {
        this.zjxxdjbccljdsArriveDate = zjxxdjbccljdsArriveDate;
    }

    public Date getZjxxdjbccljdsReceiveDate() {
        return zjxxdjbccljdsReceiveDate;
    }

    public void setZjxxdjbccljdsReceiveDate(Date zjxxdjbccljdsReceiveDate) {
        this.zjxxdjbccljdsReceiveDate = zjxxdjbccljdsReceiveDate;
    }

    public Date getCyqzwqcltzsSatrtDate() {
        return cyqzwqcltzsSatrtDate;
    }

    public void setCyqzwqcltzsSatrtDate(Date cyqzwqcltzsSatrtDate) {
        this.cyqzwqcltzsSatrtDate = cyqzwqcltzsSatrtDate;
    }

    public Date getCyqzwqcltzsEndDate() {
        return cyqzwqcltzsEndDate;
    }

    public void setCyqzwqcltzsEndDate(Date cyqzwqcltzsEndDate) {
        this.cyqzwqcltzsEndDate = cyqzwqcltzsEndDate;
    }

    public Date getCyqzwqcltzsSealDate() {
        return cyqzwqcltzsSealDate;
    }

    public void setCyqzwqcltzsSealDate(Date cyqzwqcltzsSealDate) {
        this.cyqzwqcltzsSealDate = cyqzwqcltzsSealDate;
    }

    public Date getCyqzwqcltzsArriveDate() {
        return cyqzwqcltzsArriveDate;
    }

    public void setCyqzwqcltzsArriveDate(Date cyqzwqcltzsArriveDate) {
        this.cyqzwqcltzsArriveDate = cyqzwqcltzsArriveDate;
    }

    public Date getCyqzwqcltzsReceiveDate() {
        return cyqzwqcltzsReceiveDate;
    }

    public void setCyqzwqcltzsReceiveDate(Date cyqzwqcltzsReceiveDate) {
        this.cyqzwqcltzsReceiveDate = cyqzwqcltzsReceiveDate;
    }

    public Date getAjysdDcqzSendDate() {
        return ajysdDcqzSendDate;
    }

    public void setAjysdDcqzSendDate(Date ajysdDcqzSendDate) {
        this.ajysdDcqzSendDate = ajysdDcqzSendDate;
    }

    public Date getZlgztzsModifyDate() {
        return zlgztzsModifyDate;
    }

    public void setZlgztzsModifyDate(Date zlgztzsModifyDate) {
        this.zlgztzsModifyDate = zlgztzsModifyDate;
    }

    public Date getZlgztzsSealDate() {
        return zlgztzsSealDate;
    }

    public void setZlgztzsSealDate(Date zlgztzsSealDate) {
        this.zlgztzsSealDate = zlgztzsSealDate;
    }

    public Date getZlgztzsArriveDate() {
        return zlgztzsArriveDate;
    }

    public void setZlgztzsArriveDate(Date zlgztzsArriveDate) {
        this.zlgztzsArriveDate = zlgztzsArriveDate;
    }

    public Date getZlgztzsReceiveDate() {
        return zlgztzsReceiveDate;
    }

    public void setZlgztzsReceiveDate(Date zlgztzsReceiveDate) {
        this.zlgztzsReceiveDate = zlgztzsReceiveDate;
    }

    public Date getXzcfsxgzsSealDate() {
        return xzcfsxgzsSealDate;
    }

    public void setXzcfsxgzsSealDate(Date xzcfsxgzsSealDate) {
        this.xzcfsxgzsSealDate = xzcfsxgzsSealDate;
    }

    public Date getXzcfsxgzsSignDate() {
        return xzcfsxgzsSignDate;
    }

    public void setXzcfsxgzsSignDate(Date xzcfsxgzsSignDate) {
        this.xzcfsxgzsSignDate = xzcfsxgzsSignDate;
    }

    public Date getXzcflxcgsSealDate() {
        return xzcflxcgsSealDate;
    }

    public void setXzcflxcgsSealDate(Date xzcflxcgsSealDate) {
        this.xzcflxcgsSealDate = xzcflxcgsSealDate;
    }

    public Date getXzcflxcgsArriveDate() {
        return xzcflxcgsArriveDate;
    }

    public void setXzcflxcgsArriveDate(Date xzcflxcgsArriveDate) {
        this.xzcflxcgsArriveDate = xzcflxcgsArriveDate;
    }

    public Date getXzcflxcgsReceiveDate() {
        return xzcflxcgsReceiveDate;
    }

    public void setXzcflxcgsReceiveDate(Date xzcflxcgsReceiveDate) {
        this.xzcflxcgsReceiveDate = xzcflxcgsReceiveDate;
    }

    public Date getZjclysqdZggzArriveDate() {
        return zjclysqdZggzArriveDate;
    }

    public void setZjclysqdZggzArriveDate(Date zjclysqdZggzArriveDate) {
        this.zjclysqdZggzArriveDate = zjclysqdZggzArriveDate;
    }

    public Date getZjclysqdZggzReceiveDate() {
        return zjclysqdZggzReceiveDate;
    }

    public void setZjclysqdZggzReceiveDate(Date zjclysqdZggzReceiveDate) {
        this.zjclysqdZggzReceiveDate = zjclysqdZggzReceiveDate;
    }

    public Date getSdhzZggzArriveDate() {
        return sdhzZggzArriveDate;
    }

    public void setSdhzZggzArriveDate(Date sdhzZggzArriveDate) {
        this.sdhzZggzArriveDate = sdhzZggzArriveDate;
    }

    public Date getAjysdZggzSendDate() {
        return ajysdZggzSendDate;
    }

    public void setAjysdZggzSendDate(Date ajysdZggzSendDate) {
        this.ajysdZggzSendDate = ajysdZggzSendDate;
    }

    public Date getAjclcpbSealDate() {
        return ajclcpbSealDate;
    }

    public void setAjclcpbSealDate(Date ajclcpbSealDate) {
        this.ajclcpbSealDate = ajclcpbSealDate;
    }

    public Date getSdhzAjcpArriveDate() {
        return sdhzAjcpArriveDate;
    }

    public void setSdhzAjcpArriveDate(Date sdhzAjcpArriveDate) {
        this.sdhzAjcpArriveDate = sdhzAjcpArriveDate;
    }

    public Date getAjjttlAtartDate() {
        return ajjttlAtartDate;
    }

    public void setAjjttlAtartDate(Date ajjttlAtartDate) {
        this.ajjttlAtartDate = ajjttlAtartDate;
    }

    public Date getAjjttlEndDate() {
        return ajjttlEndDate;
    }

    public void setAjjttlEndDate(Date ajjttlEndDate) {
        this.ajjttlEndDate = ajjttlEndDate;
    }

    public Date getRkckdArriveDate() {
        return rkckdArriveDate;
    }

    public void setRkckdArriveDate(Date rkckdArriveDate) {
        this.rkckdArriveDate = rkckdArriveDate;
    }

    public Date getRkckdReceiveDate() {
        return rkckdReceiveDate;
    }

    public void setRkckdReceiveDate(Date rkckdReceiveDate) {
        this.rkckdReceiveDate = rkckdReceiveDate;
    }

    public Date getJabgSealDate() {
        return jabgSealDate;
    }

    public void setJabgSealDate(Date jabgSealDate) {
        this.jabgSealDate = jabgSealDate;
    }

    public Date getYqjnfkpzsDelayDate() {
        return yqjnfkpzsDelayDate;
    }

    public void setYqjnfkpzsDelayDate(Date yqjnfkpzsDelayDate) {
        this.yqjnfkpzsDelayDate = yqjnfkpzsDelayDate;
    }

    public Date getYqjnfkpzsSealDate() {
        return yqjnfkpzsSealDate;
    }

    public void setYqjnfkpzsSealDate(Date yqjnfkpzsSealDate) {
        this.yqjnfkpzsSealDate = yqjnfkpzsSealDate;
    }

    public Date getYqjnfkpzsArriveDate() {
        return yqjnfkpzsArriveDate;
    }

    public void setYqjnfkpzsArriveDate(Date yqjnfkpzsArriveDate) {
        this.yqjnfkpzsArriveDate = yqjnfkpzsArriveDate;
    }

    public Date getYqjnfkpzsRecelveDate() {
        return yqjnfkpzsRecelveDate;
    }

    public void setYqjnfkpzsRecelveDate(Date yqjnfkpzsRecelveDate) {
        this.yqjnfkpzsRecelveDate = yqjnfkpzsRecelveDate;
    }

    public Date getFqjnfkpzsOneDate() {
        return fqjnfkpzsOneDate;
    }

    public void setFqjnfkpzsOneDate(Date fqjnfkpzsOneDate) {
        this.fqjnfkpzsOneDate = fqjnfkpzsOneDate;
    }

    public Date getFqjnfkpzsTwoDate() {
        return fqjnfkpzsTwoDate;
    }

    public void setFqjnfkpzsTwoDate(Date fqjnfkpzsTwoDate) {
        this.fqjnfkpzsTwoDate = fqjnfkpzsTwoDate;
    }

    public Date getFqjnfkpzsThreeDate() {
        return fqjnfkpzsThreeDate;
    }

    public void setFqjnfkpzsThreeDate(Date fqjnfkpzsThreeDate) {
        this.fqjnfkpzsThreeDate = fqjnfkpzsThreeDate;
    }

    public Date getFqjnfkpzsSealDate() {
        return fqjnfkpzsSealDate;
    }

    public void setFqjnfkpzsSealDate(Date fqjnfkpzsSealDate) {
        this.fqjnfkpzsSealDate = fqjnfkpzsSealDate;
    }

    public Date getFqjnfkpzsArriveDate() {
        return fqjnfkpzsArriveDate;
    }

    public void setFqjnfkpzsArriveDate(Date fqjnfkpzsArriveDate) {
        this.fqjnfkpzsArriveDate = fqjnfkpzsArriveDate;
    }

    public Date getFqjnfkpzsRecelveDate() {
        return fqjnfkpzsRecelveDate;
    }

    public void setFqjnfkpzsRecelveDate(Date fqjnfkpzsRecelveDate) {
        this.fqjnfkpzsRecelveDate = fqjnfkpzsRecelveDate;
    }

    public Date getQzzxsqsSealDate() {
        return qzzxsqsSealDate;
    }

    public void setQzzxsqsSealDate(Date qzzxsqsSealDate) {
        this.qzzxsqsSealDate = qzzxsqsSealDate;
    }

    public Date getXzcfjcfkjdsSealDate() {
        return xzcfjcfkjdsSealDate;
    }

    public void setXzcfjcfkjdsSealDate(Date xzcfjcfkjdsSealDate) {
        this.xzcfjcfkjdsSealDate = xzcfjcfkjdsSealDate;
    }

    public Date getXzcfjcfkjdsArriveDate() {
        return xzcfjcfkjdsArriveDate;
    }

    public void setXzcfjcfkjdsArriveDate(Date xzcfjcfkjdsArriveDate) {
        this.xzcfjcfkjdsArriveDate = xzcfjcfkjdsArriveDate;
    }

    public Date getXzcfjcfkjdsRecelveDate() {
        return xzcfjcfkjdsRecelveDate;
    }

    public void setXzcfjcfkjdsRecelveDate(Date xzcfjcfkjdsRecelveDate) {
        this.xzcfjcfkjdsRecelveDate = xzcfjcfkjdsRecelveDate;
    }

    public Date getXzcftztzsStartDate() {
        return xzcftztzsStartDate;
    }

    public void setXzcftztzsStartDate(Date xzcftztzsStartDate) {
        this.xzcftztzsStartDate = xzcftztzsStartDate;
    }

    public Date getXzcftztzsSealDate() {
        return xzcftztzsSealDate;
    }

    public void setXzcftztzsSealDate(Date xzcftztzsSealDate) {
        this.xzcftztzsSealDate = xzcftztzsSealDate;
    }

    public Date getXzcftztzsArriveDate() {
        return xzcftztzsArriveDate;
    }

    public void setXzcftztzsArriveDate(Date xzcftztzsArriveDate) {
        this.xzcftztzsArriveDate = xzcftztzsArriveDate;
    }

    public Date getXzcftztzsRecelveDate() {
        return xzcftztzsRecelveDate;
    }

    public void setXzcftztzsRecelveDate(Date xzcftztzsRecelveDate) {
        this.xzcftztzsRecelveDate = xzcftztzsRecelveDate;
    }

    public Date getTzggSealDate() {
        return tzggSealDate;
    }

    public void setTzggSealDate(Date tzggSealDate) {
        this.tzggSealDate = tzggSealDate;
    }

    public Date getXzcftzblStartDate() {
        return xzcftzblStartDate;
    }

    public void setXzcftzblStartDate(Date xzcftzblStartDate) {
        this.xzcftzblStartDate = xzcftzblStartDate;
    }

    public Date getXzcftzblEndDate() {
        return xzcftzblEndDate;
    }

    public void setXzcftzblEndDate(Date xzcftzblEndDate) {
        this.xzcftzblEndDate = xzcftzblEndDate;
    }

    public Date getXzcftzbgSealDate() {
        return xzcftzbgSealDate;
    }

    public void setXzcftzbgSealDate(Date xzcftzbgSealDate) {
        this.xzcftzbgSealDate = xzcftzbgSealDate;
    }

}
