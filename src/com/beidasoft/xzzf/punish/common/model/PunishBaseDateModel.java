package com.beidasoft.xzzf.punish.common.model;

/**
 * 案件办理时间表MODEL类
 */
public class PunishBaseDateModel {
    // 执法办案统一编号
    private String baseId;

    // 立案日期
    private String laspDateStr;

    // 现场检查（勘验）检查开始
    private String xcjckyblStartDateStr;

    // 现场检查（勘验）检查结束
    private String xcjckyblEndDateStr;

    // 现场检查（勘验）签章时间
    private String xcjckyblSealDateStr;

    // 现场检查（勘验）当事人签字
    private String xcjckyblSignDateStr;

    // 物品清单签章时间
    private String wpqdSealDateStr;

    // 物品清单当事人签字时间
    private String wpqdSignDateStr;

    // 行政处罚时间（通用）
    private String xzcfjdsDateStr;

    // 查封决定书开始时间
    private String cfjdsStartDateStr;

    // 查封决定书结束时间
    private String cfjdsEndDateStr;

    // 查封决定书签章时间
    private String cfjdsSealDateStr;

    // 查封决定书送达时间
    private String cfjdsArriveDateStr;

    // 查封决定书签收时间
    private String cfjdsReceiveDateStr;

    // 扣押决定书开始时间
    private String kyjdsStartDateStr;

    // 扣押决定书结束时间
    private String kyjdsEndDateStr;

    // 扣押决定书签章时间
    private String kyjdsSealDateStr;

    // 扣押决定书送达时间
    private String kyjdsArriveDateStr;

    // 扣押决定书签收时间
    private String kyjdsReceiveDateStr;

    // 取证情况及证据说明取证开始
    private String qzqkjzjsmStartDateStr;

    // 取证情况及证据说明取证结束
    private String qzqkjzjsmEndDateStr;

    // 取证情况证据说明当事人签字
    private String qzqkjzjsmSignDateStr;

    // 抽样取证凭证签章时间
    private String cyqzpzSealDateStr;

    // 抽样取证凭证送达时间
    private String cyqzpzArriveDateStr;

    // 抽样取证凭证签收时间
    private String cyqzpzReceiveDateStr;

    // 审批表抽样取证
    private String spbCyqzDateStr;

    // 审批表抽样取证物品处理
    private String spbCyqzwqclDateStr;

    // 审批表证据先行登记保存
    private String spbZjxxdjbcDateStr;

    // 审批表证据先行登记保存处理 
    private String spbZjxxdjbcclDateStr;

    // 审批表查封/扣押
    private String spbCfkyDateStr;

    // 审批表延长查封/扣押
    private String spbYccfkyDateStr;

    // 审批表解除查封/扣押
    private String spbJccfkyDateStr;

    // 审批表移送案件
    private String spbYsajDateStr;

    // 审批表案件延期办理
    private String spbAjyqblDateStr;

    // 审批表延期/分期缴纳罚款
    private String spbYqfqjnfkDateStr;

    // 审批表出库
    private String spbCkDateStr;

    // 审批表撤销案件
    private String spbCxajDateStr;

    // 审批表其他
    private String spbQtDateStr;

    // 查封处理决定书延长到
    private String cfcljdsDelayDateStr;

    // 查封处理决定书鉴定开始
    private String cfcljdsAppraisalDateStr;

    // 查封处理决定书鉴定结束
    private String cfcljdsEndDateStr;

    // 查封处理决定书鉴定顺延到
    private String cfcljdsAppraisalDelayDateStr;

    // 查封处理决定书送达时间
    private String cfcljdsArriveDateStr;

    // 查封处理决定书签收时间
    private String cfcljdsReceiveDateStr;

    // 查封处理决定书签章时间
    private String cfcljdsSealDateStr;

    // 扣押处理决定书延长到
    private String kycljdsDelayDateStr;

    // 扣押处理决定书鉴定开始
    private String kycljdsAppraisalDateStr;

    // 扣押处理决定书鉴定结束
    private String kycljdsEndDateStr;

    // 扣押处理决定书鉴定顺延到
    private String kycljdsAppraisalDelayDateStr;

    // 扣押处理决定书送达时间
    private String kycljdsArriveDateStr;

    // 扣押处理决定书签收时间
    private String kycljdsReceiveDateStr;

    // 扣押处理决定书签章时间
    private String kycljdsSealDateStr;

    // 调查询问笔录询问开始时间
    private String dcxwblStartDateStr;

    // 调查询问笔录询问结束时间
    private String dcxwblEndDateStr;

    // 证据材料移送清单移送时间
    private String zjclysqdDcqzArriveDateStr;

    // 证据材料移送清单接收时间
    private String zjclysqdDcqzReceiveDateStr;

    // 送达回证送达时间
    private String sdhzDcqzArriveDateStr;

    // 证据先行登记保存期限开始
    private String zjxxdjbctzsSatrtDateStr;

    // 证据先行登记保存期限结束
    private String zjxxdjbctzsEndDateStr;

    // 证据先行登记保存移送时间
    private String zjxxdjbctzsArriveDateStr;

    // 证据先行登记保存接收时间
    private String zjxxdjbctzsReceiveDateStr;

    // 证据先行登记保存签章时间
    private String zjxxdjbctzsSealDateStr;

    // 证据先行登记保存处理决定书鉴定开始
    private String zjxxdjbccljdsSatrtDateStr;

