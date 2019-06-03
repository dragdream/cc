/**   
 * 功能描述：一般案件信息表实体类
 * @Package: com.beidasoft.zfjd.caseManager.bean 
 * @author: songff   
 * @date: 2018年12月26日 上午11:20:11 
 */
package com.beidasoft.zfjd.caseManager.commonCase.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**   
* 2018 
* @ClassName: CaseCommonBasic.java
* @Description: 一般案件信息表实体类
*
* @author: songff
* @date: 2018年12月26日 上午11:20:11 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_BASE")
public class CaseCommonBase {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件编号
    @Column(name = "CASE_CODE")
    private String caseCode;

    // 执法主体ID
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    // 执法部门ID
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    // 登记人
    @Column(name = "REGISTRANT")
    private String registrant;

    // 执法日期
    @Column(name = "ENFORCEMENT_DATE")
    private Date enforcementDate;

    // 登记日期
    @Column(name = "REGISTRANT_DATE")
    private Date registrantDate;

    // 案件名称
    @Column(name = "NAME")
    private String name;

    // 当事人类型(code:1公民,2法人,9其他组织)
    @Column(name = "PARTY_TYPE")
    private String partyType;

    // 公民姓名
    @Column(name = "CITIZEN_NAME")
    private String citizenName;

    // 公民性别（1男，2女）
    @Column(name = "CITIZEN_SEX")
    private Integer citizenSex;

    // 公民有效身份证件类型（code: 01身份证，02驾驶证，03军官证，04护照，99其他）
    @Column(name = "CITIZEN_CARD_TYPE")
    private String citizenCardType;

    // 公民有效身份证件号码
    @Column(name = "CITIZEN_CARD_CODE")
    private String citizenCardCode;

    // 公民单位
    @Column(name = "CITIZEN_COMPANY")
    private String citizenCompany;

    // 公民住址
    @Column(name = "CITIZEN_ADDRESS")
    private String citizenAddress;

    // 单位名称
    @Column(name = "COMPANY_NAME")
    private String companyName;

    // 法定代表人或负责人
    @Column(name = "PRINCIPAL")
    private String principal;

    // 组织机构代码
    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;

    // 地址
    @Column(name = "ADDRESS")
    private String address;

    // 行政处罚决定书文号
    @Column(name = "PUNISHMENT_CODE")
    private String punishmentCode;

    // 行政处罚决定书日期
    @Column(name = "PUNISHMENT_DATE")
    private Date punishmentDate;

    // 处罚决定书文件名称
    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    // 处罚决定书文件路径
    @Column(name = "DOCUMENT_PATH")
    private String documentPath;

    // 行政处罚决定书书送达日期
    @Column(name = "PD_SENT_DATE")
    private Date pdSentDate;

    // 行政处罚缴款书送达日期
    @Column(name = "PP_SENT_DATE")
    private Date ppSentDate;

    // 行政处罚决定书送达方式(code: 01直接送达，02留置送达，03委托送达，04邮递送达，05公告送达)
    @Column(name = "SENT_WAY")
    private String sentWay;

    // 行政处罚缴款书送达方式(code: 01直接送达，02留置送达，03委托送达，04邮递送达，05公告送达)
    @Column(name = "PP_SENT_WAY")
    private String ppSentWay;

    // 是否警告(0不警告,1警告)
    @Column(name = "IS_WARN")
    private Integer isWarn;

    // 是否罚款(0不罚款,1罚款)
    @Column(name = "IS_FINE")
    private Integer isFine;

    // 罚款金额
    @Column(name = "FINE_SUM")
    private Double fineSum;

    // 是否吊销许可证或营业执照
    @Column(name = "IS_REVOKE_LICENSE")
    private Integer isRevokeLicense;

    // 是否责令停产停业
    @Column(name = "IS_ORDER_CLOSURE")
    private Integer isOrderClosure;

    // 是否行政拘留
    @Column(name = "IS_DTAIN")
    private Integer isDtain;

    // 拘留天数
    @Column(name = "DTAIN_DAYS")
    private Integer dtainDays;

    // 是否暂扣许可证或营业执照(0不暂扣,1暂扣)
    @Column(name = "IS_TD_LICENSE")
    private Integer isTdLicense;

    // 是否没收违法所得(0不没收,1没收)
    @Column(name = "IS_CONFISCATE")
    private Integer isConfiscate;

    // 没收所得金额
    @Column(name = "CONFISCATE_MONEY")
    private Integer confiscateMoney;

    // 是否没收非法财物(0不没收,1没收)
    @Column(name = "IS_CONFIS_PROPERTY")
    private Integer isConfisProperty;

    // 没收非法财物明细内容
    @Column(name = "CONFISCATE_DETAIL")
    private String confiscateDetail;

    // 没收非法财物金额
    @Column(name = "CONFISCATE_PRO_MON")
    private Integer confiscateProMon;

    // 是否实施行政强制措施(0不实施,1实施)
    @Column(name = "IS_FORCE")
    private Integer isForce;

    // 是否限制公民人身自由(0不限制,1限制)
    @Column(name = "IS_LIMIT_FREE")
    private Integer isLimitFree;

    // 是否查封场所、设施或者财物(0不查封,1查封)
    @Column(name = "IS_DISTRESS")
    private Integer isDistress;

    // 是否扣押财物(0不扣押,1扣押)
    @Column(name = "IS_DTAIN_PROPERTY")
    private Integer isDtainProperty;

