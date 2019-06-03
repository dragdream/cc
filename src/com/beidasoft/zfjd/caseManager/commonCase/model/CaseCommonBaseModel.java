/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:23:11 
 */
package com.beidasoft.zfjd.caseManager.commonCase.model;

import javax.persistence.Column;

/**
 * 2018
 * 
 * @ClassName: CaseCommonBasicModel.java
 * @Description: 一般案件信息表MODEL类
 *
 * @author: songff
 * @date: 2018年12月26日 下午2:23:11
 *
 */
public class CaseCommonBaseModel {
    // 主键
    private String id;

    // 案件编号
    private String caseCode;

    // 执法主体ID
    private String subjectId;

    // 执法主体ID
    private String subjectIds;

    // 执法部门ID
    private String departmentId;

    // 主体所属系统
    private String orgSys;

    // 登记人
    private String registrant;

    // 执法日期
    private String enforcementDateStr;

    // 登记日期
    private String registrantDateStr;

    // 案件名称
    private String name;

    // 当事人类型(code:1公民,2法人,9其他组织)
    private String partyType;

    // 公民姓名
    private String citizenName;

    // 公民性别
    private Integer citizenSex;

    // 公民有效身份证件类型（code: 01身份证，02驾驶证，03军官证，04护照，99其他）
    private String citizenCardType;

    // 公民有效身份证件号码
    private String citizenCardCode;

    // 公民单位
    private String citizenCompany;

    // 公民住址
    private String citizenAddress;

    // 单位名称
    private String companyName;

    // 法定代表人或负责人
    private String principal;

    // 组织机构代码
    private String organizationCode;

    // 地址
    private String address;

    // 行政处罚决定书文号
    private String punishmentCode;

    // 行政处罚决定书日期
    private String punishmentDateStr;

    private String punishmentStartDateStr;

    private String punishmentEndDateStr;

    // 处罚决定书文件名称
    private String documentName;

    // 处罚决定书文件路径
    private String documentPath;

    // 行政处罚决定书书送达日期
    private String pdSentDateStr;

    // 行政处罚缴款书送达日期
    private String ppSentDateStr;

    // 行政处罚决定书送达方式(code: 01直接送达，02留置送达，03委托送达，04邮递送达，05公告送达)
    @Column(name = "SENT_WAY")
    private String sentWay;

    // 行政处罚缴款书送达方式(code: 01直接送达，02留置送达，03委托送达，04邮递送达，05公告送达)
    @Column(name = "PP_SENT_WAY")
    private String ppSentWay;

    // 是否警告(0不警告,1警告)
    private Integer isWarn;

    // 是否罚款(0不罚款,1罚款)
    private Integer isFine;

    // 罚款金额
    private Double fineSum;

    // 是否吊销许可证或营业执照
    private Integer isRevokeLicense;

    // 是否责令停产停业
    private Integer isOrderClosure;

    // 是否行政拘留
    private Integer isDtain;

    // 拘留天数
    private Integer dtainDays;

    // 是否暂扣许可证或营业执照(0不暂扣,1暂扣)
    private Integer isTdLicense;

    // 是否没收违法所得(0不没收,1没收)
    private Integer isConfiscate;

    // 没收所得金额
    private Integer confiscateMoney;

    // 是否没收非法财物(0不没收,1没收)
    private Integer isConfisProperty;

    // 没收非法财物明细内容
    private String confiscateDetail;

    // 没收非法财物金额
    private Integer confiscateProMon;

    // 是否实施行政强制措施(0不实施,1实施)
    private Integer isForce;

    // 是否限制公民人身自由(0不限制,1限制)
    private Integer isLimitFree;

    // 是否查封场所、设施或者财物(0不查封,1查封)
    private Integer isDistress;

    // 是否扣押财物(0不扣押,1扣押)
    private Integer isDtainProperty;

    // 是否冻结存款、汇款(0不冻结,1冻结)
    private Integer isFress;

    // 是否其他行政强制措施0不其他1其他
    private Integer isOrtherForce;

    // 是否听证(0不听证,1听证)
    private Integer isHearing;

    // 听证通知日期
    private String hearingInformDateStr;

    // 听证公告日期
    private String hearingNoticeDateStr;

    // 听证举行日期
    private String hearingHoldDateStr;

    // 听证主持人
    private String hearingHost;

    // 听证参加人
    private String hearingParticipants;

    // 是否集体讨论
    private Integer isCollectiveDiscussion;

    // 集体讨论日期
    private String collectiveDiscussionDateStr;

    // 集体讨论结果
    private String collectiveDiscussionResult;

    // 集体讨论主持人姓名
    private String collectiveDiscussHostName;

    // 集体讨论主持人职务
    private String collectiveDiscussHostPost;

    // 集体讨论参加人
    private String collectiveDiscussAttender;

    // 案件是否移送（0未移送，1已移送）
    private Integer isTransfer;

    // 移送机关
    private String transferOrgan;

    // 移送机关是否立案
    private Integer transferOrganIsFiling;

    // 移送理由
    private String transferReason;

    // 责令改正通知书日期
    private String ordercorrectNoticDateStr;

    // 责令改正当事人签收日期
    private String ordercorrectHostReceiptDateStr;

    // 责令改正限期改正日期
    private String ordercorrectLimiCorrectDateStr;

    // 责令改正内容
    private String ordercorrectContent;

    // 是否行政处罚决定执行
    private Integer isPunishDecisionExecut;

    // 行政处罚决定执行日期
    private String punishDecisionExecutDateStr;

    // 是否当事人执行
    private Integer isPartyExecution;

    // 当事人执行情况
    private String partyExecutionSituation;

    // 当事人执行罚没款金额
    private Integer partyExecutionFines;

    // 是否人民法院行政强制执行
    private Integer isPeoplecourtEnforcement;

    // 人民法院行政强制执行情况
    private String peoplecourtEnforceStatus;

    // 人民法院行政强制执行罚没款金额
    private Integer peoplecourtEnforceFines;

    // 是否人民法院行政实际执行
    private Integer isPeoplecourtActualExec;

    // 人民法院行政实际执行情况
    private String peoplecourtActualExecStatus;

    // 人民法院行政实际执行罚没款金额
    private Integer peoplecourtActualExecFines;

    // 是否行政复查
    private Integer isAdministrativeReveiew;

    // 是否加处罚款
    private Integer isPlusFine;

    // 加处罚款金额
    private Integer plusFineAmount;

    // 是否行政复议(0不复议,1复议)
    private Integer isReconsideration;

    // 复议日期
    private String reconsiderationDateStr;

    // 复议结果
    private String reconsiderationResult;

    // 是否行政诉讼(0不诉讼,1诉讼)
    private Integer isLawsuit;

    // 诉讼日期
    private String lawsuitDateStr;

    // 诉讼结果
    private String lawsuitResult;

    // 是否结案
    private Integer isEndCase;

    // 结案日期
    private String closedDateStr;

    private String closedStartDateStr;

    private String closedEndDateStr;

    // 对应的结案状态（code: 01正常结案.02撤销立案,03不予处罚,04撤销原处罚决定,98终结）
    // 当前状态（code: 01已立案,02已调查取证,03已做处罚决定,04处罚执行完毕,05结案,06撤销立案）
    private String currentState;

    // 创建时间
    private String createDateStr;

    // 入库开始日期
    private String createStartDateStr;

    // 入库结束日期
    private String createEndDateStr;

    // 数据来源（0系统录入，1接口对接）
    private Integer dataSource;

