/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.service 
 * @author: songff   
 * @date: 2018年12月26日 下午3:05:52 
 */
package com.beidasoft.zfjd.caseManager.commonCase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonGist;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonPower;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonPunish;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonBaseDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonCaseSourceDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonEndDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonGistDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonNopunishmentDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonPowerDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonPunishDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonRevokeDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonRevokePunishDao;
import com.beidasoft.zfjd.caseManager.commonCase.dao.CaseCommonStaffDao;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseSearchModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonDataModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonGistModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonPowerModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonPunishModel;
import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.officials.bean.TblOfficials;
import com.beidasoft.zfjd.officials.dao.TblOfficialsDao;
import com.beidasoft.zfjd.officials.model.TblOfficialsModel;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.dao.SubjectDao;
import com.beidasoft.zfjd.subject.model.SubjectModel;
import com.beidasoft.zfjd.subjectPerson.bean.TblSubjectPerson;
import com.beidasoft.zfjd.subjectPerson.dao.TblSubjectPersonDao;
import com.beidasoft.zfjd.subjectPerson.model.TblSubjectPersonModel;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**   
* 2018 
* @ClassName: CaseCommonBasicService.java
* @Description: 该类的功能描述
*
* @author: songff
* @date: 2018年12月26日 下午3:05:52 
*
*/
@Service
public class CaseCommonBaseService extends TeeBaseService{

  //获取日志记录器Logger，名字为本类类名
    private static Logger logger = LogManager.getLogger(CaseCommonBaseService.class);
    
    @Autowired
    private CaseCommonBaseDao caseCommonBaseDao;
    @Autowired
    private CaseCommonCaseSourceDao cSourceDao;
    @Autowired
    private CaseCommonPowerDao caseCommonPowerDao;
    @Autowired
    private CaseCommonGistDao caseCommonGistDao;
    @Autowired
    private CaseCommonPunishDao caseCommonPunishDao;
    @Autowired
    private CaseCommonNopunishmentDao caseCommonNopunishmentDao;
    @Autowired
    private CaseCommonRevokeDao caseCommonRevokeDao;
    @Autowired
    private CaseCommonRevokePunishDao caseCommonRevokePunishDao;
    @Autowired
    private CaseCommonStaffDao caseCommonStaffDao;
    @Autowired
    private CaseCommonEndDao caseCommonEndDao;
    @Autowired
    private TblOfficialsDao officialsDao;
    @Autowired
    private TblSubjectPersonDao subjectPersonDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired 
    private TblDepartmentDao departmentDao;
    /**
     * 
    * @Function: findListByPage
    * @Description: 分页查询一般案件
    *
    * @param: tModel datagrid页面列表参数
    * @param: cBasic 查询条件参数
    * @return：返回查询结果集List
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2018年12月26日 下午3:35:44 
    *
     */
    public List<CaseCommonBase> findListByPage(TeeDataGridModel tModel, CaseCommonBaseModel cbModel) {
        return caseCommonBaseDao.findListByPageSearch(tModel, cbModel);
    }
    
    /**
     * 
    * @Function: listCount
    * @Description: 查询案件总数
    *
    * @param: cBasic 查询条件参数
    * @return：返回案件总数
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2018年12月26日 下午3:36:34 
    *
     */
    public long listCount(CaseCommonBaseModel cbModel) {
        return caseCommonBaseDao.listCount(cbModel);
    }
    
    /**
     * 
    * @Function: findListByPageSearch()
    * @Description: 一般案件查询方法
    *
    * @param: tModel 表格参数
    * @param: cbModel 查询条件参数
    * @param: request 
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月28日 上午9:09:46 
    *
     */
    public List<CaseCommonBase> findListByPageSearch(TeeDataGridModel tModel, CaseCommonBaseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return caseCommonBaseDao.findListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }
    
    /**
     * 
    * @Function: listSearchCount()
    * @Description: 一般案件查询总数方法
    *
    * @param: tModel 表格参数
    * @param: cbModel 查询条件参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月28日 上午9:10:20 
    *
     */
    public long listSearchCount(CaseCommonBaseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return caseCommonBaseDao.listSearchCount(cbModel, orgCtrl);
    }
    
    /**
     * 
    * @Function: findCaseCommonBaseById()
    * @Description: 通过ID，查询案件信息
    *
    * @param: cbModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月14日 下午2:51:24 
    *
     */
    public CaseCommonBase findCaseCommonBaseById(CaseCommonBaseModel cbModel) {
        return caseCommonBaseDao.findCaseCommonBaseById(cbModel);
    }
    