    // 是否冻结存款、汇款(0不冻结,1冻结)
    @Column(name = "IS_FRESS")
    private Integer isFress;

    // 是否其他行政强制措施0不其他1其他
    @Column(name = "IS_ORTHER_FORCE")
    private Integer isOrtherForce;

    // 是否听证(0不听证,1听证)
    @Column(name = "IS_HEARING")
    private Integer isHearing;

    // 听证通知日期
    @Column(name = "HEARING_INFORM_DATE")
    private Date hearingInformDate;

    // 听证公告日期
    @Column(name = "HEARING_NOTICE_DATE")
    private Date hearingNoticeDate;

    // 听证举行日期
    @Column(name = "HEARING_HOLD_DATE")
    private Date hearingHoldDate;

    // 听证主持人
    @Column(name = "HEARING_HOST")
    private String hearingHost;

    // 听证参加人
    @Column(name = "HEARING_PARTICIPANTS")
    private String hearingParticipants;

    // 是否集体讨论
    @Column(name = "IS_COLLECTIVE_DISCUSSION")
    private Integer isCollectiveDiscussion;

    // 集体讨论日期
    @Column(name = "COLLECTIVE_DISCUSSION_DATE")
    private Date collectiveDiscussionDate;

    // 集体讨论结果
    @Column(name = "COLLECTIVE_DISCUSSION_RESULT")
    private String collectiveDiscussionResult;
    
    // 集体讨论主持人姓名
    @Column(name = "COLLECTIVE_DISCUSS_HOST_NAME")
    private String collectiveDiscussHostName;

    // 集体讨论主持人职务
    @Column(name = "COLLECTIVE_DISCUSS_HOST_POST")
    private String collectiveDiscussHostPost;

    // 集体讨论参加人
    @Column(name = "COLLECTIVE_DISCUSS_ATTENDER")
    private String collectiveDiscussAttender;

    // 案件是否移送（0未移送，1已移送）
    @Column(name = "IS_TRANSFER")
    private Integer isTransfer;

    // 移送机关
    @Column(name = "TRANSFER_ORGAN")
    private String transferOrgan;

    // 移送机关是否立案
    @Column(name = "TRANSFER_ORGAN_IS_FILING")
    private Integer transferOrganIsFiling;

    // 移送理由
    @Column(name = "TRANSFER_REASON")
    private String transferReason;

    // 责令改正通知书日期
    @Column(name = "ORDERCORRECT_NOTIC_DATE")
    private Date ordercorrectNoticDate;

    // 责令改正当事人签收日期
    @Column(name = "ORDERCORRECT_HOST_RECEIPT_DATE")
    private Date ordercorrectHostReceiptDate;

    // 责令改正限期改正日期
    @Column(name = "ORDERCORRECT_LIMI_CORRECT_DATE")
    private Date ordercorrectLimiCorrectDate;

    // 责令改正内容
    @Column(name = "ORDERCORRECT_CONTENT")
    private String ordercorrectContent;

    // 是否行政处罚决定执行
    @Column(name = "IS_PUNISH_DECISION_EXECUT")
    private Integer isPunishDecisionExecut;

    // 行政处罚决定执行日期
    @Column(name = "PUNISH_DECISION_EXECUT_DATE")
    private Date punishDecisionExecutDate;

    // 是否当事人执行
    @Column(name = "IS_PARTY_EXECUTION")
    private Integer isPartyExecution;

    // 当事人执行情况
    @Column(name = "PARTY_EXECUTION_SITUATION")
    private String partyExecutionSituation;

    // 当事人执行罚没款金额
    @Column(name = "PARTY_EXECUTION_FINES")
    private Integer partyExecutionFines;

    // 是否人民法院行政强制执行
    @Column(name = "IS_PEOPLECOURT_ENFORCEMENT")
    private Integer isPeoplecourtEnforcement;

    // 人民法院行政强制执行情况
    @Column(name = "PEOPLECOURT_ENFORCE_STATUS")
    private String peoplecourtEnforceStatus;

    // 人民法院行政强制执行罚没款金额
    @Column(name = "PEOPLECOURT_ENFORCE_FINES")
    private Integer peoplecourtEnforceFines;

    // 是否人民法院行政实际执行
    @Column(name = "IS_PEOPLECOURT_ACTUAL_EXEC")
    private Integer isPeoplecourtActualExec;

    // 人民法院行政实际执行情况
    @Column(name = "PEOPLECOURT_ACTUAL_EXEC_STATUS")
    private String peoplecourtActualExecStatus;

    // 人民法院行政实际执行罚没款金额
    @Column(name = "PEOPLECOURT_ACTUAL_EXEC_FINES")
    private Integer peoplecourtActualExecFines;

    // 是否行政复查
    @Column(name = "IS_ADMINISTRATIVE_REVEIEW")
    private Integer isAdministrativeReveiew;

    // 是否加处罚款
    @Column(name = "IS_PLUS_FINE")
    private Integer isPlusFine;

    // 加处罚款金额
    @Column(name = "PLUS_FINE_AMOUNT")
    private Integer plusFineAmount;

    // 是否行政复议(0不复议,1复议)
    @Column(name = "IS_RECONSIDERATION")
    private Integer isReconsideration;

    // 复议日期
    @Column(name = "RECONSIDERATION_DATE")
    private Date reconsiderationDate;

    // 复议结果
    @Column(name = "RECONSIDERATION_RESULT")
    private String reconsiderationResult;