    // 数据删除标识（0未删除，1已删除）
    private Integer isDelete;

    // 案卷评审状态
    private String fileReviewStatus;

    // 案件抽查者
    private String caseCheck;

    // 案件抽查时间
    private String caseCheckTimeStr;

    // 案件撤销抽查者
    private String caseRevokeCheck;

    // 案件撤销抽查时间
    private String caseRevokeCheckTimeStr;

    // 区县政府法制办审核者
    private String districtGovOfficeAuditor;

    // 区县政府法制办审核时间
    private String districtGovOfficeAuditTimeStr;

    // 区县政府法制办审核状态
    private Integer districtGovOfficeAuditSta;

    // 是否（其他）
    private Integer isOther;

    // 其他明细内容
    private String otherDetailContent;

    // 是否责令改正
    private Integer isOrderCorrect;

    // 立案号（以下为二期追加字段）
    private String filingNumber;

    // 批准人
    private String approvedPerson;

    // 批准日期
    private String approvedDateStr;

    // 批准意见
    private String approvedSuggest;

    // 是否法制审查
    private Integer isFilingLegalReview;

    // 法制审查日期
    private String filingLegalReviewDateStr;

    // 联系电话（公民）
    private String contactPhoneCitizen;

    // 联系电话（法人）
    private String contactPhoneLegalperson;

    // 告知书送达日期
    private String informingbookDeliveryDateStr;

    // 告知书名称
    private String informingbookName;

    // 告知书路径
    private String informingbookPath;

    // 调查终结日期
    private String surveyEndDateStr;

    private String surveyStartEndDateStr;

    private String surveyEndEndDateStr;

    // 责令改正(处罚决定)
    private Integer isOrdercorrectDecision;

    // 是否有减轻（1从轻，2从重情节，3处罚决定， 0无）
    private Integer isPlot;

    // 责令改正(处罚执行)
    private Integer isOrdercorrectExection;

    // 分期执行(处罚执行)
    private Integer stagedExection;

    // 延后执行(处罚执行)
    private Integer delayedExection;

    // 案件来源（code:1现场检查,2投诉,3举报,4上级机关交办,5其他机关移送,6媒体曝光9其他）
    private String caseSource;

    // 检查单号（案件来源为现场检查）
    private String commonCaseCode;

    // 其他(案件来源)
    private String caseSourceOther;

    // 暂扣许可证天数
    private Integer detainPermitDays;

    // 立案日期
    private String filingDateStr;

    private String filingStartDateStr;

    private String filingEndDateStr;

    // 听证日期
    private String hearingDateStr;

    // 统一社会信用代码
    private String uniformCreditCode;

    // 发生地
    private String happenPlace;

    // 当事人主动履行日期
    private String partyActivePerforDateStr;

    // 是否行政机关强制执行
    private Integer isOrganEnforce;

    // 行政机关强制执行日期
    private String organEnforcementDateStr;

    // 申请人民法院强制执行日期
    private String applyCourtEnforceDateStr;

    // 人民法院实际执行日期
    private String courtActualExeDateStr;

    // 违法事实
    private String illegalFacts;

    // 违法证据
    private String illegalEvidence;

    // 机构编号类型
    private String organizationCodeType;

    // 证据类型(CODE:01书证, 02物证, 03试听资料 , 04电子数据, 05证人证言, 06当事人陈述, 07鉴定意见,
    // 08勘验笔录、现场笔录)
    private String illegalEvidenceType;

    // 证据描述
    private String illegalDescript;

    // 改正类型(调查取证)
    private Integer correctType;

    // 改正类型(处罚决定)
    private Integer correctTypeDecision;

    // 改正类型(处罚执行)
    private Integer correctTypeExection;

    // 改正开始日期(调查取证)
    private String correctStartdateStr;

    // 改正结束日期(调查取证)
    private String correctEnddateStr;

    // 改正开始日期(处罚决定)
    private String correctStartdateDecisionStr;

    // 改正结束日期(处罚决定)
    private String correctEnddateDecisionStr;

    // 改正开始日期(处罚执行)
    private String correctStartdateExectionStr;

    // 改正结束日期(处罚执行)
    private String correctEnddateExectionStr;

    // 改正复查日期
    private String correctDateStr;

    // 开始日期（暂扣许可证）
    private String startdateWithholdStr;

    // 结束日期（暂扣许可证）
    private String enddateWithholdStr;

    // 开始日期（行政拘留）
    private String startdateDetainStr;

    // 结束日期（行政拘留）
    private String enddateDetainStr;

    // 延期至（延期）
    private String postponedToDelayStr;

    // 申请日期（延期）
    private String applyDateDelayStr;

    // 批准日期（延期）
    private String approvalDateDelayStr;

    // 最后期限（分期）
    private String deadlineStageStr;

    // 申请日期（分期）
    private String applyDateStageStr;

    // 批准日期（分期）
    private String approvalDateStageStr;

    // 结案说明
    private String closedCaseInfo;
    // 对应的当前状态（code: 01已立案,02已调查取证,03已做处罚决定,04处罚执行完毕,05结案,06撤销立案）
    // 结案状态（code: 01正常结案.02撤销立案,03不予处罚,04撤销原处罚决定,98终结）
    private String closedState;

    // 法制审核结果
    private String legalExaminaResult;

    // 法制审查文书
    private String legalReviewFile;

    // 其机构负责人
    private String otherOrganName;

    // 其他机构联系方式
    private String otherOrganPhoneNum;

    // 其他机构名称
    private String otherOrganCompanyName;

    // 其他机构 组织机构代码
    private String otherOrganCode;

    // 其他机构地址
    private String otherOrganAddress;

    // 是否异常终止？？
    private Integer stagesExection;

    // 是否提交
    private Integer isSubmit;

    // 是否个体户
    private Integer isSelfemployed;

    // 字号
    private String selfemployedCode;

    // 个体工商户证件号
    private String selfemployedCharteredCode;

    // 个体工商户证件类型
    private String selfemployedCharteredType;

    // 营业地址
    private String selfemployedAddress;

    // 公民年龄
    private Integer citizenAge;

    // 是否作出处罚
    private Integer isPunishment;

    // 提交日期
    private String submitDateStr;

    // 删除日期
    private String deleteDateStr;

    // 更新日期
    private String updateDateStr;

    // 案件来源信息json字符串
    private String caseSourceJsonStr;

    // 行政处罚事先告知书制发日期(案件调查)
    private String punishHearingMakingDateStr;

    // 行政处罚事先告知书送达日期(案件调查)
    private String punishHearingSendDateStr;

    // 行政处罚事先告知书送达方式(案件调查)(code: 01直接送达，02留置送达，03委托送达，04邮递送达，05公告送达)
    private String punishHearingSendWay;

    // 当事人是否申请听证(0不申请，1申请)(案件调查)
    private Integer isPartyApplyHearing;

    // 是否受理听证（0不受理，1受理）(案件调查)
    private Integer isDealHearing;

    // 证据是否先行登记保存（0没保存，1保存）(案件调查)
    private Integer isSavePriorEvidence;

    // 证据先行登记保存登记日期(案件调查)
    private String savePriorEvidenceDateStr;

    // 证据先行登记保存处理日期(案件调查)
    private String dealPriorEvidenceDateStr;

    // 是否延期（0无延期，1延期）(案件调查)
    private Integer isDelaySearch;

    // 延期批准人(案件调查)
    private String approvePersonSearch;

    // 同意延期天数(案件调查)
    private Integer delayDaySearch;