    // 证据先行登记保存处理决定书鉴定结束
    private String zjxxdjbccljdsEndDateStr;

    // 证据先行登记保存处理决定书签章时间
    private String zjxxdjbccljdsSealDateStr;

    // 证据先行登记保存处理决定书送达时间
    private String zjxxdjbccljdsArriveDateStr;

    // 证据先行登记保存处理决定书接收时间
    private String zjxxdjbccljdsReceiveDateStr;

    // 抽样取证物品处理通知书鉴定开始
    private String cyqzwqcltzsSatrtDateStr;

    // 抽样取证物品处理通知书鉴定结束
    private String cyqzwqcltzsEndDateStr;

    // 抽样取证物品处理通知书签章时间
    private String cyqzwqcltzsSealDateStr;

    // 抽样取证物品处理通知书送达时间
    private String cyqzwqcltzsArriveDateStr;

    // 抽样取证物品处理通知书接收时间
    private String cyqzwqcltzsReceiveDateStr;

    // 案件移送单移送时间
    private String ajysdDcqzSendDateStr;

    // 责令改正通知书整改时间
    private String zlgztzsModifyDateStr;

    // 责令改正通知书签章时间
    private String zlgztzsSealDateStr;

    // 责令改正通知书送达时间
    private String zlgztzsArriveDateStr;

    // 责令改正通知书签收时间
    private String zlgztzsReceiveDateStr;

    // 行政处罚事先告知书签章时间
    private String xzcfsxgzsSealDateStr;

    // 行政处罚事先告知书当事人签字
    private String xzcfsxgzsSignDateStr;

    // 行政处罚履行催告书签章时间
    private String xzcflxcgsSealDateStr;

    // 行政处罚履行催告书送达时间
    private String xzcflxcgsArriveDateStr;

    // 行政处罚履行催告书签收时间
    private String xzcflxcgsReceiveDateStr;

    // 证据材料移送清单移送时间
    private String zjclysqdZggzArriveDateStr;

    // 证据材料移送清单接收时间
    private String zjclysqdZggzReceiveDateStr;

    // 送达回证送达时间
    private String sdhzZggzArriveDateStr;

    // 案件移送单移送时间
    private String ajysdZggzSendDateStr;

    // 案件处理呈批表领导签字时间
    private String ajclcpbSealDateStr;

    // 送达回证送达时间(案件呈批)
    private String sdhzAjcpArriveDateStr;

    // 案件集体讨论记录开始时间
    private String ajjttlAtartDateStr;

    // 案件集体讨论记录结束时间
    private String ajjttlEndDateStr;

    // 入库出库单移送时间
    private String rkckdArriveDateStr;

    // 入库出库单接收时间
    private String rkckdReceiveDateStr;

    // 结案报告领导签字时间
    private String jabgSealDateStr;

    // 延期缴纳罚款批准书延期至
    private String yqjnfkpzsDelayDateStr;

    // 延期缴纳罚款批准书签章时间
    private String yqjnfkpzsSealDateStr;

    // 延期缴纳罚款批准书送达时间
    private String yqjnfkpzsArriveDateStr;

    // 延期缴纳罚款批准书签收时间
    private String yqjnfkpzsRecelveDateStr;

    // 分期缴纳罚款批准书第一期
    private String fqjnfkpzsOneDateStr;

    // 分期缴纳罚款批准书第二期
    private String fqjnfkpzsTwoDateStr;

    // 分期缴纳罚款批准书第三期
    private String fqjnfkpzsThreeDateStr;

    // 分期缴纳罚款批准书签章时间
    private String fqjnfkpzsSealDateStr;

    // 分期缴纳罚款批准书送达时间
    private String fqjnfkpzsArriveDateStr;

    // 分期缴纳罚款批准书签收时间
    private String fqjnfkpzsRecelveDateStr;

    // 强制执行申请书批准时间
    private String qzzxsqsSealDateStr;

    // 行政处罚加处罚款决定书签章时间
    private String xzcfjcfkjdsSealDateStr;

    // 行政处罚加处罚款决定书送达时间
    private String xzcfjcfkjdsArriveDateStr;

    // 行政处罚加处罚款决定书签收时间
    private String xzcfjcfkjdsRecelveDateStr;

    // 行政处罚听证通知书听证时间
    private String xzcftztzsStartDateStr;

    // 行政处罚听证通知书签章时间
    private String xzcftztzsSealDateStr;

    // 行政处罚听证通知书送达时间
    private String xzcftztzsArriveDateStr;

    // 行政处罚听证通知书签收时间
    private String xzcftztzsRecelveDateStr;

    // 听证公告签章时间
    private String tzggSealDateStr;

    // 行政处罚听证笔录开始时间
    private String xzcftzblStartDateStr;

    // 行政处罚听证笔录结束时间
    private String xzcftzblEndDateStr;

    // 行政处罚听证报告签字时间
    private String xzcftzbgSealDateStr;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getLaspDateStr() {
        return laspDateStr;
    }

    public void setLaspDateStr(String laspDateStr) {
        this.laspDateStr = laspDateStr;
    }

    public String getXcjckyblStartDateStr() {
        return xcjckyblStartDateStr;
    }

    public void setXcjckyblStartDateStr(String xcjckyblStartDateStr) {
        this.xcjckyblStartDateStr = xcjckyblStartDateStr;
    }