    // 是否行政诉讼(0不诉讼,1诉讼)
    @Column(name = "IS_LAWSUIT")
    private Integer isLawsuit;

    // 诉讼日期
    @Column(name = "LAWSUIT_DATE")
    private Date lawsuitDate;

    // 诉讼结果
    @Column(name = "LAWSUIT_RESULT")
    private String lawsuitResult;

    // 是否结案
    @Column(name = "IS_END_CASE")
    private Integer isEndCase;

    // 结案日期
    @Column(name = "CLOSED_DATE")
    private Date closedDate;

    // 当前状态（code: 01已立案，02已调查取证,03已做处罚决定,04处罚执行完毕,05结案,06撤销立案）
    @Column(name = "CURRENT_STATE")
    private String currentState;

    // 创建时间
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 数据来源（1系统录入，2接口对接）
    @Column(name = "DATA_SOURCE")
    private Integer dataSource;

    // 数据删除标识（0未删除，1已删除）
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 案卷评审状态
    @Column(name = "FILE_REVIEW_STATUS")
    private String fileReviewStatus;

    // 案件抽查者
    @Column(name = "CASE_CHECK")
    private String caseCheck;

    // 案件抽查时间
    @Column(name = "CASE_CHECK_TIME")
    private Date caseCheckTime;

    // 案件撤销抽查者
    @Column(name = "CASE_REVOKE_CHECK")
    private String caseRevokeCheck;

    // 案件撤销抽查时间
    @Column(name = "CASE_REVOKE_CHECK_TIME")
    private Date caseRevokeCheckTime;

    // 区县政府法制办审核者
    @Column(name = "DISTRICT_GOV_OFFICE_AUDITOR")
    private String districtGovOfficeAuditor;

    // 区县政府法制办审核时间
    @Column(name = "DISTRICT_GOV_OFFICE_AUDIT_TIME")
    private Date districtGovOfficeAuditTime;

    // 区县政府法制办审核状态
    @Column(name = "DISTRICT_GOV_OFFICE_AUDIT_STA")
    private Integer districtGovOfficeAuditSta;

    // 是否（其他）
    @Column(name = "IS_OTHER")
    private Integer isOther;

    // 其他明细内容
    @Column(name = "OTHER_DETAIL_CONTENT")
    private String otherDetailContent;

    // 是否责令改正
    @Column(name = "IS_ORDER_CORRECT")
    private Integer isOrderCorrect;

    // 立案号（以下为二期追加字段）
    @Column(name = "FILING_NUMBER")
    private String filingNumber;

    // 批准人
    @Column(name = "APPROVED_PERSON")
    private String approvedPerson;

    // 批准日期
    @Column(name = "APPROVED_DATE")
    private Date approvedDate;

    //批准意见
    @Column(name = "APPROVED_SUGGEST")
    private String approvedSuggest;
    
    //是否法制审查
    @Column(name = "IS_FILING_LEGAL_REVIEW")
    private Integer isFilingLegalReview;
    
    //法制审查日期 
    @Column(name = "FILING_LEGAL_REVIEW_DATE")
    private Date filingLegalReviewDate;
    
    // 联系电话（公民）
    @Column(name = "CONTACT_PHONE_CITIZEN")
    private String contactPhoneCitizen;

    // 联系电话（法人）
    @Column(name = "CONTACT_PHONE_LEGALPERSON")
    private String contactPhoneLegalperson;

    // 告知书送达日期
    @Column(name = "INFORMINGBOOK_DELIVERY_DATE")
    private Date informingbookDeliveryDate;

    // 告知书名称
    @Column(name = "INFORMINGBOOK_NAME")
    private String informingbookName;

    // 告知书路径
    @Column(name = "INFORMINGBOOK_PATH")
    private String informingbookPath;

    // 调查终结日期
    @Column(name = "SURVEY_END_DATE")
    private Date surveyEndDate;

    // 责令改正(处罚决定)
    @Column(name = "IS_ORDERCORRECT_DECISION")
    private Integer isOrdercorrectDecision;

    // 是否有减轻（1从轻，2减轻情节，3从重情节， 0无）
    @Column(name = "IS_PLOT")
    private Integer isPlot;

    // 责令改正(处罚执行)
    @Column(name = "IS_ORDERCORRECT_EXECTION")
    private Integer isOrdercorrectExection;

    // 分期执行(处罚执行)
    @Column(name = "STAGED_EXECTION")
    private Integer stagedExection;

    // 延后执行(处罚执行)
    @Column(name = "DELAYED_EXECTION")
    private Integer delayedExection;

    // 案件来源（code:1现场检查,2投诉,3举报,4上级机关交办,5其他机关移送,6媒体曝光9其他）
    @Column(name = "CASE_SOURCE")
    private String caseSource;

    // 检查单号（案件来源为现场检查）
    @Column(name = "COMMON_CASE_CODE")
    private String commonCaseCode;

    // 其他(案件来源)
    @Column(name = "CASE_SOURCE_OTHER")
    private String caseSourceOther;

    // 暂扣许可证天数
    @Column(name = "DETAIN_PERMIT_DAYS")
    private Integer detainPermitDays;

    // 立案日期
    @Column(name = "FILING_DATE")
    private Date filingDate;

    // 听证日期
    @Column(name = "HEARING_DATE")
    private Date hearingDate;

    // 统一社会信用代码
    @Column(name = "UNIFORM_CREDIT_CODE")
    private String uniformCreditCode;