    // 延期起始时间(案件调查)
    private String delayStartDateSearchStr;

    // 延期结束时间(案件调查)
    private String delayEndDateSearchStr;

    // 案件处理呈批的承办人意见(审查决定)
    private String caseContractorSuggest;

    // 案件处理呈批日期(审查决定)
    private String caseHandleDateStr;

    // 法制审核机构意见(审查决定)
    private String legalReviewOrgSuggest;

    // 法制审核人员姓名(审查决定)
    private String legalReviewPerson;

    // 有无法律职业资格证(审查决定)
    private Integer isLegalQualifications;

    // 法制审核人员意见(审查决定)
    private String legalReviewPersonSuggest;

    // 法制审核日期(审查决定)
    private String legalReviewDateStr;

    // 是否属于重大案件(审查决定)
    private Integer isMajorCase;

    // 整改结果
    private String correctResult;

    // 结案报告呈批人
    private String closedReportApprovePerson;

    // 结案批准人
    private String closedApprovePerson;

    // 结案批准意见
    private String closedApproveSuggest;

    // 步骤环节(0立案，1调查取证，2处罚决定，3处罚执行，4结案)
    private Integer isNext;

    // 其他组织证件类型（code: 01统一社会信用代码，02组织机构代码，03营业执照注册码）
    private String otherOrganizationCodeType;

    // 操作类型（1新增，2修改，3查看）
    private Integer editFlag;

    // 操作环节(01立案，02调查取证，03处罚决定，04处罚执行，05结案)
    private String grading;

    // 执法人员json对象
    private String personJsonStr;

    // 违法行为
    private String powerJsonStr;

    // 违法依据
    private String gistJsonStr;

    // 处罚依据
    private String punishJsonStr;

    // 自由裁量基准
    private String discretionaryJsonStr;

    // 撤销立案信息json对象
    private String revokeJsonStr;

    // 不予处罚信息json对象
    private String nopunishmentJsonStr;

    // 撤销原处罚决定
    private String revokePunishJsonStr;