    /**
     * 
    * @Function: saveFilingStage()
    * @Description: 保存立案信息
    *
    * @param: cBase 立案信息项对象
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月10日 下午5:17:33 
    *
     */
    public void saveFilingStage(CaseCommonBase cBase) throws Exception {
        try {
            caseCommonBaseDao.saveFilingStage(cBase);
            
            //先删除子表信息，再插入子表信息
            cSourceDao.deleteCaseSourceByCaseId(cBase);
            cSourceDao.saveBatchCaseSource(cBase.getCaseSources());
            
            //先删除旧的执法人员，再插入新的执法人员
            caseCommonStaffDao.deleteCaseStaff(cBase);
            caseCommonStaffDao.saveBatchCaseStaff(cBase.getCaseCommonStaffs());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        
    }
    
    /**
     * 
    * @Function: findListBySubjectIdAndPerson()
    * @Description: 通过主体ID，人员其他信息查询人员
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月17日 下午3:17:25 
    *
     */
    public TeeEasyuiDataGridJson findListBySubjectIdAndPerson(TeeDataGridModel tModel, TblSubjectPersonModel subPersonModel){
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        //根据主体id查询执法人员id
        List<TblSubjectPerson> subPersonList = subjectPersonDao.listSubjectPerson(subPersonModel);
        //查询主体
        Subject subject = subjectDao.get(subPersonModel.getSubjectId());
        // 接收查询集合 cList
        List<TblOfficials> cList = null;
        // bean对应的model cModels
        List<TblOfficialsModel> cModels = new ArrayList<TblOfficialsModel>();
        // 查询集合总数
        Long count = null;
        //人员model
        TblOfficialsModel cbModel = new TblOfficialsModel();
        //获取有效人员ID
        String ids = "";
        for (TblSubjectPerson tblSubjectPerson : subPersonList) {
            TblOfficialsModel oModel = new TblOfficialsModel();
            oModel.setId(tblSubjectPerson.getPersonId());
            oModel.setName(subPersonModel.getName());
            oModel.setEnforcerCode(subPersonModel.getEnforceCode());
            TblOfficials officials = officialsDao.findOfficialsById(oModel);
            if (officials != null) {
                ids = ids + "'" +officials.getId() + "',";
            }
        }
        if (ids.length() > 0) {
            ids = ids.substring(0,ids.length() - 1);
        }else {
            ids = "'empty'";
        }
        
        cbModel.setIds(ids);
        cbModel.setName(subPersonModel.getName());
        cbModel.setEnforcerCode(subPersonModel.getEnforceCode());
        //有效人员ID获取结束
        cList = officialsDao.listByPage(tModel.getFirstResult(),tModel.getRows(), cbModel);
        count = officialsDao.getTotal(cbModel);
        if (!cList.isEmpty()) {
            for (TblOfficials officials : cList) {
                TblOfficialsModel model = new TblOfficialsModel();
                BeanUtils.copyProperties(officials, model);
                //主体名称
                model.setSubjectName(subject.getSubName());
                //执法证号
                if (!TeeUtility.isNullorEmpty(officials.getCityCode())) {
                    model.setEnforcerCode(officials.getCityCode());
                } else {
                    model.setEnforcerCode(officials.getDepartmentCode());
                }
                cModels.add(model);
            }
        }
        json.setRows(cModels);
        json.setTotal(count);
        return json;
    }
    
    /**
     * 
    * @Function: updateCaseInfoByColumnsAndId()
    * @Description: 该函数的功能描述
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 上午12:35:17 
    *
     */
    public void updateCaseInfoByColumnsAndId(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        Map<String, Object> columns = new HashMap<String, Object>();
        String id = cBase.getId();
        try {
            if (cbModel.getGrading() != null && !"".equals(cbModel.getGrading())) {
                String grading = cbModel.getGrading();
                columns.put("UPDATE_DATE", cBase.getUpdateDate());
                columns.put("CURRENT_STATE", cBase.getCurrentState()); //案件当前状态
                columns.put("IS_NEXT", cBase.getIsNext()); //案件当前状态
                if ("01".equals(grading)) {//立案
                    //处理需要更新的字段map
                    columns.put("SUBJECT_ID", cBase.getSubjectId());//执法主体
                    columns.put("DEPARTMENT_ID", cBase.getDepartmentId());// 执法系统
                    columns.put("NAME", cBase.getName()); // 案件名称
                    columns.put("REGISTRANT", cBase.getRegistrant()); // 登记人
                    columns.put("CASE_SOURCE", cBase.getCaseSource());// 案件来源
                    columns.put("FILING_NUMBER", cBase.getFilingNumber());// 立案号
                    columns.put("FILING_DATE", cBase.getFilingDate());//立案日期
                    columns.put("APPROVED_PERSON", cBase.getApprovedPerson());//申请人
                    columns.put("APPROVED_DATE", cBase.getApprovedDate());//申请时间
                    columns.put("APPROVED_SUGGEST", cBase.getApprovedSuggest());//批准意见
                    columns.put("IS_FILING_LEGAL_REVIEW", cBase.getIsFilingLegalReview());//是否法制审查
                    columns.put("FILING_LEGAL_REVIEW_DATE", cBase.getFilingLegalReviewDate());//法制审查日期
                    
                    columns.put("PARTY_TYPE", cBase.getPartyType());//当事人类型
                    //当事人为公民
                    columns.put("IS_SELFEMPLOYED", cBase.getIsSelfemployed());// 是否个体工商户
                    columns.put("CITIZEN_NAME", cBase.getCitizenName());// 公民姓名
                    columns.put("CITIZEN_CARD_TYPE", cBase.getCitizenCardType());// 证件类型
                    columns.put("CITIZEN_CARD_CODE", cBase.getCitizenCardCode());// 证件号
                    columns.put("CITIZEN_SEX", cBase.getCitizenSex());// 性别（1男，2女）
                    columns.put("CITIZEN_AGE", cBase.getCitizenAge());// 年龄
                    columns.put("CONTACT_PHONE_CITIZEN", cBase.getContactPhoneCitizen());// 联系电话
                    columns.put("CITIZEN_ADDRESS", cBase.getCitizenAddress());// 住址
                    columns.put("CITIZEN_COMPANY", cBase.getCitizenCompany());// 单位
                    columns.put("SELFEMPLOYED_CODE", cBase.getSelfemployedCode());// 字号
                    columns.put("SELFEMPLOYED_CHARTERED_CODE", cBase.getSelfemployedCharteredCode());// 个体工商户证件号
                    columns.put("SELFEMPLOYED_CHARTERED_TYPE", cBase.getSelfemployedCharteredType());// 个体工商户证件类型
                    columns.put("SELFEMPLOYED_ADDRESS", cBase.getSelfemployedAddress());// 营业地址
                    //当事人为法人
                    columns.put("PRINCIPAL", cBase.getPrincipal());// 法人
                    columns.put("CONTACT_PHONE_LEGALPERSON", cBase.getContactPhoneLegalperson());// 法人联系方式
                    columns.put("ORGANIZATION_CODE_TYPE", cBase.getOrganizationCodeType());// 法人代码类型
                    columns.put("ORGANIZATION_CODE", cBase.getOrganizationCode());// 法人代码
                    columns.put("COMPANY_NAME", cBase.getCompanyName());// 单位名称
                    columns.put("ADDRESS", cBase.getAddress());// 地址
                    //当事人为其他组织
                    columns.put("OTHER_ORGAN_NAME", cBase.getOtherOrganName());// 其他组织负责人
                    columns.put("OTHER_ORGAN_PHONE_NUM", cBase.getOtherOrganPhoneNum()); // 联系电话
                    columns.put("OTHER_ORGANIZATION_CODE_TYPE", cBase.getOtherOrganizationCodeType());// 其他组织，代码类型
                    columns.put("OTHER_ORGAN_CODE", cBase.getOtherOrganCode());// 其他组织，代码
                    columns.put("OTHER_ORGAN_COMPANY_NAME", cBase.getOtherOrganCompanyName());// 其他组织单位名称
                    columns.put("OTHER_ORGAN_ADDRESS", cBase.getOtherOrganAddress());// 其他组织地址
                    
                    //先删除子表信息, 再插入子表信息
                    cSourceDao.deleteCaseSourceByCaseId(cBase);
                    cSourceDao.saveBatchCaseSource(cBase.getCaseSources());
                    
                    //先删除旧的执法人员, 再插入新的执法人员
                    caseCommonStaffDao.deleteCaseStaff(cBase);
                    caseCommonStaffDao.saveBatchCaseStaff(cBase.getCaseCommonStaffs());
                    
                }else if("02".equals(grading)){//调查取证
                    columns.put("SURVEY_END_DATE", cBase.getSurveyEndDate());//调查终结日期
                    
                    columns.put("PUNISH_HEARING_MAKING_DATE", cBase.getPunishHearingMakingDate()); //行政处罚事先告知书制发日期(案件调查)
                    columns.put("PUNISH_HEARING_SEND_DATE", cBase.getPunishHearingSendDate()); //行政处罚事先告知书送达日期(案件调查)
                    columns.put("PUNISH_HEARING_SEND_WAY", cBase.getPunishHearingSendWay()); //行政处罚事先告知书送达方式(案件调查)(code: 01直接送达，02留置送达，03委托送达，04邮递送达，05公告送达)
                    columns.put("IS_PARTY_APPLY_HEARING", cBase.getIsPartyApplyHearing()); //当事人是否申请听证(0不申请，1申请)(案件调查)
                    columns.put("IS_DEAL_HEARING", cBase.getIsDealHearing()); //是否受理听证（0不受理，1受理）(案件调查)
                    columns.put("IS_SAVE_PRIOR_EVIDENCE", cBase.getIsSavePriorEvidence()); //证据是否先行登记保存（0没保存，1保存）(案件调查)
                    columns.put("SAVE_PRIOR_EVIDENCE_DATE", cBase.getSavePriorEvidenceDate()); //证据先行登记保存登记日期(案件调查)
                    columns.put("DEAL_PRIOR_EVIDENCE_DATE", cBase.getDealPriorEvidenceDate()); //证据先行登记保存处理日期(案件调查)
                    columns.put("IS_DELAY_SEARCH", cBase.getIsDelaySearch()); //是否延期（0无延期，1延期）(案件调查)
                    columns.put("APPROVE_PERSON_SEARCH", cBase.getApprovePersonSearch()); //延期批准人(案件调查)
                    columns.put("DELAY_DAY_SEARCH", cBase.getDelayDaySearch()); //同意延期天数(案件调查)
                    columns.put("DELAY_START_DATE_SEARCH", cBase.getDelayStartDateSearch()); //延期起始日期(案件调查)
                    columns.put("DELAY_END_DATE_SEARCH", cBase.getDelayEndDateSearch()); //延期结束日期(案件调查)
                    
                    columns.put("IS_HEARING", cBase.getIsHearing()); //是否听证
                    columns.put("IS_FORCE", cBase.getIsForce());//是否采取行政强制措施
                    columns.put("HEARING_DATE", cBase.getHearingDate());//听证通知书送达日期
                    columns.put("HEARING_HOLD_DATE", cBase.getHearingHoldDate());//听证举行日期
                    columns.put("IS_ORDER_CORRECT", cBase.getIsOrderCorrect());//是否责令改正
                    columns.put("CORRECT_TYPE", cBase.getCorrectType());//责令改正类型
                    columns.put("CORRECT_STARTDATE", cBase.getCorrectStartdate());//起止日期
                    columns.put("CORRECT_ENDDATE", cBase.getCorrectEnddate());//结束日期
                    columns.put("INFORMINGBOOK_DELIVERY_DATE", cBase.getInformingbookDeliveryDate());//告知书送达日期
                    columns.put("ILLEGAL_EVIDENCE_TYPE", cBase.getIllegalEvidenceType()); //证件类型
                    columns.put("ILLEGAL_DESCRIPT", cBase.getIllegalDescript());//证件描述
                    
                }else if("03".equals(grading)){//处罚决定
                    columns.put("IS_PLOT", cBase.getIsPlot());//是否减轻情节
                    columns.put("ILLEGAL_FACTS", cBase.getIllegalFacts()); //违法事实
                    columns.put("IS_PUNISHMENT", cBase.getIsPunishment()); //是否作出处罚决定（1作出处罚，2不予处罚）
                    
                    columns.put("CASE_CONTRACTOR_SUGGEST", cBase.getCaseContractorSuggest()); //案件处理呈批的承办人意见
                    columns.put("CASE_HANDLE_DATE", cBase.getCaseHandleDate()); //案件处理呈批日期
                    columns.put("LEGAL_REVIEW_ORG_SUGGEST", cBase.getLegalReviewOrgSuggest()); //法制审核机构意见
                    columns.put("LEGAL_REVIEW_PERSON", cBase.getLegalReviewPerson()); //法制审核人员姓名
                    columns.put("IS_LEGAL_QUALIFICATIONS", cBase.getIsLegalQualifications()); //有无法律职业资格证
                    columns.put("LEGAL_REVIEW_PERSON_SUGGEST", cBase.getLegalReviewPersonSuggest()); //法制审核人员意见
                    columns.put("LEGAL_REVIEW_DATE", cBase.getLegalReviewDate()); //法制审核日期
                    columns.put("LEGAL_EXAMINA_RESULT", cBase.getLegalExaminaResult());//法制审核结果
                    columns.put("IS_MAJOR_CASE", cBase.getIsMajorCase()); //是否属于重大案件
                    columns.put("IS_COLLECTIVE_DISCUSSION", cBase.getIsCollectiveDiscussion()); //是否行政机关负责人集体讨论
                    columns.put("COLLECTIVE_DISCUSSION_DATE", cBase.getCollectiveDiscussionDate()); //集体讨论日期
                    columns.put("COLLECTIVE_DISCUSSION_RESULT", cBase.getCollectiveDiscussionResult()); //集体讨论决定
                    
                    columns.put("DOCUMENT_PATH", cBase.getDocumentPath()); //上传文件唯一标识ID
                    columns.put("PUNISHMENT_CODE", cBase.getPunishmentCode());//处罚决定书文号
                    columns.put("PUNISHMENT_DATE", cBase.getPunishmentDate()); //处罚决定书日期
                    columns.put("SENT_WAY", cBase.getSentWay());//处罚决定书送达方式
                    columns.put("PP_SENT_WAY", cBase.getPpSentWay());//处罚缴款书送达方式
                    columns.put("PD_SENT_DATE", cBase.getPdSentDate());//决定书送达日期
                    columns.put("PP_SENT_DATE", cBase.getPpSentDate());//处罚缴款书送达日期
                    columns.put("IS_WARN", cBase.getIsWarn());//是否警告
                    columns.put("IS_FINE", cBase.getIsFine());//是否罚款
                    columns.put("FINE_SUM", cBase.getFineSum());//罚款金额
                    columns.put("IS_ORDER_CLOSURE", cBase.getIsOrderClosure());// 是否责令停产停业
                    columns.put("IS_REVOKE_LICENSE", cBase.getIsRevokeLicense());//是否吊销许可证或营业执照
                    columns.put("IS_TD_LICENSE", cBase.getIsTdLicense());//是否暂扣许可证或营业执照
                    columns.put("IS_ORDERCORRECT_DECISION", cBase.getIsOrdercorrectDecision());//是否责令改正
                    columns.put("CORRECT_TYPE_DECISION", cBase.getCorrectTypeDecision());//责令改正类型
                    columns.put("CORRECT_STARTDATE_DECISION", cBase.getCorrectStartdateDecision());//起止日期
                    columns.put("CORRECT_ENDDATE_DECISION", cBase.getCorrectEnddateDecision());//截至日期
                    columns.put("DETAIN_PERMIT_DAYS", cBase.getDetainPermitDays());//暂扣天数
                    columns.put("STARTDATE_WITHHOLD", cBase.getStartdateWithhold());//暂扣起止日期
                    columns.put("ENDDATE_WITHHOLD", cBase.getEnddateWithhold());//暂扣结束时间
                    columns.put("IS_DTAIN", cBase.getIsDtain());//是否拘留
                    columns.put("DTAIN_DAYS", cBase.getDtainDays());//拘留天数
                    columns.put("STARTDATE_DETAIN", cBase.getStartdateDetain());//拘留起止时间
                    columns.put("ENDDATE_DETAIN", cBase.getEnddateDetain());//拘留结束时间
                    columns.put("IS_CONFISCATE", cBase.getIsConfiscate());//是否没收违法所得
                    columns.put("CONFISCATE_MONEY", cBase.getConfiscateMoney());//没收违法所得金额
                    columns.put("IS_CONFIS_PROPERTY", cBase.getIsConfisProperty());//是否没收非法财物
                    columns.put("CONFISCATE_PRO_MON", cBase.getConfiscateProMon());// 没收违法财物所得金额
                    columns.put("IS_OTHER", cBase.getIsOther());//其他
                    columns.put("OTHER_DETAIL_CONTENT", cBase.getOtherDetailContent());//其他明细内容
                    
                    //先删除违法行为信息, 再插入新的违法信息
                    caseCommonPowerDao.deleteCasePower(cBase);
                    caseCommonPowerDao.saveBatchCasePower(cBase.getCaseCommonPowers());
                    
                    //先删除违法依据信息, 再插入新的违法依据信息
                    caseCommonGistDao.deleteCaseGist(cBase);
                    caseCommonGistDao.saveBatchCaseGist(cBase.getCaseCommonGists());
                    
                    //先删除旧的处罚依据，再插入新的处罚依据
                    caseCommonPunishDao.deleteCasePunish(cBase);
                    caseCommonPunishDao.saveBatchCasePunish(cBase.getCaseCommonPunishs());
                    
                    if (cBase.getIsPunishment() == 1) {
                        //删除不予处罚信息
                        caseCommonNopunishmentDao.deleteCaseNoPunishment(cBase);
                    }
                    
                }else if("04".equals(grading)){//处罚执行
                    columns.put("IS_PUNISH_DECISION_EXECUT", cBase.getIsPunishDecisionExecut()); //行政处罚决定是否执行
                    columns.put("PUNISH_DECISION_EXECUT_DATE", cBase.getPunishDecisionExecutDate()); //行政处罚决定执行日期
                    columns.put("IS_ORDERCORRECT_EXECTION", cBase.getIsOrdercorrectExection()); //是否检查整改情况
                    columns.put("CORRECT_DATE", cBase.getCorrectDate()); //整改复查日期
                    columns.put("CORRECT_RESULT", cBase.getCorrectResult()); //整改结果
                    
                    columns.put("DELAYED_EXECTION", cBase.getDelayedExection());//是否延期
                    columns.put("POSTPONED_TO_DELAY", cBase.getPostponedToDelay());//延庆截至日期
                    columns.put("APPLY_DATE_DELAY", cBase.getApplyDateDelay());//延庆申请日期
                    columns.put("APPROVAL_DATE_DELAY", cBase.getApprovalDateDelay());//延庆批准日期
                    columns.put("STAGES_EXECTION", cBase.getStagesExection());//分期执行
                    columns.put("DEADLINE_STAGE", cBase.getDeadlineStage());// 分期截至日期(最后日期)
                    columns.put("APPLY_DATE_STAGE", cBase.getApplyDateStage());//申请日期
                    columns.put("APPROVAL_DATE_STAGE", cBase.getApprovalDateStage());//批准日期
                    columns.put("IS_PARTY_EXECUTION", cBase.getIsPartyExecution());//是否当事人执行
                    columns.put("PARTY_ACTIVE_PERFOR_DATE", cBase.getPartyActivePerforDate());//当事人执行日期
                    columns.put("IS_ORGAN_ENFORCE", cBase.getIsOrganEnforce());//是否行政机关强制执行
                }else if("05".equals(grading)){//结案
                    columns.put("CLOSED_REPORT_APPROVE_PERSON", cBase.getClosedReportApprovePerson()); //结案报告呈批人
                    columns.put("CLOSED_APPROVE_PERSON", cBase.getClosedApprovePerson()); //结案批准人
                    columns.put("CLOSED_APPROVE_SUGGEST", cBase.getClosedApproveSuggest()); //结案批准意见
                    
                    columns.put("CLOSED_STATE", cBase.getClosedState()); //结案状态
                    columns.put("CLOSED_DATE", cBase.getClosedDate());// 结案日期
                    columns.put("CLOSED_CASE_INFO", cBase.getClosedCaseInfo());//结案说明
                    columns.put("IS_SUBMIT", cBase.getIsSubmit());//是否提交
                }
            }
            caseCommonBaseDao.updateCaseInfoByColumnsAndId(columns, id);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: saveRevoke();
    * @Description: 保存撤销立案信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:50:19 
    *
     */
    public void saveRevoke(CaseCommonBase cBase) throws Exception{
        try {
            CaseCommonBaseModel cbModel = new CaseCommonBaseModel();
            cbModel.setGrading("03");
            //更新案件调查取证信息
            updateCaseInfoByColumnsAndId(cBase, cbModel);
            
            Map<String, Object> columns = new HashMap<String, Object>();
            columns.put("CLOSED_STATE", cBase.getClosedState()); //结案状态
            columns.put("CURRENT_STATE", cBase.getCurrentState()); //案件当前状态
            columns.put("IS_NEXT", cBase.getIsNext()); //案件当前状态
            caseCommonBaseDao.updateCaseInfoByColumnsAndId(columns, cBase.getId());
            
            //先删除旧的数据，再插入新的数据。
            caseCommonRevokeDao.deleteCaseRevoke(cBase);
            caseCommonRevokeDao.saveBatchCaseRevoke(cBase.getCaseCommonRevokes());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: saveNopunishment()
    * @Description: 保存不予处罚案件信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月18日 下午5:37:30 
    *
     */
    public void saveNopunishment(CaseCommonBase cBase) throws Exception {
        try {
            CaseCommonBaseModel cbModel = new CaseCommonBaseModel();
            cbModel.setGrading("03");
            //更新案件作出处罚信息
            updateCaseInfoByColumnsAndId(cBase, cbModel);
            
            Map<String, Object> columns = new HashMap<String, Object>();
            columns.put("CLOSED_STATE", cBase.getClosedState()); //结案状态
            columns.put("CURRENT_STATE", cBase.getCurrentState()); //案件当前状态
            columns.put("IS_NEXT", cBase.getIsNext()); //案件当前状态
            columns.put("IS_PLOT", cBase.getIsPlot());//是否减轻情节
            columns.put("ILLEGAL_FACTS", cBase.getIllegalFacts()); //违法事实
            columns.put("IS_PUNISHMENT", cBase.getIsPunishment()); //是否作出处罚决定（1作出处罚，2不予处罚）
            /*
             * columns.put("IS_PLOT", null);//是否减轻情节 
             * columns.put("ILLEGAL_FACTS", null);//违法事实 
             * columns.put("DOCUMENT_PATH", null); //上传文件唯一标识ID
             * columns.put("PUNISHMENT_CODE", null);//处罚决定书文号 
             * columns.put("PUNISHMENT_DATE",null); //处罚决定书日期
             * columns.put("PD_SENT_DATE", null);//决定书送达日期
             * columns.put("PP_SENT_DATE", null);//处罚缴款书送达日期
             * columns.put("IS_WARN",null);//是否警告
             * columns.put("IS_FINE", null);//是否罚款 
             * columns.put("FINE_SUM",null);//罚款金额
             * columns.put("IS_ORDER_CLOSURE", null);// 是否责令停产停业
             * columns.put("IS_REVOKE_LICENSE", null);//是否吊销许可证或营业执照
             * columns.put("IS_TD_LICENSE", null);//是否暂扣许可证或营业执照
             * columns.put("DETAIN_PERMIT_DAYS", null);//暂扣天数
             * columns.put("STARTDATE_WITHHOLD", null);//暂扣起止日期
             * columns.put("ENDDATE_WITHHOLD", null);//暂扣结束时间 
             * columns.put("IS_DTAIN",null);//是否拘留
             * columns.put("DTAIN_DAYS", null);//拘留天数
             * columns.put("STARTDATE_DETAIN", null);//拘留起止时间 
             * columns.put("ENDDATE_DETAIN",null);//拘留结束时间 
             * columns.put("IS_CONFISCATE", null);//是否没收违法所得
             * columns.put("CONFISCATE_MONEY", null);//没收违法所得金额
             * columns.put("IS_CONFIS_PROPERTY", null);//是否没收非法财物
             * columns.put("CONFISCATE_PRO_MON", null);// 没收违法财物所得金额 
             * columns.put("IS_OTHER",null);//其他 
             * columns.put("OTHER_DETAIL_CONTENT", null);//其他明细内容
             */
            caseCommonBaseDao.updateCaseInfoByColumnsAndId(columns, cBase.getId());
            
            //先删除旧的不予处罚信息，再插入新的不予处罚信息
            caseCommonNopunishmentDao.deleteCaseNoPunishment(cBase);
            caseCommonNopunishmentDao.saveBatchCaseNoPunishment(cBase.getCaseCommonNopunishments());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: saveRevokePunish()
    * @Description: 保存撤销原处罚决定信息
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午7:00:52 
    *
     */
    public void saveRevokePunish(CaseCommonBase cBase) throws Exception {
        try {
            CaseCommonBaseModel cbModel = new CaseCommonBaseModel();
            cbModel.setGrading("04");
            //更新案件处罚执行信息
            updateCaseInfoByColumnsAndId(cBase, cbModel);
            
            //更新案件状态 为 撤销原处罚决定
            Map<String, Object> columns = new HashMap<String, Object>();
            columns.put("CLOSED_STATE", cBase.getClosedState()); //结案状态
            columns.put("CURRENT_STATE", cBase.getCurrentState()); //案件当前状态
            caseCommonBaseDao.updateCaseInfoByColumnsAndId(columns, cBase.getId());
            
            //先删除旧的数据，再插入新的数据。
            caseCommonRevokePunishDao.deleteCaseRevokePunish(cBase);
            caseCommonRevokePunishDao.saveBatchCaseRevokePunish(cBase.getCaseCommonRevokePunishs());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: saveCaseEnd()
    * @Description: 保存案件终结信息
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午7:04:01 
    *
     */
    public void saveCaseEnd(CaseCommonBase cBase) throws Exception {
        try {
            CaseCommonBaseModel cbModel = new CaseCommonBaseModel();
            cbModel.setGrading("04");
            //更新案件处罚执行信息
            updateCaseInfoByColumnsAndId(cBase, cbModel);
            //更新案件状态 为 终结
            Map<String, Object> columns = new HashMap<String, Object>();
            columns.put("CLOSED_STATE", cBase.getClosedState()); //结案状态
            columns.put("CURRENT_STATE", cBase.getCurrentState()); //案件当前状态
            caseCommonBaseDao.updateCaseInfoByColumnsAndId(columns, cBase.getId());
            
            //先删除旧的数据，再插入新的信息
            caseCommonEndDao.deleteCaseEnd(cBase);
            caseCommonEndDao.saveBatchCaseEnd(cBase.getCaseCommonEnds());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: updateOrdeleteOrSubmitByIds()
    * @Description: 更新或者删除或者提交案件
    *
    * @param: cbModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月23日 下午6:18:17 
    *
     */
    public void updateOrdeleteOrSubmitByIds(CaseCommonBaseModel cbModel) throws Exception{
        Map<String, Object> columns = new HashMap<String, Object>();
        Date data = new Date();
        try {
            if (!TeeUtility.isNullorEmpty(cbModel.getIsSubmit())) {
                columns.put("IS_SUBMIT", cbModel.getIsSubmit()); //提交 或 退回 状态
                columns.put("SUBMIT_DATE", data); //提交，退回时间
            }
            if (!TeeUtility.isNullorEmpty(cbModel.getIsDelete())) {
                columns.put("IS_DELETE", cbModel.getIsDelete()); //删除状态
                columns.put("DELETE_DATE", data); //删除时间
            }
            columns.put("UPDATE_DATE", data); //更新时间
            caseCommonBaseDao.updateOrDeleteOrSubmitByIds(columns, cbModel.getId());
        } catch (Exception e) {
        }
    }
    
    /**
     * 
    * @Function: subjectList()
    * @Description: 查询执法主体
    *
    * @param: subjectModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月15日 下午5:14:15 
    *
     */
    public List<Subject> subjectList(SubjectModel subjectModel) {
        return subjectDao.subjectList(subjectModel);
    }
    
    /**
     * 
    * @Function: findDepartmentList()
    * @Description: 查询所有部门
    *
    * @param: model 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月15日 下午6:26:38 
    *
     */
    public List<TblDepartmentInfo> findDepartmentList(TblDepartmentModel model) {
        return departmentDao.findDepartmentList(model);
    }
    
    /**
     * 
    * @Function: findCasePowers()
    * @Description: 查询违法行为
    *
    * @param: tModel 表格参数
    * @param: cGistModel 查询参数
    * @return：依据列表
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月9日 上午11:21:11 
    *
     */
    public List<CaseCommonPower> findCasePowers(TeeDataGridModel tModel, CaseCommonPowerModel cPowerModel) {
        return caseCommonPowerDao.findCasePowers(tModel.getFirstResult(), tModel.getRows(), cPowerModel);
    }
    
    /**
     * 
    * @Function: findCommonGists()
    * @Description: 查询违法依据
    *
    * @param: tModel 表格参数
    * @param: cGistModel 查询参数
    * @return：依据列表
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月9日 上午11:21:11 
    *
     */
    public List<CaseCommonGist> findCommonGists(TeeDataGridModel tModel, CaseCommonGistModel cGistModel) {
        return caseCommonGistDao.findCommonGists(tModel.getFirstResult(), tModel.getRows(), cGistModel);
    }
    
    /**
     * 
    * @Function: findCasePunishs()
    * @Description: 查询处罚依据
    *
    * @param: tModel 表格参数
    * @param: cGistModel 查询参数
    * @return：依据列表
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月9日 上午11:21:11 
    *
     */
    public List<CaseCommonPunish> findCasePunishs(TeeDataGridModel tModel, CaseCommonPunishModel cPunishModel) {
        return caseCommonPunishDao.findCasePunishs(tModel.getFirstResult(), tModel.getRows(), cPunishModel);
    }

    public List<CaseCommonBaseSearchModel> findListByPage(TeeDataGridModel tModel, Map<String, Object> requestData, List<CaseCommonDataModel> caseCommonDataModels, OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
        List<CaseCommonBaseSearchModel> caseCommonBaseSearchModels = new ArrayList<>();
        CaseCommonBaseSearchModel caseCommonBaseSearchModel = new CaseCommonBaseSearchModel();
        List<CaseCommonBase> caseCommonBases = caseCommonBaseDao.findListByPageSearch(tModel, requestData, caseCommonDataModels, orgCtrl);
        for (int i = 0; i < caseCommonBases.size(); i++) {
            caseCommonBaseSearchModel = new CaseCommonBaseSearchModel();
            BeanUtils.copyProperties(caseCommonBases.get(i), caseCommonBaseSearchModel);
            caseCommonBaseSearchModels.add(caseCommonBaseSearchModel);
        }
        return caseCommonBaseSearchModels;
    }

    public Long listCount(Map<String, Object> requestData, List<CaseCommonDataModel> caseCommonDataModels, OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
        return caseCommonBaseDao.listCount(requestData, caseCommonDataModels, orgCtrl);
    }

    public List<CaseCommonBaseSearchModel> findListByPage(TeeDataGridModel tModel, Map<String, Object> requestData,
            OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
        List<CaseCommonBaseSearchModel> caseCommonBaseSearchModels = new ArrayList<>();
        CaseCommonBaseSearchModel caseCommonBaseSearchModel = new CaseCommonBaseSearchModel();
        List<CaseCommonBase> caseCommonBases = caseCommonBaseDao.findListByPageSearch(tModel, requestData, orgCtrl);
        for (int i = 0; i < caseCommonBases.size(); i++) {
            caseCommonBaseSearchModel = new CaseCommonBaseSearchModel();
            caseCommonBaseSearchModel = copyAllProperties(caseCommonBases.get(i));
            caseCommonBaseSearchModels.add(caseCommonBaseSearchModel);
        }
        return caseCommonBaseSearchModels;
    }

    public Long listCount(Map<String, Object> requestData, OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
        return caseCommonBaseDao.listCount(requestData, orgCtrl);
    }
    
    private CaseCommonBaseSearchModel copyAllProperties(CaseCommonBase caseCommonBase){
        CaseCommonBaseSearchModel caseCommonBaseSearchModel = new CaseCommonBaseSearchModel();
        BeanUtils.copyProperties(caseCommonBase, caseCommonBaseSearchModel);
        // 从代码表获取当事人类型
        if (caseCommonBase.getPartyType() != null) {
            caseCommonBaseSearchModel.setPartyTypeValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("COMMON_PARTY_TYPE", caseCommonBase.getPartyType()));
        }
        // 从代码表获取办理状态
        if (caseCommonBase.getCurrentState() != null) {
            caseCommonBaseSearchModel.setCurrentStateValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("COMMON_CURRENT_STATE", caseCommonBase.getCurrentState()));
        }
        // 从代码表获取结案状态
        if (caseCommonBase.getClosedState() != null) {
            caseCommonBaseSearchModel.setClosedStateValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("COMMON_CLOSED_STATE", caseCommonBase.getClosedState()));
        }
        // 将立案日期转化为格式化的字符串
        if (caseCommonBase.getFilingDate() != null) {
            caseCommonBaseSearchModel.setFilingDateStr(TeeDateUtil.format(caseCommonBase.getFilingDate(), "yyyy-MM-dd"));    
        }else {
            caseCommonBaseSearchModel.setFilingDateStr("");
        }
        // 将调查终结日期转化为格式化的字符串
        if (caseCommonBase.getSurveyEndDate() != null) {
            caseCommonBaseSearchModel.setSurveyEndDateStr(TeeDateUtil.format(caseCommonBase.getSurveyEndDate(), "yyyy-MM-dd"));    
        }else {
            caseCommonBaseSearchModel.setSurveyEndDateStr("");
        }
        // 将行政处罚决定书日期转化为格式化的字符串
        if (caseCommonBase.getPunishmentDate() != null) {
            caseCommonBaseSearchModel.setPunishmentDateStr(TeeDateUtil.format(caseCommonBase.getPunishmentDate(), "yyyy-MM-dd"));    
        }else {
            caseCommonBaseSearchModel.setPunishmentDateStr("");
        }
        // 将决定书送达日期转化为格式化的字符串
        if (caseCommonBase.getPdSentDate() != null) {
            caseCommonBaseSearchModel.setPdSentDateStr(TeeDateUtil.format(caseCommonBase.getPdSentDate(), "yyyy-MM-dd"));    
        }else {
            caseCommonBaseSearchModel.setPdSentDateStr("");
        }
        // 将结案日期转化为格式化的字符串
        if (caseCommonBase.getClosedDate() != null) {
            caseCommonBaseSearchModel.setClosedDateStr(TeeDateUtil.format(caseCommonBase.getClosedDate(), "yyyy-MM-dd"));    
        }else {
            caseCommonBaseSearchModel.setClosedDateStr("");
        }
        // 将入库日期转化为格式化的字符串
        if (caseCommonBase.getCreateDate() != null) {
            caseCommonBaseSearchModel.setCreateDateStr(TeeDateUtil.format(caseCommonBase.getCreateDate(), "yyyy-MM-dd"));    
        }else {
            caseCommonBaseSearchModel.setCreateDateStr("");
        }
        // 获取案件总时长
        if(caseCommonBase.getFilingDate() != null && caseCommonBase.getClosedDate() != null){
            caseCommonBaseSearchModel.setCaseTime((caseCommonBase.getClosedDate().getTime() - caseCommonBase.getFilingDate().getTime())/1000/60/60/24 + 1);
        }
        // 获取立案周期
        if(caseCommonBase.getFilingDate() != null && caseCommonBase.getCaseSources() != null && caseCommonBase.getCaseSources().size() > 0 && caseCommonBase.getCaseSources().get(0).getCommonCaseDate() != null){
            caseCommonBaseSearchModel.setFilingTime((caseCommonBase.getCaseSources().get(0).getCommonCaseDate().getTime() - caseCommonBase.getFilingDate().getTime())/1000/60/60/24 + 1);
        }
        // 获取调查取证周期
        if(caseCommonBase.getFilingDate() != null && caseCommonBase.getSurveyEndDate() != null){
            caseCommonBaseSearchModel.setSurveyTime((caseCommonBase.getSurveyEndDate().getTime() - caseCommonBase.getFilingDate().getTime())/1000/60/60/24 + 1);
        }
        // 获取作出处罚决定周期
        if(caseCommonBase.getPunishmentDate() != null && caseCommonBase.getSurveyEndDate() != null){
            caseCommonBaseSearchModel.setPunishTime((caseCommonBase.getPunishmentDate().getTime() - caseCommonBase.getSurveyEndDate().getTime())/1000/60/60/24 + 1);
        }
        // 获取处罚决定执行周期
        if(caseCommonBase.getPunishDecisionExecutDate() != null && caseCommonBase.getPunishmentDate() != null){
            caseCommonBaseSearchModel.setPunishDecisionExecutTime((caseCommonBase.getPunishDecisionExecutDate().getTime() - caseCommonBase.getPunishmentDate().getTime())/1000/60/60/24 + 1);
        }
        // 获取结案周期
        if(caseCommonBase.getPunishDecisionExecutDate() != null && caseCommonBase.getClosedDate() != null){
            caseCommonBaseSearchModel.setClosedTime((caseCommonBase.getClosedDate().getTime() - caseCommonBase.getPunishDecisionExecutDate().getTime())/1000/60/60/24 + 1);
        }
        // 获取案件来源
        if(caseCommonBase.getDataSource() != null){
            caseCommonBaseSearchModel.setDataSourceValue(caseCommonBase.getDataSource()==1?"系统录入":(caseCommonBase.getDataSource()==2?"接口对接":""));
        }
        // 获取执法人员姓名
        if(caseCommonBase.getCaseCommonStaffs() != null && caseCommonBase.getCaseCommonStaffs().size() > 0){
            String officeName = "";
            for (int i = 0; i < caseCommonBase.getCaseCommonStaffs().size(); i++) {
                officeName += caseCommonBase.getCaseCommonStaffs().get(i).getOfficeName() + ",";
            }
            caseCommonBaseSearchModel.setOfficeName(officeName.substring(0,officeName.length() - 1));
        }else{
            caseCommonBaseSearchModel.setOfficeName("");
        }
        return caseCommonBaseSearchModel;
    }
}