    // 发生地
    @Column(name = "HAPPEN_PLACE")
    private String happenPlace;

    // 当事人主动履行日期
    @Column(name = "PARTY_ACTIVE_PERFOR_DATE")
    private Date partyActivePerforDate;

    // 是否行政机关强制执行
    @Column(name = "IS_ORGAN_ENFORCE")
    private Integer isOrganEnforce;

    // 行政机关强制执行日期
    @Column(name = "ORGAN_ENFORCEMENT_DATE")
    private Date organEnforcementDate;

    // 申请人民法院强制执行日期
    @Column(name = "APPLY_COURT_ENFORCE_DATE")
    private Date applyCourtEnforceDate;

    // 人民法院实际执行日期
    @Column(name = "COURT_ACTUAL_EXE_DATE")
    private Date courtActualExeDate;

    // 违法事实
    @Column(name = "ILLEGAL_FACTS")
    private String illegalFacts;

    // 违法证据
    @Column(name = "ILLEGAL_EVIDENCE")
    private String illegalEvidence;

    // 机构编号类型
    @Column(name = "ORGANIZATION_CODE_TYPE")
    private String organizationCodeType;

    // 证据类型（CODE:01书证，02物证，03试听资料，04电子数据，05证人证言，06当事人陈述，07鉴定意见，08勘验笔录、现场笔录）
    @Column(name = "ILLEGAL_EVIDENCE_TYPE")
    private String illegalEvidenceType;

    // 证据描述
    @Column(name = "ILLEGAL_DESCRIPT")
    private String illegalDescript;

    // 改正类型(调查取证)
    @Column(name = "CORRECT_TYPE")
    private Integer correctType;

    // 改正类型(处罚决定)
    @Column(name = "CORRECT_TYPE_DECISION")
    private Integer correctTypeDecision;

    // 改正类型(处罚执行)
    @Column(name = "CORRECT_TYPE_EXECTION")
    private Integer correctTypeExection;

    // 改正开始日期(调查取证)
    @Column(name = "CORRECT_STARTDATE")
    private Date correctStartdate;

    // 改正结束日期(调查取证)
    @Column(name = "CORRECT_ENDDATE")
    private Date correctEnddate;

    // 改正开始日期(处罚决定)
    @Column(name = "CORRECT_STARTDATE_DECISION")
    private Date correctStartdateDecision;

    // 改正结束日期(处罚决定)
    @Column(name = "CORRECT_ENDDATE_DECISION")
    private Date correctEnddateDecision;

    // 改正开始日期(处罚执行)
    @Column(name = "CORRECT_STARTDATE_EXECTION")
    private Date correctStartdateExection;

    // 改正结束日期(处罚执行)
    @Column(name = "CORRECT_ENDDATE_EXECTION")
    private Date correctEnddateExection;

    // 改正复查日期
    @Column(name = "CORRECT_DATE")
    private Date correctDate;

    // 开始日期（暂扣许可证）
    @Column(name = "STARTDATE_WITHHOLD")
    private Date startdateWithhold;

    // 结束日期（暂扣许可证）
    @Column(name = "ENDDATE_WITHHOLD")
    private Date enddateWithhold;

    // 开始日期（行政拘留）
    @Column(name = "STARTDATE_DETAIN")
    private Date startdateDetain;

    // 结束日期（行政拘留）
    @Column(name = "ENDDATE_DETAIN")
    private Date enddateDetain;

    // 延期至（延期）
    @Column(name = "POSTPONED_TO_DELAY")
    private Date postponedToDelay;

    // 申请日期（延期）
    @Column(name = "APPLY_DATE_DELAY")
    private Date applyDateDelay;

    // 批准日期（延期）
    @Column(name = "APPROVAL_DATE_DELAY")
    private Date approvalDateDelay;

    // 最后期限（分期）
    @Column(name = "DEADLINE_STAGE")
    private Date deadlineStage;

    // 申请日期（分期）
    @Column(name = "APPLY_DATE_STAGE")
    private Date applyDateStage;

    // 批准日期（分期）
    @Column(name = "APPROVAL_DATE_STAGE")
    private Date approvalDateStage;

    // 结案说明
    @Column(name = "CLOSED_CASE_INFO")
    private String closedCaseInfo;

    // 结案状态（code: 01正常结案,02撤销立案,03不予处罚,04撤销原处罚决定,98终结）
    @Column(name = "CLOSED_STATE")
    private String closedState;

    // 法制审核结果
    @Column(name = "LEGAL_EXAMINA_RESULT")
    private String legalExaminaResult;

    // 法制审查文书
    @Column(name = "LEGAL_REVIEW_FILE")
    private String legalReviewFile;

    // 其机构负责人
    @Column(name = "OTHER_ORGAN_NAME")
    private String otherOrganName;

    // 其他机构联系方式
    @Column(name = "OTHER_ORGAN_PHONE_NUM")
    private String otherOrganPhoneNum;

    // 其他机构名称
    @Column(name = "OTHER_ORGAN_COMPANY_NAME")
    private String otherOrganCompanyName;

    // 其他机构 组织机构代码
    @Column(name = "OTHER_ORGAN_CODE")
    private String otherOrganCode;

    // 其他机构地址
    @Column(name = "OTHER_ORGAN_ADDRESS")
    private String otherOrganAddress;

    // 分期执行(分期)
    @Column(name = "STAGES_EXECTION")
    private Integer stagesExection;