    public String getXcjckyblEndDateStr() {
        return xcjckyblEndDateStr;
    }

    public void setXcjckyblEndDateStr(String xcjckyblEndDateStr) {
        this.xcjckyblEndDateStr = xcjckyblEndDateStr;
    }

    public String getXcjckyblSealDateStr() {
        return xcjckyblSealDateStr;
    }

    public void setXcjckyblSealDateStr(String xcjckyblSealDateStr) {
        this.xcjckyblSealDateStr = xcjckyblSealDateStr;
    }

    public String getXcjckyblSignDateStr() {
        return xcjckyblSignDateStr;
    }

    public void setXcjckyblSignDateStr(String xcjckyblSignDateStr) {
        this.xcjckyblSignDateStr = xcjckyblSignDateStr;
    }

    public String getWpqdSealDateStr() {
        return wpqdSealDateStr;
    }

    public void setWpqdSealDateStr(String wpqdSealDateStr) {
        this.wpqdSealDateStr = wpqdSealDateStr;
    }

    public String getWpqdSignDateStr() {
        return wpqdSignDateStr;
    }

    public void setWpqdSignDateStr(String wpqdSignDateStr) {
        this.wpqdSignDateStr = wpqdSignDateStr;
    }

    public String getXzcfjdsDateStr() {
        return xzcfjdsDateStr;
    }

    public void setXzcfjdsDateStr(String xzcfjdsDateStr) {
        this.xzcfjdsDateStr = xzcfjdsDateStr;
    }

    public String getCfjdsStartDateStr() {
        return cfjdsStartDateStr;
    }

    public void setCfjdsStartDateStr(String cfjdsStartDateStr) {
        this.cfjdsStartDateStr = cfjdsStartDateStr;
    }

    public String getCfjdsEndDateStr() {
        return cfjdsEndDateStr;
    }

    public void setCfjdsEndDateStr(String cfjdsEndDateStr) {
        this.cfjdsEndDateStr = cfjdsEndDateStr;
    }

    public String getCfjdsSealDateStr() {
        return cfjdsSealDateStr;
    }

    public void setCfjdsSealDateStr(String cfjdsSealDateStr) {
        this.cfjdsSealDateStr = cfjdsSealDateStr;
    }

    public String getCfjdsArriveDateStr() {
        return cfjdsArriveDateStr;
    }

    public void setCfjdsArriveDateStr(String cfjdsArriveDateStr) {
        this.cfjdsArriveDateStr = cfjdsArriveDateStr;
    }

    public String getCfjdsReceiveDateStr() {
        return cfjdsReceiveDateStr;
    }

    public void setCfjdsReceiveDateStr(String cfjdsReceiveDateStr) {
        this.cfjdsReceiveDateStr = cfjdsReceiveDateStr;
    }

    public String getKyjdsStartDateStr() {
        return kyjdsStartDateStr;
    }

    public void setKyjdsStartDateStr(String kyjdsStartDateStr) {
        this.kyjdsStartDateStr = kyjdsStartDateStr;
    }

    public String getKyjdsEndDateStr() {
        return kyjdsEndDateStr;
    }

    public void setKyjdsEndDateStr(String kyjdsEndDateStr) {
        this.kyjdsEndDateStr = kyjdsEndDateStr;
    }

    public String getKyjdsSealDateStr() {
        return kyjdsSealDateStr;
    }

    public void setKyjdsSealDateStr(String kyjdsSealDateStr) {
        this.kyjdsSealDateStr = kyjdsSealDateStr;
    }

    public String getKyjdsArriveDateStr() {
        return kyjdsArriveDateStr;
    }

    public void setKyjdsArriveDateStr(String kyjdsArriveDateStr) {
        this.kyjdsArriveDateStr = kyjdsArriveDateStr;
    }

    public String getKyjdsReceiveDateStr() {
        return kyjdsReceiveDateStr;
    }

    public void setKyjdsReceiveDateStr(String kyjdsReceiveDateStr) {
        this.kyjdsReceiveDateStr = kyjdsReceiveDateStr;
    }

    public String getQzqkjzjsmStartDateStr() {
        return qzqkjzjsmStartDateStr;
    }

    public void setQzqkjzjsmStartDateStr(String qzqkjzjsmStartDateStr) {
        this.qzqkjzjsmStartDateStr = qzqkjzjsmStartDateStr;
    }

    public String getQzqkjzjsmEndDateStr() {
        return qzqkjzjsmEndDateStr;
    }

    public void setQzqkjzjsmEndDateStr(String qzqkjzjsmEndDateStr) {
        this.qzqkjzjsmEndDateStr = qzqkjzjsmEndDateStr;
    }

    public String getQzqkjzjsmSignDateStr() {
        return qzqkjzjsmSignDateStr;
    }

    public void setQzqkjzjsmSignDateStr(String qzqkjzjsmSignDateStr) {
        this.qzqkjzjsmSignDateStr = qzqkjzjsmSignDateStr;
    }

    public String getCyqzpzSealDateStr() {
        return cyqzpzSealDateStr;
    }

    public void setCyqzpzSealDateStr(String cyqzpzSealDateStr) {
        this.cyqzpzSealDateStr = cyqzpzSealDateStr;
    }

    public String getCyqzpzArriveDateStr() {
        return cyqzpzArriveDateStr;
    }