    // 终结
    private String endJsonStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(String subjectIds) {
        this.subjectIds = subjectIds;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getOrgSys() {
        return orgSys;
    }

    public void setOrgSys(String orgSys) {
        this.orgSys = orgSys;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public String getEnforcementDateStr() {
        return enforcementDateStr;
    }

    public void setEnforcementDateStr(String enforcementDateStr) {
        this.enforcementDateStr = enforcementDateStr;
    }

    public String getRegistrantDateStr() {
        return registrantDateStr;
    }

    public void setRegistrantDateStr(String registrantDateStr) {
        this.registrantDateStr = registrantDateStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public Integer getCitizenSex() {
        return citizenSex;
    }

    public void setCitizenSex(Integer citizenSex) {
        this.citizenSex = citizenSex;
    }

    public String getCitizenCardType() {
        return citizenCardType;
    }

    public void setCitizenCardType(String citizenCardType) {
        this.citizenCardType = citizenCardType;
    }

    public String getCitizenCardCode() {
        return citizenCardCode;
    }

    public void setCitizenCardCode(String citizenCardCode) {
        this.citizenCardCode = citizenCardCode;
    }

    public String getCitizenCompany() {
        return citizenCompany;
    }

    public void setCitizenCompany(String citizenCompany) {
        this.citizenCompany = citizenCompany;
    }

    public String getCitizenAddress() {
        return citizenAddress;
    }

    public void setCitizenAddress(String citizenAddress) {
        this.citizenAddress = citizenAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPunishmentCode() {
        return punishmentCode;
    }

    public void setPunishmentCode(String punishmentCode) {
        this.punishmentCode = punishmentCode;
    }

    public String getPunishmentDateStr() {
        return punishmentDateStr;
    }

    public void setPunishmentDateStr(String punishmentDateStr) {
        this.punishmentDateStr = punishmentDateStr;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getPdSentDateStr() {
        return pdSentDateStr;
    }

    public void setPdSentDateStr(String pdSentDateStr) {
        this.pdSentDateStr = pdSentDateStr;
    }

    public String getPpSentDateStr() {
        return ppSentDateStr;
    }

    public void setPpSentDateStr(String ppSentDateStr) {
        this.ppSentDateStr = ppSentDateStr;
    }

    public String getSentWay() {
        return sentWay;
    }

    public void setSentWay(String sentWay) {
        this.sentWay = sentWay;
    }

    public String getPpSentWay() {
        return ppSentWay;
    }

    public void setPpSentWay(String ppSentWay) {
        this.ppSentWay = ppSentWay;
    }

    public Integer getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(Integer isWarn) {
        this.isWarn = isWarn;
    }

    public Integer getIsFine() {
        return isFine;
    }

    public void setIsFine(Integer isFine) {
        this.isFine = isFine;
    }

    public Double getFineSum() {
        return fineSum;
    }

    public void setFineSum(Double fineSum) {
        this.fineSum = fineSum;
    }

    public Integer getIsRevokeLicense() {
        return isRevokeLicense;
    }

    public void setIsRevokeLicense(Integer isRevokeLicense) {
        this.isRevokeLicense = isRevokeLicense;
    }

    public Integer getIsOrderClosure() {
        return isOrderClosure;
    }

    public void setIsOrderClosure(Integer isOrderClosure) {
        this.isOrderClosure = isOrderClosure;
    }

    public Integer getIsDtain() {
        return isDtain;
    }

    public void setIsDtain(Integer isDtain) {
        this.isDtain = isDtain;
    }

    public Integer getDtainDays() {
        return dtainDays;
    }

    public void setDtainDays(Integer dtainDays) {
        this.dtainDays = dtainDays;
    }

    public Integer getIsTdLicense() {
        return isTdLicense;
    }

    public void setIsTdLicense(Integer isTdLicense) {
        this.isTdLicense = isTdLicense;
    }

    public Integer getIsConfiscate() {
        return isConfiscate;
    }

    public void setIsConfiscate(Integer isConfiscate) {
        this.isConfiscate = isConfiscate;
    }

    public Integer getConfiscateMoney() {
        return confiscateMoney;
    }

    public void setConfiscateMoney(Integer confiscateMoney) {
        this.confiscateMoney = confiscateMoney;
    }

    public Integer getIsConfisProperty() {
        return isConfisProperty;
    }

    public void setIsConfisProperty(Integer isConfisProperty) {
        this.isConfisProperty = isConfisProperty;
    }

    public String getConfiscateDetail() {
        return confiscateDetail;
    }

    public void setConfiscateDetail(String confiscateDetail) {
        this.confiscateDetail = confiscateDetail;
    }

    public Integer getConfiscateProMon() {
        return confiscateProMon;
    }

    public void setConfiscateProMon(Integer confiscateProMon) {
        this.confiscateProMon = confiscateProMon;
    }

    public Integer getIsForce() {
        return isForce;
    }

    public void setIsForce(Integer isForce) {
        this.isForce = isForce;
    }

    public Integer getIsLimitFree() {
        return isLimitFree;
    }

    public void setIsLimitFree(Integer isLimitFree) {
        this.isLimitFree = isLimitFree;
    }

    public Integer getIsDistress() {
        return isDistress;
    }

    public void setIsDistress(Integer isDistress) {
        this.isDistress = isDistress;
    }

    public Integer getIsDtainProperty() {
        return isDtainProperty;
    }

    public void setIsDtainProperty(Integer isDtainProperty) {
        this.isDtainProperty = isDtainProperty;
    }

    public Integer getIsFress() {
        return isFress;
    }

    public void setIsFress(Integer isFress) {
        this.isFress = isFress;
    }

    public Integer getIsOrtherForce() {
        return isOrtherForce;
    }

    public void setIsOrtherForce(Integer isOrtherForce) {
        this.isOrtherForce = isOrtherForce;
    }

    public Integer getIsHearing() {
        return isHearing;
    }

    public void setIsHearing(Integer isHearing) {
        this.isHearing = isHearing;
    }

    public String getHearingInformDateStr() {
        return hearingInformDateStr;
    }

    public void setHearingInformDateStr(String hearingInformDateStr) {
        this.hearingInformDateStr = hearingInformDateStr;
    }

    public String getHearingNoticeDateStr() {
        return hearingNoticeDateStr;
    }

    public void setHearingNoticeDateStr(String hearingNoticeDateStr) {
        this.hearingNoticeDateStr = hearingNoticeDateStr;
    }

    public String getHearingHoldDateStr() {
        return hearingHoldDateStr;
    }

    public void setHearingHoldDateStr(String hearingHoldDateStr) {
        this.hearingHoldDateStr = hearingHoldDateStr;
    }

    public String getHearingHost() {
        return hearingHost;
    }

    public void setHearingHost(String hearingHost) {
        this.hearingHost = hearingHost;
    }

    public String getHearingParticipants() {
        return hearingParticipants;
    }

    public void setHearingParticipants(String hearingParticipants) {
        this.hearingParticipants = hearingParticipants;
    }

    public Integer getIsCollectiveDiscussion() {
        return isCollectiveDiscussion;
    }

    public void setIsCollectiveDiscussion(Integer isCollectiveDiscussion) {
        this.isCollectiveDiscussion = isCollectiveDiscussion;
    }

    public String getCollectiveDiscussionDateStr() {
        return collectiveDiscussionDateStr;
    }

    public void setCollectiveDiscussionDateStr(String collectiveDiscussionDateStr) {
        this.collectiveDiscussionDateStr = collectiveDiscussionDateStr;
    }

    public String getCollectiveDiscussionResult() {
        return collectiveDiscussionResult;
    }

    public void setCollectiveDiscussionResult(String collectiveDiscussionResult) {
        this.collectiveDiscussionResult = collectiveDiscussionResult;
    }

    public String getCollectiveDiscussHostName() {
        return collectiveDiscussHostName;
    }

    public void setCollectiveDiscussHostName(String collectiveDiscussHostName) {
        this.collectiveDiscussHostName = collectiveDiscussHostName;
    }

    public String getCollectiveDiscussHostPost() {
        return collectiveDiscussHostPost;
    }

    public void setCollectiveDiscussHostPost(String collectiveDiscussHostPost) {
        this.collectiveDiscussHostPost = collectiveDiscussHostPost;
    }

    public String getCollectiveDiscussAttender() {
        return collectiveDiscussAttender;
    }

    public void setCollectiveDiscussAttender(String collectiveDiscussAttender) {
        this.collectiveDiscussAttender = collectiveDiscussAttender;
    }

    public Integer getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(Integer isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getTransferOrgan() {
        return transferOrgan;
    }

    public void setTransferOrgan(String transferOrgan) {
        this.transferOrgan = transferOrgan;
    }

    public Integer getTransferOrganIsFiling() {
        return transferOrganIsFiling;
    }

    public void setTransferOrganIsFiling(Integer transferOrganIsFiling) {
        this.transferOrganIsFiling = transferOrganIsFiling;
    }

    public String getTransferReason() {
        return transferReason;
    }

    public void setTransferReason(String transferReason) {
        this.transferReason = transferReason;
    }

    public String getOrdercorrectNoticDateStr() {
        return ordercorrectNoticDateStr;
    }

    public void setOrdercorrectNoticDateStr(String ordercorrectNoticDateStr) {
        this.ordercorrectNoticDateStr = ordercorrectNoticDateStr;
    }

    public String getOrdercorrectHostReceiptDateStr() {
        return ordercorrectHostReceiptDateStr;
    }

    public void setOrdercorrectHostReceiptDateStr(String ordercorrectHostReceiptDateStr) {
        this.ordercorrectHostReceiptDateStr = ordercorrectHostReceiptDateStr;
    }

    public String getOrdercorrectLimiCorrectDateStr() {
        return ordercorrectLimiCorrectDateStr;
    }

    public void setOrdercorrectLimiCorrectDateStr(String ordercorrectLimiCorrectDateStr) {
        this.ordercorrectLimiCorrectDateStr = ordercorrectLimiCorrectDateStr;
    }

    public String getOrdercorrectContent() {
        return ordercorrectContent;
    }

    public void setOrdercorrectContent(String ordercorrectContent) {
        this.ordercorrectContent = ordercorrectContent;
    }

    public Integer getIsPunishDecisionExecut() {
        return isPunishDecisionExecut;
    }

    public void setIsPunishDecisionExecut(Integer isPunishDecisionExecut) {
        this.isPunishDecisionExecut = isPunishDecisionExecut;
    }

    public String getPunishDecisionExecutDateStr() {
        return punishDecisionExecutDateStr;
    }

    public void setPunishDecisionExecutDateStr(String punishDecisionExecutDateStr) {
        this.punishDecisionExecutDateStr = punishDecisionExecutDateStr;
    }

    public Integer getIsPartyExecution() {
        return isPartyExecution;
    }

    public void setIsPartyExecution(Integer isPartyExecution) {
        this.isPartyExecution = isPartyExecution;
    }

    public String getPartyExecutionSituation() {
        return partyExecutionSituation;
    }

    public void setPartyExecutionSituation(String partyExecutionSituation) {
        this.partyExecutionSituation = partyExecutionSituation;
    }

    public Integer getPartyExecutionFines() {
        return partyExecutionFines;
    }

    public void setPartyExecutionFines(Integer partyExecutionFines) {
        this.partyExecutionFines = partyExecutionFines;
    }

    public Integer getIsPeoplecourtEnforcement() {
        return isPeoplecourtEnforcement;
    }

    public void setIsPeoplecourtEnforcement(Integer isPeoplecourtEnforcement) {
        this.isPeoplecourtEnforcement = isPeoplecourtEnforcement;
    }

    public String getPeoplecourtEnforceStatus() {
        return peoplecourtEnforceStatus;
    }

    public void setPeoplecourtEnforceStatus(String peoplecourtEnforceStatus) {
        this.peoplecourtEnforceStatus = peoplecourtEnforceStatus;
    }

    public Integer getPeoplecourtEnforceFines() {
        return peoplecourtEnforceFines;
    }

    public void setPeoplecourtEnforceFines(Integer peoplecourtEnforceFines) {
        this.peoplecourtEnforceFines = peoplecourtEnforceFines;
    }

    public Integer getIsPeoplecourtActualExec() {
        return isPeoplecourtActualExec;
    }

    public void setIsPeoplecourtActualExec(Integer isPeoplecourtActualExec) {
        this.isPeoplecourtActualExec = isPeoplecourtActualExec;
    }

    public String getPeoplecourtActualExecStatus() {
        return peoplecourtActualExecStatus;
    }

    public void setPeoplecourtActualExecStatus(String peoplecourtActualExecStatus) {
        this.peoplecourtActualExecStatus = peoplecourtActualExecStatus;
    }

    public Integer getPeoplecourtActualExecFines() {
        return peoplecourtActualExecFines;
    }

    public void setPeoplecourtActualExecFines(Integer peoplecourtActualExecFines) {
        this.peoplecourtActualExecFines = peoplecourtActualExecFines;
    }

    public Integer getIsAdministrativeReveiew() {
        return isAdministrativeReveiew;
    }

    public void setIsAdministrativeReveiew(Integer isAdministrativeReveiew) {
        this.isAdministrativeReveiew = isAdministrativeReveiew;
    }

    public Integer getIsPlusFine() {
        return isPlusFine;
    }

    public void setIsPlusFine(Integer isPlusFine) {
        this.isPlusFine = isPlusFine;
    }

    public Integer getPlusFineAmount() {
        return plusFineAmount;
    }

    public void setPlusFineAmount(Integer plusFineAmount) {
        this.plusFineAmount = plusFineAmount;
    }

    public Integer getIsReconsideration() {
        return isReconsideration;
    }

    public void setIsReconsideration(Integer isReconsideration) {
        this.isReconsideration = isReconsideration;
    }

    public String getReconsiderationDateStr() {
        return reconsiderationDateStr;
    }

    public void setReconsiderationDateStr(String reconsiderationDateStr) {
        this.reconsiderationDateStr = reconsiderationDateStr;
    }

    public String getReconsiderationResult() {
        return reconsiderationResult;
    }

    public void setReconsiderationResult(String reconsiderationResult) {
        this.reconsiderationResult = reconsiderationResult;
    }

    public Integer getIsLawsuit() {
        return isLawsuit;
    }

    public void setIsLawsuit(Integer isLawsuit) {
        this.isLawsuit = isLawsuit;
    }

    public String getLawsuitDateStr() {
        return lawsuitDateStr;
    }

    public void setLawsuitDateStr(String lawsuitDateStr) {
        this.lawsuitDateStr = lawsuitDateStr;
    }

    public String getLawsuitResult() {
        return lawsuitResult;
    }

    public void setLawsuitResult(String lawsuitResult) {
        this.lawsuitResult = lawsuitResult;
    }

    public Integer getIsEndCase() {
        return isEndCase;
    }

    public void setIsEndCase(Integer isEndCase) {
        this.isEndCase = isEndCase;
    }

    public String getClosedDateStr() {
        return closedDateStr;
    }

    public void setClosedDateStr(String closedDateStr) {
        this.closedDateStr = closedDateStr;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getFileReviewStatus() {
        return fileReviewStatus;
    }

    public void setFileReviewStatus(String fileReviewStatus) {
        this.fileReviewStatus = fileReviewStatus;
    }

    public String getCaseCheck() {
        return caseCheck;
    }

    public void setCaseCheck(String caseCheck) {
        this.caseCheck = caseCheck;
    }

    public String getCaseCheckTimeStr() {
        return caseCheckTimeStr;
    }

    public void setCaseCheckTimeStr(String caseCheckTimeStr) {
        this.caseCheckTimeStr = caseCheckTimeStr;
    }

    public String getCaseRevokeCheck() {
        return caseRevokeCheck;
    }

    public void setCaseRevokeCheck(String caseRevokeCheck) {
        this.caseRevokeCheck = caseRevokeCheck;
    }

    public String getCaseRevokeCheckTimeStr() {
        return caseRevokeCheckTimeStr;
    }

    public void setCaseRevokeCheckTimeStr(String caseRevokeCheckTimeStr) {
        this.caseRevokeCheckTimeStr = caseRevokeCheckTimeStr;
    }

    public String getDistrictGovOfficeAuditor() {
        return districtGovOfficeAuditor;
    }

    public void setDistrictGovOfficeAuditor(String districtGovOfficeAuditor) {
        this.districtGovOfficeAuditor = districtGovOfficeAuditor;
    }

    public String getDistrictGovOfficeAuditTimeStr() {
        return districtGovOfficeAuditTimeStr;
    }

    public void setDistrictGovOfficeAuditTimeStr(String districtGovOfficeAuditTimeStr) {
        this.districtGovOfficeAuditTimeStr = districtGovOfficeAuditTimeStr;
    }

    public Integer getDistrictGovOfficeAuditSta() {
        return districtGovOfficeAuditSta;
    }

    public void setDistrictGovOfficeAuditSta(Integer districtGovOfficeAuditSta) {
        this.districtGovOfficeAuditSta = districtGovOfficeAuditSta;
    }

    public Integer getIsOther() {
        return isOther;
    }

    public void setIsOther(Integer isOther) {
        this.isOther = isOther;
    }

    public String getOtherDetailContent() {
        return otherDetailContent;
    }

    public void setOtherDetailContent(String otherDetailContent) {
        this.otherDetailContent = otherDetailContent;
    }

    public Integer getIsOrderCorrect() {
        return isOrderCorrect;
    }

    public void setIsOrderCorrect(Integer isOrderCorrect) {
        this.isOrderCorrect = isOrderCorrect;
    }

    public String getFilingNumber() {
        return filingNumber;
    }

    public void setFilingNumber(String filingNumber) {
        this.filingNumber = filingNumber;
    }

    public String getApprovedPerson() {
        return approvedPerson;
    }

    public void setApprovedPerson(String approvedPerson) {
        this.approvedPerson = approvedPerson;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedSuggest() {
        return approvedSuggest;
    }

    public void setApprovedSuggest(String approvedSuggest) {
        this.approvedSuggest = approvedSuggest;
    }

    public String getContactPhoneCitizen() {
        return contactPhoneCitizen;
    }

    public void setContactPhoneCitizen(String contactPhoneCitizen) {
        this.contactPhoneCitizen = contactPhoneCitizen;
    }

    public Integer getIsFilingLegalReview() {
        return isFilingLegalReview;
    }

    public void setIsFilingLegalReview(Integer isFilingLegalReview) {
        this.isFilingLegalReview = isFilingLegalReview;
    }

    public String getFilingLegalReviewDateStr() {
        return filingLegalReviewDateStr;
    }

    public void setFilingLegalReviewDateStr(String filingLegalReviewDateStr) {
        this.filingLegalReviewDateStr = filingLegalReviewDateStr;
    }

    public String getContactPhoneLegalperson() {
        return contactPhoneLegalperson;
    }

    public void setContactPhoneLegalperson(String contactPhoneLegalperson) {
        this.contactPhoneLegalperson = contactPhoneLegalperson;
    }

    public String getInformingbookDeliveryDateStr() {
        return informingbookDeliveryDateStr;
    }

    public void setInformingbookDeliveryDateStr(String informingbookDeliveryDateStr) {
        this.informingbookDeliveryDateStr = informingbookDeliveryDateStr;
    }

    public String getInformingbookName() {
        return informingbookName;
    }

    public void setInformingbookName(String informingbookName) {
        this.informingbookName = informingbookName;
    }

    public String getInformingbookPath() {
        return informingbookPath;
    }

    public void setInformingbookPath(String informingbookPath) {
        this.informingbookPath = informingbookPath;
    }

    public String getSurveyEndDateStr() {
        return surveyEndDateStr;
    }

    public void setSurveyEndDateStr(String surveyEndDateStr) {
        this.surveyEndDateStr = surveyEndDateStr;
    }

    public Integer getIsOrdercorrectDecision() {
        return isOrdercorrectDecision;
    }

    public void setIsOrdercorrectDecision(Integer isOrdercorrectDecision) {
        this.isOrdercorrectDecision = isOrdercorrectDecision;
    }

    public Integer getIsPlot() {
        return isPlot;
    }

    public void setIsPlot(Integer isPlot) {
        this.isPlot = isPlot;
    }

    public Integer getIsOrdercorrectExection() {
        return isOrdercorrectExection;
    }

    public void setIsOrdercorrectExection(Integer isOrdercorrectExection) {
        this.isOrdercorrectExection = isOrdercorrectExection;
    }

    public Integer getStagedExection() {
        return stagedExection;
    }

    public void setStagedExection(Integer stagedExection) {
        this.stagedExection = stagedExection;
    }

    public Integer getDelayedExection() {
        return delayedExection;
    }

    public void setDelayedExection(Integer delayedExection) {
        this.delayedExection = delayedExection;
    }

    public String getCaseSource() {
        return caseSource;
    }

    public void setCaseSource(String caseSource) {
        this.caseSource = caseSource;
    }

    public String getCommonCaseCode() {
        return commonCaseCode;
    }

    public void setCommonCaseCode(String commonCaseCode) {
        this.commonCaseCode = commonCaseCode;
    }

    public String getCaseSourceOther() {
        return caseSourceOther;
    }

    public void setCaseSourceOther(String caseSourceOther) {
        this.caseSourceOther = caseSourceOther;
    }

    public Integer getDetainPermitDays() {
        return detainPermitDays;
    }

    public void setDetainPermitDays(Integer detainPermitDays) {
        this.detainPermitDays = detainPermitDays;
    }

    public String getFilingDateStr() {
        return filingDateStr;
    }

    public void setFilingDateStr(String filingDateStr) {
        this.filingDateStr = filingDateStr;
    }

    public String getHearingDateStr() {
        return hearingDateStr;
    }

    public void setHearingDateStr(String hearingDateStr) {
        this.hearingDateStr = hearingDateStr;
    }

    public String getUniformCreditCode() {
        return uniformCreditCode;
    }

    public void setUniformCreditCode(String uniformCreditCode) {
        this.uniformCreditCode = uniformCreditCode;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getPartyActivePerforDateStr() {
        return partyActivePerforDateStr;
    }

    public void setPartyActivePerforDateStr(String partyActivePerforDateStr) {
        this.partyActivePerforDateStr = partyActivePerforDateStr;
    }

    public Integer getIsOrganEnforce() {
        return isOrganEnforce;
    }

    public void setIsOrganEnforce(Integer isOrganEnforce) {
        this.isOrganEnforce = isOrganEnforce;
    }

    public String getOrganEnforcementDateStr() {
        return organEnforcementDateStr;
    }

    public void setOrganEnforcementDateStr(String organEnforcementDateStr) {
        this.organEnforcementDateStr = organEnforcementDateStr;
    }

    public String getApplyCourtEnforceDateStr() {
        return applyCourtEnforceDateStr;
    }

    public void setApplyCourtEnforceDateStr(String applyCourtEnforceDateStr) {
        this.applyCourtEnforceDateStr = applyCourtEnforceDateStr;
    }

    public String getCourtActualExeDateStr() {
        return courtActualExeDateStr;
    }

    public void setCourtActualExeDateStr(String courtActualExeDateStr) {
        this.courtActualExeDateStr = courtActualExeDateStr;
    }

    public String getIllegalFacts() {
        return illegalFacts;
    }

    public void setIllegalFacts(String illegalFacts) {
        this.illegalFacts = illegalFacts;
    }

    public String getIllegalEvidence() {
        return illegalEvidence;
    }

    public void setIllegalEvidence(String illegalEvidence) {
        this.illegalEvidence = illegalEvidence;
    }

    public String getOrganizationCodeType() {
        return organizationCodeType;
    }

    public void setOrganizationCodeType(String organizationCodeType) {
        this.organizationCodeType = organizationCodeType;
    }

    public String getIllegalEvidenceType() {
        return illegalEvidenceType;
    }

    public void setIllegalEvidenceType(String illegalEvidenceType) {
        this.illegalEvidenceType = illegalEvidenceType;
    }

    public String getIllegalDescript() {
        return illegalDescript;
    }

    public void setIllegalDescript(String illegalDescript) {
        this.illegalDescript = illegalDescript;
    }

    public Integer getCorrectType() {
        return correctType;
    }

    public void setCorrectType(Integer correctType) {
        this.correctType = correctType;
    }

    public Integer getCorrectTypeDecision() {
        return correctTypeDecision;
    }

    public void setCorrectTypeDecision(Integer correctTypeDecision) {
        this.correctTypeDecision = correctTypeDecision;
    }

    public Integer getCorrectTypeExection() {
        return correctTypeExection;
    }

    public void setCorrectTypeExection(Integer correctTypeExection) {
        this.correctTypeExection = correctTypeExection;
    }

    public String getCorrectStartdateStr() {
        return correctStartdateStr;
    }

    public void setCorrectStartdateStr(String correctStartdateStr) {
        this.correctStartdateStr = correctStartdateStr;
    }

    public String getCorrectEnddateStr() {
        return correctEnddateStr;
    }

    public void setCorrectEnddateStr(String correctEnddateStr) {
        this.correctEnddateStr = correctEnddateStr;
    }

    public String getCorrectStartdateDecisionStr() {
        return correctStartdateDecisionStr;
    }

    public void setCorrectStartdateDecisionStr(String correctStartdateDecisionStr) {
        this.correctStartdateDecisionStr = correctStartdateDecisionStr;
    }

    public String getCorrectEnddateDecisionStr() {
        return correctEnddateDecisionStr;
    }

    public void setCorrectEnddateDecisionStr(String correctEnddateDecisionStr) {
        this.correctEnddateDecisionStr = correctEnddateDecisionStr;
    }

    public String getCorrectStartdateExectionStr() {
        return correctStartdateExectionStr;
    }

    public void setCorrectStartdateExectionStr(String correctStartdateExectionStr) {
        this.correctStartdateExectionStr = correctStartdateExectionStr;
    }

    public String getCorrectEnddateExectionStr() {
        return correctEnddateExectionStr;
    }

    public void setCorrectEnddateExectionStr(String correctEnddateExectionStr) {
        this.correctEnddateExectionStr = correctEnddateExectionStr;
    }

    public String getCorrectDateStr() {
        return correctDateStr;
    }

    public void setCorrectDateStr(String correctDateStr) {
        this.correctDateStr = correctDateStr;
    }

    public String getStartdateWithholdStr() {
        return startdateWithholdStr;
    }

    public void setStartdateWithholdStr(String startdateWithholdStr) {
        this.startdateWithholdStr = startdateWithholdStr;
    }

    public String getEnddateWithholdStr() {
        return enddateWithholdStr;
    }

    public void setEnddateWithholdStr(String enddateWithholdStr) {
        this.enddateWithholdStr = enddateWithholdStr;
    }

    public String getStartdateDetainStr() {
        return startdateDetainStr;
    }

    public void setStartdateDetainStr(String startdateDetainStr) {
        this.startdateDetainStr = startdateDetainStr;
    }

    public String getEnddateDetainStr() {
        return enddateDetainStr;
    }

    public void setEnddateDetainStr(String enddateDetainStr) {
        this.enddateDetainStr = enddateDetainStr;
    }

    public String getPostponedToDelayStr() {
        return postponedToDelayStr;
    }

    public void setPostponedToDelayStr(String postponedToDelayStr) {
        this.postponedToDelayStr = postponedToDelayStr;
    }

    public String getApplyDateDelayStr() {
        return applyDateDelayStr;
    }

    public void setApplyDateDelayStr(String applyDateDelayStr) {
        this.applyDateDelayStr = applyDateDelayStr;
    }

    public String getApprovalDateDelayStr() {
        return approvalDateDelayStr;
    }

    public void setApprovalDateDelayStr(String approvalDateDelayStr) {
        this.approvalDateDelayStr = approvalDateDelayStr;
    }

    public String getDeadlineStageStr() {
        return deadlineStageStr;
    }

    public void setDeadlineStageStr(String deadlineStageStr) {
        this.deadlineStageStr = deadlineStageStr;
    }

    public String getApplyDateStageStr() {
        return applyDateStageStr;
    }

    public void setApplyDateStageStr(String applyDateStageStr) {
        this.applyDateStageStr = applyDateStageStr;
    }

    public String getApprovalDateStageStr() {
        return approvalDateStageStr;
    }

    public void setApprovalDateStageStr(String approvalDateStageStr) {
        this.approvalDateStageStr = approvalDateStageStr;
    }

    public String getClosedCaseInfo() {
        return closedCaseInfo;
    }

    public void setClosedCaseInfo(String closedCaseInfo) {
        this.closedCaseInfo = closedCaseInfo;
    }

    public String getClosedState() {
        return closedState;
    }

    public void setClosedState(String closedState) {
        this.closedState = closedState;
    }

    public String getLegalExaminaResult() {
        return legalExaminaResult;
    }

    public void setLegalExaminaResult(String legalExaminaResult) {
        this.legalExaminaResult = legalExaminaResult;
    }

    public String getLegalReviewFile() {
        return legalReviewFile;
    }

    public void setLegalReviewFile(String legalReviewFile) {
        this.legalReviewFile = legalReviewFile;
    }

    public String getOtherOrganName() {
        return otherOrganName;
    }

    public void setOtherOrganName(String otherOrganName) {
        this.otherOrganName = otherOrganName;
    }

    public String getOtherOrganPhoneNum() {
        return otherOrganPhoneNum;
    }

    public void setOtherOrganPhoneNum(String otherOrganPhoneNum) {
        this.otherOrganPhoneNum = otherOrganPhoneNum;
    }

    public String getOtherOrganCompanyName() {
        return otherOrganCompanyName;
    }

    public void setOtherOrganCompanyName(String otherOrganCompanyName) {
        this.otherOrganCompanyName = otherOrganCompanyName;
    }

    public String getOtherOrganCode() {
        return otherOrganCode;
    }

    public void setOtherOrganCode(String otherOrganCode) {
        this.otherOrganCode = otherOrganCode;
    }

    public String getOtherOrganAddress() {
        return otherOrganAddress;
    }

    public void setOtherOrganAddress(String otherOrganAddress) {
        this.otherOrganAddress = otherOrganAddress;
    }

    public Integer getStagesExection() {
        return stagesExection;
    }

    public void setStagesExection(Integer stagesExection) {
        this.stagesExection = stagesExection;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public Integer getIsSelfemployed() {
        return isSelfemployed;
    }

    public void setIsSelfemployed(Integer isSelfemployed) {
        this.isSelfemployed = isSelfemployed;
    }

    public String getSelfemployedCode() {
        return selfemployedCode;
    }

    public void setSelfemployedCode(String selfemployedCode) {
        this.selfemployedCode = selfemployedCode;
    }

    public String getSelfemployedCharteredCode() {
        return selfemployedCharteredCode;
    }

    public void setSelfemployedCharteredCode(String selfemployedCharteredCode) {
        this.selfemployedCharteredCode = selfemployedCharteredCode;
    }

    public String getSelfemployedCharteredType() {
        return selfemployedCharteredType;
    }

    public void setSelfemployedCharteredType(String selfemployedCharteredType) {
        this.selfemployedCharteredType = selfemployedCharteredType;
    }

    public String getSelfemployedAddress() {
        return selfemployedAddress;
    }

    public void setSelfemployedAddress(String selfemployedAddress) {
        this.selfemployedAddress = selfemployedAddress;
    }

    public Integer getCitizenAge() {
        return citizenAge;
    }

    public void setCitizenAge(Integer citizenAge) {
        this.citizenAge = citizenAge;
    }

    public Integer getIsPunishment() {
        return isPunishment;
    }

    public void setIsPunishment(Integer isPunishment) {
        this.isPunishment = isPunishment;
    }

    public String getSubmitDateStr() {
        return submitDateStr;
    }

    public void setSubmitDateStr(String submitDateStr) {
        this.submitDateStr = submitDateStr;
    }

    public String getDeleteDateStr() {
        return deleteDateStr;
    }

    public void setDeleteDateStr(String deleteDateStr) {
        this.deleteDateStr = deleteDateStr;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getCreateStartDateStr() {
        return createStartDateStr;
    }

    public void setCreateStartDateStr(String createStartDateStr) {
        this.createStartDateStr = createStartDateStr;
    }

    public String getCreateEndDateStr() {
        return createEndDateStr;
    }

    public void setCreateEndDateStr(String createEndDateStr) {
        this.createEndDateStr = createEndDateStr;
    }

    public String getCaseSourceJsonStr() {
        return caseSourceJsonStr;
    }

    public void setCaseSourceJsonStr(String caseSourceJsonStr) {
        this.caseSourceJsonStr = caseSourceJsonStr;
    }

    public String getPunishHearingMakingDateStr() {
        return punishHearingMakingDateStr;
    }

    public void setPunishHearingMakingDateStr(String punishHearingMakingDateStr) {
        this.punishHearingMakingDateStr = punishHearingMakingDateStr;
    }

    public String getPunishHearingSendDateStr() {
        return punishHearingSendDateStr;
    }

    public void setPunishHearingSendDateStr(String punishHearingSendDateStr) {
        this.punishHearingSendDateStr = punishHearingSendDateStr;
    }

    public String getPunishHearingSendWay() {
        return punishHearingSendWay;
    }

    public void setPunishHearingSendWay(String punishHearingSendWay) {
        this.punishHearingSendWay = punishHearingSendWay;
    }

    public Integer getIsPartyApplyHearing() {
        return isPartyApplyHearing;
    }

    public void setIsPartyApplyHearing(Integer isPartyApplyHearing) {
        this.isPartyApplyHearing = isPartyApplyHearing;
    }

    public Integer getIsDealHearing() {
        return isDealHearing;
    }

    public void setIsDealHearing(Integer isDealHearing) {
        this.isDealHearing = isDealHearing;
    }

    public Integer getIsSavePriorEvidence() {
        return isSavePriorEvidence;
    }

    public void setIsSavePriorEvidence(Integer isSavePriorEvidence) {
        this.isSavePriorEvidence = isSavePriorEvidence;
    }

    public String getSavePriorEvidenceDateStr() {
        return savePriorEvidenceDateStr;
    }

    public void setSavePriorEvidenceDateStr(String savePriorEvidenceDateStr) {
        this.savePriorEvidenceDateStr = savePriorEvidenceDateStr;
    }

    public String getDealPriorEvidenceDateStr() {
        return dealPriorEvidenceDateStr;
    }

    public void setDealPriorEvidenceDateStr(String dealPriorEvidenceDateStr) {
        this.dealPriorEvidenceDateStr = dealPriorEvidenceDateStr;
    }

    public Integer getIsDelaySearch() {
        return isDelaySearch;
    }

    public void setIsDelaySearch(Integer isDelaySearch) {
        this.isDelaySearch = isDelaySearch;
    }

    public String getApprovePersonSearch() {
        return approvePersonSearch;
    }

    public void setApprovePersonSearch(String approvePersonSearch) {
        this.approvePersonSearch = approvePersonSearch;
    }

    public Integer getDelayDaySearch() {
        return delayDaySearch;
    }

    public void setDelayDaySearch(Integer delayDaySearch) {
        this.delayDaySearch = delayDaySearch;
    }

    public String getDelayStartDateSearchStr() {
        return delayStartDateSearchStr;
    }

    public void setDelayStartDateSearchStr(String delayStartDateSearchStr) {
        this.delayStartDateSearchStr = delayStartDateSearchStr;
    }

    public String getDelayEndDateSearchStr() {
        return delayEndDateSearchStr;
    }

    public void setDelayEndDateSearchStr(String delayEndDateSearchStr) {
        this.delayEndDateSearchStr = delayEndDateSearchStr;
    }

    public String getCaseContractorSuggest() {
        return caseContractorSuggest;
    }

    public void setCaseContractorSuggest(String caseContractorSuggest) {
        this.caseContractorSuggest = caseContractorSuggest;
    }

    public String getCaseHandleDateStr() {
        return caseHandleDateStr;
    }

    public void setCaseHandleDateStr(String caseHandleDateStr) {
        this.caseHandleDateStr = caseHandleDateStr;
    }

    public String getLegalReviewOrgSuggest() {
        return legalReviewOrgSuggest;
    }

    public void setLegalReviewOrgSuggest(String legalReviewOrgSuggest) {
        this.legalReviewOrgSuggest = legalReviewOrgSuggest;
    }

    public String getLegalReviewPerson() {
        return legalReviewPerson;
    }

    public void setLegalReviewPerson(String legalReviewPerson) {
        this.legalReviewPerson = legalReviewPerson;
    }

    public Integer getIsLegalQualifications() {
        return isLegalQualifications;
    }

    public void setIsLegalQualifications(Integer isLegalQualifications) {
        this.isLegalQualifications = isLegalQualifications;
    }

    public String getLegalReviewPersonSuggest() {
        return legalReviewPersonSuggest;
    }

    public void setLegalReviewPersonSuggest(String legalReviewPersonSuggest) {
        this.legalReviewPersonSuggest = legalReviewPersonSuggest;
    }

    public String getLegalReviewDateStr() {
        return legalReviewDateStr;
    }

    public void setLegalReviewDateStr(String legalReviewDateStr) {
        this.legalReviewDateStr = legalReviewDateStr;
    }

    public Integer getIsMajorCase() {
        return isMajorCase;
    }

    public void setIsMajorCase(Integer isMajorCase) {
        this.isMajorCase = isMajorCase;
    }

    public String getCorrectResult() {
        return correctResult;
    }

    public void setCorrectResult(String correctResult) {
        this.correctResult = correctResult;
    }

    public String getClosedReportApprovePerson() {
        return closedReportApprovePerson;
    }

    public void setClosedReportApprovePerson(String closedReportApprovePerson) {
        this.closedReportApprovePerson = closedReportApprovePerson;
    }

    public String getClosedApprovePerson() {
        return closedApprovePerson;
    }

    public void setClosedApprovePerson(String closedApprovePerson) {
        this.closedApprovePerson = closedApprovePerson;
    }

    public String getClosedApproveSuggest() {
        return closedApproveSuggest;
    }

    public void setClosedApproveSuggest(String closedApproveSuggest) {
        this.closedApproveSuggest = closedApproveSuggest;
    }

    public Integer getIsNext() {
        return isNext;
    }

    public void setIsNext(Integer isNext) {
        this.isNext = isNext;
    }

    public String getOtherOrganizationCodeType() {
        return otherOrganizationCodeType;
    }

    public void setOtherOrganizationCodeType(String otherOrganizationCodeType) {
        this.otherOrganizationCodeType = otherOrganizationCodeType;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }

    public String getGrading() {
        return grading;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public String getPersonJsonStr() {
        return personJsonStr;
    }

    public void setPersonJsonStr(String personJsonStr) {
        this.personJsonStr = personJsonStr;
    }

    public String getPowerJsonStr() {
        return powerJsonStr;
    }

    public void setPowerJsonStr(String powerJsonStr) {
        this.powerJsonStr = powerJsonStr;
    }

    public String getGistJsonStr() {
        return gistJsonStr;
    }

    public void setGistJsonStr(String gistJsonStr) {
        this.gistJsonStr = gistJsonStr;
    }

    public String getPunishJsonStr() {
        return punishJsonStr;
    }

    public void setPunishJsonStr(String punishJsonStr) {
        this.punishJsonStr = punishJsonStr;
    }

    public String getDiscretionaryJsonStr() {
        return discretionaryJsonStr;
    }

    public void setDiscretionaryJsonStr(String discretionaryJsonStr) {
        this.discretionaryJsonStr = discretionaryJsonStr;
    }

    public String getRevokeJsonStr() {
        return revokeJsonStr;
    }

    public void setRevokeJsonStr(String revokeJsonStr) {
        this.revokeJsonStr = revokeJsonStr;
    }

    public String getNopunishmentJsonStr() {
        return nopunishmentJsonStr;
    }

    public void setNopunishmentJsonStr(String nopunishmentJsonStr) {
        this.nopunishmentJsonStr = nopunishmentJsonStr;
    }

    public String getRevokePunishJsonStr() {
        return revokePunishJsonStr;
    }

    public void setRevokePunishJsonStr(String revokePunishJsonStr) {
        this.revokePunishJsonStr = revokePunishJsonStr;
    }

    public String getEndJsonStr() {
        return endJsonStr;
    }

    public void setEndJsonStr(String endJsonStr) {
        this.endJsonStr = endJsonStr;
    }

    public String getPunishmentStartDateStr() {
        return punishmentStartDateStr;
    }

    public void setPunishmentStartDateStr(String punishmentStartDateStr) {
        this.punishmentStartDateStr = punishmentStartDateStr;
    }

    public String getPunishmentEndDateStr() {
        return punishmentEndDateStr;
    }

    public void setPunishmentEndDateStr(String punishmentEndDateStr) {
        this.punishmentEndDateStr = punishmentEndDateStr;
    }

    public String getClosedStartDateStr() {
        return closedStartDateStr;
    }

    public void setClosedStartDateStr(String closedStartDateStr) {
        this.closedStartDateStr = closedStartDateStr;
    }

    public String getClosedEndDateStr() {
        return closedEndDateStr;
    }

    public void setClosedEndDateStr(String closedEndDateStr) {
        this.closedEndDateStr = closedEndDateStr;
    }

    public String getSurveyStartEndDateStr() {
        return surveyStartEndDateStr;
    }

    public void setSurveyStartEndDateStr(String surveyStartEndDateStr) {
        this.surveyStartEndDateStr = surveyStartEndDateStr;
    }

    public String getSurveyEndEndDateStr() {
        return surveyEndEndDateStr;
    }

    public void setSurveyEndEndDateStr(String surveyEndEndDateStr) {
        this.surveyEndEndDateStr = surveyEndEndDateStr;
    }

    public String getFilingStartDateStr() {
        return filingStartDateStr;
    }

    public void setFilingStartDateStr(String filingStartDateStr) {
        this.filingStartDateStr = filingStartDateStr;
    }

    public String getFilingEndDateStr() {
        return filingEndDateStr;
    }

    public void setFilingEndDateStr(String filingEndDateStr) {
        this.filingEndDateStr = filingEndDateStr;
    }

}