    // 是否提交
    @Column(name = "IS_SUBMIT")
    private Integer isSubmit;

    // 是否个体户
    @Column(name = "IS_SELFEMPLOYED")
    private Integer isSelfemployed;

    // 字号
    @Column(name = "SELFEMPLOYED_CODE")
    private String selfemployedCode;

    // 个体工商户证件号
    @Column(name = "SELFEMPLOYED_CHARTERED_CODE")
    private String selfemployedCharteredCode;

    //个体工商户证件类型
    @Column(name = "SELFEMPLOYED_CHARTERED_TYPE")
    private String selfemployedCharteredType;
    
    // 营业地址
    @Column(name = "SELFEMPLOYED_ADDRESS")
    private String selfemployedAddress;

    // 公民年龄
    @Column(name = "CITIZEN_AGE")
    private Integer citizenAge;

    // 是否作出处罚
    @Column(name = "IS_PUNISHMENT")
    private Integer isPunishment;

    // 提交日期
    @Column(name = "SUBMIT_DATE")
    private Date submitDate;

    // 删除日期
    @Column(name = "DELETE_DATE")
    private Date deleteDate;

    // 更新日期
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    
    // 行政处罚事先告知书制发日期(案件调查)
    @Column(name = "PUNISH_HEARING_MAKING_DATE")
    private Date punishHearingMakingDate;

    // 行政处罚事先告知书送达日期(案件调查)
    @Column(name = "PUNISH_HEARING_SEND_DATE")
    private Date punishHearingSendDate;

    // 行政处罚事先告知书送达方式(案件调查)(code: 01直接送达，02留置送达，03委托送达，04邮递送达，05公告送达)
    @Column(name = "PUNISH_HEARING_SEND_WAY")
    private String punishHearingSendWay;

    // 当事人是否申请听证(0不申请，1申请)(案件调查)
    @Column(name = "IS_PARTY_APPLY_HEARING")
    private Integer isPartyApplyHearing;

    // 是否受理听证（0不受理，1受理）(案件调查)
    @Column(name = "IS_DEAL_HEARING")
    private Integer isDealHearing;

    // 证据是否先行登记保存（0没保存，1保存）(案件调查)
    @Column(name = "IS_SAVE_PRIOR_EVIDENCE")
    private Integer isSavePriorEvidence;

    // 证据先行登记保存登记日期(案件调查)
    @Column(name = "SAVE_PRIOR_EVIDENCE_DATE")
    private Date savePriorEvidenceDate;

    // 证据先行登记保存处理日期(案件调查)
    @Column(name = "DEAL_PRIOR_EVIDENCE_DATE")
    private Date dealPriorEvidenceDate;

    // 是否延期（0无延期，1延期）(案件调查)
    @Column(name = "IS_DELAY_SEARCH")
    private Integer isDelaySearch;

    // 延期批准人(案件调查)
    @Column(name = "APPROVE_PERSON_SEARCH")
    private String approvePersonSearch;

    // 同意延期天数(案件调查)
    @Column(name = "DELAY_DAY_SEARCH")
    private Integer delayDaySearch;

    // 延期起始时间(案件调查)
    @Column(name = "DELAY_START_DATE_SEARCH")
    private Date delayStartDateSearch;

    // 延期结束时间(案件调查)
    @Column(name = "DELAY_END_DATE_SEARCH")
    private Date delayEndDateSearch;
    
 // 案件处理呈批的承办人意见(审查决定)
    @Column(name = "CASE_CONTRACTOR_SUGGEST")
    private String caseContractorSuggest;

    // 案件处理呈批日期(审查决定)
    @Column(name = "CASE_HANDLE_DATE")
    private Date caseHandleDate;

    // 法制审核机构意见(审查决定)
    @Column(name = "LEGAL_REVIEW_ORG_SUGGEST")
    private String legalReviewOrgSuggest;

    // 法制审核人员姓名(审查决定)
    @Column(name = "LEGAL_REVIEW_PERSON")
    private String legalReviewPerson;

    // 有无法律职业资格证(审查决定)
    @Column(name = "IS_LEGAL_QUALIFICATIONS")
    private Integer isLegalQualifications;

    // 法制审核人员意见(审查决定)
    @Column(name = "LEGAL_REVIEW_PERSON_SUGGEST")
    private String legalReviewPersonSuggest;

    // 法制审核日期(审查决定)
    @Column(name = "LEGAL_REVIEW_DATE")
    private Date legalReviewDate;

    // 是否属于重大案件(审查决定)
    @Column(name = "IS_MAJOR_CASE")
    private Integer isMajorCase;
    
    //整改结果
    @Column(name = "CORRECT_RESULT")
    private String correctResult;
    
    //结案报告呈批人
    @Column(name = "CLOSED_REPORT_APPROVE_PERSON")
    private String closedReportApprovePerson;
    
    //结案批准人
    @Column(name = "CLOSED_APPROVE_PERSON")
    private String closedApprovePerson;
    
    //结案批准意见
    @Column(name = "CLOSED_APPROVE_SUGGEST")
    private String closedApproveSuggest;
    
    //步骤环节(0立案，1调查取证，2处罚决定，3处罚执行，4结案)
    @Column(name = "IS_NEXT")
    private Integer isNext;
    
    //其他组织证件类型（code: 01统一社会信用代码，02组织机构代码，03营业执照注册码）
    @Column(name = "OTHER_ORGANIZATION_CODE_TYPE")
    private String otherOrganizationCodeType;
    