    public void setCyqzpzArriveDateStr(String cyqzpzArriveDateStr) {
        this.cyqzpzArriveDateStr = cyqzpzArriveDateStr;
    }

    public String getCyqzpzReceiveDateStr() {
        return cyqzpzReceiveDateStr;
    }

    public void setCyqzpzReceiveDateStr(String cyqzpzReceiveDateStr) {
        this.cyqzpzReceiveDateStr = cyqzpzReceiveDateStr;
    }

    public String getSpbCyqzDateStr() {
        return spbCyqzDateStr;
    }

    public void setSpbCyqzDateStr(String spbCyqzDateStr) {
        this.spbCyqzDateStr = spbCyqzDateStr;
    }

    public String getSpbCyqzwqclDateStr() {
        return spbCyqzwqclDateStr;
    }

    public void setSpbCyqzwqclDateStr(String spbCyqzwqclDateStr) {
        this.spbCyqzwqclDateStr = spbCyqzwqclDateStr;
    }

    public String getSpbZjxxdjbcDateStr() {
        return spbZjxxdjbcDateStr;
    }

    public void setSpbZjxxdjbcDateStr(String spbZjxxdjbcDateStr) {
        this.spbZjxxdjbcDateStr = spbZjxxdjbcDateStr;
    }

    public String getSpbZjxxdjbcclDateStr() {
        return spbZjxxdjbcclDateStr;
    }

    public void setSpbZjxxdjbcclDateStr(String spbZjxxdjbcclDateStr) {
        this.spbZjxxdjbcclDateStr = spbZjxxdjbcclDateStr;
    }

    public String getSpbCfkyDateStr() {
        return spbCfkyDateStr;
    }

    public void setSpbCfkyDateStr(String spbCfkyDateStr) {
        this.spbCfkyDateStr = spbCfkyDateStr;
    }

    public String getSpbYccfkyDateStr() {
        return spbYccfkyDateStr;
    }

    public void setSpbYccfkyDateStr(String spbYccfkyDateStr) {
        this.spbYccfkyDateStr = spbYccfkyDateStr;
    }

    public String getSpbJccfkyDateStr() {
        return spbJccfkyDateStr;
    }

    public void setSpbJccfkyDateStr(String spbJccfkyDateStr) {
        this.spbJccfkyDateStr = spbJccfkyDateStr;
    }

    public String getSpbYsajDateStr() {
        return spbYsajDateStr;
    }

    public void setSpbYsajDateStr(String spbYsajDateStr) {
        this.spbYsajDateStr = spbYsajDateStr;
    }

    public String getSpbAjyqblDateStr() {
        return spbAjyqblDateStr;
    }

    public void setSpbAjyqblDateStr(String spbAjyqblDateStr) {
        this.spbAjyqblDateStr = spbAjyqblDateStr;
    }

    public String getSpbYqfqjnfkDateStr() {
        return spbYqfqjnfkDateStr;
    }

    public void setSpbYqfqjnfkDateStr(String spbYqfqjnfkDateStr) {
        this.spbYqfqjnfkDateStr = spbYqfqjnfkDateStr;
    }

    public String getSpbCkDateStr() {
        return spbCkDateStr;
    }

    public void setSpbCkDateStr(String spbCkDateStr) {
        this.spbCkDateStr = spbCkDateStr;
    }

    public String getSpbCxajDateStr() {
        return spbCxajDateStr;
    }

    public void setSpbCxajDateStr(String spbCxajDateStr) {
        this.spbCxajDateStr = spbCxajDateStr;
    }

    public String getSpbQtDateStr() {
        return spbQtDateStr;
    }

    public void setSpbQtDateStr(String spbQtDateStr) {
        this.spbQtDateStr = spbQtDateStr;
    }

    public String getCfcljdsDelayDateStr() {
        return cfcljdsDelayDateStr;
    }

    public void setCfcljdsDelayDateStr(String cfcljdsDelayDateStr) {
        this.cfcljdsDelayDateStr = cfcljdsDelayDateStr;
    }

    public String getCfcljdsAppraisalDateStr() {
        return cfcljdsAppraisalDateStr;
    }

    public void setCfcljdsAppraisalDateStr(String cfcljdsAppraisalDateStr) {
        this.cfcljdsAppraisalDateStr = cfcljdsAppraisalDateStr;
    }

    public String getCfcljdsEndDateStr() {
        return cfcljdsEndDateStr;
    }

    public void setCfcljdsEndDateStr(String cfcljdsEndDateStr) {
        this.cfcljdsEndDateStr = cfcljdsEndDateStr;
    }

    public String getCfcljdsAppraisalDelayDateStr() {
        return cfcljdsAppraisalDelayDateStr;
    }

    public void setCfcljdsAppraisalDelayDateStr(String cfcljdsAppraisalDelayDateStr) {
        this.cfcljdsAppraisalDelayDateStr = cfcljdsAppraisalDelayDateStr;
    }

    public String getCfcljdsArriveDateStr() {
        return cfcljdsArriveDateStr;
    }

    public void setCfcljdsArriveDateStr(String cfcljdsArriveDateStr) {
        this.cfcljdsArriveDateStr = cfcljdsArriveDateStr;
    }

    public String getCfcljdsReceiveDateStr() {
        return cfcljdsReceiveDateStr;
    }

    public void setCfcljdsReceiveDateStr(String cfcljdsReceiveDateStr) {
        this.cfcljdsReceiveDateStr = cfcljdsReceiveDateStr;
    }

    public String getCfcljdsSealDateStr() {
        return cfcljdsSealDateStr;
    }

    public void setCfcljdsSealDateStr(String cfcljdsSealDateStr) {
        this.cfcljdsSealDateStr = cfcljdsSealDateStr;
    }

    public String getKycljdsDelayDateStr() {
        return kycljdsDelayDateStr;
    }

    public void setKycljdsDelayDateStr(String kycljdsDelayDateStr) {
        this.kycljdsDelayDateStr = kycljdsDelayDateStr;
    }

    public String getKycljdsAppraisalDateStr() {
        return kycljdsAppraisalDateStr;
    }

    public void setKycljdsAppraisalDateStr(String kycljdsAppraisalDateStr) {
        this.kycljdsAppraisalDateStr = kycljdsAppraisalDateStr;
    }

    public String getKycljdsEndDateStr() {
        return kycljdsEndDateStr;
    }

    public void setKycljdsEndDateStr(String kycljdsEndDateStr) {
        this.kycljdsEndDateStr = kycljdsEndDateStr;
    }

    public String getKycljdsAppraisalDelayDateStr() {
        return kycljdsAppraisalDelayDateStr;
    }

    public void setKycljdsAppraisalDelayDateStr(String kycljdsAppraisalDelayDateStr) {
        this.kycljdsAppraisalDelayDateStr = kycljdsAppraisalDelayDateStr;
    }

    public String getKycljdsArriveDateStr() {
        return kycljdsArriveDateStr;
    }

    public void setKycljdsArriveDateStr(String kycljdsArriveDateStr) {
        this.kycljdsArriveDateStr = kycljdsArriveDateStr;
    }

    public String getKycljdsReceiveDateStr() {
        return kycljdsReceiveDateStr;
    }

    public void setKycljdsReceiveDateStr(String kycljdsReceiveDateStr) {
        this.kycljdsReceiveDateStr = kycljdsReceiveDateStr;
    }

    public String getKycljdsSealDateStr() {
        return kycljdsSealDateStr;
    }

    public void setKycljdsSealDateStr(String kycljdsSealDateStr) {
        this.kycljdsSealDateStr = kycljdsSealDateStr;
    }

    public String getDcxwblStartDateStr() {
        return dcxwblStartDateStr;
    }

    public void setDcxwblStartDateStr(String dcxwblStartDateStr) {
        this.dcxwblStartDateStr = dcxwblStartDateStr;
    }

    public String getDcxwblEndDateStr() {
        return dcxwblEndDateStr;
    }

    public void setDcxwblEndDateStr(String dcxwblEndDateStr) {
        this.dcxwblEndDateStr = dcxwblEndDateStr;
    }

    public String getZjclysqdDcqzArriveDateStr() {
        return zjclysqdDcqzArriveDateStr;
    }

    public void setZjclysqdDcqzArriveDateStr(String zjclysqdDcqzArriveDateStr) {
        this.zjclysqdDcqzArriveDateStr = zjclysqdDcqzArriveDateStr;
    }

    public String getZjclysqdDcqzReceiveDateStr() {
        return zjclysqdDcqzReceiveDateStr;
    }

    public void setZjclysqdDcqzReceiveDateStr(String zjclysqdDcqzReceiveDateStr) {
        this.zjclysqdDcqzReceiveDateStr = zjclysqdDcqzReceiveDateStr;
    }

    public String getSdhzDcqzArriveDateStr() {
        return sdhzDcqzArriveDateStr;
    }

    public void setSdhzDcqzArriveDateStr(String sdhzDcqzArriveDateStr) {
        this.sdhzDcqzArriveDateStr = sdhzDcqzArriveDateStr;
    }

    public String getZjxxdjbctzsSatrtDateStr() {
        return zjxxdjbctzsSatrtDateStr;
    }

    public void setZjxxdjbctzsSatrtDateStr(String zjxxdjbctzsSatrtDateStr) {
        this.zjxxdjbctzsSatrtDateStr = zjxxdjbctzsSatrtDateStr;
    }

    public String getZjxxdjbctzsEndDateStr() {
        return zjxxdjbctzsEndDateStr;
    }

    public void setZjxxdjbctzsEndDateStr(String zjxxdjbctzsEndDateStr) {
        this.zjxxdjbctzsEndDateStr = zjxxdjbctzsEndDateStr;
    }

    public String getZjxxdjbctzsArriveDateStr() {
        return zjxxdjbctzsArriveDateStr;
    }

    public void setZjxxdjbctzsArriveDateStr(String zjxxdjbctzsArriveDateStr) {
        this.zjxxdjbctzsArriveDateStr = zjxxdjbctzsArriveDateStr;
    }

    public String getZjxxdjbctzsReceiveDateStr() {
        return zjxxdjbctzsReceiveDateStr;
    }

    public void setZjxxdjbctzsReceiveDateStr(String zjxxdjbctzsReceiveDateStr) {
        this.zjxxdjbctzsReceiveDateStr = zjxxdjbctzsReceiveDateStr;
    }

    public String getZjxxdjbctzsSealDateStr() {
        return zjxxdjbctzsSealDateStr;
    }

    public void setZjxxdjbctzsSealDateStr(String zjxxdjbctzsSealDateStr) {
        this.zjxxdjbctzsSealDateStr = zjxxdjbctzsSealDateStr;
    }