    //案件来源子表
    @OneToMany(mappedBy = "caseCommonCaseSource", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonCaseSource> caseSources;
    
    //案件职权子表
    @OneToMany(mappedBy = "caseCommonPower", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonPower> caseCommonPowers;
    
    //案件违法依据子表
    @OneToMany(mappedBy = "caseCommonGist", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonGist> caseCommonGists;
    
    //案件处罚依据子表
    @OneToMany(mappedBy = "caseCommonPunish", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonPunish> caseCommonPunishs;
    
    //案件自由裁量基准
    @OneToMany(mappedBy = "caseCommonDiscretionary", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonDiscretionary> caseCommonDiscretionarys;
    
    //撤销立案子表
    @OneToMany(mappedBy = "caseCommonRevoke", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonRevoke> caseCommonRevokes;
    
    //不予处罚案件子表
    @OneToMany(mappedBy = "caseCommonNopunishment", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonNopunishment> caseCommonNopunishments;
    
    //案件执法人员子表
    @OneToMany(mappedBy = "caseCommonStaff", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonStaff> caseCommonStaffs;
    
    //案件终结子表
    @OneToMany(mappedBy = "caseCommonEnd", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonEnd> caseCommonEnds;
    
    //撤销原处罚决定
    @OneToMany(mappedBy = "caseCommonRevokePunish", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CaseCommonRevokePunish> caseCommonRevokePunishs;
    
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public Date getEnforcementDate() {
        return enforcementDate;
    }

    public void setEnforcementDate(Date enforcementDate) {
        this.enforcementDate = enforcementDate;
    }

    public Date getRegistrantDate() {
        return registrantDate;
    }

    public void setRegistrantDate(Date registrantDate) {
        this.registrantDate = registrantDate;
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

    public Date getPunishmentDate() {
        return punishmentDate;
    }

    public void setPunishmentDate(Date punishmentDate) {
        this.punishmentDate = punishmentDate;
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

    public Date getPdSentDate() {
        return pdSentDate;
    }

    public void setPdSentDate(Date pdSentDate) {
        this.pdSentDate = pdSentDate;
    }

    public Date getPpSentDate() {
        return ppSentDate;
    }

    public void setPpSentDate(Date ppSentDate) {
        this.ppSentDate = ppSentDate;
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

    public Date getHearingInformDate() {
        return hearingInformDate;
    }

    public void setHearingInformDate(Date hearingInformDate) {
        this.hearingInformDate = hearingInformDate;
    }

    public Date getHearingNoticeDate() {
        return hearingNoticeDate;
    }

    public void setHearingNoticeDate(Date hearingNoticeDate) {
        this.hearingNoticeDate = hearingNoticeDate;
    }

    public Date getHearingHoldDate() {
        return hearingHoldDate;
    }

    public void setHearingHoldDate(Date hearingHoldDate) {
        this.hearingHoldDate = hearingHoldDate;
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

    public Date getCollectiveDiscussionDate() {
        return collectiveDiscussionDate;
    }

    public void setCollectiveDiscussionDate(Date collectiveDiscussionDate) {
        this.collectiveDiscussionDate = collectiveDiscussionDate;
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

    public Date getOrdercorrectNoticDate() {
        return ordercorrectNoticDate;
    }

    public void setOrdercorrectNoticDate(Date ordercorrectNoticDate) {
        this.ordercorrectNoticDate = ordercorrectNoticDate;
    }

    public Date getOrdercorrectHostReceiptDate() {
        return ordercorrectHostReceiptDate;
    }

    public void setOrdercorrectHostReceiptDate(Date ordercorrectHostReceiptDate) {
        this.ordercorrectHostReceiptDate = ordercorrectHostReceiptDate;
    }

    public Date getOrdercorrectLimiCorrectDate() {
        return ordercorrectLimiCorrectDate;
    }

    public void setOrdercorrectLimiCorrectDate(Date ordercorrectLimiCorrectDate) {
        this.ordercorrectLimiCorrectDate = ordercorrectLimiCorrectDate;
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

    public Date getPunishDecisionExecutDate() {
        return punishDecisionExecutDate;
    }

    public void setPunishDecisionExecutDate(Date punishDecisionExecutDate) {
        this.punishDecisionExecutDate = punishDecisionExecutDate;
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

    public Date getReconsiderationDate() {
        return reconsiderationDate;
    }

    public void setReconsiderationDate(Date reconsiderationDate) {
        this.reconsiderationDate = reconsiderationDate;
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

    public Date getLawsuitDate() {
        return lawsuitDate;
    }

    public void setLawsuitDate(Date lawsuitDate) {
        this.lawsuitDate = lawsuitDate;
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

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Date getCaseCheckTime() {
        return caseCheckTime;
    }

    public void setCaseCheckTime(Date caseCheckTime) {
        this.caseCheckTime = caseCheckTime;
    }

    public String getCaseRevokeCheck() {
        return caseRevokeCheck;
    }

    public void setCaseRevokeCheck(String caseRevokeCheck) {
        this.caseRevokeCheck = caseRevokeCheck;
    }

    public Date getCaseRevokeCheckTime() {
        return caseRevokeCheckTime;
    }

    public void setCaseRevokeCheckTime(Date caseRevokeCheckTime) {
        this.caseRevokeCheckTime = caseRevokeCheckTime;
    }

    public String getDistrictGovOfficeAuditor() {
        return districtGovOfficeAuditor;
    }

    public void setDistrictGovOfficeAuditor(String districtGovOfficeAuditor) {
        this.districtGovOfficeAuditor = districtGovOfficeAuditor;
    }

    public Date getDistrictGovOfficeAuditTime() {
        return districtGovOfficeAuditTime;
    }

    public void setDistrictGovOfficeAuditTime(Date districtGovOfficeAuditTime) {
        this.districtGovOfficeAuditTime = districtGovOfficeAuditTime;
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

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
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

    public Date getFilingLegalReviewDate() {
        return filingLegalReviewDate;
    }

    public void setFilingLegalReviewDate(Date filingLegalReviewDate) {
        this.filingLegalReviewDate = filingLegalReviewDate;
    }

    public String getContactPhoneLegalperson() {
        return contactPhoneLegalperson;
    }

    public void setContactPhoneLegalperson(String contactPhoneLegalperson) {
        this.contactPhoneLegalperson = contactPhoneLegalperson;
    }

    public Date getInformingbookDeliveryDate() {
        return informingbookDeliveryDate;
    }

    public void setInformingbookDeliveryDate(Date informingbookDeliveryDate) {
        this.informingbookDeliveryDate = informingbookDeliveryDate;
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

    public Date getSurveyEndDate() {
        return surveyEndDate;
    }

    public void setSurveyEndDate(Date surveyEndDate) {
        this.surveyEndDate = surveyEndDate;
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

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public Date getHearingDate() {
        return hearingDate;
    }

    public void setHearingDate(Date hearingDate) {
        this.hearingDate = hearingDate;
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

    public Date getPartyActivePerforDate() {
        return partyActivePerforDate;
    }

    public void setPartyActivePerforDate(Date partyActivePerforDate) {
        this.partyActivePerforDate = partyActivePerforDate;
    }

    public Integer getIsOrganEnforce() {
        return isOrganEnforce;
    }

    public void setIsOrganEnforce(Integer isOrganEnforce) {
        this.isOrganEnforce = isOrganEnforce;
    }

    public Date getOrganEnforcementDate() {
        return organEnforcementDate;
    }

    public void setOrganEnforcementDate(Date organEnforcementDate) {
        this.organEnforcementDate = organEnforcementDate;
    }

    public Date getApplyCourtEnforceDate() {
        return applyCourtEnforceDate;
    }

    public void setApplyCourtEnforceDate(Date applyCourtEnforceDate) {
        this.applyCourtEnforceDate = applyCourtEnforceDate;
    }

    public Date getCourtActualExeDate() {
        return courtActualExeDate;
    }

    public void setCourtActualExeDate(Date courtActualExeDate) {
        this.courtActualExeDate = courtActualExeDate;
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

    public Date getCorrectStartdate() {
        return correctStartdate;
    }

    public void setCorrectStartdate(Date correctStartdate) {
        this.correctStartdate = correctStartdate;
    }

    public Date getCorrectEnddate() {
        return correctEnddate;
    }

    public void setCorrectEnddate(Date correctEnddate) {
        this.correctEnddate = correctEnddate;
    }

    public Date getCorrectStartdateDecision() {
        return correctStartdateDecision;
    }

    public void setCorrectStartdateDecision(Date correctStartdateDecision) {
        this.correctStartdateDecision = correctStartdateDecision;
    }

    public Date getCorrectEnddateDecision() {
        return correctEnddateDecision;
    }

    public void setCorrectEnddateDecision(Date correctEnddateDecision) {
        this.correctEnddateDecision = correctEnddateDecision;
    }

    public Date getCorrectStartdateExection() {
        return correctStartdateExection;
    }

    public void setCorrectStartdateExection(Date correctStartdateExection) {
        this.correctStartdateExection = correctStartdateExection;
    }

    public Date getCorrectEnddateExection() {
        return correctEnddateExection;
    }

    public void setCorrectEnddateExection(Date correctEnddateExection) {
        this.correctEnddateExection = correctEnddateExection;
    }

    public Date getCorrectDate() {
        return correctDate;
    }

    public void setCorrectDate(Date correctDate) {
        this.correctDate = correctDate;
    }

    public Date getStartdateWithhold() {
        return startdateWithhold;
    }

    public void setStartdateWithhold(Date startdateWithhold) {
        this.startdateWithhold = startdateWithhold;
    }

    public Date getEnddateWithhold() {
        return enddateWithhold;
    }

    public void setEnddateWithhold(Date enddateWithhold) {
        this.enddateWithhold = enddateWithhold;
    }

    public Date getStartdateDetain() {
        return startdateDetain;
    }

    public void setStartdateDetain(Date startdateDetain) {
        this.startdateDetain = startdateDetain;
    }

    public Date getEnddateDetain() {
        return enddateDetain;
    }

    public void setEnddateDetain(Date enddateDetain) {
        this.enddateDetain = enddateDetain;
    }

    public Date getPostponedToDelay() {
        return postponedToDelay;
    }

    public void setPostponedToDelay(Date postponedToDelay) {
        this.postponedToDelay = postponedToDelay;
    }

    public Date getApplyDateDelay() {
        return applyDateDelay;
    }

    public void setApplyDateDelay(Date applyDateDelay) {
        this.applyDateDelay = applyDateDelay;
    }

    public Date getApprovalDateDelay() {
        return approvalDateDelay;
    }

    public void setApprovalDateDelay(Date approvalDateDelay) {
        this.approvalDateDelay = approvalDateDelay;
    }

    public Date getDeadlineStage() {
        return deadlineStage;
    }

    public void setDeadlineStage(Date deadlineStage) {
        this.deadlineStage = deadlineStage;
    }

    public Date getApplyDateStage() {
        return applyDateStage;
    }

    public void setApplyDateStage(Date applyDateStage) {
        this.applyDateStage = applyDateStage;
    }

    public Date getApprovalDateStage() {
        return approvalDateStage;
    }

    public void setApprovalDateStage(Date approvalDateStage) {
        this.approvalDateStage = approvalDateStage;
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

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public Date getPunishHearingMakingDate() {
        return punishHearingMakingDate;
    }

    public void setPunishHearingMakingDate(Date punishHearingMakingDate) {
        this.punishHearingMakingDate = punishHearingMakingDate;
    }

    public Date getPunishHearingSendDate() {
        return punishHearingSendDate;
    }

    public void setPunishHearingSendDate(Date punishHearingSendDate) {
        this.punishHearingSendDate = punishHearingSendDate;
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

    public Date getSavePriorEvidenceDate() {
        return savePriorEvidenceDate;
    }

    public void setSavePriorEvidenceDate(Date savePriorEvidenceDate) {
        this.savePriorEvidenceDate = savePriorEvidenceDate;
    }

    public Date getDealPriorEvidenceDate() {
        return dealPriorEvidenceDate;
    }

    public void setDealPriorEvidenceDate(Date dealPriorEvidenceDate) {
        this.dealPriorEvidenceDate = dealPriorEvidenceDate;
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

    public Date getDelayStartDateSearch() {
        return delayStartDateSearch;
    }

    public void setDelayStartDateSearch(Date delayStartDateSearch) {
        this.delayStartDateSearch = delayStartDateSearch;
    }

    public Date getDelayEndDateSearch() {
        return delayEndDateSearch;
    }

    public void setDelayEndDateSearch(Date delayEndDateSearch) {
        this.delayEndDateSearch = delayEndDateSearch;
    }

    public String getCaseContractorSuggest() {
        return caseContractorSuggest;
    }

    public void setCaseContractorSuggest(String caseContractorSuggest) {
        this.caseContractorSuggest = caseContractorSuggest;
    }

    public Date getCaseHandleDate() {
        return caseHandleDate;
    }

    public void setCaseHandleDate(Date caseHandleDate) {
        this.caseHandleDate = caseHandleDate;
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
    
    public Date getLegalReviewDate() {
        return legalReviewDate;
    }

    public void setLegalReviewDate(Date legalReviewDate) {
        this.legalReviewDate = legalReviewDate;
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

    public List<CaseCommonCaseSource> getCaseSources() {
        return caseSources;
    }

    public void setCaseSources(List<CaseCommonCaseSource> caseSources) {
        this.caseSources = caseSources;
    }

    public List<CaseCommonGist> getCaseCommonGists() {
        return caseCommonGists;
    }

    public void setCaseCommonGists(List<CaseCommonGist> caseCommonGists) {
        this.caseCommonGists = caseCommonGists;
    }

    public List<CaseCommonPower> getCaseCommonPowers() {
        return caseCommonPowers;
    }

    public void setCaseCommonPowers(List<CaseCommonPower> caseCommonPowers) {
        this.caseCommonPowers = caseCommonPowers;
    }

    public List<CaseCommonPunish> getCaseCommonPunishs() {
        return caseCommonPunishs;
    }

    public void setCaseCommonPunishs(List<CaseCommonPunish> caseCommonPunishs) {
        this.caseCommonPunishs = caseCommonPunishs;
    }

    public List<CaseCommonDiscretionary> getCaseCommonDiscretionarys() {
        return caseCommonDiscretionarys;
    }

    public void setCaseCommonDiscretionarys(List<CaseCommonDiscretionary> caseCommonDiscretionarys) {
        this.caseCommonDiscretionarys = caseCommonDiscretionarys;
    }

    public List<CaseCommonRevoke> getCaseCommonRevokes() {
        return caseCommonRevokes;
    }

    public void setCaseCommonRevokes(List<CaseCommonRevoke> caseCommonRevokes) {
        this.caseCommonRevokes = caseCommonRevokes;
    }

    public List<CaseCommonNopunishment> getCaseCommonNopunishments() {
        return caseCommonNopunishments;
    }

    public void setCaseCommonNopunishments(List<CaseCommonNopunishment> caseCommonNopunishments) {
        this.caseCommonNopunishments = caseCommonNopunishments;
    }

    public List<CaseCommonStaff> getCaseCommonStaffs() {
        return caseCommonStaffs;
    }

    public void setCaseCommonStaffs(List<CaseCommonStaff> caseCommonStaffs) {
        this.caseCommonStaffs = caseCommonStaffs;
    }

    public List<CaseCommonEnd> getCaseCommonEnds() {
        return caseCommonEnds;
    }

    public void setCaseCommonEnds(List<CaseCommonEnd> caseCommonEnds) {
        this.caseCommonEnds = caseCommonEnds;
    }

    public List<CaseCommonRevokePunish> getCaseCommonRevokePunishs() {
        return caseCommonRevokePunishs;
    }

    public void setCaseCommonRevokePunishs(List<CaseCommonRevokePunish> caseCommonRevokePunishs) {
        this.caseCommonRevokePunishs = caseCommonRevokePunishs;
    }
    
}