    public String getZjxxdjbccljdsSatrtDateStr() {
        return zjxxdjbccljdsSatrtDateStr;
    }

    public void setZjxxdjbccljdsSatrtDateStr(String zjxxdjbccljdsSatrtDateStr) {
        this.zjxxdjbccljdsSatrtDateStr = zjxxdjbccljdsSatrtDateStr;
    }

    public String getZjxxdjbccljdsEndDateStr() {
        return zjxxdjbccljdsEndDateStr;
    }

    public void setZjxxdjbccljdsEndDateStr(String zjxxdjbccljdsEndDateStr) {
        this.zjxxdjbccljdsEndDateStr = zjxxdjbccljdsEndDateStr;
    }

    public String getZjxxdjbccljdsSealDateStr() {
        return zjxxdjbccljdsSealDateStr;
    }

    public void setZjxxdjbccljdsSealDateStr(String zjxxdjbccljdsSealDateStr) {
        this.zjxxdjbccljdsSealDateStr = zjxxdjbccljdsSealDateStr;
    }

    public String getZjxxdjbccljdsArriveDateStr() {
        return zjxxdjbccljdsArriveDateStr;
    }

    public void setZjxxdjbccljdsArriveDateStr(String zjxxdjbccljdsArriveDateStr) {
        this.zjxxdjbccljdsArriveDateStr = zjxxdjbccljdsArriveDateStr;
    }

    public String getZjxxdjbccljdsReceiveDateStr() {
        return zjxxdjbccljdsReceiveDateStr;
    }

    public void setZjxxdjbccljdsReceiveDateStr(String zjxxdjbccljdsReceiveDateStr) {
        this.zjxxdjbccljdsReceiveDateStr = zjxxdjbccljdsReceiveDateStr;
    }

    public String getCyqzwqcltzsSatrtDateStr() {
        return cyqzwqcltzsSatrtDateStr;
    }

    public void setCyqzwqcltzsSatrtDateStr(String cyqzwqcltzsSatrtDateStr) {
        this.cyqzwqcltzsSatrtDateStr = cyqzwqcltzsSatrtDateStr;
    }

    public String getCyqzwqcltzsEndDateStr() {
        return cyqzwqcltzsEndDateStr;
    }

    public void setCyqzwqcltzsEndDateStr(String cyqzwqcltzsEndDateStr) {
        this.cyqzwqcltzsEndDateStr = cyqzwqcltzsEndDateStr;
    }

    public String getCyqzwqcltzsSealDateStr() {
        return cyqzwqcltzsSealDateStr;
    }

    public void setCyqzwqcltzsSealDateStr(String cyqzwqcltzsSealDateStr) {
        this.cyqzwqcltzsSealDateStr = cyqzwqcltzsSealDateStr;
    }

    public String getCyqzwqcltzsArriveDateStr() {
        return cyqzwqcltzsArriveDateStr;
    }

    public void setCyqzwqcltzsArriveDateStr(String cyqzwqcltzsArriveDateStr) {
        this.cyqzwqcltzsArriveDateStr = cyqzwqcltzsArriveDateStr;
    }

    public String getCyqzwqcltzsReceiveDateStr() {
        return cyqzwqcltzsReceiveDateStr;
    }

    public void setCyqzwqcltzsReceiveDateStr(String cyqzwqcltzsReceiveDateStr) {
        this.cyqzwqcltzsReceiveDateStr = cyqzwqcltzsReceiveDateStr;
    }

    public String getAjysdDcqzSendDateStr() {
        return ajysdDcqzSendDateStr;
    }

    public void setAjysdDcqzSendDateStr(String ajysdDcqzSendDateStr) {
        this.ajysdDcqzSendDateStr = ajysdDcqzSendDateStr;
    }

    public String getZlgztzsModifyDateStr() {
        return zlgztzsModifyDateStr;
    }

    public void setZlgztzsModifyDateStr(String zlgztzsModifyDateStr) {
        this.zlgztzsModifyDateStr = zlgztzsModifyDateStr;
    }

    public String getZlgztzsSealDateStr() {
        return zlgztzsSealDateStr;
    }

    public void setZlgztzsSealDateStr(String zlgztzsSealDateStr) {
        this.zlgztzsSealDateStr = zlgztzsSealDateStr;
    }

    public String getZlgztzsArriveDateStr() {
        return zlgztzsArriveDateStr;
    }

    public void setZlgztzsArriveDateStr(String zlgztzsArriveDateStr) {
        this.zlgztzsArriveDateStr = zlgztzsArriveDateStr;
    }

    public String getZlgztzsReceiveDateStr() {
        return zlgztzsReceiveDateStr;
    }

    public void setZlgztzsReceiveDateStr(String zlgztzsReceiveDateStr) {
        this.zlgztzsReceiveDateStr = zlgztzsReceiveDateStr;
    }

    public String getXzcfsxgzsSealDateStr() {
        return xzcfsxgzsSealDateStr;
    }

    public void setXzcfsxgzsSealDateStr(String xzcfsxgzsSealDateStr) {
        this.xzcfsxgzsSealDateStr = xzcfsxgzsSealDateStr;
    }

    public String getXzcfsxgzsSignDateStr() {
        return xzcfsxgzsSignDateStr;
    }

    public void setXzcfsxgzsSignDateStr(String xzcfsxgzsSignDateStr) {
        this.xzcfsxgzsSignDateStr = xzcfsxgzsSignDateStr;
    }

    public String getXzcflxcgsSealDateStr() {
        return xzcflxcgsSealDateStr;
    }

    public void setXzcflxcgsSealDateStr(String xzcflxcgsSealDateStr) {
        this.xzcflxcgsSealDateStr = xzcflxcgsSealDateStr;
    }

    public String getXzcflxcgsArriveDateStr() {
        return xzcflxcgsArriveDateStr;
    }

    public void setXzcflxcgsArriveDateStr(String xzcflxcgsArriveDateStr) {
        this.xzcflxcgsArriveDateStr = xzcflxcgsArriveDateStr;
    }

    public String getXzcflxcgsReceiveDateStr() {
        return xzcflxcgsReceiveDateStr;
    }

    public void setXzcflxcgsReceiveDateStr(String xzcflxcgsReceiveDateStr) {
        this.xzcflxcgsReceiveDateStr = xzcflxcgsReceiveDateStr;
    }

    public String getZjclysqdZggzArriveDateStr() {
        return zjclysqdZggzArriveDateStr;
    }

    public void setZjclysqdZggzArriveDateStr(String zjclysqdZggzArriveDateStr) {
        this.zjclysqdZggzArriveDateStr = zjclysqdZggzArriveDateStr;
    }

    public String getZjclysqdZggzReceiveDateStr() {
        return zjclysqdZggzReceiveDateStr;
    }

    public void setZjclysqdZggzReceiveDateStr(String zjclysqdZggzReceiveDateStr) {
        this.zjclysqdZggzReceiveDateStr = zjclysqdZggzReceiveDateStr;
    }

    public String getSdhzZggzArriveDateStr() {
        return sdhzZggzArriveDateStr;
    }

    public void setSdhzZggzArriveDateStr(String sdhzZggzArriveDateStr) {
        this.sdhzZggzArriveDateStr = sdhzZggzArriveDateStr;
    }

    public String getAjysdZggzSendDateStr() {
        return ajysdZggzSendDateStr;
    }

    public void setAjysdZggzSendDateStr(String ajysdZggzSendDateStr) {
        this.ajysdZggzSendDateStr = ajysdZggzSendDateStr;
    }

    public String getAjclcpbSealDateStr() {
        return ajclcpbSealDateStr;
    }

    public void setAjclcpbSealDateStr(String ajclcpbSealDateStr) {
        this.ajclcpbSealDateStr = ajclcpbSealDateStr;
    }

    public String getSdhzAjcpArriveDateStr() {
        return sdhzAjcpArriveDateStr;
    }

    public void setSdhzAjcpArriveDateStr(String sdhzAjcpArriveDateStr) {
        this.sdhzAjcpArriveDateStr = sdhzAjcpArriveDateStr;
    }

    public String getAjjttlAtartDateStr() {
        return ajjttlAtartDateStr;
    }

    public void setAjjttlAtartDateStr(String ajjttlAtartDateStr) {
        this.ajjttlAtartDateStr = ajjttlAtartDateStr;
    }

    public String getAjjttlEndDateStr() {
        return ajjttlEndDateStr;
    }

    public void setAjjttlEndDateStr(String ajjttlEndDateStr) {
        this.ajjttlEndDateStr = ajjttlEndDateStr;
    }

    public String getRkckdArriveDateStr() {
        return rkckdArriveDateStr;
    }

    public void setRkckdArriveDateStr(String rkckdArriveDateStr) {
        this.rkckdArriveDateStr = rkckdArriveDateStr;
    }

    public String getRkckdReceiveDateStr() {
        return rkckdReceiveDateStr;
    }

    public void setRkckdReceiveDateStr(String rkckdReceiveDateStr) {
        this.rkckdReceiveDateStr = rkckdReceiveDateStr;
    }

    public String getJabgSealDateStr() {
        return jabgSealDateStr;
    }

    public void setJabgSealDateStr(String jabgSealDateStr) {
        this.jabgSealDateStr = jabgSealDateStr;
    }

    public String getYqjnfkpzsDelayDateStr() {
        return yqjnfkpzsDelayDateStr;
    }

    public void setYqjnfkpzsDelayDateStr(String yqjnfkpzsDelayDateStr) {
        this.yqjnfkpzsDelayDateStr = yqjnfkpzsDelayDateStr;
    }

    public String getYqjnfkpzsSealDateStr() {
        return yqjnfkpzsSealDateStr;
    }

    public void setYqjnfkpzsSealDateStr(String yqjnfkpzsSealDateStr) {
        this.yqjnfkpzsSealDateStr = yqjnfkpzsSealDateStr;
    }

    public String getYqjnfkpzsArriveDateStr() {
        return yqjnfkpzsArriveDateStr;
    }

    public void setYqjnfkpzsArriveDateStr(String yqjnfkpzsArriveDateStr) {
        this.yqjnfkpzsArriveDateStr = yqjnfkpzsArriveDateStr;
    }

    public String getYqjnfkpzsRecelveDateStr() {
        return yqjnfkpzsRecelveDateStr;
    }

    public void setYqjnfkpzsRecelveDateStr(String yqjnfkpzsRecelveDateStr) {
        this.yqjnfkpzsRecelveDateStr = yqjnfkpzsRecelveDateStr;
    }

    public String getFqjnfkpzsOneDateStr() {
        return fqjnfkpzsOneDateStr;
    }

    public void setFqjnfkpzsOneDateStr(String fqjnfkpzsOneDateStr) {
        this.fqjnfkpzsOneDateStr = fqjnfkpzsOneDateStr;
    }

    public String getFqjnfkpzsTwoDateStr() {
        return fqjnfkpzsTwoDateStr;
    }

    public void setFqjnfkpzsTwoDateStr(String fqjnfkpzsTwoDateStr) {
        this.fqjnfkpzsTwoDateStr = fqjnfkpzsTwoDateStr;
    }

    public String getFqjnfkpzsThreeDateStr() {
        return fqjnfkpzsThreeDateStr;
    }

    public void setFqjnfkpzsThreeDateStr(String fqjnfkpzsThreeDateStr) {
        this.fqjnfkpzsThreeDateStr = fqjnfkpzsThreeDateStr;
    }

    public String getFqjnfkpzsSealDateStr() {
        return fqjnfkpzsSealDateStr;
    }

    public void setFqjnfkpzsSealDateStr(String fqjnfkpzsSealDateStr) {
        this.fqjnfkpzsSealDateStr = fqjnfkpzsSealDateStr;
    }

    public String getFqjnfkpzsArriveDateStr() {
        return fqjnfkpzsArriveDateStr;
    }

    public void setFqjnfkpzsArriveDateStr(String fqjnfkpzsArriveDateStr) {
        this.fqjnfkpzsArriveDateStr = fqjnfkpzsArriveDateStr;
    }

    public String getFqjnfkpzsRecelveDateStr() {
        return fqjnfkpzsRecelveDateStr;
    }

    public void setFqjnfkpzsRecelveDateStr(String fqjnfkpzsRecelveDateStr) {
        this.fqjnfkpzsRecelveDateStr = fqjnfkpzsRecelveDateStr;
    }

    public String getQzzxsqsSealDateStr() {
        return qzzxsqsSealDateStr;
    }

    public void setQzzxsqsSealDateStr(String qzzxsqsSealDateStr) {
        this.qzzxsqsSealDateStr = qzzxsqsSealDateStr;
    }

    public String getXzcfjcfkjdsSealDateStr() {
        return xzcfjcfkjdsSealDateStr;
    }

    public void setXzcfjcfkjdsSealDateStr(String xzcfjcfkjdsSealDateStr) {
        this.xzcfjcfkjdsSealDateStr = xzcfjcfkjdsSealDateStr;
    }

    public String getXzcfjcfkjdsArriveDateStr() {
        return xzcfjcfkjdsArriveDateStr;
    }

    public void setXzcfjcfkjdsArriveDateStr(String xzcfjcfkjdsArriveDateStr) {
        this.xzcfjcfkjdsArriveDateStr = xzcfjcfkjdsArriveDateStr;
    }

    public String getXzcfjcfkjdsRecelveDateStr() {
        return xzcfjcfkjdsRecelveDateStr;
    }

    public void setXzcfjcfkjdsRecelveDateStr(String xzcfjcfkjdsRecelveDateStr) {
        this.xzcfjcfkjdsRecelveDateStr = xzcfjcfkjdsRecelveDateStr;
    }

    public String getXzcftztzsStartDateStr() {
        return xzcftztzsStartDateStr;
    }

    public void setXzcftztzsStartDateStr(String xzcftztzsStartDateStr) {
        this.xzcftztzsStartDateStr = xzcftztzsStartDateStr;
    }

    public String getXzcftztzsSealDateStr() {
        return xzcftztzsSealDateStr;
    }

    public void setXzcftztzsSealDateStr(String xzcftztzsSealDateStr) {
        this.xzcftztzsSealDateStr = xzcftztzsSealDateStr;
    }

    public String getXzcftztzsArriveDateStr() {
        return xzcftztzsArriveDateStr;
    }

    public void setXzcftztzsArriveDateStr(String xzcftztzsArriveDateStr) {
        this.xzcftztzsArriveDateStr = xzcftztzsArriveDateStr;
    }

    public String getXzcftztzsRecelveDateStr() {
        return xzcftztzsRecelveDateStr;
    }

    public void setXzcftztzsRecelveDateStr(String xzcftztzsRecelveDateStr) {
        this.xzcftztzsRecelveDateStr = xzcftztzsRecelveDateStr;
    }

    public String getTzggSealDateStr() {
        return tzggSealDateStr;
    }

    public void setTzggSealDateStr(String tzggSealDateStr) {
        this.tzggSealDateStr = tzggSealDateStr;
    }

    public String getXzcftzblStartDateStr() {
        return xzcftzblStartDateStr;
    }

    public void setXzcftzblStartDateStr(String xzcftzblStartDateStr) {
        this.xzcftzblStartDateStr = xzcftzblStartDateStr;
    }

    public String getXzcftzblEndDateStr() {
        return xzcftzblEndDateStr;
    }

    public void setXzcftzblEndDateStr(String xzcftzblEndDateStr) {
        this.xzcftzblEndDateStr = xzcftzblEndDateStr;
    }

    public String getXzcftzbgSealDateStr() {
        return xzcftzbgSealDateStr;
    }

    public void setXzcftzbgSealDateStr(String xzcftzbgSealDateStr) {
        this.xzcftzbgSealDateStr = xzcftzbgSealDateStr;
    }

}
